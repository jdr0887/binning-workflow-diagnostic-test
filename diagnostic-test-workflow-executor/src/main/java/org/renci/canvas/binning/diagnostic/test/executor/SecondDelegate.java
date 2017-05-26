package org.renci.canvas.binning.diagnostic.test.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondDelegate implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(SecondDelegate.class);

    public SecondDelegate() {
        super();
    }

    @Override
    public void execute(DelegateExecution arg0) {
        try {
            ExecutorService es = Executors.newFixedThreadPool(2);
            es.submit(() -> {
                for (int i = 0; i < 1000; i++) {
                    logger.info("i = {}", i);
                }
            });
            es.shutdown();
            if (!es.awaitTermination(5L, TimeUnit.MINUTES)) {
                es.shutdownNow();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
