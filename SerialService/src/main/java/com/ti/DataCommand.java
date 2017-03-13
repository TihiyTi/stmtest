package com.ti;

import java.nio.ByteBuffer;

public class DataCommand<COMMAND_TYPE extends CheckByHeadByte> extends AbstractCommand<COMMAND_TYPE> {
    private int reo = 0;
    private int mio = 0;

    public DataCommand(COMMAND_TYPE commandType) {
        type = commandType;
    }
    public DataCommand(COMMAND_TYPE commandType, int reo, int mio) {
        type = commandType;
        this.reo = reo;
        this.mio = mio;
    }

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        reo = buffer.getInt();
        mio = buffer.getInt();
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte)0xAA);
        buffer.put(type.getHeadByte());
        buffer.putInt(reo);
        buffer.putInt(mio);
        return buffer;
    }

    public int getReo(){return reo;}
    public int getMio(){return mio;}
}
