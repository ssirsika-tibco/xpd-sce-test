/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.indexing.ActivityInvokingProcessIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.MoveProcessProcessor;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This participant mainly deals with adding project references which will be
 * required during process move.
 * 
 * Note: the processor for this participant is {@link MoveProcessProcessor}
 * 
 * @author kthombar
 * @since 28-Sep-2014
 */
public class AddProjectReferencesParticipant extends
        AbstractAddProjectReferencesForProcessMove {

    /**
     * 
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove#getAllProjectsThatTargetProjectMustReference(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param refactoredProcess
     * @param destinationResource
     * @return {@link Set} of Projects that the destination project should
     *         reference after refactor, empty Set if there are no projects to
     *         reference.
     */
    @Override
    protected Set<IProject> getAllProjectsThatTargetProjectMustReference(
            Process refactoredProcess, IResource destinationResource) {

        Set<IProject> allProjectsToReference = new HashSet<IProject>();

        /* add all the BOM projects to reference to the set */
        referencedBomProjects(refactoredProcess,
                destinationResource,
                allProjectsToReference);

        /* add all the OM projects to reference to the set */
        Set<Participant> referencedParticipants =
                getReferencedParticipants(refactoredProcess);
        referencedOmProjects(referencedParticipants,
                refactoredProcess,
                destinationResource,
                allProjectsToReference);

        /* add other project references. */
        otherProjectsToReference(refactoredProcess,
                destinationResource,
                allProjectsToReference);

        return allProjectsToReference;
    }

    /**
     * Returns only those participants which are used in the process(process
     * activities) to be refactored.
     * 
     * @return
     */
    private Set<Participant> getReferencedParticipants(Process sourceProcess) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(sourceProcess);
        Set<Participant> referencedParticipants = new HashSet<Participant>();
        for (Activity eachActivity : activities) {
            EList<Performer> performerList = eachActivity.getPerformerList();
            for (Performer eachPerformer : performerList) {
                EList<Participant> participants =
                        sourceProcess.getPackage().getParticipants();
                for (Participant eachParticipant : participants) {
                    if (eachParticipant.getId()
                            .equals(eachPerformer.getValue())) {
                        referencedParticipants.add(eachParticipant);
                    }
                }
            }
        }
        return referencedParticipants;
    }

    /**
     * Populates the passed Set 'allReferencedProjects' with projects to
     * reference due to the following condition:
     * <p>
     * 1. If the Referenced process by a sub-process is in other project post
     * refactor
     * <p>
     * 2. If the Invoked process by the Send Task is in other project post
     * refactor
     * <p>
     * 3. If the implemented interface by a process is in other project post
     * refactor
     * <p>
     * 4. If the Page flow refernced by the user task form type is in other
     * project post refactor
     * 
     * 
     * 
     * @param sourceProcess
     * @param allReferencedProjects
     *            Set to populate with the projects to reference
     */
    private void otherProjectsToReference(Process sourceProcess,
            IResource destinationResource, Set<IProject> allReferencedProjects) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(sourceProcess);

        for (Activity activity : allActivitiesInProc) {

            Implementation impl = activity.getImplementation();

            if (impl instanceof SubFlow) {
                /*
                 * check if the re-usable sub process and the invoked process
                 * are in different projects post refactor, if yes then add it
                 * project to the list of prokects to referernce
                 */
                SubFlow subFlow = (SubFlow) impl;

                String processId = subFlow.getProcessId();

                if (processId != null && !processId.isEmpty()) {

                    EObject subProcessOrInterface =
                            ProcessDataUtil.getSubProcessOrInterface(activity);

                    if (subProcessOrInterface != null) {

                        IProject projectFor =
                                WorkingCopyUtil
                                        .getProjectFor(subProcessOrInterface);

                        if (projectFor != destinationResource.getProject()
                                && !allReferencedProjects.contains(projectFor)) {
                            allReferencedProjects.add(projectFor);
                        }
                    }
                }

            } else if (impl instanceof Task) {

                Task task = (Task) impl;
                TaskSend taskSend = task.getTaskSend();

                if (taskSend != null) {

                    Object otherElement =
                            Xpdl2ModelUtil.getOtherElement(taskSend,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_BusinessProcess());
                    if (otherElement instanceof BusinessProcess) {
                        /*
                         * check if the send task and the invoked process are in
                         * different projects post refactor, if yes then add it
                         * project to the list of prokects to referernce
                         */
                        BusinessProcess sendTaskBusinessProc =
                                (BusinessProcess) otherElement;

                        String processId = sendTaskBusinessProc.getProcessId();

                        if (processId != null && !processId.isEmpty()) {
                            Process procesById =
                                    ProcessUIUtil.getProcesById(processId);

                            if (procesById != null) {
                                IProject projectFor =
                                        WorkingCopyUtil
                                                .getProjectFor(procesById);

                                if (projectFor != null
                                        && projectFor != destinationResource
                                                .getProject()
                                        && !allReferencedProjects
                                                .contains(projectFor)) {
                                    allReferencedProjects.add(projectFor);
                                }
                            }
                        }
                    }
                } else if (task.getTaskUser() != null) {

                    Process userTaskPageflowProcess =
                            TaskObjectUtil.getUserTaskPageflowProcess(activity);

                    if (userTaskPageflowProcess != null) {
                        /*
                         * check if the user task and the invoked pageflow form
                         * type are in different projects post refactor, if yes
                         * then add it project to the list of prokects to
                         * referernce
                         */
                        IProject projectFor =
                                WorkingCopyUtil
                                        .getProjectFor(userTaskPageflowProcess);

                        if (projectFor != null
                                && projectFor != destinationResource
                                        .getProject()
                                && !allReferencedProjects.contains(projectFor)) {
                            allReferencedProjects.add(projectFor);
                        }
                    }
                }
            }
        }

        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(sourceProcess,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface());

        if (otherElement instanceof ImplementedInterface) {
            /* go ahead only if the process implements an interface */
            ImplementedInterface implementedIfc =
                    (ImplementedInterface) otherElement;

            String processIfcId = implementedIfc.getProcessInterfaceId();

            if (processIfcId != null && !processIfcId.isEmpty()) {

                List<EObject> processInterfacesById =
                        getProcessInterfacesById(processIfcId);

                if (!processInterfacesById.isEmpty()) {

                    IProject projectFor =
                            WorkingCopyUtil.getProjectFor(processInterfacesById
                                    .get(0));

                    if (projectFor != destinationResource.getProject()) {
                        /*
                         * If post refactor the process and the implemented
                         * interface are in separate projects then add project
                         * reference if not already present.
                         */
                        allReferencedProjects.add(projectFor);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param processIfcId
     * @return all the {@link ProcessInterface} in the Workspace with the given
     *         Id.
     */
    private List<EObject> getProcessInterfacesById(String processIfcId) {

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID,
                processIfcId);
        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessResourceItemType.PROCESSINTERFACE.toString(),
                        null, additionalAttributes);

        return ProcessUIUtil
                .getIndexedElements(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                        criteria);
    }

    /**
     * Checks if the participants are moved to different project, if yes then
     * check if project referennce needs to be added and return all the projects
     * which need to be referenced.
     * 
     * @param referencedParticipants
     * @param eachProcess
     * @param allReferencedProjects
     * @return
     */
    private void referencedOmProjects(Set<Participant> referencedParticipants,
            Process eachProcess, IResource destinationResource,
            Set<IProject> allReferencedProjects) {

        for (Participant eachParticipant : referencedParticipants) {

            if (eachParticipant.getExternalReference() != null) {

                ExternalReference externalReference =
                        eachParticipant.getExternalReference();
                IFile omFile =
                        SpecialFolderUtil
                                .resolveSpecialFolderRelativePath(WorkingCopyUtil
                                        .getProjectFor(eachProcess),
                                        "om", //$NON-NLS-1$
                                        externalReference.getLocation(),
                                        true);
                if (omFile != null) {
                    IProject omProject = omFile.getProject();
                    if (omProject != destinationResource.getProject()
                            && !allReferencedProjects.contains(omProject)) {
                        allReferencedProjects.add(omProject);
                    }
                }
            }
        }
    }

    /**
     * Gets all the process data referencing BOM elements. Then checks if after
     * moving the process to a package in different project requires any project
     * references to be made. If yes then returns all the projects that need to
     * referenced from the project to which the process is moved.
     * 
     * @param allReferencedProjects
     * 
     * @return All projects that need to be referenced.
     */
    private void referencedBomProjects(Process sourceProcess,
            IResource destinationResource, Set<IProject> allReferencedProjects) {

        /*
         * Check if there is any reference to BOM from Data fields.
         */
        List<ProcessRelevantData> allExternalProcessRelevantData =
                ProcessInterfaceUtil
                        .getAllExternalProcessRelevantData(sourceProcess);

        for (ProcessRelevantData procRelData : allExternalProcessRelevantData) {

            ExternalReference extRef = null;

            if (procRelData.getDataType() instanceof ExternalReference) {

                extRef = (ExternalReference) procRelData.getDataType();

            } else if (procRelData.getDataType() instanceof RecordType) {

                RecordType recordType = (RecordType) procRelData.getDataType();

                extRef = recordType.getMember().get(0).getExternalReference();

            }

            if (extRef != null) {
                IFile bomFile =
                        SpecialFolderUtil
                                .resolveSpecialFolderRelativePath(WorkingCopyUtil
                                        .getProjectFor(sourceProcess),
                                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                        extRef.getLocation(),
                                        true);
                if (bomFile != null) {
                    IProject bomProject = bomFile.getProject();
                    if (bomProject != destinationResource.getProject()
                            && !allReferencedProjects.contains(bomProject)) {
                        allReferencedProjects.add(bomProject);
                    }
                }
            }
        }

        /*
         * Check if there is refernce to Global BOM from the Global Data service
         * task.
         */
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(sourceProcess);

        for (Activity activity : allActivitiesInProc) {
            CaseAccessOperationsType caseAccessOps =
                    getCACOperationFromActivity(activity);

            if (caseAccessOps != null) {

                ExternalReference extRef =
                        caseAccessOps.getCaseClassExternalReference();

                if (extRef != null) {
                    IFile bomFile =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(WorkingCopyUtil
                                            .getProjectFor(sourceProcess),
                                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                            extRef.getLocation(),
                                            true);
                    if (bomFile != null) {
                        IProject bomProject = bomFile.getProject();
                        if (bomProject != destinationResource.getProject()
                                && !allReferencedProjects.contains(bomProject)) {
                            allReferencedProjects.add(bomProject);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param activity
     * @return {@link CaseAccessOperationsType} of the passed Activity(if it has
     *         one, else <code>null</code>)
     */
    private CaseAccessOperationsType getCACOperationFromActivity(
            Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {

            Task task = (Task) implementation;
            if (task.getTaskService() != null) {

                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(task.getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_GlobalDataOperation());

                if (otherElement instanceof GlobalDataOperation) {

                    /* We are interested only in Global Data Operations */
                    GlobalDataOperation globalDataOp =
                            (GlobalDataOperation) otherElement;
                    /*
                     * Return the CaseAccessOperationType if its defined, else
                     * will return null.
                     */
                    return globalDataOp.getCaseAccessOperations();
                }
            }
        }
        return null;
    }

    /**
     * This method uses the indexer {@link ActivityInvokingProcessIndexProvider}
     * to get the Activities which reference the process being refactored.
     * Checks if post refactor these activities and the refactored process are
     * in different projects and if a project reference is required, if yes then
     * returns all the projects that need to reference the destination project.
     * 
     * @param refactoredProcess
     * @return all the Projects that should references the destination package
     *         parent project post refactor.
     */
    @Override
    protected Set<IProject> getAllProjectsThatShouldRefTargetProject(
            Process refactoredProcess, IResource destinationResource) {

        Set<IProject> allReferencedProjects = new HashSet<IProject>();

        IndexerItemImpl criteria = new IndexerItemImpl();

        String processId = refactoredProcess.getId();

        criteria.set(ActivityInvokingProcessIndexProvider.COLUMN_PROCESS_ID,
                processId);

        Collection<IndexerItem> query =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(ActivityInvokingProcessIndexProvider.ACTIVITY_INVOKING_PROCESS_REFERENCE_INDEXER_ID,
                                criteria);

        for (IndexerItem indexerItem : query) {

            URI uri = URI.createURI(indexerItem.getURI());

            EObject eObjectFrom = ProcessUIUtil.getEObjectFrom(uri);

            if (eObjectFrom instanceof Activity) {
                Activity activity = (Activity) eObjectFrom;
                IProject project = WorkingCopyUtil.getProjectFor(activity);

                if (!allReferencedProjects.contains(project)
                        && project != destinationResource.getProject()) {
                    allReferencedProjects.add(project);
                }
            }
        }
        return allReferencedProjects;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove#getChangeName()
     * 
     * @return
     */
    @Override
    protected String getChangeName() {

        return Messages.AddProjectReferencesParticipant_AddProjectReferencesDueToProcessOmBomDependency_msg;
    }

}
