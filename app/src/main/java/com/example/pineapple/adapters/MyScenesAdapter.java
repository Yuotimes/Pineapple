package com.example.pineapple.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.beans.MyScenes;

import java.util.List;

public class MyScenesAdapter extends BaseAdapter {
    private Context context;
    private List<MyScenes> myScenesList;

    public MyScenesAdapter(Context context, List<MyScenes> myScenesList){
        this.context = context;
        this.myScenesList = myScenesList;
    }
    @Override
    public int getCount() {
        return myScenesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MyScenesAdapter.ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(this.context, R.layout.item_myscene, null);
            viewHolder = new MyScenesAdapter.ViewHolder();
            viewHolder.my_scenes_scene = convertView.findViewById(R.id.my_scenes_scene);
            viewHolder.my_scenes_ticket = convertView.findViewById(R.id.my_scenes_ticket);
            viewHolder.my_scenes_time = convertView.findViewById(R.id.my_scenes_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder  = (MyScenesAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.my_scenes_scene.setText(this.myScenesList.get(position).getScene().trim());
        viewHolder.my_scenes_ticket.setText(this.myScenesList.get(position).getTicket().trim());
        viewHolder.my_scenes_time.setText(this.myScenesList.get(position).getDate().trim());

        return convertView;
    }
    private static class ViewHolder{

        TextView my_scenes_scene;
        TextView my_scenes_ticket;
        TextView my_scenes_time;

    }
}
