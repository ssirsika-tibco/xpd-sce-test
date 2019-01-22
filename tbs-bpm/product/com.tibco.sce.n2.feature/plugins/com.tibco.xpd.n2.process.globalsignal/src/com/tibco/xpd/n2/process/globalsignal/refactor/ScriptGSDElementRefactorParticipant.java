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
 * Participant for refactoring of GSD elements being used in scripts (e.g.
 * Payload data).
 * 
 * @author sajain
 * @since May 13, 2015
 */
public class ScriptGSDElementRefactorParticipant extends RenameParticipant {

    /**
     * Element being refactored.
     */
    private EObject element = null;

    /**
     * Participant for refactoring of GSD elements being used in scripts (e.g.
     * Payload data).
     */
    public ScriptGSDElementRefactorParticipant() {
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

        Set<Activity> referencingScriptContainerCandidates =
                GSDElementRefactorHelper.getReferencingActivities(element);

        pm.worked(0);

        if (referencingScriptContainerCandidates != null
                && !referencingScriptContainerCandidates.isEmpty()) {

            pm.beginTask(Messages.ScriptGSDElementRefactorParticipant_ProcessingScripts,
                    referencingScriptContainerCandidates.size());

            Set<CompositeChange> activityChangeSet =
                    new HashSet<CompositeChange>();

            int count = 0;

            for (EObject referencingScriptContainer : referencingScriptContainerCandidates) {

                if (referencingScriptContainer instanceof Activity) {

                    Activity activityCandidate =
                            (Activity) referencingScriptContainer;

                    ScriptGSDReferenceChangeFactory changeFactory =
                            new ScriptGSDReferenceChangeFactory(
                                    activityCandidate, getArguments(), element);

                    List<AbstractScriptGSDReferenceChange> scriptChanges =
                            changeFactory.getChanges();

                    if (scriptChanges != null && !scriptChanges.isEmpty()) {

                        Set<AbstractScriptGSDReferenceChange> scriptChangeSet =
                                new HashSet<AbstractScriptGSDReferenceChange>();

                        for (AbstractScriptGSDReferenceChange scriptGSDReferenceChange : scriptChanges) {

                            if (scriptGSDReferenceChange
                                    .containsModifications()) {

                                scriptChangeSet.add(scriptGSDReferenceChange);
                            }
                        }

                        if (!scriptChangeSet.isEmpty()) {

                            String message =
                                    Messages.GSDElementRefactorParticipant_RenameScriptsForActivity;

                            String activityName =
                                    Xpdl2ModelUtil
                                            .getDisplayNameOrName(activityCandidate);

                            CompositeChange activityCompositeChange =
                                    new CompositeChange(
                                            String.format(message, activityName),
                                            scriptChangeSet
                                                    .toArray(new Change[scriptChangeSet
                                                            .size()]));

                            activityChangeSet.add(activityCompositeChange);
                        }
                    }
                }
                pm.worked(count);
                count++;
            }

            if (!activityChangeSet.isEmpty()) {

                return new CompositeChange(
                        Messages.ScriptGSDElementRefactorParticipant_RenamingActivitiesScripts,
                        activityChangeSet.toArray(new Change[activityChangeSet
                                .size()]));
            }
        }

        return new NullChange(
                Messages.ScriptGSDElementRefactorParticipant_NoScriptElementsAffected);
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
