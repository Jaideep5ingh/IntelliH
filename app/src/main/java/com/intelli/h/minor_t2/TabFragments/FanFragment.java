package com.intelli.h.minor_t2.TabFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intelli.h.minor_t2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FanFragment extends Fragment {
    Context context;
    RecyclerView recyclerView;

    public FanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fan, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        FanRecyclerViewAdapter mAdapter = new FanRecyclerViewAdapter(context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }

}
