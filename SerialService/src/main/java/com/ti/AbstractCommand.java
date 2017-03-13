package com.ti;

public abstract class AbstractCommand<COMMAND_TYPE> implements Requestable, Responsable{

    COMMAND_TYPE type;

    public COMMAND_TYPE is(){
        return type;
    }
}
