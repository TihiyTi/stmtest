package com.ti;

import com.ti.comm.MainAnTeController;
import com.ti.view.AnTeViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AnalogTester extends Application{
    private  static  final  String SCENE_XML = "analogTester.fxml";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(SCENE_XML));
        BorderPane root =  loader.load();
        Scene scene = new Scene(root,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();


        AnTeViewController viewController = loader.getController();
        MainAnTeController mainController = new MainAnTeController();
        mainController.setViewController(viewController);
        viewController.setControllable(mainController);
        mainController.runEmulation();

    }
    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }

}
