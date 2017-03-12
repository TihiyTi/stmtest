package com.ti.impl;

import com.ti.AbstractCommand;
import com.ti.Protocol;
import com.ti.SerialControllable;

import java.util.ArrayList;
import java.util.List;

public class TildaController implements SerialControllable<AbstractCommand<TildaCommandTypes>,AbstractCommand<TildaCommandTypes>> {
    List<Protocol<AbstractCommand<TildaCommandTypes>,AbstractCommand<TildaCommandTypes>>> list = new ArrayList<>();


    @Override
    public void serviceRequest(AbstractCommand<TildaCommandTypes> command) {
        command.isCommand(TildaCommandTypes.DATA);
    }

    @Override
    public void toServiceResponse(AbstractCommand<TildaCommandTypes> command) {
        list.forEach(x->x.sendResponse(command));
    }

    @Override
    public void addProtocol(Protocol<AbstractCommand<TildaCommandTypes>, AbstractCommand<TildaCommandTypes>> protocol) {list.add(protocol);}

    public void runTest() {
        toServiceResponse(TildaCommandTypes.OK.getCommand());
    }
}
