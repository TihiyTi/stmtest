package com.ti;

import com.ti.command.param.Amplitude;
import com.ti.command.param.Form;
import com.ti.command.param.Frequency;
import com.ti.command.param.State;

public interface TildaInterface {
    default void setMainController(TildaInterface controller){};

    //Requests
    default void syncOk(){};
    default void no(){};
    default void processData(int reo, int mio) {}
    default void processState(){};

    //Service Response
    default void waitSync(){};
    default void setFrequency(Frequency frequency){};
    default void setForm(Form form){};
    default void setAmplitude(Amplitude amplitude){};
    default void setState(State state){};
}
