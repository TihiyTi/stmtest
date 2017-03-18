package com.ti.comm;

import com.ti.AbstractEmulController;
import com.ti.TildaProtocol;

public class AnTeEmulController extends AbstractEmulController {
    private byte count = 0;
    @Override
    protected void doIt() {
        count++;
//        System.out.println("ToServise ReoMio:"+count);
        toServiceResponse(TildaProtocol.sendDataCommand((int)count,(int)count));
    }
}
