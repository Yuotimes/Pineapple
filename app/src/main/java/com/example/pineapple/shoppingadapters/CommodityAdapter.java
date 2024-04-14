package com.example.pineapple.shoppingadapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.smartimageview.SmartImageTask;
import com.example.pineapple.smartimageview.SmartImageView;

import java.text.BreakIterator;
import java.util.List;

public class CommodityAdapter extends BaseAdapter {
    private Context context;
    private List<Commodity> commodityList;


    public CommodityAdapter(Context context, List<Commodity> commodityList){
        this.context = context;
        this.commodityList = commodityList;

    }

    @Override
    public int getCount() {
        return this.commodityList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(this.context, R.layout.item_commodity,null);
            viewHolder = new ViewHolder();
            viewHolder.siv_image = convertView.findViewById(R.id.siv_image);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_locate = convertView.findViewById(R.id.tv_locate);
            viewHolder.tv_description = convertView.findViewById(R.id.tv_description);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder =(ViewHolder) convertView.getTag();

        }
        viewHolder.siv_image.setImageUrl(this.commodityList.get(position).getImage(), new SmartImageTask.OnCompleteListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {

            }

            @Override
            public void onFail() {

            }
        });
        viewHolder.tv_title.setText(this.commodityList.get(position).getTitle().trim());
        viewHolder.tv_locate.setText(this.commodityList.get(position).getLocation().trim());
        viewHolder.tv_description.setText(this.commodityList.get(position).getDescription().trim());


        return convertView;

    }
    public static class ViewHolder{
        public BreakIterator tv_number;
        public TextClassification tv_increase;
        SmartImageView siv_image;
        TextView tv_title;
        TextView tv_locate;
        TextView tv_description;

    }
}