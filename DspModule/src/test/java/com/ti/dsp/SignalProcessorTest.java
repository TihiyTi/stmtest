package com.ti.dsp;

import com.ti.FirFilter;
import com.ti.SignalProcessor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SignalProcessorTest {
    @Test
    public void processorTest(){
        SignalProcessor dsp = new SignalProcessor();
        FirFilter f1 = new FirFilter(new double[]{1,0,-1});
        FirFilter f2 = new FirFilter(new double[]{4.});
        dsp.addFIR(f1);
        dsp.addFIR(f2);

        Integer[] source = new Integer[]{1,2,3,4,5,6,7,6,5,4,3,2,1};
        List<Number> out = Stream.of(source).map(x-> dsp.addElement(x)).collect(Collectors.toList());
        System.out.println(Arrays.asList(source).toString());
        System.out.println(out.toString());
    }

    @Test
    public void splitTest(){
        String test = "one,two,three";
        List<String> list = Arrays.asList(test.split(","));
        System.out.println(list);
    }
}