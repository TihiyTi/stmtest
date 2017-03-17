package com.ti.comm;

public interface ViewInterface {
    byte FREQUENCY_0_8 = (byte)0x00;
    byte FREQUENCY_1 = (byte)0x01;
    byte FREQUENCY_1_2 = (byte)0x02;
    byte FREQUENCY_2_5 = (byte)0x03;
    byte FREQUENCY_12 = (byte)0x04;

    byte AMPLITUDE_25 = (byte)0x00;
    byte AMPLITUDE_50 = (byte)0x01;
    byte AMPLITUDE_75 = (byte)0x02;
    byte AMPLITUDE_100 = (byte)0x03;

    byte FORM1 = (byte)0x00;
    byte FORM2 = (byte)0x01;
    byte FORM3 = (byte)0x02;
    byte FORM4 = (byte)0x03;

    byte MIN10 = (byte)0x00;
    byte MIN20 = (byte)0x01;
    byte MIN30 = (byte)0x02;
    byte MIN40 = (byte)0x03;


    void setFrequency(byte freq);
    void setForm(byte form);
    void setAmplitude(byte ampl);
    void setTimer(byte min);
}
