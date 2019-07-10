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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.ApplicationType;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationImpl#getFormalParameters <em>Formal Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationImpl#getExternalReference <em>External Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ApplicationImpl extends NamedElementImpl implements Application {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExtendedAttributes() <em>Extended Attributes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The cached value of the '{@link #getFormalParameters() <em>Formal Parameters</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormalParameters()
     * @generated
     * @ordered
     */
    protected EList<FormalParameter> formalParameters;

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
     * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected ApplicationType type;

    /**
     * The cached value of the '{@link #getExternalReference() <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExternalReference()
     * @generated
     * @ordered
     */
    protected ExternalReference externalReference;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ApplicationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.APPLICATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FormalParameter> getFormalParameters() {
        if (formalParameters == null) {
            formalParameters = new EObjectContainmentEList<FormalParameter>(FormalParameter.class, this,
                    Xpdl2Package.APPLICATION__FORMAL_PARAMETERS);
        }
        return formalParameters;
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
                    Xpdl2Package.APPLICATION__DESCRIPTION, oldDescription, newDescription);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION__DESCRIPTION, null, msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION__DESCRIPTION, newDescription,
                    newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ApplicationType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetType(ApplicationType newType, NotificationChain msgs) {
        ApplicationType oldType = type;
        type = newType;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION__TYPE, oldType, newType);
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
    public void setType(ApplicationType newType) {
        if (newType != type) {
            NotificationChain msgs = null;
            if (type != null)
                msgs = ((InternalEObject) type)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION__TYPE, null, msgs);
            if (newType != null)
                msgs = ((InternalEObject) newType)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION__TYPE, null, msgs);
            msgs = basicSetType(newType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION__TYPE, newType, newType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExternalReference getExternalReference() {
        return externalReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExternalReference(ExternalReference newExternalReference, NotificationChain msgs) {
        ExternalReference oldExternalReference = externalReference;
        externalReference = newExternalReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE, oldExternalReference, newExternalReference);
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
    public void setExternalReference(ExternalReference newExternalReference) {
        if (newExternalReference != externalReference) {
            NotificationChain msgs = null;
            if (externalReference != null)
                msgs = ((InternalEObject) externalReference).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            if (newExternalReference != null)
                msgs = ((InternalEObject) newExternalReference).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            msgs = basicSetExternalReference(newExternalReference, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE,
                    newExternalReference, newExternalReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.APPLICATION__FORMAL_PARAMETERS:
            return ((InternalEList<?>) getFormalParameters()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.APPLICATION__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.APPLICATION__TYPE:
            return basicSetType(null, msgs);
        case Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE:
            return basicSetExternalReference(null, msgs);
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
        case Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.APPLICATION__FORMAL_PARAMETERS:
            return getFormalParameters();
        case Xpdl2Package.APPLICATION__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.APPLICATION__TYPE:
            return getType();
        case Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE:
            return getExternalReference();
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
        case Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.APPLICATION__FORMAL_PARAMETERS:
            getFormalParameters().clear();
            getFormalParameters().addAll((Collection<? extends FormalParameter>) newValue);
            return;
        case Xpdl2Package.APPLICATION__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.APPLICATION__TYPE:
            setType((ApplicationType) newValue);
            return;
        case Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) newValue);
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
        case Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.APPLICATION__FORMAL_PARAMETERS:
            getFormalParameters().clear();
            return;
        case Xpdl2Package.APPLICATION__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.APPLICATION__TYPE:
            setType((ApplicationType) null);
            return;
        case Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) null);
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
        case Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.APPLICATION__FORMAL_PARAMETERS:
            return formalParameters != null && !formalParameters.isEmpty();
        case Xpdl2Package.APPLICATION__DESCRIPTION:
            return description != null;
        case Xpdl2Package.APPLICATION__TYPE:
            return type != null;
        case Xpdl2Package.APPLICATION__EXTERNAL_REFERENCE:
            return externalReference != null;
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
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == FormalParametersContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.APPLICATION__FORMAL_PARAMETERS:
                return Xpdl2Package.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.APPLICATION__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
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
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.APPLICATION__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == FormalParametersContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS:
                return Xpdl2Package.APPLICATION__FORMAL_PARAMETERS;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION:
                return Xpdl2Package.APPLICATION__DESCRIPTION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} //ApplicationImpl
