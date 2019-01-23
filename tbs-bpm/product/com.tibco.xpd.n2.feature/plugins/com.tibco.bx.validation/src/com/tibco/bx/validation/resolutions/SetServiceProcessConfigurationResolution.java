/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.rules.CallSubProcessRule;
import com.tibco.bx.validation.rules.process.ServiceProcessInterfaceValidationRules;
import com.tibco.bx.validation.rules.process.ServiceProcessValidationRules;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution quick-fix class to set deployment target configuration on a
 * Service Process or Service Process Interface.
 * 
 * <p>
 * This resolution updates {@link ServiceProcessConfiguration} model element for
 * a Service Process or Service Process Interface
 * </p>
 * 
 * <p>
 * This resolution is used for the following validation problems raised from
 * following validation classes:
 * </p>
 * 
 * <p>
 * 1. {@link ServiceProcessValidationRules}:
 * <li>At least one deployment target configuration property must be selected
 * for Service Process (raised if no target deployment configuration property is
 * selected on the Service Process)</li>
 * <li>At least one deployment target configuration property must be selected
 * for Service Processes</li>
 * </p>
 * 
 * <p>
 * 2. {@link ServiceProcessInterfaceValidationRules}:
 * <li>At least one deployment target configuration property must be selected
 * for Service Process Interfaces</li>
 * </p>
 * 
 * <p>
 * 3. {@link CallSubProcessRule}:
 * <li>A Business process cannot invoke a Service process/interface that does
 * not have Process run-time as a deployment target.</li>
 * <li>In asynchronous-detached mode a Service process can only call a Service
 * process/interface that has the Process run-time deployment target selected.</li>
 * </p>
 * 
 * <p>
 * 4. PageflowCallSubProcessRule (in Process Editor Feature):
 * <li>A Pageflow process can only synchronously call a Service
 * process/interface that has the Pageflow run-time deployment target selected.</li>
 * <li>In asynchronous-detached mode a Pageflow process can only call a Service
 * process/interface that has the Process run-time deployment target selected.</li>
 * </p>
 * 
 * <p>
 * <b>Please note that when we use this resolution for validations coming from
 * CallSubProcessRule OR PageflowCallSubProcessRule, it is used from an activity
 * in a business or pageflow that invokes a service process.</b>
 * </p>
 * 
 * @author bharge
 * @since 10 Feb 2015
 */
public abstract class SetServiceProcessConfigurationResolution extends
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

        ServiceProcessConfiguration serviceProcessConfiguration = null;

        boolean deployToProcessRuntime = shouldDeployToProcessRuntime();

        boolean deployToPageflowRuntime = shouldDeployToPageflowRuntime();

        if (target instanceof Process) {

            Process process = (Process) target;

            /*
             * XPD-7503: Saket: Before fetching the service process config from
             * the process, we need to check whether the service process
             * implements an interface or not. Because if it does, then we'd
             * need to fetch the service process config from the service process
             * interface rather than from the process (as the process would
             * itself had inherited the deployment targets etc from the service
             * process interface).
             */
            if (Xpdl2ModelUtil.isServiceProcess(process)) {

                serviceProcessConfiguration = getServiceProcessConfig(process);

                if (null == serviceProcessConfiguration) {

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.ServiceProcessDeploymentTargetSection_setServiceProcessConfiguration_command);

                    serviceProcessConfiguration =
                            XpdExtensionFactory.eINSTANCE
                                    .createServiceProcessConfiguration();

                    serviceProcessConfiguration
                            .setDeployToProcessRuntime(deployToProcessRuntime);

                    serviceProcessConfiguration
                            .setDeployToPageflowRuntime(deployToPageflowRuntime);

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    (Process) target,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration(),
                                    serviceProcessConfiguration));

                    return cmd;
                }
            }
        } else if (target instanceof ProcessInterface) {

            ProcessInterface processInterface = (ProcessInterface) target;

            if (Xpdl2ModelUtil.isServiceProcessInterface(processInterface)) {

                serviceProcessConfiguration =
                        processInterface.getServiceProcessConfiguration();
            }

        } else if (target instanceof Activity) {

            Activity activity = (Activity) target;
            /*
             * This activity can be a sub proc task in a business process or a
             * pageflow process invoking a service process. The invoked service
             * process might have process run-time selected but might be getting
             * invoked from pageflow or vice-versa. So this resolution should
             * take care that the existing deployment target configuration on
             * the invoked service process is not lost.
             */
            if (activity.getImplementation() instanceof SubFlow) {

                EObject subProcOrIntfcObject =
                        TaskObjectUtil.getSubProcessOrInterface(activity);

                if (subProcOrIntfcObject instanceof Process) {

                    Process subProc = (Process) subProcOrIntfcObject;

                    /*
                     * XPD-7503: Saket: Before fetching the service process
                     * config from the process, we need to check whether the
                     * service process implements an interface or not. Because
                     * if it does, then we'd need to fetch the service process
                     * config from the service process interface rather than
                     * from the process (as the process would itself had
                     * inherited the deployment targets etc from the service
                     * process interface).
                     */
                    serviceProcessConfiguration =
                            getServiceProcessConfig(subProc);

                } else if (subProcOrIntfcObject instanceof ProcessInterface) {

                    ProcessInterface processInterface =
                            (ProcessInterface) subProcOrIntfcObject;

                    serviceProcessConfiguration =
                            processInterface.getServiceProcessConfiguration();
                }

                if (serviceProcessConfiguration != null) {

                    /*
                     * If pageflow run-time is already selected, then do not
                     * disturb that configuration. (This happens when a sub proc
                     * task in a business process is invoking a service process
                     * that has pageflow run-time already selected).
                     */
                    if (serviceProcessConfiguration.isDeployToPageflowRuntime()) {

                        deployToPageflowRuntime =
                                serviceProcessConfiguration
                                        .isDeployToPageflowRuntime();
                    } else {

                        deployToPageflowRuntime =
                                shouldDeployToPageflowRuntime();
                    }

                    /*
                     * If process run-time is already selected, then do not
                     * disturb that configuration. (This happens when a sub proc
                     * task in a pageflow process is invoking a service process
                     * that has process run-time already selected).
                     */
                    if (serviceProcessConfiguration.isDeployToProcessRuntime()) {

                        deployToProcessRuntime =
                                serviceProcessConfiguration
                                        .isDeployToProcessRuntime();
                    } else {

                        deployToProcessRuntime = shouldDeployToProcessRuntime();
                    }
                }
            }

        }

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ServiceProcessDeploymentTargetSection_setServiceProcessConfiguration_command);

        cmd.append(SetCommand
                .create(editingDomain,
                        serviceProcessConfiguration,
                        XpdExtensionPackage.eINSTANCE
                                .getServiceProcessConfiguration_DeployToProcessRuntime(),
                        deployToProcessRuntime));

        cmd.append(SetCommand
                .create(editingDomain,
                        serviceProcessConfiguration,
                        XpdExtensionPackage.eINSTANCE
                                .getServiceProcessConfiguration_DeployToPageflowRuntime(),
                        deployToPageflowRuntime));

        return cmd;
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
     * 
     * @return <code>true</code> if the Service Process or Service Process
     *         Interface is to be deployed on to Process Run-time
     */
    protected abstract boolean shouldDeployToProcessRuntime();

    /**
     * 
     * @return <code>true</code> if the Service Process or Service Process
     *         Interface is to be deployed on to Pageflow Run-time
     */
    protected abstract boolean shouldDeployToPageflowRuntime();

    /**
     * Class to set deployment target configuration to Process Run-time
     * 
     * 
     * @author bharge
     * @since 11 Feb 2015
     */
    public static class SetDeployToProcessRuntimeResolution extends
            SetServiceProcessConfigurationResolution {

        /**
         * @see com.tibco.bx.validation.resolutions.SetServiceProcessConfigurationResolution#shouldDeployToProcessRuntime()
         * 
         * @return
         */
        @Override
        protected boolean shouldDeployToProcessRuntime() {

            return true;
        }

        /**
         * @see com.tibco.bx.validation.resolutions.SetServiceProcessConfigurationResolution#shouldDeployToPageflowRuntime()
         * 
         * @return
         */
        @Override
        protected boolean shouldDeployToPageflowRuntime() {

            return false;
        }

    }

    /**
     * Class to set deployment target configuration to Pageflow Run-time
     * 
     * 
     * @author bharge
     * @since 11 Feb 2015
     */
    public static class SetDeployToPageflowRuntimeResolution extends
            SetServiceProcessConfigurationResolution {

        /**
         * @see com.tibco.bx.validation.resolutions.SetServiceProcessConfigurationResolution#shouldDeployToProcessRuntime()
         * 
         * @return
         */
        @Override
        protected boolean shouldDeployToProcessRuntime() {

            return false;
        }

        /**
         * @see com.tibco.bx.validation.resolutions.SetServiceProcessConfigurationResolution#shouldDeployToPageflowRuntime()
         * 
         * @return
         */
        @Override
        protected boolean shouldDeployToPageflowRuntime() {

            return true;
        }

    }

    /**
     * Class to set deployment target configuration to Process and Pageflow
     * Run-time
     * 
     * 
     * @author bharge
     * @since 11 Feb 2015
     */
    public static class SetDeployToProcessAndPageflowRuntimeResolution extends
            SetServiceProcessConfigurationResolution {

        /**
         * @see com.tibco.bx.validation.resolutions.SetServiceProcessConfigurationResolution#shouldDeployToProcessRuntime()
         * 
         * @return
         */
        @Override
        protected boolean shouldDeployToProcessRuntime() {

            return true;
        }

        /**
         * @see com.tibco.bx.validation.resolutions.SetServiceProcessConfigurationResolution#shouldDeployToPageflowRuntime()
         * 
         * @return
         */
        @Override
        protected boolean shouldDeployToPageflowRuntime() {

            return true;
        }

    }
}
