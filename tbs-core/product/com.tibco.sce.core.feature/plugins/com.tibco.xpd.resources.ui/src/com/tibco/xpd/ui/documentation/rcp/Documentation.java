/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.rcp;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * This class generates the Documentation.
 * 
 * @author mtorres
 */
public class Documentation implements IApplication {

    private final static String LAUNCHER_OPEN_FILE_ARG = "-launcher.openFile";//$NON-NLS-1$

    private final static String PROJECT_NAME_ARG = "-projectName";//$NON-NLS-1$

    private final static String OUTPUT_PATH_ARG = "-outputPath";//$NON-NLS-1$

    private final static String MAA_FILE_EXTENSION = ".maa";//$NON-NLS-1$

    private static boolean isAutoBuilding() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceDescription description = workspace.getDescription();
        return description.isAutoBuilding();
    }

    private static void setAutoBuilding(boolean value) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceDescription description = workspace.getDescription();
        if (description.isAutoBuilding() != value) {
            description.setAutoBuilding(value);
            try {
                workspace.setDescription(description);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
     * IApplicationContext)
     */
    @Override
    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        XpdResourcesPlugin.overrideStandardDisplay(display);
        boolean preservedAutoBuild = isAutoBuilding();
        try {
            setAutoBuilding(false);
            final String[] args =
                    (String[]) context.getArguments()
                            .get(IApplicationContext.APPLICATION_ARGS);
            DocumentationInfo documentationInfo = getDocumentationInfo(args);
            if (documentationInfo != null) {
                String projectName = documentationInfo.getProjectName();
                if (projectName != null) {
                    System.out.println(Messages.Documentation_ProjectName
                            + projectName);
                } else {
                    System.out.println(Messages.Documentation_InvalidProject);
                    return IApplication.EXIT_OK;
                }
                IPath outputPath = documentationInfo.getOutputPath();
                if (outputPath != null) {
                    String outputStrPath = outputPath.toPortableString();
                    System.out.println(Messages.Documentation_OutputPath
                            + outputStrPath);
                    // Create outputPath if it doesn't exist
                    File outputPathFile = new File(outputStrPath);
                    if (!outputPathFile.exists()) {
                        try {
                            outputPathFile.mkdir();
                        } catch (Exception e) {
                            System.out
                                    .println(Messages.Documentation_ErrorCreatingOutputPath);
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out
                            .println(Messages.Documentation_InvalidOutputPath);
                    return IApplication.EXIT_OK;
                }
                int returnCode = EXIT_OK;
                returnCode =
                        PlatformUI.createAndRunWorkbench(display,
                                new ApplicationWorkbenchAdvisor(projectName,
                                        outputPath));
                if (returnCode == PlatformUI.RETURN_RESTART) {
                    return IApplication.EXIT_RESTART;
                }
            } else {
                System.out.println(Messages.Documentation_InvalidParameters);
                return IApplication.EXIT_OK;
            }
        } finally {
            setAutoBuilding(preservedAutoBuild);
            display.dispose();
        }
        System.out.println(Messages.Documentation_FinishedGeneration);
        return IApplication.EXIT_OK;
    }

    private DocumentationInfo getDocumentationInfo(String[] args) {
        // == MAA file Cases
        // Case1 -launcher.openFile argument provided
        if (isLauncherOpenFile(args)) {
            return getDocumentationInfoForLauncherOpenFile(args);
        } else {
            // == Command Line Cases ==
            // Case2: Project name/path output path provided
            return getDocumentationInfoForCommandLineArgs(args);
        }
    }

    private boolean isLauncherOpenFile(String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg != null && arg.equals(LAUNCHER_OPEN_FILE_ARG)) {
                    if ((i + 1) < args.length) {
                        String maaFileStrPath = args[i + 1];
                        if (isValidProjectFile(maaFileStrPath)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the documentation for Launcher open file
     * 
     * //a: No output folder provided (Generation in the same folder) //b:
     * Output folder provided
     * 
     * @param args
     * @return
     */
    private DocumentationInfo getDocumentationInfoForLauncherOpenFile(
            String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg != null && arg.equals(LAUNCHER_OPEN_FILE_ARG)) {
                    if ((i + 1) < args.length) {
                        String maaFileStrPath = args[i + 1];
                        String outputFolder = null;
                        if (isValidProjectFile(maaFileStrPath)) {
                            // Check if output folder is provided
                            if (getOutputPathFromArgs(args) != null) {
                                outputFolder = getOutputPathFromArgs(args);
                            } else {
                                IPath maaFilePath = new Path(maaFileStrPath);
                                if (maaFilePath.segmentCount() > 1) {
                                    IPath outputFolderPath =
                                            maaFilePath.uptoSegment(maaFilePath
                                                    .segmentCount() - 1);
                                    String maaFileName =
                                            maaFilePath.lastSegment();
                                    if (maaFileName != null
                                            && maaFileName
                                                    .contains(MAA_FILE_EXTENSION)) {
                                        outputFolderPath =
                                                outputFolderPath
                                                        .append(maaFileName
                                                                .replace(MAA_FILE_EXTENSION,
                                                                        ""));//$NON-NLS-1$
                                    }
                                    outputFolder =
                                            outputFolderPath.toPortableString();
                                }
                            }
                            if (outputFolder != null && maaFileStrPath != null) {
                                return new DocumentationInfo(maaFileStrPath,
                                        new Path(outputFolder));
                            }
                        } else {
                            System.out
                                    .println(Messages.Documentation_InvalidFile);
                        }
                    }
                }
            }
        }
        return null;
    }

    private DocumentationInfo getDocumentationInfoForCommandLineArgs(
            String[] args) {
        if (args == null) {
            System.out.println(Messages.Documentation_WrongNumberOfParameters);
            return null;
        }
        String projectName = getProjectNameFromArgs(args);
        String outputPath = getOutputPathFromArgs(args);
        if (projectName != null && outputPath != null) {
            return new DocumentationInfo(projectName, new Path(outputPath));
        } else {
            System.out
                    .println(Messages.Documentation_InvalidProjectNameAndOutputPath);
        }
        return null;
    }

    private String getProjectNameFromArgs(String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg != null && arg.equals(PROJECT_NAME_ARG)) {
                    if ((i + 1) < args.length) {
                        return args[i + 1];
                    }
                }
            }
        }
        return null;
    }

    private String getOutputPathFromArgs(String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg != null && arg.equals(OUTPUT_PATH_ARG)) {
                    if ((i + 1) < args.length) {
                        return args[i + 1];
                    }
                }
            }
        }
        return null;
    }

    private boolean isValidProjectFile(String filePath) {
        if (filePath != null && filePath.endsWith(MAA_FILE_EXTENSION)) {
            // Check that the file exists
            File outputPathFile = new File(filePath);
            if (outputPathFile.exists()) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    @Override
    public void stop() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench == null)
            return;
        final Display display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            @Override
            public void run() {
                if (!display.isDisposed())
                    workbench.close();
            }
        });
    }

    class DocumentationInfo {
        private String projectName;

        private IPath outputPath;

        public DocumentationInfo(String projectName, IPath outputPath) {
            this.projectName = projectName;
            this.outputPath = outputPath;
        }

        public String getProjectName() {
            return projectName;
        }

        public IPath getOutputPath() {
            return outputPath;
        }
    }

}
