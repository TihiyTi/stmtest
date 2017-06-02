package com.ti.control;

import com.ti.AnalogTester;
import com.ti.PropertiesService;
import com.ti.TildaInterface;
import com.ti.TimeProfiler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AnalogSignalManager implements TildaInterface {
    private static final Logger LOG = LogManager.getLogger("reportsLogger");
    private long time = 0;

    public AnalogSignalManager() {
        String reoRangeKoef = PropertiesService.getGlobalProperty("reoRangeKoef", Level.INFO.toString());
        String mioRangeKoef = PropertiesService.getGlobalProperty("mioRangeKoef", Level.INFO.toString());
        Integer reoKoef = Integer.valueOf(reoRangeKoef);
        Integer mioKoef = Integer.valueOf(mioRangeKoef);
        reoReranger = new Reranger(reoKoef);
        mioReranger = new Reranger(mioKoef);
//        time = new Date().getTime();
    }

    private TimeProfiler profiler = new TimeProfiler("AnalogSignalManager");
    private Reranger reoReranger;
    private Reranger mioReranger;

    private int count = 0;

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
//        if(time==0){
//            time = new Date().getTime();
//        }
        profiler.check(count++, 100);
        reoQueue.add(reoReranger.rerange(reo));
        mioQueue.add(mioReranger.rerange(mio));
//        LOG.info((new Date().getTime() - time)/1000.+" "+reo+" "+ mio);
        LOG.info((time++)/1000.+" "+reo+" "+ mio);
    }
}
