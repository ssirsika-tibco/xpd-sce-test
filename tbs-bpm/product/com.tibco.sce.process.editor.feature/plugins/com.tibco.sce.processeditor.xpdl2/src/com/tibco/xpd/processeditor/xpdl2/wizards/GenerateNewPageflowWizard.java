/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.SectionWizardPage;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This wizard comes up when user right clicks on a user task and selects
 * Pageflow -> Generate from the popup menu. This basically comes with a wizard
 * page that lists the available user task interface data and other process
 * relevant data for selection in the first page and then goes thru the default
 * New Pageflow Process wizard pages
 * 
 * @author aallway
 * @since 3.2
 */
public class GenerateNewPageflowWizard extends NewPageflowProcessWizard {

    private static final String DATA_SELECTION_PAGE_ID =
            "generate.pageflow.dataSelection"; //$NON-NLS-1$

    private Activity userTaskActivity;

    private List<ProcessRelevantData> availableData;

    private List<ProcessRelevantData> associatedData;

    private SectionWizardPage dataSelectionPage;

    private GeneratePageflowDataSelectionSection dataSelectionSection;

    public GenerateNewPageflowWizard(Activity userTaskActivity) {
        super();
        this.userTaskActivity = userTaskActivity;

        availableData =
                ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(userTaskActivity);

        associatedData =
                ProcessInterfaceUtil
                        .getAssociatedProcessRelevantDataForActivity(userTaskActivity);

        /* Reset default name according to user task name. */
        Process process = getProcess();
        if (process != null) {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(userTaskActivity);
            Xpdl2ModelUtil
                    .setOtherAttribute(process, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), name);
            process.setName(NameUtil.getInternalName(name, false));

        }
    }

    @Override
    protected List<IWizardPage> finaliseWizardPageList(
            List<IWizardPage> contributedPages) {

        List<IWizardPage> newList = new ArrayList<IWizardPage>();

        if (dataSelectionPage == null) {
            dataSelectionSection =
                    new GeneratePageflowDataSelectionSection(availableData,
                            associatedData);

            dataSelectionPage =
                    new SectionWizardPage(DATA_SELECTION_PAGE_ID,
                            new AbstractXpdSection[] { dataSelectionSection });
            dataSelectionPage.setWizard(this);
            dataSelectionPage
                    .setTitle(Messages.NewPageflowDataSelectionPage_SelectParamsForPageflow_title);
            dataSelectionPage
                    .setMessage(Messages.NewPageflowDataSelectionPage_SelectParamsForPageflow_longdesc);
        }

        //
        // Insert data selection page after package selection page.
        for (IWizardPage page : contributedPages) {
            newList.add(page);

            if (page instanceof PackageSelectionPage) {
                newList.add(dataSelectionPage);
            }
        }

        return newList;
    }

    @Override
    protected Command createFinalAddProcessCommand(EditingDomain editingDomain,
            WorkingCopy containerWorkingCopy, Process pageflowProcess,
            Command createDefaultProcessCmd) {

        CompoundCommand cmd =
                new CompoundCommand(createDefaultProcessCmd.getLabel());
        cmd.append(super.createFinalAddProcessCommand(editingDomain,
                containerWorkingCopy,
                pageflowProcess,
                createDefaultProcessCmd));

        //
        // Duplicate the data selected by user as as pageflow formal parameters.
        //
        Map<String, AssociatedParameter> nameToAssocParamMap =
                PageflowUtil.getNameToAssociatedParameterMap(userTaskActivity);

        List<ProcessRelevantData> selectedData =
                dataSelectionSection.getUserSelectedData();

        List<AssociatedParameter> newAssociations =
                new ArrayList<AssociatedParameter>();

        for (ProcessRelevantData sourceData : selectedData) {
            AssociatedParameter assocParam =
                    nameToAssocParamMap.get(sourceData.getName());

            //
            // Duplicate the source data field / parameter as a pageflow formal
            // parameter.
            FormalParameter pageflowParameter =
                    PageflowUtil
                            .createPageflowParamForAssociatedData(assocParam,
                                    sourceData);

            // Add parameter
            pageflowProcess.getFormalParameters().add(pageflowParameter);

            // Create/re-create associated param.
            if (assocParam != null) {
                // Data already previously associated - Even so Copy existing
                // assocs just to be on safe side because
                // we'll regenerate the whole thing.
                newAssociations.add(EcoreUtil.copy(assocParam));

            } else {
                // Data Not previously associated, create assoc param to match.
                AssociatedParameter assocP =
                        createAssociatedParamFromPageflowParam(sourceData,
                                pageflowParameter);

                assocParam = assocP;
                newAssociations.add(assocParam);

            }

        } // Next selected data

        //
        // Synchronisation / validation is based on the user task having the
        // same associated interface data as the pageflow parameters.
        // Therefore we should set/reset the user task associated parameters to
        // the data set selected.
        // Simply recreate the whole thing (to save getting issues with
        // adding/removing from same list.
        AssociatedParameters assocParameters =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameters();
        /*
         * Sid XPD-2087. If there are no associated parameters to create then
         * there is no data for pageflow sop we should disable implicit data
         * association.
         */
        if (newAssociations.isEmpty()) {
            assocParameters.setDisableImplicitAssociation(true);
        } else {
            assocParameters.getAssociatedParameter().addAll(newAssociations);
        }

        cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                userTaskActivity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters(),
                assocParameters));

        //
        // Set user task to reference pageflow...
        // Have to do as a late exec command to ensure that process is already
        // added to package when we create reference, else
        AbstractLateExecuteCommand setFormImpl =
                new AbstractLateExecuteCommand(editingDomain, new Object[] {
                        pageflowProcess, userTaskActivity }) {
                    @Override
                    protected Command createCommand(
                            EditingDomain editingDomain, Object contextObject) {
                        Process pageflowProcess =
                                (Process) ((Object[]) contextObject)[0];
                        Activity userTaskActivity =
                                (Activity) ((Object[]) contextObject)[1];

                        return TaskObjectUtil
                                .getUserTaskSetFormImplementationCommand(editingDomain,
                                        userTaskActivity,
                                        FormImplementationType.PAGEFLOW,
                                        TaskObjectUtil
                                                .getUserTaskFormURIFromPageflowProcess(pageflowProcess));

                    }
                };
        cmd.append(setFormImpl);

        return cmd;
    }

    /**
     * Create an AssociatedParameter matching for the given sourceData and
     * pageflow parameter for a task referencing a pageflow.
     * 
     * @param sourceData
     * @param pageflowParameter
     * @return new associated parameter.
     */
    public AssociatedParameter createAssociatedParamFromPageflowParam(
            ProcessRelevantData sourceData, FormalParameter pageflowParameter) {
        AssociatedParameter assocP =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter();

        assocP.setFormalParam(sourceData.getName());
        assocP.setMandatory(pageflowParameter.isRequired());
        assocP.setMode(pageflowParameter.getMode());
        return assocP;
    }

}
