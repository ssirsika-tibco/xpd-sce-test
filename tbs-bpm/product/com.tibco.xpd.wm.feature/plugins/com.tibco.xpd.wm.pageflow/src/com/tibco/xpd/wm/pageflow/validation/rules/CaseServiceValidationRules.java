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
import com.tibco.xpd.bom.modeler.custom.terminalstates.TerminalStateProperties;
import com.tibco.xpd.bom.types.PrimitivesUtil;
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

    private static final String NO_CASE_CLASS_TYPE_SELECTED =
            "wm.caseservice.NoCaseClassSelectedForCaseService"; //$NON-NLS-1$

    private static final String SINGLE_PARAM_IN_CASE_SERVICE_REQUIRED_ISSUE_ID =
            "wm.caseservice.SingleParamInCaseServiceRequired"; //$NON-NLS-1$

    private static final String MULTIPLE_PARAMS_IN_CASE_SERVICE_NOT_ALLOWED_ISSUE_ID =
            "wm.caseservice.MultipleParamsNotAllowedInCaseService"; //$NON-NLS-1$

    private static final String PARAM_IN_CASE_SERVICE_MUST_BE_CASE_CLASS_CASE_REF_TYPE_ISSUE_ID =
            "wm.caseservice.paramInCaseServiceMustBeCaseClassCaseRefType"; //$NON-NLS-1$

    private static final String PARAM_IN_CASE_SERVICE_MUST_BE_INPUT_ISSUE_ID =
            "wm.caseservice.paramInCaseServiceMustBeInput"; //$NON-NLS-1$

    private static final String CASE_CLASS_CASE_STATE_ATTRIBUTE_NOT_FOUND =
            "wm.caseservice.CaseClassCaseStateAttributeNotFound"; //$NON-NLS-1$

    private static final String INVALID_CASE_STATE_ATTRIBUTE_FOUND_IN_CASE_SERVICE =
            "wm.caseservice.InvalidCaseStateAttributeFoundInCaseService"; //$NON-NLS-1$

    private static final String TERMINAL_CASE_STATE_NOT_ALLOWED =
            "ace.wm.caseservice.TerminalCaseStateNotAllowed"; //$NON-NLS-1$

    private static final String NO_SPECIFIC_CASE_STATE_SELECTED_ISSUE_ID =
            "wm.caseservice.NoSpecificCaseStateIsSelected"; //$NON-NLS-1$

    public static final String PARAM_TYPE_ADDITIONAL_INFO_KEY = "paramType"; //$NON-NLS-1$

    public static final String PARAM_LABEL_ADDITIONAL_INFO_KEY = "paramLabel"; //$NON-NLS-1$

    private static final TerminalStateProperties terminalStateUtil =
            new TerminalStateProperties();

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        if (!Xpdl2ModelUtil.isCaseService(process)) {
            return;
        }

        CaseService caseService = getCaseService(process);
        if (caseService == null || caseService.getCaseClassType() == null) {
            addIssue(NO_CASE_CLASS_TYPE_SELECTED, process);
            return;
        }

        ExternalReference caseClassExtRef = caseService.getCaseClassType();

        EObject eo = ProcessUIUtil.getReferencedClassifier(caseClassExtRef,
                WorkingCopyUtil.getProjectFor(process));
        Class caseClass = null;
        String caseClassQualifiesName = ""; //$NON-NLS-1$
        if (eo instanceof Class) {
            caseClass = (Class) eo;
            caseClassQualifiesName = caseClass.getQualifiedName();
        }

        /*
         * If case service has no param or has a param that is not of mode IN or
         * has multiple params then raise the issue
         */
        List<FormalParameter> caseServiceParams =
                ProcessInterfaceUtil.getAllFormalParameters(process);

        // if no parameters specified
        if (caseServiceParams.isEmpty()) {
            addIssue(SINGLE_PARAM_IN_CASE_SERVICE_REQUIRED_ISSUE_ID,
                    process,
                    Collections.singletonList(caseClassQualifiesName));

        } else {
            // retrieve and validate the input parameter
            FormalParameter inputParam =
                    getParameter(process, caseServiceParams);

            if (inputParam == null) {
                addIssue(MULTIPLE_PARAMS_IN_CASE_SERVICE_NOT_ALLOWED_ISSUE_ID,
                        process,
                        Collections.singletonList(caseClassQualifiesName));
            } else {
                validateInputParameter(inputParam,
                        caseClassExtRef,
                        caseClassQualifiesName);
            }
        }

        Property caseClassCaseStateAttrib =
                ProcessUIUtil.getCaseClassCaseState(caseClass);

        if (caseClassCaseStateAttrib == null || !(caseClassCaseStateAttrib
                .getType() instanceof Enumeration)) {
            /*
             * Raise issue if case class doesn't have a case state attribute
             */
            addIssue(CASE_CLASS_CASE_STATE_ATTRIBUTE_NOT_FOUND,
                    process,
                    Collections.singletonList(caseClassQualifiesName));
        } else {
            validateSelectedStates(process,
                    caseService,
                    caseClassCaseStateAttrib);
        }
    }

    /**
     * Returns the input parameter to the case-action. If only one parameter is
     * specified, it will be returned whether it's an input or output (validate
     * will catch it). Otherwise, if multiple, or no, input parameters are
     * specified the return value will be null.
     * 
     * @param aProcess
     * @param aCaseServiceParams
     * @return the case-action's parameter
     */
    private FormalParameter getParameter(Process aProcess,
            List<FormalParameter> aCaseServiceParams) {

        // if only one parameter specified
        if (aCaseServiceParams.size() == 1) {
            // return regardless of direction - validate later
            return aCaseServiceParams.get(0);
        }

        // multiple parameters - only one can be an input
        FormalParameter result = null;
        int numberOfInParams = 0;
        for (FormalParameter parameter : aCaseServiceParams) {
            if (ModeType.IN_LITERAL.equals(parameter.getMode())) {
                numberOfInParams++;
                result = parameter;
            }

            if (ModeType.INOUT_LITERAL.equals(parameter.getMode())) {
                numberOfInParams++;
            }

            if (numberOfInParams > 1) {
                return null;
            }
        }

        return result;
    }

    /**
     * Validates the case-action's input parameter.
     * 
     * @param aInputParam
     * @param aCaseClassExtRef
     * @param aCaseClassQualifiesName
     */
    private void validateInputParameter(FormalParameter aInputParam,
            ExternalReference aCaseClassExtRef,
            String aCaseClassQualifiesName) {
        Map<String, String> addInfo = new HashMap<String, String>();
        addInfo.put(PARAM_LABEL_ADDITIONAL_INFO_KEY, aInputParam.getName());
        addInfo.put(PARAM_TYPE_ADDITIONAL_INFO_KEY, aCaseClassQualifiesName);

        /*
         * check if the type of param is a reference to the same case class as
         * that of the case service case class
         */
        boolean invalidFormalParamType = true;
        DataType type = aInputParam.getDataType();
        if (type instanceof RecordType) {
            RecordType recordType = (RecordType) type;
            ExternalReference paramTypeExtRef =
                    recordType.getMember().get(0).getExternalReference();
            if (EcoreUtil.equals(aCaseClassExtRef, paramTypeExtRef)) {
                invalidFormalParamType = false;
            }
        }

        if (invalidFormalParamType) {
            addIssue(
                    PARAM_IN_CASE_SERVICE_MUST_BE_CASE_CLASS_CASE_REF_TYPE_ISSUE_ID,
                    aInputParam,
                    Collections.singletonList(aCaseClassQualifiesName),
                    addInfo);

        } else if (ModeType.IN != aInputParam.getMode().getValue()) {
            addIssue(PARAM_IN_CASE_SERVICE_MUST_BE_INPUT_ISSUE_ID,
                    aInputParam,
                    Collections.singletonList(aCaseClassQualifiesName),
                    addInfo);

        }

        /* XPD-6827: Case Actions *CaseRef array* support */
        if (aInputParam.isIsArray()) {
            addIssue(INPUT_CASE_REF_CANNOT_BE_AN_ARRAY,
                    aInputParam,
                    Collections.singletonList(aCaseClassQualifiesName),
                    addInfo);
        }
    }

    /**
     * Validates the case states that have been selected as those in which the
     * case-action will be visible/available.
     * 
     * @param aProcess
     * @param aCaseService
     * @param aCaseStateAttr
     */
    private void validateSelectedStates(Process aProcess,
            CaseService aCaseService, Property aCaseStateAttr) {
        VisibleForCaseStates visibleStates =
                aCaseService.getVisibleForCaseStates();
        if (visibleStates == null) {
            // null means "all suitable states"
            return;
        }

        if (!visibleStates.isVisibleForUnsetCaseState()
                && visibleStates.getCaseState().isEmpty()) {
            addIssue(NO_SPECIFIC_CASE_STATE_SELECTED_ISSUE_ID, aProcess);
            return;
        }

        // get the enumeration and its possible values
        Enumeration caseStateEnum = (Enumeration) aCaseStateAttr.getType();
        EList<EnumerationLiteral> caseStateValues =
                caseStateEnum.getOwnedLiterals();

        // get the list of terminal state values
        EList<EnumerationLiteral> terminalStates =
                terminalStateUtil.getTerminalStates(aCaseStateAttr);

        // test all selected case-state values
        for (ExternalReference selectedState : visibleStates.getCaseState()) {
            // Check if the state is one of the enums in the case class
            boolean found = false;
            for (EnumerationLiteral caseStateValue : caseStateValues) {
                ExternalReference ref = ProcessUIUtil
                        .getExternalRefForEnumLit(aCaseService, caseStateValue);
                if (EcoreUtil.equals(ref, selectedState)) {
                    // we found it - is it one of the terminal states
                    if (terminalStates.contains(caseStateValue)) {
                        addIssue(TERMINAL_CASE_STATE_NOT_ALLOWED,
                                aProcess,
                                Collections.singletonList(PrimitivesUtil
                                        .getDisplayLabel(caseStateValue)));
                    }

                    found = true;
                    break;
                }
            }
            if (!found) {
                addIssue(INVALID_CASE_STATE_ATTRIBUTE_FOUND_IN_CASE_SERVICE,
                        aProcess);
            }
        }
    }

    /**
     * 
     * @return case service set in the given process or null
     */
    public static CaseService getCaseService(Process process) {

        if (process != null) {
            Object caseService = Xpdl2ModelUtil.getOtherElement(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_CaseService());
            if (caseService instanceof CaseService) {
                return (CaseService) caseService;
            }
        }
        return null;
    }
}
