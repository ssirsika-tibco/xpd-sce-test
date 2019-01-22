/**
 */
package com.tibco.xpd.rsd;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.rsd.RsdPackage
 * @generated
 */
public interface RsdFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    RsdFactory eINSTANCE = com.tibco.xpd.rsd.impl.RsdFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Service</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Service</em>'.
     * @generated
     */
    Service createService();

    /**
     * Returns a new object of class '<em>Resource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Resource</em>'.
     * @generated
     */
    Resource createResource();

    /**
     * Returns a new object of class '<em>Method</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Method</em>'.
     * @generated
     */
    Method createMethod();

    /**
     * Returns a new object of class '<em>Request</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Request</em>'.
     * @generated
     */
    Request createRequest();

    /**
     * Returns a new object of class '<em>Parameter</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Parameter</em>'.
     * @generated
     */
    Parameter createParameter();

    /**
     * Returns a new object of class '<em>Payload Reference</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Payload Reference</em>'.
     * @generated
     */
    PayloadReference createPayloadReference();

    /**
     * Returns a new object of class '<em>Fault</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Fault</em>'.
     * @generated
     */
    Fault createFault();

    /**
     * Returns a new object of class '<em>Response</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Response</em>'.
     * @generated
     */
    Response createResponse();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    RsdPackage getRsdPackage();

} //RsdFactory
