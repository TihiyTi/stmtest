package com.ti.comm;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SignalSender {
    private ConcurrentLinkedQueue queue;
    private ConcurrentLinkedQueue maxQueue;
    private SerialPort port;
    private int count = 0;

    private List<AbstractProtocol> protocolList = new ArrayList<>();


    public SignalSender() {
        openPort("COM7", SerialPort.BAUDRATE_115200);
    }

//    public void linkToQueue(ConcurrentLinkedQueue<Number> queue){
//        this.queue = queue;
//    }

//    public void linkToMaxQueue(ConcurrentLinkedQueue<Number> outputQueue) {
//        maxQueue = outputQueue;
//    }

    public void openPort(String portName, int speed) {
        port = new SerialPort(portName);
        try {
            port.openPort();
            System.out.println("Port opened");
            port.setParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            port.setEventsMask(SerialPort.MASK_RXCHAR);
            port.addEventListener(new SimpleProtocolListener());
            port.writeByte((byte)12);
//            port.writeString("Hurrah!");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                System.out.println("Close port");
//                port.closePort();
//            } catch (SerialPortException e) {
//                e.printStackTrace();
//            }
//        }
    }
    public void sendData(Number data){
        try {
            port.writeByte(data.byteValue());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void sendDataArray(byte[] array){
        try {
            port.writeBytes(array);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void setProtocol(AbstractProtocol protocol) {
        protocolList.add(protocol);
        protocol.setSender(this);
    }

    class SimpleProtocolListener implements SerialPortEventListener{
        private boolean isProtocolCorrect = false;
        private int skipByteCount = 0;
        private ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<Byte>();
        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue()>0){
                byte[] buf = new byte[0];
                try {
                    buf = port.readBytes();
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
                for (byte element: buf){
                    deque.add(element);
                }
                if(protocolList.get(0).checkProtocol(deque)){
                    protocolList.forEach(x->{
                        x.parseQueue(deque);
                    });
                }

//                boolean isProtocol = checkProtocol();
////                System.out.println(isProtocol);
//                if(isProtocol){
//                    while(isCommandReady().size()>0){
////                        System.out.print("_");
//                    }
//                }
////                System.out.println();
            }
        }
        private boolean checkProtocol(){

            if(deque.peek()==(byte)0xAA){
                isProtocolCorrect = true;
                return true;
            }else{
                while(!deque.isEmpty()){
                    if(deque.peek()==(byte)0xAA){
                        isProtocolCorrect = true;
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

        private final int OK = 0;
        private final int NO = 1;
        private final byte DATA = (byte)0xa1;
        private final byte MAX = (byte)0xa2;
        private final int STATUS = 3;
//        private final int MAX = 4;
        private final int MIN = 5;

        private List<Byte> isCommandReady(){
            boolean fullCommand = false;
            List<Byte> list = new ArrayList<Byte>();
            if(deque.size()>2){
                list.add(deque.poll());
                if(!deque.isEmpty()){
                    Byte command = deque.poll();
                    list.add(command);
//                System.out.println("command " + command);
                    switch (command){
                        case OK:
                            break;
                        case NO:
                            break;
                        case DATA:
                            if(deque.size()<6){break;}
                            byte one = deque.poll();
                            byte two = deque.poll();
                            byte three = deque.poll();
                            byte four = deque.poll();
                            int oneInt = one & 0xFF;
                            int twoInt = two & 0xFF;
                            int threeInt = three & 0xFF;
                            int fourInt = four & 0xFF;
                            deque.poll();
                            deque.poll();
//                        int value = three + (two << 8)+(one << 16);
                            //int value = (four<<24) + (three<<16) + (two << 8)+(one);
                            int valueInt = (fourInt<<24) + (threeInt<<16) + (twoInt << 8)+(oneInt);
                            //System.out.println("Send "+value+"  one: "+ one+ "  two: "+two+ "   three: "+three);
                            //System.out.println("     "+valueInt+"  one: "+ oneInt+ "  two: "+twoInt+ "   three: "+threeInt);
//                            System.out.println(count+ "  " +valueInt);
                            count++;
                            queue.add(valueInt);
                            list.add(one);
                            list.add(two);
                            list.add(three);
                            fullCommand = true;
//                        System.out.println(value);
                            break;
                        case MAX:
                            if(deque.size()<6){break;}
                            int oneIntM = deque.poll() & 0xFF;
                            int twoIntM = deque.poll() & 0xFF;
                            int threeIntM = deque.poll() & 0xFF;
                            int fourIntM = deque.poll() & 0xFF;
                            deque.poll();
                            deque.poll();
                            int valueIntM = (fourIntM<<24) + (threeIntM<<16) + (twoIntM << 8)+(oneIntM);
                            System.out.println("Max = " + valueIntM);
                            maxQueue.add(valueIntM);
                            list.add((byte)oneIntM);
                            list.add((byte)twoIntM);
                            list.add((byte)threeIntM);
                            fullCommand = true;
                            break;
                        default:
                            if(deque.size()<6){break;}
                            deque.poll();
                            deque.poll();
                            deque.poll();
                            deque.poll();
                            deque.poll();
                            deque.poll();
                            list.add((byte) 0);
                            break;
                    }
                }
            }
            if(!fullCommand){
//                System.out.println(deque);
//                System.out.println(list);
                for(int i = list.size()-1; i > -1; i--){
//                    deque.addFirst(list.get(i));
                    deque.offerFirst(list.get(i));
                }
//                System.out.println(deque);
                list.clear();
            }
            return list;
        }
    }

}
