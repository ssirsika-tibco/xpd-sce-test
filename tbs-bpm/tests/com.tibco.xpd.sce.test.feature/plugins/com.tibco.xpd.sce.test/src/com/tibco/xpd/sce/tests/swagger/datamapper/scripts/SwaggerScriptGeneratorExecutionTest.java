/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import java.util.Map.Entry;
import java.util.Set;

import javax.script.Bindings;
import javax.script.ScriptContext;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.sce.tests.legacy.datamapper.ScriptExecutor;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * Base class for tests that generate and execute Swagger Input/Output mapping scripts using
 * {@link SwaggerScriptGeneratorInfoProvider}.
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public abstract class SwaggerScriptGeneratorExecutionTest extends TestCase
{

	private ProjectImporter		projectImporter;

	protected ScriptExecutor	executor;

	/**
	 * A script scope initialisation script for REST_REQUEST object
	 * 
	 * (the reason for the odd 'new String(param).toString()' to assign properties, is so that the property gets sense
	 * to a plain string NOT a ScriptObjectMirror representing an array of characters (which I think happens when you do
	 * String concatenation) -
	 * 
	 * AND in all cases, the payload data is always set (if not null) using REST_PAYLOAD.setData(JSON.stringify(value))
	 * - so it's safe even to force data to a string, because that is what stringify() returns.
	 */
	private static final String	REST_REQUEST_INIT_SCRIPT	= "var REST_REQUEST = \n"													//
			+ "{\n"																														//
			+ "    \"headers\" : {},\n"																												//
			+ "    \"url\" : null,\n"																									//
			+ "    \"method\" : null,\n"																								//
			+ "    \"data\" : null,\n"																									//
			+ "    \"setUrl\"   : function(aUrl) { this.url = (aUrl == null ? null : new String(aUrl).toString()); },\n"				//
			+ "    \"setMethod\": function(aMethod) { this.method = (aMethod == null ? null : new String(aMethod).toString()); },\n"	//
			+ "    \"setHeader\": function(name, value) { this['headers'][name] = (value == null ? null : new String(value).toString()); },\n"		//
			+ "    \"setData\"  : function(value) { this.data = (value == null ? null : new String(value).toString());  }\n"			//
			+ "};\n\n";																																//

	/**
	 * A script scope initialisation script for REST_RESPONSE object
	 */
	private static final String	REST_RESPONSE_INIT_SCRIPT	= "var REST_RESPONSE = {\n"													//
			+ "    \"headers\" : {},\n"																									//
			+ "    \"data\" : null,\n"																									//
			+ "    \"getStatus\" : function () {\n"																						//
			+ "        return %d;\n"																									//
			+ "    },\n"																												//
			+ "    \"getHeader\" : function (name) {\n"																					//
			+ "        return this['headers'][name];\n"																					//
			+ "    },\n"																												//
			+ "    \"getData\" : function () {\n"																						//
			+ "        return this.data;\n"																								//
			+ "    }\n"																													//
			+ "};\n\n"																													//
			+ "var data = {};\n";																										//

	/**
	 * Function to allow deepEquals of complex payloads
	 */
	private static final String	DEEP_EQUAL_FUNCTION			= "function deepEqual(obj1, obj2, path) {\n"													//
			+ "  // Handle primitive values and null\n"																										//
			+ "  if (typeof obj1 !== 'object' || obj1 === null || typeof obj2 !== 'object' || obj2 === null) {\n"											//
			+ "    if (obj1 === obj2) { return null; } else { return 'One or other of path('+path+') is not the same: obj1('+obj1+'), obj2('+obj2+') (or one is Class Object and other is primitive type) (or primitive with different value)'; }\n"	//
			+ "  }\n"																																		//
			+ "  // Compare object types and number of keys\n"																								//
			+ "  var keys1 = Object.keys(obj1);\n"																											//
			+ "  var keys2 = Object.keys(obj2);\n"																											//
			+ "  if (keys1.length !== keys2.length) {\n"																									//
			+ "    return 'there are different numbers of child properties under path('+path+')\\n  Properties1='+keys1.sort()+'\\n  Properties2='+keys2.sort();\n"								//
			+ "  }\n"																																		//
			+ "  // Recursively compare properties\n"																										//
			+ "  var k1 = 0;"																																//
			+ "  for (; k1 < keys1.length; k1++) {\n"																										//
			+ "    var key = keys1[k1];\n"																													//
			+ "    if (!obj2.hasOwnProperty(key)) {"
			+ "        return 'Property '+path+'.'+key+' is missing from obj2';\n"																			//
			+ "    }\n"																																		//
			+ "    var nestedResult =  deepEqual(obj1[key], obj2[key], path + '.' + key);\n"																//
			+ "    if (nestedResult != null) {"																												//
			+ "        return nestedResult;\n"																												//
			+ "    }\n"																																		//
			+ "  }\n"																																		//
			+ "  return null;\n"																															//
			+ "}\n\n";																																		//

	/**
	 * Required for handling the 'Exclude empty optional objects/arrays' functionality of rest/swagger input mapping
	 * scripts.
	 * 
	 * Copied from the caclulations.js class from TCLA dt-service-script (already adopted into BPMe)
	 */
	private static final String	CALCULATION_JS_CLASS		= "var Calculation = {\n"																//
			+ "  \"deepContainsPrimitiveProperties\" : function(obj) {\n"																			//
			+ "		// If we're passed a generic Object or Array then check it's children \n"														//
			+ "		//  (else it's considered to be a primitive type and hence has no children, so we drop thru and return false); \n"				//
			+ "		if (!!obj && obj instanceof Object && \n"																						//
			+ "				(Object.getPrototypeOf(obj) === Object.prototype || Array.isArray(obj))) {\n"											//
			+ "			for (var key in obj) {\n"																									//
			+ "				if (!(obj[key] instanceof Object) || \n"																				//
			+ "						(Object.getPrototypeOf(obj[key]) !== Object.prototype && !Array.isArray(obj[key]))) {\n"						//
			+ "					// It's not a generic Object or Array so must be primitive-type or primitive type\n"								//
			+ "					// class object like String or Date\n"																				//
			+ "		        	return true;\n"																										//
			+ "		        }\n"																													//
			+ "		        else {\n"																												//
			+ "		        	// It's a generic Object or Array, recurs and check it's children \n"												//
			+ "		        	if (this.deepContainsPrimitiveProperties(obj[key])) {\n"															//
			+ "		            	return true;\n"																									//
			+ "		            }\n"																												//
			+ "		        }\n"																													//
			+ "		    }\n"																														//
			+ "		}\n"																															//
			+ "	    return false;\n"																												//
			+ "	  }\n"																																//
			+ "};\n\n";
	
	
	/**
	 * @return An array of the projet folder to import for the test (setup once for all tests in sub-class
	 */
	protected abstract String[] getProjectsForTest();

	/**
	 * Assert that script variable exists and has the expected value
	 * 
	 * Remember that REST_REQUEST.data (set from the call to REST_REQUEST.setData() is JSON stringified, therefore
	 * for...
	 * <li>Strings including Date and DateTime - the payload should be a quoted string - so pass expected value as
	 * "\"Expected Value\""</li>
	 * <li>Numbers - the payload will be an unquoted String - so pass value as "123.456"</li>
	 * 
	 * @param scriptContext
	 * @param variablePath
	 * @param expectedValue
	 */
	protected void assertVariableValue(ScriptContext scriptContext, String variablePath, Object expectedValue)
	{
		Object value = executor.getScriptParameter(variablePath, scriptContext);

		assertEquals(expectedValue, value);
	}

	/**
	 * Assert that script variable exists and has the expected value
	 * 
	 * Remember that REST_REQUEST.data (set from the call to REST_REQUEST.setData() is JSON stringified, therefore
	 * for...
	 * <li>Strings including Date and DateTime - the payload should be a quoted string - so pass expected value as
	 * "\"Expected Value\""</li>
	 * <li>Numbers - the payload will be an unquoted String - so pass value as "123.456"</li>
	 * 
	 * @param scriptContext
	 * @param variablePath
	 * @param expectedValue
	 */
	protected void assertVariableValue(ScriptContext scriptContext, String variablePath, Object[] expectedValues)
	{
		Object value = executor.getScriptParameter(variablePath, scriptContext);

		if (expectedValues == null)
		{
			assertNull(value);
		}
		else
		{
			/*
			 * A JS array should be a Bindings (Map) with index numbers --> Value.
			 */
			assertTrue("Expected value to be an array", value instanceof Bindings);

			Bindings bindings = (Bindings) value;

			assertEquals("Expected value array to be length: " + expectedValues.length, expectedValues.length,
					bindings.size());

			int i = 0;

			Set<Entry<String, Object>> entries = bindings.entrySet();

			for (Entry<String, Object> entry : entries)
			{
				assertEquals(String.format("Expected value array[%d] index key to be corresponding number but was '%s'",
						i, entry.getKey()), new Integer(i).toString(), entry.getKey());
				
				assertEquals(String.format("Unexpected array[%d] value - ", i), expectedValues[i], entry.getValue());

				i++;
			}
		}
	}

	/**
	 * Assert that the value in the given script payload variable (REST_PAYLOAD_xxxxx) is a JSON object value and
	 * asssert if not.
	 * 
	 * Then if it is a JSON object, return it.
	 * 
	 * @param scriptContext
	 * @param payloadVariablename
	 *
	 * @return If the given variable is a JSON object, returns it, else fails ion assertion.
	 */
	protected Bindings assertPayloadValueObject(ScriptContext scriptContext, String payloadVariablename)
	{
		Object value = scriptContext.getAttribute(payloadVariablename);

		assertTrue(value instanceof Bindings);

		return (Bindings) value;
	}



	/**
	 * Create the REST_RESPONSE variable initialisation script.
	 * 
	 * This returns the REST_RESPONSE that would be setup by the process-engine at runtime and appends the
	 * additionalSetupScript if supplied
	 * 
	 * @param statusCode
	 * @param additionalSetupScript
	 *            Script to append to REST_RESPONSE setup script (for example REST_RESPONSE.data value that is returend
	 *            by RES_RESPONSE.getData())
	 * @param bomPkgNameSpace
	 *            The BOM pkg namespace for BOM classes that need to have factory.xxxx.createXxx() methods provided for
	 *            response scripts. <code>null</code> if no BOM class creation is required.
	 * @param bomClasses
	 *            The BOM classes - including the list of array properties within them to initialises - that need to
	 *            have factory.xxxx.createXxx() methods provided for response scripts. <code>null</code> if no BOM class
	 *            creation is required
	 * 
	 * @return A script snippet to setup the REST_RESPONSE variable
	 */
	protected String getRestResponseInitScript(int statusCode, String additionalSetupScript, String bomPkgNameSpace,
			BomClassAndArrayProperties[] bomClasses)
	{
		/*
		 * Build the 'factory' class
		 */
		StringBuilder factoryClass = new StringBuilder();

		if (bomPkgNameSpace != null && !bomPkgNameSpace.isEmpty() && bomClasses != null && bomClasses.length > 0)
		{
			factoryClass.append("var factory = {\n");
			factoryClass.append(String.format("    \"%s\" : {", bomPkgNameSpace));

			boolean first = true;

			for (BomClassAndArrayProperties bomClass : bomClasses)
			{
				if (first)
				{
					factoryClass.append("\n");
				}
				else
				{
					factoryClass.append(",\n");
				}
				factoryClass.append(String.format("        \"create%s\" : function() {\n", bomClass.name));
				factoryClass.append("            var bomClass = {};\n");

				if (bomClass.arrayProperties != null)
				{
					for (String arrayPropertyName : bomClass.arrayProperties)
					{
						factoryClass.append(String.format("            bomClass.%s = [];\n", arrayPropertyName));
					}
				}

				factoryClass.append("            return bomClass;\n");
				factoryClass.append("        }");

				first = false;
			}

			factoryClass.append("\n    }\n");
			factoryClass.append("};\n\n");
		}

		return factoryClass.toString() + String.format(REST_RESPONSE_INIT_SCRIPT, statusCode)
				+ (additionalSetupScript != null ? additionalSetupScript : "");
	}

	protected String getRestResponseInitScript(int statusCode, String additionalSetupScript, String bomPkgNameSpace,
			String[] bomClassNames)
	{
		BomClassAndArrayProperties[] bomClasses = new BomClassAndArrayProperties[bomClassNames.length];

		int i = 0;
		for (String bomClassName : bomClassNames)
		{
			bomClasses[i++] = new BomClassAndArrayProperties(bomClassName, new String[0]);
		}

		return getRestResponseInitScript(statusCode, additionalSetupScript, bomPkgNameSpace, bomClasses);
	}

	/**
	 * Gets the REST request mapping script intialisation script.
	 * 
	 * This includes the REST_REQUEST object usually scoped by Process-Engine when it runs the input mapping script
	 * (with the setUrl(), setHeader(), setData(), setMethod() functions).
	 * 
	 * The each set method sets a property inside the REST_REQUEST object for that item. so...
	 * <li>REST_REQUEST.setMethod(method) sets the REST_REQUEST.method property</li>
	 * <li>REST_REQUEST.setUrl(url) sets the REST_REQUEST.url property</li>
	 * <li>REST_REQUEST.setData(value) sets the REST_REQUEST.data property</li>
	 * <li>REST_REQUEST.setHeader(name, value) sets the REST_REQUEST[name] property</li>
	 * @param additionalSetupScript
	 *            Script to append to REST_REQUEST setup script (for example the process 'data' wrapper var)
	 * @param Expected
	 *            REST_PAYLOAD data type (string, number, integer, boolean, date, datetime, object)
	 * 
	 * @return the REST request mapping script intialisation script.
	 */
	protected String getRestRequestInitScript(String additionalSetupScript)
	{
		return REST_REQUEST_INIT_SCRIPT + CALCULATION_JS_CLASS
				+ (additionalSetupScript != null ? additionalSetupScript : "");
	}

	/**
	 * Get a script fragment for doing an (DEEP) equality check on two given complex variables
	 * 
	 * Script throws exception if the two objects are not equal.
	 * 
	 * Note that for Strings PLEASE PROVIDE expected properties as new String('xxxx') - our particular version of
	 * Nashorn doesn't like comparing constant string with String objects and mapping scripts always create new String()
	 * when assigning target data (to ensure that values are coerced correctly
	 * 
	 * @param expectedValue
	 * @param testValue
	 * 
	 * @return Script fragment to append to mapping script
	 */
	protected String getTestObjectsEqualScript(String expectedValue, String testValue)
	{
		String testScript = String.format("\n\n//\n// TEST CODE - NOT PART OF GENERATED MAPPING SCRIPT...\n//\n" //
				+ DEEP_EQUAL_FUNCTION //
				+ "var expectedValue = %1$s;\n" //
				+ "var diffResult = deepEqual(expectedValue, %2$s, '%2$s');\n" //
				+ "if (diffResult != null) {"
				+ "   throw 'Objects are not the same: ' + diffResult + '\\n  - expected(shown as JSON.stringified)...\\n      ' + JSON.stringify(expectedValue) + '\\n  - but was...\\n      ' + JSON.stringify(%2$s) + '\\n';\n" //
				+ "}\n", //
				expectedValue, testValue);

		return testScript;
	}

	/**
	 * Get a script fragment for doing an (DEEP) equality check on two given complex variables
	 * 
	 * Script throws exception if the two objects are not equal.
	 * 
	 * Note that for Strings PLEASE PROVIDE expected properties as new String('xxxx') - our particular version of
	 * Nashorn doesn't like comparing constant string with String objects and mapping scripts always create new String()
	 * when assigning target data (to ensure that values are coerced correctly
	 * 
	 * @param expectedValue
	 * @param testValue
	 * 
	 * @return Script fragment to append to mapping script
	 */
	protected String getTestProcessDataScript(String expectedValue)
	{
		return getTestObjectsEqualScript(expectedValue, "data");

	}

	/**
	 * Generate REST service task input or output data mapping script, depending on direction.
	 * 
	 * @param xpdlFilePath
	 * @param processName
	 * @param activityName
	 * @param direction
	 * 
	 * @return mapping script or asserts if there are any issues.
	 */
	protected String generateRestMappingScript(String xpdlFilePath, String processName, String activityName,
			MappingDirection direction)
	{
		ScriptDataMapper scriptDataMapperModel = getScriptDataMapperModel(xpdlFilePath, processName, activityName,
				direction);

		DataMapperJavascriptGenerator generator = new DataMapperJavascriptGenerator();

		String mappingScript = generator.convertMappingsToJavascript(scriptDataMapperModel);

		assertNotNull(mappingScript);

		return mappingScript;
	}

	/**
	 * @param xpdlFilePath
	 * @param processName
	 * @param activityName
	 * @param direction
	 * 
	 * @return The {@link ScriptDataMapper} model for ther given activity and direction.
	 */
	private ScriptDataMapper getScriptDataMapperModel(String xpdlFilePath, String processName, String activityName,
			MappingDirection direction)
	{
		Activity activity = findActivity(xpdlFilePath, processName, activityName);

		RestScriptDataMapperProvider scriptDataMapperModelProvider = new RestScriptDataMapperProvider(direction,
				new RestServiceTaskAdapter().getMapperContext(activity, direction));

		ScriptDataMapper scriptDataMapper = scriptDataMapperModelProvider.getScriptDataMapper(activity);

		assertNotNull(String.format("ScriptDataMapper model not found (%s, %s, %s, %s)", xpdlFilePath, processName,
				activityName, direction.name()), scriptDataMapper);

		return scriptDataMapper;
	}

	/**
	 * @param xpdlFilePath
	 * @param activityName
	 * @param processName
	 * 
	 * @return The activity or asserts if file / process / activity cannot be found.
	 */
	private Activity findActivity(String xpdlFilePath, String processName, String activityName)
	{
		IFile xpdlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(xpdlFilePath));

		assertNotNull(xpdlFile);
		assertTrue(String.format("XPDL File not found (%s)", xpdlFilePath), xpdlFile.isAccessible());

		WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		assertTrue(wc instanceof Xpdl2WorkingCopyImpl);

		Package pkg = (Package) wc.getRootElement();

		Process process = null;
		for (Process p : pkg.getProcesses())
		{
			if (processName.equals(p.getName()) || processName.equals(Xpdl2ModelUtil.getDisplayName(p)))
			{
				process = p;
				break;
			}
		}

		assertNotNull(String.format("Process not found (%s, %s)", xpdlFilePath, processName), process);

		Activity activity = null;
		for (Activity a : Xpdl2ModelUtil.getAllActivitiesInProc(process))
		{
			if (activityName.equals(a.getName()) || activityName.equals(Xpdl2ModelUtil.getDisplayName(a)))
			{
				activity = a;
				break;
			}
		}

		assertNotNull(String.format("Activity not found (%s, %s, %s)", xpdlFilePath, processName, activityName),
				activity);

		return activity;
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 *
	 * @throws Exception
	 */
	@Override
	@Before
	protected void setUp() throws Exception
	{
		super.setUp();

		String[] projectFolders = getProjectsForTest();
		String[] projectNames = new String[projectFolders.length];

		int i = 0;
		for (String folder : projectFolders)
		{
			Path path = new Path(folder);

			projectNames[i++] = path.lastSegment();
		}

		projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				projectFolders,
				projectNames);

		assertTrue("Failed to load projects from \"resources/SwaggerScriptGenTests/", projectImporter != null);

		try
		{
			executor = new ScriptExecutor();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * @see junit.framework.TestCase#tearDown()
	 *
	 * @throws Exception
	 */
	@Override
	@After
	protected void tearDown() throws Exception
	{
		if (projectImporter != null)
		{
			projectImporter.performDelete();
			projectImporter = null;
		}
	}

	/**
	 * Data class that names a class and its array properties.
	 * 
	 * This can then be used by
	 * {@link SwaggerScriptGeneratorExecutionTest#getRestResponseInitScript(int, String, String, BomClassAndArrayProperties[])}
	 * 
	 */
	protected static class BomClassAndArrayProperties
	{
		String		name;

		String[]	arrayProperties;

		/**
		 * @param name
		 * @param arrayProperties
		 */
		public BomClassAndArrayProperties(String name, String[] arrayProperties)
		{
			super();
			this.name = name;
			this.arrayProperties = arrayProperties;
		}
	}
}
