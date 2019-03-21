/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.rasc.contributors;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.exception.RuntimeApplicationException;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.n2.pe.transform.PERascContributor;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.rasc.core.RascWriter;

/**
 * Test Process Engine and Page-Flow RASC Contribution.
 * 
 * @author pwatson
 * @since 20 Mar 2019
 */
@SuppressWarnings("nls")
public class PERascContributorTest extends AbstractN2BaseResourceTest {

    private TestResourceInfo processResourceInfo =
            new TestResourceInfo("resources/BpelRascTest",
                    "RASC/Process Packages{processes}/TestContributor.xpdl") {
            };

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     */
    @Override
    protected String getTestName() {
        return "BPEL RASC Transform Test";
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { processResourceInfo };
        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.pe.test";
    }

    public void testHasContributionsFor() throws Exception {
        RascContributor fixture = new PERascContributor();
        checkTestFilesCreated();
        IFile testFile = getTestFile("TestContributor.xpdl");
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
        if (getTestFile("TestContributor.xpdl") == null) {
            throw new NullPointerException("Unable to find test file");
        }

        // create a mock writer to capture contributor's output
        MockRascWriter writer = new MockRascWriter();

        // find the project in which the test data resides
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot().getProject("RASC");

        // call the contributor's process() method
        RascContributor fixture = new PERascContributor();
        fixture.process(project, null, writer);

        // these are the expected artefacts and their destinations
        Map<String, MicroService[]> expected = new HashMap<>();
        expected.put("TestPageFowProcess.bpel",
                new MicroService[] { MicroService.WR });
        expected.put("TestServiceProcess.bpel",
                new MicroService[] { MicroService.WR });
        expected.put("TestBusinessProcess.bpel",
                new MicroService[] { MicroService.BP });

        // artifacts should have been added to the writer
        assertEquals(expected.size(), writer.getArtifacts().size());

        for (WriterContent artifact : writer.getArtifacts()) {
            boolean found = false;
            for (Map.Entry<String, MicroService[]> entry : expected
                    .entrySet()) {
                if (entry.getKey().equals(artifact.getName())) {
                    assertArrayEquals(entry.getValue(), artifact.getServices());

                    // some data was written to the artifact
                    assertTrue(artifact.getContent().size() > 0);
                    found = true;
                    break;
                }
            }
            assertTrue("Unexpected artifact: " + artifact.getName(), found);
        }
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
