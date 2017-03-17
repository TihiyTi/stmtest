package com.ti.impl;

import com.ti.command.AbstractCommand;
import com.ti.protocol.AbstractCommandProtocol;
import com.ti.impl.command.DataCommand;
import com.ti.impl.command.EmptyCommand;
import com.ti.impl.command.ParamEnum;
import com.ti.impl.command.SetParamCommand;

public class TildaProtocol extends AbstractCommandProtocol<TildaCommandTypes>{


    public TildaProtocol() {
        setCommandMap(TildaCommandTypes.OK.getCommandSizeMap());
        fillSetOfCommandType(TildaCommandTypes.values());
    }

    //Debug Command
    public static AbstractCommand sendDataCommand(int reo, int mio){
        return new DataCommand<>(TildaCommandTypes.DATA, reo, mio);
    }


    public static AbstractCommand createCommand(TildaCommandTypes type){
        return new EmptyCommand<>(type);
    }
    public static AbstractCommand sendParam(TildaCommandTypes type, ParamEnum value){
        return new SetParamCommand<>(type, value);
    }


    @Override
    protected void supportCommand(AbstractCommand command) {

    }

//    public TildaProtocol() {
//        fillSetOfCommandType(TildaCommandTypes.values());
//    }

}
