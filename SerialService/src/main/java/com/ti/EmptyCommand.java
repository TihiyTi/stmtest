package com.ti;

import com.ti.impl.TildaCommandTypes;

import java.nio.ByteBuffer;

public class EmptyCommand extends AbstractCommand<TildaCommandTypes> {
    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        return null;
    }
}
