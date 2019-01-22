/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.QualifiedHolding;

import java.math.BigDecimal;
import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Qualified Holding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getString <em>String</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getInteger <em>Integer</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getDecimal <em>Decimal</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getDate <em>Date</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getTime <em>Time</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getDateTime <em>Date Time</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#isBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getEnum <em>Enum</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getEnumSet <em>Enum Set</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl#getElement <em>Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class QualifiedHoldingImpl extends EObjectImpl implements QualifiedHolding {
    /**
     * The default value of the '{@link #getString() <em>String</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getString()
     * @generated
     * @ordered
     */
    protected static final String STRING_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getString() <em>String</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getString()
     * @generated
     * @ordered
     */
    protected String string = STRING_EDEFAULT;

    /**
     * The default value of the '{@link #getInteger() <em>Integer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInteger()
     * @generated
     * @ordered
     */
    protected static final long INTEGER_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getInteger() <em>Integer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInteger()
     * @generated
     * @ordered
     */
    protected long integer = INTEGER_EDEFAULT;

    /**
     * This is true if the Integer attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean integerESet;

    /**
     * The default value of the '{@link #getDecimal() <em>Decimal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDecimal()
     * @generated
     * @ordered
     */
    protected static final BigDecimal DECIMAL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDecimal() <em>Decimal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDecimal()
     * @generated
     * @ordered
     */
    protected BigDecimal decimal = DECIMAL_EDEFAULT;

    /**
     * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar date = DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getTime() <em>Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTime()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTime() <em>Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTime()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar time = TIME_EDEFAULT;

    /**
     * The default value of the '{@link #getDateTime() <em>Date Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDateTime()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar DATE_TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDateTime() <em>Date Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDateTime()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar dateTime = DATE_TIME_EDEFAULT;

    /**
     * The default value of the '{@link #isBoolean() <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isBoolean()
     * @generated
     * @ordered
     */
    protected static final boolean BOOLEAN_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBoolean() <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isBoolean()
     * @generated
     * @ordered
     */
    protected boolean boolean_ = BOOLEAN_EDEFAULT;

    /**
     * This is true if the Boolean attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean booleanESet;

    /**
     * The default value of the '{@link #getEnum() <em>Enum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnum()
     * @generated
     * @ordered
     */
    protected static final String ENUM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEnum() <em>Enum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnum()
     * @generated
     * @ordered
     */
    protected String enum_ = ENUM_EDEFAULT;

    /**
     * The cached value of the '{@link #getEnumSet() <em>Enum Set</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnumSet()
     * @generated
     * @ordered
     */
    protected EList<String> enumSet;

    /**
     * The cached value of the '{@link #getElement() <em>Element</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getElement()
     * @generated
     * @ordered
     */
    protected EList<String> element;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected QualifiedHoldingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.QUALIFIED_HOLDING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getString() {
        return string;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setString(String newString) {
        String oldString = string;
        string = newString;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__STRING, oldString, string));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getInteger() {
        return integer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInteger(long newInteger) {
        long oldInteger = integer;
        integer = newInteger;
        boolean oldIntegerESet = integerESet;
        integerESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__INTEGER, oldInteger, integer, !oldIntegerESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetInteger() {
        long oldInteger = integer;
        boolean oldIntegerESet = integerESet;
        integer = INTEGER_EDEFAULT;
        integerESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.QUALIFIED_HOLDING__INTEGER, oldInteger, INTEGER_EDEFAULT, oldIntegerESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetInteger() {
        return integerESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigDecimal getDecimal() {
        return decimal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDecimal(BigDecimal newDecimal) {
        BigDecimal oldDecimal = decimal;
        decimal = newDecimal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__DECIMAL, oldDecimal, decimal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDate(XMLGregorianCalendar newDate) {
        XMLGregorianCalendar oldDate = date;
        date = newDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__DATE, oldDate, date));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTime(XMLGregorianCalendar newTime) {
        XMLGregorianCalendar oldTime = time;
        time = newTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__TIME, oldTime, time));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDateTime(XMLGregorianCalendar newDateTime) {
        XMLGregorianCalendar oldDateTime = dateTime;
        dateTime = newDateTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__DATE_TIME, oldDateTime, dateTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isBoolean() {
        return boolean_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBoolean(boolean newBoolean) {
        boolean oldBoolean = boolean_;
        boolean_ = newBoolean;
        boolean oldBooleanESet = booleanESet;
        booleanESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__BOOLEAN, oldBoolean, boolean_, !oldBooleanESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetBoolean() {
        boolean oldBoolean = boolean_;
        boolean oldBooleanESet = booleanESet;
        boolean_ = BOOLEAN_EDEFAULT;
        booleanESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.QUALIFIED_HOLDING__BOOLEAN, oldBoolean, BOOLEAN_EDEFAULT, oldBooleanESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetBoolean() {
        return booleanESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getEnum() {
        return enum_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnum(String newEnum) {
        String oldEnum = enum_;
        enum_ = newEnum;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.QUALIFIED_HOLDING__ENUM, oldEnum, enum_));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getEnumSet() {
        if (enumSet == null) {
            enumSet = new EDataTypeEList<String>(String.class, this, DePackage.QUALIFIED_HOLDING__ENUM_SET);
        }
        return enumSet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getElement() {
        if (element == null) {
            element = new EDataTypeEList<String>(String.class, this, DePackage.QUALIFIED_HOLDING__ELEMENT);
        }
        return element;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DePackage.QUALIFIED_HOLDING__STRING:
                return getString();
            case DePackage.QUALIFIED_HOLDING__INTEGER:
                return getInteger();
            case DePackage.QUALIFIED_HOLDING__DECIMAL:
                return getDecimal();
            case DePackage.QUALIFIED_HOLDING__DATE:
                return getDate();
            case DePackage.QUALIFIED_HOLDING__TIME:
                return getTime();
            case DePackage.QUALIFIED_HOLDING__DATE_TIME:
                return getDateTime();
            case DePackage.QUALIFIED_HOLDING__BOOLEAN:
                return isBoolean();
            case DePackage.QUALIFIED_HOLDING__ENUM:
                return getEnum();
            case DePackage.QUALIFIED_HOLDING__ENUM_SET:
                return getEnumSet();
            case DePackage.QUALIFIED_HOLDING__ELEMENT:
                return getElement();
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
            case DePackage.QUALIFIED_HOLDING__STRING:
                setString((String)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__INTEGER:
                setInteger((Long)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__DECIMAL:
                setDecimal((BigDecimal)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__DATE:
                setDate((XMLGregorianCalendar)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__TIME:
                setTime((XMLGregorianCalendar)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__DATE_TIME:
                setDateTime((XMLGregorianCalendar)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__BOOLEAN:
                setBoolean((Boolean)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__ENUM:
                setEnum((String)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__ENUM_SET:
                getEnumSet().clear();
                getEnumSet().addAll((Collection<? extends String>)newValue);
                return;
            case DePackage.QUALIFIED_HOLDING__ELEMENT:
                getElement().clear();
                getElement().addAll((Collection<? extends String>)newValue);
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
            case DePackage.QUALIFIED_HOLDING__STRING:
                setString(STRING_EDEFAULT);
                return;
            case DePackage.QUALIFIED_HOLDING__INTEGER:
                unsetInteger();
                return;
            case DePackage.QUALIFIED_HOLDING__DECIMAL:
                setDecimal(DECIMAL_EDEFAULT);
                return;
            case DePackage.QUALIFIED_HOLDING__DATE:
                setDate(DATE_EDEFAULT);
                return;
            case DePackage.QUALIFIED_HOLDING__TIME:
                setTime(TIME_EDEFAULT);
                return;
            case DePackage.QUALIFIED_HOLDING__DATE_TIME:
                setDateTime(DATE_TIME_EDEFAULT);
                return;
            case DePackage.QUALIFIED_HOLDING__BOOLEAN:
                unsetBoolean();
                return;
            case DePackage.QUALIFIED_HOLDING__ENUM:
                setEnum(ENUM_EDEFAULT);
                return;
            case DePackage.QUALIFIED_HOLDING__ENUM_SET:
                getEnumSet().clear();
                return;
            case DePackage.QUALIFIED_HOLDING__ELEMENT:
                getElement().clear();
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
            case DePackage.QUALIFIED_HOLDING__STRING:
                return STRING_EDEFAULT == null ? string != null : !STRING_EDEFAULT.equals(string);
            case DePackage.QUALIFIED_HOLDING__INTEGER:
                return isSetInteger();
            case DePackage.QUALIFIED_HOLDING__DECIMAL:
                return DECIMAL_EDEFAULT == null ? decimal != null : !DECIMAL_EDEFAULT.equals(decimal);
            case DePackage.QUALIFIED_HOLDING__DATE:
                return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
            case DePackage.QUALIFIED_HOLDING__TIME:
                return TIME_EDEFAULT == null ? time != null : !TIME_EDEFAULT.equals(time);
            case DePackage.QUALIFIED_HOLDING__DATE_TIME:
                return DATE_TIME_EDEFAULT == null ? dateTime != null : !DATE_TIME_EDEFAULT.equals(dateTime);
            case DePackage.QUALIFIED_HOLDING__BOOLEAN:
                return isSetBoolean();
            case DePackage.QUALIFIED_HOLDING__ENUM:
                return ENUM_EDEFAULT == null ? enum_ != null : !ENUM_EDEFAULT.equals(enum_);
            case DePackage.QUALIFIED_HOLDING__ENUM_SET:
                return enumSet != null && !enumSet.isEmpty();
            case DePackage.QUALIFIED_HOLDING__ELEMENT:
                return element != null && !element.isEmpty();
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
        result.append(" (string: ");
        result.append(string);
        result.append(", integer: ");
        if (integerESet) result.append(integer); else result.append("<unset>");
        result.append(", decimal: ");
        result.append(decimal);
        result.append(", date: ");
        result.append(date);
        result.append(", time: ");
        result.append(time);
        result.append(", dateTime: ");
        result.append(dateTime);
        result.append(", boolean: ");
        if (booleanESet) result.append(boolean_); else result.append("<unset>");
        result.append(", enum: ");
        result.append(enum_);
        result.append(", enumSet: ");
        result.append(enumSet);
        result.append(", element: ");
        result.append(element);
        result.append(')');
        return result.toString();
    }

} //QualifiedHoldingImpl
