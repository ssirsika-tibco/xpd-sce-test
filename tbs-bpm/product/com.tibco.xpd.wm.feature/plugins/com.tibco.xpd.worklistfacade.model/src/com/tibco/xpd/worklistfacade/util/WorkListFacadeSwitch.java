/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.xpd.worklistfacade.model.DocumentRoot;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage
 * @generated
 */
public class WorkListFacadeSwitch<T> extends Switch<T> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static WorkListFacadePackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public WorkListFacadeSwitch() {
        if (modelPackage == null) {
            modelPackage = WorkListFacadePackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
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
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case WorkListFacadePackage.DOCUMENT_ROOT: {
            DocumentRoot documentRoot = (DocumentRoot) theEObject;
            T result = caseDocumentRoot(documentRoot);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTE: {
            WorkItemAttribute workItemAttribute =
                    (WorkItemAttribute) theEObject;
            T result = caseWorkItemAttribute(workItemAttribute);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTES: {
            WorkItemAttributes workItemAttributes =
                    (WorkItemAttributes) theEObject;
            T result = caseWorkItemAttributes(workItemAttributes);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case WorkListFacadePackage.WORK_LIST_FACADE: {
            WorkListFacade workListFacade = (WorkListFacade) theEObject;
            T result = caseWorkListFacade(workListFacade);
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
     * <em>Work Item Attribute</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Work Item Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemAttribute(WorkItemAttribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Work Item Attributes</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Work Item Attributes</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemAttributes(WorkItemAttributes object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Work List Facade</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Work List Facade</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkListFacade(WorkListFacade object) {
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
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // WorkListFacadeSwitch
