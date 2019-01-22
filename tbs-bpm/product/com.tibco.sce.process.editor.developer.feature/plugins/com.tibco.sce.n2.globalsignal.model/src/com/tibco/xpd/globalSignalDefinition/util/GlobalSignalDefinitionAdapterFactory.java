/**
 */
package com.tibco.xpd.globalSignalDefinition.util;

import com.tibco.xpd.globalSignalDefinition.*;

import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage
 * @generated
 */
public class GlobalSignalDefinitionAdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static GlobalSignalDefinitionPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitionAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = GlobalSignalDefinitionPackage.eINSTANCE;
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
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GlobalSignalDefinitionSwitch<Adapter> modelSwitch =
        new GlobalSignalDefinitionSwitch<Adapter>() {
            @Override
            public Adapter caseGlobalSignal(GlobalSignal object) {
                return createGlobalSignalAdapter();
            }
            @Override
            public Adapter caseGlobalSignalDefinitions(GlobalSignalDefinitions object) {
                return createGlobalSignalDefinitionsAdapter();
            }
            @Override
            public Adapter casePayloadDataField(PayloadDataField object) {
                return createPayloadDataFieldAdapter();
            }
            @Override
            public Adapter caseUniqueIdElement(UniqueIdElement object) {
                return createUniqueIdElementAdapter();
            }
            @Override
            public Adapter caseOtherAttributesContainer(OtherAttributesContainer object) {
                return createOtherAttributesContainerAdapter();
            }
            @Override
            public Adapter caseNamedElement(NamedElement object) {
                return createNamedElementAdapter();
            }
            @Override
            public Adapter caseDescribedElement(DescribedElement object) {
                return createDescribedElementAdapter();
            }
            @Override
            public Adapter caseOtherElementsContainer(OtherElementsContainer object) {
                return createOtherElementsContainerAdapter();
            }
            @Override
            public Adapter caseProcessRelevantData(ProcessRelevantData object) {
                return createProcessRelevantDataAdapter();
            }
            @Override
            public Adapter caseExtendedAttributesContainer(ExtendedAttributesContainer object) {
                return createExtendedAttributesContainerAdapter();
            }
            @Override
            public Adapter caseDataField(DataField object) {
                return createDataFieldAdapter();
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
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal <em>Global Signal</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignal
     * @generated
     */
    public Adapter createGlobalSignalAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions <em>Global Signal Definitions</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions
     * @generated
     */
    public Adapter createGlobalSignalDefinitionsAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.globalSignalDefinition.PayloadDataField <em>Payload Data Field</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.globalSignalDefinition.PayloadDataField
     * @generated
     */
    public Adapter createPayloadDataFieldAdapter() {
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

} //GlobalSignalDefinitionAdapterFactory
