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

import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Group;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl#getNodeGraphicsInfos <em>Node Graphics Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl#getDataObject <em>Data Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl#getArtifactType <em>Artifact Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl#getTextAnnotation <em>Text Annotation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl#getGroup <em>Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactImpl extends NamedElementImpl implements Artifact {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getNodeGraphicsInfos() <em>Node Graphics Infos</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNodeGraphicsInfos()
     * @generated
     * @ordered
     */
    protected EList<NodeGraphicsInfo> nodeGraphicsInfos;

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
     * The cached value of the '{@link #getDataObject() <em>Data Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataObject()
     * @generated
     * @ordered
     */
    protected DataObject dataObject;

    /**
     * The default value of the '{@link #getArtifactType() <em>Artifact Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getArtifactType()
     * @generated
     * @ordered
     */
    protected static final ArtifactType ARTIFACT_TYPE_EDEFAULT =
            ArtifactType.DATA_OBJECT_LITERAL;

    /**
     * The cached value of the '{@link #getArtifactType() <em>Artifact Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getArtifactType()
     * @generated
     * @ordered
     */
    protected ArtifactType artifactType = ARTIFACT_TYPE_EDEFAULT;

    /**
     * This is true if the Artifact Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean artifactTypeESet;

    /**
     * The default value of the '{@link #getTextAnnotation() <em>Text Annotation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTextAnnotation()
     * @generated
     * @ordered
     */
    protected static final String TEXT_ANNOTATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTextAnnotation() <em>Text Annotation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTextAnnotation()
     * @generated
     * @ordered
     */
    protected String textAnnotation = TEXT_ANNOTATION_EDEFAULT;

    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected Group group;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ArtifactImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ARTIFACT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<NodeGraphicsInfo> getNodeGraphicsInfos() {
        if (nodeGraphicsInfos == null) {
            nodeGraphicsInfos =
                    new EObjectContainmentEList<NodeGraphicsInfo>(
                            NodeGraphicsInfo.class, this,
                            Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS);
        }
        return nodeGraphicsInfos;
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
    public NotificationChain basicSetObject(
            com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.ARTIFACT__OBJECT, oldObject, newObject);
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
                msgs =
                        ((InternalEObject) object).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ARTIFACT__OBJECT,
                                null,
                                msgs);
            if (newObject != null)
                msgs =
                        ((InternalEObject) newObject).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ARTIFACT__OBJECT,
                                null,
                                msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ARTIFACT__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataObject getDataObject() {
        return dataObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDataObject(DataObject newDataObject,
            NotificationChain msgs) {
        DataObject oldDataObject = dataObject;
        dataObject = newDataObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.ARTIFACT__DATA_OBJECT, oldDataObject,
                            newDataObject);
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
    public void setDataObject(DataObject newDataObject) {
        if (newDataObject != dataObject) {
            NotificationChain msgs = null;
            if (dataObject != null)
                msgs =
                        ((InternalEObject) dataObject).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ARTIFACT__DATA_OBJECT,
                                null,
                                msgs);
            if (newDataObject != null)
                msgs =
                        ((InternalEObject) newDataObject).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ARTIFACT__DATA_OBJECT,
                                null,
                                msgs);
            msgs = basicSetDataObject(newDataObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ARTIFACT__DATA_OBJECT, newDataObject,
                    newDataObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setArtifactType(ArtifactType newArtifactType) {
        ArtifactType oldArtifactType = artifactType;
        artifactType =
                newArtifactType == null ? ARTIFACT_TYPE_EDEFAULT
                        : newArtifactType;
        boolean oldArtifactTypeESet = artifactTypeESet;
        artifactTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ARTIFACT__ARTIFACT_TYPE, oldArtifactType,
                    artifactType, !oldArtifactTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetArtifactType() {
        ArtifactType oldArtifactType = artifactType;
        boolean oldArtifactTypeESet = artifactTypeESet;
        artifactType = ARTIFACT_TYPE_EDEFAULT;
        artifactTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ARTIFACT__ARTIFACT_TYPE, oldArtifactType,
                    ARTIFACT_TYPE_EDEFAULT, oldArtifactTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetArtifactType() {
        return artifactTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Group getGroup() {
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGroup(Group newGroup,
            NotificationChain msgs) {
        Group oldGroup = group;
        group = newGroup;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.ARTIFACT__GROUP, oldGroup, newGroup);
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
    public void setGroup(Group newGroup) {
        if (newGroup != group) {
            NotificationChain msgs = null;
            if (group != null)
                msgs =
                        ((InternalEObject) group).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ARTIFACT__GROUP,
                                null,
                                msgs);
            if (newGroup != null)
                msgs =
                        ((InternalEObject) newGroup).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ARTIFACT__GROUP,
                                null,
                                msgs);
            msgs = basicSetGroup(newGroup, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ARTIFACT__GROUP, newGroup, newGroup));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTextAnnotation() {
        return textAnnotation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTextAnnotation(String newTextAnnotation) {
        String oldTextAnnotation = textAnnotation;
        textAnnotation = newTextAnnotation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ARTIFACT__TEXT_ANNOTATION, oldTextAnnotation,
                    textAnnotation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Package getPackage() {
        if (eContainerFeatureID() != Xpdl2Package.ARTIFACT__PACKAGE)
            return null;
        return (com.tibco.xpd.xpdl2.Package) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPackage(
            com.tibco.xpd.xpdl2.Package newPackage, NotificationChain msgs) {
        msgs =
                eBasicSetContainer((InternalEObject) newPackage,
                        Xpdl2Package.ARTIFACT__PACKAGE,
                        msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackage(com.tibco.xpd.xpdl2.Package newPackage) {
        if (newPackage != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.ARTIFACT__PACKAGE && newPackage != null)) {
            if (EcoreUtil.isAncestor(this, newPackage))
                throw new IllegalArgumentException(
                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newPackage != null)
                msgs =
                        ((InternalEObject) newPackage).eInverseAdd(this,
                                Xpdl2Package.PACKAGE__ARTIFACTS,
                                com.tibco.xpd.xpdl2.Package.class,
                                msgs);
            msgs = basicSetPackage(newPackage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ARTIFACT__PACKAGE, newPackage, newPackage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public NodeGraphicsInfo getNodeGraphicsInfoForTool(String id) {
        EList<NodeGraphicsInfo> ngis = getNodeGraphicsInfos();
        if (ngis != null) {
            for (NodeGraphicsInfo ngi : ngis) {
                String toolId = ngi.getToolId();

                if (id == null || id.length() == 0) {
                    if (toolId == null || toolId.length() == 0) {
                        return ngi;
                    }
                } else if (id.equals(toolId)) {
                    return ngi;
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
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ARTIFACT__PACKAGE:
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
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS:
            return ((InternalEList<?>) getNodeGraphicsInfos())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.ARTIFACT__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.ARTIFACT__DATA_OBJECT:
            return basicSetDataObject(null, msgs);
        case Xpdl2Package.ARTIFACT__PACKAGE:
            return basicSetPackage(null, msgs);
        case Xpdl2Package.ARTIFACT__GROUP:
            return basicSetGroup(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(
            NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.ARTIFACT__PACKAGE:
            return eInternalContainer().eInverseRemove(this,
                    Xpdl2Package.PACKAGE__ARTIFACTS,
                    com.tibco.xpd.xpdl2.Package.class,
                    msgs);
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
        case Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS:
            return getNodeGraphicsInfos();
        case Xpdl2Package.ARTIFACT__OBJECT:
            return getObject();
        case Xpdl2Package.ARTIFACT__DATA_OBJECT:
            return getDataObject();
        case Xpdl2Package.ARTIFACT__ARTIFACT_TYPE:
            return getArtifactType();
        case Xpdl2Package.ARTIFACT__TEXT_ANNOTATION:
            return getTextAnnotation();
        case Xpdl2Package.ARTIFACT__PACKAGE:
            return getPackage();
        case Xpdl2Package.ARTIFACT__GROUP:
            return getGroup();
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
        case Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            getNodeGraphicsInfos()
                    .addAll((Collection<? extends NodeGraphicsInfo>) newValue);
            return;
        case Xpdl2Package.ARTIFACT__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.ARTIFACT__DATA_OBJECT:
            setDataObject((DataObject) newValue);
            return;
        case Xpdl2Package.ARTIFACT__ARTIFACT_TYPE:
            setArtifactType((ArtifactType) newValue);
            return;
        case Xpdl2Package.ARTIFACT__TEXT_ANNOTATION:
            setTextAnnotation((String) newValue);
            return;
        case Xpdl2Package.ARTIFACT__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) newValue);
            return;
        case Xpdl2Package.ARTIFACT__GROUP:
            setGroup((Group) newValue);
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
        case Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            return;
        case Xpdl2Package.ARTIFACT__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.ARTIFACT__DATA_OBJECT:
            setDataObject((DataObject) null);
            return;
        case Xpdl2Package.ARTIFACT__ARTIFACT_TYPE:
            unsetArtifactType();
            return;
        case Xpdl2Package.ARTIFACT__TEXT_ANNOTATION:
            setTextAnnotation(TEXT_ANNOTATION_EDEFAULT);
            return;
        case Xpdl2Package.ARTIFACT__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) null);
            return;
        case Xpdl2Package.ARTIFACT__GROUP:
            setGroup((Group) null);
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
        case Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS:
            return nodeGraphicsInfos != null && !nodeGraphicsInfos.isEmpty();
        case Xpdl2Package.ARTIFACT__OBJECT:
            return object != null;
        case Xpdl2Package.ARTIFACT__DATA_OBJECT:
            return dataObject != null;
        case Xpdl2Package.ARTIFACT__ARTIFACT_TYPE:
            return isSetArtifactType();
        case Xpdl2Package.ARTIFACT__TEXT_ANNOTATION:
            return TEXT_ANNOTATION_EDEFAULT == null ? textAnnotation != null
                    : !TEXT_ANNOTATION_EDEFAULT.equals(textAnnotation);
        case Xpdl2Package.ARTIFACT__PACKAGE:
            return getPackage() != null;
        case Xpdl2Package.ARTIFACT__GROUP:
            return group != null;
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
        if (baseClass == GraphicalNode.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS;
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
        if (baseClass == GraphicalNode.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS;
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (artifactType: "); //$NON-NLS-1$
        if (artifactTypeESet)
            result.append(artifactType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", textAnnotation: "); //$NON-NLS-1$
        result.append(textAnnotation);
        result.append(')');
        return result.toString();
    }

} //ArtifactImpl
