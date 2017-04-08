package com.ti.view;

import com.ti.PropertiesService;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RealTimeChart{
    //Param from properties
    private int maxDataPoint = 1000;
    private boolean skip10 = true;

    private ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> dataOfMax = new ConcurrentLinkedQueue<Number>();
    private LineChartWithMarkers<Number, Number> sc;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart.Series series;
    private int xSeriesData = 0;

    public RealTimeChart() {
        maxDataPoint = Integer.valueOf(PropertiesService.getGlobalProperty("pointOnView"));
        skip10 = Boolean.valueOf(PropertiesService.getGlobalProperty("skip10"));

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
                if(skip10){
                    addDataToSeries();
                }else {
                    addDataToSeriesAll();
                }
            }
        }.start();
    }

    private void addDataToSeries() {
//        System.out.println("Data size " + dataQ.size() + new Date().toString());

//        System.out.print(" s:"+dataQ.size()+" ");
        for (int i = 0; i < dataQ.size()/10-1; i++) { //-- add 20 numbers to the plot+
            if (dataQ.isEmpty()) break;
            xSeriesData = xSeriesData+1;
            if((xSeriesData%10) == 0){
                series.getData().add(new LineChart.Data(xSeriesData/10, dataQ.remove()));
            }else{
                dataQ.remove();
            }
            while(dataOfMax.size()>0){
                int maximum = (Integer)dataOfMax.poll();
//                System.out.println("Add maximum " + maximum);
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
    private void addDataToSeriesAll() {
//        System.out.println("Data size " + dataQ.size() + new Date().toString());

//        System.out.print(" s:"+dataQ.size()+" ");
        for (int i = 0; i < dataQ.size(); i++) { //-- add 20 numbers to the plot+
//            if (dataQ.isEmpty()) break;
            xSeriesData = xSeriesData+1;
//            if((xSeriesData%10) == 0){
                series.getData().add(new LineChart.Data(xSeriesData, dataQ.remove()));
//            }else{
//                dataQ.remove();
//            }
//            while(dataOfMax.size()>0){
//                int maximum = (Integer)dataOfMax.poll();
//                System.out.println("Add maximum " + maximum);
//                sc.addVerticalValueMarker(new XYChart.Data<>(maximum/10, 0 ));
//            }
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series.getData().size() > maxDataPoint) {
            series.getData().remove(0, series.getData().size() - maxDataPoint);
        }
        // update
        xAxis.setLowerBound((xSeriesData - maxDataPoint));
        xAxis.setUpperBound((xSeriesData));
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
