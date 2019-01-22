package com.tibco.xpd.n2.live.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.ui.DeployUIActivator;

/**
 * View to allow easy access to Openspace on any of the configured BPM servers.
 * The server can be selected via a combo box and the Openspace web page is
 * displayed in an embedded browser. Buttons allow the page to be refreshed or
 * opened in an external browser.
 * 
 * @author nwilson
 * @since 2 Sep 2014
 */
public class OpenspaceViewPart extends ViewPart implements
        ITabbedPropertySheetPageContributor {

    private static final String ADMIN_SERVER_TYPE_ID =
            "com.tibco.amf.tools.admincligen.adminserver"; //$NON-NLS-1$

    /**
     * A list of available BPM servers.
     */
    private ComboViewer servers;

    /**
     * The embedded browser control.
     */
    private Browser browser;

    private Button btnRefresh;

    private Button copyUrlToClipboard;

    private Button btnLaunch;

    private Label messageLabel;

    private IUndoContext undoContext = new UndoContext();

    /**
     * @see org.eclipse.ui.part.WorkbenchPart
     *      #createPartControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     *            The parent component.
     */
    @Override
    public void createPartControl(Composite parent) {

        ScrolledComposite sc =
                new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        Composite root = new Composite(sc, SWT.NONE);
        root.setLayout(new GridLayout(4, false));
        sc.setContent(root);

        Label serverLabel = new Label(root, SWT.NONE);
        serverLabel.setText(Messages.OpenspaceViewPart_ServerComboLabel);
        GridData gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
        gd.verticalAlignment = SWT.CENTER;
        serverLabel.setLayoutData(gd);

        servers = new ComboViewer(root);
        GridData serverLayoutData =
                new GridData(SWT.FILL, SWT.FILL, false, false);
        serverLayoutData.widthHint = 150;
        servers.getCombo().setLayoutData(serverLayoutData);
        servers.setContentProvider(new ArrayContentProvider());
        servers.setLabelProvider(new ServersLabelProvider());
        servers.addSelectionChangedListener(new ServerSelectionListener());

        messageLabel = new Label(root, SWT.NONE);
        messageLabel.setText(Messages.OpenspaceViewPart_ServerNotFoundLabel);
        GridData gd2 = new GridData(SWT.FILL, SWT.CENTER, true, false);
        messageLabel.setLayoutData(gd2);

        Composite buttons = new Composite(root, SWT.NONE);
        buttons.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
        buttons.setLayout(new FillLayout());

        ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();

        ControlSelectionListener listener = new ControlSelectionListener();

        copyUrlToClipboard = new Button(buttons, SWT.PUSH);
        copyUrlToClipboard.setImage(imageRegistry.get(Activator.COPY_URL_ICON));
        copyUrlToClipboard
                .setToolTipText(Messages.OpenspaceViewPart_CopyUrlToolTip);
        copyUrlToClipboard.addSelectionListener(listener);

        btnRefresh = new Button(buttons, SWT.PUSH);
        btnRefresh.setImage(imageRegistry.get(Activator.REFRESH_ICON));
        btnRefresh
                .setToolTipText(Messages.OpenspaceViewPart_RefreshButtonToolTip);
        btnRefresh.addSelectionListener(listener);

        btnLaunch = new Button(buttons, SWT.PUSH);
        btnLaunch.setImage(imageRegistry.get(Activator.LAUNCH_ICON));
        btnLaunch
                .setToolTipText(Messages.OpenspaceViewPart_RelaunchButtonToolTip);
        btnLaunch.addSelectionListener(listener);

        getSite().setSelectionProvider(servers);

        browser = new Browser(root, SWT.NONE);
        browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

        sc.setMinSize(root.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        initialiseControls();
    }

    /**
     * Get servers, set combo box input and and select the appropriate server in
     * the combo box and set browser URL accordingly
     * 
     */
    public void initialiseControls() {

        messageLabel.setVisible(false);
        browser.setText(Messages.OpenspaceViewPart_BrowserLoadingMessage);

        List<Server> deploymentServers = getDeploymentServers();
        servers.setInput(deploymentServers);

        /*
         * if server selection is stored in the preference then select that
         * server if it is still in the list of available servers
         */

        String prefServerId = OpenspaceViewHelper.getServerIdFromPreferences();
        Server server = null;
        if (prefServerId != null) {
            for (int i = 0; i < deploymentServers.size(); i++) {
                if (prefServerId.equals(deploymentServers.get(i).getId())) {

                    /* server found in prefs then select this in the combo */
                    servers.getCombo().select(i);
                    server = deploymentServers.get(i);
                    break;
                }
                /*
                 * TODO: server not found, so delete the prefs value for the
                 * server prefServerId
                 */
            }
        } else if (deploymentServers.size() > 0) {
            server = deploymentServers.get(0);
            servers.getCombo().select(0);
        }

        if (server != null) {
            /*
             * save the selected server's id in the preferences
             */
            OpenspaceViewHelper.saveServerIdInPreferences(server.getId());

            final String openspaceURL =
                    OpenspaceViewHelper.getOpenspaceURLWithParams(server);

            if (openspaceURL != null) {
                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        browser.setUrl(openspaceURL);
                    }
                });
            } else {
                browser.setText(""); //$NON-NLS-1$
            }
        } else {
            browser.setText(""); //$NON-NLS-1$
        }

        if (deploymentServers == null || deploymentServers.isEmpty()) {

            /* Show message for 'No deployment server found' */
            messageLabel.setVisible(true);
        }
    }

    @Override
    public void setFocus() {
        servers.getControl().setFocus();
    }

    /**
     * Get list of available deployment servers
     * 
     * @return list of <code>com.tibco.xpd.deploy.Server</code>
     */
    private List<Server> getDeploymentServers() {
        List<Server> servers = new ArrayList<>();
        com.tibco.xpd.deploy.manager.ServerManager serverManagers =
                DeployUIActivator.getServerManager();
        EList<Server> allServers =
                serverManagers.getServerContainer().getServers();
        for (Server server : allServers) {
            ServerType serverType = server.getServerType();
            if (serverType != null) {
                if (ADMIN_SERVER_TYPE_ID.equals(serverType.getId())) {
                    servers.add(server);
                }
            }
        }

        return servers;
    }

    /**
     * 
     * 
     * Label provider for servers
     */
    private class ServersLabelProvider implements ILabelProvider {

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
         * 
         * @param listener
         */
        @Override
        public void addListener(ILabelProviderListener listener) {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
         * 
         */
        @Override
        public void dispose() {
            // TODO Auto-generated method stub

        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
         *      java.lang.String)
         * 
         * @param element
         * @param property
         * @return
         */
        @Override
        public boolean isLabelProperty(Object element, String property) {
            // TODO Auto-generated method stub
            return false;
        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
         * 
         * @param listener
         */
        @Override
        public void removeListener(ILabelProviderListener listener) {
            // TODO Auto-generated method stub
        }

        /**
         * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Image getImage(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public String getText(Object element) {
            if (element instanceof Server) {
                Server server = (Server) element;
                return server.getName();
            }
            return null;
        }
    }

    /**
     * Selection change listener for servers combo
     */
    public class ServerSelectionListener implements ISelectionChangedListener {

        /**
         * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
         * 
         * @param event
         */
        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            Server server = getSelectedServer(event.getSelection());
            if (server != null) {
                browser.setText(Messages.OpenspaceViewPart_BrowserLoadingMessage);
                /*
                 * save the selected server's id in the preferences
                 */
                OpenspaceViewHelper.saveServerIdInPreferences(server.getId());

                final String openspaceURL =
                        OpenspaceViewHelper.getOpenspaceURLWithParams(server);
                if (openspaceURL != null) {
                    Display.getDefault().asyncExec(new Runnable() {

                        @Override
                        public void run() {
                            browser.setUrl(openspaceURL);
                        }
                    });
                }
            } else {
                browser.setText(""); //$NON-NLS-1$
            }
        }
    }

    /**
     * @param selection
     * @return server from the given selection
     */
    private Server getSelectedServer(ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = ((IStructuredSelection) selection);
            Object selectedObj = ss.getFirstElement();
            return (Server) selectedObj;
        }
        return null;
    }

    /**
     * controls selection listener
     */
    public class ControlSelectionListener extends SelectionAdapter {

        /**
         * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         * 
         * @param e
         */
        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.widget == btnRefresh) {

                /*
                 * reset combo input to load any newly added servers and set
                 * broswer url to currently selected
                 */
                initialiseControls();

            } else if (e.widget == btnLaunch) {

                // get selected server
                Server server = getSelectedServer(servers.getSelection());
                if (server != null) {

                    try {
                        String openspaceUrl =
                                OpenspaceViewHelper
                                        .getOpenspaceURLWithParams(server);
                        if (openspaceUrl != null) {
                            URL url = new URL(openspaceUrl);
                            PlatformUI.getWorkbench().getBrowserSupport()
                                    .getExternalBrowser().openURL(url);
                        }
                    } catch (PartInitException e1) {
                        MessageDialog.openError(getSite().getShell(),
                                Messages.OpenspaceViewPart_LaunchErrorTitle,
                                Messages.OpenspaceViewPart_LaunchErrorMessage
                                        + "\n" + e1.getMessage()); //$NON-NLS-1$
                    } catch (MalformedURLException e1) {
                        String base =
                                OpenspaceViewHelper.getOpenspaceBaseURL(server);
                        MessageDialog.openError(getSite().getShell(),
                                Messages.OpenspaceViewPart_LaunchErrorTitle,
                                Messages.OpenspaceViewPart_InvalidUrl
                                        + "\n" + base); //$NON-NLS-1$
                    }
                }
            } else if (e.widget == copyUrlToClipboard) {
                Server server = getSelectedServer(servers.getSelection());
                if (server != null) {
                    String url =
                            OpenspaceViewHelper
                                    .getOpenspaceURLWithParams(server);
                    Display display = Display.getDefault();
                    Clipboard clipboard = new Clipboard(display);
                    TextTransfer textTransfer = TextTransfer.getInstance();
                    Transfer[] transfers = new Transfer[] { textTransfer };
                    Object[] data = new Object[] { url };
                    clipboard.setContents(data, transfers);
                    clipboard.dispose();
                }
            }
        }
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return getSite().getId();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class) {
            return new TabbedPropertySheetPage(this);
        } else if (adapter == IUndoContext.class) {
            return undoContext;
        }
        return super.getAdapter(adapter);
    }
}
