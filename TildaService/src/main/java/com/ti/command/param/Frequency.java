package com.ti.command.param;

import java.util.stream.Stream;

public enum Frequency implements ParamEnum<Frequency>{
    HZ_0_8((byte)0x01), HZ_1((byte)0x02), HZ_2_5((byte)0x03), HZ_12((byte)0x04);

    Frequency(byte b) {
        idByte = b;
    }

    byte idByte;

    @Override
    public byte getIdByte(){return idByte;}

//    public Stream<Frequency> getValues(){
//        return Stream.of(Frequency.values());
//    }
}
