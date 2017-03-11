package com.ti.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    ViewInterface viewInterface;

//    public ViewController(ViewInterface interf){
//        viewInterface = interf;
//        freq08.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("0.8 Hz");
//            }
//        });
//    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void freq08action() {viewInterface.setFrequency(ViewInterface.FREQUENCY_0_8);}
    public void freq10action() {viewInterface.setFrequency(ViewInterface.FREQUENCY_1);}
    public void freq12action() {viewInterface.setFrequency(ViewInterface.FREQUENCY_1_2);}
    public void freq25action() {viewInterface.setFrequency(ViewInterface.FREQUENCY_2_5);}
    public void freq120action() {viewInterface.setFrequency(ViewInterface.FREQUENCY_12);}

    public void ampl25() {viewInterface.setAmplitude(ViewInterface.AMPLITUDE_25);}
    public void ampl50() {viewInterface.setAmplitude(ViewInterface.AMPLITUDE_50);}
    public void ampl75() {viewInterface.setAmplitude(ViewInterface.AMPLITUDE_75);}
    public void ampl100() {viewInterface.setAmplitude(ViewInterface.AMPLITUDE_100);}

    public void form1() {viewInterface.setForm(ViewInterface.FORM1);}
    public void form2() {viewInterface.setForm(ViewInterface.FORM2);}
    public void form3() {viewInterface.setForm(ViewInterface.FORM3);}
    public void form4() {viewInterface.setForm(ViewInterface.FORM4);}

    public void min10() {viewInterface.setTimer(ViewInterface.MIN10);}
    public void min20() {viewInterface.setTimer(ViewInterface.MIN20);}
    public void min30() {viewInterface.setTimer(ViewInterface.MIN30);}
    public void min40() {viewInterface.setTimer(ViewInterface.MIN40);}

    public void setExternalController(ViewInterface externalController){
        viewInterface = externalController;
    }

    public void run(ActionEvent actionEvent) {
    }
    public void stop(ActionEvent actionEvent) {
    }
}
