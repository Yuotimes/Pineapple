package com.example.pineapple.ticketadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.ticketbeans.PlaneInfo;

import java.util.List;

public class PlaneAdapter extends RecyclerView.Adapter<PlaneAdapter.ViewHolder>{

    private List<PlaneInfo> PlaneInfoList;
    private Context context;
    private OnItemClickListener onItemClickListener;





    public PlaneAdapter(Context context, List<PlaneInfo> PlaneInfoList) {
        this.context = context;
        this.PlaneInfoList = PlaneInfoList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_time, tv_number, tv_price;
        ImageView iv_image;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_time = itemView.findViewById(R.id.tv_time);
            this.tv_number = itemView.findViewById(R.id.tv_number);
            this.tv_price = itemView.findViewById(R.id.tv_price);
            this.iv_image = itemView.findViewById(R.id.iv_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_plane,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PlaneInfo info = PlaneInfoList.get(position);
        holder.tv_title.setText(info.getTitle());
        holder.tv_time.setText(info.getTime());
        holder.tv_number.setText(info.getNumber());
        holder.tv_price.setText("￥" + info.getPrice());
        Glide.with(context).load(info.getImage()).into(holder.iv_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(info, holder.getAdapterPosition());
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return PlaneInfoList.size();
    }

    public interface OnItemClickListener{
       void onItemClick(PlaneInfo planeInfo,int position);


    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener ;
    }

}
