package com.ti.logic;

import com.ti.TildaInterface;


public class SyncKeeper implements Runnable {
    private Tilda tilda;
    private TildaInterface service;

    public SyncKeeper(Tilda tilda, TildaInterface service) {
        this.tilda = tilda;
        this.service = service;
    }


    @Override
    public void run() {
        service.waitSync();
        tilda.syncTimeout.setValue(false);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!tilda.syncTimeout.get()){
            tilda.setSync(false);
        }
    }
}
