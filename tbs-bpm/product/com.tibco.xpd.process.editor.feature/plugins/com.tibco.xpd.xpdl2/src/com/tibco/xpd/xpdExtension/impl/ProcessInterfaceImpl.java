/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

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

import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdInterfaceType;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.impl.NamedElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Interface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getFormalParameters <em>Formal Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getStartMethods <em>Start Methods</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getIntermediateMethods <em>Intermediate Methods</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getXpdInterfaceType <em>Xpd Interface Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl#getServiceProcessConfiguration <em>Service Process Configuration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessInterfaceImpl extends NamedElementImpl implements ProcessInterface {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

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
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The cached value of the '{@link #getStartMethods() <em>Start Methods</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartMethods()
     * @generated
     * @ordered
     */
    protected EList<StartMethod> startMethods;

    /**
     * The cached value of the '{@link #getIntermediateMethods() <em>Intermediate Methods</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIntermediateMethods()
     * @generated
     * @ordered
     */
    protected EList<IntermediateMethod> intermediateMethods;

    /**
     * The default value of the '{@link #getXpdInterfaceType() <em>Xpd Interface Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXpdInterfaceType()
     * @generated
     * @ordered
     */
    protected static final XpdInterfaceType XPD_INTERFACE_TYPE_EDEFAULT = XpdInterfaceType.PROCESS_INTERFACE;

    /**
     * The cached value of the '{@link #getXpdInterfaceType() <em>Xpd Interface Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXpdInterfaceType()
     * @generated
     * @ordered
     */
    protected XpdInterfaceType xpdInterfaceType = XPD_INTERFACE_TYPE_EDEFAULT;

    /**
     * This is true if the Xpd Interface Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean xpdInterfaceTypeESet;

    /**
     * The cached value of the '{@link #getServiceProcessConfiguration() <em>Service Process Configuration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServiceProcessConfiguration()
     * @generated
     * @ordered
     */
    protected ServiceProcessConfiguration serviceProcessConfiguration;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ProcessInterfaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.PROCESS_INTERFACE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FormalParameter> getFormalParameters() {
        if (formalParameters == null) {
            formalParameters = new EObjectContainmentEList<FormalParameter>(FormalParameter.class, this,
                    XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS);
        }
        return formalParameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<StartMethod> getStartMethods() {
        if (startMethods == null) {
            startMethods = new EObjectContainmentEList<StartMethod>(StartMethod.class, this,
                    XpdExtensionPackage.PROCESS_INTERFACE__START_METHODS);
        }
        return startMethods;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<IntermediateMethod> getIntermediateMethods() {
        if (intermediateMethods == null) {
            intermediateMethods = new EObjectContainmentEList<IntermediateMethod>(IntermediateMethod.class, this,
                    XpdExtensionPackage.PROCESS_INTERFACE__INTERMEDIATE_METHODS);
        }
        return intermediateMethods;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XpdInterfaceType getXpdInterfaceType() {
        return xpdInterfaceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setXpdInterfaceType(XpdInterfaceType newXpdInterfaceType) {
        XpdInterfaceType oldXpdInterfaceType = xpdInterfaceType;
        xpdInterfaceType = newXpdInterfaceType == null ? XPD_INTERFACE_TYPE_EDEFAULT : newXpdInterfaceType;
        boolean oldXpdInterfaceTypeESet = xpdInterfaceTypeESet;
        xpdInterfaceTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PROCESS_INTERFACE__XPD_INTERFACE_TYPE, oldXpdInterfaceType, xpdInterfaceType,
                    !oldXpdInterfaceTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetXpdInterfaceType() {
        XpdInterfaceType oldXpdInterfaceType = xpdInterfaceType;
        boolean oldXpdInterfaceTypeESet = xpdInterfaceTypeESet;
        xpdInterfaceType = XPD_INTERFACE_TYPE_EDEFAULT;
        xpdInterfaceTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.PROCESS_INTERFACE__XPD_INTERFACE_TYPE, oldXpdInterfaceType,
                    XPD_INTERFACE_TYPE_EDEFAULT, oldXpdInterfaceTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetXpdInterfaceType() {
        return xpdInterfaceTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServiceProcessConfiguration getServiceProcessConfiguration() {
        return serviceProcessConfiguration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetServiceProcessConfiguration(
            ServiceProcessConfiguration newServiceProcessConfiguration, NotificationChain msgs) {
        ServiceProcessConfiguration oldServiceProcessConfiguration = serviceProcessConfiguration;
        serviceProcessConfiguration = newServiceProcessConfiguration;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION,
                    oldServiceProcessConfiguration, newServiceProcessConfiguration);
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
    public void setServiceProcessConfiguration(ServiceProcessConfiguration newServiceProcessConfiguration) {
        if (newServiceProcessConfiguration != serviceProcessConfiguration) {
            NotificationChain msgs = null;
            if (serviceProcessConfiguration != null)
                msgs = ((InternalEObject) serviceProcessConfiguration).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION,
                        null,
                        msgs);
            if (newServiceProcessConfiguration != null)
                msgs = ((InternalEObject) newServiceProcessConfiguration).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION,
                        null,
                        msgs);
            msgs = basicSetServiceProcessConfiguration(newServiceProcessConfiguration, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION,
                    newServiceProcessConfiguration, newServiceProcessConfiguration));
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
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
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
                    XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION, oldDescription, newDescription);
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
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION,
                        null,
                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION,
                    newDescription, newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS:
            return ((InternalEList<?>) getFormalParameters()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.PROCESS_INTERFACE__START_METHODS:
            return ((InternalEList<?>) getStartMethods()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.PROCESS_INTERFACE__INTERMEDIATE_METHODS:
            return ((InternalEList<?>) getIntermediateMethods()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION:
            return basicSetServiceProcessConfiguration(null, msgs);
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
        case XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION:
            return getDescription();
        case XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS:
            return getFormalParameters();
        case XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case XpdExtensionPackage.PROCESS_INTERFACE__START_METHODS:
            return getStartMethods();
        case XpdExtensionPackage.PROCESS_INTERFACE__INTERMEDIATE_METHODS:
            return getIntermediateMethods();
        case XpdExtensionPackage.PROCESS_INTERFACE__XPD_INTERFACE_TYPE:
            return getXpdInterfaceType();
        case XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION:
            return getServiceProcessConfiguration();
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
        case XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS:
            getFormalParameters().clear();
            getFormalParameters().addAll((Collection<? extends FormalParameter>) newValue);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__START_METHODS:
            getStartMethods().clear();
            getStartMethods().addAll((Collection<? extends StartMethod>) newValue);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__INTERMEDIATE_METHODS:
            getIntermediateMethods().clear();
            getIntermediateMethods().addAll((Collection<? extends IntermediateMethod>) newValue);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__XPD_INTERFACE_TYPE:
            setXpdInterfaceType((XpdInterfaceType) newValue);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION:
            setServiceProcessConfiguration((ServiceProcessConfiguration) newValue);
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
        case XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION:
            setDescription((Description) null);
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS:
            getFormalParameters().clear();
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__START_METHODS:
            getStartMethods().clear();
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__INTERMEDIATE_METHODS:
            getIntermediateMethods().clear();
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__XPD_INTERFACE_TYPE:
            unsetXpdInterfaceType();
            return;
        case XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION:
            setServiceProcessConfiguration((ServiceProcessConfiguration) null);
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
        case XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION:
            return description != null;
        case XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS:
            return formalParameters != null && !formalParameters.isEmpty();
        case XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case XpdExtensionPackage.PROCESS_INTERFACE__START_METHODS:
            return startMethods != null && !startMethods.isEmpty();
        case XpdExtensionPackage.PROCESS_INTERFACE__INTERMEDIATE_METHODS:
            return intermediateMethods != null && !intermediateMethods.isEmpty();
        case XpdExtensionPackage.PROCESS_INTERFACE__XPD_INTERFACE_TYPE:
            return isSetXpdInterfaceType();
        case XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION:
            return serviceProcessConfiguration != null;
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
        if (baseClass == DescribedElement.class) {
            switch (derivedFeatureID) {
            case XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == FormalParametersContainer.class) {
            switch (derivedFeatureID) {
            case XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS:
                return Xpdl2Package.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS:
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
        if (baseClass == DescribedElement.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION:
                return XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == FormalParametersContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS:
                return XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS;
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", xpdInterfaceType: "); //$NON-NLS-1$
        if (xpdInterfaceTypeESet)
            result.append(xpdInterfaceType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //ProcessInterfaceImpl