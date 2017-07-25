package com.ti;

import javafx.animation.AnimationTimer;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RealTimeChart{
    private int maxDataPoint = 200;
    private ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> dataOfMax = new ConcurrentLinkedQueue<Number>();
    private LineChartWithMarkers<Number, Number> sc;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart.Series series;
    private int xSeriesData = 0;

    public RealTimeChart() {
        xAxis = new NumberAxis(0, maxDataPoint, maxDataPoint/10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
//        xAxis.setScaleX(1/4);
        yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        series = new AreaChart.Series<Number,Number>();
        series.setName("Area Chart Series");

        sc = new LineChartWithMarkers<>(xAxis, yAxis);

        sc.setCreateSymbols(false);
        sc.setAnimated(false);
        sc.getData().add(series);
        prepareTimeline();


        Line line = new Line();
        Polyline polyline = new Polyline();
    }

    private void prepareTimeline() {
        new AnimationTimer(){
            @Override
            public void handle(long now){
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataQ.isEmpty()) break;
            xSeriesData = xSeriesData+1;
            if((xSeriesData%10) == 0){
                series.getData().add(new LineChart.Data(xSeriesData/10, dataQ.remove()));
            }else{
                dataQ.remove();
            }
            while(dataOfMax.size()>0){
                int maximum = (Integer)dataOfMax.poll();
                System.out.println("Add maximum " + maximum);
                sc.addVerticalValueMarker(new XYChart.Data<>(maximum/10, 0 ));
            }
        }

        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series.getData().size() > maxDataPoint) {
            series.getData().remove(0, series.getData().size() - maxDataPoint);
        }
        // update
        xAxis.setLowerBound((xSeriesData/10-maxDataPoint));
        xAxis.setUpperBound((xSeriesData)/10-1);
    }

    public void setQueue(ConcurrentLinkedQueue queue){
        this.dataQ = queue;
    }
    public void setMaxQueue(ConcurrentLinkedQueue queue){
        this.dataOfMax = queue;
    }

    public void setMaxDataPoint(int maxDataPoint){
        this.maxDataPoint = maxDataPoint;
    }

    public LineChart getChart(){
        return sc;
    }
}
