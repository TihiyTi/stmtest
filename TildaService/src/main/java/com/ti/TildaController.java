package com.ti;

import com.ti.command.AbstractCommand;
import com.ti.command.DataCommand;
import com.ti.command.SetParamCommand;
import com.ti.command.param.Amplitude;
import com.ti.command.param.Form;
import com.ti.command.param.Frequency;
import com.ti.command.param.State;

public class TildaController extends AbstractSerialController implements TildaInterface{
    // TODO: 13.03.2017 судя по всему нет необходимости передавать во все протоколы, так как в методе
    // sendResponse происходит проброс RESPONSE<AbstractCommand> на все протоколы
    private TildaInterface mainController;

    @Override
    public void serviceRequest(AbstractCommand command) {
        if(TildaCommandTypes.OK.equalCommand(command)){
            mainController.syncOk();
//            System.out.println("Ok");
        }if(TildaCommandTypes.NO.equalCommand(command)){
            mainController.no();
//            System.out.println("NO");
        }if(TildaCommandTypes.DATA.equalCommand(command)){
            int reo = ((DataCommand)command).getReo();
            int mio = ((DataCommand)command).getMio();
            mainController.processData(reo,mio);
//            System.out.println("DATA reo: "+ reo + " mio: " + mio);
        }

    }
    @Override
    public void setMainController(TildaInterface controller){
        mainController = controller;
    }

    @Override
    public void syncOk() {}
    @Override
    public void no() {}
    @Override
    public void processData(int reo, int mio) {}
    @Override
    public void processState() {}


    @Override
    public void waitSync() {
        toServiceResponse(TildaCommandTypes.WAIT_SYNC.getCommand());
    }
    @Override
    public void setFrequency(Frequency frequency) {
        toServiceResponse(new SetParamCommand<>(TildaCommandTypes.FREQ_SET, frequency));
    }
    @Override
    public void setForm(Form form) {
        toServiceResponse(new SetParamCommand<>(TildaCommandTypes.FORM_SET, form));
    }
    @Override
    public void setAmplitude(Amplitude amplitude) {
        toServiceResponse(new SetParamCommand<>(TildaCommandTypes.AMPL_SET, amplitude));
    }
    @Override
    public void setState(State state) {
        toServiceResponse(new SetParamCommand<>(TildaCommandTypes.STATE_SET, state));
    }
}
