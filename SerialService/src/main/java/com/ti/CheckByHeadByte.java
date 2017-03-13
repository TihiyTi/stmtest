package com.ti;

import java.util.Map;

public interface CheckByHeadByte {
    // TODO: 13.03.2017 make abstract class and move same method to abstract class
    boolean check(byte head);
    byte getHeadByte();
    int getCommandSize();
    Map<Byte, Integer> getCommandSizeMap();
    AbstractCommand getCommand();
    boolean equalCommand(AbstractCommand command);
}
