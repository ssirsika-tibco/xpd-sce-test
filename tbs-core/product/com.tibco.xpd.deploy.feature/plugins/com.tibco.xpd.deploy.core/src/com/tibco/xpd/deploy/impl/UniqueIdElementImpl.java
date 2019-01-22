/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.UniqueIdElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unique Id Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.UniqueIdElementImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class UniqueIdElementImpl extends EObjectImpl implements
        UniqueIdElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #isIdFromFile() <em>Id From File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIdFromFile()
     * @generated NOT
     * @ordered
     */
    protected static final boolean ID_FROM_FILE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIdFromFile() <em>Id From File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIdFromFile()
     * @generated NOT
     * @ordered
     */
    protected boolean idFromFile = ID_FROM_FILE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    protected UniqueIdElementImpl() {
        super();
        // Automatically allocate universal unique id...
        id = EcoreUtil.generateUUID();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.UNIQUE_ID_ELEMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public boolean isIdFromFile() {
        return idFromFile;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.UNIQUE_ID_ELEMENT__ID:
            return getId();
        }
        return super.eGet(featureID, resolve, coreType);
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
        case DeployPackage.UNIQUE_ID_ELEMENT__ID:
            String oldValue = id;
            id = (String) newValue;
            idFromFile = true;
            if (eNotificationRequired()) {
                eNotify(new ENotificationImpl(this, Notification.SET,
                        DeployPackage.UNIQUE_ID_ELEMENT__ID, oldValue, newValue));
            }
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DeployPackage.UNIQUE_ID_ELEMENT__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (id: ");
        result.append(id);
        result.append(')');
        return result.toString();
    }

} //UniqueIdElementImpl
