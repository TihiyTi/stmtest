package com.ti;

import com.ti.FirFilter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SignalProcessor {
    private List<FirFilter> filterList = new ArrayList<>();
    private Queue<FirFilter> queue = new ArrayDeque<>();

    public Number addElement(Number el){
        if (filterList.size()!=0){
            return filterList.get(0).addElement(el);
        }else{
            return el;
        }
    }


    public void addFIR(FirFilter filter){
        if(filterList.size()!=0){
            filterList.get(filterList.size()-1).linkForward(filter);
            filterList.add(filter);
        }else{
            filterList.add(filter);
        }
    }
}
