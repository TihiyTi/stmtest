package com.ti;

import com.ti.comm.AnTeEmulController;
import com.ti.command.AbstractCommand;
import com.ti.control.AnTeViewController;
import com.ti.logic.TildaLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AnalogTester extends Application{
    private  static  final  String SCENE_XML = "analogTester.fxml";
//    private  static  final  String CONTROL_XML = "ControlPanel.fxml";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(SCENE_XML));
        BorderPane root =  loader.load();
        Scene scene = new Scene(root,600,600);
        primaryStage.setScene(scene);
        primaryStage.show();
        AnTeViewController viewController = loader.getController();

        TildaController tildaController = new TildaController();
        AnTeEmulController emulController = new AnTeEmulController();
        TildaProtocol tildaProtocol = new TildaProtocol();
        SerialService<AbstractCommand,AbstractCommand> service = new SerialService<>();

        service.setProtocol(tildaProtocol);
        service.addController(tildaController);
        service.addController(emulController);

        TildaLogic logic = new TildaLogic(tildaController, viewController.getTildaInterfaces(), viewController.getControlPanelController());

//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleWithFixedDelay(emulController, 0L, 1L, TimeUnit.MILLISECONDS);

    }
    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }

}
