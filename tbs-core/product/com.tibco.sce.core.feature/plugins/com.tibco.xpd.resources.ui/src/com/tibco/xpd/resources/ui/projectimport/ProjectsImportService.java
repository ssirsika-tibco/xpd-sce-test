/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.ui.projectimport;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileManipulations;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.TarException;
import org.eclipse.ui.internal.wizards.datatransfer.TarFile;
import org.eclipse.ui.internal.wizards.datatransfer.TarLeveledStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Service that is designed not to depend on presence of a Workbench. Initially
 * written for use with headless project importing. Currently duplicates code
 * found in the
 * {@link com.tibco.xpd.resources.ui.internal.importexport.ProjectsImportPage}
 * which is not completely decoupled from UI.
 * 
 * @author patkinso
 * @since 19 Apr 2013
 */
public class ProjectsImportService {

    /**
     * Batch up import of projects
     */
    protected class ProjectImportRunnable implements IWorkspaceRunnable {

        private final ProjectImporter[] projectsToImport;

        private final boolean skipPostImportTask;

        private List<IProject> importedProjects = new ArrayList<IProject>();

        /**
         * @param projectsToImport
         */
        public ProjectImportRunnable(ProjectImporter[] projectsToImport,
                boolean skipPostImportTask) {
            this.projectsToImport = projectsToImport;
            this.skipPostImportTask = skipPostImportTask;
        }

        /**
         * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param monitor
         * @throws CoreException
         */
        @Override
        public void run(IProgressMonitor monitor) throws CoreException {

            if (projectsToImport != null && projectsToImport.length > 0) {

                monitor.beginTask("Importing projects...", projectsToImport.length * 2); //$NON-NLS-1$ 

                Map<String, ProjectImporter> projectsRemainingImport =
                        getProjectsImportRemaining();

                /* import projects */
                for (ProjectImporter projectRec : projectsToImport) {
                    importProjectHierarchy(projectRec,
                            importedProjects,
                            projectsRemainingImport,
                            monitor);
                }

                /* temp measure to avoid deadlock */
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*
                 * Run the post import tasks AFTER ALL the imports.
                 * 
                 * This is because any one of the import tasks COULD cause the
                 * working copies to be loaded for the files in a project.
                 * 
                 * This will cause the files to be indexed.
                 * 
                 * If a dependent project hasn't been imported yet then all
                 * sorts of indexing could fail.
                 */
                List<IProject> sortedImportedProjects =
                        ProjectUtil
                                .getDependencySortedProjects(importedProjects);

                if (!skipPostImportTask) {
                    PostImportUtil.getInstance()
                            .performPostImportTasks(sortedImportedProjects,
                                    SubProgressMonitorEx
                                            .createMainProgressMonitor(monitor,
                                                    1));
                }

            }
        }

        /**
         * @param projectsToImport
         * @return
         */
        protected Map<String, ProjectImporter> getProjectsImportRemaining() {
            Map<String, ProjectImporter> ret =
                    new HashMap<String, ProjectImporter>();

            for (ProjectImporter rec : projectsToImport) {
                ret.put(rec.getName(), rec);
            }
            return ret;
        }

        /**
         * @param projectRec
         * @param importedProjects
         * @param pendingImport
         * @param monitor
         */
        private void importProjectHierarchy(ProjectImporter projectRec,
                List<IProject> importedProjects,
                Map<String, ProjectImporter> pendingImport,
                IProgressMonitor monitor) {

            /* If user has cancelled then stop */
            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            /* create all descendant projects first using recursive calls */
            IProject[] referenced = projectRec.getReferencedProjects();

            if ((referenced != null) && (referenced.length > 0)) {

                for (IProject proj : referenced) {
                    if (pendingImport.containsKey(proj.getName())) {
                        ProjectImporter childProjectRec =
                                pendingImport.get(proj.getName());
                        importProjectHierarchy(childProjectRec,
                                importedProjects,
                                pendingImport,
                                monitor);
                    }
                }
            }

            /* import current project */
            if (pendingImport.containsKey(projectRec.getName())) {
                SubProgressMonitorEx progressMonitor =
                        SubProgressMonitorEx.createMainProgressMonitor(monitor,
                                1);
                IProject createdProj =
                        projectRec.importProject(progressMonitor);
                if (createdProj != null /* ????? && createdProj.exists() */) {
                    pendingImport.remove(projectRec.getName());
                    importedProjects.add(createdProj);
                }
            }

        }

    }

    private static ProjectsImportService instance = null;

    private FilenameFilter archiveFileNameFilter = new ArchiveFilenameFilter();

    private ProjectsImportService() {
        super();
    }

    public static ProjectsImportService getInstance() {
        if (instance == null) {
            instance = new ProjectsImportService();
        }
        return instance;
    }

    /**
     * Batch up importing of all pending projects in one operation
     * 
     * @param viableDirectories
     * @return
     */
    public List<IProject> doProjectsImport(ProjectImporter[] projectsToImport,
            boolean skipPostImportTask) {

        ProjectImportRunnable projectImportRunnable =
                new ProjectImportRunnable(projectsToImport, skipPostImportTask);

        try {
            ResourcesPlugin.getWorkspace().run(projectImportRunnable, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return projectImportRunnable.importedProjects;
    }

    /**
     * @param viableResources
     */
    public ProjectImporter[] getProjectRepresentations(File... viableResources)
            throws IllegalArgumentException {

        if (isResourceSetAllDirectories(viableResources)) {
            return getDirectoryProjectRepresentations(viableResources);
        }

        if (isResourceSetAllArchiveFiles(viableResources)) {
            return getArchiveProjectRepresentations(viableResources);
        }

        String errMsg;

        if (!resourceExists(viableResources)) {
            errMsg = "No viable import locations could be determined."; //$NON-NLS-1$
        } else {
            errMsg =
                    "The array of java.io.Files contains a mix of folders and files."; //$NON-NLS-1$
        }

        throw new IllegalArgumentException(errMsg);
    }

    /**
     * @param viableResources
     * @return <code>true</code> if any viable resource locations have been
     *         defined
     */
    private boolean resourceExists(File[] viableResources) {
        for (File resource : viableResources) {
            if (resource != null && resource.exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param viableResources
     */
    private boolean isResourceSetAllArchiveFiles(File[] viableResources) {
        boolean allArchiveFiles = true;
        for (int i = 0; (i < viableResources.length) && allArchiveFiles; i++) {
            allArchiveFiles &= viableResources[i].isFile();
            allArchiveFiles &=
                    archiveFileNameFilter.accept(null,
                            viableResources[i].getName());
        }
        return allArchiveFiles;
    }

    /**
     * @param viableResources
     * @return
     */
    private boolean isResourceSetAllDirectories(File[] viableResources) {
        boolean allDirectories = true;
        for (int i = 0; (i < viableResources.length) && allDirectories; i++) {
            allDirectories &= viableResources[i].isDirectory();
        }
        return allDirectories;
    }

    /**
     * @param viableResources
     * @deprecated
     */
    @Deprecated
    public ProjectImporter[] getDirectoryProjects(File[] viableResources) {

        Set<ProjectImporter> projectRecords = new HashSet<ProjectImporter>();

        // attempt to get .project file from viable directory
        for (File dotProjectFile : getDotProjectFiles(viableResources)) {
            try {
                ProjectImporter rec =
                        new DirectoryProjectImporter(dotProjectFile);
                if (isProjectInWorkspace(rec.getName())) {
                    // log that project already in workspace
                } else {
                    projectRecords.add(rec);
                }
            } catch (CoreException e) {
                XpdResourcesUIActivator.getDefault().getLog()
                        .log(e.getStatus());
            }
        }

        return projectRecords
                .toArray(new ProjectImporter[projectRecords.size()]);
    }

    /**
     * @param viableDirectories
     * @return
     */
    private ProjectImporter[] getDirectoryProjectRepresentations(
            File[] viableDirectories) {

        Set<File> directoriesVisited = new HashSet<File>();
        Set<ProjectImporter> projectImporters = new HashSet<ProjectImporter>();

        for (File dir : viableDirectories) {
            appendProjectImporters(projectImporters, directoriesVisited, dir);
        }

        return projectImporters.toArray(new ProjectImporter[projectImporters
                .size()]);
    }

    /**
     * @param viableResources
     * @return
     */
    private ProjectImporter[] getArchiveProjectRepresentations(
            File[] viableResources) {

        final String zipSuffix = SupportedArchiveFileTypeEnum.ZIP.getSuffix();
        final String tarSuffix = SupportedArchiveFileTypeEnum.TAR.getSuffix();
        final String compressedTarSuffix =
                SupportedArchiveFileTypeEnum.COMPRESSED_TAR.getSuffix();

        Set<ProjectImporter> projectImporters = new HashSet<ProjectImporter>();

        for (File archiveFile : viableResources) {
            String archiveName = archiveFile.getName();
            String archivePath = archiveFile.getPath();
            ILeveledImportStructureProvider archiveStructureProvider = null;

            if (archiveName.endsWith(zipSuffix)
                    && ArchiveFileManipulations.isZipFile(archivePath)) {
                archiveStructureProvider = getZipStructureProvider(archiveFile);
            } else if ((archiveName.endsWith(tarSuffix) || archiveName
                    .endsWith(compressedTarSuffix))
                    && ArchiveFileManipulations.isTarFile(archivePath)) {
                archiveStructureProvider = getTarStructureProvider(archiveFile);
            } else {
                // TODO log archive %s found not to be a zip/tar file!
                return null;
            }

            Object root = archiveStructureProvider.getRoot();
            appendProjectImporters(projectImporters,
                    archiveStructureProvider,
                    root,
                    0
            /* ,archiveFile */);
        }

        return projectImporters.toArray(new ProjectImporter[projectImporters
                .size()]);
    }

    /**
     * @param archiveFile
     * @return
     */
    private TarLeveledStructureProvider getTarStructureProvider(File archiveFile) {

        TarLeveledStructureProvider tarStructureProvider = null;

        TarFile tarFile;
        try {
            tarFile = new TarFile(archiveFile);
            tarStructureProvider = new TarLeveledStructureProvider(tarFile);
        } catch (TarException e) {
            // TODO log "Source file is not a valid tar file."
        } catch (IOException e) {
            // TODO log Source file could not be read.
        }

        return tarStructureProvider;

    }

    /**
     * @param archiveFile
     */
    private ZipLeveledStructureProvider getZipStructureProvider(File archiveFile) {

        ZipLeveledStructureProvider zipStructureProvider = null;
        try {
            ZipFile zipFile = new ZipFile(archiveFile);
            zipStructureProvider = new ZipLeveledStructureProvider(zipFile);
        } catch (ZipException e) {
            // TODO log "Source file is not a valid Zip file."
        } catch (IOException e) {
            // TODO log "Source file could not be read."
        }

        return zipStructureProvider;
    }

    /**
     * @param projectImporters
     * @param archiveFile
     */
    private void appendProjectImporters(Set<ProjectImporter> projectImporters,
            ILeveledImportStructureProvider archiveStructureProvider,
            Object parent, int level) {

        List<?> children = archiveStructureProvider.getChildren(parent);
        if (children == null) {
            children = Collections.emptyList();
        }

        for (Object child : children) {
            if (archiveStructureProvider.isFolder(child)) {
                appendProjectImporters(projectImporters,
                        archiveStructureProvider,
                        child,
                        level + 1);
            }
            String childName = archiveStructureProvider.getLabel(child);
            if (childName.equals(IProjectDescription.DESCRIPTION_FILE_NAME)) {

                ProjectImporter projectImporter = null;
                if (archiveStructureProvider instanceof ZipLeveledStructureProvider) {
                    projectImporter =
                            new ArchiveFileProjectImporters.ZipFileProjectImporter(
                                    child, parent, level,
                                    archiveStructureProvider);
                } else if (archiveStructureProvider instanceof TarLeveledStructureProvider) {
                    projectImporter =
                            new ArchiveFileProjectImporters.TarFileProjectImporter(
                                    child, parent, level,
                                    archiveStructureProvider);
                } else {
                    // log error
                    return;
                }

                projectImporters.add(projectImporter);
            }
        }
    }

    /**
     * Import projects from file system directories
     * 
     * @param projectImporters
     * @param directoriesVisited
     * @param dir
     */
    private void appendProjectImporters(Set<ProjectImporter> projectImporters,
            Set<File> directoriesVisited, File dir) {

        if (isCandidateDirectory(dir) && !directoriesVisited.contains(dir)) {
            directoriesVisited.add(dir);

            // Look in current directory for project description file
            File[] dotProjectResources = dir.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return IProjectDescription.DESCRIPTION_FILE_NAME
                            .equals(name);
                }

            });

            if (dotProjectResources != null
                    && (dotProjectResources.length != 0)) {
                for (File dotProjectResource : dotProjectResources) {
                    if (dotProjectResource.isFile()) {
                        if (appendProjectImporter(projectImporters,
                                dotProjectResource)) {
                            // Only expect a single .project file per project
                            break;
                        }
                    }
                }
                // Cannot have nested projects so no point searching
                // sub-directories
            } else {

                File[] subDirs = dir.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(File pathname) {
                        return isCandidateDirectory(pathname);
                    }

                });

                for (File subDir : subDirs) {
                    appendProjectImporters(projectImporters,
                            directoriesVisited,
                            subDir);
                }
            }
        }
    }

    /**
     * @param projectImporters
     * @param dotProjectResource
     */
    private boolean appendProjectImporter(
            Set<ProjectImporter> projectImporters, File dotProjectResource) {
        try {
            ProjectImporter projectImporter =
                    new DirectoryProjectImporter(dotProjectResource);
            if (!isProjectInWorkspace(projectImporter.getName())) {
                return projectImporters.add(projectImporter);
            }
        } catch (CoreException e) {
            XpdResourcesUIActivator.getDefault().getLog().log(e.getStatus());
        }

        return false;
    }

    /**
     * @param dirs
     * @return
     * @deprecated
     */
    @Deprecated
    protected File[] getDotProjectFiles(File[] dirs) {

        Set<File> directoriesVisited = new HashSet<File>();
        Set<File> dotProjectFiles = new HashSet<File>();

        for (File dir : dirs) {
            appendDotProjectFiles(dotProjectFiles, directoriesVisited, dir);
        }

        return dotProjectFiles.toArray(new File[dotProjectFiles.size()]);
    }

    /**
     * @param dotProjectFiles
     * @param directoriesVisited
     * @param dir
     * @deprecated
     */
    @Deprecated
    private void appendDotProjectFiles(Set<File> dotProjectFiles,
            Set<File> directoriesVisited, File dir) {

        if (isCandidateDirectory(dir) && !directoriesVisited.contains(dir)) {
            directoriesVisited.add(dir);

            // Look in current directory for project description file
            File[] dotProjectResources = dir.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return IProjectDescription.DESCRIPTION_FILE_NAME
                            .equals(name);
                }

            });

            if (dotProjectResources != null
                    && (dotProjectResources.length != 0)) {
                for (File dotProjectResource : dotProjectResources) {
                    if (dotProjectResource.isFile()) {
                        dotProjectFiles.add(dotProjectResource);
                        // Only expect a single .project file per project
                        break;
                    }
                }
                // Cannot have nested projects so no point searching
                // sub-directories
            } else {

                File[] subDirs = dir.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(File pathname) {
                        return isCandidateDirectory(pathname);
                    }

                });

                for (File subDir : subDirs) {
                    appendDotProjectFiles(dotProjectFiles,
                            directoriesVisited,
                            subDir);
                }
            }
        }

    }

    /**
     * Checks if this {@link File} representation is...</br> &nbsp;&nbsp;1) a
     * directory</br>&nbsp; 2) the directory still exists on the file
     * system</br>&nbsp; 3) it can be accessed</br>&nbsp; 4) its name does not
     * start with a '.' character: This protects against situations in which the
     * specified directory is within a pre-existing workspace and the directory
     * represents a hidden project (e.g. .bsProject).</br>
     * 
     * @param dir
     * @return
     */
    private boolean isCandidateDirectory(File dir) {
        if (dir != null) {
            String dirName = dir.getName();
            return dir.isDirectory() && dir.canRead()
                    && !dirName.startsWith("."); //$NON-NLS-1$
        }
        return false;
    }

    /**
     * Determine if the project with the given name is in the current workspace.
     * 
     * @param projectName
     *            String the project name to check
     * @return boolean true if the project with the given name is in this
     *         workspace
     */
    private boolean isProjectInWorkspace(String projectName) {

        if (projectName != null) {
            for (IProject workspaceProject : getProjectsInWorkspace()) {
                if (projectName.equals(workspaceProject.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieve all the projects in the current workspace.
     * 
     * @return IProject[] array of IProject in the current workspace
     */
    private IProject[] getProjectsInWorkspace() {
        return ResourcesPlugin.getWorkspace().getRoot().getProjects();
    }

    /**
     * @param selectedProjects
     * @return
     * @throws CyclicDependencyException
     */
    public Set<String> getMissingReferencedProjects(
            ProjectImporter[] selectedProjects) {

        Set<String> hangingProjectReferences = Collections.<String> emptySet();
        try {
            Set<IProjectDescription> visibleProjectDescriptions =
                    new HashSet<IProjectDescription>();
            Set<IProjectDescription> workspaceProjectDescriptions =
                    getProjectDescriptions(getProjectsInWorkspace());
            visibleProjectDescriptions.addAll(workspaceProjectDescriptions);
            Set<IProjectDescription> selectedProjectDescriptions =
                    getProjectDescriptions(selectedProjects);
            visibleProjectDescriptions.addAll(selectedProjectDescriptions);

            hangingProjectReferences = new HashSet<String>();
            ProjectUtil2
                    .getReferencedProjectsHierarchies(selectedProjectDescriptions,
                            hangingProjectReferences,
                            visibleProjectDescriptions);
        } catch (CyclicDependencyException e) {
            String fmtMsg =
                    "Cyclic dependency found relating to one of the following projects selected: %s (%s)"; //$NON-NLS-1$ 
            IStatus status =
                    new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                            fmtMsg, e);
            XpdResourcesUIActivator.getDefault().getLog().log(status);
        }

        return hangingProjectReferences;
    }

    /**
     * @return Set of <code>ProjectDescription</code>s extracted from an array
     *         of <code>ProjectDescription</code>s
     */
    private Set<IProjectDescription> getProjectDescriptions(
            ProjectImporter[] projects) {

        Set<IProjectDescription> ret = new HashSet<IProjectDescription>();

        for (ProjectImporter proj : projects)
            ret.add(proj.getDescription());

        return ret;
    }

    /**
     * @param projectsInWorkspace
     * @return
     */
    private Set<IProjectDescription> getProjectDescriptions(IProject[] projects) {

        Set<IProjectDescription> ret = new HashSet<IProjectDescription>();

        for (IProject proj : projects)
            try {
                ret.add(proj.getDescription());
            } catch (CoreException e) {
                e.printStackTrace();
            }

        return ret;
    }

}
