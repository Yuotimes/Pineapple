package com.example.pineapple.shoppingadapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.smartimageview.SmartImageTask;
import com.example.pineapple.smartimageview.SmartImageView;

import java.util.List;

public class CommodityAdminAdapter extends BaseAdapter {
    private Context context;
    private List<Commodity> commodityList;

    private CommodityAdminClickListener commodityAdminClickListener;

    public interface CommodityAdminClickListener{
        void commodityInfoAddListener(Commodity commodity, int position);
        void commodityInfoDeleteListener(Commodity commodity, int position);
    }

    public CommodityAdminAdapter(Context context, List<Commodity> commodityList, CommodityAdminClickListener listener){
        this.context = context;
        this.commodityList = commodityList;
        this.commodityAdminClickListener = listener;
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
            convertView = View.inflate(this.context, R.layout.item_commodity_admin,null);
            viewHolder = new ViewHolder();
            viewHolder.siv_image = convertView.findViewById(R.id.siv_image);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_locate = convertView.findViewById(R.id.tv_locate);
            viewHolder.tv_description = convertView.findViewById(R.id.tv_description);
            viewHolder.tv_decrease = convertView.findViewById(R.id.tv_decrease);
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
            viewHolder.tv_increase = convertView.findViewById(R.id.tv_increase);

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
        Commodity commodity = this.commodityList.get(position);
        viewHolder.tv_title.setText(commodity.getTitle().trim());
        viewHolder.tv_locate.setText(commodity.getLocation().trim());
        viewHolder.tv_description.setText(commodity.getDescription().trim());
        viewHolder.tv_number.setText(commodity.getNumber()+"");

        viewHolder.tv_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commodity.setNumber(Math.max(0, commodity.getNumber() - 1));
                viewHolder.tv_number.setText(commodity.getNumber()+"");
                commodityAdminClickListener.commodityInfoDeleteListener(commodity, position);
            }
        });
        viewHolder.tv_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commodity.setNumber(commodity.getNumber() + 1);
                viewHolder.tv_number.setText(commodity.getNumber()+"");
                commodityAdminClickListener.commodityInfoAddListener(commodity, position);
            }
        });

        return convertView;

    }
    private static class ViewHolder{
        SmartImageView siv_image;
        TextView tv_title;
        TextView tv_locate;
        TextView tv_description;
        TextView tv_decrease, tv_number, tv_increase;
    }
}