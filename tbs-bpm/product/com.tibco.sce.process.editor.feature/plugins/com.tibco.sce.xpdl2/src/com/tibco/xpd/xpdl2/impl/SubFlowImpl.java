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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExecutionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sub Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getActualParameters <em>Actual Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getExecution <em>Execution</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getInstanceDataField <em>Instance Data Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getPackageRefId <em>Package Ref Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getStartActivityId <em>Start Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getStartActivitySetId <em>Start Activity Set Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl#getEndPoint <em>End Point</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubFlowImpl extends ImplementationImpl implements SubFlow {
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
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

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
     * The default value of the '{@link #getExecution() <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExecution()
     * @generated
     * @ordered
     */
    protected static final ExecutionType EXECUTION_EDEFAULT =
            ExecutionType.SYNCHR_LITERAL;

    /**
     * The cached value of the '{@link #getExecution() <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExecution()
     * @generated
     * @ordered
     */
    protected ExecutionType execution = EXECUTION_EDEFAULT;

    /**
     * This is true if the Execution attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean executionESet;

    /**
     * The default value of the '{@link #getInstanceDataField() <em>Instance Data Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInstanceDataField()
     * @generated
     * @ordered
     */
    protected static final String INSTANCE_DATA_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInstanceDataField() <em>Instance Data Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInstanceDataField()
     * @generated
     * @ordered
     */
    protected String instanceDataField = INSTANCE_DATA_FIELD_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected String processId = PROCESS_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getPackageRefId() <em>Package Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRefId()
     * @generated
     * @ordered
     */
    protected static final String PACKAGE_REF_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPackageRefId() <em>Package Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRefId()
     * @generated
     * @ordered
     */
    protected String packageRefId = PACKAGE_REF_ID_EDEFAULT;

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
     * The default value of the '{@link #getStartActivitySetId() <em>Start Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartActivitySetId()
     * @generated
     * @ordered
     */
    protected static final String START_ACTIVITY_SET_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartActivitySetId() <em>Start Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartActivitySetId()
     * @generated
     * @ordered
     */
    protected String startActivitySetId = START_ACTIVITY_SET_ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getEndPoint() <em>End Point</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndPoint()
     * @generated
     * @ordered
     */
    protected EndPoint endPoint;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SubFlowImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.SUB_FLOW;
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
                            Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements =
                    new BasicFeatureMap(this,
                            Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Expression> getActualParameters() {
        if (actualParameters == null) {
            actualParameters =
                    new EObjectContainmentEList<Expression>(Expression.class,
                            this, Xpdl2Package.SUB_FLOW__ACTUAL_PARAMETERS);
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
            dataMappings =
                    new EObjectContainmentEList<DataMapping>(DataMapping.class,
                            this, Xpdl2Package.SUB_FLOW__DATA_MAPPINGS);
        }
        return dataMappings;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExecutionType getExecution() {
        return execution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExecution(ExecutionType newExecution) {
        ExecutionType oldExecution = execution;
        execution = newExecution == null ? EXECUTION_EDEFAULT : newExecution;
        boolean oldExecutionESet = executionESet;
        executionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SUB_FLOW__EXECUTION, oldExecution, execution,
                    !oldExecutionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExecution() {
        ExecutionType oldExecution = execution;
        boolean oldExecutionESet = executionESet;
        execution = EXECUTION_EDEFAULT;
        executionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.SUB_FLOW__EXECUTION, oldExecution,
                    EXECUTION_EDEFAULT, oldExecutionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExecution() {
        return executionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getInstanceDataField() {
        return instanceDataField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInstanceDataField(String newInstanceDataField) {
        String oldInstanceDataField = instanceDataField;
        instanceDataField = newInstanceDataField;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SUB_FLOW__INSTANCE_DATA_FIELD,
                    oldInstanceDataField, instanceDataField));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessId(String newProcessId) {
        String oldProcessId = processId;
        processId = newProcessId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SUB_FLOW__PROCESS_ID, oldProcessId, processId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPackageRefId() {
        return packageRefId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackageRefId(String newPackageRefId) {
        String oldPackageRefId = packageRefId;
        packageRefId = newPackageRefId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SUB_FLOW__PACKAGE_REF_ID, oldPackageRefId,
                    packageRefId));
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
                    Xpdl2Package.SUB_FLOW__START_ACTIVITY_ID,
                    oldStartActivityId, startActivityId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getStartActivitySetId() {
        return startActivitySetId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartActivitySetId(String newStartActivitySetId) {
        String oldStartActivitySetId = startActivitySetId;
        startActivitySetId = newStartActivitySetId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SUB_FLOW__START_ACTIVITY_SET_ID,
                    oldStartActivitySetId, startActivitySetId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndPoint getEndPoint() {
        return endPoint;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEndPoint(EndPoint newEndPoint,
            NotificationChain msgs) {
        EndPoint oldEndPoint = endPoint;
        endPoint = newEndPoint;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.SUB_FLOW__END_POINT, oldEndPoint,
                            newEndPoint);
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
    public void setEndPoint(EndPoint newEndPoint) {
        if (newEndPoint != endPoint) {
            NotificationChain msgs = null;
            if (endPoint != null)
                msgs =
                        ((InternalEObject) endPoint).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.SUB_FLOW__END_POINT,
                                null,
                                msgs);
            if (newEndPoint != null)
                msgs =
                        ((InternalEObject) newEndPoint).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.SUB_FLOW__END_POINT,
                                null,
                                msgs);
            msgs = basicSetEndPoint(newEndPoint, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SUB_FLOW__END_POINT, newEndPoint, newEndPoint));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getOtherElement(String elementName) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
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
        case Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.SUB_FLOW__ACTUAL_PARAMETERS:
            return ((InternalEList<?>) getActualParameters())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.SUB_FLOW__DATA_MAPPINGS:
            return ((InternalEList<?>) getDataMappings()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.SUB_FLOW__END_POINT:
            return basicSetEndPoint(null, msgs);
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
        case Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.SUB_FLOW__ACTUAL_PARAMETERS:
            return getActualParameters();
        case Xpdl2Package.SUB_FLOW__DATA_MAPPINGS:
            return getDataMappings();
        case Xpdl2Package.SUB_FLOW__EXECUTION:
            return getExecution();
        case Xpdl2Package.SUB_FLOW__INSTANCE_DATA_FIELD:
            return getInstanceDataField();
        case Xpdl2Package.SUB_FLOW__PROCESS_ID:
            return getProcessId();
        case Xpdl2Package.SUB_FLOW__PACKAGE_REF_ID:
            return getPackageRefId();
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_ID:
            return getStartActivityId();
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_SET_ID:
            return getStartActivitySetId();
        case Xpdl2Package.SUB_FLOW__END_POINT:
            return getEndPoint();
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
        case Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.SUB_FLOW__ACTUAL_PARAMETERS:
            getActualParameters().clear();
            getActualParameters()
                    .addAll((Collection<? extends Expression>) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__DATA_MAPPINGS:
            getDataMappings().clear();
            getDataMappings()
                    .addAll((Collection<? extends DataMapping>) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__EXECUTION:
            setExecution((ExecutionType) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__INSTANCE_DATA_FIELD:
            setInstanceDataField((String) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__PROCESS_ID:
            setProcessId((String) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__PACKAGE_REF_ID:
            setPackageRefId((String) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_ID:
            setStartActivityId((String) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_SET_ID:
            setStartActivitySetId((String) newValue);
            return;
        case Xpdl2Package.SUB_FLOW__END_POINT:
            setEndPoint((EndPoint) newValue);
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
        case Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.SUB_FLOW__ACTUAL_PARAMETERS:
            getActualParameters().clear();
            return;
        case Xpdl2Package.SUB_FLOW__DATA_MAPPINGS:
            getDataMappings().clear();
            return;
        case Xpdl2Package.SUB_FLOW__EXECUTION:
            unsetExecution();
            return;
        case Xpdl2Package.SUB_FLOW__INSTANCE_DATA_FIELD:
            setInstanceDataField(INSTANCE_DATA_FIELD_EDEFAULT);
            return;
        case Xpdl2Package.SUB_FLOW__PROCESS_ID:
            setProcessId(PROCESS_ID_EDEFAULT);
            return;
        case Xpdl2Package.SUB_FLOW__PACKAGE_REF_ID:
            setPackageRefId(PACKAGE_REF_ID_EDEFAULT);
            return;
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_ID:
            setStartActivityId(START_ACTIVITY_ID_EDEFAULT);
            return;
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_SET_ID:
            setStartActivitySetId(START_ACTIVITY_SET_ID_EDEFAULT);
            return;
        case Xpdl2Package.SUB_FLOW__END_POINT:
            setEndPoint((EndPoint) null);
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
        case Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.SUB_FLOW__ACTUAL_PARAMETERS:
            return actualParameters != null && !actualParameters.isEmpty();
        case Xpdl2Package.SUB_FLOW__DATA_MAPPINGS:
            return dataMappings != null && !dataMappings.isEmpty();
        case Xpdl2Package.SUB_FLOW__EXECUTION:
            return isSetExecution();
        case Xpdl2Package.SUB_FLOW__INSTANCE_DATA_FIELD:
            return INSTANCE_DATA_FIELD_EDEFAULT == null ? instanceDataField != null
                    : !INSTANCE_DATA_FIELD_EDEFAULT.equals(instanceDataField);
        case Xpdl2Package.SUB_FLOW__PROCESS_ID:
            return PROCESS_ID_EDEFAULT == null ? processId != null
                    : !PROCESS_ID_EDEFAULT.equals(processId);
        case Xpdl2Package.SUB_FLOW__PACKAGE_REF_ID:
            return PACKAGE_REF_ID_EDEFAULT == null ? packageRefId != null
                    : !PACKAGE_REF_ID_EDEFAULT.equals(packageRefId);
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_ID:
            return START_ACTIVITY_ID_EDEFAULT == null ? startActivityId != null
                    : !START_ACTIVITY_ID_EDEFAULT.equals(startActivityId);
        case Xpdl2Package.SUB_FLOW__START_ACTIVITY_SET_ID:
            return START_ACTIVITY_SET_ID_EDEFAULT == null ? startActivitySetId != null
                    : !START_ACTIVITY_SET_ID_EDEFAULT
                            .equals(startActivitySetId);
        case Xpdl2Package.SUB_FLOW__END_POINT:
            return endPoint != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == OtherAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES:
                return Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == OtherAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES:
                return Xpdl2Package.SUB_FLOW__OTHER_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.SUB_FLOW__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(", otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", execution: "); //$NON-NLS-1$
        if (executionESet)
            result.append(execution);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", instanceDataField: "); //$NON-NLS-1$
        result.append(instanceDataField);
        result.append(", processId: "); //$NON-NLS-1$
        result.append(processId);
        result.append(", packageRefId: "); //$NON-NLS-1$
        result.append(packageRefId);
        result.append(", startActivityId: "); //$NON-NLS-1$
        result.append(startActivityId);
        result.append(", startActivitySetId: "); //$NON-NLS-1$
        result.append(startActivitySetId);
        result.append(')');
        return result.toString();
    }

} //SubFlowImpl
