package com.example.pineapple.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.beans.Essays;
import com.example.pineapple.beans.MyComments;
import com.example.pineapple.beans.PicCompress;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentsAdapter extends BaseAdapter {
    private Context context;
    private List<MyComments> commentsList;
    public CommentsAdapter(Context context, List<MyComments> commentsList){
        this.context = context;
        this.commentsList = commentsList;
    }
    @Override
    public int getCount() {
        return this.commentsList.size();
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
        CommentsAdapter.ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(this.context, R.layout.item_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.comment_user_name = convertView.findViewById(R.id.comments_user_name);
            viewHolder.comment_text = convertView.findViewById(R.id.comments_text);
            viewHolder.comment_date = convertView.findViewById(R.id.comments_date);
            viewHolder.comment_user_head = convertView.findViewById(R.id.comments_user_head);
            convertView.setTag(viewHolder);
        }else {
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        viewHolder.comment_user_name.setText(this.commentsList.get(position).getUsername().trim());
        viewHolder.comment_text.setText(this.commentsList.get(position).getCommentstext().trim());
//        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this.commentsList.get(position).getCreatedAt()).trim();
        viewHolder.comment_date.setText(this.commentsList.get(position).getCreatedAt().trim());
        PicCompress picCompress = new PicCompress();
        String userhead = this.commentsList.get(position).getUserhead();
        Bitmap userheadbitmap = picCompress.decodeAndDecompressBase64ToImage(userhead);
        viewHolder.comment_user_head.setImageBitmap(userheadbitmap);
        return convertView;
    }
    private static class ViewHolder{

        TextView comment_user_name;
        TextView comment_text;
        TextView comment_date;
        ImageView comment_user_head;



    }
}
