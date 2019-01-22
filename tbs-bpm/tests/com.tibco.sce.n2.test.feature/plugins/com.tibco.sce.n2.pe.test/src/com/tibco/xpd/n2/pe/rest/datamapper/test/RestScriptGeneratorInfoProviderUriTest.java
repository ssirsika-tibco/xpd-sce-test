/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.rest.datamapper.test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tibco.xpd.datamapper.api.JavaScriptStringBuilder;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamTreeItem;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.RestScriptGeneratorInfoProvider;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Tests for RestScriptGeneratorInfoProvider buildUri method. This tests
 * generation of the URI builder script for a number of different scenarios.
 * 
 * @author nwilson
 * @since 13 May 2015
 */
public class RestScriptGeneratorInfoProviderUriTest extends TestCase {
    private static final String CONTEXT = "/context/"; //$NON-NLS-1$

    private static final String STATIC_PATH = "static/path"; //$NON-NLS-1$

    private static final String PARAM_NAME = "param"; //$NON-NLS-1$

    private static final String PARAM_PATH = "path"; //$NON-NLS-1$

    private JavaScriptStringBuilder jssb;

    private JavaScriptStringBuilder initScript;

    @Mock
    private RestMapperTreeItemFactory factory;

    @Mock
    private Activity activity;

    @Mock
    private Method method;

    @Mock
    private Resource resource;

    @Mock
    private Service service;

    @Mock
    private RestParamContainerTreeItem treeItems;

    @Mock
    private RestParamTreeItem paramTreeItem;

    @Mock
    private Parameter param;

    @InjectMocks
    private RestScriptGeneratorInfoProvider generator;

    private static ScriptExecutor executor;

    /**
     * Inject and initialse mocks.
     * 
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jssb = new JavaScriptStringBuilder();
        initScript = new JavaScriptStringBuilder();
        generator.appendQueryUriFunctions(initScript);
        when(method.eContainer()).thenReturn(resource);
        when(resource.eContainer()).thenReturn(service);

        try {
            executor = new ScriptExecutor();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Test for empty context & path with no query params.
     */
    public void testBuildUriNoContextNoPathNoQuery() {
        generator.buildUri(jssb, factory, activity, method);
        assertEquals("''", jssb.toString()); //$NON-NLS-1$
    }

    /**
     * Test for populated context path, but no path template or query params.
     */
    public void testBuildUriContextNoPathNoQuery() {
        when(service.getContextPath()).thenReturn(CONTEXT);
        generator.buildUri(jssb, factory, activity, method);
        assertEquals("encodeURI('" + CONTEXT + "')", jssb.toString()); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test for empty context, populated static path and no query params.
     */
    public void testBuildUriNoContextStaticPathNoQuery() {
        when(resource.getPathTemplate()).thenReturn(STATIC_PATH);
        generator.buildUri(jssb, factory, activity, method);
        assertEquals("encodeURI('" + STATIC_PATH + "')", jssb.toString()); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test for empty context, populated static path and no query params.
     */
    public void testBuildUriNoContextDynamicPathNoQuery() {
        List<RestMapperTreeItem> pathItemList = new ArrayList<>();
        pathItemList.add(paramTreeItem);
        when(resource.getPathTemplate()).thenReturn("/path/{param}"); //$NON-NLS-1$
        when(factory.createParamContainerTreeItem(activity,
                ParameterStyle.PATH,
                MappingDirection.IN)).thenReturn(treeItems);
        when(treeItems.getChildren()).thenReturn(pathItemList);
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_PATH);
        when(param.getName()).thenReturn(PARAM_NAME);
        generator.buildUri(jssb, factory, activity, method);
        initScript.append("var path = 'abc'"); //$NON-NLS-1$
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "/path/abc"); //$NON-NLS-1$
    }

    /**
     * Test for no context or path template with one query param.
     */
    public void testBuildUriNoContextNoPathQuery() {
        List<RestMapperTreeItem> queryItemList = new ArrayList<>();
        queryItemList.add(paramTreeItem);
        when(factory.createParamContainerTreeItem(activity,
                ParameterStyle.QUERY,
                MappingDirection.IN)).thenReturn(treeItems);
        when(treeItems.getChildren()).thenReturn(queryItemList);
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_PATH);
        when(param.getName()).thenReturn(PARAM_NAME);
        generator.buildUri(jssb, factory, activity, method);
        initScript.append("var path = 'abc'"); //$NON-NLS-1$
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "?param=abc"); //$NON-NLS-1$
    }

    /**
     * Test with slash on end of the context, but no slash on the start of the
     * template path.
     */
    public void testBuildUriSlashOnContext() {
        when(service.getContextPath()).thenReturn("context/"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("template"); //$NON-NLS-1$
        generator.buildUri(jssb, factory, activity, method);
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template"); //$NON-NLS-1$

    }

    /**
     * Test with no slash on the end of the context, but a slash on the start of
     * the template path.
     */
    public void testBuildUriSlashOnTemplate() {
        when(service.getContextPath()).thenReturn("context"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("/template"); //$NON-NLS-1$
        generator.buildUri(jssb, factory, activity, method);
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template"); //$NON-NLS-1$
    }

    /**
     * Test with no slash on the end of the context path or on the start of the
     * template path.
     */
    public void testBuildUriNoSlash() {
        when(service.getContextPath()).thenReturn("context"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("template"); //$NON-NLS-1$
        generator.buildUri(jssb, factory, activity, method);
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template"); //$NON-NLS-1$
    }

    /**
     * Test with a slash on the end of the context path and on the start of the
     * template path.
     */
    public void testBuildUriDoubleSlash() {
        when(service.getContextPath()).thenReturn("context/"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("/template"); //$NON-NLS-1$
        generator.buildUri(jssb, factory, activity, method);
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context//template"); //$NON-NLS-1$
    }

    /**
     * Test mandatory non-null query parameters are included.
     */
    public void testBuildUriMandatoryNonNullQueryParam() {
        List<RestMapperTreeItem> queryItemList = new ArrayList<>();
        queryItemList.add(paramTreeItem);
        when(service.getContextPath()).thenReturn("context/"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("template"); //$NON-NLS-1$
        when(factory.createParamContainerTreeItem(activity,
                ParameterStyle.QUERY,
                MappingDirection.IN)).thenReturn(treeItems);
        when(treeItems.getChildren()).thenReturn(queryItemList);
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_NAME);
        when(param.getName()).thenReturn(PARAM_NAME);
        when(param.isMandatory()).thenReturn(true);

        generator.buildUri(jssb, factory, activity, method);
        initScript.append("var " + PARAM_NAME + "='abc'"); //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template?param=abc"); //$NON-NLS-1$
    }

    /**
     * Test mandatory null query parameters are included.
     */
    public void testBuildUriMandatoryNullQueryParam() {
        List<RestMapperTreeItem> queryItemList = new ArrayList<>();
        queryItemList.add(paramTreeItem);
        when(service.getContextPath()).thenReturn("context/"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("template"); //$NON-NLS-1$
        when(factory.createParamContainerTreeItem(activity,
                ParameterStyle.QUERY,
                MappingDirection.IN)).thenReturn(treeItems);
        when(treeItems.getChildren()).thenReturn(queryItemList);
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_NAME);
        when(param.getName()).thenReturn(PARAM_NAME);
        when(param.isMandatory()).thenReturn(true);

        generator.buildUri(jssb, factory, activity, method);
        initScript.append("var " + PARAM_NAME + "=null"); //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template?param=null"); //$NON-NLS-1$
    }

    /**
     * Test optional non-null query parameters are included.
     */
    public void testBuildUriOptionalNonNullQueryParam() {
        List<RestMapperTreeItem> queryItemList = new ArrayList<>();
        queryItemList.add(paramTreeItem);
        when(service.getContextPath()).thenReturn("context/"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("template"); //$NON-NLS-1$
        when(factory.createParamContainerTreeItem(activity,
                ParameterStyle.QUERY,
                MappingDirection.IN)).thenReturn(treeItems);
        when(treeItems.getChildren()).thenReturn(queryItemList);
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_NAME);
        when(param.getName()).thenReturn(PARAM_NAME);
        when(param.isMandatory()).thenReturn(false);

        generator.buildUri(jssb, factory, activity, method);
        initScript.append("var " + PARAM_NAME + "='abc'"); //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template?param=abc"); //$NON-NLS-1$
    }

    /**
     * Test optional null query parameters are included.
     */
    public void testBuildUriOptionalNullQueryParam() {
        List<RestMapperTreeItem> queryItemList = new ArrayList<>();
        queryItemList.add(paramTreeItem);
        when(service.getContextPath()).thenReturn("context/"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("template"); //$NON-NLS-1$
        when(factory.createParamContainerTreeItem(activity,
                ParameterStyle.QUERY,
                MappingDirection.IN)).thenReturn(treeItems);
        when(treeItems.getChildren()).thenReturn(queryItemList);
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_NAME);
        when(param.getName()).thenReturn(PARAM_NAME);
        when(param.isMandatory()).thenReturn(false);

        generator.buildUri(jssb, factory, activity, method);
        initScript.append("var " + PARAM_NAME + "=null"); //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template"); //$NON-NLS-1$
    }

    /**
     * Test a mix of mandatory/optional and null/non-null query parameters.
     */
    public void testBuildUriMixedQueryParams() {
        List<RestMapperTreeItem> queryItemList = new ArrayList<>();
        queryItemList.add(createTreeItem("param1", true));
        queryItemList.add(createTreeItem("param2", true));
        queryItemList.add(createTreeItem("param3", false));
        queryItemList.add(createTreeItem("param4", false));
        when(service.getContextPath()).thenReturn("context/"); //$NON-NLS-1$
        when(resource.getPathTemplate()).thenReturn("template"); //$NON-NLS-1$
        when(factory.createParamContainerTreeItem(activity,
                ParameterStyle.QUERY,
                MappingDirection.IN)).thenReturn(treeItems);
        when(treeItems.getChildren()).thenReturn(queryItemList);

        generator.buildUri(jssb, factory, activity, method);
        initScript.append(
                "var param1='abc';var param2=null;var param3='def';var param4=null"); //$NON-NLS-1$
        executor.verifyScript("var uri = " + jssb.toString(), //$NON-NLS-1$
                initScript.toString(),
                "uri", //$NON-NLS-1$
                "context/template?param1=abc&param2=null&param3=def"); //$NON-NLS-1$
    }

    /**
     * Create a mock RestParamTreeItem.
     * 
     * @param name
     *            The param name (also used as the path)
     * @param mandatory
     *            true if mandatory.
     * @return a mock RestParamTreeItem.
     */
    private RestParamTreeItem createTreeItem(String name, boolean mandatory) {
        RestParamTreeItem item = Mockito.mock(RestParamTreeItem.class);
        Parameter itemParam = Mockito.mock(Parameter.class);
        when(item.getParam()).thenReturn(itemParam);
        when(item.getPath()).thenReturn(name);
        when(itemParam.getName()).thenReturn(name);
        when(itemParam.isMandatory()).thenReturn(mandatory);
        return item;
    }
}
