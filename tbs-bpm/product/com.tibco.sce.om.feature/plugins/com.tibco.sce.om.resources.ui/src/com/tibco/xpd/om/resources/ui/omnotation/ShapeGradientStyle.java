/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.resources.ui.omnotation;

import org.eclipse.gmf.runtime.notation.Style;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shape Gradient Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradStartColor <em>Grad Start Color</em>}</li>
 *   <li>{@link com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradEndColor <em>Grad End Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage#getShapeGradientStyle()
 * @model
 * @generated
 */
public interface ShapeGradientStyle extends Style {
    /**
     * Returns the value of the '<em><b>Grad Start Color</b></em>' attribute.
     * The default value is <code>"16777215"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Grad Start Color</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Grad Start Color</em>' attribute.
     * @see #setGradStartColor(int)
     * @see com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage#getShapeGradientStyle_GradStartColor()
     * @model default="16777215"
     * @generated
     */
    int getGradStartColor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradStartColor <em>Grad Start Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Grad Start Color</em>' attribute.
     * @see #getGradStartColor()
     * @generated
     */
    void setGradStartColor(int value);

    /**
     * Returns the value of the '<em><b>Grad End Color</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Grad End Color</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Grad End Color</em>' attribute.
     * @see #setGradEndColor(int)
     * @see com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage#getShapeGradientStyle_GradEndColor()
     * @model default="0"
     * @generated
     */
    int getGradEndColor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradEndColor <em>Grad End Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Grad End Color</em>' attribute.
     * @see #getGradEndColor()
     * @generated
     */
    void setGradEndColor(int value);

} // ShapeGradientStyle
