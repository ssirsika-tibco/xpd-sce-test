/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Detailed Error Line</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Extension of ErrorLine, which adds a line number, column number and severity level.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getColumnNumber <em>Column Number</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getSeverity <em>Severity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDetailedErrorLine()
 * @model extendedMetaData="name='DetailedErrorLine' kind='elementOnly'"
 * @generated
 */
public interface DetailedErrorLine extends ErrorLine {
    /**
     * Returns the value of the '<em><b>Column Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Column number in which the error occurred.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Column Number</em>' attribute.
     * @see #isSetColumnNumber()
     * @see #unsetColumnNumber()
     * @see #setColumnNumber(int)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDetailedErrorLine_ColumnNumber()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='columnNumber'"
     * @generated
     */
    int getColumnNumber();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getColumnNumber <em>Column Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Column Number</em>' attribute.
     * @see #isSetColumnNumber()
     * @see #unsetColumnNumber()
     * @see #getColumnNumber()
     * @generated
     */
    void setColumnNumber(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getColumnNumber <em>Column Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetColumnNumber()
     * @see #getColumnNumber()
     * @see #setColumnNumber(int)
     * @generated
     */
    void unsetColumnNumber();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getColumnNumber <em>Column Number</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Column Number</em>' attribute is set.
     * @see #unsetColumnNumber()
     * @see #getColumnNumber()
     * @see #setColumnNumber(int)
     * @generated
     */
    boolean isSetColumnNumber();

    /**
     * Returns the value of the '<em><b>Line Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Line number on which the error occurred.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Line Number</em>' attribute.
     * @see #isSetLineNumber()
     * @see #unsetLineNumber()
     * @see #setLineNumber(int)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDetailedErrorLine_LineNumber()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='lineNumber'"
     * @generated
     */
    int getLineNumber();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getLineNumber <em>Line Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Line Number</em>' attribute.
     * @see #isSetLineNumber()
     * @see #unsetLineNumber()
     * @see #getLineNumber()
     * @generated
     */
    void setLineNumber(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getLineNumber <em>Line Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLineNumber()
     * @see #getLineNumber()
     * @see #setLineNumber(int)
     * @generated
     */
    void unsetLineNumber();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getLineNumber <em>Line Number</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Line Number</em>' attribute is set.
     * @see #unsetLineNumber()
     * @see #getLineNumber()
     * @see #setLineNumber(int)
     * @generated
     */
    boolean isSetLineNumber();

    /**
     * Returns the value of the '<em><b>Severity</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.api.exception.SeverityType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Severity of the message - for example, ERROR, WARNING or INFORMATION.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Severity</em>' attribute.
     * @see com.tibco.n2.common.api.exception.SeverityType
     * @see #isSetSeverity()
     * @see #unsetSeverity()
     * @see #setSeverity(SeverityType)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDetailedErrorLine_Severity()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='severity'"
     * @generated
     */
    SeverityType getSeverity();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getSeverity <em>Severity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Severity</em>' attribute.
     * @see com.tibco.n2.common.api.exception.SeverityType
     * @see #isSetSeverity()
     * @see #unsetSeverity()
     * @see #getSeverity()
     * @generated
     */
    void setSeverity(SeverityType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getSeverity <em>Severity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSeverity()
     * @see #getSeverity()
     * @see #setSeverity(SeverityType)
     * @generated
     */
    void unsetSeverity();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getSeverity <em>Severity</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Severity</em>' attribute is set.
     * @see #unsetSeverity()
     * @see #getSeverity()
     * @see #setSeverity(SeverityType)
     * @generated
     */
    boolean isSetSeverity();

} // DetailedErrorLine
