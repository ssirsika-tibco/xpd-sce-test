/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.MoveProcessProcessor;
import com.tibco.xpd.xpdl2.Process;

/**
 * Abstract move process participant specifically designed for
 * {@link MoveProcessProcessor}. Provides the implementers with the necessary
 * data which will assist them to successfully move process to destination
 * package and fix necessary references.
 * 
 * @author kthombar
 * @since 28-Sep-2014
 */
public abstract class AbstractMoveProcessParticipant extends MoveParticipant {

    /**
     * the refactored process
     */
    private Process processToMove;

    /**
     * destination resource
     */
    private IResource destinationResource;

    /**
     * the new Package name (if created)
     */
    private String newPackageName;

    /**
     * The move processor for this participant.
     */
    private MoveProcessProcessor moveProcessProcessor;

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
        /*
         * just return true from there the other overloaded
         * initialize(RefactoringProcessor, Object , RefactoringArguments)
         * method does the real computation
         */
        return true;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor,
     *      java.lang.Object,
     *      org.eclipse.ltk.core.refactoring.participants.RefactoringArguments)
     * 
     * @param processor
     * @param element
     * @param arguments
     * @return
     */
    @Override
    public boolean initialize(RefactoringProcessor processor, Object element,
            RefactoringArguments arguments) {

        /*
         * Initialize the necessary data required for process move.
         */
        if ((processor instanceof MoveProcessProcessor)
                && (arguments instanceof MoveArguments)
                && element instanceof Process) {

            moveProcessProcessor = (MoveProcessProcessor) processor;

            processToMove = (Process) element;

            if (((MoveArguments) arguments).getDestination() instanceof IResource) {
                destinationResource =
                        (IResource) ((MoveArguments) arguments)
                                .getDestination();
            }

            newPackageName = moveProcessProcessor.getNewPackageName();

        }

        return processToMove != null && destinationResource != null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public abstract String getName();

    /**
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @return
     * @throws OperationCanceledException
     */
    @Override
    public final RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {
        return checkConditions(pm,
                context,
                processToMove,
                destinationResource,
                moveProcessProcessor,
                newPackageName);
    }

    /**
     * Checks the conditions of the move process participant.
     * 
     * The move process is considered as not being executable if the returned
     * status has the severity of RefactoringStatus#FATAL. Note that this blocks
     * the whole move process operation!
     * 
     * @param pm
     *            the monitor to track progress
     * @param context
     *            the check condition context , Clients should use the passed
     *            CheckConditionsContext to validate the changes they generate.
     *            If the generated changes include workspace resource
     *            modifications, clients should call ...
     * 
     *            (ResourceChangeChecker)
     *            context.getChecker(ResourceChangeChecker.class);
     *            IResourceChangeDescriptionFactory deltaFactory=
     *            checker.getDeltaFactory(); ... and use the delta factory to
     *            describe all resource modifications in advance.
     * @param processToMove
     *            the process which is being moved
     * @param destinationResource
     *            the destination resource to which this process is being moved.
     *            Note that if the process is moved to complately new xpdl
     *            package this resource might not exist physically(just a dummy
     *            will be passed so that participants work).
     * @param moveProcessProcessor
     *            the processor which initiates this move.
     * @param newPackageName
     *            the new package name if the move creates a new Package, else
     *            <code>null</code>
     * @return the status of refactoring.
     */
    protected abstract RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context, Process processToMove,
            IResource destinationResource,
            MoveProcessProcessor moveProcessProcessor, String newPackageName);

    /**
     * Creates the Change for moving the process to the destination.
     * 
     * @param pm
     *            the monitor to track progress
     * 
     * @param processToMove
     *            the process which is being moved
     * @param destinationResource
     *            the destination resource to which this process is being moved.
     *            Note that if the process is moved to complately new xpdl
     *            package this resource might not exist physically(just a dummy
     *            will be passed so that participants work).
     * @param moveProcessProcessor
     *            the processor which initiates this move.
     * @param newPackageName
     *            the new package name if the move creates a new Package, else
     *            <code>null</code>
     * @param oldIdToNewEObjectMap
     *            Map of Old elements Id's to the new Copied elements with
     *            reassigned ID's
     * @return the Change that will perform the process move.
     */
    protected abstract Change createChange(IProgressMonitor pm,
            Process processToMove, IResource destinationResource,
            MoveProcessProcessor moveProcessProcessor, String newPackageName,
            Map<String, EObject> oldIdToNewEObjectMap);

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public final Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        return createChange(pm,
                processToMove,
                destinationResource,
                moveProcessProcessor,
                newPackageName,
                moveProcessProcessor.getOldIdToNewEObjectMap());
    }
}
