package com.ti;

import com.ti.command.param.Amplitude;
import com.ti.command.param.Form;
import com.ti.command.param.Frequency;
import com.ti.command.param.State;

import java.util.ArrayList;
import java.util.List;

public class TildaLogic implements TildaInterface{
    private TildaInterface service;
    private List<TildaInterface> consumers = new ArrayList<>();

    TildaLogic(TildaInterface serviceInterface, List<TildaInterface> consumersInterfaces) {
        service = serviceInterface;
        service.setMainController(this);

        consumers = consumersInterfaces;
        consumers.forEach(x->x.setMainController(this));
    }


    //Service requests
    @Override public void syncOk(){
        consumers.forEach(TildaInterface::syncOk);
    }
    @Override public void no(){};
    @Override public void processData(int reo, int mio) {
        consumers.forEach(TildaInterface::syncOk);
    }
    @Override public void processState(){};

    //To service Response
    @Override public void waitSync(){
        service.waitSync();
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
