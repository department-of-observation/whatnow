package com.sp.whatnow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

public class DocumentsFragment extends Fragment {
    private Button uploadbutton ;




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

        TableRow school = view.findViewById(R.id.table_school);
        TableRow courses = view.findViewById(R.id.table_courses);
        TableRow national_award = view.findViewById(R.id.table_national_award);
        TableRow international_award = view.findViewById(R.id.table_international_award);
        TableRow others = view.findViewById(R.id.table_others);
        uploadbutton = view.findViewById(R.id.upload_button);

        school.setTag("school");
        courses.setTag("courses");
        national_award.setTag("national");
        international_award.setTag("international");
        others.setTag("others");

        // Set a click listener for the uploadbutton
        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentUploadFragment image = new DocumentUploadFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homeFragmentContainer, image)
                        .addToBackStack(null) // Add to the back stack for back navigation
                        .commit();
            }
        });
        View.OnClickListener tableClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
            }
        };

        school.setOnClickListener(tableClickListener);
        courses.setOnClickListener(tableClickListener);
        national_award.setOnClickListener(tableClickListener);
        international_award.setOnClickListener(tableClickListener);
        others.setOnClickListener(tableClickListener);

        return view;
    }
    public void handleClick(View view) {
        String clickedTag = (String) view.getTag();
        DoucmentsImageShowFragment image = new DoucmentsImageShowFragment();
        Log.d("baka", "clickedId"+clickedTag);

        Bundle bundle = new Bundle();
        bundle.putString("clickedId", clickedTag);
        image.setArguments(bundle);

        // Replace the current fragment with the new one
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFragmentContainer, image)
                .addToBackStack(null) // Add to the back stack for back navigation
                .commit();
    }


}