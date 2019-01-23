/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.OrgUnit;
import com.tibco.n2.directory.model.de.Position;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Org Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrgUnitImpl#getModelTemplateRef <em>Model Template Ref</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrgUnitImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrgUnitImpl#getOrgUnit <em>Org Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgUnitImpl extends OrgUnitBaseImpl implements OrgUnit {
    /**
     * The cached value of the '{@link #getModelTemplateRef() <em>Model Template Ref</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModelTemplateRef()
     * @generated
     * @ordered
     */
    protected ModelTemplate modelTemplateRef;

    /**
     * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPosition()
     * @generated
     * @ordered
     */
    protected EList<Position> position;

    /**
     * The cached value of the '{@link #getOrgUnit() <em>Org Unit</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrgUnit()
     * @generated
     * @ordered
     */
    protected EList<OrgUnit> orgUnit;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OrgUnitImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.ORG_UNIT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModelTemplate getModelTemplateRef() {
        return modelTemplateRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModelTemplateRef(ModelTemplate newModelTemplateRef) {
        ModelTemplate oldModelTemplateRef = modelTemplateRef;
        modelTemplateRef = newModelTemplateRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.ORG_UNIT__MODEL_TEMPLATE_REF, oldModelTemplateRef, modelTemplateRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Position> getPosition() {
        if (position == null) {
            position = new EObjectContainmentEList<Position>(Position.class, this, DePackage.ORG_UNIT__POSITION);
        }
        return position;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnit> getOrgUnit() {
        if (orgUnit == null) {
            orgUnit = new EObjectContainmentEList<OrgUnit>(OrgUnit.class, this, DePackage.ORG_UNIT__ORG_UNIT);
        }
        return orgUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.ORG_UNIT__POSITION:
                return ((InternalEList<?>)getPosition()).basicRemove(otherEnd, msgs);
            case DePackage.ORG_UNIT__ORG_UNIT:
                return ((InternalEList<?>)getOrgUnit()).basicRemove(otherEnd, msgs);
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
            case DePackage.ORG_UNIT__MODEL_TEMPLATE_REF:
                return getModelTemplateRef();
            case DePackage.ORG_UNIT__POSITION:
                return getPosition();
            case DePackage.ORG_UNIT__ORG_UNIT:
                return getOrgUnit();
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
            case DePackage.ORG_UNIT__MODEL_TEMPLATE_REF:
                setModelTemplateRef((ModelTemplate)newValue);
                return;
            case DePackage.ORG_UNIT__POSITION:
                getPosition().clear();
                getPosition().addAll((Collection<? extends Position>)newValue);
                return;
            case DePackage.ORG_UNIT__ORG_UNIT:
                getOrgUnit().clear();
                getOrgUnit().addAll((Collection<? extends OrgUnit>)newValue);
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
            case DePackage.ORG_UNIT__MODEL_TEMPLATE_REF:
                setModelTemplateRef((ModelTemplate)null);
                return;
            case DePackage.ORG_UNIT__POSITION:
                getPosition().clear();
                return;
            case DePackage.ORG_UNIT__ORG_UNIT:
                getOrgUnit().clear();
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
            case DePackage.ORG_UNIT__MODEL_TEMPLATE_REF:
                return modelTemplateRef != null;
            case DePackage.ORG_UNIT__POSITION:
                return position != null && !position.isEmpty();
            case DePackage.ORG_UNIT__ORG_UNIT:
                return orgUnit != null && !orgUnit.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //OrgUnitImpl
