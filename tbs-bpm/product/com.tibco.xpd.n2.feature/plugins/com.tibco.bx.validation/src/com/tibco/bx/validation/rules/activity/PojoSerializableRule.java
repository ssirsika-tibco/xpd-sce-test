/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.osgi.framework.Version;

import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper.PluginProjectDetails;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethod;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author mtorres
 */
public class PojoSerializableRule extends FlowContainerValidationRule {

    private static final String POJO_OSGI_VERSION_QUALIFIER_RULE =
            "bx.pojoOsgiVersionQualifier"; //$NON-NLS-1$

    private static final String POJO_SERIALIZABLE_RULE = "bx.pojoSerializable"; //$NON-NLS-1$

    private static final String[] NON_SERIALIZABLE_CLASSES_EXCEPTIONS =
            new String[] { "javax.xml.datatype.XMLGregorianCalendar" };//$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskService() != null) {
                    TaskService taskService = task.getTaskService();
                    String implementionType =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (implementionType != null
                            && implementionType.equals("Java")) { //$NON-NLS-1$
                        // Get the Java Method
                        JavaMethod javaMethod =
                                JavaServiceMappingUtil.getJavaMethod(activity);
                        if (javaMethod != null) {
                            EAIJava eai =
                                    JavaServiceMappingUtil.getEAIJava(activity);
                            if (eai != null) {
                                IProject parentJavaProject =
                                        ResourcesPlugin.getWorkspace()
                                                .getRoot()
                                                .getProject(eai.getProject());
                                if (parentJavaProject != null) {
                                    PluginProjectDetails pluginProjectDetails =
                                            PluginManifestHelper
                                                    .getPluginProjectDetails(parentJavaProject);

                                    /*
                                     * SID: If reference is to a Java project
                                     * rather than a Java-PlugIn project then
                                     * there will be NO pluginProjectDetails).
                                     */
                                    if (pluginProjectDetails != null
                                            && pluginProjectDetails
                                                    .getBundleVersion() != null) {
                                        Version version =
                                                new Version(
                                                        pluginProjectDetails
                                                                .getBundleVersion());
                                        if (version != null
                                                && (version.getQualifier() == null || version
                                                        .getQualifier().trim()
                                                        .length() == 0)) {
                                            addIssue(POJO_OSGI_VERSION_QUALIFIER_RULE,
                                                    activity);
                                        }
                                    }
                                }
                            }
                            try {
                                JavaMethodParameter[] parameters =
                                        javaMethod.getParameters();
                                if (parameters != null && parameters.length > 0) {
                                    if (eai != null) {
                                        for (JavaMethodParameter javaMethodParameter : parameters) {
                                            if (!isJavaTypeSerializable(javaMethodParameter,
                                                    eai)) {
                                                addIssue(POJO_SERIALIZABLE_RULE,
                                                        activity);
                                            }
                                        }
                                    }
                                }
                                JavaMethodParameter returnParam =
                                        javaMethod.getReturnParam();
                                if (!isJavaTypeSerializable(returnParam, eai)) {
                                    addIssue(POJO_SERIALIZABLE_RULE, activity);
                                }
                            } catch (JavaModelException e) {
                                XpdResourcesPlugin.getDefault().getLogger()
                                        .error(e);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isJavaTypeSerializable(
            JavaMethodParameter javaMethodParameter, EAIJava eai) {
        try {
            if (eai != null && javaMethodParameter != null
                    && javaMethodParameter.getType() != null) {
                ParameterTypeEnum typeEnum = javaMethodParameter.getType();
                if (typeEnum.equals(ParameterTypeEnum.CLASS)) {

                    String qualifiedClassName =
                            javaMethodParameter.getQualifiedType(true);

                    IType classType =
                            JavaModelUtil.findClass(eai.getProject(),
                                    qualifiedClassName);
                    if (classType != null) {
                        if (isClassSerializable(classType)) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return false;
    }

    private boolean isClassSerializable(IType classType)
            throws JavaModelException {
        if (isClassSerializableException(classType.getFullyQualifiedName())) {
            return true;
        }
        // Check type hierarchy
        List<String> typeHierarchy = new ArrayList<String>();
        addAllTypeHierarchy(classType, typeHierarchy);
        if (typeHierarchy.contains("java.io.Serializable")) { //$NON-NLS-1$
            return true;
        }

        return false;
    }

    private boolean isClassSerializableException(String typeFQN) {
        if (typeFQN != null) {
            for (String typeException : NON_SERIALIZABLE_CLASSES_EXCEPTIONS) {
                if (typeFQN.equals(typeException)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addAllTypeHierarchy(IType type,
            List<String> interfacesHierarchy) throws JavaModelException {
        if (type != null
                && !interfacesHierarchy.contains(type.getFullyQualifiedName())) {
            interfacesHierarchy.add(type.getFullyQualifiedName());
            // super types
            String superclassTypeSignature = type.getSuperclassTypeSignature();
            if (superclassTypeSignature != null) {
                IType resolveIType =
                        JavaModelUtil.resolveIType(type,
                                superclassTypeSignature);
                if (resolveIType != null) {
                    addAllTypeHierarchy(resolveIType, interfacesHierarchy);
                }
            }
            // super interfaces
            String[] superInterfaceTypeSignatures =
                    type.getSuperInterfaceTypeSignatures();
            if (superInterfaceTypeSignatures != null) {
                for (String superInterfaceTypeSignature : superInterfaceTypeSignatures) {
                    if (superInterfaceTypeSignature != null) {
                        IType resolveIType =
                                JavaModelUtil.resolveIType(type,
                                        superInterfaceTypeSignature);
                        if (resolveIType != null) {
                            addAllTypeHierarchy(resolveIType,
                                    interfacesHierarchy);
                        }
                    }
                }
            }
        }
    }

}
