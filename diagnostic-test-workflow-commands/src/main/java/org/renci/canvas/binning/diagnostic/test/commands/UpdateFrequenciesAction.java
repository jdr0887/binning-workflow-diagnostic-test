package org.renci.canvas.binning.diagnostic.test.commands;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.renci.canvas.binning.diagnostic.test.commons.UpdateFrequenciesCallable;
import org.renci.canvas.dao.CANVASDAOBeanService;
import org.renci.canvas.dao.CANVASDAOException;
import org.renci.canvas.dao.clinbin.model.DiagnosticBinningJob;
import org.renci.canvas.dao.clinbin.model.MaxFrequency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "diagnostic-test", name = "update-frequencies", description = "Update Frequencies")
@Service
public class UpdateFrequenciesAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(UpdateFrequenciesAction.class);

    @Reference
    private CANVASDAOBeanService daoBeanService;

    @Option(name = "--binningJobId", description = "DiagnosticBinningJob Identifier", required = true, multiValued = false)
    private Integer binningJobId;

    public UpdateFrequenciesAction() {
        super();
    }

    @Override
    public Object execute() throws Exception {
        logger.debug("ENTERING execute()");

        DiagnosticBinningJob binningJob = daoBeanService.getDiagnosticBinningJobDAO().findById(binningJobId);
        logger.info(binningJob.toString());

        try {
            binningJob.setStatus(daoBeanService.getDiagnosticStatusTypeDAO().findById("Updating frequency table"));
            daoBeanService.getDiagnosticBinningJobDAO().save(binningJob);

            binningJob.setStatus(daoBeanService.getDiagnosticStatusTypeDAO().findById("Updated frequency table"));
            daoBeanService.getDiagnosticBinningJobDAO().save(binningJob);

        } catch (Exception e) {
            try {
                binningJob.setStop(new Date());
                binningJob.setFailureMessage(e.getMessage());
                binningJob.setStatus(daoBeanService.getDiagnosticStatusTypeDAO().findById("Failed"));
                daoBeanService.getDiagnosticBinningJobDAO().save(binningJob);
            } catch (CANVASDAOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

}
