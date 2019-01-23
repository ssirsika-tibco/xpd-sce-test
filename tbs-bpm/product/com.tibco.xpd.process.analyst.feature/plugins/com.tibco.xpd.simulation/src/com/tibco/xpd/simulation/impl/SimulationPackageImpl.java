/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.DocumentRoot;
import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ExponentialRealDistribution;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ExternalEmpiricalDistribution;
import com.tibco.xpd.simulation.LoopControlStrategy;
import com.tibco.xpd.simulation.LoopControlTransitionType;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.MaxElapseTimeStrategyType;
import com.tibco.xpd.simulation.MaxLoopCountStrategyType;
import com.tibco.xpd.simulation.NormalDistributionStrategyType;
import com.tibco.xpd.simulation.NormalRealDistribution;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.RealDistributionCategory;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.TimeUnitCostType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.UniformRealDistribution;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;

import com.tibco.xpd.simulation.util.SimulationValidator;

import com.tibco.xpd.xpdl2.Xpdl2Package;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimulationPackageImpl extends EPackageImpl implements
        SimulationPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass activitySimulationDataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass abstractBasicDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass constantRealDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass enumBasedExpressionTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass enumerationValueTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass exponentialRealDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass expressionTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass externalEmpiricalDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass loopControlTransitionTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass loopControlTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass maxElapseTimeStrategyTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass maxLoopCountStrategyTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass normalRealDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterBasedDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterDependentDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass participantSimulationDataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simulationRealDistributionTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass splitParameterTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass splitSimulationDataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass startSimulationDataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass structuredConditionTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass timeUnitCostTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass transitionSimulationDataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass uniformRealDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workflowProcessSimulationDataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass normalDistributionStrategyTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum loopControlStrategyEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum realDistributionCategoryEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum timeDisplayUnitTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType instancesTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType loopControlStrategyObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType operatorTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType realDistributionCategoryObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType timeDisplayUnitTypeObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.simulation.SimulationPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private SimulationPackageImpl() {
        super(eNS_URI, SimulationFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static SimulationPackage init() {
        if (isInited)
            return (SimulationPackage) EPackage.Registry.INSTANCE
                    .getEPackage(SimulationPackage.eNS_URI);

        // Obtain or create and register package
        SimulationPackageImpl theSimulationPackage =
                (SimulationPackageImpl) (EPackage.Registry.INSTANCE
                        .getEPackage(eNS_URI) instanceof SimulationPackageImpl ? EPackage.Registry.INSTANCE
                        .getEPackage(eNS_URI)
                        : new SimulationPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        Xpdl2Package.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theSimulationPackage.createPackageContents();

        // Initialize created meta-data
        theSimulationPackage.initializePackageContents();

        // Register package validator
        EValidator.Registry.INSTANCE.put(theSimulationPackage,
                new EValidator.Descriptor() {
                    public EValidator getEValidator() {
                        return SimulationValidator.INSTANCE;
                    }
                });

        // Mark meta-data to indicate it can't be changed
        theSimulationPackage.freeze();

        return theSimulationPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getActivitySimulationDataType() {
        return activitySimulationDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivitySimulationDataType_Duration() {
        return (EReference) activitySimulationDataTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivitySimulationDataType_DisplayTimeUnit() {
        return (EAttribute) activitySimulationDataTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivitySimulationDataType_LoopControl() {
        return (EReference) activitySimulationDataTypeEClass
                .getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getActivitySimulationDataType_SlaMaximumDelay() {
        return (EAttribute) activitySimulationDataTypeEClass
                .getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAbstractBasicDistribution() {
        return abstractBasicDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getConstantRealDistribution() {
        return constantRealDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConstantRealDistribution_ConstantValue() {
        return (EAttribute) constantRealDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ActivitySimulationData() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ParticipantSimulationData() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SplitSimulationData() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_StartSimulationData() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_TransitionSimulationData() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_WorkflowProcessSimulationData() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnumBasedExpressionType() {
        return enumBasedExpressionTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumBasedExpressionType_EnumValue() {
        return (EAttribute) enumBasedExpressionTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumBasedExpressionType_ParamName() {
        return (EAttribute) enumBasedExpressionTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnumerationValueType() {
        return enumerationValueTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumerationValueType_Description() {
        return (EAttribute) enumerationValueTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumerationValueType_Value() {
        return (EAttribute) enumerationValueTypeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumerationValueType_WeightingFactor() {
        return (EAttribute) enumerationValueTypeEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExponentialRealDistribution() {
        return exponentialRealDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExponentialRealDistribution_Mean() {
        return (EAttribute) exponentialRealDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExpressionType() {
        return expressionTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getExpressionType_EnumBasedExpression() {
        return (EReference) expressionTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExpressionType_ScriptExpression() {
        return (EAttribute) expressionTypeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getExpressionType_StructuredExpression() {
        return (EReference) expressionTypeEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getExpressionType_Default() {
        return (EReference) expressionTypeEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExternalEmpiricalDistribution() {
        return externalEmpiricalDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExternalEmpiricalDistribution_Reference() {
        return (EAttribute) externalEmpiricalDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExternalEmpiricalDistribution_Type() {
        return (EAttribute) externalEmpiricalDistributionEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLoopControlTransitionType() {
        return loopControlTransitionTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopControlTransitionType_DecisionActivity() {
        return (EAttribute) loopControlTransitionTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoopControlTransitionType_ToActivity() {
        return (EAttribute) loopControlTransitionTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLoopControlType() {
        return loopControlTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoopControlType_MaxLoopCountStrategy() {
        return (EReference) loopControlTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoopControlType_NormalDistributionStrategy() {
        return (EReference) loopControlTypeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLoopControlType_MaxElapseTimeStrategy() {
        return (EReference) loopControlTypeEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMaxElapseTimeStrategyType() {
        return maxElapseTimeStrategyTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMaxElapseTimeStrategyType_DisplayTimeUnit() {
        return (EAttribute) maxElapseTimeStrategyTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMaxElapseTimeStrategyType_MaxElapseTime() {
        return (EAttribute) maxElapseTimeStrategyTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMaxLoopCountStrategyType() {
        return maxLoopCountStrategyTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMaxLoopCountStrategyType_MaxLoopCount() {
        return (EAttribute) maxLoopCountStrategyTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNormalRealDistribution() {
        return normalRealDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNormalRealDistribution_Mean() {
        return (EAttribute) normalRealDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNormalRealDistribution_StandardDeviation() {
        return (EAttribute) normalRealDistributionEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterBasedDistribution() {
        return parameterBasedDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParameterBasedDistribution_ParameterDependentDistributions() {
        return (EReference) parameterBasedDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterDependentDistribution() {
        return parameterDependentDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParameterDependentDistribution_BasicDistribution() {
        return (EReference) parameterDependentDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParameterDependentDistribution_Expression() {
        return (EReference) parameterDependentDistributionEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterDistribution() {
        return parameterDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParameterDistribution_EnumerationValue() {
        return (EReference) parameterDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterDistribution_ParameterId() {
        return (EAttribute) parameterDistributionEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParticipantSimulationDataType() {
        return participantSimulationDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParticipantSimulationDataType_Instances() {
        return (EAttribute) participantSimulationDataTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParticipantSimulationDataType_TimeUnitCost() {
        return (EReference) participantSimulationDataTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParticipantSimulationDataType_SlaMinimumUtilisation() {
        return (EAttribute) participantSimulationDataTypeEClass
                .getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParticipantSimulationDataType_SlaMaximumUtilisation() {
        return (EAttribute) participantSimulationDataTypeEClass
                .getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimulationRealDistributionType() {
        return simulationRealDistributionTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimulationRealDistributionType_BasicDistribution() {
        return (EReference) simulationRealDistributionTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimulationRealDistributionType_ParameterBasedDistribution() {
        return (EReference) simulationRealDistributionTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSplitParameterType() {
        return splitParameterTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSplitParameterType_ParameterId() {
        return (EAttribute) splitParameterTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSplitSimulationDataType() {
        return splitSimulationDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSplitSimulationDataType_ParameterDeterminedSplit() {
        return (EAttribute) splitSimulationDataTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSplitSimulationDataType_SplitParameter() {
        return (EReference) splitSimulationDataTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getStartSimulationDataType() {
        return startSimulationDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getStartSimulationDataType_Duration() {
        return (EReference) startSimulationDataTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStartSimulationDataType_DisplayTimeUnit() {
        return (EAttribute) startSimulationDataTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStartSimulationDataType_NumberOfCases() {
        return (EAttribute) startSimulationDataTypeEClass
                .getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getStructuredConditionType() {
        return structuredConditionTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructuredConditionType_ParameterId() {
        return (EAttribute) structuredConditionTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructuredConditionType_Operator() {
        return (EAttribute) structuredConditionTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStructuredConditionType_ParameterValue() {
        return (EAttribute) structuredConditionTypeEClass
                .getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTimeUnitCostType() {
        return timeUnitCostTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTimeUnitCostType_Cost() {
        return (EAttribute) timeUnitCostTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTimeUnitCostType_TimeDisplayUnit() {
        return (EAttribute) timeUnitCostTypeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTransitionSimulationDataType() {
        return transitionSimulationDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTransitionSimulationDataType_ParameterDeterminedCondition() {
        return (EAttribute) transitionSimulationDataTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTransitionSimulationDataType_StructuredCondition() {
        return (EReference) transitionSimulationDataTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUniformRealDistribution() {
        return uniformRealDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUniformRealDistribution_LowerBorder() {
        return (EAttribute) uniformRealDistributionEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUniformRealDistribution_UpperBorder() {
        return (EAttribute) uniformRealDistributionEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkflowProcessSimulationDataType() {
        return workflowProcessSimulationDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkflowProcessSimulationDataType_ParameterDistribution() {
        return (EReference) workflowProcessSimulationDataTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNormalDistributionStrategyType() {
        return normalDistributionStrategyTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNormalDistributionStrategyType_Mean() {
        return (EAttribute) normalDistributionStrategyTypeEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNormalDistributionStrategyType_StandardDeviation() {
        return (EAttribute) normalDistributionStrategyTypeEClass
                .getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getLoopControlStrategy() {
        return loopControlStrategyEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getRealDistributionCategory() {
        return realDistributionCategoryEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getTimeDisplayUnitType() {
        return timeDisplayUnitTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getInstancesType() {
        return instancesTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getLoopControlStrategyObject() {
        return loopControlStrategyObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getOperatorType() {
        return operatorTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getRealDistributionCategoryObject() {
        return realDistributionCategoryObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTimeDisplayUnitTypeObject() {
        return timeDisplayUnitTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationFactory getSimulationFactory() {
        return (SimulationFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        activitySimulationDataTypeEClass =
                createEClass(ACTIVITY_SIMULATION_DATA_TYPE);
        createEReference(activitySimulationDataTypeEClass,
                ACTIVITY_SIMULATION_DATA_TYPE__DURATION);
        createEAttribute(activitySimulationDataTypeEClass,
                ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT);
        createEReference(activitySimulationDataTypeEClass,
                ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL);
        createEAttribute(activitySimulationDataTypeEClass,
                ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY);

        abstractBasicDistributionEClass =
                createEClass(ABSTRACT_BASIC_DISTRIBUTION);

        constantRealDistributionEClass =
                createEClass(CONSTANT_REAL_DISTRIBUTION);
        createEAttribute(constantRealDistributionEClass,
                CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass,
                DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA);
        createEReference(documentRootEClass,
                DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA);
        createEReference(documentRootEClass,
                DOCUMENT_ROOT__SPLIT_SIMULATION_DATA);
        createEReference(documentRootEClass,
                DOCUMENT_ROOT__START_SIMULATION_DATA);
        createEReference(documentRootEClass,
                DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA);
        createEReference(documentRootEClass,
                DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA);

        enumBasedExpressionTypeEClass =
                createEClass(ENUM_BASED_EXPRESSION_TYPE);
        createEAttribute(enumBasedExpressionTypeEClass,
                ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE);
        createEAttribute(enumBasedExpressionTypeEClass,
                ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME);

        enumerationValueTypeEClass = createEClass(ENUMERATION_VALUE_TYPE);
        createEAttribute(enumerationValueTypeEClass,
                ENUMERATION_VALUE_TYPE__DESCRIPTION);
        createEAttribute(enumerationValueTypeEClass,
                ENUMERATION_VALUE_TYPE__VALUE);
        createEAttribute(enumerationValueTypeEClass,
                ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR);

        exponentialRealDistributionEClass =
                createEClass(EXPONENTIAL_REAL_DISTRIBUTION);
        createEAttribute(exponentialRealDistributionEClass,
                EXPONENTIAL_REAL_DISTRIBUTION__MEAN);

        expressionTypeEClass = createEClass(EXPRESSION_TYPE);
        createEReference(expressionTypeEClass,
                EXPRESSION_TYPE__ENUM_BASED_EXPRESSION);
        createEAttribute(expressionTypeEClass,
                EXPRESSION_TYPE__SCRIPT_EXPRESSION);
        createEReference(expressionTypeEClass,
                EXPRESSION_TYPE__STRUCTURED_EXPRESSION);
        createEReference(expressionTypeEClass, EXPRESSION_TYPE__DEFAULT);

        externalEmpiricalDistributionEClass =
                createEClass(EXTERNAL_EMPIRICAL_DISTRIBUTION);
        createEAttribute(externalEmpiricalDistributionEClass,
                EXTERNAL_EMPIRICAL_DISTRIBUTION__REFERENCE);
        createEAttribute(externalEmpiricalDistributionEClass,
                EXTERNAL_EMPIRICAL_DISTRIBUTION__TYPE);

        loopControlTransitionTypeEClass =
                createEClass(LOOP_CONTROL_TRANSITION_TYPE);
        createEAttribute(loopControlTransitionTypeEClass,
                LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY);
        createEAttribute(loopControlTransitionTypeEClass,
                LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY);

        loopControlTypeEClass = createEClass(LOOP_CONTROL_TYPE);
        createEReference(loopControlTypeEClass,
                LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY);
        createEReference(loopControlTypeEClass,
                LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY);
        createEReference(loopControlTypeEClass,
                LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY);

        maxElapseTimeStrategyTypeEClass =
                createEClass(MAX_ELAPSE_TIME_STRATEGY_TYPE);
        createEAttribute(maxElapseTimeStrategyTypeEClass,
                MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT);
        createEAttribute(maxElapseTimeStrategyTypeEClass,
                MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME);

        maxLoopCountStrategyTypeEClass =
                createEClass(MAX_LOOP_COUNT_STRATEGY_TYPE);
        createEAttribute(maxLoopCountStrategyTypeEClass,
                MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT);

        normalRealDistributionEClass = createEClass(NORMAL_REAL_DISTRIBUTION);
        createEAttribute(normalRealDistributionEClass,
                NORMAL_REAL_DISTRIBUTION__MEAN);
        createEAttribute(normalRealDistributionEClass,
                NORMAL_REAL_DISTRIBUTION__STANDARD_DEVIATION);

        parameterBasedDistributionEClass =
                createEClass(PARAMETER_BASED_DISTRIBUTION);
        createEReference(parameterBasedDistributionEClass,
                PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS);

        parameterDependentDistributionEClass =
                createEClass(PARAMETER_DEPENDENT_DISTRIBUTION);
        createEReference(parameterDependentDistributionEClass,
                PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION);
        createEReference(parameterDependentDistributionEClass,
                PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION);

        parameterDistributionEClass = createEClass(PARAMETER_DISTRIBUTION);
        createEReference(parameterDistributionEClass,
                PARAMETER_DISTRIBUTION__ENUMERATION_VALUE);
        createEAttribute(parameterDistributionEClass,
                PARAMETER_DISTRIBUTION__PARAMETER_ID);

        participantSimulationDataTypeEClass =
                createEClass(PARTICIPANT_SIMULATION_DATA_TYPE);
        createEAttribute(participantSimulationDataTypeEClass,
                PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES);
        createEReference(participantSimulationDataTypeEClass,
                PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST);
        createEAttribute(participantSimulationDataTypeEClass,
                PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION);
        createEAttribute(participantSimulationDataTypeEClass,
                PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION);

        simulationRealDistributionTypeEClass =
                createEClass(SIMULATION_REAL_DISTRIBUTION_TYPE);
        createEReference(simulationRealDistributionTypeEClass,
                SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION);
        createEReference(simulationRealDistributionTypeEClass,
                SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION);

        splitParameterTypeEClass = createEClass(SPLIT_PARAMETER_TYPE);
        createEAttribute(splitParameterTypeEClass,
                SPLIT_PARAMETER_TYPE__PARAMETER_ID);

        splitSimulationDataTypeEClass =
                createEClass(SPLIT_SIMULATION_DATA_TYPE);
        createEAttribute(splitSimulationDataTypeEClass,
                SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT);
        createEReference(splitSimulationDataTypeEClass,
                SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER);

        startSimulationDataTypeEClass =
                createEClass(START_SIMULATION_DATA_TYPE);
        createEReference(startSimulationDataTypeEClass,
                START_SIMULATION_DATA_TYPE__DURATION);
        createEAttribute(startSimulationDataTypeEClass,
                START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT);
        createEAttribute(startSimulationDataTypeEClass,
                START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES);

        structuredConditionTypeEClass = createEClass(STRUCTURED_CONDITION_TYPE);
        createEAttribute(structuredConditionTypeEClass,
                STRUCTURED_CONDITION_TYPE__PARAMETER_ID);
        createEAttribute(structuredConditionTypeEClass,
                STRUCTURED_CONDITION_TYPE__OPERATOR);
        createEAttribute(structuredConditionTypeEClass,
                STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE);

        timeUnitCostTypeEClass = createEClass(TIME_UNIT_COST_TYPE);
        createEAttribute(timeUnitCostTypeEClass, TIME_UNIT_COST_TYPE__COST);
        createEAttribute(timeUnitCostTypeEClass,
                TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT);

        transitionSimulationDataTypeEClass =
                createEClass(TRANSITION_SIMULATION_DATA_TYPE);
        createEAttribute(transitionSimulationDataTypeEClass,
                TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION);
        createEReference(transitionSimulationDataTypeEClass,
                TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION);

        normalDistributionStrategyTypeEClass =
                createEClass(NORMAL_DISTRIBUTION_STRATEGY_TYPE);
        createEAttribute(normalDistributionStrategyTypeEClass,
                NORMAL_DISTRIBUTION_STRATEGY_TYPE__MEAN);
        createEAttribute(normalDistributionStrategyTypeEClass,
                NORMAL_DISTRIBUTION_STRATEGY_TYPE__STANDARD_DEVIATION);

        uniformRealDistributionEClass = createEClass(UNIFORM_REAL_DISTRIBUTION);
        createEAttribute(uniformRealDistributionEClass,
                UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER);
        createEAttribute(uniformRealDistributionEClass,
                UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER);

        workflowProcessSimulationDataTypeEClass =
                createEClass(WORKFLOW_PROCESS_SIMULATION_DATA_TYPE);
        createEReference(workflowProcessSimulationDataTypeEClass,
                WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION);

        // Create enums
        loopControlStrategyEEnum = createEEnum(LOOP_CONTROL_STRATEGY);
        realDistributionCategoryEEnum = createEEnum(REAL_DISTRIBUTION_CATEGORY);
        timeDisplayUnitTypeEEnum = createEEnum(TIME_DISPLAY_UNIT_TYPE);

        // Create data types
        instancesTypeEDataType = createEDataType(INSTANCES_TYPE);
        loopControlStrategyObjectEDataType =
                createEDataType(LOOP_CONTROL_STRATEGY_OBJECT);
        operatorTypeEDataType = createEDataType(OPERATOR_TYPE);
        realDistributionCategoryObjectEDataType =
                createEDataType(REAL_DISTRIBUTION_CATEGORY_OBJECT);
        timeDisplayUnitTypeObjectEDataType =
                createEDataType(TIME_DISPLAY_UNIT_TYPE_OBJECT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage =
                (XMLTypePackage) EPackage.Registry.INSTANCE
                        .getEPackage(XMLTypePackage.eNS_URI);
        Xpdl2Package theXpdl2Package =
                (Xpdl2Package) EPackage.Registry.INSTANCE
                        .getEPackage(Xpdl2Package.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        constantRealDistributionEClass.getESuperTypes().add(this
                .getAbstractBasicDistribution());
        exponentialRealDistributionEClass.getESuperTypes().add(this
                .getAbstractBasicDistribution());
        externalEmpiricalDistributionEClass.getESuperTypes().add(this
                .getAbstractBasicDistribution());
        maxElapseTimeStrategyTypeEClass.getESuperTypes().add(this
                .getLoopControlTransitionType());
        maxLoopCountStrategyTypeEClass.getESuperTypes().add(this
                .getLoopControlTransitionType());
        normalRealDistributionEClass.getESuperTypes().add(this
                .getAbstractBasicDistribution());
        normalDistributionStrategyTypeEClass.getESuperTypes().add(this
                .getLoopControlTransitionType());
        uniformRealDistributionEClass.getESuperTypes().add(this
                .getAbstractBasicDistribution());

        // Initialize classes and features; add operations and parameters
        initEClass(activitySimulationDataTypeEClass,
                ActivitySimulationDataType.class,
                "ActivitySimulationDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getActivitySimulationDataType_Duration(),
                this.getSimulationRealDistributionType(),
                null,
                "duration", null, 1, 1, ActivitySimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getActivitySimulationDataType_DisplayTimeUnit(),
                this.getTimeDisplayUnitType(),
                "displayTimeUnit", "YEAR", 1, 1, ActivitySimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEReference(getActivitySimulationDataType_LoopControl(),
                this.getLoopControlType(),
                null,
                "loopControl", null, 0, 1, ActivitySimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getActivitySimulationDataType_SlaMaximumDelay(),
                theXMLTypePackage.getDoubleObject(),
                "slaMaximumDelay", null, 0, 1, ActivitySimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(abstractBasicDistributionEClass,
                AbstractBasicDistribution.class,
                "AbstractBasicDistribution", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(constantRealDistributionEClass,
                ConstantRealDistribution.class,
                "ConstantRealDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getConstantRealDistribution_ConstantValue(),
                theXMLTypePackage.getDouble(),
                "constantValue", null, 1, 1, ConstantRealDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(documentRootEClass,
                DocumentRoot.class,
                "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getDocumentRoot_Mixed(),
                ecorePackage.getEFeatureMapEntry(),
                "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XMLNSPrefixMap(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XSISchemaLocation(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_ActivitySimulationData(),
                this.getActivitySimulationDataType(),
                null,
                "activitySimulationData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_ParticipantSimulationData(),
                this.getParticipantSimulationDataType(),
                null,
                "participantSimulationData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_SplitSimulationData(),
                this.getSplitSimulationDataType(),
                null,
                "splitSimulationData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_StartSimulationData(),
                this.getStartSimulationDataType(),
                null,
                "startSimulationData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_TransitionSimulationData(),
                this.getTransitionSimulationDataType(),
                null,
                "transitionSimulationData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_WorkflowProcessSimulationData(),
                this.getWorkflowProcessSimulationDataType(),
                null,
                "workflowProcessSimulationData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(enumBasedExpressionTypeEClass,
                EnumBasedExpressionType.class,
                "EnumBasedExpressionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getEnumBasedExpressionType_EnumValue(),
                theXMLTypePackage.getAnySimpleType(),
                "enumValue", null, 0, 1, EnumBasedExpressionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getEnumBasedExpressionType_ParamName(),
                theXMLTypePackage.getAnySimpleType(),
                "paramName", null, 0, 1, EnumBasedExpressionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(enumerationValueTypeEClass,
                EnumerationValueType.class,
                "EnumerationValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getEnumerationValueType_Description(),
                theXMLTypePackage.getString(),
                "description", null, 0, 1, EnumerationValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getEnumerationValueType_Value(),
                theXMLTypePackage.getString(),
                "value", null, 1, 1, EnumerationValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getEnumerationValueType_WeightingFactor(),
                theXMLTypePackage.getDouble(),
                "weightingFactor", null, 1, 1, EnumerationValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(exponentialRealDistributionEClass,
                ExponentialRealDistribution.class,
                "ExponentialRealDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getExponentialRealDistribution_Mean(),
                theXMLTypePackage.getDouble(),
                "mean", null, 1, 1, ExponentialRealDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(expressionTypeEClass,
                ExpressionType.class,
                "ExpressionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getExpressionType_EnumBasedExpression(),
                this.getEnumBasedExpressionType(),
                null,
                "enumBasedExpression", null, 0, 1, ExpressionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getExpressionType_ScriptExpression(),
                theXMLTypePackage.getString(),
                "scriptExpression", null, 0, 1, ExpressionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getExpressionType_StructuredExpression(),
                ecorePackage.getEObject(),
                null,
                "structuredExpression", null, 0, 1, ExpressionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getExpressionType_Default(),
                ecorePackage.getEObject(),
                null,
                "default", null, 0, 1, ExpressionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(externalEmpiricalDistributionEClass,
                ExternalEmpiricalDistribution.class,
                "ExternalEmpiricalDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getExternalEmpiricalDistribution_Reference(),
                theXMLTypePackage.getString(),
                "reference", null, 1, 1, ExternalEmpiricalDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getExternalEmpiricalDistribution_Type(),
                theXMLTypePackage.getString(),
                "type", null, 1, 1, ExternalEmpiricalDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(loopControlTransitionTypeEClass,
                LoopControlTransitionType.class,
                "LoopControlTransitionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getLoopControlTransitionType_DecisionActivity(),
                theXpdl2Package.getIdReferenceString(),
                "decisionActivity", null, 1, 1, LoopControlTransitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getLoopControlTransitionType_ToActivity(),
                theXpdl2Package.getIdReferenceString(),
                "toActivity", null, 1, 1, LoopControlTransitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(loopControlTypeEClass,
                LoopControlType.class,
                "LoopControlType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getLoopControlType_MaxLoopCountStrategy(),
                this.getMaxLoopCountStrategyType(),
                null,
                "maxLoopCountStrategy", null, 0, 1, LoopControlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getLoopControlType_NormalDistributionStrategy(),
                this.getNormalDistributionStrategyType(),
                null,
                "normalDistributionStrategy", null, 0, 1, LoopControlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getLoopControlType_MaxElapseTimeStrategy(),
                this.getMaxElapseTimeStrategyType(),
                null,
                "maxElapseTimeStrategy", null, 0, 1, LoopControlType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(maxElapseTimeStrategyTypeEClass,
                MaxElapseTimeStrategyType.class,
                "MaxElapseTimeStrategyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getMaxElapseTimeStrategyType_DisplayTimeUnit(),
                this.getTimeDisplayUnitType(),
                "displayTimeUnit", "YEAR", 1, 1, MaxElapseTimeStrategyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getMaxElapseTimeStrategyType_MaxElapseTime(),
                theXMLTypePackage.getDouble(),
                "maxElapseTime", null, 1, 1, MaxElapseTimeStrategyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(maxLoopCountStrategyTypeEClass,
                MaxLoopCountStrategyType.class,
                "MaxLoopCountStrategyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getMaxLoopCountStrategyType_MaxLoopCount(),
                theXMLTypePackage.getInt(),
                "maxLoopCount", null, 1, 1, MaxLoopCountStrategyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(normalRealDistributionEClass,
                NormalRealDistribution.class,
                "NormalRealDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getNormalRealDistribution_Mean(),
                theXMLTypePackage.getDouble(),
                "mean", null, 1, 1, NormalRealDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getNormalRealDistribution_StandardDeviation(),
                theXMLTypePackage.getDouble(),
                "standardDeviation", null, 1, 1, NormalRealDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(parameterBasedDistributionEClass,
                ParameterBasedDistribution.class,
                "ParameterBasedDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getParameterBasedDistribution_ParameterDependentDistributions(),
                this.getParameterDependentDistribution(),
                null,
                "parameterDependentDistributions", null, 1, -1, ParameterBasedDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(parameterDependentDistributionEClass,
                ParameterDependentDistribution.class,
                "ParameterDependentDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getParameterDependentDistribution_BasicDistribution(),
                this.getAbstractBasicDistribution(),
                null,
                "basicDistribution", null, 0, 1, ParameterDependentDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getParameterDependentDistribution_Expression(),
                this.getExpressionType(),
                null,
                "expression", null, 1, 1, ParameterDependentDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(parameterDistributionEClass,
                ParameterDistribution.class,
                "ParameterDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getParameterDistribution_EnumerationValue(),
                this.getEnumerationValueType(),
                null,
                "enumerationValue", null, 0, -1, ParameterDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getParameterDistribution_ParameterId(),
                theXMLTypePackage.getNMTOKEN(),
                "parameterId", null, 1, 1, ParameterDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(participantSimulationDataTypeEClass,
                ParticipantSimulationDataType.class,
                "ParticipantSimulationDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getParticipantSimulationDataType_Instances(),
                this.getInstancesType(),
                "instances", null, 1, 1, ParticipantSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getParticipantSimulationDataType_TimeUnitCost(),
                this.getTimeUnitCostType(),
                null,
                "timeUnitCost", null, 1, 1, ParticipantSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getParticipantSimulationDataType_SlaMinimumUtilisation(),
                theXMLTypePackage.getDoubleObject(),
                "slaMinimumUtilisation", null, 0, 1, ParticipantSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getParticipantSimulationDataType_SlaMaximumUtilisation(),
                theXMLTypePackage.getDoubleObject(),
                "slaMaximumUtilisation", null, 0, 1, ParticipantSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simulationRealDistributionTypeEClass,
                SimulationRealDistributionType.class,
                "SimulationRealDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getSimulationRealDistributionType_BasicDistribution(),
                this.getAbstractBasicDistribution(),
                null,
                "basicDistribution", null, 0, 1, SimulationRealDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimulationRealDistributionType_ParameterBasedDistribution(),
                this.getParameterBasedDistribution(),
                null,
                "parameterBasedDistribution", null, 0, 1, SimulationRealDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(splitParameterTypeEClass,
                SplitParameterType.class,
                "SplitParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSplitParameterType_ParameterId(),
                theXMLTypePackage.getNMTOKEN(),
                "parameterId", null, 1, 1, SplitParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(splitSimulationDataTypeEClass,
                SplitSimulationDataType.class,
                "SplitSimulationDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSplitSimulationDataType_ParameterDeterminedSplit(),
                theXMLTypePackage.getBoolean(),
                "parameterDeterminedSplit", "true", 1, 1, SplitSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEReference(getSplitSimulationDataType_SplitParameter(),
                this.getSplitParameterType(),
                null,
                "splitParameter", null, 0, 1, SplitSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(startSimulationDataTypeEClass,
                StartSimulationDataType.class,
                "StartSimulationDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getStartSimulationDataType_Duration(),
                this.getAbstractBasicDistribution(),
                null,
                "duration", null, 1, 1, StartSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getStartSimulationDataType_DisplayTimeUnit(),
                this.getTimeDisplayUnitType(),
                "displayTimeUnit", "YEAR", 1, 1, StartSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getStartSimulationDataType_NumberOfCases(),
                theXMLTypePackage.getLong(),
                "numberOfCases", null, 0, 1, StartSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(structuredConditionTypeEClass,
                StructuredConditionType.class,
                "StructuredConditionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getStructuredConditionType_ParameterId(),
                theXMLTypePackage.getNMTOKEN(),
                "parameterId", null, 1, 1, StructuredConditionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getStructuredConditionType_Operator(),
                this.getOperatorType(),
                "operator", null, 1, 1, StructuredConditionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getStructuredConditionType_ParameterValue(),
                theXMLTypePackage.getString(),
                "parameterValue", null, 1, 1, StructuredConditionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(timeUnitCostTypeEClass,
                TimeUnitCostType.class,
                "TimeUnitCostType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getTimeUnitCostType_Cost(),
                theXMLTypePackage.getDouble(),
                "cost", null, 1, 1, TimeUnitCostType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getTimeUnitCostType_TimeDisplayUnit(),
                this.getTimeDisplayUnitType(),
                "timeDisplayUnit", "YEAR", 1, 1, TimeUnitCostType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(transitionSimulationDataTypeEClass,
                TransitionSimulationDataType.class,
                "TransitionSimulationDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getTransitionSimulationDataType_ParameterDeterminedCondition(),
                theXMLTypePackage.getBoolean(),
                "parameterDeterminedCondition", "true", 1, 1, TransitionSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEReference(getTransitionSimulationDataType_StructuredCondition(),
                this.getStructuredConditionType(),
                null,
                "structuredCondition", null, 0, 1, TransitionSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(normalDistributionStrategyTypeEClass,
                NormalDistributionStrategyType.class,
                "NormalDistributionStrategyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getNormalDistributionStrategyType_Mean(),
                theXMLTypePackage.getDouble(),
                "mean", "0.0", 1, 1, NormalDistributionStrategyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getNormalDistributionStrategyType_StandardDeviation(),
                theXMLTypePackage.getDouble(),
                "standardDeviation", "0.0", 1, 1, NormalDistributionStrategyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(uniformRealDistributionEClass,
                UniformRealDistribution.class,
                "UniformRealDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getUniformRealDistribution_LowerBorder(),
                theXMLTypePackage.getDouble(),
                "lowerBorder", null, 1, 1, UniformRealDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getUniformRealDistribution_UpperBorder(),
                theXMLTypePackage.getDouble(),
                "upperBorder", null, 1, 1, UniformRealDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(workflowProcessSimulationDataTypeEClass,
                WorkflowProcessSimulationDataType.class,
                "WorkflowProcessSimulationDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getWorkflowProcessSimulationDataType_ParameterDistribution(),
                this.getParameterDistribution(),
                null,
                "parameterDistribution", null, 0, -1, WorkflowProcessSimulationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        // Initialize enums and add enum literals
        initEEnum(loopControlStrategyEEnum,
                LoopControlStrategy.class,
                "LoopControlStrategy"); //$NON-NLS-1$
        addEEnumLiteral(loopControlStrategyEEnum,
                LoopControlStrategy.MAX_LOOP_COUNT_LITERAL);
        addEEnumLiteral(loopControlStrategyEEnum,
                LoopControlStrategy.MAX_ELAPSE_TIME_LITERAL);
        addEEnumLiteral(loopControlStrategyEEnum,
                LoopControlStrategy.NORMAL_DISTRIBUTION_LITERAL);

        initEEnum(realDistributionCategoryEEnum,
                RealDistributionCategory.class,
                "RealDistributionCategory"); //$NON-NLS-1$
        addEEnumLiteral(realDistributionCategoryEEnum,
                RealDistributionCategory.CONSTANT_LITERAL);
        addEEnumLiteral(realDistributionCategoryEEnum,
                RealDistributionCategory.UNIFORM_LITERAL);
        addEEnumLiteral(realDistributionCategoryEEnum,
                RealDistributionCategory.NORMAL_LITERAL);
        addEEnumLiteral(realDistributionCategoryEEnum,
                RealDistributionCategory.EXPONENTIAL_LITERAL);
        addEEnumLiteral(realDistributionCategoryEEnum,
                RealDistributionCategory.EMPIRICAL_LITERAL);
        addEEnumLiteral(realDistributionCategoryEEnum,
                RealDistributionCategory.PARAMETER_BASED_LITERAL);

        initEEnum(timeDisplayUnitTypeEEnum,
                TimeDisplayUnitType.class,
                "TimeDisplayUnitType"); //$NON-NLS-1$
        addEEnumLiteral(timeDisplayUnitTypeEEnum,
                TimeDisplayUnitType.YEAR_LITERAL);
        addEEnumLiteral(timeDisplayUnitTypeEEnum,
                TimeDisplayUnitType.MONTH_LITERAL);
        addEEnumLiteral(timeDisplayUnitTypeEEnum,
                TimeDisplayUnitType.DAY_LITERAL);
        addEEnumLiteral(timeDisplayUnitTypeEEnum,
                TimeDisplayUnitType.HOUR_LITERAL);
        addEEnumLiteral(timeDisplayUnitTypeEEnum,
                TimeDisplayUnitType.MINUTE_LITERAL);
        addEEnumLiteral(timeDisplayUnitTypeEEnum,
                TimeDisplayUnitType.SECOND_LITERAL);

        // Initialize data types
        initEDataType(instancesTypeEDataType,
                BigInteger.class,
                "InstancesType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(loopControlStrategyObjectEDataType,
                LoopControlStrategy.class,
                "LoopControlStrategyObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(operatorTypeEDataType,
                String.class,
                "OperatorType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(realDistributionCategoryObjectEDataType,
                RealDistributionCategory.class,
                "RealDistributionCategoryObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(timeDisplayUnitTypeObjectEDataType,
                TimeDisplayUnitType.class,
                "TimeDisplayUnitTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$		
        addAnnotation(activitySimulationDataTypeEClass, source, new String[] {
                "name", "ActivitySimulationDataType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getActivitySimulationDataType_Duration(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Duration", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getActivitySimulationDataType_DisplayTimeUnit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisplayTimeUnit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getActivitySimulationDataType_LoopControl(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "LoopControl", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getActivitySimulationDataType_SlaMaximumDelay(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SlaMaximumDelay" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(abstractBasicDistributionEClass, source, new String[] {
                "name", "BasicDistributionType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(constantRealDistributionEClass, source, new String[] {
                "name", "ConstantRealDistribution_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getConstantRealDistribution_ConstantValue(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ConstantValue" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(documentRootEClass, source, new String[] { "name", "", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_Mixed(), source, new String[] {
                "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
                "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XMLNSPrefixMap(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XSISchemaLocation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ActivitySimulationData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivitySimulationData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ParticipantSimulationData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParticipantSimulationData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SplitSimulationData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SplitSimulationData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_StartSimulationData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StartSimulationData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_TransitionSimulationData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TransitionSimulationData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_WorkflowProcessSimulationData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WorkflowProcessSimulationData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(enumBasedExpressionTypeEClass, source, new String[] {
                "name", "EnumBasedExpression_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getEnumBasedExpressionType_EnumValue(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "enumValue" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getEnumBasedExpressionType_ParamName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "paramName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(enumerationValueTypeEClass, source, new String[] {
                "name", "EnumerationValue_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getEnumerationValueType_Description(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Description", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getEnumerationValueType_Value(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Value" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getEnumerationValueType_WeightingFactor(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WeightingFactor" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(exponentialRealDistributionEClass, source, new String[] {
                "name", "ExponentialRealDistribution_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getExponentialRealDistribution_Mean(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Mean" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(expressionTypeEClass, source, new String[] {
                "name", "Expresion_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getExpressionType_EnumBasedExpression(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "EnumBasedExpression", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getExpressionType_ScriptExpression(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ScriptExpression", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getExpressionType_StructuredExpression(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StructuredExpression", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getExpressionType_Default(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Default", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(externalEmpiricalDistributionEClass,
                source,
                new String[] { "name", "ExternalEmpiricalDistribution_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getExternalEmpiricalDistribution_Reference(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Reference" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getExternalEmpiricalDistribution_Type(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(instancesTypeEDataType, source, new String[] {
                "name", "Instances_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "baseType", "http://www.eclipse.org/emf/2003/XMLType#integer", //$NON-NLS-1$ //$NON-NLS-2$
                "minInclusive", "0" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(loopControlStrategyEEnum, source, new String[] {
                "name", "LoopControlStrategy" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(loopControlStrategyObjectEDataType, source, new String[] {
                "name", "LoopControlStrategy:Object", //$NON-NLS-1$ //$NON-NLS-2$
                "baseType", "LoopControlStrategy" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(loopControlTransitionTypeEClass, source, new String[] {
                "name", "LoopControlTransitionType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getLoopControlTransitionType_DecisionActivity(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DecisionActivity", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLoopControlTransitionType_ToActivity(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ToActivity", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(loopControlTypeEClass, source, new String[] {
                "name", "LoopControlType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getLoopControlType_MaxLoopCountStrategy(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MaxLoopCountStrategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLoopControlType_NormalDistributionStrategy(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "NormalDistributionStrategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLoopControlType_MaxElapseTimeStrategy(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MaxElapseTimeStrategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(maxElapseTimeStrategyTypeEClass, source, new String[] {
                "name", "MaxElapseTimeStrategyType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getMaxElapseTimeStrategyType_DisplayTimeUnit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisplayTimeUnit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getMaxElapseTimeStrategyType_MaxElapseTime(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MaxElapseTime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(maxLoopCountStrategyTypeEClass, source, new String[] {
                "name", "MaxLoopCountStrategyType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getMaxLoopCountStrategyType_MaxLoopCount(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MaxLoopCount", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(normalRealDistributionEClass, source, new String[] {
                "name", "NormalRealDistribution_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getNormalRealDistribution_Mean(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Mean" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getNormalRealDistribution_StandardDeviation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StandardDeviation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(operatorTypeEDataType, source, new String[] {
                "name", "Operator_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "baseType", "http://www.eclipse.org/emf/2003/XMLType#string", //$NON-NLS-1$ //$NON-NLS-2$
                "enumeration", "=" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(parameterBasedDistributionEClass, source, new String[] {
                "name", "ParameterBasedDistribution_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getParameterBasedDistribution_ParameterDependentDistributions(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterDependentDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParameterBasedDistribution_ParameterDependentDistributions(),
                1,
                "http:///org/eclipse/emf/ecore/util/ExtendedMetaData", //$NON-NLS-1$
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterDependentDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParameterBasedDistribution_ParameterDependentDistributions(),
                1,
                "http:///org/eclipse/emf/ecore/util/ExtendedMetaData", //$NON-NLS-1$
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterDependentDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(parameterDependentDistributionEClass,
                source,
                new String[] { "name", "ParameterDependentDistribution_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParameterDependentDistribution_BasicDistribution(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "BasicDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "subclass-wrap", "BasicDistribution" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParameterDependentDistribution_Expression(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Expression", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(parameterDistributionEClass, source, new String[] {
                "name", "ParameterDistributionType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getParameterDistribution_EnumerationValue(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "EnumerationValue", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParameterDistribution_ParameterId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(participantSimulationDataTypeEClass,
                source,
                new String[] { "name", "ParticipantSimulationDataType", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSimulationDataType_Instances(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Instances", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSimulationDataType_TimeUnitCost(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TimeUnitCost", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSimulationDataType_SlaMinimumUtilisation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SlaMinimumUtilisation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSimulationDataType_SlaMaximumUtilisation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SlaMaximumUtilisation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(realDistributionCategoryEEnum, source, new String[] {
                "name", "RealDistributionCategory" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(realDistributionCategoryObjectEDataType,
                source,
                new String[] { "name", "RealDistributionCategory:Object", //$NON-NLS-1$ //$NON-NLS-2$
                        "baseType", "RealDistributionCategory" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simulationRealDistributionTypeEClass,
                source,
                new String[] { "name", "SimulationRealDistributionType", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimulationRealDistributionType_BasicDistribution(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "BasicDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "subclass-wrap", "BasicDistribution" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimulationRealDistributionType_ParameterBasedDistribution(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterBasedDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(splitParameterTypeEClass, source, new String[] {
                "name", "SplitParameter_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSplitParameterType_ParameterId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(splitSimulationDataTypeEClass, source, new String[] {
                "name", "SplitSimulationDataType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSplitSimulationDataType_ParameterDeterminedSplit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterDeterminedSplit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSplitSimulationDataType_SplitParameter(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SplitParameter", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(startSimulationDataTypeEClass, source, new String[] {
                "name", "StartSimulationDataType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getStartSimulationDataType_Duration(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Duration", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "subclass-wrap", "Duration" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getStartSimulationDataType_DisplayTimeUnit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisplayTimeUnit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getStartSimulationDataType_NumberOfCases(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "NumberOfCases" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(structuredConditionTypeEClass, source, new String[] {
                "name", "StructuredConditionType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getStructuredConditionType_ParameterId(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getStructuredConditionType_Operator(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Operator", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getStructuredConditionType_ParameterValue(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterValue", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(timeDisplayUnitTypeEEnum, source, new String[] {
                "name", "TimeDisplayUnitType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(timeDisplayUnitTypeObjectEDataType, source, new String[] {
                "name", "TimeDisplayUnitType:Object", //$NON-NLS-1$ //$NON-NLS-2$
                "baseType", "TimeDisplayUnitType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(timeUnitCostTypeEClass, source, new String[] {
                "name", "TimeUnitCostType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getTimeUnitCostType_Cost(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Cost", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getTimeUnitCostType_TimeDisplayUnit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TimeDisplayUnit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(transitionSimulationDataTypeEClass, source, new String[] {
                "name", "TransitionSimulationDataType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getTransitionSimulationDataType_ParameterDeterminedCondition(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterDeterminedCondition", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getTransitionSimulationDataType_StructuredCondition(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StructuredCondition", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(normalDistributionStrategyTypeEClass,
                source,
                new String[] { "name", "NormalDistributionStrategyType", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getNormalDistributionStrategyType_Mean(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Mean", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getNormalDistributionStrategyType_StandardDeviation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StandardDeviation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(uniformRealDistributionEClass, source, new String[] {
                "name", "UniformRealDistribution_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getUniformRealDistribution_LowerBorder(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "LowerBorder" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUniformRealDistribution_UpperBorder(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "UpperBorder" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(workflowProcessSimulationDataTypeEClass,
                source,
                new String[] { "name", "WorkflowProcessSimulationDataType", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWorkflowProcessSimulationDataType_ParameterDistribution(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ParameterDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
    }

} //SimulationPackageImpl
