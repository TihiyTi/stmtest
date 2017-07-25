package com.ti.logic;

import com.ti.TildaInterface;
import com.ti.command.param.Amplitude;
import com.ti.command.param.Form;
import com.ti.command.param.Frequency;
import com.ti.command.param.State;
import com.ti.control.AnalogSignalManager;
import com.ti.control.ViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TildaLogic implements TildaInterface {
    private TildaInterface service;
    private List<TildaInterface> consumers = new ArrayList<>();
    private Tilda tilda;

    private ViewController viewController;

    private boolean syncRun = false;

    public TildaLogic(TildaInterface serviceInterface, List<TildaInterface> consumersInterfaces, ViewController controller) {
        service = serviceInterface;
        service.setMainController(this);

        consumers = consumersInterfaces;
        consumers.forEach(x->x.setMainController(this));

        viewController = controller;

        tilda = new Tilda();
        viewController.setSyncProperty(tilda.sync);
    }

    private void connectWithTilda(){
        if(!syncRun){
            Executors.newSingleThreadScheduledExecutor().
                    scheduleWithFixedDelay(new SyncKeeper(tilda,service), 0L, 5L, TimeUnit.SECONDS);
        }
    }



    //Service requests
    @Override public void syncOk(){
        tilda.setSync(true);
//        consumers.forEach(TildaInterface::syncOk);
    }
    @Override public void no(){};
    @Override public void processData(int reo, int mio) {
        consumers.forEach(x->x.processData(reo,mio));
    }
    @Override public void processState(){};

    //To service Response
    @Override public void waitSync(){
        connectWithTilda();
    };
    @Override public void setFrequency(Frequency frequency){
        service.setFrequency(frequency);
    }
    @Override public void setForm(Form form){
        service.setForm(form);
    }
    @Override public void setAmplitude(Amplitude amplitude){
        service.setAmplitude(amplitude);
    }
    @Override public void setState(State state){
        service.setState(state);
    }

}
