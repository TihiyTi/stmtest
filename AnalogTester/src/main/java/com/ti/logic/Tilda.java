package com.ti.logic;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.concurrent.atomic.AtomicBoolean;

public class Tilda {
    BooleanProperty sync = new SimpleBooleanProperty(false);
    BooleanProperty syncTimeout = new SimpleBooleanProperty(false);



    public boolean isSync() {
        return sync.get();
    }

    public void setSync(boolean sync) {
        this.sync.set(sync);
        syncTimeout.set(sync);
    }


}
