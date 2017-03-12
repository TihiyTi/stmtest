package com.ti;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface CommandSplittable<REQUEST> {
    void setCommandSizeMap(Map<Byte, Integer> map);
    void parseQueue(ConcurrentLinkedDeque<Byte> deque);
}
