package com.ti.data;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TildaProtocol {
    int numOfBytes = 3;
    byte sinchro = (byte) 0xAA;

    public void sendFormattedData(PushbackInputStream stream, ConcurrentLinkedQueue queue, String flowName) {
        Collection<Integer> list = new ArrayList<Integer>();
        try {
            int numOfPackage = (stream.available()/numOfBytes);
            if(numOfPackage > 0){
                byte[] buf = new byte[numOfPackage*numOfBytes];
                int n = stream.read(buf);
                for(int i = 0; i < buf.length; i+=numOfBytes){
                    if(buf[i]==sinchro){
                        int value = buf[i+2] + (buf[i+1]<<8);
                        list.add(value);
//                        System.out.println(buf[i] + " "+ buf[i + 1] + " "+buf[i+2] + " itogo" + value);
                    }else {
                        stream.unread(buf);
                        Logger.getLogger(getClass().getName()).info("Protocol CRASH");
                    }
                }

            }
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.FINE, String.format("%s %s", toString(), e.getMessage()), e);
//            e.printStackTrace();
        }
        if(!list.isEmpty()){
//            System.out.println(""+list.toString());
//            manager.getQueue(flowName).addAll(list);
        }
    }
}
