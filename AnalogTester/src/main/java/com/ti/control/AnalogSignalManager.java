package com.ti.control;

import com.ti.PropertiesService;
import com.ti.TildaInterface;
import com.ti.TimeProfiler;
import com.ti.FirFilter;
import com.ti.SignalProcessor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AnalogSignalManager implements TildaInterface {
    private static final Logger LOG = LogManager.getLogger("reportsLogger");
    private long time = 0;

    private TimeProfiler profiler = new TimeProfiler("AnalogSignalManager");
    private Reranger reoReranger;
    private Reranger mioReranger;
    private int count = 0;

    SignalProcessor dspReo = new SignalProcessor();
    SignalProcessor dspMio = new SignalProcessor();

    private ConcurrentLinkedQueue<Number> reoQueue = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> mioQueue = new ConcurrentLinkedQueue<Number>();

    public AnalogSignalManager() {
        String reoRangeKoef = PropertiesService.getGlobalProperty("reoRangeKoef", Level.INFO.toString());
        String mioRangeKoef = PropertiesService.getGlobalProperty("mioRangeKoef", Level.INFO.toString());
        Integer reoKoef = Integer.valueOf(reoRangeKoef);
        Integer mioKoef = Integer.valueOf(mioRangeKoef);
        reoReranger = new Reranger(reoKoef);
        mioReranger = new Reranger(mioKoef);
//        time = new Date().getTime();

        // TODO: 21.06.2017 Вынести функционал настройки фильтров по Properties в DSP модуль
        String filter1KoefReo = PropertiesService.getGlobalProperty("f1koefReo", "1");
        String filter2KoefReo = PropertiesService.getGlobalProperty("f2koefReo", "1");
        String filter1KoefMio = PropertiesService.getGlobalProperty("f1koefMio", "1");
        String filter2KoefMio = PropertiesService.getGlobalProperty("f2koefMio", "1");
        double[] koefReo = Arrays.stream(filter1KoefReo.split(",")).map(Double::valueOf).mapToDouble(Number::doubleValue).toArray();
        double[] koef2Reo = Arrays.stream(filter2KoefReo.split(",")).map(Double::valueOf).mapToDouble(Number::doubleValue).toArray();
        double[] koefMio = Arrays.stream(filter1KoefMio.split(",")).map(Double::valueOf).mapToDouble(Number::doubleValue).toArray();
        double[] koef2Mio = Arrays.stream(filter2KoefMio.split(",")).map(Double::valueOf).mapToDouble(Number::doubleValue).toArray();

        FirFilter f1Reo = new FirFilter(koefReo);
        FirFilter f2Reo = new FirFilter(koef2Reo);
        dspReo.addFIR(f1Reo);
        dspReo.addFIR(f2Reo);

        FirFilter f1Mio = new FirFilter(koefMio);
        FirFilter f2Mio = new FirFilter(koef2Mio);
        dspMio.addFIR(f1Mio);
        dspMio.addFIR(f2Mio);
    }

    @Override
    public void processData(int reo,int mio){
//        if(time==0){
//            time = new Date().getTime();
//        }

//        profiler.check(count++, 100);

        Number reoF = dspReo.addElement(reo);
        Number mioF = dspReo.addElement(mio);

//        reoQueue.add(reoF);
//        mioQueue.add(mioF);
        reoQueue.add((double)reo);
        mioQueue.add((double)mio);
//        LOG.info((new Date().getTime() - time)/1000.+" "+reo+" "+ mio);
        LOG.trace((time++)/1000.+" "+reoF+" "+ mioF);
    }

    public ConcurrentLinkedQueue<Number> getReoQueue() {
        return reoQueue;
    }
    public ConcurrentLinkedQueue<Number> getMioQueue() {
        return mioQueue;
    }
}
