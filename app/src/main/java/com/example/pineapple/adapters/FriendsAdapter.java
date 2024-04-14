package com.example.pineapple.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pineapple.R;
import com.example.pineapple.activities.EssayActivity;
import com.example.pineapple.activities.SendMessageActivity;
import com.example.pineapple.activities.ShowMyScenesActivity;
import com.example.pineapple.beans.MyComments;
import com.example.pineapple.beans.MyFriends;
import com.example.pineapple.beans.MyMesS;
import com.example.pineapple.beans.MyScenes;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.beans.PicCompress;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class FriendsAdapter extends BaseAdapter {
    private Context context;
    private List<MyFriends> myFriendsList;
    public FriendsAdapter(Context context, List<MyFriends> myFriendsList) {
        this.context = context;
        this.myFriendsList = myFriendsList;
    }
    @Override
    public int getCount() {
        if(myFriendsList!=null&&myFriendsList.size()>0){
            return this.myFriendsList.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return myFriendsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        FriendsAdapter.ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(this.context, R.layout.item_friends, null);
            viewHolder = new ViewHolder();
            viewHolder.friends_name = convertView.findViewById(R.id.friends_name);
            viewHolder.image_friends_head = convertView.findViewById(R.id.image_friends_head);
            viewHolder.rl_friend_item = convertView.findViewById(R.id.rl_friend_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        viewHolder.friends_name.setText(this.myFriendsList.get(position).getFriendsname().trim());
        PicCompress picCompress = new PicCompress();
        String userhead = this.myFriendsList.get(position).getFriendshead();
        Bitmap userheadbitmap = picCompress.decodeAndDecompressBase64ToImage(userhead);
        viewHolder.image_friends_head.setImageBitmap(userheadbitmap);

        viewHolder.rl_friend_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFriends myFriends = myFriendsList.get(position);
                String account = myFriends.getAccount();
                String friendsaccount = myFriends.getFriendsaccount();
                String friendsname = myFriends.getFriendsname();
                String friendshead = myFriends.getFriendshead();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定要和" +friendsname+"发消息吗？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BmobQuery<MyMesS> query = new BmobQuery<>();
                        query.addWhereEqualTo("sendaccount", account);
                        query.addWhereEqualTo("receiveaccount", friendsaccount);
                        query.findObjects(new FindListener<MyMesS>() {
                            @Override
                            public void done(List<MyMesS> list, BmobException e) {
                                if (list.size() == 1){
                                    Toast.makeText(context, "对话已经存在了去消息列表看看吧~", Toast.LENGTH_SHORT).show();
                                }else {
                                    MyMesS myMesS = new MyMesS();
                                    myMesS.setSendaccount(account);
                                    myMesS.setReceiveaccount(friendsaccount);
                                    myMesS.setReceivename(friendsname);
                                    myMesS.setReceivehead(friendshead);
                                    myMesS.setMessageID(account+friendsname);
                                    myMesS.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null){
                                                BmobQuery<MyUsers> query = new BmobQuery<>();
                                                query.addWhereEqualTo("account", account);
                                                query.findObjects(new FindListener<MyUsers>() {
                                                    @Override
                                                    public void done(List<MyUsers> list, BmobException e) {
                                                        if (e == null) {
                                                            MyUsers users = list.get(0);
                                                            String name = users.getNickname();
                                                            String head = users.getHead();
                                                            MyMesS myMesS = new MyMesS();
                                                            myMesS.setSendaccount(friendsaccount);
                                                            myMesS.setReceiveaccount(account);
                                                            myMesS.setReceivename(name);
                                                            myMesS.setReceivehead(head);
                                                            myMesS.setMessageID(account+friendsname);
                                                            myMesS.save(new SaveListener<String>() {
                                                                @Override
                                                                public void done(String s, BmobException e) {
                                                                    if(e == null){
                                                                        BmobQuery<MyMesS> query = new BmobQuery<>();
                                                                        query.addWhereEqualTo("sendaccount", account);
                                                                        query.addWhereEqualTo("receiveaccount",friendsaccount);
                                                                        query.findObjects(new FindListener<MyMesS>() {
                                                                            @Override
                                                                            public void done(List<MyMesS> list, BmobException e) {
                                                                                MyMesS myMesS = list.get(0);
                                                                                String messageID = myMesS.getMessageID();
                                                                                Intent intent = new Intent(context, SendMessageActivity.class);
                                                                                intent.putExtra("messageID", messageID);
                                                                                context.startActivity(intent);
                                                                            }
                                                                        });

                                                                    }
                                                                }
                                                            });

                                                        }
                                                    }
                                                });
                                            }

                                        }
                                    });
                                }
                            }
                        });

                    }
                }).setCancelable(false).show();
            }
        });
        return convertView;
    }
    private static class ViewHolder{

        TextView friends_name;
        ImageView image_friends_head;
        RelativeLayout rl_friend_item;

    }
}
