/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import com.tibco.xpd.xpdl2.AccessLevelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.AdHocOrderingType;
import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.ApplicationType;
import com.tibco.xpd.xpdl2.ArrayType;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactInput;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.AssignTimeType;
import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.AssociationDirectionType;
import com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.BusinessRuleApplication;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Category;
import com.tibco.xpd.xpdl2.Codepage;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.ConformanceClass;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Cost;
import com.tibco.xpd.xpdl2.CostStructure;
import com.tibco.xpd.xpdl2.CostUnit;
import com.tibco.xpd.xpdl2.CountryKey;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.DeprecatedResultCompensation;
import com.tibco.xpd.xpdl2.DeprecatedTriggerRule;
import com.tibco.xpd.xpdl2.DeprecatedXorType;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.Duration;
import com.tibco.xpd.xpdl2.DurationUnitType;
import com.tibco.xpd.xpdl2.EjbApplication;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.EnumerationType;
import com.tibco.xpd.xpdl2.EnumerationValue;
import com.tibco.xpd.xpdl2.ExceptionName;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.ExecutionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FinishModeType;
import com.tibco.xpd.xpdl2.FormApplication;
import com.tibco.xpd.xpdl2.FormLayout;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.GatewayType;
import com.tibco.xpd.xpdl2.GraphConformanceType;
import com.tibco.xpd.xpdl2.HomeClass;
import com.tibco.xpd.xpdl2.IORules;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Input;
import com.tibco.xpd.xpdl2.InputSet;
import com.tibco.xpd.xpdl2.InstantiationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.IsArrayType;
import com.tibco.xpd.xpdl2.JndiName;
import com.tibco.xpd.xpdl2.Join;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.LayoutInfo;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Limit;
import com.tibco.xpd.xpdl2.ListType;
import com.tibco.xpd.xpdl2.Location;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Method;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ModificationDate;
import com.tibco.xpd.xpdl2.MyRole;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import java.lang.Object;
import com.tibco.xpd.xpdl2.OrientationType;
import com.tibco.xpd.xpdl2.Output;
import com.tibco.xpd.xpdl2.OutputSet;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.Page;
import com.tibco.xpd.xpdl2.Pages;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Partner;
import com.tibco.xpd.xpdl2.PartnerLink;
import com.tibco.xpd.xpdl2.PartnerLinkType;
import com.tibco.xpd.xpdl2.PartnerRole;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.PojoApplication;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.PriorityUnit;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.ProcessType;
import com.tibco.xpd.xpdl2.PropertyInput;
import com.tibco.xpd.xpdl2.PublicationStatusType;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.ResourceCosts;
import com.tibco.xpd.xpdl2.Responsible;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultMultiple;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Role;
import com.tibco.xpd.xpdl2.RoleType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Rule;
import com.tibco.xpd.xpdl2.RuleName;
import com.tibco.xpd.xpdl2.SHAPEType;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.Schema;
import com.tibco.xpd.xpdl2.Script;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.SimulationInformation;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.StartModeType;
import com.tibco.xpd.xpdl2.StatusType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskApplication;
import com.tibco.xpd.xpdl2.TaskManual;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskReference;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.TestTimeType;
import com.tibco.xpd.xpdl2.TimeEstimation;
import com.tibco.xpd.xpdl2.Transaction;
import com.tibco.xpd.xpdl2.TransactionMethodType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TransitionRef;
import com.tibco.xpd.xpdl2.TransitionRestriction;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerIntermediateMultiple;
import com.tibco.xpd.xpdl2.TriggerMultiple;
import com.tibco.xpd.xpdl2.TriggerResultCancel;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UnionType;
import com.tibco.xpd.xpdl2.ValidFrom;
import com.tibco.xpd.xpdl2.ValidTo;
import com.tibco.xpd.xpdl2.VendorExtension;
import com.tibco.xpd.xpdl2.VendorExtensions;
import com.tibco.xpd.xpdl2.ViewType;
import com.tibco.xpd.xpdl2.WaitingTime;
import com.tibco.xpd.xpdl2.WebServiceApplication;
import com.tibco.xpd.xpdl2.WebServiceFaultCatch;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.WorkingTime;
import com.tibco.xpd.xpdl2.XorType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.XsltApplication;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.xpdl2.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class Xpdl2FactoryImpl extends EFactoryImpl implements Xpdl2Factory {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public static Xpdl2Factory init() {
        try {
            Xpdl2Factory theXpdl2Factory =
                    (Xpdl2Factory) EPackage.Registry.INSTANCE
                            .getEFactory(Xpdl2Package.eNS_URI);
            if (theXpdl2Factory != null) {
                return theXpdl2Factory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new Xpdl2FactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public Xpdl2FactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case Xpdl2Package.ACTIVITY_SET:
            return createActivitySet();
        case Xpdl2Package.ACTIVITY:
            return createActivity();
        case Xpdl2Package.APPLICATION_TYPE:
            return createApplicationType();
        case Xpdl2Package.APPLICATION:
            return createApplication();
        case Xpdl2Package.ARRAY_TYPE:
            return createArrayType();
        case Xpdl2Package.ARTIFACT:
            return createArtifact();
        case Xpdl2Package.ARTIFACT_INPUT:
            return createArtifactInput();
        case Xpdl2Package.ASSIGNMENT:
            return createAssignment();
        case Xpdl2Package.ASSOCIATION:
            return createAssociation();
        case Xpdl2Package.BASIC_TYPE:
            return createBasicType();
        case Xpdl2Package.BLOCK_ACTIVITY:
            return createBlockActivity();
        case Xpdl2Package.BUSINESS_RULE_APPLICATION:
            return createBusinessRuleApplication();
        case Xpdl2Package.CATEGORY:
            return createCategory();
        case Xpdl2Package.CLASS:
            return createClass();
        case Xpdl2Package.CODEPAGE:
            return createCodepage();
        case Xpdl2Package.CONDITION:
            return createCondition();
        case Xpdl2Package.CONFORMANCE_CLASS:
            return createConformanceClass();
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO:
            return createConnectorGraphicsInfo();
        case Xpdl2Package.COORDINATES:
            return createCoordinates();
        case Xpdl2Package.COST:
            return createCost();
        case Xpdl2Package.COST_STRUCTURE:
            return createCostStructure();
        case Xpdl2Package.COST_UNIT:
            return createCostUnit();
        case Xpdl2Package.COUNTRY_KEY:
            return createCountryKey();
        case Xpdl2Package.DATA_FIELD:
            return createDataField();
        case Xpdl2Package.DATA_FIELDS_CONTAINER:
            return createDataFieldsContainer();
        case Xpdl2Package.DATA_MAPPING:
            return createDataMapping();
        case Xpdl2Package.DATA_OBJECT:
            return createDataObject();
        case Xpdl2Package.DEADLINE:
            return createDeadline();
        case Xpdl2Package.DECLARED_TYPE:
            return createDeclaredType();
        case Xpdl2Package.DEPRECATED_RESULT_COMPENSATION:
            return createDeprecatedResultCompensation();
        case Xpdl2Package.DEPRECATED_TRIGGER_RULE:
            return createDeprecatedTriggerRule();
        case Xpdl2Package.DESCRIPTION:
            return createDescription();
        case Xpdl2Package.DOCUMENTATION:
            return createDocumentation();
        case Xpdl2Package.DOCUMENT_ROOT:
            return createDocumentRoot();
        case Xpdl2Package.DURATION:
            return createDuration();
        case Xpdl2Package.EJB_APPLICATION:
            return createEjbApplication();
        case Xpdl2Package.END_EVENT:
            return createEndEvent();
        case Xpdl2Package.END_POINT:
            return createEndPoint();
        case Xpdl2Package.ENUMERATION_TYPE:
            return createEnumerationType();
        case Xpdl2Package.ENUMERATION_VALUE:
            return createEnumerationValue();
        case Xpdl2Package.EXCEPTION_NAME:
            return createExceptionName();
        case Xpdl2Package.EXPRESSION:
            return createExpression();
        case Xpdl2Package.EXTENDED_ATTRIBUTE:
            return createExtendedAttribute();
        case Xpdl2Package.EXTERNAL_PACKAGE:
            return createExternalPackage();
        case Xpdl2Package.EXTERNAL_REFERENCE:
            return createExternalReference();
        case Xpdl2Package.FORMAL_PARAMETER:
            return createFormalParameter();
        case Xpdl2Package.FORM_LAYOUT:
            return createFormLayout();
        case Xpdl2Package.FORM_APPLICATION:
            return createFormApplication();
        case Xpdl2Package.HOME_CLASS:
            return createHomeClass();
        case Xpdl2Package.ICON:
            return createIcon();
        case Xpdl2Package.INPUT_SET:
            return createInputSet();
        case Xpdl2Package.INPUT:
            return createInput();
        case Xpdl2Package.INTERMEDIATE_EVENT:
            return createIntermediateEvent();
        case Xpdl2Package.IO_RULES:
            return createIORules();
        case Xpdl2Package.JNDI_NAME:
            return createJndiName();
        case Xpdl2Package.JOIN:
            return createJoin();
        case Xpdl2Package.LAYOUT_INFO:
            return createLayoutInfo();
        case Xpdl2Package.LANE:
            return createLane();
        case Xpdl2Package.LENGTH:
            return createLength();
        case Xpdl2Package.LIMIT:
            return createLimit();
        case Xpdl2Package.LIST_TYPE:
            return createListType();
        case Xpdl2Package.LOCATION:
            return createLocation();
        case Xpdl2Package.LOOP_MULTI_INSTANCE:
            return createLoopMultiInstance();
        case Xpdl2Package.LOOP_STANDARD:
            return createLoopStandard();
        case Xpdl2Package.LOOP:
            return createLoop();
        case Xpdl2Package.MEMBER:
            return createMember();
        case Xpdl2Package.MESSAGE_FLOW:
            return createMessageFlow();
        case Xpdl2Package.MESSAGE:
            return createMessage();
        case Xpdl2Package.METHOD:
            return createMethod();
        case Xpdl2Package.MODIFICATION_DATE:
            return createModificationDate();
        case Xpdl2Package.MY_ROLE:
            return createMyRole();
        case Xpdl2Package.NODE_GRAPHICS_INFO:
            return createNodeGraphicsInfo();
        case Xpdl2Package.NO:
            return createNo();
        case Xpdl2Package.OBJECT:
            return createObject();
        case Xpdl2Package.OUTPUT_SET:
            return createOutputSet();
        case Xpdl2Package.OUTPUT:
            return createOutput();
        case Xpdl2Package.PACKAGE_HEADER:
            return createPackageHeader();
        case Xpdl2Package.PACKAGE:
            return createPackage();
        case Xpdl2Package.PAGE:
            return createPage();
        case Xpdl2Package.PAGES:
            return createPages();
        case Xpdl2Package.PARTICIPANT:
            return createParticipant();
        case Xpdl2Package.PARTICIPANT_TYPE_ELEM:
            return createParticipantTypeElem();
        case Xpdl2Package.PARTNER_LINK:
            return createPartnerLink();
        case Xpdl2Package.PARTNER_LINK_TYPE:
            return createPartnerLinkType();
        case Xpdl2Package.PARTNER_ROLE:
            return createPartnerRole();
        case Xpdl2Package.PARTNER:
            return createPartner();
        case Xpdl2Package.PERFORMER:
            return createPerformer();
        case Xpdl2Package.PERFORMERS:
            return createPerformers();
        case Xpdl2Package.POJO_APPLICATION:
            return createPojoApplication();
        case Xpdl2Package.POOL:
            return createPool();
        case Xpdl2Package.PRECISION:
            return createPrecision();
        case Xpdl2Package.PRIORITY:
            return createPriority();
        case Xpdl2Package.PRIORITY_UNIT:
            return createPriorityUnit();
        case Xpdl2Package.PROCESS_HEADER:
            return createProcessHeader();
        case Xpdl2Package.PROCESS:
            return createProcess();
        case Xpdl2Package.PROPERTY_INPUT:
            return createPropertyInput();
        case Xpdl2Package.RECORD_TYPE:
            return createRecordType();
        case Xpdl2Package.REDEFINABLE_HEADER:
            return createRedefinableHeader();
        case Xpdl2Package.REFERENCE:
            return createReference();
        case Xpdl2Package.RESOURCE_COSTS:
            return createResourceCosts();
        case Xpdl2Package.RESPONSIBLE:
            return createResponsible();
        case Xpdl2Package.RESULT_ERROR:
            return createResultError();
        case Xpdl2Package.RESULT_MULTIPLE:
            return createResultMultiple();
        case Xpdl2Package.ROLE:
            return createRole();
        case Xpdl2Package.ROUTE:
            return createRoute();
        case Xpdl2Package.RULE_NAME:
            return createRuleName();
        case Xpdl2Package.RULE:
            return createRule();
        case Xpdl2Package.SCALE:
            return createScale();
        case Xpdl2Package.SCHEMA:
            return createSchema();
        case Xpdl2Package.SCRIPT:
            return createScript();
        case Xpdl2Package.SERVICE:
            return createService();
        case Xpdl2Package.SIMULATION_INFORMATION:
            return createSimulationInformation();
        case Xpdl2Package.SPLIT:
            return createSplit();
        case Xpdl2Package.START_EVENT:
            return createStartEvent();
        case Xpdl2Package.SUB_FLOW:
            return createSubFlow();
        case Xpdl2Package.TASK_APPLICATION:
            return createTaskApplication();
        case Xpdl2Package.TASK_MANUAL:
            return createTaskManual();
        case Xpdl2Package.TASK_RECEIVE:
            return createTaskReceive();
        case Xpdl2Package.TASK_REFERENCE:
            return createTaskReference();
        case Xpdl2Package.TASK_SCRIPT:
            return createTaskScript();
        case Xpdl2Package.TASK_SEND:
            return createTaskSend();
        case Xpdl2Package.TASK_SERVICE:
            return createTaskService();
        case Xpdl2Package.TASK:
            return createTask();
        case Xpdl2Package.TASK_USER:
            return createTaskUser();
        case Xpdl2Package.TIME_ESTIMATION:
            return createTimeEstimation();
        case Xpdl2Package.TRANSACTION:
            return createTransaction();
        case Xpdl2Package.TRANSITION_REF:
            return createTransitionRef();
        case Xpdl2Package.TRANSITION_RESTRICTION:
            return createTransitionRestriction();
        case Xpdl2Package.TRANSITION:
            return createTransition();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE:
            return createTriggerIntermediateMultiple();
        case Xpdl2Package.TRIGGER_MULTIPLE:
            return createTriggerMultiple();
        case Xpdl2Package.TRIGGER_RESULT_CANCEL:
            return createTriggerResultCancel();
        case Xpdl2Package.TRIGGER_RESULT_COMPENSATION:
            return createTriggerResultCompensation();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL:
            return createTriggerResultSignal();
        case Xpdl2Package.TRIGGER_RESULT_LINK:
            return createTriggerResultLink();
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE:
            return createTriggerResultMessage();
        case Xpdl2Package.TRIGGER_CONDITIONAL:
            return createTriggerConditional();
        case Xpdl2Package.TRIGGER_TIMER:
            return createTriggerTimer();
        case Xpdl2Package.TYPE_DECLARATION:
            return createTypeDeclaration();
        case Xpdl2Package.UNION_TYPE:
            return createUnionType();
        case Xpdl2Package.VALID_FROM:
            return createValidFrom();
        case Xpdl2Package.VALID_TO:
            return createValidTo();
        case Xpdl2Package.VENDOR_EXTENSIONS:
            return createVendorExtensions();
        case Xpdl2Package.VENDOR_EXTENSION:
            return createVendorExtension();
        case Xpdl2Package.WAITING_TIME:
            return createWaitingTime();
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH:
            return createWebServiceFaultCatch();
        case Xpdl2Package.WEB_SERVICE_OPERATION:
            return createWebServiceOperation();
        case Xpdl2Package.WEB_SERVICE_APPLICATION:
            return createWebServiceApplication();
        case Xpdl2Package.WORKING_TIME:
            return createWorkingTime();
        case Xpdl2Package.XSLT_APPLICATION:
            return createXsltApplication();
        default:
            throw new IllegalArgumentException(
                    "The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case Xpdl2Package.ACCESS_LEVEL_TYPE:
            return createAccessLevelTypeFromString(eDataType, initialValue);
        case Xpdl2Package.AD_HOC_ORDERING_TYPE:
            return createAdHocOrderingTypeFromString(eDataType, initialValue);
        case Xpdl2Package.ARTIFACT_TYPE:
            return createArtifactTypeFromString(eDataType, initialValue);
        case Xpdl2Package.ASSIGN_TIME_TYPE:
            return createAssignTimeTypeFromString(eDataType, initialValue);
        case Xpdl2Package.ASSOCIATION_DIRECTION_TYPE:
            return createAssociationDirectionTypeFromString(eDataType,
                    initialValue);
        case Xpdl2Package.BASIC_TYPE_TYPE:
            return createBasicTypeTypeFromString(eDataType, initialValue);
        case Xpdl2Package.BPMN_MODEL_PORTABILITY_CONFORMANCE:
            return createBPMNModelPortabilityConformanceFromString(eDataType,
                    initialValue);
        case Xpdl2Package.CATCH_THROW:
            return createCatchThrowFromString(eDataType, initialValue);
        case Xpdl2Package.CONDITION_TYPE:
            return createConditionTypeFromString(eDataType, initialValue);
        case Xpdl2Package.DIRECTION_TYPE:
            return createDirectionTypeFromString(eDataType, initialValue);
        case Xpdl2Package.DURATION_UNIT_TYPE:
            return createDurationUnitTypeFromString(eDataType, initialValue);
        case Xpdl2Package.END_POINT_TYPE_TYPE:
            return createEndPointTypeTypeFromString(eDataType, initialValue);
        case Xpdl2Package.EXCLUSIVE_TYPE:
            return createExclusiveTypeFromString(eDataType, initialValue);
        case Xpdl2Package.EXECUTION_TYPE:
            return createExecutionTypeFromString(eDataType, initialValue);
        case Xpdl2Package.FINISH_MODE_TYPE:
            return createFinishModeTypeFromString(eDataType, initialValue);
        case Xpdl2Package.JOIN_SPLIT_TYPE:
            return createJoinSplitTypeFromString(eDataType, initialValue);
        case Xpdl2Package.GATEWAY_TYPE:
            return createGatewayTypeFromString(eDataType, initialValue);
        case Xpdl2Package.GRAPH_CONFORMANCE_TYPE:
            return createGraphConformanceTypeFromString(eDataType, initialValue);
        case Xpdl2Package.IMPLEMENTATION_TYPE:
            return createImplementationTypeFromString(eDataType, initialValue);
        case Xpdl2Package.INSTANTIATION_TYPE:
            return createInstantiationTypeFromString(eDataType, initialValue);
        case Xpdl2Package.IS_ARRAY_TYPE:
            return createIsArrayTypeFromString(eDataType, initialValue);
        case Xpdl2Package.LOOP_TYPE:
            return createLoopTypeFromString(eDataType, initialValue);
        case Xpdl2Package.MI_FLOW_CONDITION_TYPE:
            return createMIFlowConditionTypeFromString(eDataType, initialValue);
        case Xpdl2Package.MI_ORDERING_TYPE:
            return createMIOrderingTypeFromString(eDataType, initialValue);
        case Xpdl2Package.MODE_TYPE:
            return createModeTypeFromString(eDataType, initialValue);
        case Xpdl2Package.ORIENTATION_TYPE:
            return createOrientationTypeFromString(eDataType, initialValue);
        case Xpdl2Package.PARTICIPANT_TYPE:
            return createParticipantTypeFromString(eDataType, initialValue);
        case Xpdl2Package.PROCESS_TYPE:
            return createProcessTypeFromString(eDataType, initialValue);
        case Xpdl2Package.PUBLICATION_STATUS_TYPE:
            return createPublicationStatusTypeFromString(eDataType,
                    initialValue);
        case Xpdl2Package.RESULT_TYPE:
            return createResultTypeFromString(eDataType, initialValue);
        case Xpdl2Package.ROLE_TYPE:
            return createRoleTypeFromString(eDataType, initialValue);
        case Xpdl2Package.SHAPE_TYPE:
            return createSHAPETypeFromString(eDataType, initialValue);
        case Xpdl2Package.START_MODE_TYPE:
            return createStartModeTypeFromString(eDataType, initialValue);
        case Xpdl2Package.STATUS_TYPE:
            return createStatusTypeFromString(eDataType, initialValue);
        case Xpdl2Package.TEST_TIME_TYPE:
            return createTestTimeTypeFromString(eDataType, initialValue);
        case Xpdl2Package.TRANSACTION_METHOD_TYPE:
            return createTransactionMethodTypeFromString(eDataType,
                    initialValue);
        case Xpdl2Package.TRIGGER_TYPE:
            return createTriggerTypeFromString(eDataType, initialValue);
        case Xpdl2Package.VIEW_TYPE:
            return createViewTypeFromString(eDataType, initialValue);
        case Xpdl2Package.DEPRECATED_XOR_TYPE:
            return createDeprecatedXorTypeFromString(eDataType, initialValue);
        case Xpdl2Package.ACCESS_LEVEL_TYPE_OBJECT:
            return createAccessLevelTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.AD_HOC_ORDERING_TYPE_OBJECT:
            return createAdHocOrderingTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.ARTIFACT_TYPE_OBJECT:
            return createArtifactTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.ASSIGN_TIME_TYPE_OBJECT:
            return createAssignTimeTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.ASSOCIATION_DIRECTION_TYPE_OBJECT:
            return createAssociationDirectionTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.DIRECTION_TYPE_OBJECT:
            return createDirectionTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.DURATION_UNIT_TYPE_OBJECT:
            return createDurationUnitTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.END_POINT_TYPE_TYPE_OBJECT:
            return createEndPointTypeTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.EXECUTION_TYPE_OBJECT:
            return createExecutionTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.FINISH_MODE_TYPE_OBJECT:
            return createFinishModeTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.GATEWAY_TYPE_OBJECT:
            return createGatewayTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.GRAPH_CONFORMANCE_TYPE_OBJECT:
            return createGraphConformanceTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.ID_REFERENCE_STRING:
            return createIdReferenceStringFromString(eDataType, initialValue);
        case Xpdl2Package.IMPLEMENTATION_TYPE_OBJECT:
            return createImplementationTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.INSTANTIATION_TYPE_OBJECT:
            return createInstantiationTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.IS_ARRAY_TYPE_OBJECT:
            return createIsArrayTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.LOOP_TYPE_OBJECT:
            return createLoopTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.MI_FLOW_CONDITION_TYPE_OBJECT:
            return createMIFlowConditionTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.MI_ORDERING_TYPE_OBJECT:
            return createMIOrderingTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.MODE_TYPE_OBJECT:
            return createModeTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.ORIENTATION_TYPE_OBJECT:
            return createOrientationTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.PROCESS_TYPE_OBJECT:
            return createProcessTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.PUBLICATION_STATUS_TYPE_OBJECT:
            return createPublicationStatusTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.RESULT_TYPE_OBJECT:
            return createResultTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.ROLE_TYPE_OBJECT:
            return createRoleTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.SHAPE_TYPE_OBJECT:
            return createSHAPETypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.START_MODE_TYPE_OBJECT:
            return createStartModeTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.STATUS_TYPE_OBJECT:
            return createStatusTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.TEST_TIME_TYPE_OBJECT:
            return createTestTimeTypeObjectFromString(eDataType, initialValue);
        case Xpdl2Package.TRANSACTION_METHOD_TYPE_OBJECT:
            return createTransactionMethodTypeObjectFromString(eDataType,
                    initialValue);
        case Xpdl2Package.TRIGGER_TYPE_OBJECT:
            return createTriggerTypeObjectFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case Xpdl2Package.ACCESS_LEVEL_TYPE:
            return convertAccessLevelTypeToString(eDataType, instanceValue);
        case Xpdl2Package.AD_HOC_ORDERING_TYPE:
            return convertAdHocOrderingTypeToString(eDataType, instanceValue);
        case Xpdl2Package.ARTIFACT_TYPE:
            return convertArtifactTypeToString(eDataType, instanceValue);
        case Xpdl2Package.ASSIGN_TIME_TYPE:
            return convertAssignTimeTypeToString(eDataType, instanceValue);
        case Xpdl2Package.ASSOCIATION_DIRECTION_TYPE:
            return convertAssociationDirectionTypeToString(eDataType,
                    instanceValue);
        case Xpdl2Package.BASIC_TYPE_TYPE:
            return convertBasicTypeTypeToString(eDataType, instanceValue);
        case Xpdl2Package.BPMN_MODEL_PORTABILITY_CONFORMANCE:
            return convertBPMNModelPortabilityConformanceToString(eDataType,
                    instanceValue);
        case Xpdl2Package.CATCH_THROW:
            return convertCatchThrowToString(eDataType, instanceValue);
        case Xpdl2Package.CONDITION_TYPE:
            return convertConditionTypeToString(eDataType, instanceValue);
        case Xpdl2Package.DIRECTION_TYPE:
            return convertDirectionTypeToString(eDataType, instanceValue);
        case Xpdl2Package.DURATION_UNIT_TYPE:
            return convertDurationUnitTypeToString(eDataType, instanceValue);
        case Xpdl2Package.END_POINT_TYPE_TYPE:
            return convertEndPointTypeTypeToString(eDataType, instanceValue);
        case Xpdl2Package.EXCLUSIVE_TYPE:
            return convertExclusiveTypeToString(eDataType, instanceValue);
        case Xpdl2Package.EXECUTION_TYPE:
            return convertExecutionTypeToString(eDataType, instanceValue);
        case Xpdl2Package.FINISH_MODE_TYPE:
            return convertFinishModeTypeToString(eDataType, instanceValue);
        case Xpdl2Package.JOIN_SPLIT_TYPE:
            return convertJoinSplitTypeToString(eDataType, instanceValue);
        case Xpdl2Package.GATEWAY_TYPE:
            return convertGatewayTypeToString(eDataType, instanceValue);
        case Xpdl2Package.GRAPH_CONFORMANCE_TYPE:
            return convertGraphConformanceTypeToString(eDataType, instanceValue);
        case Xpdl2Package.IMPLEMENTATION_TYPE:
            return convertImplementationTypeToString(eDataType, instanceValue);
        case Xpdl2Package.INSTANTIATION_TYPE:
            return convertInstantiationTypeToString(eDataType, instanceValue);
        case Xpdl2Package.IS_ARRAY_TYPE:
            return convertIsArrayTypeToString(eDataType, instanceValue);
        case Xpdl2Package.LOOP_TYPE:
            return convertLoopTypeToString(eDataType, instanceValue);
        case Xpdl2Package.MI_FLOW_CONDITION_TYPE:
            return convertMIFlowConditionTypeToString(eDataType, instanceValue);
        case Xpdl2Package.MI_ORDERING_TYPE:
            return convertMIOrderingTypeToString(eDataType, instanceValue);
        case Xpdl2Package.MODE_TYPE:
            return convertModeTypeToString(eDataType, instanceValue);
        case Xpdl2Package.ORIENTATION_TYPE:
            return convertOrientationTypeToString(eDataType, instanceValue);
        case Xpdl2Package.PARTICIPANT_TYPE:
            return convertParticipantTypeToString(eDataType, instanceValue);
        case Xpdl2Package.PROCESS_TYPE:
            return convertProcessTypeToString(eDataType, instanceValue);
        case Xpdl2Package.PUBLICATION_STATUS_TYPE:
            return convertPublicationStatusTypeToString(eDataType,
                    instanceValue);
        case Xpdl2Package.RESULT_TYPE:
            return convertResultTypeToString(eDataType, instanceValue);
        case Xpdl2Package.ROLE_TYPE:
            return convertRoleTypeToString(eDataType, instanceValue);
        case Xpdl2Package.SHAPE_TYPE:
            return convertSHAPETypeToString(eDataType, instanceValue);
        case Xpdl2Package.START_MODE_TYPE:
            return convertStartModeTypeToString(eDataType, instanceValue);
        case Xpdl2Package.STATUS_TYPE:
            return convertStatusTypeToString(eDataType, instanceValue);
        case Xpdl2Package.TEST_TIME_TYPE:
            return convertTestTimeTypeToString(eDataType, instanceValue);
        case Xpdl2Package.TRANSACTION_METHOD_TYPE:
            return convertTransactionMethodTypeToString(eDataType,
                    instanceValue);
        case Xpdl2Package.TRIGGER_TYPE:
            return convertTriggerTypeToString(eDataType, instanceValue);
        case Xpdl2Package.VIEW_TYPE:
            return convertViewTypeToString(eDataType, instanceValue);
        case Xpdl2Package.DEPRECATED_XOR_TYPE:
            return convertDeprecatedXorTypeToString(eDataType, instanceValue);
        case Xpdl2Package.ACCESS_LEVEL_TYPE_OBJECT:
            return convertAccessLevelTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.AD_HOC_ORDERING_TYPE_OBJECT:
            return convertAdHocOrderingTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.ARTIFACT_TYPE_OBJECT:
            return convertArtifactTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.ASSIGN_TIME_TYPE_OBJECT:
            return convertAssignTimeTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.ASSOCIATION_DIRECTION_TYPE_OBJECT:
            return convertAssociationDirectionTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.DIRECTION_TYPE_OBJECT:
            return convertDirectionTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.DURATION_UNIT_TYPE_OBJECT:
            return convertDurationUnitTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.END_POINT_TYPE_TYPE_OBJECT:
            return convertEndPointTypeTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.EXECUTION_TYPE_OBJECT:
            return convertExecutionTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.FINISH_MODE_TYPE_OBJECT:
            return convertFinishModeTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.GATEWAY_TYPE_OBJECT:
            return convertGatewayTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.GRAPH_CONFORMANCE_TYPE_OBJECT:
            return convertGraphConformanceTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.ID_REFERENCE_STRING:
            return convertIdReferenceStringToString(eDataType, instanceValue);
        case Xpdl2Package.IMPLEMENTATION_TYPE_OBJECT:
            return convertImplementationTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.INSTANTIATION_TYPE_OBJECT:
            return convertInstantiationTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.IS_ARRAY_TYPE_OBJECT:
            return convertIsArrayTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.LOOP_TYPE_OBJECT:
            return convertLoopTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.MI_FLOW_CONDITION_TYPE_OBJECT:
            return convertMIFlowConditionTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.MI_ORDERING_TYPE_OBJECT:
            return convertMIOrderingTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.MODE_TYPE_OBJECT:
            return convertModeTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.ORIENTATION_TYPE_OBJECT:
            return convertOrientationTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.PROCESS_TYPE_OBJECT:
            return convertProcessTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.PUBLICATION_STATUS_TYPE_OBJECT:
            return convertPublicationStatusTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.RESULT_TYPE_OBJECT:
            return convertResultTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.ROLE_TYPE_OBJECT:
            return convertRoleTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.SHAPE_TYPE_OBJECT:
            return convertSHAPETypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.START_MODE_TYPE_OBJECT:
            return convertStartModeTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.STATUS_TYPE_OBJECT:
            return convertStatusTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.TEST_TIME_TYPE_OBJECT:
            return convertTestTimeTypeObjectToString(eDataType, instanceValue);
        case Xpdl2Package.TRANSACTION_METHOD_TYPE_OBJECT:
            return convertTransactionMethodTypeObjectToString(eDataType,
                    instanceValue);
        case Xpdl2Package.TRIGGER_TYPE_OBJECT:
            return convertTriggerTypeObjectToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ActivitySet createActivitySet() {
        ActivitySetImpl activitySet = new ActivitySetImpl();
        return activitySet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Activity createActivity() {
        ActivityImpl activity = new ActivityImpl();
        return activity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ApplicationType createApplicationType() {
        ApplicationTypeImpl applicationType = new ApplicationTypeImpl();
        return applicationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Application createApplication() {
        ApplicationImpl application = new ApplicationImpl();
        return application;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ArrayType createArrayType() {
        ArrayTypeImpl arrayType = new ArrayTypeImpl();
        return arrayType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Artifact createArtifact() {
        ArtifactImpl artifact = new ArtifactImpl();
        return artifact;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ArtifactInput createArtifactInput() {
        ArtifactInputImpl artifactInput = new ArtifactInputImpl();
        return artifactInput;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Assignment createAssignment() {
        AssignmentImpl assignment = new AssignmentImpl();
        return assignment;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Association createAssociation() {
        AssociationImpl association = new AssociationImpl();
        return association;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public BasicType createBasicType() {
        BasicTypeImpl basicType = new BasicTypeImpl();
        return basicType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public BlockActivity createBlockActivity() {
        BlockActivityImpl blockActivity = new BlockActivityImpl();
        return blockActivity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public BusinessRuleApplication createBusinessRuleApplication() {
        BusinessRuleApplicationImpl businessRuleApplication =
                new BusinessRuleApplicationImpl();
        return businessRuleApplication;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Category createCategory() {
        CategoryImpl category = new CategoryImpl();
        return category;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Class createClass() {
        ClassImpl class_ = new ClassImpl();
        return class_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Codepage createCodepage() {
        CodepageImpl codepage = new CodepageImpl();
        return codepage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Condition createCondition() {
        ConditionImpl condition = new ConditionImpl();
        return condition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConformanceClass createConformanceClass() {
        ConformanceClassImpl conformanceClass = new ConformanceClassImpl();
        return conformanceClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConnectorGraphicsInfo createConnectorGraphicsInfo() {
        ConnectorGraphicsInfoImpl connectorGraphicsInfo =
                new ConnectorGraphicsInfoImpl();
        return connectorGraphicsInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Coordinates createCoordinates() {
        CoordinatesImpl coordinates = new CoordinatesImpl();
        return coordinates;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Cost createCost() {
        CostImpl cost = new CostImpl();
        return cost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CostStructure createCostStructure() {
        CostStructureImpl costStructure = new CostStructureImpl();
        return costStructure;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public CostUnit createCostUnit() {
        CostUnitImpl costUnit = new CostUnitImpl();
        return costUnit;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public CountryKey createCountryKey() {
        CountryKeyImpl countryKey = new CountryKeyImpl();
        return countryKey;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DataField createDataField() {
        DataFieldImpl dataField = new DataFieldImpl();
        return dataField;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DataMapping createDataMapping() {
        DataMappingImpl dataMapping = new DataMappingImpl();
        return dataMapping;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DataObject createDataObject() {
        DataObjectImpl dataObject = new DataObjectImpl();
        return dataObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Deadline createDeadline() {
        DeadlineImpl deadline = new DeadlineImpl();
        return deadline;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeclaredType createDeclaredType() {
        DeclaredTypeImpl declaredType = new DeclaredTypeImpl();
        return declaredType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeprecatedResultCompensation createDeprecatedResultCompensation() {
        DeprecatedResultCompensationImpl deprecatedResultCompensation =
                new DeprecatedResultCompensationImpl();
        return deprecatedResultCompensation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeprecatedTriggerRule createDeprecatedTriggerRule() {
        DeprecatedTriggerRuleImpl deprecatedTriggerRule =
                new DeprecatedTriggerRuleImpl();
        return deprecatedTriggerRule;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Description createDescription() {
        DescriptionImpl description = new DescriptionImpl();
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Documentation createDocumentation() {
        DocumentationImpl documentation = new DocumentationImpl();
        return documentation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Duration createDuration() {
        DurationImpl duration = new DurationImpl();
        return duration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EjbApplication createEjbApplication() {
        EjbApplicationImpl ejbApplication = new EjbApplicationImpl();
        return ejbApplication;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EndEvent createEndEvent() {
        EndEventImpl endEvent = new EndEventImpl();
        return endEvent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EndPoint createEndPoint() {
        EndPointImpl endPoint = new EndPointImpl();
        return endPoint;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EnumerationType createEnumerationType() {
        EnumerationTypeImpl enumerationType = new EnumerationTypeImpl();
        return enumerationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EnumerationValue createEnumerationValue() {
        EnumerationValueImpl enumerationValue = new EnumerationValueImpl();
        return enumerationValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ExceptionName createExceptionName() {
        ExceptionNameImpl exceptionName = new ExceptionNameImpl();
        return exceptionName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Expression createExpression() {
        ExpressionImpl expression = new ExpressionImpl();
        return expression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ExtendedAttribute createExtendedAttribute() {
        ExtendedAttributeImpl extendedAttribute = new ExtendedAttributeImpl();
        return extendedAttribute;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ExternalPackage createExternalPackage() {
        ExternalPackageImpl externalPackage = new ExternalPackageImpl();
        return externalPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ExternalReference createExternalReference() {
        ExternalReferenceImpl externalReference = new ExternalReferenceImpl();
        return externalReference;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FormalParameter createFormalParameter() {
        FormalParameterImpl formalParameter = new FormalParameterImpl();
        return formalParameter;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FormLayout createFormLayout() {
        FormLayoutImpl formLayout = new FormLayoutImpl();
        return formLayout;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FormApplication createFormApplication() {
        FormApplicationImpl formApplication = new FormApplicationImpl();
        return formApplication;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public HomeClass createHomeClass() {
        HomeClassImpl homeClass = new HomeClassImpl();
        return homeClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Icon createIcon() {
        IconImpl icon = new IconImpl();
        return icon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public InputSet createInputSet() {
        InputSetImpl inputSet = new InputSetImpl();
        return inputSet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Input createInput() {
        InputImpl input = new InputImpl();
        return input;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IntermediateEvent createIntermediateEvent() {
        IntermediateEventImpl intermediateEvent = new IntermediateEventImpl();
        return intermediateEvent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IORules createIORules() {
        IORulesImpl ioRules = new IORulesImpl();
        return ioRules;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public JndiName createJndiName() {
        JndiNameImpl jndiName = new JndiNameImpl();
        return jndiName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Join createJoin() {
        JoinImpl join = new JoinImpl();
        return join;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LayoutInfo createLayoutInfo() {
        LayoutInfoImpl layoutInfo = new LayoutInfoImpl();
        return layoutInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Lane createLane() {
        LaneImpl lane = new LaneImpl();
        return lane;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Length createLength() {
        LengthImpl length = new LengthImpl();
        return length;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Limit createLimit() {
        LimitImpl limit = new LimitImpl();
        return limit;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ListType createListType() {
        ListTypeImpl listType = new ListTypeImpl();
        return listType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Location createLocation() {
        LocationImpl location = new LocationImpl();
        return location;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public LoopMultiInstance createLoopMultiInstance() {
        LoopMultiInstanceImpl loopMultiInstance = new LoopMultiInstanceImpl();
        return loopMultiInstance;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public LoopStandard createLoopStandard() {
        LoopStandardImpl loopStandard = new LoopStandardImpl();
        return loopStandard;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Loop createLoop() {
        LoopImpl loop = new LoopImpl();
        return loop;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Member createMember() {
        MemberImpl member = new MemberImpl();
        return member;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public MessageFlow createMessageFlow() {
        MessageFlowImpl messageFlow = new MessageFlowImpl();
        return messageFlow;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Message createMessage() {
        MessageImpl message = new MessageImpl();
        return message;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Method createMethod() {
        MethodImpl method = new MethodImpl();
        return method;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModificationDate createModificationDate() {
        ModificationDateImpl modificationDate = new ModificationDateImpl();
        return modificationDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public MyRole createMyRole() {
        MyRoleImpl myRole = new MyRoleImpl();
        return myRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NodeGraphicsInfo createNodeGraphicsInfo() {
        NodeGraphicsInfoImpl nodeGraphicsInfo = new NodeGraphicsInfoImpl();
        return nodeGraphicsInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public No createNo() {
        NoImpl no = new NoImpl();
        return no;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Object createObject() {
        ObjectImpl object = new ObjectImpl();
        return object;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OutputSet createOutputSet() {
        OutputSetImpl outputSet = new OutputSetImpl();
        return outputSet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Output createOutput() {
        OutputImpl output = new OutputImpl();
        return output;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public PackageHeader createPackageHeader() {
        PackageHeaderImpl packageHeader = new PackageHeaderImpl();
        // Set the XPDL Version
        packageHeader.eSet(Xpdl2Package.PACKAGE_HEADER__XPDL_VERSION, "2.1"); //$NON-NLS-1$
        return packageHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Package createPackage() {
        PackageImpl package_ = new PackageImpl();
        return package_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Page createPage() {
        PageImpl page = new PageImpl();
        return page;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Pages createPages() {
        PagesImpl pages = new PagesImpl();
        return pages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DataFieldsContainer createDataFieldsContainer() {
        DataFieldsContainerImpl dataFieldsContainer =
                new DataFieldsContainerImpl();
        return dataFieldsContainer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Participant createParticipant() {
        ParticipantImpl participant = new ParticipantImpl();
        return participant;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ParticipantTypeElem createParticipantTypeElem() {
        ParticipantTypeElemImpl participantTypeElem =
                new ParticipantTypeElemImpl();
        return participantTypeElem;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PartnerLink createPartnerLink() {
        PartnerLinkImpl partnerLink = new PartnerLinkImpl();
        return partnerLink;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PartnerLinkType createPartnerLinkType() {
        PartnerLinkTypeImpl partnerLinkType = new PartnerLinkTypeImpl();
        return partnerLinkType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PartnerRole createPartnerRole() {
        PartnerRoleImpl partnerRole = new PartnerRoleImpl();
        return partnerRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Partner createPartner() {
        PartnerImpl partner = new PartnerImpl();
        return partner;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Performer createPerformer() {
        PerformerImpl performer = new PerformerImpl();
        return performer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PojoApplication createPojoApplication() {
        PojoApplicationImpl pojoApplication = new PojoApplicationImpl();
        return pojoApplication;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Pool createPool() {
        PoolImpl pool = new PoolImpl();
        return pool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Precision createPrecision() {
        PrecisionImpl precision = new PrecisionImpl();
        return precision;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Priority createPriority() {
        PriorityImpl priority = new PriorityImpl();
        return priority;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PriorityUnit createPriorityUnit() {
        PriorityUnitImpl priorityUnit = new PriorityUnitImpl();
        return priorityUnit;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProcessHeader createProcessHeader() {
        ProcessHeaderImpl processHeader = new ProcessHeaderImpl();
        return processHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Process createProcess() {
        ProcessImpl process = new ProcessImpl();
        return process;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertyInput createPropertyInput() {
        PropertyInputImpl propertyInput = new PropertyInputImpl();
        return propertyInput;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RecordType createRecordType() {
        RecordTypeImpl recordType = new RecordTypeImpl();
        return recordType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RedefinableHeader createRedefinableHeader() {
        RedefinableHeaderImpl redefinableHeader = new RedefinableHeaderImpl();
        return redefinableHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Reference createReference() {
        ReferenceImpl reference = new ReferenceImpl();
        return reference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceCosts createResourceCosts() {
        ResourceCostsImpl resourceCosts = new ResourceCostsImpl();
        return resourceCosts;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Responsible createResponsible() {
        ResponsibleImpl responsible = new ResponsibleImpl();
        return responsible;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResultError createResultError() {
        ResultErrorImpl resultError = new ResultErrorImpl();
        return resultError;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResultMultiple createResultMultiple() {
        ResultMultipleImpl resultMultiple = new ResultMultipleImpl();
        return resultMultiple;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Role createRole() {
        RoleImpl role = new RoleImpl();
        return role;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Route createRoute() {
        RouteImpl route = new RouteImpl();
        return route;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RuleName createRuleName() {
        RuleNameImpl ruleName = new RuleNameImpl();
        return ruleName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Rule createRule() {
        RuleImpl rule = new RuleImpl();
        return rule;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Scale createScale() {
        ScaleImpl scale = new ScaleImpl();
        return scale;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Schema createSchema() {
        SchemaImpl schema = new SchemaImpl();
        return schema;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Script createScript() {
        ScriptImpl script = new ScriptImpl();
        return script;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Service createService() {
        ServiceImpl service = new ServiceImpl();
        return service;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SimulationInformation createSimulationInformation() {
        SimulationInformationImpl simulationInformation =
                new SimulationInformationImpl();
        return simulationInformation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Split createSplit() {
        SplitImpl split = new SplitImpl();
        return split;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public StartEvent createStartEvent() {
        StartEventImpl startEvent = new StartEventImpl();
        return startEvent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SubFlow createSubFlow() {
        SubFlowImpl subFlow = new SubFlowImpl();
        return subFlow;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskApplication createTaskApplication() {
        TaskApplicationImpl taskApplication = new TaskApplicationImpl();
        return taskApplication;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskManual createTaskManual() {
        TaskManualImpl taskManual = new TaskManualImpl();
        return taskManual;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskReceive createTaskReceive() {
        TaskReceiveImpl taskReceive = new TaskReceiveImpl();
        return taskReceive;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskReference createTaskReference() {
        TaskReferenceImpl taskReference = new TaskReferenceImpl();
        return taskReference;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskScript createTaskScript() {
        TaskScriptImpl taskScript = new TaskScriptImpl();
        return taskScript;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskSend createTaskSend() {
        TaskSendImpl taskSend = new TaskSendImpl();
        return taskSend;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskService createTaskService() {
        TaskServiceImpl taskService = new TaskServiceImpl();
        return taskService;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Task createTask() {
        TaskImpl task = new TaskImpl();
        return task;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TaskUser createTaskUser() {
        TaskUserImpl taskUser = new TaskUserImpl();
        return taskUser;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TimeEstimation createTimeEstimation() {
        TimeEstimationImpl timeEstimation = new TimeEstimationImpl();
        return timeEstimation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Transaction createTransaction() {
        TransactionImpl transaction = new TransactionImpl();
        return transaction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TransitionRef createTransitionRef() {
        TransitionRefImpl transitionRef = new TransitionRefImpl();
        return transitionRef;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TransitionRestriction createTransitionRestriction() {
        TransitionRestrictionImpl transitionRestriction =
                new TransitionRestrictionImpl();
        return transitionRestriction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Transition createTransition() {
        TransitionImpl transition = new TransitionImpl();
        return transition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerIntermediateMultiple createTriggerIntermediateMultiple() {
        TriggerIntermediateMultipleImpl triggerIntermediateMultiple =
                new TriggerIntermediateMultipleImpl();
        return triggerIntermediateMultiple;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerMultiple createTriggerMultiple() {
        TriggerMultipleImpl triggerMultiple = new TriggerMultipleImpl();
        return triggerMultiple;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultCancel createTriggerResultCancel() {
        TriggerResultCancelImpl triggerResultCancel =
                new TriggerResultCancelImpl();
        return triggerResultCancel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultCompensation createTriggerResultCompensation() {
        TriggerResultCompensationImpl triggerResultCompensation =
                new TriggerResultCompensationImpl();
        return triggerResultCompensation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultSignal createTriggerResultSignal() {
        TriggerResultSignalImpl triggerResultSignal =
                new TriggerResultSignalImpl();
        return triggerResultSignal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultLink createTriggerResultLink() {
        TriggerResultLinkImpl triggerResultLink = new TriggerResultLinkImpl();
        return triggerResultLink;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultMessage createTriggerResultMessage() {
        TriggerResultMessageImpl triggerResultMessage =
                new TriggerResultMessageImpl();
        return triggerResultMessage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerConditional createTriggerConditional() {
        TriggerConditionalImpl triggerConditional =
                new TriggerConditionalImpl();
        return triggerConditional;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerTimer createTriggerTimer() {
        TriggerTimerImpl triggerTimer = new TriggerTimerImpl();
        return triggerTimer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TypeDeclaration createTypeDeclaration() {
        TypeDeclarationImpl typeDeclaration = new TypeDeclarationImpl();
        return typeDeclaration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public UnionType createUnionType() {
        UnionTypeImpl unionType = new UnionTypeImpl();
        return unionType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ValidFrom createValidFrom() {
        ValidFromImpl validFrom = new ValidFromImpl();
        return validFrom;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ValidTo createValidTo() {
        ValidToImpl validTo = new ValidToImpl();
        return validTo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public VendorExtensions createVendorExtensions() {
        VendorExtensionsImpl vendorExtensions = new VendorExtensionsImpl();
        return vendorExtensions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public VendorExtension createVendorExtension() {
        VendorExtensionImpl vendorExtension = new VendorExtensionImpl();
        return vendorExtension;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WaitingTime createWaitingTime() {
        WaitingTimeImpl waitingTime = new WaitingTimeImpl();
        return waitingTime;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WebServiceFaultCatch createWebServiceFaultCatch() {
        WebServiceFaultCatchImpl webServiceFaultCatch =
                new WebServiceFaultCatchImpl();
        return webServiceFaultCatch;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WebServiceOperation createWebServiceOperation() {
        WebServiceOperationImpl webServiceOperation =
                new WebServiceOperationImpl();
        return webServiceOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WebServiceApplication createWebServiceApplication() {
        WebServiceApplicationImpl webServiceApplication =
                new WebServiceApplicationImpl();
        return webServiceApplication;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WorkingTime createWorkingTime() {
        WorkingTimeImpl workingTime = new WorkingTimeImpl();
        return workingTime;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public XsltApplication createXsltApplication() {
        XsltApplicationImpl xsltApplication = new XsltApplicationImpl();
        return xsltApplication;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Performers createPerformers() {
        PerformersImpl performers = new PerformersImpl();
        return performers;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AccessLevelType createAccessLevelTypeFromString(EDataType eDataType,
            String initialValue) {
        AccessLevelType result = AccessLevelType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAccessLevelTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AdHocOrderingType createAdHocOrderingTypeFromString(
            EDataType eDataType, String initialValue) {
        AdHocOrderingType result = AdHocOrderingType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAdHocOrderingTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ArtifactType createArtifactTypeFromString(EDataType eDataType,
            String initialValue) {
        ArtifactType result = ArtifactType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertArtifactTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AssignTimeType createAssignTimeTypeFromString(EDataType eDataType,
            String initialValue) {
        AssignTimeType result = AssignTimeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAssignTimeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AssociationDirectionType createAssociationDirectionTypeFromString(
            EDataType eDataType, String initialValue) {
        AssociationDirectionType result =
                AssociationDirectionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAssociationDirectionTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DirectionType createDirectionTypeFromString(EDataType eDataType,
            String initialValue) {
        DirectionType result = DirectionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDirectionTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DurationUnitType createDurationUnitTypeFromString(
            EDataType eDataType, String initialValue) {
        DurationUnitType result = DurationUnitType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDurationUnitTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EndPointTypeType createEndPointTypeTypeFromString(
            EDataType eDataType, String initialValue) {
        EndPointTypeType result = EndPointTypeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertEndPointTypeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExclusiveType createExclusiveTypeFromString(EDataType eDataType,
            String initialValue) {
        ExclusiveType result = ExclusiveType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertExclusiveTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ExecutionType createExecutionTypeFromString(EDataType eDataType,
            String initialValue) {
        ExecutionType result = ExecutionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertExecutionTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FinishModeType createFinishModeTypeFromString(EDataType eDataType,
            String initialValue) {
        FinishModeType result = FinishModeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertFinishModeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public JoinSplitType createJoinSplitTypeFromString(EDataType eDataType,
            String initialValue) {
        JoinSplitType result = JoinSplitType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertJoinSplitTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public GraphConformanceType createGraphConformanceTypeFromString(
            EDataType eDataType, String initialValue) {
        GraphConformanceType result = GraphConformanceType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertGraphConformanceTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ImplementationType createImplementationTypeFromString(
            EDataType eDataType, String initialValue) {
        ImplementationType result = ImplementationType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertImplementationTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public InstantiationType createInstantiationTypeFromString(
            EDataType eDataType, String initialValue) {
        InstantiationType result = InstantiationType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertInstantiationTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IsArrayType createIsArrayTypeFromString(EDataType eDataType,
            String initialValue) {
        IsArrayType result = IsArrayType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertIsArrayTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public LoopType createLoopTypeFromString(EDataType eDataType,
            String initialValue) {
        LoopType result = LoopType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertLoopTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public MIFlowConditionType createMIFlowConditionTypeFromString(
            EDataType eDataType, String initialValue) {
        MIFlowConditionType result = MIFlowConditionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMIFlowConditionTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public MIOrderingType createMIOrderingTypeFromString(EDataType eDataType,
            String initialValue) {
        MIOrderingType result = MIOrderingType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMIOrderingTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ModeType createModeTypeFromString(EDataType eDataType,
            String initialValue) {
        ModeType result = ModeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertModeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrientationType createOrientationTypeFromString(EDataType eDataType,
            String initialValue) {
        OrientationType result = OrientationType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertOrientationTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProcessType createProcessTypeFromString(EDataType eDataType,
            String initialValue) {
        ProcessType result = ProcessType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertProcessTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PublicationStatusType createPublicationStatusTypeFromString(
            EDataType eDataType, String initialValue) {
        PublicationStatusType result = PublicationStatusType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertPublicationStatusTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResultType createResultTypeFromString(EDataType eDataType,
            String initialValue) {
        ResultType result = ResultType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertResultTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RoleType createRoleTypeFromString(EDataType eDataType,
            String initialValue) {
        RoleType result = RoleType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertRoleTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SHAPEType createSHAPETypeFromString(EDataType eDataType,
            String initialValue) {
        SHAPEType result = SHAPEType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSHAPETypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public StartModeType createStartModeTypeFromString(EDataType eDataType,
            String initialValue) {
        StartModeType result = StartModeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertStartModeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public StatusType createStatusTypeFromString(EDataType eDataType,
            String initialValue) {
        StatusType result = StatusType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertStatusTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TestTimeType createTestTimeTypeFromString(EDataType eDataType,
            String initialValue) {
        TestTimeType result = TestTimeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTestTimeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TransactionMethodType createTransactionMethodTypeFromString(
            EDataType eDataType, String initialValue) {
        TransactionMethodType result = TransactionMethodType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTransactionMethodTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerType createTriggerTypeFromString(EDataType eDataType,
            String initialValue) {
        TriggerType result = TriggerType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTriggerTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ViewType createViewTypeFromString(EDataType eDataType,
            String initialValue) {
        ViewType result = ViewType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertViewTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeprecatedXorType createDeprecatedXorTypeFromString(
            EDataType eDataType, String initialValue) {
        DeprecatedXorType result = DeprecatedXorType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDeprecatedXorTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConditionType createConditionTypeFromString(EDataType eDataType,
            String initialValue) {
        ConditionType result = ConditionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertConditionTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ParticipantType createParticipantTypeFromString(EDataType eDataType,
            String initialValue) {
        ParticipantType result = ParticipantType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertParticipantTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public BasicTypeType createBasicTypeTypeFromString(EDataType eDataType,
            String initialValue) {
        BasicTypeType result = BasicTypeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertBasicTypeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BPMNModelPortabilityConformance createBPMNModelPortabilityConformanceFromString(
            EDataType eDataType, String initialValue) {
        BPMNModelPortabilityConformance result =
                BPMNModelPortabilityConformance.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertBPMNModelPortabilityConformanceToString(
            EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CatchThrow createCatchThrowFromString(EDataType eDataType,
            String initialValue) {
        CatchThrow result = CatchThrow.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertCatchThrowToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public GatewayType createGatewayTypeFromString(EDataType eDataType,
            String initialValue) {
        GatewayType result = GatewayType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertGatewayTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AccessLevelType createAccessLevelTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createAccessLevelTypeFromString(Xpdl2Package.Literals.ACCESS_LEVEL_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAccessLevelTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertAccessLevelTypeToString(Xpdl2Package.Literals.ACCESS_LEVEL_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AdHocOrderingType createAdHocOrderingTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createAdHocOrderingTypeFromString(Xpdl2Package.Literals.AD_HOC_ORDERING_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAdHocOrderingTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertAdHocOrderingTypeToString(Xpdl2Package.Literals.AD_HOC_ORDERING_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ArtifactType createArtifactTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createArtifactTypeFromString(Xpdl2Package.Literals.ARTIFACT_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertArtifactTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertArtifactTypeToString(Xpdl2Package.Literals.ARTIFACT_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AssignTimeType createAssignTimeTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createAssignTimeTypeFromString(Xpdl2Package.Literals.ASSIGN_TIME_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAssignTimeTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertAssignTimeTypeToString(Xpdl2Package.Literals.ASSIGN_TIME_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AssociationDirectionType createAssociationDirectionTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createAssociationDirectionTypeFromString(Xpdl2Package.Literals.ASSOCIATION_DIRECTION_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAssociationDirectionTypeObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertAssociationDirectionTypeToString(Xpdl2Package.Literals.ASSOCIATION_DIRECTION_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DirectionType createDirectionTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createDirectionTypeFromString(Xpdl2Package.Literals.DIRECTION_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDirectionTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertDirectionTypeToString(Xpdl2Package.Literals.DIRECTION_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DurationUnitType createDurationUnitTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createDurationUnitTypeFromString(Xpdl2Package.Literals.DURATION_UNIT_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDurationUnitTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertDurationUnitTypeToString(Xpdl2Package.Literals.DURATION_UNIT_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EndPointTypeType createEndPointTypeTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createEndPointTypeTypeFromString(Xpdl2Package.Literals.END_POINT_TYPE_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertEndPointTypeTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertEndPointTypeTypeToString(Xpdl2Package.Literals.END_POINT_TYPE_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ExecutionType createExecutionTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createExecutionTypeFromString(Xpdl2Package.Literals.EXECUTION_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertExecutionTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertExecutionTypeToString(Xpdl2Package.Literals.EXECUTION_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FinishModeType createFinishModeTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createFinishModeTypeFromString(Xpdl2Package.Literals.FINISH_MODE_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertFinishModeTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertFinishModeTypeToString(Xpdl2Package.Literals.FINISH_MODE_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public JoinSplitType createGatewayTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createJoinSplitTypeFromString(Xpdl2Package.Literals.JOIN_SPLIT_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertGatewayTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertJoinSplitTypeToString(Xpdl2Package.Literals.JOIN_SPLIT_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public GraphConformanceType createGraphConformanceTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createGraphConformanceTypeFromString(Xpdl2Package.Literals.GRAPH_CONFORMANCE_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertGraphConformanceTypeObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertGraphConformanceTypeToString(Xpdl2Package.Literals.GRAPH_CONFORMANCE_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Enumerator createImplementationTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return (Enumerator) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertImplementationTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public InstantiationType createInstantiationTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createInstantiationTypeFromString(Xpdl2Package.Literals.INSTANTIATION_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertInstantiationTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertInstantiationTypeToString(Xpdl2Package.Literals.INSTANTIATION_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IsArrayType createIsArrayTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createIsArrayTypeFromString(Xpdl2Package.Literals.IS_ARRAY_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertIsArrayTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertIsArrayTypeToString(Xpdl2Package.Literals.IS_ARRAY_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public LoopType createLoopTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createLoopTypeFromString(Xpdl2Package.Literals.LOOP_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertLoopTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertLoopTypeToString(Xpdl2Package.Literals.LOOP_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public MIFlowConditionType createMIFlowConditionTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createMIFlowConditionTypeFromString(Xpdl2Package.Literals.MI_FLOW_CONDITION_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMIFlowConditionTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertMIFlowConditionTypeToString(Xpdl2Package.Literals.MI_FLOW_CONDITION_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public MIOrderingType createMIOrderingTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createMIOrderingTypeFromString(Xpdl2Package.Literals.MI_ORDERING_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMIOrderingTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertMIOrderingTypeToString(Xpdl2Package.Literals.MI_ORDERING_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ModeType createModeTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createModeTypeFromString(Xpdl2Package.Literals.MODE_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertModeTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertModeTypeToString(Xpdl2Package.Literals.MODE_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrientationType createOrientationTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createOrientationTypeFromString(Xpdl2Package.Literals.ORIENTATION_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertOrientationTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertOrientationTypeToString(Xpdl2Package.Literals.ORIENTATION_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProcessType createProcessTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createProcessTypeFromString(Xpdl2Package.Literals.PROCESS_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertProcessTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertProcessTypeToString(Xpdl2Package.Literals.PROCESS_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PublicationStatusType createPublicationStatusTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createPublicationStatusTypeFromString(Xpdl2Package.Literals.PUBLICATION_STATUS_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertPublicationStatusTypeObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertPublicationStatusTypeToString(Xpdl2Package.Literals.PUBLICATION_STATUS_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResultType createResultTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createResultTypeFromString(Xpdl2Package.Literals.RESULT_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertResultTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertResultTypeToString(Xpdl2Package.Literals.RESULT_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RoleType createRoleTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createRoleTypeFromString(Xpdl2Package.Literals.ROLE_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertRoleTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertRoleTypeToString(Xpdl2Package.Literals.ROLE_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SHAPEType createSHAPETypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createSHAPETypeFromString(Xpdl2Package.Literals.SHAPE_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSHAPETypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertSHAPETypeToString(Xpdl2Package.Literals.SHAPE_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public StartModeType createStartModeTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createStartModeTypeFromString(Xpdl2Package.Literals.START_MODE_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertStartModeTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertStartModeTypeToString(Xpdl2Package.Literals.START_MODE_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public StatusType createStatusTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createStatusTypeFromString(Xpdl2Package.Literals.STATUS_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertStatusTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertStatusTypeToString(Xpdl2Package.Literals.STATUS_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TestTimeType createTestTimeTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createTestTimeTypeFromString(Xpdl2Package.Literals.TEST_TIME_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTestTimeTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertTestTimeTypeToString(Xpdl2Package.Literals.TEST_TIME_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TransactionMethodType createTransactionMethodTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createTransactionMethodTypeFromString(Xpdl2Package.Literals.TRANSACTION_METHOD_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTransactionMethodTypeObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertTransactionMethodTypeToString(Xpdl2Package.Literals.TRANSACTION_METHOD_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerType createTriggerTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createTriggerTypeFromString(Xpdl2Package.Literals.TRIGGER_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTriggerTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertTriggerTypeToString(Xpdl2Package.Literals.TRIGGER_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String createIdReferenceStringFromString(EDataType eDataType,
            String initialValue) {
        return (String) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertIdReferenceStringToString(EDataType eDataType,
            Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Xpdl2Package getXpdl2Package() {
        return (Xpdl2Package) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static Xpdl2Package getPackage() {
        return Xpdl2Package.eINSTANCE;
    }

    public Coordinates createCoordinates(double x, double y) {
        Coordinates coord = createCoordinates();
        coord.setXCoordinate(x);
        coord.setYCoordinate(y);
        return coord;
    }

    /**
     * @generated not
     */
    public Expression createExpression(String text) {
        Expression exp = createExpression();

        exp.getMixed()
                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        text);
        return exp;
    }

} // Xpdl2FactoryImpl
