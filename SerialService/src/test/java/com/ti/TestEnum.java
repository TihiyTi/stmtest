package com.ti;

public class TestEnum {
    public static void main(String[] args) {
        En oneFirst = En.FIRST;
        oneFirst.setValue(1);
        System.out.println(oneFirst.getValue());
        En twoFirst = En.FIRST;
        twoFirst.setValue(2);

        System.out.println(oneFirst.getValue());
        System.out.println(twoFirst.getValue());

    }
    enum En implements SetValuable{
        FIRST(){
            @Override
            public void setValue(int i){value = i;}
            public void doOneFirst(){}
            public void doTwoFirst(){}
        },SECOND(){
            @Override
            public void setValue(int i){value = 2*i;}
        };

        int value;

        int getValue(){
            return value;
        }
    }
    interface SetValuable {
        void setValue(int i);
    }
}
