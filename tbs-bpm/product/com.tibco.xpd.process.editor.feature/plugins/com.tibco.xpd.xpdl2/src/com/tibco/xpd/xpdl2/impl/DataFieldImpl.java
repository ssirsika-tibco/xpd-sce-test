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

import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#isIsArray <em>Is Array</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#isCorrelation <em>Correlation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl#getDeprecatedDataIsArray <em>Deprecated Data Is Array</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataFieldImpl extends NamedElementImpl implements DataField {
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
     * The cached value of the '{@link #getExtendedAttributes() <em>Extended Attributes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The cached value of the '{@link #getInitialValue() <em>Initial Value</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInitialValue()
     * @generated
     * @ordered
     */
    protected Expression initialValue;

    /**
     * The default value of the '{@link #isCorrelation() <em>Correlation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isCorrelation()
     * @generated
     * @ordered
     */
    protected static final boolean CORRELATION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isCorrelation() <em>Correlation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isCorrelation()
     * @generated
     * @ordered
     */
    protected boolean correlation = CORRELATION_EDEFAULT;

    /**
     * This is true if the Correlation attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean correlationESet;

    /**
     * The default value of the '{@link #getDeprecatedDataIsArray() <em>Deprecated Data Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedDataIsArray()
     * @generated
     * @ordered
     */
    protected static final String DEPRECATED_DATA_IS_ARRAY_EDEFAULT = "FALSE"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDeprecatedDataIsArray() <em>Deprecated Data Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedDataIsArray()
     * @generated
     * @ordered
     */
    protected String deprecatedDataIsArray = DEPRECATED_DATA_IS_ARRAY_EDEFAULT;

    /**
     * This is true if the Deprecated Data Is Array attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean deprecatedDataIsArrayESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DataFieldImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.DATA_FIELD;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS);
        }
        return otherElements;
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
                    Xpdl2Package.DATA_FIELD__DATA_TYPE, oldDataType, newDataType);
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
                msgs = ((InternalEObject) dataType)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__DATA_TYPE, null, msgs);
            if (newDataType != null)
                msgs = ((InternalEObject) newDataType)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__DATA_TYPE, null, msgs);
            msgs = basicSetDataType(newDataType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__DATA_TYPE, newDataType,
                    newDataType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getInitialValue() {
        return initialValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetInitialValue(Expression newInitialValue, NotificationChain msgs) {
        Expression oldInitialValue = initialValue;
        initialValue = newInitialValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DATA_FIELD__INITIAL_VALUE, oldInitialValue, newInitialValue);
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
    public void setInitialValue(Expression newInitialValue) {
        if (newInitialValue != initialValue) {
            NotificationChain msgs = null;
            if (initialValue != null)
                msgs = ((InternalEObject) initialValue).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__INITIAL_VALUE,
                        null,
                        msgs);
            if (newInitialValue != null)
                msgs = ((InternalEObject) newInitialValue)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__INITIAL_VALUE, null, msgs);
            msgs = basicSetInitialValue(newInitialValue, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__INITIAL_VALUE,
                    newInitialValue, newInitialValue));
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
                    Xpdl2Package.DATA_FIELD__LENGTH, oldLength, newLength);
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
                msgs = ((InternalEObject) length)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__LENGTH, null, msgs);
            if (newLength != null)
                msgs = ((InternalEObject) newLength)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__LENGTH, null, msgs);
            msgs = basicSetLength(newLength, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__LENGTH, newLength,
                    newLength));
    }

    /**
     * <!-- begin-user-doc -->
     * Allow lower case literals (because once upon a time this is how they 
     * were stored - Doh!).  This is why dataIsArray is a String rather than
     * the enumerated type <code>IsArrayType</code>.
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__IS_ARRAY, oldIsArray,
                    isArray, !oldIsArrayESet));
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
                    Xpdl2Package.DATA_FIELD__DESCRIPTION, oldDescription, newDescription);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.DATA_FIELD__DESCRIPTION, null, msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__DESCRIPTION, newDescription,
                    newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isCorrelation() {
        return correlation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCorrelation(boolean newCorrelation) {
        boolean oldCorrelation = correlation;
        correlation = newCorrelation;
        boolean oldCorrelationESet = correlationESet;
        correlationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__CORRELATION, oldCorrelation,
                    correlation, !oldCorrelationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCorrelation() {
        boolean oldCorrelation = correlation;
        boolean oldCorrelationESet = correlationESet;
        correlation = CORRELATION_EDEFAULT;
        correlationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.DATA_FIELD__CORRELATION,
                    oldCorrelation, CORRELATION_EDEFAULT, oldCorrelationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCorrelation() {
        return correlationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDeprecatedDataIsArray() {
        return deprecatedDataIsArray;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedDataIsArray(String newDeprecatedDataIsArray) {
        String oldDeprecatedDataIsArray = deprecatedDataIsArray;
        deprecatedDataIsArray = newDeprecatedDataIsArray;
        boolean oldDeprecatedDataIsArrayESet = deprecatedDataIsArrayESet;
        deprecatedDataIsArrayESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY,
                    oldDeprecatedDataIsArray, deprecatedDataIsArray, !oldDeprecatedDataIsArrayESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDeprecatedDataIsArray() {
        String oldDeprecatedDataIsArray = deprecatedDataIsArray;
        boolean oldDeprecatedDataIsArrayESet = deprecatedDataIsArrayESet;
        deprecatedDataIsArray = DEPRECATED_DATA_IS_ARRAY_EDEFAULT;
        deprecatedDataIsArrayESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY,
                    oldDeprecatedDataIsArray, DEPRECATED_DATA_IS_ARRAY_EDEFAULT, oldDeprecatedDataIsArrayESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDeprecatedDataIsArray() {
        return deprecatedDataIsArrayESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_FIELD__READ_ONLY, oldReadOnly,
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.DATA_FIELD__READ_ONLY, oldReadOnly,
                    READ_ONLY_EDEFAULT, oldReadOnlyESet));
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
     * @generated
     */
    public void unsetIsArray() {
        boolean oldIsArray = isArray;
        boolean oldIsArrayESet = isArrayESet;
        isArray = IS_ARRAY_EDEFAULT;
        isArrayESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.DATA_FIELD__IS_ARRAY, oldIsArray,
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
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.DATA_FIELD__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.DATA_FIELD__DATA_TYPE:
            return basicSetDataType(null, msgs);
        case Xpdl2Package.DATA_FIELD__LENGTH:
            return basicSetLength(null, msgs);
        case Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.DATA_FIELD__INITIAL_VALUE:
            return basicSetInitialValue(null, msgs);
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
        case Xpdl2Package.DATA_FIELD__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.DATA_FIELD__DATA_TYPE:
            return getDataType();
        case Xpdl2Package.DATA_FIELD__LENGTH:
            return getLength();
        case Xpdl2Package.DATA_FIELD__IS_ARRAY:
            return isIsArray();
        case Xpdl2Package.DATA_FIELD__READ_ONLY:
            return isReadOnly();
        case Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.DATA_FIELD__INITIAL_VALUE:
            return getInitialValue();
        case Xpdl2Package.DATA_FIELD__CORRELATION:
            return isCorrelation();
        case Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY:
            return getDeprecatedDataIsArray();
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
        case Xpdl2Package.DATA_FIELD__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.DATA_FIELD__DATA_TYPE:
            setDataType((DataType) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__LENGTH:
            setLength((Length) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__IS_ARRAY:
            setIsArray((Boolean) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__READ_ONLY:
            setReadOnly((Boolean) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__INITIAL_VALUE:
            setInitialValue((Expression) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__CORRELATION:
            setCorrelation((Boolean) newValue);
            return;
        case Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY:
            setDeprecatedDataIsArray((String) newValue);
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
        case Xpdl2Package.DATA_FIELD__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.DATA_FIELD__DATA_TYPE:
            setDataType((DataType) null);
            return;
        case Xpdl2Package.DATA_FIELD__LENGTH:
            setLength((Length) null);
            return;
        case Xpdl2Package.DATA_FIELD__IS_ARRAY:
            unsetIsArray();
            return;
        case Xpdl2Package.DATA_FIELD__READ_ONLY:
            unsetReadOnly();
            return;
        case Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.DATA_FIELD__INITIAL_VALUE:
            setInitialValue((Expression) null);
            return;
        case Xpdl2Package.DATA_FIELD__CORRELATION:
            unsetCorrelation();
            return;
        case Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY:
            unsetDeprecatedDataIsArray();
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
        case Xpdl2Package.DATA_FIELD__DESCRIPTION:
            return description != null;
        case Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.DATA_FIELD__DATA_TYPE:
            return dataType != null;
        case Xpdl2Package.DATA_FIELD__LENGTH:
            return length != null;
        case Xpdl2Package.DATA_FIELD__IS_ARRAY:
            return isSetIsArray();
        case Xpdl2Package.DATA_FIELD__READ_ONLY:
            return isSetReadOnly();
        case Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.DATA_FIELD__INITIAL_VALUE:
            return initialValue != null;
        case Xpdl2Package.DATA_FIELD__CORRELATION:
            return isSetCorrelation();
        case Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY:
            return isSetDeprecatedDataIsArray();
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
            case Xpdl2Package.DATA_FIELD__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
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
                return Xpdl2Package.DATA_FIELD__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES;
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
        result.append(", correlation: "); //$NON-NLS-1$
        if (correlationESet)
            result.append(correlation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", deprecatedDataIsArray: "); //$NON-NLS-1$
        if (deprecatedDataIsArrayESet)
            result.append(deprecatedDataIsArray);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //DataFieldImpl
