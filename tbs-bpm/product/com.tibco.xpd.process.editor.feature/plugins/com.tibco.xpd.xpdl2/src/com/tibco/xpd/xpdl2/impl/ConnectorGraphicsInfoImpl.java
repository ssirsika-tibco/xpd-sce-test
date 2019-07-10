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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Page;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connector Graphics Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl#getCoordinates <em>Coordinates</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl#getFillColor <em>Fill Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl#isIsVisible <em>Is Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl#getPageId <em>Page Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl#getStyle <em>Style</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl#getToolId <em>Tool Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectorGraphicsInfoImpl extends EObjectImpl implements ConnectorGraphicsInfo {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCoordinates() <em>Coordinates</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCoordinates()
     * @generated
     * @ordered
     */
    protected EList<Coordinates> coordinates;

    /**
     * The default value of the '{@link #getBorderColor() <em>Border Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected static final String BORDER_COLOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected String borderColor = BORDER_COLOR_EDEFAULT;

    /**
     * The default value of the '{@link #getFillColor() <em>Fill Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFillColor()
     * @generated
     * @ordered
     */
    protected static final String FILL_COLOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFillColor() <em>Fill Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFillColor()
     * @generated
     * @ordered
     */
    protected String fillColor = FILL_COLOR_EDEFAULT;

    /**
     * The default value of the '{@link #isIsVisible() <em>Is Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsVisible()
     * @generated
     * @ordered
     */
    protected static final boolean IS_VISIBLE_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isIsVisible() <em>Is Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsVisible()
     * @generated
     * @ordered
     */
    protected boolean isVisible = IS_VISIBLE_EDEFAULT;

    /**
     * This is true if the Is Visible attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean isVisibleESet;

    /**
     * The default value of the '{@link #getPageId() <em>Page Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPageId()
     * @generated
     * @ordered
     */
    protected static final String PAGE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPageId() <em>Page Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPageId()
     * @generated
     * @ordered
     */
    protected String pageId = PAGE_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getStyle() <em>Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected static final String STYLE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected String style = STYLE_EDEFAULT;

    /**
     * The default value of the '{@link #getToolId() <em>Tool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToolId()
     * @generated
     * @ordered
     */
    protected static final String TOOL_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getToolId() <em>Tool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToolId()
     * @generated
     * @ordered
     */
    protected String toolId = TOOL_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConnectorGraphicsInfoImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.CONNECTOR_GRAPHICS_INFO;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Coordinates> getCoordinates() {
        if (coordinates == null) {
            coordinates = new EObjectContainmentEList<Coordinates>(Coordinates.class, this,
                    Xpdl2Package.CONNECTOR_GRAPHICS_INFO__COORDINATES);
        }
        return coordinates;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBorderColor(String newBorderColor) {
        String oldBorderColor = borderColor;
        borderColor = newBorderColor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.CONNECTOR_GRAPHICS_INFO__BORDER_COLOR,
                    oldBorderColor, borderColor));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFillColor() {
        return fillColor;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFillColor(String newFillColor) {
        String oldFillColor = fillColor;
        fillColor = newFillColor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.CONNECTOR_GRAPHICS_INFO__FILL_COLOR,
                    oldFillColor, fillColor));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsVisible() {
        return isVisible;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIsVisible(boolean newIsVisible) {
        boolean oldIsVisible = isVisible;
        isVisible = newIsVisible;
        boolean oldIsVisibleESet = isVisibleESet;
        isVisibleESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.CONNECTOR_GRAPHICS_INFO__IS_VISIBLE,
                    oldIsVisible, isVisible, !oldIsVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetIsVisible() {
        boolean oldIsVisible = isVisible;
        boolean oldIsVisibleESet = isVisibleESet;
        isVisible = IS_VISIBLE_EDEFAULT;
        isVisibleESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.CONNECTOR_GRAPHICS_INFO__IS_VISIBLE,
                    oldIsVisible, IS_VISIBLE_EDEFAULT, oldIsVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetIsVisible() {
        return isVisibleESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPageId(String newPageId) {
        String oldPageId = pageId;
        pageId = newPageId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.CONNECTOR_GRAPHICS_INFO__PAGE_ID,
                    oldPageId, pageId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getStyle() {
        return style;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStyle(String newStyle) {
        String oldStyle = style;
        style = newStyle;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.CONNECTOR_GRAPHICS_INFO__STYLE, oldStyle,
                    style));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getToolId() {
        return toolId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setToolId(String newToolId) {
        String oldToolId = toolId;
        toolId = newToolId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.CONNECTOR_GRAPHICS_INFO__TOOL_ID,
                    oldToolId, toolId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__COORDINATES:
            return ((InternalEList<?>) getCoordinates()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__COORDINATES:
            return getCoordinates();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__BORDER_COLOR:
            return getBorderColor();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__FILL_COLOR:
            return getFillColor();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__IS_VISIBLE:
            return isIsVisible();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__PAGE_ID:
            return getPageId();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__STYLE:
            return getStyle();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__TOOL_ID:
            return getToolId();
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
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__COORDINATES:
            getCoordinates().clear();
            getCoordinates().addAll((Collection<? extends Coordinates>) newValue);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__BORDER_COLOR:
            setBorderColor((String) newValue);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__FILL_COLOR:
            setFillColor((String) newValue);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__IS_VISIBLE:
            setIsVisible((Boolean) newValue);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__PAGE_ID:
            setPageId((String) newValue);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__STYLE:
            setStyle((String) newValue);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__TOOL_ID:
            setToolId((String) newValue);
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
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__COORDINATES:
            getCoordinates().clear();
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__BORDER_COLOR:
            setBorderColor(BORDER_COLOR_EDEFAULT);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__FILL_COLOR:
            setFillColor(FILL_COLOR_EDEFAULT);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__IS_VISIBLE:
            unsetIsVisible();
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__PAGE_ID:
            setPageId(PAGE_ID_EDEFAULT);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__STYLE:
            setStyle(STYLE_EDEFAULT);
            return;
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__TOOL_ID:
            setToolId(TOOL_ID_EDEFAULT);
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
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__COORDINATES:
            return coordinates != null && !coordinates.isEmpty();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__BORDER_COLOR:
            return BORDER_COLOR_EDEFAULT == null ? borderColor != null : !BORDER_COLOR_EDEFAULT.equals(borderColor);
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__FILL_COLOR:
            return FILL_COLOR_EDEFAULT == null ? fillColor != null : !FILL_COLOR_EDEFAULT.equals(fillColor);
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__IS_VISIBLE:
            return isSetIsVisible();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__PAGE_ID:
            return PAGE_ID_EDEFAULT == null ? pageId != null : !PAGE_ID_EDEFAULT.equals(pageId);
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__STYLE:
            return STYLE_EDEFAULT == null ? style != null : !STYLE_EDEFAULT.equals(style);
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO__TOOL_ID:
            return TOOL_ID_EDEFAULT == null ? toolId != null : !TOOL_ID_EDEFAULT.equals(toolId);
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
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (borderColor: "); //$NON-NLS-1$
        result.append(borderColor);
        result.append(", fillColor: "); //$NON-NLS-1$
        result.append(fillColor);
        result.append(", isVisible: "); //$NON-NLS-1$
        if (isVisibleESet)
            result.append(isVisible);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", pageId: "); //$NON-NLS-1$
        result.append(pageId);
        result.append(", style: "); //$NON-NLS-1$
        result.append(style);
        result.append(", toolId: "); //$NON-NLS-1$
        result.append(toolId);
        result.append(')');
        return result.toString();
    }

} //ConnectorGraphicsInfoImpl
