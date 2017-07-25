package com.ti;

import com.ti.command.AbstractCommand;
import com.ti.protocol.AbstractCommandProtocol;
import com.ti.command.DataCommand;
import com.ti.command.EmptyCommand;
import com.ti.command.param.ParamEnum;
import com.ti.command.SetParamCommand;

public class TildaProtocol extends AbstractCommandProtocol<TildaCommandTypes>{


    public TildaProtocol() {
        setCommandMap(TildaCommandTypes.OK.getCommandSizeMap());
        fillSetOfCommandType(TildaCommandTypes.values());
    }

    //Debug Command
    @Deprecated
    public static AbstractCommand sendDataCommand(int reo, int mio){
        return new DataCommand<>(TildaCommandTypes.DATA, reo, mio);
    }
    @Deprecated
    public static EmptyCommand<TildaCommandTypes> createCommand(TildaCommandTypes type){
        return new EmptyCommand<>(type);
    }
}
