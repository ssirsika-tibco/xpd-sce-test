/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.wsdl.rules.resolutions;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
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
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Repair the generated web services in the target
 * package/process/processinterface/
 * 
 * @author aallway
 * @since 3.3 (14 May 2010)
 */
public class RepairGeneratedWebServiceLocationResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        Package pkg = Xpdl2ModelUtil.getPackage(target);
        if (pkg != null) {
            IFile xpdlFile = WorkingCopyUtil.getFile(pkg);
            if (xpdlFile != null) {

                CompoundCommand cmd = new CompoundCommand();

                /*
                 * Repair broken interface method reference locations
                 */
                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);
                if (processInterfaces != null) {
                    String wsdlFileLocation =
                            Xpdl2ModelUtil.getWsdlFileName(xpdlFile);
                    if (wsdlFileLocation != null) {
                        for (ProcessInterface processInterface : processInterfaces
                                .getProcessInterface()) {
                            Collection<InterfaceMethod> methods =
                                    ProcessInterfaceUtil
                                            .getIfcMessageMethods(processInterface);
                            for (InterfaceMethod method : methods) {
                                repairGeneratedWsdlLocation(editingDomain,
                                        method,
                                        wsdlFileLocation,
                                        cmd);
                            }
                        }
                    }
                }

                /*
                 * Repair broken generated wsdl activity reference locations.
                 */
                for (Process process : pkg.getProcesses()) {
                    Collection<Activity> activities =
                            Xpdl2ModelUtil.getAllActivitiesInProc(process);
                    for (Activity activity : activities) {
                        repairGeneratedWsdlLocation(editingDomain,
                                activity,
                                cmd);
                    }
                }

                if (!cmd.isEmpty()) {
                    return cmd;
                }
            }

        }
        return null;
    }

    /**
     * Append command to repair the wsdl location in the given method.
     * 
     * @param editingDomain
     * @param method
     * @param wsdlFileLocation
     * @param cmd
     */
    private void repairGeneratedWsdlLocation(EditingDomain editingDomain,
            InterfaceMethod method, String wsdlFileLocation, CompoundCommand cmd) {
        TriggerResultMessage trm = method.getTriggerResultMessage();
        if (trm != null) {
            repairGeneratedWsdlLocation(editingDomain,
                    trm.getWebServiceOperation(),
                    wsdlFileLocation,
                    cmd);

            repairGeneratedWsdlLocation(editingDomain,
                    (PortTypeOperation) Xpdl2ModelUtil.getOtherElement(trm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation()),
                    wsdlFileLocation,
                    cmd);
        }

        return;
    }

    /**
     * Append command to repair the wsdl location in the given activity.
     * 
     * @param editingDomain
     * @param activity
     * @param cmd
     */
    private void repairGeneratedWsdlLocation(EditingDomain editingDomain,
            Activity activity, CompoundCommand cmd) {
        if (isGeneratedRequestOrReply(activity)) {

            IFile xpdlFile = WorkingCopyUtil.getFile(activity);

            /*
             * Sid XPD-7618: Activity may be implementing an interface method
             * that may be in a different XPDL file in which case differnet
             * WSDL. So get the wsdl file locaiton per activity now.
             */
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                InterfaceMethod implementedMethod =
                        ProcessInterfaceUtil.getImplementedMethod(activity);

                if (implementedMethod != null) {
                    xpdlFile = WorkingCopyUtil.getFile(implementedMethod);
                }
            }

            if (xpdlFile != null) {
                String wsdlFileLocation =
                        Xpdl2ModelUtil.getWsdlFileName(xpdlFile);

                if (wsdlFileLocation != null) {

                    repairGeneratedWsdlLocation(editingDomain,
                            Xpdl2ModelUtil.getWebServiceOperation(activity),
                            wsdlFileLocation.toString(),
                            cmd);

                    repairGeneratedWsdlLocation(editingDomain,
                            Xpdl2ModelUtil.getPortTypeOperation(activity),
                            wsdlFileLocation.toString(),
                            cmd);
                }
            }
        }
        return;
    }

    /**
     * @param activity
     * @return true if the activity is a generated request or a reply to one.
     */
    public static boolean isGeneratedRequestOrReply(Activity activity) {
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
     * Append command to repair the wsdl location in the given
     * WebServiceOperation
     * 
     * @param editingDomain
     * @param wso
     * @param wsdlFileLocation
     * @param cmd
     */
    private void repairGeneratedWsdlLocation(EditingDomain editingDomain,
            WebServiceOperation wso, String wsdlFileLocation,
            CompoundCommand cmd) {
        if (wso != null) {
            Service service = wso.getService();
            if (service != null) {
                EndPoint endPoint = service.getEndPoint();
                if (endPoint != null) {
                    ExternalReference externalReference =
                            endPoint.getExternalReference();

                    String wsdlLocation = externalReference.getLocation();
                    if (!wsdlFileLocation.equals(wsdlLocation)) {
                        cmd.append(SetCommand.create(editingDomain,
                                externalReference,
                                Xpdl2Package.eINSTANCE
                                        .getExternalReference_Location(),
                                wsdlFileLocation));
                    }
                }
            }
        }
        return;
    }

    /**
     * Append command to repair the wsdl location in the given PortTypeOperation
     * 
     * @param editingDomain
     * @param pto
     * @param wsdlFileLocation
     * @param cmd
     */
    private void repairGeneratedWsdlLocation(EditingDomain editingDomain,
            PortTypeOperation pto, String wsdlFileLocation, CompoundCommand cmd) {
        if (pto != null) {
            ExternalReference externalReference = pto.getExternalReference();

            String wsdlLocation = externalReference.getLocation();
            if (!wsdlFileLocation.equals(wsdlLocation)) {
                cmd.append(SetCommand.create(editingDomain,
                        externalReference,
                        Xpdl2Package.eINSTANCE.getExternalReference_Location(),
                        wsdlFileLocation));
            }

        }
        return;
    }

}
