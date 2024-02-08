package com.sp.whatnow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DoucmentsImageShowFragment extends Fragment {


    public DoucmentsImageShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doucments_image_show, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String clickedId = bundle.getString("clickedId");
            // Use the clickedId as needed
        }

        return view;
    }
}