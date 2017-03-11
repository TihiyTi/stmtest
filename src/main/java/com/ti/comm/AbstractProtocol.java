package com.ti.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class AbstractProtocol implements Protocol {
    private int skipByteCount = 0;
    private Map<Byte, Integer> commandSizes;
    private boolean partCommand = false;
    private SignalSender sender;

    public void setCommandMap(Map<Byte,Integer> map) {
        commandSizes = map;
    }

    void setSender(SignalSender sender){
        this.sender = sender;
    }

    boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        if(deque.peek()==(byte)0xAA){
            return true;
        }else{
            while(!deque.isEmpty()){
                if(deque.peek()==(byte)0xAA){
                    return true;
                }else{
                    deque.poll();
                    skipByteCount++;
                    if(skipByteCount%10 ==0){
                        System.out.println("Crash Protocol N "+skipByteCount);
                    }
                }
            }
            return false;
        }
    }

    void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        partCommand = false;
        while(deque.size()>2 && !partCommand){
//            System.out.println("ParseCommand");
            parseCommand(deque);
        }
    }

    private void parseCommand(ConcurrentLinkedDeque<Byte> deque){
        List<Byte> bufferByteList = new ArrayList<Byte>();
        bufferByteList.add(deque.poll());
            Byte command = deque.poll();
            bufferByteList.add(command);
//            System.out.println("Command " + command);
            if(commandSizes.containsKey(command) && deque.size() >=  commandSizes.get(command)){
                int commandSize = commandSizes.get(command);
                byte[] data = new byte[commandSize];
                for(int i = 0; i < commandSize; i++){
                    data[i] = deque.poll();
                }
//                System.out.println("Data " + data.toString());
                chooseCommand(command, data);
            }else{
                for(int i = bufferByteList.size()-1; i > -1; i--){
                    deque.offerFirst(bufferByteList.get(i));
                }
                bufferByteList.clear();
                partCommand = true;
            }
    }

}
