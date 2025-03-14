/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.rasc.contributors;

import static org.junit.Assert.assertArrayEquals;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.Version;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.n2.globalsignal.transform.GlobalSignalRascContributor;
import com.tibco.xpd.rasc.core.RascAppSummary;
import com.tibco.xpd.rasc.core.RascContext;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.sce.tests.rasc.contributors.MockRascWriter.WriterContent;

import junit.framework.TestCase;

/**
 * Test Global Signals RASC Contribution.
 *
 * @author pwatson
 * @since 10 July 2019
 */
@SuppressWarnings("nls")
public class GlobalSignalRascContributorTest extends TestCase {

    /**
     * Test that the hasContributionsFor and RASC generation contribution for
     * BRM work model and work-type model work corectly.
     * 
     * @throws Exception
     */
    public void testProjectWithContributions() throws Exception {
        String projectName = "simple-signal";

        ProjectImporter projectImporter = importProject("resources/GlobalSignalsRascTest/", projectName);
        try {
            RascContributor fixture = new GlobalSignalRascContributor();

            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(projectName);

            assertTrue(
                    projectName + " project should have GSD RASC contributions",
                    fixture.hasContributionsFor(project));

            // create a mock writer to capture contributor's output
            MockRascWriter writer = new MockRascWriter();

            // call the contributor's process() method
            Version version = new Version(
                    "1.0.0." + ProjectUtil2.getAutogeneratedQualifier());

            RascContext rascContext = new RascContext() {
                @Override
                public Version getVersion() {
                    return version;
                }

                @Override
                public RascAppSummary getAppSummary() {
                    return null;
                }
            };
            fixture.process(project, rascContext, null, writer);

            // the GSD artifact should have been added to the writer
            WriterContent gsdArtifact = null;
            for (WriterContent artifact : writer.getArtifacts()) {
                if ("globalSignal.gsd".equals(artifact.getArtifactName())) {
                    gsdArtifact = artifact;
                }
            }

            assertTrue(projectName
                    + " project should have a contributed globalSignal.gsd artifact", gsdArtifact != null);

            // The work model should be delivered to the Work-Manager service
            assertArrayEquals(projectName
                    + " project globalSignal.gsd artifact should be targeted to the BP micro-service",
                    new MicroService[] { MicroService.BP },
                    gsdArtifact.getServices());

            // some data was written to the artifacts
            assertTrue(projectName
                    + " project globalSignal.gsd artifact should have content",
                    gsdArtifact.getContent().size() > 0);

            assertTrue(projectName
                    + " project globalSignal.gsd artifact should have content", gsdArtifact.getContent().size() > 0);

            // Should have the correct version range set.
            String contentString = gsdArtifact.getContent().toString("UTF-8");

            String expectedWorkTypeVersionRange =
                    "globalSignalDefinitions signalName=\"simple-signal.gsd#GlobalSignal\"";

            assertTrue(projectName
                    + " project globalSignal.gsd artifact should contain '" + expectedWorkTypeVersionRange + "'",
                    contentString.contains(expectedWorkTypeVersionRange));

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Check that a process project with no user tasks doesn't claim to have
     * contributions.
     * 
     * @throws Exception
     */
    public void testProjectWithoutContributions1() throws Exception {

        String projectName = "BrmRascTestProjectWithoutContributions";

        ProjectImporter projectImporter = importProject("resources/BrmRascTest/", projectName);
        try {
            RascContributor fixture = new GlobalSignalRascContributor();

            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(projectName);

            assertFalse(
                    projectName
                            + " project should not have GSD RASC contributions",
                    fixture.hasContributionsFor(project));
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Import the given project from test plugin resources.
     * 
     * @param projectName
     * @return
     */
    private ProjectImporter importProject(String aFolder, String projectName) {
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test",
                new String[] { aFolder + projectName + "/" },
                new String[] { projectName });

        assertTrue("Failed to load projects from \"" + aFolder + projectName + "\"",
                projectImporter != null);

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);
        assertTrue(projectName + " project does not exist", 
                project.isAccessible());

        TestUtil.buildAndWait();

        return projectImporter;
    }
}
