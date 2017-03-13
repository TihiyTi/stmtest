package com.ti.impl;

import com.ti.AbstractCommand;
import com.ti.AbstractCommandProtocol;
import com.ti.DataCommand;
import com.ti.EmptyCommand;

public class TildaProtocol extends AbstractCommandProtocol<TildaCommandTypes>{


    public TildaProtocol() {
        setCommandMap(TildaCommandTypes.OK.getCommandSizeMap());
        fillSetOfCommandType(TildaCommandTypes.values());
    }

    static AbstractCommand createCommand(TildaCommandTypes type){
        return new EmptyCommand<>(type);
    }
    static AbstractCommand sendDataCommand(int reo, int mio){
        return new DataCommand<>(TildaCommandTypes.DATA, reo, mio);
    }


    @Override
    protected void supportCommand(AbstractCommand command) {

    }

//    public TildaProtocol() {
//        fillSetOfCommandType(TildaCommandTypes.values());
//    }

}
