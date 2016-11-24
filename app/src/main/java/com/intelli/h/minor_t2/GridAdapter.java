package com.intelli.h.minor_t2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intelli.h.minor_t2.WifiTether.HttpRequestAsyncTask;

import java.util.ArrayList;
import java.util.List;


import static android.R.color.black;

public class GridAdapter extends BaseAdapter {

    private List<String> bulbList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public GridAdapter(Context context) {

        bulbList.add("Bulb 1");
        bulbList.add("Bulb 2");
        bulbList.add("Bulb 3");
        this.context = context;
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return bulbList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = inflater.inflate(R.layout.control_panel_item, parent, false);
        final ImageButton imageButton = (ImageButton) view.findViewById(R.id.bulb_image_button);
        LinearLayout switcher = (LinearLayout) view.findViewById(R.id.switcher);
        TextView textView = (TextView) view.findViewById(R.id.bulbName);
        textView.setText(bulbList.get(position));
        imageButton.setBackgroundColor(view.getResources().getColor(black));
        imageButton.setImageResource(R.drawable.ic_lightbulb);
        imageButton.setOnClickListener(new View.OnClickListener() {
            boolean flag = true;

            @Override
            public void onClick(View v) {
                flag = !flag;
                if (!flag) {
                    new HttpRequestAsyncTask(
                            view.getContext(), "13", "192.168.43.208", "80", "pin"
                    ).execute();
                    imageButton.setBackgroundColor(v.getResources().getColor(R.color.colorAccent));
                    imageButton.setImageResource(R.drawable.ic_lightbulb_lit);
                } else {
                    new HttpRequestAsyncTask(
                            view.getContext(), "13", "192.168.43.208", "80", "pin"
                    ).execute();
                    imageButton.setBackgroundColor(v.getResources().getColor(black));
                    imageButton.setImageResource(R.drawable.ic_lightbulb);
                }

            }
        });
        return view;
    }
}