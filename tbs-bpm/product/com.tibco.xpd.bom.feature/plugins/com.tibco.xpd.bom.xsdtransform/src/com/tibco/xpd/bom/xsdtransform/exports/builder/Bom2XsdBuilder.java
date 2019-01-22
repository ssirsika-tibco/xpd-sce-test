/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.exports.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.api.Bom2XsdCachedTransformer;
import com.tibco.xpd.bom.xsdtransform.api.Bom2XsdSourceValidationType;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.export.progress.Bom2XsdMonitorMessages;
import com.tibco.xpd.bom.xsdtransform.export.progress.Messages;
import com.tibco.xpd.bom.xsdtransform.exports.BOM2XSDTransformer;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.AbstractIncrementalProjectBuilder;

/**
 * Builder to generate XSD from BOM.
 * 
 * @author rsomayaj
 * 
 */
@SuppressWarnings("restriction")
public class Bom2XsdBuilder extends AbstractIncrementalProjectBuilder {

    private static final String XSD_FILE_EXTN = "xsd"; //$NON-NLS-1$

    public static final String BUILDER_ID = Activator.PLUGIN_ID + ".xsdBuilder"; //$NON-NLS-1$

    /**
     * Kind of special folder used for delivering Xsds for Boms
     */
    public static final String BOM_2_XSD_SPECIAL_FOLDER_KIND = "bom2xsd"; //$NON-NLS-1$

    private final Logger LOG = Activator.getDefault().getLogger();

    /**
     * 
     */
    public Bom2XsdBuilder() {
    }

    /**
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param kind
     * @param args
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    protected IProject[] doBuild(int kind, Map<?, ?> args,
            IProgressMonitor monitor) throws CoreException {

        LOG.trace(Logger.CATEGORY_BUILD,
                "BUILD STARTED: Bom2XsdBuilder:" + getProject()); //$NON-NLS-1$

        /*
         * XPD-7540: Remove problem markers that we have added against bom files
         * before re-generating the xsd
         */
        removeUnexpectedErrorProblemMarker();
        long start = System.currentTimeMillis();
        IResourceDelta delta = getDelta(getProject());

        if (delta != null && !isConfigUpdated(delta)) {
            incrementalBuild(delta, monitor);
        } else {
            fullbuild(monitor);
        }

        LOG.trace(Logger.CATEGORY_BUILD, String
                .format("BUILD FINISHED: Bom2XsdBuilder: %s (took %d ms)", //$NON-NLS-1$
                        getProject(),
                        (System.currentTimeMillis() - start)));
        return null;
    }

    /**
     * Perform a full build. In our case this means, perform a build on all
     * BOM's in any special folder in the project whose derived xsd's are out of
     * date.
     * 
     * 
     * @param monitor
     */
    private void fullbuild(final IProgressMonitor monitor) {

        sysout("==> " + this.getClass().getSimpleName() + ".fullBuild()"); //$NON-NLS-1$ //$NON-NLS-2$
        long start = System.currentTimeMillis();

        /* Get all BOMs in all special folders in the project. */
        Set<IFile> allBomFiles = getAllBomsInProject();

        /*
         * Check if they have outof date xsd's and build from top-level BOMs
         * down.
         */
        checkAndBuildBomsWithOutOfDateXsds(monitor, allBomFiles, false);

        sysout("<== " + this.getClass().getSimpleName() + ".fullBuild() Took: " //$NON-NLS-1$ //$NON-NLS-2$
                + ((System.currentTimeMillis() - start) / 1000.0f));

        return;
    }

    /**
     * The list of BOM's is then filtered to the set of top-level BOM's (boms
     * that are not referenced from other BOM's because the OAW transform will
     * traves and output the referenced BOMs already (so don't need to transform
     * referenced BOMs explcitly)
     * 
     * @param monitor
     * @param bomFiles
     * @param forcedOutOfDate
     *            Treat the bomFiles as out-of-date regardless of timestamp of
     *            xsd's. This is used for incremental builds where we KNOW the
     *            source BOM is changing so nominally the xsd MUST need to be
     *            re-built.
     */
    private void checkAndBuildBomsWithOutOfDateXsds(
            final IProgressMonitor monitor, Set<IFile> bomFiles,
            boolean forcedOutOfDate) {

        try {
            final int ticksForCheckAndProp = 10;
            final int ticksForResolveRoot = 5;
            final int ticksForTransformFiles = 80;
            final int ticksForRemoveUnnecessaryXsds = 5;

            monitor.beginTask("", ticksForCheckAndProp + ticksForResolveRoot //$NON-NLS-1$
                    + ticksForTransformFiles + ticksForRemoveUnnecessaryXsds);

            /*
             * Filter the list to the set of BOM's whose generated xsd's are out
             * of date or don't exist) (checkAndPrepareResourceForBuild() will
             * also clean up xsds for any file that needs build.
             */
            monitor.subTask(Bom2XsdMonitorMessages
                    .getBom2XsdProgressText(Messages.Bom2XsdBuilder_AscertainingOutOfDateXsds_message));

            /*
             * Sid XPD-3965: Collect the actual xsd files that will be rebuilt
             * rather than just file names (so we can correctly reload their
             * working copies after transform even if they are in different
             * projects.
             */
            Set<IFile> rebuiltXsdFiles = new HashSet<IFile>();

            Set<IFile> validBomsWithOutOfDateXsds =
                    getValidBomsWithOutOfDateXsds(bomFiles,
                            forcedOutOfDate,
                            rebuiltXsdFiles);

            monitor.worked(ticksForCheckAndProp);

            if (!validBomsWithOutOfDateXsds.isEmpty()) {
                /*
                 * Reduce the set of BOM's we need to build to ONLY the
                 * top-level ones (i.e. the BOMs that are not referenced by
                 * another BOM in the list.
                 */
                monitor.subTask(Bom2XsdMonitorMessages
                        .getBom2XsdProgressText(Messages.Bom2XsdBuilder_AscertainingRootModels_message));

                Set<IFile> topLevelFiles =
                        ascertainRootFiles(validBomsWithOutOfDateXsds);

                monitor.worked(ticksForResolveRoot);

                /*
                 * And finally - transform the top-level files (OAW transform
                 * will recursively transform the referenced BOM's)
                 */
                monitor.subTask(Bom2XsdMonitorMessages
                        .getBom2XsdProgressText(Messages.Bom2XsdBuilder_TransformingRootModels_message));

                long start = System.currentTimeMillis();
                sysout("==> transformBOMFiles()"); //$NON-NLS-1$

                transformBomFiles(topLevelFiles, new SubProgressMonitor(
                        monitor, ticksForTransformFiles));

                sysout("<== transformBOMFiles() Took: " //$NON-NLS-1$
                        + ((System.currentTimeMillis() - start) / 1000.0f));

                /*
                 * If we actually did some building the the OAW transform will
                 * have output XSDs for any referenced external project BOM into
                 * THIS project.
                 * 
                 * These xsd's are spurious in that (a) they are NOT used by
                 * XPDL2WSDL (which always picks up the xsd form the originating
                 * BOMs project) and (b) They don't get updated when the
                 * external BOM changes.
                 * 
                 * So to save confusion all round we will remove any that have
                 * been created.
                 */
                monitor.subTask(Bom2XsdMonitorMessages
                        .getBom2XsdProgressText(Messages.Bom2XsdBuilder_RemovingExternallyRefdXsds_message));

                removeUnnecessaryBom2Xsds();

                /*
                 * Reload and index the generated XSDs. This is required because
                 * the Xpdl2Wsdl transform relies on the workingcopy/indexes for
                 * the generated bom2xsd files AND these won't necessarily get
                 * reloaded until AFTER Xpdl2Wsdl has already been done (i.e.
                 * these are not reloaded until workspace-modify operation
                 * events are received and build may happen that way).
                 * 
                 * Sid XPD-3965: Reload the actual xsd files (used to look for
                 * all xsdFileNames in the current project - but of course, some
                 * could be in different projects from original bom that changed
                 * (for references to/from other projects).
                 */
                IndexerServiceImpl service =
                        (IndexerServiceImpl) XpdResourcesPlugin.getDefault()
                                .getIndexerService();

                for (IFile xsdFile : rebuiltXsdFiles) {
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xsdFile);
                    if (wc != null) {
                        wc.reLoad();
                        service.index(wc);
                    }
                }

                monitor.worked(ticksForRemoveUnnecessaryXsds);
            }

        } finally {
            monitor.done();
        }

        return;
    }

    /**
     * Filter the list to the set of BOM's whose generated xsd's are out of date
     * or don't exist) (checkAndPrepareResourceForBuild() will also clean up
     * xsds for any file that needs build.
     * 
     * @param bomFiles
     * @param forcedOutOfDate
     * @param rebuildingXsdNames
     *            Will be assigned the xsd files to be generated from this Bom
     *            file IF it is valid and it's xsds are out of date.
     * @return The bomFiles list reduce to those BOM's that are valid and that
     *         have out-of-date derived xsds
     */
    private Set<IFile> getValidBomsWithOutOfDateXsds(Set<IFile> bomFiles,
            boolean forcedOutOfDate, Set<IFile> rebuildingXsdFiles) {
        Set<IFile> validBomsWithOutOfDateXsds = new HashSet<IFile>();

        /*
         * For performance, this allows us to cached validation error-check
         * results when there may be multiple ref's to same BOM's
         */
        HashMap<IResource, Boolean> checkForErrorsResultCache =
                new HashMap<IResource, Boolean>();

        long start2 = System.currentTimeMillis();
        sysout("==> checkAndPrepareResourceForBuild's: "); //$NON-NLS-1$

        for (IFile bomFile : bomFiles) {
            if (checkAndPrepareResourceForBuild(bomFile,
                    forcedOutOfDate,
                    checkForErrorsResultCache,
                    rebuildingXsdFiles)) {
                validBomsWithOutOfDateXsds.add(bomFile);
            }
        }

        sysout("<== checkAndPrepareResourceForBuild's()" + " Took: " //$NON-NLS-1$ //$NON-NLS-2$
                + ((System.currentTimeMillis() - start2) / 1000.0f));

        return validBomsWithOutOfDateXsds;
    }

    /**
     * Reduce the set of BOM's we need to build to ONLY the top-level ones (i.e.
     * the BOMs that are not referenced by another BOM in the list.
     * 
     * @param validBomsWithOutOfDateXsds
     * 
     * @return The boms that can be considered to be the top level root of files
     *         in the set.
     */
    private Set<IFile> ascertainRootFiles(Set<IFile> validBomsWithOutOfDateXsds) {
        long start2 = System.currentTimeMillis();
        sysout("==> TransformHelper.newGetRootSelectedFiles(): "); //$NON-NLS-1$

        Set<IFile> topLevelFiles =
                TransformHelper
                        .getRootBomsForBom2XsdTransform(validBomsWithOutOfDateXsds);

        sysout("<== TransformHelper.newGetRootSelectedFiles()" + " Took: " //$NON-NLS-1$ //$NON-NLS-2$
                + ((System.currentTimeMillis() - start2) / 1000.0f));
        return topLevelFiles;
    }

    /**
     * Create a list of all BOM's in all BOM folders
     * 
     * @return All BOM's in special folders in the project.
     */
    private Set<IFile> getAllBomsInProject() {
        final Set<IFile> bomFiles = new HashSet<IFile>();

        List<IFolder> bomFolders = getBomSpecialFolder(getProject());

        for (IFolder bomFolder : bomFolders) {
            if (bomFolder.isAccessible()) {
                try {
                    bomFolder.accept(new IResourceVisitor() {

                        @Override
                        public boolean visit(IResource resource)
                                throws CoreException {
                            if (resource instanceof IFile
                                    && BOMResourcesPlugin.BOM_FILE_EXTENSION
                                            .equalsIgnoreCase(resource
                                                    .getFileExtension())) {
                                bomFiles.add((IFile) resource);

                                return false;
                            }

                            return true;
                        }
                    });
                } catch (CoreException e) {
                    LOG.error(e);
                    throw new RuntimeException(e);
                }
            }
        }
        return bomFiles;
    }

    /**
     * @param delta
     * @param monitor
     */
    private void incrementalBuild(IResourceDelta delta,
            final IProgressMonitor monitor) {
        long start = System.currentTimeMillis();

        try {

            final Set<IFile> bomsAddedOrChanged = new HashSet<IFile>();

            /*
             * Remove derivied artifacts for BOM's and gather set of bom's added
             * or changed.
             */
            delta.accept(new IResourceDeltaVisitor() {

                @Override
                public boolean visit(IResourceDelta delta) throws CoreException {
                    int kind = delta.getKind();
                    IResource resource = delta.getResource();

                    if (kind == IResourceDelta.REMOVED) {
                        handleResourceRemoved(monitor, resource);

                    } else if (kind == IResourceDelta.ADDED
                            || kind == IResourceDelta.CHANGED) {

                        if (resource instanceof IFile
                                && isBOMInASpecialFolder((IFile) resource)) {
                            bomsAddedOrChanged.add((IFile) resource);
                        }
                    }
                    checkCancel(monitor, true);
                    return true;
                }
            });

            /*
             * Go thru the added / changed BOMs; clean up derived artifacts from
             * each one and transform those which are top-level (not referenced
             * by other things in the delta - because referenced boms will be
             * output by the transform anyway.
             */
            if (!bomsAddedOrChanged.isEmpty()) {
                long start2 = System.currentTimeMillis();

                /*
                 * We need to rebuild any BOM files that reference the one(s)
                 * that has changed.
                 * 
                 * Get all BOMs in project and find the ones dependent on the
                 * ones added / changed.
                 */
                Set<IFile> completeSet = new HashSet<IFile>();
                /*
                 * XPD-3109: for each bom added/changed get all the boms
                 * dependent on it (from this project and referencing projects)
                 */
                for (IFile bomFile : bomsAddedOrChanged) {
                    getDependentBoms(bomFile, completeSet);
                }
                checkCancel(monitor, true);

                sysout(this.getClass().getSimpleName()
                        + ".incremental(Add Dependents) Took: " //$NON-NLS-1$
                        + ((System.currentTimeMillis() - start2) / 1000.0f));

                /*
                 * Force build on all BOMs changed and their dependencies. One
                 * reason to force build is that we may have affected something
                 * in a dependent bom AND we may have replaced a BOM with an
                 * older revision from local history (in which case the xsd may
                 * appear to be up to date when actually it should be rebuilt).
                 */
                checkAndBuildBomsWithOutOfDateXsds(monitor, completeSet, true);
            }

        } catch (CoreException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }

        sysout(this.getClass().getSimpleName() + ".incremental() Took: " //$NON-NLS-1$
                + ((System.currentTimeMillis() - start) / 1000.0f));
        return;
    }

    /**
     * Given a BOM file look for BOM's that are dependent upon it and add them
     * to the list.
     * 
     * @param bomFile
     * @param completeSet
     * @return the set of all dependent boms
     */
    private Set<IFile> getDependentBoms(IFile bomFile, Set<IFile> completeSet) {

        if (bomFile != null && !completeSet.contains(bomFile)) {
            completeSet.add(bomFile);
            for (IResource res : WorkingCopyUtil.getAffectedResources(bomFile)) {
                if (res instanceof IFile && isBOM((IFile) res)) {
                    completeSet.add((IFile) res);
                    completeSet.addAll(getDependentBoms((IFile) res,
                            completeSet));
                }
            }
        }

        return completeSet;
    }

    /***
     * 
     * @param file
     * @return
     */
    private boolean isBOM(IFile file) {
        if (BOMResourcesPlugin.BOM_FILE_EXTENSION.equalsIgnoreCase(file
                .getFileExtension())) {
            return true;
        }
        return false;
    }

    /**
     * If the BOM file is removed - delete the XSD corresponding to the BOM
     * file.
     * 
     * @param monitor
     * @param resource
     * @throws CoreException
     */
    private void handleResourceRemoved(IProgressMonitor monitor,
            IResource resource) throws CoreException {
        if (resource instanceof IFile
                && isBOMInASpecialFolder((IFile) resource)) {
            IFile bomFile = (IFile) resource;
            deleteRelevantXSDFiles(bomFile);
        }

        return;
    }

    /**
     * Uses the indexer to find the XSD files that are generated for a given BOM
     * File AND that exist.
     * 
     * @param bomFile
     * @return a set of XSD {@link IFile}s for a given BOM file.
     */
    private Set<IFile> getCurrentXsdsGeneratedFromBom(IFile bomFile) {
        IProject project = getProject();

        return Bom2XsdUtil.getCurrentXsdsGeneratedFromBom(project, bomFile);
    }

    /**
     * Checks whether the given resource is a BOM file that requires a build
     * (one or more of the xsd's that should be generated from it are out of
     * date or don't exist) and returns <code>true</code> if so.
     * <p>
     * If the file does not require build returns <code>false</code>
     * <p>
     * In any case, if the file requires a build (or has valdiation errors) then
     * the previously xsd's generated from it are deleted.
     * 
     * @param monitor
     * @param bomFile
     * @param forcedOutOfDate
     *            Treat the bomFiles as out-of-date regardless of timestamp of
     *            xsd's. This is used for incremental builds where we KNOW the
     *            source BOM is changing so nominally the xsd MUST need to be
     *            re-built.
     * @param checkErrorResultCache
     * @param rebuildingXsdFiles
     *            Will be assigned the xsd files to be generated from this Bom
     *            file IF it is valid and it's xsds are out of date.
     * 
     * @return <code>true</code> if it's a BOM file that requires building (i.e
     *         a BOM that passes validations AND whose derived xsd(s) are otu of
     *         date)
     */
    private boolean checkAndPrepareResourceForBuild(IFile bomFile,
            boolean forcedOutOfDate,
            HashMap<IResource, Boolean> checkErrorResultCache,
            Set<IFile> rebuildingXsdFiles) {
        try {
            boolean hasPassedValidations =
                    BOM2XSDTransformer.hasPassedBom2XsdValidation(bomFile,
                            false,
                            checkErrorResultCache);

            /*
             * If there are any errors on the BOM file or if it hasn't been
             * marked as a required destination, the transformer does not
             * generate the XSD. In that case, it is necessary to delete the XSD
             * to propagate the change to the WSDL Builder.
             */
            if (!hasPassedValidations) {
                /*
                 * delete all the XSD files related to the BOM files, if there
                 * exists any
                 */
                deleteRelevantXSDFiles(bomFile);

            } else {
                /*
                 * This set is evaluated from the BOM file as to what XSDs it
                 * will create (SIMPLE NAMES).
                 */
                Collection<String> fileNamesForModel =
                        XSDUtil.getOutputXsdFileNames(bomFile);

                /*
                 * Find out the XSD Names generated for the BOM - using the
                 * indexer.
                 */
                boolean areXsdsFileNamesDifferentOrOutOfDate = forcedOutOfDate;

                if (!areXsdsFileNamesDifferentOrOutOfDate) {

                    /*
                     * This set is evaluated from the existing xsds that were
                     * previous generated from this BOM.
                     */
                    Set<IFile> currentXsdsOutputForBom =
                            getCurrentXsdsGeneratedFromBom(bomFile);

                    /* Compare with the list that is got from */
                    areXsdsFileNamesDifferentOrOutOfDate =
                            areXsdFileNamesDifferent(currentXsdsOutputForBom,
                                    fileNamesForModel);

                    /*
                     * If they are the same, then identify the earliest
                     * timestamp of the XSDs is greater the timestamp of the BOM
                     */
                    if (!areXsdsFileNamesDifferentOrOutOfDate) {

                        long bomFileLocalTimestamp =
                                bomFile.getLocalTimeStamp();
                        long xsdTimestamp = Long.MAX_VALUE;
                        boolean currentXsdHasProblemMarker = false;

                        for (IFile xsdFile : currentXsdsOutputForBom) {
                            /*
                             * Check if current xsd has problem marker and if so
                             * always rebuild.
                             */
                            if (!xsdFile.isAccessible()
                                    || IMarker.SEVERITY_ERROR == xsdFile
                                            .findMaxProblemSeverity(XpdConsts.VALIDATION_MARKER_TYPE,
                                                    true,
                                                    IResource.DEPTH_ZERO)) {
                                currentXsdHasProblemMarker = true;
                                break;
                            }

                            if (xsdFile.getLocalTimeStamp() < xsdTimestamp) {
                                xsdTimestamp = xsdFile.getLocalTimeStamp();
                            }
                        }

                        if (currentXsdHasProblemMarker
                                || xsdTimestamp < bomFileLocalTimestamp) {
                            areXsdsFileNamesDifferentOrOutOfDate = true;
                        }
                    }

                }

                if (areXsdsFileNamesDifferentOrOutOfDate) {

                    deleteRelevantXSDFiles(bomFile);
                    /*
                     * Sid XPD-3965: Return ACTUAL files for xsd Files expected
                     * to be generated from this BOM, as they may be in
                     * different project to the project actually being built.
                     * 
                     * This can happen IF for instance a BOM in project1 is
                     * depended on by a BOM in project2. Both will be built when
                     * BOM in project1 changes BUT each into the project for the
                     * source BOM.
                     * 
                     * So when we refresh the working copy / index to ensure
                     * things are up to date before WSDL is generated in same
                     * build cycle (which might happen before working copy is
                     * reloaded via workspace changes) - then we need to refresh
                     * the xsd from the correct project's bom2xsd folder NOT
                     * necessarily the project the builder is working on at the
                     * moment.
                     */
                    IProject bomProject = bomFile.getProject();
                    IFolder bom2XsdFolder =
                            bomProject
                                    .getFolder(Bom2XsdUtil.DEFAULT_BOM_2_XSD_FOLDER);

                    for (String xsdFileName : fileNamesForModel) {
                        IFile xsdFile = bom2XsdFolder.getFile(xsdFileName);
                        rebuildingXsdFiles.add(xsdFile);
                    }

                    return true;
                } else {
                    /* XSD's are up to date. */
                    return false;
                }

            }
        } catch (CoreException e) {
            LOG.error(e);
        }

        return false;
    }

    /**
     * @param monitor
     * @param bomFile
     */
    private void transformBomFiles(Set<IFile> bomFiles, IProgressMonitor monitor) {
        try {
            if (!bomFiles.isEmpty()) {
                IFolder xsdOutputFolder = getXsdOutputFolder(true);
                IPath xsdOutputPath = xsdOutputFolder.getFullPath();

                /*
                 * Create a transformer that takes original XSD type stereotypes
                 * into account and doesn't bother with validation because we've
                 * already done that here.
                 */
                Bom2XsdCachedTransformer transformer =
                        new Bom2XsdCachedTransformer(true,
                                Bom2XsdSourceValidationType.NONE);

                /*
                 * Transform each file in turn (the transaformer will keep
                 * repeatedly referenced BOM's xsd schemas cached so that they
                 * are not output multiple times.
                 */
                monitor.beginTask("", bomFiles.size()); //$NON-NLS-1$

                /*
                 * XPD-3664 XPD-3636 XPD-3697 : validate the schemas in the wsdl
                 * and report problem markers on the generated bom
                 */
                List<IStatus> errors = new ArrayList<IStatus>();
                for (IFile bomFile : bomFiles) {
                    /*
                     * XPD-2569 Use new transform method that explicitly
                     * transforms into bom2xsd.
                     */
                    List<IStatus> validationStatuses =
                            transformer.transformToBom2Xsd(bomFile,
                                    new SubProgressMonitor(monitor, 1));

                    for (IStatus iStatus : validationStatuses) {
                        if (IStatus.ERROR == iStatus.getSeverity()) {
                            errors.add(iStatus);
                        }
                    }
                    if (!errors.isEmpty()) {
                        createProblemMarker(bomFile, xsdOutputPath, errors);
                    }
                    checkCancel(monitor, true);
                }
            }
        } finally {
            monitor.done();
        }

        return;
    }

    /**
     * Create an error problem marker on the given resource.
     * 
     * @param bomFile
     * @param xsdOutputPath
     * @param errors
     */
    private void createProblemMarker(IFile bomFile, IPath xsdOutputPath,
            List<IStatus> errors) {

        if (bomFile.isAccessible()) {
            try {
                IMarker[] findMarkers =
                        bomFile.findMarkers(BOMResourcesPlugin.WSDL_SCHEMA_MARKER_TYPE,
                                false,
                                IResource.DEPTH_ZERO);
                boolean alreadyCreated = false;
                for (IMarker marker : findMarkers) {
                    if (Activator.PLUGIN_ID.equals(marker
                            .getAttribute(IMarker.SOURCE_ID))) {
                        alreadyCreated = true;
                        break;
                    }
                }

                if (!alreadyCreated) {
                    IMarker marker =
                            bomFile.createMarker(BOMResourcesPlugin.WSDL_SCHEMA_MARKER_TYPE);
                    marker.setAttribute(IMarker.MESSAGE,
                            Messages.Bom2XsdBuilder_WsdlInlineSchemaValidation_message);
                    marker.setAttribute(IMarker.SEVERITY,
                            IMarker.SEVERITY_ERROR);
                    marker.setAttribute(IMarker.SOURCE_ID, Activator.PLUGIN_ID);

                    if (errors.size() > 0) {
                        LOG.log(new MultiStatus(
                                Activator.PLUGIN_ID,
                                IStatus.ERROR,
                                errors.toArray(new IStatus[errors.size()]),
                                Messages.Bom2XsdBuilder_WsdlInlineSchemaNotValidated_message,
                                null));

                    }
                }

            } catch (CoreException e) {
            }
        }
    }

    /**
     * Remove problem markers that may have been raised against the bom file(s)
     * 
     * @param bomFile
     * @throws CoreException
     */
    private void removeUnexpectedErrorProblemMarker() throws CoreException {

        Set<IFile> allBomsInProject = getAllBomsInProject();
        for (IFile bomFile : allBomsInProject) {
            IMarker[] markers =
                    bomFile.findMarkers(BOMResourcesPlugin.WSDL_SCHEMA_MARKER_TYPE,
                            false,
                            IResource.DEPTH_ZERO);
            for (IMarker marker : markers) {
                if (Activator.PLUGIN_ID.equals(marker
                        .getAttribute(IMarker.SOURCE_ID))) {
                    marker.delete();
                }
            }
        }
    }

    /**
     * @param queryGeneratedXsdsFromBom
     * @param fileNamesForModel
     * @return <code>true</code> if the XSD file names the BOM thinks of
     *         generating is different from what is already in the workspace.
     */
    private boolean areXsdFileNamesDifferent(
            Set<IFile> currentXsdsOutputForBom,
            Collection<String> fileNamesForModel) {
        if (currentXsdsOutputForBom.size() != fileNamesForModel.size()) {
            return true;
        }

        for (IFile xsdFile : currentXsdsOutputForBom) {
            String xsdName = xsdFile.getName();

            if (!fileNamesForModel.contains(xsdName)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Iterates through the list of XSD files generated for the BOM and deletes
     * them all.
     * 
     * @param bomFile
     * @throws CoreException
     */
    private void deleteRelevantXSDFiles(IFile bomFile) throws CoreException {
        // Uses the indexer to identify the XSD files generated for the given
        // BOM file.
        Set<IFile> xsdFilesFromOutputFolder =
                getCurrentXsdsGeneratedFromBom(bomFile);

        for (IFile xsdFile : xsdFilesFromOutputFolder) {
            xsdFile.delete(true, null);
        }

    }

    /**
     * Transform only BOM files in BOM special folders.
     * 
     * @param resource
     * @return
     */
    private boolean isBOMInASpecialFolder(IFile file) {
        if (BOMResourcesPlugin.BOM_FILE_EXTENSION.equalsIgnoreCase(file
                .getFileExtension())) {

            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(getProject());
            if (projectConfig != null) {
                SpecialFolders specialFolders =
                        projectConfig.getSpecialFolders();
                if (specialFolders != null) {
                    SpecialFolder folderContainer =
                            specialFolders.getFolderContainer(file);

                    if (folderContainer != null) {
                        String specialFolderKind = folderContainer.getKind();

                        if (BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                                .equals(specialFolderKind)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns list of eclipse folders which are marked as special BOM folder
     * for provided project.
     * 
     * @param project
     *            the eclipse project.
     * @return list of special packages folders for the project.
     */
    private List<IFolder> getBomSpecialFolder(IProject project) {
        SpecialFolders specialFolders =
                XpdResourcesPlugin.getDefault().getProjectConfig(project)
                        .getSpecialFolders();
        if (specialFolders != null) {
            return specialFolders
                    .getEclipseIFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        }
        return Collections.emptyList();
    }

    /**
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {

        super.clean(monitor);

        // Delete all the derived resources in the .bom2Xsd folder
        if (isPreferenceToDeleteXsdsOnCleanSet()) {
            IFolder xsdOutputFolder = getXsdOutputFolder(false);

            if (xsdOutputFolder != null && xsdOutputFolder.exists()) {
                IResource[] members = xsdOutputFolder.members();
                for (int mem = 0; mem < members.length; mem++) {
                    // XPD-6655 We need to clean up working copies
                    // when the file is deleted.
                    WorkingCopyUtil
                            .deleteResourceInWorkspaceOperation(members[mem],
                                    monitor);
                }
            }
        } else {
            /*
             * Must delete XSDs derived from BOM's that no longer exist.
             * 
             * Get a set of all the Boms in this project and then all the BOMs
             * that are referenced by them.
             */
            removeUnnecessaryBom2Xsds();
        }
        /*
         * Remove problem markers that may have been raised against the bom
         * file(s)
         */
        removeUnexpectedErrorProblemMarker();
        return;
    }

    /**
     * Remove any xsd from the .bom2xsd folder in the project we are building
     * that is no longer to be derived from any bom file in the project.
     * 
     * TODO This will not be necessary once we change the OAW transform to NOT
     * recurs thru imports
     * 
     */
    private void removeUnnecessaryBom2Xsds() {
        // TODO This will not be necessary once we change the OAW transform to
        // NOT recurs thru imports
        IFolder xsdOutputFolder = getXsdOutputFolder(false);

        if (xsdOutputFolder != null && xsdOutputFolder.exists()) {
            Set<IFile> allBomsInProject = getAllBomsInProject();

            /* Get a set of the xsd's that should be built from these BOMs */
            Set<String> expectedXsds = new HashSet<String>();

            for (IFile bomFile : allBomsInProject) {
                expectedXsds.addAll(XSDUtil.getOutputXsdFileNames(bomFile));
            }

            /*
             * Go thru all the files in the .bom2xsd folder checking for files
             * not expected to be generated from current set of BOMs.
             */
            try {
                IResource[] members = xsdOutputFolder.members();

                for (int mem = 0; mem < members.length; mem++) {
                    IResource member = members[mem];

                    if (member.isAccessible()) {
                        if (XSD_FILE_EXTN.equals(member.getFileExtension())) {
                            String xsdName = member.getName();

                            if (!expectedXsds.contains(xsdName)) {
                                /*
                                 * This XSD is no longer generated by the Boms
                                 * in project so remove it.
                                 */
                                try {
                                    // XPD-6655 We need to clean up working
                                    // copies
                                    // when the file is deleted.
                                    WorkingCopyUtil
                                            .deleteResourceInWorkspaceOperation(member,
                                                    null);
                                } catch (CoreException e) {
                                    Activator
                                            .getDefault()
                                            .getLogger()
                                            .error(e,
                                                    "Failed deleting unnecessary Bom2Xsd: " //$NON-NLS-1$
                                                            + member.getFullPath());
                                }

                            }
                        }
                    }

                }
            } catch (CoreException e) {
                Activator.getDefault().getLogger()
                        .error(e, "Failed Bom2Xsd folder members: "); //$NON-NLS-1$
            }
        }

        return;
    }

    /**
     * Preference on Business Object Modeler ->Business Object Model Generator
     * -> Re-generate BOM on project clean
     * 
     * @return
     */
    private boolean isPreferenceToDeleteXsdsOnCleanSet() {
        IPreferenceStore store =
                com.tibco.xpd.bom.gen.ui.Activator.getDefault()
                        .getPreferenceStore();
        return store
                .getBoolean(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_BOMXSD_GENERATION);
    }

    /**
     * Output to system console (can be turned off/on)
     * 
     * @param msg
     */
    private void sysout(String msg) {
        // System.out.println(msg);
        return;
    }

    /**
     * Gets eclipse IFolder which is marked as the BOM to XSD special folder for
     * the project. If the special folder is not set (and project has required
     * nature) the default one will be created and returned.
     * 
     * @param create
     *            true if the folder should be created if don't exist.
     * @return eclipse IFolder which is marked as the BOM to XSD special folder.
     *         If special folder doesn't exist (and project has required nature)
     *         and the create parameter is true, the default folder will be
     *         created otherwise null will be returned.
     */
    private IFolder getXsdOutputFolder(boolean create) {
        SpecialFolder sf =
                SpecialFolderUtil.getSpecialFolderOfKind(getProject(),
                        BOM_2_XSD_SPECIAL_FOLDER_KIND);
        if (sf == null || sf.getFolder() == null || !sf.getFolder().exists()) {
            if (create) {
                sf =
                        SpecialFolderUtil
                                .getCreateSpecialFolderOfKind(getProject(),
                                        BOM_2_XSD_SPECIAL_FOLDER_KIND,
                                        Bom2XsdUtil.DEFAULT_BOM_2_XSD_FOLDER);
            }
        }

        return sf == null ? null : sf.getFolder();
    }

    /**
     * @see com.tibco.xpd.validation.AbstractIncrementalProjectBuilder#shouldRun()
     * 
     * @return
     */
    @Override
    protected boolean shouldRun() {

        boolean shouldRun = super.shouldRun();
        /*
         * TODO: return true for pre compile project if we want to ensure the
         * generated artefacts be up to date
         */
        IProject project = getProject();
        boolean isPreCompiled = ProjectUtil.isPrecompiledProject(project);
        if (isPreCompiled) {

            shouldRun = false;
        }
        return shouldRun;
    }
}
