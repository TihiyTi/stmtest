package com.ti.data;

public interface TildaReceiveInterface {
    default void addMaximumIndex(){};
    default void addOutSignalSample(Number sample){};
    default void syncOk(){};
}
