package com.ti;

import com.ti.comm.AnTeEmulController;
import com.ti.command.AbstractCommand;
import com.ti.control.AnTeViewController;
import com.ti.logic.SyncKeeper;
import com.ti.logic.TildaLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AnalogTester extends Application{
    private static final Logger LOG = LogManager.getLogger(AnalogTester.class);
    private  static  final  String SCENE_XML = "analogTester.fxml";
//    private  static  final  String CONTROL_XML = "ControlPanel.fxml";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Start");
        PropertiesService.setGlobalPropertyFileName(AnalogTester.class.getSimpleName());
        System.out.println("Start");
//        String logLevel = PropertiesService.getGlobalProperty("logLevel", Level.INFO.toString());
        System.out.println("Start");
        // TODO: 02.06.2017 вернуть функционал выбора уровня из файла свойств
//        LogManager.getRootLogger().setLevel(Level.toLevel(logLevel));
//        LOG.info("Start application with LOG Level " + logLevel +"  at  " + new Date().toString());

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

        if(Boolean.valueOf(PropertiesService.getGlobalProperty("isEmulate"))){
            Executors.newSingleThreadScheduledExecutor().
                    scheduleWithFixedDelay(emulController, 0L, 10L, TimeUnit.MILLISECONDS);
        }
    }
    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }
}
