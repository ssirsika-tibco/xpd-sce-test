/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.process.ServiceProcessPropertySection;
import com.tibco.xpd.processinterface.properties.ServiceProcessInterfacePropertySection;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ServiceProcessDeploymentTargetSection provides deployment configuration
 * controls for a Service Process. A Service Process can be configured to be
 * deployable to process run-time and/or pageflow-runtime. This section is used
 * from {@link ServiceProcessPropertySection},
 * {@link ServiceProcessInterfacePropertySection} and
 * {@link ProcessInterfaceEObjectSection}
 * 
 * @author bharge
 * @since 21 Jan 2015
 */
public class ServiceProcessDeploymentTargetSection extends
        AbstractTransactionalSection {

    private Button deployToProcessRuntime;

    private Button deployToPageflowRuntime;

    private String deploymentTargetDescText;

    private Composite deployTargetsContainer;

    public ServiceProcessDeploymentTargetSection(String descText) {

        this.deploymentTargetDescText = descText;
    }

    @Override
    protected EObject resollveInput(Object object) {

        if (object instanceof EditPart) {

            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        /*
         * If a Service Process implements a Service Process Interface, then the
         * deployment target configuration must be inherited from the
         * implementing process interface. So we disable the buttons
         */
        if (getInput() instanceof Process) {

            Process process = (Process) getInput();
            ProcessInterface implementedProcessInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);
            if (null != implementedProcessInterface) {

                if (Xpdl2ModelUtil
                        .isServiceProcessInterface(implementedProcessInterface)) {

                    ServiceProcessConfiguration implementedServiceProcessConfig =
                            implementedProcessInterface
                                    .getServiceProcessConfiguration();

                    if (null != implementedServiceProcessConfig) {

                        deployToPageflowRuntime
                                .setSelection(implementedServiceProcessConfig
                                        .isDeployToPageflowRuntime());
                        deployToProcessRuntime
                                .setSelection(implementedServiceProcessConfig
                                        .isDeployToProcessRuntime());
                        deployToPageflowRuntime.setEnabled(false);
                        deployToProcessRuntime.setEnabled(false);
                        return;
                    }
                } else {

                    /*
                     * Enable them back if the implementing interface is not a
                     * Service Process Interface
                     */
                    deployToPageflowRuntime.setEnabled(true);
                    deployToProcessRuntime.setEnabled(true);
                }
            } else {

                /* Enable them back if it is not implementing any interface */
                deployToPageflowRuntime.setEnabled(true);
                deployToProcessRuntime.setEnabled(true);
            }

        }

        /* Do the normal stuff */
        ServiceProcessConfiguration serviceProcessConfig = null;
        if (getInput() instanceof ProcessInterface) {

            ProcessInterface processInterface = (ProcessInterface) getInput();
            serviceProcessConfig =
                    processInterface.getServiceProcessConfiguration();
        } else if (getInput() instanceof Process) {

            Process process = (Process) getInput();
            serviceProcessConfig =
                    (ServiceProcessConfiguration) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration());
        }
        if (null != serviceProcessConfig) {

            if (null != deployToProcessRuntime
                    && null != deployToPageflowRuntime) {

                Boolean isDeployedToPageflowRuntime =
                        serviceProcessConfig.isDeployToPageflowRuntime();

                Boolean isDeployedToProcessRuntime =
                        serviceProcessConfig.isDeployToProcessRuntime();

                if (null != isDeployedToPageflowRuntime
                        && isDeployedToPageflowRuntime.booleanValue() == true) {

                    deployToPageflowRuntime.setSelection(true);
                } else {

                    deployToPageflowRuntime.setSelection(false);
                }
                if (null != isDeployedToProcessRuntime
                        && isDeployedToProcessRuntime.booleanValue() == true) {

                    deployToProcessRuntime.setSelection(true);
                } else {

                    deployToProcessRuntime.setSelection(false);
                }
            }
        } else {

            deployToProcessRuntime.setSelection(false);
            deployToPageflowRuntime.setSelection(false);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        GridLayout gl;

        deployTargetsContainer = toolkit.createComposite(parent);

        final Composite restrictedSizeCOntainer =
                toolkit.createComposite(deployTargetsContainer);
        gl = new GridLayout(1, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        restrictedSizeCOntainer.setLayout(gl);

        final Label wrappedDescriptionControl =
                toolkit.createLabel(restrictedSizeCOntainer,
                        deploymentTargetDescText,
                        SWT.WRAP);

        GridData gd =
                new GridData(GridData.FILL_HORIZONTAL
                        | GridData.GRAB_HORIZONTAL);
        wrappedDescriptionControl.setLayoutData(gd);

        final Composite deployTargets =
                toolkit.createComposite(restrictedSizeCOntainer);

        deployTargets.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

        gl = new GridLayout(2, false);
        gl.marginTop = gl.marginHeight;
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        deployTargets.setLayout(gl);

        deployToProcessRuntime =
                toolkit.createButton(deployTargets,
                        Messages.ServiceProcessDeploymentTargetSection_deployToProcessRuntime_label,
                        SWT.CHECK,
                        "deployToProcessRuntime_checkbox"); //$NON-NLS-1$
        deployToProcessRuntime
                .setToolTipText(Messages.ServiceProcessDeploymentTargetSection_deployToProcessRuntime_tooltiptext);
        deployToProcessRuntime.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                false, false, 2, 1));
        manageControl(deployToProcessRuntime);

        deployToPageflowRuntime =
                toolkit.createButton(deployTargets,
                        Messages.ServiceProcessDeploymentTargetSection_deployToPageflowRuntime_label,
                        SWT.CHECK,
                        "deployToPageflowRuntime_checkbox"); //$NON-NLS-1$
        deployToPageflowRuntime.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                false, false, 2, 1));
        deployToPageflowRuntime
                .setToolTipText(Messages.ServiceProcessDeploymentTargetSection_deployToPageflowRuntime_tooltiptext);
        manageControl(deployToPageflowRuntime);

        /*
         * Simple fill layout that once we have a definitive width to work on we
         * use the wrapped description label to calculate our required size
         * (because we're in a scrolled container any wrapped control needs to
         * have it's width constricted some how)
         */
        deployTargetsContainer.setLayout(new Layout() {

            Point cachedSz = new Point(SWT.DEFAULT, 20);

            @Override
            protected Point computeSize(Composite composite, int wHint,
                    int hHint, boolean flushCache) {

                if (flushCache) {

                    cachedSz.x = SWT.DEFAULT;
                }

                if (wHint > 0) {

                    Point textNominalSz =
                            wrappedDescriptionControl.computeSize(wHint,
                                    SWT.DEFAULT,
                                    true);
                    Point btnsNominalSz =
                            deployTargets.computeSize(wHint, SWT.DEFAULT, true);

                    cachedSz =
                            new Point(wHint, textNominalSz.y + btnsNominalSz.y
                                    + 15/* margins */);
                }
                return cachedSz;
            }

            @Override
            protected void layout(Composite composite, boolean flushCache) {

                restrictedSizeCOntainer.setBounds(composite.getClientArea());
            }
        });

        return deployTargetsContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        EditingDomain ed = getEditingDomain();
        ServiceProcessConfiguration serviceProcessConfig = null;
        if (getInput() instanceof Process) {

            Process serviceProcess = (Process) getInput();
            serviceProcessConfig =
                    (ServiceProcessConfiguration) Xpdl2ModelUtil
                            .getOtherElement(serviceProcess,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration());
            /*
             * ServiceProcessConfiguration can be null on a Service Process when
             * it implements a Service Process Interface
             */
            if (null == serviceProcessConfig) {

                return getCreateServiceProcessConfigurationCmd(ed,
                        serviceProcess);
            }

        } else if (getInput() instanceof ProcessInterface) {

            ProcessInterface serviceProcessInterface =
                    (ProcessInterface) getInput();
            serviceProcessConfig =
                    serviceProcessInterface.getServiceProcessConfiguration();
        }
        if (null != serviceProcessConfig) {

            if (obj == deployToProcessRuntime
                    && !deployToProcessRuntime.isDisposed()) {

                return getSetDeployToProcessRuntimeCommand(ed,
                        serviceProcessConfig,
                        deployToProcessRuntime.getSelection());
            }
            if (obj == deployToPageflowRuntime
                    && !deployToPageflowRuntime.isDisposed()) {

                return getSetDeployToPageflowRuntimeCommand(ed,
                        serviceProcessConfig,
                        deployToPageflowRuntime.getSelection());
            }
        }

        return null;
    }

    /**
     * @param ed
     * @param serviceProcess
     * @return command that sets Service Process Deployment Target configuration
     *         on a given Service Process
     */
    private Command getCreateServiceProcessConfigurationCmd(EditingDomain ed,
            Process serviceProcess) {

        ServiceProcessConfiguration serviceProcessConfiguration =
                XpdExtensionFactory.eINSTANCE
                        .createServiceProcessConfiguration();
        serviceProcessConfiguration
                .setDeployToPageflowRuntime(deployToPageflowRuntime
                        .getSelection());
        serviceProcessConfiguration
                .setDeployToProcessRuntime(deployToProcessRuntime
                        .getSelection());

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ServiceProcessDeploymentTargetSection_setServiceProcessConfiguration_command);

        cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                serviceProcess,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ServiceProcessConfiguration(),
                serviceProcessConfiguration));
        return cmd;
    }

    /**
     * Command to set or un-set the deployment target to pageflow run-time on a
     * given Service Process Configuration
     * 
     * @param ed
     * @param serviceProcessConfig
     * @param isPageflowRuntimeSelected
     * @return {@link Command} that sets or unsets the pageflow run-time
     *         deployment target
     */
    private Command getSetDeployToPageflowRuntimeCommand(EditingDomain ed,
            ServiceProcessConfiguration serviceProcessConfig,
            boolean isPageflowRuntimeSelected) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ServiceProcessDeploymentTargetSection_setDeployToPageflowRuntime_command);

        cmd.append(SetCommand.create(ed,
                serviceProcessConfig,
                XpdExtensionPackage.eINSTANCE
                        .getServiceProcessConfiguration_DeployToPageflowRuntime(),
                isPageflowRuntimeSelected));
        return cmd;
    }

    /**
     * Command to set or un-set the deployment target to process run-time on a
     * given Service Process Configuration
     * 
     * @param ed
     * @param serviceProcessConfig
     * @param isProcessRuntimeSelected
     * @return {@link Command} that sets or unsets the process run-time
     *         deployment target
     */
    private Command getSetDeployToProcessRuntimeCommand(EditingDomain ed,
            ServiceProcessConfiguration serviceProcessConfig,
            boolean isProcessRuntimeSelected) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ServiceProcessDeploymentTargetSection_setDeployToProcessRuntime_command);

        cmd.append(SetCommand.create(ed,
                serviceProcessConfig,
                XpdExtensionPackage.eINSTANCE
                        .getServiceProcessConfiguration_DeployToProcessRuntime(),
                isProcessRuntimeSelected));
        return cmd;
    }

}
