package com.ti.comm;

public interface Protocol {

    void chooseCommand(byte command, byte[] data);
//    void setCommandMap();
}
