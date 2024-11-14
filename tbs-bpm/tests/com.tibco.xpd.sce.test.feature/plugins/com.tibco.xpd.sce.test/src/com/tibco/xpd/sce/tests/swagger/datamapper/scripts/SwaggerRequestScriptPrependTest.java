/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import java.util.List;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of the prepended script of the Swagger INPUT
 * mapping script (for variable initialisation etc)
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerRequestScriptPrependTest extends SwaggerScriptGeneratorExecutionTest
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
	 * Check outcome of REQUEST prepend script if there are no params/payload inputs at all
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testRequestPrependScript_NoInputParamsOrPayload() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerRequestScriptPrependTests_Process", "noInputData - PUT /noInputData",
				MappingDirection.IN);

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};"));

		/*
		 * Should be no additional data scoped beyond making sure that the script generation doesn't break and that no
		 * REST_xxxx variables are created.
		 */
		assertNotNull(scriptContext);

		List<Integer> scopes = scriptContext.getScopes();

		for (Integer scopeId : scopes)
		{
			Bindings scopeVars = scriptContext.getBindings(scopeId);

			if (scopeVars != null)
			{
				for (Entry<String, Object> scopeVar : scopeVars.entrySet())
				{
					if (scopeVar.getKey().startsWith("REST_") && !scopeVar.getKey().startsWith("REST_REQUEST"))
					{
						fail(String.format(
								"Unexpected %s variable added to scope when there are no input request paramerters.",
								scopeVar.getKey()));
					}
				}
			}
		}

	}


	/**
	 * Check outcome of REQUEST prepend script if all input param types (path/query/header) & payload is defined for
	 * service.
	 * 
	 * Each param type has 2 params, and one of each of these has a name that is not valid for JS variable (i.e.
	 * contains "-" chars). This is to ensure correct handling of the REST_xxx JS variables that are used to represent
	 * these values.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testRequestPrependScript_AllInputParamTypesIncluded() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerRequestScriptPrependTests_Process", "inputWithAllParamTypes", MappingDirection.IN);

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};"));

		/*
		 * Should be variables for each param of each type and one for the payload also.
		 */
		assertVariableValue(scriptContext, "REST_HEADER_X_Header_With_Bad_JS_Varname_Chars", null);
		assertVariableValue(scriptContext, "REST_HEADER_nonProblematicHeaderName", null);

		assertVariableValue(scriptContext, "REST_QUERY_X_Query_With_Bad_JS_Varname_Chars", null);
		assertVariableValue(scriptContext, "REST_QUERY_nonProblematicQueryName", null);

		assertVariableValue(scriptContext, "REST_PATH_X_Path_With_Bad_JS_Varname_Chars", null);
		assertVariableValue(scriptContext, "REST_PATH_nonProblematicPathName", null);

		assertVariableValue(scriptContext, "REST_PAYLOAD", null);

	}

}
