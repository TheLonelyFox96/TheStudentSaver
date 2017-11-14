package com.example.thomasfox.thestudentsaver;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thomasfox on 12/11/2017.
 */

public class HomeFragment extends Fragment{
    View myview;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.activity_home,container,false);
        return myview;
    }
}
