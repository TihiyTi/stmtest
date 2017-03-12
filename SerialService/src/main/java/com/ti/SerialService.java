package com.ti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerialService<RESPONSE, REQUEST> {
    private ComPortWorker comPort = new ComPortWorker();
    private AbstractProtocol<RESPONSE, REQUEST> protocol;
    private List<AbstractProtocol<RESPONSE, REQUEST>> listChildrenProtocol = new ArrayList<>();

//    public void initService(SerialControllable<RESPONSE,REQUEST> controller,
//                            AbstractProtocol<RESPONSE, REQUEST> mainProtocol,
//                            AbstractProtocol<RESPONSE, REQUEST> ... childrenProtocols){
//        protocol = mainProtocol;
//        protocol.addController(controller);
//
//        Arrays.asList(childrenProtocols).forEach(x->{
//            x.addController(controller);
//            controller.addProtocol(x);
//        });
//    }

    public void setProtocol(AbstractProtocol<RESPONSE, REQUEST> mainProtocol){
        protocol = mainProtocol;

        protocol.setSender(comPort);
        comPort.setProtocol(protocol);
    }
    @SafeVarargs
    public final void addChildrenProtocol(AbstractProtocol<RESPONSE, REQUEST>... childrenProtocols){
        //todo добавить логирование, если "детские" протоколы добавлены первыми
        listChildrenProtocol.addAll(Arrays.asList(childrenProtocols));
        listChildrenProtocol.forEach(x->protocol.addProtocol(x));
    }
    public void addController(SerialControllable<RESPONSE,REQUEST> controller){
        //todo добавить логирование, если контроллер добавлен, до родительского протокола
        protocol.addController(controller);
        controller.addProtocol(protocol);
        listChildrenProtocol.forEach(controller::addProtocol);

    }

}
