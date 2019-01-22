/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.script.model.client.DynamicJsAttribute;
import com.tibco.xpd.script.model.client.JsAttribute;

/**
 * Utility class to provide convenience methods that use JDT to explore the java
 * project system. This class declares only the non-UI methods.
 * 
 * @author patkinso
 * @since 24 Apr 2013
 */
public class JavaModelUtil0 {

    /**
     * Find the given <i>className</i> from the given java project. This call
     * may take some time to return.
     * 
     * @param eai
     * @param classToLoad
     * @return The <code>IType</code> of the class if found, <code>null</code>
     *         otherwise.
     * @throws JavaModelException
     */
    public static IType findClass(String projectName, String qualifiedClassName)
            throws JavaModelException {
        IType type = null;

        if (projectName != null && qualifiedClassName != null) {
            IJavaProject javaProject = getJavaProject(projectName);

            if (javaProject != null) {
                type = javaProject.findType(qualifiedClassName);
            }
        } else {
            throw new NullPointerException("Project or class name is null."); //$NON-NLS-1$)
        }

        return type;
    }

    /**
     * Get the java project of the given kind.
     * 
     * @param projectName
     * @return <code>IJavaProject</code> if the project exists and is open.
     */
    public static IJavaProject getJavaProject(String projectName) {
        IJavaProject javaProject = null;

        if (projectName != null) {
            IProject project =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(projectName);

            if (project != null && project.isOpen()) {
                javaProject = JavaCore.create(project);

                if (!javaProject.exists()) {
                    javaProject = null;
                }
            }
        }

        return javaProject;
    }

    /**
     * Get the methods of the give type
     * 
     * @param type
     *            The class to search for the methods.
     * @param methodType
     *            <code>Flags</code> value for the method type.
     * @return List of <code>IMethod</code> objects.
     * @throws JavaModelException
     */
    public static List<IMethod> getMethods(IType type, int methodType)
            throws JavaModelException {
        List<IMethod> publicMethods = new ArrayList<IMethod>();

        if (type != null) {
            IMethod[] methods = type.getMethods();

            if (methods != null) {
                // Add public methods to the list (not including constructors
                // and deprecated methods)
                for (IMethod method : methods) {
                    if ((method.getFlags() & methodType) == methodType
                            && !method.isConstructor()
                            && !Flags.isDeprecated(method.getFlags())) {
                        publicMethods.add(method);
                    }
                }

                // Sort the list
                Collections.sort(publicMethods, new MethodComparator());
            }
        } else {
            throw new NullPointerException("No type specified."); //$NON-NLS-1$
        }

        return publicMethods;
    }

    /**
     * A comparator for the <code>IMethod</code> objects.
     * 
     * @author njpatel
     * 
     */
    private static class MethodComparator implements Comparator<IMethod> {

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(IMethod o1, IMethod o2) {
            int ret = 0;
            // If both are either constructors or not constructors then compare
            // the method names
            try {
                if ((o1.isConstructor() && o2.isConstructor())
                        || (!o1.isConstructor() && !o2.isConstructor())) {
                    ret = o1.getElementName().compareTo(o2.getElementName());
                } else if (o1.isConstructor()) {
                    ret = -1;
                } else {
                    ret = 1;
                }
            } catch (JavaModelException e) {
                // Do nothing
            }
            return ret;
        }
    }

    /**
     * Get the class that the signature type is refering to
     * 
     * @param type2
     * @return
     * @throws JavaModelException
     */
    public static IType resolveIType(IType containingClass, String sigType)
            throws JavaModelException {
        IType resolved = null;

        if (containingClass != null) {
            String[][] classes = resolveType(containingClass, sigType);

            if (classes != null && classes.length > 0) {
                // Find class
                IJavaProject javaProject = containingClass.getJavaProject();

                if (javaProject != null) {
                    for (String[] className : classes) {
                        resolved =
                                javaProject
                                        .findType(className[0], className[1]);

                        // If a class was resolved then quit
                        if (resolved != null) {
                            break;
                        }
                    }
                }
            }
        }
        return resolved;
    }

    /**
     * Resolve the given type signature in the given <i>type</i>.
     * 
     * @see IType#resolveType(String)
     * 
     * @param project
     * @param typeSignature
     * @return
     * @throws JavaModelException
     */
    public static String[][] resolveType(IType type, String typeSignature)
            throws JavaModelException {
        String[][] resolvedClasses = null;

        if (type != null && typeSignature != null) {

            // If this is a binary type then the signature will contain the
            // qualified class name
            String signatureQualifier =
                    Signature.getSignatureQualifier(typeSignature);

            if (signatureQualifier != null && signatureQualifier.length() > 0) {
                resolvedClasses =
                        new String[][] { { signatureQualifier,
                                Signature.getSignatureSimpleName(typeSignature) } };
            } else {
                resolvedClasses =
                        type.resolveType(Signature
                                .getSignatureSimpleName(Signature
                                        .getElementType(typeSignature)));
            }
        }

        return resolvedClasses;
    }

    protected static DynamicJsAttribute getParameterValueAttribute(
            List<JsAttribute> attributeList) {
        DynamicJsAttribute dynamicAttribute = null;
        if (attributeList != null && !attributeList.isEmpty()) {
            for (Iterator iterator = attributeList.iterator(); iterator
                    .hasNext();) {
                JsAttribute attribute = (JsAttribute) iterator.next();
                if (attribute instanceof DynamicJsAttribute
                        && attribute.getName()
                                .equals(ProcessJsConsts.POJO_PARAMETER_VALUE)) {
                    dynamicAttribute = (DynamicJsAttribute) attribute;
                }
            }
        }
        return dynamicAttribute;
    }

    protected static DynamicJsAttribute getReturnValueAttribute(
            List<JsAttribute> attributeList) {
        DynamicJsAttribute dynamicAttribute = null;
        if (attributeList != null && !attributeList.isEmpty()) {
            for (Iterator iterator = attributeList.iterator(); iterator
                    .hasNext();) {
                JsAttribute attribute = (JsAttribute) iterator.next();
                if (attribute instanceof DynamicJsAttribute
                        && attribute.getName()
                                .equals(ProcessJsConsts.POJO_RETURN_VALUE)) {
                    dynamicAttribute = (DynamicJsAttribute) attribute;
                }
            }
        }
        return dynamicAttribute;
    }

}
