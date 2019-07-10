/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Graphics Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getCoordinates <em>Coordinates</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getFillColor <em>Fill Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#isIsVisible <em>Is Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getLaneId <em>Lane Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getPage <em>Page</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getShape <em>Shape</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getToolId <em>Tool Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl#getPageId <em>Page Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeGraphicsInfoImpl extends EObjectImpl implements NodeGraphicsInfo {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCoordinates() <em>Coordinates</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCoordinates()
     * @generated
     * @ordered
     */
    protected Coordinates coordinates;

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
     * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHeight()
     * @generated
     * @ordered
     */
    protected static final double HEIGHT_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHeight()
     * @generated
     * @ordered
     */
    protected double height = HEIGHT_EDEFAULT;

    /**
     * This is true if the Height attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean heightESet;

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
     * The default value of the '{@link #getLaneId() <em>Lane Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLaneId()
     * @generated
     * @ordered
     */
    protected static final String LANE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLaneId() <em>Lane Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLaneId()
     * @generated
     * @ordered
     */
    protected String laneId = LANE_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getPage() <em>Page</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPage()
     * @generated
     * @ordered
     */
    protected static final String PAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPage() <em>Page</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPage()
     * @generated
     * @ordered
     */
    protected String page = PAGE_EDEFAULT;

    /**
     * The default value of the '{@link #getShape() <em>Shape</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getShape()
     * @generated
     * @ordered
     */
    protected static final String SHAPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getShape() <em>Shape</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getShape()
     * @generated
     * @ordered
     */
    protected String shape = SHAPE_EDEFAULT;

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
     * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWidth()
     * @generated
     * @ordered
     */
    protected static final double WIDTH_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWidth()
     * @generated
     * @ordered
     */
    protected double width = WIDTH_EDEFAULT;

    /**
     * This is true if the Width attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean widthESet;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected NodeGraphicsInfoImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.NODE_GRAPHICS_INFO;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCoordinates(Coordinates newCoordinates, NotificationChain msgs) {
        Coordinates oldCoordinates = coordinates;
        coordinates = newCoordinates;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES, oldCoordinates, newCoordinates);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCoordinates(Coordinates newCoordinates) {
        if (newCoordinates != coordinates) {
            NotificationChain msgs = null;
            if (coordinates != null)
                msgs = ((InternalEObject) coordinates).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES,
                        null,
                        msgs);
            if (newCoordinates != null)
                msgs = ((InternalEObject) newCoordinates).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES,
                        null,
                        msgs);
            msgs = basicSetCoordinates(newCoordinates, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES,
                    newCoordinates, newCoordinates));
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__BORDER_COLOR,
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__FILL_COLOR,
                    oldFillColor, fillColor));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getHeight() {
        return height;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHeight(double newHeight) {
        double oldHeight = height;
        height = newHeight;
        boolean oldHeightESet = heightESet;
        heightESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__HEIGHT, oldHeight,
                    height, !oldHeightESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetHeight() {
        double oldHeight = height;
        boolean oldHeightESet = heightESet;
        height = HEIGHT_EDEFAULT;
        heightESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.NODE_GRAPHICS_INFO__HEIGHT, oldHeight,
                    HEIGHT_EDEFAULT, oldHeightESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetHeight() {
        return heightESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__IS_VISIBLE,
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.NODE_GRAPHICS_INFO__IS_VISIBLE,
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
    public String getLaneId() {
        return laneId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLaneId(String newLaneId) {
        String oldLaneId = laneId;
        laneId = newLaneId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__LANE_ID, oldLaneId,
                    laneId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPage() {
        return page;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPage(String newPage) {
        String oldPage = page;
        page = newPage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__PAGE, oldPage,
                    page));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getShape() {
        return shape;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setShape(String newShape) {
        String oldShape = shape;
        shape = newShape;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__SHAPE, oldShape,
                    shape));
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__TOOL_ID, oldToolId,
                    toolId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getWidth() {
        return width;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWidth(double newWidth) {
        double oldWidth = width;
        width = newWidth;
        boolean oldWidthESet = widthESet;
        widthESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__WIDTH, oldWidth,
                    width, !oldWidthESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWidth() {
        double oldWidth = width;
        boolean oldWidthESet = widthESet;
        width = WIDTH_EDEFAULT;
        widthESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.NODE_GRAPHICS_INFO__WIDTH, oldWidth,
                    WIDTH_EDEFAULT, oldWidthESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWidth() {
        return widthESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.NODE_GRAPHICS_INFO__PAGE_ID, oldPageId,
                    pageId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES:
            return basicSetCoordinates(null, msgs);
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
        case Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES:
            return getCoordinates();
        case Xpdl2Package.NODE_GRAPHICS_INFO__BORDER_COLOR:
            return getBorderColor();
        case Xpdl2Package.NODE_GRAPHICS_INFO__FILL_COLOR:
            return getFillColor();
        case Xpdl2Package.NODE_GRAPHICS_INFO__HEIGHT:
            return getHeight();
        case Xpdl2Package.NODE_GRAPHICS_INFO__IS_VISIBLE:
            return isIsVisible();
        case Xpdl2Package.NODE_GRAPHICS_INFO__LANE_ID:
            return getLaneId();
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE:
            return getPage();
        case Xpdl2Package.NODE_GRAPHICS_INFO__SHAPE:
            return getShape();
        case Xpdl2Package.NODE_GRAPHICS_INFO__TOOL_ID:
            return getToolId();
        case Xpdl2Package.NODE_GRAPHICS_INFO__WIDTH:
            return getWidth();
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE_ID:
            return getPageId();
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
        case Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES:
            setCoordinates((Coordinates) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__BORDER_COLOR:
            setBorderColor((String) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__FILL_COLOR:
            setFillColor((String) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__HEIGHT:
            setHeight((Double) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__IS_VISIBLE:
            setIsVisible((Boolean) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__LANE_ID:
            setLaneId((String) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE:
            setPage((String) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__SHAPE:
            setShape((String) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__TOOL_ID:
            setToolId((String) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__WIDTH:
            setWidth((Double) newValue);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE_ID:
            setPageId((String) newValue);
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
        case Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES:
            setCoordinates((Coordinates) null);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__BORDER_COLOR:
            setBorderColor(BORDER_COLOR_EDEFAULT);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__FILL_COLOR:
            setFillColor(FILL_COLOR_EDEFAULT);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__HEIGHT:
            unsetHeight();
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__IS_VISIBLE:
            unsetIsVisible();
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__LANE_ID:
            setLaneId(LANE_ID_EDEFAULT);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE:
            setPage(PAGE_EDEFAULT);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__SHAPE:
            setShape(SHAPE_EDEFAULT);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__TOOL_ID:
            setToolId(TOOL_ID_EDEFAULT);
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__WIDTH:
            unsetWidth();
            return;
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE_ID:
            setPageId(PAGE_ID_EDEFAULT);
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
        case Xpdl2Package.NODE_GRAPHICS_INFO__COORDINATES:
            return coordinates != null;
        case Xpdl2Package.NODE_GRAPHICS_INFO__BORDER_COLOR:
            return BORDER_COLOR_EDEFAULT == null ? borderColor != null : !BORDER_COLOR_EDEFAULT.equals(borderColor);
        case Xpdl2Package.NODE_GRAPHICS_INFO__FILL_COLOR:
            return FILL_COLOR_EDEFAULT == null ? fillColor != null : !FILL_COLOR_EDEFAULT.equals(fillColor);
        case Xpdl2Package.NODE_GRAPHICS_INFO__HEIGHT:
            return isSetHeight();
        case Xpdl2Package.NODE_GRAPHICS_INFO__IS_VISIBLE:
            return isSetIsVisible();
        case Xpdl2Package.NODE_GRAPHICS_INFO__LANE_ID:
            return LANE_ID_EDEFAULT == null ? laneId != null : !LANE_ID_EDEFAULT.equals(laneId);
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE:
            return PAGE_EDEFAULT == null ? page != null : !PAGE_EDEFAULT.equals(page);
        case Xpdl2Package.NODE_GRAPHICS_INFO__SHAPE:
            return SHAPE_EDEFAULT == null ? shape != null : !SHAPE_EDEFAULT.equals(shape);
        case Xpdl2Package.NODE_GRAPHICS_INFO__TOOL_ID:
            return TOOL_ID_EDEFAULT == null ? toolId != null : !TOOL_ID_EDEFAULT.equals(toolId);
        case Xpdl2Package.NODE_GRAPHICS_INFO__WIDTH:
            return isSetWidth();
        case Xpdl2Package.NODE_GRAPHICS_INFO__PAGE_ID:
            return PAGE_ID_EDEFAULT == null ? pageId != null : !PAGE_ID_EDEFAULT.equals(pageId);
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
        result.append(", height: "); //$NON-NLS-1$
        if (heightESet)
            result.append(height);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", isVisible: "); //$NON-NLS-1$
        if (isVisibleESet)
            result.append(isVisible);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", laneId: "); //$NON-NLS-1$
        result.append(laneId);
        result.append(", page: "); //$NON-NLS-1$
        result.append(page);
        result.append(", shape: "); //$NON-NLS-1$
        result.append(shape);
        result.append(", toolId: "); //$NON-NLS-1$
        result.append(toolId);
        result.append(", width: "); //$NON-NLS-1$
        if (widthESet)
            result.append(width);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", pageId: "); //$NON-NLS-1$
        result.append(pageId);
        result.append(')');
        return result.toString();
    }

} //NodeGraphicsInfoImpl
