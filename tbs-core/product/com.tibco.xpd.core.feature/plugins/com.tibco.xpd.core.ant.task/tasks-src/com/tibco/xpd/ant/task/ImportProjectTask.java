/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.ant.task;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.FileScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.FileSet;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.ui.projectimport.ArchiveFilenameFilter;
import com.tibco.xpd.resources.ui.projectimport.DirectoryProjectImporter;
import com.tibco.xpd.resources.ui.projectimport.ProjectImporter;
import com.tibco.xpd.resources.ui.projectimport.ProjectsImportService;
import com.tibco.xpd.resources.util.TargetPlatformInitialiser;
import com.tibco.xpd.validation.ValidationActivator;

/**
 * Task to import BPM projects from directories or archives based up Ant dirSets
 * and/or Ant fileSets.
 * 
 * A 'useArchives' attribute can be used to ensure only projects from archives
 * are considered. When projects are coming direct from directories you can
 * specify to copy the resources into the workspace using the 'copyProjects'
 * attribute.
 * 
 * @author patkinso
 * @since 18 Apr 2013
 */
public class ImportProjectTask extends Task {

    /* <task attributes> */
    private String dir = null;

    private String file = null;

    private Boolean copyProjects = null;

    private Boolean useArchives = null;

    private Boolean skipPostImportTask = null;

    /* </task attributes> */

    /* <task nested elements> */
    private Set<DirSet> dirSets = new HashSet<DirSet>();

    private Set<FileSet> fileSets = new HashSet<FileSet>();

    /* </task nested elements> */

    private FilenameFilter archiveFileNameFilter = new ArchiveFilenameFilter();

    private ProjectsImportService srv;

    private final String newLn = System.getProperty("line.separator"); //$NON-NLS-1$

    /**
     * @see org.apache.tools.ant.Task#execute()
     * 
     * @throws BuildException
     */
    @Override
    public void execute() throws BuildException {

        validateTaskUsage();
        setAttributeDefaults();

        ProjectImporter[] projects = null;

        Set<File> viableResources =
                (isArchiveImport()) ? getViableArchiveFiles()
                        : getViableImportDirectories();
        File[] viableResourcesArr =
                viableResources.toArray(new File[viableResources.size()]);

        projects = srv.getProjectRepresentations(viableResourcesArr);

        /*
         * option to copy projects from file system, into the workspace, is
         * specific to project imports from folders
         */
        if (copyProjects && !isArchiveImport()) {
            for (ProjectImporter proj : projects) {
                if (proj instanceof DirectoryProjectImporter) {
                    ((DirectoryProjectImporter) proj).setCopyProjects(true);
                }
            }
        }

        preImportProjectCheck(srv, projects);
        final List<IProject> importedProjects =
                srv.doProjectsImport(projects, skipPostImportTask);

        if (!importedProjects.isEmpty()) {

            String msg =
                    getDisplayFormatedProjectList("Successful import of the following projects:", importedProjects); //$NON-NLS-1$
            log(msg);

            /*
             * Make sure any builds are complete.
             */
            if (!skipPostImportTask)
                doBuild(importedProjects);

        } else {
            String msg =
                    "Some candidate projects could not be imported so none were attempted"; //$NON-NLS-1$ 
            log(msg, Project.MSG_WARN);
        }

        super.execute();
    }

    /**
     * 
     */
    private void setAttributeDefaults() {
        if (copyProjects == null)
            copyProjects = true;
        if (useArchives == null)
            useArchives = false;
        if (skipPostImportTask == null)
            skipPostImportTask = false;
    }

    /**
     * @return
     */
    private boolean isArchiveImport() {
        return isFileAttributeSet() || useArchives;
    }

    /**
     * 
     */
    private void validateTaskUsage() throws BuildException {

        // nested element(s) + simple usage attributes (dir / file)
        if ((isFileAttributeSet() || isDirAttributeSet())
                && ((!dirSets.isEmpty()) || !fileSets.isEmpty())) {
            throw new BuildException(
                    "Invalid Task Usage: When using nested elements the simple format specific attributes ('dir' 'file') should not be set."); //$NON-NLS-1$
        }

        // task simple usage: 'file' and 'dir' attributes are mutually
        // exclusive)
        if (isFileAttributeSet() && isDirAttributeSet()) {
            throw new BuildException(
                    "Invalid Task Usage: When using the simple task format the attributes 'dir' and 'file' are mutually exclusive: usage of 'file' implies an archive import; usage of 'dir' implies a folder structure project import."); //$NON-NLS-1$
        }

        // nested FileSet without useArchive explicitly set to true not
        // permitted: without useArchives set to true might be ambiguous to user
        // whether archive or folder import taking place
        if (!fileSets.isEmpty() && !Boolean.TRUE.equals(useArchives)) {
            throw new BuildException(
                    "Invalid Task Usage: Nested FileSet elements cannot be used without setting the 'useArchives' attribute to true."); //$NON-NLS-1$
        }

        // if skipping postimporttasks then don't allow setting of skipBuild to
        // be false
        /*
         * if (Boolean.TRUE.equals(skipPostImportTask) && (skipBuild == null ||
         * Boolean.FALSE.equals(skipBuild))) { throw new BuildException(
         * "Invalid Task Usage: If post import tasks are skipped then the build should also be skipped ('skipBuild'=\"true\")"
         * ); //$NON-NLS-1$ }
         */
        // archive import + copyProjects explicitly set to true
        if ((isFileAttributeSet() || Boolean.TRUE.equals(useArchives))
                && Boolean.TRUE.equals(copyProjects)) {
            String fmtMsg =
                    "%sThe attribute 'copyProjects' has been set to \"true\" and an archive import will be attempted. As such the 'copyProjects' attribute value is redundant%s"; //$NON-NLS-1$
            log(String.format(fmtMsg, newLn));
        }

    }

    /**
     * @return
     */
    private boolean isFileAttributeSet() {
        return file != null && !file.isEmpty();
    }

    /**
     * @return
     */
    private boolean isDirAttributeSet() {
        return dir != null && !dir.isEmpty();
    }

    /**
     * @return all directories specified which should be considered when
     *         searching for BPM projects
     */
    private Set<File> getViableImportDirectories() {

        Set<File> dirNames = new HashSet<File>();

        if (isDirAttributeSet()) {
            dirNames.add(new File(dir));
        } else {
            for (DirSet dirset : dirSets) {
                DirectoryScanner ds = dirset.getDirectoryScanner(getProject());
                for (String includedDir : ds.getIncludedDirectories()) {
                    File resource = new File(ds.getBasedir(), includedDir);
                    if (resource.isDirectory()) {
                        dirNames.add(resource);
                    }
                }
            }
        }

        return dirNames;
    }

    /**
     * @return all files specified which should be considered when searching for
     *         BPM projects
     */
    private Set<File> getViableArchiveFiles() {

        Set<File> fileNames = new HashSet<File>();

        if (isFileAttributeSet()) {
            // WHEN USING SIMPLE TASK FORMAT:
            fileNames.add(new File(file));
        } else {
            // WHEN USING COMPREHENSIVE TASK FORMAT:
            // files specified by filesets
            for (FileSet fileSet : fileSets) {
                FileScanner fs = fileSet.getDirectoryScanner(getProject());
                for (String includedFile : fs.getIncludedFiles()) {
                    if (archiveFileNameFilter.accept(null, includedFile)) {
                        File resource = new File(fs.getBasedir(), includedFile);
                        fileNames.add(resource);
                    }
                }
            }

            // files specified indirectly via dirsets
            fileNames.addAll(getViableFiles(getViableImportDirectories()));
        }

        return fileNames;
    }

    /**
     * @param viableDirectories
     * @return
     */
    private Collection<? extends File> getViableFiles(
            Set<File> viableDirectories) {

        Set<File> archiveFiles = new HashSet<File>();

        for (File dir : viableDirectories) {
            if (dir.isDirectory()) {
                File[] filteredFiles = dir.listFiles(archiveFileNameFilter);
                archiveFiles.addAll(Arrays.asList(filteredFiles));
            }
        }
        return archiveFiles;
    }

    /**
     * @param importedProjects
     */
    private void doBuild(final List<IProject> importedProjects) {

        Job th = new Job("Build imported projects") { //$NON-NLS-1$

                    @Override
                    protected IStatus run(IProgressMonitor monitor) {

                        String msg =
                                getDisplayFormatedProjectList("Building the following projects:", importedProjects); //$NON-NLS-1$
                        log(msg);

                        IStatus synchronizedBuildStatus =
                                BuildSynchronizerUtil
                                        .synchronizedBuild(importedProjects,
                                                null,
                                                true);

                        if (synchronizedBuildStatus.getSeverity() > IStatus.WARNING) {
                            String message =
                                    synchronizedBuildStatus.getMessage();
                            Throwable exception =
                                    synchronizedBuildStatus.getException();
                            log(message, exception, Project.MSG_ERR);
                        }
                        return synchronizedBuildStatus;
                    }
                };

        try {
            th.schedule();
            th.join();
            log("Build complete"); //$NON-NLS-1$
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param importService
     * @param projects
     * @param abortImporting
     * @return <code>true</code> if supplied projects hierarchies can be
     *         imported/resolved in the workspace.
     */
    protected boolean preImportProjectCheck(
            ProjectsImportService importService, ProjectImporter[] projects) {

        Set<String> missingProjects =
                importService.getMissingReferencedProjects(projects);
        if (!missingProjects.isEmpty()) {
            String msg =
                    getDisplayFormatedProjectList("The following projects are required but could not be determined:", //$NON-NLS-1$
                            missingProjects);
            log(msg, Project.MSG_WARN);
            return false;
        }
        return true;
    }

    /**
     * @param msg
     * @param missingProjects
     * @return presentable list for use in logs etc
     */
    private String getDisplayFormatedProjectList(String msg,
            Set<String> projects) {

        StringBuilder sb = new StringBuilder();
        for (String proj : projects) {
            sb.append(" >").append(proj).append("<").append(newLn); //$NON-NLS-1$ //$NON-NLS-2$
        }

        String importedProjectsLst = sb.toString();
        sb = new StringBuilder();
        sb.append(newLn).append(msg).append(newLn).append(importedProjectsLst)
                .append(newLn);

        return sb.toString();
    }

    /**
     * @param projects
     * @return presentable list for use in logs etc
     */
    private String getDisplayFormatedProjectList(String msg,
            Collection<IProject> projects) {

        Set<String> projectNames = new HashSet<String>();
        for (IProject proj : projects)
            projectNames.add(proj.getName());
        return getDisplayFormatedProjectList(msg, projectNames);
    }

    /**
     * @param dirset
     *            the directories to set
     */
    public void addDirSet(DirSet dirset) {
        this.dirSets.add(dirset);
    }

    public void addFileSet(FileSet fileSet) {
        this.fileSets.add(fileSet);
    }

    /**
     * @param copyProjects
     *            the copyProjects to set
     */
    public void setCopyProjects(boolean copyProjects) {
        this.copyProjects = copyProjects;
    }

    /**
     * @param useArchives
     *            the useArchives to set
     */
    public void setUseArchives(boolean useArchives) {
        this.useArchives = useArchives;
    }

    /**
     * @param skipPostImportTask
     *            the skipPostImportTask to set
     */
    public void setSkipPostImportTask(boolean skipPostImportTask) {
        this.skipPostImportTask = skipPostImportTask;
    }

    /**
     * @param dir
     *            the dir to set
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * @param file
     *            the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @see org.apache.tools.ant.Task#init()
     * 
     * @throws BuildException
     */
    @Override
    public void init() throws BuildException {

        boolean initialised =
                TargetPlatformInitialiser.getInstance().initalise();
        if (initialised) {
            String fmtStr =
                    "%sInitialization of Target Platform appears to have been successful."; //$NON-NLS-1$
            log(String.format(fmtStr, newLn), Project.MSG_INFO);
        } else {
            String fmtStr =
                    "%sFailed to locate Target Platform definition for initialization."; //$NON-NLS-1$
            log(String.format(fmtStr, newLn), Project.MSG_WARN);
        }

        // running in headless mode so plugin's IStartup not having an effect -
        // but crucial to have it loaded before importing projects
        try {
            /*
             * SCF-209: Use the START_TRANSIENT flag to start this plug-in. If
             * this is not used then the Studio configuration will change
             * causing the validation plug-in (on subsequent run of Studio, with
             * any workspace) to load before the workbench is ready. This will
             * have side-effects such as validation resolutions not loading
             * (broken quick-fix) as it thinks it's running in headless mode.
             */
            Platform.getBundle(ValidationActivator.PLUGIN_ID)
                    .start(Bundle.START_TRANSIENT);
        } catch (BundleException e) {
            e.printStackTrace();
        }

        srv = ProjectsImportService.getInstance();
    }
}
