package com.example.pineapple.ticketadapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pineapple.R;
import com.example.pineapple.ticketbeans.FlightInfo;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder>{

    private List<FlightInfo> flightList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public FlightAdapter(Context context, List<FlightInfo> flightList) {
        this.context = context;
        this.flightList = flightList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_flight,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FlightInfo info = flightList.get(position);
        holder.tv_name.setText(info.getName());
        holder.tv_name.setBackgroundResource(info.isSelect() ? R.drawable.bg_shape : R.drawable.bg_shape2);
        holder.tv_name.setTextColor(info.isSelect() ? Color.WHITE : Color.BLACK);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onContentClick(info, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public interface OnItemClickListener{
        void onContentClick(FlightInfo info, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener ;
    }
}
