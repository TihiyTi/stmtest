package com.ti.impl.command;

public enum Frequency implements ParamEnum{
    HZ_0_8((byte)0x01), HZ_1((byte)0x02), HZ_2_5((byte)0x03), HZ_12((byte)0x04);

    Frequency(byte b) {
        idByte = b;
    }

    byte idByte;

    @Override
    public byte getIdByte(){return idByte;}
}
