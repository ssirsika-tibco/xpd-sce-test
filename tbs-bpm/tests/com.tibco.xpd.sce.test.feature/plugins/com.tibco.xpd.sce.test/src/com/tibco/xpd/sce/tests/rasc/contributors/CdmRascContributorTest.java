/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.rasc.contributors;

import static org.junit.Assert.assertArrayEquals;

import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.n2.cdm.rasc.CdmRascContributor;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.sce.tests.rasc.contributors.MockRascWriter.WriterContent;

/**
 * Test CDM (created from BOM) RASC Contribution.
 *
 * @author jarciuch
 * @since 26 Mar 2019
 */
@SuppressWarnings("nls")
public class CdmRascContributorTest extends AbstractBuildingBaseResourceTest {

    private final String projectName = "CdmRascTest";

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     */
    @Override
    protected String getTestName() {
        return "CDM RASC Contributor Test";
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo("resources",
                        projectName + "/Business Objects{bom}/Simple.bom") {
                } };
        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
    }

    public void testHasContributionsFor() throws Exception {
        RascContributor fixture = new CdmRascContributor();
        checkTestFilesCreated();
        IFile testFile = getTestFile("Simple.bom");
        if (testFile == null) {
            throw new NullPointerException("Unable to find test file");
        }

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);

        assertTrue(fixture.hasContributionsFor(project));
    }

    public void testProcess() throws Exception {

        // create a mock writer to capture contributor's output
        MockRascWriter writer = new MockRascWriter();

        // find the project in which the test data resides
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);

        // call the contributor's process() method
        RascContributor fixture = new CdmRascContributor();
        fixture.process(project, null, writer);

        // There should be 2 artifacts as one is added by XpdProjectWizard used
        // to create project in the setUp method (default .bom is named the same
        // as the project).
        assertEquals(2, writer.getArtifacts().size());

        // that artifact should be named after the test data file
        // and be targetted to the DE micro-service
        Optional<WriterContent> artifactOpt = writer.getArtifacts().stream()
                .filter(a -> a.getFullPath()
                        .equals("cm/com.example.simple.dm"))
                .findFirst();
        assertTrue(artifactOpt.isPresent());
        WriterContent artifact = artifactOpt.get();
        assertArrayEquals(new MicroService[] { MicroService.CM },
                artifact.getServices());

        assertEquals(artifact.getArtifactName(), "My Test BOM");
        assertEquals(artifact.getInternalName(), "com.example.simple");

        // some data was written to the artifact
        assertTrue(artifact.getContent().size() > 0);
    }
}
