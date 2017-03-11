package com.ti;

import com.ti.data.TildaReceiveInterface;
import com.ti.data.TildaTranciveInterface;
import com.ti.view.ViewInterface;

public class MagnitController implements ViewInterface, TildaReceiveInterface {
    private TildaTranciveInterface tildaTranciveController;

    public void setTildaTranciveController(TildaTranciveInterface tildaTranciveController) {
        this.tildaTranciveController = tildaTranciveController;
    }

    @Override
    public void setFrequency(byte freq) {
        System.out.println("Change frequency " + freq);
    }

    @Override
    public void setForm(byte form) {
        System.out.println("Change form " + form);
    }

    @Override
    public void setAmplitude(byte ampl) {
        System.out.println("Change amplitude " + ampl);
    }

    @Override
    public void setTimer(byte min) {
        System.out.println("Change time to " + min);
    }




//    public void setWorker(SignalWorker worker) {
//        this.worker = worker;
//    }
}
