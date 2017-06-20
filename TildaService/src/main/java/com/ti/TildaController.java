package com.ti;

import com.ti.command.AbstractCommand;
import com.ti.command.DataCommand;
import com.ti.command.NakeDataCommand;
import com.ti.command.SetParamCommand;
import com.ti.command.param.Amplitude;
import com.ti.command.param.Form;
import com.ti.command.param.Frequency;
import com.ti.command.param.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Date;

public class TildaController extends AbstractSerialController implements TildaInterface{
    private static final Logger LOG = LogManager.getLogger(TildaController.class);
    private static final Logger NAKELOG = LogManager.getLogger();
    // TODO: 13.03.2017 судя по всему нет необходимости передавать во все протоколы, так как в методе
    // sendResponse происходит проброс RESPONSE<AbstractCommand> на все протоколы
    private TildaInterface mainController;
    private long dataCounter = 0;

    @Override
    public void serviceRequest(AbstractCommand command) {
        if(TildaCommandTypes.OK.equalCommand(command)){
            mainController.syncOk();
            System.out.println("Ok");
        }else if(TildaCommandTypes.NO.equalCommand(command)){
            mainController.no();
            System.out.println("NO");
        }else if(TildaCommandTypes.DATA.equalCommand(command)){
            int reo = ((DataCommand)command).getReo();
            int mio = ((DataCommand)command).getMio();
            mainController.processData(reo,mio);
            dataCounter++;
            if(dataCounter%10000 == 0){
                LOG.info("Data counter " + dataCounter + "  " + new Date().toString());
            }
            LOG.trace("DATA reo: "+ reo + " mio: " + mio);
//            System.out.println("DATA reo: "+ reo + " mio: " + mio);
        }else {
            command.debugPrint();
        }
    }
    @Override
    public void setMainController(TildaInterface controller){
        mainController = controller;
    }

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
