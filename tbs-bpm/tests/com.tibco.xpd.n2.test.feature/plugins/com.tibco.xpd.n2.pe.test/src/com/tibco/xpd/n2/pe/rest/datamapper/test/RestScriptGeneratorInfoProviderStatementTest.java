/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.rest.datamapper.test;

import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.uml2.uml.PrimitiveType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamTreeItem;
import com.tibco.xpd.n2.pe.rest.datamapper.RestScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.RestConceptPath;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.xpdl2.Activity;

import junit.framework.TestCase;

/**
 * Tests for RestScriptGeneratorInfoProvider buildUri method. This tests
 * generation of the URI builder script for a number of different scenarios.
 * 
 * @author nwilson
 * @since 13 May 2015
 */
public class RestScriptGeneratorInfoProviderStatementTest extends TestCase {
    private static final String PARAM_NAME = "param"; //$NON-NLS-1$

    private static final String PARAM_PATH = "path"; //$NON-NLS-1$

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

    @Mock
    private RestConceptPath conceptPath;

    @Mock
    private PrimitiveType primitive;

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
        when(method.eContainer()).thenReturn(resource);
        when(resource.eContainer()).thenReturn(service);

        executor = new ScriptExecutor();
    }

    /**
     * Test assignment script for DateTime mapping.
     */
    public void testGetAssignmentStatementDateTime() throws Exception {
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_PATH);
        when(param.getName()).thenReturn(PARAM_NAME);
        when(param.getDataType()).thenReturn(DataType.DATE_TIME);
        String statement =
                generator.getAssignmentStatement(paramTreeItem, "rhs", null); //$NON-NLS-1$
        String expected = "2019-06-18T15:39:42.088Z"; //$NON-NLS-1$
        String paramScript = "var rhs = new Date('" + expected + "');"; //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript(statement, paramScript, PARAM_PATH, expected);
    }

    /**
     * Test assignment script for Date mapping.
     */
    public void testGetAssignmentStatementDate() throws Exception {
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_PATH);
        when(param.getName()).thenReturn(PARAM_NAME);
        when(param.getDataType()).thenReturn(DataType.DATE);
        String statement =
                generator.getAssignmentStatement(paramTreeItem, "rhs", null); //$NON-NLS-1$
        String expected = "2019-06-18T15:39:42.088Z"; //$NON-NLS-1$
        String paramScript = "var rhs = new Date('" + expected + "');"; //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript(statement, paramScript, PARAM_PATH, expected);
    }

    /**
     * Test assignment script for Time mapping.
     */
    public void testGetAssignmentStatementTime() throws Exception {
        when(paramTreeItem.getParam()).thenReturn(param);
        when(paramTreeItem.getPath()).thenReturn(PARAM_PATH);
        when(param.getName()).thenReturn(PARAM_NAME);
        when(param.getDataType()).thenReturn(DataType.TIME);
        String statement =
                generator.getAssignmentStatement(paramTreeItem, "rhs", null); //$NON-NLS-1$
        String expected = "2019-06-18T15:39:42.088Z"; //$NON-NLS-1$
        String paramScript = "var rhs = new Date('" + expected + "');"; //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript(statement, paramScript, PARAM_PATH, expected);
    }

    /**
     * Test assignment script for Time mapping.
     */
    public void testGetGetterStatementTime() throws Exception {
        when(conceptPath.getPath()).thenReturn("data.time"); //$NON-NLS-1$
        when(conceptPath.getType()).thenReturn(primitive);
        when(primitive.getName())
                .thenReturn(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);
        when(primitive.getGenerals()).thenReturn(new BasicEList<>());
        String statement = "var lhs = " //$NON-NLS-1$
                + generator.getGetterStatement(conceptPath, null) + ".toJSON()"; //$NON-NLS-1$
        String expected = "2019-06-18T15:39:42.088Z"; //$NON-NLS-1$
        String paramScript = "var data = {time: new Date('" + expected + "')};"; //$NON-NLS-1$ //$NON-NLS-2$
        executor.verifyScript(statement, paramScript, "lhs", expected); //$NON-NLS-1$
    }

}
