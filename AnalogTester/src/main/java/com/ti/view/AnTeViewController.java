package com.ti.view;

import com.ti.comm.AnTeControllable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AnTeViewController implements Initializable {
    public VBox chartHBox;
    public BorderPane border;
    public TextField portname;
    public BorderPane chartmc1;
    RealTimeChart realTimeChart = new RealTimeChart();
    RealTimeChart realTimeChart2 = new RealTimeChart();

    AnTeControllable controllable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chartHBox.getChildren().addAll(realTimeChart.getChart(),realTimeChart2.getChart());
        realTimeChart.getChart().prefHeightProperty().bind(border.heightProperty().divide(2));
        realTimeChart2.getChart().prefHeightProperty().bind(border.heightProperty().divide(2));
//        borderPane.prefHeightProperty().bind(scene.heightProperty().divide(3));
    }

    public void setDataQueueOne(ConcurrentLinkedQueue queue){
        realTimeChart.setQueue(queue);
    }
    public void setDataQueueTwo(ConcurrentLinkedQueue queue){
        realTimeChart2.setQueue(queue);
    }
    public void setControllable(AnTeControllable anTeControllable){
        controllable = anTeControllable;
    }

    @FXML
    public void changeState() {
        controllable.changeEmulateState();
    }

    public void reopen() {
        controllable.reopenPort(portname.getText(),0);
    }
}
