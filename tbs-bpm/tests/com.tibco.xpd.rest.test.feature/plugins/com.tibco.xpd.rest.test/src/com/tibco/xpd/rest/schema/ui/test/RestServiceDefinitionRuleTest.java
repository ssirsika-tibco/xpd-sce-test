/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.validator.rules.RestServiceDefinitionRule;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Tests for RestServiceDefinitionRule class.
 * 
 * @author nwilson
 * @since 2 Apr 2015
 */
public class RestServiceDefinitionRuleTest extends TestCase {
    public void testTargetClass() {
        RestServiceDefinitionRule rule = new RestServiceDefinitionRule();
        assertEquals(Service.class, rule.getTargetClass());
    }

    public void testMismatchedBraces1() {
        checkMismatchedBraces("", false); //$NON-NLS-1$
        checkMismatchedBraces("{}", false); //$NON-NLS-1$
        checkMismatchedBraces("{a}", false); //$NON-NLS-1$
        checkMismatchedBraces("{a}{b}", false); //$NON-NLS-1$
        checkMismatchedBraces("{a}/{b}", false); //$NON-NLS-1$
        checkMismatchedBraces("{", true); //$NON-NLS-1$
        checkMismatchedBraces("{a", true); //$NON-NLS-1$
        checkMismatchedBraces("}", true); //$NON-NLS-1$
        checkMismatchedBraces("}{", true); //$NON-NLS-1$
        checkMismatchedBraces("a}{", true); //$NON-NLS-1$
        checkMismatchedBraces("}a{", true); //$NON-NLS-1$
        checkMismatchedBraces("}{a", true); //$NON-NLS-1$
        checkMismatchedBraces("{a}}", true); //$NON-NLS-1$
        checkMismatchedBraces("{a{}", true); //$NON-NLS-1$
        checkMismatchedBraces("{a{}}", true); //$NON-NLS-1$
    }

    public void testUnusedPathParameter() {
        checkUnusedPathParameter("path", "param", true); //$NON-NLS-1$ //$NON-NLS-2$
        checkUnusedPathParameter("{param}", "param", false); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testInvalidPathParameter() {
        checkInvalidPathParameter("{param}{abc}", "abc", true); //$NON-NLS-1$ //$NON-NLS-2$
        checkInvalidPathParameter("{param}", "param", false); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testParameters() {
        String INVALID_NAME = "rest.invalidResourcePathParam"; //$NON-NLS-1$
        String NO_TYPE = "rest.noParameterType"; //$NON-NLS-1$
        String INVALID_DEFAULT = "rest.invalidParameterDefault"; //$NON-NLS-1$
        checkParameter("a", DataType.TEXT, "text", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("1a", DataType.TEXT, "text", INVALID_NAME); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", null, "text", NO_TYPE); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.BOOLEAN, "true", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.BOOLEAN, "false", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.BOOLEAN, "text", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DATE, "2015-10-08", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DATE, "2015/10/08", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DATE_TIME, "2015-10-08T11:12:13", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DATE_TIME, "2015-10-08", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DECIMAL, "123", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DECIMAL, "123.456", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DECIMAL, "123.456e789", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.DECIMAL, "text", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.INTEGER, "123", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.INTEGER, "123.456", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.TIME, "10:11:12", null); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.TIME, "10:11", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.TIME, "10:11:12s123", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
        checkParameter("a", DataType.TIME, "10:00am", INVALID_DEFAULT); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * 
     */
    private void checkParameter(String name, DataType type,
            String defaultValue, String errorId) {
        String uri = "uri"; //$NON-NLS-1$
        String path = "path"; //$NON-NLS-1$
        String methodName = "method"; //$NON-NLS-1$
        String resourceName = "resource"; //$NON-NLS-1$
        RestServiceDefinitionRule rule = new RestServiceDefinitionRule();
        IValidationScope scope = mock(IValidationScope.class);
        Service service = mock(Service.class);
        Resource resource = mock(Resource.class);
        Method method = mock(Method.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        org.eclipse.emf.ecore.resource.Resource eResource =
                mock(org.eclipse.emf.ecore.resource.Resource.class);
        EList<Resource> resources =
                (EList<Resource>) ECollections.singletonEList(resource);
        Parameter parameter = mock(Parameter.class);
        when(service.getResources()).thenReturn(resources);
        when(resource.getPathTemplate()).thenReturn(path);
        EList<Parameter> pathParameters = ECollections.emptyEList();
        EList<Parameter> responseParameters = ECollections.emptyEList();
        EList<Parameter> requestParameters =
                (EList<Parameter>) ECollections.singletonEList(parameter);
        EList<Method> methods =
                (EList<Method>) ECollections.singletonEList(method);
        EList<Fault> faults = ECollections.emptyEList();
        when(resource.getParameters()).thenReturn(pathParameters);
        when(resource.getMethods()).thenReturn(methods);
        when(resource.getName()).thenReturn(resourceName);
        when(method.getRequest()).thenReturn(request);
        when(method.getResponse()).thenReturn(response);
        when(method.getName()).thenReturn(methodName);
        when(method.getFaults()).thenReturn(faults);
        when(method.eResource()).thenReturn(eResource);
        when(request.getParameters()).thenReturn(requestParameters);
        when(response.getParameters()).thenReturn(responseParameters);
        when(parameter.getName()).thenReturn(name);
        when(parameter.getDataType()).thenReturn(type);
        when(parameter.getDefaultValue()).thenReturn(defaultValue);
        when(eResource.getURIFragment(method)).thenReturn(uri);
        rule.validate(scope, service);
        if (errorId == null) {
            verify(scope, times(0)).createIssue(anyString(),
                    anyString(),
                    eq(uri),
                    anyCollectionOf(String.class));
        } else {
            verify(scope, times(1)).createIssue(eq(errorId),
                    anyString(),
                    eq(uri),
                    anyCollectionOf(String.class));
        }
    }

    /**
     * @param path
     * @param paramName
     */
    private void checkUnusedPathParameter(String path, String paramName,
            boolean errorExpected) {
        String uri = "uri"; //$NON-NLS-1$
        String resourceName = "resource"; //$NON-NLS-1$
        RestServiceDefinitionRule rule = new RestServiceDefinitionRule();
        IValidationScope scope = mock(IValidationScope.class);
        Service service = mock(Service.class);
        Resource resource = mock(Resource.class);
        org.eclipse.emf.ecore.resource.Resource eResource =
                mock(org.eclipse.emf.ecore.resource.Resource.class);
        EList<Resource> resources =
                (EList<Resource>) ECollections.singletonEList(resource);
        Parameter parameter = mock(Parameter.class);
        when(service.getResources()).thenReturn(resources);
        when(resource.getPathTemplate()).thenReturn(path);
        when(resource.eResource()).thenReturn(eResource);
        EList<Parameter> parameters =
                (EList<Parameter>) ECollections.singletonEList(parameter);
        EList<Method> methods = ECollections.emptyEList();
        when(resource.getParameters()).thenReturn(parameters);
        when(resource.getMethods()).thenReturn(methods);
        when(resource.getName()).thenReturn(resourceName);
        when(parameter.getName()).thenReturn(paramName);
        when(eResource.getURIFragment(resource)).thenReturn(uri);
        rule.validate(scope, service);
        Collection<String> messages = new ArrayList<>();
        messages.add(paramName);
        String errorId = "rest.unusedPathParameter"; //$NON-NLS-1$
        if (errorExpected) {
            verify(scope, times(1)).createIssue(errorId,
                    resourceName,
                    uri,
                    messages);
        } else {
            verify(scope, times(0)).createIssue(eq(errorId),
                    anyString(),
                    eq(uri),
                    anyCollectionOf(String.class));
        }
    }

    /**
     * @param path
     * @param paramName
     */
    private void checkInvalidPathParameter(String path, String paramName,
            boolean errorExpected) {
        String uri = "uri"; //$NON-NLS-1$
        String resourceName = "resource"; //$NON-NLS-1$
        RestServiceDefinitionRule rule = new RestServiceDefinitionRule();
        IValidationScope scope = mock(IValidationScope.class);
        Service service = mock(Service.class);
        Resource resource = mock(Resource.class);
        org.eclipse.emf.ecore.resource.Resource eResource =
                mock(org.eclipse.emf.ecore.resource.Resource.class);
        EList<Resource> resources =
                (EList<Resource>) ECollections.singletonEList(resource);
        Parameter parameter = mock(Parameter.class);
        when(service.getResources()).thenReturn(resources);
        when(resource.getPathTemplate()).thenReturn(path);
        when(resource.eResource()).thenReturn(eResource);
        EList<Parameter> parameters =
                (EList<Parameter>) ECollections.singletonEList(parameter);
        EList<Method> methods = ECollections.emptyEList();
        when(resource.getParameters()).thenReturn(parameters);
        when(resource.getMethods()).thenReturn(methods);
        when(resource.getName()).thenReturn(resourceName);
        when(parameter.getName()).thenReturn(paramName);
        when(eResource.getURIFragment(resource)).thenReturn(uri);
        rule.validate(scope, service);
        Collection<String> messages = new ArrayList<>();
        messages.add(paramName);
        String errorId = "rest.invalidPath"; //$NON-NLS-1$
        if (errorExpected) {
            verify(scope, times(1)).createIssue(eq(errorId),
                    anyString(),
                    eq(uri),
                    anyCollectionOf(String.class));
        } else {
            verify(scope, times(0)).createIssue(eq(errorId),
                    anyString(),
                    eq(uri),
                    anyCollectionOf(String.class));
        }
    }

    /**
     * @param string
     */
    private void checkMismatchedBraces(String path, boolean mismatchExpected) {
        String uri = "uri"; //$NON-NLS-1$
        RestServiceDefinitionRule rule = new RestServiceDefinitionRule();
        IValidationScope scope = mock(IValidationScope.class);
        Service service = mock(Service.class);
        Resource resource = mock(Resource.class);
        org.eclipse.emf.ecore.resource.Resource eResource =
                mock(org.eclipse.emf.ecore.resource.Resource.class);
        EList<Resource> resources =
                (EList<Resource>) ECollections.singletonEList(resource);
        when(service.getResources()).thenReturn(resources);
        when(resource.getPathTemplate()).thenReturn(path);
        when(resource.eResource()).thenReturn(eResource);
        EList<Parameter> parameters = ECollections.emptyEList();
        EList<Method> methods = ECollections.emptyEList();
        when(resource.getParameters()).thenReturn(parameters);
        when(resource.getMethods()).thenReturn(methods);
        when(eResource.getURIFragment(resource)).thenReturn(uri);
        rule.validate(scope, service);
        String errorId = "rest.invalidPath"; //$NON-NLS-1$
        if (mismatchExpected) {
            Collection<String> messages =
                    Collections
                            .singleton("The resource path contains unmatched brackets.");
            verify(scope, times(1)).createIssue(errorId, null, uri, messages);
        } else {
            verify(scope, times(0)).createIssue(eq(errorId),
                    anyString(),
                    eq(uri));
        }
    }
}
