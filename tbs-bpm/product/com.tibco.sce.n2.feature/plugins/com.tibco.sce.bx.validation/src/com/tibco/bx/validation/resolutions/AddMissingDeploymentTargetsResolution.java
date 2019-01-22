/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to add missing deployment targets for a service process invoked
 * from a sub proc activity in a service process.
 * 
 * @author sajain
 * @since Jan 16, 2015
 */
public class AddMissingDeploymentTargetsResolution extends
        AbstractWorkingCopyResolution {

    /**
     * Flag to indicate whether we need to save model or not.
     */
    private boolean shouldSaveModel = false;

    /**
     * Working copy instance to store the target working copy.
     */
    WorkingCopy targetWorkingCopy = null;

    /**
     * 
     */

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

            Activity act = (Activity) target;

            /*
             * Check if activity is a call-subprocess activity.
             */
            if (act.getImplementation() instanceof SubFlow) {

                /*
                 * Get referenced process.
                 */
                EObject subProcOrIntfcObject =
                        TaskObjectUtil.getSubProcessOrInterface(act);

                ServiceProcessConfiguration parentServiceProcessConfig = null;
                ServiceProcessConfiguration subServiceProcessConfig = null;
                Process parentProc = act.getProcess();

                if (Xpdl2ModelUtil.isServiceProcess(parentProc)) {

                    /*
                     * XPD-7386: Saket: Before fetching the service process
                     * config from the process, we need to check whether the
                     * service process implements an interface or not. Because
                     * if it does, then we'd need to fetch the service process
                     * config from the service process interface rather than
                     * from the process (as the process would itself had
                     * inherited the deployment targets etc from the service
                     * process interface).
                     */
                    parentServiceProcessConfig =
                            getServiceProcessConfig(parentProc);
                }

                if (subProcOrIntfcObject instanceof Process) {

                    Process subProc = (Process) subProcOrIntfcObject;

                    /*
                     * XPD-7314: Saket: Before fetching the service process
                     * config from the process, we need to check whether the
                     * service process implements an interface or not. Because
                     * if it does, then we'd need to fetch the service process
                     * config from the service process interface rather than
                     * from the process (as the process would itself had
                     * inherited the deployment targets etc from the service
                     * process interface).
                     */
                    subServiceProcessConfig = getServiceProcessConfig(subProc);

                } else if (subProcOrIntfcObject instanceof ProcessInterface) {

                    ProcessInterface subProcIfc =
                            (ProcessInterface) subProcOrIntfcObject;
                    subServiceProcessConfig =
                            subProcIfc.getServiceProcessConfiguration();
                }

                if (null != parentServiceProcessConfig
                        && null != subServiceProcessConfig) {

                    CompoundCommand cmd = new CompoundCommand();

                    /*
                     * If the deployment target of sub process is not same as
                     * the parent, then set it.
                     */
                    if (parentServiceProcessConfig.isDeployToPageflowRuntime() != subServiceProcessConfig
                            .isDeployToPageflowRuntime()) {

                        cmd.append(SetCommand
                                .create(editingDomain,
                                        subServiceProcessConfig,
                                        XpdExtensionPackage.eINSTANCE
                                                .getServiceProcessConfiguration_DeployToPageflowRuntime(),
                                        true));
                    }

                    /*
                     * If the deployment target of sub process is not same as
                     * the parent, then set it.
                     */
                    if (parentServiceProcessConfig.isDeployToProcessRuntime() != subServiceProcessConfig
                            .isDeployToProcessRuntime()) {

                        cmd.append(SetCommand
                                .create(editingDomain,
                                        subServiceProcessConfig,
                                        XpdExtensionPackage.eINSTANCE
                                                .getServiceProcessConfiguration_DeployToProcessRuntime(),
                                        true));
                    }

                    boolean executeCommand = false;

                    /*
                     * Check if subServiceProcessConfig and
                     * parentServiceProcessConfig are in different XPDLs because
                     * if that's the case, then we'll have to save.
                     */
                    if (!WorkingCopyUtil
                            .getFile(subServiceProcessConfig)
                            .equals(WorkingCopyUtil.getFile(parentServiceProcessConfig))) {

                        targetWorkingCopy =
                                WorkingCopyUtil
                                        .getWorkingCopyFor(subServiceProcessConfig);

                        /*
                         * Open the dialog to get a confirmation from the user
                         * that the changes to be made under this quick fix will
                         * be saved.
                         */
                        executeCommand =
                                MessageDialog
                                        .openConfirm(Display.getDefault()
                                                .getActiveShell(),
                                                Messages.AddMissingDeploymentTargetsResolution_MessageDialog_Title,
                                                Messages.AddMissingDeploymentTargetsResolution_MessageDialog_Text);

                        shouldSaveModel = executeCommand;

                    } else {

                        executeCommand = true;
                    }
                    return cmd;
                }
            }
        }

        return null;
    }

    /**
     * Get service process configuration from the specified process. This is to
     * be noted that if the specified process implements an interface, then this
     * method will fetch the service process config from the process interface
     * rather than from the process (as the service process config from the
     * process would be null).
     * 
     * @param proc
     *            Process whose service process configuration is to be fetched.
     * 
     * @return Service process configuration from the specified process. This is
     *         to be noted that if the specified process implements an
     *         interface, then this method will fetch the service process config
     *         from the process interface rather than from the process (as the
     *         service process config from the process would be null).
     */
    private ServiceProcessConfiguration getServiceProcessConfig(Process proc) {

        ServiceProcessConfiguration parentServiceProcessConfig;

        ProcessInterface procIfcImplementedByParentProc =
                ProcessInterfaceUtil.getImplementedProcessInterface(proc);

        if (procIfcImplementedByParentProc == null) {

            parentServiceProcessConfig =
                    (ServiceProcessConfiguration) Xpdl2ModelUtil
                            .getOtherElement(proc,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration());

        } else {

            parentServiceProcessConfig =
                    procIfcImplementedByParentProc
                            .getServiceProcessConfiguration();
        }

        return parentServiceProcessConfig;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#performAdditionalOperations()
     * 
     */
    @Override
    protected void performAdditionalOperations(EditingDomain editingDomain,
            EObject target, IMarker marker) {

        if (shouldSaveModel && targetWorkingCopy != null) {

            try {

                targetWorkingCopy.save();

            } catch (IOException e) {

                BxValidationPlugin.getDefault().getLogger().error(e);
            }
        }
    }

}
