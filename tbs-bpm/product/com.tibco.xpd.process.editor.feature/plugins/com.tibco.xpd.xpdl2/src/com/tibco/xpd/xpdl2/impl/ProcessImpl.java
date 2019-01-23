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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.impl.RESTServicesImpl;
import com.tibco.xpd.xpdl2.AccessLevelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.AdHocOrderingType;
import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.ApplicationsContainer;
import com.tibco.xpd.xpdl2.AssigmentsContainer;
import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.PartnerLink;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ProcessType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.StatusType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Process</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#isAdHoc <em>Ad Hoc</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getAdHocCompletionCondition <em>Ad Hoc Completion Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getAdHocOrdering <em>Ad Hoc Ordering</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getDefaultStartActivityId <em>Default Start Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getFormalParameters <em>Formal Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getAssignments <em>Assignments</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getDataFields <em>Data Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getParticipants <em>Participants</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getApplications <em>Applications</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getProcessHeader <em>Process Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getRedefinableHeader <em>Redefinable Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getPartnerLinks <em>Partner Links</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getAccessLevel <em>Access Level</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getDefaultStartActivitySetId <em>Default Start Activity Set Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#isEnableInstanceCompensation <em>Enable Instance Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getProcessType <em>Process Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#isSuppressJoinFailure <em>Suppress Join Failure</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessImpl#getActivitySets <em>Activity Sets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessImpl extends NamedElementImpl implements
        com.tibco.xpd.xpdl2.Process {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getActivities()
     * @generated
     * @ordered
     */
    protected EList<Activity> activities;

    /**
     * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTransitions()
     * @generated
     * @ordered
     */
    protected EList<Transition> transitions;

    /**
     * The default value of the '{@link #isAdHoc() <em>Ad Hoc</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isAdHoc()
     * @generated
     * @ordered
     */
    protected static final boolean AD_HOC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAdHoc() <em>Ad Hoc</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isAdHoc()
     * @generated
     * @ordered
     */
    protected boolean adHoc = AD_HOC_EDEFAULT;

    /**
     * This is true if the Ad Hoc attribute has been set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean adHocESet;

    /**
     * The default value of the '{@link #getAdHocCompletionCondition() <em>Ad Hoc Completion Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAdHocCompletionCondition()
     * @generated
     * @ordered
     */
    protected static final String AD_HOC_COMPLETION_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAdHocCompletionCondition() <em>Ad Hoc Completion Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAdHocCompletionCondition()
     * @generated
     * @ordered
     */
    protected String adHocCompletionCondition =
            AD_HOC_COMPLETION_CONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getAdHocOrdering() <em>Ad Hoc Ordering</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAdHocOrdering()
     * @generated
     * @ordered
     */
    protected static final AdHocOrderingType AD_HOC_ORDERING_EDEFAULT =
            AdHocOrderingType.PARALLEL_LITERAL;

    /**
     * The cached value of the '{@link #getAdHocOrdering() <em>Ad Hoc Ordering</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAdHocOrdering()
     * @generated
     * @ordered
     */
    protected AdHocOrderingType adHocOrdering = AD_HOC_ORDERING_EDEFAULT;

    /**
     * This is true if the Ad Hoc Ordering attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean adHocOrderingESet;

    /**
     * The default value of the '{@link #getDefaultStartActivityId() <em>Default Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultStartActivityId()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_START_ACTIVITY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultStartActivityId() <em>Default Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultStartActivityId()
     * @generated
     * @ordered
     */
    protected String defaultStartActivityId =
            DEFAULT_START_ACTIVITY_ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getExtendedAttributes()
     * <em>Extended Attributes</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The cached value of the '{@link #getFormalParameters()
     * <em>Formal Parameters</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getFormalParameters()
     * @generated
     * @ordered
     */
    protected EList<FormalParameter> formalParameters;

    /**
     * The cached value of the '{@link #getAssignments() <em>Assignments</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAssignments()
     * @generated
     * @ordered
     */
    protected Assignment assignments;

    /**
     * The cached value of the '{@link #getDataFields() <em>Data Fields</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDataFields()
     * @generated
     * @ordered
     */
    protected EList<DataField> dataFields;

    /**
     * The cached value of the '{@link #getParticipants() <em>Participants</em>}
     * ' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getParticipants()
     * @generated
     * @ordered
     */
    protected EList<Participant> participants;

    /**
     * The cached value of the '{@link #getApplications() <em>Applications</em>}
     * ' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getApplications()
     * @generated
     * @ordered
     */
    protected EList<Application> applications;

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The cached value of the '{@link #getProcessHeader() <em>Process Header</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessHeader()
     * @generated
     * @ordered
     */
    protected ProcessHeader processHeader;

    /**
     * The cached value of the '{@link #getRedefinableHeader() <em>Redefinable Header</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getRedefinableHeader()
     * @generated
     * @ordered
     */
    protected RedefinableHeader redefinableHeader;

    /**
     * The cached value of the '{@link #getPartnerLinks() <em>Partner Links</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getPartnerLinks()
     * @generated
     * @ordered
     */
    protected EList<PartnerLink> partnerLinks;

    /**
     * The cached value of the '{@link #getObject() <em>Object</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getObject()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Object object;

    /**
     * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getExtensions()
     * @generated
     * @ordered
     */
    protected EObject extensions;

    /**
     * The default value of the '{@link #getAccessLevel() <em>Access Level</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAccessLevel()
     * @generated
     * @ordered
     */
    protected static final AccessLevelType ACCESS_LEVEL_EDEFAULT =
            AccessLevelType.PUBLIC_LITERAL;

    /**
     * The cached value of the '{@link #getAccessLevel() <em>Access Level</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAccessLevel()
     * @generated
     * @ordered
     */
    protected AccessLevelType accessLevel = ACCESS_LEVEL_EDEFAULT;

    /**
     * This is true if the Access Level attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean accessLevelESet;

    /**
     * The default value of the '{@link #getDefaultStartActivitySetId() <em>Default Start Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getDefaultStartActivitySetId()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_START_ACTIVITY_SET_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultStartActivitySetId() <em>Default Start Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getDefaultStartActivitySetId()
     * @generated
     * @ordered
     */
    protected String defaultStartActivitySetId =
            DEFAULT_START_ACTIVITY_SET_ID_EDEFAULT;

    /**
     * The default value of the '{@link #isEnableInstanceCompensation() <em>Enable Instance Compensation</em>}' attribute.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #isEnableInstanceCompensation()
     * @generated
     * @ordered
     */
    protected static final boolean ENABLE_INSTANCE_COMPENSATION_EDEFAULT =
            false;

    /**
     * The cached value of the '{@link #isEnableInstanceCompensation() <em>Enable Instance Compensation</em>}' attribute.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #isEnableInstanceCompensation()
     * @generated
     * @ordered
     */
    protected boolean enableInstanceCompensation =
            ENABLE_INSTANCE_COMPENSATION_EDEFAULT;

    /**
     * This is true if the Enable Instance Compensation attribute has been set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean enableInstanceCompensationESet;

    /**
     * The default value of the '{@link #getProcessType() <em>Process Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProcessType()
     * @generated
     * @ordered
     */
    protected static final ProcessType PROCESS_TYPE_EDEFAULT =
            ProcessType.NONE_LITERAL;

    /**
     * The cached value of the '{@link #getProcessType() <em>Process Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProcessType()
     * @generated
     * @ordered
     */
    protected ProcessType processType = PROCESS_TYPE_EDEFAULT;

    /**
     * This is true if the Process Type attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean processTypeESet;

    /**
     * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStatus()
     * @generated
     * @ordered
     */
    protected static final StatusType STATUS_EDEFAULT = StatusType.NONE_LITERAL;

    /**
     * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStatus()
     * @generated
     * @ordered
     */
    protected StatusType status = STATUS_EDEFAULT;

    /**
     * This is true if the Status attribute has been set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean statusESet;

    /**
     * The default value of the '{@link #isSuppressJoinFailure() <em>Suppress Join Failure</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isSuppressJoinFailure()
     * @generated
     * @ordered
     */
    protected static final boolean SUPPRESS_JOIN_FAILURE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSuppressJoinFailure() <em>Suppress Join Failure</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isSuppressJoinFailure()
     * @generated
     * @ordered
     */
    protected boolean suppressJoinFailure = SUPPRESS_JOIN_FAILURE_EDEFAULT;

    /**
     * This is true if the Suppress Join Failure attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean suppressJoinFailureESet;

    /**
     * The cached value of the '{@link #getActivitySets() <em>Activity Sets</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getActivitySets()
     * @generated
     * @ordered
     */
    protected EList<ActivitySet> activitySets;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ProcessImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PROCESS;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Activity> getActivities() {
        if (activities == null) {
            activities =
                    new EObjectContainmentWithInverseEList<Activity>(
                            Activity.class, this,
                            Xpdl2Package.PROCESS__ACTIVITIES,
                            Xpdl2Package.ACTIVITY__FLOW_CONTAINER);
        }
        return activities;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Transition> getTransitions() {
        if (transitions == null) {
            transitions =
                    new EObjectContainmentWithInverseEList<Transition>(
                            Transition.class, this,
                            Xpdl2Package.PROCESS__TRANSITIONS,
                            Xpdl2Package.TRANSITION__FLOW_CONTAINER);
        }
        return transitions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isAdHoc() {
        return adHoc;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAdHoc(boolean newAdHoc) {
        boolean oldAdHoc = adHoc;
        adHoc = newAdHoc;
        boolean oldAdHocESet = adHocESet;
        adHocESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__AD_HOC, oldAdHoc, adHoc,
                    !oldAdHocESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetAdHoc() {
        boolean oldAdHoc = adHoc;
        boolean oldAdHocESet = adHocESet;
        adHoc = AD_HOC_EDEFAULT;
        adHocESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.PROCESS__AD_HOC, oldAdHoc, AD_HOC_EDEFAULT,
                    oldAdHocESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetAdHoc() {
        return adHocESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getAdHocCompletionCondition() {
        return adHocCompletionCondition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAdHocCompletionCondition(String newAdHocCompletionCondition) {
        String oldAdHocCompletionCondition = adHocCompletionCondition;
        adHocCompletionCondition = newAdHocCompletionCondition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION,
                    oldAdHocCompletionCondition, adHocCompletionCondition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AdHocOrderingType getAdHocOrdering() {
        return adHocOrdering;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAdHocOrdering(AdHocOrderingType newAdHocOrdering) {
        AdHocOrderingType oldAdHocOrdering = adHocOrdering;
        adHocOrdering =
                newAdHocOrdering == null ? AD_HOC_ORDERING_EDEFAULT
                        : newAdHocOrdering;
        boolean oldAdHocOrderingESet = adHocOrderingESet;
        adHocOrderingESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__AD_HOC_ORDERING, oldAdHocOrdering,
                    adHocOrdering, !oldAdHocOrderingESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetAdHocOrdering() {
        AdHocOrderingType oldAdHocOrdering = adHocOrdering;
        boolean oldAdHocOrderingESet = adHocOrderingESet;
        adHocOrdering = AD_HOC_ORDERING_EDEFAULT;
        adHocOrderingESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.PROCESS__AD_HOC_ORDERING, oldAdHocOrdering,
                    AD_HOC_ORDERING_EDEFAULT, oldAdHocOrderingESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetAdHocOrdering() {
        return adHocOrderingESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getDefaultStartActivityId() {
        return defaultStartActivityId;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setDefaultStartActivityId(String newDefaultStartActivityId) {
        String oldDefaultStartActivityId = defaultStartActivityId;
        defaultStartActivityId = newDefaultStartActivityId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID,
                    oldDefaultStartActivityId, defaultStartActivityId));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes =
                    new EObjectContainmentEList<ExtendedAttribute>(
                            ExtendedAttribute.class, this,
                            Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<FormalParameter> getFormalParameters() {
        if (formalParameters == null) {
            formalParameters =
                    new EObjectContainmentEList<FormalParameter>(
                            FormalParameter.class, this,
                            Xpdl2Package.PROCESS__FORMAL_PARAMETERS);
        }
        return formalParameters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Assignment getAssignments() {
        return assignments;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAssignments(Assignment newAssignments,
            NotificationChain msgs) {
        Assignment oldAssignments = assignments;
        assignments = newAssignments;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PROCESS__ASSIGNMENTS, oldAssignments,
                            newAssignments);
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
    @Override
    public void setAssignments(Assignment newAssignments) {
        if (newAssignments != assignments) {
            NotificationChain msgs = null;
            if (assignments != null)
                msgs =
                        ((InternalEObject) assignments).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__ASSIGNMENTS,
                                null,
                                msgs);
            if (newAssignments != null)
                msgs =
                        ((InternalEObject) newAssignments).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__ASSIGNMENTS,
                                null,
                                msgs);
            msgs = basicSetAssignments(newAssignments, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__ASSIGNMENTS, newAssignments,
                    newAssignments));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<DataField> getDataFields() {
        if (dataFields == null) {
            dataFields =
                    new EObjectContainmentEList<DataField>(DataField.class,
                            this, Xpdl2Package.PROCESS__DATA_FIELDS);
        }
        return dataFields;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Participant> getParticipants() {
        if (participants == null) {
            participants =
                    new EObjectContainmentEList<Participant>(Participant.class,
                            this, Xpdl2Package.PROCESS__PARTICIPANTS);
        }
        return participants;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<Application> getApplications() {
        if (applications == null) {
            applications =
                    new EObjectContainmentEList<Application>(Application.class,
                            this, Xpdl2Package.PROCESS__APPLICATIONS);
        }
        return applications;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements =
                    new BasicFeatureMap(this,
                            Xpdl2Package.PROCESS__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ProcessHeader getProcessHeader() {
        return processHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetProcessHeader(
            ProcessHeader newProcessHeader, NotificationChain msgs) {
        ProcessHeader oldProcessHeader = processHeader;
        processHeader = newProcessHeader;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PROCESS__PROCESS_HEADER,
                            oldProcessHeader, newProcessHeader);
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
    @Override
    public void setProcessHeader(ProcessHeader newProcessHeader) {
        if (newProcessHeader != processHeader) {
            NotificationChain msgs = null;
            if (processHeader != null)
                msgs =
                        ((InternalEObject) processHeader).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__PROCESS_HEADER,
                                null,
                                msgs);
            if (newProcessHeader != null)
                msgs =
                        ((InternalEObject) newProcessHeader).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__PROCESS_HEADER,
                                null,
                                msgs);
            msgs = basicSetProcessHeader(newProcessHeader, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__PROCESS_HEADER, newProcessHeader,
                    newProcessHeader));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RedefinableHeader getRedefinableHeader() {
        return redefinableHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRedefinableHeader(
            RedefinableHeader newRedefinableHeader, NotificationChain msgs) {
        RedefinableHeader oldRedefinableHeader = redefinableHeader;
        redefinableHeader = newRedefinableHeader;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PROCESS__REDEFINABLE_HEADER,
                            oldRedefinableHeader, newRedefinableHeader);
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
    @Override
    public void setRedefinableHeader(RedefinableHeader newRedefinableHeader) {
        if (newRedefinableHeader != redefinableHeader) {
            NotificationChain msgs = null;
            if (redefinableHeader != null)
                msgs =
                        ((InternalEObject) redefinableHeader)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PROCESS__REDEFINABLE_HEADER,
                                        null,
                                        msgs);
            if (newRedefinableHeader != null)
                msgs =
                        ((InternalEObject) newRedefinableHeader)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PROCESS__REDEFINABLE_HEADER,
                                        null,
                                        msgs);
            msgs = basicSetRedefinableHeader(newRedefinableHeader, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__REDEFINABLE_HEADER,
                    newRedefinableHeader, newRedefinableHeader));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<ActivitySet> getActivitySets() {
        if (activitySets == null) {
            activitySets =
                    new EObjectContainmentWithInverseEList<ActivitySet>(
                            ActivitySet.class, this,
                            Xpdl2Package.PROCESS__ACTIVITY_SETS,
                            Xpdl2Package.ACTIVITY_SET__PROCESS);
        }
        return activitySets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<PartnerLink> getPartnerLinks() {
        if (partnerLinks == null) {
            partnerLinks =
                    new EObjectContainmentEList<PartnerLink>(PartnerLink.class,
                            this, Xpdl2Package.PROCESS__PARTNER_LINKS);
        }
        return partnerLinks;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public com.tibco.xpd.xpdl2.Object getObject() {
        return object;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObject(
            com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PROCESS__OBJECT, oldObject, newObject);
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
    @Override
    public void setObject(com.tibco.xpd.xpdl2.Object newObject) {
        if (newObject != object) {
            NotificationChain msgs = null;
            if (object != null)
                msgs =
                        ((InternalEObject) object).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__OBJECT,
                                null,
                                msgs);
            if (newObject != null)
                msgs =
                        ((InternalEObject) newObject).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__OBJECT,
                                null,
                                msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject getExtensions() {
        return extensions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExtensions(EObject newExtensions,
            NotificationChain msgs) {
        EObject oldExtensions = extensions;
        extensions = newExtensions;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PROCESS__EXTENSIONS, oldExtensions,
                            newExtensions);
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
    @Override
    public void setExtensions(EObject newExtensions) {
        if (newExtensions != extensions) {
            NotificationChain msgs = null;
            if (extensions != null)
                msgs =
                        ((InternalEObject) extensions).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__EXTENSIONS,
                                null,
                                msgs);
            if (newExtensions != null)
                msgs =
                        ((InternalEObject) newExtensions).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PROCESS__EXTENSIONS,
                                null,
                                msgs);
            msgs = basicSetExtensions(newExtensions, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__EXTENSIONS, newExtensions,
                    newExtensions));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AccessLevelType getAccessLevel() {
        return accessLevel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAccessLevel(AccessLevelType newAccessLevel) {
        AccessLevelType oldAccessLevel = accessLevel;
        accessLevel =
                newAccessLevel == null ? ACCESS_LEVEL_EDEFAULT : newAccessLevel;
        boolean oldAccessLevelESet = accessLevelESet;
        accessLevelESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__ACCESS_LEVEL, oldAccessLevel,
                    accessLevel, !oldAccessLevelESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetAccessLevel() {
        AccessLevelType oldAccessLevel = accessLevel;
        boolean oldAccessLevelESet = accessLevelESet;
        accessLevel = ACCESS_LEVEL_EDEFAULT;
        accessLevelESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.PROCESS__ACCESS_LEVEL, oldAccessLevel,
                    ACCESS_LEVEL_EDEFAULT, oldAccessLevelESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetAccessLevel() {
        return accessLevelESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getDefaultStartActivitySetId() {
        return defaultStartActivitySetId;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setDefaultStartActivitySetId(String newDefaultStartActivitySetId) {
        String oldDefaultStartActivitySetId = defaultStartActivitySetId;
        defaultStartActivitySetId = newDefaultStartActivitySetId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_SET_ID,
                    oldDefaultStartActivitySetId, defaultStartActivitySetId));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isEnableInstanceCompensation() {
        return enableInstanceCompensation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setEnableInstanceCompensation(
            boolean newEnableInstanceCompensation) {
        boolean oldEnableInstanceCompensation = enableInstanceCompensation;
        enableInstanceCompensation = newEnableInstanceCompensation;
        boolean oldEnableInstanceCompensationESet =
                enableInstanceCompensationESet;
        enableInstanceCompensationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__ENABLE_INSTANCE_COMPENSATION,
                    oldEnableInstanceCompensation, enableInstanceCompensation,
                    !oldEnableInstanceCompensationESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetEnableInstanceCompensation() {
        boolean oldEnableInstanceCompensation = enableInstanceCompensation;
        boolean oldEnableInstanceCompensationESet =
                enableInstanceCompensationESet;
        enableInstanceCompensation = ENABLE_INSTANCE_COMPENSATION_EDEFAULT;
        enableInstanceCompensationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.PROCESS__ENABLE_INSTANCE_COMPENSATION,
                    oldEnableInstanceCompensation,
                    ENABLE_INSTANCE_COMPENSATION_EDEFAULT,
                    oldEnableInstanceCompensationESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetEnableInstanceCompensation() {
        return enableInstanceCompensationESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ProcessType getProcessType() {
        return processType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setProcessType(ProcessType newProcessType) {
        ProcessType oldProcessType = processType;
        processType =
                newProcessType == null ? PROCESS_TYPE_EDEFAULT : newProcessType;
        boolean oldProcessTypeESet = processTypeESet;
        processTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__PROCESS_TYPE, oldProcessType,
                    processType, !oldProcessTypeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetProcessType() {
        ProcessType oldProcessType = processType;
        boolean oldProcessTypeESet = processTypeESet;
        processType = PROCESS_TYPE_EDEFAULT;
        processTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.PROCESS__PROCESS_TYPE, oldProcessType,
                    PROCESS_TYPE_EDEFAULT, oldProcessTypeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetProcessType() {
        return processTypeESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public StatusType getStatus() {
        return status;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setStatus(StatusType newStatus) {
        StatusType oldStatus = status;
        status = newStatus == null ? STATUS_EDEFAULT : newStatus;
        boolean oldStatusESet = statusESet;
        statusESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__STATUS, oldStatus, status,
                    !oldStatusESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetStatus() {
        StatusType oldStatus = status;
        boolean oldStatusESet = statusESet;
        status = STATUS_EDEFAULT;
        statusESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.PROCESS__STATUS, oldStatus, STATUS_EDEFAULT,
                    oldStatusESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetStatus() {
        return statusESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSuppressJoinFailure() {
        return suppressJoinFailure;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setSuppressJoinFailure(boolean newSuppressJoinFailure) {
        boolean oldSuppressJoinFailure = suppressJoinFailure;
        suppressJoinFailure = newSuppressJoinFailure;
        boolean oldSuppressJoinFailureESet = suppressJoinFailureESet;
        suppressJoinFailureESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__SUPPRESS_JOIN_FAILURE,
                    oldSuppressJoinFailure, suppressJoinFailure,
                    !oldSuppressJoinFailureESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetSuppressJoinFailure() {
        boolean oldSuppressJoinFailure = suppressJoinFailure;
        boolean oldSuppressJoinFailureESet = suppressJoinFailureESet;
        suppressJoinFailure = SUPPRESS_JOIN_FAILURE_EDEFAULT;
        suppressJoinFailureESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.PROCESS__SUPPRESS_JOIN_FAILURE,
                    oldSuppressJoinFailure, SUPPRESS_JOIN_FAILURE_EDEFAULT,
                    oldSuppressJoinFailureESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetSuppressJoinFailure() {
        return suppressJoinFailureESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Activity getActivity(String id) {
        return (Activity) EMFSearchUtil.findInList(getActivities(),
                Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Transition getTransition(String id) {
        return (Transition) EMFSearchUtil.findInList(getTransitions(),
                Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public EList findStartActivities() {
        return Xpdl2InternalUtil.findStartActivity(this);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public ActivitySet getActivitySet(String id) {
        return (ActivitySet) EMFSearchUtil.findInList(getActivitySets(),
                Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public ProcessRelevantData getProcessData(String id) {
        ProcessRelevantData data =
                (ProcessRelevantData) EMFSearchUtil.findInList(getDataFields(),
                        Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                        id);
        if (data == null) {
            data =
                    (ProcessRelevantData) EMFSearchUtil
                            .findInList(getFormalParameters(),
                                    Xpdl2Package.eINSTANCE
                                            .getUniqueIdElement_Id(),
                                    id);
        }
        if (data == null) {
            data =
                    (ProcessRelevantData) EMFSearchUtil.findInList(getPackage()
                            .getDataFields(), Xpdl2Package.eINSTANCE
                            .getUniqueIdElement_Id(), id);
        }
        return data;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public EObject getOtherElement(String elementName) {
        return Xpdl2Operations.getOtherElement(this, elementName);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Participant getParticipant(String id) {
        Participant participant =
                (Participant) EMFSearchUtil.findInList(getParticipants(),
                        Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                        id);

        if (participant == null) {
            participant = getPackage().getParticipant(id);
        }

        return participant;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PROCESS__ACTIVITIES:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getActivities())
                    .basicAdd(otherEnd, msgs);
        case Xpdl2Package.PROCESS__TRANSITIONS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getTransitions())
                    .basicAdd(otherEnd, msgs);
        case Xpdl2Package.PROCESS__PACKAGE:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetPackage((com.tibco.xpd.xpdl2.Package) otherEnd, msgs);
        case Xpdl2Package.PROCESS__ACTIVITY_SETS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getActivitySets())
                    .basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PROCESS__ACTIVITIES:
            return ((InternalEList<?>) getActivities()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.PROCESS__TRANSITIONS:
            return ((InternalEList<?>) getTransitions()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.PROCESS__FORMAL_PARAMETERS:
            return ((InternalEList<?>) getFormalParameters())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.PROCESS__ASSIGNMENTS:
            return basicSetAssignments(null, msgs);
        case Xpdl2Package.PROCESS__DATA_FIELDS:
            return ((InternalEList<?>) getDataFields()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.PROCESS__PARTICIPANTS:
            return ((InternalEList<?>) getParticipants()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.PROCESS__APPLICATIONS:
            return ((InternalEList<?>) getApplications()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.PROCESS__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.PROCESS__PROCESS_HEADER:
            return basicSetProcessHeader(null, msgs);
        case Xpdl2Package.PROCESS__REDEFINABLE_HEADER:
            return basicSetRedefinableHeader(null, msgs);
        case Xpdl2Package.PROCESS__PARTNER_LINKS:
            return ((InternalEList<?>) getPartnerLinks()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.PROCESS__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.PROCESS__EXTENSIONS:
            return basicSetExtensions(null, msgs);
        case Xpdl2Package.PROCESS__PACKAGE:
            return basicSetPackage(null, msgs);
        case Xpdl2Package.PROCESS__ACTIVITY_SETS:
            return ((InternalEList<?>) getActivitySets()).basicRemove(otherEnd,
                    msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(
            NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.PROCESS__PACKAGE:
            return eInternalContainer().eInverseRemove(this,
                    Xpdl2Package.PACKAGE__PROCESSES,
                    com.tibco.xpd.xpdl2.Package.class,
                    msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.PROCESS__ACTIVITIES:
            return getActivities();
        case Xpdl2Package.PROCESS__TRANSITIONS:
            return getTransitions();
        case Xpdl2Package.PROCESS__AD_HOC:
            return isAdHoc();
        case Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION:
            return getAdHocCompletionCondition();
        case Xpdl2Package.PROCESS__AD_HOC_ORDERING:
            return getAdHocOrdering();
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID:
            return getDefaultStartActivityId();
        case Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.PROCESS__FORMAL_PARAMETERS:
            return getFormalParameters();
        case Xpdl2Package.PROCESS__ASSIGNMENTS:
            return getAssignments();
        case Xpdl2Package.PROCESS__DATA_FIELDS:
            return getDataFields();
        case Xpdl2Package.PROCESS__PARTICIPANTS:
            return getParticipants();
        case Xpdl2Package.PROCESS__APPLICATIONS:
            return getApplications();
        case Xpdl2Package.PROCESS__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.PROCESS__PROCESS_HEADER:
            return getProcessHeader();
        case Xpdl2Package.PROCESS__REDEFINABLE_HEADER:
            return getRedefinableHeader();
        case Xpdl2Package.PROCESS__PARTNER_LINKS:
            return getPartnerLinks();
        case Xpdl2Package.PROCESS__OBJECT:
            return getObject();
        case Xpdl2Package.PROCESS__EXTENSIONS:
            return getExtensions();
        case Xpdl2Package.PROCESS__ACCESS_LEVEL:
            return getAccessLevel();
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_SET_ID:
            return getDefaultStartActivitySetId();
        case Xpdl2Package.PROCESS__ENABLE_INSTANCE_COMPENSATION:
            return isEnableInstanceCompensation();
        case Xpdl2Package.PROCESS__PROCESS_TYPE:
            return getProcessType();
        case Xpdl2Package.PROCESS__STATUS:
            return getStatus();
        case Xpdl2Package.PROCESS__SUPPRESS_JOIN_FAILURE:
            return isSuppressJoinFailure();
        case Xpdl2Package.PROCESS__PACKAGE:
            return getPackage();
        case Xpdl2Package.PROCESS__ACTIVITY_SETS:
            return getActivitySets();
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
        case Xpdl2Package.PROCESS__ACTIVITIES:
            getActivities().clear();
            getActivities().addAll((Collection<? extends Activity>) newValue);
            return;
        case Xpdl2Package.PROCESS__TRANSITIONS:
            getTransitions().clear();
            getTransitions()
                    .addAll((Collection<? extends Transition>) newValue);
            return;
        case Xpdl2Package.PROCESS__AD_HOC:
            setAdHoc((Boolean) newValue);
            return;
        case Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION:
            setAdHocCompletionCondition((String) newValue);
            return;
        case Xpdl2Package.PROCESS__AD_HOC_ORDERING:
            setAdHocOrdering((AdHocOrderingType) newValue);
            return;
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID:
            setDefaultStartActivityId((String) newValue);
            return;
        case Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes()
                    .addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.PROCESS__FORMAL_PARAMETERS:
            getFormalParameters().clear();
            getFormalParameters()
                    .addAll((Collection<? extends FormalParameter>) newValue);
            return;
        case Xpdl2Package.PROCESS__ASSIGNMENTS:
            setAssignments((Assignment) newValue);
            return;
        case Xpdl2Package.PROCESS__DATA_FIELDS:
            getDataFields().clear();
            getDataFields().addAll((Collection<? extends DataField>) newValue);
            return;
        case Xpdl2Package.PROCESS__PARTICIPANTS:
            getParticipants().clear();
            getParticipants()
                    .addAll((Collection<? extends Participant>) newValue);
            return;
        case Xpdl2Package.PROCESS__APPLICATIONS:
            getApplications().clear();
            getApplications()
                    .addAll((Collection<? extends Application>) newValue);
            return;
        case Xpdl2Package.PROCESS__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.PROCESS__PROCESS_HEADER:
            setProcessHeader((ProcessHeader) newValue);
            return;
        case Xpdl2Package.PROCESS__REDEFINABLE_HEADER:
            setRedefinableHeader((RedefinableHeader) newValue);
            return;
        case Xpdl2Package.PROCESS__PARTNER_LINKS:
            getPartnerLinks().clear();
            getPartnerLinks()
                    .addAll((Collection<? extends PartnerLink>) newValue);
            return;
        case Xpdl2Package.PROCESS__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.PROCESS__EXTENSIONS:
            setExtensions((EObject) newValue);
            return;
        case Xpdl2Package.PROCESS__ACCESS_LEVEL:
            setAccessLevel((AccessLevelType) newValue);
            return;
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_SET_ID:
            setDefaultStartActivitySetId((String) newValue);
            return;
        case Xpdl2Package.PROCESS__ENABLE_INSTANCE_COMPENSATION:
            setEnableInstanceCompensation((Boolean) newValue);
            return;
        case Xpdl2Package.PROCESS__PROCESS_TYPE:
            setProcessType((ProcessType) newValue);
            return;
        case Xpdl2Package.PROCESS__STATUS:
            setStatus((StatusType) newValue);
            return;
        case Xpdl2Package.PROCESS__SUPPRESS_JOIN_FAILURE:
            setSuppressJoinFailure((Boolean) newValue);
            return;
        case Xpdl2Package.PROCESS__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) newValue);
            return;
        case Xpdl2Package.PROCESS__ACTIVITY_SETS:
            getActivitySets().clear();
            getActivitySets()
                    .addAll((Collection<? extends ActivitySet>) newValue);
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
        case Xpdl2Package.PROCESS__ACTIVITIES:
            getActivities().clear();
            return;
        case Xpdl2Package.PROCESS__TRANSITIONS:
            getTransitions().clear();
            return;
        case Xpdl2Package.PROCESS__AD_HOC:
            unsetAdHoc();
            return;
        case Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION:
            setAdHocCompletionCondition(AD_HOC_COMPLETION_CONDITION_EDEFAULT);
            return;
        case Xpdl2Package.PROCESS__AD_HOC_ORDERING:
            unsetAdHocOrdering();
            return;
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID:
            setDefaultStartActivityId(DEFAULT_START_ACTIVITY_ID_EDEFAULT);
            return;
        case Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.PROCESS__FORMAL_PARAMETERS:
            getFormalParameters().clear();
            return;
        case Xpdl2Package.PROCESS__ASSIGNMENTS:
            setAssignments((Assignment) null);
            return;
        case Xpdl2Package.PROCESS__DATA_FIELDS:
            getDataFields().clear();
            return;
        case Xpdl2Package.PROCESS__PARTICIPANTS:
            getParticipants().clear();
            return;
        case Xpdl2Package.PROCESS__APPLICATIONS:
            getApplications().clear();
            return;
        case Xpdl2Package.PROCESS__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.PROCESS__PROCESS_HEADER:
            setProcessHeader((ProcessHeader) null);
            return;
        case Xpdl2Package.PROCESS__REDEFINABLE_HEADER:
            setRedefinableHeader((RedefinableHeader) null);
            return;
        case Xpdl2Package.PROCESS__PARTNER_LINKS:
            getPartnerLinks().clear();
            return;
        case Xpdl2Package.PROCESS__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.PROCESS__EXTENSIONS:
            setExtensions((EObject) null);
            return;
        case Xpdl2Package.PROCESS__ACCESS_LEVEL:
            unsetAccessLevel();
            return;
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_SET_ID:
            setDefaultStartActivitySetId(DEFAULT_START_ACTIVITY_SET_ID_EDEFAULT);
            return;
        case Xpdl2Package.PROCESS__ENABLE_INSTANCE_COMPENSATION:
            unsetEnableInstanceCompensation();
            return;
        case Xpdl2Package.PROCESS__PROCESS_TYPE:
            unsetProcessType();
            return;
        case Xpdl2Package.PROCESS__STATUS:
            unsetStatus();
            return;
        case Xpdl2Package.PROCESS__SUPPRESS_JOIN_FAILURE:
            unsetSuppressJoinFailure();
            return;
        case Xpdl2Package.PROCESS__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) null);
            return;
        case Xpdl2Package.PROCESS__ACTIVITY_SETS:
            getActivitySets().clear();
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
        case Xpdl2Package.PROCESS__ACTIVITIES:
            return activities != null && !activities.isEmpty();
        case Xpdl2Package.PROCESS__TRANSITIONS:
            return transitions != null && !transitions.isEmpty();
        case Xpdl2Package.PROCESS__AD_HOC:
            return isSetAdHoc();
        case Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION:
            return AD_HOC_COMPLETION_CONDITION_EDEFAULT == null ? adHocCompletionCondition != null
                    : !AD_HOC_COMPLETION_CONDITION_EDEFAULT
                            .equals(adHocCompletionCondition);
        case Xpdl2Package.PROCESS__AD_HOC_ORDERING:
            return isSetAdHocOrdering();
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID:
            return DEFAULT_START_ACTIVITY_ID_EDEFAULT == null ? defaultStartActivityId != null
                    : !DEFAULT_START_ACTIVITY_ID_EDEFAULT
                            .equals(defaultStartActivityId);
        case Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.PROCESS__FORMAL_PARAMETERS:
            return formalParameters != null && !formalParameters.isEmpty();
        case Xpdl2Package.PROCESS__ASSIGNMENTS:
            return assignments != null;
        case Xpdl2Package.PROCESS__DATA_FIELDS:
            return dataFields != null && !dataFields.isEmpty();
        case Xpdl2Package.PROCESS__PARTICIPANTS:
            return participants != null && !participants.isEmpty();
        case Xpdl2Package.PROCESS__APPLICATIONS:
            return applications != null && !applications.isEmpty();
        case Xpdl2Package.PROCESS__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.PROCESS__PROCESS_HEADER:
            return processHeader != null;
        case Xpdl2Package.PROCESS__REDEFINABLE_HEADER:
            return redefinableHeader != null;
        case Xpdl2Package.PROCESS__PARTNER_LINKS:
            return partnerLinks != null && !partnerLinks.isEmpty();
        case Xpdl2Package.PROCESS__OBJECT:
            return object != null;
        case Xpdl2Package.PROCESS__EXTENSIONS:
            return extensions != null;
        case Xpdl2Package.PROCESS__ACCESS_LEVEL:
            return isSetAccessLevel();
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_SET_ID:
            return DEFAULT_START_ACTIVITY_SET_ID_EDEFAULT == null ? defaultStartActivitySetId != null
                    : !DEFAULT_START_ACTIVITY_SET_ID_EDEFAULT
                            .equals(defaultStartActivitySetId);
        case Xpdl2Package.PROCESS__ENABLE_INSTANCE_COMPENSATION:
            return isSetEnableInstanceCompensation();
        case Xpdl2Package.PROCESS__PROCESS_TYPE:
            return isSetProcessType();
        case Xpdl2Package.PROCESS__STATUS:
            return isSetStatus();
        case Xpdl2Package.PROCESS__SUPPRESS_JOIN_FAILURE:
            return isSetSuppressJoinFailure();
        case Xpdl2Package.PROCESS__PACKAGE:
            return getPackage() != null;
        case Xpdl2Package.PROCESS__ACTIVITY_SETS:
            return activitySets != null && !activitySets.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public DataField getDataField(String id) {
        return (DataField) EMFSearchUtil.findInList(getDataFields(),
                Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public com.tibco.xpd.xpdl2.Package getPackage() {
        EObject obj = eContainer();
        if (obj instanceof RESTServicesImpl) {
            return (Package) obj.eContainer().eContainer();
        } else {
            return (Package) obj;
        }

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPackage(
            com.tibco.xpd.xpdl2.Package newPackage, NotificationChain msgs) {
        msgs =
                eBasicSetContainer((InternalEObject) newPackage,
                        Xpdl2Package.PROCESS__PACKAGE,
                        msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setPackage(com.tibco.xpd.xpdl2.Package newPackage) {
        if (newPackage != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.PROCESS__PACKAGE && newPackage != null)) {
            if (EcoreUtil.isAncestor(this, newPackage))
                throw new IllegalArgumentException(
                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newPackage != null)
                msgs =
                        ((InternalEObject) newPackage).eInverseAdd(this,
                                Xpdl2Package.PACKAGE__PROCESSES,
                                com.tibco.xpd.xpdl2.Package.class,
                                msgs);
            msgs = basicSetPackage(newPackage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS__PACKAGE, newPackage, newPackage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == FlowContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__ACTIVITIES:
                return Xpdl2Package.FLOW_CONTAINER__ACTIVITIES;
            case Xpdl2Package.PROCESS__TRANSITIONS:
                return Xpdl2Package.FLOW_CONTAINER__TRANSITIONS;
            case Xpdl2Package.PROCESS__AD_HOC:
                return Xpdl2Package.FLOW_CONTAINER__AD_HOC;
            case Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION:
                return Xpdl2Package.FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION;
            case Xpdl2Package.PROCESS__AD_HOC_ORDERING:
                return Xpdl2Package.FLOW_CONTAINER__AD_HOC_ORDERING;
            case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID:
                return Xpdl2Package.FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID;
            default:
                return -1;
            }
        }
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == FormalParametersContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__FORMAL_PARAMETERS:
                return Xpdl2Package.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS;
            default:
                return -1;
            }
        }
        if (baseClass == AssigmentsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__ASSIGNMENTS:
                return Xpdl2Package.ASSIGMENTS_CONTAINER__ASSIGNMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == DataFieldsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__DATA_FIELDS:
                return Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS;
            default:
                return -1;
            }
        }
        if (baseClass == ParticipantsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__PARTICIPANTS:
                return Xpdl2Package.PARTICIPANTS_CONTAINER__PARTICIPANTS;
            default:
                return -1;
            }
        }
        if (baseClass == ApplicationsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__APPLICATIONS:
                return Xpdl2Package.APPLICATIONS_CONTAINER__APPLICATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.PROCESS__OTHER_ELEMENTS:
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
        if (baseClass == FlowContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.FLOW_CONTAINER__ACTIVITIES:
                return Xpdl2Package.PROCESS__ACTIVITIES;
            case Xpdl2Package.FLOW_CONTAINER__TRANSITIONS:
                return Xpdl2Package.PROCESS__TRANSITIONS;
            case Xpdl2Package.FLOW_CONTAINER__AD_HOC:
                return Xpdl2Package.PROCESS__AD_HOC;
            case Xpdl2Package.FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION:
                return Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION;
            case Xpdl2Package.FLOW_CONTAINER__AD_HOC_ORDERING:
                return Xpdl2Package.PROCESS__AD_HOC_ORDERING;
            case Xpdl2Package.FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID:
                return Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID;
            default:
                return -1;
            }
        }
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == FormalParametersContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS:
                return Xpdl2Package.PROCESS__FORMAL_PARAMETERS;
            default:
                return -1;
            }
        }
        if (baseClass == AssigmentsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.ASSIGMENTS_CONTAINER__ASSIGNMENTS:
                return Xpdl2Package.PROCESS__ASSIGNMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == DataFieldsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS:
                return Xpdl2Package.PROCESS__DATA_FIELDS;
            default:
                return -1;
            }
        }
        if (baseClass == ParticipantsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.PARTICIPANTS_CONTAINER__PARTICIPANTS:
                return Xpdl2Package.PROCESS__PARTICIPANTS;
            default:
                return -1;
            }
        }
        if (baseClass == ApplicationsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.APPLICATIONS_CONTAINER__APPLICATIONS:
                return Xpdl2Package.PROCESS__APPLICATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.PROCESS__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (adHoc: "); //$NON-NLS-1$
        if (adHocESet)
            result.append(adHoc);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", adHocCompletionCondition: "); //$NON-NLS-1$
        result.append(adHocCompletionCondition);
        result.append(", adHocOrdering: "); //$NON-NLS-1$
        if (adHocOrderingESet)
            result.append(adHocOrdering);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", defaultStartActivityId: "); //$NON-NLS-1$
        result.append(defaultStartActivityId);
        result.append(", otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", accessLevel: "); //$NON-NLS-1$
        if (accessLevelESet)
            result.append(accessLevel);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", defaultStartActivitySetId: "); //$NON-NLS-1$
        result.append(defaultStartActivitySetId);
        result.append(", enableInstanceCompensation: "); //$NON-NLS-1$
        if (enableInstanceCompensationESet)
            result.append(enableInstanceCompensation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", processType: "); //$NON-NLS-1$
        if (processTypeESet)
            result.append(processType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", status: "); //$NON-NLS-1$
        if (statusESet)
            result.append(status);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", suppressJoinFailure: "); //$NON-NLS-1$
        if (suppressJoinFailureESet)
            result.append(suppressJoinFailure);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

    @Override
    public EList getLocalFormalParameters() {
        return getFormalParameters();
    }

} // ProcessImpl
