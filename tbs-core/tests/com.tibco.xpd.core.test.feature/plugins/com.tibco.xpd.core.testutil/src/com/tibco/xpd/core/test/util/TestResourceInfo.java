/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Class representing an individual test resource file.
 * <p>
 * A test resource file should be stored in the test plugin (by default in a
 * "resources" folder. It's path under this folder should reflect the run time
 * path.
 * <p>
 * The test resource has a "/" separated path that is considered to be relative
 * to the test-runtime project AND the test-plugin's "resources" folder
 * (changeable via setBaseTestPluginResourceFolder())
 * <p>
 * Elements in the path may be suffixed with markers {<specialFolderKind>}
 * specifying that a folder is a special folder kind...
 * 
 * <pre>
 *     Process Packages{processes}/pkg.xpdl
 * </pre>
 * 
 * @author aallway
 * @since 3.2
 */
public class TestResourceInfo {

    /**
     * Elements in the path may be suffixed with markers {<specialFolderKind>}
     * specifying that a folder is a special folder kind...
     * 
     * <pre>
     *     Process Packages{processes}/pkg.xpdl
     * </pre>
     * 
     * <p>
     * The first segment of the path is expected to be the name of the project.
     */
    private String tokenisedPath;

    private String baseTokenisedPath;

    private String projectName;

    public static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    public static final String TOKEN_START_MARKER = "{"; //$NON-NLS-1$

    public static final String TOKEN_END_MARKER = "}"; //$NON-NLS-1$

    private String baseTestPluginResourceFolder;

    /**
     * Create a {@link TestResourceInfo} resource.
     * <p>
     * The baseTestPluginResourceFolder names the root source-code plugin folder
     * from which the test resource is loaded at runtime.
     * <p>
     * The tokenisedPath names BOTH the <b>baseTestPluginResourceFolder-relative
     * source file path in source-code plugin</b> AND the
     * <b>test-workspace-relative target file path</b> (with special tokens to
     * allow for specification of special folders see class main comment).
     * Therefore it is expected that the source plugin file/folder structure
     * under baseTestPluginResourceFolder will be replicated in the target test
     * workspace folder.
     * <p>
     * The first segment of this path is ALWAYS taken to be the test project
     * name.
     * <p>
     * For example if you wish to create a test that has a Process.xpdl file in
     * the "process packages" folder of the project "TestProject" then set up
     * your source plugin with the following file structure...
     * 
     * <pre>
     * my.test.plugin/
     *    resources/
     *        TestProject/
     *            Process Packages/
     *                Process.xpdl
     * </pre>
     * 
     * Then construct a {@link TestResourceInfo} as follows...
     * 
     * <pre>
     * new TestResourceInfo("resources", "TestProject/Process Packages{processes}/Process.xpdl
     * </pre>
     * 
     * Then when {@link #createFile(String)} is called it will create the
     * TestProject (if not present), the process packages special folder (if not
     * present) and then copy the file from
     * 
     * <pre>
     * source-code-plugin-bundle/resources/TestProject/Process Packages/Process.xpdl to test-workspace/TestProcess/Process Packages/Process.xpdl
     * </pre>
     * 
     * If you are using this via {@link AbstractBaseResourceTest} or one of its
     * derivatives then <b>normally the {@link #createFile(String)} is performed
     * automatically by the {@link AbstractBaseResourceTest#setUp()} method</b>.
     * Therefore there is no need to explicitly call {@link #clone()}.
     * 
     * @param baseTestPluginResourceFolder
     *            the source-code plugin folder from which test resources are
     *            loaded at runtime.
     * @param tokenisedPath
     *            The tokenisedPath names BOTH the
     *            <b>baseTestPluginResourceFolder-relative source file path in
     *            source-code plugin</b> AND the <b>test-workspace-relative
     *            target file path (with special tokens to allow for
     *            specification of special folders see class main comment).
     */
    public TestResourceInfo(String baseTestPluginResourceFolder,
            String tokenisedPath) {
        super();

        projectName = ""; //$NON-NLS-1$
        this.tokenisedPath = ""; //$NON-NLS-1$
        this.baseTokenisedPath = tokenisedPath;

        int idx = tokenisedPath.indexOf(PATH_SEPARATOR);
        if (idx >= 0) {
            projectName = tokenisedPath.substring(0, idx);
            this.tokenisedPath = tokenisedPath.substring(idx + 1);
        }

        if (projectName.length() == 0 || tokenisedPath.length() == 0) {
            throw new IllegalArgumentException(
                    "Expected at least leading project name in path: " //$NON-NLS-1$
                            + tokenisedPath);
        }

        this.baseTestPluginResourceFolder = baseTestPluginResourceFolder;
    }

    /**
     * @return the targetProjectRelativePath
     */
    public String getTokenisedPath() {
        return tokenisedPath;
    }

    /**
     * @return the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @return TestPlugin/resources or RuntimeTestProject relative path for the
     *         resource source file.
     */
    public String getTestFilePath() {
        return detokenisePath(tokenisedPath);
    }

    /**
     * @param path
     * @return The given path with our special folder tokens removed.
     */
    private String detokenisePath(String path) {
        String[] segments = tokenisedPath.split(PATH_SEPARATOR); //$NON-NLS-1$

        String testResourcePath = ""; //$NON-NLS-1$
        for (String seg : segments) {
            if (testResourcePath.length() > 0) {
                testResourcePath += PATH_SEPARATOR;
            }

            // Strip special folder tag.
            int idx = seg.indexOf(TOKEN_START_MARKER);
            if (idx >= 0) {
                testResourcePath += seg.substring(0, idx);
            } else {
                testResourcePath += seg;
            }
        }
        return testResourcePath;
    }

    /**
     * Get the project for the file.
     * 
     * @return
     */
    public IProject getProject() {
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);

        if (project == null) {
            throw new RuntimeException(
                    "Invalid code sequence. The TestResourceInfo.createFile() method must be called before you can access the parent project."); //$NON-NLS-1$
        }

        return project;
    }

    /**
     * Copy this file from the test plugin resources to the given test runtime
     * environment project and return appropriate IFile for it.
     * 
     * @param project
     * @param source
     *            test plugin id
     * 
     * @return IFile copy of test resource in runtime test project.
     */
    public IFile createFile(String testPluginId) throws Exception {
        IFile newFile = null;

        InputStream fileInputStream = null;
        try {
            Bundle bundle = Platform.getBundle(testPluginId);

            String testResourceSourcePath = getTestFilePath();

            // Get the test plugin source resource file.
            Path filePath =
                    new Path(baseTestPluginResourceFolder + PATH_SEPARATOR
                            + projectName + PATH_SEPARATOR
                            + testResourceSourcePath);

            fileInputStream = FileLocator.openStream(bundle, filePath, false);

            // Create path to file.
            IContainer folder =
                    createTargetFolder(getTokenisedTargetFolderPath());

            newFile = folder.getFile(new Path(getTestFileName()));

            if (!newFile.exists()) {
                newFile.create(fileInputStream, true, null);
            } else {
                newFile.setContents(fileInputStream, true, true, null);

                /**
                 * Sid, IF using createProjectWizard() to create the project AND
                 * an asset in teh test suite has same name as a default asset
                 * created with the project, then this setContent() wasn't
                 * causing working copy reload and hence test was against the
                 * working copy with BLANK file EMF model left by the create
                 * project!
                 */
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(newFile);
                if (wc != null) {
                    wc.reLoad();
                }
            }

            //
            // Allow test file managers a chance to perform any mods to the test
            // file.
            List<ITestFileManager> testFileManagers =
                    TestResourceManagerExtPoint.INSTANCE
                            .getTestFileManagers(newFile.getFullPath()
                                    .getFileExtension());

            for (ITestFileManager manager : testFileManagers) {
                manager.testFileCreated(newFile);
            }

        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
            }
        }

        return newFile;
    }

    /**
     * Get the file runtime test file for the test resource.
     * <p>
     * The file does not necessarily exist, see
     * {@link #createFile(IProject, String)}.
     * 
     * @return The IFile for the test resource relative to the given project.
     */
    public IFile getTestFile() {
        IPath projectRelPath = new Path(getTestFilePath());

        IProject project = getProject();

        return project.getFile(projectRelPath);
    }

    /**
     * @return The simple filename part of the test resource
     */
    public String getTestFileName() {
        int idx = tokenisedPath.lastIndexOf(PATH_SEPARATOR);
        if (idx >= 0) {
            return tokenisedPath.substring(idx + 1);
        }

        return tokenisedPath;
    }

    /**
     * @return The project relative path of the parent folder of the test
     *         resource.
     */
    public String getTestTargetFolderPath() {
        return detokenisePath(getTokenisedTargetFolderPath());
    }

    /**
     * @param project
     * 
     * @return Get the parent folder of the test file.
     */
    public IFolder getTestTargetFolder() {
        IPath path = new Path(getTestTargetFolderPath());

        IProject project = getProject();

        return project.getFolder(path);
    }

    /**
     * @return The project relative path to the file's parent folder.
     */
    public String getTokenisedTargetFolderPath() {
        int idx = getTokenisedPath().lastIndexOf(PATH_SEPARATOR);
        if (idx >= 0) {
            return tokenisedPath.substring(0, idx);
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Create project relative path.
     * <p>
     * 
     * @return The final folder along path.
     * @throws CoreException
     */
    private IContainer createTargetFolder(String tokenisedFolderPath)
            throws Exception {
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        if (project == null || !project.exists()) {
            project = TestUtil.createProjectFromWizard(projectName);
            if (project == null || !project.isAccessible()) {
                throw new RuntimeException("Failed to create project: " //$NON-NLS-1$
                        + projectName);
            }

            //
            // Allow test project managers a chance to perform any mods to the
            // test project.

            List<ITestProjectManager> testProjectManagers =
                    TestResourceManagerExtPoint.INSTANCE
                            .getTestProjectManagers();

            for (ITestProjectManager manager : testProjectManagers) {
                manager.testProjectCreated(project);
            }

        }

        IContainer folder = project;

        String[] segments = tokenisedFolderPath.split(PATH_SEPARATOR); //$NON-NLS-1$

        String pathSoFar = ""; //$NON-NLS-1$

        for (String seg : segments) {
            if (pathSoFar.length() > 0) {
                pathSoFar += "/"; //$NON-NLS-1$ 
            }

            // Strip off special folder designation.
            String folderName;
            String specialFolderKind = null;

            int idxStart = seg.indexOf(TOKEN_START_MARKER);
            if (idxStart >= 0) {
                specialFolderKind = seg.substring(idxStart + 1);
                int idxEnd = specialFolderKind.indexOf(TOKEN_END_MARKER);
                if (idxEnd >= 0) {
                    specialFolderKind = specialFolderKind.substring(0, idxEnd);
                }

                folderName = seg.substring(0, idxStart);

            } else {
                folderName = seg;
            }

            pathSoFar += folderName;

            // Check if it exists.
            IFolder nextFolder = project.getFolder(new Path(pathSoFar));
            if (!nextFolder.exists()) {
                if (specialFolderKind != null && specialFolderKind.length() > 0) {
                    // Special folder.
                    SpecialFolder sf =
                            TestUtil.createSpecialFolder(project,
                                    pathSoFar,
                                    specialFolderKind);
                    nextFolder = sf.getFolder();

                } else {
                    // Plain folder.
                    nextFolder.create(true, true, new NullProgressMonitor());
                }
            }

            folder = nextFolder;
        }

        return folder;
    }

    /**
     * Utility that will create a special tokenised version of the given file's
     * project relative path that the {@link TestResourceInfo} class will
     * understand.
     * 
     * @param file
     * @return
     */
    public static String createTokenisedTestResourceInfoPath(IFile file) {
        String tokenised = ""; //$NON-NLS-1$

        IProject project = file.getProject();
        tokenised = project.getName() + PATH_SEPARATOR;

        IPath fileParentFolder = file.getParent().getProjectRelativePath();

        int segmentCount = fileParentFolder.segmentCount();
        for (int i = 1; i <= segmentCount; i++) {
            if (i != 1) {
                tokenised += PATH_SEPARATOR;
            }

            IPath path;
            if (i < segmentCount) {
                path = fileParentFolder.removeLastSegments(segmentCount - i);
            } else {
                path = fileParentFolder;
            }

            IFolder folder = project.getFolder(path);

            String kind = SpecialFolderUtil.getSpecialFolderKind(folder);

            tokenised += folder.getName();

            if (kind != null && kind.length() > 0) {
                tokenised += TOKEN_START_MARKER + kind + TOKEN_END_MARKER;
            }
        }

        tokenised += PATH_SEPARATOR;
        tokenised += file.getName();

        return tokenised;
    }

    public String getBaseTestPluginResourceFolder() {
        return baseTestPluginResourceFolder;
    }

    public String getBaseTokenisedPath() {
        return baseTokenisedPath;
    }

}
