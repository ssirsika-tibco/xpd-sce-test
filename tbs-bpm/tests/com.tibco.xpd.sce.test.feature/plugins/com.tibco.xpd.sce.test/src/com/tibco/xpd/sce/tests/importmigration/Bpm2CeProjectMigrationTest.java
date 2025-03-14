/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.custom.terminalstates.TerminalStateProperties;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
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
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * Test for All of DDP 2 (migration and conversion from AMX BPM proejcst to ACE projects
 * (http://confluence.tibco.com/pages/viewpage.action?pageId=171031408)
 * 
 * @author aallway
 * @since 22 Mar 2019
 */
@SuppressWarnings("nls")
public class Bpm2CeProjectMigrationTest extends TestCase {

    // @Test
    public void testLocalBOMDataProjectMigration() {
        String projectName = "ProjectMigrationTest_LocalBOM"; //$NON-NLS-1$
        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);

            assertTrue(projectName
                    + " project should have problem marker 'Business Object assets must be in a Business Data project.'", //$NON-NLS-1$
                    hasProblemMarker(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName),
                            "ace.bom.asset.must.be.in.biz.data")); //$NON-NLS-1$
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

	/**
	 * Test ACE-8497 v4.3.3 Studio BOM files are not migrated.
	 * 
	 * (originally discovered during ACE-6518 task implementation, so test added here)
	 */
	public void testV433BOMDataProjectMigration()
	{
		String projectName = "v433_DataWithServices"; //$NON-NLS-1$
		ProjectImporter projectImporter = null;

		try
		{
			projectImporter = doTestProject(projectName, 1);

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("v433_DataWithServices"); //$NON-NLS-1$

			assertFalse(projectName
					+ " project should import with no problem markers but has...\n" //$NON-NLS-1$
					+ TestUtil.getErrorProblemMarkerList(project, true) + "\n", //$NON-NLS-1$
					TestUtil.hasErrorProblemMarker(
							project, true, "v433_DataWithServices")); //$NON-NLS-1$
		}
		finally
		{
			if (projectImporter != null)
			{
				projectImporter.performDelete();
			}
		}
	}

	/**
	 * Sid ACE-8366 / ACE-8371 - tests for automatic addition of case-state attribute along with corresponding new
	 * enumeration class
	 * 
	 * @throws CoreException
	 */
	public void testBOMCaseStatesMigration() throws CoreException
	{
		String projectName = "v433CasesWithAndWithoutStates"; //$NON-NLS-1$
		ProjectImporter projectImporter = null;

		try
		{
			projectImporter = doTestProject(projectName, 1);

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

			/*
			 * We should have imported the project with only 1 error marker for the existing v4 case-state, which will
			 * not have a terminal state set.
			 */
			Collection<IMarker> errorMarkers = TestUtil.getErrorMarkers(project, true);

			IMarker errorMarker = errorMarkers.iterator().next();

			if (errorMarkers.size() != 1
					|| !"v433CasesWithAndWithoutStates.bom".equals(errorMarker.getResource().getName())
					|| !"casestate.no.terminal.states.issue".equals(errorMarker.getAttribute("issueId"))
					|| !"_Mx4EwEBjEe-sAeKL9g10AA".equals(errorMarker.getAttribute(IMarker.LOCATION)))
			{
				fail(projectName
						+ " should import with a single problem marker 'BPM  : A Case State must have at least one Terminal State set (v4CaseState)' on v4CaseState in v433CasesWithAndWithoutStates.bom, but has...\n"
						+ TestUtil.getErrorProblemMarkerList(project, true));
			}

			/*
			 * Check contents of migrated BOM model
			 */
			WorkingCopy workingCopy = WorkingCopyUtil
					.getWorkingCopy(project.getFile(new Path("Business Objects/v433CasesWithAndWithoutStates.bom")));
			Model model = (Model) ((BOMWorkingCopy) workingCopy).getRootElement();

			/*
			 * Check CaseWithoutStates class has been correctly migrated for case-state
			 */
			checkAutoAddedCaseState(model, "CaseWithoutStates");

			/*
			 * Check SecondCaseWithoutStates class has been correctly migrated for case-state
			 */
			checkAutoAddedCaseState(model, "SecondCaseWithoutStates");
		}
		finally
		{
			if (projectImporter != null)
			{
				projectImporter.performDelete();
			}
		}
	}

	/**
	 * Check case class should have had a case state attribute added and a corresponding enumeration with 2 literals
	 * "Running" and "Complete"
	 * 
	 * Asserts if not configured correctly
	 * 
	 * Sid ACE-8366 / ACE-8371
	 * 
	 * @param model
	 * @param caseClassName
	 */
	private void checkAutoAddedCaseState(Model model, String caseClassName)
	{
		Class caseClass = (Class) model.getPackagedElement(caseClassName);
		assertTrue(BOMGlobalDataUtils.isCaseClass(caseClass));

		// Must have property called 'caseState'
		Property caseStateProperty = null;
		for (Property property : caseClass.getOwnedAttributes())
		{
			if ("caseState".equals(property.getName()))
			{
				caseStateProperty = property;
				break;
			}
		}

		assertNotNull(caseStateProperty);

		// Must be configured as a case-state
		assertTrue(BOMGlobalDataUtils.isCaseState(caseStateProperty));

		// Must be mandatory
		assertEquals(1, caseStateProperty.getLower());
		assertEquals(1, caseStateProperty.getUpper());

		// Sid ACE-8588 Must have aggregation="composite"
		assertEquals(AggregationKind.COMPOSITE_LITERAL, caseStateProperty.getAggregation());

		// Enumeration type should have been created
		Type caseStateType = caseStateProperty.getType();
		assertTrue(caseStateType instanceof Enumeration);
		// With name based on original case class
		assertEquals(caseClass.getName() + "CaseStates", caseStateType.getName());

		// With 2 enumerations
		EList<EnumerationLiteral> caseStateLiterals = ((Enumeration) caseStateType).getOwnedLiterals();
		assertEquals(2, caseStateLiterals.size());
		assertEquals("RUNNING", caseStateLiterals.get(0).getName());
		assertEquals("COMPLETE", caseStateLiterals.get(1).getName());
		
		// And COMPLETE is set to terminal state
		Object terminalState = caseStateProperty.getValue(BOMGlobalDataUtils.getCaseStateStereotype(),
				TerminalStateProperties.BOM_TERMINAL_STATES);
		assertEquals(caseStateLiterals.get(1), ((EList) terminalState).get(0));
	}

    // @Test
    public void testOrgProjectMigration() {
        /*
         * Sid ACE-992 - Org project also as a version of 2.3.4.qualifier so will test that version is reset to <current
         * major version>.0.0.qualifier
         */
        String projectName = "ProjectMigrationTest_Org"; //$NON-NLS-1$

        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 2);
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    @Test
    public void testDataProjectMigration() {
        String projectName = "ProjectMigrationTest_Data"; //$NON-NLS-1$

        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);

            /*
             * Check that the specific integer properties/primtives we know about for definite have been changed.
             */
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            IFile testBomFile = project.getFile("Business Objects/NumberRefactoring.bom"); //$NON-NLS-1$
            WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(testBomFile);
            Model model = (Model) testWC.getRootElement();

            EList<Element> allOwnedElements = model.allOwnedElements();

            PrimitiveType decimalType = PrimitivesUtil.getStandardPrimitiveTypeByName(
                    XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet(),
                    PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

            for (Element element : allOwnedElements) {
                if (element instanceof Property && "integerAttribute" //$NON-NLS-1$
                        .equals(((Property) element).getName())) {
                    Property property = (Property) element;

                    assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + property.getName() + "' should have been converted from Integer to Decimal", //$NON-NLS-1$
                            (decimalType.equals(property.getType())));

                    Object facetPropertyValue = PrimitivesUtil.getFacetPropertyValue((PrimitiveType) property.getType(),
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                            property);

                    assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + property.getName() + "' should have been converted to Decimal Subtype FixedPoint", //$NON-NLS-1$
                            facetPropertyValue instanceof EnumerationLiteral
                                    && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                            .equals((((EnumerationLiteral) facetPropertyValue).getName())));

                    facetPropertyValue = PrimitivesUtil.getFacetPropertyValue((PrimitiveType) property.getType(),
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                            property);

                    assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + property.getName()
                            + "' should have been converted to Decimal, FixedPoint with ZERO decimal places", //$NON-NLS-1$
                            facetPropertyValue != null && new Integer(0).equals(facetPropertyValue));

                    /*
                     * In the 2nd class the integerAttribute has default values that should have been carried over to
                     * the decimals equivalent. (And should have had the integer constriants removed.
                     */
                    if ("NumberAttributes2withconstraints" //$NON-NLS-1$
                            .equals(property.getClass_().getName())) {
                        assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + property.getName()
                                + "' Should have carried over it's integerDefaultValue as decimalDefaultValue (123)", //$NON-NLS-1$
                                "123".equals( //$NON-NLS-1$
                                        PrimitivesUtil.getFacetPropertyValue((PrimitiveType) property.getType(),
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE,
                                                property)));

                        facetPropertyValue = PrimitivesUtil.getFacetPropertyValue( // $NON-NLS-1$
                                (PrimitiveType) property.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE,
                                property);
                        assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + property.getName() + "' Should have removed its integerDefaultValue", //$NON-NLS-1$
                                facetPropertyValue == null || "".equals(facetPropertyValue)); //$NON-NLS-1$

                        assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + property.getName()
                                + "' Should have carried over its integerLower as decimalLower (111)", //$NON-NLS-1$
                                "111".equals( //$NON-NLS-1$
                                        PrimitivesUtil.getFacetPropertyValue((PrimitiveType) property.getType(),
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                                property)));

                        facetPropertyValue = PrimitivesUtil.getFacetPropertyValue( // $NON-NLS-1$
                                (PrimitiveType) property.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                property);
                        assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + property.getName() + "' Should have removed its integerLower", //$NON-NLS-1$
                                facetPropertyValue == null || "".equals(facetPropertyValue)); //$NON-NLS-1$

                        assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + property.getName()
                                + "' Should have carried over its integerUpper as decimalUpper (999)", //$NON-NLS-1$
                                "999".equals( //$NON-NLS-1$
                                        PrimitivesUtil.getFacetPropertyValue((PrimitiveType) property.getType(),
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                                property)));

                        facetPropertyValue = PrimitivesUtil.getFacetPropertyValue( // $NON-NLS-1$
                                (PrimitiveType) property.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                property);
                        assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + property.getName() + "' Should have removed its integerUpper", //$NON-NLS-1$
                                facetPropertyValue == null || "".equals(facetPropertyValue)); //$NON-NLS-1$
                    }

                } else if (element instanceof PrimitiveType && ("MyIntegerPrimitive" //$NON-NLS-1$
                        .equals(((PrimitiveType) element).getName())
                        || "MyIntegerPrimitiveWithConstraints" //$NON-NLS-1$
                                .equals(((PrimitiveType) element).getName()))) {
                    PrimitiveType primitiveType = (PrimitiveType) element;

                    assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + primitiveType.getName() + "' should have been converted from Integer to Decimal", //$NON-NLS-1$
                            decimalType.equals(primitiveType.getGenerals().get(0)));

                    assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + primitiveType.getName() + "' should have been converted to Decimal Subtype FixedPoint", //$NON-NLS-1$
                            PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                    .equals(((EnumerationLiteral) PrimitivesUtil.getFacetPropertyValue(primitiveType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE)).getName()));

                    assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + primitiveType.getName()
                            + "' should have been converted to Decimal, FixedPoint with ZERO decimal places", //$NON-NLS-1$
                            new Integer(0).equals(PrimitivesUtil.getFacetPropertyValue(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES)));

                    /*
                     * In the 2nd class the integerAttribute has default values that should have been carried over to
                     * the decimals equivalent. (And should have had the integer constriants removed.
                     */
                    if ("MyIntegerPrimitiveWithConstraints" //$NON-NLS-1$
                            .equals(primitiveType.getName())) {
                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName()
                                + "' Should have carried over its integerDefaultValue as decimalDefaultValue (123)", //$NON-NLS-1$
                                "123".equals(PrimitivesUtil //$NON-NLS-1$
                                        .getFacetPropertyValue(primitiveType,
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE)));

                        Object facetPropertyValue = PrimitivesUtil.getFacetPropertyValue( // $NON-NLS-1$
                                primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE);
                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName() + "' Should have removed its integerDefaultValue", //$NON-NLS-1$
                                facetPropertyValue == null || "".equals(facetPropertyValue)); //$NON-NLS-1$

                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName()
                                + "' Should have carried over its integerLower as decimalLower (111)", //$NON-NLS-1$
                                "111".equals( //$NON-NLS-1$
                                        PrimitivesUtil.getFacetPropertyValue(primitiveType,
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER)));

                        facetPropertyValue = PrimitivesUtil.getFacetPropertyValue( // $NON-NLS-1$
                                primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);
                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName() + "' Should have removed its integerLower", //$NON-NLS-1$
                                facetPropertyValue == null || "".equals(facetPropertyValue)); //$NON-NLS-1$

                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName()
                                + "' Should have carried over its integerUpper as decimalUpper (999)", //$NON-NLS-1$
                                "999".equals( //$NON-NLS-1$
                                        PrimitivesUtil.getFacetPropertyValue(primitiveType,
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER)));

                        facetPropertyValue = PrimitivesUtil.getFacetPropertyValue( // $NON-NLS-1$
                                primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);
                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName() + "' Should have removed its integerUpper", //$NON-NLS-1$
                                facetPropertyValue == null || "".equals(facetPropertyValue)); //$NON-NLS-1$
                    }

                }
            }

            /* Clean up. */
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Some older BOM models (pre V4.0 Studio I think) did not set aggregation="composite" on standard attributes (non
     * association). This messes up bom->cdm & bom-js transforms and is inconsistent with attributes created in
     * current/later version.
     * 
     * So this function looks for ownedAttribute's that have aggregation=none and no association - these should be
     * compositions.
     */
    @Test
    public void testMissingCompositeAggregationDataProjectMigration() {
        String projectName = "MissingCompositeAggregationData"; //$NON-NLS-1$

        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);


            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            IFile testBomFile = project.getFile("Business Objects/MissingCompositeAggregation.bom"); //$NON-NLS-1$
            WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(testBomFile);
            Model model = (Model) testWC.getRootElement();

            EList<Element> allOwnedElements = model.allOwnedElements();

            /*
             * Customer should have 3 properties autoCaseIdnetifier1 (aggregation=composite already set), rating
             * (aggregation=composite NOT set, should be added) and Person (association link so should NOT have
             * aggregation=composite added).
             */
            Class clazz = (Class) model.getPackagedElement("Customer"); //$NON-NLS-1$
            assertNotNull("Expected to find a Customer class", clazz);

			/* Sid ACE-8371 Now expect 4 attributes not 3, because we now create missing case-state attribute */
			assertEquals("Customer class should have 4 attributes", 4, clazz.getAttributes().size());

            Property attr = getAttribute(clazz, "autoCaseIdentifier1");
            assertNotNull("Customer.autoCaseIdentifier1 property should exist", attr);
            assertEquals("Customer.autoCaseIdentifier1 should have kept aggregation=composite",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());

            attr = getAttribute(clazz, "person");
            assertNotNull("Customer.person property should exist", attr);
            assertEquals("Customer.person case link should NOT have had aggregation=composite added",
                    AggregationKind.NONE_LITERAL,
                    attr.getAggregation());

            attr = getAttribute(clazz, "rating");
            assertNotNull("Customer.rating property should exist", attr);
            assertEquals("Customer.rating should have had aggregation=composite added",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());

            /*
             * Person class should have 4 attributes autoCaseIdentifier1(aggregation=composite already set), name
             * (aggregation=composite NOT set, should be added), Customer (association link so should NOT have
             * aggregation=composite added) and Address (composite association)
             */
            clazz = (Class) model.getPackagedElement("Person"); //$NON-NLS-1$
            assertNotNull("Expected to find a Person class", clazz);

			/* Sid ACE-8371 Now expect 5 attributes not 3, because we now create missing case-state attribute */
			assertEquals("Person class should have 5 attributes", 5, clazz.getAttributes().size());

            attr = getAttribute(clazz, "autoCaseIdentifier1");
            assertNotNull("Person.autoCaseIdentifier1 property should exist", attr);
            assertEquals("Person.autoCaseIdentifier1 should have kept aggregation=composite",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());

            attr = getAttribute(clazz, "customer");
            assertNotNull("Person.customer property should exist", attr);
            assertEquals("Person.customer case link should NOT have had aggregation=composite added",
                    AggregationKind.NONE_LITERAL,
                    attr.getAggregation());

            attr = getAttribute(clazz, "name");
            assertNotNull("Person.name property should exist", attr);
            assertEquals("Person.name should have had aggregation=composite added",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());

            attr = getAttribute(clazz, "address");
            assertNotNull("Person.address property should exist", attr);
            assertEquals("Person.address should have kept aggregation=composite",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());
            
            /*
             * Address class should have 3 attributes line1, line2, postcode (all of which have aggregation=composite
             * NOT set, should be added)
             */
            clazz = (Class) model.getPackagedElement("Address"); //$NON-NLS-1$
            assertNotNull("Expected to find a Address class", clazz);

            assertEquals("Address class should have 3 attributes", 3, clazz.getAttributes().size());

            attr = getAttribute(clazz, "line1");
            assertNotNull("Address.line1 property should exist", attr);
            assertEquals("Address.line1 should have had aggregation=composite added",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());

            attr = getAttribute(clazz, "line2");
            assertNotNull("Address.line2 property should exist", attr);
            assertEquals("Address.line2 should have had aggregation=composite added",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());

            attr = getAttribute(clazz, "postcode");
            assertNotNull("Address.postcode property should exist", attr);
            assertEquals("Address.postcode should have had aggregation=composite added",
                    AggregationKind.COMPOSITE_LITERAL,
                    attr.getAggregation());

        } finally {
            /* Clean up. */
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * @param clazz
     * @param attrName
     * @return
     */
    private Property getAttribute(Class clazz, String attrName) {
        for (Property p : clazz.getAttributes()) {
            if (attrName.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    @Test
    public void testProcessProjectMigration() {
        String projectName = "ProjectMigrationTest_Process"; //$NON-NLS-1$

        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);

            /*
             * After checking all the standard stuff (like destination set, no non-datamapper related JavaScript
             * mappings, we'll do some extra checking on this particular Projects which has specific DataMapper mapping
             * grammar processes to test they still has all of the datamappings that we expect to be persisted thru
             * mgiration (i.e. that we have not deleted them accidentally).
             */
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            IFile dataMapperXpdl =
                    project.getFile("Process Packages/ProjectMigrationTest_DataMapper_DataMappings.xpdl"); //$NON-NLS-1$
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

            Collection<Activity> allActivitiesInProc = Xpdl2ModelUtil.getAllActivitiesInProc(dataMapperProcess);

            for (Activity activity : allActivitiesInProc) {
                String displayName = Xpdl2ModelUtil.getDisplayName(activity);

                EStructuralFeature mappingFeature1 = null;
                OtherElementsContainer mappingParent1 = null;
                EStructuralFeature mappingFeature2 = null;
                OtherElementsContainer mappingParent2 = null;

                if ("Send Task mappings".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 = ((Task) activity.getImplementation()).getTaskSend().getMessage();

                } else if ("Web-service task mappings".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 = ((Task) activity.getImplementation()).getTaskService().getMessageIn();
                    mappingFeature2 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings();
                    mappingParent2 = ((Task) activity.getImplementation()).getTaskService().getMessageOut();

                } else if ("Error Event Web-service error mappings" //$NON-NLS-1$
                        .equals(displayName)) {
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings();
                    mappingParent1 = ((CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(
                            ((ResultError) activity.getEvent().getEventTriggerTypeNode()),
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchErrorMappings())).getMessage();

                } else if ("Throw message mappings".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 =
                            ((TriggerResultMessage) activity.getEvent().getEventTriggerTypeNode()).getMessage();

                } else if ("Start Event User defined WSDL".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings();
                    mappingParent1 =
                            ((TriggerResultMessage) activity.getEvent().getEventTriggerTypeNode()).getMessage();

                } else if ("Reply To: Start Event User defined WSDL" //$NON-NLS-1$
                        .equals(displayName)) {
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 =
                            ((TriggerResultMessage) activity.getEvent().getEventTriggerTypeNode()).getMessage();

                } else if ("SubProcess mappings".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 = ((SubFlow) activity.getImplementation());
                    mappingFeature2 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings();
                    mappingParent2 = ((SubFlow) activity.getImplementation());

                } else if ("Error Event SubProcess Error mappings" //$NON-NLS-1$
                        .equals(displayName)) {
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings();
                    mappingParent1 = ((CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(
                            ((ResultError) activity.getEvent().getEventTriggerTypeNode()),
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchErrorMappings())).getMessage();

                } else if ("Error Event".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 = ((Message) Xpdl2ModelUtil.getOtherElement(
                            ((ResultError) activity.getEvent().getEventTriggerTypeNode()),
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_FaultMessage()));

                } else if ("Receive Task".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings();
                    mappingParent1 = ((Task) activity.getImplementation()).getTaskReceive().getMessage();

                } else if ("Reply To: Receive Task".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 = ((Task) activity.getImplementation()).getTaskSend().getMessage();

                } else if ("Error Event 2".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings();
                    mappingParent1 = ((Message) Xpdl2ModelUtil.getOtherElement(
                            ((ResultError) activity.getEvent().getEventTriggerTypeNode()),
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_FaultMessage()));

                } else if ("Method1 - PUT Resource1".equals(displayName)) { //$NON-NLS-1$
                    mappingFeature1 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_ScriptDataMapper();
                    mappingParent1 = ((Task) activity.getImplementation()).getTaskService().getMessageIn();
                    mappingFeature2 = XpdExtensionPackage.eINSTANCE.getDocumentRoot_ScriptDataMapper();
                    mappingParent2 = ((Task) activity.getImplementation()).getTaskService().getMessageOut();

                }

                if (mappingFeature1 != null || mappingFeature2 != null) {
                    numChecked++;
                    if (mappingFeature1 != null) {
                        Object mappingContainer1 = Xpdl2ModelUtil.getOtherElement(mappingParent1, mappingFeature1);

                        assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(activity)) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(activity)
                                + " - 1st DataMapper mappings container is missing or empty", //$NON-NLS-1$
                                (mappingContainer1 instanceof ScriptDataMapper)
                                        && !((ScriptDataMapper) mappingContainer1).getDataMappings().isEmpty());
                    }

                    if (mappingFeature2 != null) {
                        Object mappingContainer2 = Xpdl2ModelUtil.getOtherElement(mappingParent2, mappingFeature2);

                        assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(activity)) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(activity)
                                + " - 2nd DataMapper mappings container is missing or empty", //$NON-NLS-1$
                                (mappingContainer2 instanceof ScriptDataMapper)
                                        && !((ScriptDataMapper) mappingContainer2).getDataMappings().isEmpty());
                    }

                } else if ("Script Task".equals(displayName)) { //$NON-NLS-1$
                    numChecked++;
                    /*
                     * Script task is a special case as it the Script DataMapper is contained in an Expression.
                     */
                    ScriptDataMapper scriptDataMapper = DataMapperUtils.getExistingScriptDataMapper(
                            ((Task) activity.getImplementation()).getTaskScript().getScript());

                    assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(activity)) + ":" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(activity)
                            + " - Script Task DataMapper mappings are missing or empty", //$NON-NLS-1$
                            (scriptDataMapper != null) && !scriptDataMapper.getDataMappings().isEmpty());

                } else if ("Task Script".equals(displayName)) { //$NON-NLS-1$
                    numChecked++;
                    /*
                     * Task scripts are a special case as it the Script DataMapper is contained in an Expression.
                     */
                    Audit audit = (Audit) Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit());

                    ScriptDataMapper scriptDataMapper =
                            DataMapperUtils.getExistingScriptDataMapper(audit.getAuditEvent().get(0).getInformation());

                    assertTrue(dataMapperXpdl.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(activity)) + ":" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(activity)
                            + " - Task Initiate Script DataMapper mappings are missing or empty", //$NON-NLS-1$
                            (scriptDataMapper != null) && !scriptDataMapper.getDataMappings().isEmpty());

                }
            }

            assertTrue("Number of dataMapper mapping scenarios was " + numChecked //$NON-NLS-1$
                    + " but expected 15", //$NON-NLS-1$
                    numChecked == 15);

            /*
             * Sid ACE-2024 Check that web-service related content has been removed.
             */
            IFile receiveTaskXpdl = project.getFile("Process Packages/ProjectMigrationTest_ReceiveTask.xpdl"); //$NON-NLS-1$
            testWC = WorkingCopyUtil.getWorkingCopy(receiveTaskXpdl);
            pkg = (Package) testWC.getRootElement();
            Process process = pkg.getProcess("_A3Sika1dEemL6f5sRm58aQ"); //$NON-NLS-1$

            Activity receiveActivity = process.getActivity("_Gz1Zga1dEemL6f5sRm58aQ"); //$NON-NLS-1$

			assertNotNull("xpdExt:CorrelationTimeout should have been retained from receivetask xpdl2:Activity", //$NON-NLS-1$
                    Xpdl2ModelUtil.getOtherElement(receiveActivity,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelationTimeout()));
			
			
			ConstantPeriod constantP = ((ConstantPeriod) Xpdl2ModelUtil.getOtherElement(receiveActivity,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelationTimeout()));


			assertEquals(
					"xpdExt:CorrelationTimeout attribute should have days set to 1", //$NON-NLS-1$
					"1", // $NON-NLS-1$
					constantP.getDays().toString());

			assertEquals("xpdExt:CorrelationTimeout attribute should have hours set to 2", //$NON-NLS-1$
					"2", // $NON-NLS-1$
					constantP.getHours().toString());

			assertEquals("xpdExt:CorrelationTimeout attribute should have minutes set to 3", //$NON-NLS-1$
					"3", // $NON-NLS-1$
					constantP.getMinutes().toString());

			assertEquals("xpdExt:CorrelationTimeout attribute should have sec set to 4", //$NON-NLS-1$
					"4", // $NON-NLS-1$
					constantP.getSeconds().toString());

            TaskReceive taskReceive = ((Task) receiveActivity.getImplementation()).getTaskReceive();

            assertEquals(
                    "xpdExt:ImplementationType attribute should have been set to 'Unspecified' on xpdl2:TaskReceive.", //$NON-NLS-1$
                    "Unspecified", //$NON-NLS-1$
                    Xpdl2ModelUtil.getOtherAttribute(taskReceive,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_ImplementationType()));

            assertEquals("Implementation attribute should have been set to 'Unspecified' on xpdl2:TaskReceive.", //$NON-NLS-1$
                    "Unspecified", //$NON-NLS-1$
                    taskReceive.getImplementation().getLiteral());

            assertNull("xpdExt:Generated attribute should have been removed from xpdl2:TaskReceive.", //$NON-NLS-1$
                    Xpdl2ModelUtil.getOtherAttribute(taskReceive,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_Generated()));

			assertNotNull(
					"xpdExt:CorrelateImmediately attribute should have been retained from xpdl2:TaskReceive. If already set", //$NON-NLS-1$
                    Xpdl2ModelUtil.getOtherAttribute(taskReceive,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelateImmediately()));

            assertNull("xpdl2:WebServiceOperation attribute should have been removed from xpdl2:TaskReceive.", //$NON-NLS-1$
                    taskReceive.getWebServiceOperation());

            assertNull("xpdExt:PortTypeOperation attribute should have been removed from xpdl2:TaskReceive.", //$NON-NLS-1$
                    Xpdl2ModelUtil.getOtherElement(taskReceive,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_PortTypeOperation()));

            assertTrue("DataMappings should have been removed from xpdl2:TaskReceive/xpdl2:Message.", //$NON-NLS-1$
                    taskReceive.getMessage() == null || taskReceive.getMessage().getDataMappings().isEmpty());

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }

    }

    @Test
    public void testMaaProjectMigration() {
        String projectName = "ProjectMigrationTest_maa"; //$NON-NLS-1$

        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);

            assertTrue(projectName
                    + " project should have problem marker 'Organisation assets must be in their own project (not mixed with other asset types such as Process and Business Object etc).'", //$NON-NLS-1$
                    hasProblemMarker(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName),
                            "ace.org.asset.must.be.alone")); //$NON-NLS-1$

            assertTrue(projectName
                    + " project should have problem marker 'Business Object assets must be alone in their own Business Data project (not mixed with other asset types such as Process and Organisation etc).'", //$NON-NLS-1$
                    hasProblemMarker(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName),
                            "ace.bom.asset.must.be.alone")); //$NON-NLS-1$

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    @Test
    public void testProcessAndDataBuildFolderRemovalMigration() {
        String projectName = "ProjectMigrationTest_ProcessBuildFolders"; //$NON-NLS-1$
        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    @Test
    public void testOrgBuildFolderRemovalMigration() {
        String projectName = "ProjectMigrationTest_OrgBuildFolders"; //$NON-NLS-1$
        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    @Test
    public void testGenBomNotMovedMigration1_UserBomAlreadyExists() {
        String projectName = "ProjectMigrationTest_GenAndUserBOMData"; //$NON-NLS-1$
        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            /*
			 * Sid ACE-8350 (NOW) Check that the expected BOMs are in SAME location and not moved to user-defined
			 * location.
			 */
			IFile genBomFile = project.getFile("Generated Business Objects/org.example.NewWSDLFile.bom"); //$NON-NLS-1$
			assertTrue(
					"Generated BOM 'Generated Business Objects/org.example.NewWSDLFile.bom' should be left in generated BOM folder", //$NON-NLS-1$
					genBomFile.exists());

			IFile genBomFile1 = project.getFile("Generated Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom"); //$NON-NLS-1$
			assertTrue(
					"Generated BOM 'Generated Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom' should be left in generated BOM folder", //$NON-NLS-1$
					genBomFile1.exists());

			/*
			 * Sid ACE-8358 Check that generated business objects special folder has had 'generated' config unset, so
			 * that it's now treated as a user defined boms folder.
			 */
			assertFalse(
					"4.x Generated BOM 'Generated Business Objects' special folder should not be tagged as 'generated'", //$NON-NLS-1$
					ProjectUtil.isGeneratedProject(projectName));

            IFile newWSDLFilebom = project.getFile("Business Objects/org.example.NewWSDLFile.bom"); //$NON-NLS-1$
            assertTrue(
					"Generated BOM 'Business Objects/org.example.NewWSDLFile.bom' should have not been moved to user defined BOM folder", //$NON-NLS-1$
					!newWSDLFilebom.exists());

            IFile subNewWsdlFile1bom = project.getFile("Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom"); //$NON-NLS-1$
            assertTrue(
					"Generated BOM 'Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom' should not have been moved to user defined BOM folder", //$NON-NLS-1$
					!subNewWsdlFile1bom.exists());

            /*
             * Check that moved boms have had the 'global data capability' added
             */
            assertTrue(
					"Generated BOM 'Generated Business Objects/org.example.NewWSDLFile.bom' should have had global data capability added", //$NON-NLS-1$
					BOMGlobalDataUtils.isGlobalDataBOM(genBomFile));

            assertTrue(
					"Generated BOM 'Generated Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom' should have had global data capability added", //$NON-NLS-1$
					BOMGlobalDataUtils.isGlobalDataBOM(genBomFile1));

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

	/**
	 * Sid ACE-8350 As we no longer move generated BOMs to user defined BOMs folder, there's nothing really to test for
	 * 'If user defined BOM folders doesn't exist' use case
	 */
	// public void testGenBomMoveMigration1_UserBomNotExist()

    @Test
    public void testPresentationChannelMigration() {
        String projectName = "ProjectMigrationTest_ChannelConfig"; //$NON-NLS-1$

        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            PresentationManager pm = PresentationManager.getInstance();

            Channels channelContainer = pm.getChannels(project);
            if (channelContainer != null && pm.isProjectChannels(channelContainer, project)) {

                for (Channel channel : channelContainer.getChannels()) {

                    for (TypeAssociation typeAssociation : channel.getTypeAssociations()) {

                        ChannelType channelType = typeAssociation.getChannelType();
                        if (channelType != null) {
                            assertFalse(projectName + " should have had 'Workspace General Interface' channel removed", //$NON-NLS-1$
                                    PresentationManager.GI_GI_PULL.equals(channelType.getId()));
                            assertFalse(projectName + " should have had 'Workspace Google Web Toolkit' channel removed", //$NON-NLS-1$
                                    PresentationManager.GI_GWT_PULL.equals(channelType.getId()));
                            assertFalse(projectName + " should have had 'Workspace Email' channel removed", //$NON-NLS-1$
                                    PresentationManager.EMAIL_GI_PUSH.equals(channelType.getId()));
                        }
                    }
                }
            }

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }

    }

    /**
     * Test that Global classes are converted to Local classes.
     */
    @Test
    public void testGlobalClassDataProjectMigration() {
        String projectName = "ProjectMigrationTest_GlobalClass"; //$NON-NLS-1$
        ProjectImporter projectImporter = null;

        try {
            projectImporter = doTestProject(projectName, 1);

            // Check that we have a Local class
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            Collection<IResource> bomFiles = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(project,
                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                    BOMResourcesPlugin.BOM_FILE_EXTENSION,
                    false);

            // Should only be one
            assertEquals(1, bomFiles.size());

            // Get the model
            IResource bomFile = bomFiles.iterator().next();
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            Model model = (Model) wc.getRootElement();

            // Check that we don't have any global classes
            for (Element element : model.allOwnedElements()) {
                if (element instanceof Class) {
                    Class clazz = (Class) element;
                    assertFalse(BOMGlobalDataUtils.isGlobalClass(clazz));
                }
            }

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
	 * ACE-7093: Test that Generalizations are removed after coping derived attributes.
	 */
	@SuppressWarnings("nls")
	@Test
    public void testGeneralizationProjectMigration() {
		/*
		 * Test imports two projects, 'ProjectMigrationTest_BaseGeneralization' and
		 * 'ProjectMigrationTest_Generalization'. There exists generalization relationship between BOM class
		 * (LowerGeneralClass) present in 'ProjectMigrationTest_Generalization' project with
		 * 'ProjectMigrationTest_BaseGeneralization'. This is to check the cross-projects BOM generalization handling. 
		 */ 
		
		String baseProjectName = "ProjectMigrationTest_BaseGeneralization"; //$NON-NLS-1$
        String projectName = "ProjectMigrationTest_Generalization"; //$NON-NLS-1$
        ProjectImporter baseProjectImporter = null;
        ProjectImporter projectImporter = null;

        try {
        	baseProjectImporter = doTestProject(baseProjectName, 1);
            projectImporter = doTestProject(projectName, 1);

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            Collection<IResource> bomFiles = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(project,
                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                    BOMResourcesPlugin.BOM_FILE_EXTENSION,
                    false);

            // Should only be one
            assertEquals(1, bomFiles.size());

            // Get the model
            IResource bomFile = bomFiles.iterator().next();
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            Model model = (Model) wc.getRootElement();

			HashMap<String, List<String>> expectedClassToAttributesMap = buildExpectedClassMap();
			int classCount = 0;
            for (Element element : model.allOwnedElements()) {
                if (element instanceof Class) {
                    Class clazz = (Class) element;
					String className = clazz.getName();
					assertTrue("Class '" + className + "' should not have any generalization.",
							clazz.getGeneralizations().isEmpty());
					EList<Property> allAttributes = clazz.allAttributes();
					List<String> expectedAttributeList = expectedClassToAttributesMap.get(className);
					assertNotNull("Not expecting class '" + className + "' in the input project",
							expectedAttributeList);
					assertEquals(
							"Number of attributes for class '" + className
									+ "' does not match with expected attributes.",
							expectedAttributeList.size(), clazz.allAttributes().size());

					for (String attributeName : expectedAttributeList)
					{
						assertTrue(
								"Missing expected attribute '" + attributeName + "' in the migrated class '"
										+ className + "'.",
								allAttributes.stream().anyMatch(p -> p.getName().equals(attributeName)));
					}

					/* For few sample attributes, check that stereotypes are getting copied correctly. */
					if (className.equals("MainClass"))
					{
						Property attribute = allAttributes.stream().filter(p -> p.getName().equals("sd1aInteger")) //$NON-NLS-1$
								.findAny().orElse(null);

						assertEquals("sd1a Integer", PrimitivesUtil.getDisplayLabel(attribute));
						Stereotype typeStereotype = attribute												//NOSONAR
								.getAppliedStereotype("PrimitiveTypeFacets::RestrictedType");
						assertNotNull("type Stereotype should be present ", typeStereotype);
						
						attribute = allAttributes.stream().filter(p -> p.getName().equals("s1aFixedPointNumber")) //$NON-NLS-1$
								.findAny().orElse(null);

						assertEquals("s1a Fixed Point Number", PrimitivesUtil.getDisplayLabel(attribute));
						typeStereotype = attribute															//NOSONAR
								.getAppliedStereotype("PrimitiveTypeFacets::RestrictedType");
						assertNotNull("type Stereotype should be present ", typeStereotype);
						assertEquals("123", attribute.getValue(typeStereotype, "decimalDefaultValue"));
					} 
					
					if (className.equals("SuperClass1"))
					{
						Property attribute = allAttributes.stream().filter(p -> p.getName().equals("sd1aNonDiagramComposedAttribute")) //$NON-NLS-1$
								.findAny().orElse(null);

						assertEquals("sd1a Non-Diagram Composed Attribute", PrimitivesUtil.getDisplayLabel(attribute));
					}
					
					if (className.equals("IncludedInMainClass"))
					{
						Property attribute = allAttributes.stream().filter(p -> p.getName().equals("sdciwdrInteger")) //$NON-NLS-1$
								.findAny().orElse(null);

						assertEquals("sdciwdr Integer", PrimitivesUtil.getDisplayLabel(attribute));
						Stereotype typeStereotype = attribute												//NOSONAR
								.getAppliedStereotype("PrimitiveTypeFacets::RestrictedType");
						assertNotNull("type Stereotype should be present ", typeStereotype);
					} 
					classCount++;
				}
            }

			assertEquals(expectedClassToAttributesMap.keySet().size(), classCount);

        } finally {
			
			if (baseProjectImporter != null)
			{
				baseProjectImporter.performDelete();
			}
			if (projectImporter != null)
			{
				projectImporter.performDelete();
			}
			 
        }
    }

	/**
	 * Sid ACE-8369 Added test for case class migration (initially case-id's change to mandatory, later other
	 * migrations)...
	 * 
	 * <li>All case-id's should be changed to mandatory if not already</li>
	 */
	public void testCaseClassMigration()
	{
		String projectName = "ProjectMigrationTest_CaseData"; //$NON-NLS-1$

		ProjectImporter projectImporter = null;

		try
		{
			projectImporter = doTestProject(projectName, 1);

			/*
			 * General BOM's test will have checked that all case-id's are switched to mandatory, we should also check
			 * what normal optional attributes are NOT set to mandatory.
			 */
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

			WorkingCopy bomWC = WorkingCopyUtil
					.getWorkingCopy(project.getFile(new Path("Business Objects/ProjectMigrationTest_CaseData.bom")));

			Model bomModel = (Model) bomWC.getRootElement();

			Property optionalAttribute = getBomPropertyByName(bomModel, "optionalAttribute");
			assertTrue(
					"Property 'optionalAttribute' in ProjectMigrationTest_CaseData.bom should have been left as optional",
					(optionalAttribute.getLower() == 0 && optionalAttribute.getUpper() == 1));

			Property optionalArrayAttribute = getBomPropertyByName(bomModel, "optionalArrayAttribute");
			assertTrue(
					"Property 'optionalArrayAttribute' in ProjectMigrationTest_CaseData.bom should have been left as optional",
					(optionalArrayAttribute.getLower() == 0 && optionalArrayAttribute.getUpper() == -1));

		}
		finally
		{
			if (projectImporter != null)
			{
				projectImporter.performDelete();
			}
		}
	}

	/**
	 * Sid ACE-8369 Added handy getter for BOM properties.
	 * 
	 * @param bomModel
	 * @param propertyName
	 * 
	 * @return BOM property with given name from any class in BOM model.
	 */
	private Property getBomPropertyByName(Model bomModel, String propertyName)
	{
		Property property = null;

		EList<Element> allOwnedElements = bomModel.allOwnedElements();

		for (Element element : allOwnedElements)
		{
			if (element instanceof Property && propertyName.equals(((Property) element).getName()))
			{
				property = (Property) element;
				break;
			}
		}
		return property;
	}
    
	/**
	 * @return the Map of expected class<->attributes collection.
	 * 
	 */
	@SuppressWarnings("nls")
	private HashMap<String, List<String>> buildExpectedClassMap()
	{
		HashMap<String, List<String>> classToAttributeMap = new HashMap<>();
		classToAttributeMap.put("GlobalClass", Arrays.asList("sgc1", "sgc2", "gc2", "gc1"));
		classToAttributeMap.put("IncludedInMainClass",
				Arrays.asList("scc1", "sdciwdrInteger", "scc2", "sdciwdrText", "imc2", "imc1"));
		classToAttributeMap.put("IncludedInSuperClass", Arrays.asList("isc2", "isc1"));
		classToAttributeMap.put("IncludedInSuperDuperClass", Arrays.asList("isdc2", "isdc1"));
		classToAttributeMap.put("MainClass",
				Arrays.asList("maText", "maFloatingPointNumber", "childComposedFromMainClass",
						"childComposedFromSuperClass", "s1aFixedPointNumber", "s2aDateTimeTZ", "sd1aInteger",
						"childArrayComposedFromSuperDuperClass", "sd1aNonDiagramComposedAttribute"));
		classToAttributeMap.put("SuperChildClass", Arrays.asList("sdciwdrText", "sdciwdrInteger", "scc2", "scc1"));
		classToAttributeMap.put("SuperClass1",
				Arrays.asList("sd1aNonDiagramComposedAttribute", "childArrayComposedFromSuperDuperClass", "sd1aInteger",
						"childComposedFromSuperClass", "s2aDateTimeTZ", "s1aFixedPointNumber"));
		classToAttributeMap.put("SuperDuperChildInheritedwithoutDiagramRelationship",
				Arrays.asList("sdciwdrInteger", "sdciwdrText"));
		classToAttributeMap.put("SuperDuperClass1",
				Arrays.asList("sd1aNonDiagramComposedAttribute", "childArrayComposedFromSuperDuperClass",
						"sd1aInteger"));
		classToAttributeMap.put("SuperGlobalClass", Arrays.asList("sgc1", "sgc2"));
		classToAttributeMap.put("CaseClass",
				Arrays.asList("caseClass2", "summary", "globalChild", "caseState", "caseID"));
		/* Sid ACE-8371 Now expect caseState attribute to be added automatically. */
		classToAttributeMap.put("CaseClass2", Arrays.asList("caseClass", "caseIdentifier", "caseState"));
		classToAttributeMap.put("LowerGeneralClass", Arrays.asList("lowerGeneralAtr", "middleBOMAtr", "baseBOMAtr"));
		return classToAttributeMap;
	}

	/**
	 * Test the given project.
	 * 
	 * @param projectName
	 * @param expectedMajorVersion
	 *            the expected major version after import.
	 */
    private ProjectImporter doTestProject(String projectName, int expectedMajorVersion) {
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/ImportMigrationTests/" + projectName + "/" }, //$NON-NLS-1$ //$NON-NLS-2$
                new String[] { projectName });

        assertTrue("Failed to load projects from \"resources/ImportMigrationTests/ImportMigrationTests/" //$NON-NLS-1$
                + projectName + "\"", //$NON-NLS-1$
                projectImporter != null);

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName); // $NON-NLS-1$
        assertTrue(projectName + " project does not exist", //$NON-NLS-1$
                project.isAccessible());

        TestUtil.buildAndWait();

        ProjectConfig projectConfig = XpdResourcesPlugin.getDefault().getProjectConfig(project);

        ProjectDetails projectDetails = projectConfig.getProjectDetails();

        /*
         * Check version has been reset to <current major version>.0.0.qualifier
         */
        String expectedVersion = String.format("%d.0.0.qualifier", expectedMajorVersion); //$NON-NLS-1$

        assertEquals(projectName + " project has not had its version set to " + expectedVersion, //$NON-NLS-1$
                expectedVersion,
                projectDetails.getVersion());

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
         * All processes and process interfaces should have the CE destination set.
         * 
         * This also has the advantage of checking that the XPDL migration has created valid format xpdl files with
         * things like simulation/eaijava namespace elements removed.
         * 
         */
        Collection<IResource> xpdlFiles = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(project,
                Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                Xpdl2ResourcesConsts.XPDL_EXTENSION,
                false);

		Map<String, String> expectedSharedResourceNamesMap = getExpectedSharedResourceNameList();

        for (IResource xpdlFile : xpdlFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);

            assertTrue("File '" + xpdlFile.getName() //$NON-NLS-1$
                    + "' was not migrated successfully (cannot load working copy).", //$NON-NLS-1$
                    wc != null && wc.getRootElement() != null);

            Package pkg = (Package) wc.getRootElement();

            /* Sid ACE-1354 Package version should be removed on migration. */
            assertTrue("Process package version has been removed.", //$NON-NLS-1$
                    pkg.getRedefinableHeader() == null || pkg.getRedefinableHeader().getVersion() == null);

            Collection<ExtendedAttributesContainer> procsAndIfcs = new ArrayList<>();
            procsAndIfcs.addAll(pkg.getProcesses());

            ProcessInterfaces processInterfaces = ProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                procsAndIfcs.addAll(processInterfaces.getProcessInterface());
            }

            for (ExtendedAttributesContainer procOrIfc : procsAndIfcs) {
                boolean CeDestFound = false;
                boolean otherDestFound = false;

                for (ExtendedAttribute extendedAttribute : procOrIfc.getExtendedAttributes()) {
                    if ("Destination".equals(extendedAttribute.getName())) { //$NON-NLS-1$
                        if ("CE".equals(extendedAttribute.getValue())) { //$NON-NLS-1$
                            CeDestFound = true;
                        } else {
                            otherDestFound = true;
                        }
                    }
                }

                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName((NamedElement) procOrIfc)
                        + " - CE destination environment should have been added to process/interface.", //$NON-NLS-1$
                        CeDestFound);

                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName((NamedElement) procOrIfc)
                        + " - non-CE destinations should have been removed from process/interface", //$NON-NLS-1$
                        !otherDestFound);

                /*
                 * Check that non-text formal parameters do not have Allowed-Values configuration
                 */
                for (FormalParameter param : ((FormalParametersContainer) procOrIfc).getFormalParameters()) {

                    if (param.getDataType() instanceof BasicType) {
                        if (!BasicTypeType.STRING_LITERAL.equals(((BasicType) param.getDataType()).getType())) {

                            assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName((NamedElement) procOrIfc) + ":" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName(param)
                                    + " - Non-text field Allowed-Values should have been removed", //$NON-NLS-1$
                                    Xpdl2ModelUtil.getOtherElement(param,
                                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_InitialValues()) == null);

                        } else if ("Text Allowed Values param" //$NON-NLS-1$
                                .equals(Xpdl2ModelUtil.getDisplayName(param))) {
                            assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName((NamedElement) procOrIfc) + ":" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName(param)
                                    + " - Text field Allowed-Values should NOT have been removed", //$NON-NLS-1$
                                    Xpdl2ModelUtil.getOtherElement(param,
                                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_InitialValues()) == null);

                        }

                        if (Xpdl2ModelUtil.getDisplayName(param).contains("nteger")) { //$NON-NLS-1$
                            /*
                             * Make sure formal parameters converted to FLOAT with 0 decimals.
                             */
                            assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName((NamedElement) procOrIfc) + ":" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName(param)
                                    + " - Integer formal parameters should have been changed to Decimals type", //$NON-NLS-1$
                                    BasicTypeType.FLOAT_LITERAL.equals(((BasicType) param.getDataType()).getType()));
                        }
                    }
                }

                /* Make sure data fields converted to FLOAT with 0 decimals. */
                if (procOrIfc instanceof DataFieldsContainer) {
                    for (DataField field : ((DataFieldsContainer) procOrIfc).getDataFields()) {

                        if (field.getDataType() instanceof BasicType) {
                            if (Xpdl2ModelUtil.getDisplayName(field).contains("nteger")) { //$NON-NLS-1$
                                /*
                                 * Make sure formal parameters converted to FLOAT with 0 decimals.
                                 */
                                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                        + Xpdl2ModelUtil.getDisplayName((NamedElement) procOrIfc) + ":" //$NON-NLS-1$
                                        + Xpdl2ModelUtil.getDisplayName(field)
                                        + " - Integer data fields should have been changed to Decimals type", //$NON-NLS-1$
                                        BasicTypeType.FLOAT_LITERAL
                                                .equals(((BasicType) field.getDataType()).getType()));
                            }
                        }
                    }
                }
            }

            /*
             * Check that Publish as REST service configuration has been removed.
             */
            for (Process process : pkg.getProcesses()) {
                /*
                 * xpdl:WorkflowProcess/xpdExt:RESTServices should have been removed.
                 */
                assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName(process)
                        + " - xpdl:WorkflowProcess/xpdExt:RESTServices should have been removed", //$NON-NLS-1$
                        Xpdl2ModelUtil.getOtherElement(process,
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_RESTServices()) == null);

                /* xpdExt:publishAsRESTService should have been removed. */
                for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
                    assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(process) + ":" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(activity)
                            + " - xpdl2:Activity/xpdExt:publishAsRESTService should have been removed", //$NON-NLS-1$
                            Xpdl2ModelUtil.getOtherAttribute(activity,
                                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_PublishAsRestService()) == null);

                }

                /*
                 * Check that PROCESS REST service participants have had their AMX BPM specific info removed
                 */
                for (Participant participant : process.getParticipants()) {
                    ParticipantSharedResource psr =
                            (ParticipantSharedResource) Xpdl2ModelUtil.getOtherElement(participant,
                                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_ParticipantSharedResource());

                    if (psr != null) {
						/**
						 * ACE-7329 : On migration from 4.x - a REST service system participant's shared resource name
						 * must be preserved.
						 * 
						 * ACE-7188 : On migration from 4.x a SOAP-service consumer participant configured as SOAP over
						 * HTTP the shared resource name must be preserved
						 */
						if (expectedSharedResourceNamesMap.get(participant.getName()) != null)
						{
							assertEquals(expectedSharedResourceNamesMap.get(participant.getName()),
									psr.getRestService().getResourceName());
						}

                        assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(process) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(participant)
                                + " - REST/WEB/JDBC system participant should have had xpdExt:ParticipantSharedResource removed", //$NON-NLS-1$
                                psr.getWebService() == null && psr.getJdbc() == null);

                        /*
                         * Sid ACE-479 We now only remove the content of xpdExt:RestService not the whole element so
                         * that we preserve it's type but not it's config
                         */
                        if (psr.getRestService() != null) {
                            assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                    + Xpdl2ModelUtil.getDisplayName(participant) + ":" //$NON-NLS-1$
                                    + " - REST system participant should have had all content from xpdExt:ParticipantSharedResource/xpdExt:RestService removed", //$NON-NLS-1$
                                    psr.getRestService().getSecurityPolicy().isEmpty());
                        }
                    }
                }

            }

            /*
             * Check that PACKAGE REST service participants have had their AMX BPM specific info removed
             */
            for (Participant participant : pkg.getParticipants()) {
                ParticipantSharedResource psr = (ParticipantSharedResource) Xpdl2ModelUtil.getOtherElement(participant,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_ParticipantSharedResource());

                if (psr != null) {

					/**
					 * ACE-7329 : On migration from 4.x a REST service system participant's shared resource name must be
					 * preserved.
					 * 
					 * ACE-7188 : On migration from 4.x a SOAP-service consumer participant configured as SOAP over HTTP
					 * the shared resource name must be preserved
					 */
					if (expectedSharedResourceNamesMap.get(participant.getName()) != null)
					{
						assertEquals(expectedSharedResourceNamesMap.get(participant.getName()),
								psr.getRestService().getResourceName());
					}

                    assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getDisplayName(participant) + ":" //$NON-NLS-1$
                            + " - REST/WEB/JDBC system participant should have had xpdExt:ParticipantSharedResource removed", //$NON-NLS-1$
                            psr.getWebService() == null);

                    /*
                     * Sid ACE-7117 We now only remove the JdbcPofileName from xpdExt:Jdbc not the whole element.
                     */
                    if (psr.getJdbc() != null) {
                        assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(participant) + ":" //$NON-NLS-1$
                                + " - JDBC system participant should have had xpdExt:ParticipantSharedResource/xpdExt:Jdbc/JdbcProfileName attribute removed", //$NON-NLS-1$
                                psr.getJdbc().getJdbcProfileName() == null);
                    }

                    /*
                     * Sid ACE-479 We now only remove the content of xpdExt:RestService not the whole element so that we
                     * preserve it's type but not it's config
                     */
                    if (psr.getRestService() != null) {
                        assertTrue(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(participant) + ":" //$NON-NLS-1$
                                + " - REST system participant should have had all content from xpdExt:ParticipantSharedResource/xpdExt:RestService removed", //$NON-NLS-1$
                                psr.getRestService().getSecurityPolicy().isEmpty());
                    }
                }
            }

            /*
             * Check JavaScript DataMapping removal.
             */
            for (Iterator iterator = pkg.eAllContents(); iterator.hasNext();) {
                EObject eo = (EObject) iterator.next();

                /*
                 * * There should be NO DataMappings with ScriptGrammar="JavaScript" UNLESS they are within a
                 * ScriptDataMapper type element (which means that they are JavaScript data-mapping elements within a
                 * DataMapper grammar scenario
                 * 
                 * XPath grammar mappings should never exist
                 */
                if (eo instanceof DataMapping) {
                    Expression actual = ((DataMapping) eo).getActual();

                    if (actual != null) {
                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(eo)) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getParentActivity(eo))
                                + " - XPath grammar mappings should have been removed should have been removed", //$NON-NLS-1$
                                "XPath".equals(actual.getScriptGrammar())); //$NON-NLS-1$

                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(eo)) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getParentActivity(eo))
                                + " - JavaScript grammar mappings should have been removed should have been removed", //$NON-NLS-1$
                                ("JavaScript".equals(actual.getScriptGrammar()) //$NON-NLS-1$
                                        && Xpdl2ModelUtil.getAncestor(eo, ScriptDataMapper.class) == null));

                    }
                }

                /*
                 * Unmapped script mappings and script-mapping used to designated that the activity's mappings grammar
                 * is JavaScript or XPath (this approach not used for DataMapper) should all be removed.
                 */
                if (eo instanceof ScriptInformation && eo.eContainer() instanceof Activity) {
                    Expression expression = ((ScriptInformation) eo).getExpression();

                    if (expression != null) {
                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(eo)) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getParentActivity(eo))
                                + " - JavaScript unmapped script-mapping (or mapping grammar designator) should have been removed", //$NON-NLS-1$
                                "JavaScript" //$NON-NLS-1$
                                        .equals(expression.getScriptGrammar()));

                        assertFalse(xpdlFile.getName() + "::" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getProcess(eo)) + ":" //$NON-NLS-1$
                                + Xpdl2ModelUtil.getDisplayName(Xpdl2ModelUtil.getParentActivity(eo))
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
        Collection<IResource> bomFiles = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(project,
                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                BOMResourcesPlugin.BOM_FILE_EXTENSION,
                false);

        for (IResource bomFile : bomFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);

            Model model = (Model) wc.getRootElement();

            assertTrue("BOM file '" + bomFile.getName() //$NON-NLS-1$
                    + "' should have had the XsdNotationProfile removed", //$NON-NLS-1$
                    model.getAppliedProfile(BOMUtils.XSD_NOTATION_PROFILE) == null);
        }

        /*
         * Ensure that all integer attributes and primitive types have been changed to Decimal, Fixed Point with 0
         * decimals.
         */
        PrimitiveType integerType = PrimitivesUtil.getStandardPrimitiveTypeByName(
                XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet(),
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        for (IResource bomFile : bomFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);

            Model model = (Model) wc.getRootElement();

            /*
             * Ensure that there are no Integer type properties/primitives
             * 
             * Ensure that operations have been removed from classes.
             */
            EList<Element> allOwnedElements = model.allOwnedElements();

            for (Element element : allOwnedElements) {
                if (element instanceof Property) {
                    Property property = (Property) element;

                    assertTrue("Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + property.getName()
                            + "' should have been converted from Integer to Decimal, FixedPoint with Zero decimals", //$NON-NLS-1$
                            !(integerType.equals(property.getType())));
                    
					/* Sid ACE-8369 - All Case Id attributes should be set to mandatory */
                    if (BOMGlobalDataUtils.isCID(property)) {
						assertTrue("Case Identifier Property '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + property.getName()
								+ "' should have been set to Mandatory (lower==1, upper==1) if it wasn't already", //$NON-NLS-1$
								(property.getLower() == 1 && property.getUpper() == 1));
                    }
                    

                } else if (element instanceof PrimitiveType) {
                    PrimitiveType primitiveType = (PrimitiveType) element;

                    for (Element general : primitiveType.getGenerals()) {
                        assertTrue("PrimitiveType '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                                + primitiveType.getName()
                                + "' should have been converted from Integer to Decimal, FixedPoint with Zero decimals", //$NON-NLS-1$
                                !(integerType.equals(general)));
                    }

                } else if (element instanceof Class) {
                    Class clazz = (Class) element;

                    assertTrue("Class '" + model.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
                            + clazz.getName() + "' should have had all operations removed", //$NON-NLS-1$
                            clazz.getOwnedOperations().isEmpty());
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

            assertTrue("Nature 'com.tibco.xpd.wsdltobom.wsdlBomNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.wsdltobom.wsdlBomNature")); //$NON-NLS-1$
            assertTrue("Nature 'com.tibco.xpd.bom.xsdtransform.xsdNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.bom.xsdtransform.xsdNature")); //$NON-NLS-1$
            assertTrue("Nature 'com.tibco.xpd.wsdlgen.wsdlGenNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.wsdlgen.wsdlGenNature")); //$NON-NLS-1$
            assertTrue("Nature 'com.tibco.xpd.n2.daa.cleanBpmFolderNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.n2.daa.cleanBpmFolderNature")); //$NON-NLS-1$

            /*
             * Check builders have been removed.
             */
            assertTrue("Builder 'com.tibco.xpd.wsdltobom.wsdlToBomBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description, "com.tibco.xpd.wsdltobom.wsdlToBomBuilder")); //$NON-NLS-1$
            assertTrue("Builder 'com.tibco.xpd.bom.xsdtransform.xsdBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description, "com.tibco.xpd.bom.xsdtransform.xsdBuilder")); //$NON-NLS-1$
            assertTrue("Builder 'com.tibco.xpd.n2.daa.cleanBpmFolderBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description, "com.tibco.xpd.n2.daa.cleanBpmFolderBuilder")); //$NON-NLS-1$
            assertTrue("Builder 'com.tibco.xpd.wsdlgen.wsdlGen' should have been removed from project", //$NON-NLS-1$
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
            } else if ("com.tibco.xpd.asset.decisions".equals(asset.getId())) { //$NON-NLS-1$
                found = true;
            }
        }
        assertTrue("WSDL/Decisions project asset should have been removed", //$NON-NLS-1$
                !found);
    }

    /**
     * Check for unwanted folders being left behind.
     * 
     * @param project
     * @param projectConfig
     */
    private void checkUnwantedSpecialFoldersRemoved(IProject project, ProjectConfig projectConfig) {
        /* Special folder configurations first. */
        assertTrue("Project should have no 'compositeModulesOutput' special-folder", //$NON-NLS-1$
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
         * Make sure all generated/user defined WSDL folders have been removed.
         */
        assertTrue("All generated/user defined WSDL Special folders have not been removed", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "wsdl")); //$NON-NLS-1$

        assertTrue("All generated/user defined WSDL Special folders have not been removed", //$NON-NLS-1$
                !project.getFolder("Generated Services") //$NON-NLS-1$
                        .exists() && !project.getFolder("Services Descriptors").exists()); //$NON-NLS-1$
    }

    /**
     * Check the project has only the CE destination set.
     * 
     * @param project
     * @param projectDetails
     */
    private void checkProjectHasOnlyCEDestination(IProject project, ProjectDetails projectDetails) {
        assertTrue(project.getName() + " project should have only one destination set", //$NON-NLS-1$
                projectDetails.getEnabledGlobalDestinationIds().size() == 1);

        assertTrue(project.getName() + " project does not have CE destination set", //$NON-NLS-1$
                XpdConsts.ACE_DESTINATION_NAME.equals(projectDetails.getEnabledGlobalDestinationIds().get(0)));
    }

    /**
     * Cannot use projectConfig.getSpecialFolders() .getFoldersOfKind() BECAUSE it relies on the special folder kind
     * being a valid kin and that requires a special older contribution.
     * 
     * If that contribution is for some build folder or other that isn't used in SCE then the contribution won't be
     * there and it will fail.
     * 
     * So we have to look by hand.
     * 
     * @param projectConfig
     * @param kind
     * 
     * @return <code>true</code>if the project has any configuration for the given special folder kind.
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
     * @return <code>true</code> if the project has the given builder configured on it
     */
    private boolean hasBuilder(IProjectDescription description, String builderName) {
        for (ICommand builder : description.getBuildSpec()) {
            if (builderName.equals(builder.getBuilderName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param resource
     * @param markerId
     * @return <code>true</code> if given resource has given problem marker raised on it.
     */
    private boolean hasProblemMarker(IResource resource, String markerId) {
        try {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);

            if (markers != null) {
                for (IMarker marker : markers) {
                    if (markerId.equals(marker.getAttribute(IIssue.ID))) {
                        return true;
                    }
                }
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

	/**
	 * Expected Map of Shared Resource Name for given Participant Name
	 * 
	 * @return
	 */
	@SuppressWarnings("nls")
	private Map<String, String> getExpectedSharedResourceNameList()
	{
		HashMap<String, String> contents = new HashMap<>();
		contents.put("ProjectMigrationTest_RESTService_Consumer", "ProjectMigrationTest_REST-Service");
		contents.put("Participant2", "REST_CLientInstance");
		contents.put("RESTParticipantAlreadyMigrated", "REST_Service_Instance_AlreadyMigrated");

		contents.put("SOAPConsumerParticipant1", "SOAP_Service_Instance1");
		contents.put("SOAPParticipant2", "SOAP_Service_Instance2");
		contents.put("ProcessLevelSOAPConsumerParticipant", "SOAP_Service_Instance3");
		contents.put("SOAPParticipantWithJMS", null);
		contents.put("SOAPParticipantWithVirtualization", null);
		return contents;
}

}
