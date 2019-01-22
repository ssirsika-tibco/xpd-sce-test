/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.core.test;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Test ProjectUtil and ProjectUtil2 utility classes.
 * 
 * TODO: JA Provide more tests for other public methods.
 * 
 * @author jarciuch
 * @since 3.5.3
 */
public class ProjectUtilTest extends TestCase {

    private IProject p1;

    private IProject p2;

    private IProject p3;

    private IProject p4;

    /**
     * Create a couple of projects.
     * 
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        p1 = TestUtil.createProjectFromWizard("p1");
        TestUtil.waitForJobs(1000);
        p2 = TestUtil.createProjectFromWizard("p2");
        TestUtil.waitForJobs(1000);
        p3 = TestUtil.createProjectFromWizard("p3");
        TestUtil.waitForJobs(1000);
        p4 = TestUtil.createProjectFromWizard("p4");
        TestUtil.waitForJobs(1000);
    }

    public void testGetDependencySortedProjects() throws Exception {
        assertTrue(p1.exists());
        assertTrue(p2.exists());
        assertTrue(p3.exists());
        assertTrue(p4.exists());
        ProjectUtil.addReferenceProject(p1, p2);
        ProjectUtil.addReferenceProject(p1, p4);
        ProjectUtil.addReferenceProject(p4, p3);

        {// case1 - direct dependency
            List<IProject> sortedProjects =
                    ProjectUtil.getDependencySortedProjects(Arrays.asList(p4,
                            p3,
                            p1));

            List<IProject> expectedSortedProjects = Arrays.asList(p3, p4, p1);
            assertTrue(String.format("Expected: '%1$s' Was: '%2$s'",
                    expectedSortedProjects,
                    sortedProjects),
                    sameContentAndOrder(expectedSortedProjects, sortedProjects));
        }
        {// case2 - indirect dependency
            List<IProject> sortedProjects =
                    ProjectUtil.getDependencySortedProjects(Arrays.asList(p1,
                            p3));

            List<IProject> expectedSortedProjects = Arrays.asList(p3, p1);
            assertTrue(String.format("Expected: '%1$s' Was: '%2$s'",
                    expectedSortedProjects,
                    sortedProjects),
                    sameContentAndOrder(expectedSortedProjects, sortedProjects));
        }
    }

    /**
     * Check if the provided lists of projects have the same content and order.
     * 
     * @param list1
     * @param list2
     * @return true if list have the same content. (equals() method is used to
     *         compare projects)
     */
    private boolean sameContentAndOrder(List<IProject> list1,
            List<IProject> list2) {
        int size = list1.size();
        if (size != list2.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes a couple of projects created in setUp() method.
     * 
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        TestUtil.removeProject(p1.getName());
        TestUtil.removeProject(p2.getName());
        TestUtil.removeProject(p3.getName());
        TestUtil.removeProject(p4.getName());
        TestUtil.waitForJobs(500);
        super.tearDown();
    }
}
