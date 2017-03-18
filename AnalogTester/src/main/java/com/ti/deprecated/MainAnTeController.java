package com.ti.deprecated;

import com.ti.comm.AnTeControllable;
import com.ti.comm.AnTeEmulController;
import com.ti.command.AbstractCommand;
import com.ti.control.AnalogSignalManager;
import com.ti.SerialService;
import com.ti.TildaController;
import com.ti.TildaProtocol;
import com.ti.control.AnTeViewController;

import java.util.concurrent.*;

public class MainAnTeController implements AnTeControllable {
//    SerialService<AbstractCommand,AbstractCommand> service = new SerialService<>();
//    TildaProtocol protocol = new TildaProtocol();
//    TildaController controller = new AnTeTildaController();
    AnTeEmulController emulController =  new AnTeEmulController();

    ScheduledExecutorService executor;
    private boolean state = false;

    AnalogSignalManager manager = new AnalogSignalManager();
    AnTeViewController viewController;

    public MainAnTeController() {
//        service.setProtocol(protocol);
//        service.addController(controller);
//        service.addController(emulController);
//        ((AnTeTildaController)controller).setSignalManager(manager);
    }

    public void setViewController(AnTeViewController teViewController){
//        viewController = teViewController;
//        viewController.setDataQueueOne(manager.getReoQueue());
//        viewController.setDataQueueTwo(manager.getMioQueue());
    }

    public void runEmulation(){
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleWithFixedDelay(emulController, 0L, 10L, TimeUnit.MILLISECONDS);

    }

    @Override
    public void reopenPort(String name, int rate) {
        /*service.reopenPort(name,rate);*/
    }

    @Override
    public void changeEmulateState() {
        if(state){
            executor.shutdown();
            state = false;
        }else{
            executor = Executors.newSingleThreadScheduledExecutor();
//            executor.scheduleWithFixedDelay(emulController, 0L, 10L, TimeUnit.MILLISECONDS);
            state = true;
        }
    }
}
