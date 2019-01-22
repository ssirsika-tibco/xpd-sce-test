/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.bom.gen.BOMGenActivator;
import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension.GenerationMode;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.internal.preferences.BpmPreferenceUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.AbstractIncrementalProjectBuilder;

/**
 * BOM Generator project builder.
 * 
 * @author njpatel
 * 
 */
public class BOMGenProjectBuilder extends AbstractIncrementalProjectBuilder {

    private static boolean NO_FULL_BUILDS = false;

    public static void setNoFullBuilds(boolean noFullBuilds) {
        synchronized (BOMGenProjectBuilder.class) {
            NO_FULL_BUILDS = noFullBuilds;
        }
    }

    private static final BOMGeneratorHelper bomGenHelper = BOMGeneratorHelper
            .getInstance();

    /**
     * BOM Generator project builder id
     */
    public static String ID = "com.tibco.xpd.bom.gen.bomGenBuilder"; //$NON-NLS-1$

    @Override
    protected IProject[] doBuild(int kind, Map<?, ?> args,
            IProgressMonitor monitor) throws CoreException {
        IProject[] ret = null;

        /*
         * In debug mode all extensions will generate projects in an incremental
         * way. In normal (non-debug) mode only extensions marked as INCREMENTAL
         * will be built incrementally.
         */
        final BOMGenerator2Extension[] strategies =
                BpmPreferenceUtil.isInBpmDebugMode() ? BOMGenerator2ExtensionHelper
                        .getInstance().getExtensions()
                        : BOMGenerator2ExtensionHelper.getInstance()
                                .getExtensions(GenerationMode.INCREMENTAL);

        TRACE("Start of BOMGenProjectBuilder (%d generators registered)...", //$NON-NLS-1$
                strategies.length);
        long start = System.currentTimeMillis();
        if (strategies != null && strategies.length > 0) {

            /*
             * Sid XPD-1605: prepare contributions
             */
            try {
                for (BOMGenerator2Extension ext : strategies) {
                    ext.getGenerator().prepareForBuildCycle(getProject(),
                            kind == INCREMENTAL_BUILD ? BuildType.INCREMENTAL
                                    : BuildType.FULL);
                }

                IResourceDelta delta = getDelta(getProject());
                if (kind == FULL_BUILD
                        || (!NO_FULL_BUILDS && isFullBuildRequired(delta))) {
                    // Full build
                    doFullBuild(args, strategies, monitor);
                } else if (delta.getAffectedChildren().length > 0) {
                    // Incremental build
                    doIncrementalBuild(args, strategies, monitor);
                }
                /*
                 * Return all referenced projects (the full hierarchy) from the
                 * current project so that models can be rebuilt if any
                 * dependent models have changed
                 */
                Set<IProject> projects =
                        ProjectUtil
                                .getReferencedProjectsHierarchy(getProject(),
                                        null);

                if (!projects.isEmpty()) {
                    /*
                     * Remove any projects that are generated - don't want the
                     * builder to be activated when a change is made to the
                     * generated project as it will only override the generated
                     * project.
                     */
                    for (Iterator<IProject> iter = projects.iterator(); iter
                            .hasNext();) {
                        if (isGeneratedProject(iter.next())) {
                            iter.remove();
                        }
                    }
                    ret = projects.toArray(new IProject[projects.size()]);
                }

            } finally {
                /*
                 * Sid XPD-1605: Unprepare the contributions
                 */
                for (BOMGenerator2Extension ext : strategies) {
                    ext.getGenerator().buildCycleComplete(getProject());
                }
            }
        }
        TRACE("Completed BOMGenProjectBuilder in %d ms.", //$NON-NLS-1$ 
                System.currentTimeMillis() - start);
        return ret;
    }

    @Override
    /**
     * Call clean on all generator strategies to remove all generated
     * artifacts.
     * Clean will be called on all strategies (not only  {@link BOMGenerator2Extension#GenerationMode#INCREMENTAL}).
     */
    protected void clean(IProgressMonitor monitor) throws CoreException {
        TRACE("Clean triggered..."); //$NON-NLS-1$
        long start = System.currentTimeMillis();
        BOMGenerator2Extension[] allStrategies =
                BOMGenerator2ExtensionHelper.getInstance().getExtensions();
        /*
         * Sid XPD-1605: prepare contributions
         */
        try {
            for (BOMGenerator2Extension ext : allStrategies) {
                ext.getGenerator().prepareForBuildCycle(getProject(),
                        BuildType.CLEAN);
            }

            List<IFile> files = getAllBomFiles(getProject());
            if (!files.isEmpty()) {
                bomGenHelper.clean(allStrategies, files, monitor);
            }

        } finally {
            /*
             * Sid XPD-1605: Unprepare the contributions
             */
            for (BOMGenerator2Extension ext : allStrategies) {
                ext.getGenerator().buildCycleComplete(getProject());
            }
        }

        TRACE("Clean complete in %d ms.", //$NON-NLS-1$ 
                System.currentTimeMillis() - start);
    }

    /**
     * Run a full build
     * 
     * @param args
     *            build arguments
     * @param strategies
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    private void doFullBuild(Map<?, ?> args,
            final BOMGenerator2Extension[] strategies,
            final IProgressMonitor monitor) throws CoreException {
        TRACE("Running full build..."); //$NON-NLS-1$
        /*
         * Build all BOM files in the project. This should process all .bom
         * files in case a special folder was removed in which case previously
         * generated projects from those special folders should be cleaned.
         */
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(getProject());

        FullBuildVisitor visitor = new FullBuildVisitor(config);
        getProject().accept(visitor, IResource.NONE);
        checkCancel(monitor, true);

        // Call Full Build
        List<IFile> filesToBuild = visitor.getFilesToBuild();

        /*
         * Sid XPD-1605: Create the strategy to GeneratorData map 'up-front'.
         * This stores the set of projects already created this cycle so that
         * generator.initialize() does not keep recreating projects for BOMs
         * that are dependencies of multiple other files.
         */
        Map<BOMGenerator2Extension, GeneratorData> strategyToGenDataMap =
                bomGenHelper.createStrategyToGeneratorDataMap(strategies,
                        new ArrayList<IFile>(),
                        BuildType.FULL);

        // If boms have been removed then remove all generated projects
        if (!visitor.getFilesToClean().isEmpty()) {
            bomGenHelper.clean(BOMGenerator2ExtensionHelper.getInstance()
                    .getExtensions(), visitor.getFilesToClean(), monitor);
        }
        checkCancel(monitor, true);

        bomGenHelper.doBuild(getProject(),
                strategies,
                filesToBuild,
                BuildType.FULL,
                strategyToGenDataMap,
                monitor);
        TRACE("Full build complete."); //$NON-NLS-1$
    }

    /**
     * Run an incremental build.
     * 
     * @param args
     *            build arguments
     * @param strategies
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    private void doIncrementalBuild(Map<?, ?> args,
            final BOMGenerator2Extension[] strategies,
            final IProgressMonitor monitor) throws CoreException {
        TRACE("Running incremental build..."); //$NON-NLS-1$
        IResourceDelta delta = getDelta(getProject());
        // if (delta != null && delta.getAffectedChildren().length > 0) {
        // Build any BOM files in the delta
        IncrementalBuildVisitor visitor = new IncrementalBuildVisitor();
        delta.accept(visitor);

        List<IFile> filesToBuild = visitor.getFilesToBuild();
        if (filesToBuild.size() != 0) {

            List<IFile> toBuild = new ArrayList<IFile>(filesToBuild);
            List<IFile> processed = new ArrayList<IFile>();
            IProject project = getProject();
            for (IFile file : filesToBuild) {
                for (IResource res : getAllDependentFiles(file, processed)) {
                    if (res instanceof IFile) {
                        if (!filesToBuild.contains(res)
                                && isBOMModel((IFile) res)) {
                            if (project.equals(res.getProject())) {
                                if (!toBuild.contains(res)) {
                                    toBuild.add((IFile) res);
                                }
                            } else {
                                res.touch(null);
                            }
                        }
                    }
                }
            }

            /*
             * Sid XPD-1605: Create the strategy to GeneratorData map
             * 'up-front'. This stores the set of projects already created this
             * cycle so that generator.initialize() does not keep recreating
             * projects for BOMs that are dependencies of multiple other files.
             */
            Map<BOMGenerator2Extension, GeneratorData> strategyToGenDataMap =
                    bomGenHelper.createStrategyToGeneratorDataMap(strategies,
                            filesToBuild,
                            BuildType.INCREMENTAL);

            // If boms have been removed then remove all generated projects
            if (!visitor.getFilesToClean().isEmpty()) {
                bomGenHelper.clean(BOMGenerator2ExtensionHelper.getInstance()
                        .getExtensions(), visitor.getFilesToClean(), monitor);
            }

            bomGenHelper.doBuild(getProject(),
                    strategies,
                    toBuild,
                    BuildType.INCREMENTAL,
                    strategyToGenDataMap,
                    monitor);

        }
        TRACE("Incremental build complete."); //$NON-NLS-1$
    }

    /**
     * Check if the given file is a BOM model in a BOM special folder.
     * 
     * @param file
     * @return
     */
    private boolean isBOMModel(IFile file) {
        if (file != null
                && file.getFileExtension() != null
                && file.getFileExtension()
                        .equals(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
            // If the BOM file is in the BOM special folder then return true
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(file.getProject());
            if (config != null) {
                SpecialFolder sf =
                        config.getSpecialFolders().getFolderContainer(file);
                return sf != null
                        && sf.getKind()
                                .equals(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
            }
        }
        return false;
    }

    /**
     * Get all the BOM files in the given project. This will return BOM files
     * that are contained in BOM special folder(s).
     * 
     * @param project
     * @return all BOM files in the given project, empty list if none found
     * @throws CoreException
     */
    private List<IFile> getAllBomFiles(IProject project) throws CoreException {
        final List<IFile> files = new ArrayList<IFile>();

        List<SpecialFolder> folders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        if (folders != null) {
            for (SpecialFolder sf : folders) {
                IFolder folder = sf.getFolder();
                if (folder != null && folder.isAccessible()) {
                    folder.accept(new IResourceProxyVisitor() {
                        @Override
                        public boolean visit(IResourceProxy proxy)
                                throws CoreException {
                            if (proxy.getType() == IResource.FILE
                                    && proxy.getName()
                                            .endsWith("." //$NON-NLS-1$
                                                    + BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                                IResource resource = proxy.requestResource();
                                if (resource instanceof IFile) {
                                    files.add((IFile) resource);
                                    return false;
                                }
                            }
                            return true;
                        }
                    },
                            0);
                }
            }
        }

        return files;
    }

    /**
     * Check if the given project is a generated project, rather than a Studio
     * project.
     * 
     * @param project
     * @return true if project name starts with a dot and has no XPDNature
     *         applied.
     */
    private boolean isGeneratedProject(IProject project) {
        if (project != null && project.getName().startsWith(".")) { //$NON-NLS-1$
            try {
                if (project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    return false;
                }
            } catch (CoreException e) {
                // Ignore the error
            }
        }
        return true;
    }

    /**
     * Get all the dependent files of the given file. This will return the
     * complete dependency tree.
     * 
     * @param file
     * @param processedFiles
     * @return
     */
    private List<IFile> getAllDependentFiles(IFile file,
            List<IFile> processedFiles) {
        List<IFile> files = new ArrayList<IFile>();

        if (!processedFiles.contains(file)) {
            processedFiles.add(file);

            Collection<IResource> resources =
                    WorkingCopyUtil.getAffectedResources(file);
            if (resources != null && !resources.isEmpty()) {
                for (IResource resource : resources) {
                    if (resource instanceof IFile
                            && isBOMModel((IFile) resource)) {
                        files.add((IFile) resource);
                    }
                }
            }

            // Process all the dependencies of the dependent files
            List<IFile> dependents = new ArrayList<IFile>();
            for (IFile dependent : files) {
                dependents.addAll(getAllDependentFiles(dependent,
                        processedFiles));
            }
            if (!dependents.isEmpty()) {
                files.addAll(dependents);
            }
        }
        return files;
    }

    /**
     * Resource delta visitor that will run the incremental build.
     * 
     * @author njpatel
     * 
     */
    private class IncrementalBuildVisitor implements IResourceDeltaVisitor {

        private final List<IFile> filesToClean;

        private final List<IFile> filesToBuild;

        public IncrementalBuildVisitor() {
            filesToClean = new ArrayList<IFile>();
            filesToBuild = new ArrayList<IFile>();
        }

        public List<IFile> getFilesToBuild() {
            return filesToBuild;
        }

        public List<IFile> getFilesToClean() {
            return filesToClean;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse
         * .core.resources.IResourceDelta)
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            if (resource instanceof IFile) {
                IFile file = (IFile) resource;
                if (isBOMModel(file)) {
                    // If the resource is deleted then it has no content,
                    // otherwise check if it has content.
                    boolean isDeleted =
                            delta.getKind() == IResourceDelta.REMOVED;

                    if (!isDeleted) {
                        if (!filesToBuild.contains(file)) {
                            filesToBuild.add(file);
                        }
                    } else {
                        if (!filesToClean.contains(file)) {
                            filesToClean.add(file);
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }

    /**
     * Resource visitor to find all BOM resources in a project during full
     * build.
     * 
     * @author njpatel
     * 
     */
    /* package */static class FullBuildVisitor implements IResourceProxyVisitor {

        private final List<IFile> filesToClean;

        private final List<IFile> filesToBuild;

        private final ProjectConfig config;

        public FullBuildVisitor(ProjectConfig config) {
            this.config = config;
            filesToBuild = new ArrayList<IFile>();
            filesToClean = new ArrayList<IFile>();
        }

        /**
         * Get all the files to build from the project
         * 
         * @return
         */
        public List<IFile> getFilesToBuild() {
            return filesToBuild;
        }

        /**
         * Get all the files to clean from the project - these are bom files
         * that are outside the BOM special folder, or contain no content.
         * 
         * @return
         */
        public List<IFile> getFilesToClean() {
            return filesToClean;
        }

        @Override
        public boolean visit(IResourceProxy proxy) throws CoreException {
            if (proxy.getType() == IResource.FILE
                    && proxy.getName().endsWith("." //$NON-NLS-1$
                            + BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                IResource resource = proxy.requestResource();
                if (resource instanceof IFile) {
                    // If this file is in a BOM Special Folder then build
                    // it, otherwise clean it
                    SpecialFolder sf = null;
                    if (config != null) {
                        sf =
                                config.getSpecialFolders()
                                        .getFolderContainer(resource);
                    }
                    if (sf != null
                            && BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                                    .equals(sf.getKind())) {
                        if (!filesToBuild.contains(resource)) {
                            filesToBuild.add((IFile) resource);
                        }
                    } else {
                        if (!filesToClean.contains(resource)) {
                            filesToClean.add((IFile) resource);
                        }
                    }
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Add a debug trace message.
     * 
     * @param message
     */
    private static void TRACE(String message, Object... param) {
        BOMGenActivator
                .getDefault()
                .getLogger()
                .trace(Logger.CATEGORY_BUILD,
                        param != null && param.length > 0 ? String
                                .format(message, param) : message);
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
