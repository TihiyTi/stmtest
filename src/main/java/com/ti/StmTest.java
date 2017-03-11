package com.ti;

import com.ti.comm.SignalWorker;
import com.ti.data.TildaProtocol;
import com.ti.view.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class StmTest extends Application{
    public static final Logger LOG = Logger.getLogger(StmTest.class);
    private static final String SCENE_XML = "scene.fxml";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Start application");
        init(primaryStage);
        primaryStage.show();
    }

    private void init(Stage primaryStage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        RealTimeChart realTimeChart = new RealTimeChart();
        RealTimeChart realTimeChart2 = new RealTimeChart();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(SCENE_XML));
        BorderPane borderPane =  loader.load();

        MagnitController controller = new MagnitController();
        ViewController viewController = loader.getController();
        viewController.setExternalController(controller);


        SignalWorker worker = new SignalWorker();
        worker.setOutput();
        worker.setSource(SignalWorker.SOURCE_FILE);
        realTimeChart.setQueue(worker.getSourceQueue());
        realTimeChart.setMaxQueue(worker.getMaxQueue());
        realTimeChart2.setQueue(worker.getOutputQueue());

        TildaProtocol protocol = new TildaProtocol();
        worker.addProtocol(protocol);

        protocol.addController(controller);
        protocol.addController(worker);

        LineChart chart = realTimeChart.getChart();
        LineChart chart2 = realTimeChart2.getChart();
        chart.prefHeightProperty().bind(scene.heightProperty().divide(3));
        chart2.prefHeightProperty().bind(scene.heightProperty().divide(3));
        borderPane.prefHeightProperty().bind(scene.heightProperty().divide(3));
        chart.prefWidthProperty().bind(scene.widthProperty());
        chart.prefWidthProperty().bind(scene.widthProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        root.getChildren().addAll(new VBox(chart,chart2, borderPane));
    }

    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }

}
