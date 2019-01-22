/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.RuntimeConfig;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Runtime client parameters editing.
 * <p>
 * <i>Created: 29 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RuntimeClientPage extends BaseAddServerWizardPage implements
        Listener {

    /** */
    private static final String NAME_FLAG = "name"; //$NON-NLS-1$

    private static final String RUNTIME_DATA = "RuntimeData"; //$NON-NLS-1$

    private static final String PARAMETER_INFO = "ParameterInfo"; //$NON-NLS-1$

    private static final String NEW_RUNTIME_CLIENT = " New Runtime Client..."; //$NON-NLS-1$

    private Group parametersParent;

    private ComboViewer runtimeViewer;

    private RuntimeType runtimeType;

    private Runtime newRuntime;

    private final boolean newOnly;
    
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

    public RuntimeClientPage(boolean newOnly) {
        super("RuntimeClientPage"); //$NON-NLS-1$
        this.newOnly = newOnly;
        setTitle(Messages.RuntimeClientPage_Page_title);
        setDescription(Messages.RuntimeClientPage_PageDescription_longdesc);
    }

    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, getTitle());

        final ServerManager serverManager = getAddServerWizard()
                .getServerManager();

        Group serverTypeGroup = new Group(composite, SWT.NULL);
        serverTypeGroup
                .setText(Messages.RuntimeClientPage_ClientRuntimesGroup_label);
        GridData serverGroupGridData = new GridData(GridData.FILL_HORIZONTAL);
        serverTypeGroup.setLayoutData(serverGroupGridData);

        GridLayout serverTypeGroupLayout = new GridLayout();
        serverTypeGroupLayout.numColumns = 3;
        serverTypeGroup.setLayout(serverTypeGroupLayout);

        createLabel(serverTypeGroup,
                Messages.RuntimeClientPage_RuntimeClient_label);
        Combo runtimeTypeCombo = createCombo(serverTypeGroup);
        runtimeViewer = new ComboViewer(runtimeTypeCombo);
        runtimeViewer.setContentProvider(new RuntimeContentProvider());
        runtimeViewer.setLabelProvider(new RuntimeLabelProvider(serverManager
                .getAdapterFactory()));

        runtimeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        Object sel = ((IStructuredSelection) event
                                .getSelection()).getFirstElement();
                        if (sel instanceof Runtime) {
                            Runtime runtime = (Runtime) sel;
                            fillRuntimeClientControls(runtime, false);
                            setPageComplete(validatePage(false, true));
                        } else if (sel instanceof String
                                && NEW_RUNTIME_CLIENT.equals(sel)) {
                            if (newRuntime == null) {
                                newRuntime = RuntimeClientPage
                                        .createDefaultRuntime(runtimeType);
                            }
                            fillRuntimeClientControls(newRuntime, true);
                            setPageComplete(validatePage(true, true));
                        }
                    }
                });

        runtimeViewer.addFilter(new RuntimeTypeFilter());
        if (newOnly) {
            runtimeViewer.setInput(Collections.emptyList());
        } else {
            runtimeViewer.setInput(serverManager.getServerContainer()
                    .getRuntimes());
        }
        runtimeViewer.setSorter(new RuntimeSorter());

        parametersParent = new Group(composite, SWT.NULL);
        parametersParent
                .setText(Messages.RuntimeClientPage_RuntimeClientParametersGroup_label);
        GridData parametersGroupGridData = new GridData(GridData.FILL_BOTH);
        parametersParent.setLayoutData(parametersGroupGridData);

        setPageComplete(false);
        RuntimeType runtimeType = getAddServerWizard().getServerTypePage()
                .getRuntimeType();
        if (runtimeType != null) {
            createParametersControls(runtimeType);
        } else {
            setPageComplete(true);
        }
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
            createLabel(parametersParent, Messages.RuntimeClientPage_Name_label);
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
            default:
                throw new IllegalStateException(
                        "Incorrect runtime config type."); //$NON-NLS-1$
            }
            control.setData(PARAMETER_INFO, paramInfo);
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

        if (runtimeType != null && isNewRuntime()) {
            Control[] children = parametersParent.getChildren();
            for (int i = 0; i < children.length; i++) {
                Control control = children[i];
                if (control.getData(PARAMETER_INFO) != null) {
                    ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                            .getData(PARAMETER_INFO);
                    // errors
                    if (control instanceof Text && paramInfo.isRequired()
                            && ((Text) control).getText().length() == 0) {
                        String message = String.format(
                                Messages.RuntimeClientPage_EmptyField_message,
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
                                        Messages.RuntimeClientPage_NotIntegerField_message,
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

                } else if (control.getData(RUNTIME_DATA) != null) {
                    String runtimeData = (String) control.getData(RUNTIME_DATA);
                    if (runtimeData.equals(NAME_FLAG)) {
                        String name = ((Text) control).getText();
                        if (name.length() == 0) {
                            if (showMessage) {
                                setErrorMessage(Messages.RuntimeClientPage_EmptyName_message);
                            }
                            if (setFocus) {
                                control.setFocus();
                            }
                            setPageComplete(false);
                            return false;
                        }
                        if (!getAddServerWizard().getServerManager()
                                .getServerContainer().isValidNewRuntimeName(
                                        name.trim())) {
                            if (showMessage) {
                                setErrorMessage(Messages.RuntimeClientPage_NotUniqueName_message);
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
        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setPageComplete(true);
        return true;
    }

    private boolean isNewRuntime() {
        if (runtimeViewer != null) {
            Object selection = ((IStructuredSelection) runtimeViewer
                    .getSelection()).getFirstElement();
            if (selection instanceof Runtime) {
                return false;
            } else if (selection instanceof String
                    && NEW_RUNTIME_CLIENT.equals(selection)) {
                return true;
            }
        }
        return false;
    }

    private void createParametersControls(RuntimeType runtimeType) {
        if (parametersParent != null) {
            createParametersControls();
        }
    }

    public void setRuntimeType(RuntimeType rType) {
        runtimeType = rType;
        newRuntime = null;
        if (runtimeViewer != null) {
            if (runtimeType != null) {
                createParametersControls(runtimeType);
            }
            runtimeViewer.refresh();
            Object firstElement = runtimeViewer.getElementAt(0);
            if (firstElement != null) {
                runtimeViewer.setSelection(
                        new StructuredSelection(firstElement), true);
            }
        } else {
            validatePage(false, true);
        }
    }

    public void handleEvent(Event event) {
        Widget control = event.widget;
        if (control.getData(PARAMETER_INFO) instanceof ConfigParameterInfo
                || control.getData(RUNTIME_DATA) instanceof String) {
            if (event.type == SWT.Modify) {
                if (isNewRuntime()) {
                    ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                            .getData(PARAMETER_INFO);
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
        DeployFactory factory = DeployFactory.eINSTANCE;
        RuntimeConfig runtimeConfig = factory.createRuntimeConfig();
        List configParams = runtimeConfig.getConfigParameters();
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null) {
                ConfigParameter configParam = factory.createConfigParameter();
                ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                        .getData(PARAMETER_INFO);
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
        Control[] children = parametersParent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            if (control.getData(PARAMETER_INFO) != null) {
                ConfigParameterInfo paramInfo = (ConfigParameterInfo) control
                        .getData(PARAMETER_INFO);
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

    public Runtime getRuntime() {
        Object selection = ((IStructuredSelection) runtimeViewer.getSelection())
                .getFirstElement();
        if (selection instanceof Runtime) {
            return (Runtime) selection;
        } else if (selection instanceof String
                && NEW_RUNTIME_CLIENT.equals(selection)) {
            newRuntime.setName(getRuntimeName());
            newRuntime.setRuntimeConfig(getRuntimeConfig());
            return newRuntime;
        }
        return null;
    }

    private class RuntimeTypeFilter extends ViewerFilter {
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (runtimeType != null) {
                if (element instanceof Runtime
                        && runtimeType.equals(((Runtime) element)
                                .getRuntimeType()) || element instanceof String
                        && NEW_RUNTIME_CLIENT.equals(element)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class RuntimeContentProvider extends ArrayContentProvider {
        /**
         * Returns the elements in the input, which must be either an array or a
         * <code>Collection</code>.
         */
        @SuppressWarnings("unchecked")
        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Object[]) {
                Object[] elements = (Object[]) inputElement;
                Object[] retElements = new Object[elements.length + 1];
                System.arraycopy(elements, 0, retElements, 0, elements.length);
                retElements[retElements.length - 1] = NEW_RUNTIME_CLIENT;
                return retElements;
            }
            if (inputElement instanceof Collection) {
                ArrayList<Object> arrayList = new ArrayList<Object>(
                        (Collection) inputElement);
                (arrayList).add(NEW_RUNTIME_CLIENT);
                return (arrayList).toArray();
            }
            return new Object[0];
        }
    }

    private class RuntimeLabelProvider extends AdapterFactoryLabelProvider {

        public RuntimeLabelProvider(AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        @Override
        public String getText(Object object) {
            if (object instanceof String) {
                return (String) object;
            }
            return super.getText(object);
        }

        @Override
        public Image getImage(Object object) {
            if (object instanceof String) {
                return null;
            }
            return super.getImage(object);
        }

    }

    private class RuntimeSorter extends ViewerSorter {
        @Override
        public int compare(Viewer viewer, Object e1, Object e2) {
            if (e1 instanceof String || e2 instanceof String) {
                if (e1 instanceof String && !(e2 instanceof String)) {
                    return 1;
                } else if (e2 instanceof String && !(e1 instanceof String)) {
                    return -1;
                }
            }
            return super.compare(viewer, e1, e2);
        }

    }

}
