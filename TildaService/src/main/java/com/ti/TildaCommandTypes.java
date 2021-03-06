package com.ti;

import com.ti.command.AbstractCommand;
import com.ti.command.*;
import com.ti.command.param.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum TildaCommandTypes implements CommandTypable {
    OK((byte)0x00, Direction.REQUEST),
    NO((byte)0x01, Direction.REQUEST),
    DATA((byte)0x02, Direction.REQUEST){
        @Override
        public AbstractCommand getCommand(){
            return new DataCommand<>(this);
        }
    },
    STATE((byte)0x03, Direction.REQUEST),
    MAX_INDEX((byte)0x04, Direction.REQUEST){
        @Override
        public AbstractCommand getCommand(){
            return new IndexCommand<>(this);
        }
    },
    MIN_INDEX((byte)0x05, Direction.REQUEST){
        @Override
        public AbstractCommand getCommand(){
            return new IndexCommand<>(this);
        }
    },
    WAIT_SYNC((byte)0x10, Direction.RESPONSE),
    FREQ_SET((byte)0x11, Direction.RESPONSE){
        @Override
        public SetParamCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,Frequency>(this, Stream.of(Frequency.values()));}
    },
    FORM_SET((byte)0x12, Direction.RESPONSE){
        @Override
        public AbstractCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,Form>(this,Stream.of(Form.values()));}
    },
    AMPL_SET((byte)0x13, Direction.RESPONSE){
        @Override
        public AbstractCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,Amplitude>(this, Stream.of(Amplitude.values()));}
    },
    STATE_SET((byte)0x14, Direction.RESPONSE){
        @Override
        public AbstractCommand getCommand(){ return new SetParamCommand<TildaCommandTypes,State>(this,Stream.of(State.values()));}
    },
    NAKE_DATA((byte)0xA3, Direction.REQUEST){
        @Override
        public AbstractCommand getCommand(){
            return new NakeDataCommand(this);
        }
    };
    byte syncByte;

    TildaCommandTypes(byte syncByte, Direction direction){ this.syncByte = syncByte;}

    // Default overriding for typical command
    @Override
    public AbstractCommand getCommand(){
        return new EmptyCommand<>(this);
    }
    @Override
    public boolean equalCommand(AbstractCommand command){
        return command.is() == this;
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

    public Map<Byte,Integer> getCommandSizeMap(){
        Map<Byte,Integer> map = new HashMap<>();
        Arrays.asList(values()).forEach(x->map.put(x.getHeadByte(),x.getCommandSize()));
        return map;
    }
}
