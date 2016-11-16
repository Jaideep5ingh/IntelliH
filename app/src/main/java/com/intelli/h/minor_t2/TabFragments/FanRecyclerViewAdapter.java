package com.intelli.h.minor_t2.TabFragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelli.h.minor_t2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YASH on 16-Nov-16.
 */

public class FanRecyclerViewAdapter extends RecyclerView.Adapter<FanRecyclerViewAdapter.SexyViewHolder> {

    List<String> roomNames = new ArrayList<>();
    int[] imageAssets = new int[3];
    Context context;

    public FanRecyclerViewAdapter(Context context) {
        roomNames.add("Living Room");
        roomNames.add("Master Bed Room");
        roomNames.add("Kitchen");
        imageAssets[0] = (R.drawable.living_room);
        imageAssets[1] = (R.drawable.bedroom);
        imageAssets[2] = (R.drawable.kitchen);
        this.context = context;
    }


    @Override
    public FanRecyclerViewAdapter.SexyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fan_tab_recycler_view_item, parent, false);
        return new SexyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FanRecyclerViewAdapter.SexyViewHolder holder, int position) {
        holder.roomName.setText(roomNames.get(position));
        holder.imageView.setImageResource(imageAssets[position]);
    }

    @Override
    public int getItemCount() {
        return roomNames.size();
    }

    public class SexyViewHolder extends RecyclerView.ViewHolder {
        public TextView roomName;
        public ImageView imageView;

        public SexyViewHolder(View itemView) {
            super(itemView);
            roomName = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.roomImage);

        }
    }
}
