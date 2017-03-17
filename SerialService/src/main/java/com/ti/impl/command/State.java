package com.ti.impl.command;

public enum State  implements ParamEnum{
    ENABLE((byte)0x01), DISABLE((byte)0x02);

    State(byte b) {
        idByte = b;
    }

    byte idByte;

    @Override
    public byte getIdByte(){return idByte;}

}
