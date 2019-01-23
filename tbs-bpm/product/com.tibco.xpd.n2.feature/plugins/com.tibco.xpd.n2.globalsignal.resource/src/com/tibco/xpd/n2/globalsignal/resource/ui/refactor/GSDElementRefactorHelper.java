/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.refactor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.indexer.ActivityToGlobalSignalIndexer;
import com.tibco.xpd.process.js.parser.util.ScriptRefactorParserUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Helper class for refactoring of references to GSD elements.
 * 
 * @author sajain
 * @since May 13, 2015
 */
public class GSDElementRefactorHelper {

    /**
     * Returns the activities that can possibly reference the modified Object.
     * 
     * @param eObject
     * 
     * @return The activities that can possibly reference the modified Object.
     */
    public static Set<Activity> getReferencingActivities(EObject eObject) {

        if (eObject != null) {

            GlobalSignal gs = null;

            if (eObject instanceof GlobalSignal) {

                gs = (GlobalSignal) eObject;

            } else if (eObject instanceof PayloadDataField) {

                EObject eContainer = eObject.eContainer();

                if (eContainer instanceof GlobalSignal) {

                    gs = (GlobalSignal) eContainer;
                }
            }

            Set<Activity> referencingScriptContainerCandidates =
                    queryReferencingActivities(gs);

            return referencingScriptContainerCandidates;
        }
        return Collections.emptySet();
    }

    /**
     * This method is to get all the activities that reference the specified
     * Global signal.
     * 
     * @param gs
     *            Global signal.
     * 
     * @return All the activities that reference the specified Global signal.
     */
    private static Set<Activity> queryReferencingActivities(GlobalSignal gs) {

        /*
         * Get global signal project.
         */
        IProject gsdProject = WorkingCopyUtil.getProjectFor(gs);

        /*
         * Get projects referencing this global signal project.
         */
        Set<IProject> referecingProjects =
                ProjectUtil.getReferencingProjectsHierarchy(gsdProject, null);

        Set<Activity> allActivitiesReferencingGlobalSignal =
                new HashSet<Activity>();

        /*
         * Go through all referencing projects and collect activities which
         * refer the specified global signal.
         */
        for (IProject eachReferencingProject : referecingProjects) {

            /*
             * Get all process packages folders from each project.
             */
            List<SpecialFolder> allProcessPackagesFolder =
                    SpecialFolderUtil
                            .getAllSpecialFoldersOfKind(eachReferencingProject,
                                    Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

            if (null != allProcessPackagesFolder
                    && !allProcessPackagesFolder.isEmpty()) {

                for (SpecialFolder eachSpecialFolder : allProcessPackagesFolder) {

                    try {

                        /*
                         * Get all resources from each process packages folder.
                         */
                        IResource[] allResources =
                                eachSpecialFolder.getFolder().members();

                        for (IResource eachResource : allResources) {

                            if (eachResource instanceof IFile
                                    && eachResource.getFileExtension() != null
                                    && eachResource
                                            .getFileExtension()
                                            .endsWith(Xpdl2ResourcesConsts.XPDL_EXTENSION)) {

                                WorkingCopy wc =
                                        WorkingCopyUtil
                                                .getWorkingCopy(eachResource);

                                if (wc != null
                                        && wc.getRootElement() instanceof Package) {

                                    /*
                                     * Get package from each xpdl.
                                     */
                                    Package pkg =
                                            (Package) (wc.getRootElement());

                                    /*
                                     * Get all processes from each package.
                                     */
                                    List<Process> allProcesssesInPackage =
                                            pkg.getProcesses();

                                    if (null != allProcesssesInPackage
                                            && !allProcesssesInPackage
                                                    .isEmpty()) {

                                        for (Process eachProcess : allProcesssesInPackage) {

                                            /*
                                             * Get all activities from each
                                             * process.
                                             */
                                            Collection<Activity> allActivitiesInProcess =
                                                    Xpdl2ModelUtil
                                                            .getAllActivitiesInProc(eachProcess);

                                            if (null != allActivitiesInProcess
                                                    && !allActivitiesInProcess
                                                            .isEmpty()) {

                                                for (Activity eachActivity : allActivitiesInProcess) {

                                                    IndexerItemImpl criteria =
                                                            new IndexerItemImpl();
                                                    criteria.setName(eachActivity
                                                            .getName());

                                                    criteria.set(IndexerServiceImpl.ATTRIBUTE_NAME,
                                                            eachActivity
                                                                    .getName());

                                                    Collection<IndexerItem> query =
                                                            XpdResourcesPlugin
                                                                    .getDefault()
                                                                    .getIndexerService()
                                                                    .query(ActivityToGlobalSignalIndexer.ID,
                                                                            criteria);

                                                    for (IndexerItem indexerItem : query) {

                                                        if (indexerItem
                                                                .getURI()
                                                                .equals(ProcessUIUtil
                                                                        .getURIString(eachActivity,
                                                                                true))) {

                                                            String referencedGsQualifiedName =
                                                                    indexerItem
                                                                            .get(ActivityToGlobalSignalIndexer.INDEXER_ATTRIBUTE_GS_QUALIFIED_NAME);

                                                            String gsQualifiedNameString =
                                                                    GlobalSignalUtil
                                                                            .getGlobalSignalQualifiedName(gs);

                                                            if (referencedGsQualifiedName != null
                                                                    && gsQualifiedNameString != null
                                                                    && referencedGsQualifiedName
                                                                            .equals(gsQualifiedNameString)) {

                                                                allActivitiesReferencingGlobalSignal
                                                                        .add(eachActivity);

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } catch (CoreException e) {

                        GsdResourcePlugin.getDefault().getLogger().error(e);
                        ;
                    }
                }
            }
        }

        return allActivitiesReferencingGlobalSignal;
    }

    @SuppressWarnings("restriction")
    public static String replaceReferenceChangesForElement(String strScript,
            String oldName, String newName, EObject element,
            EObject scriptContainer, String scriptType,
            IVarNameResolver varNameResolver) {

        RefactoringInfo refactoringInfo =
                new RefactoringInfo(element, oldName, newName);

        HashMap<String, RefactoringInfo> oldNameToRefactorInfoMap =
                new HashMap<>();

        oldNameToRefactorInfoMap.put(oldName, refactoringInfo);

        return ScriptRefactorParserUtil.replaceDataRefByName(strScript,
                oldNameToRefactorInfoMap);
    }
}