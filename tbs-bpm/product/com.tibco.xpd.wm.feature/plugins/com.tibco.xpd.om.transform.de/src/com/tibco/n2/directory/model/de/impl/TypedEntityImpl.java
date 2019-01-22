/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.Attribute;
import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.EntityType;
import com.tibco.n2.directory.model.de.TypedEntity;

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
 * An implementation of the model object '<em><b>Typed Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.TypedEntityImpl#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.TypedEntityImpl#getDefinition <em>Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class TypedEntityImpl extends NamedEntityImpl implements TypedEntity {
    /**
     * The cached value of the '{@link #getAttributeValue() <em>Attribute Value</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeValue()
     * @generated
     * @ordered
     */
    protected EList<Attribute> attributeValue;

    /**
     * The cached value of the '{@link #getDefinition() <em>Definition</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefinition()
     * @generated
     * @ordered
     */
    protected EntityType definition;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TypedEntityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.TYPED_ENTITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Attribute> getAttributeValue() {
        if (attributeValue == null) {
            attributeValue = new EObjectContainmentEList<Attribute>(Attribute.class, this, DePackage.TYPED_ENTITY__ATTRIBUTE_VALUE);
        }
        return attributeValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EntityType getDefinition() {
        return definition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefinition(EntityType newDefinition) {
        EntityType oldDefinition = definition;
        definition = newDefinition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.TYPED_ENTITY__DEFINITION, oldDefinition, definition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.TYPED_ENTITY__ATTRIBUTE_VALUE:
                return ((InternalEList<?>)getAttributeValue()).basicRemove(otherEnd, msgs);
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
            case DePackage.TYPED_ENTITY__ATTRIBUTE_VALUE:
                return getAttributeValue();
            case DePackage.TYPED_ENTITY__DEFINITION:
                return getDefinition();
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
            case DePackage.TYPED_ENTITY__ATTRIBUTE_VALUE:
                getAttributeValue().clear();
                getAttributeValue().addAll((Collection<? extends Attribute>)newValue);
                return;
            case DePackage.TYPED_ENTITY__DEFINITION:
                setDefinition((EntityType)newValue);
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
            case DePackage.TYPED_ENTITY__ATTRIBUTE_VALUE:
                getAttributeValue().clear();
                return;
            case DePackage.TYPED_ENTITY__DEFINITION:
                setDefinition((EntityType)null);
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
            case DePackage.TYPED_ENTITY__ATTRIBUTE_VALUE:
                return attributeValue != null && !attributeValue.isEmpty();
            case DePackage.TYPED_ENTITY__DEFINITION:
                return definition != null;
        }
        return super.eIsSet(featureID);
    }

} //TypedEntityImpl
