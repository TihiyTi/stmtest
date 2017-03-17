package com.ti;

import com.ti.protocol.Protocol;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller implements SerialControllable<ByteBuffer,ByteBuffer> {
    List<Protocol<ByteBuffer, ByteBuffer>> list = new ArrayList<>();

    @Override
    public void serviceRequest(ByteBuffer buffer) {
        System.out.println(Arrays.toString(buffer.array()));
//        Arrays.asList(buffer.array()).stream().map(x->);
    }

    @Override
    public void toServiceResponse(ByteBuffer buffer) {
        list.forEach(x->x.sendResponse(buffer));
    }

    @Override
    public void addProtocol(Protocol<ByteBuffer, ByteBuffer> protocol) {
        list.add(protocol);
    }

    void runTest(){
        ByteBuffer list = ByteBuffer.allocate(127);
        for (byte i = 0; i < 127; i++) {
            list.put(i);
        }
        toServiceResponse(list);
    }
}
