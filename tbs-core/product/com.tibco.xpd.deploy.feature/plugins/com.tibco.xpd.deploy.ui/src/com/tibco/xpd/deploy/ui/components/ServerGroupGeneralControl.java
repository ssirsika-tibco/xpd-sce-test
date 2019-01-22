/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.ConfigToolkit;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.deploy.ui.wizards.addserver.AddServerWizard;

/**
 * Control for managing server group general properties.
 * <p>
 * <i>Created: 4 Dec 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz, Gary Lewis
 */
abstract public class ServerGroupGeneralControl extends Composite {

    /** */
    private static final String SERVER_COL_PROPERTY = "server"; //$NON-NLS-1$

    /** */
    private static final String SERVER_TYPE_COL_PROPERTY = "serverType"; //$NON-NLS-1$

    /** Default name for new server group. */
    private static final String DEFAULT_SERVER_GROUP_NAME = Messages.ServerGroupGeneralControl_serverGroupDefaultName_shortdesc;

    /**
     * 
     * <p>
     * <i>Created: 12 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private final class ComboBoxViewerCellEditorExtension extends
            ComboBoxViewerCellEditor {
        /**
         * @param parent
         * @param style
         */
        private ComboBoxViewerCellEditorExtension(Composite parent, int style) {
            super(parent, style);
        }

        /** {@inheritDoc} */
        @Override
        public void focusLost() {
            super.focusLost();
        }
    }

    private class EmptyServerElement extends ItemProviderAdapter implements
            IItemLabelProvider {
        public EmptyServerElement(AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        /** {@inheritDoc} */
        @Override
        public String getText(Object object) {
            return ""; //$NON-NLS-1$
        }
    }

    private final EmptyServerElement emptyServerElement = new EmptyServerElement(
            getServerManager().getAdapterFactory());

    private class NewServerElement extends ItemProviderAdapter implements
            IItemLabelProvider {
        public NewServerElement(AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        /** {@inheritDoc} */
        @Override
        public String getText(Object object) {
            return Messages.ServerGroupGeneralControl_newServerGroup_action;
        }
    }

    private final NewServerElement newServerElement = new NewServerElement(
            getServerManager().getAdapterFactory());

    /**
     * 
     * <p>
     * <i>Created: 3 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private final class FilteredServerContentProvider extends
            ArrayContentProvider {
        /** {@inheritDoc} */
        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements = super.getElements(inputElement);
            Object[] newElements = new Object[elements.length + 2];
            newElements[0] = emptyServerElement;
            newElements[1] = newServerElement;
            System.arraycopy(elements, 0, newElements, 2, elements.length);
            return newElements;
        }
    }

    /**
     * 
     * <p>
     * <i>Created: 1 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    public class ServersContentProvider extends ArrayContentProvider implements
            IContentProvider {
        private ServerGroupEntry[] serverGroupEntriesArray = new ServerGroupEntry[0];

        /** {@inheritDoc} */
        @Override
        public Object[] getElements(Object inputElement) {
            return serverGroupEntriesArray;
        }

        /** {@inheritDoc} */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            if (newInput instanceof ServerGroupType) {
                ArrayList<ServerGroupEntry> serverGroupEntries = new ArrayList<ServerGroupEntry>();
                ServerGroupType serverGroupType = (ServerGroupType) newInput;
                for (ServerType st : serverGroupType.getServerTypes()) {
                    serverGroupEntries.add(new ServerGroupEntry(st, null));
                }
                serverGroupEntriesArray = serverGroupEntries
                        .toArray(new ServerGroupEntry[serverGroupEntries.size()]);
            } else if (newInput != null) {
                Assert.isLegal(false, "Invalid ServerViewer input."); //$NON-NLS-1$
            }
        }

        public ServerGroupEntry[] getGroupEntries() {
            return serverGroupEntriesArray;
        }
    }

    /**
     * 
     * <p>
     * <i>Created: 1 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private final class ServersLabelProvider extends LabelProvider implements

    ITableLabelProvider {
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof ServerGroupEntry) {
                ServerGroupEntry serverTypeEntry = (ServerGroupEntry) element;
                switch (columnIndex) {
                case 0:
                    return serverTypeEntry.getServerType().getName();
                case 1:
                    Server server = serverTypeEntry.getServer();
                    if (server != null) {
                        return server.getName();
                    }
                    return null;
                default:
                    Assert.isLegal(false, "Column index out of range."); //$NON-NLS-1$
                }
            }
            return null;
        }
    }

    private static class ServerGroupEntry {
        private final ServerType serverType;
        private Server server;

        /**
         *  
         */
        public ServerGroupEntry(ServerType serverType, Server server) {
            this.serverType = serverType;
            this.server = server;
        }

        /**
         * @return the serverType
         */
        public ServerType getServerType() {
            return serverType;
        }

        /**
         * @param server
         *            the server to set
         */
        public void setServer(Server server) {
            this.server = server;
        }

        /**
         * @return the server
         */
        public Server getServer() {
            return server;
        }
    }

    /**
     * Handles the cell editing / selection of the servers table.
     * 
     */
    private class ServersCellModifier implements ICellModifier {

        /** {@inheritDoc} */
        public boolean canModify(Object element, String property) {
            if (SERVER_COL_PROPERTY.equals(property)
                    && element instanceof ServerGroupEntry) {
                return true;
            }
            return false;
        }

        /** {@inheritDoc} */
        public Object getValue(Object element, String property) {
            if (SERVER_COL_PROPERTY.equals(property)
                    && element instanceof ServerGroupEntry) {
                Server server = ((ServerGroupEntry) element).getServer();
                if (server != null) {
                    return server;
                }
            }
            return null;
        }

        /** {@inheritDoc} */
        public void modify(Object element, String property, Object value) {
            ServerGroupEntry serverGroupEntry = null;
            if (element instanceof TableItem
                    && ((TableItem) element).getData() instanceof ServerGroupEntry) {
                serverGroupEntry = (ServerGroupEntry) ((TableItem) element)
                        .getData();
            }
            if (SERVER_COL_PROPERTY.equals(property)
                    && serverGroupEntry != null && value instanceof Server) {
                serverGroupEntry.setServer((Server) value);
                serversViewer.refresh(serverGroupEntry);
            } else if (SERVER_COL_PROPERTY.equals(property)
                    && serverGroupEntry != null
                    && value instanceof EmptyServerElement) {
                serverGroupEntry.setServer(null);
                serversViewer.refresh(serverGroupEntry);
            } else if (SERVER_COL_PROPERTY.equals(property)
                    && serverGroupEntry != null
                    && value instanceof NewServerElement) {
                ServerType serverType = serverGroupEntry.getServerType();
                AddServerWizard wizard = new AddServerWizard();
                wizard.setInitialServerType(serverType, true);
                WizardDialog dialog = new WizardDialog(PlatformUI
                        .getWorkbench().getActiveWorkbenchWindow().getShell(),
                        wizard);
                dialog.open();

                if (dialog.getReturnCode() == Dialog.OK) {
                    serversCellEditor.getViewer().refresh();
                    serverGroupEntry.setServer(wizard.getCreatedServer());
                }
                serversViewer.refresh(serverGroupEntry);
            }
            setControlComplete(validatePage(true));
        }
    }

    private final Text serverGroupNameText;

    private final ComboViewer serverGroupTypeViewer;

    private boolean initialised = false;

    private final TableViewer serversViewer;

    private boolean isControlComplete = false;

    private ServerGroup contextServerGroup;

    private final ComboBoxViewerCellEditorExtension serversCellEditor;

    public ServerGroupGeneralControl(Composite parent,
            ServerGroup contextServerGroup) {
        this(parent);
        this.contextServerGroup = contextServerGroup;
    }

    /**
     * @param parent
     * @param style
     */
    public ServerGroupGeneralControl(Composite parent) {
        super(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        this.setLayout(gridLayout);

        Group serverGroupGroup = new Group(this, SWT.NULL);
        serverGroupGroup
                .setText(Messages.ServerGroupGeneralControl_serverGroup_label);
        GridData gridData2 = new GridData(GridData.FILL_BOTH);
        serverGroupGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        serverGroupGroup.setLayout(paramGroupLayout);

        final ConfigToolkit toolkit = ConfigToolkit.INSTANCE;
        toolkit.createLabel(serverGroupGroup,
                Messages.ServerGroupGeneralControl_serverGroupName_label);
        serverGroupNameText = toolkit.createText(serverGroupGroup, ""); //$NON-NLS-1$
        ((GridData) serverGroupNameText.getLayoutData()).horizontalSpan = 2;
        serverGroupNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setControlComplete(validatePage(true));
            }
        });

        /* server group type */
        toolkit.createLabel(serverGroupGroup,
                Messages.ServerGroupGeneralControl_serverGroupType_label);
        Combo serverGroupTypeCombo = toolkit.createCombo(serverGroupGroup);
        ((GridData) serverGroupTypeCombo.getLayoutData()).horizontalSpan = 2;
        serverGroupTypeViewer = new ComboViewer(serverGroupTypeCombo);
        serverGroupTypeViewer.setContentProvider(new ArrayContentProvider());
        serverGroupTypeViewer.setLabelProvider(new AdapterFactoryLabelProvider(
                getServerManager().getAdapterFactory()));
        serverGroupTypeViewer.setInput(getServerManager().getServerContainer()
                .getServerGroupTypes());
        serverGroupTypeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        Object object = ((IStructuredSelection) event
                                .getSelection()).getFirstElement();
                        if (object instanceof ServerGroupType) {
                            ServerGroupType sGroupType = (ServerGroupType) object;
                            serversViewer.setInput(sGroupType);
                        }
                        setControlComplete(validatePage(true));
                    }
                });
        serverGroupTypeViewer.addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof ServerGroupType) {
                    ServerGroupType sGroupType = (ServerGroupType) element;
                    return sGroupType.isValid();
                }
                return false;
            }

        });

        Table serversTable = toolkit.createTable(serverGroupGroup);

        TableColumn serverTypeColumn = new TableColumn(serversTable, SWT.NONE);
        serverTypeColumn
                .setText(Messages.ServerGroupGeneralControl_serverTypeColumn_label);
        serverTypeColumn.setWidth(250);

        TableColumn serverColumn = new TableColumn(serversTable, SWT.NONE);
        serverColumn
                .setText(Messages.ServerGroupGeneralControl_serverColumn_label);
        serverColumn.setWidth(250);

        GridDataFactory gdFactory = GridDataFactory.fillDefaults().grab(true,
                true).span(3, 1);
        gdFactory.applyTo(serversTable);
        serversViewer = new TableViewer(serversTable);
        serversViewer.getTable().setLinesVisible(true);
        serversViewer.getTable().setHeaderVisible(true);
        serversViewer.setContentProvider(new ServersContentProvider());
        serversViewer.setLabelProvider(new ServersLabelProvider());

        serversViewer.setColumnProperties(new String[] {
                SERVER_TYPE_COL_PROPERTY, SERVER_COL_PROPERTY });

        serversCellEditor = new ComboBoxViewerCellEditorExtension(serversTable,
                SWT.READ_ONLY);
        serversCellEditor.getViewer().addSelectionChangedListener(
                new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        serversCellEditor.focusLost();
                    }
                });

        serversCellEditor
                .setContenProvider(new FilteredServerContentProvider());
        serversCellEditor.setLabelProvider(new AdapterFactoryLabelProvider(
                getServerManager().getAdapterFactory()));
        serversCellEditor.setInput(getServerManager().getServerContainer()
                .getServers());
        serversViewer.setCellModifier(new ServersCellModifier());
        serversViewer
                .setCellEditors(new CellEditor[] { null, serversCellEditor });

        serversViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        serversCellEditor.getViewer().setFilters(
                                new ViewerFilter[] { new ViewerFilter() {
                                    @Override
                                    public boolean select(Viewer viewer,
                                            Object parentElement, Object element) {
                                        IStructuredSelection sel = (IStructuredSelection) serversViewer
                                                .getSelection();
                                        Object firstElement = sel
                                                .getFirstElement();
                                        if (firstElement instanceof ServerGroupEntry) {
                                            ServerGroupEntry sge = (ServerGroupEntry) firstElement;
                                            ServerType serverType = sge
                                                    .getServerType();
                                            if (serverType != null
                                                    && element instanceof Server) {
                                                Server server = (Server) element;
                                                ServerType elementServerType = server
                                                        .getServerType();
                                                if (elementServerType
                                                        .equals(serverType)
                                                        && (server
                                                                .getServerGroup() == null || server
                                                                .getServerGroup()
                                                                .equals(
                                                                        contextServerGroup))) {
                                                    return true;
                                                }
                                            } else if (serverType != null
                                                    && element instanceof EmptyServerElement) {
                                                return true;
                                            } else if (serverType != null
                                                    && element instanceof NewServerElement) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    };
                                } });
                    }
                });
        setControlComplete(false);
    }

    /**
     * @param validatePage
     */
    protected void setControlComplete(boolean isComplete) {
        isControlComplete = isComplete;
    }

    /**
     * @param validatePage
     */
    protected boolean isControlComplete() {
        return isControlComplete;
    }

    /**
     * @return the contextServerGroup
     */
    public ServerGroup getContextServerGroup() {
        return contextServerGroup;
    }

    /**
     * Sets the message for this page with an indication of what type of message
     * it is.
     * <p>
     * The valid message types are one of <code>NONE</code>,
     * <code>INFORMATION</code>,<code>WARNING</code>, or <code>ERROR</code>.
     * </p>
     * <p>
     * Note that for backward compatibility, a message of type
     * <code>ERROR</code> is different than an error message (set using
     * <code>setErrorMessage</code>). An error message overrides the current
     * message until the error message is cleared. This method replaces the
     * current message and does not affect the error message.
     * </p>
     * 
     * @param newMessage
     *            the message, or <code>null</code> to clear the message
     * @param newType
     *            the message type
     * @since 2.0
     */
    public void setMessage(String newMessage, int newType) {
        // Implement if you need validation messages.
    }

    public String getServerGroupName() {
        return serverGroupNameText.getText().trim();
    }

    public ServerGroupType getServerGroupType() {
        return (ServerGroupType) ((IStructuredSelection) serverGroupTypeViewer
                .getSelection()).getFirstElement();
    }

    /**
     * Returns list of associated servers.
     */
    public List<Server> getAssociatedServers() {
        List<Server> servers = new ArrayList<Server>();
        if (serversViewer != null
                && serversViewer.getContentProvider() instanceof ServersContentProvider) {
            ServerGroupEntry[] groupEntries = ((ServersContentProvider) serversViewer
                    .getContentProvider()).getGroupEntries();
            for (ServerGroupEntry groupEntry : groupEntries) {
                if (groupEntry.getServer() != null) {
                    servers.add(groupEntry.getServer());
                }
            }
        }
        return servers;
    }

    public void initializeControlDefaults() {
        // set default server name
        serverGroupNameText.setText(DeployUtil.getDefaultName(
                DEFAULT_SERVER_GROUP_NAME, getServerGroupNames()));
        if (contextServerGroup != null) {
            String name = contextServerGroup.getName() == null ? "" //$NON-NLS-1$
                    : contextServerGroup.getName();
            serverGroupNameText.setText(name);
            ServerGroupType groupType = contextServerGroup.getServerGroupType();
            serverGroupTypeViewer.setSelection(new StructuredSelection(
                    groupType), true);

            ServerGroupEntry[] groupEntries = getServerGroupEntires();
            for (Server server : contextServerGroup.getMembers()) {
                ServerGroupEntry entry = getEntryFromServerType(groupEntries,
                        server.getServerType());
                if (entry != null) {
                    entry.setServer(server);
                }
            }
            serversViewer.refresh();

        }
        serverGroupNameText.setFocus();
        serverGroupNameText.selectAll();
        initialised = true;
        setControlComplete(validatePage(false));
    }

    public void commitToServerGroup() throws Exception {
        if (contextServerGroup != null) {
            boolean updateName = !getServerGroupName().equals(
                    contextServerGroup.getName());
            boolean updateServerGroupType = getServerGroupType().equals(
                    contextServerGroup.getServerGroupType());

            EditingDomain ed = getServerManager().getEditingDomain();

            CompoundCommand command = new CompoundCommand(
                    Messages.ServerGroupGeneralControl_updateServerGroup_action);
            Command innerCommand;
            if (updateName) {
                innerCommand = SetCommand.create(ed, contextServerGroup,
                        DeployPackage.eINSTANCE.getNamedElement_Name(),
                        getServerGroupName());
                command.append(innerCommand);
            }
            if (updateServerGroupType) {
                innerCommand = SetCommand.create(ed, contextServerGroup,
                        DeployPackage.eINSTANCE
                                .getServerGroup_ServerGroupType(),
                        getServerGroupType());
                command.append(innerCommand);
            }
            ServerGroupEntry[] groupEntries = getServerGroupEntires();
            // List<Server> updatedServers = new ArrayList<Server>(
            // contextServerGroup.getMembers());
            for (Server server : contextServerGroup.getMembers()) {
                ServerGroupEntry entry = getEntryFromServerType(groupEntries,
                        server.getServerType());
                if (entry != null) {
                    Server entryServer = entry.getServer();
                    if (entryServer == null) {
                        // remove server from group
                        // innerCommand = RemoveCommand.create(ed,
                        // contextServerGroup, DeployPackage.eINSTANCE
                        // .getServerGroup_Members(), server);
                        // command.append(innerCommand);
                        // and the opposite reference
                        innerCommand = SetCommand
                                .create(ed, server, DeployPackage.eINSTANCE
                                        .getServer_ServerGroup(), null);
                        command.append(innerCommand);

                    } else if (entryServer != null
                            && !entryServer.equals(server)) {
                        // update
                        innerCommand = SetCommand
                                .create(ed, server, DeployPackage.eINSTANCE
                                        .getServer_ServerGroup(),
                                        contextServerGroup);
                        command.append(innerCommand);
                    }
                }

            }
            for (ServerGroupEntry serverGroupEntry : groupEntries) {
                Server entryServer = serverGroupEntry.getServer();
                if (entryServer != null
                        && !contextServerGroup.getMembers().contains(
                                entryServer)) {
                    // add
                    innerCommand = AddCommand.create(ed, contextServerGroup,
                            DeployPackage.eINSTANCE.getServerGroup_Members(),
                            entryServer);
                    command.append(innerCommand);
                }
            }

            if (command.canExecute()) {
                ed.getCommandStack().execute(command);
            }
        }
    }

    private boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);
        if (!initialised)
            return true;

        // errors
        if (getServerGroupName().length() == 0) {
            if (showMessages) {
                setErrorMessage(Messages.ServerGroupGeneralControl_emptyName_message);
            }
            serverGroupNameText.setFocus();
            return false;
        }
        if (getServerGroupNames().contains(getServerGroupName())) {
            if (showMessages) {
                setErrorMessage(Messages.ServerGroupGeneralControl_notUniqueName_message);
            }
            serverGroupNameText.setFocus();
            return false;
        }
        if (getServerGroupType() == null) {
            if (showMessages) {
                setErrorMessage(Messages.ServerGroupGeneralControl_notSelectedType_message);
            }
            if (!serverGroupNameText.isFocusControl()) {
                serverGroupTypeViewer.getCombo().setFocus();
            }
            return false;
        }

        // warnings
        if (getAssociatedServers().isEmpty()) {
            if (showMessages) {
                setWarningMessage(Messages.ServerGroupGeneralControl_emptyServerGroup_message);
            }
            if (!serversViewer.getControl().isFocusControl()) {
                serversViewer.getControl().setFocus();
            }
            return true;
        }

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setWarningMessage(null);

        return true;
    }

    private ArrayList<String> getServerGroupNames() {
        ArrayList<String> serverGroupNames = new ArrayList<String>();
        for (ServerGroup serverGroup : getServerManager().getServerContainer()
                .getServerGroups()) {
            if (!serverGroup.equals(contextServerGroup)) {
                serverGroupNames.add(serverGroup.getName());
            }
        }
        return serverGroupNames;
    }

    /**
     * @param groupEntries
     * @param serverType
     * @return
     */
    private ServerGroupEntry getEntryFromServerType(
            ServerGroupEntry[] groupEntries, ServerType serverType) {
        for (ServerGroupEntry serverGroupEntry : groupEntries) {
            if (serverGroupEntry.getServerType().equals(serverType)) {
                return serverGroupEntry;
            }
        }
        return null;
    }

    /**
     * @return
     */
    private ServerGroupEntry[] getServerGroupEntires() {
        IContentProvider contentProvider = serversViewer.getContentProvider();
        ServerGroupEntry[] groupEntries = new ServerGroupEntry[0];
        if (contentProvider instanceof ServersContentProvider) {
            groupEntries = ((ServersContentProvider) contentProvider)
                    .getGroupEntries();
        }
        return groupEntries;
    }

    /**
     * @return
     */
    private ServerManager getServerManager() {
        return DeployUIActivator.getServerManager();
    }

    /**
     * @param object
     */
    private void setMessage(String object) {
        setMessage(object, IMessageProvider.NONE);
    }

    /**
     * @param object
     */
    private void setErrorMessage(String object) {
        setMessage(object, IMessageProvider.ERROR);
    }

    /**
     * @param object
     */
    private void setWarningMessage(String object) {
        setMessage(object, IMessageProvider.WARNING);
    }
}
