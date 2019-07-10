/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.TaskApplication;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskApplicationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskApplicationImpl#getActualParameters <em>Actual Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskApplicationImpl#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskApplicationImpl#getPackageRef <em>Package Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskApplicationImpl#getApplicationId <em>Application Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskApplicationImpl extends EObjectImpl implements TaskApplication {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

    /**
     * The cached value of the '{@link #getActualParameters() <em>Actual Parameters</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActualParameters()
     * @generated
     * @ordered
     */
    protected EList<Expression> actualParameters;

    /**
     * The cached value of the '{@link #getDataMappings() <em>Data Mappings</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataMappings()
     * @generated
     * @ordered
     */
    protected EList<DataMapping> dataMappings;

    /**
     * The default value of the '{@link #getPackageRef() <em>Package Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRef()
     * @generated
     * @ordered
     */
    protected static final String PACKAGE_REF_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPackageRef() <em>Package Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRef()
     * @generated
     * @ordered
     */
    protected String packageRef = PACKAGE_REF_EDEFAULT;

    /**
     * The default value of the '{@link #getApplicationId() <em>Application Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getApplicationId()
     * @generated
     * @ordered
     */
    protected static final String APPLICATION_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getApplicationId() <em>Application Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getApplicationId()
     * @generated
     * @ordered
     */
    protected String applicationId = APPLICATION_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TaskApplicationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TASK_APPLICATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Expression> getActualParameters() {
        if (actualParameters == null) {
            actualParameters = new EObjectContainmentEList<Expression>(Expression.class, this,
                    Xpdl2Package.TASK_APPLICATION__ACTUAL_PARAMETERS);
        }
        return actualParameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DataMapping> getDataMappings() {
        if (dataMappings == null) {
            dataMappings = new EObjectContainmentEList<DataMapping>(DataMapping.class, this,
                    Xpdl2Package.TASK_APPLICATION__DATA_MAPPINGS);
        }
        return dataMappings;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Description getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDescription(Description newDescription, NotificationChain msgs) {
        Description oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK_APPLICATION__DESCRIPTION, oldDescription, newDescription);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(Description newDescription) {
        if (newDescription != description) {
            NotificationChain msgs = null;
            if (description != null)
                msgs = ((InternalEObject) description).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TASK_APPLICATION__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TASK_APPLICATION__DESCRIPTION,
                        null,
                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TASK_APPLICATION__DESCRIPTION,
                    newDescription, newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPackageRef() {
        return packageRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackageRef(String newPackageRef) {
        String oldPackageRef = packageRef;
        packageRef = newPackageRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TASK_APPLICATION__PACKAGE_REF,
                    oldPackageRef, packageRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setApplicationId(String newApplicationId) {
        String oldApplicationId = applicationId;
        applicationId = newApplicationId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TASK_APPLICATION__APPLICATION_ID,
                    oldApplicationId, applicationId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TASK_APPLICATION__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.TASK_APPLICATION__ACTUAL_PARAMETERS:
            return ((InternalEList<?>) getActualParameters()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.TASK_APPLICATION__DATA_MAPPINGS:
            return ((InternalEList<?>) getDataMappings()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.TASK_APPLICATION__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.TASK_APPLICATION__ACTUAL_PARAMETERS:
            return getActualParameters();
        case Xpdl2Package.TASK_APPLICATION__DATA_MAPPINGS:
            return getDataMappings();
        case Xpdl2Package.TASK_APPLICATION__PACKAGE_REF:
            return getPackageRef();
        case Xpdl2Package.TASK_APPLICATION__APPLICATION_ID:
            return getApplicationId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.TASK_APPLICATION__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.TASK_APPLICATION__ACTUAL_PARAMETERS:
            getActualParameters().clear();
            getActualParameters().addAll((Collection<? extends Expression>) newValue);
            return;
        case Xpdl2Package.TASK_APPLICATION__DATA_MAPPINGS:
            getDataMappings().clear();
            getDataMappings().addAll((Collection<? extends DataMapping>) newValue);
            return;
        case Xpdl2Package.TASK_APPLICATION__PACKAGE_REF:
            setPackageRef((String) newValue);
            return;
        case Xpdl2Package.TASK_APPLICATION__APPLICATION_ID:
            setApplicationId((String) newValue);
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
        case Xpdl2Package.TASK_APPLICATION__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.TASK_APPLICATION__ACTUAL_PARAMETERS:
            getActualParameters().clear();
            return;
        case Xpdl2Package.TASK_APPLICATION__DATA_MAPPINGS:
            getDataMappings().clear();
            return;
        case Xpdl2Package.TASK_APPLICATION__PACKAGE_REF:
            setPackageRef(PACKAGE_REF_EDEFAULT);
            return;
        case Xpdl2Package.TASK_APPLICATION__APPLICATION_ID:
            setApplicationId(APPLICATION_ID_EDEFAULT);
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
        case Xpdl2Package.TASK_APPLICATION__DESCRIPTION:
            return description != null;
        case Xpdl2Package.TASK_APPLICATION__ACTUAL_PARAMETERS:
            return actualParameters != null && !actualParameters.isEmpty();
        case Xpdl2Package.TASK_APPLICATION__DATA_MAPPINGS:
            return dataMappings != null && !dataMappings.isEmpty();
        case Xpdl2Package.TASK_APPLICATION__PACKAGE_REF:
            return PACKAGE_REF_EDEFAULT == null ? packageRef != null : !PACKAGE_REF_EDEFAULT.equals(packageRef);
        case Xpdl2Package.TASK_APPLICATION__APPLICATION_ID:
            return APPLICATION_ID_EDEFAULT == null ? applicationId != null
                    : !APPLICATION_ID_EDEFAULT.equals(applicationId);
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (packageRef: "); //$NON-NLS-1$
        result.append(packageRef);
        result.append(", applicationId: "); //$NON-NLS-1$
        result.append(applicationId);
        result.append(')');
        return result.toString();
    }

} //TaskApplicationImpl
