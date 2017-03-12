package com.ti;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCommandProtocol<Comma extends CommandTypable<AbstractCommand,Comma>> extends AbstractProtocol<AbstractCommand, AbstractCommand> {
    Set<Comma> commandable = new HashSet<>();

    protected void fillSetOfCommandType(Comma[] arrayOfCommanType){
        commandable.addAll(Arrays.asList(arrayOfCommanType));
    }

    @Override
    public ByteBuffer createResponseToByte(AbstractCommand command) {
        return command.createByteBuffer();
    }

    @Override
    public AbstractCommand createByteToRequest(ByteBuffer buffer) {
        AbstractCommand request = commandable.stream().
                filter(x->x.thisCommand(buffer.get())).findFirst().get().getCommand().parseByteBuffer(buffer);
        return request;
    }
}
