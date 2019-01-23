/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.bom.gen.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.gen.BOMGenActivator;
import com.tibco.xpd.bom.gen.api.BOMGenerator2;
import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension.GenerationMode;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.gen.internal.BOMGenProjectBuilder.FullBuildVisitor;
import com.tibco.xpd.bom.gen.util.DependencyAnalyzer;
import com.tibco.xpd.bom.resources.ui.internal.preferences.BpmPreferenceUtil;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.DependencySorter;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.utils.ValidationProblemUtil;

/**
 * Helper class to assist generation of BOM derived artifacts using
 * BOMGenerator2.
 * 
 * @since 8 Dec 2011
 */
public class BOMGeneratorHelper {

    private static BOMGeneratorHelper INSTANCE = new BOMGeneratorHelper();

    public static BOMGeneratorHelper getInstance() {
        return INSTANCE;
    }

    private BOMGeneratorHelper() {
    }

    private static final String MARKER_TYPE =
            "com.tibco.xpd.bom.gen.bomgenMarker"; //$NON-NLS-1$

    /**
     * Run a on-demand generation for a specified project.
     * 
     * @param args
     *            build arguments
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    public void runOnDemandGeneration(IProject project,
            final IProgressMonitor monitor, Set<String> generatorIds)
            throws CoreException {

        BOMGeneratorHelper.TRACE("Running on-demand BOM-GEN build..."); //$NON-NLS-1$

        BOMGenerator2Extension[] onDemandStrategies;
        if (BpmPreferenceUtil.isInBpmDebugMode()) {
            onDemandStrategies = new BOMGenerator2Extension[0];
        } else {
            BOMGenerator2ExtensionHelper genHelper =
                    new BOMGenerator2ExtensionHelper(generatorIds);
            onDemandStrategies =
                    genHelper.getExtensions(GenerationMode.ON_DEMAND);
        }

        if (onDemandStrategies.length > 0) {
            try {
                for (BOMGenerator2Extension ext : onDemandStrategies) {
                    ext.getGenerator().prepareForBuildCycle(project,
                            BuildType.FULL);
                }

                /*
                 * filesToBuild and filesToClean will be collected also from all
                 * dependent projects (recursively).
                 */
                List<IFile> filesToBuild = new ArrayList<IFile>();
                List<IFile> filesToClean = new ArrayList<IFile>();
                getAllBOMFilesForProjectInternal(project,
                        filesToBuild,
                        filesToClean);

                /*
                 * Sid XPD-1605: Create the strategy to GeneratorData map
                 * 'up-front'. This stores the set of projects already created
                 * this cycle so that generator.initialize() does not keep
                 * recreating projects for BOMs that are dependencies of
                 * multiple other files.
                 */
                Map<BOMGenerator2Extension, GeneratorData> strategyToGenDataMap =
                        createStrategyToGeneratorDataMap(onDemandStrategies,
                                new ArrayList<IFile>(),
                                BuildType.FULL);

                // Clean all generated projects
                if (!filesToClean.isEmpty()) {
                    BOMGenerator2ExtensionHelper genHelper =
                            new BOMGenerator2ExtensionHelper(generatorIds);
                    clean(genHelper.getExtensions(), filesToClean, monitor);
                }

                doBuild(project,
                        onDemandStrategies,
                        filesToBuild,
                        BuildType.FULL,
                        strategyToGenDataMap,
                        monitor);

            } finally {
                /*
                 * Prepare the contributions for
                 */
                for (BOMGenerator2Extension ext : onDemandStrategies) {
                    ext.getGenerator().buildCycleComplete(project);
                }
            }
        }
        BOMGeneratorHelper.TRACE("On-demand BOM-GEN build complete."); //$NON-NLS-1$
    }

    /**
     * Returns the list of the BOM files 'in scope' of the project.
     * 
     * @param project
     *            the context project.
     * @return the list of the BOM files 'in scope' of the project.
     * @throws CoreException
     *             if there is problem during retrieval.
     */
    public static List<IFile> getAllBOMFilesForProject(IProject project)
            throws CoreException {
        List<IFile> filesToBuild = new ArrayList<IFile>();
        getAllBOMFilesForProjectInternal(project,
                filesToBuild,
                new ArrayList<IFile>());
        return filesToBuild;
    }

    /**
     * Adds BOM included or referenced BOM files to the 'filesToBuild' (and
     * 'filesToClean') lists. Lists also include all 'in scope' BOM files from
     * all referenced projects.
     * 
     * @param project
     *            the context project.
     * @param filesToBuild
     *            all BOM files to build for the provided project.
     * @param filesToClean
     *            all BOM files to clean (not in BOM special folder) for the
     *            provided project.
     * @throws CoreException
     *             if there is a cyclic dependency.
     */
    private static void getAllBOMFilesForProjectInternal(IProject project,
            List<IFile> filesToBuild, List<IFile> filesToClean)
            throws CoreException {
        List<IProject> projectsHierarchy;
        try {
            Set<IProject> refProjects =
                    ProjectUtil2.getReferencedProjectsHierarchy(project, true);
            projectsHierarchy = new ArrayList<IProject>(refProjects);
            Collections.reverse(projectsHierarchy);
        } catch (CyclicDependencyException e) {
            // Translate to core exception.
            throw new CoreException(new Status(IStatus.ERROR,
                    BOMGenActivator.PLUGIN_ID,
                    Messages.BOMGeneratorHelper_CyclicDependency_message, e));
        }
        projectsHierarchy.add(project);
        for (IProject refProject : projectsHierarchy) {

            /*
             * Build all BOM files in the project. This should process all .bom
             * files in case a special folder was removed in which case
             * previously generated projects from those special folders should
             * be cleaned.
             */
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(refProject);

            FullBuildVisitor visitor = new FullBuildVisitor(config);
            refProject.accept(visitor, IResource.NONE);

            // Call Full Build
            filesToBuild.addAll(visitor.getFilesToBuild());
            filesToClean.addAll(visitor.getFilesToClean());
        }
    }

    /**
     * Run a build on the given files. All generated artifacts of the resources
     * in the files in the filesToBuild will be re-generated.
     * 
     * @param project
     *            the project being built
     * @param filesToBuild
     *            re-generate from these files, empty list if none
     * @param type
     *            type of build
     * @param monitor
     *            progress monitor
     */
    /* package */void doBuild(IProject project,
            BOMGenerator2Extension[] strategies,
            Collection<IFile> filesToBuild, BuildType type,
            Map<BOMGenerator2Extension, GeneratorData> strategyToGenDataMap,
            IProgressMonitor monitor) {
        try {
            monitor.beginTask(Messages.BOMGenProjectBuilder_generator_progress_shortdesc,
                    strategies.length
                            * (filesToBuild != null ? filesToBuild.size() : 0));

            // Build the affected files
            if (filesToBuild != null && !filesToBuild.isEmpty()) {
                Map<IFile, Boolean> validationMap =
                        new HashMap<IFile, Boolean>();

                /*
                 * Try passing each changed file in the correct reverse
                 * dependency order so - this would mean that when a lot of
                 * files change or fullbuild we create and generate each project
                 * in the correct order. rather than maybe passing in a top
                 * level file first which then does a createProject for each
                 * dependency before then generating each dependency.
                 * 
                 * This may help to solve some java build order problems.
                 */
                DependencyAnalyzer allBomsAnalyzer =
                        new DependencyAnalyzer(filesToBuild);

                /* Get a copy of the reverse order dependecy list. */
                List<IFile> revDependency = new ArrayList<IFile>();

                try {
                    revDependency =
                            new ArrayList<IFile>(allBomsAnalyzer.getResult());
                } catch (IllegalArgumentException e) {
                    /*
                     * Cyclic dependency detected so report in log and stop this
                     * build.
                     */
                    reportCyclicDependency(project, e);
                    throw new OperationCanceledException();
                }

                /*
                 * Analyzer may have added dependency files so JUST to keep
                 * things on an even footing with existing functionality at this
                 * point we'll re-remove all except the original set to be on
                 * the safe side
                 */
                revDependency.retainAll(filesToBuild);

                for (IFile file : revDependency) {

                    TRACE("Starting build on %s", file); //$NON-NLS-1$
                    // Run dependency analyser on the BOM resource to rebuild
                    DependencyAnalyzer analyzer =
                            new DependencyAnalyzer(Collections.singleton(file));
                    /*
                     * Get the list of the dependencies here so that if there is
                     * a cyclic dependency then it will throw an exception.
                     */
                    List<IFile> allFiles = new ArrayList<IFile>();
                    try {
                        allFiles = analyzer.getResult();
                    } catch (IllegalArgumentException e) {
                        /*
                         * Cyclic dependency detected so report in log and stop
                         * this build.
                         */
                        reportCyclicDependency(project, e);
                        throw new OperationCanceledException();
                    }

                    // Check if the file is valid, if not then clean any of its
                    // artifacts
                    boolean isValid =
                            isFileValid(file, validationMap, analyzer);

                    // Process each strategy in turn
                    for (BOMGenerator2Extension strategy : strategies) {
                        try {
                            /*
                             * Sid XPD-1605: Get the GeneratorData for this
                             * strategy and reset the build type according to
                             * whether the file is valid or not.
                             */
                            GeneratorData data =
                                    strategyToGenDataMap.get(strategy);
                            data.setBuildType(isValid ? type : BuildType.CLEAN);

                            /*
                             * If file is valid then check if the BOM file has
                             * any content - if not and the strategy can support
                             * an empty model then continue, otherwise clean up
                             * the (previously) generated artifacts for this
                             * file.
                             */
                            if (isValid
                                    && (hasContent(file) || strategy
                                            .isEmptyBOMSupported())) {
                                // Generate artifacts for the file
                                try {
                                    executeStrategy(file,
                                            strategy,
                                            new ArrayList<IFile>(allFiles),
                                            data,
                                            analyzer,
                                            new SubProgressMonitor(monitor, 1));

                                } catch (CoreException e) {
                                    // Log error
                                    BOMGenActivator.getDefault().getLogger()
                                            .log(e.getStatus());
                                }
                            } else {
                                TRACE(" -->File is not valid or empty"); //$NON-NLS-1$
                                // Clean the generated artifacts (if any) as
                                // this
                                // file is invalid
                                try {
                                    strategy.getGenerator()
                                            .clean(Collections.singleton(file),
                                                    data,
                                                    new SubProgressMonitor(
                                                            monitor, 1));
                                } catch (CoreException e) {
                                    // Log error
                                    BOMGenActivator.getDefault().getLogger()
                                            .log(e.getStatus());
                                }
                            }
                        } catch (Exception e) {
                            // Unexpected error
                            String message =
                                    e.getLocalizedMessage() == null ? String
                                            .format(Messages.BOMGenProjectBuilder_unexpecterError_shortdesc,
                                                    strategy.getLabel(),
                                                    e.getLocalizedMessage())
                                            : String.format(Messages.BOMGeneratorHelper_unexpectedError_seeEventDetails_error_longdesc,
                                                    strategy.getLabel());
                            BOMGenActivator.getDefault().getLogger()
                                    .error(e, message);
                        }
                        monitor.worked(1);
                    }
                }
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Report the cyclic dependency exception in the log.
     * 
     * @param project
     *            project being built
     * @param e
     *            exception from the {@link DependencySorter}
     */
    private void reportCyclicDependency(IProject project,
            IllegalArgumentException e) {
        IStatus status = null;
        if (e.getCause() instanceof CoreException) {
            status = ((CoreException) e.getCause()).getStatus();
        }

        if (status == null) {
            status =
                    new Status(
                            IStatus.ERROR,
                            BOMGenActivator.PLUGIN_ID,
                            Messages.BOMGeneratorHelper_cyclicDependency_error_shortdesc);
        }

        BOMGenActivator
                .getDefault()
                .getLogger()
                .log(new MultiStatus(
                        BOMGenActivator.PLUGIN_ID,
                        0,
                        new IStatus[] { status },
                        String.format(Messages.BOMGeneratorHelper_BOMGenFailed_error_longdesc,
                                project.getName()), null));
    }

    /**
     * Clean the generated artifacts of the given files.
     * 
     * @param filesToClean
     * @param monitor
     * @throws CoreException
     */
    /* package */void clean(BOMGenerator2Extension[] strategies,
            Collection<IFile> filesToClean, IProgressMonitor monitor)
            throws CoreException {

        /*
         * Clear any "unexpected error" marker that may have been added to the
         * resource
         */
        for (IFile file : filesToClean) {
            removeUnexpectedErrorProblemMarker(file, null);
        }

        if (strategies != null && strategies.length > 0) {
            for (BOMGenerator2Extension generator : strategies) {
                TRACE("Clean called on generator '%s'...", //$NON-NLS-1$
                        generator.getId());
                TRACE(" -->Files to clean: %s", filesToClean); //$NON-NLS-1$
                long start = System.currentTimeMillis();
                generator.getGenerator().clean(filesToClean,
                        new GeneratorData(BuildType.CLEAN, filesToClean),
                        monitor);
                TRACE("Clean on generator '%s' completed in %d ms", //$NON-NLS-1$
                        generator.getId(),
                        System.currentTimeMillis() - start);
            }
        }
    }

    /**
     * Create a map of strategy to GeneratorData for that startegy.
     * <p>
     * The generatorData contains a 'bomsAlreadyDone' list which allows
     * {@link BOMGenerator2#initialize(Collection, GeneratorData, IProgressMonitor)}
     * when called from
     * {@link #doBuild(Collection, Collection, BuildType, Map, IProgressMonitor)}
     * to make a sensible decision as to whether the project for a strategy for
     * a file really needs re-creation within a single build cycle.
     * 
     * @param filesToBuild
     * @param type
     * 
     * @return Map of all strategies a {@link GeneratorData}
     */
    /* package */Map<BOMGenerator2Extension, GeneratorData> createStrategyToGeneratorDataMap(
            BOMGenerator2Extension[] strategies,
            Collection<IFile> filesToBuild, BuildType type) {
        /*
         * Sid XPD-1605: Keep the GeneratorData-per-strategy for the life of the
         * build cycle as it now contains a list of BOMs that have already had
         * projects created.
         */
        Map<BOMGenerator2Extension, GeneratorData> strategyToGenDataMap =
                new HashMap<BOMGenerator2Extension, GeneratorData>();
        for (BOMGenerator2Extension strategy : strategies) {
            strategyToGenDataMap.put(strategy, new GeneratorData(type,
                    filesToBuild));
        }
        return strategyToGenDataMap;
    }

    /**
     * Check if the given file is valid. This will also check all dependencies
     * of this file (all the way down the chain of dependencies).
     * 
     * @param file
     *            file to validate
     * @param validationMap
     *            cache to store result of the validation - this is to avoid
     *            re-validating the same resource
     * @param analyzer
     *            dependency analyser
     * @return <code>true</code> if file is valid and all dependencies (full
     *         chain) is valid.
     */
    /* package */boolean isFileValid(IFile file,
            Map<IFile, Boolean> validationMap, DependencyAnalyzer analyzer) {
        if (file != null && validationMap != null) {
            if (validationMap.containsKey(file)) {
                // If this file has already been validated then return the
                // previous result
                return validationMap.get(file);
            }

            boolean isValid = false;
            if (isFileValid(file)) {
                isValid = true;
                // Check all dependencies
                for (IFile dep : analyzer.getAllDependencies(file)) {
                    // Check if the file has already been validated
                    if (validationMap.containsKey(dep)) {
                        isValid = validationMap.get(dep);
                    } else {
                        // Not checked before so check file
                        isValid = isFileValid(dep, validationMap, analyzer);
                    }
                    /*
                     * If the dependency (or any children thereof) is invalid
                     * then this file is invalid
                     */
                    if (!isValid) {
                        break;
                    }
                }
            }
            validationMap.put(file, isValid);
            return isValid;
        }
        return false;
    }

    /**
     * Execute the given strategy.
     * 
     * @param fileBeingValidated
     *            the file that is actually being validated
     * @param ext
     *            generator extension
     * @param bomFiles
     *            selected files to build, including all dependencies
     * @param data
     *            generator data
     * @param analyzer
     *            Dependency analyzer to pass to the generator
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    /* package */void executeStrategy(IFile fileBeingValidated,
            BOMGenerator2Extension ext, Collection<IFile> bomFiles,
            GeneratorData data, DependencyAnalyzer analyzer,
            IProgressMonitor monitor) throws CoreException {
        monitor.beginTask(String
                .format(Messages.BOMGenProjectBuilder_executeStrategy_shortdesc,
                        ext.getLabel()),
                5);
        TRACE("Starting generator '%s' for: %s", //$NON-NLS-1$
                ext.getId(),
                bomFiles);

        try {
            BOMGenerator2 generator = ext.getGenerator();
            generator.setExtension(ext);
            generator.setDependencyProvider(analyzer);

            if (generator
                    .supports(bomFiles, new SubProgressMonitor(monitor, 1))) {
                monitor.worked(1);

                // Validate the resources with the generator
                IStatus status =
                        generator.validate(bomFiles, new SubProgressMonitor(
                                monitor, 1));

                if (status.getSeverity() != IStatus.ERROR) {
                    long start = System.currentTimeMillis();
                    // Initialize
                    generator.initialize(bomFiles,
                            data,
                            new SubProgressMonitor(monitor, 1));
                    try {
                        // Generate
                        generator.generate(bomFiles,
                                data,
                                new SubProgressMonitor(monitor, 1));
                        /*
                         * Clear any "unexpected error" validation marker that
                         * may have been added in the previous run.
                         */
                        removeUnexpectedErrorProblemMarker(fileBeingValidated,
                                ext);

                        // Archive the generated project
                        // generator.archive(bomFiles,
                        // null,
                        // data,
                        // new SubProgressMonitor(monitor, 1));

                    } catch (CoreException e) {
                        TRACE(" -->Exception: %s", e.getLocalizedMessage()); //$NON-NLS-1$
                        if (e.getStatus() != null
                                && e.getStatus().getSeverity() == IStatus.ERROR) {
                            TRACE(" -->Problem marker being created because of an ERROR exception."); //$NON-NLS-1$
                            // Create a marker on the bom resource to indicate
                            // problem with generator
                            createErrorProblemMarker(fileBeingValidated, ext);

                            /*
                             * As an error has been reported by the generator
                             * any generated artifacts of the file being built
                             * should be removed
                             */
                            generator.clean(Collections
                                    .singleton(fileBeingValidated),
                                    data,
                                    new NullProgressMonitor());
                        } else {
                            /*
                             * Clear any "unexpected error" validation marker
                             * that may have been added in the previous run.
                             */
                            removeUnexpectedErrorProblemMarker(fileBeingValidated,
                                    ext);
                        }
                        throw e;
                    }
                    TRACE(" -->Completed generation in %d ms.", //$NON-NLS-1$
                            System.currentTimeMillis() - start);
                } else {
                    TRACE("Generator '%s' reported validation problems with selection, cleaning any generated artifacts", //$NON-NLS-1$
                            ext.getId());
                    /*
                     * As an error has been reported by the generator any
                     * generated artifacts of the file being built should be
                     * removed
                     */
                    generator.clean(Collections.singleton(fileBeingValidated),
                            data,
                            monitor);
                }
            } else {
                TRACE("Generator '%s' does not support selection, cleaning any generated artifacts.", //$NON-NLS-1$
                        ext.getId());
                // This strategy does not support the selection so clean up in
                // case artifacts were generated in previous run
                generator.clean(Collections.singleton(fileBeingValidated),
                        data,
                        new SubProgressMonitor(monitor, 1));
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Create an error problem marker on the given resource.
     * 
     * @param bomFile
     * @param ext
     * @throws CoreException
     */
    /* package */void createErrorProblemMarker(IFile bomFile,
            BOMGenerator2Extension ext) throws CoreException {
        if (bomFile != null && bomFile.exists() && ext != null) {
            /*
             * Add a problem marker to the resource if one has already not been
             * added in the previous run
             */
            IMarker[] markers =
                    bomFile.findMarkers(BOMGeneratorHelper.MARKER_TYPE,
                            false,
                            IResource.DEPTH_ZERO);
            boolean alreadyCreated = false;

            for (IMarker marker : markers) {
                if (ext.getId().equals(marker.getAttribute(IMarker.SOURCE_ID))) {
                    alreadyCreated = true;
                    break;
                }
            }

            if (!alreadyCreated) {
                IMarker marker =
                        bomFile.createMarker(BOMGeneratorHelper.MARKER_TYPE);
                marker.setAttribute(IMarker.MESSAGE,
                        Messages.BOMGenProjectBuilder_generationFailed_problemMarker_shortdesc1);
                marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
                marker.setAttribute(IMarker.SOURCE_ID, ext.getId());
            }
        }
    }

    /**
     * Remove problem markers that may have been raised against the given
     * extension by a previous build run.
     * 
     * @param bomFile
     * @param ext
     *            remove problem markers of this extension, <code>null</code> to
     *            remove all problem markers
     * @throws CoreException
     */
    /* package */void removeUnexpectedErrorProblemMarker(IFile bomFile,
            BOMGenerator2Extension ext) throws CoreException {
        if (bomFile != null && bomFile.exists()) {
            IMarker[] markers =
                    bomFile.findMarkers(BOMGeneratorHelper.MARKER_TYPE,
                            false,
                            IResource.DEPTH_ZERO);
            for (IMarker marker : markers) {
                // If no extension is defined then delete all markers
                if (ext == null
                        || (ext != null && ext.getId()
                                .equals(marker.getAttribute(IMarker.SOURCE_ID)))) {
                    marker.delete();
                }
            }
        }
    }

    /**
     * Check if the given BOM model has some content.
     * 
     * @param bomFile
     * @return <code>true</code> if this model has some content,
     *         <code>false</code> if it is empty.
     */
    public static boolean hasContent(IFile bomFile) {
        if (bomFile != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            if (wc != null) {
                EObject model = wc.getRootElement();
                if (model instanceof Model) {
                    return !((Model) model).getPackagedElements().isEmpty()
                            || hasToplevelElements((Model) model);
                }
            }
        }
        return false;
    }

    /**
     * Returns 'true' if model has top level elements. BOM model could have top
     * level elements pointing to types from another package, so there could be
     * a case when there are no package elements in model, but there are still
     * top level elements.
     * 
     * @param model
     *            BOM model.
     * @return 'true' if model has top level elements.
     */
    @SuppressWarnings("rawtypes")
    private static boolean hasToplevelElements(Model model) {
        /*
         * JA: Could not depend on 'com.tibco.xpd.bom.transform' as it would
         * lead to a cyclic dependency hence some constants values were used
         * directly. See constants:
         * 
         * XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME = "XsdNotationProfile"
         * 
         * XsdStereotypeUtils.TOP_LEVEL_ELEMENTS = "TopLevelElements"
         * 
         * XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS = "elements"
         */
        Stereotype tleStereotype =
                BOMUtils.getStereotype(model,
                        "TopLevelElements", "XsdNotationProfile"); //$NON-NLS-1$ //$NON-NLS-2$
        if (tleStereotype != null) {
            Object elements = model.getValue(tleStereotype, "elements"); //$NON-NLS-1$
            if (elements instanceof EList) {
                return !((EList) elements).isEmpty();
            }
        }
        return false;
    }

    /**
     * Check if the given BOM file is valid. This will check for any BOM Generic
     * issues in the file.
     * 
     * @param file
     * @return
     */
    /* package */boolean isFileValid(IFile file) {
        if (file != null && file.exists()) {
            try {
                Collection<IMarker> markers =
                        ValidationProblemUtil
                                .getProblemMarkers(BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC,
                                        IMarker.SEVERITY_ERROR,
                                        false,
                                        file);
                return markers.isEmpty();
            } catch (CoreException e) {
                IStatus status =
                        new Status(
                                IStatus.ERROR,
                                BOMGenActivator.PLUGIN_ID,
                                String.format(Messages.BOMGenProjectBuilder_validationErrorReporter_error_shortdesc,
                                        file.getFullPath().toString()), e);
                BOMGenActivator.getDefault().getLogger().log(status);
            }
        }
        return false;
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

}