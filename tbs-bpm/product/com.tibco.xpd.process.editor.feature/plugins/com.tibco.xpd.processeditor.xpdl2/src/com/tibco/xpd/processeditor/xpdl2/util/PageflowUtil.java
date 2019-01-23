/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.wizards.GenerateNewPageflowWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.ReportAdditionalPageflowDataWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.SynchronisePageflowWizard;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.EqualityHelperXpd;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public final class PageflowUtil {

    /** Script context identifier for sub-processes. */
    public static final String PAGEFLOW_CONTEXT = "Pageflow"; //$NON-NLS-1$

    public static boolean logToConsole = false;

    /**
     * Private constructor.
     */
    private PageflowUtil() {
    }

    /**
     * @param activity
     *            The User Task activity.
     * @param direction
     *            The mapping direction.
     * @return A list of formal parameters for the referenced Pageflow process.
     */
    public static List<FormalParameter> getPageflowFormalParams(
            Activity activity, MappingDirection direction) {
        List<FormalParameter> parameters = new ArrayList<FormalParameter>();

        Process pageflowProcess =
                TaskObjectUtil.getUserTaskPageflowProcess(activity);
        if (pageflowProcess != null) {
            if (Xpdl2ModelUtil.isPageflow(pageflowProcess)) {
                parameters =
                        SubProcUtil.getProcessParameters(pageflowProcess,
                                direction);
            }
        }
        return parameters;
    }

    /**
     * TODO Should read categories from all packages in this project as well as
     * traversing project dependencies. (Indexer?)
     * 
     * @param process
     * @return
     */
    public static List<String> getBusinessCategories(Process process) {
        // Set<Process> processes = new HashSet<Process>();
        List<String> categories = new ArrayList<String>();
        // processes.addAll(process.getPackage().getProcesses());
        // for (Process current : processes) {
        // String category =
        // (String) Xpdl2ModelUtil.getOtherAttribute(current,
        // XpdExtensionPackage.eINSTANCE
        // .getDocumentRoot_BusinessServiceCategory());
        // if (category != null && category.length() != 0) {
        // categories.add(category);
        // }
        // }

        Set<String> projects = new HashSet<String>();
        IProject project = WorkingCopyUtil.getProjectFor(process);
        addProject(projects, project);

        categories = getAllCategories(projects);

        return categories;
    }

    /**
     * Bring up a wizard that to show what synch is to be done and then perform
     * a complete syncrhoisation of the pageflow parameters as compared with the
     * user task interface data.
     * 
     * @param userTaskActivity
     */
    public static void synchronizePageflowParameters(Activity userTaskActivity) {

        Process pageflowProcess =
                TaskObjectUtil.getUserTaskPageflowProcess(userTaskActivity);
        if (pageflowProcess != null) {

            IFile pageflowFile = WorkingCopyUtil.getFile(pageflowProcess);
            WorkingCopy pageflowWCToSave = null;

            IFile businessFile = WorkingCopyUtil.getFile(userTaskActivity);
            if (businessFile != null && pageflowFile != null) {

                /*
                 * If the pageflow is in a different package then set the
                 * pageflowWCToSave to indicate to ExecuteAndSaveCommand that a
                 * save is required after executin the save
                 */
                if (!businessFile.equals(pageflowFile)) {
                    pageflowWCToSave =
                            WorkingCopyUtil.getWorkingCopy(pageflowFile);

                    if (pageflowWCToSave != null) {
                        if (pageflowWCToSave.isWorkingCopyDirty()) {
                            if (!MessageDialog
                                    .openConfirm(Display.getCurrent()
                                            .getActiveShell(),
                                            Messages.PageflowUtil_Save_title,
                                            Messages.PageflowUtil_MustSavePageflowPackageForFix_longdesc)) {
                                return;
                            }

                            try {
                                pageflowWCToSave.save();
                            } catch (IOException e) {
                                throw new RuntimeException(
                                        "Failed saving pageflow resource", e); //$NON-NLS-1$
                            }
                        }
                    }
                }
            }

            List<FormalParameter> paramsToAdd =
                    new ArrayList<FormalParameter>();
            List<FormalParameter> paramsToRemove =
                    new ArrayList<FormalParameter>();
            List<FormalParameter> paramsToModify =
                    new ArrayList<FormalParameter>();

            compareUserTaskInterfaceWithPageflowInterface(userTaskActivity,
                    pageflowProcess,
                    paramsToAdd,
                    paramsToRemove,
                    paramsToModify);

            Shell activeShell = Display.getCurrent().getActiveShell();

            SynchronisePageflowWizard synchWizard =
                    new SynchronisePageflowWizard(userTaskActivity,
                            pageflowProcess, paramsToAdd, paramsToModify,
                            paramsToRemove);

            WizardDialog wizDig = new WizardDialog(activeShell, synchWizard);
            if (wizDig.open() == WizardDialog.OK) {
                logToConsole("==> " + PageflowUtil.class.getSimpleName() + ".synchronizePageflowParameters()"); //$NON-NLS-1$ //$NON-NLS-2$

                // Ok lets do it.
                EditingDomain editingDomain =
                        WorkingCopyUtil.getEditingDomain(pageflowProcess);
                LateExecuteCompoundCommand cmd =
                        new ExecuteAndSaveCommand(pageflowWCToSave);
                cmd.setLabel(Messages.PageflowUtil_SynchPageflowCOmmand_menu);

                // We may need to modify pageflow start event associated params.
                // For this we will need to locate them and cash up other
                // relevant info to help create / modify them.
                AssociatedParameters pageflowStartAssoc = null;

                // Start event associations (if any)
                Map<String, AssociatedParameter> pageflowStartEventAssocParamMap =
                        Collections.emptyMap();

                Activity pageflowStart =
                        SubProcUtil.getSubProcessStartEvent(pageflowProcess);

                if (pageflowStart != null) {
                    pageflowStartAssoc =
                            (AssociatedParameters) Xpdl2ModelUtil
                                    .getOtherElement(pageflowStart,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters());
                    pageflowStartEventAssocParamMap =
                            getNameToAssociatedParameterMap(pageflowStart);
                }

                List existingPageflowParams =
                        ProcessInterfaceUtil
                                .getAllFormalParameters(pageflowProcess);

                // Map of data name to user Task interface assoc param (if any)
                Map<String, AssociatedParameter> userTaskAssocParamMap =
                        getNameToAssociatedParameterMap(userTaskActivity);

                // Map of source activitry dat aname to data.
                Map<String, ProcessRelevantData> sourceDataMap =
                        ProcessDataUtil
                                .getDataMap(ProcessInterfaceUtil
                                        .getAllAvailableRelevantDataForActivity(userTaskActivity));

                // Remove pageflow params that user task interface does not
                // contain anymore.
                for (FormalParameter pageflowParam : paramsToRemove) {
                    // Remove references to param in pageflow first.
                    logToConsole("     Removing pageflow parameter: " + pageflowParam.getName()); //$NON-NLS-1$
                    Command refCmd =
                            ProcessDataUtil
                                    .getRemoveProcessDataReferenceCommand(editingDomain,
                                            pageflowParam,
                                            pageflowProcess);
                    if (refCmd != null) {
                        logToConsole("          And referecens to it."); //$NON-NLS-1$
                        cmd.append(refCmd);

                    }

                    // Remove the param itself.
                    cmd.append(RemoveCommand.create(editingDomain,
                            pageflowParam));
                }

                //
                // Add any parameters that are now in user task interface but
                // not in pageflow.
                for (FormalParameter pageflowParam : paramsToAdd) {
                    // Note that the paramsToAdd contains NEW params created for
                    // us by compareUserTaskInterfaceWithPageflowInterface() OR
                    // existing params in the case where they exist in pageflow
                    // but are not associated (but others are).
                    if (existingPageflowParams.contains(pageflowParam)) {
                        // associations element should exist if we get here
                        // (Because param exists but was explicitly
                        // disassociated), so don't need to create it or
                        // anything.
                        if (pageflowStartAssoc != null) {
                            logToConsole("     Adding existing pageflow parameter to start event associations: " + pageflowParam.getName()); //$NON-NLS-1$
                            AssociatedParameter newAssoc =
                                    createMatchingPageflowAssocParam(userTaskAssocParamMap
                                            .get(pageflowParam.getName()),
                                            sourceDataMap.get(pageflowParam
                                                    .getName()),
                                            pageflowParam.getName());
                            cmd.append(AddCommand
                                    .create(editingDomain,
                                            pageflowStartAssoc,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParameters_AssociatedParameter(),
                                            newAssoc));

                            // There is also a possibility that the existing
                            // unassociated parameter also has wrong type/mode,
                            // so recreate that as well.
                            AssociatedParameter assocParam =
                                    userTaskAssocParamMap.get(pageflowParam
                                            .getName());
                            ProcessRelevantData sourceData =
                                    sourceDataMap.get(pageflowParam.getName());

                            FormalParameter replacementParam =
                                    createPageflowParamForAssociatedData(assocParam,
                                            sourceData);
                            replacementParam.eSet(Xpdl2Package.eINSTANCE
                                    .getUniqueIdElement_Id(), pageflowParam
                                    .getId());

                            cmd.append(RemoveCommand.create(editingDomain,
                                    pageflowParam));
                            cmd.append(AddCommand
                                    .create(editingDomain,
                                            pageflowProcess,
                                            Xpdl2Package.eINSTANCE
                                                    .getFormalParametersContainer_FormalParameters(),
                                            replacementParam));
                        }

                    } else {
                        // No current pageflow parameter to match the user task
                        // data. So add it to the pageflow process.
                        logToConsole("     Creating pageflow parameter: " + pageflowParam.getName()); //$NON-NLS-1$

                        cmd.append(AddCommand
                                .create(editingDomain,
                                        pageflowProcess,
                                        Xpdl2Package.eINSTANCE
                                                .getFormalParametersContainer_FormalParameters(),
                                        pageflowParam));

                        // If there are explicit pageflow start event data
                        // associations then add this new param to them.
                        if (pageflowStartAssoc != null) {
                            logToConsole("          And adding to start event associations"); //$NON-NLS-1$
                            AssociatedParameter newAssoc =
                                    createMatchingPageflowAssocParam(userTaskAssocParamMap
                                            .get(pageflowParam.getName()),
                                            sourceDataMap.get(pageflowParam
                                                    .getName()),
                                            pageflowParam.getName());
                            cmd.append(AddCommand
                                    .create(editingDomain,
                                            pageflowStartAssoc,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParameters_AssociatedParameter(),
                                            newAssoc));
                        }
                    }
                }

                //
                // Modify type info for params that need to be modified.
                for (FormalParameter pageflowParam : paramsToModify) {
                    // The easiest thin to do will be to create a new copy of
                    // the original param (including id) and replace original.
                    AssociatedParameter assocParam =
                            userTaskAssocParamMap.get(pageflowParam.getName());
                    ProcessRelevantData sourceData =
                            sourceDataMap.get(pageflowParam.getName());

                    FormalParameter replacementParam =
                            createPageflowParamForAssociatedData(assocParam,
                                    sourceData);
                    replacementParam.eSet(Xpdl2Package.eINSTANCE
                            .getUniqueIdElement_Id(), pageflowParam.getId());

                    cmd.append(RemoveCommand.create(editingDomain,
                            pageflowParam));
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    pageflowProcess,
                                    Xpdl2Package.eINSTANCE
                                            .getFormalParametersContainer_FormalParameters(),
                                    replacementParam));

                    // If the Pageflow start event has associated params replace
                    // the associated param too.
                    if (pageflowStartAssoc != null) {
                        logToConsole("     Replacing pageflow parameter: " + pageflowParam.getName()); //$NON-NLS-1$

                        AssociatedParameter replacementAssoc =
                                createMatchingPageflowAssocParam(userTaskAssocParamMap
                                        .get(pageflowParam.getName()),
                                        sourceDataMap.get(pageflowParam
                                                .getName()),
                                        pageflowParam.getName());

                        AssociatedParameter existingAssoc =
                                pageflowStartEventAssocParamMap
                                        .get(pageflowParam.getName());
                        if (existingAssoc != null) {
                            logToConsole("          And it's start event association"); //$NON-NLS-1$
                            cmd.append(RemoveCommand.create(editingDomain,
                                    existingAssoc));
                        }

                        cmd.append(AddCommand
                                .create(editingDomain,
                                        pageflowStartAssoc,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAssociatedParameters_AssociatedParameter(),
                                        replacementAssoc));
                    }

                }

                // THAT'S IT!

                // Execute the command
                if (!cmd.isEmpty() && cmd.canExecute()) {
                    editingDomain.getCommandStack().execute(cmd);
                    logToConsole("<== " + PageflowUtil.class.getSimpleName() + ".synchronizePageflowParameters() - SUCCESS"); //$NON-NLS-1$ //$NON-NLS-2$

                } else {
                    logToConsole("<== " + PageflowUtil.class.getSimpleName() + ".synchronizePageflowParameters() - BAD COMMAND"); //$NON-NLS-1$ //$NON-NLS-2$

                }
            }

        }

        return;
    }

    /**
     * Adds a new pageflow start event associated parameter for the given
     * pageflowParam extracting info from the various passed sources in order to
     * ascertain the correct mandatory and mode values.
     * 
     * @param userTaskAssocParamMap
     * @param sourceDataMap
     * @param pageflowParam
     */
    public static AssociatedParameter createMatchingPageflowAssocParam(
            AssociatedParameter userTaskAssoc, ProcessRelevantData sourceData,
            String dataName) {
        AssociatedParameter newAssoc =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter();
        newAssoc.setFormalParam(dataName);

        newAssoc.setMandatory(getExplicitOrImplicitParamMandatory(userTaskAssoc,
                sourceData));
        newAssoc.setMode(getExplicitOrImplicitParamMode(userTaskAssoc,
                sourceData));

        return newAssoc;
    }

    /**
     * Compare the interface of the given user task (i.e. the associated data)
     * wiuth the interface of the pageflow process (i.e. the pageflow parameters
     * explcitly or implicitly associated with the pageflow start event.
     * <p>
     * Passed lists are populated with new parameters to add / modify / remove
     * 
     * @param userTaskActivity
     * @param pageflowProcess
     * @param pageflowParamsToAdd
     *            Pageflow formal parameters for data associated with user task
     *            that are missing from pageflow (in which case the param is a
     *            newly created one) or exist but are explicitly unassociated
     *            with pageflow start event (in which case the EXISTING param is
     *            returned).
     * @param paegflowParamsToRemove
     *            Pageflow parameters for which there is no longer data
     *            associated to the user task.
     * @param pageflowParamsToModify
     *            pageflow parameters that are not the same type / mode /
     *            mandatory in the pageflow and user task associated data.
     * 
     * @return true if the interface match else false.
     */
    public static boolean compareUserTaskInterfaceWithPageflowInterface(
            Activity userTaskActivity, Process pageflowProcess,
            List<FormalParameter> pageflowParamsToAdd,
            List<FormalParameter> paegflowParamsToRemove,
            List<FormalParameter> pageflowParamsToModify) {

        logToConsole("==> " + PageflowUtil.class.getSimpleName() + ".compareUserTaskInterfaceWithPageflowInterface()"); //$NON-NLS-1$ //$NON-NLS-2$

        EqualityHelperXpd eEqualityHelper = new EqualityHelperXpd();

        //
        // Get pageflow parameters (explciitly or implicitly associated with
        // start event.
        List<FormalParameter> pageFlowParams =
                SubProcUtil.getProcessParameters(pageflowProcess, null);

        // Add all pageflow start-associated params to remove list - we will
        // remove params from this list for each user task data we find with
        // same name.
        paegflowParamsToRemove.addAll(pageFlowParams);

        // Map existing parameter names
        Map<String, FormalParameter> existingAssociatedPageflowParams =
                new HashMap<String, FormalParameter>();
        for (FormalParameter formal : pageFlowParams) {
            existingAssociatedPageflowParams.put(formal.getName(), formal);
        }

        //
        // Get the parameters that should be present (i.e. the data
        // explicitly or implicitly associated with the user task).

        List<ProcessRelevantData> userTaskAssociatedData =
                ProcessInterfaceUtil
                        .getAssociatedProcessRelevantDataForActivity(userTaskActivity);

        Map<String, AssociatedParameter> nameToAssocParamMap =
                getNameToAssociatedParameterMap(userTaskActivity);

        // We may need to modify pageflow start event associated params.
        // For this we will need to locate them and cash up other
        // relevant info to help create / modify them.
        Activity pageflowStart =
                SubProcUtil.getSubProcessStartEvent(pageflowProcess);

        // Start event associations (if any)
        AssociatedParameters pageflowStartAssoc = null;
        Map<String, AssociatedParameter> pageflowStartEventAssocParamMap =
                Collections.emptyMap();

        if (pageflowStart != null) {
            pageflowStartAssoc =
                    (AssociatedParameters) Xpdl2ModelUtil
                            .getOtherElement(pageflowStart,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedParameters());
            pageflowStartEventAssocParamMap =
                    getNameToAssociatedParameterMap(pageflowStart);
        }

        // Go thru the data associated (expl or impl) checking for any
        // missing data in pageflow.
        for (ProcessRelevantData userTaskData : userTaskAssociatedData) {

            AssociatedParameter assocParam =
                    nameToAssocParamMap.get(userTaskData.getName());

            FormalParameter pageflowParam =
                    existingAssociatedPageflowParams
                            .get(userTaskData.getName());

            AssociatedParameter pageflowAssocParam =
                    pageflowStartEventAssocParamMap.get(userTaskData.getName());

            if (pageflowParam == null) {
                // No such pageflow param create one to match the associated
                // parameter
                //
                // There may actually be an existing parameter that is
                // explicitly unassociated (becuase other ARE explicitly
                // associated) - if so return the original.

                EObject p =
                        EMFSearchUtil
                                .findInList(pageflowProcess
                                        .getFormalParameters(),
                                        Xpdl2Package.eINSTANCE
                                                .getNamedElement_Name(),
                                        userTaskData.getName());
                if (p instanceof FormalParameter) {
                    logToConsole("     Pageflow Parameter missing from start event associations: " + userTaskData.getName()); //$NON-NLS-1$
                    pageflowParamsToAdd.add((FormalParameter) p);
                } else {
                    // Doesn't exist at all so create a copy.
                    logToConsole("     Parameter missing from pageflow: " + userTaskData.getName()); //$NON-NLS-1$
                    pageflowParamsToAdd
                            .add(createPageflowParamForAssociatedData(assocParam,
                                    userTaskData));
                }

            } else {
                // Parameter exists - remove from the remove params list
                // check type etc
                paegflowParamsToRemove.remove(pageflowParam);

                boolean same = false;

                ModeType sourceDataMode =
                        getExplicitOrImplicitParamMode(assocParam, userTaskData);
                boolean sourceDataMandatory =
                        getExplicitOrImplicitParamMandatory(assocParam,
                                userTaskData);

                ModeType pageflowParamMode =
                        getExplicitOrImplicitParamMode(pageflowAssocParam,
                                pageflowParam);
                boolean pageflowParamMandatory =
                        getExplicitOrImplicitParamMandatory(pageflowAssocParam,
                                pageflowParam);

                if (sourceDataMode.equals(pageflowParamMode)) {
                    if (sourceDataMandatory == pageflowParamMandatory) {
                        if (userTaskData.isReadOnly() == pageflowParam
                                .isReadOnly()) {
                            if (userTaskData.isIsArray() == pageflowParam
                                    .isIsArray()) {
                                if (eEqualityHelper.equals(userTaskData
                                        .getDataType(), pageflowParam
                                        .getDataType())) {
                                    same = true;
                                } else {
                                    logToConsole("     Pageflow Parameter different dataType: " + pageflowParam.getName()); //$NON-NLS-1$
                                }
                            } else {
                                logToConsole("     Pageflow Parameter different isArray: " + pageflowParam.getName()); //$NON-NLS-1$
                            }
                        } else {
                            logToConsole("     Pageflow Parameter different read-only: " + pageflowParam.getName()); //$NON-NLS-1$
                        }
                    } else {
                        logToConsole("     Pageflow Parameter different mandatory: " + pageflowParam.getName()); //$NON-NLS-1$
                    }
                } else {
                    logToConsole("     Pageflow Parameter different mode: " + pageflowParam.getName()); //$NON-NLS-1$
                }

                if (!same) {
                    pageflowParamsToModify.add(pageflowParam);
                }
            }
        }

        if (logToConsole) {
            for (FormalParameter p : paegflowParamsToRemove) {
                logToConsole("     Extra unrequired Pageflow Parameter: " + p.getName()); //$NON-NLS-1$
            }
        }

        if (!pageflowParamsToAdd.isEmpty() || !pageflowParamsToModify.isEmpty()
                || !paegflowParamsToRemove.isEmpty()) {
            logToConsole("<== " + PageflowUtil.class.getSimpleName() + ".compareUserTaskInterfaceWithPageflowInterface() - User task Interface and PageflowInterface DO NOT Match"); //$NON-NLS-1$ //$NON-NLS-2$
            return false;
        }

        logToConsole("<== " + PageflowUtil.class.getSimpleName() + ".compareUserTaskInterfaceWithPageflowInterface() - User task Interface and PageflowInterface Match"); //$NON-NLS-1$ //$NON-NLS-2$
        return true;
    }

    /**
     * @param projects
     *            A list of all referenced projects.
     * @param project
     *            The project to add.
     */
    private static void addProject(Set<String> projects, IProject project) {
        if (project != null) {
            projects.add(project.getName());
            try {
                for (IProject referenced : project.getReferencedProjects()) {
                    String name = referenced.getName();
                    if (!projects.contains(name)) {
                        addProject(projects, referenced);
                    }
                }
            } catch (CoreException e) {
            }
        }
    }

    private static List<String> getAllCategories(Set<String> projects) {
        List<String> elements = new ArrayList<String>();
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        elements = getAllCategories(projects, criteria);
        return elements;
    }

    private static List<String> getAllCategories(Set<String> projects,
            IndexerItem criteria) {
        List<String> elements = new ArrayList<String>();
        // Get the index items from the indexer
        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                criteria);
        if (result != null && !result.isEmpty()) {
            // Get the eObjects for the indexer items
            for (IndexerItem item : result) {
                if (item != null) {
                    String projectName =
                            item.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);
                    if (projects.contains(projectName)) {

                        String category =
                                item.get(Xpdl2ResourcesPlugin.ATTRIBUTE_CATEGORY);
                        if (category != null && category.length() != 0) {
                            elements.add(category);
                        }
                    }
                }
            }
        }
        return elements;
    }

    /**
     * Bring up a wizard that allows a new pageflow process to be generated for
     * the given task (taking the task associated parameters into account etc.
     * 
     * @param userTaskActivity
     * @return Newly created process or null if user cancelled.
     */
    public static Process generatePageFlowForUserTask(Activity userTaskActivity) {
        //
        // Create the dialog....
        //
        Package pckg = Xpdl2ModelUtil.getPackage(userTaskActivity);

        IWorkbench workbench = PlatformUI.getWorkbench();
        Shell shell = workbench.getActiveWorkbenchWindow().getShell();

        //
        // Tell the wizard about available / associated data.
        NewPageflowProcessWizard wizard =
                new GenerateNewPageflowWizard(userTaskActivity);

        IStructuredSelection selection = new StructuredSelection(pckg);

        wizard.init(workbench, selection);

        //
        // Run the wizard.
        // This takes complete control of building / executing the command.
        WizardDialog dialog = new WizardDialog(shell, wizard);
        if (dialog.open() == WizardDialog.OK) {
            return wizard.getProcess();
        }

        return null;
    }

    /**
     * @return map of data name -> associated parameter for given activity.
     */
    public static Map<String, AssociatedParameter> getNameToAssociatedParameterMap(
            Activity activity) {
        Map<String, AssociatedParameter> nameToAssocParamMap =
                new HashMap<String, AssociatedParameter>();

        List<AssociatedParameter> assocParams =
                ProcessInterfaceUtil.getActivityAssociatedParameters(activity);
        for (AssociatedParameter p : assocParams) {
            nameToAssocParamMap.put(p.getFormalParam(), p);
        }
        return nameToAssocParamMap;
    }

    /**
     * Create a new pageflow parameter to match the given associated parameter
     * and its source process data
     * 
     * @param userTaskAssocParam
     *            The associated parameter OR null if the sourceData is
     *            implicitly associated.
     * @param userTaskData
     *            The source process data field / param for the association
     *            (must always be provided).
     * @return new formal parameter matching teh criteria in given association.
     */
    public static FormalParameter createPageflowParamForAssociatedData(
            AssociatedParameter userTaskAssocParam,
            ProcessRelevantData userTaskData) {
        FormalParameter pageflowParameter =
                Xpdl2Factory.eINSTANCE.createFormalParameter();

        // Name
        String name = userTaskData.getName();
        pageflowParameter.setName(name);

        // Display name
        String display = Xpdl2ModelUtil.getDisplayName(userTaskData);
        if (display != null) {
            Xpdl2ModelUtil
                    .setOtherAttribute(pageflowParameter,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            display);
        }

        // Data type
        pageflowParameter.setDataType(userTaskData.getDataType() == null ? null
                : (DataType) EcoreUtil.copy(userTaskData.getDataType()));

        // Is array
        pageflowParameter.setIsArray(userTaskData.isIsArray());

        // Length
        pageflowParameter.setLength(userTaskData.getLength() == null ? null
                : (Length) EcoreUtil.copy(userTaskData.getLength()));

        // InOut mode.
        pageflowParameter
                .setMode(getExplicitOrImplicitParamMode(userTaskAssocParam,
                        userTaskData));

        // Mandatory flag
        pageflowParameter
                .setRequired(getExplicitOrImplicitParamMandatory(userTaskAssocParam,
                        userTaskData));

        // Read only flag
        pageflowParameter.setReadOnly(userTaskData.isReadOnly());

        return pageflowParameter;
    }

    /**
     * InOut Mode - Take from user task associated param if available. Otherwise
     * (use selected data that wasn't originally associated), if it's a formal
     * parameter take mode from that other wise default fields to IN/OUT.
     * 
     * @param assocParam
     * @param sourceData
     * @return in out mode.
     */
    public static ModeType getExplicitOrImplicitParamMode(
            AssociatedParameter assocParam, ProcessRelevantData sourceData) {
        ModeType mode;
        if (assocParam != null && assocParam.getMode() != null) {
            mode = assocParam.getMode();
        } else if (sourceData instanceof FormalParameter) {
            mode = ((FormalParameter) sourceData).getMode();
        } else {
            mode = ModeType.INOUT_LITERAL;
        }
        return mode;
    }

    /**
     * take from asssociated parameter if there otherwise use formal param
     * otherwise set as optional by default. Note that Mandatory in the case of
     * pageflow parameters means the same as for Forms (i.e. the data MUST have
     * a value before the pageflow/form can be submitted.
     * 
     * @param assocParam
     * @param sourceData
     * @return mandatory flag
     */
    public static boolean getExplicitOrImplicitParamMandatory(
            AssociatedParameter assocParam, ProcessRelevantData sourceData) {
        boolean mandatory;

        if (assocParam != null) {
            mandatory = assocParam.isMandatory();
        } else if (sourceData instanceof FormalParameter) {
            mandatory = ((FormalParameter) sourceData).isRequired();
        } else {
            mandatory = false;
        }
        return mandatory;
    }

    /**
     * Create a new data field to match the given associated parameter from a
     * pageflow process's start event.
     * 
     * @param pageflowParam
     *            The source process data field / param for the association
     *            (must always be provided).
     * 
     * @return new formal parameter matching teh criteria in given association.
     */
    public static DataField createDataForPageflowAssociatedData(
            ProcessRelevantData pageflowParam) {
        DataField dataField = Xpdl2Factory.eINSTANCE.createDataField();

        // Name
        String name = pageflowParam.getName();
        dataField.setName(name);

        // Display name
        String display = Xpdl2ModelUtil.getDisplayName(pageflowParam);
        if (display != null) {
            Xpdl2ModelUtil
                    .setOtherAttribute(dataField, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), display);
        }

        // Data type
        dataField.setDataType(pageflowParam.getDataType() == null ? null
                : (DataType) EcoreUtil.copy(pageflowParam.getDataType()));

        // Is array
        dataField.setIsArray(pageflowParam.isIsArray());

        // Length
        dataField.setLength(pageflowParam.getLength() == null ? null
                : (Length) EcoreUtil.copy(pageflowParam.getLength()));

        // Read only flag
        dataField.setReadOnly(pageflowParam.isReadOnly());

        return dataField;
    }

    /**
     * Creates a new user task associated parameter for the given data
     * extracting info from the various passed sources in order to ascertain the
     * correct mandatory and mode values.
     * 
     * @param pageflowStartAssocParamMap
     * @param sourceDataMap
     * @param userTaskData
     * @param cmd
     */
    public static AssociatedParameter createMatchingUserTaskAssocParam(
            AssociatedParameter pageflowStartEventAssoc,
            ProcessRelevantData pageflowParam, String dataName) {

        AssociatedParameter newAssoc =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter();
        newAssoc.setFormalParam(dataName);

        newAssoc.setMandatory(getExplicitOrImplicitParamMandatory(pageflowStartEventAssoc,
                pageflowParam));
        newAssoc.setMode(getExplicitOrImplicitParamMode(pageflowStartEventAssoc,
                pageflowParam));

        return newAssoc;
    }

    /**
     * Get command that ANY explicit / implicit pageflow start parameters that
     * have no equivalent (by name) in target process will have data fields
     * created for them and the user task associaitons are set to match the
     * pageflow interface. This is in order that the best efforts are made to
     * ensure that the user task interface and pageflow interface match on
     * initial creation of reference.
     * 
     * @param editingDomain
     * @param userTaskActivity
     * @param pageflowProcess
     * @param warnUser
     *            Popup dialog to warn of additional data ?
     * 
     * @return Command to add data fields and set the user task associations to
     *         match the given pageflow process interface or NULL if user
     *         cancels operation via warn add extra data dialog.
     */
    public static Command getCreateUserTaskDataForPageflowCommand(
            EditingDomain editingDomain, Activity userTaskActivity,
            Process taregetProcess, Process pageflowProcess, boolean warnUser) {
        LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();

        //
        // Create a command to add any data fields to the target process
        // and associate all the necessary data with correct modes etc

        // Local data name to data map.
        Map<String, ProcessRelevantData> localDataMap =
                ProcessDataUtil.getDataMap(ProcessInterfaceUtil
                        .getAllProcessRelevantData(taregetProcess));

        // Pageflow explicitly or implicitly associated parameters
        AssociatedParameters pageflowStartAssoc = null;

        // Start event associations (if any)
        Map<String, AssociatedParameter> pageflowStartEventAssocParamMap =
                Collections.emptyMap();

        Activity pageflowStart =
                SubProcUtil.getSubProcessStartEvent(pageflowProcess);

        if (pageflowStart != null) {
            pageflowStartEventAssocParamMap =
                    PageflowUtil.getNameToAssociatedParameterMap(pageflowStart);
        }

        List pageflowParams =
                ProcessInterfaceUtil.getAllFormalParameters(pageflowProcess);
        Map<String, ProcessRelevantData> pageflowParamMap =
                ProcessDataUtil.getDataMap(pageflowParams);

        //
        // Creaty list of associated pageflow parameters.
        List<FormalParameter> assocPageflowParams =
                new ArrayList<FormalParameter>();

        if (!pageflowStartEventAssocParamMap.isEmpty()) {
            //
            // Create new fields and associations from Explicit pageflow
            // start event associations
            for (AssociatedParameter assocParam : pageflowStartEventAssocParamMap
                    .values()) {
                ProcessRelevantData pageflowParam =
                        pageflowParamMap.get(assocParam.getFormalParam());
                if (pageflowParam instanceof FormalParameter) {
                    assocPageflowParams.add((FormalParameter) pageflowParam);
                }
            }

        } else {
            //
            // Create new fields and associations from Implicit pageflow
            // start event associations
            /*
             * Sid XPD-2087: Only use all data implicitly if implicit
             * association has not been disabled.
             */
            if (!ProcessInterfaceUtil
                    .isImplicitAssociationDisabled(pageflowStart)) {
                for (ProcessRelevantData pageflowParam : pageflowParamMap
                        .values()) {
                    if (pageflowParam instanceof FormalParameter) {
                        assocPageflowParams
                                .add((FormalParameter) pageflowParam);
                    }
                }
            }
        }

        //
        // Create data fields for any asosc pageflow param that doesn't
        // already exist in target user task proess (match by name
        // only).

        // We Always just overwrite the user task associations with new
        // info - to ensure it is perfect according to pageflow
        // interface
        List<ProcessRelevantData> additionalData =
                new ArrayList<ProcessRelevantData>();

        AssociatedParameters newAssociatedParameters =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameters();

        /*
         * Sid XPD-2739 - Used to set the disableImplicitAssociation flag if
         * there were no pageflow parameters - but this could cause confusion
         * later when the user selected a differnet user task option (generate
         * form / pageflow becuase they would not realise that this flag had
         * been set. Now pageflow parms is very edge case and chances are it
         * mean sthat the user simply hasn't dealt with the pageflow param
         * creation yet.
         * 
         * So now the user will have to synch the pageflow params to the user
         * task interface.
         */
        for (FormalParameter pageflowParam : assocPageflowParams) {

            if (localDataMap.get(pageflowParam.getName()) == null) {
                // No data field matching given pageflow param
                // name.Add a data field to match the pageflow
                // parameter.
                DataField newData =
                        PageflowUtil
                                .createDataForPageflowAssociatedData(pageflowParam);
                cmd.append(AddCommand.create(editingDomain,
                        taregetProcess,
                        Xpdl2Package.eINSTANCE
                                .getDataFieldsContainer_DataFields(),
                        newData));

                additionalData.add(pageflowParam);
            }

            // And create a new associated parameter for the data.
            AssociatedParameter newAssoc =
                    PageflowUtil
                            .createMatchingUserTaskAssocParam(pageflowStartEventAssocParamMap
                                    .get(pageflowParam.getName()),
                                    pageflowParamMap.get(pageflowParam
                                            .getName()),
                                    pageflowParam.getName());
            newAssociatedParameters.getAssociatedParameter().add(newAssoc);

        }

        //
        // Finally, reset the user task associated parameters
        cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                userTaskActivity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters(),
                newAssociatedParameters));

        // If requested, give user opportiunity to cancel if extra data would be
        // added.
        if (!additionalData.isEmpty() && warnUser) {
            Shell activeShell = Display.getCurrent().getActiveShell();

            ReportAdditionalPageflowDataWizard reportWizard =
                    new ReportAdditionalPageflowDataWizard(userTaskActivity,
                            pageflowProcess, additionalData);

            WizardDialog wizDig = new WizardDialog(activeShell, reportWizard);
            if (wizDig.open() != WizardDialog.OK) {
                cmd = null;
            }
        }

        return cmd;
    }

    private static void logToConsole(String msg) {
        if (logToConsole) {
            System.out.println(msg);
        }

    }

    /**
     * Command that perfors a save on the given resource working copy after
     * executing its child commands.
     * 
     * 
     * @author aallway
     * @since 3.3 (29 Mar 2010)
     */
    private static class ExecuteAndSaveCommand extends
            LateExecuteCompoundCommand {

        private WorkingCopy wcToSave;

        ExecuteAndSaveCommand(WorkingCopy wcToSave) {
            this.wcToSave = wcToSave;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand#execute()
         */
        @Override
        public void execute() {
            try {
                super.execute();

                if (wcToSave != null) {
                    wcToSave.save();
                }

            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to execute command or save file", e); //$NON-NLS-1$
            }
        }

    }
}
