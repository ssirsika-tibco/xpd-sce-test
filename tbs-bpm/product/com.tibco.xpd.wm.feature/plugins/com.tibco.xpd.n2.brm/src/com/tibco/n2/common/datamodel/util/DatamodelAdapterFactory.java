/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.util;

import com.tibco.n2.common.datamodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.datamodel.DatamodelPackage
 * @generated
 */
public class DatamodelAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static DatamodelPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatamodelAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = DatamodelPackage.eINSTANCE;
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
    protected DatamodelSwitch<Adapter> modelSwitch =
        new DatamodelSwitch<Adapter>() {
            @Override
            public Adapter caseAliasType(AliasType object) {
                return createAliasTypeAdapter();
            }
            @Override
            public Adapter caseComplexSpecType(ComplexSpecType object) {
                return createComplexSpecTypeAdapter();
            }
            @Override
            public Adapter caseDataModel(DataModel object) {
                return createDataModelAdapter();
            }
            @Override
            public Adapter caseFieldType(FieldType object) {
                return createFieldTypeAdapter();
            }
            @Override
            public Adapter caseSimpleSpecType(SimpleSpecType object) {
                return createSimpleSpecTypeAdapter();
            }
            @Override
            public Adapter caseWorkType(WorkType object) {
                return createWorkTypeAdapter();
            }
            @Override
            public Adapter caseWorkTypeSpec(WorkTypeSpec object) {
                return createWorkTypeSpecAdapter();
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
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.AliasType <em>Alias Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.AliasType
     * @generated
     */
    public Adapter createAliasTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.ComplexSpecType <em>Complex Spec Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.ComplexSpecType
     * @generated
     */
    public Adapter createComplexSpecTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.DataModel <em>Data Model</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.DataModel
     * @generated
     */
    public Adapter createDataModelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.FieldType <em>Field Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.FieldType
     * @generated
     */
    public Adapter createFieldTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.SimpleSpecType <em>Simple Spec Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.SimpleSpecType
     * @generated
     */
    public Adapter createSimpleSpecTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.WorkType <em>Work Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.WorkType
     * @generated
     */
    public Adapter createWorkTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.WorkTypeSpec <em>Work Type Spec</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.WorkTypeSpec
     * @generated
     */
    public Adapter createWorkTypeSpecAdapter() {
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

} //DatamodelAdapterFactory
