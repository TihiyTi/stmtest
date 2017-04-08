package com.ti.control;

import com.ti.TildaInterface;
import com.ti.TimeProfiler;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AnalogSignalManager implements TildaInterface {
    private TimeProfiler profiler = new TimeProfiler("AnalogSignalManager");
    private int count = 0;

    public static Logger LOG = Logger.getLogger(AnalogSignalManager.class);
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
        profiler.check(count++, 100);
        reoQueue.add(reo);
        mioQueue.add(mio);
    }

}
