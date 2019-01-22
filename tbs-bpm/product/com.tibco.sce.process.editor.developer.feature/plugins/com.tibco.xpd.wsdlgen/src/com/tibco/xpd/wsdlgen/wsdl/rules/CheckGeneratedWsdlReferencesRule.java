/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.wsdl.rules;

import java.util.Collection;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check that the generated web service activities have correct
 * reference to their generated WSDL.
 * 
 * @author aallway
 * @since 3.3 (14 May 2010)
 */
public class CheckGeneratedWsdlReferencesRule extends PackageValidationRule
        implements IValidationRule {

    private static final String ISSUE_BADREF =
            "wsdlgen.badGeneratedWebServiceConfiguration"; //$NON-NLS-1$

    @Override
    public void validate(Package pkg) {

        IFile xpdlFile = WorkingCopyUtil.getFile(pkg);
        if (xpdlFile != null && xpdlFile.exists()) {

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);

            if (processInterfaces != null) {
                /*
                 * All interfaces WSDL file refs will be from this one XPDL
                 * file.
                 */
                String wsdlFileName = Xpdl2ModelUtil.getWsdlFileName(xpdlFile);
                if (wsdlFileName != null) {

                    for (ProcessInterface processInterface : processInterfaces
                            .getProcessInterface()) {
                        /*
                         * SID XPD-914: changed to complain at activity/method
                         * level so can be seen at same level as
                         * "WSDL not generated yet" problems
                         */
                        checkBadGeneratedWsdlRef(processInterface, wsdlFileName);
                    }
                }
            }

            for (Process process : pkg.getProcesses()) {
                /*
                 * SID XPD-914: changed to complain at activity/method level so
                 * can be seen at same level as "WSDL not generated yet"
                 * problems
                 */

                /*
                 * XPD-7384 don't know which is correct WSDL file until we get
                 * to each individual activity, maybe the WSDL generated from
                 * process OR could be that generated for implemented process
                 * interface message event.
                 */
                ProcessInterface implementedProcessInterface =
                        ProcessInterfaceUtil
                                .getImplementedProcessInterface(process);

                checkBadGeneratedWsdlRef(process, implementedProcessInterface);
            }

        }

    }

    /**
     * @param process
     * @param implementedProcessInterface
     *            Implemnted interface or <code>null</code> if not implemetning
     *            one.
     * 
     * @return true if there are bad references to generated wsdl.
     */
    private boolean checkBadGeneratedWsdlRef(Process process,
            ProcessInterface implementedProcessInterface) {
        boolean ret = false;

        /*
         * XPD-7384 don't know which is correct WSDL file until we get to each
         * individual activity, maybe the WSDL generated from process OR could
         * be that generated for implemented process interface message event.
         */
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            String wsdlFileName = null;
            if (implementedProcessInterface != null
                    && Xpdl2ModelUtil.isEventImplemented(activity)) {
                wsdlFileName =
                        Xpdl2ModelUtil.getWsdlFileName(WorkingCopyUtil
                                .getFile(implementedProcessInterface));
            } else {
                wsdlFileName =
                        Xpdl2ModelUtil.getWsdlFileName(WorkingCopyUtil
                                .getFile(process));
            }

            if (wsdlFileName != null) {
                if (checkBadGeneratedWsdlRef(activity, wsdlFileName)) {
                    addIssue(ISSUE_BADREF, activity);
                    ret = true;
                }
            }
        }

        return ret;
    }

    /**
     * @param activity
     * @return true if there are bad references to generated wsdl.
     */
    public static boolean checkBadGeneratedWsdlRef(Activity activity,
            String wsdlFileName) {
        if (isGeneratedRequestOrReply(activity)) {

            /* Check location in WebServiceOperation */
            String wsdlLocation =
                    getWsdlLocation(Xpdl2ModelUtil
                            .getWebServiceOperation(activity));
            if (wsdlLocation != null && wsdlLocation.length() > 0) {
                /*
                 * Only interested if defined WRONG not interested if not
                 * defined at all (after all, tyhe user might not have the
                 * generating feature installed / active)
                 */
                if (!wsdlFileName.equals(wsdlLocation)) {
                    return true;
                }
            }

            /* Check location in PortTypeOperation */
            wsdlLocation =
                    getWsdlLocation(Xpdl2ModelUtil
                            .getPortTypeOperation(activity));
            if (wsdlLocation != null && wsdlLocation.length() > 0) {
                /*
                 * Only interested if defined WRONG not interested if not
                 * defined at all (after all, tyhe user might not have the
                 * generating feature installed / active)
                 */
                if (!wsdlFileName.equals(wsdlLocation)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * @param activity
     * @return true if the activity is a generated request or a reply to one.
     */
    private static boolean isGeneratedRequestOrReply(Activity activity) {
        if (ReplyActivityUtil.isReplyActivity(activity)) {
            activity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
        }

        if (activity != null) {
            return Xpdl2ModelUtil.isGeneratedRequestActivity(activity);
        }

        return false;
    }

    /**
     * @param processInterface
     * @param wsdlFileName
     * @return true if there are bad references to generated wsdl.
     */
    private boolean checkBadGeneratedWsdlRef(ProcessInterface processInterface,
            String wsdlFileName) {
        boolean ret = false;
        Collection<InterfaceMethod> ifcMessageMethods =
                ProcessInterfaceUtil.getIfcMessageMethods(processInterface);
        for (InterfaceMethod interfaceMethod : ifcMessageMethods) {
            TriggerResultMessage trm =
                    interfaceMethod.getTriggerResultMessage();
            if (trm != null) {
                /* Check location in WebServiceOperation */
                String wsdlLocation =
                        getWsdlLocation(trm.getWebServiceOperation());
                if (wsdlLocation != null && wsdlLocation.length() > 0) {
                    /*
                     * Only interested if defined WRONG not interested if not
                     * defined at all (after all, tyhe user might not have the
                     * generating feature installed / active)
                     */
                    if (!wsdlFileName.equals(wsdlLocation)) {
                        addIssue(ISSUE_BADREF, interfaceMethod);
                        ret = true;
                    }
                }

                /* Check location in PortTypeOperation */
                wsdlLocation =
                        getWsdlLocation((PortTypeOperation) Xpdl2ModelUtil
                                .getOtherElement(trm,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation()));

                if (wsdlLocation != null && wsdlLocation.length() > 0) {
                    /*
                     * Only interested if defined WRONG not interested if not
                     * defined at all (after all, tyhe user might not have the
                     * generating feature installed / active)
                     */
                    if (!wsdlFileName.equals(wsdlLocation)) {
                        addIssue(ISSUE_BADREF, interfaceMethod);
                        ret = true;
                    }
                }
            }

        }
        return ret;
    }

    /**
     * @param portTypeOperation
     * @return The ExternalReference/Location attribute or null if not defined.
     */
    private static String getWsdlLocation(PortTypeOperation pto) {
        String wsdlLocation = null;
        if (pto != null) {
            ExternalReference externalReference = pto.getExternalReference();

            wsdlLocation = externalReference.getLocation();
        }
        return wsdlLocation;
    }

    /**
     * @param wso
     * @return The ExternalReference/Location attribute or null if not defined.
     */
    private static String getWsdlLocation(WebServiceOperation wso) {
        String wsdlLocation = null;
        if (wso != null) {
            Service service = wso.getService();
            if (service != null) {
                EndPoint endPoint = service.getEndPoint();
                if (endPoint != null) {
                    ExternalReference externalReference =
                            endPoint.getExternalReference();

                    /*
                     * ExternalReference is not always there until first save of
                     * generated wsdl.
                     */
                    if (externalReference != null) {
                        wsdlLocation = externalReference.getLocation();
                    }

                }
            }
        }
        return wsdlLocation;
    }

}
