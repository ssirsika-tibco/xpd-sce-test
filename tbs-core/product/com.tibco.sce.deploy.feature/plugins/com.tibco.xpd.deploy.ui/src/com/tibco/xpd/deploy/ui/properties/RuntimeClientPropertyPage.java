/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.properties;

import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Properties page for Client Runtime of the selected server.
 * <p>
 * <i>Created: 7 December 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RuntimeClientPropertyPage extends PropertyPage {

    private static final String PARAMETER_INFO_DATA = "ParameterInfo"; //$NON-NLS-1$

    private static final String NAME_FLAG = "name"; //$NON-NLS-1$

    private static final String RUNTIME_DATA = "RuntimeData"; //$NON-NLS-1$

    private Server selectedServer;

    private Group parametersParent;

    private ComboViewer runtimeViewer;

    /**
     * Constructor for SamplePropertyPage.
     */
    public RuntimeClientPropertyPage() {
        super();
    }

    /**
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        selectedServer = (Server) (getElement()).getAdapter(Server.class);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        final ServerManager serverManager = DeployUIActivator
                .getServerManager();

        Group serverTypeGroup = new Group(composite, SWT.NULL);
        serverTypeGroup
                .setText(Messages.RuntimeClientPropertyPage_ClientRuntimesGroup_label);
        GridData serverGroupGridData = new GridData(GridData.FILL_HORIZONTAL);
        serverTypeGroup.setLayoutData(serverGroupGridData);

        GridLayout serverTypeGroupLayout = new GridLayout();
        serverTypeGroupLayout.numColumns = 3;
        serverTypeGroup.setLayout(serverTypeGroupLayout);

        createLabel(serverTypeGroup,
                Messages.RuntimeClientPropertyPage_RuntimeClient_label);
        Combo runtimeTypeCombo = createCombo(serverTypeGroup);
        runtimeViewer = new ComboViewer(runtimeTypeCombo);
        runtimeViewer.setContentProvider(new ArrayContentProvider());
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
                        }
                    }
                });

        runtimeViewer.addFilter(new RuntimeTypeFilter());
        runtimeViewer
                .setInput(serverManager.getServerContainer().getRuntimes());

        parametersParent = new Group(composite, SWT.NULL);
        parametersParent
                .setText(Messages.RuntimeClientPropertyPage_RuntimeClientParametersGroup_Label);
        GridData parametersGroupGridData = new GridData(GridData.FILL_BOTH);
        parametersParent.setLayoutData(parametersGroupGridData);

        createParametersControls();
        selectRuntime(selectedServer.getRuntime());

        return composite;
    }

    private void selectRuntime(Runtime runtime) {
        runtimeViewer.refresh();
        runtimeViewer.setSelection(new StructuredSelection(runtime), true);
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
                    Messages.RuntimeClientPropertyPage_Name_label);
            control = createText(parametersParent, ""); //$NON-NLS-1$
            control.setData(RUNTIME_DATA, NAME_FLAG);
            control.setEnabled(false);
        }
        RuntimeType runtimeType = selectedServer.getRuntime().getRuntimeType();
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
                throw new IllegalStateException("Incorrect configuration type."); //$NON-NLS-1$
            }
            control.setData(PARAMETER_INFO_DATA, paramInfo);
            control.setEnabled(false);
        }

        parametersParent.layout();
        validatePage(true, false);
        setMessage(null);
        setErrorMessage(null);
    }

    private void fillRuntimeClientControls(
            com.tibco.xpd.deploy.Runtime runtime, boolean enableEdit) {
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

    private boolean validatePage(boolean setFocus, boolean showMessage) {
        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setValid(true);
        return true;
    }

    /*
     * (non-Javadoc) Populate the page with the default values
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        selectRuntime(selectedServer.getRuntime());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        IStructuredSelection selection = (IStructuredSelection) runtimeViewer
                .getSelection();
        if (!selection.isEmpty() && selection != null) {
            Runtime runtime = (Runtime) selection.getFirstElement();
            if (runtime != selectedServer.getRuntime()) {
                selectedServer.setRuntime(runtime);
            }
        }
        return true;
    }

    protected Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        return label;
    }

    protected Combo createCombo(Composite parent) {
        Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
        combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return combo;
    }

    protected Text createText(Composite parent, String defaultText) {
        Text textFeld = new Text(parent, SWT.SINGLE | SWT.BORDER);
        textFeld.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textFeld.setText(defaultText);
        return textFeld;
    }

    protected Button createCheckbox(Composite parent, boolean defaultValue) {
        Button button = new Button(parent, SWT.CHECK);
        button.setSelection(defaultValue);
        return button;
    }

    private class RuntimeTypeFilter extends ViewerFilter {
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            RuntimeType runtimeType = selectedServer.getRuntime()
                    .getRuntimeType();
            if (runtimeType != null) {
                if (element instanceof Runtime
                        && runtimeType
                                .equals(((com.tibco.xpd.deploy.Runtime) element)
                                        .getRuntimeType())) {
                    return true;
                }
            }
            return false;
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

}