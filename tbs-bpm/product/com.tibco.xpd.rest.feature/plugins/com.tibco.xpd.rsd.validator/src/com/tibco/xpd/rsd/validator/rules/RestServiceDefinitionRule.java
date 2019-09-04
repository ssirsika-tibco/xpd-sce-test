/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.validator.rules;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.internal.JsonSchemaIndexProvider;
import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat;
import com.tibco.xpd.rest.schema.ui.ISO8601DateFormat.ISO8601DateFormatType;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.NamedElement;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator.RestPathIssue;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator.RestPathIssueType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule for REST Service Definition files.
 * 
 * @author nwilson
 * @since 9 Mar 2015
 */
public class RestServiceDefinitionRule implements IValidationRule {

    private static final String INVALID_PATH = "rest.invalidPath"; //$NON-NLS-1$

    private static final String ENCODED_PATH = "rest.encodedPath"; //$NON-NLS-1$

    private static final String UNUSED_PATH_PARAMETER =
            "rest.unusedPathParameter"; //$NON-NLS-1$

    private static final String MISSING_PROJECT_REFERENCE =
            "rest.missingProjectReference"; //$NON-NLS-1$

    private static final String MISSING_TYPE_REFERENCE =
            "rest.missingTypeReference"; //$NON-NLS-1$

    private static final String PRIVATE_TYPE_REFERENCE = "rest.privateTypeReference"; //$NON-NLS-1$

    private static final String DUPLICATE_TYPE_REFERENCE =
            "rest.duplicateTypeReference"; //$NON-NLS-1$

    /** Resource path parameter '<paramname>' contains invalid characters */
    private static final String INVALID_RESOURCE_PATH_PARAM =
            "rest.invalidResourcePathParam"; //$NON-NLS-1$

    /** Request query parameter '<paramname>' contains invalid characters */
    private static final String INVALID_REQUEST_QUERY_PARAMETERS =
            "rest.invalidRequestQueryParameter"; //$NON-NLS-1$

    /** Request header parameter '<paramname>' contains invalid characters */
    private static final String INVALID_REQUEST_HEADER_PARAMETERS =
            "rest.invalidRequestHeaderParameter"; //$NON-NLS-1$

    /** Response header parameter '<paramname>' contains invalid characters */
    private static final String INVALID_RESPONSE_HEADER_PARAMETERS =
            "rest.invalidResponseHeaderParameter"; //$NON-NLS-1$

    private static final String NO_PARAMETER_TYPE = "rest.noParameterType"; //$NON-NLS-1$

    private static final String INVALID_PARAMETER_DEFAULT =
            "rest.invalidParameterDefault"; //$NON-NLS-1$

    private static final String MISSING_FAULT_CODE = "rest.missingFaultCode"; //$NON-NLS-1$

    private static final String DUPLICATE_FAULT_CODE =
            "rest.duplicateFaultCode"; //$NON-NLS-1$

    private static final String DUPLICATE_PARAMETER_NAME =
            "rest.duplicateParameterName"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return The Service class.
     */
    @Override
    public Class<?> getTargetClass() {
        return Service.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule
     *      #validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     *            The validation scope.
     * @param o
     *            The Service object to validate.
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        RestServicePathValidator validator = new RestServicePathValidator();

        Service service = (Service) o;
        validateContextPath(scope, service, validator);

        for (Resource resource : service.getResources()) {
            validatePathTemplate(scope, service, resource, validator);
        }
    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param validator
     */
    private void validateContextPath(IValidationScope scope, Service service,
            RestServicePathValidator validator) {

        RestPathIssue issue =
                validator.validate(service, service.getContextPath());

        if (RestPathIssueType.CONTAINS_URL_ENCODING.equals(issue.getType())) {
            /* Handle encoding issues as these have a separate quick fix. */
            addIssue(scope,
                    ENCODED_PATH,
                    service,
                    Collections.singleton(issue.getMessage()));

        } else if (!RestPathIssueType.OK.equals(issue.getType())) {
            addIssue(scope,
                    INVALID_PATH,
                    service,
                    Collections.singleton(issue.getMessage()));
        }

    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param resource
     *            The resource to validate.
     * @param validator
     */
    private void validatePathTemplate(IValidationScope scope, Service service,
            Resource resource, RestServicePathValidator validator) {

        RestPathIssue issue =
                validator.validate(resource, resource.getPathTemplate());

        if (RestPathIssueType.CONTAINS_URL_ENCODING.equals(issue.getType())) {
            /* Handle encoding issues as these have a separate quick fix. */
            addIssue(scope,
                    ENCODED_PATH,
                    resource,
                    Collections.singleton(issue.getMessage()));

        } else if (!RestPathIssueType.OK.equals(issue.getType())) {
            addIssue(scope,
                    INVALID_PATH,
                    resource,
                    Collections.singleton(issue.getMessage()));
        }

        validateUnusedParameters(scope, service, resource, validator);
        validateMethods(scope, service, resource);

    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param resource
     *            The resource to validate.
     */
    private void validateMethods(IValidationScope scope, Service service,
            Resource resource) {
        for (Method method : resource.getMethods()) {
            validateRequest(scope, service, method);
            validateResponse(scope, service, method);
            Set<String> codes = new HashSet<>();
            Set<String> duplicates = new HashSet<>();
            for (Fault fault : method.getFaults()) {
                String code = fault.getStatusCode();
                if (code == null || code.length() == 0) {
                    Collection<String> messages = new ArrayList<>();
                    messages.add(fault.getName());
                    messages.add(method.getName());
                    messages.add(service.getName());
                    addIssue(scope, MISSING_FAULT_CODE, method, messages);
                } else if (codes.contains(code)) {
                    duplicates.add(code);
                } else {
                    codes.add(code);
                }
                validateFault(scope, service, method, fault);
            }
            for (String duplicate : duplicates) {
                Collection<String> messages = new ArrayList<>();
                messages.add(duplicate);
                messages.add(method.getName());
                messages.add(service.getName());
                addIssue(scope, DUPLICATE_FAULT_CODE, method, messages);
            }
        }
    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param method
     *            The method to validate.
     */
    private void validateRequest(IValidationScope scope, Service service,
            Method method) {
        Request request = method.getRequest();
        PayloadReference ref = request.getPayloadReference();
        validatePayloadReference(scope, service, method, ref, "Request"); //$NON-NLS-1$
        validateParameters(scope, service, method, request, "Request"); //$NON-NLS-1$
    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param method
     *            The method to validate.
     */
    private void validateResponse(IValidationScope scope, Service service,
            Method method) {
        Response response = method.getResponse();
        PayloadReference ref = response.getPayloadReference();
        validatePayloadReference(scope, service, method, ref, "Response"); //$NON-NLS-1$
        validateParameters(scope, service, method, response, "Response"); //$NON-NLS-1$
    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param method
     *            The method to validate.
     * @param fault
     *            The fault to validate.
     */
    private void validateFault(IValidationScope scope, Service service,
            Method method, Fault fault) {
        PayloadReference ref = fault.getPayloadReference();
        validatePayloadReference(scope, service, method, ref, "Fault"); //$NON-NLS-1$
        validateParameters(scope, service, method, fault, "Fault"); //$NON-NLS-1$
    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param method
     *            The method to validate.
     * @param ref
     *            The payload reference to validate.
     * @param payloadType
     *            "Request", "Response" or "Fault" used in error message.
     */
    private void validatePayloadReference(IValidationScope scope,
            Service service, Method method, PayloadReference ref,
            String payloadType) {
        if (ref != null) {
            if (!JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE.equals(ref
                    .getRef())) {
                // Find in index
                String type = org.eclipse.uml2.uml.Class.class.getName();
                Map<String, String> additionalAttributes = new HashMap<>();
                additionalAttributes.put(JsonSchemaIndexProvider.TYPE_ID,
                        ref.getRef());
                // additionalAttributes.put(JsonSchemaIndexProvider.IS_ROOT,
                // Boolean.TRUE.toString());
                IndexerItem queryItem =
                        new IndexerItemImpl(null, type, null,
                                additionalAttributes);
                Collection<IndexerItem> results =
                        XpdResourcesPlugin
                                .getDefault()
                                .getIndexerService()
                                .query(JsonSchemaUtil.JSON_SCHEMA_INDEXER_ID,
                                        queryItem);

                int count = results.size();
                if (count == 1) {
                    // If it's there get the IProject
                    IndexerItem ii = results.iterator().next();
                    String itemProjectName =
                            ii.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);
                    if (itemProjectName != null) {
                        IProject project =
                                WorkingCopyUtil.getProjectFor(service);
                        IProject itemProject =
                                project.getWorkspace().getRoot()
                                        .getProject(itemProjectName);
                        // If IProject is the not same or is not in
                        // ProjectUtil.getReferencedProjectsHierarchy then throw
                        // an
                        // error as we have a missing project reference.
                        if (!project.equals(itemProject)) {
                            Set<IProject> projects = new HashSet<>();
                            ProjectUtil.getReferencedProjectsHierarchy(project,
                                    projects);
                            if (!projects.contains(itemProject)) {
                                Collection<String> messages = new ArrayList<>();
                                messages.add(itemProjectName);
                                messages.add(ii.getName());
                                addIssue(scope,
                                        MISSING_PROJECT_REFERENCE,
                                        method,
                                        messages);
                            }
                        }
                    }
                    if (!"true".equals(ii.get(JsonSchemaIndexProvider.IS_ROOT))) { //$NON-NLS-1$
                        Collection<String> messages = new ArrayList<>();
                        String methodName =
                                method.getName() == null ? method.getHttpMethod().getName() : method.getName();
                        messages.add(methodName);
                        messages.add(service.getName());
                        messages.add(payloadType);
                        addIssue(scope, PRIVATE_TYPE_REFERENCE, method, messages);
                    }
                } else if (count == 0) {
                    Collection<String> messages = new ArrayList<>();
                    String methodName =
                            method.getName() == null ? method.getHttpMethod()
                                    .getName() : method.getName();
                    messages.add(methodName);
                    messages.add(service.getName());
                    messages.add(payloadType);
                    addIssue(scope, MISSING_TYPE_REFERENCE, method, messages);
                } else if (count > 1) {
                    Collection<String> messages = new ArrayList<>();
                    String methodName =
                            method.getName() == null ? method.getHttpMethod()
                                    .getName() : method.getName();
                    messages.add(ref.getRef());
                    messages.add(payloadType);
                    messages.add(methodName);
                    messages.add(service.getName());
                    addIssue(scope, DUPLICATE_TYPE_REFERENCE, method, messages);
                }
            }
        }
    }

    /**
     * @param scope
     *            The validation scope.
     * @param resource
     *            The RSD Resource.
     * @param validator
     * @param templateNames
     *            Parameter names used in the template.
     */
    private void validateUnusedParameters(IValidationScope scope,
            Service service, Resource resource,
            RestServicePathValidator validator) {
        Set<String> templateNames =
                validator.getParamNamesFromPath(resource.getPathTemplate());

        if (templateNames != null) {
            Set<String> available = new HashSet<>();

            for (Parameter parameter : resource.getParameters()) {
                String name = parameter.getName();
                available.add(name);
                if (!templateNames.contains(name)) {
                    Collection<String> messages = new ArrayList<>();
                    messages.add(name);
                    addIssue(scope, UNUSED_PATH_PARAMETER, resource, messages);
                }
            }
        }

        validateParameters(scope, service, resource, resource, "Resource"); //$NON-NLS-1$
    }

    /**
     * @param scope
     *            The validation scope.
     * @param service
     *            The Service to validate.
     * @param parent
     *            The parent of the element to validate.
     * @param container
     *            The parameter container to validate.
     * @param containerType
     *            "Request", "Response" or "Fault" used in error message.
     */
    private void validateParameters(IValidationScope scope, Service service,
            NamedElement parent, ParameterContainer container,
            String containerType) {
        String parentName = parent.getName();
        if (parentName == null && parent instanceof Method) {
            parentName = ((Method) parent).getHttpMethod().getName();
        }
        Map<ParameterStyle, Set<String>> nameMap = new HashMap<>();
        Map<ParameterStyle, Set<String>> duplicateMap = new HashMap<>();
        for (Parameter parameter : container.getParameters()) {
            String name = parameter.getName();
            ParameterStyle paramStyle = parameter.getStyle();
            Set<String> names = nameMap.get(paramStyle);
            if (names == null) {
                names = new HashSet<>();
                nameMap.put(paramStyle, names);
            }
            if (names.contains(name)) {
                Set<String> duplicates = duplicateMap.get(paramStyle);
                if (duplicates == null) {
                    duplicates = new HashSet<>();
                    duplicateMap.put(paramStyle, duplicates);
                }
                duplicates.add(name);
            } else {
                names.add(name);
            }

            if (ParameterStyle.HEADER.equals(paramStyle)) {
                if (!isValidHeaderName(name)) {
                    Collection<String> messages = new ArrayList<>();
                    messages.add(name);
                    addIssue(scope,
                            container instanceof Response ? INVALID_RESPONSE_HEADER_PARAMETERS
                                    : INVALID_REQUEST_HEADER_PARAMETERS,
                            parent,
                            messages);
                }
            } else if (ParameterStyle.QUERY.equals(paramStyle)) {
                if (!isValidHeaderName(name)) {
                    Collection<String> messages = new ArrayList<>();
                    messages.add(name);
                    addIssue(scope,
                            INVALID_REQUEST_QUERY_PARAMETERS,
                            parent,
                            messages);
                }
            } else {
                /* Resource path params. */
                if (!NameUtil.isValidName(name, false)) {
                    Collection<String> messages = new ArrayList<>();
                    messages.add(name);
                    addIssue(scope,
                            INVALID_RESOURCE_PATH_PARAM,
                            parent,
                            messages);
                }
            }

            DataType type = parameter.getDataType();
            String def = parameter.getDefaultValue();
            if (type == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(name);
                messages.add(containerType);
                messages.add(parentName);
                messages.add(service.getName());
                addIssue(scope, NO_PARAMETER_TYPE, parent, messages);

            } else if (def != null && def.length() > 0) {
                String expected = null;
                switch (type) {
                case BOOLEAN:
                    if (!"true".equals(def) && !"false".equals(def)) { //$NON-NLS-1$ //$NON-NLS-2$
                        expected = "true|false"; //$NON-NLS-1$
                    }
                    break;
                case DATE:
                    ISO8601DateFormat dfd =
                            new ISO8601DateFormat(ISO8601DateFormatType.DATE);
                    try {
                        dfd.parse(def);
                    } catch (ParseException e) {
                        expected = "yyyy-MM-dd"; //$NON-NLS-1$
                    }
                    break;
                case DATE_TIME:
                    ISO8601DateFormat dfdt =
                            new ISO8601DateFormat(
                                    ISO8601DateFormatType.DATE_TIME);
                    try {
                        dfdt.parse(def);
                    } catch (ParseException e) {
                        expected = "yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]"; //$NON-NLS-1$
                    }
                    break;
                case TIME:
                    ISO8601DateFormat dft =
                            new ISO8601DateFormat(ISO8601DateFormatType.TIME);
                    try {
                        dft.parse(def);
                    } catch (ParseException e) {
                        expected = "HH:mm:ss[.SSS][XXX]"; //$NON-NLS-1$
                    }
                    break;
                case DECIMAL:
                    try {
                        Double.valueOf(def);
                    } catch (NumberFormatException e) {
                        expected = "nnn.nnn[ennn]"; //$NON-NLS-1$
                    }
                    break;
                case INTEGER:
                    try {
                        Long.valueOf(def);
                    } catch (NumberFormatException e) {
                        expected = "nnn"; //$NON-NLS-1$
                    }
                    break;
                default:
                }
                if (expected != null) {
                    Collection<String> messages = new ArrayList<>();
                    messages.add(expected);
                    messages.add(name);
                    messages.add(containerType);
                    messages.add(parentName);
                    messages.add(service.getName());
                    addIssue(scope, INVALID_PARAMETER_DEFAULT, parent, messages);
                }
            }
        }

        for (Entry<ParameterStyle, Set<String>> duplicateEntry : duplicateMap
                .entrySet()) {
            ParameterStyle style = duplicateEntry.getKey();
            String styleName = style.getName().toLowerCase();
            Set<String> duplicates = duplicateEntry.getValue();
            for (String duplicate : duplicates) {
                Collection<String> messages = new ArrayList<>();
                messages.add(styleName);
                messages.add(duplicate);
                messages.add(containerType);
                messages.add(parentName);
                messages.add(service.getName());
                addIssue(scope, DUPLICATE_PARAMETER_NAME, parent, messages);
            }
        }
    }

    /**
     * Header names can include any US-ASCII character except Control Characters
     * (octects 0 - 31), SP (space - 32), DEL (127) or Separator Characters:
     * 
     * ()<>@,;:\"/[]?={}
     * 
     * @return
     */
    private boolean isValidHeaderName(String name) {
        boolean isValid = true;
        for (char c : name.toCharArray()) {
            if (c < 33 || c == 34 || c == 40 || c == 41 || c == 44 || c == 47
                    || (c > 57 && c < 65) || (c > 90 && c < 94) || c == 123
                    || c == 125 || c == 127) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    /**
     * Returns a URI for an EObject.
     * 
     * @param eo
     *            The EObject.
     * @return The URI.
     */
    private String getUri(EObject eo) {
        return eo.eResource().getURIFragment(eo);
    }

    /**
     * Sid XPD-7755 Improved location reporting as part of this JIRA.
     * 
     * @param scope
     * @param issueId
     * @param affectedObject
     * @param messages
     */
    private void addIssue(IValidationScope scope, String issueId,
            EObject affectedObject, Collection<String> messages) {
        String location = "";

        EObject eo = affectedObject;
        while (eo != null) {
            if (eo instanceof NamedElement) {
                if (location.length() != 0) {
                    location = ((NamedElement) eo).getName() + "/" + location; //$NON-NLS-1$
                } else {
                    location = ((NamedElement) eo).getName();
                }
            }

            eo = eo.eContainer();
        }

        scope.createIssue(issueId, location, getUri(affectedObject), messages);

    }
}
