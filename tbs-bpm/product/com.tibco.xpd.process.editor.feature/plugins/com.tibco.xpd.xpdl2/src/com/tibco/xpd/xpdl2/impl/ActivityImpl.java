/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FinishModeType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.IORules;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.InputSet;
import com.tibco.xpd.xpdl2.Limit;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.OutputSet;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.SimulationInformation;
import com.tibco.xpd.xpdl2.StartModeType;
import com.tibco.xpd.xpdl2.StatusType;
import com.tibco.xpd.xpdl2.Transaction;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TransitionRestriction;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getNodeGraphicsInfos <em>Node Graphics Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getDataFields <em>Data Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getLimit <em>Limit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getRoute <em>Route</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getBlockActivity <em>Block Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getTransaction <em>Transaction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getPerformer <em>Performer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getPerformers <em>Performers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getDeadline <em>Deadline</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getSimulationInformation <em>Simulation Information</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getTransitionRestrictions <em>Transition Restrictions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getInputSets <em>Input Sets</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getOutputSets <em>Output Sets</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getIoRules <em>Io Rules</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getLoop <em>Loop</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getAssignments <em>Assignments</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getFinishMode <em>Finish Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#isIsATransaction <em>Is ATransaction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#isStartActivity <em>Start Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getStartMode <em>Start Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getStartQuantity <em>Start Quantity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getFlowContainer <em>Flow Container</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#isIsForCompensation <em>Is For Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivityImpl#getCompletionQuantity <em>Completion Quantity</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActivityImpl extends NamedElementImpl implements Activity {
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
     * The cached value of the '{@link #getNodeGraphicsInfos() <em>Node Graphics Infos</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getNodeGraphicsInfos()
     * @generated
     * @ordered
     */
    protected EList<NodeGraphicsInfo> nodeGraphicsInfos;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

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
     * The cached value of the '{@link #getDataFields() <em>Data Fields</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDataFields()
     * @generated
     * @ordered
     */
    protected EList<DataField> dataFields;

    /**
     * The cached value of the '{@link #getLimit() <em>Limit</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLimit()
     * @generated
     * @ordered
     */
    protected Limit limit;

    /**
     * The cached value of the '{@link #getRoute() <em>Route</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRoute()
     * @generated
     * @ordered
     */
    protected Route route;

    /**
     * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected Implementation implementation;

    /**
     * The cached value of the '{@link #getBlockActivity() <em>Block Activity</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getBlockActivity()
     * @generated
     * @ordered
     */
    protected BlockActivity blockActivity;

    /**
     * The cached value of the '{@link #getEvent() <em>Event</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEvent()
     * @generated
     * @ordered
     */
    protected Event event;

    /**
     * The cached value of the '{@link #getTransaction() <em>Transaction</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTransaction()
     * @generated
     * @ordered
     */
    protected Transaction transaction;

    /**
     * The cached value of the '{@link #getPerformer() <em>Performer</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPerformer()
     * @generated
     * @ordered
     */
    protected Performer performer;

    /**
     * The cached value of the '{@link #getPerformers() <em>Performers</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPerformers()
     * @generated
     * @ordered
     */
    protected Performers performers;

    /**
     * The cached value of the '{@link #getPriority() <em>Priority</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected Priority priority;

    /**
     * The cached value of the '{@link #getDeadline() <em>Deadline</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDeadline()
     * @generated
     * @ordered
     */
    protected EList<Deadline> deadline;

    /**
     * The cached value of the '{@link #getSimulationInformation() <em>Simulation Information</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getSimulationInformation()
     * @generated
     * @ordered
     */
    protected SimulationInformation simulationInformation;

    /**
     * The cached value of the '{@link #getIcon() <em>Icon</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getIcon()
     * @generated
     * @ordered
     */
    protected Icon icon;

    /**
     * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDocumentation()
     * @generated
     * @ordered
     */
    protected Documentation documentation;

    /**
     * The cached value of the '{@link #getTransitionRestrictions() <em>Transition Restrictions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTransitionRestrictions()
     * @generated
     * @ordered
     */
    protected EList<TransitionRestriction> transitionRestrictions;

    /**
     * The cached value of the '{@link #getInputSets() <em>Input Sets</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getInputSets()
     * @generated
     * @ordered
     */
    protected EList<InputSet> inputSets;

    /**
     * The cached value of the '{@link #getOutputSets() <em>Output Sets</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getOutputSets()
     * @generated
     * @ordered
     */
    protected OutputSet outputSets;

    /**
     * The cached value of the '{@link #getIoRules() <em>Io Rules</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getIoRules()
     * @generated
     * @ordered
     */
    protected IORules ioRules;

    /**
     * The cached value of the '{@link #getLoop() <em>Loop</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLoop()
     * @generated
     * @ordered
     */
    protected Loop loop;

    /**
     * The cached value of the '{@link #getAssignments() <em>Assignments</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAssignments()
     * @generated
     * @ordered
     */
    protected EList<Assignment> assignments;

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
     * The default value of the '{@link #getFinishMode() <em>Finish Mode</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFinishMode()
     * @generated
     * @ordered
     */
    protected static final FinishModeType FINISH_MODE_EDEFAULT = FinishModeType.AUTOMATIC_LITERAL;

    /**
     * The cached value of the '{@link #getFinishMode() <em>Finish Mode</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFinishMode()
     * @generated
     * @ordered
     */
    protected FinishModeType finishMode = FINISH_MODE_EDEFAULT;

    /**
     * This is true if the Finish Mode attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean finishModeESet;

    /**
     * The default value of the '{@link #isIsATransaction() <em>Is ATransaction</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isIsATransaction()
     * @generated
     * @ordered
     */
    protected static final boolean IS_ATRANSACTION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsATransaction() <em>Is ATransaction</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isIsATransaction()
     * @generated
     * @ordered
     */
    protected boolean isATransaction = IS_ATRANSACTION_EDEFAULT;

    /**
     * This is true if the Is ATransaction attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean isATransactionESet;

    /**
     * The default value of the '{@link #isStartActivity() <em>Start Activity</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isStartActivity()
     * @generated
     * @ordered
     */
    protected static final boolean START_ACTIVITY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isStartActivity() <em>Start Activity</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isStartActivity()
     * @generated
     * @ordered
     */
    protected boolean startActivity = START_ACTIVITY_EDEFAULT;

    /**
     * This is true if the Start Activity attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean startActivityESet;

    /**
     * The default value of the '{@link #getStartMode() <em>Start Mode</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartMode()
     * @generated
     * @ordered
     */
    protected static final StartModeType START_MODE_EDEFAULT = StartModeType.AUTOMATIC_LITERAL;

    /**
     * The cached value of the '{@link #getStartMode() <em>Start Mode</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartMode()
     * @generated
     * @ordered
     */
    protected StartModeType startMode = START_MODE_EDEFAULT;

    /**
     * This is true if the Start Mode attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean startModeESet;

    /**
     * The default value of the '{@link #getStartQuantity() <em>Start Quantity</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartQuantity()
     * @generated
     * @ordered
     */
    protected static final BigInteger START_QUANTITY_EDEFAULT = new BigInteger("1"); //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getStartQuantity() <em>Start Quantity</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartQuantity()
     * @generated
     * @ordered
     */
    protected BigInteger startQuantity = START_QUANTITY_EDEFAULT;

    /**
     * This is true if the Start Quantity attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean startQuantityESet;

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
     * The cached value of the '{@link #getProcess() <em>Process</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getProcess()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Process process;

    /**
     * The default value of the '{@link #isIsForCompensation() <em>Is For Compensation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsForCompensation()
     * @generated
     * @ordered
     */
    protected static final boolean IS_FOR_COMPENSATION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsForCompensation() <em>Is For Compensation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsForCompensation()
     * @generated
     * @ordered
     */
    protected boolean isForCompensation = IS_FOR_COMPENSATION_EDEFAULT;

    /**
     * This is true if the Is For Compensation attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean isForCompensationESet;

    /**
     * The default value of the '{@link #getCompletionQuantity() <em>Completion Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCompletionQuantity()
     * @generated
     * @ordered
     */
    protected static final BigInteger COMPLETION_QUANTITY_EDEFAULT = new BigInteger("1"); //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCompletionQuantity() <em>Completion Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCompletionQuantity()
     * @generated
     * @ordered
     */
    protected BigInteger completionQuantity = COMPLETION_QUANTITY_EDEFAULT;

    /**
     * This is true if the Completion Quantity attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean completionQuantityESet;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ActivityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ACTIVITY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<NodeGraphicsInfo> getNodeGraphicsInfos() {
        if (nodeGraphicsInfos == null) {
            nodeGraphicsInfos = new EObjectContainmentEList<NodeGraphicsInfo>(NodeGraphicsInfo.class, this,
                    Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS);
        }
        return nodeGraphicsInfos;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
                    Xpdl2Package.ACTIVITY__DESCRIPTION, oldDescription, newDescription);
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
                msgs = ((InternalEObject) description)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__DESCRIPTION, null, msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__DESCRIPTION, null, msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__DESCRIPTION, newDescription,
                    newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.ACTIVITY__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Limit getLimit() {
        return limit;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLimit(Limit newLimit, NotificationChain msgs) {
        Limit oldLimit = limit;
        limit = newLimit;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__LIMIT, oldLimit, newLimit);
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
    public void setLimit(Limit newLimit) {
        if (newLimit != limit) {
            NotificationChain msgs = null;
            if (limit != null)
                msgs = ((InternalEObject) limit)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__LIMIT, null, msgs);
            if (newLimit != null)
                msgs = ((InternalEObject) newLimit)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__LIMIT, null, msgs);
            msgs = basicSetLimit(newLimit, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__LIMIT, newLimit, newLimit));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Route getRoute() {
        return route;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRoute(Route newRoute, NotificationChain msgs) {
        Route oldRoute = route;
        route = newRoute;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__ROUTE, oldRoute, newRoute);
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
    public void setRoute(Route newRoute) {
        if (newRoute != route) {
            NotificationChain msgs = null;
            if (route != null)
                msgs = ((InternalEObject) route)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__ROUTE, null, msgs);
            if (newRoute != null)
                msgs = ((InternalEObject) newRoute)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__ROUTE, null, msgs);
            msgs = basicSetRoute(newRoute, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__ROUTE, newRoute, newRoute));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Implementation getImplementation() {
        return implementation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetImplementation(Implementation newImplementation, NotificationChain msgs) {
        Implementation oldImplementation = implementation;
        implementation = newImplementation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__IMPLEMENTATION, oldImplementation, newImplementation);
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
    public void setImplementation(Implementation newImplementation) {
        if (newImplementation != implementation) {
            NotificationChain msgs = null;
            if (implementation != null)
                msgs = ((InternalEObject) implementation)
                        .eInverseRemove(this, Xpdl2Package.IMPLEMENTATION__ACTIVITY, Implementation.class, msgs);
            if (newImplementation != null)
                msgs = ((InternalEObject) newImplementation)
                        .eInverseAdd(this, Xpdl2Package.IMPLEMENTATION__ACTIVITY, Implementation.class, msgs);
            msgs = basicSetImplementation(newImplementation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__IMPLEMENTATION,
                    newImplementation, newImplementation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public BlockActivity getBlockActivity() {
        return blockActivity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBlockActivity(BlockActivity newBlockActivity, NotificationChain msgs) {
        BlockActivity oldBlockActivity = blockActivity;
        blockActivity = newBlockActivity;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY, oldBlockActivity, newBlockActivity);
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
    public void setBlockActivity(BlockActivity newBlockActivity) {
        if (newBlockActivity != blockActivity) {
            NotificationChain msgs = null;
            if (blockActivity != null)
                msgs = ((InternalEObject) blockActivity).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY,
                        null,
                        msgs);
            if (newBlockActivity != null)
                msgs = ((InternalEObject) newBlockActivity)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY, null, msgs);
            msgs = basicSetBlockActivity(newBlockActivity, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY,
                    newBlockActivity, newBlockActivity));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Event getEvent() {
        return event;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEvent(Event newEvent, NotificationChain msgs) {
        Event oldEvent = event;
        event = newEvent;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__EVENT, oldEvent, newEvent);
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
    public void setEvent(Event newEvent) {
        if (newEvent != event) {
            NotificationChain msgs = null;
            if (event != null)
                msgs = ((InternalEObject) event)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__EVENT, null, msgs);
            if (newEvent != null)
                msgs = ((InternalEObject) newEvent)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__EVENT, null, msgs);
            msgs = basicSetEvent(newEvent, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__EVENT, newEvent, newEvent));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTransaction(Transaction newTransaction, NotificationChain msgs) {
        Transaction oldTransaction = transaction;
        transaction = newTransaction;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__TRANSACTION, oldTransaction, newTransaction);
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
    public void setTransaction(Transaction newTransaction) {
        if (newTransaction != transaction) {
            NotificationChain msgs = null;
            if (transaction != null)
                msgs = ((InternalEObject) transaction)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__TRANSACTION, null, msgs);
            if (newTransaction != null)
                msgs = ((InternalEObject) newTransaction)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__TRANSACTION, null, msgs);
            msgs = basicSetTransaction(newTransaction, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__TRANSACTION, newTransaction,
                    newTransaction));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Performer getPerformer() {
        return performer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPerformer(Performer newPerformer, NotificationChain msgs) {
        Performer oldPerformer = performer;
        performer = newPerformer;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__PERFORMER, oldPerformer, newPerformer);
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
    public void setPerformer(Performer newPerformer) {
        if (newPerformer != performer) {
            NotificationChain msgs = null;
            if (performer != null)
                msgs = ((InternalEObject) performer)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__PERFORMER, null, msgs);
            if (newPerformer != null)
                msgs = ((InternalEObject) newPerformer)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__PERFORMER, null, msgs);
            msgs = basicSetPerformer(newPerformer, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__PERFORMER, newPerformer,
                    newPerformer));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Performers getPerformers() {
        return performers;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPerformers(Performers newPerformers, NotificationChain msgs) {
        Performers oldPerformers = performers;
        performers = newPerformers;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__PERFORMERS, oldPerformers, newPerformers);
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
    public void setPerformers(Performers newPerformers) {
        if (newPerformers != performers) {
            NotificationChain msgs = null;
            if (performers != null)
                msgs = ((InternalEObject) performers)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__PERFORMERS, null, msgs);
            if (newPerformers != null)
                msgs = ((InternalEObject) newPerformers)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__PERFORMERS, null, msgs);
            msgs = basicSetPerformers(newPerformers, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__PERFORMERS, newPerformers,
                    newPerformers));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPriority(Priority newPriority, NotificationChain msgs) {
        Priority oldPriority = priority;
        priority = newPriority;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__PRIORITY, oldPriority, newPriority);
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
    public void setPriority(Priority newPriority) {
        if (newPriority != priority) {
            NotificationChain msgs = null;
            if (priority != null)
                msgs = ((InternalEObject) priority)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__PRIORITY, null, msgs);
            if (newPriority != null)
                msgs = ((InternalEObject) newPriority)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__PRIORITY, null, msgs);
            msgs = basicSetPriority(newPriority, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__PRIORITY, newPriority,
                    newPriority));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Deadline> getDeadline() {
        if (deadline == null) {
            deadline = new EObjectContainmentEList<Deadline>(Deadline.class, this, Xpdl2Package.ACTIVITY__DEADLINE);
        }
        return deadline;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SimulationInformation getSimulationInformation() {
        return simulationInformation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSimulationInformation(SimulationInformation newSimulationInformation,
            NotificationChain msgs) {
        SimulationInformation oldSimulationInformation = simulationInformation;
        simulationInformation = newSimulationInformation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION, oldSimulationInformation, newSimulationInformation);
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
    public void setSimulationInformation(SimulationInformation newSimulationInformation) {
        if (newSimulationInformation != simulationInformation) {
            NotificationChain msgs = null;
            if (simulationInformation != null)
                msgs = ((InternalEObject) simulationInformation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION,
                        null,
                        msgs);
            if (newSimulationInformation != null)
                msgs = ((InternalEObject) newSimulationInformation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION,
                        null,
                        msgs);
            msgs = basicSetSimulationInformation(newSimulationInformation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION,
                    newSimulationInformation, newSimulationInformation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetIcon(Icon newIcon, NotificationChain msgs) {
        Icon oldIcon = icon;
        icon = newIcon;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__ICON, oldIcon, newIcon);
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
    public void setIcon(Icon newIcon) {
        if (newIcon != icon) {
            NotificationChain msgs = null;
            if (icon != null)
                msgs = ((InternalEObject) icon)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__ICON, null, msgs);
            if (newIcon != null)
                msgs = ((InternalEObject) newIcon)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__ICON, null, msgs);
            msgs = basicSetIcon(newIcon, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__ICON, newIcon, newIcon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Documentation getDocumentation() {
        return documentation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDocumentation(Documentation newDocumentation, NotificationChain msgs) {
        Documentation oldDocumentation = documentation;
        documentation = newDocumentation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__DOCUMENTATION, oldDocumentation, newDocumentation);
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
    public void setDocumentation(Documentation newDocumentation) {
        if (newDocumentation != documentation) {
            NotificationChain msgs = null;
            if (documentation != null)
                msgs = ((InternalEObject) documentation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__DOCUMENTATION,
                        null,
                        msgs);
            if (newDocumentation != null)
                msgs = ((InternalEObject) newDocumentation)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__DOCUMENTATION, null, msgs);
            msgs = basicSetDocumentation(newDocumentation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__DOCUMENTATION,
                    newDocumentation, newDocumentation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<TransitionRestriction> getTransitionRestrictions() {
        if (transitionRestrictions == null) {
            transitionRestrictions = new EObjectContainmentEList<TransitionRestriction>(TransitionRestriction.class,
                    this, Xpdl2Package.ACTIVITY__TRANSITION_RESTRICTIONS);
        }
        return transitionRestrictions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<DataField> getDataFields() {
        if (dataFields == null) {
            dataFields =
                    new EObjectContainmentEList<DataField>(DataField.class, this, Xpdl2Package.ACTIVITY__DATA_FIELDS);
        }
        return dataFields;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<InputSet> getInputSets() {
        if (inputSets == null) {
            inputSets = new EObjectContainmentEList<InputSet>(InputSet.class, this, Xpdl2Package.ACTIVITY__INPUT_SETS);
        }
        return inputSets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OutputSet getOutputSets() {
        return outputSets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOutputSets(OutputSet newOutputSets, NotificationChain msgs) {
        OutputSet oldOutputSets = outputSets;
        outputSets = newOutputSets;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__OUTPUT_SETS, oldOutputSets, newOutputSets);
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
    public void setOutputSets(OutputSet newOutputSets) {
        if (newOutputSets != outputSets) {
            NotificationChain msgs = null;
            if (outputSets != null)
                msgs = ((InternalEObject) outputSets)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__OUTPUT_SETS, null, msgs);
            if (newOutputSets != null)
                msgs = ((InternalEObject) newOutputSets)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__OUTPUT_SETS, null, msgs);
            msgs = basicSetOutputSets(newOutputSets, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__OUTPUT_SETS, newOutputSets,
                    newOutputSets));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IORules getIoRules() {
        return ioRules;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetIoRules(IORules newIoRules, NotificationChain msgs) {
        IORules oldIoRules = ioRules;
        ioRules = newIoRules;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__IO_RULES, oldIoRules, newIoRules);
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
    public void setIoRules(IORules newIoRules) {
        if (newIoRules != ioRules) {
            NotificationChain msgs = null;
            if (ioRules != null)
                msgs = ((InternalEObject) ioRules)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__IO_RULES, null, msgs);
            if (newIoRules != null)
                msgs = ((InternalEObject) newIoRules)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__IO_RULES, null, msgs);
            msgs = basicSetIoRules(newIoRules, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__IO_RULES, newIoRules,
                    newIoRules));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Loop getLoop() {
        return loop;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLoop(Loop newLoop, NotificationChain msgs) {
        Loop oldLoop = loop;
        loop = newLoop;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__LOOP, oldLoop, newLoop);
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
    public void setLoop(Loop newLoop) {
        if (newLoop != loop) {
            NotificationChain msgs = null;
            if (loop != null)
                msgs = ((InternalEObject) loop)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__LOOP, null, msgs);
            if (newLoop != null)
                msgs = ((InternalEObject) newLoop)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__LOOP, null, msgs);
            msgs = basicSetLoop(newLoop, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__LOOP, newLoop, newLoop));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Assignment> getAssignments() {
        if (assignments == null) {
            assignments =
                    new EObjectContainmentEList<Assignment>(Assignment.class, this, Xpdl2Package.ACTIVITY__ASSIGNMENTS);
        }
        return assignments;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Object getObject() {
        return object;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObject(com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__OBJECT, oldObject, newObject);
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
    public void setObject(com.tibco.xpd.xpdl2.Object newObject) {
        if (newObject != object) {
            NotificationChain msgs = null;
            if (object != null)
                msgs = ((InternalEObject) object)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__OBJECT, null, msgs);
            if (newObject != null)
                msgs = ((InternalEObject) newObject)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__OBJECT, null, msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EObject getExtensions() {
        return extensions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExtensions(EObject newExtensions, NotificationChain msgs) {
        EObject oldExtensions = extensions;
        extensions = newExtensions;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY__EXTENSIONS, oldExtensions, newExtensions);
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
    public void setExtensions(EObject newExtensions) {
        if (newExtensions != extensions) {
            NotificationChain msgs = null;
            if (extensions != null)
                msgs = ((InternalEObject) extensions)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__EXTENSIONS, null, msgs);
            if (newExtensions != null)
                msgs = ((InternalEObject) newExtensions)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__EXTENSIONS, null, msgs);
            msgs = basicSetExtensions(newExtensions, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__EXTENSIONS, newExtensions,
                    newExtensions));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FinishModeType getFinishMode() {
        return finishMode;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setFinishMode(FinishModeType newFinishMode) {
        FinishModeType oldFinishMode = finishMode;
        finishMode = newFinishMode == null ? FINISH_MODE_EDEFAULT : newFinishMode;
        boolean oldFinishModeESet = finishModeESet;
        finishModeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__FINISH_MODE, oldFinishMode,
                    finishMode, !oldFinishModeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetFinishMode() {
        FinishModeType oldFinishMode = finishMode;
        boolean oldFinishModeESet = finishModeESet;
        finishMode = FINISH_MODE_EDEFAULT;
        finishModeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__FINISH_MODE, oldFinishMode,
                    FINISH_MODE_EDEFAULT, oldFinishModeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetFinishMode() {
        return finishModeESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsATransaction() {
        return isATransaction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setIsATransaction(boolean newIsATransaction) {
        boolean oldIsATransaction = isATransaction;
        isATransaction = newIsATransaction;
        boolean oldIsATransactionESet = isATransactionESet;
        isATransactionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__IS_ATRANSACTION,
                    oldIsATransaction, isATransaction, !oldIsATransactionESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetIsATransaction() {
        boolean oldIsATransaction = isATransaction;
        boolean oldIsATransactionESet = isATransactionESet;
        isATransaction = IS_ATRANSACTION_EDEFAULT;
        isATransactionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__IS_ATRANSACTION,
                    oldIsATransaction, IS_ATRANSACTION_EDEFAULT, oldIsATransactionESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetIsATransaction() {
        return isATransactionESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isStartActivity() {
        return startActivity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setStartActivity(boolean newStartActivity) {
        boolean oldStartActivity = startActivity;
        startActivity = newStartActivity;
        boolean oldStartActivityESet = startActivityESet;
        startActivityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__START_ACTIVITY,
                    oldStartActivity, startActivity, !oldStartActivityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetStartActivity() {
        boolean oldStartActivity = startActivity;
        boolean oldStartActivityESet = startActivityESet;
        startActivity = START_ACTIVITY_EDEFAULT;
        startActivityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__START_ACTIVITY,
                    oldStartActivity, START_ACTIVITY_EDEFAULT, oldStartActivityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStartActivity() {
        return startActivityESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public StartModeType getStartMode() {
        return startMode;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setStartMode(StartModeType newStartMode) {
        StartModeType oldStartMode = startMode;
        startMode = newStartMode == null ? START_MODE_EDEFAULT : newStartMode;
        boolean oldStartModeESet = startModeESet;
        startModeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__START_MODE, oldStartMode,
                    startMode, !oldStartModeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetStartMode() {
        StartModeType oldStartMode = startMode;
        boolean oldStartModeESet = startModeESet;
        startMode = START_MODE_EDEFAULT;
        startModeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__START_MODE, oldStartMode,
                    START_MODE_EDEFAULT, oldStartModeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStartMode() {
        return startModeESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getStartQuantity() {
        return startQuantity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setStartQuantity(BigInteger newStartQuantity) {
        BigInteger oldStartQuantity = startQuantity;
        startQuantity = newStartQuantity;
        boolean oldStartQuantityESet = startQuantityESet;
        startQuantityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__START_QUANTITY,
                    oldStartQuantity, startQuantity, !oldStartQuantityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetStartQuantity() {
        BigInteger oldStartQuantity = startQuantity;
        boolean oldStartQuantityESet = startQuantityESet;
        startQuantity = START_QUANTITY_EDEFAULT;
        startQuantityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__START_QUANTITY,
                    oldStartQuantity, START_QUANTITY_EDEFAULT, oldStartQuantityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStartQuantity() {
        return startQuantityESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setStatus(StatusType newStatus) {
        StatusType oldStatus = status;
        status = newStatus == null ? STATUS_EDEFAULT : newStatus;
        boolean oldStatusESet = statusESet;
        statusESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__STATUS, oldStatus, status,
                    !oldStatusESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetStatus() {
        StatusType oldStatus = status;
        boolean oldStatusESet = statusESet;
        status = STATUS_EDEFAULT;
        statusESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__STATUS, oldStatus,
                    STATUS_EDEFAULT, oldStatusESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStatus() {
        return statusESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FlowContainer getFlowContainer() {
        if (eContainerFeatureID() != Xpdl2Package.ACTIVITY__FLOW_CONTAINER)
            return null;
        return (FlowContainer) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFlowContainer(FlowContainer newFlowContainer, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject) newFlowContainer, Xpdl2Package.ACTIVITY__FLOW_CONTAINER, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setFlowContainer(FlowContainer newFlowContainer) {
        if (newFlowContainer != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.ACTIVITY__FLOW_CONTAINER && newFlowContainer != null)) {
            if (EcoreUtil.isAncestor(this, newFlowContainer))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newFlowContainer != null)
                msgs = ((InternalEObject) newFlowContainer)
                        .eInverseAdd(this, Xpdl2Package.FLOW_CONTAINER__ACTIVITIES, FlowContainer.class, msgs);
            msgs = basicSetFlowContainer(newFlowContainer, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__FLOW_CONTAINER,
                    newFlowContainer, newFlowContainer));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
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
        if (getProcess() == null || getProcess().getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getProcess().getPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Target(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getOutgoingAssociations() {
        if (getProcess() == null || getProcess().getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getProcess().getPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Source(),
                getId());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getOutgoingTransitions() {
        if (getFlowContainer() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getFlowContainer().getTransitions(),
                Xpdl2Package.eINSTANCE.getTransition_From(),
                getId());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList getIncomingTransitions() {
        if (getFlowContainer() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getFlowContainer().getTransitions(),
                Xpdl2Package.eINSTANCE.getTransition_To(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <p>
     * IN old Processes Studio used to use Singleton Activity->Performer
     * element.
     * <p>
     * Now it has changed to use Activity->Performers sequence.
     * <p>
     * This convenience method will return a list with EITHER the singleton OR
     * the performers from sequence.
     * </p>
     * <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList getPerformerList() {

        EList returnPerfs = new BasicEList();

        Performer performer = getPerformer();
        if (performer != null) {
            returnPerfs.add(performer);

        } else if (performers != null) {
            EList perfs = performers.getPerformers();

            if (perfs != null) {
                for (Iterator iter = perfs.iterator(); iter.hasNext();) {
                    Performer p = (Performer) iter.next();

                    returnPerfs.add(p);
                }
            }
        }

        return returnPerfs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataField getDataField(String id) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
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
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ACTIVITY__IMPLEMENTATION:
            if (implementation != null)
                msgs = ((InternalEObject) implementation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.ACTIVITY__IMPLEMENTATION,
                        null,
                        msgs);
            return basicSetImplementation((Implementation) otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__FLOW_CONTAINER:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetFlowContainer((FlowContainer) otherEnd, msgs);
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
        case Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS:
            return ((InternalEList<?>) getNodeGraphicsInfos()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.ACTIVITY__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__DATA_FIELDS:
            return ((InternalEList<?>) getDataFields()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__LIMIT:
            return basicSetLimit(null, msgs);
        case Xpdl2Package.ACTIVITY__ROUTE:
            return basicSetRoute(null, msgs);
        case Xpdl2Package.ACTIVITY__IMPLEMENTATION:
            return basicSetImplementation(null, msgs);
        case Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY:
            return basicSetBlockActivity(null, msgs);
        case Xpdl2Package.ACTIVITY__EVENT:
            return basicSetEvent(null, msgs);
        case Xpdl2Package.ACTIVITY__TRANSACTION:
            return basicSetTransaction(null, msgs);
        case Xpdl2Package.ACTIVITY__PERFORMER:
            return basicSetPerformer(null, msgs);
        case Xpdl2Package.ACTIVITY__PERFORMERS:
            return basicSetPerformers(null, msgs);
        case Xpdl2Package.ACTIVITY__PRIORITY:
            return basicSetPriority(null, msgs);
        case Xpdl2Package.ACTIVITY__DEADLINE:
            return ((InternalEList<?>) getDeadline()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION:
            return basicSetSimulationInformation(null, msgs);
        case Xpdl2Package.ACTIVITY__ICON:
            return basicSetIcon(null, msgs);
        case Xpdl2Package.ACTIVITY__DOCUMENTATION:
            return basicSetDocumentation(null, msgs);
        case Xpdl2Package.ACTIVITY__TRANSITION_RESTRICTIONS:
            return ((InternalEList<?>) getTransitionRestrictions()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__INPUT_SETS:
            return ((InternalEList<?>) getInputSets()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__OUTPUT_SETS:
            return basicSetOutputSets(null, msgs);
        case Xpdl2Package.ACTIVITY__IO_RULES:
            return basicSetIoRules(null, msgs);
        case Xpdl2Package.ACTIVITY__LOOP:
            return basicSetLoop(null, msgs);
        case Xpdl2Package.ACTIVITY__ASSIGNMENTS:
            return ((InternalEList<?>) getAssignments()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.ACTIVITY__EXTENSIONS:
            return basicSetExtensions(null, msgs);
        case Xpdl2Package.ACTIVITY__FLOW_CONTAINER:
            return basicSetFlowContainer(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.ACTIVITY__FLOW_CONTAINER:
            return eInternalContainer()
                    .eInverseRemove(this, Xpdl2Package.FLOW_CONTAINER__ACTIVITIES, FlowContainer.class, msgs);
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
        case Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS:
            return getNodeGraphicsInfos();
        case Xpdl2Package.ACTIVITY__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.ACTIVITY__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.ACTIVITY__DATA_FIELDS:
            return getDataFields();
        case Xpdl2Package.ACTIVITY__LIMIT:
            return getLimit();
        case Xpdl2Package.ACTIVITY__ROUTE:
            return getRoute();
        case Xpdl2Package.ACTIVITY__IMPLEMENTATION:
            return getImplementation();
        case Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY:
            return getBlockActivity();
        case Xpdl2Package.ACTIVITY__EVENT:
            return getEvent();
        case Xpdl2Package.ACTIVITY__TRANSACTION:
            return getTransaction();
        case Xpdl2Package.ACTIVITY__PERFORMER:
            return getPerformer();
        case Xpdl2Package.ACTIVITY__PERFORMERS:
            return getPerformers();
        case Xpdl2Package.ACTIVITY__PRIORITY:
            return getPriority();
        case Xpdl2Package.ACTIVITY__DEADLINE:
            return getDeadline();
        case Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION:
            return getSimulationInformation();
        case Xpdl2Package.ACTIVITY__ICON:
            return getIcon();
        case Xpdl2Package.ACTIVITY__DOCUMENTATION:
            return getDocumentation();
        case Xpdl2Package.ACTIVITY__TRANSITION_RESTRICTIONS:
            return getTransitionRestrictions();
        case Xpdl2Package.ACTIVITY__INPUT_SETS:
            return getInputSets();
        case Xpdl2Package.ACTIVITY__OUTPUT_SETS:
            return getOutputSets();
        case Xpdl2Package.ACTIVITY__IO_RULES:
            return getIoRules();
        case Xpdl2Package.ACTIVITY__LOOP:
            return getLoop();
        case Xpdl2Package.ACTIVITY__ASSIGNMENTS:
            return getAssignments();
        case Xpdl2Package.ACTIVITY__OBJECT:
            return getObject();
        case Xpdl2Package.ACTIVITY__EXTENSIONS:
            return getExtensions();
        case Xpdl2Package.ACTIVITY__FINISH_MODE:
            return getFinishMode();
        case Xpdl2Package.ACTIVITY__IS_ATRANSACTION:
            return isIsATransaction();
        case Xpdl2Package.ACTIVITY__START_ACTIVITY:
            return isStartActivity();
        case Xpdl2Package.ACTIVITY__START_MODE:
            return getStartMode();
        case Xpdl2Package.ACTIVITY__START_QUANTITY:
            return getStartQuantity();
        case Xpdl2Package.ACTIVITY__STATUS:
            return getStatus();
        case Xpdl2Package.ACTIVITY__FLOW_CONTAINER:
            return getFlowContainer();
        case Xpdl2Package.ACTIVITY__PROCESS:
            if (resolve)
                return getProcess();
            return basicGetProcess();
        case Xpdl2Package.ACTIVITY__IS_FOR_COMPENSATION:
            return isIsForCompensation();
        case Xpdl2Package.ACTIVITY__COMPLETION_QUANTITY:
            return getCompletionQuantity();
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
        case Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            getNodeGraphicsInfos().addAll((Collection<? extends NodeGraphicsInfo>) newValue);
            return;
        case Xpdl2Package.ACTIVITY__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.ACTIVITY__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.ACTIVITY__DATA_FIELDS:
            getDataFields().clear();
            getDataFields().addAll((Collection<? extends DataField>) newValue);
            return;
        case Xpdl2Package.ACTIVITY__LIMIT:
            setLimit((Limit) newValue);
            return;
        case Xpdl2Package.ACTIVITY__ROUTE:
            setRoute((Route) newValue);
            return;
        case Xpdl2Package.ACTIVITY__IMPLEMENTATION:
            setImplementation((Implementation) newValue);
            return;
        case Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY:
            setBlockActivity((BlockActivity) newValue);
            return;
        case Xpdl2Package.ACTIVITY__EVENT:
            setEvent((Event) newValue);
            return;
        case Xpdl2Package.ACTIVITY__TRANSACTION:
            setTransaction((Transaction) newValue);
            return;
        case Xpdl2Package.ACTIVITY__PERFORMER:
            setPerformer((Performer) newValue);
            return;
        case Xpdl2Package.ACTIVITY__PERFORMERS:
            setPerformers((Performers) newValue);
            return;
        case Xpdl2Package.ACTIVITY__PRIORITY:
            setPriority((Priority) newValue);
            return;
        case Xpdl2Package.ACTIVITY__DEADLINE:
            getDeadline().clear();
            getDeadline().addAll((Collection<? extends Deadline>) newValue);
            return;
        case Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION:
            setSimulationInformation((SimulationInformation) newValue);
            return;
        case Xpdl2Package.ACTIVITY__ICON:
            setIcon((Icon) newValue);
            return;
        case Xpdl2Package.ACTIVITY__DOCUMENTATION:
            setDocumentation((Documentation) newValue);
            return;
        case Xpdl2Package.ACTIVITY__TRANSITION_RESTRICTIONS:
            getTransitionRestrictions().clear();
            getTransitionRestrictions().addAll((Collection<? extends TransitionRestriction>) newValue);
            return;
        case Xpdl2Package.ACTIVITY__INPUT_SETS:
            getInputSets().clear();
            getInputSets().addAll((Collection<? extends InputSet>) newValue);
            return;
        case Xpdl2Package.ACTIVITY__OUTPUT_SETS:
            setOutputSets((OutputSet) newValue);
            return;
        case Xpdl2Package.ACTIVITY__IO_RULES:
            setIoRules((IORules) newValue);
            return;
        case Xpdl2Package.ACTIVITY__LOOP:
            setLoop((Loop) newValue);
            return;
        case Xpdl2Package.ACTIVITY__ASSIGNMENTS:
            getAssignments().clear();
            getAssignments().addAll((Collection<? extends Assignment>) newValue);
            return;
        case Xpdl2Package.ACTIVITY__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.ACTIVITY__EXTENSIONS:
            setExtensions((EObject) newValue);
            return;
        case Xpdl2Package.ACTIVITY__FINISH_MODE:
            setFinishMode((FinishModeType) newValue);
            return;
        case Xpdl2Package.ACTIVITY__IS_ATRANSACTION:
            setIsATransaction((Boolean) newValue);
            return;
        case Xpdl2Package.ACTIVITY__START_ACTIVITY:
            setStartActivity((Boolean) newValue);
            return;
        case Xpdl2Package.ACTIVITY__START_MODE:
            setStartMode((StartModeType) newValue);
            return;
        case Xpdl2Package.ACTIVITY__START_QUANTITY:
            setStartQuantity((BigInteger) newValue);
            return;
        case Xpdl2Package.ACTIVITY__STATUS:
            setStatus((StatusType) newValue);
            return;
        case Xpdl2Package.ACTIVITY__FLOW_CONTAINER:
            setFlowContainer((FlowContainer) newValue);
            return;
        case Xpdl2Package.ACTIVITY__IS_FOR_COMPENSATION:
            setIsForCompensation((Boolean) newValue);
            return;
        case Xpdl2Package.ACTIVITY__COMPLETION_QUANTITY:
            setCompletionQuantity((BigInteger) newValue);
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
        case Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            return;
        case Xpdl2Package.ACTIVITY__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.ACTIVITY__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.ACTIVITY__DATA_FIELDS:
            getDataFields().clear();
            return;
        case Xpdl2Package.ACTIVITY__LIMIT:
            setLimit((Limit) null);
            return;
        case Xpdl2Package.ACTIVITY__ROUTE:
            setRoute((Route) null);
            return;
        case Xpdl2Package.ACTIVITY__IMPLEMENTATION:
            setImplementation((Implementation) null);
            return;
        case Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY:
            setBlockActivity((BlockActivity) null);
            return;
        case Xpdl2Package.ACTIVITY__EVENT:
            setEvent((Event) null);
            return;
        case Xpdl2Package.ACTIVITY__TRANSACTION:
            setTransaction((Transaction) null);
            return;
        case Xpdl2Package.ACTIVITY__PERFORMER:
            setPerformer((Performer) null);
            return;
        case Xpdl2Package.ACTIVITY__PERFORMERS:
            setPerformers((Performers) null);
            return;
        case Xpdl2Package.ACTIVITY__PRIORITY:
            setPriority((Priority) null);
            return;
        case Xpdl2Package.ACTIVITY__DEADLINE:
            getDeadline().clear();
            return;
        case Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION:
            setSimulationInformation((SimulationInformation) null);
            return;
        case Xpdl2Package.ACTIVITY__ICON:
            setIcon((Icon) null);
            return;
        case Xpdl2Package.ACTIVITY__DOCUMENTATION:
            setDocumentation((Documentation) null);
            return;
        case Xpdl2Package.ACTIVITY__TRANSITION_RESTRICTIONS:
            getTransitionRestrictions().clear();
            return;
        case Xpdl2Package.ACTIVITY__INPUT_SETS:
            getInputSets().clear();
            return;
        case Xpdl2Package.ACTIVITY__OUTPUT_SETS:
            setOutputSets((OutputSet) null);
            return;
        case Xpdl2Package.ACTIVITY__IO_RULES:
            setIoRules((IORules) null);
            return;
        case Xpdl2Package.ACTIVITY__LOOP:
            setLoop((Loop) null);
            return;
        case Xpdl2Package.ACTIVITY__ASSIGNMENTS:
            getAssignments().clear();
            return;
        case Xpdl2Package.ACTIVITY__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.ACTIVITY__EXTENSIONS:
            setExtensions((EObject) null);
            return;
        case Xpdl2Package.ACTIVITY__FINISH_MODE:
            unsetFinishMode();
            return;
        case Xpdl2Package.ACTIVITY__IS_ATRANSACTION:
            unsetIsATransaction();
            return;
        case Xpdl2Package.ACTIVITY__START_ACTIVITY:
            unsetStartActivity();
            return;
        case Xpdl2Package.ACTIVITY__START_MODE:
            unsetStartMode();
            return;
        case Xpdl2Package.ACTIVITY__START_QUANTITY:
            unsetStartQuantity();
            return;
        case Xpdl2Package.ACTIVITY__STATUS:
            unsetStatus();
            return;
        case Xpdl2Package.ACTIVITY__FLOW_CONTAINER:
            setFlowContainer((FlowContainer) null);
            return;
        case Xpdl2Package.ACTIVITY__IS_FOR_COMPENSATION:
            unsetIsForCompensation();
            return;
        case Xpdl2Package.ACTIVITY__COMPLETION_QUANTITY:
            unsetCompletionQuantity();
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
        case Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS:
            return nodeGraphicsInfos != null && !nodeGraphicsInfos.isEmpty();
        case Xpdl2Package.ACTIVITY__DESCRIPTION:
            return description != null;
        case Xpdl2Package.ACTIVITY__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.ACTIVITY__DATA_FIELDS:
            return dataFields != null && !dataFields.isEmpty();
        case Xpdl2Package.ACTIVITY__LIMIT:
            return limit != null;
        case Xpdl2Package.ACTIVITY__ROUTE:
            return route != null;
        case Xpdl2Package.ACTIVITY__IMPLEMENTATION:
            return implementation != null;
        case Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY:
            return blockActivity != null;
        case Xpdl2Package.ACTIVITY__EVENT:
            return event != null;
        case Xpdl2Package.ACTIVITY__TRANSACTION:
            return transaction != null;
        case Xpdl2Package.ACTIVITY__PERFORMER:
            return performer != null;
        case Xpdl2Package.ACTIVITY__PERFORMERS:
            return performers != null;
        case Xpdl2Package.ACTIVITY__PRIORITY:
            return priority != null;
        case Xpdl2Package.ACTIVITY__DEADLINE:
            return deadline != null && !deadline.isEmpty();
        case Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION:
            return simulationInformation != null;
        case Xpdl2Package.ACTIVITY__ICON:
            return icon != null;
        case Xpdl2Package.ACTIVITY__DOCUMENTATION:
            return documentation != null;
        case Xpdl2Package.ACTIVITY__TRANSITION_RESTRICTIONS:
            return transitionRestrictions != null && !transitionRestrictions.isEmpty();
        case Xpdl2Package.ACTIVITY__INPUT_SETS:
            return inputSets != null && !inputSets.isEmpty();
        case Xpdl2Package.ACTIVITY__OUTPUT_SETS:
            return outputSets != null;
        case Xpdl2Package.ACTIVITY__IO_RULES:
            return ioRules != null;
        case Xpdl2Package.ACTIVITY__LOOP:
            return loop != null;
        case Xpdl2Package.ACTIVITY__ASSIGNMENTS:
            return assignments != null && !assignments.isEmpty();
        case Xpdl2Package.ACTIVITY__OBJECT:
            return object != null;
        case Xpdl2Package.ACTIVITY__EXTENSIONS:
            return extensions != null;
        case Xpdl2Package.ACTIVITY__FINISH_MODE:
            return isSetFinishMode();
        case Xpdl2Package.ACTIVITY__IS_ATRANSACTION:
            return isSetIsATransaction();
        case Xpdl2Package.ACTIVITY__START_ACTIVITY:
            return isSetStartActivity();
        case Xpdl2Package.ACTIVITY__START_MODE:
            return isSetStartMode();
        case Xpdl2Package.ACTIVITY__START_QUANTITY:
            return isSetStartQuantity();
        case Xpdl2Package.ACTIVITY__STATUS:
            return isSetStatus();
        case Xpdl2Package.ACTIVITY__FLOW_CONTAINER:
            return getFlowContainer() != null;
        case Xpdl2Package.ACTIVITY__PROCESS:
            return process != null;
        case Xpdl2Package.ACTIVITY__IS_FOR_COMPENSATION:
            return isSetIsForCompensation();
        case Xpdl2Package.ACTIVITY__COMPLETION_QUANTITY:
            return isSetCompletionQuantity();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Process getProcess() {
        EObject eo = eContainer();
        while (!(eo instanceof Process) && eo != null) {
            eo = eo.eContainer();
        }
        return (Process) eo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Process basicGetProcess() {
        return process;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsForCompensation() {
        return isForCompensation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIsForCompensation(boolean newIsForCompensation) {
        boolean oldIsForCompensation = isForCompensation;
        isForCompensation = newIsForCompensation;
        boolean oldIsForCompensationESet = isForCompensationESet;
        isForCompensationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__IS_FOR_COMPENSATION,
                    oldIsForCompensation, isForCompensation, !oldIsForCompensationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetIsForCompensation() {
        boolean oldIsForCompensation = isForCompensation;
        boolean oldIsForCompensationESet = isForCompensationESet;
        isForCompensation = IS_FOR_COMPENSATION_EDEFAULT;
        isForCompensationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__IS_FOR_COMPENSATION,
                    oldIsForCompensation, IS_FOR_COMPENSATION_EDEFAULT, oldIsForCompensationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetIsForCompensation() {
        return isForCompensationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getCompletionQuantity() {
        return completionQuantity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCompletionQuantity(BigInteger newCompletionQuantity) {
        BigInteger oldCompletionQuantity = completionQuantity;
        completionQuantity = newCompletionQuantity;
        boolean oldCompletionQuantityESet = completionQuantityESet;
        completionQuantityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ACTIVITY__COMPLETION_QUANTITY,
                    oldCompletionQuantity, completionQuantity, !oldCompletionQuantityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCompletionQuantity() {
        BigInteger oldCompletionQuantity = completionQuantity;
        boolean oldCompletionQuantityESet = completionQuantityESet;
        completionQuantity = COMPLETION_QUANTITY_EDEFAULT;
        completionQuantityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ACTIVITY__COMPLETION_QUANTITY,
                    oldCompletionQuantity, COMPLETION_QUANTITY_EDEFAULT, oldCompletionQuantityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCompletionQuantity() {
        return completionQuantityESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == GraphicalNode.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ACTIVITY__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ACTIVITY__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == DataFieldsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ACTIVITY__DATA_FIELDS:
                return Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS;
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
                return Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == GraphicalNode.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION:
                return Xpdl2Package.ACTIVITY__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.ACTIVITY__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == DataFieldsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS:
                return Xpdl2Package.ACTIVITY__DATA_FIELDS;
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", finishMode: "); //$NON-NLS-1$
        if (finishModeESet)
            result.append(finishMode);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", isATransaction: "); //$NON-NLS-1$
        if (isATransactionESet)
            result.append(isATransaction);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", startActivity: "); //$NON-NLS-1$
        if (startActivityESet)
            result.append(startActivity);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", startMode: "); //$NON-NLS-1$
        if (startModeESet)
            result.append(startMode);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", startQuantity: "); //$NON-NLS-1$
        if (startQuantityESet)
            result.append(startQuantity);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", status: "); //$NON-NLS-1$
        if (statusESet)
            result.append(status);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", isForCompensation: "); //$NON-NLS-1$
        if (isForCompensationESet)
            result.append(isForCompensation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", completionQuantity: "); //$NON-NLS-1$
        if (completionQuantityESet)
            result.append(completionQuantity);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} // ActivityImpl
