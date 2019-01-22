/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.ui.refactor.GSDElementRefactorHelper;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Participant for refactoring of payload data mapping references.
 * 
 * @author sajain
 * @since May 18, 2015
 */
public class PayloadDataMappingReferenceParticipant extends RenameParticipant {

    /**
     * Element being refactored.
     */
    private EObject element = null;

    /**
     * Participant for refactoring of payload data mapping references.
     */
    public PayloadDataMappingReferenceParticipant() {
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {

        RefactoringStatus status = new RefactoringStatus();
        return status;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        pm.beginTask(Messages.GSDElementRefactorParticipant_ExaminingActivities,
                1);

        Set<Activity> referencingActivities =
                GSDElementRefactorHelper.getReferencingActivities(element);

        pm.worked(0);

        if (referencingActivities != null && !referencingActivities.isEmpty()) {

            pm.beginTask(Messages.GSDReferenceParticipant_ProgressMonitor_Label,
                    referencingActivities.size());

            Set<CompositeChange> activityChangeSet =
                    new HashSet<CompositeChange>();

            int count = 0;

            for (Activity activityCandidate : referencingActivities) {

                PayloadDataMappingReferenceChangeFactory changeFactory =
                        new PayloadDataMappingReferenceChangeFactory(
                                activityCandidate, getArguments(), element);

                List<PayloadDataMappingReferenceChange> refChanges =
                        changeFactory.getChanges();

                if (refChanges != null && !refChanges.isEmpty()) {

                    for (PayloadDataMappingReferenceChange eachChange : refChanges) {

                        if (eachChange.containsModifications()) {

                            String message =
                                    Messages.GSDElementRefactorParticipant_RenameScriptsForActivity;

                            String activityName =
                                    Xpdl2ModelUtil
                                            .getDisplayNameOrName(activityCandidate);

                            PayloadDataMappingReferenceChange changeArray[] =
                                    { eachChange };

                            CompositeChange activityCompositeChange =
                                    new CompositeChange(String.format(message,
                                            activityName), changeArray);

                            activityChangeSet.add(activityCompositeChange);
                        }
                    }
                }

                pm.worked(count);
                count++;
            }

            if (!activityChangeSet.isEmpty()) {

                return new CompositeChange(
                        Messages.GSDReferenceParticipant_CompositeChange_Name,
                        activityChangeSet.toArray(new Change[activityChangeSet
                                .size()]));
            }
        }

        return new NullChange(Messages.GSDReferenceParticipant_NoChange_Name);
    }

    @Override
    public String getName() {

        if (element instanceof PayloadDataField) {

            return ((PayloadDataField) element).getName();
        }

        return null;
    }

    @Override
    protected boolean initialize(Object element) {

        /*
         * Checking of EObject instance as we are already filtering out in
         * plugin.xml and hence we'd be flexible enough to accommodate new
         * additions in future.
         */
        if (element instanceof EObject) {

            this.element = (EObject) element;

            return true;
        }
        return false;
    }

}
