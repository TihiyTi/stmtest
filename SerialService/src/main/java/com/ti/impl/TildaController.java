package com.ti.impl;

import com.ti.AbstractCommand;
import com.ti.DataCommand;
import com.ti.Protocol;
import com.ti.SerialControllable;

import java.util.ArrayList;
import java.util.List;

public class TildaController implements SerialControllable<AbstractCommand,AbstractCommand> {
    // TODO: 13.03.2017 судя по всему нет необходимости передавать во все протоколы, так как в методе
    // sendResponse происходит проброс RESPONSE<AbstractCommand> на все протоколы
    // TODO: 13.03.2017 возможно можно работу с протоколом вынести в abstract метод
    // (видно на примере TildaController и EmulController)
    List<Protocol<AbstractCommand,AbstractCommand>> list = new ArrayList<>();


    @Override
    public void serviceRequest(AbstractCommand command) {
        if(TildaCommandTypes.OK.equalCommand(command)){
            System.out.println("Ok");
        }if(TildaCommandTypes.NO.equalCommand(command)){
            System.out.println("NO");
        }if(TildaCommandTypes.DATA.equalCommand(command)){
            int reo = ((DataCommand)command).getReo();
            int mio = ((DataCommand)command).getMio();
            System.out.println("DATA reo: "+ reo + " mio: " + mio);
        }

    }

    @Override
    public void toServiceResponse(AbstractCommand command) {
        list.forEach(x->x.sendResponse(command));
    }

    @Override
    public void addProtocol(Protocol<AbstractCommand, AbstractCommand> protocol) {list.add(protocol);}

    public void runTest() {
        toServiceResponse(TildaProtocol.createCommand(TildaCommandTypes.NO));
        toServiceResponse(TildaProtocol.createCommand(TildaCommandTypes.OK));
        toServiceResponse(TildaProtocol.createCommand(TildaCommandTypes.NO));
        toServiceResponse(TildaProtocol.sendDataCommand(123,345));
    }
}
