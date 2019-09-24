package com.tibco.xpd.n2.live.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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
import com.tibco.xpd.n2.live.OpenspaceViewHelper;

/**
 * View to allow easy access to Openspace on any of the configured BPM servers.
 * 
 * Sid ACE-2918 In ACE there are no deployment servers, so the preferences just holds a single in-built server id. NOW
 * the user sets the single server's URL in the property sheet and that gets saved in the preferences.
 * 
 * For now we will keep this class as permitting multiple servers as we may introduce deployment servers back into ACE
 * or may allow the user to manually create multiple server configurations. The difference from AMX BPM is that the
 * server will be slected in the properties view not in this view if we ever did so, therefore this function doesn't
 * need to save selected server to prefs etc.
 * 
 * @author nwilson
 * @since 2 Sep 2014
 */
public class OpenspaceViewPart extends ViewPart implements
        ITabbedPropertySheetPageContributor {

    /**
     * The embedded browser control.
     * 
     * Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view.
     * 
     * Left old view config stuff commented out in the code in case we wish to return to it after an update of the
     * internal browser by Eclipse
     */
    // private Browser browser;

    private Button btnRefresh;

    private Button copyUrlToClipboard;

    private Button btnLaunch;

    private static Font btnLaunchFont = null;

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

        // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
        // ScrolledComposite sc =
        // new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        // sc.setExpandHorizontal(true);
        // sc.setExpandVertical(true);

        Composite root = new Composite(parent, SWT.NONE);
        GridLayout rootLayout = new GridLayout(1, false);
        rootLayout.marginWidth = 0;
        rootLayout.marginHeight = 0;
        root.setLayout(rootLayout);

        // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
        // sc.setContent(root);

        Composite btnBar = new Composite(root, SWT.NONE);
        btnBar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

        GridLayout btnBarLayout = new GridLayout(3, false);
        // btnBarLayout.marginWidth = 0;
        // btnBarLayout.marginHeight = 0;
        btnBar.setLayout(btnBarLayout);
        btnBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

        Label serverLabel = new Label(btnBar, SWT.NONE);
        serverLabel.setText(Messages.OpenspaceViewPart_ServerComboLabel);
        GridData gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
        gd.verticalAlignment = SWT.CENTER;
        serverLabel.setLayoutData(gd);
        
        /* Sid ACE-2918 Deploy Server selection is no longer required. */ 
        //
        // servers = new ComboViewer(root);
        // GridData serverLayoutData =
        // new GridData(SWT.FILL, SWT.FILL, false, false);
        // serverLayoutData.widthHint = 150;
        // servers.getCombo().setLayoutData(serverLayoutData);
        // servers.setContentProvider(new ArrayContentProvider());
        // servers.setLabelProvider(new ServersLabelProvider());
        // servers.addSelectionChangedListener(new ServerSelectionListener());

        messageLabel = new Label(btnBar, SWT.NONE);
        messageLabel.setText(Messages.OpenspaceViewPart_ServerNotFoundLabel);
        GridData gd2 = new GridData(SWT.FILL, SWT.CENTER, true, false);
        messageLabel.setLayoutData(gd2);

        Composite buttons = new Composite(btnBar, SWT.NONE);
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

        // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
        // Moved launch to main view.
        btnLaunch = new Button(root, SWT.PUSH);

        GridData gd3 = new GridData(SWT.LEFT, SWT.CENTER, false, true);
        gd3.horizontalIndent = 20;
        // gd3.minimumHeight = 70;
        btnLaunch.setLayoutData(gd3);

        btnLaunch.setImage(imageRegistry.get(Activator.LAUNCH_ICON32));
        btnLaunch.setText("  " + Messages.OpenspaceViewPart_LaunchLiveDevWorkManager_btn + "  "); //$NON-NLS-1$ //$NON-NLS-2$
        btnLaunch.setToolTipText(Messages.OpenspaceViewPart_RelaunchButtonToolTip);
        btnLaunch.addSelectionListener(listener);

        if (btnLaunchFont == null) {
            Font font = btnLaunch.getFont();

            FontData[] fontData = font.getFontData();

            if (fontData != null && fontData.length > 0) {
                fontData[0].height *= 1.5;

                btnLaunchFont = new Font(null, fontData[0]);
            }
        }

        if (btnLaunchFont != null) {
            btnLaunch.setFont(btnLaunchFont);
        }

        /*
         * Sid ACE-2918 In ACE there is just one built in server {@link OpenspaceViewHelper#ACE_DEFAULT_SERVER_ID} -
         * later, we may provide a way to create additional id's so we'll act as if the user has selected the default
         * serverId.
         */
        getSite().setSelectionProvider(new ISelectionProvider() {

            @Override
            public void addSelectionChangedListener(ISelectionChangedListener listener) {
            }

            @Override
            public ISelection getSelection() {
                String serverId = OpenspaceViewHelper.getServerIdFromPreferences();

                if (serverId == null || serverId.isEmpty()) {
                    serverId = OpenspaceViewHelper.ACE_DUMMY_DEFAULT_SERVER_ID;
                }

                Server deployServer = OpenspaceViewHelper.getDeployServer(serverId);

                if (deployServer != null) {
                    return new StructuredSelection(Collections.singletonList(deployServer));
                }

                return null;
            }

            @Override
            public void removeSelectionChangedListener(ISelectionChangedListener listener) {
            }

            @Override
            public void setSelection(ISelection selection) {
                // Only one thing is ever selected - so do nothing
            }
        });

        // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
        // browser = new Browser(root, SWT.NONE);
        // browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
        // sc.setMinSize(root.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
        initialiseControls();

    }

    /**
     * Get servers, set combo box input and and select the appropriate server in
     * the combo box and set browser URL accordingly
     * 
     */
    public void initialiseControls() {

        // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
        // browser.setText(Messages.OpenspaceViewPart_BrowserLoadingMessage);

        /*
         * if server selection is stored in the preference then select that
         * 
         * Sid ACE-2918 In ACE there are no deployment servers, so the preferences just holds a single in-built server
         * id. NOW the user sets the single server's URL in the property sheet and that gets saved in the preferences.
         * 
         * For now we will keep this class as permitting multiple servers as we may introduce deployment servers back
         * into ACE or may allow the user to manually create multiple server configurations. The difference from AMX BPM
         * is that the server will be slected in the properties view not in this view if we ever did so, therefore this
         * function doesn't need to save selected server to prefs etc.
         */
        String selectedServerId = OpenspaceViewHelper.getServerIdFromPreferences();

        /*
         * If the user hasn't changed from the default URL or hasn't at least change the domain name then tell them
         * that.
         */
        try {
            if (selectedServerId != null && !OpenspaceViewHelper
                    .isTemplateOpenspaceURLDomain(OpenspaceViewHelper.getOpenspaceBaseURL(selectedServerId))) {
                final String openspaceBaseURL = OpenspaceViewHelper.getOpenspaceBaseURL(selectedServerId);
                messageLabel.setText(openspaceBaseURL);
                messageLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
                messageLabel.setToolTipText(null);

                // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
                // Display.getDefault().asyncExec(new Runnable() {
                //
                // @Override
                // public void run() {
                // browser.setUrl(openspaceBaseURL);
                // }
                // });

            } else {
                messageLabel.setText(String.format(Messages.OpenspaceViewPart_ConfigureWorkManagerURL_message,
                        Messages.OpenspaceViewHelper_DomainNameForDefaultTemplateURL));
                messageLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
                messageLabel.setToolTipText(String.format(
                        Messages.OpenspaceViewPart_SetRULDomainTooltip_message,
                        Messages.OpenspaceViewHelper_DomainNameForDefaultTemplateURL));

                // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
                // browser.setText(""); //$NON-NLS-1$
            }
        } catch (MalformedURLException e) {
            messageLabel.setText(String.format(Messages.OpenspaceViewPart_InvalidWorkManagerURL_message,
                    Messages.OpenspaceViewHelper_DomainNameForDefaultTemplateURL));
            messageLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
            messageLabel.setToolTipText(null);

            // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
            // browser.setText(""); //$NON-NLS-1$
        }
    }

    @Override
    public void setFocus() {
        // Sid ACE-3218 Studio Internal browser not compatible with latest forms stuff, so need to redesign view
        // if (browser != null && !browser.isDisposed()) {
        // browser.setFocus();
        // }

        if (btnLaunch != null && !btnLaunch.isDisposed()) {
            btnLaunch.setFocus();
        }

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
                /* Sid ACE-2918 We don't work with real servers, just the (currently one built in) server id's */
                String selectedServerId = OpenspaceViewHelper.getServerIdFromPreferences();
                if (selectedServerId != null) {

                    try {
                        String openspaceUrl = OpenspaceViewHelper.getOpenspaceURLWithParams(selectedServerId);
                        if (openspaceUrl != null) {
                            URL url = new URL(openspaceUrl);
                            PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(url);
                        }
                    } catch (PartInitException e1) {
                        MessageDialog.openError(getSite().getShell(),
                                Messages.OpenspaceViewPart_LaunchErrorTitle,
                                Messages.OpenspaceViewPart_LaunchErrorMessage + "\n" + e1.getMessage()); //$NON-NLS-1$
                    } catch (MalformedURLException e1) {
                        String base = OpenspaceViewHelper.getOpenspaceBaseURL(selectedServerId);
                        MessageDialog.openError(getSite().getShell(),
                                Messages.OpenspaceViewPart_LaunchErrorTitle,
                                Messages.OpenspaceViewPart_InvalidUrl + "\n" + base); //$NON-NLS-1$
                    }
                }

            } else if (e.widget == copyUrlToClipboard) {
                /* Sid ACE-2918 We don't work with real servers, just the (currently one built in) server id's */
                String selectedServerId = OpenspaceViewHelper.getServerIdFromPreferences();
                if (selectedServerId != null) {
                    String url = OpenspaceViewHelper.getOpenspaceURLWithParams(selectedServerId);
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
