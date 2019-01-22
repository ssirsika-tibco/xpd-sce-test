/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Features are a means of referencing a meta-model type from within another meta-model
 *         type. Thus, org unit, position and location types can be defined once and re-used
 *         by reference within several other types.
 *         The number of instances of a Feature can also be constrained by upper and lower
 *         boundaries. An upper-bound attribute value of "-1" signifies that no upper constraint
 *         is placed.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Feature#getDefinition <em>Definition</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Feature#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Feature#getUpperBound <em>Upper Bound</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getFeature()
 * @model extendedMetaData="name='Feature' kind='empty'"
 * @generated
 */
public interface Feature extends NamedEntity {
    /**
     * Returns the value of the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               The identity reference to the EntityType from which this Feature is derived.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Definition</em>' reference.
     * @see #setDefinition(EntityType)
     * @see com.tibco.n2.directory.model.de.DePackage#getFeature_Definition()
     * @model resolveProxies="false" required="true"
     *        extendedMetaData="kind='attribute' name='definition'"
     * @generated
     */
    EntityType getDefinition();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Feature#getDefinition <em>Definition</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Definition</em>' reference.
     * @see #getDefinition()
     * @generated
     */
    void setDefinition(EntityType value);

    /**
     * Returns the value of the '<em><b>Lower Bound</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               The lower boundary for the number of instances of this Feature.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Lower Bound</em>' attribute.
     * @see #isSetLowerBound()
     * @see #unsetLowerBound()
     * @see #setLowerBound(int)
     * @see com.tibco.n2.directory.model.de.DePackage#getFeature_LowerBound()
     * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='lower-bound'"
     * @generated
     */
    int getLowerBound();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Feature#getLowerBound <em>Lower Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lower Bound</em>' attribute.
     * @see #isSetLowerBound()
     * @see #unsetLowerBound()
     * @see #getLowerBound()
     * @generated
     */
    void setLowerBound(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Feature#getLowerBound <em>Lower Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLowerBound()
     * @see #getLowerBound()
     * @see #setLowerBound(int)
     * @generated
     */
    void unsetLowerBound();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Feature#getLowerBound <em>Lower Bound</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Lower Bound</em>' attribute is set.
     * @see #unsetLowerBound()
     * @see #getLowerBound()
     * @see #setLowerBound(int)
     * @generated
     */
    boolean isSetLowerBound();

    /**
     * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
     * The default value is <code>"1"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               The upper boundary for the number of instances of this Feature. A value of -1
     *               means 'no limit'.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Upper Bound</em>' attribute.
     * @see #isSetUpperBound()
     * @see #unsetUpperBound()
     * @see #setUpperBound(int)
     * @see com.tibco.n2.directory.model.de.DePackage#getFeature_UpperBound()
     * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='upper-bound'"
     * @generated
     */
    int getUpperBound();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Feature#getUpperBound <em>Upper Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Upper Bound</em>' attribute.
     * @see #isSetUpperBound()
     * @see #unsetUpperBound()
     * @see #getUpperBound()
     * @generated
     */
    void setUpperBound(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Feature#getUpperBound <em>Upper Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetUpperBound()
     * @see #getUpperBound()
     * @see #setUpperBound(int)
     * @generated
     */
    void unsetUpperBound();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Feature#getUpperBound <em>Upper Bound</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Upper Bound</em>' attribute is set.
     * @see #unsetUpperBound()
     * @see #getUpperBound()
     * @see #setUpperBound(int)
     * @generated
     */
    boolean isSetUpperBound();

} // Feature
