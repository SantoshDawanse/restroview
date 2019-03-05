package com.dawn.restroview.adminfrags;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dawn.restroview.R;
import com.dawn.restroview.model.TapDatabase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFrag extends Fragment {

    private TextView dateToday;
    private PieChart foodPieChart, servicePieChart;
    private BarChart barChart;

    private TapDatabase tapDatabase;


    public TodayFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        tapDatabase = new TapDatabase(getContext());

        dateToday = view.findViewById(R.id.todayfrag_date);
        dateToday.setText(getTodayDate());

        foodPieChart = view.findViewById(R.id.todayfrag_piechart_food);
        servicePieChart = view.findViewById(R.id.todayfrag_piechart_service);

        barChart = view.findViewById(R.id.todayfrag_barchart);

        //food data in pie chart
        setFoodPieData();
        //service data in pie chart
        setServicePieData();
        return view;
    }

    private String getTodayDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void setFoodPieData() {

        tapDatabase.openDB();
        float foodLove = tapDatabase.getFoodLoveData();
        float foodSad = tapDatabase.getFoodSadData();
        tapDatabase.closeDB();

        float totalFoodInteractions = foodLove + foodSad;

        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(foodLove, "love"));
        pieEntries.add(new PieEntry(foodSad, "sad"));

        PieDataSet dataSet = new PieDataSet(pieEntries, "Food Quality Results");

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(ColorTemplate.getHoloBlue());
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        foodPieChart.setData(data);
        foodPieChart.invalidate();
    }

    private void setServicePieData() {

        tapDatabase.openDB();
        float serviceLove = tapDatabase.getServiceLoveData();
        float serviceSad = tapDatabase.getServiceSadData();
        tapDatabase.closeDB();

        float totalFoodInteractions = serviceLove + serviceSad;

        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(serviceLove, "love"));
        pieEntries.add(new PieEntry(serviceSad, "sad"));

        PieDataSet dataSet = new PieDataSet(pieEntries, "Service Quality Results");

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(ColorTemplate.getHoloBlue());
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        servicePieChart.setData(data);
        servicePieChart.invalidate();
    }

}
