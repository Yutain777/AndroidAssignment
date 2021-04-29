package com.example.mymoviememoir.ui.report;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ProcessLifecycleOwner;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.daterelated.DateRelated;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.example.mymoviememoir.sharedpreferences.SharedPreferencesFile;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Report_Fragment extends Fragment {
    private String starDate;
    private String endDate;
    private String selectedYear;
    private DatePicker pickeStar;
    private DatePicker pickEnd;
    private PieChart pieChart;
    private BarChart barChart;
    private ArrayList<String> cinemaList;
    private ArrayList<Integer> numberList;
    private ArrayList<Integer> monthList;
    private ArrayList<Integer> numberList2;
    private Spinner spinner;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report_, container, false);
        pickeStar = root.findViewById(R.id.startdatePicker);
        pickEnd = root.findViewById(R.id.enddatePicker);
        pieChart = root.findViewById(R.id.chart);
        barChart = root.findViewById(R.id.barChart);
        spinner = root.findViewById(R.id.spinnerYear);

        SharedPreferencesFile sp = SharedPreferencesFile.getInstance(getActivity());
        Long pid = sp.getLong("personId", (long) 0);
        final String personId = Long.toString(pid);
        //simple pie chart
        Button btnPick = root.findViewById(R.id.btnSelect);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinemaList = new ArrayList<>();
                numberList = new ArrayList<>();
                int year = pickeStar.getYear();
                int mon = pickeStar.getMonth() + 1;
                int day = pickeStar.getDayOfMonth();
                starDate = Integer.toString(year) +"-"+ Integer.toString(mon)+ "-"+ Integer.toString(day);
                year = pickEnd.getYear();
                mon = pickEnd.getMonth() + 1;
                day = pickEnd.getDayOfMonth();
                endDate = Integer.toString(year) +"-"+ Integer.toString(mon)+ "-"+ Integer.toString(day);
                GetPie getPieChart = new GetPie();
                getPieChart.execute(personId,starDate,endDate);
            }
        });
        //bar
        Button button = root.findViewById(R.id.btnBar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthList = new ArrayList<>();
                numberList2 = new ArrayList<>();
                String year = spinner.getSelectedItem().toString();
                GetBar getBar = new GetBar();
                getBar.execute(personId,year);
            }
        });
        return root;
    }




     private class GetPie extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.getPieChart(params[0], (params[1]),(params[2]));
        }

        protected void onPostExecute(String param) {

            try {
                JSONArray jsonArray = new JSONArray(param);
                int len = jsonArray.length();
                for(int index = 0 ; index<len; index++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    int i = jsonObject.getInt("totalNumber");
                    String code = jsonObject.getString("cinemaPostcode");
                    numberList.add(i);
                    cinemaList.add(code);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
           drawPie();
        }
    }


    private class GetBar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            NetworkConnection networkConnection = new NetworkConnection();
            return networkConnection.getBarChart(params[0],params[1]);
        }
        protected void onPostExecute(String param) {

            try {
                JSONArray jsonArray = new JSONArray(param);
                int len = jsonArray.length();
                for(int index = 0 ; index<len; index++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    int month  = jsonObject.getInt("Month");
                    int number = jsonObject.getInt("totalNumber");
                    numberList2.add(number);
                    monthList.add(month);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            drawbar();

        }
    }


    private void drawPie(){

    int sum=0;
    for(int i:numberList){
        sum = sum +i;
    }
    List<PieEntry> strings = new ArrayList<>();
    for(int i=0; i<numberList.size();i++) {
       int value = numberList.get(i);
       value = value *100 /sum;
        strings.add(new PieEntry(value, cinemaList.get(i)));
    }

    PieDataSet dataSet = new PieDataSet(strings, "Label");
    ArrayList<Integer> colors = new ArrayList<Integer>();
    colors.add(getResources().getColor(R.color.colorAccent));
    colors.add(getResources().getColor(R.color.colorPrimaryDark));
    colors.add(getResources().getColor(R.color.design_default_color_error));
    dataSet.setColors(colors);

    PieData pieData = new PieData(dataSet);
    pieData.setDrawValues(true);
    pieData.setValueFormatter(new PercentFormatter());
    pieData.setValueTextSize(12f);

    pieChart.setData(pieData);
    pieChart.invalidate();

    Description description = new Description();
    description.setText("total number of movies watched per postcode for the selected period");
    pieChart.setDescription(description);
    pieChart.setHoleRadius(0f);
    pieChart.setTransparentCircleRadius(0f);
}



    private void drawbar(){
        BarChartManager barChartManager = new BarChartManager(barChart);
        //xxdata
        ArrayList<Float> x = new ArrayList<>();
        for(int i =0; i<monthList.size();i++)
        {
            x.add((float)(monthList.get(i)));
        }
        //y data
        ArrayList<Float> y = new ArrayList<>();
        for(int i =0; i< numberList2.size();i++)
        {
            y.add((float)(numberList2.get(i)));
        }
        barChartManager.showBarChart(x,y,"Total number in this year",Color.BLUE);


    }

    private class BarChartManager {
        private BarChart mBarChart;
        private YAxis leftAxis;
        private YAxis rightAxis;
        private XAxis xAxis;
        public BarChartManager(BarChart barChart) {
            this.mBarChart = barChart;
            leftAxis = mBarChart.getAxisLeft();
            rightAxis = mBarChart.getAxisRight();
            xAxis = mBarChart.getXAxis();
        }
        //初始化
        private void initLineChart() {
            mBarChart.setBackgroundColor(Color.WHITE);
            mBarChart.setDrawGridBackground(false);
            mBarChart.setDrawBarShadow(false);
            mBarChart.setHighlightFullBarEnabled(false);
            mBarChart.setDrawBorders(true);
            Legend legend = mBarChart.getLegend();
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setTextSize(20f);
            legend.setTextColor(Color.BLUE);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextColor(Color.BLUE);
            xAxis.setTextSize(5f);
            xAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f);
            rightAxis.setAxisMinimum(0f);
            leftAxis.setAxisMaximum(30f);
            rightAxis.setAxisMaximum(30f);


        }
        private void showBarChart(List xAxisValues, List yAxisValues, String label, int color) {
            initLineChart();
            List<BarEntry> barEntries1 = new ArrayList<>();
            for (int i = 0 ; i<monthList.size(); i++) {
                barEntries1.add(new BarEntry((float)(xAxisValues.get(i)), (float) yAxisValues.get(i)));
            }
            BarDataSet barDataSet = new BarDataSet(barEntries1, label);
            barDataSet.setColor(color);
            barDataSet.setValueTextSize(9f);
            barDataSet.setFormLineWidth(1f);
            barDataSet.setFormSize(15.f);
            ArrayList dataSets = new ArrayList<>();
            dataSets.add(barDataSet);
            BarData data = new BarData(dataSets);
            xAxis.setLabelCount(barEntries1.size() - 1, false);
            mBarChart.setData(data);
        }

        }

        }































