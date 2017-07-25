package com.ti;

import com.google.common.primitives.Doubles;

import java.util.ArrayList;
import java.util.List;

public class FirFilter {
    private double[] kernel;
    private FirFilter nextUnit;

    private int bufferSize;
    private List<Number> buffer = new ArrayList<>();


    public FirFilter(double[] kernel) {
        this.kernel = kernel;
        bufferSize = kernel.length;
//        System.out.println("Const" + buffer.size() + " " + kernel.length);
        List<Double> kernelList = Doubles.asList(kernel);
        kernelList.forEach(x-> buffer.add(0));
//        System.out.println("Const" + buffer.size());
    }


    public Number addElement(Number el){
        if(nextUnit ==  null){
            return apply(el);
        }else{
            return nextUnit.addElement(apply(el));
        }
    }

    public void linkForward(FirFilter forward){
        nextUnit = forward;
    }

    private Number apply(Number el){
        if(kernel == null){
            return el;
        }else{
//            System.out.println(buffer.size());
            buffer.add(el);
//            System.out.println(buffer.size());
            buffer.remove(0);
            double[] bufferArray = buffer.stream().mapToDouble(Number::doubleValue).toArray();
            Double result = 0.;
            for (int i = 0; i < bufferSize; i++) {
//                System.out.println(bufferArray.length+ "  "+kernel.length+ "   " + i+" "+(bufferSize-1-i));
                result = (result + bufferArray[i]*kernel[bufferSize-1-i]);
            }
            return result;
        }
    }

}
