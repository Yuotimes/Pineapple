package com.example.pineapple.shoppingadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.smartimageview.SmartImageView;

import java.util.List;

public class DetailsAdapter extends BaseAdapter {
    private Context context;
    private List<Commodity> commodityList;
    private MyClickListener listener;

    public interface MyClickListener{

        public void clickListener(View v);

    }
    public DetailsAdapter(Context context, List<Commodity> commodityList, MyClickListener listener) {
        this.context = context;
        this.commodityList = commodityList;
        this.listener = listener;
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
        if (convertView == null) {
            convertView = View.inflate(this.context, R.layout.item_details, null);
            viewHolder = new ViewHolder();
            viewHolder.siv_image = convertView.findViewById(R.id.siv_image);
            viewHolder.details_title = convertView.findViewById(R.id.details_title);
            viewHolder.details_locate = convertView.findViewById(R.id.details_location);
            viewHolder.details_price= convertView.findViewById(R.id.details_price);
            viewHolder.btn_cancel = convertView.findViewById(R.id.btn_cancel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.siv_image.setImageUrl(this.commodityList.get(position).getImage());
        viewHolder.details_title.setText(this.commodityList.get(position).getTitle().trim());
        viewHolder.details_locate.setText(this.commodityList.get(position).getLocation().trim());
        viewHolder.details_price.setText(this.commodityList.get(position).getPrice().trim());
        viewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickListener(v);


            }
        });
        viewHolder.btn_cancel.setTag(position);
        return convertView;
    }

    private static class ViewHolder {
        SmartImageView siv_image;
        TextView details_title;
        TextView details_locate;
        TextView details_price;
        ImageButton btn_cancel;
    }
}
