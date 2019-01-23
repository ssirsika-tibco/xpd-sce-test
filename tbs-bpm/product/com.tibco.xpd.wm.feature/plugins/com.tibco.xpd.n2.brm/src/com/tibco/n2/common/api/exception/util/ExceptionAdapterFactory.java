/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.util;

import com.tibco.n2.common.api.exception.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.api.exception.ExceptionPackage
 * @generated
 */
public class ExceptionAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ExceptionPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExceptionAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ExceptionPackage.eINSTANCE;
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
    protected ExceptionSwitch<Adapter> modelSwitch =
        new ExceptionSwitch<Adapter>() {
            @Override
            public Adapter caseDeploymentParameterFaultType(DeploymentParameterFaultType object) {
                return createDeploymentParameterFaultTypeAdapter();
            }
            @Override
            public Adapter caseDetailedErrorLine(DetailedErrorLine object) {
                return createDetailedErrorLineAdapter();
            }
            @Override
            public Adapter caseDetailedException(DetailedException object) {
                return createDetailedExceptionAdapter();
            }
            @Override
            public Adapter caseErrorLine(ErrorLine object) {
                return createErrorLineAdapter();
            }
            @Override
            public Adapter caseDocumentRoot(DocumentRoot object) {
                return createDocumentRootAdapter();
            }
            @Override
            public Adapter caseInternalServiceFaultType(InternalServiceFaultType object) {
                return createInternalServiceFaultTypeAdapter();
            }
            @Override
            public Adapter caseInvalidWorkTypeFaultType(InvalidWorkTypeFaultType object) {
                return createInvalidWorkTypeFaultTypeAdapter();
            }
            @Override
            public Adapter caseWorkTypeFaultType(WorkTypeFaultType object) {
                return createWorkTypeFaultTypeAdapter();
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
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.DeploymentParameterFaultType <em>Deployment Parameter Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.DeploymentParameterFaultType
     * @generated
     */
    public Adapter createDeploymentParameterFaultTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.DetailedErrorLine <em>Detailed Error Line</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.DetailedErrorLine
     * @generated
     */
    public Adapter createDetailedErrorLineAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.DetailedException <em>Detailed Exception</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.DetailedException
     * @generated
     */
    public Adapter createDetailedExceptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.ErrorLine <em>Error Line</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.ErrorLine
     * @generated
     */
    public Adapter createErrorLineAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.InternalServiceFaultType <em>Internal Service Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.InternalServiceFaultType
     * @generated
     */
    public Adapter createInternalServiceFaultTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType <em>Invalid Work Type Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType
     * @generated
     */
    public Adapter createInvalidWorkTypeFaultTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.api.exception.WorkTypeFaultType <em>Work Type Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.api.exception.WorkTypeFaultType
     * @generated
     */
    public Adapter createWorkTypeFaultTypeAdapter() {
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

} //ExceptionAdapterFactory
