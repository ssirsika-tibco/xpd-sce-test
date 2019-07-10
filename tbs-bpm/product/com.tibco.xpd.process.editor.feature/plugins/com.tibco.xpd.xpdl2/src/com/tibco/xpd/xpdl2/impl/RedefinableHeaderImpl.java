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
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Codepage;
import com.tibco.xpd.xpdl2.CountryKey;
import com.tibco.xpd.xpdl2.PublicationStatusType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Responsible;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Redefinable Header</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl#getCodepage <em>Codepage</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl#getCountrykey <em>Countrykey</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl#getResponsibles <em>Responsibles</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl#getPublicationStatus <em>Publication Status</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RedefinableHeaderImpl extends EObjectImpl implements RedefinableHeader {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

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
     * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAuthor()
     * @generated
     * @ordered
     */
    protected static final String AUTHOR_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAuthor()
     * @generated
     * @ordered
     */
    protected String author = AUTHOR_EDEFAULT;

    /**
     * This is true if the Author attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean authorESet;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * This is true if the Version attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean versionESet;

    /**
     * The cached value of the '{@link #getCodepage() <em>Codepage</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCodepage()
     * @generated
     * @ordered
     */
    protected Codepage codepage;

    /**
     * The cached value of the '{@link #getCountrykey() <em>Countrykey</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCountrykey()
     * @generated
     * @ordered
     */
    protected CountryKey countrykey;

    /**
     * The cached value of the '{@link #getResponsibles() <em>Responsibles</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResponsibles()
     * @generated
     * @ordered
     */
    protected EList<Responsible> responsibles;

    /**
     * The default value of the '{@link #getPublicationStatus() <em>Publication Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPublicationStatus()
     * @generated
     * @ordered
     */
    protected static final PublicationStatusType PUBLICATION_STATUS_EDEFAULT =
            PublicationStatusType.UNDER_REVISION_LITERAL;

    /**
     * The cached value of the '{@link #getPublicationStatus() <em>Publication Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPublicationStatus()
     * @generated
     * @ordered
     */
    protected PublicationStatusType publicationStatus = PUBLICATION_STATUS_EDEFAULT;

    /**
     * This is true if the Publication Status attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean publicationStatusESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RedefinableHeaderImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.REDEFINABLE_HEADER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes = new BasicFeatureMap(this, Xpdl2Package.REDEFINABLE_HEADER__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAuthor() {
        return author;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAuthor(String newAuthor) {
        String oldAuthor = author;
        author = newAuthor;
        boolean oldAuthorESet = authorESet;
        authorESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.REDEFINABLE_HEADER__AUTHOR, oldAuthor,
                    author, !oldAuthorESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAuthor() {
        String oldAuthor = author;
        boolean oldAuthorESet = authorESet;
        author = AUTHOR_EDEFAULT;
        authorESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.REDEFINABLE_HEADER__AUTHOR, oldAuthor,
                    AUTHOR_EDEFAULT, oldAuthorESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAuthor() {
        return authorESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        boolean oldVersionESet = versionESet;
        versionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.REDEFINABLE_HEADER__VERSION, oldVersion,
                    version, !oldVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetVersion() {
        String oldVersion = version;
        boolean oldVersionESet = versionESet;
        version = VERSION_EDEFAULT;
        versionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.REDEFINABLE_HEADER__VERSION,
                    oldVersion, VERSION_EDEFAULT, oldVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetVersion() {
        return versionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Codepage getCodepage() {
        return codepage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCodepage(Codepage newCodepage, NotificationChain msgs) {
        Codepage oldCodepage = codepage;
        codepage = newCodepage;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE, oldCodepage, newCodepage);
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
    public void setCodepage(Codepage newCodepage) {
        if (newCodepage != codepage) {
            NotificationChain msgs = null;
            if (codepage != null)
                msgs = ((InternalEObject) codepage).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE,
                        null,
                        msgs);
            if (newCodepage != null)
                msgs = ((InternalEObject) newCodepage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE,
                        null,
                        msgs);
            msgs = basicSetCodepage(newCodepage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE,
                    newCodepage, newCodepage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CountryKey getCountrykey() {
        return countrykey;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCountrykey(CountryKey newCountrykey, NotificationChain msgs) {
        CountryKey oldCountrykey = countrykey;
        countrykey = newCountrykey;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY, oldCountrykey, newCountrykey);
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
    public void setCountrykey(CountryKey newCountrykey) {
        if (newCountrykey != countrykey) {
            NotificationChain msgs = null;
            if (countrykey != null)
                msgs = ((InternalEObject) countrykey).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY,
                        null,
                        msgs);
            if (newCountrykey != null)
                msgs = ((InternalEObject) newCountrykey).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY,
                        null,
                        msgs);
            msgs = basicSetCountrykey(newCountrykey, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY,
                    newCountrykey, newCountrykey));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Responsible> getResponsibles() {
        if (responsibles == null) {
            responsibles = new EObjectContainmentEList<Responsible>(Responsible.class, this,
                    Xpdl2Package.REDEFINABLE_HEADER__RESPONSIBLES);
        }
        return responsibles;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PublicationStatusType getPublicationStatus() {
        return publicationStatus;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPublicationStatus(PublicationStatusType newPublicationStatus) {
        PublicationStatusType oldPublicationStatus = publicationStatus;
        publicationStatus = newPublicationStatus == null ? PUBLICATION_STATUS_EDEFAULT : newPublicationStatus;
        boolean oldPublicationStatusESet = publicationStatusESet;
        publicationStatusESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.REDEFINABLE_HEADER__PUBLICATION_STATUS,
                    oldPublicationStatus, publicationStatus, !oldPublicationStatusESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPublicationStatus() {
        PublicationStatusType oldPublicationStatus = publicationStatus;
        boolean oldPublicationStatusESet = publicationStatusESet;
        publicationStatus = PUBLICATION_STATUS_EDEFAULT;
        publicationStatusESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.REDEFINABLE_HEADER__PUBLICATION_STATUS,
                    oldPublicationStatus, PUBLICATION_STATUS_EDEFAULT, oldPublicationStatusESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPublicationStatus() {
        return publicationStatusESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.REDEFINABLE_HEADER__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE:
            return basicSetCodepage(null, msgs);
        case Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY:
            return basicSetCountrykey(null, msgs);
        case Xpdl2Package.REDEFINABLE_HEADER__RESPONSIBLES:
            return ((InternalEList<?>) getResponsibles()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.REDEFINABLE_HEADER__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.REDEFINABLE_HEADER__AUTHOR:
            return getAuthor();
        case Xpdl2Package.REDEFINABLE_HEADER__VERSION:
            return getVersion();
        case Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE:
            return getCodepage();
        case Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY:
            return getCountrykey();
        case Xpdl2Package.REDEFINABLE_HEADER__RESPONSIBLES:
            return getResponsibles();
        case Xpdl2Package.REDEFINABLE_HEADER__PUBLICATION_STATUS:
            return getPublicationStatus();
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
        case Xpdl2Package.REDEFINABLE_HEADER__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__AUTHOR:
            setAuthor((String) newValue);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__VERSION:
            setVersion((String) newValue);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE:
            setCodepage((Codepage) newValue);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY:
            setCountrykey((CountryKey) newValue);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__RESPONSIBLES:
            getResponsibles().clear();
            getResponsibles().addAll((Collection<? extends Responsible>) newValue);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__PUBLICATION_STATUS:
            setPublicationStatus((PublicationStatusType) newValue);
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
        case Xpdl2Package.REDEFINABLE_HEADER__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__AUTHOR:
            unsetAuthor();
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__VERSION:
            unsetVersion();
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE:
            setCodepage((Codepage) null);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY:
            setCountrykey((CountryKey) null);
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__RESPONSIBLES:
            getResponsibles().clear();
            return;
        case Xpdl2Package.REDEFINABLE_HEADER__PUBLICATION_STATUS:
            unsetPublicationStatus();
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
        case Xpdl2Package.REDEFINABLE_HEADER__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.REDEFINABLE_HEADER__AUTHOR:
            return isSetAuthor();
        case Xpdl2Package.REDEFINABLE_HEADER__VERSION:
            return isSetVersion();
        case Xpdl2Package.REDEFINABLE_HEADER__CODEPAGE:
            return codepage != null;
        case Xpdl2Package.REDEFINABLE_HEADER__COUNTRYKEY:
            return countrykey != null;
        case Xpdl2Package.REDEFINABLE_HEADER__RESPONSIBLES:
            return responsibles != null && !responsibles.isEmpty();
        case Xpdl2Package.REDEFINABLE_HEADER__PUBLICATION_STATUS:
            return isSetPublicationStatus();
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", author: "); //$NON-NLS-1$
        if (authorESet)
            result.append(author);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", version: "); //$NON-NLS-1$
        if (versionESet)
            result.append(version);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", publicationStatus: "); //$NON-NLS-1$
        if (publicationStatusESet)
            result.append(publicationStatus);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //RedefinableHeaderImpl
