package org.renci.canvas.binning.diagnostic.test.executor;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstDelegate implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(FirstDelegate.class);

    public FirstDelegate() {
        super();
    }

    @Override
    public void execute(DelegateExecution arg0) throws Exception {
        logger.info("asyncExeuctorActive(): {}", arg0.getEngineServices().getProcessEngineConfiguration().isAsyncExecutorActivate());
        for (int i = 0; i < 10000; i++) {
            logger.info("i = {}", i);
        }
    }

}
