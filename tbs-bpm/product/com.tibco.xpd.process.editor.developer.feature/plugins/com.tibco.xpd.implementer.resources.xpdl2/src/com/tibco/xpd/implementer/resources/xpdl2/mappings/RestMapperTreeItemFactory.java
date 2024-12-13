/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerInputParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.RestConceptPath;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;
import com.tibco.xpd.rsd.util.RsdModelUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultError;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

/**
 * Factory used to create (or resolve) REST service mapping tree items.
 * 
 * @author jarciuch
 * @since 8 Apr 2015
 */
@SuppressWarnings("nls")
public class RestMapperTreeItemFactory {

    private static final RestMapperTreeItemFactory INSTANCE =
            new RestMapperTreeItemFactory();

    /**
     * Stateless adapter instance.
     */
    private RestServiceTaskAdapter restServiceTaskAdapter =
            new RestServiceTaskAdapter();

    /**
     * Prevents instantiation. Use {@link #getInstance()} to obtain instance.
     */
    private RestMapperTreeItemFactory() {
    }

    /**
     * @return an instance of the factory.
     */
    public static RestMapperTreeItemFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Creates REST service parameter container tree item.
     * 
     * @param activity
     *            the context activity.
     * @param paramStyle
     *            the style of the parameter (will determine the type of the
     *            container).
     * @param direction
     *            the direction of mapping.
     * @return an instance of {@link RestParamContainerTreeItem} specific to the
     *         parameter style and mapping direction.
     */
    public RestParamContainerTreeItem createParamContainerTreeItem(
            Activity activity, ParameterStyle paramStyle,
            MappingDirection direction) {
        if (direction == MappingDirection.IN) {
            switch (paramStyle) {
            case PATH:
                return new RestParamContainerTreeItem(
                        activity,
                        Messages.RestMapperTreeItemFactory_PathParameters_label,
                        ParameterStyle.PATH, MappingDirection.IN) {
                    @Override
                    protected Collection<Parameter> getParams(Activity activity) {
                        Method rsoMethod =
                                restServiceTaskAdapter.getRSOMethod(activity);
                        if (rsoMethod != null
                                && rsoMethod.eContainer() instanceof Resource) {
                            return ((Resource) rsoMethod.eContainer())
                                    .getParameters();
                        }
                        return Collections.emptyList();
                    }
                };
            case QUERY:
                return new RestParamContainerTreeItem(
                        activity,
                        Messages.RestMapperTreeItemFactory_QueryParameters_label,
                        ParameterStyle.QUERY, MappingDirection.IN) {
                    @Override
                    protected Collection<Parameter> getParams(Activity activity) {
                        Method rsoMethod =
                                restServiceTaskAdapter.getRSOMethod(activity);
                        if (rsoMethod != null && rsoMethod.getRequest() != null) {
                            return rsoMethod.getRequest().getParameters();
                        }
                        return Collections.emptyList();
                    }
                };
            case HEADER:
                return new RestParamContainerTreeItem(
                        activity,
                        Messages.RestMapperTreeItemFactory_HeaderParameters_label,
                        ParameterStyle.HEADER, MappingDirection.IN) {
                    @Override
                    protected Collection<Parameter> getParams(Activity activity) {
                        Method rsoMethod =
                                restServiceTaskAdapter.getRSOMethod(activity);
                        if (rsoMethod != null && rsoMethod.getRequest() != null) {
                            return rsoMethod.getRequest().getParameters();
                        }
                        return Collections.emptyList();
                    }
                };

            default:
                assert false : String
                        .format("Unsupported parameter style: '%1$s'.", paramStyle); //$NON-NLS-1$
                return null;
            }
        } else if (direction == MappingDirection.OUT) {
            switch (paramStyle) {
            case HEADER:
                return new RestParamContainerTreeItem(
                        activity,
                        Messages.RestMapperTreeItemFactory_HeaderParameters_label,
                        ParameterStyle.HEADER, MappingDirection.OUT) {
                    @Override
                    protected Collection<Parameter> getParams(Activity activity) {
                        Method rsoMethod =
                                restServiceTaskAdapter.getRSOMethod(activity);
                        if (rsoMethod != null
                                && rsoMethod.getResponse() != null) {
                            return rsoMethod.getResponse().getParameters();
                        }
                        return Collections.emptyList();
                    }
                };

            default:
                assert false : String
                        .format("Unsupported parameter style: '%1$s'.", paramStyle); //$NON-NLS-1$
                return null;
            }
        }
        assert false : String
                .format("Unsupported mapping direction: '%1$s'.", direction); //$NON-NLS-1$
        return null;
    }

    /**
     * Creates rest payload container tree item.
     * 
     * @param activity
     *            the context activity.
     * @param direction
     *            the direction of mapping.
     * @return an instance of {@link RestPayloadContainerTreeItem} specific to
     *         the mapping direction.
     */
    public RestPayloadContainerTreeItem createPayloadContainerTreeItem(
            Activity activity, MappingDirection direction) {
        if (direction == MappingDirection.IN) {
            return new RestPayloadContainerTreeItem(activity,
                    Messages.RestMapperTreeItemFactory_Payload_label, direction) {
                @Override
                protected PayloadReference getPayloadReference(Activity activity) {
                    Method rsoMethod =
                            restServiceTaskAdapter.getRSOMethod(activity);
                    if (rsoMethod != null && rsoMethod.getRequest() != null) {
                        return rsoMethod.getRequest().getPayloadReference();
                    }
                    return null;
                }
            };
        } else if (direction == MappingDirection.OUT) {
            return new RestPayloadContainerTreeItem(activity,
                    Messages.RestMapperTreeItemFactory_Payload_label, direction) {
                @Override
                protected PayloadReference getPayloadReference(Activity activity) {
                    Method rsoMethod =
                            restServiceTaskAdapter.getRSOMethod(activity);
                    if (rsoMethod != null && rsoMethod.getResponse() != null) {
                        return rsoMethod.getResponse().getPayloadReference();
                    }
                    return null;
                }
            };
        }
        assert false : String
                .format("Unsupported mapping direction: '%1$s'.", direction); //$NON-NLS-1$
        return null;
    }

	/**
	 * Creates Swagger parameter container tree item
	 * 
	 * @param activity
	 * @param paramStyle
	 * @param direction
	 * @return
	 */
	/*
	 * TODO SID: IS there now anything really shared between RSD and Swagger handling in this class?
	 * 
	 * If not then may as well separate them(?)
	 */
	public SwaggerParamContainerTreeItem createSwaggerParamContainerTreeItem(Activity activity,
			ParameterStyle paramStyle, MappingDirection direction)
	{
		if (direction == MappingDirection.IN)
		{
			Operation rsoOperation = restServiceTaskAdapter.getRSOOperation(activity);
			
			List<io.swagger.v3.oas.models.parameters.Parameter> parameters = rsoOperation != null ? rsoOperation.getParameters() : null;

			SwaggerParamContainerTreeItem paramContainer = new SwaggerInputParamContainerTreeItem(null, activity,
					paramStyle, parameters);

			return paramContainer;
			
		}

		assert false : String.format("Unsupported mapping direction: '%1$s'.", direction); //$NON-NLS-1$
		return null;
	}

	/**
	 * Creates Swagger Payload container tree item
	 * 
	 * @param activity
	 * @param direction
	 * @return
	 */
	public SwaggerPayloadContainerTreeItem createSwaggerPayloadContainerTreeItem(Activity activity,
			MappingDirection direction)
	{
		if (direction == MappingDirection.IN)
		{
			Content content = null;
			boolean isRequired = false;

			Operation rsoOperation = restServiceTaskAdapter.getRSOOperation(activity);
			if (rsoOperation != null && rsoOperation.getRequestBody() != null)
			{
				content = rsoOperation.getRequestBody().getContent();
				isRequired = Boolean.TRUE.equals(rsoOperation.getRequestBody().getRequired());
			}

			return new SwaggerPayloadContainerTreeItem(null, activity, Messages.RestMapperTreeItemFactory_Payload_label,
					direction,
					RestMappingPrefix.PAYLOAD.getPrefix(), content, isRequired);

		}

		assert false : String.format("Unsupported mapping direction: '%1$s'.", direction); //$NON-NLS-1$
		return null;
	}

	/**
	 * Creates Tree Items for all Swagger success request tree items
	 * 
	 * @param activity
	 * 
	 * @return tree items for the service request for given activity
	 */
	public Collection<SwaggerContainerTreeItem> createSwaggerRequestItems(Activity activity)
	{
		ArrayList<SwaggerContainerTreeItem> children = new ArrayList<SwaggerContainerTreeItem>();

		// Add Path, Query and Header params containers.
		children.add(createSwaggerParamContainerTreeItem(activity, ParameterStyle.PATH, MappingDirection.IN));
		children.add(createSwaggerParamContainerTreeItem(activity, ParameterStyle.QUERY, MappingDirection.IN));
		children.add(createSwaggerParamContainerTreeItem(activity, ParameterStyle.HEADER, MappingDirection.IN));

		// Payload container.
		children.add(createSwaggerPayloadContainerTreeItem(activity, MappingDirection.IN));

		return children;
	}

	/**
	 * Creates Tree Items for all Swagger success response tree items (for each possible status Code 2xx)
	 * 
	 * @param activity
	 *
	 * @return tree items for the service reponse for given activity
	 */
	public Collection<SwaggerResponseContainerTreeItem> createSwaggerResponseItems(Activity activity)
	{
		List<SwaggerResponseContainerTreeItem> children = new ArrayList<SwaggerResponseContainerTreeItem>();

		if (activity != null)
		{
			Operation rsoOperation = restServiceTaskAdapter.getRSOOperation(activity);
			if (rsoOperation != null && rsoOperation.getResponses() != null)
			{
				ApiResponses responses = rsoOperation.getResponses();
				if (responses != null)
				{
					responses.forEach((statusCode, apiResponse) -> {
						if (isSuccessCode(statusCode) || ("default".equals(statusCode)))
						{
							children.add(createSwaggerResponseContainerTreeItem(activity, statusCode,
									apiResponse));
						}
					});
				}
			}
		}

		return children;
	}

	/**
	 * Returns true if the status code is a success code (all 2xx codes); false otherwise
	 * 
	 * @param statusCode
	 * @return
	 */
	private static boolean isSuccessCode(String statusCode)
	{
		try
		{
			int code = Integer.parseInt(statusCode);
			return code >= 200 && code < 300;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	/**
	 * Creates Swagger Response container tree item - containing Header and Payload Tree items
	 * 
	 * @param activity
	 * @param statusCode
	 * @param apiResponse
	 * 
	 * @return {@link SwaggerResponseContainerTreeItem}
	 */
	public SwaggerResponseContainerTreeItem createSwaggerResponseContainerTreeItem(Activity activity, String statusCode,
			ApiResponse apiResponse)
	{
		return new SwaggerResponseContainerTreeItem(activity, statusCode, apiResponse);
	}

    /**
     * Resolves parameter for the provided formalName (a.k.a. path) and mapping
     * direction.
     * 
     * @param activity
     *            the context activity.
     * @param direction
     *            direction of mapping.
     * @param formalName
     *            the name (path) of the mapping element (including prefix) as
     *            used in js mapping.
     * @return a tree item ({@link ConceptPath} or {@link RestParamTreeItem}) if
     *         the formalName can be resolved or 'null' otherwise.
     */
    public Object resolveParameter(Activity activity,
            MappingDirection direction, String formalName) {
        if (restServiceTaskAdapter.isCatchEvent(activity)) {
            // May be Catch Event for REST throw.
            String code = getCaughtErrorCode(activity);
            Activity thrower =
                    restServiceTaskAdapter.getThrowerActivity(activity);
            Fault fault = restServiceTaskAdapter.getRSOFault(thrower, code);
            if (fault != null) {
                UnprocessedTextRestMapperTreeItem jsonTreeItem =
                        createCatchJsonStringItem(activity,
                                thrower,
                                code,
                                formalName);
                if (jsonTreeItem != null) {
                    return jsonTreeItem;
                }
                ConceptPath payloadConceptPath =
                        createPayloadConceptPath(fault,
                                activity,
                                formalName,
                                direction);
                if (payloadConceptPath != null) {
                    return payloadConceptPath;
                }
            }
        } else {
            Method rsoMethod = restServiceTaskAdapter.getRSOMethod(activity);
            if (rsoMethod != null) {
                UnprocessedTextRestMapperTreeItem jsonTreeItem =
                        createJsonStringItem(activity, direction, formalName);
                if (jsonTreeItem != null) {
                    return jsonTreeItem;
                }
                if (direction == MappingDirection.IN) {
                    RestParamTreeItem pathParamTreeItem =
                            createParamTreeItem(ParameterStyle.PATH,
                                    (ParameterContainer) rsoMethod.eContainer(),
                                    activity,
                                    formalName,
                                    direction);
                    if (pathParamTreeItem != null) {
                        return pathParamTreeItem;
                    }

                    RestParamTreeItem queryParamTreeItem =
                            createParamTreeItem(ParameterStyle.QUERY,
                                    rsoMethod.getRequest(),
                                    activity,
                                    formalName,
                                    direction);
                    if (queryParamTreeItem != null) {
                        return queryParamTreeItem;
                    }

                    RestParamTreeItem headerParamTreeItem =
                            createParamTreeItem(ParameterStyle.HEADER,
                                    rsoMethod.getRequest(),
                                    activity,
                                    formalName,
                                    direction);
                    if (headerParamTreeItem != null) {
                        return headerParamTreeItem;
                    }

                    ConceptPath payloadConceptPath =
                            createPayloadConceptPath(rsoMethod.getRequest(),
                                    activity,
                                    formalName,
                                    direction);
                    if (payloadConceptPath != null) {
                        return payloadConceptPath;
                    }
                } else if (direction == MappingDirection.OUT) {
                    RestParamTreeItem headerParamTreeItem =
                            createParamTreeItem(ParameterStyle.HEADER,
                                    rsoMethod.getResponse(),
                                    activity,
                                    formalName,
                                    direction);
                    if (headerParamTreeItem != null) {
                        return headerParamTreeItem;
                    }

                    ConceptPath payloadConceptPath =
                            createPayloadConceptPath(rsoMethod.getResponse(),
                                    activity,
                                    formalName,
                                    direction);
                    if (payloadConceptPath != null) {
                        return payloadConceptPath;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Creates a JSON String tree item if the path is PAYLOAD_CONTAINER.JSON.
     * 
     * @param path
     *            The path.
     * @return The JsonStringRestMapperTreeItem if the path is correct.
     */
    private UnprocessedTextRestMapperTreeItem createJsonStringItem(
            Activity activity, MappingDirection direction, String path) {
        UnprocessedTextRestMapperTreeItem item = null;
        if (UnprocessedTextRestMapperTreeItem.JSON_PATH.equals(path)) {
            RestPayloadContainerTreeItem parent =
                    createPayloadContainerTreeItem(activity, direction);
            item = new UnprocessedTextRestMapperTreeItem(parent, activity);
        }
        return item;
    }

    /**
     * Creates a JSON String tree item if the path is PAYLOAD_CONTAINER.JSON.
     * 
     * @param path
     *            The path.
     * @return The JsonStringRestMapperTreeItem if the path is correct.
     */
    private UnprocessedTextRestMapperTreeItem createCatchJsonStringItem(
            Activity activity, Activity thrower, String statusCode, String path) {
        UnprocessedTextRestMapperTreeItem item = null;
        if (UnprocessedTextRestMapperTreeItem.JSON_PATH.equals(path)) {
            RestPayloadContainerTreeItem parent =
                    createCatchPayloadContainerTreeItem(activity,
                            thrower,
                            statusCode);
            item = new UnprocessedTextRestMapperTreeItem(parent, activity);
        }
        return item;
    }

    /**
     * @param parent
     *            The parent parameter tree item.
     * @param param
     *            The child parameter.
     * @param activity
     *            The mapping activity.
     * @param direction
     *            The mapping direction.
     * @return The child parameter tree item.
     */
    private RestParamTreeItem createParamTreeItem(RestMapperTreeItem parent,
            Parameter param, Activity activity, MappingDirection direction) {
        assert param != null;
        assert activity != null;
        RestParamContainerTreeItem paramContainer = null;
        if (parent == null) {
            paramContainer =
                    createParamContainerTreeItem(activity,
                            param.getStyle(),
                            direction);
        }
        return new RestParamTreeItem(paramContainer, param, activity, direction);
    }

    /**
     * @param paramStyle
     *            The type of parameter.
     * @param paramContainer
     *            The parameter container.
     * @param activity
     *            The mapping activity.
     * @param prefixedParamName
     *            The parameter name including prefix.
     * @param direction
     *            The mapping direction.
     * @return A mapping tree item for the parameter.
     */
    private RestParamTreeItem createParamTreeItem(ParameterStyle paramStyle,
            ParameterContainer paramContainer, Activity activity,
            String prefixedParamName, MappingDirection direction) {
        RestMappingPrefix paramPrefix =
                RestMappingPrefix.getForParamStyle(paramStyle);
        if (paramPrefix != null
                && paramPrefix.startsWithPrefix(prefixedParamName)
                && paramContainer != null) {
            String paramName = paramPrefix.removePrefix(prefixedParamName);
            Parameter param =
                    RsdModelUtil.findParameterByName(paramContainer,
                            paramName,
                            paramStyle);
            if (param != null) {
                return createParamTreeItem(null, param, activity, direction);
            }
        }
        return null;
    }

    /**
     * @param payloadRefContainer
     *            The payload reference.
     * @param activity
     *            The mapping activity.
     * @param prefixedPath
     *            The path including prefix.
     * @param direction
     *            The mapping direction.
     * @return a ConceptPath for the payload reference.
     */
    private ConceptPath createPayloadConceptPath(
            PayloadRefContainer payloadRefContainer, Activity activity,
            String prefixedPath, MappingDirection direction) {
        if (RestMappingPrefix.PAYLOAD.startsWithPrefix(prefixedPath)
                && payloadRefContainer != null
                && payloadRefContainer.getPayloadReference() != null) {
            PayloadReference payloadReference =
                    payloadRefContainer.getPayloadReference();
            JsonTypeReference jsonSchemaRef =
                    JsonTypeReference.getJsonReference(payloadReference);
            IProject contextProject =
                    WorkingCopyUtil.getProjectFor(payloadRefContainer);
            Classifier root =
                    jsonSchemaRef.resolveReference(XpdResourcesPlugin
                            .getDefault().getEditingDomain(), contextProject);
            if (root != null) {
                String path =
                        RestMappingPrefix.PAYLOAD.removePrefix(prefixedPath);
                path = path.startsWith(".") ? path.substring(1) : path; //$NON-NLS-1$
                String[] pathElements =
                        path.isEmpty() ? new String[0] : path.split("\\."); //$NON-NLS-1$
                RestPayloadContainerTreeItem payloadContainer = null;
                if (payloadRefContainer instanceof Fault) {
                    Activity thrower =
                            restServiceTaskAdapter.getThrowerActivity(activity);
                    String code = getCaughtErrorCode(activity);
                    payloadContainer =
                            createCatchPayloadContainerTreeItem(activity,
                                    thrower,
                                    code);
                } else {
                    payloadContainer =
                            createPayloadContainerTreeItem(activity, direction);
                }
                try {
                    return resolveConceptPath(new RestConceptPath(
                            RestMappingPrefix.PAYLOAD.getPrefix(),
                            payloadContainer, root, payloadReference.isArray()),
                            pathElements,
                            0);
                } catch (CoreException e) {
                    Activator.getDefault().getLog().log(e.getStatus());
                }
            }
        }
        return null;
    }

    /**
     * Recursively resolves a concept path relative to a parent path.
     * 
     * @param parent
     *            The parent concept path.
     * @param pathElements
     *            The path elements to resolve.
     * @param index
     *            The current index.
     * @return The resolved concept path.
     * @throws CoreException
     *             if the path was not found.
     */
    private ConceptPath resolveConceptPath(ConceptPath parent,
            String[] pathElements, int index) throws CoreException {
        if (index == pathElements.length) {
            return parent;
        } else {
            String pathElement = pathElements[index];
            for (ConceptPath cp : parent.getChildren()) {
                if (pathElement.equals(cp.getName())) {
                    ConceptPath newParent =
                            new ConceptPath(parent, cp.getItem(), cp.getType());
                    newParent.setIncludeChi1ldrenOfArrays(true);
                    return resolveConceptPath(newParent,
                            pathElements,
                            index + 1);
                }
            }
        }

        return null;
    }

    /**
     * Creates a payload container tree item for use in the catch event fault
     * mapper.
     * 
     * @param activity
     *            The catch event activity.
     * @param thrower
     *            The activity throwing the error.
     * @param code
     *            The fault status code.
     * @return The payload container tree item.
     */
    public RestPayloadContainerTreeItem createCatchPayloadContainerTreeItem(
            Activity activity, Activity thrower, String code) {
        if (code != null) {
            Fault fault =
                    new RestServiceTaskAdapter().getRSOFault(thrower, code);
            if (fault != null) {
                final PayloadReference payloadRef = fault.getPayloadReference();
                return new RestPayloadContainerTreeItem(activity,
                        Messages.RestMapperTreeItemFactory_Payload_label,
                        MappingDirection.OUT) {
                    @Override
                    protected PayloadReference getPayloadReference(
                            Activity activity) {
                        return payloadRef;
                    }
                };
            }
        }
        return null;
    }

	/**
	 * ACE-8866: Creates Swagger Catch Response container tree item
	 * 
	 * @param activity The catch event activity.
	 * @param thrower  The activity throwing the error.
	 * @param code     The fault status code.
	 * 
	 * @return {@link SwaggerResponseContainerTreeItem}
	 */
	public SwaggerResponseContainerTreeItem createSwaggerCatchPesponseContainerTreeItem(Activity activity,
			Activity thrower, String code) {
		if (code != null) {
			Map<String, ApiResponse> rsoOperationFaults = restServiceTaskAdapter.getRSOOperationFaults(thrower);
			ApiResponse apiResponse = rsoOperationFaults.get(code);
			if (apiResponse != null) {
				return new SwaggerResponseContainerTreeItem(activity, code, apiResponse);
			}
		}
		return null;
	}
	
    /**
     * @param catchActivity
     *            The catch event.
     * @return The code caught by the event or null.
     */
    public String getCaughtErrorCode(Activity catchActivity) {
        String code = null;
        Event event = catchActivity.getEvent();
        if (event instanceof IntermediateEvent) {
            IntermediateEvent ie = (IntermediateEvent) event;
            ResultError error = ie.getResultError();
            if (error != null) {
                code = error.getErrorCode();
            }
        }
        return code;
    }
}
