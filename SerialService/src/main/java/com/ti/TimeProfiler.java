package com.ti;

import org.apache.log4j.Logger;

import java.util.Date;

public class TimeProfiler {
    private String name;
    private static Logger LOG = Logger.getLogger("Profile");

    private long previous = new Date().getTime();

    public TimeProfiler(String name) {
        this.name = name;
    }

    public void check(int index, int step){
        if(index%step == 0){
            long current = new Date().getTime();
            long delta = current - previous;
            previous = current;
            LOG.info(name + " " + delta);
        }
    }
}

