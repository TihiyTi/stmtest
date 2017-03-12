package com.ti.impl;

import com.ti.AbstractCommand;
import com.ti.CommandTypable;
import com.ti.DataCommand;
import com.ti.EmptyCommand;

public enum TildaCommandTypes implements CommandTypable<AbstractCommand, TildaCommandTypes> {
    OK((byte)0x00){
        @Override
        public AbstractCommand<TildaCommandTypes> getCommand() {
            return new EmptyCommand();
        }
    },
    NO((byte)0x01){
        @Override
        public AbstractCommand<TildaCommandTypes> getCommand() {
            return new EmptyCommand();
        }
    },
    DATA((byte)0x02){
        @Override
        public AbstractCommand<TildaCommandTypes> getCommand() {
            return new DataCommand();
        }
    },
    STATE((byte)0x03){
        @Override
        public AbstractCommand getCommand() {
            return null;
        }
    },
    MAX_INDEX((byte)0x04) {
        @Override
        public AbstractCommand getCommand() {
            return null;
        }
    }, MIN_INDEX((byte)0x05) {
        @Override
        public AbstractCommand getCommand() {
            return null;
        }
    };

    byte syncByte;
//
    TildaCommandTypes(byte syncByte){
        this.syncByte = syncByte;
    }


    @Override
    public boolean thisCommand(byte syncByte){
        return syncByte==this.syncByte;
    }

//    @Override
//    public AbstractCommand getCommand(byte head){
//
//    }
    @Override
    public AbstractCommand getCommand(byte head) {
        return null;
    }

    @Override
    public TildaCommandTypes[] getValues() {
        return new TildaCommandTypes[0];
    }

//    @Override
//    public boolean isCommand(TildaCommandTypes request){
//        this.request;
//    }
//    @Override
//    public void fillCommand(ByteBuffer buffer){}
//
//    @Nullable
//    public static TildaCommandTypes createCommand(ByteBuffer buffer){
//        TildaCommandTypes command = Arrays.asList(TildaCommandTypes.values()).stream().
//                filter(x->x.thisCommand(buffer.get())).findFirst().get();
//        return command;
//    }

}
