/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capability Holding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         This decribes the holding of a Capability (assigned to a Group or Position) and
 *         any qualifying value that applies to that holding.
 *         The term "qualified" simply means that the association may carry a value that
 *         distinguishes two holdings of the same Capability. For example, the Capability
 *         to speak a language may be qualified with the name of that language.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.CapabilityHolding#getCapability <em>Capability</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getCapabilityHolding()
 * @model extendedMetaData="name='CapabilityHolding' kind='elementOnly'"
 * @generated
 */
public interface CapabilityHolding extends QualifiedHolding {
    /**
     * Returns the value of the '<em><b>Capability</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           Identifies, by its ID, the Capability that this holding refers to.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Capability</em>' reference.
     * @see #setCapability(Capability)
     * @see com.tibco.n2.directory.model.de.DePackage#getCapabilityHolding_Capability()
     * @model resolveProxies="false" required="true"
     *        extendedMetaData="kind='attribute' name='capability'"
     * @generated
     */
    Capability getCapability();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.CapabilityHolding#getCapability <em>Capability</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Capability</em>' reference.
     * @see #getCapability()
     * @generated
     */
    void setCapability(Capability value);

} // CapabilityHolding
