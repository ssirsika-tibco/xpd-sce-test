/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class can be used to specify the rules that need to be checked for a
 * case service.
 * 
 * @author Ali
 * @since 12 Aug 2014
 */
public class CaseServiceValidationRules extends ProcessValidationRule {

    private static final String INPUT_CASE_REF_CANNOT_BE_AN_ARRAY =
            "wm.caseservice.InputCaseRefCannotBeAnArray"; //$NON-NLS-1$

    private String NO_CASE_CLASS_TYPE_SELECTED =
            "wm.caseservice.NoCaseClassSelectedForCaseService"; //$NON-NLS-1$

    private String SINGLE_PARAM_IN_CASE_SERVICE_REQUIRED_ISSUE_ID =
            "wm.caseservice.SingleParamInCaseServiceRequired"; //$NON-NLS-1$

    private String MULTIPLE_PARAMS_IN_CASE_SERVICE_NOT_ALLOWED_ISSUE_ID =
            "wm.caseservice.MultipleParamsNotAllowedInCaseService"; //$NON-NLS-1$

    private String PARAM_IN_CASE_SERVICE_MUST_BE_CASE_CLASS_CASE_REF_TYPE_ISSUE_ID =
            "wm.caseservice.paramInCaseServiceMustBeCaseClassCaseRefType"; //$NON-NLS-1$

    private String PARAM_IN_CASE_SERVICE_MUST_BE_INPUT_ISSUE_ID =
            "wm.caseservice.paramInCaseServiceMustBeInput"; //$NON-NLS-1$

    private String CASE_CLASS_CASE_STATE_ATTRIBUTE_NOT_FOUND =
            "wm.caseservice.CaseClassCaseStateAttributeNotFound"; //$NON-NLS-1$

    private String INVALID_CASE_STATE_ATTRIBUTE_FOUND_IN_CASE_SERVICE =
            "wm.caseservice.InvalidCaseStateAttributeFoundInCaseService"; //$NON-NLS-1$

    private String NO_SPECIFIC_CASE_STATE_SELECTED_ISSUE_ID =
            "wm.caseservice.NoSpecificCaseStateIsSelected"; //$NON-NLS-1$

    public static final String PARAM_TYPE_ADDITIONAL_INFO_KEY = "paramType"; //$NON-NLS-1$

    public static final String PARAM_LABEL_ADDITIONAL_INFO_KEY = "paramLabel"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        if (Xpdl2ModelUtil.isCaseService(process)) {

            CaseService caseService = getCaseService(process);
            if (caseService == null || caseService.getCaseClassType() == null) {
                addIssue(NO_CASE_CLASS_TYPE_SELECTED, process);
                return;
            }

            ExternalReference caseClassExtRef = caseService.getCaseClassType();

            String caseClassQualifiesName = ""; //$NON-NLS-1$
            EObject eo =
                    ProcessUIUtil.getReferencedClassifier(caseClassExtRef,
                            WorkingCopyUtil.getProjectFor(process));
            Class caseClass = null;
            if (eo instanceof Class) {
                caseClass = (Class) eo;
                caseClassQualifiesName = caseClass.getQualifiedName();
            }

            /*
             * If case service has no param or has a param that is not of mode
             * IN or has multiple params then raise the issue
             */
            List<FormalParameter> caseServiceParams =
                    ProcessInterfaceUtil.getAllFormalParameters(process);

            Map<String, String> addInfo = new HashMap<String, String>();
            if (caseServiceParams.isEmpty()) {

                addIssue(SINGLE_PARAM_IN_CASE_SERVICE_REQUIRED_ISSUE_ID,
                        process,
                        Collections.singletonList(caseClassQualifiesName));

            } else {
                FormalParameter theInputParam = null;
                if (caseServiceParams.size() > 1) {
                    /*
                     * XPD-8082: Saket: Only restrict INPUT parameters.
                     */
                    int numberOfInParams = 0;

                    for (FormalParameter eachCaseServiceParam : caseServiceParams) {

                        if (ModeType.IN_LITERAL.equals(eachCaseServiceParam
                                .getMode())) {
                            if (++numberOfInParams > 1) {
                                break;
                            }
                            theInputParam = eachCaseServiceParam;
                        }

                        if (ModeType.INOUT_LITERAL.equals(eachCaseServiceParam
                                .getMode())) {
                            if (++numberOfInParams > 1) {
                                break;
                            }
                        }
                    }

                    if (numberOfInParams > 1 || theInputParam == null) {
                        addIssue(MULTIPLE_PARAMS_IN_CASE_SERVICE_NOT_ALLOWED_ISSUE_ID,
                                process,
                                Collections
                                        .singletonList(caseClassQualifiesName));
                    }
                } else {
                    theInputParam = caseServiceParams.get(0);
                }

                if (theInputParam != null) {
                    addInfo.put(PARAM_LABEL_ADDITIONAL_INFO_KEY,
                            theInputParam.getName());
                    addInfo.put(PARAM_TYPE_ADDITIONAL_INFO_KEY,
                            caseClassQualifiesName);

                    /*
                     * check if the type of param is a reference to the same
                     * case class as that of the case service case class
                     */
                    boolean invalidFormalParamType = true;
                    DataType type = theInputParam.getDataType();
                    if (type instanceof RecordType) {
                        RecordType recordType = (RecordType) type;
                        ExternalReference paramTypeExtRef =
                                recordType.getMember().get(0)
                                        .getExternalReference();
                        if (EcoreUtil.equals(caseClassExtRef, paramTypeExtRef)) {
                            invalidFormalParamType = false;
                        }
                    }
                    if (invalidFormalParamType) {
                        addIssue(PARAM_IN_CASE_SERVICE_MUST_BE_CASE_CLASS_CASE_REF_TYPE_ISSUE_ID,
                                theInputParam,
                                Collections
                                        .singletonList(caseClassQualifiesName),
                                addInfo);

                    } else if (ModeType.IN != theInputParam.getMode()
                            .getValue()) {

                        addIssue(PARAM_IN_CASE_SERVICE_MUST_BE_INPUT_ISSUE_ID,
                                theInputParam,
                                Collections
                                        .singletonList(caseClassQualifiesName),
                                addInfo);

                    }

                    /* XPD-6827: Case Actions *CaseRef array* support */
                    if (theInputParam.isIsArray()) {
                        addIssue(INPUT_CASE_REF_CANNOT_BE_AN_ARRAY,
                                theInputParam,
                                Collections
                                        .singletonList(caseClassQualifiesName),
                                addInfo);
                    }
                }
            }

            /* Raise issue if case class doesn't have a case state attribute */

            Property caseClassCaseStateAttrib =
                    ProcessUIUtil.getCaseClassCaseState(caseClass);

            if (caseClassCaseStateAttrib == null
                    || !(caseClassCaseStateAttrib.getType() instanceof Enumeration)) {
                addIssue(CASE_CLASS_CASE_STATE_ATTRIBUTE_NOT_FOUND,
                        process,
                        Collections.singletonList(caseClassQualifiesName));

            } else {
                /* Raise issue if case state attribute selected doesn't exist */
                VisibleForCaseStates caseServiceCaseStates =
                        caseService.getVisibleForCaseStates();

                Enumeration caseStateEnum =
                        (Enumeration) caseClassCaseStateAttrib.getType();
                EList<EnumerationLiteral> caseClassCaseStatesList =
                        caseStateEnum.getOwnedLiterals();

                if (caseServiceCaseStates != null) {

                    /*
                     * If 'Specific States' is selected but none of the case
                     * states is selected, then raise issue
                     */
                    if (!caseServiceCaseStates.isVisibleForUnsetCaseState()
                            && caseServiceCaseStates.getCaseState().isEmpty()) {

                        addIssue(NO_SPECIFIC_CASE_STATE_SELECTED_ISSUE_ID,
                                process);
                    } else {

                        for (ExternalReference state : caseServiceCaseStates
                                .getCaseState()) {

                            /*
                             * Check if the state is one of the enums in the
                             * case class
                             */
                            boolean found = false;
                            for (EnumerationLiteral caseClassEnumLit : caseClassCaseStatesList) {
                                ExternalReference ref =
                                        ProcessUIUtil
                                                .getExternalRefForEnumLit(caseService,
                                                        caseClassEnumLit);
                                if (EcoreUtil.equals(ref, state)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                addIssue(INVALID_CASE_STATE_ATTRIBUTE_FOUND_IN_CASE_SERVICE,
                                        process);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @return case service set in the given process or null
     */
    public static CaseService getCaseService(Process process) {

        if (process != null) {
            Object caseService =
                    Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CaseService());
            if (caseService instanceof CaseService) {
                return (CaseService) caseService;
            }
        }
        return null;
    }
}
