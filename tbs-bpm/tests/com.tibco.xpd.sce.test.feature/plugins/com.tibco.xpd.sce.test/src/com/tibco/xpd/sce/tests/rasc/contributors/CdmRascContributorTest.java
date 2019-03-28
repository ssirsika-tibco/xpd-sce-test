/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.rasc.contributors;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.exception.RuntimeApplicationException;
import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.n2.cdm.rasc.CdmRascContributor;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.rasc.core.RascWriter;

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
                .filter(a -> a.name.equals("com.example.simple.dm"))
                .findFirst();
        assertTrue(artifactOpt.isPresent());
        WriterContent artifact = artifactOpt.get();
        assertArrayEquals(new MicroService[] { MicroService.CM },
                artifact.getServices());

        // some data was written to the artifact
        assertTrue(artifact.getContent().size() > 0);
    }

    /**
     * Used to capture the properties and content of an artifact written to the
     * MockRascWriter.
     */
    private static class WriterContent {
        private String name;

        private MicroService[] services;

        private ByteArrayOutputStream content;

        public WriterContent(String aName, MicroService[] aServices) {
            name = aName;
            services = aServices;
            content = new ByteArrayOutputStream();
        }

        public String getName() {
            return name;
        }

        public MicroService[] getServices() {
            return services;
        }

        public ByteArrayOutputStream getContent() {
            return content;
        }
    }

    /**
     * A mock implementation of RascWriter to capture the properties and content
     * of the artifacts written to the RASC by the RascContributor.
     */
    private static class MockRascWriter implements RascWriter {
        final ArrayList<WriterContent> artifacts = new ArrayList<>();

        /**
         * @see com.tibco.xpd.rasc.core.RascWriter#addContent(java.lang.String,
         *      com.tibco.bpm.dt.rasc.MicroService[])
         */
        @Override
        public OutputStream addContent(String aName,
                MicroService[] aMicroServices)
                throws RuntimeApplicationException, IOException {
            WriterContent result = new WriterContent(aName, aMicroServices);
            artifacts.add(result);
            return result.getContent();
        }

        /**
         * Allows the test to retrieve the captured artifacts, in the order they
         * were captured.
         * 
         * @return the ordered collection of captured artifacts.
         */
        public List<WriterContent> getArtifacts() {
            return artifacts;
        }
    }
}
