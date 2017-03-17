package com.ti.comm;

import com.ti.command.AbstractCommand;
import com.ti.AnalogSignalManager;
import com.ti.command.DataCommand;
import com.ti.TildaCommandTypes;
import com.ti.TildaController;

public class AnTeTildaController extends TildaController{
    AnalogSignalManager manager;

    @Override
    public void serviceRequest(AbstractCommand command){
        if(TildaCommandTypes.OK.equalCommand(command)){
            System.out.println("Ok");
        }if(TildaCommandTypes.NO.equalCommand(command)){
            System.out.println("NO");
        }if(TildaCommandTypes.DATA.equalCommand(command)){
            int reo = ((DataCommand)command).getReo();
            manager.addToReo(reo);
            int mio = ((DataCommand)command).getMio();
            manager.addToMio(mio);
//            System.out.println("DATA reo: "+ reo + " mio: " + mio);
        }
    }

    public void setSignalManager(AnalogSignalManager manager){
        this.manager = manager;
    }
}
