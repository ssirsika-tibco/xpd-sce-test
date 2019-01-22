/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (6 Apr 2010)
 */
public class RepairInvalidPorttypeResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {

            Activity activity = (Activity) target;

            PortTypeOperation portTypeOperation =
                    getPortTypeOperation(activity);
            InterfaceMethod implementedMethod =
                    ProcessInterfaceUtil.getImplementedMethod(activity);
            if (null != implementedMethod) {
                CompoundCommand cmd = new CompoundCommand();
                cmd.append(SetCommand.create(editingDomain,
                        portTypeOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getPortTypeOperation_PortTypeName(),
                        ProcessInterfaceUtil
                                .getProcessInterface(implementedMethod)
                                .getName()));
                cmd.append(SetCommand.create(editingDomain,
                        portTypeOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getPortTypeOperation_OperationName(),
                        implementedMethod.getName()));
                return cmd;
            }

        }
        return null;
    }

    /**
     * @param activity
     * @return
     */
    private PortTypeOperation getPortTypeOperation(Activity activity) {

        TriggerResultMessage triggerResultMessage =
                EventObjectUtil.getTriggerResultMessage(activity);
        if (null != triggerResultMessage) {
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(triggerResultMessage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
            if (otherElement instanceof PortTypeOperation) {
                return (PortTypeOperation) otherElement;
            }
        }
        return null;
    }

}
