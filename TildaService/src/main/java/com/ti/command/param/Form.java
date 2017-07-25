package com.ti.command.param;

import java.util.stream.Stream;

public enum Form implements ParamEnum{
    FORM_1((byte)0x01), FORM_2((byte)0x02), FORM_3((byte)0x03), FORM_4((byte)0x04);

    Form(byte b) {
        idByte = b;
    }

    byte idByte;

    @Override
    public byte getIdByte(){return idByte;}
}
