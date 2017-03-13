package com.ti;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractCommandProtocol<COMMAND_TYPE extends CheckByHeadByte> extends AbstractProtocol<AbstractCommand, AbstractCommand> {
    Set<COMMAND_TYPE> commandable = new HashSet<>();

    protected void fillSetOfCommandType(COMMAND_TYPE[] arrayOfCommanType){
        commandable.addAll(Arrays.asList(arrayOfCommanType));
    }

    protected abstract void supportCommand(AbstractCommand command);

    @Override
    public ByteBuffer createResponseToByte(AbstractCommand command) {
        return command.createByteBuffer();
    }

    @Override
    public AbstractCommand createByteToRequest(ByteBuffer buffer) {
        byte head = buffer.get();
        List<COMMAND_TYPE> list = commandable.stream().filter(x->x.check(head)).collect(Collectors.toList());
        return list.stream().findFirst().get().getCommand().parseByteBuffer(buffer);
    }
}
