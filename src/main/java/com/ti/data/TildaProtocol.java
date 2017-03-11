package com.ti.data;

import com.ti.comm.AbstractProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TildaProtocol extends AbstractProtocol implements TildaTranciveInterface{

    private final int OK = 0;
    private final int NO = 1;
    private final byte DATA = (byte)0xa1;
    private final byte MAX = (byte)0xa2;
    List<TildaReceiveInterface> contrList = new ArrayList<>();

    public TildaProtocol() {
        //todo спорное архитектурное решение прописывать карту размеров через метод в конструкторе
        Map<Byte, Integer> map = new HashMap<>();
        map.put(DATA, 6);
        map.put(MAX, 6);
        setCommandMap(map);
    }

    public void addController(TildaReceiveInterface controller){
        contrList.add(controller);
//        controller.
    }

    @Override
    public void chooseCommand(byte command, byte[] data) {
        if (command == DATA) {
            int oneInt = data[0] & 0xFF;
            int twoInt = data[1] & 0xFF;
            int threeInt = data[2] & 0xFF;
            int fourInt = data[3] & 0xFF;
            int valueInt = (fourInt << 24) + (threeInt << 16) + (twoInt << 8) + (oneInt);
            contrList.forEach(x -> x.addOutSignalSample(valueInt));
        }
    }

    @Override
    public void setFrequency() {

    }
}
