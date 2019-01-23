/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

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
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Rename Participant class implementation for the 
 * renaming of BOM references in the WebService Mappings
 * 
 * @author mtorres
 * 
 */
public class WSMappingBOMElementRefactorParticipant extends RenameParticipant {

    private EObject element = null;

    public WSMappingBOMElementRefactorParticipant() {
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
        pm.beginTask(Messages.BOMElementRefactorParticipant_ExaminingActivities, 1);
        List<EObject> referencingWSContainerCandidates =
                WSMappingBOMElementRefactorHelper
                        .getReferencingWSContainerCandidates(element);
        pm.worked(0);
        if (referencingWSContainerCandidates != null
                && !referencingWSContainerCandidates.isEmpty()) {
            pm.beginTask(Messages.WSMappingBOMElementRefactorParticipant_ProcessingDataMappings,
                    referencingWSContainerCandidates.size());
            Set<CompositeChange> activityChangeSet =
                    new HashSet<CompositeChange>();
            int count = 0;
            for (EObject referencingScriptContainer : referencingWSContainerCandidates) {
                if (referencingScriptContainer instanceof Activity) {
                    Activity activityCandidate = (Activity) referencingScriptContainer;
                    WSMappingBOMReferenceChangeFactory changeFactory =
                            new WSMappingBOMReferenceChangeFactory(
                                    activityCandidate, getArguments(), element);
                    List<WSMappingBOMReferenceChange> mappingChanges =
                            changeFactory.getChanges();
                    if (mappingChanges != null && !mappingChanges.isEmpty()) {
                        Set<WSMappingBOMReferenceChange> mappingChangeSet =
                                new HashSet<WSMappingBOMReferenceChange>();
                        for (WSMappingBOMReferenceChange wsMappingBOMReferenceChange : mappingChanges) {
                            if (wsMappingBOMReferenceChange
                                    .containsModifications()) {
                                mappingChangeSet.add(wsMappingBOMReferenceChange);
                            }
                        }
                        if (!mappingChangeSet.isEmpty()) {
                            String message = Messages.WSMappingBOMElementRefactorParticipant_RefactoredActivity;
                            String activityName = activityCandidate.getName();
                            CompositeChange activityCompositeChange =
                                    new CompositeChange(String.format(message,
                                            activityName), mappingChangeSet
                                            .toArray(new Change[mappingChangeSet
                                                    .size()]));
                            activityChangeSet.add(activityCompositeChange);
                        }
                    }
                }
                pm.worked(count);
                count++;
            }

            if (!activityChangeSet.isEmpty()) {
                return new CompositeChange(Messages.WSMappingBOMElementRefactorParticipant_RenamingDataMappings,
                        activityChangeSet.toArray(new Change[activityChangeSet
                                .size()]));
            }
        }

        return new NullChange(Messages.WSMappingBOMElementRefactorParticipant_NoDataMappingsAffected);
    }

    @Override
    public String getName() {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
        }
        return null;
    }

    @Override
    protected boolean initialize(Object element) {
        if (element instanceof EObject) {
            this.element = (EObject) element;
            return true;
        }
        return false;
    }

}
