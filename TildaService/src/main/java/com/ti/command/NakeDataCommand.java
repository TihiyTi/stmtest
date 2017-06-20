package com.ti.command;

import com.ti.CommandTypable;

import java.nio.ByteBuffer;

public class NakeDataCommand<COMMAND_TYPE extends CommandTypable> extends AbstractCommand<COMMAND_TYPE> {
    byte[] data;

    public NakeDataCommand(COMMAND_TYPE commandType) {
        type = commandType;
    }

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        for (int i = 0; i < 8; i++) {
            System.out.println();
            byte b = buffer.get;
            System.out.println(b);
            data[i] = b;
        }
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        return null;
    }

    @Override
    public void debugPrint() {
    }
    public byte[] getData(){
        return data;
    }
}
