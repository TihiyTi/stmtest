package com.ti.control;

import com.sun.istack.internal.NotNull;
import com.ti.TildaInterface;
import com.ti.command.param.Amplitude;
import com.ti.command.param.Form;
import com.ti.command.param.Frequency;
import com.ti.command.param.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable, TildaInterface {
    @NotNull private TildaInterface childController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void freq08action() {
        childController.setFrequency(Frequency.HZ_0_8);
    }
    public void freq10action() {childController.setFrequency(Frequency.HZ_1);}
    public void freq12action() {childController.setFrequency(Frequency.HZ_1);}
    public void freq25action() {childController.setFrequency(Frequency.HZ_1);}
    public void freq120action() {childController.setFrequency(Frequency.HZ_1);}

    public void ampl25() {childController.setAmplitude(Amplitude.AMPLITUDE_20);}
    public void ampl50(){childController.setAmplitude(Amplitude.AMPLITUDE_20);}
    public void ampl75(){childController.setAmplitude(Amplitude.AMPLITUDE_20);}
    public void ampl100(){childController.setAmplitude(Amplitude.AMPLITUDE_20);}

    public void form1(){childController.setForm(Form.FORM_1);}
    public void form2(){childController.setForm(Form.FORM_1);}
    public void form3(){childController.setForm(Form.FORM_1);}
    public void form4(){childController.setForm(Form.FORM_1);}

    public void min10(){System.out.println("Unsupported yet");}
    public void min20(){System.out.println("Unsupported yet");}
    public void min30(){System.out.println("Unsupported yet");}
    public void min40(){System.out.println("Unsupported yet");}

    public void run() { childController.setState(State.ENABLE);}
    public void stop(){ childController.setState(State.DISABLE);}

    @Override
    public void setMainController(TildaInterface controller) {
        childController = controller;
    }

    @Override
    public void setFrequency(Frequency frequency) {

    }

    @Override
    public void setForm(Form form) {

    }

    @Override
    public void setAmplitude(Amplitude amplitude) {

    }
    @Override
    public void setState(State state) {}


    //Unsupported method for this controller
    @Override
    public void syncOk() {}
    @Override
    public void no() {}
    @Override
    public void processData(int reo, int mio) {}
    @Override
    public void processState() {}

    @Override

    public void waitSync() {

    }
}
