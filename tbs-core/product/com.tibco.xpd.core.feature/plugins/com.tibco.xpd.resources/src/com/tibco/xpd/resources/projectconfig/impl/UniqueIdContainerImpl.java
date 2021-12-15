/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.UniqueIdContainer;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Unique Id Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.UniqueIdContainerImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UniqueIdContainerImpl extends EObjectImpl implements UniqueIdContainer {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected UniqueIdContainerImpl() {
        super();

        // Generate unique ID
        this.id = EcoreUtil.generateUUID();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProjectConfigPackage.Literals.UNIQUE_ID_CONTAINER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ProjectConfigPackage.UNIQUE_ID_CONTAINER__ID:
            return getId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case ProjectConfigPackage.UNIQUE_ID_CONTAINER__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> NOT Auto generated, this method would not
     * normally be generated because Id attribute in model is flagged as
     * changeable=false so that there is no setId() method so its obvious it
     * shouldn't 'normally' be changed.
     * 
     * But there are circumstances in which we do need to do a set, such as Load
     * from file and copy/paste. Therefore I've added this method back manually.
     * 
     * <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ProjectConfigPackage.UNIQUE_ID_CONTAINER__ID:
            id = (String) newValue;
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (id: ");
        result.append(id);
        result.append(')');
        return result.toString();
    }

} // UniqueIdContainerImpl
