/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * TestResoruceManager extension point contribution helper.
 * 
 * @author aallway
 * @since 3.2
 */
public class TestResourceManagerExtPoint {

    public static TestResourceManagerExtPoint INSTANCE =
            new TestResourceManagerExtPoint();

    private List<ITestProjectManager> testProjectManagersSorted = null;

    private List<TestFileManager> testFileManagersSorted = null;

    private enum Priority {
        Highest, High, Normal, Low, Lowest;
    }

    private static class TestProjectManager {
        Priority priority;

        ITestProjectManager manager;
    }

    private static class TestFileManager {
        Priority priority;

        ITestFileManager manager;

        String fileExtension;
    }

    private static final String EXT_POINT_ID = "testResourceManager"; //$NON-NLS-1$

    private static final String TEST_PROJECT_MANAGER_ELEMENT =
            "TestProjectManager"; //$NON-NLS-1$ 

    private static final String TEST_FILE_MANAGER_ELEMENT = "TestFileManager"; //$NON-NLS-1$ 

    private static final String TESTFILEMANAGER_CLASS_ATTR_NAME =
            "testFileManager"; //$NON-NLS-1$

    private static final String TESTPROJECTMANAGER_CLASS_ATTR_NAME =
            "testProjectManager"; //$NON-NLS-1$

    private static final String PRIORITY_ATTR_NAME = "priority"; //$NON-NLS-1$

    private static final String FILEEXTENSION_ATTR_NAME = "fileExtension"; //$NON-NLS-1$

    /**
     * @return List of contributed test project managers in order of priority
     *         (hightest to lowest.
     */
    public List<ITestProjectManager> getTestProjectManagers() {
        if (testProjectManagersSorted == null) {
            loadManagers();
        }

        return testProjectManagersSorted;
    }

    /**
     * @return List of contributed test project managers in order of priority.
     */
    public List<ITestFileManager> getTestFileManagers(String fileExt) {
        if (testFileManagersSorted == null) {
            loadManagers();
        }

        if (fileExt != null && fileExt.startsWith(".")) { //$NON-NLS-1$
            fileExt = fileExt.substring(1);
        }

        List<ITestFileManager> testManagers = new ArrayList<ITestFileManager>();
        for (TestFileManager manager : testFileManagersSorted) {
            if (fileExt == null || fileExt.length() == 0
                    || fileExt.equalsIgnoreCase(manager.fileExtension)) {
                testManagers.add(manager.manager);
            }
        }

        return testManagers;
    }

    private void loadManagers() {
        testProjectManagersSorted = new ArrayList<ITestProjectManager>();
        testFileManagersSorted = new ArrayList<TestFileManager>();

        ArrayList<TestProjectManager> projectManagers =
                new ArrayList<TestProjectManager>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(TestUtilPlugin.PLUGIN_ID,
                                EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] errorProviders =
                    point.getConfigurationElements();

            if (errorProviders != null) {
                for (int c = 0; c < errorProviders.length; c++) {
                    IConfigurationElement extContribution = errorProviders[c];

                    if (TEST_PROJECT_MANAGER_ELEMENT.equals(extContribution
                            .getName())) {
                        try {
                            TestProjectManager manager =
                                    new TestProjectManager();
                            manager.manager =
                                    (ITestProjectManager) extContribution
                                            .createExecutableExtension(TESTPROJECTMANAGER_CLASS_ATTR_NAME);

                            if (manager.manager != null) {
                                manager.priority =
                                        Priority
                                                .valueOf(extContribution
                                                        .getAttribute(PRIORITY_ATTR_NAME));
                                if (manager.priority == null) {
                                    manager.priority = Priority.Normal;
                                }

                                projectManagers.add(manager);
                            }

                        } catch (Exception ce) {
                            System.err.println(this.getClass().getName()
                                    + "CoreException: " + ce.getMessage()); //$NON-NLS-1$
                            ce.printStackTrace(System.err);
                        }

                    } else if (TEST_FILE_MANAGER_ELEMENT.equals(extContribution
                            .getName())) {
                        try {
                            TestFileManager manager = new TestFileManager();
                            manager.manager =
                                    (ITestFileManager) extContribution
                                            .createExecutableExtension(TESTFILEMANAGER_CLASS_ATTR_NAME);

                            if (manager.manager instanceof ITestFileManager) {
                                manager.priority =
                                        Priority
                                                .valueOf(extContribution
                                                        .getAttribute(PRIORITY_ATTR_NAME));
                                if (manager.priority == null) {
                                    manager.priority = Priority.Normal;
                                }

                                manager.fileExtension =
                                        extContribution
                                                .getAttribute(FILEEXTENSION_ATTR_NAME);
                                if (manager.fileExtension.startsWith(".")) { //$NON-NLS-1$
                                    manager.fileExtension =
                                            manager.fileExtension.substring(1);
                                }

                                testFileManagersSorted.add(manager);
                            }

                        } catch (Exception ce) {
                            System.err.println(this.getClass().getName()
                                    + "CoreException: " + ce.getMessage()); //$NON-NLS-1$
                            ce.printStackTrace(System.err);
                        }

                    }

                }
            }
        }

        //
        // Sort by descending priority.
        Collections.sort(projectManagers, new Comparator<TestProjectManager>() {
            public int compare(TestProjectManager m1, TestProjectManager m2) {
                return m1.priority.compareTo(m2.priority);
            }
        });

        for (TestProjectManager manager : projectManagers) {
            testProjectManagersSorted.add(manager.manager);
        }

        Collections.sort(testFileManagersSorted, new Comparator<TestFileManager>() {
            public int compare(TestFileManager m1, TestFileManager m2) {
                return m1.priority.compareTo(m2.priority);
            }
        });

        return;
    }
}
