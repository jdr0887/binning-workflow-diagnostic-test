package org.renci.canvas.binning.diagnostic.test.commons;

import java.util.concurrent.Executors;

import org.renci.canvas.binning.core.BinningException;
import org.renci.canvas.binning.core.diagnostic.AbstractGenerateReportCallable;
import org.renci.canvas.dao.CANVASDAOBeanService;
import org.renci.canvas.dao.CANVASDAOException;
import org.renci.canvas.dao.clinbin.model.DiagnosticBinningJob;
import org.renci.canvas.dao.clinbin.model.Report;
import org.renci.canvas.dao.jpa.CANVASDAOManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateReportCallable extends AbstractGenerateReportCallable {

    private static final Logger logger = LoggerFactory.getLogger(GenerateReportCallable.class);

    public GenerateReportCallable(CANVASDAOBeanService daoBean, DiagnosticBinningJob binningJob) {
        super(daoBean, binningJob);
    }

    @Override
    public Report call() throws BinningException {
        logger.info("ENTERING call()");
        return null;
    }

    public static void main(String[] args) {
        try {
            CANVASDAOManager daoMgr = CANVASDAOManager.getInstance();
            DiagnosticBinningJob binningJob = daoMgr.getDAOBean().getDiagnosticBinningJobDAO().findById(4218);
            GenerateReportCallable runnable = new GenerateReportCallable(daoMgr.getDAOBean(), binningJob);
            Executors.newSingleThreadExecutor().submit(runnable);
        } catch (CANVASDAOException e) {
            e.printStackTrace();
        }
    }
}
