/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.Namespace;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Named Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl#getLabelKey <em>Label Key</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class NamedElementImpl extends ModelElementImpl implements
        NamedElement {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = ""; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EDEFAULT = null;

    /**
     * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getQualifiedName()
     * @generated
     * @ordered
     */
    protected static final String QUALIFIED_NAME_EDEFAULT = ""; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getLabelKey() <em>Label Key</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLabelKey()
     * @generated
     * @ordered
     */
    protected static final String LABEL_KEY_EDEFAULT = null;

    /**
     * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDisplayName()
     * @generated
     * @ordered
     */
    protected static final String DISPLAY_NAME_EDEFAULT = ""; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDisplayName()
     * @generated
     * @ordered
     */
    protected String displayName = DISPLAY_NAME_EDEFAULT;

    /**
     * This is true if the Display Name attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean displayNameESet;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected NamedElementImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.NAMED_ELEMENT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.NAMED_ELEMENT__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Namespace getNamespace() {
        Namespace namespace = basicGetNamespace();
        return namespace != null && namespace.eIsProxy() ? (Namespace) eResolveProxy((InternalEObject) namespace)
                : namespace;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Namespace basicGetNamespace() {
        EObject container = eContainer();
        if (container instanceof Namespace) {
            EStructuralFeature containingFeature = eContainingFeature();
            if (containingFeature != null) {
                return new FeatureNamespaceImpl((Namespace) container,
                        containingFeature);
            }
            return (Namespace) container;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getLabel() {
        return OMUtil.getLabel(this);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getQualifiedName() {
        return OMUtil.getQualifiedName(this);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getLabelKey() {
        return OMUtil.getLabelKey(OMUtil.getQualifiedName(this));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public void setDisplayName(String newDisplayName) {
        String oldDisplayName = displayName;
        displayName = newDisplayName;

        String oldRequiredName =
                NameUtil.getInternalName(oldDisplayName, false);
        String oldActualName = name;
        if (oldRequiredName.equals(oldActualName)) {
            setName(NameUtil.getInternalName(displayName, false));
        }

        boolean oldDisplayNameESet = displayNameESet;
        displayNameESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.NAMED_ELEMENT__DISPLAY_NAME, oldDisplayName,
                    displayName, !oldDisplayNameESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDisplayName() {
        String oldDisplayName = displayName;
        boolean oldDisplayNameESet = displayNameESet;
        displayName = DISPLAY_NAME_EDEFAULT;
        displayNameESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    OMPackage.NAMED_ELEMENT__DISPLAY_NAME, oldDisplayName,
                    DISPLAY_NAME_EDEFAULT, oldDisplayNameESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDisplayName() {
        return displayNameESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getLabel(boolean localize) {
        return OMUtil.getLabel(this, localize);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.NAMED_ELEMENT__NAME:
            return getName();
        case OMPackage.NAMED_ELEMENT__NAMESPACE:
            if (resolve)
                return getNamespace();
            return basicGetNamespace();
        case OMPackage.NAMED_ELEMENT__LABEL:
            return getLabel();
        case OMPackage.NAMED_ELEMENT__QUALIFIED_NAME:
            return getQualifiedName();
        case OMPackage.NAMED_ELEMENT__LABEL_KEY:
            return getLabelKey();
        case OMPackage.NAMED_ELEMENT__DISPLAY_NAME:
            return getDisplayName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case OMPackage.NAMED_ELEMENT__NAME:
            setName((String) newValue);
            return;
        case OMPackage.NAMED_ELEMENT__DISPLAY_NAME:
            setDisplayName((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case OMPackage.NAMED_ELEMENT__NAME:
            setName(NAME_EDEFAULT);
            return;
        case OMPackage.NAMED_ELEMENT__DISPLAY_NAME:
            unsetDisplayName();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case OMPackage.NAMED_ELEMENT__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case OMPackage.NAMED_ELEMENT__NAMESPACE:
            return basicGetNamespace() != null;
        case OMPackage.NAMED_ELEMENT__LABEL:
            return LABEL_EDEFAULT == null ? getLabel() != null
                    : !LABEL_EDEFAULT.equals(getLabel());
        case OMPackage.NAMED_ELEMENT__QUALIFIED_NAME:
            return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null
                    : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
        case OMPackage.NAMED_ELEMENT__LABEL_KEY:
            return LABEL_KEY_EDEFAULT == null ? getLabelKey() != null
                    : !LABEL_KEY_EDEFAULT.equals(getLabelKey());
        case OMPackage.NAMED_ELEMENT__DISPLAY_NAME:
            return isSetDisplayName();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: "); //$NON-NLS-1$
        result.append(name);
        result.append(", displayName: "); //$NON-NLS-1$
        if (displayNameESet)
            result.append(displayName);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} // NamedElementImpl
