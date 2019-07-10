/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.resources.util.XpdEcoreUtil;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Unique Id Element</b></em>'.
 * 
 * This type automatically allocates a universal unique Id attribute in the
 * sub-classing element.
 * 
 * This Id is flagged as changeable=false in xpdl2.ecore so that it cannot be
 * changed 'by accident'.
 * 
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.UniqueIdElementImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UniqueIdElementImpl extends EObjectImpl implements UniqueIdElement {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

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
     * <!-- begin-user-doc -->
     * 
     * NOT Auto-generated because whenever a new object is created we want to
     * create a new universally unique Id for it.
     * 
     * <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected UniqueIdElementImpl() {
        super();

        // Automatically allocate universal unique id...
        id = XpdEcoreUtil.generateUUID();

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.UNIQUE_ID_ELEMENT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc --> NOT Auto generated, this method would not
     * normally be generated because Id attribute in xpdl2.ecore is flagged as
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
        case Xpdl2Package.UNIQUE_ID_ELEMENT__ID:
            String oldValue = id;
            id = (String) newValue;
            if (eNotificationRequired()) {
                eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.UNIQUE_ID_ELEMENT__ID, oldValue,
                        newValue));
            }
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.UNIQUE_ID_ELEMENT__ID:
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
        case Xpdl2Package.UNIQUE_ID_ELEMENT__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        }
        return super.eIsSet(featureID);
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
        result.append(" (id: "); //$NON-NLS-1$
        result.append(id);
        result.append(')');
        return result.toString();
    }

} // UniqueIdElementImpl
