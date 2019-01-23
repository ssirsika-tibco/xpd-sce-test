/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addruntime;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.RuntimeConfig;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.ui.ConfigToolkit;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.components.FileControl;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Page for editing runtime parameters.
 * <p>
 * <i>Created: 29 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RuntimeParametersPage extends BaseAddRuntimeWizardPage implements
        Listener {

    private static final String NAME_FLAG = "name"; //$NON-NLS-1$

    private static final String RUNTIME_DATA = "RuntimeData"; //$NON-NLS-1$

    private static final String PARAMETER_INFO_DATA = "ParameterInfo"; //$NON-NLS-1$

    private Group parametersParent;

    private RuntimeType runtimeType;

    private Runtime newRuntime;

    private Runtime editedRuntime;

    /**
     * Creates page instance.
     */
    public RuntimeParametersPage() {
        super("RuntimeClientPage"); //$NON-NLS-1$

        setTitle(Messages.RuntimeParametersPage_Page_title);
        setDescription(Messages.RuntimeParametersPage_Page_longdesc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        parametersParent = new Group(composite, SWT.NULL);
        parametersParent.setText(Messages.RuntimeParametersPage_Group_label);
        GridData parametersGroupGridData = new GridData(GridData.FILL_BOTH);
        parametersParent.setLayoutData(parametersGroupGridData);

        setPageComplete(false);
        setControl(composite);
    }

    @SuppressWarnings("unchecked")
    private static Runtime createDefaultRuntime(RuntimeType runtimeType) {
        Runtime runtime = DeployFactory.eINSTANCE.createRuntime();
        if (runtimeType != null) {
            RuntimeConfig runtimeConfig = DeployFactory.eINSTANCE
                    .createRuntimeConfig();
            List<ConfigParameterInfo> runtimeParameterInfos = runtimeType
                    .getRuntimeParameterInfos();
            for (ConfigParameterInfo paramInfo : runtimeParameterInfos) {
                ConfigParameter parameter = DeployFactory.eINSTANCE
                        .createConfigParameter();
                parameter.setKey(paramInfo.getKey());
                parameter.setValue(paramInfo.getDefaultValue());
                parameter.setConfigParameterInfo(paramInfo);
                runtimeConfig.getConfigParameters().add(parameter);
            }
            runtime.setRuntimeConfig(runtimeConfig);
            runtime.setRuntimeType(runtimeType);
            String namePrefix = runtimeType.getName();
            runtime.setName(DeployUIActivator.getServerManager()
                    .getServerContainer().getDefaultNewRuntimeName(namePrefix));
        }
        return runtime;

    }

    private void createParametersControls() {
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        parametersParent.setLayout(paramGroupLayout);

        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            children[i].dispose();
        }
        { // Runtime name
            Control control = null;
            createLabel(parametersParent,
                    Messages.RuntimeParametersPage_Name_label);
            control = createText(parametersParent, ""); //$NON-NLS-1$
            control.setData(RUNTIME_DATA, NAME_FLAG);
            control.addListener(SWT.Modify, this);
            control.setEnabled(false);
        }
        for (Iterator<?> iter = runtimeType.getRuntimeParameterInfos()
                .iterator(); iter.hasNext();) {
            ConfigParameterInfo paramInfo = (ConfigParameterInfo) iter.next();
            ConfigParameterType paramType = paramInfo.getParameterType();
            Control control = null;
            switch (paramType.getValue()) {
            case ConfigParameterType.STRING: {
                createLabel(parametersParent, paramInfo.getLabel());
                control = createText(parametersParent, ""); //$NON-NLS-1$
                break;
            }
            case ConfigParameterType.PASSWORD: {
                createLabel(parametersParent, paramInfo.getLabel());
                control = createText(parametersParent, ""); //$NON-NLS-1$
                ((Text) control).setEchoChar('*');
                break;
            }
            case ConfigParameterType.INTEGER: {
                createLabel(parametersParent, paramInfo.getLabel());
                control = createText(parametersParent, ""); //$NON-NLS-1$
                break;
            }
            case ConfigParameterType.BOOLEAN: {
                createLabel(parametersParent, paramInfo.getLabel());
                control = createCheckbox(parametersParent, Boolean
                        .parseBoolean(paramInfo.getDefaultValue()));
                break;
            }
            case ConfigParameterType.FILE: {
                createLabel(parametersParent, paramInfo.getLabel());
                control = ConfigToolkit.INSTANCE.createFileControl(
                        parametersParent, paramInfo.getFacetsMap());
                GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(
                        true, false).applyTo(control);
                ((FileControl) control).addTextListener(SWT.Modify, this);
                break;
            }
            default:
                throw new IllegalStateException(
                        "Incorrect runtime config type."); //$NON-NLS-1$
            }
            control.setData(PARAMETER_INFO_DATA, paramInfo);
            control.addListener(SWT.Modify, this);
            control.setEnabled(false);
        }

        parametersParent.layout();
        validatePage(true, false);
        setMessage(null);
        setErrorMessage(null);
    }

    private boolean validatePage(boolean setFocus, boolean showMessage) {
        setMessage(null);
        setErrorMessage(null);

        if (runtimeType != null && parametersParent != null) {
            Control[] children = parametersParent.getChildren();
            for (int i = 0; i < children.length; i++) {
                Control control = children[i];
                if (control.getData(PARAMETER_INFO_DATA) != null) {
                    ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                            .getData(PARAMETER_INFO_DATA);
                    // errors
                    if (control instanceof Text && paramInfo.isRequired()
                            && ((Text) control).getText().length() == 0) {
                        String message = String
                                .format(
                                        Messages.RuntimeParametersPage_FieldEmpty_message,
                                        paramInfo.getName());
                        if (showMessage) {
                            setErrorMessage(message);
                        }
                        setPageComplete(false);
                        if (setFocus) {
                            control.forceFocus();
                        }
                        return false;
                    }
                    if (control instanceof Text
                            && paramInfo.getParameterType().equals(
                                    ConfigParameterType.INTEGER_LITERAL)
                            && !((Text) control).getText().matches("\\d*")) { //$NON-NLS-1$
                        String message = String
                                .format(
                                        Messages.RuntimeParametersPage_NotIntegerField_message,
                                        paramInfo.getName());
                        if (showMessage) {
                            setErrorMessage(message);
                        }
                        setPageComplete(false);
                        if (setFocus) {
                            control.forceFocus();
                        }
                        return false;
                    }
                    if (control instanceof FileControl
                            && paramInfo.getParameterType().equals(
                                    ConfigParameterType.FILE_LITERAL)) {
                        FileControl fileControl = (FileControl) control;
                        IStatus status = fileControl.validate();
                        if (status.getSeverity() != IStatus.OK) {

                            // ignore empty path error if not required
                            if (status.getCode() != FileControl.EMPTY_PATH_ERROR_CODE
                                    || paramInfo.isRequired()) {
                                String message = String
                                        .format(
                                                Messages.ServerParametersPage_fileFieldValidationProblem_message,
                                                new Object[] { paramInfo
                                                        .getName() });
                                if (status.getMessage() != null
                                        && status.getMessage().trim().length() > 0) {
                                    message += ' ' + status.getMessage();
                                }
                                if (showMessage) {
                                    if (status.getSeverity() == IStatus.ERROR) {
                                        setErrorMessage(message);
                                    } else if (status.getSeverity() == IStatus.WARNING) {
                                        setMessage(message, WARNING);
                                    } else {
                                        setMessage(message);
                                    }
                                }
                                setPageComplete(false);
                                if (setFocus) {
                                    control.forceFocus();
                                }
                                return false;
                            }
                        }

                    }
                } else if (control.getData(RUNTIME_DATA) != null) {
                    String runtimeData = (String) control.getData(RUNTIME_DATA);
                    if (runtimeData.equals(NAME_FLAG)) {
                        String name = ((Text) control).getText().trim();
                        if (name.length() == 0) {
                            if (showMessage) {
                                setErrorMessage(Messages.RuntimeParametersPage_EmptyName_message);
                            }
                            if (setFocus) {
                                control.setFocus();
                            }
                            setPageComplete(false);
                            return false;
                        }
                        if (!DeployUIActivator.getServerManager()
                                .getServerContainer().isValidNewRuntimeName(
                                        name)) {
                            if (isNewRuntime()
                                    || !editedRuntime.getName().equals(name)) {
                                if (showMessage) {
                                    setErrorMessage(Messages.RuntimeParametersPage_NotUniqueName_message);
                                }
                                if (setFocus) {
                                    control.setFocus();
                                }
                                setPageComplete(false);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setPageComplete(true);
        return true;
    }

    private boolean isNewRuntime() {
        return (editedRuntime == null);
    }

    private void createParametersControls(RuntimeType runtimeType) {
        if (parametersParent != null) {
            createParametersControls();
        }
    }

    /**
     * @param rType
     *            sets new runtime type.
     */
    public void setRuntimeType(RuntimeType rType) {
        if (rType == runtimeType)
            return;
        runtimeType = rType;
        newRuntime = null;
        if (runtimeType != null) {
            createParametersControls(runtimeType);
            if (isNewRuntime()) {
                newRuntime = RuntimeParametersPage
                        .createDefaultRuntime(runtimeType);
                fillRuntimeClientControls(newRuntime, true);
                setPageComplete(validatePage(true, true));
            } else {
                fillRuntimeClientControls(editedRuntime, true);
                setPageComplete(validatePage(false, true));
            }
            validatePage(false, true);
        }
    }

    /**
     * Sets the runtime to be edited on the page.
     * 
     * @param runtime
     *            the runtime to be edited.
     */
    public void setEditedRuntime(Runtime runtime) {
        editedRuntime = runtime;
        setRuntimeType(runtime.getRuntimeType());

    }

    public void handleEvent(Event event) {
        Widget control = event.widget;
        if (control.getData(PARAMETER_INFO_DATA) instanceof ConfigParameterInfo
                || control.getData(RUNTIME_DATA) instanceof String) {
            if (event.type == SWT.Modify) {
                if (isNewRuntime()) {
                    ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                            .getData(PARAMETER_INFO_DATA);
                    if (paramInfo != null) {
                        ConfigParameter configParam = newRuntime
                                .getRuntimeConfig().getConfigParameter(
                                        paramInfo.getKey());
                        String value = null;
                        if (control instanceof Text) {
                            value = ((Text) control).getText();
                        }
                        if (control instanceof Button) {
                            value = String.valueOf(((Button) control)
                                    .getSelection());
                        }
                        configParam.setValue(value);
                        configParam.setConfigParameterInfo(paramInfo);
                    }
                    if (NAME_FLAG.equals(control.getData(RUNTIME_DATA))) {
                        String value = null;
                        if (control instanceof Text) {
                            value = ((Text) control).getText();
                        }
                        if (control instanceof Button) {
                            value = String.valueOf(((Button) control)
                                    .getSelection());
                        }
                        newRuntime.setName(value);
                    }

                }
                validatePage(false, true);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private RuntimeConfig getRuntimeConfig() {
        if (parametersParent == null)
            return null;
        DeployFactory factory = DeployFactory.eINSTANCE;
        RuntimeConfig runtimeConfig = factory.createRuntimeConfig();
        List configParams = runtimeConfig.getConfigParameters();
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO_DATA) != null) {
                ConfigParameter configParam = factory.createConfigParameter();
                ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                        .getData(PARAMETER_INFO_DATA);
                configParam.setKey(paramInfo.getKey());
                String value = null;
                if (control instanceof Text) {
                    value = ((Text) control).getText();
                }
                if (control instanceof Button) {
                    value = String.valueOf(((Button) control).getSelection());
                }

                configParam.setValue(value);
                configParam.setConfigParameterInfo(paramInfo);
                configParams.add(configParam);
            }
        }
        return runtimeConfig;
    }

    private String getRuntimeName() {
        if (parametersParent == null)
            return null;
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(RUNTIME_DATA) != null) {
                String runtimeData = (String) control.getData(RUNTIME_DATA);
                if (runtimeData.equals(NAME_FLAG)) {
                    return ((Text) control).getText();
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    private void fillRuntimeClientControls(Runtime runtime, boolean enableEdit) {
        if (parametersParent == null)
            return;
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO_DATA) != null) {
                ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                        .getData(PARAMETER_INFO_DATA);
                ConfigParameter configParameter = null;
                if (runtime != null) {
                    configParameter = runtime.getRuntimeConfig()
                            .getConfigParameter(paramInfo.getKey());
                }
                if (configParameter != null) {
                    String value = configParameter.getValue();
                    if (control instanceof Text) {
                        ((Text) control).setText(value);
                    }
                    if (control instanceof Button) {
                        ((Button) control).setSelection(Boolean
                                .parseBoolean(value));
                    }
                } else {
                    if (control instanceof Text) {
                        ((Text) control).setText(""); //$NON-NLS-1$
                    }
                    if (control instanceof Button) {
                        ((Button) control).setSelection(false);
                    }
                }
            } else if (control.getData(RUNTIME_DATA) != null) {
                String runtimeData = (String) control.getData(RUNTIME_DATA);
                if (runtimeData.equals(NAME_FLAG)) {
                    ((Text) control).setText(runtime.getName());
                }
            }
            control.setEnabled(enableEdit);
        }
    }

    /**
     * @return the new or modified runtime reference witch all changes form this
     *         page applayed.
     */
    public Runtime getRuntime() {
        if (isNewRuntime()) {
            newRuntime.setName(getRuntimeName());
            newRuntime.setRuntimeConfig(getRuntimeConfig());
            return newRuntime;
        } else {
            editedRuntime.setName(getRuntimeName());
            editedRuntime.setRuntimeConfig(getRuntimeConfig());
            return editedRuntime;
        }
    }
}
