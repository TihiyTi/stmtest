package com.ti.impl;

import com.ti.command.AbstractCommand;
import com.ti.CheckByHeadByte;
import com.ti.impl.command.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TildaCommandTypes implements CheckByHeadByte {
    OK((byte)0x00, Direction.REQUEST),
    NO((byte)0x01, Direction.REQUEST),
    DATA((byte)0x02, Direction.REQUEST){
        @Override
        public AbstractCommand getCommand(){
            return new DataCommand<>(this);
        }
    },
    STATE((byte)0x03, Direction.REQUEST),
    MAX_INDEX((byte)0x04, Direction.REQUEST),
    MIN_INDEX((byte)0x05, Direction.REQUEST),

    WAIT_SYNC((byte)0x00, Direction.RESPONSE),
    FREQ_SET((byte)0x01, Direction.RESPONSE){
        @Override
        public AbstractCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,Frequency>(this);}
    },
    FORM_SET((byte)0x02, Direction.RESPONSE){
        @Override
        public AbstractCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,Form>(this);}
    },
    AMPL_SET((byte)0x03, Direction.RESPONSE){
        @Override
        public AbstractCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,Amplitude>(this);}
    },
    STATE_SET((byte)0x04, Direction.RESPONSE){
        @Override
        public AbstractCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,State>(this);}
    };

    byte syncByte;

    TildaCommandTypes(byte syncByte, Direction direction){ this.syncByte = syncByte;}

    // Default overriding for typical command
    @Override
    public AbstractCommand getCommand(){
        return new EmptyCommand<>(this);
    }

    @Override
    public boolean check(byte syncByte){
        return syncByte==this.syncByte;
    }
    @Override
    public byte getHeadByte(){
        return syncByte;
    }
    @Override
    public int getCommandSize(){
        return 8;
    }
    @Override
    public boolean equalCommand(AbstractCommand command){
        return command.is() == this;
    }

    public Map<Byte,Integer> getCommandSizeMap(){
        Map<Byte,Integer> map = new HashMap<>();
        Arrays.asList(values()).forEach(x->map.put(x.getHeadByte(),x.getCommandSize()));
        return map;
    }
}
