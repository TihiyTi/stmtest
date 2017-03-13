package com.ti;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class AbstractProtocol<RESPONSE, REQUEST> implements Protocol<RESPONSE, REQUEST>{
    private ProtocolCheckable protocolChecker = new OneSynchroByteProtocolChecker();
    private CommandSplittable commandSplitter = new CommandSplitter(this);

    private List<Protocol<RESPONSE, REQUEST>> protocolList = new ArrayList<>(Collections.singletonList(this));

    private ComPortWorker sender;
    protected List<SerialControllable<RESPONSE, REQUEST>> serialControllableList = new ArrayList<>();

    // todo странная зависимость
    public void setCommandMap(Map<Byte,Integer> map) {
        commandSplitter.setCommandSizeMap(map);
    }
    void addController(SerialControllable<RESPONSE, REQUEST> controller){
        serialControllableList.add(controller);
    }
    void addProtocol(AbstractProtocol<RESPONSE, REQUEST> protocol){
        protocolList.add(protocol);
        protocol.setSender(sender);
    }

    //Receive methods
    boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        return protocolChecker.checkProtocol(deque);
    }
    void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        commandSplitter.parseQueue(deque);
    }
    void upByteBuffer(ByteBuffer buffer){
//        System.out.println("FromUART: " + Arrays.toString(buffer.array()));
        REQUEST request = protocolList.stream().map(x->x.createByteToRequest(buffer)).filter(x->(x!=null)).findFirst().get();
        serialControllableList.forEach(x->x.serviceRequest(request));
    }

    //Transceiver method
    void setSender(ComPortWorker sender){
        this.sender = sender;
    }
    @Override
    public void sendResponse(RESPONSE response){
        ByteBuffer buffer = protocolList.stream().map(x->x.createResponseToByte(response)).filter(x->(x!=null)).findFirst().get();
//        System.out.println("ToUART: "+Arrays.toString(buffer.array()));
        sender.sendDataArray(buffer);
    }
}
