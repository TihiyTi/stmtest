package com.ti;

import com.ti.view.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControlApp extends Application {
    private  static  final  String SCENE_XML = "scene.fxml";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(SCENE_XML));
        BorderPane root =  loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        MagnitController controller = new MagnitController();
        ViewController viewController = loader.getController();
        viewController.setExternalController(controller);


    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

}
