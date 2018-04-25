package com.example.thomasfox.thestudentsaver;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.DialogFragment;
import android.view.View.OnClickListener;
import android.widget.EditText;


/**
 * Created by thomasfox on 12/11/2017.
 */

public class HelpFragment extends Fragment {

    View myview;
    @Nullable
    Button about_button;
    Button feedback_button;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Help");
        myview = inflater.inflate(R.layout.activity_help, container, false);

        // Buttons defined for page.
        about_button = myview.findViewById(R.id.about_button);
        feedback_button = myview.findViewById(R.id.feedback_button);


        about_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                // set title
                alertDialogBuilder.setTitle("About Us");

                // set dialog message
                alertDialogBuilder
                        .setMessage(R.string.aboutUS)
                        .setCancelable(false)
                        .setPositiveButton("Close",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        feedback_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                // set title
                alertDialogBuilder.setTitle("Feedback");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Please enter feedback below")
                        .setCancelable(false);
                        final EditText feedbackInput = new EditText(getContext());
                        alertDialogBuilder.setView(feedbackInput)
                        .setPositiveButton("Submit",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


        return myview;
    }







}


