/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Utility class used for RSD model editing.
 * 
 * @author jarciuch
 * @since 13 Feb 2015
 */
public class RsdEditingUtil {

    private static List<HttpMethod> METHODS_WITH_REQUEST_PAYLOAD = Arrays
            .asList(HttpMethod.POST, HttpMethod.PUT);

    /**
     * Sid XPD-7889. On further investigation it appears that it is normal in
     * some circumstances to return response payload from DELETE and PUt
     * operations (so added PUT and DELETE here to show the payload properties
     * etc
     */
    private static List<HttpMethod> METHODS_WITH_RESPONSE_PAYLOAD = Arrays
            .asList(HttpMethod.GET,
                    HttpMethod.POST,
                    HttpMethod.DELETE,
                    HttpMethod.PUT);

    /**
     * Name prefix for a new resource.
     */
    public static final String NEW_RESOURCE_NAME_PREFIX =
            Messages.RsdEditingUtil_NewResourceName_prefix;

    /**
     * Name prefix for a new method.
     */
    public static final String NEW_METHOD_NAME_PREFIX =
            Messages.RsdEditingUtil_NewMethodName_prefix;

    /**
     * Prevents instantiation.
     */
    private RsdEditingUtil() {
    }

    /**
     * @return a collection of methods with response which can contain payload.
     */
    public static Collection<HttpMethod> getMethodsWithRequestPayload() {
        return METHODS_WITH_REQUEST_PAYLOAD;
    }

    /**
     * @return a collection of methods with response which can contain payload.
     */
    public static Collection<HttpMethod> getMethodsWithResponsePayload() {
        return METHODS_WITH_RESPONSE_PAYLOAD;
    }

    /**
     * Returns the first parent in the parents hierarchy of the required type or
     * <code>null</code> if the parent of the specified type is not found.
     * 
     * @param eo
     *            a {@link EObject} for which we would like a parent of a
     *            specified type.
     * @param c
     *            class of the parent we are looking for.
     * @return the first parent in the parents hierarchy of the required type or
     *         <code>null</code> if the parent of the specified type is not
     *         found.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParentOfType(EObject eo, Class<T> c) {
        if (c.isInstance(eo)) {
            return (T) eo;
        } else if (eo != null) {
            return getParentOfType(eo.eContainer(), c);
        } else {
            return null;
        }
    }

    /**
     * Returns an unique name for a EObject within the resource.
     * 
     * The EObject passed in is used to traverse the model hierarchy to collect
     * all current names to compare for uniqueness.
     * 
     * @param prefix
     *            the name prefix.
     * @param context
     *            the context object to find the NamedElements (the model needs
     *            to be connected to the object tree).
     * @param eClass
     *            the class of object to consider for uniqueness.
     * @param nameFeature
     *            the feature used to reflectively get the name (eClass must
     *            contain this feature). For example you could use:
     *            <code>RsdPackage.eINSTANCE.getNamedElement_Name()</>
     * @return String a unique name within the resource of the type.
     */
    public static String getDefaultName(String prefix, EObject context,
            EClass eClass, EStructuralFeature nameFeature) {

        if (context == null) {
            return prefix;
        }
        TreeIterator<EObject> iter = EcoreUtil.getAllContents(context, false);
        List<String> names = new ArrayList<String>();

        if (iter != null) {
            while (iter.hasNext()) {
                EObject next = iter.next();
                if (eClass.isInstance(next)) {
                    String name = (String) next.eGet(nameFeature);
                    names.add(name);
                }
            }
        }

        String name = ""; //$NON-NLS-1$
        int idx = 1;
        name = prefix + idx;
        while (names.contains(name)) {
            name = prefix + ++idx;
        }

        return name;
    }

    /**
     * Returns an unique name for a NamedElement within the resource.
     * 
     * The EObject passed in is used to traverse up the model hierarchy to find
     * the top EObject, which is then used as the starting point to collect all
     * current names to compare for uniqueness.
     * 
     * @param prefix
     *            the name prefix.
     * @param context
     *            the context object to find the NamedElements (the context
     *            needs to be connected to the object's tree).
     * @param eClass
     *            the class of object to consider for uniqueness.
     * 
     * @return String a unique name within the resource of the type.
     */
    public static String getDefaultName(String prefix, EObject context,
            EClass eClass) {
        return getDefaultName(prefix,
                context,
                eClass,
                RsdPackage.eINSTANCE.getNamedElement_Name());
    }

}
