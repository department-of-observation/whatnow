package com.sp.whatnow;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
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
    private Long totalUsageTime;
    private Long totalNonUsageTime;

    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        if (context != null) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            long currentTime = System.currentTimeMillis();
            long startTime = currentTime - 24 * 60 * 60 * 1000; // 24 hours ago

            List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY, startTime, currentTime);

             totalUsageTime = 0L;

            for (UsageStats usageStats : usageStatsList) {
                totalUsageTime += usageStats.getTotalTimeInForeground();
            }
            totalUsageTime = totalUsageTime / (1000 * 60);
            totalNonUsageTime = 1440-totalUsageTime;
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PieChart pieChart = view.findViewById(R.id.pieChart);
        PieChart pieChart2 = view.findViewById(R.id.pieChart2);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalNonUsageTime, "Sleep Time"));
        entries.add(new PieEntry(totalUsageTime, "Screen Time"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.argb(128, 0, 0, 255));   // Softened Blue for Category 1
        colors.add(Color.argb(128, 0, 255, 0));   // Softened Green for Category 2


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