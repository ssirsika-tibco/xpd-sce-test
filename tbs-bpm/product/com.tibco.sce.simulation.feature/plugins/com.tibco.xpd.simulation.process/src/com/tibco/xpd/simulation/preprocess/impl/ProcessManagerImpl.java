/* 
 ** 
 **  MODULE:             $RCSfile: ProcessManagerImpl.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-18 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess.impl;

import java.net.URL;
import java.util.Iterator;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.Messages;
import com.tibco.xpd.simulation.model.WorkflowModel;
import com.tibco.xpd.simulation.preprocess.CaseGenerator;
import com.tibco.xpd.simulation.preprocess.FlowProcessor;
import com.tibco.xpd.simulation.preprocess.GeneratorContext;
import com.tibco.xpd.simulation.preprocess.ProcessManager;
import com.tibco.xpd.simulation.preprocess.SimDataGenerator;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

public class ProcessManagerImpl implements ProcessManager {

    private WorkflowModel workflowModel;

    public void generateSimData(Package xpdlPackage, EditingDomain ed) {
        try {
            SimulationXpdlUtils.setPreprocessorMode(true);
            CompoundCommand compoundCmd = new CompoundCommand(
                    Messages.ProcessManagerImpl_GenerateCommand, Messages.ProcessManagerImpl_GenerateCommandDesc);
            FlowProcessor flowProcessor = new FlowProcessorImpl();
            SimDataGenerator[] simGenerators = new SimDataGenerator[] {
                    new ConditionGeneratorImpl(),
                    new ApplicationGeneratorImpl() };
            for (Iterator iter = xpdlPackage.getProcesses().iterator(); iter
                    .hasNext();) {
                Process process = (Process) iter.next();
                GeneratorContext context = new GeneratorContext();
                context.init(ed, compoundCmd, process);
                // process WorkflowProcess
                //
                flowProcessor.processFlow(simGenerators, process, context);

                // process all ActivitySets
                //
                for (Iterator iterator = process.getActivitySets().iterator(); iterator
                        .hasNext();) {
                    flowProcessor.processFlow(simGenerators,
                            (ActivitySet) iterator.next(), context);
                }
            }
            if (compoundCmd.getCommandList().size() > 0) {
                if (compoundCmd.canExecute()) {
                    ed.getCommandStack().execute(compoundCmd);
                }
            }
        } finally {
            SimulationXpdlUtils.setPreprocessorMode(false);
        }
    }

    public CaseGenerator getCaseGenerator(Process process, int noOfCases) {
        return new CaseGeneratorImpl(process, noOfCases);
    }

    public WorkflowModel getWorkflowModel() {
        return workflowModel;
    }

    public WorkflowModel createWorkflowModel(URL repositoryLocation,
            String xpdlPath, Process xpdlProcess) {
        String modelName = xpdlProcess.getName();
        workflowModel = new WorkflowModel(null, modelName, true, true, //$NON-NLS-1$
                repositoryLocation, xpdlPath, xpdlProcess);
        return workflowModel;
    }

}
