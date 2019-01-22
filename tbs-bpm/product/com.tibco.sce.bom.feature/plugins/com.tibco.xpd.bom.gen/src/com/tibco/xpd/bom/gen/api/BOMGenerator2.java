/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.api;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.jarpackager.IJarExportRunnable;
import org.eclipse.jdt.ui.jarpackager.JarPackageData;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.gen.BOMGenActivator;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.gen.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.preferences.BpmPreferenceUtil;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Abstract class to be implemented by extensions that will provide BOM
 * generators. (Extension point <code>com.tibco.xpd.bom.gen.bomGenerator2</code>
 * .)
 * <p>
 * The following method are of interest (some methods have default
 * implementation that can be overridden):
 * <ul>
 * <li>{@link #supports(Collection, IProgressMonitor) supports} - method called
 * to check if this generator (strategy) can generate from the selected BOM
 * resources. The default implemenetation will use the generator extension to
 * check the provided natures and destination this strategy supports.</li>
 * <li>{@link #validate(Collection, IProgressMonitor) validate} - validate the
 * selected BOM resources. The default implementation will check for the problem
 * markers defined in the extension against the resources.</li>
 * <li>{@link #initialize(Collection, GeneratorData, IProgressMonitor)
 * initialize} - initialise this strategy. The default implementation will
 * create java projects for each resource in the selection.</li>
 * <li>{@link #generate(Collection, GeneratorData, IProgressMonitor) generate} -
 * abstract method that has to be implemented by the subclass to generate from
 * the selected resources.</li>
 * <li>{@link #archive(Collection, File, GeneratorData, IProgressMonitor)
 * archive} - default implementation will archive the created projects and place
 * them in the destination provided</li>
 * <li>{@link #clean(Collection, GeneratorData, IProgressMonitor) clean} - clean
 * the artifacts generated for given BOM Resources. The default implementation
 * will delete the projects generated for the given resources.</li>
 * </ul>
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.3
 */
public abstract class BOMGenerator2 {

    private static final QualifiedName MODEL_TIMESTAMP = new QualifiedName(
            BOMGenActivator.PLUGIN_ID, "modelTimeStamp"); //$NON-NLS-1$

    private static final QualifiedName SRC_FILE = new QualifiedName(
            BOMGenActivator.PLUGIN_ID, "srcFile"); //$NON-NLS-1$

    private static final String PLUGIN_ID = "com.tibco.xpd.bom.gen"; //$NON-NLS-1$

    private BOMGenerator2Extension extension;

    /**
     * Caches the staging project created for each of the BOM resource.
     */
    private final Map<IFile, IProject> stagingProjects;

    /**
     * Cache to hold references to all regenerated projects.
     */
    private final Set<IProject> regeneratedProjects;

    private IDependencyProvider dependencyProvider;

    /**
     * Used to indicate whether the target file can be overwritten.
     */
    private enum OverwriteStatus {
        OVERWRITE, OVERWRITEALL, NOOVERWRITE;

        boolean canOverwrite() {
            return this == OVERWRITE || this == OVERWRITEALL;
        }
    }

    /**
     * BOM generator abstract class.
     */
    public BOMGenerator2() {
        stagingProjects = new HashMap<IFile, IProject>();
        regeneratedProjects = new HashSet<IProject>();
    }

    /**
     * Set the extension that this class belongs to.
     * 
     * @param extension
     *            BOM Generator2 extension
     */
    public final void setExtension(BOMGenerator2Extension extension) {
        this.extension = extension;
    }

    /**
     * Set the dependency provider for this generator.
     * 
     * @param dependencyProvider
     */
    public final void setDependencyProvider(
            IDependencyProvider dependencyProvider) {
        this.dependencyProvider = dependencyProvider;
    }

    /**
     * Get the dependency provider for this generator. This will provide methods
     * to work out the dependencies of a given resource.
     * 
     * @return {@link IDependencyProvider provider} or <code>null</code> if one
     *         is not set.
     */
    public final IDependencyProvider getDependencyProvider() {
        return dependencyProvider;
    }

    /**
     * Check if this generator supports the selected BOM resources. Default
     * implementation will check the extension for the specified natures and
     * destinations that the selected projects should have (if no natures and
     * global destinations are defined then this method will return
     * <code>true</code>). Subclasses may override to provide custom checking.
     * 
     * @see #getDependencyProvider()
     * 
     * @param bomResources
     *            selected BOM {@link IFile}s, including all dependencies (the
     *            collection is on order of the dependency tree with the items
     *            at the beginning at the bottom of the dependency tree)
     * @param monitor
     *            progress monitor
     * @return <code>true</code> if this generator can process the selected
     *         resources, <code>false</code> otherwise.
     * @throws CoreException
     */
    public boolean supports(Collection<IFile> bomResources,
            IProgressMonitor monitor) throws CoreException {
        BOMGenerator2Extension extension = getExtension();

        if (extension != null && bomResources != null
                && !bomResources.isEmpty()) {

            // Collect all selected projects
            Set<IProject> projects = new HashSet<IProject>();
            for (IResource res : bomResources) {
                IProject project = res.getProject();
                if (project != null) {
                    projects.add(project);
                }
            }

            if (!projects.isEmpty()) {
                for (IProject project : projects) {
                    if (!extension.isSupported(project)) {
                        // Project does not have the right nature/destination
                        return false;
                    }
                }
            }
            // This generator supports the selection
            return true;
        }

        return false;
    }

    /**
     * Validate the selected BOM resources. Default implementation will check
     * for error markers on the resource of the specified marker type (in the
     * extension), if any (if no validation markers are specified then this will
     * return an OK status). Subclasses may override to provide custom
     * validation.
     * 
     * @see #getDependencyProvider()
     * 
     * @param bomResources
     *            selected BOM {@link IFile}s, include all dependencies (the
     *            collection is on order of the dependency tree with the items
     *            at the beginning at the bottom of the dependency tree)
     * @param monitor
     *            progress monitor
     * @return {@link IStatus} result. OK status should be returned if no
     *         problems are found, otherwise the appropriate status level should
     *         be returned (NOTE: Error status will not allow the user to
     *         generate from this generator).
     * @throws CoreException
     */
    public IStatus validate(Collection<IFile> bomResources,
            IProgressMonitor monitor) throws CoreException {

        if (bomResources != null && !bomResources.isEmpty()) {
            List<String> types =
                    getExtension() != null ? getExtension().getMarkerTypes()
                            : null;
            if (types != null && !types.isEmpty()) {
                for (IFile file : bomResources) {
                    for (String markerType : types) {
                        if (hasErrors(file, markerType)) {
                            return new Status(
                                    IStatus.ERROR,
                                    PLUGIN_ID,
                                    String.format(Messages.BOMGenerator2_strategyHasReporterErrors_error_shortdesc,
                                            getExtension().getLabel()));
                        }
                    }
                }
            }
        }
        return Status.OK_STATUS;
    }

    /**
     * Clean the artifacts created by this generator for the given BOM
     * resources. Default implementation will remove the generated projects that
     * relate to the given resources. Subclasses may override.
     * 
     * @param bomResources
     *            BOM resources whose generated artifacts should be removed.
     *            (Note, this does not contain any dependencies as respective
     *            calls, where necessary, will be made to clean their artifacts
     *            up.)
     * @param data
     *            context data
     * @param monitor
     *            progress monitor.
     * @throws CoreException
     */
    public void clean(Collection<IFile> bomResources, GeneratorData data,
            IProgressMonitor monitor) throws CoreException {
        if (bomResources != null) {
            for (IFile file : bomResources) {
                IProject project = findProject(file.getFullPath().toString());

                if (project != null && project.exists()) {
                    // Update all the caches
                    regeneratedProjects.remove(project);
                    for (Iterator<Entry<IFile, IProject>> iter =
                            stagingProjects.entrySet().iterator(); iter
                            .hasNext();) {
                        if (iter.next().getValue().equals(project)) {
                            iter.remove();
                            break;
                        }
                    }

                    /*
                     * Remove the generated project as a dependency to the
                     * project of the source file. XPD-2610: No longer required.
                     * 
                     * removeProjectReference(file.getProject(), project);
                     */

                    // Delete the project
                    project.delete(true, monitor);
                }
            }
        }
    }

    /**
     * Find the project which corresponds to the given source file path. This
     * will use the SRC_FILE persisted property on the project to match it to
     * the project's source.
     * 
     * @param srcPath
     * @return {@link IProject} if found and exists, <code>null</code>
     *         otherwise.
     * @throws CoreException
     */
    private IProject findProject(String srcPath) throws CoreException {
        for (IProject p : ResourcesPlugin.getWorkspace().getRoot()
                .getProjects()) {
            if (p.isAccessible()) {
                String value = p.getPersistentProperty(SRC_FILE);
                if (value != null && value.equals(srcPath)
                        && p.getName().endsWith("." + extension.getSuffix())) { //$NON-NLS-1$
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Initialize the generator. The default implementation will create a
     * plug-in project (with java nature) for each bom model in the collection
     * (with a "src" folder as the source folder). Subclasses may override to
     * provide custom initialization.
     * <p>
     * Use <code>getProject</code> to get the project for a given resource
     * created by this method.
     * </p>
     * 
     * @see #getProject(IResource)
     * 
     * @param bomResources
     *            selected BOM resources, including any dependencies (the
     *            collection is on order of the dependency tree with the items
     *            at the beginning at the bottom of the dependency tree)
     * @param data
     *            context data
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    public void initialize(Collection<IFile> bomResources, GeneratorData data,
            IProgressMonitor monitor) throws CoreException {
        stagingProjects.clear();
        try {
            if (bomResources != null) {
                monitor.beginTask(Messages.BOMGenerator2_creatingProjects_monitor_shortdesc,
                        bomResources.size());
                for (IFile file : bomResources) {
                    monitor.subTask(file.getName());
                    if (!stagingProjects.containsKey(file)) {
                        Model model = BOMUtils.getModel(file);
                        List<IFile> dependencies =
                                getDependencyProvider().getDependencies(file);

                        List<IProject> dependentProjects =
                                new ArrayList<IProject>();
                        if (dependencies != null) {
                            for (IFile dependency : dependencies) {
                                IProject project = getProject(dependency);
                                if (project != null) {
                                    dependentProjects.add(project);
                                }
                            }
                        }
                        IProject project =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .getProject(getProjectName(model));
                        /*
                         * If the original selection contains the file being
                         * processed then we will re-generate the project. This
                         * is required in the instance of a build call where a
                         * dependent file is being re-built because of change to
                         * dependency.
                         * 
                         * Sid XPD-1605: That is unless we have already recreate
                         * the project for this BOM during this build cycle.
                         */
                        boolean createProject = false;

                        if (!data.getBomsAlreadyDone().contains(file)) {
                            createProject =
                                    !project.exists()
                                            || checkDependencyTimestamps(file,
                                                    project)
                                            || data.getInitialSelection()
                                                    .contains(file)
                                            || checkForRelocatedSourceFile(file,
                                                    project);
                            if (!createProject) {
                                /*
                                 * Model has not changed since last call - so
                                 * check if the project dependencies still match
                                 */
                                createProject =
                                        !dependentProjects
                                                .containsAll(Arrays
                                                        .asList(project
                                                                .getDescription()
                                                                .getReferencedProjects()));
                            }
                        }

                        if (createProject) {
                            // If project already exists then delete it
                            if (project.exists()) {
                                // XPD-6655 We need to clean up working copies
                                // when the project is deleted.
                                WorkingCopyUtil
                                        .deleteResourceInWorkspaceOperation(project,
                                                monitor);
                            }

                            /*
                             * If another project exists for this source file
                             * (could be if the model name has changed since
                             * last build) then delete it here as it is no
                             * longer required
                             */
                            IProject pr =
                                    findProject(file.getFullPath().toString());
                            if (pr != null) {
                                /*
                                 * XPD-2610: Project references should not be
                                 * necessary AFAIK and also they now would cause
                                 * problems when projects are generated on
                                 * demand.
                                 * 
                                 * removeProjectReference(file.getProject(),
                                 * pr);
                                 */
                                pr.delete(true, new NullProgressMonitor());
                            }

                            createProject(project,
                                    dependentProjects
                                            .toArray(new IProject[dependentProjects
                                                    .size()]),
                                    data);
                            /*
                             * Persist the mode modification stamp - this will
                             * allow us to determine if this project needs
                             * re-generating in subsequent calls
                             */
                            if (project.isAccessible()) {
                                /*
                                 * Sid SPD-1605: Add this project to the already
                                 * done list for this strategy.
                                 */
                                data.getBomsAlreadyDone().add(file);

                                project.setPersistentProperty(SRC_FILE, file
                                        .getFullPath().toString());
                                project.setPersistentProperty(MODEL_TIMESTAMP,
                                        String.valueOf(file
                                                .getModificationStamp()));
                                regeneratedProjects.add(project);

                                /*
                                 * Add the generated project as a dependency to
                                 * the project of the source file.
                                 */
                                /*
                                 * XPD-2610: Project references should not be
                                 * necessary AFAIK and also they now would cause
                                 * problems when projects are generated on
                                 * demand.
                                 * 
                                 * addProjectReference(file.getProject(),
                                 * project);
                                 */

                                // Remove builders. Looks like the builders are
                                // not necessary.
                                if (!BpmPreferenceUtil.isInBpmDebugMode()) {
                                    removeJavaPDEBuilders(project);
                                }
                            }
                        } else {
                            regeneratedProjects.remove(project);
                        }
                        if (project != null && project.isAccessible()) {
                            stagingProjects.put(file, project);
                        }
                    }
                }
                monitor.worked(1);
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * XPD-6826: Now (along with checking for modification of source file(s)
     * that affect project we also check whether the source file has been
     * relocated.
     * <p>
     * The source BOM path is stored as a persistent property in the generated
     * project. * Previously the project was not rebuilt if the project was
     * renamed (for instance) and this meant that the persistent property was
     * then out of synch with the actual location of the file. This in turn
     * could cause problems for other builders that looked to the project
     * persistent property as a source of truth.
     * <p>
     * 
     * 
     * @param file
     * @param project
     * @return <code>true</code> if the given BOM file has been relocated
     *         (placed in different folder or project/folder renamed etc) since
     *         the BDS project was last built.
     * @throws CoreException
     */
    private boolean checkForRelocatedSourceFile(IFile file, IProject project)
            throws CoreException {
        String srcFile = project.getPersistentProperty(SRC_FILE);

        if (srcFile == null || !srcFile.equals(file.getFullPath().toString())) {
            return true;
        }
        return false;
    }

    /**
     * Get the list of bom file dependencies (that includes the bom file's
     * project config and referenced projects project config if the project
     * version has changed). And check the time stamp on the bom file's
     * dependencies with the target bds project's time stamp.
     * 
     * 
     * @param bomFile
     * @param targetBdsProject
     * @return <code>true</code> if the time stamp on bom file or its
     *         dependencies is later than the target bds project
     */
    private boolean checkDependencyTimestamps(IFile bomFile,
            IProject targetBdsProject) {

        List<IFile> dependencies =
                getDependencyProvider().getAllDependencies(bomFile);

        IProject bpmProject = bomFile.getProject();
        IFile configFile = getProjectConfigFile(bpmProject);
        if (null != configFile && configFile.exists()) {

            dependencies.add(configFile);
        }

        /*
         * get the referenced projects and get their config files to add to the
         * dependencies
         */
        Set<IProject> projectsHierarchy =
                ProjectUtil.getReferencedProjectsHierarchy(bpmProject,
                        null,
                        true);
        for (IProject refProject : projectsHierarchy) {

            IFile refConfigFile = getProjectConfigFile(refProject);
            if (null != refConfigFile && refConfigFile.exists()) {

                dependencies.add(refConfigFile);
            }
        }

        long timestamp = bomFile.getLocalTimeStamp();
        for (IFile dependency : dependencies) {

            timestamp = Math.max(timestamp, dependency.getLocalTimeStamp());
        }
        return timestamp > targetBdsProject.getLocalTimeStamp();
    }

    /**
     * @param refProject
     * @return <code>ProjectConfig</code> file for the given bpm project
     */
    private IFile getProjectConfigFile(IProject refProject) {

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(refProject);
        IFile refConfigFile = WorkingCopyUtil.getFile(config);
        return refConfigFile;
    }

    /**
     * Updates the references of the host project to include the generated
     * project.
     * 
     * @param hostProject
     * @param refProject
     *            the bom file's generated project
     * @throws CoreException
     */
    private void addProjectReference(IProject hostProject, IProject refProject)
            throws CoreException {
        if (hostProject != null && hostProject.exists() && refProject != null) {
            IProjectDescription description = hostProject.getDescription();
            if (description != null) {
                IProject[] referencedProjects =
                        description.getReferencedProjects();
                boolean alreadyReferenced = false;
                for (IProject ref : referencedProjects) {
                    if (ref.equals(refProject)) {
                        alreadyReferenced = true;
                        break;
                    }
                }
                if (!alreadyReferenced) {
                    IProject[] newList =
                            new IProject[referencedProjects.length + 1];
                    System.arraycopy(referencedProjects,
                            0,
                            newList,
                            0,
                            referencedProjects.length);
                    newList[referencedProjects.length] = refProject;
                    description.setReferencedProjects(newList);
                    hostProject.setDescription(description,
                            new NullProgressMonitor());
                }
            }
        }
    }

    /**
     * Updates the references of the host project to exclude the generated
     * project.
     * 
     * @param hostProject
     * @param refProject
     *            the bom file's generated project
     * @throws CoreException
     */
    private void removeProjectReference(IProject hostProject,
            IProject refProject) throws CoreException {
        if (hostProject != null && hostProject.exists() && refProject != null) {
            IProjectDescription description = hostProject.getDescription();
            if (description != null) {
                IProject[] referencedProjects =
                        description.getReferencedProjects();
                boolean referenced = false;
                for (IProject ref : referencedProjects) {
                    if (ref.equals(refProject)) {
                        referenced = true;
                        break;
                    }
                }
                if (referenced) {
                    List<IProject> newList = new ArrayList<IProject>();

                    for (IProject project : referencedProjects) {
                        if (!project.equals(refProject)) {
                            newList.add(project);
                        }
                    }

                    description.setReferencedProjects(newList
                            .toArray(new IProject[newList.size()]));
                    hostProject.setDescription(description,
                            new NullProgressMonitor());
                }
            }
        }
    }

    /**
     * Get the name of the project that should be generated for the given BOM.
     * 
     * @param model
     * @return
     */
    private String getProjectName(Model model) {
        return BOMGenerator2ExtensionHelper.getGeneratedProjectName(model,
                extension);
    }

    /**
     * Generate from the selected BOM resources.
     * <p>
     * Use <code>getProject</code> to get the staging project for a given
     * resource created by the default
     * {@link #initialize(Collection, GeneratorData, IProgressMonitor)
     * initialize} method.
     * </p>
     * <p>
     * It is strongly recommended to check if the artifacts of a given BOM
     * resource should be re-generated. Use <code>needsGenerating</code> to
     * check if the generated artifact of a resource is up-to-date.
     * </p>
     * 
     * @see #getProject(IResource)
     * @see #needsGenerating(IFile)
     * 
     * @param bomResources
     *            BOM resources to generate, including the dependencies (the
     *            collection is on order of the dependency tree with the items
     *            at the beginning at the bottom of the dependency tree) i.e.
     *            the last resource depends upon the penultimate resource and so
     *            on.
     * @param data
     *            context data
     * @param monitor
     *            progress monitor
     * @throws CoreException
     *             If this CoreException contains an ERROR level status then
     *             this will create an error validation marker on the source BOM
     *             file, otherwise the exception will just be logged.
     */
    public abstract void generate(Collection<IFile> bomResources,
            GeneratorData data, IProgressMonitor monitor) throws CoreException;

    /**
     * Run after the
     * {@link #generate(Collection, GeneratorData, IProgressMonitor) generate}
     * method to archive the staging projects. This is only called if the
     * generator was triggered from the wizard.
     * <p>
     * The default implementation will export the staging projects to jar and
     * place these in the destination location given. Subclasses may override if
     * custom archiving is required.
     * </p>
     * 
     * @param bomResources
     *            BOM resources to generate, including the dependencies (the
     *            collection is on order of the dependency tree with the items
     *            at the beginning at the bottom of the dependency tree)
     * @param destination
     *            destination location of the exported artifacts.
     * @param data
     *            context data
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    public void archive(Collection<IFile> bomResources, File destination,
            GeneratorData data, IProgressMonitor monitor) throws CoreException {

        // default destination if destination is null
        boolean isDefaultDestination = (destination == null);

        if (isDefaultDestination || destination.exists()) {
            OverwriteStatus status =
                    isDefaultDestination ? OverwriteStatus.OVERWRITEALL : null;
            for (IProject project : stagingProjects.values()) {
                IPath locationDir =
                        isDefaultDestination ? getCreateDefaultLocation()
                                : new Path(destination.getAbsolutePath());
                IPath location =
                        locationDir.append(project.getName())
                                .addFileExtension("jar"); //$NON-NLS-1$

                // If file exists then ask user if it can be overwritten...

                File jarFile = new File(location.toString());
                if (jarFile.exists()
                        && (status == null || status != OverwriteStatus.OVERWRITEALL)) {
                    status = canOverwrite(jarFile);
                    if (status == null) {
                        // Cancelled
                        throw new OperationCanceledException();
                    }
                } else if (status == null
                        || status != OverwriteStatus.OVERWRITEALL) {
                    status = OverwriteStatus.OVERWRITE;
                }

                if (status.canOverwrite()) {
                    /*
                     * Need to build the project before archiving as the
                     * initialize/generate/archive all get run in a single
                     * workspace operation and the workspace would not have had
                     * a notification of project changes yet.
                     */
                    project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);

                    // Package
                    JarPackageData description = new JarPackageData();
                    description.setJarLocation(location);
                    description.setBuildIfNeeded(false);
                    description.setOverwrite(true);
                    description.setUseSourceFolderHierarchy(true);
                    description.setExportClassFiles(true);
                    description.setExportOutputFolders(true);

                    IFile file = project.getFile("META-INF/MANIFEST.MF"); //$NON-NLS-1$
                    if (file.exists()) {
                        description.setManifestLocation(file.getFullPath());
                        description.setGenerateManifest(false);
                    }

                    description.setElements(new Object[] { project });

                    IJarExportRunnable runnable =
                            description.createJarExportRunnable(null);
                    try {
                        runnable.run(monitor);
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof CoreException) {
                            throw (CoreException) cause;
                        } else {
                            throw new CoreException(
                                    new Status(
                                            IStatus.ERROR,
                                            PLUGIN_ID,
                                            String.format(Messages.BOMGenerator2_errorDuringArchive_error_shortdesc,
                                                    project.getName()),
                                            cause != null ? cause : e));
                        }
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                }
            }
        }
    }

    private IPath getCreateDefaultLocation() throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = root.getProject(".bsCache");
        if (!project.exists()) {
            project.create(null);
        }
        if (!project.isOpen()) {
            project.open(null);
        }
        IFolder bomJarsFolder = project.getFolder("BomJars");
        if (!bomJarsFolder.exists()) {
            bomJarsFolder.create(true, true, null);
        }
        return bomJarsFolder.getLocation();
    }

    /**
     * Check if the given file has error markers of the given marker type.
     * 
     * @param file
     *            file to check
     * @param markerType
     *            marker type to check for
     * @return <code>true</code> if error marker type is found,
     *         <code>false</code> otherwise.
     * @throws CoreException
     */
    protected boolean hasErrors(IFile file, String markerType)
            throws CoreException {
        if (file != null && file.exists() && markerType != null) {
            IMarker[] markers =
                    file.findMarkers(markerType, true, IResource.DEPTH_ZERO);
            for (IMarker marker : markers) {
                Object attr = marker.getAttribute(IMarker.SEVERITY);
                if (attr instanceof Integer
                        && ((Integer) attr).equals(IMarker.SEVERITY_ERROR)) {
                    // Errors found
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the default output (bin) folder (full) path for the java project
     * being created. The default implementation will return the output folder
     * as set in the Java preferences. Subclasses may override if a different
     * path is required for the output folder.
     * 
     * @param jproj
     * @return
     */
    protected IPath getDefaultOutputLocation(IJavaProject jproj) {
        if (XpdResourcesPlugin.isInHeadlessMode()) {
            return jproj.getProject().getFullPath().append("bin"); //$NON-NLS-1$
        } else {
            IPreferenceStore store = PreferenceConstants.getPreferenceStore();
            if (store.getBoolean(PreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ)) {
                String outputLocationName =
                        store.getString(PreferenceConstants.SRCBIN_BINNAME);
                return jproj.getProject().getFullPath()
                        .append(outputLocationName);
            } else {
                return jproj.getProject().getFullPath();
            }
        }
    }

    /**
     * Get the default source (src) folder (full) path for the java project
     * being created. The default implementation will return the source folder
     * as set in the Java preferences. Subclasses may override if a different
     * path is required for the source folder.
     * 
     * @param jproj
     * @return
     */
    protected IPath getDefaultSrcLocation(IJavaProject jproj) {
        if (XpdResourcesPlugin.isInHeadlessMode()) {
            return jproj.getProject().getFullPath().append("src"); //$NON-NLS-1$
        } else {
            IPreferenceStore store = PreferenceConstants.getPreferenceStore();
            if (store.getBoolean(PreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ)) {
                String srcLocationName =
                        store.getString(PreferenceConstants.SRCBIN_SRCNAME);
                return jproj.getProject().getFullPath().append(srcLocationName);
            } else {
                return jproj.getProject().getFullPath();
            }
        }
    }

    /**
     * Create a project. The default implementation will create a java project.
     * 
     * @param project
     *            handle to the project to create
     * @param dependencies
     *            project dependencies to set for the new project
     * @param data
     *            context data
     * @return
     * @throws CoreException
     */
    protected IProject createProject(IProject project, IProject[] dependencies,
            GeneratorData data) throws CoreException {
        if (project != null && !project.exists()) {
            IWorkspaceRoot root = project.getWorkspace().getRoot();
            // Create project and add the java nature
            project.create(null);
            project.open(null);
            IProjectDescription description = project.getDescription();
            description.setNatureIds(new String[] { JavaCore.NATURE_ID });
            if (dependencies != null && dependencies.length > 0) {
                description.setReferencedProjects(dependencies);
            }
            project.setDescription(description, null);
            // Create java project
            IJavaProject javaProject = JavaCore.create(project);
            // Create source folder
            IPath srcLocation = getDefaultSrcLocation(javaProject);
            IResource srcResource = null;
            if ((srcResource = root.findMember(srcLocation)) == null) {
                // Create source folder
                srcResource = root.getFolder(srcLocation);
                ProjectUtil.createFolder((IFolder) srcResource,
                        false,
                        new NullProgressMonitor());
            }
            // Create output folder
            IPath binLocation = getDefaultOutputLocation(javaProject);
            if (root.findMember(binLocation) == null) {
                // Create output folder
                IFolder folder = root.getFolder(binLocation);
                ProjectUtil.createFolder(folder,
                        false,
                        new NullProgressMonitor());
            }
            javaProject.setOutputLocation(binLocation,
                    new NullProgressMonitor());

            List<IClasspathEntry> classPathEntries =
                    new ArrayList<IClasspathEntry>();
            // Add source folder to the class path
            if (srcResource != null) {
                IPackageFragmentRoot fragmentRoot =
                        javaProject.getPackageFragmentRoot(srcResource);
                classPathEntries.add(JavaCore.newSourceEntry(fragmentRoot
                        .getPath()));
            }

            // Find an execution environment with '1.6' in its name
            IExecutionEnvironment execEnv = null;
            for (IExecutionEnvironment ee : JavaRuntime
                    .getExecutionEnvironmentsManager()
                    .getExecutionEnvironments()) {
                if (ee.getId().contains("1.6")) {
                    execEnv = ee;
                    break;
                }
            }
            if (execEnv != null) {
                // A 1.6 EE was found, so get the JRE container path for it.
                IPath newPath = JavaRuntime.newJREContainerPath(execEnv);
                classPathEntries.add(JavaCore.newContainerEntry(newPath));
            } else {
                // No 1.6 EE found, so use old behaviour
                // Add JRE libraries
                IClasspathEntry[] entries =
                        PreferenceConstants.getDefaultJRELibrary();
                if (entries != null) {
                    classPathEntries.addAll(Arrays.asList(entries));
                }
            }

            javaProject.setRawClasspath(classPathEntries
                    .toArray(new IClasspathEntry[classPathEntries.size()]),
                    new NullProgressMonitor());

            // Set all resources in the project as derived
            if (project.isAccessible()) {
                setProjectAsDerived(project);
            }
        }
        return project;
    }

    /**
     * Set all resources contained in the given project as derived.
     * 
     * @param project
     * @throws CoreException
     */
    private void setProjectAsDerived(IProject project) throws CoreException {
        project.accept(new IResourceVisitor() {
            @Override
            public boolean visit(IResource resource) throws CoreException {
                if (resource != null) {
                    resource.setDerived(true);
                }
                return true;
            }
        }, IResource.DEPTH_INFINITE, 0);
    }

    /**
     * Check if the generated artifact for the given bom resource is current.
     * The default implementation will check if the project for this resource
     * was re-generate by the call to
     * {@link #initialize(Collection, GeneratorData, IProgressMonitor)
     * initialize}. If <code>initialize</code> method is overwritten then this
     * method should be overwritten to determine if re-generation of artifacts
     * is required.
     * 
     * @param bomFile
     * @return <code>true</code> if the artifacts generated for this BOM
     *         resource is current, ie does not need re-generation.
     *         <code>false</code> if the artifacts should be generated.
     */
    protected boolean needsGenerating(IFile bomFile) {
        if (bomFile != null) {
            IProject project = getProject(bomFile);
            return project != null && regeneratedProjects.contains(project);
        }
        return false;
    }

    /**
     * Get the staging project for the given resource. The staging projects are
     * created by the default
     * {@link #initialize(Collection, GeneratorData, IProgressMonitor)
     * initialise} method.
     * 
     * @param bomFile
     * @return {@link IProject} if one has been created for the given resource,
     *         <code>null</code> otherwise.
     */
    protected IProject getProject(IFile bomFile) {
        return stagingProjects.get(bomFile);
    }

    /**
     * Get the extension that contributes this generator.
     * 
     * @return BOM generator extension.
     */
    private BOMGenerator2Extension getExtension() {
        return extension;
    }

    /**
     * Ask the user if the given file can be overwritten.
     * 
     * @param jarFile
     * @return
     */
    private OverwriteStatus canOverwrite(final File jarFile) {
        final OverwriteStatus[] status = new OverwriteStatus[] { null };

        if (jarFile != null) {
            final Display display = XpdResourcesPlugin.getStandardDisplay();
            display.syncExec(new Runnable() {

                @Override
                public void run() {
                    MessageDialog dlg =
                            new MessageDialog(
                                    display.getActiveShell(),
                                    Messages.BOMGenerator2_fileExists_dialog_title,
                                    null,
                                    String.format(Messages.BOMGenerator2_fileExists_dialog_message,
                                            jarFile.getPath().toString()),
                                    MessageDialog.QUESTION, new String[] {
                                            IDialogConstants.YES_LABEL,
                                            IDialogConstants.YES_TO_ALL_LABEL,
                                            IDialogConstants.NO_LABEL,
                                            IDialogConstants.CANCEL_LABEL }, 0);
                    int result = dlg.open();
                    switch (result) {
                    case 0: // Yes
                        status[0] = OverwriteStatus.OVERWRITE;
                        break;
                    case 1: // Yes To All
                        status[0] = OverwriteStatus.OVERWRITEALL;
                        break;
                    case 2: // No
                        status[0] = OverwriteStatus.NOOVERWRITE;
                        break;
                    }
                }

            });
        }

        return status[0];
    }

    /**
     * This method is invoked on all generators immediately prior to an BOM
     * generator individual build cycle taking place.
     * <p>
     * It is intended for use by the sub-class to allow pre-preparation for
     * build-cycle-lifetime-scoped cached state etc.
     * <p>
     * The {@link #buildCycleComplete(IProject)} is guaranteed to be called at
     * the end of the build cycle.
     * 
     * @param project
     * @param buildType
     * 
     * @since 3.5
     */
    public void prepareForBuildCycle(IProject project, BuildType buildType) {
        return;
    }

    /**
     * Called at the end o an individual BOM generator build cycle.
     * <p>
     * Provides opportunity to the sub-class to discard
     * build-cycle-lifetime-scoped state
     * 
     * @param project
     * 
     * @since 3.5
     */
    public void buildCycleComplete(IProject project) {
        return;
    }

    protected void addJavaPDEBuilders(IProject project) throws CoreException {
        IProjectDescription projectDescription = project.getDescription();
        ICommand[] builders = projectDescription.getBuildSpec();
        if (builders == null) {
            builders = new ICommand[0];
        }
        boolean hasJavaBuilder = false;
        boolean hasManifestBuilder = false;
        boolean hasSchemaBuilder = false;
        for (int i = 0; i < builders.length; ++i) {
            if ("org.eclipse.jdt.core.javabuilder".equals(builders[i] //$NON-NLS-1$
                    .getBuilderName())) {
                hasJavaBuilder = true;
            }
            if ("org.eclipse.pde.ManifestBuilder".equals(builders[i] //$NON-NLS-1$
                    .getBuilderName())) {
                hasManifestBuilder = true;
            }
            if ("org.eclipse.pde.SchemaBuilder".equals(builders[i] //$NON-NLS-1$
                    .getBuilderName())) {
                hasSchemaBuilder = true;
            }
        }
        if (!hasJavaBuilder) {
            ICommand[] oldBuilders = builders;
            builders = new ICommand[oldBuilders.length + 1];
            System.arraycopy(oldBuilders, 0, builders, 0, oldBuilders.length);
            builders[oldBuilders.length] = projectDescription.newCommand();
            builders[oldBuilders.length]
                    .setBuilderName("org.eclipse.jdt.core.javabuilder"); //$NON-NLS-1$
        }
        if (!hasManifestBuilder) {
            ICommand[] oldBuilders = builders;
            builders = new ICommand[oldBuilders.length + 1];
            System.arraycopy(oldBuilders, 0, builders, 0, oldBuilders.length);
            builders[oldBuilders.length] = projectDescription.newCommand();
            builders[oldBuilders.length]
                    .setBuilderName("org.eclipse.pde.ManifestBuilder"); //$NON-NLS-1$
        }
        if (!hasSchemaBuilder) {
            ICommand[] oldBuilders = builders;
            builders = new ICommand[oldBuilders.length + 1];
            System.arraycopy(oldBuilders, 0, builders, 0, oldBuilders.length);
            builders[oldBuilders.length] = projectDescription.newCommand();
            builders[oldBuilders.length]
                    .setBuilderName("org.eclipse.pde.SchemaBuilder"); //$NON-NLS-1$
        }
        projectDescription.setBuildSpec(builders);

        project.setDescription(projectDescription, new NullProgressMonitor());
    }

    protected void removeJavaPDEBuilders(IProject project) throws CoreException {
        IProjectDescription projectDescription = project.getDescription();
        ICommand[] builders = projectDescription.getBuildSpec();
        if (builders == null) {
            builders = new ICommand[0];
        }
        ArrayList<ICommand> newBuildersList =
                new ArrayList<ICommand>(Arrays.asList(builders));
        for (Iterator<ICommand> iter = newBuildersList.iterator(); iter
                .hasNext();) {
            ICommand builder = iter.next();
            if ("org.eclipse.jdt.core.javabuilder".equals(builder //$NON-NLS-1$
                    .getBuilderName())) {
                iter.remove();
            }
            if ("org.eclipse.pde.ManifestBuilder".equals(builder //$NON-NLS-1$
                    .getBuilderName())) {
                iter.remove();
            }
            if ("org.eclipse.pde.SchemaBuilder" //$NON-NLS-1$
            .equals(builder.getBuilderName())) {
                iter.remove();
            }
        }
        projectDescription.setBuildSpec(newBuildersList
                .toArray(new ICommand[newBuildersList.size()]));
        project.setDescription(projectDescription, new NullProgressMonitor());
    }
}
