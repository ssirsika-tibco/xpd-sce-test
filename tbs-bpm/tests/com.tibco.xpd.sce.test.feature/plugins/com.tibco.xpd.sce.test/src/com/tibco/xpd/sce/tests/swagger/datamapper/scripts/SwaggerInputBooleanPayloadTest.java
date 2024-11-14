/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of Swagger INPUT PAYLOAD mapping script for
 * primitive BOOLEAN type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputBooleanPayloadTest extends SwaggerScriptGeneratorExecutionTest
{
	/**
	 * @see com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerScriptGeneratorExecutionTest#getProjectsForTest()
	 *
	 * @return
	 */
	@Override
	protected String[] getProjectsForTest()
	{
		return new String[]{"resources/SwaggerScriptGenTests/SwaggerScriptGenTests_REST/",
				"resources/SwaggerScriptGenTests/SwaggerScriptGenTests_Data/",
				"resources/SwaggerScriptGenTests/SwaggerScriptGenTests_Process/"};
	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * 
	 * BOOLEAN PAYLOAD from BOOLEAN FIELD / ATTRIBUTE
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping to BOOLEAN input payload - mapping SOURCE process BOOLEAN FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Boolean_From_BooleanField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanInput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to BOOLEAN input payload - mapping SOURCE process BOOLEAN FIELD = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Boolean_From_BooleanField_True() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanInput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.BooleanField = true;"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the value.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "true");
	}

	/**
	 * Test mapping to BOOLEAN input payload - mapping SOURCE process BOOLEAN FIELD = FALSE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Boolean_From_BooleanField_False() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanInput - from Boolean Field", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.BooleanField = false;"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the value.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "false");
	}

	/**
	 * Test mapping to BOOLEAN input payload - mapping SOURCE process BOOLEAN ATTR that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Boolean_From_BooleanAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanInput - from Boolean Attr", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to BOOLEAN input payload - mapping SOURCE process BOOLEAN ATTR = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Boolean_From_BooleanAttr_True() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanInput - from Boolean Attr", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};"//
				+ "data.bomDataField.boolean1 = true;"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the value.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "true");
	}

	/**
	 * Test mapping to BOOLEAN input payload - mapping SOURCE process BOOLEAN ATTR = FALSE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Boolean_From_BooleanAttr_False() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanInput - from Boolean Attr", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};"//
				+ "data.bomDataField.boolean1 = false;"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the value.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "false");
	}

}
