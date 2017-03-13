package com.ti.comm;

/**
 * Created by Aleksey on 13.03.2017.
 */
public interface AnTeControllable {
    void reopenPort(String name, int rate);
    void changeEmulateState();
}
