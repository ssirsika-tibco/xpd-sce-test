/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceParameterTable.ParamsData;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property section for REST service UI
 * 
 * @author agondal
 * @since 28 Nov 2012
 */
public abstract class RestServiceSection extends AbstractTransactionalSection {

    private Button publishRestButton;

    private Text txtRestServiceName;

    private Text txtRestModuleName;

    private Label labelRestServiceName;

    private Label labelRestSeviceModuleName;

    private Label labelInputParams;

    private Label labelOutputParams;

    protected RestServiceParameterTable inputParamsTable;

    protected RestServiceParameterTable outputParamsTable;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            boolean isRESTActivity =
                    RestServiceUtil.isRESTfulActivity(activity);

            publishRestButton.setSelection(isRESTActivity);

            labelInputParams.setVisible(false);
            inputParamsTable.setVisible(false);
            labelOutputParams.setVisible(false);
            outputParamsTable.setVisible(false);

            if (isRESTActivity) {
                Process restService = RestServiceUtil.getRestService(activity);
                if (restService != null) {

                    txtRestServiceName.setText(
                            RestServiceUtil.getRestServiceName(activity));

                    labelInputParams.setVisible(true);

                    if (inputParamsTable != null
                            && inputParamsTable.getViewer() != null) {
                        inputParamsTable.setVisible(true);
                        inputParamsTable.getViewer().cancelEditing();

                        inputParamsTable.getViewer()
                                .setInput(getRestServiceParams(activity,
                                        ModeType.IN_LITERAL));
                    }

                    /*
                     * XPD-7256: let the extending class decide if the output
                     * param table is applicable and should be shown.
                     */
                    if (shouldShowRestOutputParamTable(activity)) {
                        labelOutputParams.setVisible(true);
                        if (outputParamsTable != null
                                && outputParamsTable.getViewer() != null) {
                            outputParamsTable.setVisible(true);
                            outputParamsTable.getViewer().cancelEditing();

                            outputParamsTable.getViewer()
                                    .setInput(getRestServiceParams(activity,
                                            ModeType.OUT_LITERAL));
                        }
                    }

                } else {

                    txtRestServiceName.setText(
                            Messages.RestServiceSection_RestServiceNotGenerated);

                }
                txtRestModuleName.setText(
                        RestServiceUtil.getRestServiceModuleName(activity));
                txtRestServiceName.setVisible(true);
                txtRestModuleName.setVisible(true);

                labelRestServiceName.setVisible(true);
                labelRestSeviceModuleName.setVisible(true);

            } else {
                txtRestServiceName.setVisible(false);
                txtRestModuleName.setVisible(false);

                labelRestServiceName.setVisible(false);
                labelRestSeviceModuleName.setVisible(false);

            }

            /*
             * XPD-4822: Saket: We have decided to disable 'REST service' option
             * for any message event activity that implements a process
             * interface message event as it was never intended to be used with
             * process-interface-message-event feature (and hence has never
             * worked).
             */
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                publishRestButton.setEnabled(false);
            } else {
                publishRestButton.setEnabled(true);
            }
        }
    }

    /**
     * Give a chance to the sub classes to decide if REST output param table is
     * applicable and should be visibile in UI.
     * 
     * @param activity
     * @return <code>true</code> if the REST output param table should be
     *         visible in UI else return <code>false</code>
     */
    protected abstract boolean shouldShowRestOutputParamTable(
            Activity activity);

    /**
     * Get rest service parameters for the given activity.
     * <p>
     * XPD-7256: Note that this method is now abstracted out because it
     * performed many specific operations/computation which this Section should
     * really not perform and should the left over to the sub-classes to do
     * those specific operations.
     * 
     * @param activity
     * @param modeType
     * 
     * @return List of <code>ParamsData</code>
     */
    protected abstract Collection<ParamsData> getRestServiceParams(
            Activity activity, ModeType modeType);

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite restComposite = toolkit.createComposite(parent, SWT.NONE);

        GridLayout gl1 = new GridLayout();
        gl1.marginBottom = 0;
        gl1.marginHeight = 0;
        gl1.marginWidth = 0;
        restComposite.setLayout(gl1);

        publishRestButton = toolkit.createButton(restComposite,
                Messages.Publish_as_REST_service_title,
                SWT.CHECK,
                "publishREST"); //$NON-NLS-1$

        GridData gridData = new GridData();
        gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        publishRestButton.setLayoutData(gridData);

        manageControl(publishRestButton);

        Composite restControlsContainer =
                toolkit.createComposite(restComposite);

        restControlsContainer
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridLayout gl2 = new GridLayout(2, false);
        gl2.marginWidth = 2;
        gl2.marginLeft = gl2.marginRight = 0;
        gl2.marginHeight = 2;

        restControlsContainer.setLayout(gl2);

        GridData gridData2 = new GridData();
        gridData2.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;

        labelRestServiceName = toolkit.createLabel(restControlsContainer,
                Messages.REST_service_name_label,
                SWT.NONE);
        labelRestServiceName.setToolTipText(Messages.REST_service_name_tooltip);
        labelRestServiceName.setLayoutData(gridData2);

        txtRestServiceName = toolkit.createText(restControlsContainer, ""); //$NON-NLS-1$
        txtRestServiceName.setEditable(false);
        txtRestServiceName
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        labelRestSeviceModuleName = toolkit.createLabel(restControlsContainer,
                Messages.REST_service_module_name_label,
                SWT.NONE);
        labelRestSeviceModuleName
                .setToolTipText(Messages.REST_service_module_name_tooltip);
        gridData2 = new GridData();
        gridData2.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        labelRestSeviceModuleName.setLayoutData(gridData2);

        txtRestModuleName = toolkit.createText(restControlsContainer, ""); //$NON-NLS-1$
        txtRestModuleName.setEditable(false);
        txtRestModuleName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // REST Service parameters container
        Composite paramsContainer = toolkit.createComposite(restComposite);

        paramsContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridLayout gl3 = new GridLayout(1, false);
        gl3.marginWidth = 2;
        gl3.marginLeft = gl3.marginRight = 0;
        gl3.marginHeight = 2;
        paramsContainer.setLayout(gl3);

        labelInputParams = toolkit.createLabel(paramsContainer,
                Messages.REST_service_input_parameters_label,
                SWT.NONE);
        labelInputParams
                .setToolTipText(Messages.REST_service_input_parameters_tooltip);
        labelInputParams.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
        gridData3.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        gridData3.heightHint = 125;

        // Input parameters table
        inputParamsTable = new RestServiceParameterTable(paramsContainer,
                toolkit, getEditingDomain());
        inputParamsTable.setLayoutData(gridData3);

        labelOutputParams = toolkit.createLabel(paramsContainer,
                Messages.REST_service_output_parameters_label,
                SWT.NONE);
        labelOutputParams.setToolTipText(
                Messages.REST_service_output_parameters_tooltip);

        labelOutputParams.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Output parameters table

        gridData3 = new GridData(GridData.FILL_HORIZONTAL);
        gridData3.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        gridData3.heightHint = 125;

        outputParamsTable = new RestServiceParameterTable(paramsContainer,
                toolkit, getEditingDomain());
        outputParamsTable.setLayoutData(gridData3);

        return restComposite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        // set the publishAsRestActivity flag accordingly for the activity
        Activity activity = (Activity) getInput();

        EditingDomain ed = getEditingDomain();

        if (publishRestButton != null && obj == publishRestButton) {
            Boolean publishRest = new Boolean(publishRestButton.getSelection());

            CompoundCommand cmd = new CompoundCommand();

            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_PublishAsRestService(),
                    publishRest));

            return cmd;
        }
        return null;
    }
}
