package com.ti.control;

import com.ti.SerialSettable;
import com.ti.TildaInterface;
import com.ti.view.RealTimeChart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class AnTeViewController implements Initializable {
    @FXML
    private BorderPane controlPanel;
    @FXML
    private ViewController controlPanelController;

    private AnalogSignalManager manager = new AnalogSignalManager();

    private SerialSettable serialSetter;
    @FXML
    private VBox chartHBox;
    @FXML
    private BorderPane border;
    @FXML
    private TextField portname;

    private RealTimeChart realTimeChart = new RealTimeChart();
    private RealTimeChart realTimeChart2 = new RealTimeChart();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        realTimeChart.setQueue(manager.getReoQueue());
        realTimeChart2.setQueue(manager.getMioQueue());
        chartHBox.getChildren().addAll(realTimeChart.getChart(),realTimeChart2.getChart());
        realTimeChart.getChart().prefHeightProperty().bind(border.heightProperty().divide(2));
        realTimeChart2.getChart().prefHeightProperty().bind(border.heightProperty().divide(2));
    }

    public void setSerialSetter(SerialSettable serialSetter) {
        this.serialSetter = serialSetter;
    }
    public List<TildaInterface> getTildaInterfaces(){
        return Arrays.asList(controlPanelController,manager);
    }
    public ViewController getControlPanelController(){
        return controlPanelController;
    }

    @FXML
    public void changeState() {

    }

    public Stream<TildaInterface> getTildaControllers(){
        return Stream.of(controlPanelController,manager);
    }

    public void reopen() {
        serialSetter.reopenPort(portname.getText(), 0);
    }
}
