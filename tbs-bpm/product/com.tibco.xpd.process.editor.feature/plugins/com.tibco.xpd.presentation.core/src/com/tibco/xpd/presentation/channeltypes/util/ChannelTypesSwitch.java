/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.util;

import com.tibco.xpd.presentation.channeltypes.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

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
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage
 * @generated
 */
public class ChannelTypesSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ChannelTypesPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelTypesSwitch() {
        if (modelPackage == null) {
            modelPackage = ChannelTypesPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case ChannelTypesPackage.TARGET: {
                Target target = (Target)theEObject;
                T result = caseTarget(target);
                if (result == null) result = caseNamedElement(target);
                if (result == null) result = caseModelElement(target);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.PRESENTATION: {
                Presentation presentation = (Presentation)theEObject;
                T result = casePresentation(presentation);
                if (result == null) result = caseNamedElement(presentation);
                if (result == null) result = caseModelElement(presentation);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.IMPLEMENTATION: {
                Implementation implementation = (Implementation)theEObject;
                T result = caseImplementation(implementation);
                if (result == null) result = caseNamedElement(implementation);
                if (result == null) result = caseModelElement(implementation);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.ATTRIBUTE: {
                Attribute attribute = (Attribute)theEObject;
                T result = caseAttribute(attribute);
                if (result == null) result = caseNamedElement(attribute);
                if (result == null) result = caseModelElement(attribute);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.MODEL_ELEMENT: {
                ModelElement modelElement = (ModelElement)theEObject;
                T result = caseModelElement(modelElement);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.NAMED_ELEMENT: {
                NamedElement namedElement = (NamedElement)theEObject;
                T result = caseNamedElement(namedElement);
                if (result == null) result = caseModelElement(namedElement);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.ENUM_LITERAL: {
                EnumLiteral enumLiteral = (EnumLiteral)theEObject;
                T result = caseEnumLiteral(enumLiteral);
                if (result == null) result = caseNamedElement(enumLiteral);
                if (result == null) result = caseModelElement(enumLiteral);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.CHANNEL_TYPE: {
                ChannelType channelType = (ChannelType)theEObject;
                T result = caseChannelType(channelType);
                if (result == null) result = caseNamedElement(channelType);
                if (result == null) result = caseModelElement(channelType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.CHANNEL_TYPES: {
                ChannelTypes channelTypes = (ChannelTypes)theEObject;
                T result = caseChannelTypes(channelTypes);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChannelTypesPackage.CHANNEL_DESTINATION: {
                ChannelDestination channelDestination = (ChannelDestination)theEObject;
                T result = caseChannelDestination(channelDestination);
                if (result == null) result = caseNamedElement(channelDestination);
                if (result == null) result = caseModelElement(channelDestination);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Target</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Target</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTarget(Target object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Presentation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Presentation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePresentation(Presentation object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Channel Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Channel Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChannelType(ChannelType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttribute(Attribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Channel Types</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Channel Types</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChannelTypes(ChannelTypes object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Channel Destination</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Channel Destination</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChannelDestination(ChannelDestination object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelElement(ModelElement object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Enum Literal</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enum Literal</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnumLiteral(EnumLiteral object) {
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
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //ChannelTypesSwitch
