package com.ti;

import java.nio.ByteBuffer;

public class EmptyCommand<COMMAND_TYPE extends CheckByHeadByte> extends AbstractCommand<COMMAND_TYPE> {


    public EmptyCommand(COMMAND_TYPE commandType) {
        type = commandType;
    }



    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        return ByteBuffer.wrap(new byte[]{(byte)0xAA, type.getHeadByte(),
                (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00});
    }
}
