package com.example.pineapple.ticketadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.ticketbeans.TravelInfo;

import java.util.List;

public class TravelAdapter extends BaseAdapter {
    private Context context;
    private List<TravelInfo> travelInfoList;
    private MyTravelClickListener travellistener;



    public interface MyTravelClickListener{
        void travelclickListener(View v);
        void travelSelectListener(TravelInfo travelInfo,int position);
   }
   public TravelAdapter(Context context, List<TravelInfo> travelInfoList, MyTravelClickListener travellistener){
        this.context = context;
        this.travelInfoList = travelInfoList;
        this.travellistener = travellistener;

   }

    @Override
    public int getCount() {
        return travelInfoList.size();
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
        convertView = View.inflate(this.context, R.layout.item_travel, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.travel_title = convertView.findViewById(R.id.travel_title);
        viewHolder.travel_time = convertView.findViewById(R.id.travel_time);
        viewHolder.travel_number = convertView.findViewById(R.id.travel_number);
        viewHolder.travel_price = convertView.findViewById(R.id.travel_price);
        viewHolder.iv_image = convertView.findViewById(R.id.iv_image);
        viewHolder.btn_deletetravel = convertView.findViewById(R.id.btn_deletetravel);
        viewHolder.ck_travel = convertView.findViewById(R.id.ck_travel);

        TravelInfo travelInfo = this.travelInfoList.get(position);

        viewHolder.travel_title.setText(travelInfo.getTitle().trim());
        viewHolder.travel_time.setText(travelInfo.getTime().trim());
        viewHolder.travel_number.setText(travelInfo.getNumber().trim());
        viewHolder.travel_price.setText("￥"+travelInfo.getPrice().trim());
        Glide.with(context).load(travelInfo.getImage()).into(viewHolder.iv_image);
        viewHolder.ck_travel.setChecked(travelInfo.isSelected());

        viewHolder.ck_travel.setOnCheckedChangeListener((buttonView, isChecked) ->{
            travelInfo.setSelected(isChecked);
            travellistener.travelSelectListener(travelInfo,position);
        } );

        viewHolder.btn_deletetravel.setOnClickListener(v -> travellistener.travelclickListener(v));
        viewHolder.btn_deletetravel.setTag(position);

        return convertView;
    }

    static class ViewHolder{
        TextView travel_title;
        TextView travel_time;
        TextView travel_number;
        TextView travel_price;
        ImageView iv_image;
        ImageButton btn_deletetravel;
        CheckBox ck_travel;

    }
}
