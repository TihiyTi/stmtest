package com.ti.command;

import com.ti.CommandTypable;
import com.ti.command.param.ParamEnum;

import java.nio.ByteBuffer;
import java.util.stream.Stream;

public class SetParamCommand<COMMAND_TYPE extends CommandTypable, PARAM extends ParamEnum> extends AbstractCommand<COMMAND_TYPE>{

    private Stream<PARAM> paramStream;
    private PARAM param;

//    public SetParamCommand(COMMAND_TYPE commandType) {
//        type = commandType;
//    }
    public SetParamCommand(COMMAND_TYPE type, PARAM param) {
        this.type = type;
        this.param = param;
    }
    public SetParamCommand(COMMAND_TYPE type, Stream<PARAM> paramStream) {
        this.type = type;
        this.paramStream = paramStream;
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
        byte idByte = buffer.get();
        param = paramStream.filter(x->x.getIdByte()==idByte).findFirst().get();
        return this;
    }

    @Override
    public void debugPrint() {
        System.out.println(type.toString() + " "+ param.toString());
    }
}
