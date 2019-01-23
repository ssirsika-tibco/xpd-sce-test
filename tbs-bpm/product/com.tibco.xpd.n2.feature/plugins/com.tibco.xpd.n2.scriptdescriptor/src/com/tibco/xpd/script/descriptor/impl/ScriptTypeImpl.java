/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor.impl;

import com.tibco.xpd.script.descriptor.CacType;
import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.EnumType;
import com.tibco.xpd.script.descriptor.FactoryType;
import com.tibco.xpd.script.descriptor.ProcessType;
import com.tibco.xpd.script.descriptor.ScriptType;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Script Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl#getTaskLibraryId <em>Task Library Id</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl#getFactory <em>Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl#getEnum <em>Enum</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl#getCac <em>Cac</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScriptTypeImpl extends EObjectImpl implements ScriptType {
    /**
     * The cached value of the '{@link #getProcess() <em>Process</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcess()
     * @generated
     * @ordered
     */
    protected ProcessType process;

    /**
     * The default value of the '{@link #getTaskLibraryId() <em>Task Library Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskLibraryId()
     * @generated
     * @ordered
     */
    protected static final String TASK_LIBRARY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTaskLibraryId() <em>Task Library Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskLibraryId()
     * @generated
     * @ordered
     */
    protected String taskLibraryId = TASK_LIBRARY_ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getFactory() <em>Factory</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFactory()
     * @generated
     * @ordered
     */
    protected EList<FactoryType> factory;

    /**
     * The cached value of the '{@link #getEnum() <em>Enum</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnum()
     * @generated
     * @ordered
     */
    protected EList<EnumType> enum_;

    /**
     * The cached value of the '{@link #getCac() <em>Cac</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCac()
     * @generated
     * @ordered
     */
    protected EList<CacType> cac;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScriptTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DescriptorPackage.Literals.SCRIPT_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ProcessType getProcess() {
        return process;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetProcess(ProcessType newProcess, NotificationChain msgs) {
        ProcessType oldProcess = process;
        process = newProcess;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DescriptorPackage.SCRIPT_TYPE__PROCESS, oldProcess, newProcess);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcess(ProcessType newProcess) {
        if (newProcess != process) {
            NotificationChain msgs = null;
            if (process != null)
                msgs = ((InternalEObject)process).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DescriptorPackage.SCRIPT_TYPE__PROCESS, null, msgs);
            if (newProcess != null)
                msgs = ((InternalEObject)newProcess).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DescriptorPackage.SCRIPT_TYPE__PROCESS, null, msgs);
            msgs = basicSetProcess(newProcess, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.SCRIPT_TYPE__PROCESS, newProcess, newProcess));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTaskLibraryId() {
        return taskLibraryId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskLibraryId(String newTaskLibraryId) {
        String oldTaskLibraryId = taskLibraryId;
        taskLibraryId = newTaskLibraryId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.SCRIPT_TYPE__TASK_LIBRARY_ID, oldTaskLibraryId, taskLibraryId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FactoryType> getFactory() {
        if (factory == null) {
            factory = new EObjectContainmentEList<FactoryType>(FactoryType.class, this, DescriptorPackage.SCRIPT_TYPE__FACTORY);
        }
        return factory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumType> getEnum() {
        if (enum_ == null) {
            enum_ = new EObjectContainmentEList<EnumType>(EnumType.class, this, DescriptorPackage.SCRIPT_TYPE__ENUM);
        }
        return enum_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CacType> getCac() {
        if (cac == null) {
            cac = new EObjectContainmentEList<CacType>(CacType.class, this, DescriptorPackage.SCRIPT_TYPE__CAC);
        }
        return cac;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DescriptorPackage.SCRIPT_TYPE__PROCESS:
                return basicSetProcess(null, msgs);
            case DescriptorPackage.SCRIPT_TYPE__FACTORY:
                return ((InternalEList<?>)getFactory()).basicRemove(otherEnd, msgs);
            case DescriptorPackage.SCRIPT_TYPE__ENUM:
                return ((InternalEList<?>)getEnum()).basicRemove(otherEnd, msgs);
            case DescriptorPackage.SCRIPT_TYPE__CAC:
                return ((InternalEList<?>)getCac()).basicRemove(otherEnd, msgs);
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
            case DescriptorPackage.SCRIPT_TYPE__PROCESS:
                return getProcess();
            case DescriptorPackage.SCRIPT_TYPE__TASK_LIBRARY_ID:
                return getTaskLibraryId();
            case DescriptorPackage.SCRIPT_TYPE__FACTORY:
                return getFactory();
            case DescriptorPackage.SCRIPT_TYPE__ENUM:
                return getEnum();
            case DescriptorPackage.SCRIPT_TYPE__CAC:
                return getCac();
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
            case DescriptorPackage.SCRIPT_TYPE__PROCESS:
                setProcess((ProcessType)newValue);
                return;
            case DescriptorPackage.SCRIPT_TYPE__TASK_LIBRARY_ID:
                setTaskLibraryId((String)newValue);
                return;
            case DescriptorPackage.SCRIPT_TYPE__FACTORY:
                getFactory().clear();
                getFactory().addAll((Collection<? extends FactoryType>)newValue);
                return;
            case DescriptorPackage.SCRIPT_TYPE__ENUM:
                getEnum().clear();
                getEnum().addAll((Collection<? extends EnumType>)newValue);
                return;
            case DescriptorPackage.SCRIPT_TYPE__CAC:
                getCac().clear();
                getCac().addAll((Collection<? extends CacType>)newValue);
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
            case DescriptorPackage.SCRIPT_TYPE__PROCESS:
                setProcess((ProcessType)null);
                return;
            case DescriptorPackage.SCRIPT_TYPE__TASK_LIBRARY_ID:
                setTaskLibraryId(TASK_LIBRARY_ID_EDEFAULT);
                return;
            case DescriptorPackage.SCRIPT_TYPE__FACTORY:
                getFactory().clear();
                return;
            case DescriptorPackage.SCRIPT_TYPE__ENUM:
                getEnum().clear();
                return;
            case DescriptorPackage.SCRIPT_TYPE__CAC:
                getCac().clear();
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
            case DescriptorPackage.SCRIPT_TYPE__PROCESS:
                return process != null;
            case DescriptorPackage.SCRIPT_TYPE__TASK_LIBRARY_ID:
                return TASK_LIBRARY_ID_EDEFAULT == null ? taskLibraryId != null : !TASK_LIBRARY_ID_EDEFAULT.equals(taskLibraryId);
            case DescriptorPackage.SCRIPT_TYPE__FACTORY:
                return factory != null && !factory.isEmpty();
            case DescriptorPackage.SCRIPT_TYPE__ENUM:
                return enum_ != null && !enum_.isEmpty();
            case DescriptorPackage.SCRIPT_TYPE__CAC:
                return cac != null && !cac.isEmpty();
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (taskLibraryId: ");
        result.append(taskLibraryId);
        result.append(')');
        return result.toString();
    }

} //ScriptTypeImpl
