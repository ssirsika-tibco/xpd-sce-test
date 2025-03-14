/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.rasc.contributors;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.Version;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.n2.brm.BrmModelsRascContributor;
import com.tibco.xpd.rasc.core.RascAppSummary;
import com.tibco.xpd.rasc.core.RascContext;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.sce.tests.rasc.contributors.MockRascWriter.WriterContent;

import junit.framework.TestCase;

/**
 * Test Work Model RASC Contribution.
 *
 * @author aallway
 * @since 12 Apr 2019
 */
@SuppressWarnings("nls")
public class BrmModelRascContributorTest extends TestCase {

    /**
     * Test that the hasContributionsFor and RASC generation contribution for
     * BRM work model and work-type model work corectly.
     * 
     * @throws Exception
     */
    public void testProjectWithContributions() throws Exception {

        String projectName = "BrmRascTestProject";

        ProjectImporter projectImporter = importProject(projectName);
        try {
            RascContributor fixture = new BrmModelsRascContributor();

            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(projectName);

            assertTrue(
                    projectName + " project should have BRM RASC contributions",
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

            // the two BRM artifacts should have been added to the writer
            WriterContent wmArtifact = null;
            WriterContent wtArtifact = null;

            List<WriterContent> artifacts = writer.getArtifacts();

            for (WriterContent artifact : artifacts) {
                if ("workModel.wm".equals(artifact.getArtifactName())) {
                    wmArtifact = artifact;
                } else if ("workType.wt".equals(artifact.getArtifactName())) {
                    wtArtifact = artifact;
                }
            }

            assertTrue(projectName
                    + " project should have a contributed workModel.wm artifact",
                    wmArtifact != null);
            assertTrue(projectName
                    + " project should have a contributed workType.wt artifact",
                    wtArtifact != null);

            // The work model should be delivered to the Work-Manager service
            assertArrayEquals(projectName
                    + " project workModel.wm artifact should be targeted to the Work-manager micro-service",
                    new MicroService[] { MicroService.WM },
                    wmArtifact.getServices());

            // The work type model should be delivered to the Work-Manager
            // service
            // ...
            boolean foundWMService = false;
            // AND the Work-Presentation service
            boolean foundWPService = false;

            for (MicroService microService : wtArtifact.getServices()) {
                if (MicroService.WM.equals(microService)) {
                    foundWMService = true;
                }
                // AND the Work-Presentation service
                else if (MicroService.WP.equals(microService)) {
                    foundWPService = true;
                }
            }

            assertTrue(projectName
                    + " project workType.wt artifact should be targeted to the Work-Manager micro-service",
                    foundWMService);

            // AND the Work-Presentation service
            assertTrue(projectName
                    + " project workType.wt artifact should be targeted to the Work-Presentation micro-service",
                    foundWPService);

            // some data was written to the artifacts
            assertTrue(projectName
                    + " project workModel.wm artifact should have content",
                    wmArtifact.getContent().size() > 0);

            assertTrue(projectName
                    + " project workType.wt artifact should have content",
                    wtArtifact.getContent().size() > 0);

            // Should have the correct version range set.
            String contentString = wmArtifact.getContent().toString("UTF-8");

            String expectedWorkTypeVersionRange = "WorkModelType version=\"["
                    + version.toString() + "," + version.toString() + "]\"";

            assertTrue(projectName
                    + " project workModel.wm artifact should have version range set on worktype (should contain '"
                    + expectedWorkTypeVersionRange + "')",
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

        ProjectImporter projectImporter = importProject(projectName);
        try {
            RascContributor fixture = new BrmModelsRascContributor();

            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(projectName);

            assertFalse(
                    projectName
                            + " project should not have BRM RASC contributions",
                    fixture.hasContributionsFor(project));
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Check that a data project doesn't claim to have contributions.
     * 
     * @throws Exception
     */
    public void testProjectWithoutContributions2() throws Exception {

        String projectName = "BrmRascTestProjectWithoutContributions";

        ProjectImporter projectImporter = importProject(projectName);
        try {
            RascContributor fixture = new BrmModelsRascContributor();

            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(projectName);

            assertFalse(
                    projectName
                            + " project should not have BRM RASC contributions",
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
    private ProjectImporter importProject(String projectName) {
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/BrmRascTest/" + projectName + "/" }, //$NON-NLS-1$ //$NON-NLS-2$
                new String[] { projectName });

        assertTrue("Failed to load projects from \"resources/BrmRascTest/" //$NON-NLS-1$
                + projectName + "\"", //$NON-NLS-1$
                projectImporter != null);

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName); // $NON-NLS-1$
        assertTrue(projectName + " project does not exist", //$NON-NLS-1$
                project.isAccessible());

        TestUtil.buildAndWait();

        return projectImporter;
    }
}
