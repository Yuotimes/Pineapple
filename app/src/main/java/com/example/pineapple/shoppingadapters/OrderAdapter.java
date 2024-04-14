package com.example.pineapple.shoppingadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Commodity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<Commodity> commodityList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public OrderAdapter(Context context, List<Commodity> commodityList) {
        this.context = context;
        this.commodityList = commodityList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_phone, tv_address;
        RecyclerView rv_goods;
        ImageButton btn_cancel;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_phone = itemView.findViewById(R.id.tv_phone);
            this.tv_address = itemView.findViewById(R.id.tv_address);
            this.rv_goods = itemView.findViewById(R.id.rv_goods);
            this.btn_cancel = itemView.findViewById(R.id.btn_cancel);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Commodity commodity = commodityList.get(position);
        holder.tv_phone.setText(commodity.getPhone());
        holder.tv_address.setText(commodity.getAddress());

        holder.rv_goods.setLayoutManager(new LinearLayoutManager(context));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter(context, commodity.getGoodsList());
        holder.rv_goods.setAdapter(adapter);

        adapter.setOnItemClickListener(new OrderGoodsAdapter.OnItemClickListener() {
            @Override
            public void onContentClick(Commodity info, int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onContentClick(commodity, holder.getAdapterPosition());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onContentClick(commodity, holder.getAdapterPosition());
                }
            }
        });
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onContentDelClick(commodity, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }

    public interface OnItemClickListener{
        void onContentClick(Commodity info, int position);
        void onContentDelClick(Commodity info, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener ;
    }
}
