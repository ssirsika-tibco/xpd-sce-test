/**
 * Copyright (c) TIBCO Software Inc 2004-2024. All rights reserved.
 */
package com.tibco.xpd.sce.tests.swagger.validation;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * Tests for validation against all unsupported schema content including
 * 
 * <li>External type references</li>
 * <li>Mixed type content (oneOf, anyOf, allOf, not and any-type array)</li>
 * <li>Array of Arrays</li>
 *
 * @author aallway
 * @since Nov 2024
 */
@SuppressWarnings("nls")
public class SwaggerUnsupportedSchemaContentTest extends AbstractN2BaseValidationTest
{

	private ProjectImporter projectImporter;

	public SwaggerUnsupportedSchemaContentTest()
	{
		super(true);
	}

	/**
	 * Tests for validation against all unsupported schema content including
	 * 
	 * <li>External type references</li>
	 * <li>Mixed type content (oneOf, anyOf, allOf, not and any-type array)</li>
	 * <li>Array of Arrays</li>
	 * 
	 * @throws Exception
	 */
	public void testUnsupportedSwaggerSchemaContent() throws Exception
	{
		doTestValidations();

		/* The SwaggerTaskItemProviderTests_Processes.xpdl file should not have errors */
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("SwaggerTaskItemProviderTests_Processes");

		IFile cleanXpdlFile = project.getFile(new Path("Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl"));
		assertTrue(cleanXpdlFile.isAccessible());

		assertFalse(
				"SwaggerTaskItemProviderTests_Processes.xpdl should not have any error problem markers, but has...\n"
						+ TestUtil.getErrorProblemMarkerList(cleanXpdlFile, true),
				TestUtil.hasErrorProblemMarker(cleanXpdlFile, true,
						Collections.singletonList("casestate.no.terminal.states.issue"), getTestName()));
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#customTestResourceSetup()
	 *
	 */
	@Override
	protected void customTestResourceSetup()
	{
		/*
		 * This test doesn't create files via the normal getTestResources() because we want to import and migrate a
		 * whole project from AMX BPM.
		 */
		projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/SwaggerTaskItemProviderTests/SwaggerTaskItemProviderTests_Services/",
						"resources/SwaggerTaskItemProviderTests/SwaggerTaskItemProviderTests_Processes/"},
				new String[]{"SwaggerTaskItemProviderTests_Services", "SwaggerTaskItemProviderTests_Processes"});

		assertTrue("Failed to load projects from \"resources/SwaggerWorkingCopyTest/", projectImporter != null);
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
	 *
	 * @throws Exception
	 */
	@Override
	protected void tearDown() throws Exception
	{
		if (projectImporter != null)
		{
			projectImporter.performDelete();
		}
		super.tearDown();
	}

	/*
	 * @Override protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() { return new
	 * ValidationsTestProblemMarkerInfo[]{}; }
	 */

	@Override
	protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos()
	{
		ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[]{

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_fLOaoJ0pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : oneOf) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsOneOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_fLOaoJ0pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : oneOf)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsOneOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_l9nOEJ0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: External types are not supported (IndirectionToExternalRef : External type (unsupported)... ./external-schema.json/ExternalType) (SwaggerTaskItemProviderTests_UnsupportedItems:IndirectionToExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_l9nOEJ0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: External types are not supported (IndirectionToExternalRef : External type (unsupported)... ./external-schema.json/ExternalType)     (SwaggerTaskItemProviderTests_UnsupportedItems:IndirectionToExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_lm2T4J0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: External types are not supported (./external-schema.json/ExternalType : External type (unsupported)[]... ./external-schema.json/ExternalType) (SwaggerTaskItemProviderTests_UnsupportedItems:ArrayExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_lm2T4J0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: External types are not supported (./external-schema.json/ExternalType : External type (unsupported)[]... ./external-schema.json/ExternalType)     (SwaggerTaskItemProviderTests_UnsupportedItems:ArrayExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_mV2u9J0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: External types are not supported (ArrayIndirectionToExternalRef : External type (unsupported)[]... ./external-schema.json/ExternalType) (SwaggerTaskItemProviderTests_UnsupportedItems:ArrayIndirectionToExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_mV2u9J0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: External types are not supported (ArrayIndirectionToExternalRef : External type (unsupported)[]... ./external-schema.json/ExternalType)     (SwaggerTaskItemProviderTests_UnsupportedItems:ArrayIndirectionToExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_niYxIJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported request content: Arrays of Array types are not supported (IndirectionToArrayNumberType : Number[]..[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadOverloadsArrayIndirectionToPrimitiveTypepayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_niYxIJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Arrays of Array types are not supported (IndirectionToArrayNumberType : Number[]..[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadOverloadsArrayIndirectionToPrimitiveTypepayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_pXwgE50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : oneOf[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsArrayOneOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_pXwgE50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : oneOf[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsArrayOneOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_q1m-I50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : allOf) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsAllOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_q1m-I50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : allOf)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsAllOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_qLHPoJ0pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : anyOf) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsAnyOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_qLHPoJ0pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : anyOf)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsAnyOfpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_r4oYo5ztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported request content: Arrays of Array types are not supported (Object[]..[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsInlineArrayOfComplexTypeArrayspayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_r4oYo5ztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Arrays of Array types are not supported (Object[]..[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsInlineArrayOfComplexTypeArrayspayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_r533g50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: Cannot determine property type (Undetermined type[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsAnyTypeArraypayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_r533g50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Cannot determine property type (Undetermined type[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsAnyTypeArraypayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_rXp7Q50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : not) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsNotpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_rXp7Q50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (Mixed type (unsupported) : not)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsNotpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_rY6Ww5ztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported request content: Arrays of Array types are not supported (Text[]..[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsInlineArrayOfPrimitiveTypeArrayspayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_rY6Ww5ztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Arrays of Array types are not supported (Text[]..[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsInlineArrayOfPrimitiveTypeArrayspayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (allOfArrayProperty : Mixed type (unsupported) : allOf[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (allOfProperty : Mixed type (unsupported) : allOf) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (anyOfArrayProperty : Mixed type (unsupported) : anyOf[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (anyOfProperty : Mixed type (unsupported) : anyOf) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (notArrayProperty : Mixed type (unsupported) : not[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (notProperty : Mixed type (unsupported) : not) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (oneOfArrayProperty : Mixed type (unsupported) : oneOf[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (oneOfProperty : Mixed type (unsupported) : oneOf) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: Cannot determine property type (anyTypeArrayProperty : Undetermined type[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (allOfArrayProperty : Mixed type (unsupported) : allOf[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (allOfProperty : Mixed type (unsupported) : allOf)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (anyOfArrayProperty : Mixed type (unsupported) : anyOf[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (anyOfProperty : Mixed type (unsupported) : anyOf)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (notArrayProperty : Mixed type (unsupported) : not[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (notProperty : Mixed type (unsupported) : not)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (oneOfArrayProperty : Mixed type (unsupported) : oneOf[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: 'oneOf', 'anyOf', 'allOf' and 'not' schema constructs are currently not supported (oneOfProperty : Mixed type (unsupported) : oneOf)     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_seg1o50pEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Cannot determine property type (anyTypeArrayProperty : Undetermined type[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithMixedTypePropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_sYu1AJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported request content: Arrays of Array types are not supported (CreatesArrayOfArraysJustInIndirections : MiddleIndirectionIntroducesArrayType3[]..[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeThatHasMutlipleArrayIndirectionspayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_sYu1AJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Arrays of Array types are not supported (CreatesArrayOfArraysJustInIndirections : MiddleIndirectionIntroducesArrayType3[]..[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeThatHasMutlipleArrayIndirectionspayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_tX82UJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported request content: Arrays of Array types are not supported (arrayOfArraysProperty : Text[]..[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithArrayOfArraysPropertypayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_tX82UJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Arrays of Array types are not supported (arrayOfArraysProperty : Text[]..[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadIsTypeWithArrayOfArraysPropertypayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_vxNHsJ0kEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: External types are not supported (externalRefArrayProperty : ./external-schema.json/ExternalType[]... ./external-schema.json/ExternalType) (SwaggerTaskItemProviderTests_UnsupportedItems:TypeWithExternalRefPropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_vxNHsJ0kEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: External types are not supported (externalRefProperty : ./external-schema.json/ExternalType) (SwaggerTaskItemProviderTests_UnsupportedItems:TypeWithExternalRefPropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_vxNHsJ0kEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: External types are not supported (externalRefArrayProperty : ./external-schema.json/ExternalType[]... ./external-schema.json/ExternalType)     (SwaggerTaskItemProviderTests_UnsupportedItems:TypeWithExternalRefPropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_vxNHsJ0kEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: External types are not supported (externalRefProperty : ./external-schema.json/ExternalType)     (SwaggerTaskItemProviderTests_UnsupportedItems:TypeWithExternalRefPropertiespayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_Wj5SEJ0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported request content: External types are not supported (./external-schema.json/ExternalType : External type (unsupported)) (SwaggerTaskItemProviderTests_UnsupportedItems:ExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_Wj5SEJ0hEe-y-dV17EXJNg", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: External types are not supported (./external-schema.json/ExternalType : External type (unsupported))     (SwaggerTaskItemProviderTests_UnsupportedItems:ExternalRefpayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedInputPayloadContent", //$NON-NLS-1$
						"_Zry0wJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported request content: Arrays of Array types are not supported (IndirectionToArrayComplexTypeRef : ComplexType[]..[]) (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadOverloadsArrayIndirectionToComplexTypepayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl", //$NON-NLS-1$
						"ace.unsupportedOutputPayloadContent", //$NON-NLS-1$
						"_Zry0wJztEe-xFPeXmUd19A", //$NON-NLS-1$
						"BPM  : Unsupported response (200) content: Arrays of Array types are not supported (IndirectionToArrayComplexTypeRef : ComplexType[]..[])     (SwaggerTaskItemProviderTests_UnsupportedItems:PayloadOverloadsArrayIndirectionToComplexTypepayload)", //$NON-NLS-1$
						""), //$NON-NLS-1$

		};
		return markerInfos;
	}

	@Override
	protected String getTestName()
	{
		return "SwaggerUnsupportedSchemaContentTest"; //$NON-NLS-1$
	}

	@Override
	protected String getTestPlugInId()
	{
		return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
	}

	@Override
	protected TestResourceInfo[] getTestResources()
	{
		return new TestResourceInfo[]{};
	}

}
