package com.ti;

public abstract class AbstractCommand<COMMANDTYPE> implements Requestable, Responsable{
    COMMANDTYPE command;

    public boolean isCommand(COMMANDTYPE commandForCheck){
        return command.equals(commandForCheck);
    }


}
