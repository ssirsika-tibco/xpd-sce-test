/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.util;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.ApplicationType;
import com.tibco.xpd.xpdl2.ApplicationsContainer;
import com.tibco.xpd.xpdl2.ArrayType;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactInput;
import com.tibco.xpd.xpdl2.AssigmentsContainer;
import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.BusinessRuleApplication;
import com.tibco.xpd.xpdl2.Category;
import com.tibco.xpd.xpdl2.Codepage;
import com.tibco.xpd.xpdl2.Condition;
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
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.DeprecatedResultCompensation;
import com.tibco.xpd.xpdl2.DeprecatedTriggerRule;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.Duration;
import com.tibco.xpd.xpdl2.EjbApplication;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EnumerationType;
import com.tibco.xpd.xpdl2.EnumerationValue;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExceptionName;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormApplication;
import com.tibco.xpd.xpdl2.FormLayout;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Group;
import com.tibco.xpd.xpdl2.HomeClass;
import com.tibco.xpd.xpdl2.IORules;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Input;
import com.tibco.xpd.xpdl2.InputSet;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JndiName;
import com.tibco.xpd.xpdl2.Join;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.LayoutInfo;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Limit;
import com.tibco.xpd.xpdl2.ListType;
import com.tibco.xpd.xpdl2.Location;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Method;
import com.tibco.xpd.xpdl2.ModificationDate;
import com.tibco.xpd.xpdl2.MyRole;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Output;
import com.tibco.xpd.xpdl2.OutputSet;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.Page;
import com.tibco.xpd.xpdl2.Pages;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
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
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.PropertyInput;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.ResourceCosts;
import com.tibco.xpd.xpdl2.Responsible;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultMultiple;
import com.tibco.xpd.xpdl2.Role;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Rule;
import com.tibco.xpd.xpdl2.RuleName;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.Schema;
import com.tibco.xpd.xpdl2.Script;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.SimulationInformation;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.StartEvent;
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
import com.tibco.xpd.xpdl2.TimeEstimation;
import com.tibco.xpd.xpdl2.Transaction;
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
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UnionType;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.ValidFrom;
import com.tibco.xpd.xpdl2.ValidTo;
import com.tibco.xpd.xpdl2.VendorExtension;
import com.tibco.xpd.xpdl2.VendorExtensions;
import com.tibco.xpd.xpdl2.WaitingTime;
import com.tibco.xpd.xpdl2.WebServiceApplication;
import com.tibco.xpd.xpdl2.WebServiceFaultCatch;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.WorkingTime;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.XsltApplication;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import java.lang.Object;
import com.tibco.xpd.xpdl2.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package
 * @generated
 */
public class Xpdl2AdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static Xpdl2Package modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Xpdl2AdapterFactory() {
        if (modelPackage == null) {
            modelPackage = Xpdl2Package.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Xpdl2Switch<Adapter> modelSwitch = new Xpdl2Switch<Adapter>() {
        @Override
        public Adapter caseActivitySet(ActivitySet object) {
            return createActivitySetAdapter();
        }

        @Override
        public Adapter caseActivity(Activity object) {
            return createActivityAdapter();
        }

        @Override
        public Adapter caseApplicationType(ApplicationType object) {
            return createApplicationTypeAdapter();
        }

        @Override
        public Adapter caseApplication(Application object) {
            return createApplicationAdapter();
        }

        @Override
        public Adapter caseApplicationsContainer(ApplicationsContainer object) {
            return createApplicationsContainerAdapter();
        }

        @Override
        public Adapter caseArrayType(ArrayType object) {
            return createArrayTypeAdapter();
        }

        @Override
        public Adapter caseArtifact(Artifact object) {
            return createArtifactAdapter();
        }

        @Override
        public Adapter caseArtifactInput(ArtifactInput object) {
            return createArtifactInputAdapter();
        }

        @Override
        public Adapter caseAssignment(Assignment object) {
            return createAssignmentAdapter();
        }

        @Override
        public Adapter caseAssigmentsContainer(AssigmentsContainer object) {
            return createAssigmentsContainerAdapter();
        }

        @Override
        public Adapter caseAssociation(Association object) {
            return createAssociationAdapter();
        }

        @Override
        public Adapter caseBasicType(BasicType object) {
            return createBasicTypeAdapter();
        }

        @Override
        public Adapter caseBlockActivity(BlockActivity object) {
            return createBlockActivityAdapter();
        }

        @Override
        public Adapter caseBusinessRuleApplication(
                BusinessRuleApplication object) {
            return createBusinessRuleApplicationAdapter();
        }

        @Override
        public Adapter caseCategory(Category object) {
            return createCategoryAdapter();
        }

        @Override
        public Adapter caseClass(com.tibco.xpd.xpdl2.Class object) {
            return createClassAdapter();
        }

        @Override
        public Adapter caseCodepage(Codepage object) {
            return createCodepageAdapter();
        }

        @Override
        public Adapter caseCondition(Condition object) {
            return createConditionAdapter();
        }

        @Override
        public Adapter caseConformanceClass(ConformanceClass object) {
            return createConformanceClassAdapter();
        }

        @Override
        public Adapter caseConnectorGraphicsInfo(ConnectorGraphicsInfo object) {
            return createConnectorGraphicsInfoAdapter();
        }

        @Override
        public Adapter caseCoordinates(Coordinates object) {
            return createCoordinatesAdapter();
        }

        @Override
        public Adapter caseCost(Cost object) {
            return createCostAdapter();
        }

        @Override
        public Adapter caseCostStructure(CostStructure object) {
            return createCostStructureAdapter();
        }

        @Override
        public Adapter caseCostUnit(CostUnit object) {
            return createCostUnitAdapter();
        }

        @Override
        public Adapter caseCountryKey(CountryKey object) {
            return createCountryKeyAdapter();
        }

        @Override
        public Adapter caseDataField(DataField object) {
            return createDataFieldAdapter();
        }

        @Override
        public Adapter caseDataFieldsContainer(DataFieldsContainer object) {
            return createDataFieldsContainerAdapter();
        }

        @Override
        public Adapter caseDataMapping(DataMapping object) {
            return createDataMappingAdapter();
        }

        @Override
        public Adapter caseDataObject(DataObject object) {
            return createDataObjectAdapter();
        }

        @Override
        public Adapter caseDataType(DataType object) {
            return createDataTypeAdapter();
        }

        @Override
        public Adapter caseDeadline(Deadline object) {
            return createDeadlineAdapter();
        }

        @Override
        public Adapter caseDeclaredType(DeclaredType object) {
            return createDeclaredTypeAdapter();
        }

        @Override
        public Adapter caseDeprecatedResultCompensation(
                DeprecatedResultCompensation object) {
            return createDeprecatedResultCompensationAdapter();
        }

        @Override
        public Adapter caseDeprecatedTriggerRule(DeprecatedTriggerRule object) {
            return createDeprecatedTriggerRuleAdapter();
        }

        @Override
        public Adapter caseDescribedElement(DescribedElement object) {
            return createDescribedElementAdapter();
        }

        @Override
        public Adapter caseDescription(Description object) {
            return createDescriptionAdapter();
        }

        @Override
        public Adapter caseDocumentation(Documentation object) {
            return createDocumentationAdapter();
        }

        @Override
        public Adapter caseDocumentRoot(DocumentRoot object) {
            return createDocumentRootAdapter();
        }

        @Override
        public Adapter caseDuration(Duration object) {
            return createDurationAdapter();
        }

        @Override
        public Adapter caseEjbApplication(EjbApplication object) {
            return createEjbApplicationAdapter();
        }

        @Override
        public Adapter caseEndEvent(EndEvent object) {
            return createEndEventAdapter();
        }

        @Override
        public Adapter caseEndPoint(EndPoint object) {
            return createEndPointAdapter();
        }

        @Override
        public Adapter caseEnumerationType(EnumerationType object) {
            return createEnumerationTypeAdapter();
        }

        @Override
        public Adapter caseEnumerationValue(EnumerationValue object) {
            return createEnumerationValueAdapter();
        }

        @Override
        public Adapter caseEvent(Event object) {
            return createEventAdapter();
        }

        @Override
        public Adapter caseExceptionName(ExceptionName object) {
            return createExceptionNameAdapter();
        }

        @Override
        public Adapter caseExpression(Expression object) {
            return createExpressionAdapter();
        }

        @Override
        public Adapter caseExtendedAttribute(ExtendedAttribute object) {
            return createExtendedAttributeAdapter();
        }

        @Override
        public Adapter caseExtendedAttributesContainer(
                ExtendedAttributesContainer object) {
            return createExtendedAttributesContainerAdapter();
        }

        @Override
        public Adapter caseExternalPackage(ExternalPackage object) {
            return createExternalPackageAdapter();
        }

        @Override
        public Adapter caseExternalReference(ExternalReference object) {
            return createExternalReferenceAdapter();
        }

        @Override
        public Adapter caseFlowContainer(FlowContainer object) {
            return createFlowContainerAdapter();
        }

        @Override
        public Adapter caseFormalParameter(FormalParameter object) {
            return createFormalParameterAdapter();
        }

        @Override
        public Adapter caseFormLayout(FormLayout object) {
            return createFormLayoutAdapter();
        }

        @Override
        public Adapter caseFormApplication(FormApplication object) {
            return createFormApplicationAdapter();
        }

        @Override
        public Adapter caseGraphicalNode(GraphicalNode object) {
            return createGraphicalNodeAdapter();
        }

        @Override
        public Adapter caseGraphicalConnector(GraphicalConnector object) {
            return createGraphicalConnectorAdapter();
        }

        @Override
        public Adapter caseGroup(Group object) {
            return createGroupAdapter();
        }

        @Override
        public Adapter caseHomeClass(HomeClass object) {
            return createHomeClassAdapter();
        }

        @Override
        public Adapter caseIcon(Icon object) {
            return createIconAdapter();
        }

        @Override
        public Adapter caseImplementation(Implementation object) {
            return createImplementationAdapter();
        }

        @Override
        public Adapter caseInputSet(InputSet object) {
            return createInputSetAdapter();
        }

        @Override
        public Adapter caseInput(Input object) {
            return createInputAdapter();
        }

        @Override
        public Adapter caseIntermediateEvent(IntermediateEvent object) {
            return createIntermediateEventAdapter();
        }

        @Override
        public Adapter caseIORules(IORules object) {
            return createIORulesAdapter();
        }

        @Override
        public Adapter caseJndiName(JndiName object) {
            return createJndiNameAdapter();
        }

        @Override
        public Adapter caseJoin(Join object) {
            return createJoinAdapter();
        }

        @Override
        public Adapter caseLayoutInfo(LayoutInfo object) {
            return createLayoutInfoAdapter();
        }

        @Override
        public Adapter caseLane(Lane object) {
            return createLaneAdapter();
        }

        @Override
        public Adapter caseLength(Length object) {
            return createLengthAdapter();
        }

        @Override
        public Adapter caseLimit(Limit object) {
            return createLimitAdapter();
        }

        @Override
        public Adapter caseListType(ListType object) {
            return createListTypeAdapter();
        }

        @Override
        public Adapter caseLocation(Location object) {
            return createLocationAdapter();
        }

        @Override
        public Adapter caseLoopMultiInstance(LoopMultiInstance object) {
            return createLoopMultiInstanceAdapter();
        }

        @Override
        public Adapter caseLoopStandard(LoopStandard object) {
            return createLoopStandardAdapter();
        }

        @Override
        public Adapter caseLoop(Loop object) {
            return createLoopAdapter();
        }

        @Override
        public Adapter caseMember(Member object) {
            return createMemberAdapter();
        }

        @Override
        public Adapter caseMessageFlow(MessageFlow object) {
            return createMessageFlowAdapter();
        }

        @Override
        public Adapter caseMessage(Message object) {
            return createMessageAdapter();
        }

        @Override
        public Adapter caseMethod(Method object) {
            return createMethodAdapter();
        }

        @Override
        public Adapter caseModificationDate(ModificationDate object) {
            return createModificationDateAdapter();
        }

        @Override
        public Adapter caseMyRole(MyRole object) {
            return createMyRoleAdapter();
        }

        @Override
        public Adapter caseNamedElement(NamedElement object) {
            return createNamedElementAdapter();
        }

        @Override
        public Adapter caseNodeGraphicsInfo(NodeGraphicsInfo object) {
            return createNodeGraphicsInfoAdapter();
        }

        @Override
        public Adapter caseNo(No object) {
            return createNoAdapter();
        }

        @Override
        public Adapter caseObject(com.tibco.xpd.xpdl2.Object object) {
            return createObjectAdapter();
        }

        @Override
        public Adapter caseOtherAttributesContainer(
                OtherAttributesContainer object) {
            return createOtherAttributesContainerAdapter();
        }

        @Override
        public Adapter caseOtherElementsContainer(OtherElementsContainer object) {
            return createOtherElementsContainerAdapter();
        }

        @Override
        public Adapter caseOutputSet(OutputSet object) {
            return createOutputSetAdapter();
        }

        @Override
        public Adapter caseOutput(Output object) {
            return createOutputAdapter();
        }

        @Override
        public Adapter casePackageHeader(PackageHeader object) {
            return createPackageHeaderAdapter();
        }

        @Override
        public Adapter casePackage(com.tibco.xpd.xpdl2.Package object) {
            return createPackageAdapter();
        }

        @Override
        public Adapter casePage(Page object) {
            return createPageAdapter();
        }

        @Override
        public Adapter casePages(Pages object) {
            return createPagesAdapter();
        }

        @Override
        public Adapter caseParticipantsContainer(ParticipantsContainer object) {
            return createParticipantsContainerAdapter();
        }

        @Override
        public Adapter caseParticipant(Participant object) {
            return createParticipantAdapter();
        }

        @Override
        public Adapter caseParticipantTypeElem(ParticipantTypeElem object) {
            return createParticipantTypeElemAdapter();
        }

        @Override
        public Adapter casePartnerLink(PartnerLink object) {
            return createPartnerLinkAdapter();
        }

        @Override
        public Adapter casePartnerLinkType(PartnerLinkType object) {
            return createPartnerLinkTypeAdapter();
        }

        @Override
        public Adapter casePartnerRole(PartnerRole object) {
            return createPartnerRoleAdapter();
        }

        @Override
        public Adapter casePartner(Partner object) {
            return createPartnerAdapter();
        }

        @Override
        public Adapter casePerformer(Performer object) {
            return createPerformerAdapter();
        }

        @Override
        public Adapter casePerformers(Performers object) {
            return createPerformersAdapter();
        }

        @Override
        public Adapter casePojoApplication(PojoApplication object) {
            return createPojoApplicationAdapter();
        }

        @Override
        public Adapter casePool(Pool object) {
            return createPoolAdapter();
        }

        @Override
        public Adapter casePrecision(Precision object) {
            return createPrecisionAdapter();
        }

        @Override
        public Adapter casePriority(Priority object) {
            return createPriorityAdapter();
        }

        @Override
        public Adapter casePriorityUnit(PriorityUnit object) {
            return createPriorityUnitAdapter();
        }

        @Override
        public Adapter caseProcessHeader(ProcessHeader object) {
            return createProcessHeaderAdapter();
        }

        @Override
        public Adapter caseProcess(com.tibco.xpd.xpdl2.Process object) {
            return createProcessAdapter();
        }

        @Override
        public Adapter caseFormalParametersContainer(
                FormalParametersContainer object) {
            return createFormalParametersContainerAdapter();
        }

        @Override
        public Adapter caseProcessRelevantData(ProcessRelevantData object) {
            return createProcessRelevantDataAdapter();
        }

        @Override
        public Adapter casePropertyInput(PropertyInput object) {
            return createPropertyInputAdapter();
        }

        @Override
        public Adapter caseRecordType(RecordType object) {
            return createRecordTypeAdapter();
        }

        @Override
        public Adapter caseRedefinableHeader(RedefinableHeader object) {
            return createRedefinableHeaderAdapter();
        }

        @Override
        public Adapter caseReference(Reference object) {
            return createReferenceAdapter();
        }

        @Override
        public Adapter caseResourceCosts(ResourceCosts object) {
            return createResourceCostsAdapter();
        }

        @Override
        public Adapter caseResponsible(Responsible object) {
            return createResponsibleAdapter();
        }

        @Override
        public Adapter caseResultError(ResultError object) {
            return createResultErrorAdapter();
        }

        @Override
        public Adapter caseResultMultiple(ResultMultiple object) {
            return createResultMultipleAdapter();
        }

        @Override
        public Adapter caseRole(Role object) {
            return createRoleAdapter();
        }

        @Override
        public Adapter caseRoute(Route object) {
            return createRouteAdapter();
        }

        @Override
        public Adapter caseRuleName(RuleName object) {
            return createRuleNameAdapter();
        }

        @Override
        public Adapter caseRule(Rule object) {
            return createRuleAdapter();
        }

        @Override
        public Adapter caseScale(Scale object) {
            return createScaleAdapter();
        }

        @Override
        public Adapter caseSchema(Schema object) {
            return createSchemaAdapter();
        }

        @Override
        public Adapter caseScript(Script object) {
            return createScriptAdapter();
        }

        @Override
        public Adapter caseService(Service object) {
            return createServiceAdapter();
        }

        @Override
        public Adapter caseSimulationInformation(SimulationInformation object) {
            return createSimulationInformationAdapter();
        }

        @Override
        public Adapter caseSplit(Split object) {
            return createSplitAdapter();
        }

        @Override
        public Adapter caseStartEvent(StartEvent object) {
            return createStartEventAdapter();
        }

        @Override
        public Adapter caseSubFlow(SubFlow object) {
            return createSubFlowAdapter();
        }

        @Override
        public Adapter caseTaskApplication(TaskApplication object) {
            return createTaskApplicationAdapter();
        }

        @Override
        public Adapter caseTaskManual(TaskManual object) {
            return createTaskManualAdapter();
        }

        @Override
        public Adapter caseTaskReceive(TaskReceive object) {
            return createTaskReceiveAdapter();
        }

        @Override
        public Adapter caseTaskReference(TaskReference object) {
            return createTaskReferenceAdapter();
        }

        @Override
        public Adapter caseTaskScript(TaskScript object) {
            return createTaskScriptAdapter();
        }

        @Override
        public Adapter caseTaskSend(TaskSend object) {
            return createTaskSendAdapter();
        }

        @Override
        public Adapter caseTaskService(TaskService object) {
            return createTaskServiceAdapter();
        }

        @Override
        public Adapter caseTask(Task object) {
            return createTaskAdapter();
        }

        @Override
        public Adapter caseTaskUser(TaskUser object) {
            return createTaskUserAdapter();
        }

        @Override
        public Adapter caseTimeEstimation(TimeEstimation object) {
            return createTimeEstimationAdapter();
        }

        @Override
        public Adapter caseTransaction(Transaction object) {
            return createTransactionAdapter();
        }

        @Override
        public Adapter caseTransitionRef(TransitionRef object) {
            return createTransitionRefAdapter();
        }

        @Override
        public Adapter caseTransitionRestriction(TransitionRestriction object) {
            return createTransitionRestrictionAdapter();
        }

        @Override
        public Adapter caseTransition(Transition object) {
            return createTransitionAdapter();
        }

        @Override
        public Adapter caseTriggerIntermediateMultiple(
                TriggerIntermediateMultiple object) {
            return createTriggerIntermediateMultipleAdapter();
        }

        @Override
        public Adapter caseTriggerMultiple(TriggerMultiple object) {
            return createTriggerMultipleAdapter();
        }

        @Override
        public Adapter caseTriggerResultCancel(TriggerResultCancel object) {
            return createTriggerResultCancelAdapter();
        }

        @Override
        public Adapter caseTriggerResultCompensation(
                TriggerResultCompensation object) {
            return createTriggerResultCompensationAdapter();
        }

        @Override
        public Adapter caseTriggerResultSignal(TriggerResultSignal object) {
            return createTriggerResultSignalAdapter();
        }

        @Override
        public Adapter caseTriggerResultLink(TriggerResultLink object) {
            return createTriggerResultLinkAdapter();
        }

        @Override
        public Adapter caseTriggerResultMessage(TriggerResultMessage object) {
            return createTriggerResultMessageAdapter();
        }

        @Override
        public Adapter caseTriggerConditional(TriggerConditional object) {
            return createTriggerConditionalAdapter();
        }

        @Override
        public Adapter caseTriggerTimer(TriggerTimer object) {
            return createTriggerTimerAdapter();
        }

        @Override
        public Adapter caseTypeDeclaration(TypeDeclaration object) {
            return createTypeDeclarationAdapter();
        }

        @Override
        public Adapter caseUnionType(UnionType object) {
            return createUnionTypeAdapter();
        }

        @Override
        public Adapter caseUniqueIdElement(UniqueIdElement object) {
            return createUniqueIdElementAdapter();
        }

        @Override
        public Adapter caseValidFrom(ValidFrom object) {
            return createValidFromAdapter();
        }

        @Override
        public Adapter caseValidTo(ValidTo object) {
            return createValidToAdapter();
        }

        @Override
        public Adapter caseVendorExtensions(VendorExtensions object) {
            return createVendorExtensionsAdapter();
        }

        @Override
        public Adapter caseVendorExtension(VendorExtension object) {
            return createVendorExtensionAdapter();
        }

        @Override
        public Adapter caseWaitingTime(WaitingTime object) {
            return createWaitingTimeAdapter();
        }

        @Override
        public Adapter caseWebServiceFaultCatch(WebServiceFaultCatch object) {
            return createWebServiceFaultCatchAdapter();
        }

        @Override
        public Adapter caseWebServiceOperation(WebServiceOperation object) {
            return createWebServiceOperationAdapter();
        }

        @Override
        public Adapter caseWebServiceApplication(WebServiceApplication object) {
            return createWebServiceApplicationAdapter();
        }

        @Override
        public Adapter caseWorkingTime(WorkingTime object) {
            return createWorkingTimeAdapter();
        }

        @Override
        public Adapter caseXsltApplication(XsltApplication object) {
            return createXsltApplicationAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ActivitySet <em>Activity Set</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ActivitySet
     * @generated
     */
    public Adapter createActivitySetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Activity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Activity
     * @generated
     */
    public Adapter createActivityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ApplicationType <em>Application Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ApplicationType
     * @generated
     */
    public Adapter createApplicationTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Application <em>Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Application
     * @generated
     */
    public Adapter createApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ExtendedAttributesContainer <em>Extended Attributes Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ExtendedAttributesContainer
     * @generated
     */
    public Adapter createExtendedAttributesContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ArrayType <em>Array Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ArrayType
     * @generated
     */
    public Adapter createArrayTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Artifact <em>Artifact</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Artifact
     * @generated
     */
    public Adapter createArtifactAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ArtifactInput <em>Artifact Input</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ArtifactInput
     * @generated
     */
    public Adapter createArtifactInputAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.GraphicalNode <em>Graphical Node</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.GraphicalNode
     * @generated
     */
    public Adapter createGraphicalNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.GraphicalConnector <em>Graphical Connector</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.GraphicalConnector
     * @generated
     */
    public Adapter createGraphicalConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Group <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Group
     * @generated
     */
    public Adapter createGroupAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Assignment <em>Assignment</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Assignment
     * @generated
     */
    public Adapter createAssignmentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Association <em>Association</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Association
     * @generated
     */
    public Adapter createAssociationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.BasicType <em>Basic Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.BasicType
     * @generated
     */
    public Adapter createBasicTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.BlockActivity <em>Block Activity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.BlockActivity
     * @generated
     */
    public Adapter createBlockActivityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.BusinessRuleApplication <em>Business Rule Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.BusinessRuleApplication
     * @generated
     */
    public Adapter createBusinessRuleApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Category <em>Category</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Category
     * @generated
     */
    public Adapter createCategoryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Class <em>Class</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Class
     * @generated
     */
    public Adapter createClassAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Codepage <em>Codepage</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Codepage
     * @generated
     */
    public Adapter createCodepageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Condition <em>Condition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Condition
     * @generated
     */
    public Adapter createConditionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ConformanceClass <em>Conformance Class</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ConformanceClass
     * @generated
     */
    public Adapter createConformanceClassAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo <em>Connector Graphics Info</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo
     * @generated
     */
    public Adapter createConnectorGraphicsInfoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Coordinates <em>Coordinates</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Coordinates
     * @generated
     */
    public Adapter createCoordinatesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Cost <em>Cost</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Cost
     * @generated
     */
    public Adapter createCostAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.CostStructure <em>Cost Structure</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.CostStructure
     * @generated
     */
    public Adapter createCostStructureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.CostUnit <em>Cost Unit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.CostUnit
     * @generated
     */
    public Adapter createCostUnitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.CountryKey <em>Country Key</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.CountryKey
     * @generated
     */
    public Adapter createCountryKeyAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DataField <em>Data Field</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DataField
     * @generated
     */
    public Adapter createDataFieldAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DataMapping <em>Data Mapping</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DataMapping
     * @generated
     */
    public Adapter createDataMappingAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DataObject <em>Data Object</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DataObject
     * @generated
     */
    public Adapter createDataObjectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DataType
     * @generated
     */
    public Adapter createDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Deadline <em>Deadline</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Deadline
     * @generated
     */
    public Adapter createDeadlineAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DeclaredType <em>Declared Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DeclaredType
     * @generated
     */
    public Adapter createDeclaredTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DeprecatedResultCompensation <em>Deprecated Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DeprecatedResultCompensation
     * @generated
     */
    public Adapter createDeprecatedResultCompensationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DeprecatedTriggerRule
     * @generated
     */
    public Adapter createDeprecatedTriggerRuleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Description <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Description
     * @generated
     */
    public Adapter createDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Documentation <em>Documentation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Documentation
     * @generated
     */
    public Adapter createDocumentationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Duration <em>Duration</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Duration
     * @generated
     */
    public Adapter createDurationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.EjbApplication <em>Ejb Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.EjbApplication
     * @generated
     */
    public Adapter createEjbApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.EndEvent <em>End Event</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.EndEvent
     * @generated
     */
    public Adapter createEndEventAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.EndPoint <em>End Point</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.EndPoint
     * @generated
     */
    public Adapter createEndPointAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.EnumerationType <em>Enumeration Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.EnumerationType
     * @generated
     */
    public Adapter createEnumerationTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.EnumerationValue <em>Enumeration Value</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.EnumerationValue
     * @generated
     */
    public Adapter createEnumerationValueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Event <em>Event</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Event
     * @generated
     */
    public Adapter createEventAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ExceptionName <em>Exception Name</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ExceptionName
     * @generated
     */
    public Adapter createExceptionNameAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Expression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Expression
     * @generated
     */
    public Adapter createExpressionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ExtendedAttribute <em>Extended Attribute</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ExtendedAttribute
     * @generated
     */
    public Adapter createExtendedAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ExternalPackage <em>External Package</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ExternalPackage
     * @generated
     */
    public Adapter createExternalPackageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ExternalReference
     * @generated
     */
    public Adapter createExternalReferenceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.FormalParameter <em>Formal Parameter</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.FormalParameter
     * @generated
     */
    public Adapter createFormalParameterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.FormLayout <em>Form Layout</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.FormLayout
     * @generated
     */
    public Adapter createFormLayoutAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.FormApplication <em>Form Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.FormApplication
     * @generated
     */
    public Adapter createFormApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.HomeClass <em>Home Class</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.HomeClass
     * @generated
     */
    public Adapter createHomeClassAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Icon <em>Icon</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Icon
     * @generated
     */
    public Adapter createIconAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Implementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Implementation
     * @generated
     */
    public Adapter createImplementationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.InputSet <em>Input Set</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.InputSet
     * @generated
     */
    public Adapter createInputSetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Input <em>Input</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Input
     * @generated
     */
    public Adapter createInputAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.IntermediateEvent <em>Intermediate Event</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent
     * @generated
     */
    public Adapter createIntermediateEventAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.IORules <em>IO Rules</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.IORules
     * @generated
     */
    public Adapter createIORulesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.JndiName <em>Jndi Name</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.JndiName
     * @generated
     */
    public Adapter createJndiNameAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Join <em>Join</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Join
     * @generated
     */
    public Adapter createJoinAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.LayoutInfo <em>Layout Info</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.LayoutInfo
     * @generated
     */
    public Adapter createLayoutInfoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Lane <em>Lane</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Lane
     * @generated
     */
    public Adapter createLaneAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Length <em>Length</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Length
     * @generated
     */
    public Adapter createLengthAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Limit <em>Limit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Limit
     * @generated
     */
    public Adapter createLimitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ListType <em>List Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ListType
     * @generated
     */
    public Adapter createListTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Location <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Location
     * @generated
     */
    public Adapter createLocationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.LoopMultiInstance <em>Loop Multi Instance</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance
     * @generated
     */
    public Adapter createLoopMultiInstanceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.LoopStandard <em>Loop Standard</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.LoopStandard
     * @generated
     */
    public Adapter createLoopStandardAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Loop <em>Loop</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Loop
     * @generated
     */
    public Adapter createLoopAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Member <em>Member</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Member
     * @generated
     */
    public Adapter createMemberAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.MessageFlow <em>Message Flow</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.MessageFlow
     * @generated
     */
    public Adapter createMessageFlowAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Message <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Message
     * @generated
     */
    public Adapter createMessageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Method <em>Method</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Method
     * @generated
     */
    public Adapter createMethodAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ModificationDate <em>Modification Date</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ModificationDate
     * @generated
     */
    public Adapter createModificationDateAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.MyRole <em>My Role</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.MyRole
     * @generated
     */
    public Adapter createMyRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo <em>Node Graphics Info</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo
     * @generated
     */
    public Adapter createNodeGraphicsInfoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.No <em>No</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.No
     * @generated
     */
    public Adapter createNoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Object <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Object
     * @generated
     */
    public Adapter createObjectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.OutputSet <em>Output Set</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.OutputSet
     * @generated
     */
    public Adapter createOutputSetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Output <em>Output</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Output
     * @generated
     */
    public Adapter createOutputAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.PackageHeader <em>Package Header</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.PackageHeader
     * @generated
     */
    public Adapter createPackageHeaderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Package <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Package
     * @generated
     */
    public Adapter createPackageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Page <em>Page</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Page
     * @generated
     */
    public Adapter createPageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Pages <em>Pages</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Pages
     * @generated
     */
    public Adapter createPagesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ApplicationsContainer <em>Applications Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ApplicationsContainer
     * @generated
     */
    public Adapter createApplicationsContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ParticipantsContainer <em>Participants Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ParticipantsContainer
     * @generated
     */
    public Adapter createParticipantsContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DataFieldsContainer <em>Data Fields Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DataFieldsContainer
     * @generated
     */
    public Adapter createDataFieldsContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Participant <em>Participant</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Participant
     * @generated
     */
    public Adapter createParticipantAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ParticipantTypeElem <em>Participant Type Elem</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ParticipantTypeElem
     * @generated
     */
    public Adapter createParticipantTypeElemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.PartnerLink <em>Partner Link</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.PartnerLink
     * @generated
     */
    public Adapter createPartnerLinkAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.PartnerLinkType <em>Partner Link Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.PartnerLinkType
     * @generated
     */
    public Adapter createPartnerLinkTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.PartnerRole <em>Partner Role</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.PartnerRole
     * @generated
     */
    public Adapter createPartnerRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Partner <em>Partner</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Partner
     * @generated
     */
    public Adapter createPartnerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Performer <em>Performer</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Performer
     * @generated
     */
    public Adapter createPerformerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.PojoApplication <em>Pojo Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.PojoApplication
     * @generated
     */
    public Adapter createPojoApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Pool <em>Pool</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Pool
     * @generated
     */
    public Adapter createPoolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Precision <em>Precision</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Precision
     * @generated
     */
    public Adapter createPrecisionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Priority <em>Priority</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Priority
     * @generated
     */
    public Adapter createPriorityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.PriorityUnit <em>Priority Unit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.PriorityUnit
     * @generated
     */
    public Adapter createPriorityUnitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ProcessHeader <em>Process Header</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ProcessHeader
     * @generated
     */
    public Adapter createProcessHeaderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Process <em>Process</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Process
     * @generated
     */
    public Adapter createProcessAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.FormalParametersContainer <em>Formal Parameters Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.FormalParametersContainer
     * @generated
     */
    public Adapter createFormalParametersContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.AssigmentsContainer <em>Assigments Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.AssigmentsContainer
     * @generated
     */
    public Adapter createAssigmentsContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.RecordType <em>Record Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.RecordType
     * @generated
     */
    public Adapter createRecordTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.RedefinableHeader <em>Redefinable Header</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader
     * @generated
     */
    public Adapter createRedefinableHeaderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Reference <em>Reference</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Reference
     * @generated
     */
    public Adapter createReferenceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ResourceCosts <em>Resource Costs</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ResourceCosts
     * @generated
     */
    public Adapter createResourceCostsAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Responsible <em>Responsible</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Responsible
     * @generated
     */
    public Adapter createResponsibleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ResultError <em>Result Error</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ResultError
     * @generated
     */
    public Adapter createResultErrorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ResultMultiple <em>Result Multiple</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ResultMultiple
     * @generated
     */
    public Adapter createResultMultipleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Role <em>Role</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Role
     * @generated
     */
    public Adapter createRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Route <em>Route</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Route
     * @generated
     */
    public Adapter createRouteAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.RuleName <em>Rule Name</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.RuleName
     * @generated
     */
    public Adapter createRuleNameAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Rule <em>Rule</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Rule
     * @generated
     */
    public Adapter createRuleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Scale <em>Scale</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Scale
     * @generated
     */
    public Adapter createScaleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Schema <em>Schema</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Schema
     * @generated
     */
    public Adapter createSchemaAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Script <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Script
     * @generated
     */
    public Adapter createScriptAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Service <em>Service</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Service
     * @generated
     */
    public Adapter createServiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.SimulationInformation <em>Simulation Information</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.SimulationInformation
     * @generated
     */
    public Adapter createSimulationInformationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Split <em>Split</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Split
     * @generated
     */
    public Adapter createSplitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.StartEvent <em>Start Event</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.StartEvent
     * @generated
     */
    public Adapter createStartEventAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.SubFlow <em>Sub Flow</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.SubFlow
     * @generated
     */
    public Adapter createSubFlowAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskApplication <em>Task Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskApplication
     * @generated
     */
    public Adapter createTaskApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskManual <em>Task Manual</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskManual
     * @generated
     */
    public Adapter createTaskManualAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskReceive <em>Task Receive</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskReceive
     * @generated
     */
    public Adapter createTaskReceiveAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskReference <em>Task Reference</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskReference
     * @generated
     */
    public Adapter createTaskReferenceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskScript <em>Task Script</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskScript
     * @generated
     */
    public Adapter createTaskScriptAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskSend <em>Task Send</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskSend
     * @generated
     */
    public Adapter createTaskSendAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskService <em>Task Service</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskService
     * @generated
     */
    public Adapter createTaskServiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Task <em>Task</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Task
     * @generated
     */
    public Adapter createTaskAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TaskUser <em>Task User</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TaskUser
     * @generated
     */
    public Adapter createTaskUserAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TimeEstimation <em>Time Estimation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TimeEstimation
     * @generated
     */
    public Adapter createTimeEstimationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Transaction <em>Transaction</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Transaction
     * @generated
     */
    public Adapter createTransactionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TransitionRef <em>Transition Ref</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TransitionRef
     * @generated
     */
    public Adapter createTransitionRefAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TransitionRestriction <em>Transition Restriction</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TransitionRestriction
     * @generated
     */
    public Adapter createTransitionRestrictionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Transition <em>Transition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Transition
     * @generated
     */
    public Adapter createTransitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple <em>Trigger Intermediate Multiple</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple
     * @generated
     */
    public Adapter createTriggerIntermediateMultipleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerMultiple <em>Trigger Multiple</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerMultiple
     * @generated
     */
    public Adapter createTriggerMultipleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerResultCancel <em>Trigger Result Cancel</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerResultCancel
     * @generated
     */
    public Adapter createTriggerResultCancelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerResultCompensation <em>Trigger Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerResultCompensation
     * @generated
     */
    public Adapter createTriggerResultCompensationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerResultSignal <em>Trigger Result Signal</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerResultSignal
     * @generated
     */
    public Adapter createTriggerResultSignalAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerResultLink <em>Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerResultLink
     * @generated
     */
    public Adapter createTriggerResultLinkAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerResultMessage
     * @generated
     */
    public Adapter createTriggerResultMessageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerConditional <em>Trigger Conditional</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerConditional
     * @generated
     */
    public Adapter createTriggerConditionalAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TriggerTimer <em>Trigger Timer</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TriggerTimer
     * @generated
     */
    public Adapter createTriggerTimerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.TypeDeclaration <em>Type Declaration</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration
     * @generated
     */
    public Adapter createTypeDeclarationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.UnionType <em>Union Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.UnionType
     * @generated
     */
    public Adapter createUnionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ValidFrom <em>Valid From</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ValidFrom
     * @generated
     */
    public Adapter createValidFromAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ValidTo <em>Valid To</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ValidTo
     * @generated
     */
    public Adapter createValidToAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.VendorExtensions <em>Vendor Extensions</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.VendorExtensions
     * @generated
     */
    public Adapter createVendorExtensionsAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.VendorExtension <em>Vendor Extension</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.VendorExtension
     * @generated
     */
    public Adapter createVendorExtensionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.WaitingTime <em>Waiting Time</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.WaitingTime
     * @generated
     */
    public Adapter createWaitingTimeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch <em>Web Service Fault Catch</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.WebServiceFaultCatch
     * @generated
     */
    public Adapter createWebServiceFaultCatchAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.WebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.WebServiceOperation
     * @generated
     */
    public Adapter createWebServiceOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.WebServiceApplication <em>Web Service Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.WebServiceApplication
     * @generated
     */
    public Adapter createWebServiceApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.WorkingTime <em>Working Time</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.WorkingTime
     * @generated
     */
    public Adapter createWorkingTimeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.XsltApplication <em>Xslt Application</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.XsltApplication
     * @generated
     */
    public Adapter createXsltApplicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.FlowContainer <em>Flow Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.FlowContainer
     * @generated
     */
    public Adapter createFlowContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DescribedElement <em>Described Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.DescribedElement
     * @generated
     */
    public Adapter createDescribedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ProcessRelevantData <em>Process Relevant Data</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.ProcessRelevantData
     * @generated
     */
    public Adapter createProcessRelevantDataAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.PropertyInput <em>Property Input</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.PropertyInput
     * @generated
     */
    public Adapter createPropertyInputAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.UniqueIdElement <em>Unique Id Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.UniqueIdElement
     * @generated
     */
    public Adapter createUniqueIdElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.OtherAttributesContainer <em>Other Attributes Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.OtherAttributesContainer
     * @generated
     */
    public Adapter createOtherAttributesContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.OtherElementsContainer <em>Other Elements Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.OtherElementsContainer
     * @generated
     */
    public Adapter createOtherElementsContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.Performers <em>Performers</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.xpdl2.Performers
     * @generated
     */
    public Adapter createPerformersAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //Xpdl2AdapterFactory
