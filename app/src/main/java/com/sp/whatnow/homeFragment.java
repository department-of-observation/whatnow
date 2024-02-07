package com.sp.whatnow;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;


public class homeFragment extends Fragment {

    public homeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PieChart pieChart = view.findViewById(R.id.pieChart);
        PieChart pieChart2 = view.findViewById(R.id.pieChart2);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(20f, "Sleep Time"));
        entries.add(new PieEntry(30f, "Screen Time"));
        entries.add(new PieEntry(50f, "Other"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.argb(128, 0, 0, 255));   // Softened Blue for Category 1
        colors.add(Color.argb(128, 0, 255, 0));   // Softened Green for Category 2
        colors.add(Color.argb(128, 255, 0, 0));   // Softened Red for Category 3

        PieDataSet dataSet = new PieDataSet(entries, "My Pie Chart");

        dataSet.setColors(colors);  // Customisation
        dataSet.setDrawValues(false);  // Do not draw the values (labels)
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelTextSize(20f);
        pieChart.getLegend().setEnabled(false);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart2.setData(data);

        // Customize the appearance if needed
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setCenterText("Your time allocation");
        pieChart.setCenterTextSize(15f);

        // Refresh the chart
        pieChart.invalidate();
    }
}