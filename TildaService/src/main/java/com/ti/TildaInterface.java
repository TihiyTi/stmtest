package com.ti;

import com.ti.command.param.Amplitude;
import com.ti.command.param.Form;
import com.ti.command.param.Frequency;
import com.ti.command.param.State;

public interface TildaInterface {
    void setMainController(TildaInterface controller);

    //Requests
    void syncOk();
    void no();
    void processData(int reo, int mio);
    void processState();

    //Service Response
    void waitSync();
    void setFrequency(Frequency frequency);
    void setForm(Form form);
    void setAmplitude(Amplitude amplitude);
    void setState(State state);
}
