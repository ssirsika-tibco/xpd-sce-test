/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to remove JS based mapping container (which ultimately means
 * grammar is set to Data Mapper in ACE)
 *
 * @author sajain
 * @since Sep 12, 2019
 */
public class RemoveJSDataMappingsResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain, EObject target, IMarker marker)
            throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            CompoundCommand cmd = new CompoundCommand(Messages.RemoveJSDataMappingsContainer_Resolution);

            if (activity.getImplementation() instanceof SubFlow) {
                /*
                 * SubFlow Data mappings.
                 */
                SubFlow subFlow = (SubFlow) (activity.getImplementation());
                if (null != subFlow.getDataMappings() && !subFlow.getDataMappings().isEmpty()) {
                    cmd.append(RemoveCommand.create(editingDomain, subFlow.getDataMappings()));
                }
            } else if (activity.getEvent() != null) {
                EventTriggerType eventType = EventObjectUtil.getEventTriggerType(activity);
                if (EventTriggerType.EVENT_ERROR_LITERAL.equals(eventType)
                        || EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(eventType)
                        || EventTriggerType.EVENT_SIGNAL_THROW_LITERAL.equals(eventType)) {
                    SignalData sigData = EventObjectUtil.getSignalDataExtensionElement(activity);
                    if (null != sigData && null != sigData.getDataMappings() && !sigData.getDataMappings().isEmpty()) {
                        /*
                         * Catch/Throw Global/Local signal data mappings.
                         */
                        cmd.append(RemoveCommand.create(editingDomain, sigData.getDataMappings()));
                    } else {
                        /*
                         * Catch error data mappings.
                         */
                        ResultError resultError = EventObjectUtil.getResultError(activity);
                        if (null != resultError) {
                            CatchErrorMappings catchErrorMappings =
                                    (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(resultError,
                                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchErrorMappings());
                            if (null != catchErrorMappings && null != catchErrorMappings.getMessage()
                                    && null != catchErrorMappings.getMessage().getDataMappings()
                                    && !catchErrorMappings.getMessage().getDataMappings().isEmpty()) {
                                cmd.append(RemoveCommand.create(editingDomain,
                                        catchErrorMappings.getMessage().getDataMappings()));
                            }
                        }
                    }
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }

        return null;
    }

}
