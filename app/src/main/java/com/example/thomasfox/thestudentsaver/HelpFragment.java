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



/**
 * Created by thomasfox on 12/11/2017.
 */

public class HelpFragment extends Fragment {

    View myview;
    @Nullable
    Button about_button;
    Button feedback_button;
    FragmentManager feedback = getActivity().getFragmentManager();


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Help");
        myview = inflater.inflate(R.layout.activity_help, container, false);

        // Buttons defined for page.
        about_button = (Button) myview.findViewById(R.id.about_button);
        feedback_button = (Button) myview.findViewById(R.id.feedback_button);

        AboutUsDialog();
        FeedbackDialog();

        return myview;

    }

    private void AboutUsDialog() {
        // Activate dialog for About Us button when clicked.
        about_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                FragmentManager about = getActivity().getFragmentManager();
                AboutUsFragment AboutUsFragment = new AboutUsFragment();
                AboutUsFragment.show(AboutUsFragment, "About Us Dialog");
            }
        });
    }


    private void FeedbackDialog() {
        // Activate dialog for Feedback button when clicked.
        feedback_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                FeedbackFragment feedbackFragment = new FeedbackFragment();
                // Show Alert DialogFragment
                feedbackFragment.show(feedback, "Feedback Fragment");
            }
        });
    }



}


