package com.example.pineapple.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pineapple.R;
import com.example.pineapple.beans.MyMesS;
import com.example.pineapple.beans.PicCompress;

import java.util.List;

public class MyMesSAdapter extends BaseAdapter {
    private Context context;
    private List<MyMesS> myMesSList;
    public MyMesSAdapter(Context context, List<MyMesS> myMesSList){
        this.context = context;
        this.myMesSList = myMesSList;
    }

    @Override
    public int getCount() {
        return this.myMesSList.size();
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
        MyMesSAdapter.ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(this.context, R.layout.item_mymes, null);
            viewHolder = new ViewHolder();
            viewHolder.mymes_name = convertView.findViewById(R.id.mymes_name);
            viewHolder.image_mymes_head = convertView.findViewById(R.id.image_mymes_head);
            convertView.setTag(viewHolder);
        }else {
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        viewHolder.mymes_name.setText(this.myMesSList.get(position).getReceivename().trim());
        PicCompress picCompress = new PicCompress();
        String userhead = this.myMesSList.get(position).getReceivehead();
        Bitmap userheadbitmap = picCompress.decodeAndDecompressBase64ToImage(userhead);
        viewHolder.image_mymes_head.setImageBitmap(userheadbitmap);
        return convertView;
    }

    private static class ViewHolder{

        TextView mymes_name;
        ImageView image_mymes_head;


    }
}
