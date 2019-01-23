package com.tibco.xpd.n2.live.internal;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Sash contribution section for the Openspace view to display the server
 * details and allow modification of the Openspace URL.
 * 
 * @author nwilson
 * @since 4 Sep 2014
 */
public class OpenspaceViewConnectionPropertySection extends
        AbstractFilteredTransactionalSection implements SashSection {

    /**
     * The display name of the selected server.
     */
    private Label serverName;

    /**
     * The openspace base URL.
     */
    private Text openspaceUrl;

    /**
     * Constructor to initialise this section to accept Server input obejcts.
     */
    public OpenspaceViewConnectionPropertySection() {
        super(DeployPackage.eINSTANCE.getServer());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        Server server = (Server) getInput();
        if (server != null) {
            StringBuilder name = new StringBuilder();
            name.append(server.getName());
            String url = null;
            ServerConfig config = server.getServerConfig();
            if (config != null) {
                ConfigParameter urlParameter =
                        config.getConfigParameter("serverUrl"); //$NON-NLS-1$
                if (urlParameter != null) {
                    url = urlParameter.getValue();
                }
            }
            if (url != null) {
                name.append(" ("); //$NON-NLS-1$
                name.append(url);
                name.append(")"); //$NON-NLS-1$
            }
            serverName.setText(name.toString());

            String baseUrl = OpenspaceViewHelper.getOpenspaceBaseURL(server);
            openspaceUrl.setText(baseUrl == null ? "" : baseUrl); //$NON-NLS-1$
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The toolkit to create controls.
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite baseComposite = toolkit.createComposite(parent);
        baseComposite.setLayout(new GridLayout(2, false));

        Label serverNameLabel =
                toolkit.createLabel(baseComposite,
                        Messages.OpenspaceViewConnectionPropertySection_DevServerLabel);
        serverNameLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));
        serverName = toolkit.createLabel(baseComposite, null);
        serverName
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Label openspaceUrlLabel =
                toolkit.createLabel(baseComposite,
                        Messages.OpenspaceViewConnectionPropertySection_OpenspaceUrlLabel);
        openspaceUrlLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
                false, false));
        openspaceUrl = toolkit.createText(baseComposite, null);
        openspaceUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));

        Button refresh = toolkit.createButton(baseComposite, null, SWT.PUSH);
        Image refreshIcon =
                Activator.getDefault().getImageRegistry()
                        .get(Activator.REFRESH_ICON);
        refresh.setImage(refreshIcon);
        refresh.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
        Label refreshLabel =
                toolkit.createLabel(baseComposite,
                        Messages.OpenspaceViewConnectionPropertySection_OpenspaceViewRefreshNote);
        refreshLabel
                .setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

        manageControlUpdateOnDeactivate(openspaceUrl);
        refresh.addSelectionListener(new SelectionAdapter() {

            /**
             * @see org.eclipse.swt.events.SelectionAdapter
             *      #widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                IViewPart view =
                        getSite()
                                .getPage()
                                .findView(LiveDevPerspectiveFactory.LIVE_DEV_OPENSPACE_VIEW_ID);
                if (view instanceof OpenspaceViewPart) {
                    OpenspaceViewPart ovp = (OpenspaceViewPart) view;
                    ovp.initialiseControls();
                }
            }
        });

        return baseComposite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection
     *      #doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command command = null;
        if (obj == openspaceUrl) {
            Server server = (Server) getInput();
            if (server != null) {
                String newBaseUrl = openspaceUrl.getText();
                if (newBaseUrl.length() == 0) {
                    newBaseUrl = null;
                }
                String oldBaseUrl =
                        OpenspaceViewHelper.getOpenspaceBaseURL(server);
                if (!Objects.equals(newBaseUrl, oldBaseUrl)) {
                    String serverId = server.getId();
                    IUndoableOperation operation =
                            new SetOpenspaceUrlOperation(serverId, newBaseUrl);
                    operation.addContext(getUndoContext());
                    IWorkbench workbench =
                            getSite().getWorkbenchWindow().getWorkbench();
                    IOperationHistory operationHistory =
                            workbench.getOperationSupport()
                                    .getOperationHistory();
                    try {
                        operationHistory.execute(operation, null, null);
                    } catch (ExecutionException e) {
                    }
                }
            }
        }
        return command;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashSection#shouldSashSectionRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    public boolean shouldSashSectionRefresh(List<Notification> notifications) {
        return shouldRefresh(notifications);
    }

}
