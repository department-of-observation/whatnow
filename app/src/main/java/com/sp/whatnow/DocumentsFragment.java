package com.sp.whatnow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.Toast;

public class DocumentsFragment extends Fragment {


    public DocumentsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Documents");

        TableRow tableschool = view.findViewById(R.id.table_school);
        TableRow tablecourses = view.findViewById(R.id.table_courses);
        TableRow tablenational_award = view.findViewById(R.id.table_national_award);
        TableRow tableinternational_award = view.findViewById(R.id.table_international_award);
        TableRow tableothers = view.findViewById(R.id.table_others);
        View.OnClickListener tableClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
            }
        };

        tableschool.setOnClickListener(tableClickListener);
        tablecourses.setOnClickListener(tableClickListener);
        tablenational_award.setOnClickListener(tableClickListener);
        tableinternational_award.setOnClickListener(tableClickListener);
        tableothers.setOnClickListener(tableClickListener);

        return view;
    }
    public void handleClick(View view) {
        int clickedId = view.getId();
        String clickedIdAsString = String.valueOf(clickedId);
        DoucmentsImageShowFragment image = new DoucmentsImageShowFragment();

        Bundle bundle = new Bundle();
        bundle.putString("clickedId", clickedIdAsString);
        image.setArguments(bundle);

        // Replace the current fragment with the new one
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFragmentContainer, image)
                .addToBackStack(null) // Add to the back stack for back navigation
                .commit();
    }


}