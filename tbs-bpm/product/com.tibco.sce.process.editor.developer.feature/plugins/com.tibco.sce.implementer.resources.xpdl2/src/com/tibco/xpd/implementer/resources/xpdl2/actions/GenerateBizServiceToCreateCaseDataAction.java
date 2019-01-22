/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.processeditor.xpdl2.wizards.AddExtrasToNewProcessCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Action to generate business service that creates case data.
 * 
 * @author bharge
 * @since 23 Jan 2014
 */
public class GenerateBizServiceToCreateCaseDataAction extends
        AbstractCaseDataProcessGeneratorAction {

    private final String GENERATE_CREATE_CASE_DATA_PAGEFLOW_FRAGMENT_ID =
            "_create_case_data_bizservice_fragment_id"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractCaseDataProcessGeneratorAction#generateAndShowNewProcess(com.tibco.xpd.implementer.resources.xpdl2.wizards.pageflowgen.PageflowGenerationWizard)
     * 
     * @param pageflowGenWizard
     * @return
     */
    @Override
    protected List<Process> generateNewProcesses(Package newOrExistingXpdlPkg,
            CompoundCommand cmdToCreateProcess) {

        List<Process> newProcessList = new ArrayList<Process>();
        Xpdl2WorkingCopyImpl xpdl2WorkingCopy = null;

        if (null != newOrExistingXpdlPkg) {

            cmdToCreateProcess
                    .setLabel(Messages.Generate_CreateCaseBizService_Command_label);
            xpdl2WorkingCopy =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(newOrExistingXpdlPkg);
            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(newOrExistingXpdlPkg);

            /*
             * taking a snapshot of the original list just in case if the
             * original list gets reset for some reason
             */
            List<Class> caseClassList =
                    new ArrayList<Class>(caseClassSelectionList);
            for (Class caseClass : caseClassList) {

                CreateCaseDataProcessCreator createCaseDataProcCreator =
                        new CreateCaseDataProcessCreator(
                                BUSINESS_SERVICE_FRAGMENT_ROOT_CATEGORY_ID,
                                GENERATE_CREATE_CASE_DATA_PAGEFLOW_FRAGMENT_ID,
                                ProcessWidgetType.BUSINESS_SERVICE);

                createCaseDataProcCreator.setCaseClass(caseClass);
                String name = caseClass.getName();

                String defaultProcNameSuffix =
                        Messages.Generate_CreateCaseBizService_Default_Process_Name;

                Process newProcess =
                        GenerateProcessUtil
                                .createProcess(XpdModelType.PAGE_FLOW);

                /*
                 * set the process name and create pool and lane. add the
                 * process and pool to the package
                 */
                Command createProcessCommand =
                        createCaseDataProcCreator
                                .createProcess(xpdl2WorkingCopy,
                                        newOrExistingXpdlPkg,
                                        newProcess,
                                        name,
                                        defaultProcNameSuffix);

                if (null != createProcessCommand
                        && createProcessCommand.canExecute()) {

                    cmdToCreateProcess.append(createProcessCommand);
                    newProcessList.add(newProcess);
                }

            }

            if (cmdToCreateProcess.canExecute()) {

                editingDomain.getCommandStack().execute(cmdToCreateProcess);
            }

            if (null != xpdl2WorkingCopy
                    && xpdl2WorkingCopy
                            .getAttributes()
                            .containsKey(AddExtrasToNewProcessCommand.TEMPLATEDATA)) {

                xpdl2WorkingCopy.getAttributes()
                        .remove(AddExtrasToNewProcessCommand.TEMPLATEDATA);
            }

            try {
                if (xpdl2WorkingCopy.isWorkingCopyDirty()) {

                    xpdl2WorkingCopy.save();
                }
            } catch (IOException e) {
                LOG.error(e);
            }
        }
        return newProcessList;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractCaseDataProcessGeneratorAction#getWizardPageTitle()
     * 
     * @return
     */
    @Override
    protected String getWizardPageTitle() {

        return Messages.GeneratePageflow_createData_wizardPage_title;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractCaseDataProcessGeneratorAction#getProcessWidgetType()
     * 
     * @return
     */
    @Override
    protected ProcessWidgetType getProcessWidgetType() {
        return ProcessWidgetType.BUSINESS_SERVICE;
    }
}
