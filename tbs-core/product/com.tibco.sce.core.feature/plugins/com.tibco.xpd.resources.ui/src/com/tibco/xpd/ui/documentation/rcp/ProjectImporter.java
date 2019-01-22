/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.ui.documentation.rcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.ui.CommonUIPlugin;
import org.eclipse.emf.common.ui.wizard.AbstractExampleInstallerWizard;
import org.eclipse.emf.common.ui.wizard.AbstractExampleInstallerWizard.ProjectDescriptor;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.ui.wizards.datatransfer.ZipFileStructureProvider;

/**
 * 
 * @author mtorres
 */
public class ProjectImporter extends AbstractExampleInstallerWizard {

    protected ArrayList<ProjectDescriptor> projectDescriptors;

    protected List<FileToOpen> filesToOpen = Collections.emptyList();

    protected final List<IPath> projectPaths;
    
    private Map <IPath, IProjectDescription> projectDescriptionForContextPath = null;

    private ProjectImporter(List<IPath> projectPaths) {
        this.projectPaths = projectPaths;
    }

    /**
     * Create project importer for importing list of Paths.
     * 
     * @param projectPaths
     *            The paths of the projects to import
     * @return the new project importer.
     */
    public static ProjectImporter createProjectImporter(List<IPath> projectPaths) {
        return new ProjectImporter(projectPaths);
    }

    /**
     * Create project importer for importing the project folder.
     * 
     * @param projectFolder
     *            
     * @return the new project importer.
     */
    public static ProjectImporter createFolderProjectImporter(String projectFolder) {
        List<IPath> projectPaths = new ArrayList<IPath>();
        if (projectFolder != null) {
            File directory = new File(projectFolder);
            if (directory.isDirectory() && directory.canRead()) {
                IPath projectPath = new Path(projectFolder).addTrailingSeparator();
                projectPaths.add(projectPath);
            } else if(directory.exists()){
                IPath projectPath = new Path(projectFolder);
                projectPaths.add(projectPath);
            }
        }
        return new ProjectImporter(projectPaths);
    }

    @Override
    protected List<ProjectDescriptor> getProjectDescriptors() {
        if (projectDescriptors == null) {
            createProjectDescriptors(projectPaths);
        }
        return projectDescriptors;
    }

    @Override
    public synchronized List<FileToOpen> getFilesToOpen() {
        return filesToOpen;
    }

    /**
     * Set the files to be open after import.
     * 
     * @param filesToOpen
     *            the filesToOpen to set.
     */
    public synchronized void setFilesToOpen(ArrayList<FileToOpen> filesToOpen) {
        this.filesToOpen = filesToOpen;
    }

    protected void createProjectDescriptors(List<IPath> contentPaths) {
        projectDescriptors = new ArrayList<ProjectDescriptor>();

        for (IPath contentPath : contentPaths) {
            if (contentPath != null) {
                AbstractExampleInstallerWizard.ProjectDescriptor projectDescriptor =
                        new AbstractExampleInstallerWizard.ProjectDescriptor();

                IProjectDescription projectDescriptorFromFile =
                        getProjectDescriptorFromFile(contentPath);
                if (projectDescriptorFromFile != null) {
                    projectDescriptor.setName(projectDescriptorFromFile
                            .getName());

                    URI uri = URI.createFileURI(contentPath.toPortableString());

                    projectDescriptor.setContentURI(uri);

                    projectDescriptor.setDescription(projectDescriptorFromFile
                            .getComment());
                    if(projectDescriptionForContextPath == null){
                        projectDescriptionForContextPath = new HashMap<IPath, IProjectDescription>();
                    }
                    projectDescriptionForContextPath.put(contentPath, projectDescriptorFromFile);
                    projectDescriptors.add(projectDescriptor);
                }
            }
        }
    }
    
    /**
     * Returns the project descriptor for a project archive or folder
     * 
     * @param contentPath
     * @return
     */
    @SuppressWarnings("restriction")
    protected IProjectDescription getProjectDescriptorFromFile(IPath contentPath) {
        if (contentPath != null) {
            InputStream descriptionFileStream = null;
            if (contentPath.hasTrailingSeparator()) {
                // Folder
                File descriptionFile =
                        new File(
                                contentPath
                                        .append(IProjectDescription.DESCRIPTION_FILE_NAME)
                                        .toPortableString());
                if (descriptionFile.exists() && descriptionFile.canRead()) {
                    try {
                        descriptionFileStream = new FileInputStream(descriptionFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Archive
                File archiveFile = new File(contentPath.toPortableString());
                if (archiveFile.exists() && archiveFile.canRead()) {
                    try {
                        ZipFile zipFile = new ZipFile(archiveFile);
                        ZipLeveledStructureProvider structureProvider =
                                new ZipLeveledStructureProvider(zipFile);
                        structureProvider.setStrip(1);
                        Object entry = structureProvider.getRoot();
                        List children = structureProvider.getChildren(entry);
                        if (children != null) {
                            // This is the project level go 1 deeper
                            Object projectEntry = children.iterator().next();
                            if (projectEntry != null) {
                                children =
                                        structureProvider
                                                .getChildren(projectEntry);
                                Iterator childrenEnum = children.iterator();
                                while (childrenEnum.hasNext()) {
                                    Object child = childrenEnum.next();
                                    if (child != null) {
                                        String elementLabel =
                                                structureProvider
                                                        .getLabel(child);
                                        if (elementLabel
                                                .equals(IProjectDescription.DESCRIPTION_FILE_NAME)) {
                                            descriptionFileStream =
                                                    structureProvider
                                                            .getContents(child);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ZipException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (descriptionFileStream != null) {
                IProjectDescription projectDescription = null;
                try {
                    projectDescription =
                            IDEWorkbenchPlugin
                                    .getPluginWorkspace()
                                    .loadProjectDescription(descriptionFileStream);
                } catch (CoreException e) {
                    e.printStackTrace();
                }
                return projectDescription;
            }
        }
        return null;
    }

    protected void createFilesToOpen(String[][] filesToOpenArray) {
        filesToOpen = new ArrayList<FileToOpen>();
        for (String[] record : filesToOpenArray) {
            String location = record[0];
            if (location != null) {
                AbstractExampleInstallerWizard.FileToOpen fileToOpen =
                        new AbstractExampleInstallerWizard.FileToOpen();
                fileToOpen.setLocation(location);
                fileToOpen.setEditorID(record.length > 1 ? record[1] : null);
                filesToOpen.add(fileToOpen);
            }
        }

    }

    /**
     * Perform import of projects.
     * 
     * @return true if import was successful.
     */
    public boolean performImport() {
        final Exception exceptionWrapper = new Exception();

        try {
            IProgressService ps =
                    PlatformUI.getWorkbench().getProgressService();
            ps.busyCursorWhile(new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException, InterruptedException {
                    monitor.beginTask("Importing projects", 3);

                    WorkspaceModifyOperation op =
                            new WorkspaceModifyOperation() {
                                @Override
                                protected void execute(IProgressMonitor monitor)
                                        throws CoreException,
                                        InvocationTargetException,
                                        InterruptedException {
                                    if (performDelete()) {
                                        try {
                                            installExample(monitor);
                                        } catch (Exception e) {
                                            exceptionWrapper.initCause(e);
                                            throw new InterruptedException();
                                        }
                                    }
                                }
                            };
                    op.run(new SubProgressMonitor(monitor, 1));
                    monitor.done();
                }
            });
            return true;
        } catch (InterruptedException e) {
            if (exceptionWrapper.getCause() != null) {
                CommonUIPlugin.INSTANCE.log(exceptionWrapper.getCause());
            }
        } catch (InvocationTargetException e) {
            CommonUIPlugin.INSTANCE.log(e);
        }
        return false;
    }

    /**
     * Deletes the imported projects from the workspace if they exists.
     * 
     * @return true if operation was successful.
     */
    public boolean performDelete() {
        final Exception exceptionWrapper = new Exception();

        try {
            IProgressService ps =
                    PlatformUI.getWorkbench().getProgressService();
            ps.busyCursorWhile(new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException, InterruptedException {
                    monitor.beginTask("Deleting projects", 3);

                    WorkspaceModifyOperation op =
                            new WorkspaceModifyOperation() {
                                @Override
                                protected void execute(IProgressMonitor monitor)
                                        throws CoreException,
                                        InvocationTargetException,
                                        InterruptedException {

                                    List<IProject> projects =
                                            new ArrayList<IProject>();
                                    for (ProjectDescriptor projectDescriptor : getProjectDescriptors()) {
                                        IProject project =
                                                projectDescriptor.getProject();
                                        if (project.exists()) {
                                            projects.add(project);
                                        }
                                    }
                                    Diagnostic diagnostic;
                                    org.eclipse.ui.ide.undo.DeleteResourcesOperation op =
                                            new org.eclipse.ui.ide.undo.DeleteResourcesOperation(
                                                    projects
                                                            .toArray(new IProject[projects
                                                                    .size()]),
                                                    "deleteprojects", true);
                                    try {
                                        diagnostic =
                                                BasicDiagnostic
                                                        .toDiagnostic(op
                                                                .execute(new SubProgressMonitor(
                                                                        monitor,
                                                                        1),
                                                                        null));
                                    } catch (ExecutionException e) {
                                        diagnostic =
                                                BasicDiagnostic.toDiagnostic(e);
                                    }
                                    if (diagnostic.getSeverity() != Diagnostic.OK) {
                                        exceptionWrapper
                                                .initCause(new DiagnosticException(
                                                        diagnostic));
                                        throw new InterruptedException();
                                    }
                                }
                            };
                    op.run(new SubProgressMonitor(monitor, 1));
                    monitor.done();
                }
            });
            return true;
        } catch (InterruptedException e) {
            if (exceptionWrapper.getCause() != null) {
                CommonUIPlugin.INSTANCE.log(exceptionWrapper.getCause());
            }
        } catch (InvocationTargetException e) {
            CommonUIPlugin.INSTANCE.log(e);
        }
        return false;
    }
    
    public IProjectDescription getProjectDescription(IPath contextPath){
        if(contextPath != null && projectDescriptionForContextPath != null){
            return projectDescriptionForContextPath.get(contextPath);
        }
        return null;
    }
    
    protected ImportOperation createZipImportOperation(
            ProjectDescriptor projectDescriptor, File file) throws Exception {
        // ZipFile zipFile = file.getName().endsWith(".jar") ? new JarFile(file)
        // : new ZipFile(file);
        // ZipFileStructureProvider zipFileStructureProvider = new
        // ZipFileStructureProvider(zipFile);
        //
        // Object projectFolder =
        // zipFileStructureProvider
        // .getChildren(zipFileStructureProvider.getRoot())
        // .get(0);
        //      
        // return new ImportOperation(
        // projectDescriptor.getProject().getFullPath(),
        // projectFolder,
        // zipFileStructureProvider,
        // OVERWRITE_ALL_QUERY);
        ZipFile zipFile =
                file.getName().endsWith(".jar") ? new JarFile(file)
                        : new ZipFile(file);
        ZipFileStructureProvider zipFileStructureProvider =
                new ZipFileStructureProvider(zipFile);

        ILeveledImportStructureProvider zipLeveledStructureProvider =
                new ZipLeveledStructureProvider(zipFile);

        Object projectFolder =
                zipLeveledStructureProvider
                        .getChildren(zipLeveledStructureProvider.getRoot())
                        .get(0);
        List files = zipLeveledStructureProvider.getChildren(projectFolder);

         ImportOperation operation = new ImportOperation(projectDescriptor.getProject().getFullPath(), projectFolder,
                        zipLeveledStructureProvider, new IOverwriteQuery() {
                            public String queryOverwrite(String pathString) {
                                return IOverwriteQuery.ALL;
                            }
                        }, files);
         operation.setOverwriteResources(true);
         operation.setCreateContainerStructure(false);
         return operation;
    }

}
