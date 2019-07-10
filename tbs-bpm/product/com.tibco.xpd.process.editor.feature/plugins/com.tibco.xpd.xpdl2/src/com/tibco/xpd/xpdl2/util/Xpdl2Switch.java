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
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package
 * @generated
 */
public class Xpdl2Switch<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static Xpdl2Package modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Xpdl2Switch() {
        if (modelPackage == null) {
            modelPackage = Xpdl2Package.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case Xpdl2Package.ACTIVITY_SET: {
            ActivitySet activitySet = (ActivitySet) theEObject;
            T result = caseActivitySet(activitySet);
            if (result == null)
                result = caseNamedElement(activitySet);
            if (result == null)
                result = caseFlowContainer(activitySet);
            if (result == null)
                result = caseUniqueIdElement(activitySet);
            if (result == null)
                result = caseOtherAttributesContainer(activitySet);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ACTIVITY: {
            Activity activity = (Activity) theEObject;
            T result = caseActivity(activity);
            if (result == null)
                result = caseNamedElement(activity);
            if (result == null)
                result = caseExtendedAttributesContainer(activity);
            if (result == null)
                result = caseGraphicalNode(activity);
            if (result == null)
                result = caseDescribedElement(activity);
            if (result == null)
                result = caseOtherElementsContainer(activity);
            if (result == null)
                result = caseDataFieldsContainer(activity);
            if (result == null)
                result = caseUniqueIdElement(activity);
            if (result == null)
                result = caseOtherAttributesContainer(activity);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.APPLICATION_TYPE: {
            ApplicationType applicationType = (ApplicationType) theEObject;
            T result = caseApplicationType(applicationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.APPLICATION: {
            Application application = (Application) theEObject;
            T result = caseApplication(application);
            if (result == null)
                result = caseNamedElement(application);
            if (result == null)
                result = caseExtendedAttributesContainer(application);
            if (result == null)
                result = caseFormalParametersContainer(application);
            if (result == null)
                result = caseDescribedElement(application);
            if (result == null)
                result = caseUniqueIdElement(application);
            if (result == null)
                result = caseOtherAttributesContainer(application);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.APPLICATIONS_CONTAINER: {
            ApplicationsContainer applicationsContainer = (ApplicationsContainer) theEObject;
            T result = caseApplicationsContainer(applicationsContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ARRAY_TYPE: {
            ArrayType arrayType = (ArrayType) theEObject;
            T result = caseArrayType(arrayType);
            if (result == null)
                result = caseDataType(arrayType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ARTIFACT: {
            Artifact artifact = (Artifact) theEObject;
            T result = caseArtifact(artifact);
            if (result == null)
                result = caseNamedElement(artifact);
            if (result == null)
                result = caseGraphicalNode(artifact);
            if (result == null)
                result = caseUniqueIdElement(artifact);
            if (result == null)
                result = caseOtherAttributesContainer(artifact);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ARTIFACT_INPUT: {
            ArtifactInput artifactInput = (ArtifactInput) theEObject;
            T result = caseArtifactInput(artifactInput);
            if (result == null)
                result = caseOtherElementsContainer(artifactInput);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ASSIGNMENT: {
            Assignment assignment = (Assignment) theEObject;
            T result = caseAssignment(assignment);
            if (result == null)
                result = caseOtherElementsContainer(assignment);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ASSIGMENTS_CONTAINER: {
            AssigmentsContainer assigmentsContainer = (AssigmentsContainer) theEObject;
            T result = caseAssigmentsContainer(assigmentsContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ASSOCIATION: {
            Association association = (Association) theEObject;
            T result = caseAssociation(association);
            if (result == null)
                result = caseNamedElement(association);
            if (result == null)
                result = caseGraphicalConnector(association);
            if (result == null)
                result = caseUniqueIdElement(association);
            if (result == null)
                result = caseOtherAttributesContainer(association);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.BASIC_TYPE: {
            BasicType basicType = (BasicType) theEObject;
            T result = caseBasicType(basicType);
            if (result == null)
                result = caseDataType(basicType);
            if (result == null)
                result = caseOtherElementsContainer(basicType);
            if (result == null)
                result = caseOtherAttributesContainer(basicType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.BLOCK_ACTIVITY: {
            BlockActivity blockActivity = (BlockActivity) theEObject;
            T result = caseBlockActivity(blockActivity);
            if (result == null)
                result = caseOtherAttributesContainer(blockActivity);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.BUSINESS_RULE_APPLICATION: {
            BusinessRuleApplication businessRuleApplication = (BusinessRuleApplication) theEObject;
            T result = caseBusinessRuleApplication(businessRuleApplication);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.CATEGORY: {
            Category category = (Category) theEObject;
            T result = caseCategory(category);
            if (result == null)
                result = caseNamedElement(category);
            if (result == null)
                result = caseUniqueIdElement(category);
            if (result == null)
                result = caseOtherAttributesContainer(category);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.CLASS: {
            com.tibco.xpd.xpdl2.Class class_ = (com.tibco.xpd.xpdl2.Class) theEObject;
            T result = caseClass(class_);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.CODEPAGE: {
            Codepage codepage = (Codepage) theEObject;
            T result = caseCodepage(codepage);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.CONDITION: {
            Condition condition = (Condition) theEObject;
            T result = caseCondition(condition);
            if (result == null)
                result = caseOtherAttributesContainer(condition);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.CONFORMANCE_CLASS: {
            ConformanceClass conformanceClass = (ConformanceClass) theEObject;
            T result = caseConformanceClass(conformanceClass);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.CONNECTOR_GRAPHICS_INFO: {
            ConnectorGraphicsInfo connectorGraphicsInfo = (ConnectorGraphicsInfo) theEObject;
            T result = caseConnectorGraphicsInfo(connectorGraphicsInfo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.COORDINATES: {
            Coordinates coordinates = (Coordinates) theEObject;
            T result = caseCoordinates(coordinates);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.COST: {
            Cost cost = (Cost) theEObject;
            T result = caseCost(cost);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.COST_STRUCTURE: {
            CostStructure costStructure = (CostStructure) theEObject;
            T result = caseCostStructure(costStructure);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.COST_UNIT: {
            CostUnit costUnit = (CostUnit) theEObject;
            T result = caseCostUnit(costUnit);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.COUNTRY_KEY: {
            CountryKey countryKey = (CountryKey) theEObject;
            T result = caseCountryKey(countryKey);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DATA_FIELD: {
            DataField dataField = (DataField) theEObject;
            T result = caseDataField(dataField);
            if (result == null)
                result = caseProcessRelevantData(dataField);
            if (result == null)
                result = caseExtendedAttributesContainer(dataField);
            if (result == null)
                result = caseNamedElement(dataField);
            if (result == null)
                result = caseDescribedElement(dataField);
            if (result == null)
                result = caseOtherElementsContainer(dataField);
            if (result == null)
                result = caseUniqueIdElement(dataField);
            if (result == null)
                result = caseOtherAttributesContainer(dataField);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DATA_FIELDS_CONTAINER: {
            DataFieldsContainer dataFieldsContainer = (DataFieldsContainer) theEObject;
            T result = caseDataFieldsContainer(dataFieldsContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DATA_MAPPING: {
            DataMapping dataMapping = (DataMapping) theEObject;
            T result = caseDataMapping(dataMapping);
            if (result == null)
                result = caseOtherElementsContainer(dataMapping);
            if (result == null)
                result = caseOtherAttributesContainer(dataMapping);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DATA_OBJECT: {
            DataObject dataObject = (DataObject) theEObject;
            T result = caseDataObject(dataObject);
            if (result == null)
                result = caseNamedElement(dataObject);
            if (result == null)
                result = caseDataFieldsContainer(dataObject);
            if (result == null)
                result = caseOtherElementsContainer(dataObject);
            if (result == null)
                result = caseUniqueIdElement(dataObject);
            if (result == null)
                result = caseOtherAttributesContainer(dataObject);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DATA_TYPE: {
            DataType dataType = (DataType) theEObject;
            T result = caseDataType(dataType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DEADLINE: {
            Deadline deadline = (Deadline) theEObject;
            T result = caseDeadline(deadline);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DECLARED_TYPE: {
            DeclaredType declaredType = (DeclaredType) theEObject;
            T result = caseDeclaredType(declaredType);
            if (result == null)
                result = caseDataType(declaredType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DEPRECATED_RESULT_COMPENSATION: {
            DeprecatedResultCompensation deprecatedResultCompensation = (DeprecatedResultCompensation) theEObject;
            T result = caseDeprecatedResultCompensation(deprecatedResultCompensation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DEPRECATED_TRIGGER_RULE: {
            DeprecatedTriggerRule deprecatedTriggerRule = (DeprecatedTriggerRule) theEObject;
            T result = caseDeprecatedTriggerRule(deprecatedTriggerRule);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DESCRIBED_ELEMENT: {
            DescribedElement describedElement = (DescribedElement) theEObject;
            T result = caseDescribedElement(describedElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DESCRIPTION: {
            Description description = (Description) theEObject;
            T result = caseDescription(description);
            if (result == null)
                result = caseOtherAttributesContainer(description);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DOCUMENTATION: {
            Documentation documentation = (Documentation) theEObject;
            T result = caseDocumentation(documentation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DOCUMENT_ROOT: {
            DocumentRoot documentRoot = (DocumentRoot) theEObject;
            T result = caseDocumentRoot(documentRoot);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.DURATION: {
            Duration duration = (Duration) theEObject;
            T result = caseDuration(duration);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EJB_APPLICATION: {
            EjbApplication ejbApplication = (EjbApplication) theEObject;
            T result = caseEjbApplication(ejbApplication);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.END_EVENT: {
            EndEvent endEvent = (EndEvent) theEObject;
            T result = caseEndEvent(endEvent);
            if (result == null)
                result = caseEvent(endEvent);
            if (result == null)
                result = caseOtherAttributesContainer(endEvent);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.END_POINT: {
            EndPoint endPoint = (EndPoint) theEObject;
            T result = caseEndPoint(endPoint);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ENUMERATION_TYPE: {
            EnumerationType enumerationType = (EnumerationType) theEObject;
            T result = caseEnumerationType(enumerationType);
            if (result == null)
                result = caseDataType(enumerationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ENUMERATION_VALUE: {
            EnumerationValue enumerationValue = (EnumerationValue) theEObject;
            T result = caseEnumerationValue(enumerationValue);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EVENT: {
            Event event = (Event) theEObject;
            T result = caseEvent(event);
            if (result == null)
                result = caseOtherAttributesContainer(event);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EXCEPTION_NAME: {
            ExceptionName exceptionName = (ExceptionName) theEObject;
            T result = caseExceptionName(exceptionName);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EXPRESSION: {
            Expression expression = (Expression) theEObject;
            T result = caseExpression(expression);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EXTENDED_ATTRIBUTE: {
            ExtendedAttribute extendedAttribute = (ExtendedAttribute) theEObject;
            T result = caseExtendedAttribute(extendedAttribute);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER: {
            ExtendedAttributesContainer extendedAttributesContainer = (ExtendedAttributesContainer) theEObject;
            T result = caseExtendedAttributesContainer(extendedAttributesContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EXTERNAL_PACKAGE: {
            ExternalPackage externalPackage = (ExternalPackage) theEObject;
            T result = caseExternalPackage(externalPackage);
            if (result == null)
                result = caseNamedElement(externalPackage);
            if (result == null)
                result = caseExtendedAttributesContainer(externalPackage);
            if (result == null)
                result = caseUniqueIdElement(externalPackage);
            if (result == null)
                result = caseOtherAttributesContainer(externalPackage);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.EXTERNAL_REFERENCE: {
            ExternalReference externalReference = (ExternalReference) theEObject;
            T result = caseExternalReference(externalReference);
            if (result == null)
                result = caseDataType(externalReference);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.FLOW_CONTAINER: {
            FlowContainer flowContainer = (FlowContainer) theEObject;
            T result = caseFlowContainer(flowContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.FORMAL_PARAMETER: {
            FormalParameter formalParameter = (FormalParameter) theEObject;
            T result = caseFormalParameter(formalParameter);
            if (result == null)
                result = caseProcessRelevantData(formalParameter);
            if (result == null)
                result = caseNamedElement(formalParameter);
            if (result == null)
                result = caseDescribedElement(formalParameter);
            if (result == null)
                result = caseOtherElementsContainer(formalParameter);
            if (result == null)
                result = caseUniqueIdElement(formalParameter);
            if (result == null)
                result = caseOtherAttributesContainer(formalParameter);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.FORM_LAYOUT: {
            FormLayout formLayout = (FormLayout) theEObject;
            T result = caseFormLayout(formLayout);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.FORM_APPLICATION: {
            FormApplication formApplication = (FormApplication) theEObject;
            T result = caseFormApplication(formApplication);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.GRAPHICAL_NODE: {
            GraphicalNode graphicalNode = (GraphicalNode) theEObject;
            T result = caseGraphicalNode(graphicalNode);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.GRAPHICAL_CONNECTOR: {
            GraphicalConnector graphicalConnector = (GraphicalConnector) theEObject;
            T result = caseGraphicalConnector(graphicalConnector);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.GROUP: {
            Group group = (Group) theEObject;
            T result = caseGroup(group);
            if (result == null)
                result = caseNamedElement(group);
            if (result == null)
                result = caseOtherElementsContainer(group);
            if (result == null)
                result = caseUniqueIdElement(group);
            if (result == null)
                result = caseOtherAttributesContainer(group);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.HOME_CLASS: {
            HomeClass homeClass = (HomeClass) theEObject;
            T result = caseHomeClass(homeClass);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ICON: {
            Icon icon = (Icon) theEObject;
            T result = caseIcon(icon);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.IMPLEMENTATION: {
            Implementation implementation = (Implementation) theEObject;
            T result = caseImplementation(implementation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.INPUT_SET: {
            InputSet inputSet = (InputSet) theEObject;
            T result = caseInputSet(inputSet);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.INPUT: {
            Input input = (Input) theEObject;
            T result = caseInput(input);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.INTERMEDIATE_EVENT: {
            IntermediateEvent intermediateEvent = (IntermediateEvent) theEObject;
            T result = caseIntermediateEvent(intermediateEvent);
            if (result == null)
                result = caseEvent(intermediateEvent);
            if (result == null)
                result = caseOtherAttributesContainer(intermediateEvent);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.IO_RULES: {
            IORules ioRules = (IORules) theEObject;
            T result = caseIORules(ioRules);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.JNDI_NAME: {
            JndiName jndiName = (JndiName) theEObject;
            T result = caseJndiName(jndiName);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.JOIN: {
            Join join = (Join) theEObject;
            T result = caseJoin(join);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LAYOUT_INFO: {
            LayoutInfo layoutInfo = (LayoutInfo) theEObject;
            T result = caseLayoutInfo(layoutInfo);
            if (result == null)
                result = caseOtherElementsContainer(layoutInfo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LANE: {
            Lane lane = (Lane) theEObject;
            T result = caseLane(lane);
            if (result == null)
                result = caseNamedElement(lane);
            if (result == null)
                result = caseGraphicalNode(lane);
            if (result == null)
                result = caseOtherElementsContainer(lane);
            if (result == null)
                result = caseUniqueIdElement(lane);
            if (result == null)
                result = caseOtherAttributesContainer(lane);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LENGTH: {
            Length length = (Length) theEObject;
            T result = caseLength(length);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LIMIT: {
            Limit limit = (Limit) theEObject;
            T result = caseLimit(limit);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LIST_TYPE: {
            ListType listType = (ListType) theEObject;
            T result = caseListType(listType);
            if (result == null)
                result = caseDataType(listType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LOCATION: {
            Location location = (Location) theEObject;
            T result = caseLocation(location);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LOOP_MULTI_INSTANCE: {
            LoopMultiInstance loopMultiInstance = (LoopMultiInstance) theEObject;
            T result = caseLoopMultiInstance(loopMultiInstance);
            if (result == null)
                result = caseOtherElementsContainer(loopMultiInstance);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LOOP_STANDARD: {
            LoopStandard loopStandard = (LoopStandard) theEObject;
            T result = caseLoopStandard(loopStandard);
            if (result == null)
                result = caseOtherElementsContainer(loopStandard);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.LOOP: {
            Loop loop = (Loop) theEObject;
            T result = caseLoop(loop);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.MEMBER: {
            Member member = (Member) theEObject;
            T result = caseMember(member);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.MESSAGE_FLOW: {
            MessageFlow messageFlow = (MessageFlow) theEObject;
            T result = caseMessageFlow(messageFlow);
            if (result == null)
                result = caseNamedElement(messageFlow);
            if (result == null)
                result = caseGraphicalConnector(messageFlow);
            if (result == null)
                result = caseOtherElementsContainer(messageFlow);
            if (result == null)
                result = caseUniqueIdElement(messageFlow);
            if (result == null)
                result = caseOtherAttributesContainer(messageFlow);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.MESSAGE: {
            Message message = (Message) theEObject;
            T result = caseMessage(message);
            if (result == null)
                result = caseNamedElement(message);
            if (result == null)
                result = caseOtherElementsContainer(message);
            if (result == null)
                result = caseUniqueIdElement(message);
            if (result == null)
                result = caseOtherAttributesContainer(message);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.METHOD: {
            Method method = (Method) theEObject;
            T result = caseMethod(method);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.MODIFICATION_DATE: {
            ModificationDate modificationDate = (ModificationDate) theEObject;
            T result = caseModificationDate(modificationDate);
            if (result == null)
                result = caseOtherAttributesContainer(modificationDate);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.MY_ROLE: {
            MyRole myRole = (MyRole) theEObject;
            T result = caseMyRole(myRole);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.NAMED_ELEMENT: {
            NamedElement namedElement = (NamedElement) theEObject;
            T result = caseNamedElement(namedElement);
            if (result == null)
                result = caseUniqueIdElement(namedElement);
            if (result == null)
                result = caseOtherAttributesContainer(namedElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.NODE_GRAPHICS_INFO: {
            NodeGraphicsInfo nodeGraphicsInfo = (NodeGraphicsInfo) theEObject;
            T result = caseNodeGraphicsInfo(nodeGraphicsInfo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.NO: {
            No no = (No) theEObject;
            T result = caseNo(no);
            if (result == null)
                result = caseImplementation(no);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.OBJECT: {
            com.tibco.xpd.xpdl2.Object object = (com.tibco.xpd.xpdl2.Object) theEObject;
            T result = caseObject(object);
            if (result == null)
                result = caseNamedElement(object);
            if (result == null)
                result = caseUniqueIdElement(object);
            if (result == null)
                result = caseOtherAttributesContainer(object);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER: {
            OtherAttributesContainer otherAttributesContainer = (OtherAttributesContainer) theEObject;
            T result = caseOtherAttributesContainer(otherAttributesContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.OTHER_ELEMENTS_CONTAINER: {
            OtherElementsContainer otherElementsContainer = (OtherElementsContainer) theEObject;
            T result = caseOtherElementsContainer(otherElementsContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.OUTPUT_SET: {
            OutputSet outputSet = (OutputSet) theEObject;
            T result = caseOutputSet(outputSet);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.OUTPUT: {
            Output output = (Output) theEObject;
            T result = caseOutput(output);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PACKAGE_HEADER: {
            PackageHeader packageHeader = (PackageHeader) theEObject;
            T result = casePackageHeader(packageHeader);
            if (result == null)
                result = caseDescribedElement(packageHeader);
            if (result == null)
                result = caseOtherAttributesContainer(packageHeader);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PACKAGE: {
            com.tibco.xpd.xpdl2.Package package_ = (com.tibco.xpd.xpdl2.Package) theEObject;
            T result = casePackage(package_);
            if (result == null)
                result = caseNamedElement(package_);
            if (result == null)
                result = caseExtendedAttributesContainer(package_);
            if (result == null)
                result = caseApplicationsContainer(package_);
            if (result == null)
                result = caseParticipantsContainer(package_);
            if (result == null)
                result = caseDataFieldsContainer(package_);
            if (result == null)
                result = caseOtherElementsContainer(package_);
            if (result == null)
                result = caseUniqueIdElement(package_);
            if (result == null)
                result = caseOtherAttributesContainer(package_);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PAGE: {
            Page page = (Page) theEObject;
            T result = casePage(page);
            if (result == null)
                result = caseNamedElement(page);
            if (result == null)
                result = caseOtherElementsContainer(page);
            if (result == null)
                result = caseUniqueIdElement(page);
            if (result == null)
                result = caseOtherAttributesContainer(page);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PAGES: {
            Pages pages = (Pages) theEObject;
            T result = casePages(pages);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PARTICIPANTS_CONTAINER: {
            ParticipantsContainer participantsContainer = (ParticipantsContainer) theEObject;
            T result = caseParticipantsContainer(participantsContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PARTICIPANT: {
            Participant participant = (Participant) theEObject;
            T result = caseParticipant(participant);
            if (result == null)
                result = caseNamedElement(participant);
            if (result == null)
                result = caseExtendedAttributesContainer(participant);
            if (result == null)
                result = caseDescribedElement(participant);
            if (result == null)
                result = caseOtherElementsContainer(participant);
            if (result == null)
                result = caseUniqueIdElement(participant);
            if (result == null)
                result = caseOtherAttributesContainer(participant);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PARTICIPANT_TYPE_ELEM: {
            ParticipantTypeElem participantTypeElem = (ParticipantTypeElem) theEObject;
            T result = caseParticipantTypeElem(participantTypeElem);
            if (result == null)
                result = caseOtherAttributesContainer(participantTypeElem);
            if (result == null)
                result = caseOtherElementsContainer(participantTypeElem);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PARTNER_LINK: {
            PartnerLink partnerLink = (PartnerLink) theEObject;
            T result = casePartnerLink(partnerLink);
            if (result == null)
                result = caseUniqueIdElement(partnerLink);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PARTNER_LINK_TYPE: {
            PartnerLinkType partnerLinkType = (PartnerLinkType) theEObject;
            T result = casePartnerLinkType(partnerLinkType);
            if (result == null)
                result = caseUniqueIdElement(partnerLinkType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PARTNER_ROLE: {
            PartnerRole partnerRole = (PartnerRole) theEObject;
            T result = casePartnerRole(partnerRole);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PARTNER: {
            Partner partner = (Partner) theEObject;
            T result = casePartner(partner);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PERFORMER: {
            Performer performer = (Performer) theEObject;
            T result = casePerformer(performer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PERFORMERS: {
            Performers performers = (Performers) theEObject;
            T result = casePerformers(performers);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.POJO_APPLICATION: {
            PojoApplication pojoApplication = (PojoApplication) theEObject;
            T result = casePojoApplication(pojoApplication);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.POOL: {
            Pool pool = (Pool) theEObject;
            T result = casePool(pool);
            if (result == null)
                result = caseNamedElement(pool);
            if (result == null)
                result = caseGraphicalNode(pool);
            if (result == null)
                result = caseOtherElementsContainer(pool);
            if (result == null)
                result = caseUniqueIdElement(pool);
            if (result == null)
                result = caseOtherAttributesContainer(pool);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PRECISION: {
            Precision precision = (Precision) theEObject;
            T result = casePrecision(precision);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PRIORITY: {
            Priority priority = (Priority) theEObject;
            T result = casePriority(priority);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PRIORITY_UNIT: {
            PriorityUnit priorityUnit = (PriorityUnit) theEObject;
            T result = casePriorityUnit(priorityUnit);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PROCESS_HEADER: {
            ProcessHeader processHeader = (ProcessHeader) theEObject;
            T result = caseProcessHeader(processHeader);
            if (result == null)
                result = caseDescribedElement(processHeader);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PROCESS: {
            com.tibco.xpd.xpdl2.Process process = (com.tibco.xpd.xpdl2.Process) theEObject;
            T result = caseProcess(process);
            if (result == null)
                result = caseNamedElement(process);
            if (result == null)
                result = caseFlowContainer(process);
            if (result == null)
                result = caseExtendedAttributesContainer(process);
            if (result == null)
                result = caseFormalParametersContainer(process);
            if (result == null)
                result = caseAssigmentsContainer(process);
            if (result == null)
                result = caseDataFieldsContainer(process);
            if (result == null)
                result = caseParticipantsContainer(process);
            if (result == null)
                result = caseApplicationsContainer(process);
            if (result == null)
                result = caseOtherElementsContainer(process);
            if (result == null)
                result = caseUniqueIdElement(process);
            if (result == null)
                result = caseOtherAttributesContainer(process);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.FORMAL_PARAMETERS_CONTAINER: {
            FormalParametersContainer formalParametersContainer = (FormalParametersContainer) theEObject;
            T result = caseFormalParametersContainer(formalParametersContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PROCESS_RELEVANT_DATA: {
            ProcessRelevantData processRelevantData = (ProcessRelevantData) theEObject;
            T result = caseProcessRelevantData(processRelevantData);
            if (result == null)
                result = caseNamedElement(processRelevantData);
            if (result == null)
                result = caseDescribedElement(processRelevantData);
            if (result == null)
                result = caseOtherElementsContainer(processRelevantData);
            if (result == null)
                result = caseUniqueIdElement(processRelevantData);
            if (result == null)
                result = caseOtherAttributesContainer(processRelevantData);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.PROPERTY_INPUT: {
            PropertyInput propertyInput = (PropertyInput) theEObject;
            T result = casePropertyInput(propertyInput);
            if (result == null)
                result = caseOtherAttributesContainer(propertyInput);
            if (result == null)
                result = caseOtherElementsContainer(propertyInput);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.RECORD_TYPE: {
            RecordType recordType = (RecordType) theEObject;
            T result = caseRecordType(recordType);
            if (result == null)
                result = caseDataType(recordType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.REDEFINABLE_HEADER: {
            RedefinableHeader redefinableHeader = (RedefinableHeader) theEObject;
            T result = caseRedefinableHeader(redefinableHeader);
            if (result == null)
                result = caseOtherAttributesContainer(redefinableHeader);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.REFERENCE: {
            Reference reference = (Reference) theEObject;
            T result = caseReference(reference);
            if (result == null)
                result = caseImplementation(reference);
            if (result == null)
                result = caseOtherAttributesContainer(reference);
            if (result == null)
                result = caseOtherElementsContainer(reference);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.RESOURCE_COSTS: {
            ResourceCosts resourceCosts = (ResourceCosts) theEObject;
            T result = caseResourceCosts(resourceCosts);
            if (result == null)
                result = caseNamedElement(resourceCosts);
            if (result == null)
                result = caseUniqueIdElement(resourceCosts);
            if (result == null)
                result = caseOtherAttributesContainer(resourceCosts);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.RESPONSIBLE: {
            Responsible responsible = (Responsible) theEObject;
            T result = caseResponsible(responsible);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.RESULT_ERROR: {
            ResultError resultError = (ResultError) theEObject;
            T result = caseResultError(resultError);
            if (result == null)
                result = caseOtherAttributesContainer(resultError);
            if (result == null)
                result = caseOtherElementsContainer(resultError);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.RESULT_MULTIPLE: {
            ResultMultiple resultMultiple = (ResultMultiple) theEObject;
            T result = caseResultMultiple(resultMultiple);
            if (result == null)
                result = caseOtherAttributesContainer(resultMultiple);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ROLE: {
            Role role = (Role) theEObject;
            T result = caseRole(role);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.ROUTE: {
            Route route = (Route) theEObject;
            T result = caseRoute(route);
            if (result == null)
                result = caseOtherElementsContainer(route);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.RULE_NAME: {
            RuleName ruleName = (RuleName) theEObject;
            T result = caseRuleName(ruleName);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.RULE: {
            Rule rule = (Rule) theEObject;
            T result = caseRule(rule);
            if (result == null)
                result = caseNamedElement(rule);
            if (result == null)
                result = caseUniqueIdElement(rule);
            if (result == null)
                result = caseOtherAttributesContainer(rule);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.SCALE: {
            Scale scale = (Scale) theEObject;
            T result = caseScale(scale);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.SCHEMA: {
            Schema schema = (Schema) theEObject;
            T result = caseSchema(schema);
            if (result == null)
                result = caseDataType(schema);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.SCRIPT: {
            Script script = (Script) theEObject;
            T result = caseScript(script);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.SERVICE: {
            Service service = (Service) theEObject;
            T result = caseService(service);
            if (result == null)
                result = caseOtherAttributesContainer(service);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.SIMULATION_INFORMATION: {
            SimulationInformation simulationInformation = (SimulationInformation) theEObject;
            T result = caseSimulationInformation(simulationInformation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.SPLIT: {
            Split split = (Split) theEObject;
            T result = caseSplit(split);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.START_EVENT: {
            StartEvent startEvent = (StartEvent) theEObject;
            T result = caseStartEvent(startEvent);
            if (result == null)
                result = caseEvent(startEvent);
            if (result == null)
                result = caseOtherAttributesContainer(startEvent);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.SUB_FLOW: {
            SubFlow subFlow = (SubFlow) theEObject;
            T result = caseSubFlow(subFlow);
            if (result == null)
                result = caseImplementation(subFlow);
            if (result == null)
                result = caseOtherAttributesContainer(subFlow);
            if (result == null)
                result = caseOtherElementsContainer(subFlow);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_APPLICATION: {
            TaskApplication taskApplication = (TaskApplication) theEObject;
            T result = caseTaskApplication(taskApplication);
            if (result == null)
                result = caseDescribedElement(taskApplication);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_MANUAL: {
            TaskManual taskManual = (TaskManual) theEObject;
            T result = caseTaskManual(taskManual);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_RECEIVE: {
            TaskReceive taskReceive = (TaskReceive) theEObject;
            T result = caseTaskReceive(taskReceive);
            if (result == null)
                result = caseOtherAttributesContainer(taskReceive);
            if (result == null)
                result = caseOtherElementsContainer(taskReceive);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_REFERENCE: {
            TaskReference taskReference = (TaskReference) theEObject;
            T result = caseTaskReference(taskReference);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_SCRIPT: {
            TaskScript taskScript = (TaskScript) theEObject;
            T result = caseTaskScript(taskScript);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_SEND: {
            TaskSend taskSend = (TaskSend) theEObject;
            T result = caseTaskSend(taskSend);
            if (result == null)
                result = caseOtherAttributesContainer(taskSend);
            if (result == null)
                result = caseOtherElementsContainer(taskSend);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_SERVICE: {
            TaskService taskService = (TaskService) theEObject;
            T result = caseTaskService(taskService);
            if (result == null)
                result = caseOtherAttributesContainer(taskService);
            if (result == null)
                result = caseOtherElementsContainer(taskService);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK: {
            Task task = (Task) theEObject;
            T result = caseTask(task);
            if (result == null)
                result = caseImplementation(task);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TASK_USER: {
            TaskUser taskUser = (TaskUser) theEObject;
            T result = caseTaskUser(taskUser);
            if (result == null)
                result = caseOtherAttributesContainer(taskUser);
            if (result == null)
                result = caseOtherElementsContainer(taskUser);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TIME_ESTIMATION: {
            TimeEstimation timeEstimation = (TimeEstimation) theEObject;
            T result = caseTimeEstimation(timeEstimation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRANSACTION: {
            Transaction transaction = (Transaction) theEObject;
            T result = caseTransaction(transaction);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRANSITION_REF: {
            TransitionRef transitionRef = (TransitionRef) theEObject;
            T result = caseTransitionRef(transitionRef);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRANSITION_RESTRICTION: {
            TransitionRestriction transitionRestriction = (TransitionRestriction) theEObject;
            T result = caseTransitionRestriction(transitionRestriction);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRANSITION: {
            Transition transition = (Transition) theEObject;
            T result = caseTransition(transition);
            if (result == null)
                result = caseNamedElement(transition);
            if (result == null)
                result = caseExtendedAttributesContainer(transition);
            if (result == null)
                result = caseGraphicalConnector(transition);
            if (result == null)
                result = caseDescribedElement(transition);
            if (result == null)
                result = caseUniqueIdElement(transition);
            if (result == null)
                result = caseOtherAttributesContainer(transition);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE: {
            TriggerIntermediateMultiple triggerIntermediateMultiple = (TriggerIntermediateMultiple) theEObject;
            T result = caseTriggerIntermediateMultiple(triggerIntermediateMultiple);
            if (result == null)
                result = caseOtherAttributesContainer(triggerIntermediateMultiple);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_MULTIPLE: {
            TriggerMultiple triggerMultiple = (TriggerMultiple) theEObject;
            T result = caseTriggerMultiple(triggerMultiple);
            if (result == null)
                result = caseOtherAttributesContainer(triggerMultiple);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_RESULT_CANCEL: {
            TriggerResultCancel triggerResultCancel = (TriggerResultCancel) theEObject;
            T result = caseTriggerResultCancel(triggerResultCancel);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_RESULT_COMPENSATION: {
            TriggerResultCompensation triggerResultCompensation = (TriggerResultCompensation) theEObject;
            T result = caseTriggerResultCompensation(triggerResultCompensation);
            if (result == null)
                result = caseOtherAttributesContainer(triggerResultCompensation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL: {
            TriggerResultSignal triggerResultSignal = (TriggerResultSignal) theEObject;
            T result = caseTriggerResultSignal(triggerResultSignal);
            if (result == null)
                result = caseOtherAttributesContainer(triggerResultSignal);
            if (result == null)
                result = caseOtherElementsContainer(triggerResultSignal);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_RESULT_LINK: {
            TriggerResultLink triggerResultLink = (TriggerResultLink) theEObject;
            T result = caseTriggerResultLink(triggerResultLink);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE: {
            TriggerResultMessage triggerResultMessage = (TriggerResultMessage) theEObject;
            T result = caseTriggerResultMessage(triggerResultMessage);
            if (result == null)
                result = caseOtherAttributesContainer(triggerResultMessage);
            if (result == null)
                result = caseOtherElementsContainer(triggerResultMessage);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_CONDITIONAL: {
            TriggerConditional triggerConditional = (TriggerConditional) theEObject;
            T result = caseTriggerConditional(triggerConditional);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TRIGGER_TIMER: {
            TriggerTimer triggerTimer = (TriggerTimer) theEObject;
            T result = caseTriggerTimer(triggerTimer);
            if (result == null)
                result = caseOtherAttributesContainer(triggerTimer);
            if (result == null)
                result = caseOtherElementsContainer(triggerTimer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.TYPE_DECLARATION: {
            TypeDeclaration typeDeclaration = (TypeDeclaration) theEObject;
            T result = caseTypeDeclaration(typeDeclaration);
            if (result == null)
                result = caseNamedElement(typeDeclaration);
            if (result == null)
                result = caseExtendedAttributesContainer(typeDeclaration);
            if (result == null)
                result = caseDescribedElement(typeDeclaration);
            if (result == null)
                result = caseUniqueIdElement(typeDeclaration);
            if (result == null)
                result = caseOtherAttributesContainer(typeDeclaration);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.UNION_TYPE: {
            UnionType unionType = (UnionType) theEObject;
            T result = caseUnionType(unionType);
            if (result == null)
                result = caseDataType(unionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.UNIQUE_ID_ELEMENT: {
            UniqueIdElement uniqueIdElement = (UniqueIdElement) theEObject;
            T result = caseUniqueIdElement(uniqueIdElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.VALID_FROM: {
            ValidFrom validFrom = (ValidFrom) theEObject;
            T result = caseValidFrom(validFrom);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.VALID_TO: {
            ValidTo validTo = (ValidTo) theEObject;
            T result = caseValidTo(validTo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.VENDOR_EXTENSIONS: {
            VendorExtensions vendorExtensions = (VendorExtensions) theEObject;
            T result = caseVendorExtensions(vendorExtensions);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.VENDOR_EXTENSION: {
            VendorExtension vendorExtension = (VendorExtension) theEObject;
            T result = caseVendorExtension(vendorExtension);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.WAITING_TIME: {
            WaitingTime waitingTime = (WaitingTime) theEObject;
            T result = caseWaitingTime(waitingTime);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.WEB_SERVICE_FAULT_CATCH: {
            WebServiceFaultCatch webServiceFaultCatch = (WebServiceFaultCatch) theEObject;
            T result = caseWebServiceFaultCatch(webServiceFaultCatch);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.WEB_SERVICE_OPERATION: {
            WebServiceOperation webServiceOperation = (WebServiceOperation) theEObject;
            T result = caseWebServiceOperation(webServiceOperation);
            if (result == null)
                result = caseOtherAttributesContainer(webServiceOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.WEB_SERVICE_APPLICATION: {
            WebServiceApplication webServiceApplication = (WebServiceApplication) theEObject;
            T result = caseWebServiceApplication(webServiceApplication);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.WORKING_TIME: {
            WorkingTime workingTime = (WorkingTime) theEObject;
            T result = caseWorkingTime(workingTime);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case Xpdl2Package.XSLT_APPLICATION: {
            XsltApplication xsltApplication = (XsltApplication) theEObject;
            T result = caseXsltApplication(xsltApplication);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activity Set</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activity Set</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseActivitySet(ActivitySet object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activity</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseActivity(Activity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Application Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Application Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseApplicationType(ApplicationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseApplication(Application object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Extended Attributes Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Extended Attributes Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExtendedAttributesContainer(ExtendedAttributesContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Array Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Array Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseArrayType(ArrayType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Artifact</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Artifact</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseArtifact(Artifact object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Artifact Input</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Artifact Input</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseArtifactInput(ArtifactInput object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Graphical Node</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Graphical Node</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGraphicalNode(GraphicalNode object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Graphical Connector</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Graphical Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGraphicalConnector(GraphicalConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Group</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGroup(Group object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assignment</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssignment(Assignment object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Association</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Association</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociation(Association object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Basic Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Basic Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBasicType(BasicType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Block Activity</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Block Activity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBlockActivity(BlockActivity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Business Rule Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Business Rule Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBusinessRuleApplication(BusinessRuleApplication object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Category</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Category</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCategory(Category object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Class</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Class</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseClass(com.tibco.xpd.xpdl2.Class object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Codepage</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Codepage</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCodepage(Codepage object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Condition</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Condition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCondition(Condition object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conformance Class</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conformance Class</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConformanceClass(ConformanceClass object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Connector Graphics Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Connector Graphics Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConnectorGraphicsInfo(ConnectorGraphicsInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Coordinates</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Coordinates</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCoordinates(Coordinates object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cost</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cost</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCost(Cost object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cost Structure</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cost Structure</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCostStructure(CostStructure object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cost Unit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cost Unit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCostUnit(CostUnit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Country Key</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Country Key</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCountryKey(CountryKey object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Field</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Field</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataField(DataField object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Mapping</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Mapping</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataMapping(DataMapping object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Object</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Object</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataObject(DataObject object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataType(DataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Deadline</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Deadline</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeadline(Deadline object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Declared Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Declared Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeclaredType(DeclaredType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Deprecated Result Compensation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Deprecated Result Compensation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeprecatedResultCompensation(DeprecatedResultCompensation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Deprecated Trigger Rule</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Deprecated Trigger Rule</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeprecatedTriggerRule(DeprecatedTriggerRule object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Description</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDescription(Description object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Documentation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Documentation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentation(Documentation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentRoot(DocumentRoot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Duration</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Duration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDuration(Duration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ejb Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ejb Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEjbApplication(EjbApplication object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>End Event</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>End Event</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEndEvent(EndEvent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>End Point</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>End Point</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEndPoint(EndPoint object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enumeration Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enumeration Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnumerationType(EnumerationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enumeration Value</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enumeration Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnumerationValue(EnumerationValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Event</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEvent(Event object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Exception Name</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Exception Name</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExceptionName(ExceptionName object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExpression(Expression object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Extended Attribute</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Extended Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExtendedAttribute(ExtendedAttribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>External Package</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>External Package</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExternalPackage(ExternalPackage object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>External Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>External Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExternalReference(ExternalReference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Formal Parameter</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Formal Parameter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormalParameter(FormalParameter object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Form Layout</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Form Layout</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormLayout(FormLayout object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Form Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Form Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormApplication(FormApplication object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Home Class</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Home Class</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseHomeClass(HomeClass object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Icon</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Icon</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIcon(Icon object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Implementation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Implementation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseImplementation(Implementation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Input Set</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Input Set</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInputSet(InputSet object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Input</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Input</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInput(Input object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Intermediate Event</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Intermediate Event</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIntermediateEvent(IntermediateEvent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>IO Rules</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>IO Rules</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIORules(IORules object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Jndi Name</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Jndi Name</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseJndiName(JndiName object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Join</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Join</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseJoin(Join object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Layout Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Layout Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLayoutInfo(LayoutInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Lane</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Lane</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLane(Lane object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Length</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Length</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLength(Length object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Limit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Limit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLimit(Limit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>List Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>List Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseListType(ListType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Location</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Location</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLocation(Location object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loop Multi Instance</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loop Multi Instance</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoopMultiInstance(LoopMultiInstance object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loop Standard</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loop Standard</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoopStandard(LoopStandard object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loop</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loop</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoop(Loop object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Member</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Member</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMember(Member object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Message Flow</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Message Flow</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMessageFlow(MessageFlow object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Message</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Message</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMessage(Message object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Method</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Method</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMethod(Method object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Modification Date</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Modification Date</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModificationDate(ModificationDate object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>My Role</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>My Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMyRole(MyRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Graphics Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Graphics Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNodeGraphicsInfo(NodeGraphicsInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>No</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>No</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNo(No object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Object</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Object</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseObject(com.tibco.xpd.xpdl2.Object object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Output Set</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Output Set</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOutputSet(OutputSet object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Output</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Output</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOutput(Output object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Package Header</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Package Header</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePackageHeader(PackageHeader object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Package</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Package</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePackage(com.tibco.xpd.xpdl2.Package object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Page</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Page</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePage(Page object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pages</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pages</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePages(Pages object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Applications Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Applications Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseApplicationsContainer(ApplicationsContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Participants Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Participants Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParticipantsContainer(ParticipantsContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Fields Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Fields Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataFieldsContainer(DataFieldsContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Participant</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Participant</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParticipant(Participant object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Participant Type Elem</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Participant Type Elem</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParticipantTypeElem(ParticipantTypeElem object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Partner Link</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Partner Link</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePartnerLink(PartnerLink object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Partner Link Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Partner Link Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePartnerLinkType(PartnerLinkType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Partner Role</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Partner Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePartnerRole(PartnerRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Partner</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Partner</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePartner(Partner object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Performer</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Performer</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePerformer(Performer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pojo Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pojo Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePojoApplication(PojoApplication object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pool</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePool(Pool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Precision</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Precision</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrecision(Precision object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Priority</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Priority</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePriority(Priority object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Priority Unit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Priority Unit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePriorityUnit(PriorityUnit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Process Header</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Process Header</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcessHeader(ProcessHeader object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Process</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Process</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcess(com.tibco.xpd.xpdl2.Process object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Formal Parameters Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Formal Parameters Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormalParametersContainer(FormalParametersContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Assigments Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assigments Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssigmentsContainer(AssigmentsContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Record Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Record Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRecordType(RecordType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Redefinable Header</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Redefinable Header</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRedefinableHeader(RedefinableHeader object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReference(Reference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource Costs</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource Costs</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceCosts(ResourceCosts object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Responsible</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Responsible</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResponsible(Responsible object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Result Error</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Result Error</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResultError(ResultError object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Result Multiple</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Result Multiple</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResultMultiple(ResultMultiple object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Role</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRole(Role object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Route</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Route</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRoute(Route object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rule Name</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rule Name</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRuleName(RuleName object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRule(Rule object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Scale</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Scale</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScale(Scale object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schema</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schema</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSchema(Schema object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Script</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Script</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScript(Script object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Service</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Service</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseService(Service object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Simulation Information</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Simulation Information</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimulationInformation(SimulationInformation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Split</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Split</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSplit(Split object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Start Event</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Start Event</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStartEvent(StartEvent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Sub Flow</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Sub Flow</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSubFlow(SubFlow object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskApplication(TaskApplication object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Manual</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Manual</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskManual(TaskManual object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Receive</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Receive</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskReceive(TaskReceive object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskReference(TaskReference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Script</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Script</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskScript(TaskScript object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Send</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Send</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskSend(TaskSend object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Service</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Service</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskService(TaskService object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTask(Task object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task User</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task User</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskUser(TaskUser object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Time Estimation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Time Estimation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTimeEstimation(TimeEstimation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Transaction</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Transaction</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTransaction(Transaction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Transition Ref</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Transition Ref</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTransitionRef(TransitionRef object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Transition Restriction</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Transition Restriction</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTransitionRestriction(TransitionRestriction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTransition(Transition object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Intermediate Multiple</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Intermediate Multiple</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerIntermediateMultiple(TriggerIntermediateMultiple object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Multiple</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Multiple</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerMultiple(TriggerMultiple object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Result Cancel</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Result Cancel</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerResultCancel(TriggerResultCancel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Result Compensation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Result Compensation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerResultCompensation(TriggerResultCompensation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Result Signal</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Result Signal</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerResultSignal(TriggerResultSignal object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Result Link</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Result Link</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerResultLink(TriggerResultLink object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Result Message</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Result Message</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerResultMessage(TriggerResultMessage object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Conditional</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Conditional</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerConditional(TriggerConditional object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Trigger Timer</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Trigger Timer</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTriggerTimer(TriggerTimer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Type Declaration</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Type Declaration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTypeDeclaration(TypeDeclaration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Union Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Union Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnionType(UnionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Valid From</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Valid From</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseValidFrom(ValidFrom object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Valid To</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Valid To</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseValidTo(ValidTo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Vendor Extensions</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Vendor Extensions</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseVendorExtensions(VendorExtensions object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Vendor Extension</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Vendor Extension</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseVendorExtension(VendorExtension object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Waiting Time</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Waiting Time</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWaitingTime(WaitingTime object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Web Service Fault Catch</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Web Service Fault Catch</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWebServiceFaultCatch(WebServiceFaultCatch object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Web Service Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Web Service Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWebServiceOperation(WebServiceOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Web Service Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Web Service Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWebServiceApplication(WebServiceApplication object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Working Time</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Working Time</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkingTime(WorkingTime object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xslt Application</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xslt Application</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXsltApplication(XsltApplication object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Flow Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Flow Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFlowContainer(FlowContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Described Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Described Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDescribedElement(DescribedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Process Relevant Data</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Process Relevant Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcessRelevantData(ProcessRelevantData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Property Input</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Property Input</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePropertyInput(PropertyInput object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unique Id Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unique Id Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUniqueIdElement(UniqueIdElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Other Attributes Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Other Attributes Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOtherAttributesContainer(OtherAttributesContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Other Elements Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Other Elements Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOtherElementsContainer(OtherElementsContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Performers</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Performers</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePerformers(Performers object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} //Xpdl2Switch
