/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.util;

import com.tibco.xpd.presentation.channeltypes.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage
 * @generated
 */
public class ChannelTypesAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ChannelTypesPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelTypesAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ChannelTypesPackage.eINSTANCE;
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
    protected ChannelTypesSwitch<Adapter> modelSwitch =
        new ChannelTypesSwitch<Adapter>() {
            @Override
            public Adapter caseTarget(Target object) {
                return createTargetAdapter();
            }
            @Override
            public Adapter casePresentation(Presentation object) {
                return createPresentationAdapter();
            }
            @Override
            public Adapter caseImplementation(Implementation object) {
                return createImplementationAdapter();
            }
            @Override
            public Adapter caseAttribute(Attribute object) {
                return createAttributeAdapter();
            }
            @Override
            public Adapter caseModelElement(ModelElement object) {
                return createModelElementAdapter();
            }
            @Override
            public Adapter caseNamedElement(NamedElement object) {
                return createNamedElementAdapter();
            }
            @Override
            public Adapter caseEnumLiteral(EnumLiteral object) {
                return createEnumLiteralAdapter();
            }
            @Override
            public Adapter caseChannelType(ChannelType object) {
                return createChannelTypeAdapter();
            }
            @Override
            public Adapter caseChannelTypes(ChannelTypes object) {
                return createChannelTypesAdapter();
            }
            @Override
            public Adapter caseChannelDestination(ChannelDestination object) {
                return createChannelDestinationAdapter();
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
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.Target <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.Target
     * @generated
     */
    public Adapter createTargetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.Presentation <em>Presentation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.Presentation
     * @generated
     */
    public Adapter createPresentationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.Implementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.Implementation
     * @generated
     */
    public Adapter createImplementationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.ChannelType <em>Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType
     * @generated
     */
    public Adapter createChannelTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.Attribute <em>Attribute</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.Attribute
     * @generated
     */
    public Adapter createAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes <em>Channel Types</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypes
     * @generated
     */
    public Adapter createChannelTypesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.ChannelDestination <em>Channel Destination</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelDestination
     * @generated
     */
    public Adapter createChannelDestinationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.ModelElement <em>Model Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.ModelElement
     * @generated
     */
    public Adapter createModelElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.presentation.channeltypes.EnumLiteral <em>Enum Literal</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.presentation.channeltypes.EnumLiteral
     * @generated
     */
    public Adapter createEnumLiteralAdapter() {
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

} //ChannelTypesAdapterFactory
