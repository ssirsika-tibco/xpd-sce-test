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

import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Participant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ParticipantImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ParticipantImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ParticipantImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ParticipantImpl#getParticipantType <em>Participant Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ParticipantImpl#getExternalReference <em>External Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParticipantImpl extends NamedElementImpl implements Participant {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

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
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

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
     * The cached value of the '{@link #getParticipantType() <em>Participant Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParticipantType()
     * @generated
     * @ordered
     */
    protected ParticipantTypeElem participantType;

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
    protected ParticipantImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PARTICIPANT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes =
                    new EObjectContainmentEList<ExtendedAttribute>(
                            ExtendedAttribute.class, this,
                            Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParticipantTypeElem getParticipantType() {
        return participantType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParticipantType(
            ParticipantTypeElem newParticipantType, NotificationChain msgs) {
        ParticipantTypeElem oldParticipantType = participantType;
        participantType = newParticipantType;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE,
                            oldParticipantType, newParticipantType);
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
    public void setParticipantType(ParticipantTypeElem newParticipantType) {
        if (newParticipantType != participantType) {
            NotificationChain msgs = null;
            if (participantType != null)
                msgs =
                        ((InternalEObject) participantType)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE,
                                        null,
                                        msgs);
            if (newParticipantType != null)
                msgs =
                        ((InternalEObject) newParticipantType)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE,
                                        null,
                                        msgs);
            msgs = basicSetParticipantType(newParticipantType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE,
                    newParticipantType, newParticipantType));
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
    public NotificationChain basicSetDescription(Description newDescription,
            NotificationChain msgs) {
        Description oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PARTICIPANT__DESCRIPTION,
                            oldDescription, newDescription);
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
                msgs =
                        ((InternalEObject) description)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTICIPANT__DESCRIPTION,
                                        null,
                                        msgs);
            if (newDescription != null)
                msgs =
                        ((InternalEObject) newDescription)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTICIPANT__DESCRIPTION,
                                        null,
                                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTICIPANT__DESCRIPTION, newDescription,
                    newDescription));
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
                            Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS);
        }
        return otherElements;
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
    public NotificationChain basicSetExternalReference(
            ExternalReference newExternalReference, NotificationChain msgs) {
        ExternalReference oldExternalReference = externalReference;
        externalReference = newExternalReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE,
                            oldExternalReference, newExternalReference);
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
                msgs =
                        ((InternalEObject) externalReference)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE,
                                        null,
                                        msgs);
            if (newExternalReference != null)
                msgs =
                        ((InternalEObject) newExternalReference)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE,
                                        null,
                                        msgs);
            msgs = basicSetExternalReference(newExternalReference, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE,
                    newExternalReference, newExternalReference));
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
        case Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.PARTICIPANT__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE:
            return basicSetParticipantType(null, msgs);
        case Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE:
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
        case Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.PARTICIPANT__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE:
            return getParticipantType();
        case Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE:
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
        case Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes()
                    .addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.PARTICIPANT__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE:
            setParticipantType((ParticipantTypeElem) newValue);
            return;
        case Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE:
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
        case Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.PARTICIPANT__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE:
            setParticipantType((ParticipantTypeElem) null);
            return;
        case Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE:
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
        case Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.PARTICIPANT__DESCRIPTION:
            return description != null;
        case Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.PARTICIPANT__PARTICIPANT_TYPE:
            return participantType != null;
        case Xpdl2Package.PARTICIPANT__EXTERNAL_REFERENCE:
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
            case Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PARTICIPANT__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS:
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
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.PARTICIPANT__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION:
                return Xpdl2Package.PARTICIPANT__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.PARTICIPANT__OTHER_ELEMENTS;
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(')');
        return result.toString();
    }

} //ParticipantImpl
