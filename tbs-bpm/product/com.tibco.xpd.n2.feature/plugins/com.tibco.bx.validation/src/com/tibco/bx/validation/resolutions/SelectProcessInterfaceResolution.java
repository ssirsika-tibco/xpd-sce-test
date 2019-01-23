/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.validation.rules.process.ServiceProcessValidationRules;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker.ProcessPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution quick-fix class to select Process Interface for a process.
 * 
 * <p>
 * This resolution is used for the following validation problems raised from
 * {@link ServiceProcessValidationRules}:
 * 
 * <li>A Service Process can only implement Service Process Interface (raised if
 * a service process implements a normal process interface)</li>
 * 
 * <li>Only Service Processes can implement Service Process Interfaces (raised
 * if any other process type implements service process interface)</li>
 * 
 * </p>
 * 
 * @author bharge
 * @since 11 Feb 2015
 */
public class SelectProcessInterfaceResolution extends
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

        if (target instanceof Process) {

            Process process = (Process) target;

            ProcessFilterPicker picker = null;
            /*
             * if the process is service process then show the picker with
             * service process interfaces listed
             */
            if (Xpdl2ModelUtil.isServiceProcess(process)) {

                picker =
                        new ProcessFilterPicker(Display.getDefault()
                                .getActiveShell(),
                                ProcessPickerType.SERVICEPROCESSINTERFACE,
                                false);
            } else {

                /*
                 * if the process is NOT a service process then show the picker
                 * with process interfaces listed
                 */
                picker =
                        new ProcessFilterPicker(Display.getDefault()
                                .getActiveShell(),
                                ProcessPickerType.PROCESSINTERFACE, false);
            }

            if (null != picker) {

                ProcessInterface implementedProcessInterface =
                        ProcessInterfaceUtil
                                .getImplementedProcessInterface(process);

                if (null != implementedProcessInterface) {

                    picker.setInitialElementSelections(Collections
                            .singletonList(implementedProcessInterface));
                }

                EObject result =
                        ProcessUIUtil.getResultFromPicker(picker, Display
                                .getDefault().getActiveShell(), process);
                if (result instanceof ProcessInterface) {

                    ProcessInterface newProcessInterface =
                            (ProcessInterface) result;
                    if (newProcessInterface != implementedProcessInterface) {

                        Command cmd =
                                ImplementInterfaceUtil
                                        .addImplementedProcessInterfaceCommand(editingDomain,
                                                process,
                                                newProcessInterface);
                        return cmd;
                    }
                }
            }

        }
        return null;
    }

}
