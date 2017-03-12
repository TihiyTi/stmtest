package com.ti;

public interface CommandTypable<RESPONSE_REQUEST, COMMAND_TYPE> {

    RESPONSE_REQUEST getCommand();
    RESPONSE_REQUEST getCommand(byte head);
    boolean thisCommand(byte syncByte);
    COMMAND_TYPE[] getValues();
}
