package com.ti.control;

import com.ti.TildaInterface;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AnalogSignalManager implements TildaInterface {
    private ConcurrentLinkedQueue<Number> reoQueue = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> mioQueue = new ConcurrentLinkedQueue<Number>();

    public ConcurrentLinkedQueue<Number> getReoQueue() {
        return reoQueue;
    }
    public ConcurrentLinkedQueue<Number> getMioQueue() {
        return mioQueue;
    }

    @Override
    public void processData(int reo,int mio){
        reoQueue.add(reo);
        mioQueue.add(mio);
    }

}
