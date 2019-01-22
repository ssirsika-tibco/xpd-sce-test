/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.io.IOException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class Xpdl2PackageImpl extends EPackageImpl implements Xpdl2Package {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected String packageFilename = "xpdl2.ecore"; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass activitySetEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass activityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass applicationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass applicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass extendedAttributesContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass arrayTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass artifactEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass artifactInputEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass graphicalNodeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass graphicalConnectorEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass groupEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass assignmentEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass associationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass basicTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass blockActivityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass businessRuleApplicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass categoryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass classEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass codepageEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass conditionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass conformanceClassEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass connectorGraphicsInfoEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass coordinatesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass costEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass costStructureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass costUnitEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass countryKeyEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dataFieldEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dataMappingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dataObjectEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dataTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass deadlineEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass declaredTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deprecatedResultCompensationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deprecatedTriggerRuleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass descriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass documentationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass durationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass ejbApplicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass endEventEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass endPointEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass enumerationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass enumerationValueEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass eventEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass exceptionNameEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass expressionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass extendedAttributeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass externalPackageEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass externalReferenceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass formalParameterEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass formLayoutEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass formApplicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass homeClassEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iconEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass implementationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass inputSetEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass inputEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass intermediateEventEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass ioRulesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass jndiNameEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass joinEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass layoutInfoEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass laneEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass lengthEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass limitEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass listTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass locationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass loopMultiInstanceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass loopStandardEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass loopEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass memberEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass messageFlowEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass messageEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass methodEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modificationDateEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass myRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass nodeGraphicsInfoEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass noEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass objectEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass outputSetEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass outputEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass packageHeaderEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass packageEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pageEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pagesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass applicationsContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass participantsContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dataFieldsContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass participantEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass participantTypeElemEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass partnerLinkEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass partnerLinkTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass partnerRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass partnerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass performerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass pojoApplicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass poolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass precisionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass priorityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass priorityUnitEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass processHeaderEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass processEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass formalParametersContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass assigmentsContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass recordTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass redefinableHeaderEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass referenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resourceCostsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass responsibleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass resultErrorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass resultMultipleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass roleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass routeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass ruleNameEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass ruleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass scaleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass schemaEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass scriptEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serviceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass simulationInformationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass splitEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass startEventEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass subFlowEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskApplicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskManualEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskReceiveEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskReferenceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskScriptEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskSendEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskServiceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskUserEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass timeEstimationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass transactionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass transitionRefEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass transitionRestrictionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass transitionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerIntermediateMultipleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerMultipleEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerResultCancelEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerResultCompensationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerResultSignalEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerResultLinkEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerResultMessageEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerConditionalEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass triggerTimerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass typeDeclarationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass unionTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass validFromEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass validToEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass vendorExtensionsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass vendorExtensionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass waitingTimeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass webServiceFaultCatchEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass webServiceOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass webServiceApplicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass workingTimeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass xsltApplicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass namedElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass flowContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass describedElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass processRelevantDataEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass propertyInputEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass uniqueIdElementEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass otherAttributesContainerEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass otherElementsContainerEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass performersEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum accessLevelTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum adHocOrderingTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum artifactTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum assignTimeTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum associationDirectionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum directionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum durationUnitTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum endPointTypeTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum exclusiveTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum executionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum finishModeTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum joinSplitTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum graphConformanceTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum implementationTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum instantiationTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum isArrayTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum loopTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum miFlowConditionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum miOrderingTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum modeTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum orientationTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum processTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum publicationStatusTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum resultTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum roleTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum shapeTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum startModeTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum statusTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum testTimeTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum transactionMethodTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum triggerTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum viewTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum deprecatedXorTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum conditionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum participantTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum basicTypeTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum bpmnModelPortabilityConformanceEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum catchThrowEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum gatewayTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType accessLevelTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType adHocOrderingTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType artifactTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType assignTimeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType associationDirectionTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType directionTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType durationUnitTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType endPointTypeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType executionTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType finishModeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType gatewayTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType graphConformanceTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType implementationTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType instantiationTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType isArrayTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType loopTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType miFlowConditionTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType miOrderingTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType modeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType orientationTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType processTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType publicationStatusTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType resultTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType roleTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType shapeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType startModeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType statusTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType testTimeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType transactionMethodTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType triggerTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType idReferenceStringEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
     * package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory
     * method {@link #init init()}, which also performs initialization of the
     * package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#eNS_URI
     * @see #init()
     * @generated
     */
    private Xpdl2PackageImpl() {
        super(eNS_URI, Xpdl2Factory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link Xpdl2Package#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #eNS_URI
     * @generated
     */
    public static Xpdl2Package init() {
        if (isInited)
            return (Xpdl2Package) EPackage.Registry.INSTANCE
                    .getEPackage(Xpdl2Package.eNS_URI);

        // Obtain or create and register package
        Xpdl2PackageImpl theXpdl2Package =
                (Xpdl2PackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Xpdl2PackageImpl ? EPackage.Registry.INSTANCE
                        .get(eNS_URI) : new Xpdl2PackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Load packages
        theXpdl2Package.loadPackage();

        // Fix loaded packages
        theXpdl2Package.fixPackageContents();

        // Mark meta-data to indicate it can't be changed
        theXpdl2Package.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(Xpdl2Package.eNS_URI, theXpdl2Package);
        return theXpdl2Package;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getActivitySet() {
        if (activitySetEClass == null) {
            activitySetEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(2);
        }
        return activitySetEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivitySet_Object() {
        return (EReference) getActivitySet().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivitySet_Process() {
        return (EReference) getActivitySet().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getActivity() {
        if (activityEClass == null) {
            activityEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(3);
        }
        return activityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Limit() {
        return (EReference) getActivity().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Route() {
        return (EReference) getActivity().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Implementation() {
        return (EReference) getActivity().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_BlockActivity() {
        return (EReference) getActivity().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Event() {
        return (EReference) getActivity().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Transaction() {
        return (EReference) getActivity().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Performer() {
        return (EReference) getActivity().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Performers() {
        return (EReference) getActivity().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Priority() {
        return (EReference) getActivity().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Deadline() {
        return (EReference) getActivity().getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_SimulationInformation() {
        return (EReference) getActivity().getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Icon() {
        return (EReference) getActivity().getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Documentation() {
        return (EReference) getActivity().getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_TransitionRestrictions() {
        return (EReference) getActivity().getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_InputSets() {
        return (EReference) getActivity().getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_OutputSets() {
        return (EReference) getActivity().getEStructuralFeatures().get(15);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_IoRules() {
        return (EReference) getActivity().getEStructuralFeatures().get(16);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Loop() {
        return (EReference) getActivity().getEStructuralFeatures().get(17);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Assignments() {
        return (EReference) getActivity().getEStructuralFeatures().get(18);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Object() {
        return (EReference) getActivity().getEStructuralFeatures().get(19);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Extensions() {
        return (EReference) getActivity().getEStructuralFeatures().get(20);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_FinishMode() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(21);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_IsATransaction() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(22);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_StartActivity() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(23);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_StartMode() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(24);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_StartQuantity() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(25);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_Status() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(26);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_FlowContainer() {
        return (EReference) getActivity().getEStructuralFeatures().get(27);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Process() {
        return (EReference) getActivity().getEStructuralFeatures().get(28);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_IsForCompensation() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(29);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivity_CompletionQuantity() {
        return (EAttribute) getActivity().getEStructuralFeatures().get(30);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getApplicationType() {
        if (applicationTypeEClass == null) {
            applicationTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(6);
        }
        return applicationTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationType_Ejb() {
        return (EReference) getApplicationType().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationType_Pojo() {
        return (EReference) getApplicationType().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationType_Xslt() {
        return (EReference) getApplicationType().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationType_Script() {
        return (EReference) getApplicationType().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationType_WebService() {
        return (EReference) getApplicationType().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationType_BusinessRule() {
        return (EReference) getApplicationType().getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationType_Form() {
        return (EReference) getApplicationType().getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getApplicationType_AnyAttribute() {
        return (EAttribute) getApplicationType().getEStructuralFeatures()
                .get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getApplication() {
        if (applicationEClass == null) {
            applicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(7);
        }
        return applicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplication_Type() {
        return (EReference) getApplication().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplication_ExternalReference() {
        return (EReference) getApplication().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getExtendedAttributesContainer() {
        if (extendedAttributesContainerEClass == null) {
            extendedAttributesContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(71);
        }
        return extendedAttributesContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getExtendedAttributesContainer_ExtendedAttributes() {
        return (EReference) getExtendedAttributesContainer()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getArrayType() {
        if (arrayTypeEClass == null) {
            arrayTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(9);
        }
        return arrayTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_BasicType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_DeclaredType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_SchemaType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_ExternalReference() {
        return (EReference) getArrayType().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_RecordType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_UnionType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_EnumerationType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_ArrayType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArrayType_ListType() {
        return (EReference) getArrayType().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getArrayType_LowerIndex() {
        return (EAttribute) getArrayType().getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getArrayType_UpperIndex() {
        return (EAttribute) getArrayType().getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getArtifact() {
        if (artifactEClass == null) {
            artifactEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(10);
        }
        return artifactEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArtifact_Object() {
        return (EReference) getArtifact().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArtifact_DataObject() {
        return (EReference) getArtifact().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getArtifact_ArtifactType() {
        return (EAttribute) getArtifact().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArtifact_Group() {
        return (EReference) getArtifact().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getArtifactInput() {
        if (artifactInputEClass == null) {
            artifactInputEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(11);
        }
        return artifactInputEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getArtifactInput_ArtifactId() {
        return (EAttribute) getArtifactInput().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getArtifactInput_RequiredForStart() {
        return (EAttribute) getArtifactInput().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getArtifact_TextAnnotation() {
        return (EAttribute) getArtifact().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getArtifact_Package() {
        return (EReference) getArtifact().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getGraphicalNode() {
        if (graphicalNodeEClass == null) {
            graphicalNodeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(85);
        }
        return graphicalNodeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getGraphicalNode_NodeGraphicsInfos() {
        return (EReference) getGraphicalNode().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getGraphicalConnector() {
        if (graphicalConnectorEClass == null) {
            graphicalConnectorEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(86);
        }
        return graphicalConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getGraphicalConnector_ConnectorGraphicsInfos() {
        return (EReference) getGraphicalConnector().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGroup() {
        if (groupEClass == null) {
            groupEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(87);
        }
        return groupEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_Category() {
        return (EReference) getGroup().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_Object() {
        return (EReference) getGroup().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getAssignment() {
        if (assignmentEClass == null) {
            assignmentEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(14);
        }
        return assignmentEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssignment_Target() {
        return (EReference) getAssignment().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssignment_Expression() {
        return (EReference) getAssignment().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAssignment_AssignTime() {
        return (EAttribute) getAssignment().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getAssociation() {
        if (associationEClass == null) {
            associationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(20);
        }
        return associationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssociation_Object() {
        return (EReference) getAssociation().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAssociation_AssociationDirection() {
        return (EAttribute) getAssociation().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAssociation_Source() {
        return (EAttribute) getAssociation().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAssociation_Target() {
        return (EAttribute) getAssociation().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssociation_Package() {
        return (EReference) getAssociation().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getBasicType() {
        if (basicTypeEClass == null) {
            basicTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(21);
        }
        return basicTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getBasicType_Length() {
        return (EReference) getBasicType().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getBasicType_Precision() {
        return (EReference) getBasicType().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getBasicType_Scale() {
        return (EReference) getBasicType().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBasicType_Type() {
        return (EAttribute) getBasicType().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getBlockActivity() {
        if (blockActivityEClass == null) {
            blockActivityEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(23);
        }
        return blockActivityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlockActivity_ActivitySetId() {
        return (EAttribute) getBlockActivity().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlockActivity_StartActivityId() {
        return (EAttribute) getBlockActivity().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlockActivity_View() {
        return (EAttribute) getBlockActivity().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getBusinessRuleApplication() {
        if (businessRuleApplicationEClass == null) {
            businessRuleApplicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(25);
        }
        return businessRuleApplicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getBusinessRuleApplication_RuleName() {
        return (EReference) getBusinessRuleApplication()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getBusinessRuleApplication_Location() {
        return (EReference) getBusinessRuleApplication()
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCategory() {
        if (categoryEClass == null) {
            categoryEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(27);
        }
        return categoryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getClass_() {
        if (classEClass == null) {
            classEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(28);
        }
        return classEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getClass_Value() {
        return (EAttribute) getClass_().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCodepage() {
        if (codepageEClass == null) {
            codepageEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(29);
        }
        return codepageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCodepage_Value() {
        return (EAttribute) getCodepage().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCondition() {
        if (conditionEClass == null) {
            conditionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(30);
        }
        return conditionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCondition_Mixed() {
        return (EAttribute) getCondition().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getCondition_Expression() {
        return (EReference) getCondition().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCondition_Type() {
        return (EAttribute) getCondition().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getConformanceClass() {
        if (conformanceClassEClass == null) {
            conformanceClassEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(32);
        }
        return conformanceClassEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConformanceClass_GraphConformance() {
        return (EAttribute) getConformanceClass().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConformanceClass_BpmnModelPortabilityConformance() {
        return (EAttribute) getConformanceClass().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getConnectorGraphicsInfo() {
        if (connectorGraphicsInfoEClass == null) {
            connectorGraphicsInfoEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(33);
        }
        return connectorGraphicsInfoEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getConnectorGraphicsInfo_Coordinates() {
        return (EReference) getConnectorGraphicsInfo().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConnectorGraphicsInfo_BorderColor() {
        return (EAttribute) getConnectorGraphicsInfo().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConnectorGraphicsInfo_FillColor() {
        return (EAttribute) getConnectorGraphicsInfo().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConnectorGraphicsInfo_IsVisible() {
        return (EAttribute) getConnectorGraphicsInfo().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConnectorGraphicsInfo_PageId() {
        return (EAttribute) getConnectorGraphicsInfo().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConnectorGraphicsInfo_Style() {
        return (EAttribute) getConnectorGraphicsInfo().getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConnectorGraphicsInfo_ToolId() {
        return (EAttribute) getConnectorGraphicsInfo().getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCoordinates() {
        if (coordinatesEClass == null) {
            coordinatesEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(34);
        }
        return coordinatesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCoordinates_XCoordinate() {
        return (EAttribute) getCoordinates().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCoordinates_YCoordinate() {
        return (EAttribute) getCoordinates().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCost() {
        if (costEClass == null) {
            costEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(35);
        }
        return costEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCost_Value() {
        return (EAttribute) getCost().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCostStructure() {
        if (costStructureEClass == null) {
            costStructureEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(36);
        }
        return costStructureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCostStructure_FixedCost() {
        return (EAttribute) getCostStructure().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCostStructure_ResourceCosts() {
        return (EReference) getCostStructure().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCostUnit() {
        if (costUnitEClass == null) {
            costUnitEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(37);
        }
        return costUnitEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCostUnit_Value() {
        return (EAttribute) getCostUnit().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCountryKey() {
        if (countryKeyEClass == null) {
            countryKeyEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(38);
        }
        return countryKeyEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCountryKey_Value() {
        return (EAttribute) getCountryKey().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDataField() {
        if (dataFieldEClass == null) {
            dataFieldEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(39);
        }
        return dataFieldEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getDataField_InitialValue() {
        return (EReference) getDataField().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataField_Correlation() {
        return (EAttribute) getDataField().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataField_DeprecatedDataIsArray() {
        return (EAttribute) getDataField().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDataMapping() {
        if (dataMappingEClass == null) {
            dataMappingEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(41);
        }
        return dataMappingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getDataMapping_Actual() {
        return (EReference) getDataMapping().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataMapping_Direction() {
        return (EAttribute) getDataMapping().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataMapping_Formal() {
        return (EAttribute) getDataMapping().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDataMapping_TestValue() {
        return (EReference) getDataMapping().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDataObject() {
        if (dataObjectEClass == null) {
            dataObjectEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(42);
        }
        return dataObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataObject_DeprecatedProducedAtCompletion() {
        return (EAttribute) getDataObject().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataObject_DeprecatedRequiredForStart() {
        return (EAttribute) getDataObject().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataObject_State() {
        return (EAttribute) getDataObject().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDataType() {
        if (dataTypeEClass == null) {
            dataTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(43);
        }
        return dataTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeadline() {
        if (deadlineEClass == null) {
            deadlineEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(44);
        }
        return deadlineEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getDeadline_DeadlineDuration() {
        return (EReference) getDeadline().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getDeadline_ExceptionName() {
        return (EReference) getDeadline().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeadline_Execution() {
        return (EAttribute) getDeadline().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeclaredType() {
        if (declaredTypeEClass == null) {
            declaredTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(45);
        }
        return declaredTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeclaredType_TypeDeclarationId() {
        return (EAttribute) getDeclaredType().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeclaredType_Name() {
        return (EAttribute) getDeclaredType().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeprecatedResultCompensation() {
        if (deprecatedResultCompensationEClass == null) {
            deprecatedResultCompensationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(46);
        }
        return deprecatedResultCompensationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeprecatedResultCompensation_ActivityId() {
        return (EAttribute) getDeprecatedResultCompensation()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeprecatedTriggerRule() {
        if (deprecatedTriggerRuleEClass == null) {
            deprecatedTriggerRuleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(47);
        }
        return deprecatedTriggerRuleEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeprecatedTriggerRule_RuleName() {
        return (EAttribute) getDeprecatedTriggerRule().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescription() {
        if (descriptionEClass == null) {
            descriptionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(49);
        }
        return descriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDescription_Value() {
        return (EAttribute) getDescription().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentation() {
        if (documentationEClass == null) {
            documentationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(52);
        }
        return documentationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentation_Value() {
        return (EAttribute) getDocumentation().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        if (documentRootEClass == null) {
            documentRootEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(53);
        }
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute) getDocumentRoot().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference) getDocumentRoot().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference) getDocumentRoot().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_Package() {
        return (EReference) getDocumentRoot().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDuration() {
        if (durationEClass == null) {
            durationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(54);
        }
        return durationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDuration_Value() {
        return (EAttribute) getDuration().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEjbApplication() {
        if (ejbApplicationEClass == null) {
            ejbApplicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(57);
        }
        return ejbApplicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEjbApplication_JndiName() {
        return (EReference) getEjbApplication().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEjbApplication_HomeClass() {
        return (EReference) getEjbApplication().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEjbApplication_Method() {
        return (EReference) getEjbApplication().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEndEvent() {
        if (endEventEClass == null) {
            endEventEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(58);
        }
        return endEventEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndEvent_TriggerResultMessage() {
        return (EReference) getEndEvent().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndEvent_ResultError() {
        return (EReference) getEndEvent().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndEvent_TriggerResultCompensation() {
        return (EReference) getEndEvent().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndEvent_TriggerResultSignal() {
        return (EReference) getEndEvent().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndEvent_DeprecatedResultCompensation() {
        return (EReference) getEndEvent().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndEvent_ResultMultiple() {
        return (EReference) getEndEvent().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEndEvent_Implementation() {
        return (EAttribute) getEndEvent().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEndEvent_Result() {
        return (EAttribute) getEndEvent().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndEvent_DeprecatedTriggerResultLink() {
        return (EReference) getEndEvent().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEndPoint() {
        if (endPointEClass == null) {
            endPointEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(59);
        }
        return endPointEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEndPoint_ExternalReference() {
        return (EReference) getEndPoint().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEndPoint_EndPointType() {
        return (EAttribute) getEndPoint().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnumerationType() {
        if (enumerationTypeEClass == null) {
            enumerationTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(62);
        }
        return enumerationTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEnumerationType_EnumerationValue() {
        return (EReference) getEnumerationType().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnumerationValue() {
        if (enumerationValueEClass == null) {
            enumerationValueEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(63);
        }
        return enumerationValueEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumerationValue_Name() {
        return (EAttribute) getEnumerationValue().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEvent() {
        if (eventEClass == null) {
            eventEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(64);
        }
        return eventEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getExceptionName() {
        if (exceptionNameEClass == null) {
            exceptionNameEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(65);
        }
        return exceptionNameEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExceptionName_Value() {
        return (EAttribute) getExceptionName().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getExpression() {
        if (expressionEClass == null) {
            expressionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(69);
        }
        return expressionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExpression_Mixed() {
        return (EAttribute) getExpression().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExpression_Group() {
        return (EAttribute) getExpression().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExpression_Any() {
        return (EAttribute) getExpression().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExpression_ScriptGrammar() {
        return (EAttribute) getExpression().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getExtendedAttribute() {
        if (extendedAttributeEClass == null) {
            extendedAttributeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(70);
        }
        return extendedAttributeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExtendedAttribute_Mixed() {
        return (EAttribute) getExtendedAttribute().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExtendedAttribute_Group() {
        return (EAttribute) getExtendedAttribute().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExtendedAttribute_Any() {
        return (EAttribute) getExtendedAttribute().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExtendedAttribute_Name() {
        return (EAttribute) getExtendedAttribute().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExtendedAttribute_Value() {
        return (EAttribute) getExtendedAttribute().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getExternalPackage() {
        if (externalPackageEClass == null) {
            externalPackageEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(72);
        }
        return externalPackageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExternalPackage_Href() {
        return (EAttribute) getExternalPackage().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getExternalReference() {
        if (externalReferenceEClass == null) {
            externalReferenceEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(73);
        }
        return externalReferenceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExternalReference_Location() {
        return (EAttribute) getExternalReference().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExternalReference_Namespace() {
        return (EAttribute) getExternalReference().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExternalReference_Xref() {
        return (EAttribute) getExternalReference().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getFormalParameter() {
        if (formalParameterEClass == null) {
            formalParameterEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(77);
        }
        return formalParameterEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormalParameter_Mode() {
        return (EAttribute) getFormalParameter().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormalParameter_Required() {
        return (EAttribute) getFormalParameter().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getFormLayout() {
        if (formLayoutEClass == null) {
            formLayoutEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(78);
        }
        return formLayoutEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormLayout_Mixed() {
        return (EAttribute) getFormLayout().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getFormApplication() {
        if (formApplicationEClass == null) {
            formApplicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(79);
        }
        return formApplicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getFormApplication_FormLayout() {
        return (EReference) getFormApplication().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getHomeClass() {
        if (homeClassEClass == null) {
            homeClassEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(88);
        }
        return homeClassEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHomeClass_Value() {
        return (EAttribute) getHomeClass().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIcon() {
        if (iconEClass == null) {
            iconEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(89);
        }
        return iconEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIcon_Value() {
        return (EAttribute) getIcon().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIcon_Height() {
        return (EAttribute) getIcon().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIcon_Shape() {
        return (EAttribute) getIcon().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIcon_Width() {
        return (EAttribute) getIcon().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIcon_XCoord() {
        return (EAttribute) getIcon().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIcon_YCoord() {
        return (EAttribute) getIcon().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getImplementation() {
        if (implementationEClass == null) {
            implementationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(92);
        }
        return implementationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getImplementation_Activity() {
        return (EReference) getImplementation().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getInputSet() {
        if (inputSetEClass == null) {
            inputSetEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(94);
        }
        return inputSetEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getInputSet_Input() {
        return (EReference) getInputSet().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getInputSet_ArtifactInput() {
        return (EReference) getInputSet().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getInputSet_PropertyInput() {
        return (EReference) getInputSet().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getInput() {
        if (inputEClass == null) {
            inputEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(95);
        }
        return inputEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getInput_ArtifactId() {
        return (EAttribute) getInput().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIntermediateEvent() {
        if (intermediateEventEClass == null) {
            intermediateEventEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(98);
        }
        return intermediateEventEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerResultMessage() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerTimer() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_ResultError() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerResultCompensation() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerConditional() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerResultLink() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerIntermediateMultiple() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIntermediateEvent_Implementation() {
        return (EAttribute) getIntermediateEvent().getEStructuralFeatures()
                .get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIntermediateEvent_Target() {
        return (EAttribute) getIntermediateEvent().getEStructuralFeatures()
                .get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIntermediateEvent_Trigger() {
        return (EAttribute) getIntermediateEvent().getEStructuralFeatures()
                .get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getIntermediateEvent_AnyAttribute() {
        return (EAttribute) getIntermediateEvent().getEStructuralFeatures()
                .get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerResultCancel() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_TriggerResultSignal() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_DeprecatedTriggerRule() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getIntermediateEvent_DeprecatedResultCompensation() {
        return (EReference) getIntermediateEvent().getEStructuralFeatures()
                .get(14);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIORules() {
        if (ioRulesEClass == null) {
            ioRulesEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(99);
        }
        return ioRulesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getIORules_Expression() {
        return (EReference) getIORules().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getJndiName() {
        if (jndiNameEClass == null) {
            jndiNameEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(102);
        }
        return jndiNameEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getJndiName_Value() {
        return (EAttribute) getJndiName().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getJoin() {
        if (joinEClass == null) {
            joinEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(103);
        }
        return joinEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getJoin_IncomingCondtion() {
        return (EAttribute) getJoin().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getJoin_Type() {
        return (EAttribute) getJoin().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getJoin_ExclusiveType() {
        return (EAttribute) getJoin().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLayoutInfo() {
        if (layoutInfoEClass == null) {
            layoutInfoEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(104);
        }
        return layoutInfoEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayoutInfo_PixelsPerMillimeter() {
        return (EAttribute) getLayoutInfo().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLane() {
        if (laneEClass == null) {
            laneEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(105);
        }
        return laneEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLane_Object() {
        return (EReference) getLane().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLane_DeprecatedParentLane() {
        return (EAttribute) getLane().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLane_DeprecatedParentPoolId() {
        return (EAttribute) getLane().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLane_ParentPool() {
        return (EReference) getLane().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLane_Performers() {
        return (EReference) getLane().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLane_NestedLane() {
        return (EReference) getLane().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLength() {
        if (lengthEClass == null) {
            lengthEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(106);
        }
        return lengthEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLength_Value() {
        return (EAttribute) getLength().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLimit() {
        if (limitEClass == null) {
            limitEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(107);
        }
        return limitEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLimit_Value() {
        return (EAttribute) getLimit().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getListType() {
        if (listTypeEClass == null) {
            listTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(108);
        }
        return listTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_BasicType() {
        return (EReference) getListType().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_DeclaredType() {
        return (EReference) getListType().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_SchemaType() {
        return (EReference) getListType().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_ExternalReference() {
        return (EReference) getListType().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_RecordType() {
        return (EReference) getListType().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_UnionType() {
        return (EReference) getListType().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_EnumerationType() {
        return (EReference) getListType().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_ArrayType() {
        return (EReference) getListType().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getListType_ListType() {
        return (EReference) getListType().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLocation() {
        if (locationEClass == null) {
            locationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(109);
        }
        return locationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLocation_Value() {
        return (EAttribute) getLocation().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLoopMultiInstance() {
        if (loopMultiInstanceEClass == null) {
            loopMultiInstanceEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(110);
        }
        return loopMultiInstanceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoopMultiInstance_ComplexMIFlowCondition() {
        return (EReference) getLoopMultiInstance().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopMultiInstance_AttributeComplexMI_FlowCondition() {
        return (EAttribute) getLoopMultiInstance().getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopMultiInstance_AttributeMI_Condition() {
        return (EAttribute) getLoopMultiInstance().getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopMultiInstance_LoopCounter() {
        return (EAttribute) getLoopMultiInstance().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoopMultiInstance_MICondition() {
        return (EReference) getLoopMultiInstance().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopMultiInstance_MIFlowCondition() {
        return (EAttribute) getLoopMultiInstance().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopMultiInstance_MIOrdering() {
        return (EAttribute) getLoopMultiInstance().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLoopStandard() {
        if (loopStandardEClass == null) {
            loopStandardEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(111);
        }
        return loopStandardEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoopStandard_LoopCondition() {
        return (EReference) getLoopStandard().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopStandard_LoopCounter() {
        return (EAttribute) getLoopStandard().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopStandard_LoopMaximum() {
        return (EAttribute) getLoopStandard().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopStandard_TestTime() {
        return (EAttribute) getLoopStandard().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopStandard_AttributeLoopCondition() {
        return (EAttribute) getLoopStandard().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLoop() {
        if (loopEClass == null) {
            loopEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(112);
        }
        return loopEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoop_LoopStandard() {
        return (EReference) getLoop().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoop_LoopMultiInstance() {
        return (EReference) getLoop().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoop_LoopType() {
        return (EAttribute) getLoop().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoop_AnyAttribute() {
        return (EAttribute) getLoop().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getMember() {
        if (memberEClass == null) {
            memberEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(115);
        }
        return memberEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_BasicType() {
        return (EReference) getMember().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_DeclaredType() {
        return (EReference) getMember().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_SchemaType() {
        return (EReference) getMember().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_ExternalReference() {
        return (EReference) getMember().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_RecordType() {
        return (EReference) getMember().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_UnionType() {
        return (EReference) getMember().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_EnumerationType() {
        return (EReference) getMember().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_ArrayType() {
        return (EReference) getMember().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMember_ListType() {
        return (EReference) getMember().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMember_AnyAttribute() {
        return (EAttribute) getMember().getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getMessageFlow() {
        if (messageFlowEClass == null) {
            messageFlowEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(116);
        }
        return messageFlowEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMessageFlow_Message() {
        return (EReference) getMessageFlow().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMessageFlow_Object() {
        return (EReference) getMessageFlow().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMessageFlow_Source() {
        return (EAttribute) getMessageFlow().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMessageFlow_Target() {
        return (EAttribute) getMessageFlow().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMessageFlow_Package() {
        return (EReference) getMessageFlow().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getMessage() {
        if (messageEClass == null) {
            messageEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(117);
        }
        return messageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMessage_ActualParameters() {
        return (EReference) getMessage().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMessage_DataMappings() {
        return (EReference) getMessage().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMessage_FaultName() {
        return (EAttribute) getMessage().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMessage_From() {
        return (EAttribute) getMessage().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMessage_To() {
        return (EAttribute) getMessage().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getMethod() {
        if (methodEClass == null) {
            methodEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(118);
        }
        return methodEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMethod_Value() {
        return (EAttribute) getMethod().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getModificationDate() {
        if (modificationDateEClass == null) {
            modificationDateEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(125);
        }
        return modificationDateEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getMyRole() {
        if (myRoleEClass == null) {
            myRoleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(126);
        }
        return myRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMyRole_RoleName() {
        return (EAttribute) getMyRole().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getNodeGraphicsInfo() {
        if (nodeGraphicsInfoEClass == null) {
            nodeGraphicsInfoEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(128);
        }
        return nodeGraphicsInfoEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getNodeGraphicsInfo_Coordinates() {
        return (EReference) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_BorderColor() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_FillColor() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_Height() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_IsVisible() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_LaneId() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_Page() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_Shape() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_ToolId() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_Width() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNodeGraphicsInfo_PageId() {
        return (EAttribute) getNodeGraphicsInfo().getEStructuralFeatures()
                .get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getNo() {
        if (noEClass == null) {
            noEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(129);
        }
        return noEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getObject() {
        if (objectEClass == null) {
            objectEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(130);
        }
        return objectEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getObject_Categories() {
        return (EReference) getObject().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getObject_Documentation() {
        return (EReference) getObject().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOutputSet() {
        if (outputSetEClass == null) {
            outputSetEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(135);
        }
        return outputSetEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOutputSet_Output() {
        return (EReference) getOutputSet().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOutput() {
        if (outputEClass == null) {
            outputEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(136);
        }
        return outputEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOutput_ArtifactId() {
        return (EAttribute) getOutput().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPackageHeader() {
        if (packageHeaderEClass == null) {
            packageHeaderEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(137);
        }
        return packageHeaderEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPackageHeader_XpdlVersion() {
        return (EAttribute) getPackageHeader().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPackageHeader_Vendor() {
        return (EAttribute) getPackageHeader().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPackageHeader_Created() {
        return (EAttribute) getPackageHeader().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackageHeader_Documentation() {
        return (EReference) getPackageHeader().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackageHeader_PriorityUnit() {
        return (EReference) getPackageHeader().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackageHeader_CostUnit() {
        return (EReference) getPackageHeader().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackageHeader_VendorExtensions() {
        return (EReference) getPackageHeader().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackageHeader_LayoutInfo() {
        return (EReference) getPackageHeader().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackageHeader_ModificationDate() {
        return (EReference) getPackageHeader().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPackage() {
        if (packageEClass == null) {
            packageEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(138);
        }
        return packageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_PackageHeader() {
        return (EReference) getPackage().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_RedefinableHeader() {
        return (EReference) getPackage().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_ConformanceClass() {
        return (EReference) getPackage().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_Script() {
        return (EReference) getPackage().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_ExternalPackages() {
        return (EReference) getPackage().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_TypeDeclarations() {
        return (EReference) getPackage().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_PartnerLinkTypes() {
        return (EReference) getPackage().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_Pools() {
        return (EReference) getPackage().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_MessageFlows() {
        return (EReference) getPackage().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_Associations() {
        return (EReference) getPackage().getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_Artifacts() {
        return (EReference) getPackage().getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPackage_Processes() {
        return (EReference) getPackage().getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPackage_Language() {
        return (EAttribute) getPackage().getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPackage_QueryLanguage() {
        return (EAttribute) getPackage().getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPage() {
        if (pageEClass == null) {
            pageEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(139);
        }
        return pageEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPage_Height() {
        return (EAttribute) getPage().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPage_Width() {
        return (EAttribute) getPage().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPages() {
        if (pagesEClass == null) {
            pagesEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(140);
        }
        return pagesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPages_Page() {
        return (EReference) getPages().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getApplicationsContainer() {
        if (applicationsContainerEClass == null) {
            applicationsContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(8);
        }
        return applicationsContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getApplicationsContainer_Applications() {
        return (EReference) getApplicationsContainer().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getParticipantsContainer() {
        if (participantsContainerEClass == null) {
            participantsContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(141);
        }
        return participantsContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getParticipantsContainer_Participants() {
        return (EReference) getParticipantsContainer().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDataFieldsContainer() {
        if (dataFieldsContainerEClass == null) {
            dataFieldsContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(40);
        }
        return dataFieldsContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getDataFieldsContainer_DataFields() {
        return (EReference) getDataFieldsContainer().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getParticipant() {
        if (participantEClass == null) {
            participantEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(142);
        }
        return participantEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getParticipant_ParticipantType() {
        return (EReference) getParticipant().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getParticipant_ExternalReference() {
        return (EReference) getParticipant().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getParticipantTypeElem() {
        if (participantTypeElemEClass == null) {
            participantTypeElemEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(144);
        }
        return participantTypeElemEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParticipantTypeElem_Type() {
        return (EAttribute) getParticipantTypeElem().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPartnerLink() {
        if (partnerLinkEClass == null) {
            partnerLinkEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(145);
        }
        return partnerLinkEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPartnerLink_MyRole() {
        return (EReference) getPartnerLink().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPartnerLink_PartnerRole() {
        return (EReference) getPartnerLink().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartnerLink_PartnerLinkTypeId() {
        return (EAttribute) getPartnerLink().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartnerLink_Name() {
        return (EAttribute) getPartnerLink().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPartnerLinkType() {
        if (partnerLinkTypeEClass == null) {
            partnerLinkTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(146);
        }
        return partnerLinkTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPartnerLinkType_Role() {
        return (EReference) getPartnerLinkType().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartnerLinkType_Name() {
        return (EAttribute) getPartnerLinkType().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPartnerRole() {
        if (partnerRoleEClass == null) {
            partnerRoleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(147);
        }
        return partnerRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPartnerRole_EndPoint() {
        return (EReference) getPartnerRole().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartnerRole_PortName() {
        return (EAttribute) getPartnerRole().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartnerRole_RoleName() {
        return (EAttribute) getPartnerRole().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartnerRole_ServiceName() {
        return (EAttribute) getPartnerRole().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPartner() {
        if (partnerEClass == null) {
            partnerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(148);
        }
        return partnerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartner_PartnerLinkId() {
        return (EAttribute) getPartner().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPartner_RoleType() {
        return (EAttribute) getPartner().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPerformer() {
        if (performerEClass == null) {
            performerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(149);
        }
        return performerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPerformer_Value() {
        return (EAttribute) getPerformer().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPojoApplication() {
        if (pojoApplicationEClass == null) {
            pojoApplicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(151);
        }
        return pojoApplicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPojoApplication_Class() {
        return (EReference) getPojoApplication().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPojoApplication_Method() {
        return (EReference) getPojoApplication().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPool() {
        if (poolEClass == null) {
            poolEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(152);
        }
        return poolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPool_Lanes() {
        return (EReference) getPool().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPool_Object() {
        return (EReference) getPool().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPool_BoundaryVisible() {
        return (EAttribute) getPool().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPool_Orientation() {
        return (EAttribute) getPool().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPool_ParticipantId() {
        return (EAttribute) getPool().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPool_ProcessId() {
        return (EAttribute) getPool().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPool_ParentPackage() {
        return (EReference) getPool().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPool_MainPool() {
        return (EAttribute) getPool().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPrecision() {
        if (precisionEClass == null) {
            precisionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(153);
        }
        return precisionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPrecision_Value() {
        return (EAttribute) getPrecision().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPriority() {
        if (priorityEClass == null) {
            priorityEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(154);
        }
        return priorityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPriority_Value() {
        return (EAttribute) getPriority().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPriorityUnit() {
        if (priorityUnitEClass == null) {
            priorityUnitEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(155);
        }
        return priorityUnitEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPriorityUnit_Value() {
        return (EAttribute) getPriorityUnit().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getProcessHeader() {
        if (processHeaderEClass == null) {
            processHeaderEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(156);
        }
        return processHeaderEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcessHeader_Created() {
        return (EAttribute) getProcessHeader().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessHeader_Priority() {
        return (EReference) getProcessHeader().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessHeader_Limit() {
        return (EReference) getProcessHeader().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessHeader_ValidFrom() {
        return (EReference) getProcessHeader().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessHeader_ValidTo() {
        return (EReference) getProcessHeader().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessHeader_TimeEstimation() {
        return (EReference) getProcessHeader().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcessHeader_DurationUnit() {
        return (EAttribute) getProcessHeader().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getProcess() {
        if (processEClass == null) {
            processEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(157);
        }
        return processEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcess_ProcessHeader() {
        return (EReference) getProcess().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcess_RedefinableHeader() {
        return (EReference) getProcess().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcess_PartnerLinks() {
        return (EReference) getProcess().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcess_Object() {
        return (EReference) getProcess().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcess_Extensions() {
        return (EReference) getProcess().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcess_AccessLevel() {
        return (EAttribute) getProcess().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcess_DefaultStartActivitySetId() {
        return (EAttribute) getProcess().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcess_EnableInstanceCompensation() {
        return (EAttribute) getProcess().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcess_ProcessType() {
        return (EAttribute) getProcess().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcess_Status() {
        return (EAttribute) getProcess().getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcess_SuppressJoinFailure() {
        return (EAttribute) getProcess().getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcess_Package() {
        return (EReference) getProcess().getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcess_ActivitySets() {
        return (EReference) getProcess().getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getFormalParametersContainer() {
        if (formalParametersContainerEClass == null) {
            formalParametersContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(158);
        }
        return formalParametersContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getFormalParametersContainer_FormalParameters() {
        return (EReference) getFormalParametersContainer()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getAssigmentsContainer() {
        if (assigmentsContainerEClass == null) {
            assigmentsContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(15);
        }
        return assigmentsContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssigmentsContainer_Assignments() {
        return (EReference) getAssigmentsContainer().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRecordType() {
        if (recordTypeEClass == null) {
            recordTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(165);
        }
        return recordTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRecordType_Member() {
        return (EReference) getRecordType().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRedefinableHeader() {
        if (redefinableHeaderEClass == null) {
            redefinableHeaderEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(166);
        }
        return redefinableHeaderEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRedefinableHeader_Author() {
        return (EAttribute) getRedefinableHeader().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRedefinableHeader_Version() {
        return (EAttribute) getRedefinableHeader().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRedefinableHeader_Codepage() {
        return (EReference) getRedefinableHeader().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRedefinableHeader_Countrykey() {
        return (EReference) getRedefinableHeader().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRedefinableHeader_Responsibles() {
        return (EReference) getRedefinableHeader().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRedefinableHeader_PublicationStatus() {
        return (EAttribute) getRedefinableHeader().getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getReference() {
        if (referenceEClass == null) {
            referenceEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(167);
        }
        return referenceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReference_ActivityId() {
        return (EAttribute) getReference().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResourceCosts() {
        if (resourceCostsEClass == null) {
            resourceCostsEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(168);
        }
        return resourceCostsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceCosts_ResourceCost() {
        return (EAttribute) getResourceCosts().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceCosts_CostUnitOfTime() {
        return (EAttribute) getResourceCosts().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getResponsible() {
        if (responsibleEClass == null) {
            responsibleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(169);
        }
        return responsibleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResponsible_Value() {
        return (EAttribute) getResponsible().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getResultError() {
        if (resultErrorEClass == null) {
            resultErrorEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(170);
        }
        return resultErrorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResultError_ErrorCode() {
        return (EAttribute) getResultError().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getResultMultiple() {
        if (resultMultipleEClass == null) {
            resultMultipleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(171);
        }
        return resultMultipleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getResultMultiple_TriggerResultMessage() {
        return (EReference) getResultMultiple().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getResultMultiple_TriggerResultLink() {
        return (EReference) getResultMultiple().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getResultMultiple_ResultCompensation() {
        return (EReference) getResultMultiple().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getResultMultiple_ResultError() {
        return (EReference) getResultMultiple().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRole() {
        if (roleEClass == null) {
            roleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(174);
        }
        return roleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRole_Name() {
        return (EAttribute) getRole().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRole_PortType() {
        return (EAttribute) getRole().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRoute() {
        if (routeEClass == null) {
            routeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(177);
        }
        return routeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRoute_GatewayType() {
        return (EAttribute) getRoute().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRoute_DeprecatedXorType() {
        return (EAttribute) getRoute().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRoute_DeprecatedInstantiate() {
        return (EAttribute) getRoute().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRoute_ExclusiveType() {
        return (EAttribute) getRoute().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRoute_MarkerVisible() {
        return (EAttribute) getRoute().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRoute_IncomingCondition() {
        return (EAttribute) getRoute().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRoute_OutgoingCondition() {
        return (EAttribute) getRoute().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRuleName() {
        if (ruleNameEClass == null) {
            ruleNameEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(178);
        }
        return ruleNameEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRuleName_Value() {
        return (EAttribute) getRuleName().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRule() {
        if (ruleEClass == null) {
            ruleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(179);
        }
        return ruleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRule_Expression() {
        return (EReference) getRule().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getScale() {
        if (scaleEClass == null) {
            scaleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(180);
        }
        return scaleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScale_Value() {
        return (EAttribute) getScale().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getSchema() {
        if (schemaEClass == null) {
            schemaEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(181);
        }
        return schemaEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSchema_Any() {
        return (EAttribute) getSchema().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getScript() {
        if (scriptEClass == null) {
            scriptEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(182);
        }
        return scriptEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScript_Any() {
        return (EAttribute) getScript().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScript_Grammar() {
        return (EAttribute) getScript().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScript_Type() {
        return (EAttribute) getScript().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScript_Version() {
        return (EAttribute) getScript().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getService() {
        if (serviceEClass == null) {
            serviceEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(183);
        }
        return serviceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getService_EndPoint() {
        return (EReference) getService().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getService_PortName() {
        return (EAttribute) getService().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getService_ServiceName() {
        return (EAttribute) getService().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimulationInformation() {
        if (simulationInformationEClass == null) {
            simulationInformationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(186);
        }
        return simulationInformationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimulationInformation_Cost() {
        return (EReference) getSimulationInformation().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimulationInformation_TimeEstimation() {
        return (EReference) getSimulationInformation().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimulationInformation_Instantiation() {
        return (EAttribute) getSimulationInformation().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getSplit() {
        if (splitEClass == null) {
            splitEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(187);
        }
        return splitEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getSplit_TransitionRefs() {
        return (EReference) getSplit().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSplit_OutgoingCondition() {
        return (EAttribute) getSplit().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSplit_Type() {
        return (EAttribute) getSplit().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSplit_ExclusiveType() {
        return (EAttribute) getSplit().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getStartEvent() {
        if (startEventEClass == null) {
            startEventEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(188);
        }
        return startEventEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartEvent_TriggerResultMessage() {
        return (EReference) getStartEvent().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartEvent_TriggerTimer() {
        return (EReference) getStartEvent().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartEvent_TriggerConditional() {
        return (EReference) getStartEvent().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartEvent_TriggerResultSignal() {
        return (EReference) getStartEvent().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartEvent_TriggerMultiple() {
        return (EReference) getStartEvent().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStartEvent_Implementation() {
        return (EAttribute) getStartEvent().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStartEvent_Trigger() {
        return (EAttribute) getStartEvent().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartEvent_DeprecatedTriggerRule() {
        return (EReference) getStartEvent().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartEvent_DeprecatedTriggerResultLink() {
        return (EReference) getStartEvent().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getSubFlow() {
        if (subFlowEClass == null) {
            subFlowEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(193);
        }
        return subFlowEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getSubFlow_ActualParameters() {
        return (EReference) getSubFlow().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getSubFlow_DataMappings() {
        return (EReference) getSubFlow().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubFlow_Execution() {
        return (EAttribute) getSubFlow().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubFlow_InstanceDataField() {
        return (EAttribute) getSubFlow().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubFlow_ProcessId() {
        return (EAttribute) getSubFlow().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubFlow_PackageRefId() {
        return (EAttribute) getSubFlow().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubFlow_StartActivityId() {
        return (EAttribute) getSubFlow().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSubFlow_StartActivitySetId() {
        return (EAttribute) getSubFlow().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSubFlow_EndPoint() {
        return (EReference) getSubFlow().getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskApplication() {
        if (taskApplicationEClass == null) {
            taskApplicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(194);
        }
        return taskApplicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskApplication_ActualParameters() {
        return (EReference) getTaskApplication().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskApplication_DataMappings() {
        return (EReference) getTaskApplication().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskApplication_PackageRef() {
        return (EAttribute) getTaskApplication().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskApplication_ApplicationId() {
        return (EAttribute) getTaskApplication().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskManual() {
        if (taskManualEClass == null) {
            taskManualEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(195);
        }
        return taskManualEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskManual_Performers() {
        return (EReference) getTaskManual().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskReceive() {
        if (taskReceiveEClass == null) {
            taskReceiveEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(196);
        }
        return taskReceiveEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskReceive_Message() {
        return (EReference) getTaskReceive().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskReceive_WebServiceOperation() {
        return (EReference) getTaskReceive().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskReceive_Implementation() {
        return (EAttribute) getTaskReceive().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskReceive_Instantiate() {
        return (EAttribute) getTaskReceive().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskReference() {
        if (taskReferenceEClass == null) {
            taskReferenceEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(197);
        }
        return taskReferenceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskReference_TaskRef() {
        return (EAttribute) getTaskReference().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskScript() {
        if (taskScriptEClass == null) {
            taskScriptEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(198);
        }
        return taskScriptEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskScript_Script() {
        return (EReference) getTaskScript().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskSend() {
        if (taskSendEClass == null) {
            taskSendEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(199);
        }
        return taskSendEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskSend_Message() {
        return (EReference) getTaskSend().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskSend_WebServiceOperation() {
        return (EReference) getTaskSend().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskSend_WebServiceFaultCatch() {
        return (EReference) getTaskSend().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskSend_Implementation() {
        return (EAttribute) getTaskSend().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskService() {
        if (taskServiceEClass == null) {
            taskServiceEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(200);
        }
        return taskServiceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskService_MessageIn() {
        return (EReference) getTaskService().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskService_MessageOut() {
        return (EReference) getTaskService().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskService_WebServiceOperation() {
        return (EReference) getTaskService().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskService_WebServiceFaultCatch() {
        return (EReference) getTaskService().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskService_Implementation() {
        return (EAttribute) getTaskService().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTask() {
        if (taskEClass == null) {
            taskEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(201);
        }
        return taskEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskService() {
        return (EReference) getTask().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskReceive() {
        return (EReference) getTask().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskManual() {
        return (EReference) getTask().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskReference() {
        return (EReference) getTask().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskScript() {
        return (EReference) getTask().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskSend() {
        return (EReference) getTask().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskUser() {
        return (EReference) getTask().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTask_TaskApplication() {
        return (EReference) getTask().getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTaskUser() {
        if (taskUserEClass == null) {
            taskUserEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(202);
        }
        return taskUserEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskUser_Performers() {
        return (EReference) getTaskUser().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskUser_MessageIn() {
        return (EReference) getTaskUser().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskUser_MessageOut() {
        return (EReference) getTaskUser().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTaskUser_WebServiceOperation() {
        return (EReference) getTaskUser().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTaskUser_Implementation() {
        return (EAttribute) getTaskUser().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTimeEstimation() {
        if (timeEstimationEClass == null) {
            timeEstimationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(205);
        }
        return timeEstimationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTimeEstimation_WaitingTime() {
        return (EReference) getTimeEstimation().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTimeEstimation_WorkingTime() {
        return (EReference) getTimeEstimation().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTimeEstimation_Duration() {
        return (EReference) getTimeEstimation().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTransaction() {
        if (transactionEClass == null) {
            transactionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(208);
        }
        return transactionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransaction_TransactionId() {
        return (EAttribute) getTransaction().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransaction_TransactionMethod() {
        return (EAttribute) getTransaction().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransaction_TransactionProtocol() {
        return (EAttribute) getTransaction().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTransitionRef() {
        if (transitionRefEClass == null) {
            transitionRefEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(209);
        }
        return transitionRefEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransitionRef_Id() {
        return (EAttribute) getTransitionRef().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransitionRef_Name() {
        return (EAttribute) getTransitionRef().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTransitionRestriction() {
        if (transitionRestrictionEClass == null) {
            transitionRestrictionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(210);
        }
        return transitionRestrictionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTransitionRestriction_Join() {
        return (EReference) getTransitionRestriction().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTransitionRestriction_Split() {
        return (EReference) getTransitionRestriction().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTransition() {
        if (transitionEClass == null) {
            transitionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(211);
        }
        return transitionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTransition_Condition() {
        return (EReference) getTransition().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTransition_Assignments() {
        return (EReference) getTransition().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTransition_Object() {
        return (EReference) getTransition().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransition_From() {
        return (EAttribute) getTransition().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransition_Quantity() {
        return (EAttribute) getTransition().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransition_To() {
        return (EAttribute) getTransition().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTransition_FlowContainer() {
        return (EReference) getTransition().getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerIntermediateMultiple() {
        if (triggerIntermediateMultipleEClass == null) {
            triggerIntermediateMultipleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(212);
        }
        return triggerIntermediateMultipleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerIntermediateMultiple_TriggerResultMessage() {
        return (EReference) getTriggerIntermediateMultiple()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerIntermediateMultiple_TriggerTimer() {
        return (EReference) getTriggerIntermediateMultiple()
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerIntermediateMultiple_ResultError() {
        return (EReference) getTriggerIntermediateMultiple()
                .getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerIntermediateMultiple_TriggerResultCompensation() {
        return (EReference) getTriggerIntermediateMultiple()
                .getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerIntermediateMultiple_DeprecatedResultCompensation() {
        return (EReference) getTriggerIntermediateMultiple()
                .getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerIntermediateMultiple_TriggerConditional() {
        return (EReference) getTriggerIntermediateMultiple()
                .getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerIntermediateMultiple_TriggerResultLink() {
        return (EReference) getTriggerIntermediateMultiple()
                .getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerMultiple() {
        if (triggerMultipleEClass == null) {
            triggerMultipleEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(213);
        }
        return triggerMultipleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerMultiple_TriggerResultMessage() {
        return (EReference) getTriggerMultiple().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerMultiple_TriggerTimer() {
        return (EReference) getTriggerMultiple().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerMultiple_TriggerConditional() {
        return (EReference) getTriggerMultiple().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerMultiple_TriggerResultLink() {
        return (EReference) getTriggerMultiple().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerMultiple_DeprecatedTriggerRule() {
        return (EReference) getTriggerMultiple().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerResultCancel() {
        if (triggerResultCancelEClass == null) {
            triggerResultCancelEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(214);
        }
        return triggerResultCancelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerResultCompensation() {
        if (triggerResultCompensationEClass == null) {
            triggerResultCompensationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(215);
        }
        return triggerResultCompensationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultCompensation_ActivityId() {
        return (EAttribute) getTriggerResultCompensation()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerResultSignal() {
        if (triggerResultSignalEClass == null) {
            triggerResultSignalEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(216);
        }
        return triggerResultSignalEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerResultSignal_Properties() {
        return (EReference) getTriggerResultSignal().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultSignal_CatchThrow() {
        return (EAttribute) getTriggerResultSignal().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultSignal_Name() {
        return (EAttribute) getTriggerResultSignal().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerResultLink() {
        if (triggerResultLinkEClass == null) {
            triggerResultLinkEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(217);
        }
        return triggerResultLinkEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultLink_DeprecatedLinkId() {
        return (EAttribute) getTriggerResultLink().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultLink_DeprecatedProcessRef() {
        return (EAttribute) getTriggerResultLink().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultLink_CatchThrow() {
        return (EAttribute) getTriggerResultLink().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultLink_Name() {
        return (EAttribute) getTriggerResultLink().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerResultMessage() {
        if (triggerResultMessageEClass == null) {
            triggerResultMessageEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(218);
        }
        return triggerResultMessageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerResultMessage_Message() {
        return (EReference) getTriggerResultMessage().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerResultMessage_WebServiceOperation() {
        return (EReference) getTriggerResultMessage().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerResultMessage_CatchThrow() {
        return (EAttribute) getTriggerResultMessage().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerConditional() {
        if (triggerConditionalEClass == null) {
            triggerConditionalEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(219);
        }
        return triggerConditionalEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerConditional_ConditionName() {
        return (EAttribute) getTriggerConditional().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerConditional_Expression() {
        return (EReference) getTriggerConditional().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTriggerTimer() {
        if (triggerTimerEClass == null) {
            triggerTimerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(220);
        }
        return triggerTimerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerTimer_DeprecatedTimeCycle() {
        return (EAttribute) getTriggerTimer().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTriggerTimer_DeprecatedTimeDate() {
        return (EAttribute) getTriggerTimer().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerTimer_TimeCycle() {
        return (EReference) getTriggerTimer().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTriggerTimer_TimeDate() {
        return (EReference) getTriggerTimer().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getTypeDeclaration() {
        if (typeDeclarationEClass == null) {
            typeDeclarationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(223);
        }
        return typeDeclarationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_BasicType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_DeclaredType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_SchemaType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_ExternalReference() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_RecordType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_UnionType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_EnumerationType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_ArrayType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeDeclaration_ListType() {
        return (EReference) getTypeDeclaration().getEStructuralFeatures()
                .get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getUnionType() {
        if (unionTypeEClass == null) {
            unionTypeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(224);
        }
        return unionTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getUnionType_Member() {
        return (EReference) getUnionType().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getValidFrom() {
        if (validFromEClass == null) {
            validFromEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(226);
        }
        return validFromEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getValidFrom_Value() {
        return (EAttribute) getValidFrom().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getValidTo() {
        if (validToEClass == null) {
            validToEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(227);
        }
        return validToEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getValidTo_Value() {
        return (EAttribute) getValidTo().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getVendorExtensions() {
        if (vendorExtensionsEClass == null) {
            vendorExtensionsEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(228);
        }
        return vendorExtensionsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getVendorExtensions_VendorExtension() {
        return (EReference) getVendorExtensions().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getVendorExtension() {
        if (vendorExtensionEClass == null) {
            vendorExtensionEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(229);
        }
        return vendorExtensionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getVendorExtension_ExtensionDescription() {
        return (EAttribute) getVendorExtension().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getVendorExtension_SchemaLocation() {
        return (EAttribute) getVendorExtension().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getVendorExtension_ToolId() {
        return (EAttribute) getVendorExtension().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getWaitingTime() {
        if (waitingTimeEClass == null) {
            waitingTimeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(231);
        }
        return waitingTimeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWaitingTime_Value() {
        return (EAttribute) getWaitingTime().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getWebServiceFaultCatch() {
        if (webServiceFaultCatchEClass == null) {
            webServiceFaultCatchEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(232);
        }
        return webServiceFaultCatchEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getWebServiceFaultCatch_Message() {
        return (EReference) getWebServiceFaultCatch().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getWebServiceFaultCatch_BlockActivity() {
        return (EReference) getWebServiceFaultCatch().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getWebServiceFaultCatch_TransitionRef() {
        return (EReference) getWebServiceFaultCatch().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWebServiceFaultCatch_FaultName() {
        return (EAttribute) getWebServiceFaultCatch().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getWebServiceOperation() {
        if (webServiceOperationEClass == null) {
            webServiceOperationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(233);
        }
        return webServiceOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getWebServiceOperation_Partner() {
        return (EReference) getWebServiceOperation().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getWebServiceOperation_Service() {
        return (EReference) getWebServiceOperation().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWebServiceOperation_OperationName() {
        return (EAttribute) getWebServiceOperation().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getWebServiceApplication() {
        if (webServiceApplicationEClass == null) {
            webServiceApplicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(234);
        }
        return webServiceApplicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getWebServiceApplication_WebServiceOperation() {
        return (EReference) getWebServiceApplication().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getWebServiceApplication_WebServiceFaultCatch() {
        return (EReference) getWebServiceApplication().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWebServiceApplication_InputMsgName() {
        return (EAttribute) getWebServiceApplication().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWebServiceApplication_OutputMsgName() {
        return (EAttribute) getWebServiceApplication().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkingTime() {
        if (workingTimeEClass == null) {
            workingTimeEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(235);
        }
        return workingTimeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkingTime_Value() {
        return (EAttribute) getWorkingTime().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getXsltApplication() {
        if (xsltApplicationEClass == null) {
            xsltApplicationEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(236);
        }
        return xsltApplicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXsltApplication_Location() {
        return (EAttribute) getXsltApplication().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getNamedElement() {
        if (namedElementEClass == null) {
            namedElementEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(127);
        }
        return namedElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_Name() {
        return (EAttribute) getNamedElement().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getFlowContainer() {
        if (flowContainerEClass == null) {
            flowContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(76);
        }
        return flowContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getFlowContainer_Activities() {
        return (EReference) getFlowContainer().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getFlowContainer_Transitions() {
        return (EReference) getFlowContainer().getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFlowContainer_AdHoc() {
        return (EAttribute) getFlowContainer().getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFlowContainer_AdHocCompletionCondition() {
        return (EAttribute) getFlowContainer().getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFlowContainer_AdHocOrdering() {
        return (EAttribute) getFlowContainer().getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFlowContainer_DefaultStartActivityId() {
        return (EAttribute) getFlowContainer().getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getDescribedElement() {
        if (describedElementEClass == null) {
            describedElementEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(48);
        }
        return describedElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getDescribedElement_Description() {
        return (EReference) getDescribedElement().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getProcessRelevantData() {
        if (processRelevantDataEClass == null) {
            processRelevantDataEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(159);
        }
        return processRelevantDataEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessRelevantData_DataType() {
        return (EReference) getProcessRelevantData().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessRelevantData_Length() {
        return (EReference) getProcessRelevantData().getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcessRelevantData_IsArray() {
        return (EAttribute) getProcessRelevantData().getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcessRelevantData_ReadOnly() {
        return (EAttribute) getProcessRelevantData().getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPropertyInput() {
        if (propertyInputEClass == null) {
            propertyInputEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(162);
        }
        return propertyInputEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPropertyInput_PropertyId() {
        return (EAttribute) getPropertyInput().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getUniqueIdElement() {
        if (uniqueIdElementEClass == null) {
            uniqueIdElementEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(225);
        }
        return uniqueIdElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUniqueIdElement_Id() {
        return (EAttribute) getUniqueIdElement().getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOtherAttributesContainer() {
        if (otherAttributesContainerEClass == null) {
            otherAttributesContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(133);
        }
        return otherAttributesContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOtherAttributesContainer_OtherAttributes() {
        return (EAttribute) getOtherAttributesContainer()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOtherElementsContainer() {
        if (otherElementsContainerEClass == null) {
            otherElementsContainerEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(134);
        }
        return otherElementsContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOtherElementsContainer_OtherElements() {
        return (EAttribute) getOtherElementsContainer()
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPerformers() {
        if (performersEClass == null) {
            performersEClass =
                    (EClass) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(150);
        }
        return performersEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPerformers_Performers() {
        return (EReference) getPerformers().getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAccessLevelType() {
        if (accessLevelTypeEEnum == null) {
            accessLevelTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(0);
        }
        return accessLevelTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAdHocOrderingType() {
        if (adHocOrderingTypeEEnum == null) {
            adHocOrderingTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(4);
        }
        return adHocOrderingTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getArtifactType() {
        if (artifactTypeEEnum == null) {
            artifactTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(12);
        }
        return artifactTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAssignTimeType() {
        if (assignTimeTypeEEnum == null) {
            assignTimeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(16);
        }
        return assignTimeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAssociationDirectionType() {
        if (associationDirectionTypeEEnum == null) {
            associationDirectionTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(18);
        }
        return associationDirectionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDirectionType() {
        if (directionTypeEEnum == null) {
            directionTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(50);
        }
        return directionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDurationUnitType() {
        if (durationUnitTypeEEnum == null) {
            durationUnitTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(55);
        }
        return durationUnitTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getEndPointTypeType() {
        if (endPointTypeTypeEEnum == null) {
            endPointTypeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(60);
        }
        return endPointTypeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getExclusiveType() {
        if (exclusiveTypeEEnum == null) {
            exclusiveTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(66);
        }
        return exclusiveTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getExecutionType() {
        if (executionTypeEEnum == null) {
            executionTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(67);
        }
        return executionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getFinishModeType() {
        if (finishModeTypeEEnum == null) {
            finishModeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(74);
        }
        return finishModeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getJoinSplitType() {
        if (joinSplitTypeEEnum == null) {
            joinSplitTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(80);
        }
        return joinSplitTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getGraphConformanceType() {
        if (graphConformanceTypeEEnum == null) {
            graphConformanceTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(83);
        }
        return graphConformanceTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getImplementationType() {
        if (implementationTypeEEnum == null) {
            implementationTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(91);
        }
        return implementationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getInstantiationType() {
        if (instantiationTypeEEnum == null) {
            instantiationTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(96);
        }
        return instantiationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getIsArrayType() {
        if (isArrayTypeEEnum == null) {
            isArrayTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(100);
        }
        return isArrayTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getLoopType() {
        if (loopTypeEEnum == null) {
            loopTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(113);
        }
        return loopTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getMIFlowConditionType() {
        if (miFlowConditionTypeEEnum == null) {
            miFlowConditionTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(119);
        }
        return miFlowConditionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getMIOrderingType() {
        if (miOrderingTypeEEnum == null) {
            miOrderingTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(121);
        }
        return miOrderingTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getModeType() {
        if (modeTypeEEnum == null) {
            modeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(123);
        }
        return modeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getOrientationType() {
        if (orientationTypeEEnum == null) {
            orientationTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(131);
        }
        return orientationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getProcessType() {
        if (processTypeEEnum == null) {
            processTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(160);
        }
        return processTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getPublicationStatusType() {
        if (publicationStatusTypeEEnum == null) {
            publicationStatusTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(163);
        }
        return publicationStatusTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getResultType() {
        if (resultTypeEEnum == null) {
            resultTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(172);
        }
        return resultTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getRoleType() {
        if (roleTypeEEnum == null) {
            roleTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(175);
        }
        return roleTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSHAPEType() {
        if (shapeTypeEEnum == null) {
            shapeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(184);
        }
        return shapeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getStartModeType() {
        if (startModeTypeEEnum == null) {
            startModeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(189);
        }
        return startModeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getStatusType() {
        if (statusTypeEEnum == null) {
            statusTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(191);
        }
        return statusTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getTestTimeType() {
        if (testTimeTypeEEnum == null) {
            testTimeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(203);
        }
        return testTimeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getTransactionMethodType() {
        if (transactionMethodTypeEEnum == null) {
            transactionMethodTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(206);
        }
        return transactionMethodTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getTriggerType() {
        if (triggerTypeEEnum == null) {
            triggerTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(221);
        }
        return triggerTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getViewType() {
        if (viewTypeEEnum == null) {
            viewTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(230);
        }
        return viewTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDeprecatedXorType() {
        if (deprecatedXorTypeEEnum == null) {
            deprecatedXorTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(237);
        }
        return deprecatedXorTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getConditionType() {
        if (conditionTypeEEnum == null) {
            conditionTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(31);
        }
        return conditionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getParticipantType() {
        if (participantTypeEEnum == null) {
            participantTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(143);
        }
        return participantTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getBasicTypeType() {
        if (basicTypeTypeEEnum == null) {
            basicTypeTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(22);
        }
        return basicTypeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getBPMNModelPortabilityConformance() {
        if (bpmnModelPortabilityConformanceEEnum == null) {
            bpmnModelPortabilityConformanceEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(24);
        }
        return bpmnModelPortabilityConformanceEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getCatchThrow() {
        if (catchThrowEEnum == null) {
            catchThrowEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(26);
        }
        return catchThrowEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getGatewayType() {
        if (gatewayTypeEEnum == null) {
            gatewayTypeEEnum =
                    (EEnum) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(81);
        }
        return gatewayTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAccessLevelTypeObject() {
        if (accessLevelTypeObjectEDataType == null) {
            accessLevelTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(1);
        }
        return accessLevelTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAdHocOrderingTypeObject() {
        if (adHocOrderingTypeObjectEDataType == null) {
            adHocOrderingTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(5);
        }
        return adHocOrderingTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getArtifactTypeObject() {
        if (artifactTypeObjectEDataType == null) {
            artifactTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(13);
        }
        return artifactTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAssignTimeTypeObject() {
        if (assignTimeTypeObjectEDataType == null) {
            assignTimeTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(17);
        }
        return assignTimeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAssociationDirectionTypeObject() {
        if (associationDirectionTypeObjectEDataType == null) {
            associationDirectionTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(19);
        }
        return associationDirectionTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getDirectionTypeObject() {
        if (directionTypeObjectEDataType == null) {
            directionTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(51);
        }
        return directionTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getDurationUnitTypeObject() {
        if (durationUnitTypeObjectEDataType == null) {
            durationUnitTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(56);
        }
        return durationUnitTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getEndPointTypeTypeObject() {
        if (endPointTypeTypeObjectEDataType == null) {
            endPointTypeTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(61);
        }
        return endPointTypeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getExecutionTypeObject() {
        if (executionTypeObjectEDataType == null) {
            executionTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(68);
        }
        return executionTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getFinishModeTypeObject() {
        if (finishModeTypeObjectEDataType == null) {
            finishModeTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(75);
        }
        return finishModeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getGatewayTypeObject() {
        if (gatewayTypeObjectEDataType == null) {
            gatewayTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(82);
        }
        return gatewayTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getGraphConformanceTypeObject() {
        if (graphConformanceTypeObjectEDataType == null) {
            graphConformanceTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(84);
        }
        return graphConformanceTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getImplementationTypeObject() {
        if (implementationTypeObjectEDataType == null) {
            implementationTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(93);
        }
        return implementationTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getInstantiationTypeObject() {
        if (instantiationTypeObjectEDataType == null) {
            instantiationTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(97);
        }
        return instantiationTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getIsArrayTypeObject() {
        if (isArrayTypeObjectEDataType == null) {
            isArrayTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(101);
        }
        return isArrayTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getLoopTypeObject() {
        if (loopTypeObjectEDataType == null) {
            loopTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(114);
        }
        return loopTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getMIFlowConditionTypeObject() {
        if (miFlowConditionTypeObjectEDataType == null) {
            miFlowConditionTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(120);
        }
        return miFlowConditionTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getMIOrderingTypeObject() {
        if (miOrderingTypeObjectEDataType == null) {
            miOrderingTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(122);
        }
        return miOrderingTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getModeTypeObject() {
        if (modeTypeObjectEDataType == null) {
            modeTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(124);
        }
        return modeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getOrientationTypeObject() {
        if (orientationTypeObjectEDataType == null) {
            orientationTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(132);
        }
        return orientationTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getProcessTypeObject() {
        if (processTypeObjectEDataType == null) {
            processTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(161);
        }
        return processTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getPublicationStatusTypeObject() {
        if (publicationStatusTypeObjectEDataType == null) {
            publicationStatusTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(164);
        }
        return publicationStatusTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getResultTypeObject() {
        if (resultTypeObjectEDataType == null) {
            resultTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(173);
        }
        return resultTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getRoleTypeObject() {
        if (roleTypeObjectEDataType == null) {
            roleTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(176);
        }
        return roleTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSHAPETypeObject() {
        if (shapeTypeObjectEDataType == null) {
            shapeTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(185);
        }
        return shapeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getStartModeTypeObject() {
        if (startModeTypeObjectEDataType == null) {
            startModeTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(190);
        }
        return startModeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getStatusTypeObject() {
        if (statusTypeObjectEDataType == null) {
            statusTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(192);
        }
        return statusTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTestTimeTypeObject() {
        if (testTimeTypeObjectEDataType == null) {
            testTimeTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(204);
        }
        return testTimeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTransactionMethodTypeObject() {
        if (transactionMethodTypeObjectEDataType == null) {
            transactionMethodTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(207);
        }
        return transactionMethodTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTriggerTypeObject() {
        if (triggerTypeObjectEDataType == null) {
            triggerTypeObjectEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(222);
        }
        return triggerTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getIdReferenceString() {
        if (idReferenceStringEDataType == null) {
            idReferenceStringEDataType =
                    (EDataType) EPackage.Registry.INSTANCE
                            .getEPackage(Xpdl2Package.eNS_URI)
                            .getEClassifiers().get(90);
        }
        return idReferenceStringEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Xpdl2Factory getXpdl2Factory() {
        return (Xpdl2Factory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isLoaded = false;

    /**
     * Laods the package and any sub-packages from their serialized form. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void loadPackage() {
        if (isLoaded)
            return;
        isLoaded = true;

        URL url = getClass().getResource(packageFilename);
        if (url == null) {
            throw new RuntimeException(
                    "Missing serialized package: " + packageFilename); //$NON-NLS-1$
        }
        URI uri = URI.createURI(url.toString());
        Resource resource = new EcoreResourceFactoryImpl().createResource(uri);
        try {
            resource.load(null);
        } catch (IOException exception) {
            throw new WrappedException(exception);
        }
        initializeFromLoadedEPackage(this, (EPackage) resource.getContents()
                .get(0));
        createResource(eNS_URI);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isFixed = false;

    /**
     * <!-- begin-user-doc --> Fixes up the loaded package, to make it appear as
     * if it had been programmatically built.
     * 
     * This method is prevented from auto-generating so that we can set
     * NamedElement Id attribute to changeable (without making a setId() method
     * available). Basically, creating new named element always sets a new
     * unique id, so that when perform a copy of the model object it does not
     * have same id as original UNLESS the id attribute is changeable in which
     * case the copy model object code will quite happilly copy the id across
     * too.
     * 
     * <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void fixPackageContents() {
        if (isFixed)
            return;
        isFixed = true;
        fixEClassifiers();

        // Set changeable to true for UniqueIdElement Id's.
        // We don't want get/setId methods for the Unique Id in
        // UniqueIdElement derived classes (so we set changeable=false in
        // xpdl2.ecore.
        // However, we do want Id transferred across when we take a copy of the
        // object, setting changeable=true programmatically will cause this to
        // happen.

        getUniqueIdElement_Id().setChangeable(true);
    }

    /**
     * Sets the instance class on the given classifier.
     * <!-- begin-user-doc --> Sets the instance class on the given classifier.
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void fixInstanceClass(EClassifier eClassifier) {
        if (eClassifier.getInstanceClassName() == null) {
            eClassifier
                    .setInstanceClassName("com.tibco.xpd.xpdl2." + eClassifier.getName()); //$NON-NLS-1$
            setGeneratedClassName(eClassifier);
        }
    }

} // Xpdl2PackageImpl
