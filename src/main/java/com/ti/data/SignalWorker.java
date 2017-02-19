package com.ti.data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SignalWorker implements Runnable{
    private SignalSender sender;
    private boolean comActive = false;
    public final static int SOURCE_TRIANGE = 0;
    public final static int SOURCE_FILE = 1;

    public final static int OUT_COM = 10;

    BlockingQueue<Number> blockingQueue = new LinkedBlockingQueue<Number>();
    ConcurrentLinkedQueue<Number> sourceQueue = new ConcurrentLinkedQueue<Number>();
    ConcurrentLinkedQueue<Number> outputQueue = new ConcurrentLinkedQueue<Number>();
    ConcurrentLinkedQueue<Number> maxQueue = new ConcurrentLinkedQueue<Number>();

    public SignalWorker() {

    }


    public void setSource(int sourceType){
        RealTimeQueueCreater creater;
        switch (sourceType){
            case SOURCE_TRIANGE:
                creater = new RealTimeQueueCreater();
                creater.autoFillingBlockingQueue(blockingQueue);
                Executors.newSingleThreadScheduledExecutor().execute(this);
                break;
            case SOURCE_FILE:
                creater = new RealTimeQueueCreater();
                creater.fromTxtBlockingQueue(blockingQueue);
                Executors.newSingleThreadScheduledExecutor().execute(this);
                break;
            default:
                break;
        }
    }

    public void setOutput(){
        sender = new SignalSender();
        sender.linkToQueue(outputQueue);
        sender.linkToMaxQueue(maxQueue);
        comActive = true;
    }

    public ConcurrentLinkedQueue<Number> getSourceQueue(){
        return sourceQueue;
    }
    public ConcurrentLinkedQueue<Number> getOutputQueue(){
        return outputQueue;
    }
    public ConcurrentLinkedQueue<Number> getMaxQueue() {
        return maxQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Number element = blockingQueue.take();
                sourceQueue.add(element);
                if(comActive){
//                    sender.sendData(element);
                    sender.sendDataArray(protocolAdapter(element));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] protocolAdapter(Number element){
        byte[] array = new byte[8];
        array[0] = (byte) 0xAA;
        array[1] = (byte) 0xA0;
        array[2] = element.byteValue();
        array[3] = (byte) 0x0;
        array[4] = (byte) 0x0;
        array[5] = (byte) 0x0;
        array[6] = (byte) 0x0;
        array[7] = (byte) 0x0;
        return array;
    }
}
