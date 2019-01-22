/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Util class to provide convenience methods to handle the <code>EAIJava</code>
 * extended model.
 * 
 * @author njpatel
 * 
 */
public class EAIJavaModelUtil {
    /**
     * Get the pojo extended model from the <code>TaskService</code> object.
     * This assumes that there will be only one extended model in the <b>##OTHER</b>
     * of the <code>TaskService</code> object.
     * 
     * @param taskService -
     *            Get extended model from this object
     * @return Extended model <code>EObject</code> if found, <b>null</b>
     *         otherwise.
     */
    public static EAIJava getEAIJavaObj(TaskService taskService) {
        EAIJava model = null;

        if (taskService != null) {
            // Get EAIJava model
            EObject extendedModel = TaskServiceExtUtil.getExtendedModel(
                    taskService, EaijavaPackage.eINSTANCE
                            .getDocumentRoot_EAIJava());

            if (extendedModel instanceof EAIJava) {
                model = (EAIJava) extendedModel;
            }

        }

        return model;
    }

    /**
     * Create the <code>EAIJava</code> object and add it to the given
     * <code>TaskService</code> object.
     * 
     * @param ed
     *            <code>EditingDomain</code> to use for the command.
     * @param cmd
     *            <code>CompoundCommand</code> to append the Add command to
     *            create the pojo object.
     * @param taskService
     *            Owner object to add the new pojo object to.
     * 
     * @return The new <code>EAIJava</code> object created, if any of the
     *         input is null then <b>null</b> will be returned.
     */
    public static EAIJava createEAIJavaObj(EditingDomain ed,
            CompoundCommand cmd, TaskService taskService) {
        EAIJava eai = null;

        if (ed != null && cmd != null && taskService != null) {
            eai = EaijavaFactory.eINSTANCE.createEAIJava();

            // Add the database object to the given TaskService object
            cmd.append(TaskServiceExtUtil.addExtendedModel(ed, taskService,
                    EaijavaPackage.eINSTANCE.getDocumentRoot_EAIJava(), eai));
        }

        return eai;
    }

    /**
     * Create the <code>ClassType</code> object with the given parameters. The
     * <i>cmd</i> will be updated with the <code>SetCommand</code>.
     * 
     * @param ed
     * @param cmd
     * @param parent
     * @param ref
     * @param className
     * @return
     */
    public static ClassType createClassTypeObj(EditingDomain ed,
            CompoundCommand cmd, EObject parent, EReference ref,
            String className) {
        ClassType classType = null;

        if (ed != null && cmd != null && parent != null && ref != null
                && className != null) {
            classType = EaijavaFactory.eINSTANCE.createClassType();
            classType.setName(className);

            cmd.append(SetCommand.create(ed, parent, ref, classType));
        }

        return classType;
    }

    /**
     * Load the give class reference from the model. The class will be searched
     * from it's java project which may take some time.
     * 
     * @param eai
     * @param classToLoad
     * @return
     * @throws JavaModelException
     */
    public static IType loadClassFromModel(EAIJava eai, EReference classToLoad)
            throws JavaModelException {
        IType type = null;

        if (eai != null && classToLoad != null) {
            Object obj = eai.eGet(classToLoad);

            if (obj instanceof ClassType) {
                ClassType classType = (ClassType) obj;
                String projectName = eai.getProject();
                String className = classType.getName();

                if (projectName != null && className != null) {
                    type = JavaModelUtil.findClass(projectName, className);
                }
            }
        } else {
            throw new NullPointerException(
                    "EAIJava object or the EReference is null."); //$NON-NLS-1$)
        }

        return type;
    }

    /**
     * Get the method parameters' signature list.
     * 
     * @param methodType
     * @return Array of parameter signatures, <code>null</code> if method has
     *         no parameters
     */
    public static String[] getMethodParameterSignatures(MethodType methodType) {
        String[] parameters = null;

        if (methodType.getParameters() != null
                && methodType.getParameters().getParameter() != null) {
            List<String> parameterTypes = new ArrayList<String>();

            for (Iterator<?> iter = methodType.getParameters().getParameter()
                    .iterator(); iter.hasNext();) {
                ParameterType pType = (ParameterType) iter.next();

                parameterTypes.add(pType.getSignature());
            }

            parameters = parameterTypes.toArray(new String[parameterTypes
                    .size()]);

        }

        return parameters;
    }

    /**
     * Create a <code>MethodType</code> for the given <i>method</i>.
     * 
     * @param method
     * @return
     * @throws JavaModelException
     */
    @SuppressWarnings("unchecked")
    public static MethodType createMethodType(IType type, IMethod method)
            throws JavaModelException {
        MethodType methodType = null;

        if (type != null && method != null) {
            methodType = EaijavaFactory.eINSTANCE.createMethodType();
            methodType.setName(method.getElementName());

            /*
             * Add method to the model
             */
            // Set return type
            ParameterType returnType = EaijavaFactory.eINSTANCE
                    .createParameterType();

            String rType = method.getReturnType();
            returnType.setSignature(rType);
            
            String className = getQualifiedClassName(type, rType);
            if (className != null) {
                returnType.setClassName(className);
            }

            methodType.setReturn(returnType);

            String[] parameterTypes = method.getParameterTypes();

            if (parameterTypes != null && parameterTypes.length > 0) {
                ParametersType pTypes = EaijavaFactory.eINSTANCE
                        .createParametersType();

                for (String pt : parameterTypes) {
                    ParameterType pType = EaijavaFactory.eINSTANCE
                            .createParameterType();
                    pType.setSignature(pt);
                    className = getQualifiedClassName(type, pt);

                    if (className != null) {
                        pType.setClassName(className);
                    }

                    pTypes.getParameter().add(pType);
                }

                methodType.setParameters(pTypes);
            }
        }

        return methodType;
    }

    /**
     * Get the qualified class name of the given parameter signature.
     * 
     * @param type
     * @param typeSignature
     * @return
     * @throws JavaModelException
     */
    private static String getQualifiedClassName(IType type, String typeSignature)
            throws JavaModelException {
        String className = null;

        if (type != null && typeSignature != null && !Signature.getElementType(typeSignature).equals(Signature.SIG_VOID)) {
            String[][] classNames = JavaModelUtil.resolveType(type,
                    typeSignature);

            if (classNames != null && classNames.length > 0) {
                className = classNames[0][0] + Signature.C_DOT
                        + classNames[0][1];
            }
        }

        return className;
    }
}
