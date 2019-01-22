/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.ConfigToolkit;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Page used to select type of the server.
 * <p>
 * <i>Created: 29 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerTypePage extends WizardSelectionPage {

    private Text serverNameText;

    private ComboViewer serverTypeViewer;

    private boolean initialised = false;

    private final Map<String, ServerTypeConfigWizardNode> serverTypeConfigWizardNodesCache;

    private ServerTypeConfigWizardNode selectedServerTypeConfigWizardNode;

    private ServerType initialServerType;

    private boolean initialServerTypeReadOnly = false;

    public ServerTypePage() {
        super("ServerTypePage"); //$NON-NLS-1$
        setTitle(Messages.ServerTypePage_Page_title);
        setDescription(Messages.ServerTypePage_Page_longdesc);
        serverTypeConfigWizardNodesCache =
                new HashMap<String, ServerTypeConfigWizardNode>();
    }

    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(composite, getTitle());

        Group serverTypeGroup = new Group(composite, SWT.NULL);
        serverTypeGroup
                .setText(Messages.ServerTypePage_ServerParametersGroup_label);
        GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
        serverTypeGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        serverTypeGroup.setLayout(paramGroupLayout);

        final AddServerWizard addServerWizard = (AddServerWizard) getWizard();
        final ConfigToolkit toolkit = ConfigToolkit.INSTANCE;
        toolkit
                .createLabel(serverTypeGroup,
                        Messages.ServerTypePage_Name_label);
        serverNameText = toolkit.createText(serverTypeGroup, ""); //$NON-NLS-1$
        ((GridData) serverNameText.getLayoutData()).horizontalSpan = 2;
        serverNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage(true));
            }
        });

        /* server type */
        toolkit.createLabel(serverTypeGroup,
                Messages.ServerTypePage_Runtime_label);
        Combo serverTypeCombo = toolkit.createCombo(serverTypeGroup);
        ((GridData) serverTypeCombo.getLayoutData()).horizontalSpan = 2;
        serverTypeViewer = new ComboViewer(serverTypeCombo);
        serverTypeViewer.setContentProvider(new ArrayContentProvider());
        serverTypeViewer.setLabelProvider(new AdapterFactoryLabelProvider(
                getServerManager().getAdapterFactory()));
        serverTypeViewer.addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof ServerType) {
                    ServerType serverType = (ServerType) element;
                    return serverType.isValid();
                }
                return false;
            }

        });
        serverTypeViewer.setInput(getServerManager().getServerContainer()
                .getServerTypes());
        serverTypeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        Object object =
                                ((IStructuredSelection) event.getSelection())
                                        .getFirstElement();
                        if (object instanceof ServerType) {
                            ServerType sType = (ServerType) object;
                            RuntimeType supportedRuntimeType =
                                    sType.getRuntimeType();
                            addServerWizard.getRuntimeClientPage()
                                    .setRuntimeType(supportedRuntimeType);
                            addServerWizard.getServerParametersPage()
                                    .setServerType(sType);
                            setPageComplete(validatePage(true));

                            selectedServerTypeConfigWizardNode =
                                    serverTypeConfigWizardNodesCache.get(sType
                                            .getId());
                            if (selectedServerTypeConfigWizardNode == null) {
                                selectedServerTypeConfigWizardNode =
                                        new ServerTypeConfigWizardNode(
                                                ServerTypePage.this.getWizard(),
                                                sType);
                                serverTypeConfigWizardNodesCache.put(sType
                                        .getId(),
                                        selectedServerTypeConfigWizardNode);
                            }
                            setSelectedNode(selectedServerTypeConfigWizardNode);
                        }
                    }
                });

        setPageComplete(false);
        setControl(composite);
    }

    @Override
    public void setSelectedNode(IWizardNode node) {
        super.setSelectedNode(node);
    }

    /**
     * @return
     */
    public ServerTypeConfigWizardNode getServerTypeWizardNode() {
        return selectedServerTypeConfigWizardNode;
    }

    /* package */void initializePageDefaults() {
        // set default server name
        serverNameText
                .setText(getServerManager()
                        .getServerContainer()
                        .getDefaultNewServerName(Messages.ServerTypePage_NewServerDefaultPrefix));

        // set default server type
        ServerType defaultServerType =
                getServerManager()
                        .getServerContainer()
                        .getServerTypeById(ServerManager.DEFAULT_SERVER_TYPE_ID);
        if (defaultServerType != null) {
            serverTypeViewer.setSelection(new StructuredSelection(
                    defaultServerType));
        }
        if (initialServerType != null) {
            serverTypeViewer.setSelection(new StructuredSelection(
                    initialServerType));
        }
        serverTypeViewer.getControl().setEnabled(!initialServerTypeReadOnly);
        serverNameText.setFocus();
        serverNameText.selectAll();
        setPageComplete(validatePage(false));
    }

    private boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);
        if (!initialised)
            return true;
        // errors
        if (getServerName().length() == 0) {
            if (showMessages) {
                setErrorMessage(Messages.ServerTypePage_EmptyName_message);
            }
            serverNameText.setFocus();
            return false;
        }
        if (!getServerManager().getServerContainer()
                .isValidNewServerName(serverNameText.getText().trim())) {
            if (showMessages) {
                setErrorMessage(Messages.ServerTypePage_NonUniqueName_message);
            }
            serverNameText.setFocus();
            return false;
        }
        if (getServerType() == null) {
            if (showMessages) {
                setErrorMessage(Messages.ServerTypePage_ServerTypeNotSelected_message);
            }
            if (!serverNameText.isFocusControl()) {
                serverTypeViewer.getCombo().setFocus();
            }
            return false;
        }
        // warnings

        // no warnigns and errors
        setMessage(null);
        setErrorMessage(null);

        return true;
    }

    /**
     * @return
     */
    private ServerManager getServerManager() {
        return ((AddServerWizard) getWizard()).getServerManager();
    }

    public String getServerName() {
        return serverNameText.getText().trim();
    }

    public ServerType getServerType() {
        return (ServerType) ((IStructuredSelection) serverTypeViewer
                .getSelection()).getFirstElement();
    }

    public RuntimeType getRuntimeType() {
        ServerType serverType = getServerType();
        if (serverType != null) {
            return serverType.getRuntimeType();
        }
        return null;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible == true && !initialised) {
            initializePageDefaults();
            initialised = true;
        }
        super.setVisible(visible);
    }

    // @Override
    // public boolean canFlipToNextPage() {
    // // If a page exists after current then allow next
    // IWizardPage page = getWizard().getNextPage(this);
    // if (page != null) {
    // return true;
    // }
    // return false;
    // }
    //
    // @Override
    // public IWizardPage getNextPage() {
    // if (selectedServerTypeConfigWizardNode == null) {
    // if (getRuntimeType() == null) {
    // return ((AddServerWizard) getWizard())
    // .getServerParametersPage();
    // }
    // }
    // return super.getNextPage();
    // }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

    /* package */void setInitialServerType(ServerType serverType,
            boolean readOnly) {
        initialServerType = serverType;
        initialServerTypeReadOnly = readOnly;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canFlipToNextPage() {
        return isPageComplete() && super.canFlipToNextPage();
    }
}
