package com.example.thomasfox.thestudentsaver;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thomasfox on 12/11/2017.
 */

public class HelpFragment extends Fragment{
    View myview;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Help");
        myview = inflater.inflate(R.layout.activity_help,container,false);
        return myview;
    }



}
