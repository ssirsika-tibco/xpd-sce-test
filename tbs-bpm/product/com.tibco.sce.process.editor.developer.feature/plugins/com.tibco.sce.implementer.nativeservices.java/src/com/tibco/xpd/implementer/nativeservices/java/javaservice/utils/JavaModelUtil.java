/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.dialogs.OpenTypeSelectionDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.xpd.implementer.nativeservices.java.JavaActivator;
import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.DefaultPojoJsClass;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethod;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.script.PojoContentAssistIconProvider;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.script.ResolverUMLScriptRelevantData;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.DynamicJsAttribute;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Utility class to provide convenience methods that use JDT to explore the java
 * project system. Its superclass {@link JavaModelUtil0} declares all non-UI
 * dependent methods. This subclass declares the UI dependent methods.
 * 
 * @author njpatel
 * 
 */
public final class JavaModelUtil extends JavaModelUtil0 {

    private static PojoContentAssistIconProvider pojoContentAssistIconProvider =
            null;

    /**
     * Get the java class search dialog.
     * 
     * @param shell
     * @param elementKinds
     *            Element kinds to include in the search dialog
     * @param searchScope
     *            The search scope. if this is <code>null</code> then the
     *            workspace will be set as the scope.
     * 
     * @return Search <code>SeletionStatusDialog</code>.
     */
    @SuppressWarnings("restriction")
    public static SelectionStatusDialog getClassSearchDialog(Shell shell,
            int elementKinds, IJavaElement[] searchScope) {

        IJavaSearchScope scope;

        if (searchScope != null && searchScope.length > 0) {
            scope = SearchEngine.createJavaSearchScope(searchScope);
        } else {
            scope = SearchEngine.createWorkspaceScope();
        }

        OpenTypeSelectionDialog dialog =
                new OpenTypeSelectionDialog(shell, false, PlatformUI
                        .getWorkbench().getProgressService(), scope,
                        elementKinds);
        dialog.setTitle(Messages.JavaModelUtil_SelectClass);
        dialog.setMessage(Messages.JavaModelUtil_SelectClassDetail);
        return dialog;
    }

    public static List<ResolverUMLScriptRelevantData> setResolverParameterValue(
            List<ResolverUMLScriptRelevantData> resolvers, Activity activity) {
        Image img =
                JavaActivator.getDefault().getImageRegistry()
                        .get(JavaConstants.ICON_SIMPLETYPE);
        if (resolvers == null) {
            resolvers = new ArrayList<ResolverUMLScriptRelevantData>();
        }
        if (activity != null) {
            JavaMethod javaMethod =
                    JavaServiceMappingUtil.getJavaMethod(activity);
            if (javaMethod != null) {
                try {
                    JavaMethodParameter[] parameters =
                            javaMethod.getParameters();
                    for (JavaMethodParameter parameter : parameters) {
                        if (parameter != null) {
                            ResolverUMLScriptRelevantData resolver =
                                    new ResolverUMLScriptRelevantData();
                            resolver.setIcon(img);
                            resolver.setClassName(ProcessJsConsts.RESOLVER_CLASSNAME);
                            resolver.setName(parameter.getName());
                            String dataType = JsConsts.UNDEFINED_DATA_TYPE;
                            ParameterTypeEnum type = parameter.getType();

                            if (type != null
                                    && !type.getLabel()
                                            .equals(ParameterTypeEnum.CLASS.getLabel())) {
                                dataType =
                                        JScriptUtils
                                                .resolveJavaScriptDataType(parameter
                                                        .getParameterType());
                            }
                            boolean isArray = parameter.isArray();
                            resolver =
                                    setResolverParameterValue(resolver,
                                            dataType,
                                            isArray,
                                            img);
                            resolvers.add(resolver);
                        }
                    }
                } catch (JavaModelException e) {
                    e.printStackTrace();
                }
            }
        }
        return resolvers;
    }

    private static ResolverUMLScriptRelevantData setResolverParameterValue(
            ResolverUMLScriptRelevantData resolver, String dataType,
            boolean isArray, Image icon) {
        if (resolver != null) {
            JsClass jsClass = resolver.getJsClass();
            if (jsClass != null) {
                List<JsAttribute> attributeList = jsClass.getAttributeList();
                DynamicJsAttribute dynamicAttribute =
                        JavaModelUtil0
                                .getParameterValueAttribute(attributeList);
                if (dynamicAttribute == null) {
                    dynamicAttribute =
                            new DynamicJsAttribute(
                                    ProcessJsConsts.POJO_PARAMETER_VALUE,
                                    dataType,
                                    isArray,
                                    Messages.JavaServiceMapperOutSection__ReturnValueComment,
                                    icon);
                } else {
                    dynamicAttribute.setDataType(dataType);
                    dynamicAttribute.setMultiple(isArray);
                }
                jsClass.addAttribute(dynamicAttribute);
            }
        }
        return resolver;
    }

    public static IScriptRelevantData setResolverReturnValue(
            IScriptRelevantData resolver, Activity activity) {
        Image img =
                JavaActivator.getDefault().getImageRegistry()
                        .get(JavaConstants.ICON_SIMPLETYPE);
        if (resolver == null) {
            resolver = new ResolverUMLScriptRelevantData();
            ((ResolverUMLScriptRelevantData) resolver)
                    .setClassName(ProcessJsConsts.RESOLVER_CLASSNAME);
            resolver.setName(ProcessJsConsts.RESOLVER_CLASSNAME);
        }
        resolver.setIcon(img);
        if (activity != null) {
            JavaMethod javaMethod =
                    JavaServiceMappingUtil.getJavaMethod(activity);
            if (javaMethod != null) {
                try {
                    JavaMethodParameter returnParameter =
                            javaMethod.getReturnParam();
                    if (returnParameter != null) {
                        boolean isArray = returnParameter.isArray();
                        String dataType = JsConsts.UNDEFINED_DATA_TYPE;
                        ParameterTypeEnum type = returnParameter.getType();
                        if (type != null) {
                            if (!type.getLabel()
                                    .equals(ParameterTypeEnum.CLASS.getLabel())) {
                                // Primitive type
                                dataType = type.getLabel();
                                if (resolver instanceof ResolverUMLScriptRelevantData) {
                                    dataType =
                                            JScriptUtils
                                                    .resolveJavaScriptDataType(returnParameter
                                                            .getParameterType());
                                }
                                resolver.setType(type.getLabel());
                                resolver.setIsArray(isArray);
                            } else {
                                // Complex type
                                DefaultPojoJsClass jsClass =
                                        new DefaultPojoJsClass(returnParameter);
                                jsClass.setContentAssistIconProvider(JavaModelUtil
                                        .getPojoContentAssistIconProvider());
                                resolver =
                                        new DefaultUMLScriptRelevantData(
                                                resolver.getName(),
                                                jsClass.getName(), isArray,
                                                jsClass);
                            }
                        }

                        resolver =
                                setResolverReturnValue(resolver,
                                        dataType,
                                        isArray,
                                        img);
                    }
                } catch (JavaModelException e) {
                    e.printStackTrace();
                }
            }
        }
        return resolver;
    }

    private static IScriptRelevantData setResolverReturnValue(
            IScriptRelevantData resolver, String dataType, boolean isArray,
            Image icon) {
        if (resolver instanceof ResolverUMLScriptRelevantData) {
            JsClass jsClass =
                    ((ResolverUMLScriptRelevantData) resolver).getJsClass();
            if (jsClass != null) {
                List<JsAttribute> attributeList = jsClass.getAttributeList();
                DynamicJsAttribute dynamicAttribute =
                        JavaModelUtil0.getReturnValueAttribute(attributeList);
                if (dynamicAttribute == null) {
                    dynamicAttribute =
                            new DynamicJsAttribute(
                                    ProcessJsConsts.POJO_RETURN_VALUE,
                                    dataType,
                                    isArray,
                                    Messages.JavaServiceMapperOutSection__ReturnValueComment,
                                    icon);
                } else {
                    dynamicAttribute.setDataType(dataType);
                    dynamicAttribute.setMultiple(isArray);
                }
                jsClass.addAttribute(dynamicAttribute);
            }
        }
        return resolver;
    }

    public static PojoContentAssistIconProvider getPojoContentAssistIconProvider() {
        if (pojoContentAssistIconProvider == null) {
            pojoContentAssistIconProvider = new PojoContentAssistIconProvider();
        }
        return pojoContentAssistIconProvider;
    }

}
