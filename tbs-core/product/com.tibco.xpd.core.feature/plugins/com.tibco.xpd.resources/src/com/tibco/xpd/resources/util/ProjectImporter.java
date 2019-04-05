/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.ui.CommonUIPlugin;
import org.eclipse.emf.common.ui.wizard.AbstractExampleInstallerWizard;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.progress.IProgressService;

/**
 * Imports projects into workspace. The clients should use
 * {@link #createPluginProjectImporter(String, List)} or
 * {@link #createPluginFolderProjectImporter(String, String)} or {@link
 * #createURIFolderProjectImporter(List<String>)} to create importer.
 * 
 * Then use {@link #performImport()} to import project(s) into workspace.
 * 
 * The instance of project importer can be then used to delete imported projects
 * using {@link #performDelete()}.<br/>
 * 
 * The one of possible usage in Plug-in JUnit could be:
 * 
 * <pre>
 * public class SomeTest extends TestCase {
 * 
 *     private ProjectImporter projectImporter;
 * 
 *     protected void setUp() throws Exception {
 *         projectImporter =
 *                 ProjectImporter
 *                         .createPluginFolderProjectImporter(PluginActivator.PLUGIN_ID,
 *                                 &quot;resources/MyProjectsToImport/&quot;); //$NON-NLS-1$
 *         assertTrue(projectImporter.performImport());
 *     }
 * 
 *     public void testSomething() throws Exception {
 *         // do something with the imported projects
 *     }
 * 
 *     protected void tearDown() throws Exception {
 *         assertTrue(projectImporter.performDelete());
 *     }
 * 
 * }
 * </pre>
 * 
 * @author Jan Arciuchiewicz
 */
public class ProjectImporter extends AbstractExampleInstallerWizard {

    protected ArrayList<ProjectDescriptor> projectDescriptors;

    protected List<FileToOpen> filesToOpen = Collections.emptyList();

    protected final List<String> projectURLs;

    protected final String contextPluginId;

    private ProjectImporter(String contextPluginId, List<String> projectURLs) {
        this.contextPluginId = contextPluginId;
        this.projectURLs = projectURLs;
    }

    /**
     * Create project importer for importing list of plug-in relative URIs.
     * 
     * @param contextPluginId
     *            the id of the context plug-in containing the directories/files
     *            to import as a projects.
     * @param projectURLs
     *            the list of strings representing bundle relative project URIs.
     *            Two forms are allowed: <li>plug-in relative path to folder
     *            containing project (resource/myProject/)</li> <li>plug-in
     *            relative path to zip file containing project content
     *            (resource/myProject.zip)</li>. If the project is the folder
     *            the relative URI should have trailing path separator '/'.
     * @return the new project importer.
     */
    public static ProjectImporter createPluginProjectImporter(
            String contextPluginId, List<String> projectURLs) {
        return new ProjectImporter(contextPluginId, projectURLs);
    }

    /**
     * Create project importer for importing all direct subfolders and/of zip
     * files of the of plug-in folder.
     * 
     * @param contextPluginId
     *            the id of the context plug-in containing the directories/files
     *            to import as a projects.
     * @param pluginFolder
     *            the plug-in relative URI to the folder containing folders, zip
     *            files to be imported as projects. (The subfolders with names
     *            starting with '.' will be ignored. Only files with 'zip'
     *            extension will be exported as projects.)
     * @return the new project importer.
     */
    public static ProjectImporter createPluginFolderProjectImporter(
            String contextPluginId, String pluginFolder) {
        List<String> projectURLs = new ArrayList<String>();
        if (!URI.createURI(pluginFolder).hasTrailingPathSeparator()) {
            pluginFolder += "/"; //$NON-NLS-1$
        }
        URI contentURI = URI.createURI(pluginFolder);
        if (contentURI.isRelative() && contextPluginId != null) {
            contentURI = URI.createPlatformPluginURI(contextPluginId + "/" //$NON-NLS-1$
                    + pluginFolder, false);
        }
        if (contentURI.isPlatform()) {
            contentURI = CommonPlugin.asLocalURI(contentURI);
        }

        String location = contentURI.toFileString();
        if (location != null) {
            File directory = new File(location);
            if (directory.isDirectory() && directory.canRead()) {
                File[] projectFiles = directory.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        boolean isOKDir =
                                f.isDirectory() && f.canRead()
                                        && !f.getName().startsWith("."); //$NON-NLS-1$
                        boolean isOKFile =
                                f.isFile()
                                        && f.canRead()
                                        && f.getName().toLowerCase()
                                                .endsWith(".zip"); //$NON-NLS-1$
                        return isOKDir || isOKFile;
                    }

                });
                for (File f : projectFiles) {
                    String projectPath =
                            f.isDirectory() ? f.getName() + "/" : f.getName(); //$NON-NLS-1$
                    projectURLs.add(pluginFolder + projectPath);
                }
            }
        }
        return new ProjectImporter(contextPluginId, projectURLs);
    }

    /**
     * Create project importer for importing list of absolute project's URIs.
     * 
     * @param root
     *            the absolute path from where project URIs must be imported.
     *            Two forms are allowed: zip file or folder containing the
     *            project. If the project is the folder the URI should have
     *            trailing path separator '/'.
     * @return the new project importer.
     */
    public static ProjectImporter createURIFolderProjectImporter(String root) {
        List<String> projectURIs = new ArrayList<String>();
        URI uri = URI.createURI(root);
        if (uri.hasAbsolutePath()) {
            projectURIs = ProjectImporter.createAddProjectURIsForAbsPath(root);
        }
        return new ProjectImporter(null, projectURIs);
    }

    public static ProjectImporter createIncludedProjectsProjectImporter(
            String root) {
        List<String> projectURIs = new ArrayList<String>();

        System.out.println("root = " + root);
        URI contentURI = URI.createFileURI(root);

        System.out.println("contentURI :: " + contentURI);
        String location = contentURI.toFileString();
        System.out.println("location = " + location);
        if (location != null) {
            File directory = new File(location);
            if (directory.isDirectory() && directory.canRead()) {
                String projectPath = root + "/"; //$NON-NLS-1$
                System.out.println("projectPath = " + projectPath);
                projectURIs.add(URI.createFileURI(projectPath).toString());
                System.out.println(projectURIs.get(0));
            }
        }

        return new ProjectImporter(null, projectURIs);
    }

    /**
     * @param uris
     * @param root
     */
    private static List<String> createAddProjectURIsForAbsPath(String root) {
        List<String> uris = new ArrayList<String>();
        URI contentURI = URI.createFileURI(root);
        String location = contentURI.toFileString();
        if (location != null) {
            File directory = new File(location);
            if (directory.isDirectory() && directory.canRead()) {
                File[] projectFiles = directory.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        boolean isOKDir =
                                f.isDirectory() && f.canRead()
                                        && !f.getName().startsWith("."); //$NON-NLS-1$
                        boolean isOKFile =
                                f.isFile()
                                        && f.canRead()
                                        && f.getName().toLowerCase()
                                                .endsWith(".zip"); //$NON-NLS-1$
                        return isOKDir || isOKFile;
                    }

                });

                for (File f : projectFiles) {
                    String projectPath =
                            f.isDirectory() ? f.getName() + "/" : f.getName(); //$NON-NLS-1$
                    uris.add(URI.createFileURI(root + projectPath).toString());
                }
            }
        }
        return uris;
    }

    @Override
    public List<ProjectDescriptor> getProjectDescriptors() {
        if (projectDescriptors == null) {
            createProjectDescriptors(contextPluginId, projectURLs);
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

    protected void createProjectDescriptors(String pluginId,
            List<String> contentURIs) {
        projectDescriptors = new ArrayList<ProjectDescriptor>();

        for (String contentURI : contentURIs) {
            String projectName = getProjectName(contentURI);
            if (projectName != null) {
                if (contentURI != null) {
                    AbstractExampleInstallerWizard.ProjectDescriptor projectDescriptor =
                            new AbstractExampleInstallerWizard.ProjectDescriptor();
                    projectDescriptor.setName(projectName);

                    URI uri = URI.createURI(contentURI);
                    if (uri.isPlatformPlugin()) {
                        uri = URI.createURI(contentURI, false);
                    }
                    if (uri.isRelative() && pluginId != null) {
                        uri = URI.createPlatformPluginURI(pluginId + "/" //$NON-NLS-1$
                                + contentURI, false);
                    }
                    projectDescriptor.setContentURI(uri);

                    projectDescriptor.setDescription(projectName);

                    projectDescriptors.add(projectDescriptor);
                }
            }
        }

    }

    /**
     * Gets project name from the URI.
     * 
     * @param contentURI
     *            the URI of the project resources root dir or zip.
     * @return the name of the project.
     */
    protected String getProjectName(String contentURI) {
        URI uri = URI.createURI(contentURI, false);
        String lastSegment;
        if (uri.hasTrailingPathSeparator()) {
            // folder
            lastSegment = uri.segment(uri.segmentCount() - 2);
        } else {
            // file
            lastSegment =
                    new Path(uri.lastSegment()).removeFileExtension()
                            .toPortableString();
        }
        return URI.decode(lastSegment);
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
                @Override
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
                                    Diagnostic diagnostic =
                                            deleteExistingProjects(new SubProgressMonitor(
                                                    monitor, 1));
                                    if (diagnostic.getSeverity() != Diagnostic.OK) {
                                        exceptionWrapper
                                                .initCause(new DiagnosticException(
                                                        diagnostic));
                                        throw new InterruptedException();
                                    }

                                    try {
                                        installExample(monitor);
                                    } catch (Exception e) {
                                        exceptionWrapper.initCause(e);
                                        throw new InterruptedException();
                                    }
                                }
                            };
                    op.run(new SubProgressMonitor(monitor, 1));

                    openFiles(new SubProgressMonitor(monitor, 1));
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
                @Override
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

}
