/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.n2.pe.rest.datamapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider;
import com.tibco.xpd.datamapper.api.JavaScriptStringBuilder;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadRootTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPropertyType;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.rest.swagger.SwaggerOperationReference;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Data Mapper script generator info provider for REST service invocation via Swagger/OpenAPI file.
 * 
 * This was shamelessly copied from {@link RestScriptGeneratorInfoProvider} at revision 95653 on 01/10/2024. Then it is
 * changed to handled SwaggerXXXXTreeitems rather than RestXXXTreeItems and ConceptPaths
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerScriptGeneratorInfoProvider implements IScriptGeneratorInfoProvider
{

	/**
	 * Name of variable to use for the response status code
	 */
	public static final String	RESPONSE_STATUS_VAR		= "REST_STATUS_CODE";

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getAssignmentStatement(java.lang.Object,
	 *      java.lang.String, java.lang.String)
	 * 
	 * @param object
	 *            The object for the LHS statement.
	 * @param rhsObjectStatement
	 *            The RHS statement.
	 * @param jsVarAlias
	 *            parent alias var.
	 * @return The assignment statement.
	 */
	@Override
	public String getAssignmentStatement(Object object, String rhsObjectStatement, String jsVarAlias)
	{
		StringBuilder statement = new StringBuilder();

		String lhsRestPath;

		if (jsVarAlias == null || jsVarAlias.isEmpty())
		{
			lhsRestPath = convertPath(getPath(object));

		}
		else
		{
			lhsRestPath = convertPath(jsVarAlias) + "['" + getName(object) + "']";
		}

		statement.append(lhsRestPath);
		statement.append(" = ");
		appendRhsGetterStatement(object, rhsObjectStatement, statement);
		statement.append(";");

		/*
		 * Delete REST input data rather that setting properties to null
		 * 
		 * BUT ONLY If not a Query/Header/Path parameter (i.e. REST_QUERY / REST_PATH / REST_HEADER) or Primitive type
		 * payload (i.e. REST_PAYLOAD) as these are all top level temp vars and we can't delete those.
		 * 
		 * Best way to tell is if the item's parent is also a SwaggerTypedTreeItem - if it is not then this is a top
		 * level data item and therefore cannot be deleted.
		 */
		if (((SwaggerMapperTreeItem)object).getParent() instanceof SwaggerTypedTreeItem) 
		{
			statement.append("if (");
			statement.append(lhsRestPath);
			statement.append(" === null) { ");
			statement.append("delete ");
			statement.append(lhsRestPath);
			statement.append("; }");
		}

		return statement.toString();
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getAssignmentElseStatement(java.lang.Object,
	 *      java.lang.String, java.lang.String)
	 *
	 * @param object
	 * @param rhsObjectStatement
	 * @param jsVarAlias
	 * @return
	 */
	@Override
	public String getAssignmentElseStatement(Object object, String jsVarAlias)
	{
		/*
		 * Delete REST input data rather that setting properties to null
		 * 
		 * Delete the target data (1st set to null so that the delete will always work even if target did not exist).
		 * i.e...
		 * 
		 * REST_PAYLOAD['consumerRequestType'] = null; delete REST_PAYLOAD['consumerRequestType'];
		 */
		StringBuilder statement = new StringBuilder();

		String lhsRestPath;

		if (jsVarAlias == null || jsVarAlias.isEmpty())
		{
			lhsRestPath = convertPath(getPath(object));

		}
		else
		{
			lhsRestPath = convertPath(jsVarAlias) + "['" + getName(object) + "']";
		}

		/* REST_PAYLOAD['xxxxx'] = null; */
		statement.append(lhsRestPath);
		statement.append(" = ");
		statement.append("null");
		statement.append(";");

		/*
		 * delete REST_PAYLOAD['xxxxx']; 		 
		 *  
		 * BUT ONLY If not a Query/Header/Path parameter (i.e. REST_QUERY / REST_PATH / REST_HEADER) or Primitive type
		 * payload (i.e. REST_PAYLOAD) as these are all top level temp vars and we can't delete those.
		 * 
		 * Best way to tell is if the item's parent is also a SwaggerTypedTreeItem - if it is not then this is a top
		 * level data item and therefore cannot be deleted.
		 */
		if (((SwaggerMapperTreeItem)object).getParent() instanceof SwaggerTypedTreeItem) 
		{
			statement.append("delete ");
			statement.append(lhsRestPath);
			statement.append(";");
		}

		return statement.toString();
	}

	/**
	 * 
	 * @param object
	 * @return The {@link ParameterStyle} if the given object is a {@link SwaggerParamTreeItem}
	 */
	private ParameterStyle getParameterStyle(Object object)
	{
		if (object instanceof SwaggerParamTreeItem)
		{
			return ((SwaggerParamTreeItem) object).getParamStyle();
		}
		return null;
	}

	/**
	 * Get RHS of an assignment statement where
	 * 
	 * This Appends the statement that will get value of the SOURCE of a mapping and perform any necessary
	 * pre-processing on it.
	 * 
	 * @param object
	 * @param rhsObjectStatement
	 * @param statement
	 */
	private void appendRhsGetterStatement(Object object, String rhsObjectStatement, StringBuilder statement)
	{
		String suffix = "";

		if ("null".equals(rhsObjectStatement))
		{
			/**
			 * The script generator framework may pass us literally "null" so in this case we just want to return it.
			 */

		}
		else if (object instanceof SwaggerParamTreeItem)
		{
			SwaggerParamTreeItem item = (SwaggerParamTreeItem) object;

			SwaggerPropertyType type = item.getType();

			switch (type)
			{
				case STRING:
					suffix = " ? new String(" + rhsObjectStatement + ") : null";
					break;
				case BOOLEAN:
				case NUMBER:
					suffix = " ? " + rhsObjectStatement + " : null";
					break;
				case INTEGER:
					suffix = " ? Math.round(" + rhsObjectStatement + ") : null";
					break;
				case DATE:
					suffix = " ? " + rhsObjectStatement + ".toJSON().substring(0,10) : null";
					break;
				case DATETIME:
					suffix = " ? " + rhsObjectStatement + ".toJSON() : null";
					break;

			}
		}
		else if (object instanceof SwaggerPayloadTreeItem)
		{
			SwaggerPayloadTreeItem payloadItem = (SwaggerPayloadTreeItem) object;
			SwaggerPropertyType type = payloadItem.getType();

			if (type.isSimpleType())
			{
				if (SwaggerPropertyType.DATE.equals(type))
				{
					suffix = " ? " + rhsObjectStatement + ".toJSON().substring(0,10) : null";
				}
				else if (SwaggerPropertyType.DATETIME.equals(type))
				{
					suffix = " ? " + rhsObjectStatement + ".toJSON() : null";
				}
				else if (SwaggerPropertyType.INTEGER.equals(type))
				{
					suffix = " ? Math.round(" + rhsObjectStatement + ") : null";

				}
				else if (SwaggerPropertyType.BOOLEAN.equals(type))
				{
					/*
					 * "!= null" is now done for all types at end of method now.
					 */
					suffix = " ? " + rhsObjectStatement + " : null";
				}
				else if (SwaggerPropertyType.NUMBER.equals(type))
				{
					suffix = " ? " + rhsObjectStatement + " : null";
				}
				else if (SwaggerPropertyType.STRING.equals(type))
				{

					/*
					 * If the target input property is optional, then exclude value if source field is empty.
					 * 
					 * This is to work around 'text attributes are set to empty string by Forms/Case-Data-manager even
					 * when user hasn't entered any value'.
					 */
					boolean excludeEmptyString = false;

					if (payloadItem.isRequired() && !payloadItem.isArray())
					{
						// Minimum instances == 0 == optional REST property.
						// For arrays, best just leave as is.
						excludeEmptyString = true;
					}

					if (excludeEmptyString)
					{
						suffix = " && (" + rhsObjectStatement + ".length > 0)" + " ? new String(" + rhsObjectStatement
								+ ") : null";
					}
					else
					{
						// For required target REST property always map EVEN in empty string.
						suffix = " ? new String(" + rhsObjectStatement + ") : null";
					}
				}
			}
		}

		/*
		 * Can't just use "SRC ? (typeof == 'object' ......" because if SRC is a number or boolean then "SRC" on it's
		 * own evaluates to false and therefore the 'null' else clause would be used.
		 * 
		 * hence if source value was zero or false then the target would get set to null. So instead we do a definitive
		 * check for not null and not undefined (which != will do).
		 * 
		 * FIXED - Except that IF there is no suffix we should just use the rhsObjectStatement itself. Otherwise (for
		 * instance when doing the covalue ntent of a tgt.push(value) statement we would put tgt.push((value != null))
		 * which always pushes a boolean. So if there is no suffix " ? xxxxxx" then we should not be creating a
		 * condition to put in front of it!
		 */
		if (suffix != null && suffix.length() > 0)
		{
			statement.append("(" + rhsObjectStatement + " != null)");
			statement.append(suffix);
		}
		else
		{
			statement.append(rhsObjectStatement);
		}
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getGetterStatement(java.lang.Object,
	 *      java.lang.String)
	 * 
	 * @param object
	 *            The object to get the value for.
	 * @param jsVarAlias
	 *            The parent alias var.
	 * @return The statement to get the value.
	 */
	@Override
	public String getGetterStatement(Object object, String jsVarAlias)
	{
		String statement = null;
		if (jsVarAlias == null || jsVarAlias.isEmpty())
		{
			jsVarAlias = convertPath(getPath(object));
		}
		else
		{
			jsVarAlias = convertPath(jsVarAlias) + "['" + getName(object) + "']";
		}

		if (object instanceof SwaggerTypedTreeItem)
		{
			SwaggerTypedTreeItem item = (SwaggerTypedTreeItem) object;
			SwaggerPropertyType type = item.getType();
			
			if (type.isSimpleType())
			{
				String suffix = "";

				if (SwaggerPropertyType.DATE.equals(type) || SwaggerPropertyType.DATETIME.equals(type))
				{
					suffix = " ? new Date(" + jsVarAlias + ") : null";
				}

				statement = jsVarAlias + suffix;
			}
			else
			{
				statement = "JSON.parse(JSON.stringify(" + jsVarAlias + "))";
			}
		}


		return statement;
	}

	/**
	 * Converts a dot separated path to reference children using ['child'].
	 * 
	 * @param path
	 *            The dot separated path.
	 * @return The converted path.
	 */
	private String convertPath(String path)
	{
		String converted = null;
		if (path != null)
		{
			StringBuilder sb = new StringBuilder();
			String[] parts = path.split("\\.");
			if (parts.length > 0)
			{
				sb.append(parts[0]);
			}
			for (int i = 1; i < parts.length; i++)
			{
				sb.append("['");
				sb.append(parts[i]);
				sb.append("']");
			}
			converted = sb.toString();
		}
		return converted;
	}

	/**
	 * Prepends a script to the REST input or output mapping script to declare any necessary variables.
	 * 
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToPrepend(com.tibco.xpd.xpdExtension.ScriptDataMapper,
	 *      boolean)
	 * 
	 * @param container
	 *            The script data mapper containing the mapping.
	 * @param isSource
	 *            true if the REST data is the mapping source.
	 * @return Scripts to prepend to the final mapping script.
	 */
	@Override
	public String getScriptsToPrepend(ScriptDataMapper container, boolean isSource)
	{
		JavaScriptStringBuilder jssb = new JavaScriptStringBuilder();

		Activity activity = Xpdl2ModelUtil.getParentActivity(container);

		RestMapperTreeItemFactory factory = RestMapperTreeItemFactory.getInstance();

		if (isSource)
		{
			/**
			 * ===========================================================================================
			 * 
			 * HANDLE OUTPUT FROM REST MAPPING PREPEND SCRIPT
			 * 
			 * ===========================================================================================
			 */
			jssb.addComment("Get response data.");

			jssb.addLine(String.format("var %s = REST_RESPONSE.getStatus();", RESPONSE_STATUS_VAR));
			jssb.addLine("", false, false);

			/*
			 * We potentially have mappings for multiple response codes, each with it's own set of header-params and
			 * payload.
			 * 
			 * So at the start of the script we set up all of them either as null except for the ones that match the
			 * status code which are set to the headers and payload received from the script consumer.
			 */
			Collection<SwaggerResponseContainerTreeItem> swaggerResponseItems;
			
			if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil.getEventTriggerType(activity))
					&& CatchThrow.CATCH.equals(EventObjectUtil.getCatchThrowType(activity)))
			{
				/*
				 * Error response item on catch error event.
				 */
				swaggerResponseItems = new ArrayList<>();

				Activity throwerActivity = EventObjectUtil.getTaskAttachedTo(activity);
				ResultError resultError = EventObjectUtil.getResultError(activity);
				
				if (resultError != null && resultError.getErrorCode() != null && !resultError.getErrorCode().isEmpty())
				{
					SwaggerResponseContainerTreeItem errorResponse = factory
							.createSwaggerCatchPesponseContainerTreeItem(activity,
							throwerActivity,
							resultError.getErrorCode());

					if (errorResponse != null)
					{
						swaggerResponseItems.add(errorResponse);
					}
				}
			}
			else
			{
				/*
				 * Success responses on REST service task...
				 */
				swaggerResponseItems = factory.createSwaggerResponseItems(activity);
			}

			/*
			 * First Create and initialise ALL the variables for all response codes.
			 * 
			 * And if there is a Default response then initialise those by default.
			 */
			boolean hasPayloadItem = false;
			for (SwaggerResponseContainerTreeItem swaggerResponse : swaggerResponseItems)
			{
				for (SwaggerMapperTreeItem responseChild : swaggerResponse.getMappableContentItems())
				{
					jssb.addLine(String.format("var %s = null;", getPath(responseChild), false, false));

					if (responseChild instanceof SwaggerPayloadRootTreeItem)
					{
						hasPayloadItem = true;
					}
				}
			}

			jssb.addLine("", false, false);

			/*
			 * 
			 * Then create a structure of "if status code = NNN then set params for status code NNN for any non-default
			 * response codes
			 * 
			 */
			int nonDefaultResponseCount = 0;
			SwaggerResponseContainerTreeItem defaultResponse = null;

			for (SwaggerResponseContainerTreeItem swaggerResponse : swaggerResponseItems)
			{
				if (!swaggerResponse.isDefaultResponse()) // We'll deal with 'Default' in else clause
				{
					jssb.addLine(
							String.format("%sif (%s == %s) {",
									(nonDefaultResponseCount > 0 ? "else " : ""),
									RESPONSE_STATUS_VAR,
									swaggerResponse.getStatusCode()),
							true, false);

					/* Add the header and payload variables initialisation from REST_RESPONSE variable */
					addResponseVarsAssignment(jssb, swaggerResponse);

					jssb.addLine("}", false, true);

					nonDefaultResponseCount++;
				}
				else
				{
					/* Keep track of default response for else clause */
					defaultResponse = swaggerResponse;
				}
			}

			/*
			 * Then, if we have a 'default' response code, initialise the default response variables unless it's been
			 * set for one of the non-default codes already
			 */
			if (defaultResponse != null) {
				/*
				 * If there were non-default responses, then place the default response initialisation in an else{}
				 * clause.
				 */
				if (nonDefaultResponseCount > 0)
				{
					jssb.addLine("else {", true, false);
				}

				/* Add the header and payload variables initialisation from REST_RESPONSE variable */
				addResponseVarsAssignment(jssb, defaultResponse);

				if (nonDefaultResponseCount > 0)
				{
					jssb.addLine("}", false, true);
				}
			}

		}
		else
		{
			/**
			 * ===========================================================================================
			 * 
			 * HANDLE INPUT TO REST MAPPING PREPEND SCRIPT
			 * 
			 * ===========================================================================================
			 */
			Collection<SwaggerContainerTreeItem> swaggerRequestItems = factory.createSwaggerRequestItems(activity);

			for (SwaggerContainerTreeItem swaggerRequestItem : swaggerRequestItems)
			{
				for (SwaggerMapperTreeItem requestItem : swaggerRequestItem.getMappableContentItems())
				{
					jssb.addLine(String.format("var %s = null;", getPath(requestItem), false, false));
				}
			}

		}

		return jssb.toString();
	}

	/**
	 * For JSON payloads we need to handle with care!
	 * 
	 * So we will do a try -> catch in case data type is not a valid JSON value (other than null, in case service as no
	 * payload at all).
	 * 
	 * BUT we have to do it for each individual response code payload BECAUSE the way we assign the REST_PAYLOAD_nnn
	 * variable depends on the service response mediatype (applicationjson or text/plain)
	 * 
	 * @param jssb
	 * @param responseChild
	 */
	private void addPayloadParseToPayloadVar(JavaScriptStringBuilder jssb, SwaggerPayloadRootTreeItem responseChild)
	{
		jssb.addLine("try {", true, false);

		jssb.addLine("if (REST_RESPONSE.getData() != null) {", true, false);

		if (SwaggerPayloadContainerTreeItem.JSON_CONTENT_TYPE.equals(responseChild.getMediaType()))
		{
			jssb.addLine(String.format("%s = JSON.parse(REST_RESPONSE.getData());", responseChild.getPath()), false,
				false);
		}
		else
		{
			jssb.addLine(String.format("%s = REST_RESPONSE.getData();", responseChild.getPath()), false, false);
		}

		jssb.addLine("}", false, true);

		jssb.addLine("} catch(ex) {", true, true);
		jssb.addComment(
				"Wrap exception in something more obvious if the response data parse fails (probably not JSON format)");

		jssb.addLine(
				"throw \"    JSON Response payload parsing failed (not formatted correctly as JSON string?).\\n\" + ",
				true, false);
		jssb.addLine(
				"\"        Parser Exception: \" + ex.message + \"\\n---------------------------------\\n\" + ");
		jssb.addLine(
				"\"        Payload Text: \\n\" + REST_RESPONSE.getData() + \"\\n---------------------------------\\n\";");
		jssb.addLine("", false, true);
		jssb.addLine("}\n", false, true);
	}

	/**
	 * Adds initialisation block for a given swagger response's variables, to initialise them from the headers/payload
	 * data stored in the REST_RESPONSE scoped by the script consumer e.g....
	 * 
	 * <pre>
	 *     REST_HEADER_200_param1 = REST_RESPONSE.getHeader('param1');
	 *     REST_HEADER_200_param2 = REST_RESPONSE.getHeader('param2');
	 *     REST_PAYLOAD_200 = REST_RESPONSE.getData();
	 * </pre>
	 * 
	 * @param jssb
	 * @param swaggerResponse
	 */
	private void addResponseVarsAssignment(JavaScriptStringBuilder jssb,
			SwaggerResponseContainerTreeItem swaggerResponse)
	{
		// Create the variables for this response code...
		for (SwaggerMapperTreeItem responseChild : swaggerResponse.getMappableContentItems())
		{
			if (responseChild instanceof SwaggerResponseParamTreeItem)
			{
				jssb.addLine(String.format("%s = REST_RESPONSE.getHeader('%s');",
						getPath(responseChild),
						((SwaggerResponseParamTreeItem) responseChild).getText()), false, false);
			}
			else if (responseChild instanceof SwaggerPayloadRootTreeItem)
			{
				/*
				 * Assign a temp variable REST_PAYLOAD_<status-code> so that we only need do all the JSON parse
				 * try/catch once.
				 */
				addPayloadParseToPayloadVar(jssb, (SwaggerPayloadRootTreeItem) responseChild);
			}
		}
	}

	/**
	 * Appends a script to the REST input mappings to build the request object from the parameter and payload mapping
	 * data.
	 * 
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getScriptsToAppend(com.tibco.xpd.xpdExtension.ScriptDataMapper,
	 *      boolean)
	 * 
	 * @param container
	 *            The script data mapper containing the mapping.
	 * @param isSource
	 *            true if the REST data is the mapping source.
	 * @return Scripts to append to the final mapping script.
	 */
	@Override
	public String getScriptsToAppend(ScriptDataMapper container, boolean isSource)
	{
		if (isSource)
		{
			/**
			 * ===========================================================================================
			 * 
			 * HANDLE OUTPUT FROM REST MAPPING APPEND SCRIPT
			 * 
			 * ===========================================================================================
			 */

			/*
			 * Add the pathExists() Function for Null source property and descendant checking given root object and path
			 */
			return "\n\n" + ProcessDataMapperScriptGeneratorInfoProvider.JS_NULL_PROPERTY_METHOD;
		}
		else
		{
			/**
			 * ===========================================================================================
			 * 
			 * HANDLE INPUT TO REST MAPPING APPEND SCRIPT
			 * 
			 * ===========================================================================================
			 */
			RestMapperTreeItemFactory factory = RestMapperTreeItemFactory.getInstance();
			Activity activity = Xpdl2ModelUtil.getParentActivity(container);
			JavaScriptStringBuilder jssb = new JavaScriptStringBuilder();

			RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

			Operation operation = rsta.getRSOOperation(activity);
			
			if (operation != null)
			{
				SwaggerOperationReference operationRef = new SwaggerOperationReference(rsta.getRSOMethodId(activity));
				
				HttpMethod httpMethod = operationRef.getHttpMethod();
				String resourcePath = operationRef.getResourcePath();

				Collection<SwaggerContainerTreeItem> swaggerRequestItems = factory.createSwaggerRequestItems(activity);

				/*
				 * Build the URL (including path / query params etc
				 */
				jssb.addComment("Build request URI.");
				appendQueryUriFunctions(jssb);
				jssb.append("var REST_REQUEST_URI = ");
				buildUri(jssb, rsta, factory, activity, resourcePath, swaggerRequestItems);
				jssb.addLine(";");

				jssb.addComment("Set request fields.");
				
				/*
				 * Add content type headers if we have any request/response content.
				 */
				SwaggerPayloadRootTreeItem payloadItem = getRequestPayloadRootTreeItem(swaggerRequestItems);
				
				if (payloadItem != null)
				{
					jssb.addLine("REST_REQUEST.setHeader('Content-Type','" + payloadItem.getMediaType() + "');");
					
					String responseMediaType = getResponseMediaType(activity);

					if (responseMediaType != null && !responseMediaType.isEmpty())
					{
						jssb.addLine("REST_REQUEST.setHeader('Accept','" + responseMediaType + "');");
					}
				}

				/*
				 * Set all the header param values into the REST_REQUEST object supplied by script consumer.
				 */
				Collection<SwaggerParamTreeItem> headerItems = getParamTreeItems(swaggerRequestItems,
						ParameterStyle.HEADER);

				for (SwaggerParamTreeItem headerParam : headerItems)
				{
					jssb.append("REST_REQUEST.setHeader('");
					jssb.append(headerParam.getText());
					jssb.append("',");
					jssb.append(getPath(headerParam));
					jssb.addLine(");");
				}

				jssb.addLine("REST_REQUEST.setUrl(REST_REQUEST_URI);");
				jssb.addLine("REST_REQUEST.setMethod('" + httpMethod.name() + "');");

				if (payloadItem != null)
				{
					jssb.addLine("if (REST_PAYLOAD != null) {", true, false);
					StringBuilder payload = new StringBuilder();
					payload.append("REST_REQUEST.setData(");

					if (SwaggerPayloadContainerTreeItem.JSON_CONTENT_TYPE.equals(payloadItem.getMediaType()))
					{
						payload.append("JSON.stringify(");
					}

					payload.append("REST_PAYLOAD");

					if (SwaggerPayloadContainerTreeItem.JSON_CONTENT_TYPE.equals(payloadItem.getMediaType()))
					{
						payload.append(")");
					}

					payload.append(");");
					jssb.addLine(payload.toString());

					jssb.addLine("}", false, true);
				}

				return jssb.toString();
			}
		}

		return null;
	}

	/**
	 * @param activity
	 * 
	 * @return The media type of the FIRST response listed for the service. it is assumed that all responses have the
	 *         same media type.
	 */
	private String getResponseMediaType(Activity activity)
	{
		Collection<SwaggerPayloadRootTreeItem> responseItems = getResponsePayloadRootTreeItems(
				RestMapperTreeItemFactory.getInstance().createSwaggerResponseItems(activity));

		String responseMediaType = null;
		for (SwaggerPayloadRootTreeItem responseItem : responseItems)
		{
			responseMediaType = responseItem.getMediaType();
			break;
		}
		return responseMediaType;
	}

	/**
	 * @param swaggerItems
	 * 
	 * @return The root payload tree item from the given set of REQUEST container tree items
	 */
	private SwaggerPayloadRootTreeItem getRequestPayloadRootTreeItem(Collection<SwaggerContainerTreeItem> swaggerItems)
	{
		SwaggerPayloadRootTreeItem rootPayloadItem = null;

		for (SwaggerContainerTreeItem treeItem : swaggerItems)
		{
			for (SwaggerTypedTreeItem mappableItem : treeItem.getMappableContentItems())
			{
				if (mappableItem instanceof SwaggerPayloadRootTreeItem)
				{
					rootPayloadItem = (SwaggerPayloadRootTreeItem) mappableItem;
					break;
				}
			}
		}
		return rootPayloadItem;
	}

	/**
	 * @param swaggerItems
	 * 
	 * @return The root payload tree item from the given set of RESPONSE container tree items
	 */
	private Collection<SwaggerPayloadRootTreeItem> getResponsePayloadRootTreeItems(
			Collection<SwaggerResponseContainerTreeItem> swaggerItems)
	{
		Collection<SwaggerPayloadRootTreeItem> payloadItems = new ArrayList<>();

		for (SwaggerResponseContainerTreeItem treeItem : swaggerItems)
		{
			for (SwaggerTypedTreeItem mappableItem : treeItem.getMappableContentItems())
			{
				if (mappableItem instanceof SwaggerPayloadRootTreeItem)
				{
					payloadItems.add((SwaggerPayloadRootTreeItem) mappableItem);
				}
			}
		}
		return payloadItems;
	}

	/**
	 * @param swaggerItems
	 * 
	 * @return A collection of all {@link SwaggerParamTreeItem}'s contained beneath the given set of container tree
	 *         items
	 */
	private Collection<SwaggerParamTreeItem> getParamTreeItems(Collection<SwaggerContainerTreeItem> swaggerItems,
			ParameterStyle parameterStyle)
	{
		List<SwaggerParamTreeItem> paramItems = new ArrayList<>();

		for (SwaggerContainerTreeItem treeItem : swaggerItems)
		{
			for (SwaggerTypedTreeItem mappableItem : treeItem.getMappableContentItems())
			{
				if (mappableItem instanceof SwaggerParamTreeItem
						&& parameterStyle.equals(((SwaggerParamTreeItem) mappableItem).getParamStyle()))
				{
					paramItems.add((SwaggerParamTreeItem) mappableItem);
				}
			}
		}
		return paramItems;
	}

	/**
	 * Append static functions to the given builder to assist with generating the query string for the URI.
	 * 
	 * @param jssb
	 *            The builder to add the functions to.
	 */
	private void appendQueryUriFunctions(JavaScriptStringBuilder jssb)
	{
		jssb.addLine("function __filterQueryParams(__all_params){", true, false);
		jssb.addLine("return __all_params.filter(function(__filter_param){", true, false);
		jssb.addLine("return __filter_param.mandatory||eval(__filter_param.path)!=null;");
		jssb.addLine("});", false, true);
		jssb.addLine("}", false, true);
		jssb.addLine("function __encodeQueryParams(__all_params){", true, false);
		jssb.addLine("var __filtered_params = __filterQueryParams(__all_params);");
		jssb.addLine("var __mapped_params = __filtered_params.map(function(__filtered_param){", true, false);
		jssb.addLine("return __filtered_param.name+'='+encodeURIComponent(eval(__filtered_param.path));");
		jssb.addLine("});", false, true);
		jssb.addLine("return __filtered_params.length>0?'?'+__mapped_params.join('&'):'';");
		jssb.addLine("}", false, true);
	}

	/**
	 * Builds the script to create the full relative URL for the REST service.
	 * 
	 * @param jssb
	 *            The string builder to append to.
	 * @param rsta
	 * @param factory
	 *            The REST mapper item factory used to obtain parameters from the activity.
	 * @param activity
	 *            The activity invoking the service.
	 * @param path
	 *            The resource path for the REST request
	 * @param swaggerRequestItems
	 *            The input param / payload container itesm from which the mappable content items can be got.
	 */
	private void buildUri(JavaScriptStringBuilder jssb, RestServiceTaskAdapter rsta, RestMapperTreeItemFactory factory,
			Activity activity,
			String path, Collection<SwaggerContainerTreeItem> swaggerRequestItems)
	{
		StringBuilder relativePath = new StringBuilder();

		/**
		 * Base path (anything after the host:port specification of the 1st service defined.
		 * 
		 * NOTE that it is recognised that there could be more than 1 servers entry, and it is _Possible_ that not all
		 * have the same base-path after the host URI. There is nothing we can do about that, we would not want the user
		 * to choose a specific one at design time. So if this becomes an issue then we would have to figure out a way
		 * of adding the basePath to the runtime SharedResource definition (and have the user to elect NOT to use
		 * basePath from Swagger specification here.
		 */
		String basePath = null;
		OpenAPI openAPIModel = rsta.getRSOOpenAPIModel(activity);

		List<Server> servers = openAPIModel.getServers();
		if (servers != null)
		{
			for (Server server : servers)
			{
				String baseUrl = server.getUrl();

				if (baseUrl != null)
				{
					try
					{
						basePath = new URL(baseUrl).getPath();
						break;
					}
					catch (Exception e)
					{
						/*
						 * IF the server spec is only partially defined (or not defined at all), then we will get a
						 * malformed URL - so we should just use the baseURL as is (it must be valid as we validate the
						 * swagger in SwaggerWorkingCopy and would not be able to get here unless it is valid).
						 */
						if (!"/".equals(baseUrl))
						{
							basePath = baseUrl;
						}
						else
						{
							basePath = "";
						}
					}
				}
			}
		}

		if (basePath != null && !basePath.isEmpty())
		{
			relativePath.append(String.format("encodeURI('%s') + ", basePath));
		}

		/**
		 * Resource path
		 */
		/*
		 * Any static part of the path must be URI encoded - i.e. everything except URL separators etc is encoded (see
		 * JIRA for detail).
		 */
		relativePath.append("encodeURI('");

		Collection<SwaggerParamTreeItem> pathItems = getParamTreeItems(swaggerRequestItems, ParameterStyle.PATH);

		for (SwaggerParamTreeItem pathItem : pathItems)
		{
			String find = "{" + pathItem.getText() + "}";

			/*
			 * Any path parameter value inserted must be URI-component-encoded - i.e. including url separator encoding
			 * (see JIRA for detail).
			 */
			String replace = "') + encodeURIComponent(" + getPath(pathItem) + ") + encodeURI('";
			path = path.replace(find, replace);

		}

		relativePath.append(path);
		relativePath.append("')");

		String pathScript = relativePath.toString();
		pathScript = pathScript.replace(" + encodeURI('')", "");

		if (pathScript.length() == 0)
		{
			jssb.append("''");
		}
		else
		{
			jssb.append(pathScript);
		}

		/**
		 * Query parameters
		 */
		Collection<SwaggerParamTreeItem> queryItems = getParamTreeItems(swaggerRequestItems, ParameterStyle.QUERY);

		if (queryItems != null)
		{
			if (queryItems.size() > 0)
			{
				/*
				 * We need to exclude optional query parameters with null values. As we don't know at design time which
				 * parameters to include we need to embed an inline function which will build a list of relevant
				 * parameters then construct the query string. The following function is passed a list of all of the
				 * query parameters. It first filters out any parameters that are optional and have a runtime value of
				 * null. It then maps remaining parameters to strings that will go in the URI as query statements.
				 * Finally it joins the query statements together separated by '&' and appends them to the URI.
				 */
				jssb.append("+__encodeQueryParams([");

				boolean first = true;

				for (SwaggerParamTreeItem queryParam : queryItems)
				{
					if (first)
					{
						first = false;
					}
					else
					{
						jssb.append(",");
					}

					jssb.append("{name:'");
					jssb.append(queryParam.getText());
					jssb.append("',path:'");
					jssb.append(getPath(queryParam));
					jssb.append("',mandatory:");
					jssb.append(Boolean.toString(queryParam.isRequired()));
					jssb.append("}");
				}
				jssb.append("])");
			}
		}
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionSizeScript(java.lang.Object,
	 *      java.lang.String)
	 * 
	 * @param object
	 *            The collection object.
	 * @param objectParentJsVar
	 *            JS variable containing the parent, or null.
	 * @return Script to get the collection size.
	 */
	@Override
	public String getCollectionSizeScript(Object object, String objectParentJsVar)
	{
		StringBuilder script = new StringBuilder();
		if (objectParentJsVar != null && !objectParentJsVar.isEmpty())
		{
			script.append(convertPath(objectParentJsVar) + "['" + getName(object) + "']");
		}
		else
		{
			script.append(convertPath(getPath(object)));

		}
		script.append(".length");
		return script.toString();
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionElementScript(java.lang.Object,
	 *      java.lang.String, java.lang.String)
	 * 
	 * @param collection
	 *            The collection object.
	 * @param indexVarName
	 *            The index of the element to get.
	 * @param objectParentJsVar
	 *            JS variable containing the parent, or null.
	 * @return Script to get the given element.
	 */
	@Override
	public String getCollectionElementScript(Object collection, String indexVarName, String objectParentJsVar)
	{
		String arrayItem = "";
		
		if (objectParentJsVar != null && !objectParentJsVar.isEmpty())
		{
			arrayItem = convertPath(objectParentJsVar) + "['" + getName(collection) + "']";
		}
		else
		{
			arrayItem = convertPath(getPath(collection));
		}
		
		arrayItem += "[" + indexVarName + "]";

		/*
		 * If the source type is a date or date-time, then convert it to Date object as is expected for Date items in
		 * BPM scripting.
		 */
		if (collection instanceof SwaggerTypedTreeItem)
		{
			SwaggerTypedTreeItem item = (SwaggerTypedTreeItem) collection;
			SwaggerPropertyType type = item.getType();
			
			if (SwaggerPropertyType.DATE.equals(type) || SwaggerPropertyType.DATETIME.equals(type))
			{
				arrayItem = String.format("(%1$s ? new Date(%1$s) : null)", arrayItem);
			}
		}

		return arrayItem;
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionElementScriptForTargetMerge(java.lang.Object,
	 *      java.lang.String, java.lang.String)
	 * 
	 * @param collection
	 * @param indexVarName
	 * @param objectParentJsVar
	 * @return
	 */
	@Override
	public String getCollectionElementScriptForTargetMerge(Object collection, String indexVarName,
			String objectParentJsVar)
	{
		/*
		 * for JSON content, there's nothing special to do for getting array element from source array or a target array
		 * (for source array the act of assigning an element from there to a target does not remove it from array,
		 * (where as process data arrays they can do as they're implemneted as EMF lists).
		 */
		return getCollectionElementScript(collection, indexVarName, objectParentJsVar);
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getClearCollectionScript(java.lang.Object,
	 *      java.lang.String)
	 * 
	 * @param collectionObject
	 *            The collection object.
	 * @param jsVarAlias
	 *            JS variable containing the parent, or null.
	 * @return Script to clear the collection.
	 */
	@Override
	public String getClearCollectionScript(Object collectionObject, String jsVarAlias)
	{
		StringBuilder script = new StringBuilder();
		if (jsVarAlias != null && !jsVarAlias.isEmpty())
		{
			script.append(convertPath(jsVarAlias) + "['" + getName(collectionObject) + "']");
		}
		else
		{
			script.append(convertPath(getPath(collectionObject)));

		}
		script.append(" = []");
		return script.toString();
	}

	/**
	 * 
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionAddElementScript(java.lang.Object,
	 *      java.lang.String, java.lang.String, boolean)
	 *
	 * @param collection
	 * @param jsElementToAdd
	 * @param objectParentJsVar
	 * @param excludeEmptyObjects
	 *            Handle exclusion of empty objects from target arrays if required.
	 * @return
	 */
	@Override
	public String getCollectionAddElementScript(Object collection, String jsElementToAdd, String objectParentJsVar,
			boolean excludeEmptyObjects)
	{

		StringBuilder script = new StringBuilder();

		/* Handle exclusion of empty objects from target arrays if required. */
		String targetPath;

		if (objectParentJsVar != null && !objectParentJsVar.isEmpty())
		{
			targetPath = convertPath(objectParentJsVar) + "['" + getName(collection) + "']";
		}
		else
		{
			targetPath = convertPath(getPath(collection));
		}

		if (excludeEmptyObjects)
		{
			// Add the 'if (!(<is empty object>) {' statement
			script.append(String.format("if (!(%1$s)) { ", getIsEmptyObjectConditionStatement(jsElementToAdd)));
		}

		script.append(targetPath);

		script.append(".push(");

		appendRhsGetterStatement(collection, jsElementToAdd, script);
		script.append(");");

		if (excludeEmptyObjects)
		{
			// Terminate the 'if (!(<is empty object>) {' statement
			script.append(" } ");
		}

		return script.toString();
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCollectionSetElementScript(java.lang.Object,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 * 
	 * @param collection
	 * @param jsElementToAdd
	 * @param objectParentJsVar
	 * @param loopIndexJsVar
	 * @return
	 */
	@Override
	public String getCollectionSetElementScript(Object collection, String jsElementToAdd, String objectParentJsVar,
			String loopIndexJsVar)
	{
		StringBuilder script = new StringBuilder();
		if (objectParentJsVar != null && !objectParentJsVar.isEmpty())
		{
			script.append(convertPath(objectParentJsVar) + "['" + getName(collection) + "']");
		}
		else
		{
			script.append(convertPath(getPath(collection)));
		}

		/*
		 * "Item['arrayProp'][1] = jsElementToAdd;"
		 */
		script.append("[");
		script.append(loopIndexJsVar);
		script.append("] = ");
		appendRhsGetterStatement(collection, jsElementToAdd, script);
		script.append(";");

		return script.toString();
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getComplexObjectCreationScript(java.lang.Object)
	 * 
	 * @param complexObject
	 *            The complex object to create.
	 * @return Script to create the object.
	 */
	@Override
	public String getComplexObjectCreationScript(Object complexObject)
	{
		if (complexObject instanceof SwaggerTypedTreeItem && !((SwaggerTypedTreeItem) complexObject).isSimpleType())
		{
			return "{}";
		}

		return null;
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getArrayCreationScript(java.lang.Object)
	 * 
	 * @param arrayObject
	 *            The complex object to create.
	 * @return Script to create the object.
	 */
	@Override
	public String getArrayCreationScript(Object arrayObject)
	{
		return "[]";
	}

	/**
	 * Gets the Javascript variable name, without any preceding path, for a given object.
	 * 
	 * @param object
	 *            The object to get the variable name for.
	 * @return The Javascript variable name for the object.
	 */
	private String getName(Object object)
	{
		if (object instanceof SwaggerMapperTreeItem)
		{
			return ((SwaggerMapperTreeItem) object).getText();
		}

		return null;
	}

	/**
	 * @param object
	 *            The object to get the path for.
	 * @return The full Javascript path for the object.
	 */
	private String getPath(Object object)
	{
		String path = null;
		if (object instanceof SwaggerPayloadTreeItem)
		{
			SwaggerPayloadTreeItem payloadItem = (SwaggerPayloadTreeItem) object;
			SwaggerMapperTreeItem parent = payloadItem.getParent();

			if (payloadItem instanceof SwaggerPayloadRootTreeItem)
			{
				path = payloadItem.getText();
			}
			else
			{
				path = getPath(parent) + "['" + payloadItem.getText() + "']";
			}
		}
		else if (object instanceof SwaggerParamTreeItem)
		{
			SwaggerParamTreeItem param = (SwaggerParamTreeItem) object;
			path = param.getPath();

			/**
			 * JS script variable names for query params also need to have special characters removed as well as header
			 * parameters.
			 * 
			 * Sid: Moved cleansing to SwaggerParamTreeItem sub-classes.
			 */
		}
		return path;
	}

	/**
	 * 
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getCheckNullTreeExpression(java.lang.Object,
	 *      java.lang.String, com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider.CheckNullTreeExpressionType)
	 * 
	 * @param object
	 * @param jsVarAlias
	 * @param checkType
	 * @return
	 */
	@Override
	public String getCheckNullTreeExpression(Object object, String jsVarAlias, CheckNullTreeExpressionType checkType)
	{

		/**
		 * Note that we ignore checkType because it makes no difference to JSON whether we are null-checking an array
		 * for loop iteration OR doing null-checking for single-instance source of a mapping.
		 * 
		 * In both cases for JSON, the source may be null OR undefined. Attempting to do MyField = JSONObj.property
		 * where property is undefined (not present at all) causes runtiem exception.
		 */

		String pathToCheck = null;

		if (jsVarAlias != null && jsVarAlias.length() > 0)
		{
			pathToCheck = jsVarAlias;
		}
		else if (object instanceof SwaggerMapperTreeItem)
		{
			pathToCheck = ((SwaggerMapperTreeItem) object).getPath();
		}

		if (pathToCheck != null && pathToCheck.length() > 0)
		{

			/**
			 * Use new pathExists() function (added as part of getScriptsToAppend())
			 * 
			 * So now, we convert a.b.c modelled path to a['b']['c'] style notation (to avoid issues with accented
			 * characters in property names etc).
			 * 
			 * Then we pass the path to the new 'pathExists(rootObject, "path") method. This reduces size of script by
			 * removing lots of repeated
			 * 
			 * " if ( (typeof(a) != undefined && a != null) && (typeof(a['b']) != undefined && a['b'] != null) &&
			 * (typeof(a['b']['c']) != undefined && a['b']['c'] != null) "
			 */

			/*
			 * If there's an alias then we have to check it for null.
			 * 
			 * **Note though that in situations where we're mapping nested content from within arrays jsVarAlis may
			 * be...
			 * 
			 * srcVi1.child.grandchild
			 * 
			 * Therefore nothing may have checked the null-ness of srcVi1.child yet. So we'll have to chop and check
			 * each part.
			 */

			/** Convert the dot-separted path to a['b'] notation (in case of accented chars etc) */
			String[] parts = pathToCheck.split("\\.");

			String pathTilNow = "";

			for (int i = 0; i < parts.length; i++)
			{
				String part = parts[i];

				if (i == 0)
				{
					/*
					 * First part of path is only ever the REST variable itself so don't have to surround to create
					 * "['@name']"
					 */
					pathTilNow = part;

				}
				else
				{
					/*
					 * Surround properties for "['propertyname'] to allow for special chars in identifier names.
					 */
					pathTilNow = pathTilNow + "['" + part + "']";
				}

			}

			/** Add the single call to pathExists() function passing root variable and path */
			String rootObject = parts[0];

			String pathExistsCall = String.format("pathExists(%1$s, \"%2$s\")", rootObject, pathTilNow);

			return pathExistsCall;
		}

		return null;
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#resolvePath(java.lang.Object, java.lang.String)
	 * 
	 * @param object
	 * @param path
	 * @return
	 */
	@Override
	public String resolvePath(Object object, String path)
	{
		return convertPath(path);
	}

	/**
	 * 
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getDeleteEmptyObjectScript(java.lang.Object,
	 *      java.lang.String)
	 *
	 * @param object
	 * @param jsVarAlias
	 *            the
	 * @return
	 */
	@Override
	public String getDeleteEmptyObjectScript(Object object, String jsVarAlias)
	{
		StringBuilder script = new StringBuilder();

		String targetPath;

		if (jsVarAlias == null || jsVarAlias.isEmpty())
		{
			targetPath = convertPath(getPath(object));
		}
		else
		{
			targetPath = convertPath(jsVarAlias);
		}

		// Add the 'if (!(<is empty object>) {' statement
		if (object instanceof SwaggerPayloadRootTreeItem)
		{
			/*
			 * Sid ACE-8864 Don't delete payload root (REST_PAYLOAD) because it's a scoped variable and they cannot be
			 * deleted. Set them to null instead.
			 */
			script.append(
					String.format("\nif (%1$s) {\n    %2$s = null;\n}\n",
							getIsEmptyObjectConditionStatement(targetPath),
					targetPath));
		}
		else
		{
			script.append(String.format("\nif (%1$s) {\n    delete %2$s;\n}\n",
					getIsEmptyObjectConditionStatement(targetPath),
					targetPath));
		}
		return script.toString();
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider#getDeleteEmptyArrayScript(java.lang.Object,
	 *      java.lang.String)
	 *
	 * @param object
	 * @param jsVarAlias
	 * @return
	 */
	@Override
	public String getDeleteEmptyArrayScript(Object object, String jsVarAlias)
	{
		StringBuilder script = new StringBuilder();

		String targetPath;

		if (jsVarAlias == null || jsVarAlias.isEmpty())
		{
			targetPath = convertPath(getPath(object));
		}
		else
		{
			targetPath = convertPath(jsVarAlias);
		}

		// Add the 'if (!(<is empty object>) {' statement
		if (object instanceof SwaggerPayloadRootTreeItem)
		{
			/*
			 * Sid ACE-8864 Don't delete payload root (REST_PAYLOAD) because it's a scoped variable and they cannot be
			 * deleted. Set them to null instead.
			 */
			script.append(String.format("if (%1$s) {\n    %2$s = null;\n}\n",
					getIsEmptyArrayConditionStatement(targetPath), targetPath));
		}
		else
		{
			script.append(
					String.format("if (%1$s) {\n    delete %2$s;\n}\n", getIsEmptyArrayConditionStatement(targetPath),
				targetPath));
		}

		return script.toString();
	}

	/**
	 * Get the javascript statement to check if the given object is empty.
	 * 
	 * @param path
	 *            Path of object
	 * 
	 * @return the javascript statement to check if the given object is empty.
	 */
	private Object getIsEmptyObjectConditionStatement(String path)
	{
		/*
		 * Use deepContainsPrimitiveProperties() function for checking empty optional objects. That way, if we have
		 * empty required objects/arrays (that will not themselves be deleted because they're tagged as required) under
		 * an optional Object, then we will treat them as empty.
		 */
		return String.format(
				"!!%1$s && Object.getPrototypeOf(%1$s) === Object.prototype && !Calculation.deepContainsPrimitiveProperties(%1$s)",
				path);
	}

	/**
	 * Get the javascript statement to check if the given array is empty.
	 * 
	 * @param path
	 *            Path of array
	 * 
	 * @return the javascript statement to check if the given array is empty.
	 */
	private Object getIsEmptyArrayConditionStatement(String path)
	{
		return String.format("Array.isArray(%1$s) && %1$s.length === 0", path);
	}

}
