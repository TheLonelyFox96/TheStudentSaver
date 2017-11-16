package com.example.thomasfox.thestudentsaver;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasfox on 12/11/2017.
 */



public class DiscountFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View myview;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Discounts");


        myview = inflater.inflate(R.layout.activity_discounts,container,false);
        return myview;


    }




}


