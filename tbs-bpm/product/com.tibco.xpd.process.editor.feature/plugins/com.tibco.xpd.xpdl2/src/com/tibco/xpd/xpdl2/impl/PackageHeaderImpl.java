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
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.CostUnit;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.LayoutInfo;
import com.tibco.xpd.xpdl2.ModificationDate;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.PriorityUnit;
import com.tibco.xpd.xpdl2.VendorExtensions;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package Header</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getXpdlVersion <em>Xpdl Version</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getVendor <em>Vendor</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getCreated <em>Created</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getPriorityUnit <em>Priority Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getCostUnit <em>Cost Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getVendorExtensions <em>Vendor Extensions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getLayoutInfo <em>Layout Info</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl#getModificationDate <em>Modification Date</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageHeaderImpl extends EObjectImpl implements PackageHeader {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

    /**
     * The default value of the '{@link #getXpdlVersion() <em>Xpdl Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXpdlVersion()
     * @generated
     * @ordered
     */
    protected static final String XPDL_VERSION_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getXpdlVersion() <em>Xpdl Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXpdlVersion()
     * @generated
     * @ordered
     */
    protected String xpdlVersion = XPDL_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVendor()
     * @generated
     * @ordered
     */
    protected static final String VENDOR_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVendor()
     * @generated
     * @ordered
     */
    protected String vendor = VENDOR_EDEFAULT;

    /**
     * The default value of the '{@link #getCreated() <em>Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreated()
     * @generated
     * @ordered
     */
    protected static final String CREATED_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCreated() <em>Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreated()
     * @generated
     * @ordered
     */
    protected String created = CREATED_EDEFAULT;

    /**
     * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDocumentation()
     * @generated
     * @ordered
     */
    protected Documentation documentation;

    /**
     * The cached value of the '{@link #getPriorityUnit() <em>Priority Unit</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPriorityUnit()
     * @generated
     * @ordered
     */
    protected PriorityUnit priorityUnit;

    /**
     * The cached value of the '{@link #getCostUnit() <em>Cost Unit</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCostUnit()
     * @generated
     * @ordered
     */
    protected CostUnit costUnit;

    /**
     * The cached value of the '{@link #getVendorExtensions() <em>Vendor Extensions</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVendorExtensions()
     * @generated
     * @ordered
     */
    protected VendorExtensions vendorExtensions;

    /**
     * The cached value of the '{@link #getLayoutInfo() <em>Layout Info</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLayoutInfo()
     * @generated
     * @ordered
     */
    protected LayoutInfo layoutInfo;

    /**
     * The cached value of the '{@link #getModificationDate() <em>Modification Date</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModificationDate()
     * @generated
     * @ordered
     */
    protected ModificationDate modificationDate;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PackageHeaderImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PACKAGE_HEADER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getXpdlVersion() {
        return xpdlVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVendor(String newVendor) {
        String oldVendor = vendor;
        vendor = newVendor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__VENDOR, oldVendor,
                    vendor));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCreated() {
        return created;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCreated(String newCreated) {
        String oldCreated = created;
        created = newCreated;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__CREATED, oldCreated,
                    created));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Description getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDescription(Description newDescription, NotificationChain msgs) {
        Description oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE_HEADER__DESCRIPTION, oldDescription, newDescription);
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
    public void setDescription(Description newDescription) {
        if (newDescription != description) {
            NotificationChain msgs = null;
            if (description != null)
                msgs = ((InternalEObject) description).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__DESCRIPTION,
                        null,
                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__DESCRIPTION,
                    newDescription, newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes = new BasicFeatureMap(this, Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Documentation getDocumentation() {
        return documentation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDocumentation(Documentation newDocumentation, NotificationChain msgs) {
        Documentation oldDocumentation = documentation;
        documentation = newDocumentation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION, oldDocumentation, newDocumentation);
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
    public void setDocumentation(Documentation newDocumentation) {
        if (newDocumentation != documentation) {
            NotificationChain msgs = null;
            if (documentation != null)
                msgs = ((InternalEObject) documentation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION,
                        null,
                        msgs);
            if (newDocumentation != null)
                msgs = ((InternalEObject) newDocumentation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION,
                        null,
                        msgs);
            msgs = basicSetDocumentation(newDocumentation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION,
                    newDocumentation, newDocumentation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PriorityUnit getPriorityUnit() {
        return priorityUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPriorityUnit(PriorityUnit newPriorityUnit, NotificationChain msgs) {
        PriorityUnit oldPriorityUnit = priorityUnit;
        priorityUnit = newPriorityUnit;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT, oldPriorityUnit, newPriorityUnit);
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
    public void setPriorityUnit(PriorityUnit newPriorityUnit) {
        if (newPriorityUnit != priorityUnit) {
            NotificationChain msgs = null;
            if (priorityUnit != null)
                msgs = ((InternalEObject) priorityUnit).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT,
                        null,
                        msgs);
            if (newPriorityUnit != null)
                msgs = ((InternalEObject) newPriorityUnit).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT,
                        null,
                        msgs);
            msgs = basicSetPriorityUnit(newPriorityUnit, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT,
                    newPriorityUnit, newPriorityUnit));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CostUnit getCostUnit() {
        return costUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCostUnit(CostUnit newCostUnit, NotificationChain msgs) {
        CostUnit oldCostUnit = costUnit;
        costUnit = newCostUnit;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE_HEADER__COST_UNIT, oldCostUnit, newCostUnit);
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
    public void setCostUnit(CostUnit newCostUnit) {
        if (newCostUnit != costUnit) {
            NotificationChain msgs = null;
            if (costUnit != null)
                msgs = ((InternalEObject) costUnit).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__COST_UNIT,
                        null,
                        msgs);
            if (newCostUnit != null)
                msgs = ((InternalEObject) newCostUnit)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__COST_UNIT, null, msgs);
            msgs = basicSetCostUnit(newCostUnit, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__COST_UNIT, newCostUnit,
                    newCostUnit));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public VendorExtensions getVendorExtensions() {
        return vendorExtensions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetVendorExtensions(VendorExtensions newVendorExtensions, NotificationChain msgs) {
        VendorExtensions oldVendorExtensions = vendorExtensions;
        vendorExtensions = newVendorExtensions;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS, oldVendorExtensions, newVendorExtensions);
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
    public void setVendorExtensions(VendorExtensions newVendorExtensions) {
        if (newVendorExtensions != vendorExtensions) {
            NotificationChain msgs = null;
            if (vendorExtensions != null)
                msgs = ((InternalEObject) vendorExtensions).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS,
                        null,
                        msgs);
            if (newVendorExtensions != null)
                msgs = ((InternalEObject) newVendorExtensions).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS,
                        null,
                        msgs);
            msgs = basicSetVendorExtensions(newVendorExtensions, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS,
                    newVendorExtensions, newVendorExtensions));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LayoutInfo getLayoutInfo() {
        return layoutInfo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLayoutInfo(LayoutInfo newLayoutInfo, NotificationChain msgs) {
        LayoutInfo oldLayoutInfo = layoutInfo;
        layoutInfo = newLayoutInfo;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO, oldLayoutInfo, newLayoutInfo);
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
    public void setLayoutInfo(LayoutInfo newLayoutInfo) {
        if (newLayoutInfo != layoutInfo) {
            NotificationChain msgs = null;
            if (layoutInfo != null)
                msgs = ((InternalEObject) layoutInfo).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO,
                        null,
                        msgs);
            if (newLayoutInfo != null)
                msgs = ((InternalEObject) newLayoutInfo).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO,
                        null,
                        msgs);
            msgs = basicSetLayoutInfo(newLayoutInfo, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO,
                    newLayoutInfo, newLayoutInfo));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModificationDate getModificationDate() {
        return modificationDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetModificationDate(ModificationDate newModificationDate, NotificationChain msgs) {
        ModificationDate oldModificationDate = modificationDate;
        modificationDate = newModificationDate;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE, oldModificationDate, newModificationDate);
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
    public void setModificationDate(ModificationDate newModificationDate) {
        if (newModificationDate != modificationDate) {
            NotificationChain msgs = null;
            if (modificationDate != null)
                msgs = ((InternalEObject) modificationDate).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE,
                        null,
                        msgs);
            if (newModificationDate != null)
                msgs = ((InternalEObject) newModificationDate).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE,
                        null,
                        msgs);
            msgs = basicSetModificationDate(newModificationDate, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE,
                    newModificationDate, newModificationDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PACKAGE_HEADER__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION:
            return basicSetDocumentation(null, msgs);
        case Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT:
            return basicSetPriorityUnit(null, msgs);
        case Xpdl2Package.PACKAGE_HEADER__COST_UNIT:
            return basicSetCostUnit(null, msgs);
        case Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS:
            return basicSetVendorExtensions(null, msgs);
        case Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO:
            return basicSetLayoutInfo(null, msgs);
        case Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE:
            return basicSetModificationDate(null, msgs);
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
        case Xpdl2Package.PACKAGE_HEADER__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.PACKAGE_HEADER__XPDL_VERSION:
            return getXpdlVersion();
        case Xpdl2Package.PACKAGE_HEADER__VENDOR:
            return getVendor();
        case Xpdl2Package.PACKAGE_HEADER__CREATED:
            return getCreated();
        case Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION:
            return getDocumentation();
        case Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT:
            return getPriorityUnit();
        case Xpdl2Package.PACKAGE_HEADER__COST_UNIT:
            return getCostUnit();
        case Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS:
            return getVendorExtensions();
        case Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO:
            return getLayoutInfo();
        case Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE:
            return getModificationDate();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.PACKAGE_HEADER__XPDL_VERSION:
            xpdlVersion = (String) newValue;
            return;
        case Xpdl2Package.PACKAGE_HEADER__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.PACKAGE_HEADER__VENDOR:
            setVendor((String) newValue);
            return;
        case Xpdl2Package.PACKAGE_HEADER__CREATED:
            setCreated((String) newValue);
            return;
        case Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION:
            setDocumentation((Documentation) newValue);
            return;
        case Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT:
            setPriorityUnit((PriorityUnit) newValue);
            return;
        case Xpdl2Package.PACKAGE_HEADER__COST_UNIT:
            setCostUnit((CostUnit) newValue);
            return;
        case Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS:
            setVendorExtensions((VendorExtensions) newValue);
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
        case Xpdl2Package.PACKAGE_HEADER__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.PACKAGE_HEADER__VENDOR:
            setVendor(VENDOR_EDEFAULT);
            return;
        case Xpdl2Package.PACKAGE_HEADER__CREATED:
            setCreated(CREATED_EDEFAULT);
            return;
        case Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION:
            setDocumentation((Documentation) null);
            return;
        case Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT:
            setPriorityUnit((PriorityUnit) null);
            return;
        case Xpdl2Package.PACKAGE_HEADER__COST_UNIT:
            setCostUnit((CostUnit) null);
            return;
        case Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS:
            setVendorExtensions((VendorExtensions) null);
            return;
        case Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO:
            setLayoutInfo((LayoutInfo) null);
            return;
        case Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE:
            setModificationDate((ModificationDate) null);
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
        case Xpdl2Package.PACKAGE_HEADER__DESCRIPTION:
            return description != null;
        case Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.PACKAGE_HEADER__XPDL_VERSION:
            return XPDL_VERSION_EDEFAULT == null ? xpdlVersion != null : !XPDL_VERSION_EDEFAULT.equals(xpdlVersion);
        case Xpdl2Package.PACKAGE_HEADER__VENDOR:
            return VENDOR_EDEFAULT == null ? vendor != null : !VENDOR_EDEFAULT.equals(vendor);
        case Xpdl2Package.PACKAGE_HEADER__CREATED:
            return CREATED_EDEFAULT == null ? created != null : !CREATED_EDEFAULT.equals(created);
        case Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION:
            return documentation != null;
        case Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT:
            return priorityUnit != null;
        case Xpdl2Package.PACKAGE_HEADER__COST_UNIT:
            return costUnit != null;
        case Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS:
            return vendorExtensions != null;
        case Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO:
            return layoutInfo != null;
        case Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE:
            return modificationDate != null;
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
        if (baseClass == OtherAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES:
                return Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;
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
        if (baseClass == OtherAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES:
                return Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES;
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", xpdlVersion: "); //$NON-NLS-1$
        result.append(xpdlVersion);
        result.append(", vendor: "); //$NON-NLS-1$
        result.append(vendor);
        result.append(", created: "); //$NON-NLS-1$
        result.append(created);
        result.append(')');
        return result.toString();
    }

} //PackageHeaderImpl
