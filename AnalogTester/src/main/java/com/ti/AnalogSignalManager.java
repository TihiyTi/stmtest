package com.ti;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AnalogSignalManager {
    private ConcurrentLinkedQueue<Number> reoQueue = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> mioQueue = new ConcurrentLinkedQueue<Number>();



    public ConcurrentLinkedQueue<Number> getReoQueue() {
        return reoQueue;
    }

    public ConcurrentLinkedQueue<Number> getMioQueue() {
        return mioQueue;
    }

    public void addToReo(int reoSample){
        reoQueue.add(reoSample);
    }
    public void addToMio(int mioSample){
        mioQueue.add(mioSample);
    }
}
