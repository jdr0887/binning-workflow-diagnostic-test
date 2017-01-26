package org.renci.binning.diagnostic.test.commons;

import java.util.concurrent.Executors;

import org.renci.binning.core.BinningException;
import org.renci.binning.core.diagnostic.AbstractGenerateReportCallable;
import org.renci.binning.dao.BinningDAOBeanService;
import org.renci.binning.dao.BinningDAOException;
import org.renci.binning.dao.clinbin.model.DiagnosticBinningJob;
import org.renci.binning.dao.clinbin.model.Report;
import org.renci.binning.dao.jpa.BinningDAOManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateReportCallable extends AbstractGenerateReportCallable {

    private static final Logger logger = LoggerFactory.getLogger(GenerateReportCallable.class);

    public GenerateReportCallable(BinningDAOBeanService daoBean, DiagnosticBinningJob binningJob) {
        super(daoBean, binningJob);
    }

    @Override
    public Report call() throws BinningException {
        logger.info("ENTERING call()");
        return null;
    }

    public static void main(String[] args) {
        try {
            BinningDAOManager daoMgr = BinningDAOManager.getInstance();
            DiagnosticBinningJob binningJob = daoMgr.getDAOBean().getDiagnosticBinningJobDAO().findById(4218);
            GenerateReportCallable runnable = new GenerateReportCallable(daoMgr.getDAOBean(), binningJob);
            Executors.newSingleThreadExecutor().submit(runnable);
        } catch (BinningDAOException e) {
            e.printStackTrace();
        }
    }
}
