/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.EAIJavaModelUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.SetReferencedProjectResolution;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check that the java service project class / factory class
 * and method / factory method are available.
 * 
 * @author njpatel
 * 
 */
public class JavaServiceClassRule extends ActivityValidationRule {

    private static final String ISSUE_PROJECTNOTFOUND_ID =
            "bpmn.dev.projectNotFound"; //$NON-NLS-1$

    private static final String ISSUE_CLASSNOTFOUND_ID =
            "bpmn.dev.classNotFound"; //$NON-NLS-1$

    private static final String ISSUE_METHODNOTFOUND_ID =
            "bpmn.dev.methodNotFound"; //$NON-NLS-1$

    private static final String ISSUE_NOTPLUGINPROJECT_ID =
            "bpmn.dev.javaProject.notPluginProject"; //$NON-NLS-1$

    private static final String ISSUE_NOTREFERENCEDPROJECT_ID =
            "bpmn.dev.javaProject.notReferenced"; //$NON-NLS-1$

    private static final String ISSUE_STATIC_METHOD_WITH_NO_ARGS_MISSING_IN_FACTORY =
            "bpmn.dev.javaProject.noArgsStaticMethodMissingInFactory"; //$NON-NLS-1$

    private static final String PDE_NATURE = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$

    // General mapping error issue ID
    public static final String MAPPING_ISSUE_ID = "bpmn.dev.mappingError"; //$NON-NLS-1$

    @Override
    public void validate(Activity container) {
        if (container != null) {

            if (container.getImplementation() instanceof Task) {
                TaskService taskService =
                        ((Task) container.getImplementation()).getTaskService();

                if (taskService != null) {

                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if ("Java".equals(type)) { //$NON-NLS-1$
                        EAIJava eaiJava =
                                EAIJavaModelUtil.getEAIJavaObj(taskService);

                        if (eaiJava != null) {
                            String projectName = eaiJava.getProject();

                            if (projectName != null) {
                                // Validate project
                                IProject project =
                                        ResourcesPlugin.getWorkspace()
                                                .getRoot()
                                                .getProject(projectName);

                                if (project != null) {
                                    if (project.isAccessible()) {
                                        // Validate the factory
                                        validateClass(eaiJava,
                                                eaiJava.getFactory());

                                        if (null != eaiJava.getFactory()) {
                                            validateFactory(eaiJava,
                                                    eaiJava.getFactory());
                                        }

                                        // Validate the POJO
                                        validateClass(eaiJava,
                                                eaiJava.getPojo());

                                        // Validate the java project
                                        validateProject(eaiJava, project);
                                    } else {
                                        // Project is closed or does not exist
                                        addIssue(ISSUE_PROJECTNOTFOUND_ID,
                                                container,
                                                Collections
                                                        .singletonList(projectName));
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Validate the java project.
     * 
     * @param eaiJava
     * @param project
     */
    private void validateProject(EAIJava eaiJava, IProject project) {
        try {
            Map<String, String> info = new HashMap<String, String>();
            info.put(SetReferencedProjectResolution.PROJECTNAME_ADDITIONAL_INFO,
                    project.getName());
            /*
             * Check if the project used by this step is set as a Referenced
             * project of the host project
             */
            IProject hostProject = WorkingCopyUtil.getProjectFor(eaiJava);
            if (hostProject != null) {
                boolean isReferenced = false;

                for (IProject ref : hostProject.getReferencedProjects()) {
                    if (isReferenced = ref.equals(project)) {
                        break;
                    }
                }

                if (!isReferenced) {
                    addIssue(ISSUE_NOTREFERENCEDPROJECT_ID,
                            eaiJava,
                            Collections.singletonList(project.getName()),
                            info);
                }
            }

            /*
             * The project used by this step has to be a Plug-in project
             */
            if (!project.hasNature(PDE_NATURE)) {
                addIssue(ISSUE_NOTPLUGINPROJECT_ID,
                        eaiJava,
                        Collections.singletonList(project.getName()),
                        info);
            }
        } catch (CoreException e) {
            // Do nothing
        }
    }

    /**
     * Validate the java class and method
     * 
     * @param eaiJava
     * @param classType
     */
    private void validateClass(EAIJava eaiJava, ClassType classType) {
        if (eaiJava != null && classType != null) {
            // Load the class
            IType javaClass = getClass(eaiJava, classType.getName());

            if (javaClass != null) {
                // Validate the method
                if (classType.getMethod() != null) {
                    MethodType methodType = classType.getMethod();

                    IMethod method =
                            javaClass
                                    .getMethod(methodType.getName(),
                                            EAIJavaModelUtil
                                                    .getMethodParameterSignatures(methodType));

                    if (!method.exists()) {
                        // Method does not exist
                        List<String> messages = new ArrayList<String>();

                        messages.add(getMethodSignature(methodType));
                        messages.add(classType.getName());

                        addIssue(ISSUE_METHODNOTFOUND_ID, eaiJava, messages);
                    } else {
                        /*
                         * XPD-1396: method exists - get the method's return
                         * type from model. get the method's return type from
                         * pojo. if they are not same, raise the issue
                         */
                        List<String> messages = new ArrayList<String>();
                        messages.add(getMethodSignature(methodType));
                        messages.add(classType.getName());
                        validateReturnType(eaiJava, javaClass, method, messages);
                    }
                }
            } else {
                // Class does not exist
                addIssue(ISSUE_CLASSNOTFOUND_ID,
                        eaiJava,
                        Collections.singletonList(classType.getName()));
            }
        }
    }

    /***
     * 
     * @param eaiJava
     * @param classType
     */
    private void validateFactory(EAIJava eaiJava, ClassType classType) {
        if (null != eaiJava && null != classType) {
            if (null == eaiJava.getFactory().getMethod()) {
                addIssue(ISSUE_STATIC_METHOD_WITH_NO_ARGS_MISSING_IN_FACTORY,
                        eaiJava,
                        Collections.singletonList(classType.getName()));
            }
        }
    }

    private void validateReturnType(EAIJava eaiJava, IType javaClass,
            IMethod method, List<String> messages) {
        try {

            if (null != eaiJava.getFactory()
                    && eaiJava.getFactory().getName()
                            .equals(javaClass.getFullyQualifiedName())) {

                for (IMethod factMethod : javaClass.getMethods()) {

                    if (factMethod.equals(method)) {
                        String returnType = factMethod.getReturnType();
                        String modelReturnType =
                                eaiJava.getFactory().getMethod().getReturn()
                                        .getSignature();

                        if (!returnType.equals(modelReturnType)) {
                            addIssue(ISSUE_METHODNOTFOUND_ID, eaiJava, messages);
                        }
                    }
                }
            }
            if (null != eaiJava.getPojo()
                    && eaiJava.getPojo().getName()
                            .equals(javaClass.getFullyQualifiedName())) {

                for (IMethod pojoMethod : javaClass.getMethods()) {

                    if (pojoMethod.equals(method)) {
                        String pojoRetType = pojoMethod.getReturnType();

                        if (null != eaiJava.getPojo()) {
                            String modelRetType =
                                    eaiJava.getPojo().getMethod().getReturn()
                                            .getSignature();

                            if (!(pojoRetType.equals(modelRetType))) {
                                addIssue(ISSUE_METHODNOTFOUND_ID,
                                        eaiJava,
                                        messages);
                            }
                        }
                    }
                }
            }

        } catch (JavaModelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the java class object
     * 
     * @param eaiJava
     * @param className
     * @return
     */
    private IType getClass(EAIJava eaiJava, String className) {
        IType javaClass = null;

        if (eaiJava != null && className != null) {
            try {
                // Find the class
                javaClass =
                        JavaModelUtil
                                .findClass(eaiJava.getProject(), className);

            } catch (JavaModelException e) {
                addIssue(MAPPING_ISSUE_ID,
                        eaiJava,
                        Collections.singletonList(e.getMessage()),
                        Collections
                                .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        className));
            }
        }

        return javaClass;
    }

    /**
     * Get method signature of the given <i>method</i>.
     * 
     * @param method
     * @return
     */
    private String getMethodSignature(MethodType method) {
        String signature = ""; //$NON-NLS-1$

        if (method != null) {
            ParameterType returnParam = method.getReturn();

            if (returnParam != null) {
                signature =
                        Signature.getSignatureSimpleName(returnParam
                                .getSignature()) + " "; //$NON-NLS-1$
            }

            signature += method.getName() + "("; //$NON-NLS-1$

            String[] paramSignatures =
                    EAIJavaModelUtil.getMethodParameterSignatures(method);

            if (paramSignatures != null && paramSignatures.length > 0) {
                signature +=
                        Signature.getSignatureSimpleName(paramSignatures[0]);

                for (int idx = 1; idx < paramSignatures.length; idx++) {
                    signature +=
                            ", "    + Signature.getSignatureSimpleName(paramSignatures[idx]); //$NON-NLS-1$
                }
            }

            signature += ")"; //$NON-NLS-1$
        }

        return signature;
    }

}
