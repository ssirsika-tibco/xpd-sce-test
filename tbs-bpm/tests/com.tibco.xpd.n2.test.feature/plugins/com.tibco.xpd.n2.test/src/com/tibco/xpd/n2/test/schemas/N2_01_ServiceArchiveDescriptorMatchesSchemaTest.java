/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.test.schemas;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.n2.test.Activator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 16 April 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class N2_01_ServiceArchiveDescriptorMatchesSchemaTest extends TestCase {

    protected static String TEST_PROJECT_NAME =
            "ServiceArchiveDescriptorMatchesSchemaTest";

    private static final String PLATFORM_EXAMPLE_FILE_ZIP =
            "test-resources/projects/wpTest.zip";

    private static final String SCHEMA_BASE_URI =
            "platform:/plugin/com.tibco.xpd.n2.test/test-resources/schemas/";

    private static final String SCHEMA1_NAME = "WorkPresentationDeployment.xsd";

    private static final String SCHEMA2_NAME = "EmailChannelDescriptor.xsd";

    private static final String SCHEMA3_NAME = "channeltype.xsd";

    protected final ResourceSet rs;

    protected final TransactionalEditingDomain ed;

    protected final TransactionalCommandStack stack;

    protected URI testResourceURI;

    protected IProject project;

    protected WorkingCopy workingCopy;

    protected IFile modelFile;

    protected String modelLocation;

    /**
     * 
     */
    public N2_01_ServiceArchiveDescriptorMatchesSchemaTest() {
        rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        stack = (TransactionalCommandStack) ed.getCommandStack();
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        clean();

        InstallProjectOperation op =
                new InstallProjectOperation(TEST_PROJECT_NAME, Activator
                        .getDefault().getBundle()
                        .getEntry(PLATFORM_EXAMPLE_FILE_ZIP));
        op.run(new NullProgressMonitor());

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        project = root.getProject(TEST_PROJECT_NAME);
        assertTrue("Project was not created!", project.exists());

        project.refreshLocal(IResource.DEPTH_INFINITE,
                new NullProgressMonitor());
        project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                new NullProgressMonitor());
        project.build(IncrementalProjectBuilder.FULL_BUILD,
                new NullProgressMonitor());

    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        // clean();
        super.tearDown();
    }

    public void testXPDLToOMTransformation() throws Exception {
        IFile warFile = project.getFile("/testProj/.wpModules/wp.war");
        boolean exists = warFile.exists();
        if (exists) {
            ZipFile zipFile;
            zipFile =
                    new ZipFile(FileLocator.toFileURL(warFile.getLocationURI()
                            .toURL()).getFile());
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.getName()
                        .indexOf("Service-Archive-Descriptor.xml") > 0) {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);

                    InputStream SchemaInputStream1 =
                            new ResourceSetImpl().getURIConverter()
                                    .createInputStream(URI
                                            .createURI(SCHEMA_BASE_URI
                                                    + SCHEMA1_NAME));
                    InputStream SchemaInputStream2 =
                            new ResourceSetImpl().getURIConverter()
                                    .createInputStream(URI
                                            .createURI(SCHEMA_BASE_URI
                                                    + SCHEMA2_NAME));
                    InputStream SchemaInputStream3 =
                            new ResourceSetImpl().getURIConverter()
                                    .createInputStream(URI
                                            .createURI(SCHEMA_BASE_URI
                                                    + SCHEMA3_NAME));
                    Source schemaSource1 =
                            new StreamSource(SchemaInputStream1, SCHEMA1_NAME);
                    Source schemaSource2 =
                            new StreamSource(SchemaInputStream2, SCHEMA2_NAME);
                    Source schemaSource3 =
                            new StreamSource(SchemaInputStream3, SCHEMA3_NAME);

                    Source[] schemas =
                            new Source[] { schemaSource3, schemaSource2,
                                    schemaSource1 };
                    Source document = new StreamSource(inputStream);
                    TestUtil.validateAgainstXMLSchema(schemas, document);
                }
            }
        }
    }

    private void clean() {
        IProject proj =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(TEST_PROJECT_NAME);
        if (proj.exists()) {
            TestUtil.removeProject(proj.getName());
        }
    }
}
