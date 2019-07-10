/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import com.tibco.xpd.xpdl2.extension.ResourceExtensions;

/**
 * <!-- begin-user-doc --> The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * 
 * @see com.tibco.xpd.xpdl2.util.Xpdl2ResourceFactoryImpl
 * @generated not
 */
public class Xpdl2ResourceImpl extends ResourceExtensions {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates an instance of the resource.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param uri the URI of the new resource.
     * @generated
     */
    public Xpdl2ResourceImpl(URI uri) {
        super(uri);
    }

    @Override
    public EObject getEObject(String uriFragment) {

        try {
            return super.getEObject(uriFragment);
        } catch (IllegalArgumentException e) {
            /*
             * Ignore IllegalArgumentException. This is because this can happen
             * when a uri is stored it is stored positionally (like
             * processes.0/activities.8/implementation/taskService) AND it wraps
             * up choice objects (like "activity/implementation").
             * 
             * Therefore in this example when the 8th activity wwhich was a
             * service is removed, if the 9th activity (which becomes the 8th
             * after the delete) is a task type none then it's implementation
             * element is an xpdl:No which doesn't contain a TaskService child
             * element and so EMF throws an exception is
             * resourceImpl.getEObject()
             */
        } catch (ClassCastException e) {
            /*
             * XPD_7078 - Ignore ClassCastException; there are occasions (like
             * when the mixed feature map content of an Xpdl2:Expression changes
             * from an EObject To Text and there was a validation marker
             * outstanding for the original entry.
             * 
             * The underlying EMF Eobject resolution will throw a class case
             * exception because the URI points at 'element 1 in the mixed
             * feature map and EMF assumes therefore that it must be an EObject.
             * *
             */
        }
        return null;
    }
} // Xpdl2ResourceImpl
