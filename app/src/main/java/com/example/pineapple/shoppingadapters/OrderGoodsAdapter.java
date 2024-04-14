package com.example.pineapple.shoppingadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.smartimageview.SmartImageView;

import java.util.List;

public class OrderGoodsAdapter extends RecyclerView.Adapter<OrderGoodsAdapter.ViewHolder>{

    private List<Commodity> commodityList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public OrderGoodsAdapter(Context context, List<Commodity> commodityList) {
        this.context = context;
        this.commodityList = commodityList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        SmartImageView siv_image;
        TextView details_title;
        TextView details_locate;
        TextView details_price;

        public ViewHolder(View itemView) {
            super(itemView);
            this.siv_image = itemView.findViewById(R.id.siv_image);
            this.details_title = itemView.findViewById(R.id.details_title);
            this.details_locate = itemView.findViewById(R.id.details_location);
            this.details_price = itemView.findViewById(R.id.details_price);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_order_goods,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Commodity info = commodityList.get(position);
        System.out.println("____"+info);
        viewHolder.siv_image.setImageUrl(info.getImage());
        viewHolder.details_title.setText(info.getTitle().trim());
        viewHolder.details_locate.setText(info.getLocation().trim());
        viewHolder.details_price.setText(info.getPrice().trim());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onContentClick(info, viewHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commodityList != null ? commodityList.size() : 0;
    }

    public interface OnItemClickListener{
        void onContentClick(Commodity info, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener ;
    }
}
