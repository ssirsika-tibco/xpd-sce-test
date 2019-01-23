/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.n2.daa.test.junit.JunitConfiguration.ProjectType;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * @author bharge
 * 
 */
public class SampleAbsolutePathTest extends SetUpTest {

    private static final String ABSOLUTE_PATH_FOLDER = "C:/studioProjects/"; //$NON-NLS-1$

    private static final String CONFIG_FILE = "studio_junit_conf.xml"; //$NON-NLS-1$

    private JunitConfiguration configuration;

    /**
     * @throws java.lang.Exception
     */
    @Override
    protected void setUp() throws Exception {
        TestUtil.waitForJobs();

        String newln = System.getProperty("line.separator"); //$NON-NLS-1$

        System.out.printf("-> bpm.cleandaastaging = %s%s", //$NON-NLS-1$
                System.getProperty("bpm.cleandaastaging", "null"), //$NON-NLS-1$ //$NON-NLS-2$
                newln);

        /* Read the configuration file from the user's home area */
        URI userHomeURI = URI.createFileURI(System.getProperty("user.home")); //$NON-NLS-1$
        /*
         * get the plugin id to look for the conf xml with the plugin name in
         * the user home directory. eg. if the plugin is com.tibco.xpd.uc6 then
         * the conf file must exist with name com.tibco.xpd.uc6.xml in user home
         * directory
         */
        String pluginSpecificConfigFile = getContextPlugInId() + ".xml"; //$NON-NLS-1$
        URI configFileURI = userHomeURI.appendSegment(pluginSpecificConfigFile);
        File configFile = new File(configFileURI.toFileString());

        System.out.println("pluginSpecificConfigFile: " //$NON-NLS-1$
                + pluginSpecificConfigFile);

        if (!configFile.exists()) {
            /*
             * if plugin specific config file is not found, then look plugin
             * specific config in the file in the plugin root folder
             */
            final String PLATFORM_PLUGIN_RES_URI_PREFIX =
                    "platform:/plugin/" + getContextPlugInId() //$NON-NLS-1$
                            + "/"; //$NON-NLS-1$
            configFileURI =
                    URI.createURI(PLATFORM_PLUGIN_RES_URI_PREFIX
                            + pluginSpecificConfigFile);

            System.out.println("configFileURI: " + configFileURI); //$NON-NLS-1$

            try {
                configuration = new JunitConfiguration(configFileURI);
            } catch (IOException ioex) {
                // need to catch & carry on
            } catch (CoreException coex) {
                // need to catch & carry on
            }
            if (configuration == null) {
                /*
                 * if the config file is not found with the plugin name, then
                 * look for default config file in the user home directory with
                 * the default config file name i.e CONFIG_FILE declared above
                 */
                configFileURI = userHomeURI.appendSegment(CONFIG_FILE);
                configFile = new File(configFileURI.toFileString());
                if (!configFile.exists()) {
                    /*
                     * if the config file is not found under user home
                     * directory, then look for config file with default name
                     * i.e. CONFIG_FILE declared above in the default absolute
                     * path folder i.e.ABSOLUTE_PATH_FOLDER declared above that
                     * test projects are imported to
                     */
                    configFileURI = URI.createFileURI(ABSOLUTE_PATH_FOLDER);

                    new File(configFileURI.toFileString().concat(CONFIG_FILE));
                    if (!configFile.exists()) {

                        configFile = new File("" + pluginSpecificConfigFile); //$NON-NLS-1$
                    }
                }
            }
        }

        if (!configFile.exists() && configuration == null) {

            // sorry fail the test
            fail(String.format("Configuration file '%s' not found.", //$NON-NLS-1$
                    CONFIG_FILE));
        }
        if (configuration == null) {
            configuration =
                    new JunitConfiguration(URI.createFileURI(configFile
                            .getPath()));
        }
        importProjects();
        generateDAA(configuration.getIncludeProjects());

        // boolean isDaaInExportFolder = copyGeneratedDAAInExportFolder();
        // assertTrue(String.format("Copy generated DAA in Exports folder failed"),
        // isDaaInExportFolder);
    }

    /**
     * @see com.tibco.xpd.n2.daa.test.junit.SetUpTest#importProjects()
     * 
     */
    @Override
    protected void importProjects() {
        String studioProjectLocation = null;

        // Read the path from the config, if available
        if (configuration != null) {
            studioProjectLocation = configuration.getProjectsLocation();
        }

        if (studioProjectLocation == null) {
            // Use the default value
            studioProjectLocation = ABSOLUTE_PATH_FOLDER;
            System.out.println("Studio Projects: Using default - " //$NON-NLS-1$
                    + studioProjectLocation);
        } else {
            System.out.println("Studio Projects: " + studioProjectLocation); //$NON-NLS-1$
        }

        List<ProjectType> includeProjects = configuration.getIncludeProjects();
        String baseFolderLocation = null;

        if (studioProjectLocation != null
                && studioProjectLocation.trim().length() > 0) {
            File file = new File(studioProjectLocation);
            if (file.exists() && file.isDirectory()) {
                IPath path = new Path(file.getAbsolutePath());
                baseFolderLocation = path.addTrailingSeparator().toString();
            }
        }

        if (includeProjects.size() > 0 && null != baseFolderLocation) {
            for (ProjectType projectType : includeProjects) {
                String projectName = projectType.getName();
                String includedProjectPath = baseFolderLocation + projectName;
                ProjectImporter projectImporter =
                        ProjectImporter
                                .createIncludedProjectsProjectImporter(includedProjectPath);

                projectImporter.performImport();
            }
        } else {
            if (null != baseFolderLocation) {

                ProjectImporter projectImporter =
                        ProjectImporter
                                .createURIFolderProjectImporter(baseFolderLocation);

                projectImporter.performImport();
            } else {
                fail(String.format("Projects folder '%s' not found.", //$NON-NLS-1$
                        studioProjectLocation));
            }
        }
    }

    /**
     * 
     */
    @Override
    protected boolean copyGeneratedDAAInExportFolder(IFile daaFile,
            String suffix) {
        if (daaFile != null && daaFile.exists() && daaFile instanceof IFile) {
            String daaOutputLocation = configuration.getDaaOutputLocation();
            FileCopier copier =
                    new FileCopier(daaFile, getLocationType(),
                            getWorkspaceExportFolder(), suffix);
            if (null != copier) {
                try {
                    copier.copy();
                } catch (CoreException e) {
                    return false;
                }
            }

            // Also copy to DAA output location
            if (daaOutputLocation != null && daaOutputLocation.length() > 0) {
                copier =
                        new FileCopier(daaFile,
                                DestinationLocationType.SYSTEM_FILE,
                                daaOutputLocation, suffix);
                if (copier != null) {
                    try {
                        copier.copy();
                    } catch (CoreException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // /**
    // *
    // */
    // private boolean copyGeneratedDAAInExportFolder() {
    // IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    // List<String> studioProjectNames =
    // TestUtil.getAllStudioProjectNamesInWorkSpace();
    //
    // // Check if an output folder is specified in the configuration
    // String daaOutputLocation = configuration.getDaaOutputLocation();
    //
    // for (String projectName : studioProjectNames) {
    // IProject project = root.getProject(projectName);
    // IFile srcFile =
    // ProjectDAAGenerator.getInstance().getDAAFile(project);
    // if (srcFile != null && srcFile.exists()) {
    // FileCopier copier =
    // new FileCopier(srcFile, getLocationType(),
    // getWorkspaceExportFolder());
    // if (null != copier) {
    // try {
    // copier.copy();
    // } catch (CoreException e) {
    // return false;
    // }
    // }
    //
    // // Also copy to DAA output location
    // if (daaOutputLocation != null && daaOutputLocation.length() > 0) {
    // copier =
    // new FileCopier(srcFile,
    // DestinationLocationType.SYSTEM_FILE,
    // daaOutputLocation);
    // if (copier != null) {
    // try {
    // copier.copy();
    // } catch (CoreException e) {
    // return false;
    // }
    // }
    // }
    // }
    // }
    // return true;
    // }

    private String getWorkspaceExportFolder() {
        return "/Exports/DAA"; //$NON-NLS-1$
    }

    private DestinationLocationType getLocationType() {
        return DestinationLocationType.PROJECT;
    }
}
