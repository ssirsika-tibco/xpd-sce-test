/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.n2.wp.archive.service.BusinessServiceType;
import com.tibco.n2.wp.archive.service.ChannelExtentionType;
import com.tibco.n2.wp.archive.service.ChannelType;
import com.tibco.n2.wp.archive.service.ChannelsType;
import com.tibco.n2.wp.archive.service.DocumentRoot;
import com.tibco.n2.wp.archive.service.ExtendedPropertiesType;
import com.tibco.n2.wp.archive.service.FormType;
import com.tibco.n2.wp.archive.service.PageActivityType;
import com.tibco.n2.wp.archive.service.PageFlowRefType;
import com.tibco.n2.wp.archive.service.PageFlowType;
import com.tibco.n2.wp.archive.service.PropertyType;
import com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType;
import com.tibco.n2.wp.archive.service.WPPackage;
import com.tibco.n2.wp.archive.service.WorkTypeType;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see com.tibco.n2.wp.archive.service.WPPackage
 * @generated
 */
public class WPSwitch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static WPPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public WPSwitch() {
        if (modelPackage == null) {
            modelPackage = WPPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns
     * a non null result; it yields that result. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the first non-null result returned by a <code>caseXXX</code>
     *         call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns
     * a non null result; it yields that result. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the first non-null result returned by a <code>caseXXX</code>
     *         call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? defaultCase(theEObject)
                    : doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns
     * a non null result; it yields that result. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the first non-null result returned by a <code>caseXXX</code>
     *         call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case WPPackage.BUSINESS_SERVICE_TYPE: {
            BusinessServiceType businessServiceType =
                    (BusinessServiceType) theEObject;
            T result = caseBusinessServiceType(businessServiceType);
            if (result == null)
                result = casePageFlowType(businessServiceType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.CHANNEL_EXTENTION_TYPE: {
            ChannelExtentionType channelExtentionType =
                    (ChannelExtentionType) theEObject;
            T result = caseChannelExtentionType(channelExtentionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.CHANNELS_TYPE: {
            ChannelsType channelsType = (ChannelsType) theEObject;
            T result = caseChannelsType(channelsType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.CHANNEL_TYPE: {
            ChannelType channelType = (ChannelType) theEObject;
            T result = caseChannelType(channelType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.DOCUMENT_ROOT: {
            DocumentRoot documentRoot = (DocumentRoot) theEObject;
            T result = caseDocumentRoot(documentRoot);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.EXTENDED_PROPERTIES_TYPE: {
            ExtendedPropertiesType extendedPropertiesType =
                    (ExtendedPropertiesType) theEObject;
            T result = caseExtendedPropertiesType(extendedPropertiesType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.FORM_TYPE: {
            FormType formType = (FormType) theEObject;
            T result = caseFormType(formType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.PAGE_ACTIVITY_TYPE: {
            PageActivityType pageActivityType = (PageActivityType) theEObject;
            T result = casePageActivityType(pageActivityType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.PAGE_FLOW_REF_TYPE: {
            PageFlowRefType pageFlowRefType = (PageFlowRefType) theEObject;
            T result = casePageFlowRefType(pageFlowRefType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.PAGE_FLOW_TYPE: {
            PageFlowType pageFlowType = (PageFlowType) theEObject;
            T result = casePageFlowType(pageFlowType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.PROPERTY_TYPE: {
            PropertyType propertyType = (PropertyType) theEObject;
            T result = casePropertyType(propertyType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE: {
            ServiceArchiveDescriptorType serviceArchiveDescriptorType =
                    (ServiceArchiveDescriptorType) theEObject;
            T result =
                    caseServiceArchiveDescriptorType(serviceArchiveDescriptorType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WPPackage.WORK_TYPE_TYPE: {
            WorkTypeType workTypeType = (WorkTypeType) theEObject;
            T result = caseWorkTypeType(workTypeType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Business Service Type</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Business Service Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBusinessServiceType(BusinessServiceType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Channel Extention Type</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Channel Extention Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChannelExtentionType(ChannelExtentionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Channels Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Channels Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChannelsType(ChannelsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Channel Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Channel Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChannelType(ChannelType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Document Root</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Document Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentRoot(DocumentRoot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Extended Properties Type</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Extended Properties Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExtendedPropertiesType(ExtendedPropertiesType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Form Type</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Form Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormType(FormType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Page Activity Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Page Activity Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePageActivityType(PageActivityType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Page Flow Ref Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Page Flow Ref Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePageFlowRefType(PageFlowRefType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Page Flow Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Page Flow Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePageFlowType(PageFlowType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Property Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Property Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePropertyType(PropertyType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Service Archive Descriptor Type</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Service Archive Descriptor Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServiceArchiveDescriptorType(
            ServiceArchiveDescriptorType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Work Type Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Work Type Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkTypeType(WorkTypeType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>EObject</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch, but this is
     * the last case anyway. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} // WPSwitch
