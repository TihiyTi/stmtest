package com.ti;

import com.ti.impl.TildaCommandTypes;

import java.nio.ByteBuffer;

public class DataCommand extends AbstractCommand<TildaCommandTypes> {
    private int reo = 0;
    private int mio = 0;

    public DataCommand() {
        command = TildaCommandTypes.DATA;
    }

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        reo = buffer.getInt();
        mio = buffer.getInt();
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        return null;
    }

    int getReo(){return reo;}
    int getMio(){return mio;}
}
