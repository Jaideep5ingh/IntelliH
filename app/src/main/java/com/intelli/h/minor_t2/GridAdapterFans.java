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

public class GridAdapterFans extends BaseAdapter {

    private List<String> bulbList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public GridAdapterFans(Context context) {

        bulbList.add("Fan 1");
        bulbList.add("Fan 2");
        bulbList.add("Fan 3");
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
                            view.getContext(), "12", "192.168.1.11", "80", "pin"
                    ).execute();
                    imageButton.setBackgroundColor(v.getResources().getColor(R.color.colorAccent));
                    imageButton.setImageResource(R.drawable.ic_lightbulb_lit);
                } else {
                    new HttpRequestAsyncTask(
                            view.getContext(), "12", "192.168.1.11", "80", "pin"
                    ).execute();
                    imageButton.setBackgroundColor(v.getResources().getColor(black));
                    imageButton.setImageResource(R.drawable.ic_lightbulb);
                }

            }
        });
        return view;
    }
}