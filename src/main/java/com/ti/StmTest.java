package com.ti;

import com.ti.data.SignalWorker;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class StmTest extends Application{
    public static final Logger LOG = Logger.getLogger(StmTest.class);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Start application");
        init(primaryStage);
        primaryStage.show();
    }

    private void init(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root,600,300);
        primaryStage.setScene(scene);

        RealTimeChart realTimeChart = new RealTimeChart();
        RealTimeChart realTimeChart2 = new RealTimeChart();

        SignalWorker worker = new SignalWorker();
        worker.setOutput();
        worker.setSource(SignalWorker.SOURCE_FILE);
        realTimeChart.setQueue(worker.getSourceQueue());
        realTimeChart.setMaxQueue(worker.getMaxQueue());
        realTimeChart2.setQueue(worker.getOutputQueue());

        LineChart chart = realTimeChart.getChart();
        LineChart chart2 = realTimeChart2.getChart();
        chart.prefHeightProperty().bind(scene.heightProperty().divide(2));
        chart2.prefHeightProperty().bind(scene.heightProperty().divide(2));
        chart.prefWidthProperty().bind(scene.widthProperty());
        chart.prefWidthProperty().bind(scene.widthProperty());
        root.getChildren().addAll(new VBox(chart,chart2));
    }

    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }

}
