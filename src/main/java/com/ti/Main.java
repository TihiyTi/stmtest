package com.ti;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Main {


    public static void main(String[] args) {
        SerialPort serialPort = new SerialPort("COM7");
        try {
            serialPort.openPort();
            System.out.println("OpenPort");
            serialPort.setParams(SerialPort.BAUDRATE_57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);serialPort.writeByte((byte)12);
//            serialPort.addEventListener(new PortReader(serialPort), SerialPort.MASK_RXCHAR);
            serialPort.writeString("Hurrah!");
            System.out.println("Sent data");
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    class PortReader implements SerialPortEventListener {
        SerialPort port;
        PortReader(SerialPort potr){port = potr;}

        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0) {
//                try {
//                    String receivedData = serialPort.readString(event.getEventValue());
//                    System.out.println("Received response: " + receivedData);
//                }
//                catch (SerialPortException ex) {
//                    System.out.println("Error in receiving string from COM-port: " + ex);
//                }
            }
        }

    }
}