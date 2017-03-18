package com.ti.command;

import com.ti.CommandTypable;
import com.ti.command.AbstractCommand;

import java.nio.ByteBuffer;

public class IndexCommand<COMMAND_TYPE extends CommandTypable>  extends AbstractCommand<COMMAND_TYPE> {
    private int index = 0;

    public IndexCommand(COMMAND_TYPE commandType) {
        type = commandType;
    }
    public IndexCommand(COMMAND_TYPE commandType, int index) {
        type = commandType;
        this.index = index;
    }

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        index = buffer.getInt();
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte)0xAA);
        buffer.put(type.getHeadByte());
        buffer.putInt(index);
        return buffer;
    }

    public int getIndex(){return index;}

    @Override
    public void debugPrint() {
        System.out.println(type.toString() + " "+ index);
    }
}
