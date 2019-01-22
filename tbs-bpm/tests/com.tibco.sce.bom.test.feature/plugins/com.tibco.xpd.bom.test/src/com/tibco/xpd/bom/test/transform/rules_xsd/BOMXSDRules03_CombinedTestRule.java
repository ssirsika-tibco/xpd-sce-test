/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.rules_xsd;

import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

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
public class BOMXSDRules03_CombinedTestRule extends TransformationTest {

    protected static String TEST_PROJECT_NAME = "CyclicDependencyRule";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd-rules";

    protected String BOM_MODEL_FILE = "BOMXSDRules02CyclicModel.bom";

    protected String BOM_MODEL_FILE2 = "BOMXSDRules02CyclicModel2.bom";

    protected String BOM_MODEL_FILE3 = "BOMXSDRules01AssociationTypeRule.bom";

    protected IFile modelFile;

    protected IFile modelFile2;

    protected IFile modelFile3;

    protected String modelLocation;

    protected String modelLocation2;

    protected String modelLocation3;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        timeStart = System.currentTimeMillis();

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;

        modelFileNames.add(BOM_MODEL_FILE);
        modelFileNames.add(BOM_MODEL_FILE2);
        modelFileNames.add(BOM_MODEL_FILE3);

        super.setUp();

        // crating project, special folder, and copy example files to it
        project = TestUtil.createProject(TEST_PROJECT_NAME);

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig != null) {
            ProjectDetails projDetails =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            projectConfig.setProjectDetails(projDetails);
            Destinations createDestinations =
                    ProjectConfigFactory.eINSTANCE.createDestinations();
            projDetails.setGlobalDestinations(createDestinations);
            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();
            destination.setType("BPM"); //$NON-NLS-1$
            createDestinations.getDestination().add(destination);
        }

        SpecialFolder specialFolder =
                TestUtil.createSpecialFolder(project, "Business Object Models", //$NON-NLS-1$
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        modelFile = specialFolder.getFolder().getFile(BOM_MODEL_FILE);
        InputStream modelInputStream =
                new ResourceSetImpl()
                        .getURIConverter()
                        .createInputStream(URI.createURI(PLATFORM_EXAMPLE_FILES_BASE
                                + '/' + BOM_MODEL_FILE));
        modelFile.create(modelInputStream, true, null);
        modelFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
        modelLocation =
                modelFile.getLocation().removeLastSegments(1)
                        .toPortableString();
        migrate(modelFile);

        modelFile2 = specialFolder.getFolder().getFile(BOM_MODEL_FILE2);
        InputStream modelInputStream2 =
                new ResourceSetImpl()
                        .getURIConverter()
                        .createInputStream(URI.createURI(PLATFORM_EXAMPLE_FILES_BASE
                                + '/' + BOM_MODEL_FILE2));
        modelFile2.create(modelInputStream2, true, null);
        modelFile2
                .refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
        migrate(modelFile2);

        modelLocation2 =
                modelFile2.getLocation().removeLastSegments(1)
                        .toPortableString();

        modelFile3 = specialFolder.getFolder().getFile(BOM_MODEL_FILE3);
        InputStream modelInputStream3 =
                new ResourceSetImpl()
                        .getURIConverter()
                        .createInputStream(URI.createURI(PLATFORM_EXAMPLE_FILES_BASE
                                + '/' + BOM_MODEL_FILE3));
        modelFile3.create(modelInputStream3, true, null);
        modelFile3
                .refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
        migrate(modelFile3);

        modelLocation3 =
                modelFile3.getLocation().removeLastSegments(1)
                        .toPortableString();

        /* Sid: Need to wait for another build before moving on. */
        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.CLEAN_BUILD,
                        new NullProgressMonitor());
        TestUtil.waitForJobs();

        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.FULL_BUILD,
                        new NullProgressMonitor());

        TestUtil.waitForJobs();

        return;
    }

    public void testTransformation() throws Exception {
        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_XSD };

        /*
         * Sid XPD-5118 Check for a probledm with "Cyclic Dependency" ratehr
         * than a specifc count, which tends to go wrong as we add new
         * validations.
         */
        int count =
                TransformUtil.countMarkersMatchingPattern(destIds,
                        modelFile,
                        ".*Cyclic Dependency.*");

        assertEquals("Expected 'cyclic dependency' problem on BOM file: " + modelFile.getName(), 1, count); //$NON-NLS-1$

        count =
                TransformUtil.countMarkersMatchingPattern(destIds,
                        modelFile2,
                        ".*Cyclic Dependency.*");

        assertEquals("Expected 'cyclic dependency' problem on BOM file: " + modelFile2.getName(), 1, count); //$NON-NLS-1$

        destIds = new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_XSD };

        count =
                TransformUtil.countMarkersMatchingPattern(destIds,
                        modelFile3,
                        ".*Association Classes are not supported.*");
        assertEquals("Expected 'Association Classes are not supported' problem on BOM file: " + modelFile3.getName(), 1, count); //$NON-NLS-1$

        count =
                TransformUtil
                        .countMarkersMatchingPattern(destIds,
                                modelFile3,
                                ".*Only Generalization and unidirectional composition.*");
        assertEquals("Expected 'Association Classes are not supported' problem on BOM file: " + modelFile3.getName(), 4, count); //$NON-NLS-1$

    }
}
