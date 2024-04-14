package com.example.pineapple.ticketadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.ticketbeans.MallInfo;

import java.util.List;

public class MallAdapter extends RecyclerView.Adapter<MallAdapter.ViewHolder> {

    private List<MallInfo> MallInfoList;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public MallAdapter(Context context, List<MallInfo> mallInfoList){
        this.context = context;
        this.MallInfoList = mallInfoList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mall_title, mall_price, mall_location, mall_description;
        ImageView iv_image;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mall_title=itemView.findViewById(R.id.mall_title);
            this.mall_price=itemView.findViewById(R.id.mall_price);
            this.mall_location=itemView.findViewById(R.id.mall_location);
            this.mall_description=itemView.findViewById(R.id.mall_description);
            this.iv_image = itemView.findViewById(R.id.iv_image);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mall, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final MallInfo info = MallInfoList.get(position);
            holder.mall_title.setText((CharSequence) info.getTitle());
            holder.mall_price.setText(info.getPrice());
            holder.mall_location.setText(info.getLocation());
            holder.mall_description.setText(info.getDescription());
            Glide.with(context).load(info.getImage()).into(holder.iv_image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(info,holder.getAdapterPosition());
                    }

                }



            });
    }

    @Override
    public int getItemCount() {
        return MallInfoList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(MallInfo mallInfo, int position);


    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



}
