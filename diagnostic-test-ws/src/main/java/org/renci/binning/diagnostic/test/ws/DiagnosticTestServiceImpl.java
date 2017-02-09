package org.renci.binning.diagnostic.test.ws;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.binning.core.BinningExecutorService;
import org.renci.binning.core.diagnostic.DiagnosticBinningJobInfo;
import org.renci.binning.diagnostic.test.executor.DiagnosticTestTask;
import org.renci.canvas.dao.CANVASDAOBeanService;
import org.renci.canvas.dao.CANVASDAOException;
import org.renci.canvas.dao.clinbin.model.DX;
import org.renci.canvas.dao.clinbin.model.DiagnosticBinningJob;
import org.renci.canvas.dao.clinbin.model.DiagnosticStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiagnosticTestServiceImpl implements DiagnosticTestService {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticTestServiceImpl.class);

    private CANVASDAOBeanService daoBeanService;

    private BinningExecutorService binningExecutorService;

    public DiagnosticTestServiceImpl() {
        super();
    }

    @Override
    public Response submit(DiagnosticBinningJobInfo info) {
        logger.debug("ENTERING submit(DiagnosticBinningJobInfo)");
        logger.info(info.toString());
        DiagnosticBinningJob binningJob = new DiagnosticBinningJob();
        try {
            binningJob.setStudy("TEST");
            binningJob.setGender(info.getGender());
            binningJob.setParticipant(info.getParticipant());
            binningJob.setListVersion(info.getListVersion());
            binningJob.setStatus(daoBeanService.getDiagnosticStatusTypeDAO().findById("Requested"));
            DX dx = daoBeanService.getDXDAO().findById(info.getDxId());
            binningJob.setDx(dx);
            List<DiagnosticBinningJob> foundBinningJobs = daoBeanService.getDiagnosticBinningJobDAO().findByExample(binningJob);
            if (CollectionUtils.isNotEmpty(foundBinningJobs)) {
                binningJob = foundBinningJobs.get(0);
            } else {
                binningJob.setId(daoBeanService.getDiagnosticBinningJobDAO().save(binningJob));
            }
            info.setId(binningJob.getId());
            logger.info(binningJob.toString());

            binningExecutorService.getExecutor().submit(new DiagnosticTestTask(binningJob.getId()));

        } catch (CANVASDAOException e) {
            logger.error(e.getMessage(), e);
        }
        return Response.ok(info).build();
    }

    @Override
    public DiagnosticStatusType status(Integer binningJobId) {
        logger.debug("ENTERING status(Integer)");
        try {
            DiagnosticBinningJob foundBinningJob = daoBeanService.getDiagnosticBinningJobDAO().findById(binningJobId);
            logger.info(foundBinningJob.toString());
            return foundBinningJob.getStatus();
        } catch (CANVASDAOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public BinningExecutorService getBinningExecutorService() {
        return binningExecutorService;
    }

    public void setBinningExecutorService(BinningExecutorService binningExecutorService) {
        this.binningExecutorService = binningExecutorService;
    }

    public CANVASDAOBeanService getDaoBeanService() {
        return daoBeanService;
    }

    public void setDaoBeanService(CANVASDAOBeanService daoBeanService) {
        this.daoBeanService = daoBeanService;
    }

}
