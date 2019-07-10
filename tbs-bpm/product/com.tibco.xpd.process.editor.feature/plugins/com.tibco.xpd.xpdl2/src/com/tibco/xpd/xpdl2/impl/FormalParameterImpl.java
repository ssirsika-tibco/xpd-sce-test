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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Formal Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#isIsArray <em>Is Array</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl#isRequired <em>Required</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FormalParameterImpl extends NamedElementImpl implements FormalParameter {
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
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The cached value of the '{@link #getDataType() <em>Data Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataType()
     * @generated
     * @ordered
     */
    protected DataType dataType;

    /**
     * The cached value of the '{@link #getLength() <em>Length</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLength()
     * @generated
     * @ordered
     */
    protected Length length;

    /**
     * The default value of the '{@link #isIsArray() <em>Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsArray()
     * @generated
     * @ordered
     */
    protected static final boolean IS_ARRAY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsArray() <em>Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsArray()
     * @generated
     * @ordered
     */
    protected boolean isArray = IS_ARRAY_EDEFAULT;

    /**
     * This is true if the Is Array attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean isArrayESet;

    /**
     * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReadOnly()
     * @generated
     * @ordered
     */
    protected static final boolean READ_ONLY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReadOnly()
     * @generated
     * @ordered
     */
    protected boolean readOnly = READ_ONLY_EDEFAULT;

    /**
     * This is true if the Read Only attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean readOnlyESet;

    /**
     * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMode()
     * @generated
     * @ordered
     */
    protected static final ModeType MODE_EDEFAULT = ModeType.IN_LITERAL;

    /**
     * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMode()
     * @generated
     * @ordered
     */
    protected ModeType mode = MODE_EDEFAULT;

    /**
     * This is true if the Mode attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean modeESet;

    /**
     * The default value of the '{@link #isRequired() <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRequired()
     * @generated
     * @ordered
     */
    protected static final boolean REQUIRED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isRequired() <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRequired()
     * @generated
     * @ordered
     */
    protected boolean required = REQUIRED_EDEFAULT;

    /**
     * This is true if the Required attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean requiredESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FormalParameterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.FORMAL_PARAMETER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDataType(DataType newDataType, NotificationChain msgs) {
        DataType oldDataType = dataType;
        dataType = newDataType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE, oldDataType, newDataType);
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
    public void setDataType(DataType newDataType) {
        if (newDataType != dataType) {
            NotificationChain msgs = null;
            if (dataType != null)
                msgs = ((InternalEObject) dataType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE,
                        null,
                        msgs);
            if (newDataType != null)
                msgs = ((InternalEObject) newDataType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE,
                        null,
                        msgs);
            msgs = basicSetDataType(newDataType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE, newDataType,
                    newDataType));
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
                    Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION, oldDescription, newDescription);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION,
                        null,
                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION,
                    newDescription, newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Length getLength() {
        return length;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLength(Length newLength, NotificationChain msgs) {
        Length oldLength = length;
        length = newLength;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.FORMAL_PARAMETER__LENGTH, oldLength, newLength);
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
    public void setLength(Length newLength) {
        if (newLength != length) {
            NotificationChain msgs = null;
            if (length != null)
                msgs = ((InternalEObject) length).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.FORMAL_PARAMETER__LENGTH,
                        null,
                        msgs);
            if (newLength != null)
                msgs = ((InternalEObject) newLength)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.FORMAL_PARAMETER__LENGTH, null, msgs);
            msgs = basicSetLength(newLength, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.FORMAL_PARAMETER__LENGTH, newLength,
                    newLength));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsArray() {
        return isArray;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIsArray(boolean newIsArray) {
        boolean oldIsArray = isArray;
        isArray = newIsArray;
        boolean oldIsArrayESet = isArrayESet;
        isArrayESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.FORMAL_PARAMETER__IS_ARRAY, oldIsArray,
                    isArray, !oldIsArrayESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetIsArray() {
        boolean oldIsArray = isArray;
        boolean oldIsArrayESet = isArrayESet;
        isArray = IS_ARRAY_EDEFAULT;
        isArrayESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.FORMAL_PARAMETER__IS_ARRAY, oldIsArray,
                    IS_ARRAY_EDEFAULT, oldIsArrayESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetIsArray() {
        return isArrayESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModeType getMode() {
        return mode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMode(ModeType newMode) {
        ModeType oldMode = mode;
        mode = newMode == null ? MODE_EDEFAULT : newMode;
        boolean oldModeESet = modeESet;
        modeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.FORMAL_PARAMETER__MODE, oldMode, mode,
                    !oldModeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMode() {
        ModeType oldMode = mode;
        boolean oldModeESet = modeESet;
        mode = MODE_EDEFAULT;
        modeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.FORMAL_PARAMETER__MODE, oldMode,
                    MODE_EDEFAULT, oldModeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMode() {
        return modeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRequired(boolean newRequired) {
        boolean oldRequired = required;
        required = newRequired;
        boolean oldRequiredESet = requiredESet;
        requiredESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.FORMAL_PARAMETER__REQUIRED, oldRequired,
                    required, !oldRequiredESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetRequired() {
        boolean oldRequired = required;
        boolean oldRequiredESet = requiredESet;
        required = REQUIRED_EDEFAULT;
        requiredESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.FORMAL_PARAMETER__REQUIRED,
                    oldRequired, REQUIRED_EDEFAULT, oldRequiredESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetRequired() {
        return requiredESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReadOnly(boolean newReadOnly) {
        boolean oldReadOnly = readOnly;
        readOnly = newReadOnly;
        boolean oldReadOnlyESet = readOnlyESet;
        readOnlyESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.FORMAL_PARAMETER__READ_ONLY, oldReadOnly,
                    readOnly, !oldReadOnlyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReadOnly() {
        boolean oldReadOnly = readOnly;
        boolean oldReadOnlyESet = readOnlyESet;
        readOnly = READ_ONLY_EDEFAULT;
        readOnlyESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.FORMAL_PARAMETER__READ_ONLY,
                    oldReadOnly, READ_ONLY_EDEFAULT, oldReadOnlyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReadOnly() {
        return readOnlyESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EObject getOtherElement(String elementName) {
        return Xpdl2Operations.getOtherElement(this, elementName);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE:
            return basicSetDataType(null, msgs);
        case Xpdl2Package.FORMAL_PARAMETER__LENGTH:
            return basicSetLength(null, msgs);
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
        case Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE:
            return getDataType();
        case Xpdl2Package.FORMAL_PARAMETER__LENGTH:
            return getLength();
        case Xpdl2Package.FORMAL_PARAMETER__IS_ARRAY:
            return isIsArray();
        case Xpdl2Package.FORMAL_PARAMETER__READ_ONLY:
            return isReadOnly();
        case Xpdl2Package.FORMAL_PARAMETER__MODE:
            return getMode();
        case Xpdl2Package.FORMAL_PARAMETER__REQUIRED:
            return isRequired();
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
        case Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE:
            setDataType((DataType) newValue);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__LENGTH:
            setLength((Length) newValue);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__IS_ARRAY:
            setIsArray((Boolean) newValue);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__READ_ONLY:
            setReadOnly((Boolean) newValue);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__MODE:
            setMode((ModeType) newValue);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__REQUIRED:
            setRequired((Boolean) newValue);
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
        case Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE:
            setDataType((DataType) null);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__LENGTH:
            setLength((Length) null);
            return;
        case Xpdl2Package.FORMAL_PARAMETER__IS_ARRAY:
            unsetIsArray();
            return;
        case Xpdl2Package.FORMAL_PARAMETER__READ_ONLY:
            unsetReadOnly();
            return;
        case Xpdl2Package.FORMAL_PARAMETER__MODE:
            unsetMode();
            return;
        case Xpdl2Package.FORMAL_PARAMETER__REQUIRED:
            unsetRequired();
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
        case Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION:
            return description != null;
        case Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE:
            return dataType != null;
        case Xpdl2Package.FORMAL_PARAMETER__LENGTH:
            return length != null;
        case Xpdl2Package.FORMAL_PARAMETER__IS_ARRAY:
            return isSetIsArray();
        case Xpdl2Package.FORMAL_PARAMETER__READ_ONLY:
            return isSetReadOnly();
        case Xpdl2Package.FORMAL_PARAMETER__MODE:
            return isSetMode();
        case Xpdl2Package.FORMAL_PARAMETER__REQUIRED:
            return isSetRequired();
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
            case Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS:
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
                return Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS;
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
        result.append(", isArray: "); //$NON-NLS-1$
        if (isArrayESet)
            result.append(isArray);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", readOnly: "); //$NON-NLS-1$
        if (readOnlyESet)
            result.append(readOnly);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", mode: "); //$NON-NLS-1$
        if (modeESet)
            result.append(mode);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", required: "); //$NON-NLS-1$
        if (requiredESet)
            result.append(required);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //FormalParameterImpl
