/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.util;

import com.tibco.n2.common.organisation.api.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage
 * @generated
 */
public class OrganisationAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static OrganisationPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = OrganisationPackage.eINSTANCE;
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
    protected OrganisationSwitch<Adapter> modelSwitch =
        new OrganisationSwitch<Adapter>() {
            @Override
            public Adapter caseQualifierSetType(QualifierSetType object) {
                return createQualifierSetTypeAdapter();
            }
            @Override
            public Adapter caseXmlCalendarAssignment(XmlCalendarAssignment object) {
                return createXmlCalendarAssignmentAdapter();
            }
            @Override
            public Adapter caseXmlCalendarRef(XmlCalendarRef object) {
                return createXmlCalendarRefAdapter();
            }
            @Override
            public Adapter caseXmlExecuteQuery(XmlExecuteQuery object) {
                return createXmlExecuteQueryAdapter();
            }
            @Override
            public Adapter caseXmlModelEntityId(XmlModelEntityId object) {
                return createXmlModelEntityIdAdapter();
            }
            @Override
            public Adapter caseXmlOrgModelVersion(XmlOrgModelVersion object) {
                return createXmlOrgModelVersionAdapter();
            }
            @Override
            public Adapter caseXmlParticipantId(XmlParticipantId object) {
                return createXmlParticipantIdAdapter();
            }
            @Override
            public Adapter caseXmlResourceQuery(XmlResourceQuery object) {
                return createXmlResourceQueryAdapter();
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
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.QualifierSetType <em>Qualifier Set Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.QualifierSetType
     * @generated
     */
    public Adapter createQualifierSetTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment <em>Xml Calendar Assignment</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarAssignment
     * @generated
     */
    public Adapter createXmlCalendarAssignmentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef <em>Xml Calendar Ref</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarRef
     * @generated
     */
    public Adapter createXmlCalendarRefAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery <em>Xml Execute Query</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.XmlExecuteQuery
     * @generated
     */
    public Adapter createXmlExecuteQueryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId <em>Xml Model Entity Id</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.XmlModelEntityId
     * @generated
     */
    public Adapter createXmlModelEntityIdAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.XmlOrgModelVersion <em>Xml Org Model Version</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.XmlOrgModelVersion
     * @generated
     */
    public Adapter createXmlOrgModelVersionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.XmlParticipantId <em>Xml Participant Id</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.XmlParticipantId
     * @generated
     */
    public Adapter createXmlParticipantIdAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.organisation.api.XmlResourceQuery <em>Xml Resource Query</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.organisation.api.XmlResourceQuery
     * @generated
     */
    public Adapter createXmlResourceQueryAdapter() {
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

} //OrganisationAdapterFactory
