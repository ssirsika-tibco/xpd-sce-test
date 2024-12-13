/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPropertyType;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem;
import com.tibco.xpd.n2.cds.utils.CdsSwaggerResponseScriptWrapperFactory;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script relevant data provider for Swagger Service invocations.
 *
 * @author nkelkar
 * @since Oct 1, 2024
 */
public class CdsSwaggerJavaScriptRelevantDataProvider extends DefaultJavaScriptRelevantDataProvider
{
	ResourceSetImpl wrapperResourceSet = new ResourceSetImpl();
	/**
	 * Gets a list of script relevant data for a Swagger Service invocation mapping script. The data returned will
	 * depend on whether this contribution is for an input or output mapping script.
	 * 
	 * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
	 * 
	 * @return the script relevant data contributions.
	 */
	@Override
	public List<IScriptRelevantData> getScriptRelevantDataList()
	{
		if (isInputScript())
		{
			return super.getScriptRelevantDataList();
		}
		else
		{
			return getOutputRestServiceScriptRelevantData();
		}
	}

	/**
	 * Gets a list of script relevant data for a REST Service invocation output mapping script.
	 * 
	 * @return the script relevant data contributions.
	 */
	private List<IScriptRelevantData> getOutputRestServiceScriptRelevantData()
	{
		List<IScriptRelevantData> data = new ArrayList<IScriptRelevantData>();
		Activity mappingActivity = getActivity();
		if (mappingActivity == null)
		{
			// May be a new ScriptInformation.
			EObject eo = getEObject();
			if (eo instanceof ScriptInformation)
			{
				ScriptInformation si = (ScriptInformation) eo;
				mappingActivity = si.getActivity();
			}
		}
		if (mappingActivity != null)
		{
			boolean isCatch = false;
			RestMapperTreeItemFactory factory = RestMapperTreeItemFactory.getInstance();

			if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil.getEventTriggerType(mappingActivity))
					&& EventObjectUtil.isAttachedToTask(mappingActivity))
			{
				isCatch = true;
			}

			if (isCatch)
			{
				// TODO Handle Catch events
			}
			else
			{
				
				List<JsClassDefinitionReader> readers = readContributedDefinitionReaders(
						getProcessDestinationList(getProcess()));
				
				final IScriptRelevantData statusData = ProcessUtil.resolveBasicTypeToUML(
						getJsType(SwaggerPropertyType.INTEGER),
						false, readers, getTypeMap());
				// Add all success status codes
				if (statusData != null)
				{
					statusData.setName("REST_STATUS_CODE"); //$NON-NLS-1$
					setImage(statusData, RestSchemaImage.JSON_INTEGER_PROPERTY);
					data.add(statusData);
				}
				Collection<SwaggerResponseContainerTreeItem> swaggerResponses = factory
						.createSwaggerResponseItems(mappingActivity);
				if (!swaggerResponses.isEmpty())
				{
					for (SwaggerResponseContainerTreeItem response : swaggerResponses)
					{

						for (SwaggerTypedTreeItem child : response.getMappableContentItems())
						{
							// Add DefaultScriptRelevantData for primitive types - all header response params
							// and primitive payload types
							if (child instanceof SwaggerResponseParamTreeItem || child.getChildren().isEmpty())
							{
								SwaggerPropertyType paramType = child.getType();
								boolean isArray = child.isArray();
								IScriptRelevantData srd = ProcessUtil.resolveBasicTypeToUML(getJsType(child.getType()),
										isArray, readers);
								srd.setId(child.getText() + "_" + child.hashCode());
								srd.setName(child.getPath());
								setImage(srd, getImageKey(paramType, isArray));
								data.add(srd);
							}
							else if (child instanceof SwaggerPayloadTreeItem) {

								// Add RestUMLScriptRelevantData for Payload

								IScriptRelevantData relevantData = CdsSwaggerResponseScriptWrapperFactory.getDefault()
										.createScriptWrapper((SwaggerPayloadTreeItem) child, readers,
												response.getStatusCode());
								data.add(relevantData);

							}
						}

					}
				}
			}

		}
		return data;
	}


	/**
	 * @param statusData
	 * @param jsonIntegerProperty
	 */
	private void setImage(final IScriptRelevantData srd, final RestSchemaImage imageKey)
	{
		// May be called from validation rules so make sure we use
		// the UI thread to get the icon.
		Display.getDefault().syncExec(new Runnable()
		{
			public void run()
			{
				Image icon = RestSchemaUiPlugin.getDefault().getImage(imageKey);
				srd.setIcon(icon);
			}
		});

	}

	/**
	 * 
	 * @param type
	 * @param isArray
	 * @return
	 */
	private RestSchemaImage getImageKey(SwaggerPropertyType type, boolean isArray)
	{
		RestSchemaImage key = null;
		if (type != null)
		{
			switch (type)
			{
				case STRING:
					key = isArray ? RestSchemaImage.JSON_STRING_ARRAY_PROPERTY 
							: RestSchemaImage.JSON_STRING_PROPERTY;
					break;
				case INTEGER:
					key = isArray ? RestSchemaImage.JSON_INTEGER_ARRAY_PROPERTY 
							: RestSchemaImage.JSON_INTEGER_PROPERTY;
					break;
				case NUMBER:
					key = isArray ? RestSchemaImage.JSON_NUMBER_ARRAY_PROPERTY 
							: RestSchemaImage.JSON_NUMBER_PROPERTY;
					break;
				case DATE:
					key = isArray ? RestSchemaImage.JSON_DATE_TIME_ARRAY_PROPERTY
							: RestSchemaImage.JSON_DATE_TIME_PROPERTY;
					break;
				case DATETIME:
					key = isArray ? RestSchemaImage.JSON_DATE_TIME_ARRAY_PROPERTY
							: RestSchemaImage.JSON_DATE_TIME_PROPERTY;
					break;
				case BOOLEAN:
					key = isArray ? RestSchemaImage.JSON_BOOLEAN_ARRAY_PROPERTY 
							: RestSchemaImage.JSON_BOOLEAN_PROPERTY;
					break;
				case OBJECT:
					key = isArray ? RestSchemaImage.JSON_STRING_ARRAY_PROPERTY 
							: RestSchemaImage.JSON_STRING_PROPERTY;
					break;
			}
		}
		if (key == null)
		{
			key = RestSchemaImage.JSON_BASE_PROPERTY;
		}
		return key;
	}

	/**
	 * @return
	 */
	private Map<String, String> getTypeMap()
	{
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put(JsConsts.BOOLEAN, JsConsts.BOOLEAN);
		typeMap.put(JsConsts.DATE, JsConsts.DATE);
		typeMap.put(JsConsts.NUMBER, JsConsts.NUMBER);
		typeMap.put(JsConsts.INTEGER, JsConsts.INTEGER);
		typeMap.put(JsConsts.STRING, JsConsts.STRING);
		return typeMap;
	}

	/**
	 * @param type
	 * @return
	 */
	private String getJsType(SwaggerPropertyType type)
	{
		String jsType = null;
		switch (type)
		{
			case BOOLEAN:
				jsType = JsConsts.BOOLEAN;
				break;
			case DATE:
			case DATETIME:
				jsType = JsConsts.DATE;
				break;
			case NUMBER:
				jsType = JsConsts.DECIMAL;
				break;
			case INTEGER:
				jsType = JsConsts.INTEGER;
				break;
			case STRING:
				jsType = JsConsts.TEXT;
				break;
		}
		return jsType;
	}

	/**
	 * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#isInputScript()
	 * 
	 * @return
	 */
	@Override
	protected boolean isInputScript()
	{
		boolean isInput = true;
		Object context = getContext();
		ScriptDataMapper sdm = null;
		if (context instanceof DataMapping)
		{
			DataMapping dm = (DataMapping) context;
			sdm = (ScriptDataMapper) Xpdl2ModelUtil.getAncestor(dm, ScriptDataMapper.class);
		}
		else if (context instanceof ScriptInformation)
		{
			ScriptInformation si = (ScriptInformation) context;
			isInput = DirectionType.IN_LITERAL.equals(si.getDirection());
		}
		if (sdm != null)
		{
			EObject sdmContainer = sdm.eContainer();
			if (sdmContainer instanceof Message)
			{
				EObject messageContainer = sdmContainer.eContainer();
				if (messageContainer instanceof TaskService)
				{
					TaskService ts = (TaskService) messageContainer;
					if (sdmContainer == ts.getMessageOut())
					{
						isInput = false;
					}
				}
			}
			else if (sdmContainer instanceof ResultError)
			{
				isInput = false;
			}
		}
		return isInput;
	}

}
