/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.xpdl2.Activator;
import com.tibco.xpd.validation.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;

/**
 * Resolution to enable destination environments for the process interface that
 * is being implemented by the target process.
 * 
 * @author rsomayaj
 */
public class InterfaceDestinationResolution extends
        AbstractWorkingCopyResolution {

    /**
     * Flag to indicate whether we need to save model or not.
     */
    private boolean shouldSaveModel = false;

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof com.tibco.xpd.xpdl2.Process) {

            com.tibco.xpd.xpdl2.Process process =
                    (com.tibco.xpd.xpdl2.Process) target;

            Set<String> processDestinations =
                    DestinationUtil.getEnabledGlobalDestinations(process);

            ProcessInterface implementedInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);

            boolean executeCommand = false;

            /*
             * Check if process and process interface are in different XPDLs
             * because if that's the case, then we'll have to save
             */
            if (!WorkingCopyUtil.getFile(process)
                    .equals(WorkingCopyUtil.getFile(implementedInterface))) {

                /*
                 * Open the dialog to get a confirmation from the user that the
                 * changes to be made under this quick fix will be saved.
                 */
                executeCommand =
                        MessageDialog
                                .openConfirm(Display.getDefault()
                                        .getActiveShell(),
                                        Messages.InterfaceDestinationResolution_EnableDestinationEnv_DialogTitle,
                                        Messages.InterfaceDestinationResolution_EnableDestinationEnv_DialogText);

                shouldSaveModel = executeCommand;

            } else {

                executeCommand = true;
            }

            if (executeCommand) {

                CompoundCommand destEnableCmd =
                        new CompoundCommand(
                                Messages.InterfaceDestinationResolution_EnableInterfaceDests_menu);

                for (String dest : processDestinations) {

                    if (!DestinationUtil
                            .isGlobalDestinationEnabled(implementedInterface,
                                    dest)) {

                        destEnableCmd.append(DestinationUtil
                                .getSetDestinationEnabledCommand(editingDomain,
                                        implementedInterface,
                                        dest,
                                        true));
                    }
                }

                return destEnableCmd;

            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#performAdditionalOperations()
     * 
     */
    @Override
    protected void performAdditionalOperations(EditingDomain editingDomain,
            EObject target, IMarker marker) {

        if (shouldSaveModel && target instanceof com.tibco.xpd.xpdl2.Process) {

            com.tibco.xpd.xpdl2.Process process = (Process) target;

            ProcessInterface implementedInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);

            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopyFor(implementedInterface);

            if (wc != null) {

                try {

                    wc.save();

                } catch (IOException e) {

                    Activator.getDefault().getLogger().error(e);
                }
            }
        }
    }

}
