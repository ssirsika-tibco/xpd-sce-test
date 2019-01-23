/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveProcessor;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractMoveProcessParticipant;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.MoveProcessToPackageChange;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Processor that performs process move. It provides the move participants with
 * the Process being moved and the Destination Package to which it should be
 * moved via {@link #loadParticipants(RefactoringStatus, SharableParticipants)}.
 * Additionally the Participants may extend
 * {@link AbstractMoveProcessParticipant} which will give the participants full
 * access of the necessary data.
 * 
 * 
 * @author kthombar
 * @since 04-Sep-2014
 */
public class MoveProcessProcessor extends MoveProcessor {

    /**
     * The process to refactor
     */
    private final Process refactoredProcess;

    /**
     * the destination resource to which the process is to be refactored
     */
    private IResource destinationResource;

    /**
     * in case the user chooses to create a new destination package then this
     * should be populated with the new package name
     */
    private String newPackageName;

    /**
     * Map of Old elements Id's to the new Copied elements with reassigned ID's
     */
    private Map<String, EObject> oldIdToNewEObjectMap = null;

    /**
     * 
     * @param refactoredProcess
     */
    public MoveProcessProcessor(Process process) {
        this.refactoredProcess = process;
        this.destinationResource = null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getElements()
     * 
     * @return
     */
    @Override
    public Object[] getElements() {
        List<Process> proc = new ArrayList<Process>();
        proc.add(refactoredProcess);
        return proc.toArray();
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getIdentifier()
     * 
     * @return
     */
    @Override
    public String getIdentifier() {
        return "com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.MoveProcessProcessor"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getProcessorName()
     * 
     * @return
     */
    @Override
    public String getProcessorName() {
        return Messages.MoveProcessProcessor_MoveProcessor_name;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#isApplicable()
     * 
     * @return
     * @throws CoreException
     */
    @Override
    public boolean isApplicable() throws CoreException {
        return true;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
            throws CoreException, OperationCanceledException {
        RefactoringStatus result = new RefactoringStatus();
        if (refactoredProcess != null) {
            result.merge(RefactoringStatus.create(Status.OK_STATUS));
        } else {
            result.merge(RefactoringStatus.create(Status.CANCEL_STATUS));
        }
        return result;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws CoreException,
            OperationCanceledException {

        RefactoringStatus status = new RefactoringStatus();

        if (destinationResource instanceof SpecialFolder
                && newPackageName == null) {
            status.addFatalError(Messages.MoveProcessParticipant_NewPackageNameInvalidError_msg);
        }

        return status;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#createChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        pm.beginTask("", 1); //$NON-NLS-1$
        try {
            CompositeChange compositeChange =
                    new CompositeChange(
                            Messages.MoveProcessParticipant_MoveProcessParticipant_name);
            compositeChange.markAsSynthetic();

            /*
             * Change that will actually move the process to the destination.
             */
            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(refactoredProcess);

            if (editingDomain != null) {

                MoveProcessToPackageChange moveChange = null;

                /*
                 * get all the affected objects that are involved in this move.
                 */
                List<EObject> allObjectsAffectedByMove =
                        getAllObjectsAffectedByMove();

                if (!allObjectsAffectedByMove.isEmpty()) {

                    /*
                     * creating a copy of objects to move.
                     */
                    Command create =
                            CopyCommand.create(editingDomain,
                                    allObjectsAffectedByMove);
                    create.execute();
                    Collection copyOfAllObjectsToMove = create.getResult();

                    /*
                     * reassigning ids so that we have no duplicates
                     */
                    this.oldIdToNewEObjectMap =
                            Xpdl2ModelUtil
                                    .reassignUniqueIds(copyOfAllObjectsToMove,
                                            editingDomain);

                    if (destinationResource instanceof IFile) {
                        /*
                         * If destination is an Xpdl file then we pass the
                         * Package under it to the Change class
                         */

                        WorkingCopy destinationWC =
                                WorkingCopyUtil
                                        .getWorkingCopy(destinationResource);

                        if (destinationWC != null) {

                            EObject destinationPackage =
                                    destinationWC.getRootElement();
                            if (destinationPackage instanceof Package) {

                                moveChange =
                                        new MoveProcessToPackageChange(
                                                refactoredProcess,
                                                (Package) destinationPackage,
                                                editingDomain,
                                                copyOfAllObjectsToMove,
                                                oldIdToNewEObjectMap);

                            }
                        }
                    } else if (destinationResource instanceof IFolder) {
                        /*
                         * If the destination is process package special folder
                         * then we pass it to the change class
                         */
                        if (newPackageName != null && !newPackageName.isEmpty()) {

                            moveChange =
                                    new MoveProcessToPackageChange(
                                            refactoredProcess,
                                            (IFolder) destinationResource,
                                            newPackageName, editingDomain,
                                            copyOfAllObjectsToMove,
                                            oldIdToNewEObjectMap);

                        }
                    }

                    if (moveChange != null) {
                        compositeChange.add(moveChange);
                    }
                }
            }
            pm.worked(1);
            return compositeChange;

        } finally {
            pm.done();
        }
    }

    /**
     * 
     * @return all the Objects that will be affected by the move(i.e. will ne
     *         required to be moved; i.e. the process itself, the pools,
     *         atrifacts, associations,participants, type declarations)
     */
    protected List<EObject> getAllObjectsAffectedByMove() {
        List<EObject> allObjectsAffectedByMove = new ArrayList<EObject>();

        Collection<Pool> processPools =
                Xpdl2ModelUtil.getProcessPools(refactoredProcess);

        Set<Participant> referencedParticipants =
                getReferencedParticipants(refactoredProcess);

        Set<TypeDeclaration> allReferencedTypeDeclarations =
                getAllReferencedTypeDeclarations(refactoredProcess);

        Collection<Artifact> allArtifactsInProcess =
                Xpdl2ModelUtil.getAllArtifactsInProcess(refactoredProcess);

        Collection<Association> allAssociationsInProc =
                Xpdl2ModelUtil.getAllAssociationsInProc(refactoredProcess);

        allObjectsAffectedByMove.add(refactoredProcess);
        allObjectsAffectedByMove.addAll(processPools);
        allObjectsAffectedByMove.addAll(allArtifactsInProcess);
        allObjectsAffectedByMove.addAll(allAssociationsInProc);
        allObjectsAffectedByMove.addAll(referencedParticipants);
        allObjectsAffectedByMove.addAll(allReferencedTypeDeclarations);

        return allObjectsAffectedByMove;
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
     * 
     * @param sourceProcess
     * @return all the Type Declarations referenced from the process
     */
    private Set<TypeDeclaration> getAllReferencedTypeDeclarations(
            Process sourceProcess) {
        Set<TypeDeclaration> referencedTypeDeclarations =
                new HashSet<TypeDeclaration>();

        List<ProcessRelevantData> allProcessRelevantData =
                ProcessInterfaceUtil.getAllProcessRelevantData(sourceProcess);

        Package srcPackage = sourceProcess.getPackage();

        for (ProcessRelevantData processRelevantData : allProcessRelevantData) {
            DataType dataType = processRelevantData.getDataType();

            if (dataType instanceof DeclaredType) {
                DeclaredType dtype = (DeclaredType) dataType;

                String typeDeclarationId = dtype.getTypeDeclarationId();

                if (typeDeclarationId != null && !typeDeclarationId.isEmpty()) {
                    TypeDeclaration typeDeclaration =
                            srcPackage.getTypeDeclaration(typeDeclarationId);

                    if (typeDeclaration != null
                            && !referencedTypeDeclarations
                                    .contains(typeDeclaration)) {
                        referencedTypeDeclarations.add(typeDeclaration);
                    }
                }
            }
        }
        return referencedTypeDeclarations;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#loadParticipants(org.eclipse.ltk.core.refactoring.RefactoringStatus,
     *      org.eclipse.ltk.core.refactoring.participants.SharableParticipants)
     * 
     * @param status
     * @param sharedParticipants
     * @return
     * @throws CoreException
     */
    @Override
    public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
            SharableParticipants sharedParticipants) throws CoreException {

        IResource destiIFile = destinationResource;

        if (destinationResource instanceof IFolder && newPackageName != null) {
            /*
             * If the destination is process package speciqal folder then pass
             * the handle of the xpdl resource that will be created so that the
             * contributing participants do not have to deal with other
             * un-realted stuff
             */
            destiIFile =
                    ((IFolder) destinationResource).getFile(newPackageName);
        }

        return ParticipantManager.loadMoveParticipants(status,
                this,
                refactoredProcess,
                new MoveArguments(destiIFile, true),
                new String[] { XpdConsts.PROJECT_NATURE_ID },
                sharedParticipants);
    }

    /**
     * Validate the destination.
     * 
     * @param firstElement
     * @return
     */
    protected RefactoringStatus validateDestination(Package destinationPackage) {
        RefactoringStatus result = new RefactoringStatus();

        Package package1 = refactoredProcess.getPackage();

        if (package1 != null) {
            if (package1.getId().equals((destinationPackage).getId())) {
                /*
                 * We should prevent dragging processes and dropping them to its
                 * own package.
                 */
                result.merge(RefactoringStatus
                        .createFatalErrorStatus(Messages.MoveProcessProcessor_CannotMoveProcessToParentPackageValidation_msg));
            }
        } else {
            result.merge(RefactoringStatus.create(Status.OK_STATUS));
        }
        return result;
    }

    /**
     * Sets the destination to which the process is to be refactored
     * 
     * @param res
     */
    protected void setDestinationResource(IResource res) {
        this.destinationResource = res;
    }

    /**
     * Sets the package name in case the user decides to create a new package.
     * 
     * @param newPackageName
     */
    protected void setNewPackaqeName(String newPackageName) {
        this.newPackageName = newPackageName;
    }

    /**
     * 
     * @return the Destination to which the process is to be moved
     */
    protected IResource getDestinationResource() {
        return this.destinationResource;
    }

    /**
     * 
     * @return the process to move.
     */
    public Process getProcessToMove() {
        return refactoredProcess;
    }

    /**
     * 
     * @return the name of the new Package which is created in case the process
     *         is moved to a new Package., else if no new Package was created
     *         then return <code>null</code>
     */
    public String getNewPackageName() {

        return newPackageName;
    }

    /**
     * @return the Map of Old element Id's to the new Copied elements with
     *         reassigned ID's
     * 
     */
    public Map<String, EObject> getOldIdToNewEObjectMap() {
        return oldIdToNewEObjectMap;
    }
}
