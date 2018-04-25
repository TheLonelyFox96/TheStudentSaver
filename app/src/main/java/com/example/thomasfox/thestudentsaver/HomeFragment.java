package com.example.thomasfox.thestudentsaver;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasfox on 12/11/2017.
 */

public class HomeFragment extends Fragment {
    View myview;


    private RecyclerView recyclerViewNewDiscount;
    private List<Discounts> listNewDiscounts;
    private HomeDiscountRecycler HomeDiscountRecycler;
    private DatabaseHelper DatabaseHelper;
    Button moredetails_button;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Home");
        myview = inflater.inflate(R.layout.activity_home_lists,container,false);

        initViews();
        initObjects();



        return myview;
    }



    /**
     * This method is to initialize views
     */
    private void initViews() {

        recyclerViewNewDiscount = myview.findViewById(R.id.recyclerViewNewDiscount);

    }



    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listNewDiscounts = new ArrayList<Discounts>();
        HomeDiscountRecycler = new HomeDiscountRecycler(listNewDiscounts, getContext());
        RecyclerView.LayoutManager newLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewNewDiscount.setLayoutManager(newLayoutManager);
        recyclerViewNewDiscount.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNewDiscount.setHasFixedSize(true);
        recyclerViewNewDiscount.setAdapter(HomeDiscountRecycler);
        DatabaseHelper = new DatabaseHelper(getContext());

        getDataFromSQLite();


    }

    //Fetch  from database.
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        listNewDiscounts.clear();
        listNewDiscounts.addAll(DatabaseHelper.getNewDiscounts());


    }



    }

