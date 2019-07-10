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
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.ApplicationsContainer;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.ConformanceClass;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.PartnerLinkType;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Script;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getApplications <em>Applications</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getParticipants <em>Participants</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getDataFields <em>Data Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getPackageHeader <em>Package Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getRedefinableHeader <em>Redefinable Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getConformanceClass <em>Conformance Class</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getScript <em>Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getExternalPackages <em>External Packages</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getTypeDeclarations <em>Type Declarations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getPartnerLinkTypes <em>Partner Link Types</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getPools <em>Pools</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getMessageFlows <em>Message Flows</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getAssociations <em>Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getArtifacts <em>Artifacts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getProcesses <em>Processes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PackageImpl#getQueryLanguage <em>Query Language</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageImpl extends NamedElementImpl implements com.tibco.xpd.xpdl2.Package {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExtendedAttributes() <em>Extended Attributes</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The cached value of the '{@link #getApplications() <em>Applications</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getApplications()
     * @generated
     * @ordered
     */
    protected EList<Application> applications;

    /**
     * The cached value of the '{@link #getParticipants() <em>Participants</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getParticipants()
     * @generated
     * @ordered
     */
    protected EList<Participant> participants;

    /**
     * The cached value of the '{@link #getDataFields() <em>Data Fields</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDataFields()
     * @generated
     * @ordered
     */
    protected EList<DataField> dataFields;

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The cached value of the '{@link #getPackageHeader() <em>Package Header</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPackageHeader()
     * @generated
     * @ordered
     */
    protected PackageHeader packageHeader;

    /**
     * The cached value of the '{@link #getRedefinableHeader() <em>Redefinable Header</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRedefinableHeader()
     * @generated
     * @ordered
     */
    protected RedefinableHeader redefinableHeader;

    /**
     * The cached value of the '{@link #getConformanceClass() <em>Conformance Class</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConformanceClass()
     * @generated
     * @ordered
     */
    protected ConformanceClass conformanceClass;

    /**
     * The cached value of the '{@link #getScript() <em>Script</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getScript()
     * @generated
     * @ordered
     */
    protected Script script;

    /**
     * The cached value of the '{@link #getExternalPackages() <em>External Packages</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getExternalPackages()
     * @generated
     * @ordered
     */
    protected EList<ExternalPackage> externalPackages;

    /**
     * The cached value of the '{@link #getTypeDeclarations() <em>Type Declarations</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTypeDeclarations()
     * @generated
     * @ordered
     */
    protected EList<TypeDeclaration> typeDeclarations;

    /**
     * The cached value of the '{@link #getPartnerLinkTypes() <em>Partner Link Types</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPartnerLinkTypes()
     * @generated
     * @ordered
     */
    protected EList<PartnerLinkType> partnerLinkTypes;

    /**
     * The cached value of the '{@link #getPools() <em>Pools</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPools()
     * @generated
     * @ordered
     */
    protected EList<Pool> pools;

    /**
     * The cached value of the '{@link #getMessageFlows() <em>Message Flows</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getMessageFlows()
     * @generated
     * @ordered
     */
    protected EList<MessageFlow> messageFlows;

    /**
     * The cached value of the '{@link #getAssociations() <em>Associations</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAssociations()
     * @generated
     * @ordered
     */
    protected EList<Association> associations;

    /**
     * The cached value of the '{@link #getArtifacts() <em>Artifacts</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getArtifacts()
     * @generated
     * @ordered
     */
    protected EList<Artifact> artifacts;

    /**
     * The cached value of the '{@link #getProcesses() <em>Processes</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProcesses()
     * @generated
     * @ordered
     */
    protected EList<com.tibco.xpd.xpdl2.Process> processes;

    /**
     * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLanguage()
     * @generated
     * @ordered
     */
    protected static final String LANGUAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLanguage()
     * @generated
     * @ordered
     */
    protected String language = LANGUAGE_EDEFAULT;

    /**
     * The default value of the '{@link #getQueryLanguage() <em>Query Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQueryLanguage()
     * @generated
     * @ordered
     */
    protected static final String QUERY_LANGUAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getQueryLanguage() <em>Query Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQueryLanguage()
     * @generated
     * @ordered
     */
    protected String queryLanguage = QUERY_LANGUAGE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected PackageImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PACKAGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Application> getApplications() {
        if (applications == null) {
            applications = new EObjectContainmentEList<Application>(Application.class, this,
                    Xpdl2Package.PACKAGE__APPLICATIONS);
        }
        return applications;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Participant> getParticipants() {
        if (participants == null) {
            participants = new EObjectContainmentEList<Participant>(Participant.class, this,
                    Xpdl2Package.PACKAGE__PARTICIPANTS);
        }
        return participants;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<DataField> getDataFields() {
        if (dataFields == null) {
            dataFields =
                    new EObjectContainmentEList<DataField>(DataField.class, this, Xpdl2Package.PACKAGE__DATA_FIELDS);
        }
        return dataFields;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.PACKAGE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PackageHeader getPackageHeader() {
        return packageHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPackageHeader(PackageHeader newPackageHeader, NotificationChain msgs) {
        PackageHeader oldPackageHeader = packageHeader;
        packageHeader = newPackageHeader;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE__PACKAGE_HEADER, oldPackageHeader, newPackageHeader);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setPackageHeader(PackageHeader newPackageHeader) {
        if (newPackageHeader != packageHeader) {
            NotificationChain msgs = null;
            if (packageHeader != null)
                msgs = ((InternalEObject) packageHeader).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__PACKAGE_HEADER,
                        null,
                        msgs);
            if (newPackageHeader != null)
                msgs = ((InternalEObject) newPackageHeader)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__PACKAGE_HEADER, null, msgs);
            msgs = basicSetPackageHeader(newPackageHeader, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE__PACKAGE_HEADER,
                    newPackageHeader, newPackageHeader));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RedefinableHeader getRedefinableHeader() {
        return redefinableHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRedefinableHeader(RedefinableHeader newRedefinableHeader, NotificationChain msgs) {
        RedefinableHeader oldRedefinableHeader = redefinableHeader;
        redefinableHeader = newRedefinableHeader;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE__REDEFINABLE_HEADER, oldRedefinableHeader, newRedefinableHeader);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRedefinableHeader(RedefinableHeader newRedefinableHeader) {
        if (newRedefinableHeader != redefinableHeader) {
            NotificationChain msgs = null;
            if (redefinableHeader != null)
                msgs = ((InternalEObject) redefinableHeader).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__REDEFINABLE_HEADER,
                        null,
                        msgs);
            if (newRedefinableHeader != null)
                msgs = ((InternalEObject) newRedefinableHeader).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__REDEFINABLE_HEADER,
                        null,
                        msgs);
            msgs = basicSetRedefinableHeader(newRedefinableHeader, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE__REDEFINABLE_HEADER,
                    newRedefinableHeader, newRedefinableHeader));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConformanceClass getConformanceClass() {
        return conformanceClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetConformanceClass(ConformanceClass newConformanceClass, NotificationChain msgs) {
        ConformanceClass oldConformanceClass = conformanceClass;
        conformanceClass = newConformanceClass;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PACKAGE__CONFORMANCE_CLASS, oldConformanceClass, newConformanceClass);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setConformanceClass(ConformanceClass newConformanceClass) {
        if (newConformanceClass != conformanceClass) {
            NotificationChain msgs = null;
            if (conformanceClass != null)
                msgs = ((InternalEObject) conformanceClass).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__CONFORMANCE_CLASS,
                        null,
                        msgs);
            if (newConformanceClass != null)
                msgs = ((InternalEObject) newConformanceClass).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__CONFORMANCE_CLASS,
                        null,
                        msgs);
            msgs = basicSetConformanceClass(newConformanceClass, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE__CONFORMANCE_CLASS,
                    newConformanceClass, newConformanceClass));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Script getScript() {
        return script;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScript(Script newScript, NotificationChain msgs) {
        Script oldScript = script;
        script = newScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE__SCRIPT, oldScript, newScript);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setScript(Script newScript) {
        if (newScript != script) {
            NotificationChain msgs = null;
            if (script != null)
                msgs = ((InternalEObject) script)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__SCRIPT, null, msgs);
            if (newScript != null)
                msgs = ((InternalEObject) newScript)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PACKAGE__SCRIPT, null, msgs);
            msgs = basicSetScript(newScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE__SCRIPT, newScript, newScript));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ExternalPackage> getExternalPackages() {
        if (externalPackages == null) {
            externalPackages = new EObjectContainmentEList<ExternalPackage>(ExternalPackage.class, this,
                    Xpdl2Package.PACKAGE__EXTERNAL_PACKAGES);
        }
        return externalPackages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<TypeDeclaration> getTypeDeclarations() {
        if (typeDeclarations == null) {
            typeDeclarations = new EObjectContainmentEList<TypeDeclaration>(TypeDeclaration.class, this,
                    Xpdl2Package.PACKAGE__TYPE_DECLARATIONS);
        }
        return typeDeclarations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<PartnerLinkType> getPartnerLinkTypes() {
        if (partnerLinkTypes == null) {
            partnerLinkTypes = new EObjectContainmentEList<PartnerLinkType>(PartnerLinkType.class, this,
                    Xpdl2Package.PACKAGE__PARTNER_LINK_TYPES);
        }
        return partnerLinkTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Pool> getPools() {
        if (pools == null) {
            pools = new EObjectContainmentWithInverseEList<Pool>(Pool.class, this, Xpdl2Package.PACKAGE__POOLS,
                    Xpdl2Package.POOL__PARENT_PACKAGE);
        }
        return pools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<MessageFlow> getMessageFlows() {
        if (messageFlows == null) {
            messageFlows = new EObjectContainmentWithInverseEList<MessageFlow>(MessageFlow.class, this,
                    Xpdl2Package.PACKAGE__MESSAGE_FLOWS, Xpdl2Package.MESSAGE_FLOW__PACKAGE);
        }
        return messageFlows;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Association> getAssociations() {
        if (associations == null) {
            associations = new EObjectContainmentWithInverseEList<Association>(Association.class, this,
                    Xpdl2Package.PACKAGE__ASSOCIATIONS, Xpdl2Package.ASSOCIATION__PACKAGE);
        }
        return associations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Artifact> getArtifacts() {
        if (artifacts == null) {
            artifacts = new EObjectContainmentWithInverseEList<Artifact>(Artifact.class, this,
                    Xpdl2Package.PACKAGE__ARTIFACTS, Xpdl2Package.ARTIFACT__PACKAGE);
        }
        return artifacts;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<com.tibco.xpd.xpdl2.Process> getProcesses() {
        if (processes == null) {
            processes = new EObjectContainmentWithInverseEList<com.tibco.xpd.xpdl2.Process>(
                    com.tibco.xpd.xpdl2.Process.class, this, Xpdl2Package.PACKAGE__PROCESSES,
                    Xpdl2Package.PROCESS__PACKAGE);
        }
        return processes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLanguage() {
        return language;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLanguage(String newLanguage) {
        String oldLanguage = language;
        language = newLanguage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE__LANGUAGE, oldLanguage,
                    language));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getQueryLanguage() {
        return queryLanguage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQueryLanguage(String newQueryLanguage) {
        String oldQueryLanguage = queryLanguage;
        queryLanguage = newQueryLanguage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PACKAGE__QUERY_LANGUAGE,
                    oldQueryLanguage, queryLanguage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated NOT
     */
    public DataField getDataField(String id) {
        return (DataField) EMFSearchUtil
                .findInList(getDataFields(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Participant getParticipant(String id) {
        return (Participant) EMFSearchUtil
                .findInList(getParticipants(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Process getProcess(String id) {
        return (Process) EMFSearchUtil.findInList(getProcesses(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public ExternalPackage getExternalPackage(String id) {
        return (ExternalPackage) EMFSearchUtil
                .findInList(getExternalPackages(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public TypeDeclaration getTypeDeclaration(String id) {
        return (TypeDeclaration) EMFSearchUtil
                .findInList(getTypeDeclarations(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public PartnerLinkType getPartnerLinkType(String id) {
        return (PartnerLinkType) EMFSearchUtil
                .findInList(getPartnerLinkTypes(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Pool getPool(String id) {
        return (Pool) EMFSearchUtil.findInList(getPools(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public MessageFlow getMessageFlow(String id) {
        return (MessageFlow) EMFSearchUtil
                .findInList(getMessageFlows(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Association getAssociation(String id) {
        return (Association) EMFSearchUtil
                .findInList(getAssociations(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Artifact getArtifact(String id) {
        return (Artifact) EMFSearchUtil.findInList(getArtifacts(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList getMessageFlowFrom(String id) {
        BasicEList list = new BasicEList();
        list.addAll(
                EMFSearchUtil.findManyInList(getMessageFlows(), Xpdl2Package.eINSTANCE.getMessageFlow_Source(), id));
        return list;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList getMessageFlowTo(String id) {
        BasicEList list = new BasicEList();
        list.addAll(
                EMFSearchUtil.findManyInList(getMessageFlows(), Xpdl2Package.eINSTANCE.getMessageFlow_Target(), id));
        return list;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public NamedElement findNamedElement(final String id) {

        // use EMF ID resolver
        EObject eo = null;

        if (eResource() != null) {
            eo = eResource().getEObject(id);
        }

        // with fallback to old find element mechanizm
        if (!(eo instanceof NamedElement)) {
            TreeIterator cont = EcoreUtil.getAllContents(this, false);
            while (cont.hasNext()) {
                Object element = cont.next();
                if (element instanceof NamedElement) {
                    if (id.equals(((NamedElement) element).getId())) {
                        return (NamedElement) element;
                    }
                }
            }
        } else {
            return (NamedElement) eo;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EObject getOtherElement(String elementName) {
        return Xpdl2Operations.getOtherElement(this, elementName);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PACKAGE__POOLS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getPools()).basicAdd(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__MESSAGE_FLOWS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getMessageFlows()).basicAdd(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__ASSOCIATIONS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAssociations()).basicAdd(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__ARTIFACTS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getArtifacts()).basicAdd(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__PROCESSES:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getProcesses()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__APPLICATIONS:
            return ((InternalEList<?>) getApplications()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__PARTICIPANTS:
            return ((InternalEList<?>) getParticipants()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__DATA_FIELDS:
            return ((InternalEList<?>) getDataFields()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__PACKAGE_HEADER:
            return basicSetPackageHeader(null, msgs);
        case Xpdl2Package.PACKAGE__REDEFINABLE_HEADER:
            return basicSetRedefinableHeader(null, msgs);
        case Xpdl2Package.PACKAGE__CONFORMANCE_CLASS:
            return basicSetConformanceClass(null, msgs);
        case Xpdl2Package.PACKAGE__SCRIPT:
            return basicSetScript(null, msgs);
        case Xpdl2Package.PACKAGE__EXTERNAL_PACKAGES:
            return ((InternalEList<?>) getExternalPackages()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__TYPE_DECLARATIONS:
            return ((InternalEList<?>) getTypeDeclarations()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__PARTNER_LINK_TYPES:
            return ((InternalEList<?>) getPartnerLinkTypes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__POOLS:
            return ((InternalEList<?>) getPools()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__MESSAGE_FLOWS:
            return ((InternalEList<?>) getMessageFlows()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__ASSOCIATIONS:
            return ((InternalEList<?>) getAssociations()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__ARTIFACTS:
            return ((InternalEList<?>) getArtifacts()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.PACKAGE__PROCESSES:
            return ((InternalEList<?>) getProcesses()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.PACKAGE__APPLICATIONS:
            return getApplications();
        case Xpdl2Package.PACKAGE__PARTICIPANTS:
            return getParticipants();
        case Xpdl2Package.PACKAGE__DATA_FIELDS:
            return getDataFields();
        case Xpdl2Package.PACKAGE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.PACKAGE__PACKAGE_HEADER:
            return getPackageHeader();
        case Xpdl2Package.PACKAGE__REDEFINABLE_HEADER:
            return getRedefinableHeader();
        case Xpdl2Package.PACKAGE__CONFORMANCE_CLASS:
            return getConformanceClass();
        case Xpdl2Package.PACKAGE__SCRIPT:
            return getScript();
        case Xpdl2Package.PACKAGE__EXTERNAL_PACKAGES:
            return getExternalPackages();
        case Xpdl2Package.PACKAGE__TYPE_DECLARATIONS:
            return getTypeDeclarations();
        case Xpdl2Package.PACKAGE__PARTNER_LINK_TYPES:
            return getPartnerLinkTypes();
        case Xpdl2Package.PACKAGE__POOLS:
            return getPools();
        case Xpdl2Package.PACKAGE__MESSAGE_FLOWS:
            return getMessageFlows();
        case Xpdl2Package.PACKAGE__ASSOCIATIONS:
            return getAssociations();
        case Xpdl2Package.PACKAGE__ARTIFACTS:
            return getArtifacts();
        case Xpdl2Package.PACKAGE__PROCESSES:
            return getProcesses();
        case Xpdl2Package.PACKAGE__LANGUAGE:
            return getLanguage();
        case Xpdl2Package.PACKAGE__QUERY_LANGUAGE:
            return getQueryLanguage();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.PACKAGE__APPLICATIONS:
            getApplications().clear();
            getApplications().addAll((Collection<? extends Application>) newValue);
            return;
        case Xpdl2Package.PACKAGE__PARTICIPANTS:
            getParticipants().clear();
            getParticipants().addAll((Collection<? extends Participant>) newValue);
            return;
        case Xpdl2Package.PACKAGE__DATA_FIELDS:
            getDataFields().clear();
            getDataFields().addAll((Collection<? extends DataField>) newValue);
            return;
        case Xpdl2Package.PACKAGE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.PACKAGE__PACKAGE_HEADER:
            setPackageHeader((PackageHeader) newValue);
            return;
        case Xpdl2Package.PACKAGE__REDEFINABLE_HEADER:
            setRedefinableHeader((RedefinableHeader) newValue);
            return;
        case Xpdl2Package.PACKAGE__CONFORMANCE_CLASS:
            setConformanceClass((ConformanceClass) newValue);
            return;
        case Xpdl2Package.PACKAGE__SCRIPT:
            setScript((Script) newValue);
            return;
        case Xpdl2Package.PACKAGE__EXTERNAL_PACKAGES:
            getExternalPackages().clear();
            getExternalPackages().addAll((Collection<? extends ExternalPackage>) newValue);
            return;
        case Xpdl2Package.PACKAGE__TYPE_DECLARATIONS:
            getTypeDeclarations().clear();
            getTypeDeclarations().addAll((Collection<? extends TypeDeclaration>) newValue);
            return;
        case Xpdl2Package.PACKAGE__PARTNER_LINK_TYPES:
            getPartnerLinkTypes().clear();
            getPartnerLinkTypes().addAll((Collection<? extends PartnerLinkType>) newValue);
            return;
        case Xpdl2Package.PACKAGE__POOLS:
            getPools().clear();
            getPools().addAll((Collection<? extends Pool>) newValue);
            return;
        case Xpdl2Package.PACKAGE__MESSAGE_FLOWS:
            getMessageFlows().clear();
            getMessageFlows().addAll((Collection<? extends MessageFlow>) newValue);
            return;
        case Xpdl2Package.PACKAGE__ASSOCIATIONS:
            getAssociations().clear();
            getAssociations().addAll((Collection<? extends Association>) newValue);
            return;
        case Xpdl2Package.PACKAGE__ARTIFACTS:
            getArtifacts().clear();
            getArtifacts().addAll((Collection<? extends Artifact>) newValue);
            return;
        case Xpdl2Package.PACKAGE__PROCESSES:
            getProcesses().clear();
            getProcesses().addAll((Collection<? extends com.tibco.xpd.xpdl2.Process>) newValue);
            return;
        case Xpdl2Package.PACKAGE__LANGUAGE:
            setLanguage((String) newValue);
            return;
        case Xpdl2Package.PACKAGE__QUERY_LANGUAGE:
            setQueryLanguage((String) newValue);
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
        case Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.PACKAGE__APPLICATIONS:
            getApplications().clear();
            return;
        case Xpdl2Package.PACKAGE__PARTICIPANTS:
            getParticipants().clear();
            return;
        case Xpdl2Package.PACKAGE__DATA_FIELDS:
            getDataFields().clear();
            return;
        case Xpdl2Package.PACKAGE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.PACKAGE__PACKAGE_HEADER:
            setPackageHeader((PackageHeader) null);
            return;
        case Xpdl2Package.PACKAGE__REDEFINABLE_HEADER:
            setRedefinableHeader((RedefinableHeader) null);
            return;
        case Xpdl2Package.PACKAGE__CONFORMANCE_CLASS:
            setConformanceClass((ConformanceClass) null);
            return;
        case Xpdl2Package.PACKAGE__SCRIPT:
            setScript((Script) null);
            return;
        case Xpdl2Package.PACKAGE__EXTERNAL_PACKAGES:
            getExternalPackages().clear();
            return;
        case Xpdl2Package.PACKAGE__TYPE_DECLARATIONS:
            getTypeDeclarations().clear();
            return;
        case Xpdl2Package.PACKAGE__PARTNER_LINK_TYPES:
            getPartnerLinkTypes().clear();
            return;
        case Xpdl2Package.PACKAGE__POOLS:
            getPools().clear();
            return;
        case Xpdl2Package.PACKAGE__MESSAGE_FLOWS:
            getMessageFlows().clear();
            return;
        case Xpdl2Package.PACKAGE__ASSOCIATIONS:
            getAssociations().clear();
            return;
        case Xpdl2Package.PACKAGE__ARTIFACTS:
            getArtifacts().clear();
            return;
        case Xpdl2Package.PACKAGE__PROCESSES:
            getProcesses().clear();
            return;
        case Xpdl2Package.PACKAGE__LANGUAGE:
            setLanguage(LANGUAGE_EDEFAULT);
            return;
        case Xpdl2Package.PACKAGE__QUERY_LANGUAGE:
            setQueryLanguage(QUERY_LANGUAGE_EDEFAULT);
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
        case Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.PACKAGE__APPLICATIONS:
            return applications != null && !applications.isEmpty();
        case Xpdl2Package.PACKAGE__PARTICIPANTS:
            return participants != null && !participants.isEmpty();
        case Xpdl2Package.PACKAGE__DATA_FIELDS:
            return dataFields != null && !dataFields.isEmpty();
        case Xpdl2Package.PACKAGE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.PACKAGE__PACKAGE_HEADER:
            return packageHeader != null;
        case Xpdl2Package.PACKAGE__REDEFINABLE_HEADER:
            return redefinableHeader != null;
        case Xpdl2Package.PACKAGE__CONFORMANCE_CLASS:
            return conformanceClass != null;
        case Xpdl2Package.PACKAGE__SCRIPT:
            return script != null;
        case Xpdl2Package.PACKAGE__EXTERNAL_PACKAGES:
            return externalPackages != null && !externalPackages.isEmpty();
        case Xpdl2Package.PACKAGE__TYPE_DECLARATIONS:
            return typeDeclarations != null && !typeDeclarations.isEmpty();
        case Xpdl2Package.PACKAGE__PARTNER_LINK_TYPES:
            return partnerLinkTypes != null && !partnerLinkTypes.isEmpty();
        case Xpdl2Package.PACKAGE__POOLS:
            return pools != null && !pools.isEmpty();
        case Xpdl2Package.PACKAGE__MESSAGE_FLOWS:
            return messageFlows != null && !messageFlows.isEmpty();
        case Xpdl2Package.PACKAGE__ASSOCIATIONS:
            return associations != null && !associations.isEmpty();
        case Xpdl2Package.PACKAGE__ARTIFACTS:
            return artifacts != null && !artifacts.isEmpty();
        case Xpdl2Package.PACKAGE__PROCESSES:
            return processes != null && !processes.isEmpty();
        case Xpdl2Package.PACKAGE__LANGUAGE:
            return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
        case Xpdl2Package.PACKAGE__QUERY_LANGUAGE:
            return QUERY_LANGUAGE_EDEFAULT == null ? queryLanguage != null
                    : !QUERY_LANGUAGE_EDEFAULT.equals(queryLanguage);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == ApplicationsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PACKAGE__APPLICATIONS:
                return Xpdl2Package.APPLICATIONS_CONTAINER__APPLICATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == ParticipantsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PACKAGE__PARTICIPANTS:
                return Xpdl2Package.PARTICIPANTS_CONTAINER__PARTICIPANTS;
            default:
                return -1;
            }
        }
        if (baseClass == DataFieldsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PACKAGE__DATA_FIELDS:
                return Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PACKAGE__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == ApplicationsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.APPLICATIONS_CONTAINER__APPLICATIONS:
                return Xpdl2Package.PACKAGE__APPLICATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == ParticipantsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.PARTICIPANTS_CONTAINER__PARTICIPANTS:
                return Xpdl2Package.PACKAGE__PARTICIPANTS;
            default:
                return -1;
            }
        }
        if (baseClass == DataFieldsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS:
                return Xpdl2Package.PACKAGE__DATA_FIELDS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.PACKAGE__OTHER_ELEMENTS;
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", language: "); //$NON-NLS-1$
        result.append(language);
        result.append(", queryLanguage: "); //$NON-NLS-1$
        result.append(queryLanguage);
        result.append(')');
        return result.toString();
    }

} // PackageImpl
