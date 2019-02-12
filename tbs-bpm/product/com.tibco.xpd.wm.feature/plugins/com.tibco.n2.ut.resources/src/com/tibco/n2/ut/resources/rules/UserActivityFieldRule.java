/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.resources.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation class to ensure BDS data is used correctly
 * 
 */
public class UserActivityFieldRule extends ProcessValidationRule {

    private static final String PRIMITIVE_TYPE = "n2.ut.userTaskUsingPrimitive"; //$NON-NLS-1$

    private static final String OBJECT_TYPE = "n2.ut.userTaskUsingObject"; //$NON-NLS-1$

    /*
     * Sid ACE-194 - we don't support XSD based BOMs in ACE - removed issue
     * "n2.ut.userTaskMultiplicitySequence"
     */

    private static final String CASE_REF_MODE_TYPE =
            "n2.ut.userTaskCaseClassRefModeType"; //$NON-NLS-1$

    private static final String CASE_REF_MANDATORY =
            "n2.ut.userTaskCaseClassRefMandatory"; //$NON-NLS-1$

    private static final String CASE_REF_ARRAY =
            "n2.ut.userTaskCaseClassRefArrayForm"; //$NON-NLS-1$

    private static final String CASE_REF_PAGE_FLOW =
            "n2.ut.userTaskCaseClassRefPageFlow"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        ComplexDataTypesMergedInfo compMergeInfo = ComplexDataTypeExtPointHelper
                .getAllComplexDataTypesMergedInfo();

        IProject project =
                WorkspaceSynchronizer.getFile(process.eResource()).getProject();

        // Check the list of parameters and data fields in the process
        ArrayList<String> primitiveDataTypes = new ArrayList<String>();
        ArrayList<String> containsObjectType = new ArrayList<String>();

        /*
         * Sid ACE-194 - we don't support XSD based BOMs in ACE
         */

        Map<String, ProcessRelevantData> allCaseReferences =
                new HashMap<String, ProcessRelevantData>();

        for (Activity activity : activities) {
            // Need to get all types regardless of their location, this
            // will cover things in the main process when we are in a sub-proc
            List<ProcessRelevantData> allProcessRelevantData =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(activity);

            for (ProcessRelevantData processRelevantData : allProcessRelevantData) {
                DataType dataType = processRelevantData.getDataType();
                if (dataType instanceof ExternalReference) {
                    // Check to see if the external reference is a BOM element
                    ComplexDataTypeReference complexDataTypeRef =
                            xpdl2RefToComplexDataTypeRef(
                                    (ExternalReference) dataType);

                    if (complexDataTypeRef != null) {
                        Object objectForDataType =
                                compMergeInfo.getComplexDataTypeFromReference(
                                        complexDataTypeRef,
                                        project);

                        // Check here to collect a list of primitive and
                        // enumeration
                        // types
                        if ((objectForDataType instanceof PrimitiveType)
                                || (objectForDataType instanceof Enumeration)) {
                            primitiveDataTypes
                                    .add(processRelevantData.getName());
                        }
                        // Check here to collect all the object type data fields
                        if (objectForDataType instanceof Class) {
                            // Before we start, create a List so that we know
                            // what
                            // has been checked and we don't go into an infinite
                            // loop
                            List<String> classesChecked =
                                    new ArrayList<String>();

                            if (doesClassContainObject(
                                    (Class) objectForDataType,
                                    classesChecked) != false) {
                                containsObjectType
                                        .add(processRelevantData.getName());
                            }

                            // Clear the list ready for the next check
                            classesChecked.clear();

                            /*
                             * Sid ACE-194 - we don't support XSD based BOMs in
                             * ACE
                             */
                        }
                    }
                } else if (dataType instanceof RecordType) {
                    allCaseReferences.put(processRelevantData.getName(),
                            processRelevantData);
                }
            }
        }

        // If there is an imported BOM present, then check to see if it is being
        // used in a user task
        for (Activity activity : activities) {
            // Check that this is a user task using the BOM Field
            if (TaskType.USER_LITERAL
                    .equals(TaskObjectUtil.getTaskTypeStrict(activity))) {
                Object apsObj = Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());

                // Find out if this is a page flow user task
                FormImplementation userTaskForm =
                        TaskObjectUtil.getUserTaskFormImplementation(activity);
                boolean isPageFlowForm = false;
                if ((userTaskForm != null) && (FormImplementationType.PAGEFLOW
                        .equals(userTaskForm.getFormType()))) {
                    isPageFlowForm = true;
                }

                if ((apsObj instanceof AssociatedParameters)
                        && ((AssociatedParameters) apsObj)
                                .getAssociatedParameter().size() > 0) {
                    for (Object apObj : ((AssociatedParameters) apsObj)
                            .getAssociatedParameter()) {
                        if (apObj instanceof AssociatedParameter) {
                            AssociatedParameter assocParam =
                                    (AssociatedParameter) apObj;
                            // Now check to see if it is a primitive type as
                            // this is not supported for a User Activity
                            if (primitiveDataTypes.indexOf(
                                    assocParam.getFormalParam()) != -1) {
                                addIssue(PRIMITIVE_TYPE, activity);
                                break;
                            } else if (containsObjectType.indexOf(
                                    assocParam.getFormalParam()) != -1) {
                                // Is of type class that contains an object - we
                                // do not allow that
                                addIssue(OBJECT_TYPE, activity);
                                break;
                            }
                            /*
                             * Sid ACE-194 - we don't support XSD based BOMs in
                             * ACE
                             */

                            else if (allCaseReferences
                                    .containsKey(assocParam.getFormalParam())) {
                                // Page flow processes do not support references
                                if (Xpdl2ModelUtil
                                        .isPageflow(activity.getProcess())) {
                                    addIssue(CASE_REF_PAGE_FLOW, activity);
                                    break;
                                }
                                // Case Class References are not allowed to be
                                // out only for user tasks (Unless a page-flow)
                                if (!isPageFlowForm && (assocParam.getMode()
                                        .getValue() == ModeType.OUT)) {
                                    addIssue(CASE_REF_MODE_TYPE, activity);
                                    break;
                                }
                                // Case References should always be added as
                                // mandatory
                                if (!isPageFlowForm
                                        && (assocParam.getMode()
                                                .getValue() == ModeType.INOUT)
                                        && !assocParam.isMandatory()) {
                                    addIssue(CASE_REF_MANDATORY, activity);
                                    break;
                                }
                                ProcessRelevantData procData = allCaseReferences
                                        .get(assocParam.getFormalParam());
                                // Don't allow arrays of Case References for
                                // user tasks that do not use a pageflow
                                if (!isPageFlowForm && procData.isIsArray()
                                        && (assocParam.getMode()
                                                .getValue() == ModeType.INOUT)) {
                                    addIssue(CASE_REF_ARRAY, activity);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    /*
                     * Sid XPD-2087: Only use all data implicitly if implicit
                     * association has not been disabled.
                     */
                    if (!ProcessInterfaceUtil
                            .isImplicitAssociationDisabled(activity)) {

                        // Check all the data for this Activity
                        List<ProcessRelevantData> allActivityRelevantData =
                                ProcessInterfaceUtil
                                        .getAllAvailableRelevantDataForActivity(
                                                activity);

                        for (ProcessRelevantData relevantData : allActivityRelevantData) {
                            if (primitiveDataTypes
                                    .indexOf(relevantData.getName()) != -1) {
                                // As no data is specified it immediately
                                // includes all values and as there is a
                                // primitive type, flag with error
                                addIssue(PRIMITIVE_TYPE, activity);
                            } else if (containsObjectType
                                    .indexOf(relevantData.getName()) != -1) {
                                // As no data is specified it immediately
                                // includes all values and as there is a class
                                // with an object type, flag with error
                                addIssue(OBJECT_TYPE, activity);
                            }
                            /*
                             * Sid ACE-194 - we don't support XSD based BOMs in
                             * ACE
                             */
                            else if (allCaseReferences
                                    .containsKey(relevantData.getName())) {
                                // Page flow processes do not support references
                                if (Xpdl2ModelUtil
                                        .isPageflow(activity.getProcess())) {
                                    addIssue(CASE_REF_PAGE_FLOW, activity);
                                    break;
                                }
                                // Don't allow arrays of Case References for
                                // user tasks that do not use a pageflow
                                ProcessRelevantData procData = allCaseReferences
                                        .get(relevantData.getName());
                                if (!isPageFlowForm && procData.isIsArray()) {
                                    addIssue(CASE_REF_ARRAY, activity);
                                    break;
                                }

                                // Get The Mode
                                if (relevantData instanceof FormalParameter) {
                                    FormalParameter param =
                                            (FormalParameter) relevantData;
                                    // Case Class References are not allowed to
                                    // be out only for user tasks (Unless a
                                    // page-flow)
                                    if (!isPageFlowForm && (param.getMode()
                                            .getValue() == ModeType.OUT)) {
                                        addIssue(CASE_REF_MODE_TYPE, activity);
                                        break;
                                    }
                                    // Case References should always be added as
                                    // mandatory
                                    if (!isPageFlowForm && (param.getMode()
                                            .getValue() == ModeType.INOUT)
                                            && !param.isRequired()) {
                                        addIssue(CASE_REF_MANDATORY, activity);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if a BOM was created from an import
     * 
     * @param extRef
     * @return
     */
    private ComplexDataTypeReference xpdl2RefToComplexDataTypeRef(
            ExternalReference extRef) {
        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        // Must have at least some info defined.
        int length = (nameSpace == null ? 0 : nameSpace.length());
        length += (location == null ? 0 : location.length());
        length += (xRef == null ? 0 : xRef.length());
        if (length == 0) {
            return null;
        }
        return new ComplexDataTypeReference(location, xRef, nameSpace);
    }

    /**
     * Recursively checks to see if there is an object used in the class
     * 
     * @param bomClass
     *            BOM Class to check for use of object
     * @return
     */
    private boolean doesClassContainObject(Class bomClass,
            List<String> classesChecked) {

        List<Property> allAttributes = new ArrayList<Property>();
        collectAllClassAttributes(bomClass, allAttributes);

        // Go through each of the attributes checking if any of them are
        // objects
        for (Property property : allAttributes) {
            if (property.isSetName()) {
                Type type = property.getType();
                if (null != type && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                        .equals(type.getName())) {
                    return true;
                } else if (type instanceof Class) {
                    // It's another BOM Class, that is referenced, check
                    // inside that to see if it uses an object, if it
                    // has not already been checked
                    if (!classesChecked.contains(type.getName())) {
                        // First add it to the checked list so we don't do
                        // it again
                        classesChecked.add(type.getName());

                        if (doesClassContainObject((Class) type,
                                classesChecked) != false) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param cl
     * @param allAttributes
     */
    private static void collectAllClassAttributes(Class cl,
            List<Property> allAttributes) {
        /**
         * XPD-8147 - see invoked method for details...
         */
        ConceptUtil.collectAllClassAttributes(cl, allAttributes, false);

    }

}
