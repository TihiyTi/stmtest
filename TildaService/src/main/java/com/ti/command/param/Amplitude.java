package com.ti.command.param;

import java.util.stream.Stream;

public enum Amplitude implements ParamEnum{
    AMPLITUDE_20((byte)(0xFF/5)), AMPLITUDE_40((byte)(0xFF/5*2)),
    AMPLITUDE_60((byte)(0xFF/5*3)), AMPLITUDE_80((byte)(0xFF/5*4)),
    AMPLITUDE_100((byte)0xFF);

    Amplitude(byte b) {
        idByte = b;
    }

    byte idByte;



    @Override
    public byte getIdByte(){return idByte;}
}
