/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.validations;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.xerces.util.XMLChar;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.wsdlgen.WsdlGenBuilderTransformer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Lots of things in an XPDL that is generating wsdl will be used in places in
 * the wsdl where xsd NCNAME restrictions apply.
 * <p>
 * NCNAME is (roughly speaking) any Alpha or "_" followed by any AlpohaNumeric
 * or "." or "-" or "_"
 * 
 * 
 * @author aallway
 * @since 3.3 (13 Apr 2010)
 */
public class CheckNCNamesRule extends PackageValidationRule {

    private final static String ISSUE_PACKAGE_MUST_BE_NCNAME =
            "wsdlgen.packageMustBeNCName"; //$NON-NLS-1$

    private final static String ISSUE_PROCESS_MUST_BE_NCNAME =
            "wsdlgen.processMustBeNCName"; //$NON-NLS-1$ 

    private final static String ISSUE_INTERFACE_MUST_BE_NCNAME =
            "wsdlgen.processInterfaceMustBeNCName"; //$NON-NLS-1$ 

    private final static String ISSUE_ACTIVITY_MUST_BE_NCNAME =
            "wsdlgen.apiRequestActivityMustBeNCName"; //$NON-NLS-1$ 

    private final static String ISSUE_PARAMETER_MUST_BE_NCNAME =
            "wsdlgen.parameterMustBeNCName"; //$NON-NLS-1$ 

    private final static String ISSUE_METHOD_MUST_BE_NCNAME =
            "wsdlgen.interaceMethodMustBeNCName"; //$NON-NLS-1$ 

    private final static String ISSUE_ERRORCODE_MUST_BE_NCNAME =
            "wsdlgen.errorCodeMustBeNCName"; //$NON-NLS-1$ 

    @Override
    public void validate(Package pckg) {

        if (WsdlGenBuilderTransformer.shouldCreateWsdl(pckg)) {
            /*
             * Check package name (used for the wsdl:definitions/@name
             * attribute).
             */
            checkNCName(pckg, ISSUE_PACKAGE_MUST_BE_NCNAME);

            /*
             * Check process names - these become port types
             */
            for (Process process : pckg.getProcesses()) {
                Set<FormalParameter> parametersToCheck = checkProcess(process);

                for (FormalParameter parameter : parametersToCheck) {
                    checkNCName(parameter, ISSUE_PARAMETER_MUST_BE_NCNAME);
                }
            }

            /*
             * Check interface names - these become port types
             */
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pckg);
            if (processInterfaces != null) {
                for (ProcessInterface processInterface : processInterfaces
                        .getProcessInterface()) {

                    Set<FormalParameter> parametersToCheck =
                            checkProcessInterface(processInterface);

                    for (FormalParameter parameter : parametersToCheck) {
                        checkNCName(parameter, ISSUE_PARAMETER_MUST_BE_NCNAME);
                    }
                }
            }
        }

        return;
    }

    /**
     * Check NCNAME related issues in process interface
     * 
     * @param processInterface
     * @return list of formal parameters involved in wsdl generation (hence
     *         require checking) because we only want to check each parameter
     *         once!
     */
    private Set<FormalParameter> checkProcessInterface(
            ProcessInterface processInterface) {
        HashSet<FormalParameter> checkParameters =
                new HashSet<FormalParameter>();

        checkNCName(processInterface, ISSUE_INTERFACE_MUST_BE_NCNAME);

        /*
         * Check method names these become operations.
         */
        for (InterfaceMethod method : processInterface.getStartMethods()) {
            checkMethod(ISSUE_METHOD_MUST_BE_NCNAME, method, checkParameters);
        }
        for (InterfaceMethod method : processInterface.getIntermediateMethods()) {
            checkMethod(ISSUE_METHOD_MUST_BE_NCNAME, method, checkParameters);
        }

        return checkParameters;
    }

    /**
     * Check method breaks no NCName restrictions.
     * 
     * @param issueMethodMustBeNcname
     * @param method
     * @param checkParameters
     */
    private void checkMethod(String issueMethodMustBeNcname,
            InterfaceMethod method, HashSet<FormalParameter> checkParameters) {
        /*
         * All start methods become operations.
         * 
         * Message intermediate method becomes operation.
         */
        if (method instanceof StartMethod
                || (method instanceof IntermediateMethod && TriggerType.MESSAGE_LITERAL
                        .equals(((IntermediateMethod) method).getTrigger()))) {

            checkNCName(method, ISSUE_METHOD_MUST_BE_NCNAME);

            /*
             * Explicitly or Implicitly associated data will become part names
             */
            List<FormalParameter> formalParameters =
                    ProcessInterfaceUtil
                            .getInterfaceMethodAssociatedFormalParameters(method);
            for (FormalParameter parameter : formalParameters) {
                checkParameters.add(parameter);
            }

            /*
             * Error method names become fault names.
             */
            for (ErrorMethod errorMethod : method.getErrorMethods()) {

                checkNCName(errorMethod,
                        errorMethod.getErrorCode(),
                        ISSUE_ERRORCODE_MUST_BE_NCNAME);

                /*
                 * Explicitly or Implicitly associated data will become part
                 * names
                 */
                List<FormalParameter> errorParameters =
                        ProcessInterfaceUtil
                                .getErrorMethodAssociatedFormalParameters(errorMethod);
                for (FormalParameter parameter : errorParameters) {
                    checkParameters.add(parameter);
                }
            }
        }

        return;
    }

    /**
     * Check NCNAME related issues in process
     * 
     * @param process
     * @return list of formal parameters involved in wsdl generation (hence
     *         require checking) because we only want to check each parameter
     *         once!
     */
    private Set<FormalParameter> checkProcess(Process process) {
        HashSet<FormalParameter> checkParameters =
                new HashSet<FormalParameter>();

        checkNCName(process, ISSUE_PROCESS_MUST_BE_NCNAME);

        /*
         * Check API Request activity names - these become operation names
         */
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : activities) {
            checkActivity(activity, checkParameters);
        }

        return checkParameters;
    }

    /**
     * Check activity breaks no NCName restrictions.
     * 
     * @param activity
     * @param checkParameters
     */
    private void checkActivity(Activity activity,
            HashSet<FormalParameter> checkParameters) {
        /*
         * If it's a generated request activity and not one that implements
         * interface method (as the name is set in the method) - these become
         * operation names
         * 
         * Operation will be created for start type none if the special
         * InternalJmxDebug attribute is set
         */
        boolean doCheckParameters = false;
        if (isGeneratedButNoImplementingApiActivity(activity)
                || isGeneratingSubProcessStartEvent(activity)) {

            checkNCName(activity, ISSUE_ACTIVITY_MUST_BE_NCNAME);

            doCheckParameters = true;

        } else if (isGeneratingThrowFaultEvent(activity)) {
            /*
             * A throw error event for a request activity that generates an
             * operation will become a WSDL fault
             */
            checkNCName(activity,
                    ThrowErrorEventUtil
                            .getFaultNameForErrorCode(ThrowErrorEventUtil
                                    .getThrowErrorCode(activity)),
                    ISSUE_ERRORCODE_MUST_BE_NCNAME);

            doCheckParameters = true;
        }

        if (doCheckParameters) {
            /*
             * Explicitly or Implicitly associated data will become part names
             */
            List<FormalParameter> formalParameters =
                    ProcessInterfaceUtil
                            .getAssociatedFormalParameters(activity);
            for (FormalParameter parameter : formalParameters) {
                checkParameters.add(parameter);
            }
        }

        return;
    }

    /**
     * @param activity
     * @return true if the activity is a throw fault for a request activity that
     *         generates an operation.
     */
    private boolean isGeneratingThrowFaultEvent(Activity activity) {

        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            Activity requestActivity =
                    ThrowErrorEventUtil.getRequestActivity(activity);
            if (requestActivity != null) {
                if (isGeneratedButNoImplementingApiActivity(requestActivity)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isGeneratingSubProcessStartEvent(Activity activity) {
        if (activity.getEvent() instanceof StartEvent) {
            if (TriggerType.NONE_LITERAL.equals(((StartEvent) activity
                    .getEvent()).getTrigger())) {
                if (WsdlGenBuilderTransformer
                        .doesContainRequiredExtendedAttribute(activity
                                .getProcess())) {
                    /*
                     * Operation will be created for start type none if the
                     * special InternalJmxDebug attribute is set
                     */
                    return true;
                }
            }

        }
        return false;
    }

    private boolean isGeneratedButNoImplementingApiActivity(Activity activity) {
        if ((Xpdl2ModelUtil.isGeneratedRequestActivity(activity) && !Xpdl2ModelUtil
                .isEventImplemented(activity))) {
            return true;
        }
        return false;
    }

    /**
     * Check that given named element has a valiud NCNAME xpdl2 name. Add issue
     * if not.
     * 
     * @param namedElement
     * @param issueId
     * @return true if name ok, false if issue added.
     */
    private boolean checkNCName(NamedElement namedElement, String issueId) {
        return checkNCName(namedElement, namedElement.getName(), issueId);
    }

    /**
     * Check that given named element has a valiud NCNAME xpdl2 name. Add issue
     * if not.
     * 
     * @param target
     *            target object for issue
     * @param name
     *            value that must be NCNAME
     * 
     * @param issueId
     * 
     * @return true if name ok, false if issue added.
     */
    private boolean checkNCName(EObject target, String name, String issueId) {

        if (name != null && name.length() > 0) {

            /*
             * XPD-5911: if a process name has leading digit(s) then prefix with
             * underscore
             */
            if (Character.isDigit(name.charAt(0))) {

                name = "_" + name; //$NON-NLS-1$
            }

            if (XMLChar.isValidNCName(name)) {

                return true;
            }
        }

        addIssue(issueId, target, Collections.singletonList(name));

        return false;
    }

}
