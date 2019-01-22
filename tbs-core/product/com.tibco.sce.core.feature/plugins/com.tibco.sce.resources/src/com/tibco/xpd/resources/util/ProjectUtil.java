/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.DependencySorter.Arc;

/**
 * Project utility.
 * 
 * @author nwilson, Jan Arciuchiewicz, Miguel Torres
 */
public final class ProjectUtil {

    private static Logger LOG = XpdResourcesPlugin.getDefault().getLogger();

    // private static final String DEFAULT_PROJECT_LIFECYCLE_ID_PREFIX =
    //            "com.example."; //$NON-NLS-1$

    private static final String[] GENERATED_PROJ_SUFFIXS = { ".js", ".bds" }; //$NON-NLS-1$ //$NON-NLS-2$

    private static final Pattern VALID_PROJECT_NAME_CHARACTERS_PATTERN =
            Pattern.compile("^[a-zA-Z0-9\\._-]*[a-zA-Z0-9][a-zA-Z0-9\\._-]*$"); //$NON-NLS-1$

    // characters deemed to be disruptive to good formation of URIs
    public static final Pattern URI_DISRUPTIVE_CHARACTERS_PATTERN = Pattern
            .compile("[\\#\\%]"); //$NON-NLS-1$

    public static final String REFERRED_PROJECT_IS_NOT_PRECOMPILED_ISSUE_KEY =
            "precompile_issue_to_be_ignored_in_wizard_page_validation"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String PRE_COMPILED_PROJECT_NATURE_ID =
            "com.tibco.xpd.resources.preCompiledProjectNature";//$NON-NLS-1$

    private static final String PROJECT_NAMES_SEPERATOR = ","; //$NON-NLS-1$

    /** private constructor. */
    private ProjectUtil() {
    }

    public static boolean isPrecompiledProject(IProject project) {

        boolean isPrecompiledProject = false;
        try {

            if (project.isAccessible()) {

                isPrecompiledProject =
                        project.hasNature(PRE_COMPILED_PROJECT_NATURE_ID);
            }

        } catch (CoreException e) {

            e.printStackTrace();
        }
        return isPrecompiledProject;
    }

    /**
     * Adds nature to the project. It will do nothing if nature already exist.
     * 
     * @param project
     *            eclipse project.
     * @param natureId
     *            identifier of the nature.
     * @throws CoreException
     */
    public static void addNature(IProject project, String natureId)
            throws CoreException {
        if (!project.hasNature(natureId)) {
            List<String> requiredNatures = new ArrayList<String>();
            getAllRequiredNatures(project, natureId, requiredNatures);
            IProjectDescription description = project.getDescription();
            String[] natures = description.getNatureIds();
            String[] newNatures =
                    new String[natures.length + requiredNatures.size() + 1];
            System.arraycopy(natures, 0, newNatures, 0, natures.length);
            for (int newNatureIndex = natures.length, listIndex = 0; listIndex < requiredNatures
                    .size(); newNatureIndex++, listIndex++) {
                newNatures[newNatureIndex] = requiredNatures.get(listIndex);
            }
            newNatures[newNatures.length - 1] = natureId;
            description.setNatureIds(newNatures);
            project.setDescription(description, null);
        }

    }

    /**
     * Check if the given project has a migration marker against it (this will
     * indicate that the project needs migrating to the latest version).
     * 
     * @param project
     * @return <code>true</code> if the marker is found against the project,
     *         <code>false</code> otherwise.
     * @throws CoreException
     * @since 3.5
     */
    public static boolean doesProjectHaveMigrationMarker(IProject project)
            throws CoreException {
        if (project != null && project.isAccessible()) {
            IMarker[] markers =
                    project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                            true,
                            IResource.DEPTH_ZERO);

            return markers.length > 0;
        }
        return false;
    }

    private static void getAllRequiredNatures(IProject project,
            String natureId, List<String> natureList) throws CoreException {
        IProjectNatureDescriptor natureDescriptor =
                project.getWorkspace().getNatureDescriptor(natureId);
        if (natureDescriptor == null) {
            String msg =
                    String.format(Messages.ProjectUtil_CantObtainNatureDescriptor,
                            new Object[] { natureId });
            XpdResourcesPlugin.log(new CoreException(new Status(IStatus.ERROR,
                    project.getName(), msg)));
        } else {
            String[] requiredNatureIds =
                    natureDescriptor.getRequiredNatureIds();
            for (String requiredNatureId : requiredNatureIds) {
                if (!natureList.contains(requiredNatureId)) {
                    if (!project.hasNature(requiredNatureId)) {
                        getAllRequiredNatures(project,
                                requiredNatureId,
                                natureList);
                        natureList.add(requiredNatureId);
                    }
                } else {
                    String msg =
                            String.format(Messages.ProjectUtil_CyclicDepedencyIntroduced,
                                    new Object[] { natureId });
                    throw new CoreException(new Status(IStatus.ERROR,
                            project.getName(), msg));
                }
            }
        }
    }

    /**
     * Removes nature from project.
     * 
     * @param project
     *            eclipse project.
     * @param natureId
     *            identifier of the nature.
     * @throws CoreException
     */
    public static void removeNature(IProject project, String natureId)
            throws CoreException {
        if (project.hasNature(natureId)) {
            IProjectDescription description = project.getDescription();
            ArrayList<String> naturesList =
                    new ArrayList<String>(Arrays.asList(description
                            .getNatureIds()));
            naturesList.remove(natureId);
            String[] newNatures =
                    naturesList.toArray(new String[naturesList.size()]);
            description.setNatureIds(newNatures);
            project.setDescription(description, null);
        }
    }

    /**
     * Adds builder to the project. The builder is added after all other
     * existing builders.
     * 
     * @param project
     *            eclipse project.
     * @param builderId
     *            identifier of the builder to be added.
     * @throws CoreException
     */
    public static void addBuilderToProject(IProject project, String builderId)
            throws CoreException {
        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();
        boolean found = false;

        for (int i = 0; i < commands.length; ++i) {
            if (commands[i].getBuilderName().equals(builderId)) {
                found = true;
                break;
            }
        }
        if (!found) {
            ICommand command = desc.newCommand();
            command.setBuilderName(builderId);
            ICommand[] newCommands = new ICommand[commands.length + 1];

            // Add it after other builders.
            System.arraycopy(commands, 0, newCommands, 0, commands.length);
            newCommands[commands.length] = command;
            desc.setBuildSpec(newCommands);
            project.setDescription(desc, null);
        }
    }

    /**
     * Removes builder from the project.
     * 
     * @param project
     *            eclipse project.
     * @param builderId
     *            identifier of the builder to be removed.
     * @throws CoreException
     */
    public static void removeBuilderFromProject(IProject project,
            String builderId) throws CoreException {

        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();
        int index = -1;
        for (int i = 0; i < commands.length; i++) {

            if (commands[i].getBuilderName().equals(builderId)) {

                index = i;
                break;
            }
        }
        if (index != -1) {

            List<ICommand> newCommands =
                    new ArrayList<ICommand>(Arrays.asList(commands));
            newCommands.remove(index);
            desc.setBuildSpec(newCommands.toArray(new ICommand[newCommands
                    .size()]));
            project.setDescription(desc, null);
        }
    }

    /**
     * Goes thru all the resources under a project and sets the read-only flag
     * to true to disable the modifications on project and its resources.
     * 
     * @param project
     * @throws CoreException
     */
    public static void disableModificationsOnProject(IProject project)
            throws CoreException {

        IResource[] members = project.members();
        ResourceAttributes resourceAttributes = project.getResourceAttributes();
        resourceAttributes.setReadOnly(true);
        project.setResourceAttributes(resourceAttributes);

        for (IResource iResource : members) {

            iResource.accept(new IResourceVisitor() {

                @Override
                public boolean visit(IResource resource) throws CoreException {

                    ResourceAttributes resourceAttributes =
                            resource.getResourceAttributes();
                    resourceAttributes.setReadOnly(true);
                    resource.setResourceAttributes(resourceAttributes);
                    return true;
                }
            });
        }
    }

    /**
     * Goes thru all the resources under a project and sets the read-only flag
     * to false to enable the modifications on project and its resources
     * 
     * @param project
     * @throws CoreException
     */
    public static void enableModificationsOnProject(IProject project)
            throws CoreException {

        IResource[] members = project.members();
        ResourceAttributes resourceAttributes = project.getResourceAttributes();
        resourceAttributes.setReadOnly(false);
        project.setResourceAttributes(resourceAttributes);
        for (IResource iResource : members) {

            iResource.accept(new IResourceVisitor() {

                @Override
                public boolean visit(IResource resource) throws CoreException {

                    ResourceAttributes resourceAttributes =
                            resource.getResourceAttributes();
                    resourceAttributes.setReadOnly(false);
                    resource.setResourceAttributes(resourceAttributes);
                    return true;
                }
            });
        }
    }

    /**
     * Check if <i>referenceProject</i> is referenced in <i>project</i>. This
     * only checks static references and also searches through the hierarchy of
     * references (implicit references).
     * 
     * @see IProjectDescription#getReferencedProjects()
     * 
     * @param project
     *            project to check
     * @param referenceProject
     *            reference project
     * @return <code>true</code> if the reference project is already referenced
     *         in <i>project</i>.
     * @throws CoreException
     */
    public static boolean isProjectReferenced(IProject project,
            IProject referenceProject) throws CoreException {
        boolean referenced = true;

        if (project == null) {
            throw new NullPointerException("project is null."); //$NON-NLS-1$
        }

        if (referenceProject == null) {
            throw new NullPointerException("reference project is null."); //$NON-NLS-1$
        }

        if (!project.equals(referenceProject)) {
            Set<IProject> referencedProjects =
                    getReferencedProjectsHierarchy(project, null);

            referenced = false;

            for (IProject proj : referencedProjects) {
                if (proj.equals(referenceProject)) {
                    referenced = true;
                    break;
                }
            }
        }

        return referenced;
    }

    /**
     * 
     * @param contextProject
     *            the context project
     * @param targetProject
     *            the candidate project to reference.
     * @return <code>true</code> ONLY if there is a <b>DIRECT</b> project
     *         reference from the contextProject to the contextProject else
     *         return <code>false</code>
     * @since SCF 3.6.5
     * @throws CoreException
     * 
     */
    public static boolean isDirectProjectReference(IProject contextProject,
            IProject targetProject) throws CoreException {

        if (contextProject != null && targetProject != null) {

            IProject[] referencedProjects =
                    contextProject.getReferencedProjects();
            if (referencedProjects != null) {

                return Arrays.asList(referencedProjects)
                        .contains(targetProject);
            }
        }
        return false;
    }

    /**
     * Add <i>projectToReference</i> to the list of referenced project in
     * <i>project</i>.
     * 
     * @param project
     *            the project to update
     * @param projectToReference
     *            project to reference
     * @throws CoreException
     */
    public static void addReferenceProject(IProject project,
            IProject projectToReference) throws CoreException {
        if (project == null) {
            throw new NullPointerException("project is null."); //$NON-NLS-1$
        }

        if (projectToReference == null) {
            throw new NullPointerException("project to reference is null."); //$NON-NLS-1$
        }

        if (!project.equals(projectToReference)) {
            IProjectDescription description = project.getDescription();
            IProject[] referencedProjects = description.getReferencedProjects();
            Set<IProject> projectsList =
                    new HashSet<IProject>(referencedProjects.length + 1);

            projectsList.addAll(Arrays.asList(referencedProjects));
            projectsList.add(projectToReference);

            description.setReferencedProjects(projectsList
                    .toArray(new IProject[projectsList.size()]));

            project.setDescription(description, null);
        }
    }

    /**
     * Checks whether the list of projects has cyclic dependencies.
     * 
     * @param projectList
     *            eclipse list of projects.
     * 
     * @return if the list of projects has cyclic dependencies
     * @deprecated Use
     *             {@link ProjectUtil2#getReferencedProjectsHierarchy(IProject, boolean)}
     *             or
     *             {@link ProjectUtil2#getReferencingProjectsHierarchy(IProject, Set)}
     *             instead. These methods will throw an exception if a cyclic
     *             reference is identified.
     */
    @Deprecated
    public static boolean hasCyclicDependencies(List<IProject> projectList) {
        boolean hasCyclicDependency = false;
        if (projectList != null) {
            for (Iterator<IProject> iterator = projectList.iterator(); iterator
                    .hasNext();) {
                IProject project = iterator.next();
                if (project != null) {
                    hasCyclicDependency = hasCyclicDependencies(project);
                    if (hasCyclicDependency == true) {
                        hasCyclicDependency = true;
                        return hasCyclicDependency;
                    }
                }
            }
        }
        return hasCyclicDependency;
    }

    /**
     * Checks whether the project has cyclic dependencies.
     * 
     * @param project
     *            eclipse project.
     * 
     * @return if the project has cyclic dependencies
     * @deprecated Use
     *             {@link ProjectUtil2#getReferencedProjectsHierarchy(IProject, boolean)}
     *             or
     *             {@link ProjectUtil2#getReferencingProjectsHierarchy(IProject, Set)}
     *             instead. These methods will throw an exception if a cyclic
     *             reference is identified.
     */
    @Deprecated
    public static boolean hasCyclicDependencies(IProject project) {
        boolean hasCyclicDependency = false;
        if (project != null) {
            Set<IProject> referencingProjectsHierarchy =
                    getReferencingProjectsHierarchy(project,
                            new HashSet<IProject>());
            Set<IProject> referencedProjectsHierarchy =
                    getReferencedProjectsHierarchy(project,
                            new HashSet<IProject>());

            for (Iterator<IProject> iterator =
                    referencedProjectsHierarchy.iterator(); iterator.hasNext();) {
                IProject referencedProject = iterator.next();
                if (referencedProject != null
                        && referencingProjectsHierarchy
                                .contains(referencedProject)) {
                    hasCyclicDependency = true;
                    return hasCyclicDependency;
                }
            }

        }
        return hasCyclicDependency;
    }

    /**
     * Get the full hierarchy of referencing projects.
     * <p>
     * Note: Use {@link ProjectUtil2#getReferencingProjectsHierarchy(IProject)}
     * if a notification is required when a cyclic reference is identified in
     * the project references.
     * </p>
     * 
     * @param project
     *            eclipse project.
     * 
     * @return Set
     */
    public static Set<IProject> getReferencingProjectsHierarchy(
            IProject project, Set<IProject> referencingProjectsHierarchy) {

        if (referencingProjectsHierarchy == null) {
            referencingProjectsHierarchy = new HashSet<IProject>();
        }
        // Get the referencing Projects
        IProject[] referencingProjects = project.getReferencingProjects();
        if (referencingProjects != null) {
            for (int i = 0; i < referencingProjects.length; i++) {
                IProject referencingProject = referencingProjects[i];
                if (referencingProject != null) {
                    // Add the project to the list of projects hierarchy if it
                    // is not in the list
                    if (!referencingProjectsHierarchy
                            .contains(referencingProject)) {
                        referencingProjectsHierarchy.add(referencingProject);
                        referencingProjectsHierarchy =
                                getReferencingProjectsHierarchy(referencingProject,
                                        referencingProjectsHierarchy);
                    }
                }
            }
        }
        return referencingProjectsHierarchy;
    }

    /**
     * Get the full hierarchy of referenced projects.
     * <p>
     * Note: Use
     * {@link ProjectUtil2#getReferencedProjectsHierarchy(IProject, boolean)} if
     * a notification is required when a cyclic reference is identified in the
     * project references.
     * </p>
     * 
     * @param project
     *            eclipse project.
     * 
     * @return Set
     */
    public static Set<IProject> getReferencedProjectsHierarchy(
            IProject project, Set<IProject> referencedProjectsHierarchy) {
        return getReferencedProjectsHierarchy(project,
                referencedProjectsHierarchy,
                false);
    }

    /**
     * Get the full hierarchy of referenced projects.
     * <p>
     * Note: Use
     * {@link ProjectUtil2#getReferencedProjectsHierarchy(IProject, boolean)} if
     * a notification is required when a cyclic reference is identified in the
     * project references.
     * </p>
     * 
     * @param project
     *            eclipse project.
     * @param referencedProjectsHierarchy
     *            current set of included projects
     * @param wantXpdNatureOnly
     *            <code>true</code> if only projects with XPD nature set should
     *            be included.
     * 
     * @return Set
     * @since 3.5
     */
    public static Set<IProject> getReferencedProjectsHierarchy(
            IProject project, Set<IProject> referencedProjectsHierarchy,
            boolean wantXpdNatureOnly) {

        if (referencedProjectsHierarchy == null) {
            referencedProjectsHierarchy = new HashSet<IProject>();
        }
        /* Get the referencing Projects */
        IProject[] referencedProjects = null;
        try {
            referencedProjects = project.getReferencedProjects();
        } catch (CoreException e) {
            /*
             * Do nothing, this probably means that This project does not exist
             * or This project is not open
             */
        }
        if (referencedProjects != null) {
            for (int i = 0; i < referencedProjects.length; i++) {
                IProject referencedProject = referencedProjects[i];
                try {
                    if (referencedProject != null
                            && (!wantXpdNatureOnly || referencedProject
                                    .hasNature(XpdConsts.PROJECT_NATURE_ID))) {
                        /*
                         * Add the project to the list of projects hierarchy if
                         * it is not in the list
                         */
                        if (!referencedProjectsHierarchy
                                .contains(referencedProject)) {
                            referencedProjectsHierarchy.add(referencedProject);
                            /*
                             * SDA-450: We were calling
                             * getReferencedProjectsHierarchy(referencedProject,
                             * referencedProjectsHierarchy) that calls this
                             * method with false for wantXpdNature flag.
                             * 
                             * Instead recursively call the same method passing
                             * the wantXpdNature flag.
                             */
                            referencedProjectsHierarchy =
                                    getReferencedProjectsHierarchy(referencedProject,
                                            referencedProjectsHierarchy,
                                            wantXpdNatureOnly);
                        }
                    }
                } catch (CoreException e) {

                }
            }
        }
        return referencedProjectsHierarchy;
    }

    /**
     * Makes sure that provided folder exists. If folder doesn't exist then it
     * (and all missing parent folders) will be created. This method for
     * convenience takes IContainer, but it will simply exit if IContainer is
     * not IFolder.
     * 
     * @param folder
     *            folder to be prepared (and created if not exist).
     * @param isDerived
     *            true if the created folders should be marked as derived
     *            resources.
     * @param monitor
     *            progress monitor.
     * @throws CoreException
     *             if there is problem during preparation of folder.
     */
    @SuppressWarnings("deprecation")
    public static void createFolder(final IContainer folder, boolean isDerived,
            IProgressMonitor monitor) throws CoreException {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }
        if (!(folder instanceof IFolder)) {
            return;
        }
        ArrayList<String> folderNames = new ArrayList<String>();
        IContainer parent = folder;
        while (parent instanceof IFolder && !parent.exists()) {
            folderNames.add(parent.getName());
            parent = parent.getParent();
        }
        monitor.beginTask(Messages.ProjectUtil_prepareFoldersJob_message,
                folderNames.size());

        try {
            for (int i = folderNames.size() - 1; i >= 0; i--) {
                IFolder f = parent.getFolder(new Path(folderNames.get(i)));
                if (!f.exists()) {
                    f.create(true, true, monitor);
                    f.setDerived(isDerived);
                }
                parent = f;
                monitor.worked(1);
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Creates a folder in the project. The folder created is non-derived.
     * 
     * @param project
     *            - project where the folder needs created
     * @param folderName
     *            - folder name to be created under project
     * @param monitor
     *            - progress monitor
     * @return the created folder with the given folder name
     */
    @SuppressWarnings("deprecation")
    public static IFolder createFolder(IProject project, String folderName,
            IProgressMonitor monitor) {

        if (null == monitor) {

            monitor = new NullProgressMonitor();
        }
        IFolder folder = project.getFolder(folderName);
        if (!folder.exists()) {

            try {

                folder.create(true, true, null);
                folder.setDerived(false);
            } catch (CoreException e) {

                LOG.error(e);
            }
        }
        return folder;
    }

    /**
     * Returns version of the Project. It will return null if the project is
     * null.
     * 
     * @param project
     * @return
     */
    public static String getProjectVersion(IProject project) {
        ProjectDetails projectDetails = getProjectDetails(project);
        if (projectDetails == null) {
            return null;
        }
        return projectDetails.getVersion();
    }

    /**
     * Returns id of Studio Project. It will return null if the project is null.
     * 
     * @param project
     * @return
     */
    public static String getProjectId(IProject project) {
        ProjectDetails projectDetails = getProjectDetails(project);
        if (projectDetails == null) {
            return null;
        }
        return projectDetails.getId();
    }

    /**
     * Get the default id to use for the given project name.
     * 
     * @param projectName
     * @return id or <code>null</code> if the project name is either
     *         <code>null</code> or an invalid representation of a
     *         {@link org.eclipse.core.runtime.IPath IPath} segment.
     */
    public static String getDefaultProjectLifecycleId(String projectName) {
        if (projectName == null
                || (!(new Path(projectName)).isValidSegment(projectName))) {

            /*
             * XPD-4707: don't want warning cluttering up error log for when no
             * attempt has been made to set project name yet (i.e. don't add
             * warning when project name is blank).
             */
            if (projectName != null && !projectName.trim().isEmpty()) {
                String msg =
                        "Failed to determine a default ID for the project named '%s' as it is not a valid path segment: a name containing a colon, forwardslash, backslash, or an empty name will make it invalid."; //$NON-NLS-1$
                LOG.warn(String.format(msg, projectName));
            }

            return null;
        } else {
            IProject project =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(projectName);
            String domainNameFromPreferences =
                    UserInfoUtil.getProjectPreferences(project).getDomainName();
            if (domainNameFromPreferences.length() > 0) {
                projectName = domainNameFromPreferences + "." //$NON-NLS-1$
                        + projectName.toLowerCase().replaceAll("[^\\w]", ""); //$NON-NLS-1$ //$NON-NLS-2$
            }
            return projectName;
        }
    }

    /**
     * Returns the project details for a given project, from the project config.
     * If no project details are found null is returned.
     * 
     * @param project
     * @return ProjectDetails
     */
    public static ProjectDetails getProjectDetails(IProject project) {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig == null) {
            return null;
        }
        ProjectDetails projectDetails = projectConfig.getProjectDetails();
        return projectDetails;
    }

    /**
     * returns true if the given project has the xpd nature set
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    public static boolean isStudioProject(IProject project) {
        try {
            if (project == null) {
                return Boolean.FALSE;
            }
            IProjectNature nature =
                    project.getNature(XpdConsts.PROJECT_NATURE_ID);
            if (nature != null) {
                return Boolean.TRUE;
            }
        } catch (CoreException e) {
        }
        return Boolean.FALSE;
    }

    /**
     * Convenience method to return all projects in the workspace with the
     * studio nature
     * 
     * @return
     */
    public static IProject[] getAllStudioProjects() {
        IProject[] projects =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();
        List<IProject> studioProjects = new ArrayList<IProject>();
        for (int i = 0; i < projects.length; i++) {
            try {
                if (projects[i].hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    studioProjects.add(projects[i]);
                }
            } catch (CoreException e) {
            }
        }
        return studioProjects.toArray(new IProject[studioProjects.size()]);
    }

    /**
     * Removes folder's content. Does nothing if folder doesn't exist or is
     * <code>null</null>.
     * 
     * @param folder
     *            the folder to be cleaned.
     * @throws CoreException
     *             if deletion of the resource can't be done (See:
     *             {@link IResource#delete(boolean, IProgressMonitor)}).
     */
    public static void cleanFolder(IFolder folder) throws CoreException {
        if (folder != null && folder.exists()) {
            if (!folder.isSynchronized(IResource.DEPTH_ONE)) {
                folder.refreshLocal(IResource.DEPTH_ONE, null);
            }
            for (IResource res : folder.members()) {
                res.delete(true, null);
            }
        }
    }

    /**
     * @param natureId
     * @return The nature label name for the given nbature id.
     */
    public static String getNatureName(String natureId) {
        String name = null;
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint("org.eclipse.core.resources", //$NON-NLS-1$
                                "natures"); //$NON-NLS-1$
        if (extensionPoint != null) {
            for (IExtension extension : extensionPoint.getExtensions()) {
                String configId = extension.getUniqueIdentifier();

                if (natureId.equals(configId)) {
                    name = extension.getLabel();
                    break;
                }
            }
        }

        return name != null && name.length() > 0 ? name : natureId;
    }

    /**
     * @param builderId
     * @return The builder label name for the given builder.
     */
    public static String getBuilderName(String builderId) {
        String name = null;
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint("org.eclipse.core.resources", //$NON-NLS-1$
                                "builders"); //$NON-NLS-1$
        if (extensionPoint != null) {
            for (IExtension extension : extensionPoint.getExtensions()) {
                String configId = extension.getUniqueIdentifier();

                if (builderId.equals(configId)) {
                    name = extension.getLabel();
                    break;
                }
            }
        }

        return name != null && name.length() > 0 ? name : builderId;
    }

    /**
     * Validation specific to naming projects with BPM destination
     * 
     * @param projectName
     * @return
     */
    public static boolean isValidProjectName(String projectName) {
        return VALID_PROJECT_NAME_CHARACTERS_PATTERN.matcher(projectName)
                .matches();
    }

    public static boolean isHashPresentInResourceName(String resourceName) {

        Pattern pattern = Pattern.compile("[\\#]+"); //$NON-NLS-1$
        Set<Character> invalidChars =
                getInvalidCharacters(pattern, resourceName);

        return (invalidChars.size() > 0);
    }

    /**
     * @param pattern
     *            Regex pattern. Must check only for single characters
     * @param input
     * @return Characters found in input which are mentioned in the supplied
     *         regex pattern
     */
    public static Set<Character> getInvalidCharacters(Pattern pattern,
            String input) {

        Set<Character> invalidChars = new HashSet<Character>();

        Matcher matcher = pattern.matcher(input);
        while (matcher.find() && (matcher.group().length() == 1)) {
            char ch = input.charAt(matcher.start());
            invalidChars.add(ch);
        }

        return invalidChars;
    }

    /**
     * @param pattern
     *            Regex pattern. Must check only for single characters
     * @param input
     * @return list of invalid characters found in the input. List is put in a
     *         format ready for display. <code>null</code> if no invalid
     *         characters found.
     */
    public static String getDisplayableLstOfInvalidCharacters(Pattern pattern,
            String input) {

        Set<Character> invalidChars =
                ProjectUtil.getInvalidCharacters(pattern, input);
        if (invalidChars.size() > 0) {

            final String LST_SEP = "; "; //$NON-NLS-1$
            StringBuilder sb = new StringBuilder("["); //$NON-NLS-1$
            for (Character invalidChar : invalidChars) {
                sb.append(invalidChar).append(LST_SEP);
            }
            sb.setLength(sb.length() - LST_SEP.length());
            sb.append("]"); //$NON-NLS-1$

            return sb.toString();
        }

        return null;
    }

    /**
     * Returns an ordered list of the projects (sorted according to dependency
     * (which is expressed by project references)). Also all duplicates (if
     * exists) will be removed.
     * 
     * @param projects
     *            the collection of projects.
     * @return the sorted project list. It is guaranteed that if project A
     *         depends on B, then B will appear earlier in a list (it will also
     *         cover indirect dependencies).
     * @throws CoreException
     *             if one of the provided project doesn't exist or it's not open
     *             or in case of cyclic dependency between projects
     */
    public static List<IProject> getDependencySortedProjects(
            Collection<IProject> projects) throws CoreException {
        HashSet<IProject> projectSet = new HashSet<IProject>(projects);
        List<IProject> nodes = new ArrayList<IProject>(projectSet);
        List<DependencySorter.Arc<IProject>> arcs =
                new ArrayList<Arc<IProject>>();
        for (IProject project : projects) {
            /* Use full hierarchy to cover transitive dependencies. */
            Set<IProject> referencedProjects;
            try {
                referencedProjects =
                        ProjectUtil2.getReferencedProjectsHierarchy(project,
                                false);
            } catch (CyclicDependencyException e) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN,
                                Messages.ProjectUtil_CyclicDependencyDiscovered_message,
                                e));
            }
            for (IProject refProject : referencedProjects) {
                if (projectSet.contains(refProject)) {
                    arcs.add(new DependencySorter.Arc<IProject>(project,
                            refProject));
                }
            }
        }
        final DependencySorter<IProject> dependencySorter =
                new DependencySorter<IProject>(arcs, nodes);
        return dependencySorter.getOrderedList();
    }

    /**
     * Determine whether a project's name represents a generated project.
     * 
     * @param projName
     *            Project's name
     * @return true if projName represents a generated project
     */
    public static boolean isGeneratedProject(String projName) {

        for (String suffix : GENERATED_PROJ_SUFFIXS) {
            if (projName.endsWith(suffix)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check whether cancellation is requested, and if yes, cancel the current
     * progress. <code>builder</code> can be provided to force it forget
     * <i>build state</i>.
     * 
     * @param monitor
     *            Progress monitor
     * @param builder
     *            Project builder
     */
    public static void checkBuildCancel(IProgressMonitor monitor,
            IncrementalProjectBuilder builder) {
        if (monitor.isCanceled()) {
            if (builder != null) {
                builder.forgetLastBuiltState();
            }
            throw new OperationCanceledException();
        }
    }

    /**
     * If the given referencedObject is not in same project as contextObject and
     * not in a project already referenced by the contextObject's project then
     * ask user if they wish to create the project reference.
     * <p>
     * If the user selected "Yes" then the project reference is added.
     * 
     * @param shell
     * @param contextObject
     * @param referencedObject
     * 
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReference(Shell shell,
            EObject contextObject, EObject referencedObject) {
        boolean ret = true;

        IFile contextFile = WorkingCopyUtil.getFile(contextObject);
        IFile elementFile = WorkingCopyUtil.getFile(referencedObject);

        if (elementFile != null && contextFile != null) {
            IProject contextProject = contextFile.getProject();
            IProject elementProject = elementFile.getProject();

            ret =
                    checkAndAddProjectReference(shell,
                            contextProject,
                            elementProject);
        }

        return ret;
    }

    /**
     * If the given referencedObject is not in same project as contextObject and
     * not in a project already referenced by the contextObject's project then
     * ask user if they wish to create the project reference.
     * <p>
     * If the user selected "Yes" then the project reference is added.
     * 
     * @param shell
     * @param ret
     * @param contextProject
     * @param elementProject
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReference(Shell shell,
            IProject contextProject, IProject elementProject) {
        boolean ret = true;

        if (!contextProject.equals(elementProject)) {
            // Not the same project so check reference
            try {
                /*
                 * XPD-7519: Add project reference if the target project is not
                 * DIRECTLY referenced by the context project.
                 */
                if (!ProjectUtil.isDirectProjectReference(contextProject,
                        elementProject)) {

                    // Check if there could be a cyclic
                    // dependency
                    if (!ProjectUtil.isProjectReferenced(elementProject,
                            contextProject)) {
                        // Need to reference project
                        if (MessageDialogUtil
                                .openYesNoQuestion(shell,
                                        Messages.ProjectUtil_AddProjectReferenceDialog_title,
                                        String.format(Messages.ProjectUtil_AddProjectRefDialog_desc,
                                                elementProject.getName()),
                                        Messages.ProjectUtil_AddProjectRefWithoutAskingButton_desc,
                                        false,
                                        XpdConsts.PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE,
                                        XpdResourcesPlugin.getDefault()
                                                .getPreferenceStore()) == IDialogConstants.YES_ID) {
                            // Reference the project
                            ProjectUtil.addReferenceProject(contextProject,
                                    elementProject);
                        } else {
                            // Project not referenced
                            ret = false;
                        }
                    } else {
                        // This will be a cyclic dependency
                        MessageDialog
                                .openWarning(shell,
                                        Messages.ProjectUtil_AddProjectReferenceDialog_title,
                                        String.format(Messages.ProjectUtil_CyclicDependencyError_msg,
                                                elementProject.getName(),
                                                contextProject.getName()));
                        ret = false;
                    }
                }
            } catch (CoreException e) {
                ErrorDialog.openError(shell,
                        Messages.ProjectUtil_AddProjectReferenceDialog_title,
                        Messages.ProjectUtil_ErrorInCheckProjectRef_msg,
                        e.getStatus());
                ret = false;
            }
        }
        return ret;
    }

    /**
     * If the given referencedObjects are not in same project as contextObject
     * and not in a project already referenced by the contextObject's project
     * then ask user if they wish to create the project reference.
     * <p>
     * If the user selected "Yes" then the project reference is added.
     * <p>
     * The user is asked a maximum of once per referenced project.
     * 
     * @param shell
     * @param contextObject
     * @param referencedObject
     * 
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReferences(Shell shell,
            EObject contextObject, Collection<EObject> referencedObjects) {
        //
        // Ask user to confirm for 'need to create a project reference' if any
        // are dragged from different process.
        Set<IProject> checkedProjectRefs = new HashSet<IProject>();

        for (EObject eo : referencedObjects) {
            IFile file = WorkingCopyUtil.getFile(eo);
            if (file != null) {
                IProject project = file.getProject();
                if (project != null) {
                    if (!checkedProjectRefs.contains(project)) {
                        // first time for each project we need to ask user
                        // whether to create project ref or not.
                        if (!ProjectUtil.checkAndAddProjectReference(Display
                                .getDefault().getActiveShell(),
                                contextObject,
                                eo)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * If the given project in the required reference list, is not already
     * referenced by contextProject then ask user if they wish to create the
     * project reference.Show a warning if any of the required references can
     * lead to cyclic dependency.
     * 
     * If the user selected "Yes" then the project reference is added.
     * 
     * @param shell
     * @param ret
     * @param contextProject
     * @param projectRefListToAdd
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReference(Shell shell,
            IProject contextProject, Collection<IProject> projectRefListToAdd) {
        boolean ret = true;
        StringBuffer projectsList = new StringBuffer();
        Collection<IProject> filteredProjectToAdd = new ArrayList<IProject>();
        try {
            for (IProject iProject : projectRefListToAdd) {
                if (!contextProject.equals(iProject)) {
                    // Not the same project so check reference
                    /*
                     * XPD-7519: Add project reference if the target project is
                     * not DIRECTLY referenced by the context project.
                     */
                    if (!ProjectUtil.isDirectProjectReference(contextProject,
                            iProject)) {

                        // Check if there could be a cyclic
                        // dependency
                        if (ProjectUtil.isProjectReferenced(iProject,
                                contextProject)) {
                            // This will be a cyclic dependency
                            MessageDialog
                                    .openWarning(shell,
                                            Messages.ProjectUtil_AddProjectReferenceDialog_title,
                                            String.format(Messages.ProjectUtil_CyclicDependencyError_msg,
                                                    iProject.getName(),
                                                    contextProject.getName()));
                            ret = false;
                        } else {
                            if (projectsList.length() > 0) {
                                projectsList.append(PROJECT_NAMES_SEPERATOR);
                            }
                            filteredProjectToAdd.add(iProject);
                            projectsList.append(iProject.getName());
                        }
                    }

                }
            }
            // Need to reference project
            if (!filteredProjectToAdd.isEmpty()) {
                if (MessageDialogUtil
                        .openYesNoQuestion(shell,
                                Messages.ProjectUtil_AddProjectReferenceDialog_title,
                                String.format(Messages.ProjectUtil_AddProjectRefDialog_desc,
                                        projectsList),
                                Messages.ProjectUtil_AddProjectRefWithoutAskingButton_desc,
                                false,
                                XpdConsts.PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE,
                                XpdResourcesPlugin.getDefault()
                                        .getPreferenceStore()) == IDialogConstants.YES_ID) {
                    for (IProject iProject : filteredProjectToAdd) {
                        // Reference the project

                        ProjectUtil.addReferenceProject(contextProject,
                                iProject);

                    }
                } else {
                    ret = false;
                }
            }
        } catch (CoreException e) {
            ErrorDialog.openError(shell,
                    Messages.ProjectUtil_AddProjectReferenceDialog_title,
                    Messages.ProjectUtil_ErrorInCheckProjectRef_msg,
                    e.getStatus());
            ret = false;

        }
        return ret;

    }
}
