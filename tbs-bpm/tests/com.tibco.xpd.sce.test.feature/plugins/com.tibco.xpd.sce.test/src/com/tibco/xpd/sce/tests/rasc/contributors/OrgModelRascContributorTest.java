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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.exception.RuntimeApplicationException;
import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.om.transform.de.OrgModelRascContributor;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.rasc.core.RascWriter;

/**
 * Test Org-Model RASC Contribution.
 *
 * @author pwatson
 * @since 20 Mar 2019
 */
@SuppressWarnings("nls")
public class OrgModelRascContributorTest
        extends AbstractBuildingBaseResourceTest {

    private TestResourceInfo orgmodelResourceInfo =
            new TestResourceInfo("resources/OrgModelRascTest",
                    "RASC/Organization{om}/OrganizationModel.om") {
            };

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     */
    @Override
    protected String getTestName() {
        return "OrgModel RASC Transform Test";
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { orgmodelResourceInfo };
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
        RascContributor fixture = new OrgModelRascContributor();
        checkTestFilesCreated();
        IFile testFile = getTestFile("OrganizationModel.om");
        if (testFile == null) {
            throw new NullPointerException("Unable to find test file");
        }

        IProject project =
                ResourcesPlugin.getWorkspace().getRoot().getProject("RASC");

        assertTrue(fixture.hasContributionsFor(project));
    }

    public void testProcess() throws Exception {
        // ensure the test data is present
        checkTestFilesCreated();
        if (getTestFile("OrganizationModel.om") == null) {
            throw new NullPointerException("Unable to find test file");
        }

        // create a mock writer to capture contributor's output
        MockRascWriter writer = new MockRascWriter();

        // find the project in which the test data resides
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot().getProject("RASC");

        // call the contributor's process() method
        RascContributor fixture = new OrgModelRascContributor();
        fixture.process(project, null, writer);

        // only one artifact should have been added to the writer
        assertEquals(1, writer.getArtifacts().size());

        // that artifact should be named after the test data file
        // and be targetted to the DE micro-service
        WriterContent artifact = writer.getArtifacts().get(0);
        assertEquals("OrganizationModel.de", artifact.getFullPath());
        assertArrayEquals(new MicroService[] { MicroService.DE },
                artifact.getServices());

        assertEquals(artifact.getArtifactName(), "Organization Model Label");
        assertEquals(artifact.getInternalName(), "OrganizationModelName");

        // some data was written to the artifact
        assertTrue(artifact.getContent().size() > 0);
    }

    /**
     * Used to capture the properties and content of an artifact written to the
     * MockRascWriter.
     */
    private static class WriterContent {
        private String resourcePath;

        private MicroService[] services;

        private ByteArrayOutputStream content;

        private String internalName;

        private String artifactName;

        public WriterContent(String aResourcePath, String aArtifactName,
                String aInternalName, MicroService[] aServices) {
            resourcePath = aResourcePath;
            artifactName = aArtifactName;
            internalName = aInternalName;
            services = aServices;
            content = new ByteArrayOutputStream();
        }

        public String getArtifactName() {
            return artifactName;
        }

        public String getInternalName() {
            return internalName;
        }

        public String getFullPath() {
            return resourcePath;
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
        public OutputStream addContent(String aName, String aArtifactName,
                String aInternalName, MicroService[] aMicroServices)
                throws RuntimeApplicationException, IOException {
            WriterContent result = new WriterContent(aName, aArtifactName,
                    aInternalName, aMicroServices);
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
