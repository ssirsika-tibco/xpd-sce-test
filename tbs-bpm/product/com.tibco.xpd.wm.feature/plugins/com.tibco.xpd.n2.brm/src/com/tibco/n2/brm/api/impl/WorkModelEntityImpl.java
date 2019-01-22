/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.DistributionStrategy;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkModelEntity;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;
import com.tibco.n2.common.organisation.api.XmlResourceQuery;

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
 * An implementation of the model object '<em><b>Work Model Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelEntityImpl#getEntityQuery <em>Entity Query</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelEntityImpl#getEntities <em>Entities</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelEntityImpl#getDistributionStrategy <em>Distribution Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkModelEntityImpl extends EObjectImpl implements WorkModelEntity {
    /**
     * The cached value of the '{@link #getEntityQuery() <em>Entity Query</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityQuery()
     * @generated
     * @ordered
     */
    protected XmlResourceQuery entityQuery;

    /**
     * The cached value of the '{@link #getEntities() <em>Entities</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntities()
     * @generated
     * @ordered
     */
    protected EList<XmlModelEntityId> entities;

    /**
     * The default value of the '{@link #getDistributionStrategy() <em>Distribution Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistributionStrategy()
     * @generated
     * @ordered
     */
    protected static final DistributionStrategy DISTRIBUTION_STRATEGY_EDEFAULT = DistributionStrategy.OFFER;

    /**
     * The cached value of the '{@link #getDistributionStrategy() <em>Distribution Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistributionStrategy()
     * @generated
     * @ordered
     */
    protected DistributionStrategy distributionStrategy = DISTRIBUTION_STRATEGY_EDEFAULT;

    /**
     * This is true if the Distribution Strategy attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean distributionStrategyESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkModelEntityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_MODEL_ENTITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlResourceQuery getEntityQuery() {
        return entityQuery;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEntityQuery(XmlResourceQuery newEntityQuery, NotificationChain msgs) {
        XmlResourceQuery oldEntityQuery = entityQuery;
        entityQuery = newEntityQuery;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY, oldEntityQuery, newEntityQuery);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityQuery(XmlResourceQuery newEntityQuery) {
        if (newEntityQuery != entityQuery) {
            NotificationChain msgs = null;
            if (entityQuery != null)
                msgs = ((InternalEObject)entityQuery).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY, null, msgs);
            if (newEntityQuery != null)
                msgs = ((InternalEObject)newEntityQuery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY, null, msgs);
            msgs = basicSetEntityQuery(newEntityQuery, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY, newEntityQuery, newEntityQuery));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<XmlModelEntityId> getEntities() {
        if (entities == null) {
            entities = new EObjectContainmentEList<XmlModelEntityId>(XmlModelEntityId.class, this, N2BRMPackage.WORK_MODEL_ENTITY__ENTITIES);
        }
        return entities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DistributionStrategy getDistributionStrategy() {
        return distributionStrategy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDistributionStrategy(DistributionStrategy newDistributionStrategy) {
        DistributionStrategy oldDistributionStrategy = distributionStrategy;
        distributionStrategy = newDistributionStrategy == null ? DISTRIBUTION_STRATEGY_EDEFAULT : newDistributionStrategy;
        boolean oldDistributionStrategyESet = distributionStrategyESet;
        distributionStrategyESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY, oldDistributionStrategy, distributionStrategy, !oldDistributionStrategyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDistributionStrategy() {
        DistributionStrategy oldDistributionStrategy = distributionStrategy;
        boolean oldDistributionStrategyESet = distributionStrategyESet;
        distributionStrategy = DISTRIBUTION_STRATEGY_EDEFAULT;
        distributionStrategyESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY, oldDistributionStrategy, DISTRIBUTION_STRATEGY_EDEFAULT, oldDistributionStrategyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDistributionStrategy() {
        return distributionStrategyESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY:
                return basicSetEntityQuery(null, msgs);
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITIES:
                return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY:
                return getEntityQuery();
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITIES:
                return getEntities();
            case N2BRMPackage.WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY:
                return getDistributionStrategy();
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
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY:
                setEntityQuery((XmlResourceQuery)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITIES:
                getEntities().clear();
                getEntities().addAll((Collection<? extends XmlModelEntityId>)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY:
                setDistributionStrategy((DistributionStrategy)newValue);
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
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY:
                setEntityQuery((XmlResourceQuery)null);
                return;
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITIES:
                getEntities().clear();
                return;
            case N2BRMPackage.WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY:
                unsetDistributionStrategy();
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
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITY_QUERY:
                return entityQuery != null;
            case N2BRMPackage.WORK_MODEL_ENTITY__ENTITIES:
                return entities != null && !entities.isEmpty();
            case N2BRMPackage.WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY:
                return isSetDistributionStrategy();
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
        result.append(" (distributionStrategy: ");
        if (distributionStrategyESet) result.append(distributionStrategy); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkModelEntityImpl
