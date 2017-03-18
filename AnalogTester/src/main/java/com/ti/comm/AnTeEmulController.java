package com.ti.comm;

import com.ti.AbstractEmulController;
import com.ti.TildaCommandTypes;
import com.ti.TildaProtocol;
import com.ti.command.DataCommand;

public class AnTeEmulController extends AbstractEmulController {
    private byte count = 0;
    @Override
    protected void doIt() {
        count++;
        toServiceResponse(new DataCommand<>(TildaCommandTypes.DATA, count,count));
    }
}
