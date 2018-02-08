package com.example.thomasfox.thestudentsaver;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasfox on 12/11/2017.
 */



public class DiscountFragment extends Fragment{




    private RecyclerView recyclerViewDiscount;
    private List<Discounts> listDiscounts;
    private DiscountRecycleAdapter DiscountRecyclerAdapter;
    private DatabaseHelper DatabaseHelper;

    View myview;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Discounts");


        myview = inflater.inflate(R.layout.activity_discount_list, container, false);



        initViews();
        initObjects();
        return myview;
    }





    /**
     * This method is to initialize views
     */
    private void initViews() {

        recyclerViewDiscount = (RecyclerView) myview.findViewById(R.id.recyclerViewDiscount);
    }



    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listDiscounts = new ArrayList<Discounts>();
        DiscountRecyclerAdapter = new DiscountRecycleAdapter(listDiscounts, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewDiscount.setLayoutManager(mLayoutManager);
        recyclerViewDiscount.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDiscount.setHasFixedSize(true);
        recyclerViewDiscount.setAdapter(DiscountRecyclerAdapter);
        DatabaseHelper = new DatabaseHelper(getContext());




        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */

    // This is an issue
    // Having trouble getting data from non-static context
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        listDiscounts.clear();
             listDiscounts.addAll(DatabaseHelper.getAllDiscounts());

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                listDiscounts.clear();
//                listDiscounts.addAll(DatabaseHelper.getAllDiscounts());
//
//               return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                DiscountRecyclerAdapter.notifyDataSetChanged();
//            }
//        }.execute();
    }


}


