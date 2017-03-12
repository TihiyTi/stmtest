package com.ti;

import java.nio.ByteBuffer;

public interface Requestable {
    AbstractCommand parseByteBuffer(ByteBuffer buffer);
}
