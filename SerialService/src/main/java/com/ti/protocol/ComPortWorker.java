package com.ti.protocol;

import com.ti.protocol.AbstractProtocol;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ComPortWorker {
    private SerialPort port;
    private int count = 0;

    private AbstractProtocol protocol;

    public ComPortWorker() {
        openPort("COM5", SerialPort.BAUDRATE_115200);
    }

    public void openPort(String portName, int speed) {
        port = new SerialPort(portName);
        try {
            port.openPort();
            System.out.println("Port opened");
            port.setParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            port.setEventsMask(SerialPort.MASK_RXCHAR);
            port.addEventListener(new SimpleProtocolListener());
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
    public void reopenPort(String portName){
        try {
            System.out.println("Close port");
            port.closePort();
            openPort(portName, SerialPort.BAUDRATE_115200);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void sendData(Number data){
        try {
            port.writeByte(data.byteValue());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void sendDataArray(ByteBuffer buffer){
        if(port.isOpened()){
            try {
                port.writeBytes(buffer.array());
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }else{
            // TODO: 13.03.2017 добавить логирования события
            System.out.println("Port NO open. Data don't send.");
        }
    }

    public void setProtocol(AbstractProtocol protocol) {
        this.protocol = protocol;
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
                if(protocol.checkProtocol(deque)){
                    protocol.parseQueue(deque);
                }
            }
        }
    }

}
