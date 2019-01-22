/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rename Participant class implementation for the 
 * renaming of BOM references in the scripts
 * 
 * @author mtorres
 * 
 */
public class ScriptBOMElementRefactorParticipant extends RenameParticipant {

    private EObject element = null;

    public ScriptBOMElementRefactorParticipant() {
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
        List<EObject> referencingScriptContainerCandidates =
                ScriptBOMElementRefactorHelper
                        .getReferencingScriptContainerCandidates(element);
        //XPD-4936 cache to hold the enumerations for process package scope, for this refactoring run.
        Map<Package, PackageScopeEnumCache> packageScopeEnumCacheMap =
                new HashMap<Package, PackageScopeEnumCache>();
        pm.worked(0);
        if (referencingScriptContainerCandidates != null
                && !referencingScriptContainerCandidates.isEmpty()) {
            pm.beginTask(Messages.ScriptBOMElementRefactorParticipant_ProcessingScripts,
                    referencingScriptContainerCandidates.size());
            Set<CompositeChange> activityChangeSet =
                    new HashSet<CompositeChange>();
            int count = 0;
            for (EObject referencingScriptContainer : referencingScriptContainerCandidates) {
                if (referencingScriptContainer instanceof Activity) {
                    Activity activityCandidate = (Activity) referencingScriptContainer;
                    ScriptBOMReferenceChangeFactory changeFactory =
                            new ScriptBOMReferenceChangeFactory(
                                    activityCandidate, getArguments(), element);
                    //XPD-4936: Performance improvement for Script validation
                    //Process Package to which this activity belongs
                    Package activityCandidatePackage =
                            Xpdl2ModelUtil.getPackage(activityCandidate);
                    if (activityCandidatePackage != null) {
                        //check if cache exists
                        PackageScopeEnumCache packageScopeEnumCache =
                                packageScopeEnumCacheMap
                                        .get(activityCandidatePackage);
                        if (packageScopeEnumCache == null) {
                            //create if not found
                            packageScopeEnumCache =
                                    new PackageScopeEnumCache(
                                            activityCandidatePackage);
                            //store cache
                            packageScopeEnumCacheMap
                                    .put(activityCandidatePackage,
                                            packageScopeEnumCache);
                        }
                        changeFactory
                                .setPackageScopeEnumCache(packageScopeEnumCache);
                    }
                    List<ScriptBOMReferenceChange> scriptChanges =
                            changeFactory.getChanges();
                    if (scriptChanges != null && !scriptChanges.isEmpty()) {
                        Set<ScriptBOMReferenceChange> scriptChangeSet =
                                new HashSet<ScriptBOMReferenceChange>();
                        for (ScriptBOMReferenceChange scriptBOMReferenceChange : scriptChanges) {
                            if (scriptBOMReferenceChange
                                    .containsModifications()) {
                                scriptChangeSet.add(scriptBOMReferenceChange);
                            }
                        }
                        if (!scriptChangeSet.isEmpty()) {
                            String message = Messages.ScriptBOMElementRefactorParticipant_RenameScriptsForActivity;
                            String activityName = activityCandidate.getName();
                            CompositeChange activityCompositeChange =
                                    new CompositeChange(String.format(message,
                                            activityName), scriptChangeSet
                                            .toArray(new Change[scriptChangeSet
                                                    .size()]));
                            activityChangeSet.add(activityCompositeChange);
                        }
                    }
                } else if (referencingScriptContainer instanceof Transition) {
                    Transition transitionCandidate = (Transition) referencingScriptContainer;
                    ScriptBOMReferenceChangeFactory changeFactory =
                            new ScriptBOMReferenceChangeFactory(
                                    transitionCandidate, getArguments(), element);
                    List<ScriptBOMReferenceChange> scriptChanges =
                            changeFactory.getChanges();
                    if (scriptChanges != null && !scriptChanges.isEmpty()) {
                        Set<ScriptBOMReferenceChange> scriptChangeSet =
                                new HashSet<ScriptBOMReferenceChange>();
                        for (ScriptBOMReferenceChange scriptBOMReferenceChange : scriptChanges) {
                            if (scriptBOMReferenceChange
                                    .containsModifications()) {
                                scriptChangeSet.add(scriptBOMReferenceChange);
                            }
                        }
                        if (!scriptChangeSet.isEmpty()) {
                            String message = Messages.ScriptBOMElementRefactorParticipant_RenameScriptsForSequenceFlow;
                            String activityName = transitionCandidate.getName();
                            CompositeChange activityCompositeChange =
                                    new CompositeChange(String.format(message,
                                            activityName), scriptChangeSet
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
                return new CompositeChange(Messages.ScriptBOMElementRefactorParticipant_RenamingActivitiesScripts,
                        activityChangeSet.toArray(new Change[activityChangeSet
                                .size()]));
            }
        }

        return new NullChange(Messages.ScriptBOMElementRefactorParticipant_NoScriptElementsAffected);
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
