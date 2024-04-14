package com.example.pineapple.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.example.pineapple.R;
import com.example.pineapple.beans.MyMesS;
import com.example.pineapple.beans.MyMessage;
import com.example.pineapple.beans.PicCompress;

import java.util.List;

public class MyMessageAdapter extends BaseAdapter {
    private Context context;
    private List<MyMessage> myMessageList;
    private String account;
    public MyMessageAdapter(Context context, List<MyMessage> myMessageList,String account){
        this.context = context;
        this.myMessageList = myMessageList;
        this.account = account;
    }
    @Override
    public int getCount() {
        return this.myMessageList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MyMessageAdapter.ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(this.context, R.layout.item_mymessage, null);
            viewHolder = new ViewHolder();
            viewHolder.im_my_messages_re = convertView.findViewById(R.id.im_my_messages_re);
            viewHolder.tv_my_messages_re = convertView.findViewById(R.id.tv_my_messages_re);
            viewHolder.tv_my_messages_se = convertView.findViewById(R.id.tv_my_messages_se);
            viewHolder.im_my_messages_se = convertView.findViewById(R.id.im_my_messages_se);
            convertView.setTag(viewHolder);
        }else {
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        String sendaccount = this.myMessageList.get(position).getSendaccount().trim();
        if (sendaccount.equals(account)){
            viewHolder.tv_my_messages_se.setText(this.myMessageList.get(position).getSendtext().trim());
            PicCompress picCompress = new PicCompress();
            String userhead = this.myMessageList.get(position).getSendhead();
            Bitmap userheadbitmap = picCompress.decodeAndDecompressBase64ToImage(userhead);
            viewHolder.im_my_messages_se.setImageBitmap(userheadbitmap);
            viewHolder.im_my_messages_re.setImageResource(R.drawable.write);
            viewHolder.im_my_messages_re.setVisibility(View.GONE);
            viewHolder.tv_my_messages_re.setText("");
            viewHolder.tv_my_messages_re.setVisibility(View.GONE);
        }else {
            viewHolder.tv_my_messages_re.setText(this.myMessageList.get(position).getSendtext().trim());
            PicCompress picCompress = new PicCompress();
            String userhead = this.myMessageList.get(position).getSendhead();
            Bitmap userheadbitmap = picCompress.decodeAndDecompressBase64ToImage(userhead);
            viewHolder.im_my_messages_re.setImageBitmap(userheadbitmap);
            viewHolder.im_my_messages_se.setImageResource(R.drawable.write);
            viewHolder.im_my_messages_se.setVisibility(View.GONE);
            viewHolder.tv_my_messages_se.setText("");
            viewHolder.tv_my_messages_se.setVisibility(View.GONE);
        }
        return convertView;
    }

    private static class ViewHolder{

        TextView tv_my_messages_se;
        ImageView im_my_messages_se;
        TextView tv_my_messages_re;
        ImageView im_my_messages_re;



    }
}
