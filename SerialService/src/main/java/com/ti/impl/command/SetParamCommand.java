package com.ti.impl.command;

import com.ti.command.AbstractCommand;
import com.ti.CheckByHeadByte;

import java.nio.ByteBuffer;

public class SetParamCommand<COMMAND_TYPE extends CheckByHeadByte, PARAM extends ParamEnum> extends AbstractCommand<COMMAND_TYPE>{

    private PARAM param;

    public SetParamCommand(COMMAND_TYPE commandType) {
        type = commandType;
    }
    public SetParamCommand(COMMAND_TYPE type, PARAM param) {
        this.type = type;
        this.param = param;
    }
    public void setParam(PARAM param){this.param = param;}

    @Override
    public ByteBuffer createByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte)0xAA);
        buffer.put(type.getHeadByte());
        buffer.put(param.getIdByte());
        return buffer;
    }

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        System.out.println("Unsupported in RESPONSE command! If call, please check SerialService implementation");
        return null;
    }
}
