/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import com.tibco.xpd.xpdl2.CatchThrow;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger Result Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl#getDeprecatedLinkId <em>Deprecated Link Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl#getDeprecatedProcessRef <em>Deprecated Process Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl#getCatchThrow <em>Catch Throw</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TriggerResultLinkImpl extends EObjectImpl implements
        TriggerResultLink {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getDeprecatedLinkId() <em>Deprecated Link Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedLinkId()
     * @generated
     * @ordered
     */
    protected static final String DEPRECATED_LINK_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeprecatedLinkId() <em>Deprecated Link Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedLinkId()
     * @generated
     * @ordered
     */
    protected String deprecatedLinkId = DEPRECATED_LINK_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getDeprecatedProcessRef() <em>Deprecated Process Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedProcessRef()
     * @generated
     * @ordered
     */
    protected static final String DEPRECATED_PROCESS_REF_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeprecatedProcessRef() <em>Deprecated Process Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedProcessRef()
     * @generated
     * @ordered
     */
    protected String deprecatedProcessRef = DEPRECATED_PROCESS_REF_EDEFAULT;

    /**
     * The default value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCatchThrow()
     * @generated
     * @ordered
     */
    protected static final CatchThrow CATCH_THROW_EDEFAULT = CatchThrow.CATCH;

    /**
     * The cached value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCatchThrow()
     * @generated
     * @ordered
     */
    protected CatchThrow catchThrow = CATCH_THROW_EDEFAULT;

    /**
     * This is true if the Catch Throw attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean catchThrowESet;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TriggerResultLinkImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRIGGER_RESULT_LINK;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDeprecatedLinkId() {
        return deprecatedLinkId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedLinkId(String newDeprecatedLinkId) {
        String oldDeprecatedLinkId = deprecatedLinkId;
        deprecatedLinkId = newDeprecatedLinkId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_LINK_ID,
                    oldDeprecatedLinkId, deprecatedLinkId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDeprecatedProcessRef() {
        return deprecatedProcessRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedProcessRef(String newDeprecatedProcessRef) {
        String oldDeprecatedProcessRef = deprecatedProcessRef;
        deprecatedProcessRef = newDeprecatedProcessRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_PROCESS_REF,
                    oldDeprecatedProcessRef, deprecatedProcessRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CatchThrow getCatchThrow() {
        return catchThrow;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCatchThrow(CatchThrow newCatchThrow) {
        CatchThrow oldCatchThrow = catchThrow;
        catchThrow =
                newCatchThrow == null ? CATCH_THROW_EDEFAULT : newCatchThrow;
        boolean oldCatchThrowESet = catchThrowESet;
        catchThrowESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_LINK__CATCH_THROW,
                    oldCatchThrow, catchThrow, !oldCatchThrowESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCatchThrow() {
        CatchThrow oldCatchThrow = catchThrow;
        boolean oldCatchThrowESet = catchThrowESet;
        catchThrow = CATCH_THROW_EDEFAULT;
        catchThrowESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.TRIGGER_RESULT_LINK__CATCH_THROW,
                    oldCatchThrow, CATCH_THROW_EDEFAULT, oldCatchThrowESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCatchThrow() {
        return catchThrowESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_LINK__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_LINK_ID:
            return getDeprecatedLinkId();
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_PROCESS_REF:
            return getDeprecatedProcessRef();
        case Xpdl2Package.TRIGGER_RESULT_LINK__CATCH_THROW:
            return getCatchThrow();
        case Xpdl2Package.TRIGGER_RESULT_LINK__NAME:
            return getName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_LINK_ID:
            setDeprecatedLinkId((String) newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_PROCESS_REF:
            setDeprecatedProcessRef((String) newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_LINK__CATCH_THROW:
            setCatchThrow((CatchThrow) newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_LINK__NAME:
            setName((String) newValue);
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
    public void eUnset(int featureID) {
        switch (featureID) {
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_LINK_ID:
            setDeprecatedLinkId(DEPRECATED_LINK_ID_EDEFAULT);
            return;
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_PROCESS_REF:
            setDeprecatedProcessRef(DEPRECATED_PROCESS_REF_EDEFAULT);
            return;
        case Xpdl2Package.TRIGGER_RESULT_LINK__CATCH_THROW:
            unsetCatchThrow();
            return;
        case Xpdl2Package.TRIGGER_RESULT_LINK__NAME:
            setName(NAME_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_LINK_ID:
            return DEPRECATED_LINK_ID_EDEFAULT == null ? deprecatedLinkId != null
                    : !DEPRECATED_LINK_ID_EDEFAULT.equals(deprecatedLinkId);
        case Xpdl2Package.TRIGGER_RESULT_LINK__DEPRECATED_PROCESS_REF:
            return DEPRECATED_PROCESS_REF_EDEFAULT == null ? deprecatedProcessRef != null
                    : !DEPRECATED_PROCESS_REF_EDEFAULT
                            .equals(deprecatedProcessRef);
        case Xpdl2Package.TRIGGER_RESULT_LINK__CATCH_THROW:
            return isSetCatchThrow();
        case Xpdl2Package.TRIGGER_RESULT_LINK__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
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
        result.append(" (deprecatedLinkId: "); //$NON-NLS-1$
        result.append(deprecatedLinkId);
        result.append(", deprecatedProcessRef: "); //$NON-NLS-1$
        result.append(deprecatedProcessRef);
        result.append(", catchThrow: "); //$NON-NLS-1$
        if (catchThrowESet)
            result.append(catchThrow);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //TriggerResultLinkImpl
