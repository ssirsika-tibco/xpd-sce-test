/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.impl;

import com.tibco.n2.common.api.exception.DetailedErrorLine;
import com.tibco.n2.common.api.exception.ExceptionPackage;
import com.tibco.n2.common.api.exception.SeverityType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Detailed Error Line</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DetailedErrorLineImpl#getColumnNumber <em>Column Number</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DetailedErrorLineImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DetailedErrorLineImpl#getSeverity <em>Severity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DetailedErrorLineImpl extends ErrorLineImpl implements DetailedErrorLine {
    /**
     * The default value of the '{@link #getColumnNumber() <em>Column Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnNumber()
     * @generated
     * @ordered
     */
    protected static final int COLUMN_NUMBER_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getColumnNumber() <em>Column Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnNumber()
     * @generated
     * @ordered
     */
    protected int columnNumber = COLUMN_NUMBER_EDEFAULT;

    /**
     * This is true if the Column Number attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean columnNumberESet;

    /**
     * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLineNumber()
     * @generated
     * @ordered
     */
    protected static final int LINE_NUMBER_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLineNumber()
     * @generated
     * @ordered
     */
    protected int lineNumber = LINE_NUMBER_EDEFAULT;

    /**
     * This is true if the Line Number attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean lineNumberESet;

    /**
     * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSeverity()
     * @generated
     * @ordered
     */
    protected static final SeverityType SEVERITY_EDEFAULT = SeverityType.WARNING;

    /**
     * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSeverity()
     * @generated
     * @ordered
     */
    protected SeverityType severity = SEVERITY_EDEFAULT;

    /**
     * This is true if the Severity attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean severityESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DetailedErrorLineImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ExceptionPackage.Literals.DETAILED_ERROR_LINE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setColumnNumber(int newColumnNumber) {
        int oldColumnNumber = columnNumber;
        columnNumber = newColumnNumber;
        boolean oldColumnNumberESet = columnNumberESet;
        columnNumberESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExceptionPackage.DETAILED_ERROR_LINE__COLUMN_NUMBER, oldColumnNumber, columnNumber, !oldColumnNumberESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetColumnNumber() {
        int oldColumnNumber = columnNumber;
        boolean oldColumnNumberESet = columnNumberESet;
        columnNumber = COLUMN_NUMBER_EDEFAULT;
        columnNumberESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, ExceptionPackage.DETAILED_ERROR_LINE__COLUMN_NUMBER, oldColumnNumber, COLUMN_NUMBER_EDEFAULT, oldColumnNumberESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetColumnNumber() {
        return columnNumberESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLineNumber(int newLineNumber) {
        int oldLineNumber = lineNumber;
        lineNumber = newLineNumber;
        boolean oldLineNumberESet = lineNumberESet;
        lineNumberESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExceptionPackage.DETAILED_ERROR_LINE__LINE_NUMBER, oldLineNumber, lineNumber, !oldLineNumberESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLineNumber() {
        int oldLineNumber = lineNumber;
        boolean oldLineNumberESet = lineNumberESet;
        lineNumber = LINE_NUMBER_EDEFAULT;
        lineNumberESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, ExceptionPackage.DETAILED_ERROR_LINE__LINE_NUMBER, oldLineNumber, LINE_NUMBER_EDEFAULT, oldLineNumberESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLineNumber() {
        return lineNumberESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SeverityType getSeverity() {
        return severity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSeverity(SeverityType newSeverity) {
        SeverityType oldSeverity = severity;
        severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
        boolean oldSeverityESet = severityESet;
        severityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExceptionPackage.DETAILED_ERROR_LINE__SEVERITY, oldSeverity, severity, !oldSeverityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetSeverity() {
        SeverityType oldSeverity = severity;
        boolean oldSeverityESet = severityESet;
        severity = SEVERITY_EDEFAULT;
        severityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, ExceptionPackage.DETAILED_ERROR_LINE__SEVERITY, oldSeverity, SEVERITY_EDEFAULT, oldSeverityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetSeverity() {
        return severityESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ExceptionPackage.DETAILED_ERROR_LINE__COLUMN_NUMBER:
                return getColumnNumber();
            case ExceptionPackage.DETAILED_ERROR_LINE__LINE_NUMBER:
                return getLineNumber();
            case ExceptionPackage.DETAILED_ERROR_LINE__SEVERITY:
                return getSeverity();
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
            case ExceptionPackage.DETAILED_ERROR_LINE__COLUMN_NUMBER:
                setColumnNumber((Integer)newValue);
                return;
            case ExceptionPackage.DETAILED_ERROR_LINE__LINE_NUMBER:
                setLineNumber((Integer)newValue);
                return;
            case ExceptionPackage.DETAILED_ERROR_LINE__SEVERITY:
                setSeverity((SeverityType)newValue);
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
            case ExceptionPackage.DETAILED_ERROR_LINE__COLUMN_NUMBER:
                unsetColumnNumber();
                return;
            case ExceptionPackage.DETAILED_ERROR_LINE__LINE_NUMBER:
                unsetLineNumber();
                return;
            case ExceptionPackage.DETAILED_ERROR_LINE__SEVERITY:
                unsetSeverity();
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
            case ExceptionPackage.DETAILED_ERROR_LINE__COLUMN_NUMBER:
                return isSetColumnNumber();
            case ExceptionPackage.DETAILED_ERROR_LINE__LINE_NUMBER:
                return isSetLineNumber();
            case ExceptionPackage.DETAILED_ERROR_LINE__SEVERITY:
                return isSetSeverity();
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
        result.append(" (columnNumber: ");
        if (columnNumberESet) result.append(columnNumber); else result.append("<unset>");
        result.append(", lineNumber: ");
        if (lineNumberESet) result.append(lineNumber); else result.append("<unset>");
        result.append(", severity: ");
        if (severityESet) result.append(severity); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //DetailedErrorLineImpl
