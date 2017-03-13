package com.ti.impl;

import com.ti.AbstractCommand;
import com.ti.CheckByHeadByte;
import com.ti.DataCommand;
import com.ti.EmptyCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TildaCommandTypes implements CheckByHeadByte {
    OK((byte)0x00){
        @Override
        public AbstractCommand getCommand(){
            return new EmptyCommand<>(this);
        }
    },
    NO((byte)0x01){
        @Override
        public AbstractCommand getCommand(){
            return new EmptyCommand<>(this);
        }
    },
    DATA((byte)0x02){
        @Override
        public AbstractCommand getCommand(){
            return new DataCommand<>(this);
        }
    },
    STATE((byte)0x03){
        @Override
        public AbstractCommand getCommand(){
            return new EmptyCommand<>(this);
        }
    },
    MAX_INDEX((byte)0x04){
        @Override
        public AbstractCommand getCommand(){
            return new EmptyCommand<>(this);
        }
    },
    MIN_INDEX((byte)0x05){
        @Override
        public AbstractCommand getCommand(){
            return new EmptyCommand<>(this);
        }
    };

    byte syncByte;

    TildaCommandTypes(byte syncByte){ this.syncByte = syncByte;}

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
