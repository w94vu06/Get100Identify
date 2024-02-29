package com.example.newidentify.Util;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartSetting {

    public void initchart(LineChart lineChart) {
        // 允許滑動
        lineChart.setDragEnabled(true);

        // 設定縮放
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);

        // 其他圖表設定
        lineChart.setData(new LineData());
        lineChart.getXAxis().setValueFormatter(null);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisLeft().setGranularityEnabled(false);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setAxisMinimum(1500);
        lineChart.getAxisLeft().setAxisMaximum(2500);
        lineChart.getXAxis().setAxisMinimum(0);
        lineChart.getXAxis().setAxisMaximum(300);
        lineChart.getXAxis().setGranularity(30);
        lineChart.getAxisLeft().setGranularity(250);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getDescription().setText("");
        lineChart.getLegend().setEnabled(false);

        lineChart.invalidate();
    }

    public void overlapChart(LineChart lineChart, List<Float> df1, List<Float> df2, List<Float> df3, List<Float> df4, int colorDf1,int colorDf2) {

        //list float to entry
        ArrayList<Entry> df1_ = new ArrayList<>();
        ArrayList<Entry> df2_ = new ArrayList<>();
        ArrayList<Entry> df3_ = new ArrayList<>();
        ArrayList<Entry> df4_ = new ArrayList<>();
        for (int i = 0; i < df1.size(); i++) {
            Entry entry = new Entry(i, df1.get(i));
            df1_.add(entry);
        }
        for (int i = 0; i < df2.size(); i++) {
            Entry entry = new Entry(i, df2.get(i));
            df2_.add(entry);
        }
        for (int i = 0; i < df3.size(); i++) {
            Entry entry = new Entry(i, df3.get(i));
            df3_.add(entry);
        }
        for (int i = 0; i < df4.size(); i++) {
            Entry entry = new Entry(i, df4.get(i));
            df4_.add(entry);
        }

        LineDataSet dataSet4 = createDataSet("df4", Color.parseColor("#FFD700"), df4);
        LineDataSet dataSet3 = createDataSet("df3", Color.GREEN, df3);
        LineDataSet dataSet2 = createDataSet("df2", colorDf2, df2);
        LineDataSet dataSet1 = createDataSet("df1", colorDf1, df1);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        XAxis topAxis = lineChart.getXAxis();
        topAxis.setEnabled(false);

        LineData lineData = new LineData(dataSet4, dataSet3, dataSet2, dataSet1);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private LineDataSet createDataSet(String label, int color, List<Float> values) {
        // 将 List<Float> 轉成 List<Entry>
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            entries.add(new Entry(i, values.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, label);

        dataSet.setColor(color);
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.LINEAR);

        return dataSet;
    }

    public void markRT(LineChart chart, Float[] ecg_signal_origin, List<Integer> R_index_up, List<Integer> T_index_up) {
        // 繪製ECG信號
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < ecg_signal_origin.length; i++) {
            // 將ECG信號的數據點轉換為Entry對象並添加到entries列表
            entries.add(new Entry(i, ecg_signal_origin[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "ECG Signal");
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawCircles(false); // 設置不畫圓點
        dataSet.setDrawValues(false); // 設置不繪製數值

        LineData lineData = new LineData(dataSet);

        // 標記R點
        List<Entry> rEntries = new ArrayList<>();
        for (int index : R_index_up) {
            Log.d("RRRR", "markRT: "+index);
            rEntries.add(new Entry(index, ecg_signal_origin[index])); // 確保index在ecg_signal_origin的範圍內
        }

        LineDataSet rDataSet = new LineDataSet(rEntries, "R Points");
        rDataSet.setCircleColor(Color.RED);
        rDataSet.setCircleRadius(6f);
        rDataSet.setDrawCircles(true); // 設置畫圓點
        rDataSet.setDrawValues(false);
        lineData.addDataSet(rDataSet);

        // 標記T點
        List<Entry> tEntries = new ArrayList<>();
        for (int index : T_index_up) {
            Log.d("TTTT", "markRT: "+index);
            tEntries.add(new Entry(index, ecg_signal_origin[index])); // 確保index在ecg_signal_origin的範圍內
        }

        LineDataSet tDataSet = new LineDataSet(tEntries, "T Points");
        tDataSet.setCircleColor(Color.BLUE);
        tDataSet.setCircleRadius(6f);
        tDataSet.setDrawCircles(true); // 設置畫圓點
        tDataSet.setDrawValues(false);
        tDataSet.setColor(Color.WHITE);
        lineData.addDataSet(tDataSet);

        // 將LineData對象設定給圖表並刷新
        chart.setData(lineData);
        // 去掉左側Y軸標籤
        chart.getAxisLeft().setDrawLabels(false);

        // 去掉右側Y軸標籤
        chart.getAxisRight().setDrawLabels(false);

        // 去掉圖表的描述標籤
        Description description = new Description();
        description.setText(""); // 將描述設置為空字符串
        chart.setDescription(description);

        // 隱藏圖例，如果您不希望顯示「ECG Signal」、「R Points」、「T Points」等標籤
        chart.getLegend().setEnabled(false);

        chart.invalidate();
    }

}
