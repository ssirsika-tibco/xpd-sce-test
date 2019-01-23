/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Distribution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepDistribution#getLastResetTime <em>Last Reset Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepDistribution#getObservedElements <em>Observed Elements</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepDistribution#getDistributionCategory <em>Distribution Category</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepDistribution#getParameter1 <em>Parameter1</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepDistribution#getParameter2 <em>Parameter2</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepDistribution#getSeed <em>Seed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistribution()
 * @model extendedMetaData="name='DistributionType' kind='elementOnly'"
 * @generated
 */
public interface SimRepDistribution extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Last Reset Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Last Reset Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Last Reset Time</em>' attribute.
     * @see #isSetLastResetTime()
     * @see #unsetLastResetTime()
     * @see #setLastResetTime(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistribution_LastResetTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='LastResetTime' namespace='##targetNamespace'"
     * @generated
     */
    double getLastResetTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getLastResetTime <em>Last Reset Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Last Reset Time</em>' attribute.
     * @see #isSetLastResetTime()
     * @see #unsetLastResetTime()
     * @see #getLastResetTime()
     * @generated
     */
    void setLastResetTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getLastResetTime <em>Last Reset Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLastResetTime()
     * @see #getLastResetTime()
     * @see #setLastResetTime(double)
     * @generated
     */
    void unsetLastResetTime();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getLastResetTime <em>Last Reset Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Last Reset Time</em>' attribute is set.
     * @see #unsetLastResetTime()
     * @see #getLastResetTime()
     * @see #setLastResetTime(double)
     * @generated
     */
    boolean isSetLastResetTime();

    /**
     * Returns the value of the '<em><b>Observed Elements</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Observed Elements</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Observed Elements</em>' attribute.
     * @see #isSetObservedElements()
     * @see #unsetObservedElements()
     * @see #setObservedElements(long)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistribution_ObservedElements()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='ObservedElements' namespace='##targetNamespace'"
     * @generated
     */
    long getObservedElements();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getObservedElements <em>Observed Elements</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Observed Elements</em>' attribute.
     * @see #isSetObservedElements()
     * @see #unsetObservedElements()
     * @see #getObservedElements()
     * @generated
     */
    void setObservedElements(long value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getObservedElements <em>Observed Elements</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetObservedElements()
     * @see #getObservedElements()
     * @see #setObservedElements(long)
     * @generated
     */
    void unsetObservedElements();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getObservedElements <em>Observed Elements</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Observed Elements</em>' attribute is set.
     * @see #unsetObservedElements()
     * @see #getObservedElements()
     * @see #setObservedElements(long)
     * @generated
     */
    boolean isSetObservedElements();

    /**
     * Returns the value of the '<em><b>Distribution Category</b></em>' attribute.
     * The default value is <code>"REAL_CONSTANT"</code>.
     * The literals are from the enumeration {@link com.tibco.simulation.report.SimRepDistributionCategory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Distribution Category</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Distribution Category</em>' attribute.
     * @see com.tibco.simulation.report.SimRepDistributionCategory
     * @see #isSetDistributionCategory()
     * @see #unsetDistributionCategory()
     * @see #setDistributionCategory(SimRepDistributionCategory)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistribution_DistributionCategory()
     * @model default="REAL_CONSTANT" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='DistributionCategory' namespace='##targetNamespace'"
     * @generated
     */
    SimRepDistributionCategory getDistributionCategory();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getDistributionCategory <em>Distribution Category</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Distribution Category</em>' attribute.
     * @see com.tibco.simulation.report.SimRepDistributionCategory
     * @see #isSetDistributionCategory()
     * @see #unsetDistributionCategory()
     * @see #getDistributionCategory()
     * @generated
     */
    void setDistributionCategory(SimRepDistributionCategory value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getDistributionCategory <em>Distribution Category</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDistributionCategory()
     * @see #getDistributionCategory()
     * @see #setDistributionCategory(SimRepDistributionCategory)
     * @generated
     */
    void unsetDistributionCategory();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getDistributionCategory <em>Distribution Category</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Distribution Category</em>' attribute is set.
     * @see #unsetDistributionCategory()
     * @see #getDistributionCategory()
     * @see #setDistributionCategory(SimRepDistributionCategory)
     * @generated
     */
    boolean isSetDistributionCategory();

    /**
     * Returns the value of the '<em><b>Parameter1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter1</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter1</em>' attribute.
     * @see #isSetParameter1()
     * @see #unsetParameter1()
     * @see #setParameter1(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistribution_Parameter1()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='Parameter1' namespace='##targetNamespace'"
     * @generated
     */
    double getParameter1();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getParameter1 <em>Parameter1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter1</em>' attribute.
     * @see #isSetParameter1()
     * @see #unsetParameter1()
     * @see #getParameter1()
     * @generated
     */
    void setParameter1(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getParameter1 <em>Parameter1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetParameter1()
     * @see #getParameter1()
     * @see #setParameter1(double)
     * @generated
     */
    void unsetParameter1();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getParameter1 <em>Parameter1</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Parameter1</em>' attribute is set.
     * @see #unsetParameter1()
     * @see #getParameter1()
     * @see #setParameter1(double)
     * @generated
     */
    boolean isSetParameter1();

    /**
     * Returns the value of the '<em><b>Parameter2</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter2</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter2</em>' attribute.
     * @see #isSetParameter2()
     * @see #unsetParameter2()
     * @see #setParameter2(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistribution_Parameter2()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='Parameter2' namespace='##targetNamespace'"
     * @generated
     */
    double getParameter2();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getParameter2 <em>Parameter2</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter2</em>' attribute.
     * @see #isSetParameter2()
     * @see #unsetParameter2()
     * @see #getParameter2()
     * @generated
     */
    void setParameter2(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getParameter2 <em>Parameter2</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetParameter2()
     * @see #getParameter2()
     * @see #setParameter2(double)
     * @generated
     */
    void unsetParameter2();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getParameter2 <em>Parameter2</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Parameter2</em>' attribute is set.
     * @see #unsetParameter2()
     * @see #getParameter2()
     * @see #setParameter2(double)
     * @generated
     */
    boolean isSetParameter2();

    /**
     * Returns the value of the '<em><b>Seed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Seed</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Seed</em>' attribute.
     * @see #isSetSeed()
     * @see #unsetSeed()
     * @see #setSeed(long)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistribution_Seed()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='Seed' namespace='##targetNamespace'"
     * @generated
     */
    long getSeed();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getSeed <em>Seed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Seed</em>' attribute.
     * @see #isSetSeed()
     * @see #unsetSeed()
     * @see #getSeed()
     * @generated
     */
    void setSeed(long value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getSeed <em>Seed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSeed()
     * @see #getSeed()
     * @see #setSeed(long)
     * @generated
     */
    void unsetSeed();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepDistribution#getSeed <em>Seed</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Seed</em>' attribute is set.
     * @see #unsetSeed()
     * @see #getSeed()
     * @see #setSeed(long)
     * @generated
     */
    boolean isSetSeed();

} // SimRepDistribution
