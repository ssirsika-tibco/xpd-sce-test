/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.ViewType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BlockActivityImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BlockActivityImpl#getActivitySetId <em>Activity Set Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BlockActivityImpl#getStartActivityId <em>Start Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BlockActivityImpl#getView <em>View</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BlockActivityImpl extends EObjectImpl implements BlockActivity {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

    /**
     * The default value of the '{@link #getActivitySetId() <em>Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivitySetId()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_SET_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivitySetId() <em>Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivitySetId()
     * @generated
     * @ordered
     */
    protected String activitySetId = ACTIVITY_SET_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getStartActivityId() <em>Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartActivityId()
     * @generated
     * @ordered
     */
    protected static final String START_ACTIVITY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartActivityId() <em>Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartActivityId()
     * @generated
     * @ordered
     */
    protected String startActivityId = START_ACTIVITY_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getView() <em>View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getView()
     * @generated
     * @ordered
     */
    protected static final ViewType VIEW_EDEFAULT = ViewType.COLLAPSED;

    /**
     * The cached value of the '{@link #getView() <em>View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getView()
     * @generated
     * @ordered
     */
    protected ViewType view = VIEW_EDEFAULT;

    /**
     * This is true if the View attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean viewESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BlockActivityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.BLOCK_ACTIVITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes =
                    new BasicFeatureMap(this,
                            Xpdl2Package.BLOCK_ACTIVITY__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivitySetId() {
        return activitySetId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivitySetId(String newActivitySetId) {
        String oldActivitySetId = activitySetId;
        activitySetId = newActivitySetId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.BLOCK_ACTIVITY__ACTIVITY_SET_ID,
                    oldActivitySetId, activitySetId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getStartActivityId() {
        return startActivityId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartActivityId(String newStartActivityId) {
        String oldStartActivityId = startActivityId;
        startActivityId = newStartActivityId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.BLOCK_ACTIVITY__START_ACTIVITY_ID,
                    oldStartActivityId, startActivityId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ViewType getView() {
        return view;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setView(ViewType newView) {
        ViewType oldView = view;
        view = newView == null ? VIEW_EDEFAULT : newView;
        boolean oldViewESet = viewESet;
        viewESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.BLOCK_ACTIVITY__VIEW, oldView, view,
                    !oldViewESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetView() {
        ViewType oldView = view;
        boolean oldViewESet = viewESet;
        view = VIEW_EDEFAULT;
        viewESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.BLOCK_ACTIVITY__VIEW, oldView, VIEW_EDEFAULT,
                    oldViewESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetView() {
        return viewESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.BLOCK_ACTIVITY__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.BLOCK_ACTIVITY__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.BLOCK_ACTIVITY__ACTIVITY_SET_ID:
            return getActivitySetId();
        case Xpdl2Package.BLOCK_ACTIVITY__START_ACTIVITY_ID:
            return getStartActivityId();
        case Xpdl2Package.BLOCK_ACTIVITY__VIEW:
            return getView();
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
        case Xpdl2Package.BLOCK_ACTIVITY__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.BLOCK_ACTIVITY__ACTIVITY_SET_ID:
            setActivitySetId((String) newValue);
            return;
        case Xpdl2Package.BLOCK_ACTIVITY__START_ACTIVITY_ID:
            setStartActivityId((String) newValue);
            return;
        case Xpdl2Package.BLOCK_ACTIVITY__VIEW:
            setView((ViewType) newValue);
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
        case Xpdl2Package.BLOCK_ACTIVITY__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.BLOCK_ACTIVITY__ACTIVITY_SET_ID:
            setActivitySetId(ACTIVITY_SET_ID_EDEFAULT);
            return;
        case Xpdl2Package.BLOCK_ACTIVITY__START_ACTIVITY_ID:
            setStartActivityId(START_ACTIVITY_ID_EDEFAULT);
            return;
        case Xpdl2Package.BLOCK_ACTIVITY__VIEW:
            unsetView();
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
        case Xpdl2Package.BLOCK_ACTIVITY__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.BLOCK_ACTIVITY__ACTIVITY_SET_ID:
            return ACTIVITY_SET_ID_EDEFAULT == null ? activitySetId != null
                    : !ACTIVITY_SET_ID_EDEFAULT.equals(activitySetId);
        case Xpdl2Package.BLOCK_ACTIVITY__START_ACTIVITY_ID:
            return START_ACTIVITY_ID_EDEFAULT == null ? startActivityId != null
                    : !START_ACTIVITY_ID_EDEFAULT.equals(startActivityId);
        case Xpdl2Package.BLOCK_ACTIVITY__VIEW:
            return isSetView();
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", activitySetId: "); //$NON-NLS-1$
        result.append(activitySetId);
        result.append(", startActivityId: "); //$NON-NLS-1$
        result.append(startActivityId);
        result.append(", view: "); //$NON-NLS-1$
        if (viewESet)
            result.append(view);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //BlockActivityImpl
