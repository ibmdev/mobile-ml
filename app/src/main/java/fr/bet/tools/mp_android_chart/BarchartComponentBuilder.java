package fr.bet.tools.mp_android_chart;

import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import fr.bet.unibet_football_data.beans.Match;

public class BarchartComponentBuilder {

    // BarChart affichant le nombre de buts inscrits par une équipe
    public static void createBarChartGoalsFor(final BarChart chartC, final List<Match> response, final String teamName) {
        if(response != null && !response.isEmpty()) {
        chartC.getDescription().setEnabled(false);
        chartC.setMaxVisibleValueCount(60);
        chartC.setPinchZoom(false);
        chartC.setDrawBarShadow(false);
        chartC.setDrawGridBackground(false);
        XAxis xAxis = chartC.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        chartC.getAxisLeft().setDrawGridLines(false);
        chartC.animateY(1500);
        chartC.getLegend().setEnabled(true);
        chartC.setMinimumHeight(500);
        ArrayList<BarEntry> values = new ArrayList<>();
        // Legend
        Legend l = chartC.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // Fill data
        response.stream().forEach(m -> {
            if(m.getHomeTeam().equals(teamName)) {
                values.add(new BarEntry(values.size(), m.getFTHG()));
            }
            else if(m.getAwayTeam().equals(teamName)) {
                values.add(new BarEntry(values.size(), m.getFTAG()));
            }
        });
        BarDataSet dataSet = new BarDataSet(values,"Goals for " + teamName);
        BarData data = new BarData(dataSet);
        chartC.setData(data);
        chartC.setVisibility(View.VISIBLE);
        }
        else {
            cleanBarChart(chartC);
        }
    }

    // clean BarChart
    public static void cleanBarChart(BarChart barChart) {
        barChart.clear();
        barChart.setVisibility(View.GONE);
    }
}
