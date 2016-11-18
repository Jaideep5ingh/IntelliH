package com.intelli.h.minor_t2.TabFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelli.h.minor_t2.BT_Integration.DeviceList;
import com.intelli.h.minor_t2.ControlPanel2;
import com.intelli.h.minor_t2.R;

import java.util.ArrayList;
import java.util.List;

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

        holder.roomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Pick a Way to connect")
                        .setItems(R.array.connectionType, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    context.startActivity(new Intent(context, ControlPanel2.class));
                                    dialog.dismiss();
                                } else if (which == 1) {
                                    dialog.dismiss();
                                    context.startActivity(new Intent(context, DeviceList.class));
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return roomNames.size();
    }

    public class SexyViewHolder extends RecyclerView.ViewHolder {
        public TextView roomName;
        public ImageView imageView;
        public CardView roomLayout;

        public SexyViewHolder(View itemView) {
            super(itemView);
            roomLayout = (CardView) itemView.findViewById(R.id.roomLayout);
            roomName = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.roomImage);

        }
    }
}
