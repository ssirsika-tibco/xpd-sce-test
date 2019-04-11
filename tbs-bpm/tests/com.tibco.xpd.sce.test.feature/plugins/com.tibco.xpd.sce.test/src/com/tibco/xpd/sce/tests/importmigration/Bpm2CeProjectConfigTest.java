/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * Test for DDP 2.a
 * (http://confluence.tibco.com/pages/viewpage.action?pageId=171031408)
 * 
 * Projects with XPD Nature should have all destinations removed and CE
 * destination added.
 *
 * @author aallway
 * @since 22 Mar 2019
 */
public class Bpm2CeProjectConfigTest extends TestCase {

    private static final String ParticipantSharedResource = null;

    // @Test
    public void testOrgProjectMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_Org"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testDataProjectMigration() {
        String projectName = "ProjectMigrationTest_Data"; //$NON-NLS-1$

        ProjectImporter projectImporter = doTestProject(projectName);

        /*
         * Check that the specific integer properties/primtives we know about
         * for definite have been changed.
         */
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);

        IFile testBomFile =
                project.getFile("Business Objects/NumberRefactoring.bom"); //$NON-NLS-1$
        WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(testBomFile);
        Model model = (Model) testWC.getRootElement();

        EList<Element> allOwnedElements = model.allOwnedElements();

        PrimitiveType decimalType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet(),
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        for (Element element : allOwnedElements) {
            if (element instanceof Property && "integerAttribute" //$NON-NLS-1$
                    .equals(((Property) element).getName())) {
                Property property = (Property) element;

                assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                        + property.getName()
                        + "' should have been converted from Integer to Decimal", //$NON-NLS-1$
                        (decimalType.equals(property.getType())));

                Object facetPropertyValue =
                        PrimitivesUtil.getFacetPropertyValue(
                                (PrimitiveType) property.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                property);

                assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                        + property.getName()
                        + "' should have been converted to Decimal Subtype FixedPoint", //$NON-NLS-1$
                        facetPropertyValue instanceof EnumerationLiteral
                                && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                        .equals((((EnumerationLiteral) facetPropertyValue)
                                                .getName())));

                facetPropertyValue = PrimitivesUtil.getFacetPropertyValue(
                        (PrimitiveType) property.getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                        property);

                assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                        + property.getName()
                        + "' should have been converted to Decimal, FixedPoint with ZERO decimal places", //$NON-NLS-1$
                        new Integer(0).equals(facetPropertyValue));

            } else if (element instanceof PrimitiveType && "MyIntegerPrimitive" //$NON-NLS-1$
                    .equals(((PrimitiveType) element).getName())) {
                PrimitiveType primitiveType = (PrimitiveType) element;

                assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                        + primitiveType.getName()
                        + "' should have been converted from Integer to Decimal", //$NON-NLS-1$
                        decimalType.equals(primitiveType.getGenerals().get(0)));

                assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                        + primitiveType.getName()
                        + "' should have been converted to Decimal Subtype FixedPoint", //$NON-NLS-1$
                        PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                .equals(((EnumerationLiteral) PrimitivesUtil
                                        .getFacetPropertyValue(primitiveType,
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE))
                                                        .getName()));

                assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                        + primitiveType.getName()
                        + "' should have been converted to Decimal, FixedPoint with ZERO decimal places", //$NON-NLS-1$
                        new Integer(0).equals(PrimitivesUtil
                                .getFacetPropertyValue(primitiveType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES)));
            }
        }

        /* Clean up. */
        projectImporter.performDelete();
    }

    @Test
    public void testProcessProjectMigration() {
        String projectName = "ProjectMigrationTest_Process"; //$NON-NLS-1$

        ProjectImporter projectImporter = doTestProject(projectName); // $NON-NLS-1$

        /*
         * After checking all the standard stuff (like destination set, no
         * non-datamapper related JavaScript mappings, we'll do some extra
         * checking on this particular Projects which has specific DataMapper
         * mapping grammar processes to test they still has all of the
         * datamappings that we expect to be persisted thru mgiration (i.e. that
         * we have not deleted them accidentally).
         */
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);

        IFile dataMapperXpdl = project.getFile(
                "Process Packages/ProjectMigrationTest_DataMapper_DataMappings.xpdl"); //$NON-NLS-1$
        WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(dataMapperXpdl);
        Package pkg = (Package) testWC.getRootElement();

        /* Get the right process. */
        Process dataMapperProcess = null;

        for (Process process : pkg.getProcesses()) {
            if ("ProjectMigrationTest_DataMapper-DataMappings-Process" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getDisplayName(process))) {
                dataMapperProcess = process;
                break;
            }
        }

        /* Check the specific activity mappings. */
        int numChecked = 0;

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(dataMapperProcess);

        for (Activity activity : allActivitiesInProc) {
            String displayName = Xpdl2ModelUtil.getDisplayName(activity);

            EStructuralFeature mappingFeature1 = null;
            OtherElementsContainer mappingParent1 = null;
            EStructuralFeature mappingFeature2 = null;
            OtherElementsContainer mappingParent2 = null;

            if ("Send Task mappings".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((Task) activity.getImplementation())
                        .getTaskSend().getMessage();

            } else if ("Web-service task mappings".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((Task) activity.getImplementation())
                        .getTaskService().getMessageIn();
                mappingFeature2 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_OutputMappings();
                mappingParent2 = ((Task) activity.getImplementation())
                        .getTaskService().getMessageOut();

            } else if ("Error Event Web-service error mappings" //$NON-NLS-1$
                    .equals(displayName)) {
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_OutputMappings();
                mappingParent1 =
                        ((CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(
                                ((ResultError) activity.getEvent()
                                        .getEventTriggerTypeNode()),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CatchErrorMappings()))
                                                .getMessage();

            } else if ("Throw message mappings".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((TriggerResultMessage) activity.getEvent()
                        .getEventTriggerTypeNode()).getMessage();

            } else if ("Start Event User defined WSDL".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_OutputMappings();
                mappingParent1 = ((TriggerResultMessage) activity.getEvent()
                        .getEventTriggerTypeNode()).getMessage();

            } else if ("Reply To: Start Event User defined WSDL" //$NON-NLS-1$
                    .equals(displayName)) {
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((TriggerResultMessage) activity.getEvent()
                        .getEventTriggerTypeNode()).getMessage();

            } else if ("SubProcess mappings".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((SubFlow) activity.getImplementation());
                mappingFeature2 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_OutputMappings();
                mappingParent2 = ((SubFlow) activity.getImplementation());

            } else if ("Error Event SubProcess Error mappings" //$NON-NLS-1$
                    .equals(displayName)) {
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_OutputMappings();
                mappingParent1 =
                        ((CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(
                                ((ResultError) activity.getEvent()
                                        .getEventTriggerTypeNode()),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CatchErrorMappings()))
                                                .getMessage();

            } else if ("Error Event".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((Message) Xpdl2ModelUtil.getOtherElement(
                        ((ResultError) activity.getEvent()
                                .getEventTriggerTypeNode()),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_FaultMessage()));

            } else if ("Receive Task".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_OutputMappings();
                mappingParent1 = ((Task) activity.getImplementation())
                        .getTaskReceive().getMessage();

            } else if ("Reply To: Receive Task".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((Task) activity.getImplementation())
                        .getTaskSend().getMessage();

            } else if ("Error Event 2".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_InputMappings();
                mappingParent1 = ((Message) Xpdl2ModelUtil.getOtherElement(
                        ((ResultError) activity.getEvent()
                                .getEventTriggerTypeNode()),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_FaultMessage()));

            } else if ("Method1 - PUT Resource1".equals(displayName)) { //$NON-NLS-1$
                mappingFeature1 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ScriptDataMapper();
                mappingParent1 = ((Task) activity.getImplementation())
                        .getTaskService().getMessageIn();
                mappingFeature2 = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ScriptDataMapper();
                mappingParent2 = ((Task) activity.getImplementation())
                        .getTaskService().getMessageOut();

            }

            if (mappingFeature1 != null || mappingFeature2 != null) {
                numChecked++;
                if (mappingFeature1 != null) {
                    Object mappingContainer1 = Xpdl2ModelUtil
                            .getOtherElement(mappingParent1, mappingFeature1);

                    assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(
                                    Xpdl2ModelUtil.getProcess(activity))
                            + ":" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(activity)
                            + " - 1st DataMapper mappings container is missing or empty", //$NON-NLS-1$
                            (mappingContainer1 instanceof ScriptDataMapper)
                                    && !((ScriptDataMapper) mappingContainer1)
                                            .getDataMappings().isEmpty());
                }

                if (mappingFeature2 != null) {
                    Object mappingContainer2 = Xpdl2ModelUtil
                            .getOtherElement(mappingParent2, mappingFeature2);

                    assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(
                                    Xpdl2ModelUtil.getProcess(activity))
                            + ":" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(activity)
                            + " - 2nd DataMapper mappings container is missing or empty", //$NON-NLS-1$
                            (mappingContainer2 instanceof ScriptDataMapper)
                                    && !((ScriptDataMapper) mappingContainer2)
                                            .getDataMappings().isEmpty());
                }

            } else if ("Script Task".equals(displayName)) { //$NON-NLS-1$
                numChecked++;
                /*
                 * Script task is a special case as it the Script DataMapper is
                 * contained in an Expression.
                 */
                ScriptDataMapper scriptDataMapper =
                        DataMapperUtils.getExistingScriptDataMapper(
                                ((Task) activity.getImplementation())
                                        .getTaskScript().getScript());

                assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName(
                                Xpdl2ModelUtil.getProcess(activity))
                        + ":" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName(activity)
                        + " - Script Task DataMapper mappings are missing or empty", //$NON-NLS-1$
                        (scriptDataMapper != null) && !scriptDataMapper
                                .getDataMappings().isEmpty());

            } else if ("Task Script".equals(displayName)) { //$NON-NLS-1$
                numChecked++;
                /*
                 * Task scripts are a special case as it the Script DataMapper
                 * is contained in an Expression.
                 */
                Audit audit = (Audit) Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit());

                ScriptDataMapper scriptDataMapper =
                        DataMapperUtils.getExistingScriptDataMapper(
                                audit.getAuditEvent().get(0).getInformation());

                assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName(
                                Xpdl2ModelUtil.getProcess(activity))
                        + ":" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName(activity)
                        + " - Task Initiate Script DataMapper mappings are missing or empty", //$NON-NLS-1$
                        (scriptDataMapper != null) && !scriptDataMapper
                                .getDataMappings().isEmpty());

            }
        }

        assertTrue("Number of dataMapper mapping scenarios was " + numChecked //$NON-NLS-1$
                + " but expected 15", //$NON-NLS-1$
                numChecked == 15);

        projectImporter.performDelete();

    }

    @Test
    public void testMaaProjectMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_maa"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testProcessAndDataBuildFolderRemovalMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_ProcessBuildFolders"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testOrgBuildFolderRemovalMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_OrgBuildFolders"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testGenBomMoveMigration1_UserBomAlreadyExists() {
        String projectName = "ProjectMigrationTest_GenAndUserBOMData"; //$NON-NLS-1$
        ProjectImporter projectImporter = doTestProject(projectName); // $NON-NLS-1$

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);

        /*
         * Check that the expected BOMs are in new location and not in old
         * location.
         */
        assertTrue(
                "BOM 'Generated Business Objects/org.example.NewWSDLFile.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/org.example.NewWSDLFile.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Generated Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/org.example.NewWSDLFile.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile("Business Objects/org.example.NewWSDLFile.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile(
                        "Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom") //$NON-NLS-1$
                        .exists());

        projectImporter.performDelete();

    }

    @Test
    public void testGenBomMoveMigration1_UserBomNotExist() {
        String projectName = "ProjectMigrationTest_GenBOMOnly"; //$NON-NLS-1$
        ProjectImporter projectImporter = doTestProject(projectName); // $NON-NLS-1$

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);
        /*
         * Check that the expected BOMs are in new location and not in old
         * location.
         */
        assertTrue(
                "BOM 'Generated Business Objectsorg.example.ShouldMigrateToUserDefWSDL.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/org.example.ShouldMigrateToUserDefWSDL.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Generated Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/org.example.ShouldMigrateToUserDefWSDL.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile(
                        "Business Objects/org.example.ShouldMigrateToUserDefWSDL.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile(
                        "Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom") //$NON-NLS-1$
                        .exists());

        /* Check that special folder has been added. */
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        boolean found = false;
        for (SpecialFolder specialFolder : projectConfig.getSpecialFolders()
                .getFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
            if (specialFolder.getGenerated() == null) {
                if (specialFolder.getFolder().exists()) {
                    if ("Business Objects" //$NON-NLS-1$
                            .equals(specialFolder.getFolder().getName())) {
                        found = true;
                    }
                }
            }
        }

        assertTrue(
                "User defined 'Business Objects' folder is not configured as a Special Folder", //$NON-NLS-1$
                found);

        projectImporter.performDelete();

    }

    /**
     * Test the given project.
     * 
     * @param projectName
     */
    private ProjectImporter doTestProject(String projectName) {
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] {
                        "resources/ImportMigrationTests/" + projectName + "/" }, //$NON-NLS-1$ //$NON-NLS-2$
                new String[] { projectName });

        assertTrue(
                "Failed to load projects from \"resources/ImportMigrationTests/ImportMigrationTests/" //$NON-NLS-1$
                        + projectName + "\"", //$NON-NLS-1$
                projectImporter != null);

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName); // $NON-NLS-1$
        assertTrue(projectName + " project does not exist", //$NON-NLS-1$
                project.isAccessible());

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        ProjectDetails projectDetails = projectConfig.getProjectDetails();

        /*
         * Check the project has only the CE destination set.
         */
        checkProjectHasOnlyCEDestination(project, projectDetails);

        /*
         * Check for unwanted folders being left behind.
         */
        checkUnwantedSpecialFoldersRemoved(project, projectConfig);

        /*
         * Ensure that the WSDL project asset as been removed.
         */
        checkUnwantedAssetConfigRemoved(projectConfig);

        /*
         * Ensure that unwanted natures have been removed.
         */
        checkUnwantedNaturesAndBuildersRemoved(project);

        /*
         * Check general rules for XPDL migration
         */
        checkBomGeneralContent(project);

        /*
         * Check general rules for XPDL migration
         */
        checkXpdlGeneralContent(project);

        return projectImporter;
    }

    /**
     * Check general rules for XPDL migration
     * 
     * @param project
     */
    private void checkXpdlGeneralContent(IProject project) {
        /*
         * All processes and process interfaces should have the CE destination
         * set.
         * 
         * This also has the advantage of checking that the XPDL migration has
         * created valid format xpdl files with things like simulation namespace
         * elements removed.
         * 
         */
        Collection<IResource> xpdlFiles = SpecialFolderUtil
                .getAllDeepResourcesInSpecialFolderOfKind(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                        Xpdl2ResourcesConsts.XPDL_EXTENSION,
                        false);

        for (IResource xpdlFile : xpdlFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);

            assertTrue("File '" + xpdlFile.getName() //$NON-NLS-1$
                    + "' was not migrated successfully (cannot load working copy).", //$NON-NLS-1$
                    wc != null);

            Package pkg = (Package) wc.getRootElement();

            Collection<ExtendedAttributesContainer> procsAndIfcs =
                    new ArrayList<>();
            procsAndIfcs.addAll(pkg.getProcesses());

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                procsAndIfcs.addAll(processInterfaces.getProcessInterface());
            }

            for (ExtendedAttributesContainer procOrIfc : procsAndIfcs) {
                boolean CeDestFound = false;
                boolean otherDestFound = false;

                for (ExtendedAttribute extendedAttribute : procOrIfc
                        .getExtendedAttributes()) {
                    if ("Destination".equals(extendedAttribute.getName())) { //$NON-NLS-1$
                        if ("CE".equals(extendedAttribute.getValue())) { //$NON-NLS-1$
                            CeDestFound = true;
                        } else {
                            otherDestFound = true;
                        }
                    }
                }

                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil
                                .getDisplayName((NamedElement) procOrIfc)
                        + " - CE destination environment should have been added to process/interface.", //$NON-NLS-1$
                        CeDestFound);

                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil
                                .getDisplayName((NamedElement) procOrIfc)
                        + " - non-CE destinations should have been removed from process/interface", //$NON-NLS-1$
                        !otherDestFound);

                /*
                 * Check that non-text formal parameters do not have
                 * Allowed-Values configuration
                 */
                for (FormalParameter param : ((FormalParametersContainer) procOrIfc)
                        .getFormalParameters()) {

                    if (param.getDataType() instanceof BasicType) {
                        if (!BasicTypeType.STRING_LITERAL.equals(
                                ((BasicType) param.getDataType()).getType())) {

                            assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName(
                                            (NamedElement) procOrIfc)
                                    + ":" + Xpdl2ModelUtil.getDisplayName(param) //$NON-NLS-1$
                                    + " - Non-text field Allowed-Values should have been removed", //$NON-NLS-1$
                                    Xpdl2ModelUtil.getOtherElement(param,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_InitialValues()) == null);

                        } else if ("Text Allowed Values param" //$NON-NLS-1$
                                .equals(Xpdl2ModelUtil.getDisplayName(param))) {
                            assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName(
                                            (NamedElement) procOrIfc)
                                    + ":" + Xpdl2ModelUtil.getDisplayName(param) //$NON-NLS-1$
                                    + " - Text field Allowed-Values should NOT have been removed", //$NON-NLS-1$
                                    Xpdl2ModelUtil.getOtherElement(param,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_InitialValues()) == null);

                        } 
                        
                        if (Xpdl2ModelUtil.getDisplayName(param)
                                .contains("nteger")) { //$NON-NLS-1$
                            /*
                             * Make sure formal parameters converted to FLOAT
                             * with 0 decimals.
                             */
                            assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName(
                                            (NamedElement) procOrIfc)
                                    + ":" + Xpdl2ModelUtil.getDisplayName(param) //$NON-NLS-1$
                                    + " - Integer formal parameters should have been changed to Decimals type", //$NON-NLS-1$
                                    BasicTypeType.FLOAT_LITERAL.equals(
                                            ((BasicType) param.getDataType())
                                                    .getType()));
                        }
                    }
                }

                /* Make sure data fields converted to FLOAT with 0 decimals. */
                if (procOrIfc instanceof DataFieldsContainer) {
                    for (DataField field : ((DataFieldsContainer) procOrIfc)
                            .getDataFields()) {

                        if (field.getDataType() instanceof BasicType) {
                            if (Xpdl2ModelUtil.getDisplayName(field)
                                    .contains("nteger")) { //$NON-NLS-1$
                                /*
                                 * Make sure formal parameters converted to
                                 * FLOAT with 0 decimals.
                                 */
                                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                        + Xpdl2ModelUtil.getDisplayName(
                                                (NamedElement) procOrIfc)
                                        + ":" //$NON-NLS-1$
                                        + Xpdl2ModelUtil.getDisplayName(field)
                                        + " - Integer data fields should have been changed to Decimals type", //$NON-NLS-1$
                                        BasicTypeType.FLOAT_LITERAL
                                                .equals(((BasicType) field
                                                        .getDataType())
                                                                .getType()));
                            }
                        }
                    }
                }
            }

            /*
             * Check that Publish as REST service configuration has been
             * removed.
             */
            for (Process process : pkg.getProcesses()) {
                /*
                 * xpdl:WorkflowProcess/xpdExt:RESTServices should have been
                 * removed.
                 */
                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName(process)
                        + " - xpdl:WorkflowProcess/xpdExt:RESTServices should have been removed", //$NON-NLS-1$
                        Xpdl2ModelUtil.getOtherElement(process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RESTServices()) == null);

                /* xpdExt:publishAsRESTService should have been removed. */
                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {
                    assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(process) + ":" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(activity)
                            + " - xpdl2:Activity/xpdExt:publishAsRESTService should have been removed", //$NON-NLS-1$
                            Xpdl2ModelUtil.getOtherAttribute(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PublishAsRestService()) == null);

                }

                /*
                 * Check that PROCESS REST service participants have had their
                 * AMX BPM specific info removed
                 */
                for (Participant participant : process.getParticipants()) {
                    ParticipantSharedResource psr =
                            (ParticipantSharedResource) Xpdl2ModelUtil
                                    .getOtherElement(participant,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource());

                    if (psr != null) {
                        assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(process) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(participant)
                                + " - REST/WEB system participant should have had xpdExt:ParticipantSharedResource removed", //$NON-NLS-1$
                                psr.getRestService() == null
                                        && psr.getWebService() == null);
                    }
                }

            }

            /*
             * Check that PACKAGE REST service participants have had their AMX
             * BPM specific info removed
             */
            for (Participant participant : pkg.getParticipants()) {
                ParticipantSharedResource psr =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());

                if (psr != null) {
                    assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(participant) + ":" //$NON-NLS-1$
                            + " - REST/WEB system participant should have had xpdExt:ParticipantSharedResource removed", //$NON-NLS-1$
                            psr.getRestService() == null
                                    && psr.getWebService() == null);
                }
            }

            /*
             * Check JavaScript DataMapping removal.
             */
            for (Iterator iterator = pkg.eAllContents(); iterator.hasNext();) {
                EObject eo = (EObject) iterator.next();

                /*
                 * * There should be NO DataMappings with
                 * ScriptGrammar="JavaScript" UNLESS they are within a
                 * ScriptDataMapper type element (which means that they are
                 * JavaScript data-mapping elements within a DataMapper grammar
                 * scenario
                 * 
                 * XPath grammar mappings should never exist
                 */
                if (eo instanceof DataMapping) {
                    Expression actual = ((DataMapping) eo).getActual();

                    if (actual != null) {
                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getProcess(eo))
                                + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getParentActivity(eo))
                                + " - XPath grammar mappings should have been removed should have been removed", //$NON-NLS-1$
                                "XPath".equals(actual.getScriptGrammar())); //$NON-NLS-1$

                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getProcess(eo))
                                + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getParentActivity(eo))
                                + " - JavaScript grammar mappings should have been removed should have been removed", //$NON-NLS-1$
                                ("JavaScript".equals(actual.getScriptGrammar()) //$NON-NLS-1$
                                        && Xpdl2ModelUtil.getAncestor(eo,
                                                ScriptDataMapper.class) == null));

                    }
                }

                /*
                 * Unmapped script mappings and script-mapping used to
                 * designated that the activity's mappings grammar is JavaScript
                 * or XPath (this approach not used for DataMapper) should all
                 * be removed.
                 */
                if (eo instanceof ScriptInformation
                        && eo.eContainer() instanceof Activity) {
                    Expression expression =
                            ((ScriptInformation) eo).getExpression();

                    if (expression != null) {
                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getProcess(eo))
                                + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getParentActivity(eo))
                                + " - JavaScript unmapped script-mapping (or mapping grammar designator) should have been removed", //$NON-NLS-1$
                                "JavaScript" //$NON-NLS-1$
                                        .equals(expression.getScriptGrammar()));

                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getProcess(eo))
                                + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(
                                        Xpdl2ModelUtil.getParentActivity(eo))
                                + " - XPath unmapped script-mapping (or mapping grammar designator) should have been removed", //$NON-NLS-1$
                                "XPath".equals(expression.getScriptGrammar())); //$NON-NLS-1$
                    }
                }
            }

        }
    }

    /**
     * Check general rules for XPDL migration
     * 
     * @param project
     */
    private void checkBomGeneralContent(IProject project) {
        /*
         * No project should have BOM models with XSD notation profile set.
         */
        Collection<IResource> bomFiles = SpecialFolderUtil
                .getAllDeepResourcesInSpecialFolderOfKind(project,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                        BOMResourcesPlugin.BOM_FILE_EXTENSION,
                        false);

        for (IResource bomFile : bomFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);

            Model model = (Model) wc.getRootElement();

            assertTrue("BOM file '" + bomFile.getName() //$NON-NLS-1$
                    + "' should have had the XsdNotationProfile removed", //$NON-NLS-1$
                    model.getAppliedProfile(
                            BOMUtils.XSD_NOTATION_PROFILE) == null);
        }

        /*
         * Ensure that all integer attributes and primitive types have been
         * changed to Decimal, Fixed Point with 0 decimals.
         */
        PrimitiveType integerType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet(),
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        for (IResource bomFile : bomFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);

            Model model = (Model) wc.getRootElement();

            /* Ensure that there are no Integer type properties/primitives */
            EList<Element> allOwnedElements = model.allOwnedElements();

            for (Element element : allOwnedElements) {
                if (element instanceof Property) {
                    Property property = (Property) element;

                    assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + property.getName()
                            + "' should have been converted from Integer to Decimal, FixedPoint with Zero decimals", //$NON-NLS-1$
                            !(integerType.equals(property.getType())));

                } else if (element instanceof PrimitiveType) {
                    PrimitiveType primitiveType = (PrimitiveType) element;

                    for (Element general : primitiveType.getGenerals()) {
                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName()
                                + "' should have been converted from Integer to Decimal, FixedPoint with Zero decimals", //$NON-NLS-1$
                                !(integerType.equals(general)));
                    }
                }
            }
        }
    }

    /**
     * Ensure that unwanted natures have been removed.
     * 
     * @param project
     */
    private void checkUnwantedNaturesAndBuildersRemoved(IProject project) {
        try {
            IProjectDescription description = project.getDescription();

            List<String> natures = Arrays.asList(description.getNatureIds());

            assertTrue(
                    "Nature 'com.tibco.xpd.wsdltobom.wsdlBomNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.wsdltobom.wsdlBomNature")); //$NON-NLS-1$
            assertTrue(
                    "Nature 'com.tibco.xpd.bom.xsdtransform.xsdNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains(
                            "com.tibco.xpd.bom.xsdtransform.xsdNature")); //$NON-NLS-1$
            assertTrue(
                    "Nature 'com.tibco.xpd.wsdlgen.wsdlGenNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.wsdlgen.wsdlGenNature")); //$NON-NLS-1$
            assertTrue(
                    "Nature 'com.tibco.xpd.n2.daa.cleanBpmFolderNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains(
                            "com.tibco.xpd.n2.daa.cleanBpmFolderNature")); //$NON-NLS-1$

            /*
             * Check builders have been removed.
             */
            assertTrue(
                    "Builder 'com.tibco.xpd.wsdltobom.wsdlToBomBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description,
                            "com.tibco.xpd.wsdltobom.wsdlToBomBuilder")); //$NON-NLS-1$
            assertTrue(
                    "Builder 'com.tibco.xpd.bom.xsdtransform.xsdBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description,
                            "com.tibco.xpd.bom.xsdtransform.xsdBuilder")); //$NON-NLS-1$
            assertTrue(
                    "Builder 'com.tibco.xpd.n2.daa.cleanBpmFolderBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description,
                            "com.tibco.xpd.n2.daa.cleanBpmFolderBuilder")); //$NON-NLS-1$
            assertTrue(
                    "Builder 'com.tibco.xpd.wsdlgen.wsdlGen' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description, "com.tibco.xpd.wsdlgen.wsdlGen")); //$NON-NLS-1$

        } catch (CoreException e) {
            e.printStackTrace();
            fail("Exception thrown during test execution: " + e.getMessage()); //$NON-NLS-1$
        }
    }

    /**
     * Ensure that the WSDL project asset as been removed.
     * 
     * @param projectConfig
     */
    private void checkUnwantedAssetConfigRemoved(ProjectConfig projectConfig) {
        boolean found = false;
        for (AssetType asset : projectConfig.getAssetTypes()) {
            if ("com.tibco.xpd.asset.wsdl".equals(asset.getId())) { //$NON-NLS-1$
                found = true;
            }
        }
        assertTrue("WSDL project asset should have been removed", !found); //$NON-NLS-1$
    }

    /**
     * Check for unwanted folders being left behind.
     * 
     * @param project
     * @param projectConfig
     */
    private void checkUnwantedSpecialFoldersRemoved(IProject project,
            ProjectConfig projectConfig) {
        /* Special folder configurations first. */
        assertTrue(
                "Project should have no 'compositeModulesOutput' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "compositeModulesOutput")); //$NON-NLS-1$

        assertTrue("Project should have no 'bom2xsd' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "bom2xsd")); //$NON-NLS-1$

        assertTrue("Project should have no 'deModulesOutput' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "deModulesOutput")); //$NON-NLS-1$

        assertTrue("Project should have no 'daaBinFolder' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "daaBinFolder")); //$NON-NLS-1$

        /* Check physical folders. */
        assertTrue("Project should have no '.bpm' folder", //$NON-NLS-1$
                !project.getFolder(".bpm").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.bom2Xsd' folder", //$NON-NLS-1$
                !project.getFolder(".bom2Xsd").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.processOut' folder", //$NON-NLS-1$
                !project.getFolder(".processOut").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.bomjars' folder", //$NON-NLS-1$
                !project.getFolder(".bomjars").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.deModulesOutput' folder", //$NON-NLS-1$
                !project.getFolder(".deModulesOutput").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.daabin' folder", //$NON-NLS-1$
                !project.getFolder(".daabin").exists()); //$NON-NLS-1$

        /*
         * ACE-457 Check that unwanted 'Generate Business Objects' special
         * folder was removed.
         */
        boolean found = false;

        for (SpecialFolder specialFolder : projectConfig.getSpecialFolders()
                .getFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
            if (BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE
                    .equals(specialFolder.getGenerated())) {
                found = true;
            }
        }

        if (!found) {
            /*
             * check the actual folder even if special folder removed from
             * .config
             */
            found = projectConfig.getProject()
                    .getFolder("Generated Business Objects").exists(); //$NON-NLS-1$
        }

        assertTrue(
                "'Generated Business Objects' folder has not been removed from Special Folders / file system", //$NON-NLS-1$
                !found);

        /*
         * Make sure all generated/user defined WSDL folders have been removed.
         */
        assertTrue(
                "All generated/user defined WSDL Special folders have not been removed", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "wsdl")); //$NON-NLS-1$

        assertTrue(
                "All generated/user defined WSDL Special folders have not been removed", //$NON-NLS-1$
                !project.getFolder("Generated Services") //$NON-NLS-1$
                        .exists()
                        && !project.getFolder("Services Descriptors").exists()); //$NON-NLS-1$
    }

    /**
     * Check the project has only the CE destination set.
     * 
     * @param project
     * @param projectDetails
     */
    private void checkProjectHasOnlyCEDestination(IProject project,
            ProjectDetails projectDetails) {
        assertTrue(
                project.getName()
                        + " project should have only one destination set", //$NON-NLS-1$
                projectDetails.getEnabledGlobalDestinationIds().size() == 1);

        assertTrue(
                project.getName() + " project does not have CE destination set", //$NON-NLS-1$
                XpdConsts.ACE_DESTINATION_NAME.equals(projectDetails
                        .getEnabledGlobalDestinationIds().get(0)));
    }

    /**
     * Cannot use projectConfig.getSpecialFolders() .getFoldersOfKind() BECAUSE
     * it relies on the special folder kind being a valid kin and that requires
     * a special older contribution.
     * 
     * If that contribution is for some build folder or other that isn't used in
     * SCE then the contribution won't be there and it will fail.
     * 
     * So we have to look by hand.
     * 
     * @param projectConfig
     * @param kind
     * 
     * @return <code>true</code>if the project has any configuration for the
     *         given special folder kind.
     */
    private boolean hasSpecialFolder(ProjectConfig projectConfig, String kind) {
        SpecialFolders specialFolders = projectConfig.getSpecialFolders();

        if (specialFolders != null) {
            for (SpecialFolder specialFolder : specialFolders.getFolders()) {
                if (kind.equals(specialFolder.getKind())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if a given builder is configured for the given project.
     * 
     * @param description
     * @param builderName
     * @return <code>true</code> if the project has the given builder configured
     *         on it
     */
    private boolean hasBuilder(IProjectDescription description,
            String builderName) {
        for (ICommand builder : description.getBuildSpec()) {
            if (builderName.equals(builder.getBuilderName())) {
                return true;
            }
        }
        return false;
    }

}
