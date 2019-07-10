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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.AssociationDirectionType;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssociationImpl#getConnectorGraphicsInfos <em>Connector Graphics Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssociationImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssociationImpl#getAssociationDirection <em>Association Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssociationImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssociationImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssociationImpl#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssociationImpl extends NamedElementImpl implements Association {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getConnectorGraphicsInfos() <em>Connector Graphics Infos</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConnectorGraphicsInfos()
     * @generated
     * @ordered
     */
    protected EList<ConnectorGraphicsInfo> connectorGraphicsInfos;

    /**
     * The cached value of the '{@link #getObject() <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObject()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Object object;

    /**
     * The default value of the '{@link #getAssociationDirection() <em>Association Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAssociationDirection()
     * @generated
     * @ordered
     */
    protected static final AssociationDirectionType ASSOCIATION_DIRECTION_EDEFAULT =
            AssociationDirectionType.NONE_LITERAL;

    /**
     * The cached value of the '{@link #getAssociationDirection() <em>Association Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAssociationDirection()
     * @generated
     * @ordered
     */
    protected AssociationDirectionType associationDirection = ASSOCIATION_DIRECTION_EDEFAULT;

    /**
     * This is true if the Association Direction attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean associationDirectionESet;

    /**
     * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSource()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSource()
     * @generated
     * @ordered
     */
    protected String source = SOURCE_EDEFAULT;

    /**
     * The default value of the '{@link #getTarget() <em>Target</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected static final String TARGET_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected String target = TARGET_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AssociationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ASSOCIATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ConnectorGraphicsInfo> getConnectorGraphicsInfos() {
        if (connectorGraphicsInfos == null) {
            connectorGraphicsInfos = new EObjectContainmentEList<ConnectorGraphicsInfo>(ConnectorGraphicsInfo.class,
                    this, Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS);
        }
        return connectorGraphicsInfos;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Object getObject() {
        return object;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObject(com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ASSOCIATION__OBJECT, oldObject, newObject);
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
    public void setObject(com.tibco.xpd.xpdl2.Object newObject) {
        if (newObject != object) {
            NotificationChain msgs = null;
            if (object != null)
                msgs = ((InternalEObject) object)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ASSOCIATION__OBJECT, null, msgs);
            if (newObject != null)
                msgs = ((InternalEObject) newObject)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ASSOCIATION__OBJECT, null, msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSOCIATION__OBJECT, newObject,
                    newObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AssociationDirectionType getAssociationDirection() {
        return associationDirection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAssociationDirection(AssociationDirectionType newAssociationDirection) {
        AssociationDirectionType oldAssociationDirection = associationDirection;
        associationDirection =
                newAssociationDirection == null ? ASSOCIATION_DIRECTION_EDEFAULT : newAssociationDirection;
        boolean oldAssociationDirectionESet = associationDirectionESet;
        associationDirectionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSOCIATION__ASSOCIATION_DIRECTION,
                    oldAssociationDirection, associationDirection, !oldAssociationDirectionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAssociationDirection() {
        AssociationDirectionType oldAssociationDirection = associationDirection;
        boolean oldAssociationDirectionESet = associationDirectionESet;
        associationDirection = ASSOCIATION_DIRECTION_EDEFAULT;
        associationDirectionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ASSOCIATION__ASSOCIATION_DIRECTION,
                    oldAssociationDirection, ASSOCIATION_DIRECTION_EDEFAULT, oldAssociationDirectionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAssociationDirection() {
        return associationDirectionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSource() {
        return source;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSource(String newSource) {
        String oldSource = source;
        source = newSource;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSOCIATION__SOURCE, oldSource, source));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(String newTarget) {
        String oldTarget = target;
        target = newTarget;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSOCIATION__TARGET, oldTarget, target));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Package getPackage() {
        if (eContainerFeatureID() != Xpdl2Package.ASSOCIATION__PACKAGE)
            return null;
        return (com.tibco.xpd.xpdl2.Package) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPackage(com.tibco.xpd.xpdl2.Package newPackage, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject) newPackage, Xpdl2Package.ASSOCIATION__PACKAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackage(com.tibco.xpd.xpdl2.Package newPackage) {
        if (newPackage != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.ASSOCIATION__PACKAGE && newPackage != null)) {
            if (EcoreUtil.isAncestor(this, newPackage))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newPackage != null)
                msgs = ((InternalEObject) newPackage)
                        .eInverseAdd(this, Xpdl2Package.PACKAGE__ASSOCIATIONS, com.tibco.xpd.xpdl2.Package.class, msgs);
            msgs = basicSetPackage(newPackage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSOCIATION__PACKAGE, newPackage,
                    newPackage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public ConnectorGraphicsInfo getConnectorGraphicsInfoForTool(String id) {
        EList<ConnectorGraphicsInfo> cgis = getConnectorGraphicsInfos();
        if (cgis != null) {
            for (ConnectorGraphicsInfo cgi : cgis) {
                String toolId = cgi.getToolId();

                if (id == null || id.length() == 0) {
                    if (toolId == null || toolId.length() == 0) {
                        return cgi;
                    }
                } else if (id.equals(toolId)) {
                    return cgi;
                }
            }

        }

        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getIncomingAssociations() {
        if (getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Target(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getOutgoingAssociations() {
        if (getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Source(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ASSOCIATION__PACKAGE:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetPackage((com.tibco.xpd.xpdl2.Package) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS:
            return ((InternalEList<?>) getConnectorGraphicsInfos()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ASSOCIATION__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.ASSOCIATION__PACKAGE:
            return basicSetPackage(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.ASSOCIATION__PACKAGE:
            return eInternalContainer()
                    .eInverseRemove(this, Xpdl2Package.PACKAGE__ASSOCIATIONS, com.tibco.xpd.xpdl2.Package.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS:
            return getConnectorGraphicsInfos();
        case Xpdl2Package.ASSOCIATION__OBJECT:
            return getObject();
        case Xpdl2Package.ASSOCIATION__ASSOCIATION_DIRECTION:
            return getAssociationDirection();
        case Xpdl2Package.ASSOCIATION__SOURCE:
            return getSource();
        case Xpdl2Package.ASSOCIATION__TARGET:
            return getTarget();
        case Xpdl2Package.ASSOCIATION__PACKAGE:
            return getPackage();
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
        case Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS:
            getConnectorGraphicsInfos().clear();
            getConnectorGraphicsInfos().addAll((Collection<? extends ConnectorGraphicsInfo>) newValue);
            return;
        case Xpdl2Package.ASSOCIATION__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.ASSOCIATION__ASSOCIATION_DIRECTION:
            setAssociationDirection((AssociationDirectionType) newValue);
            return;
        case Xpdl2Package.ASSOCIATION__SOURCE:
            setSource((String) newValue);
            return;
        case Xpdl2Package.ASSOCIATION__TARGET:
            setTarget((String) newValue);
            return;
        case Xpdl2Package.ASSOCIATION__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) newValue);
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
        case Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS:
            getConnectorGraphicsInfos().clear();
            return;
        case Xpdl2Package.ASSOCIATION__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.ASSOCIATION__ASSOCIATION_DIRECTION:
            unsetAssociationDirection();
            return;
        case Xpdl2Package.ASSOCIATION__SOURCE:
            setSource(SOURCE_EDEFAULT);
            return;
        case Xpdl2Package.ASSOCIATION__TARGET:
            setTarget(TARGET_EDEFAULT);
            return;
        case Xpdl2Package.ASSOCIATION__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) null);
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
        case Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS:
            return connectorGraphicsInfos != null && !connectorGraphicsInfos.isEmpty();
        case Xpdl2Package.ASSOCIATION__OBJECT:
            return object != null;
        case Xpdl2Package.ASSOCIATION__ASSOCIATION_DIRECTION:
            return isSetAssociationDirection();
        case Xpdl2Package.ASSOCIATION__SOURCE:
            return SOURCE_EDEFAULT == null ? source != null : !SOURCE_EDEFAULT.equals(source);
        case Xpdl2Package.ASSOCIATION__TARGET:
            return TARGET_EDEFAULT == null ? target != null : !TARGET_EDEFAULT.equals(target);
        case Xpdl2Package.ASSOCIATION__PACKAGE:
            return getPackage() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == GraphicalConnector.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS:
                return Xpdl2Package.GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == GraphicalConnector.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS:
                return Xpdl2Package.ASSOCIATION__CONNECTOR_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (associationDirection: "); //$NON-NLS-1$
        if (associationDirectionESet)
            result.append(associationDirection);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", source: "); //$NON-NLS-1$
        result.append(source);
        result.append(", target: "); //$NON-NLS-1$
        result.append(target);
        result.append(')');
        return result.toString();
    }

} //AssociationImpl
