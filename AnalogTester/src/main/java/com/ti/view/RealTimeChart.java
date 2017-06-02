package com.ti.view;

import com.ti.PropertiesService;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RealTimeChart{
    //Param from properties
    private int maxDataPoint = 1000;
    private boolean skip10 = true;

    private ConcurrentLinkedQueue<Integer> dataQ = new ConcurrentLinkedQueue<>();
    private List<Integer> tempList = new ArrayList<>();
    private ConcurrentLinkedQueue<Integer> dataOfMax = new ConcurrentLinkedQueue<>();
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
        yAxis.setForceZeroInRange(false);
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
                int addingValue = dataQ.remove();
                tempList.add(addingValue);
                series.getData().add(new LineChart.Data(xSeriesData/10, addingValue));
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

//        Integer min = Collections.min(dataQ);
//        Integer max = Collections.max(dataQ);
//        if(min!=null){
//            yAxis.setLowerBound(min);
//            yAxis.setUpperBound(max);
//        }
    }
    private void addDataToSeriesAll() {
        for (int i = 0; i < dataQ.size(); i++) { //-- add 20 numbers to the plot+
            xSeriesData = xSeriesData+1;
            int addingValue = dataQ.remove();
            tempList.add(addingValue);
            if(tempList.size() > maxDataPoint){
                tempList.remove(0);
            }
            series.getData().add(new LineChart.Data(xSeriesData, addingValue));
        }
        if (series.getData().size() > maxDataPoint) {
            series.getData().remove(0, series.getData().size() - maxDataPoint);
        }
        xAxis.setLowerBound((xSeriesData - maxDataPoint));
        xAxis.setUpperBound((xSeriesData));

//        if(!dataQ.isEmpty()){
//            Integer min = Collections.min(tempList);
//            Integer max = Collections.max(tempList);
//            int from = (int) yAxis.getLowerBound();
//            int to = (int) yAxis.getUpperBound();
//            System.out.println("from = "+from + "  to = "+ to +"   "+ min+ "   "+max);
//            if(min!=null){
//                if( min < (from + 100) && min > from - 100){
//
//                }else {
//                    yAxis.setLowerBound(min - 100);
//                }
//                if( max < (to + 100) && max > to - 100){
//
//                }else {
//                    yAxis.setUpperBound(max + 100);
//                }
//            }
//        }
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
