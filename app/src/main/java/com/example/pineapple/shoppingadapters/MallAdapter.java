package com.example.pineapple.shoppingadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Commodity;

import java.util.List;

public class MallAdapter extends BaseAdapter {
    private Context context;
    private List<Commodity> commodityList;

    public MallAdapter(Context context, List<Commodity> commodityList){
        this.context = context;
        this.commodityList = commodityList;
        System.out.println("==="+ commodityList);



    }
    @Override
    public int getCount() {
        return this.commodityList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.commodityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(this.context, R.layout.item_shopping_mall,null);
            viewHolder = new ViewHolder();
            viewHolder.mall_title = convertView.findViewById(R.id.tv_title);
            viewHolder.mall_location = convertView.findViewById(R.id.tv_locate);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mall_title.setText(this.commodityList.get(position).getTitle());
        viewHolder.mall_location.setText(this.commodityList.get(position).getLocation());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
    public static class ViewHolder{
        TextView mall_title;
        TextView mall_location;
    }
}
