/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessInterface#getStartMethods <em>Start Methods</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessInterface#getIntermediateMethods <em>Intermediate Methods</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessInterface#getXpdInterfaceType <em>Xpd Interface Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessInterface#getServiceProcessConfiguration <em>Service Process Configuration</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessInterface()
 * @model extendedMetaData="name='ProcessInterface' kind='elementOnly' features-order='startMethods intermediateMethods extendedAttributes'"
 * @generated
 */
public interface ProcessInterface extends NamedElement, DescribedElement,
        ExtendedAttributesContainer, FormalParametersContainer,
        OtherAttributesContainer, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Start Methods</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.StartMethod}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Lists the start methods associated with the process interface.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Methods</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessInterface_StartMethods()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='StartMethod' namespace='##targetNamespace' wrap='StartMethods'"
     * @generated
     */
    EList<StartMethod> getStartMethods();

    /**
     * Returns the value of the '<em><b>Intermediate Methods</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.IntermediateMethod}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Lists the intermediate events methods that are associated with the process interface.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Intermediate Methods</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessInterface_IntermediateMethods()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='IntermediateMethod' namespace='##targetNamespace' wrap='IntermediateMethods'"
     * @generated
     */
    EList<IntermediateMethod> getIntermediateMethods();

    /**
     * Returns the value of the '<em><b>Xpd Interface Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.XpdInterfaceType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Interface type - ProcessInterface or ServiceProcessInterface
     * <!-- end-model-doc -->
     * @return the value of the '<em>Xpd Interface Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.XpdInterfaceType
     * @see #isSetXpdInterfaceType()
     * @see #unsetXpdInterfaceType()
     * @see #setXpdInterfaceType(XpdInterfaceType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessInterface_XpdInterfaceType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='XpdInterfaceType'"
     * @generated
     */
    XpdInterfaceType getXpdInterfaceType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getXpdInterfaceType <em>Xpd Interface Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Xpd Interface Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.XpdInterfaceType
     * @see #isSetXpdInterfaceType()
     * @see #unsetXpdInterfaceType()
     * @see #getXpdInterfaceType()
     * @generated
     */
    void setXpdInterfaceType(XpdInterfaceType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getXpdInterfaceType <em>Xpd Interface Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetXpdInterfaceType()
     * @see #getXpdInterfaceType()
     * @see #setXpdInterfaceType(XpdInterfaceType)
     * @generated
     */
    void unsetXpdInterfaceType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getXpdInterfaceType <em>Xpd Interface Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Xpd Interface Type</em>' attribute is set.
     * @see #unsetXpdInterfaceType()
     * @see #getXpdInterfaceType()
     * @see #setXpdInterfaceType(XpdInterfaceType)
     * @generated
     */
    boolean isSetXpdInterfaceType();

    /**
     * Returns the value of the '<em><b>Service Process Configuration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specifies the deployment targets if the interface type is ServiceProcessInterface
     * <!-- end-model-doc -->
     * @return the value of the '<em>Service Process Configuration</em>' containment reference.
     * @see #setServiceProcessConfiguration(ServiceProcessConfiguration)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessInterface_ServiceProcessConfiguration()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ServiceProcessConfiguration' namespace='##targetNamespace'"
     * @generated
     */
    ServiceProcessConfiguration getServiceProcessConfiguration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getServiceProcessConfiguration <em>Service Process Configuration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Service Process Configuration</em>' containment reference.
     * @see #getServiceProcessConfiguration()
     * @generated
     */
    void setServiceProcessConfiguration(ServiceProcessConfiguration value);

} // ProcessInterface