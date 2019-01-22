package com.tibco.xpd.deploy.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ViewPluginAction;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.deploy.impl.ServerContainerImpl;
import com.tibco.xpd.deploy.impl.ServerImpl;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.actions.ConnectServerAction;
import com.tibco.xpd.deploy.ui.actions.DeleteServerAction;
import com.tibco.xpd.deploy.ui.actions.DisconnectServerAction;
import com.tibco.xpd.deploy.ui.actions.RefreshAction;
import com.tibco.xpd.deploy.ui.wizards.addserver.AddServerWizard;

/**
 * Delegate for Deployment Server's Toolbar view Actions.
 * <p>
 * Adds the following actions to the toolbar
 * <li>
 * Create new Server</li>
 * <li>
 * Connect Server</li>
 * <li>
 * Disconnect Server</li>
 * <li>
 * Refresh Server</li>
 * <li>
 * Delete Server</li>
 * 
 * @author kthombar
 * @since May 26, 2015
 */
public class DeploymentServerViewActionDelegate implements IViewActionDelegate {

    private static String DELETE_SERVER_ACTION_ID =
            "com.tibco.xpd.deploy.ui.deleteserveraction"; //$NON-NLS-1$

    private static String NEW_SERVER_ACTION_ID =
            "com.tibco.xpd.deploy.ui.newserveraction"; //$NON-NLS-1$

    private static String CONNECT_SERVER_ACTION_ID =
            "com.tibco.xpd.deploy.ui.connectserveraction"; //$NON-NLS-1$

    private static String DISCONNECT_SERVER_ACTION_ID =
            "com.tibco.xpd.deploy.ui.disconnectserveraction"; //$NON-NLS-1$

    private static String REFRESH_SERVER_ACTION_ID =
            "com.tibco.xpd.deploy.ui.refreshserveraction";//$NON-NLS-1$

    /**
     * the current selection in the deployment tree
     */
    private ISelection currentSelectionInTree;

    /**
     * the Navigator for the deployment tree
     */
    private CommonNavigator navigator = null;

    @SuppressWarnings("restriction")
    @Override
    public void run(IAction action) {

        if (currentSelectionInTree instanceof TreeSelection
                && action instanceof ViewPluginAction) {

            final TreeSelection treeSel =
                    (TreeSelection) currentSelectionInTree;

            ViewPluginAction viewPluginAction = (ViewPluginAction) action;

            Object firstElement = treeSel.getFirstElement();

            if (firstElement instanceof ServerImpl) {
                /*
                 * If the Server is selected, then we allow the user to
                 * connect,disconnect,refresh,delete or add a new server.
                 */
                if (CONNECT_SERVER_ACTION_ID.equals(viewPluginAction.getId())) {

                    connectServer(treeSel);

                } else if (DISCONNECT_SERVER_ACTION_ID.equals(viewPluginAction
                        .getId())) {

                    disconnectServer(treeSel);

                } else if (DELETE_SERVER_ACTION_ID.equals(viewPluginAction
                        .getId())) {

                    deleteServer(treeSel);

                } else if (REFRESH_SERVER_ACTION_ID.equals(viewPluginAction
                        .getId())) {

                    refreshServer(treeSel);

                } else {
                    createNewServer(treeSel);
                }

            } else if (firstElement instanceof ServerContainerImpl) {
                /*
                 * If the Server Container is selected then we only allow the
                 * user to create a new server.
                 */
                createNewServer(treeSel);

            }
        }
    }

    /**
     * Refreshes the selected server.
     * 
     * @param treeSel
     */
    private void refreshServer(final TreeSelection treeSel) {

        if (navigator != null) {
            /*
             * Calling the 'RefreshAction' for 2 reasons , 1. Code reusability,
             * 2. RefreshAction has everything that we need to refresh the
             * server(i.e. Monitor based job, exception handling etc)
             */
            RefreshAction refreshServer =
                    new RefreshAction(navigator.getCommonViewer()) {
                        /**
                         * @see org.eclipse.ui.actions.BaseSelectionListenerAction#getStructuredSelection()
                         * 
                         * @return
                         */
                        @Override
                        public IStructuredSelection getStructuredSelection() {

                            return treeSel;
                        }
                    };

            refreshServer.run();
        }
    }

    /**
     * Creates a new deployment server.
     * 
     * @param treeSel
     */
    private void createNewServer(TreeSelection treeSel) {
        /*
         * call the AddServerWizard
         */
        AddServerWizard addServerWizard = new AddServerWizard();

        addServerWizard.init(PlatformUI.getWorkbench(), treeSel);

        WizardDialog dialog =
                new WizardDialog(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell(), addServerWizard);

        dialog.open();
    }

    /**
     * Deletes the selected server(s).
     * 
     * @param treeSel
     */
    private void deleteServer(final TreeSelection treeSel) {

        DeleteServerAction deleteServer = new DeleteServerAction() {
            /**
             * @see org.eclipse.ui.actions.BaseSelectionListenerAction#getStructuredSelection()
             * 
             * @return
             */
            @Override
            public IStructuredSelection getStructuredSelection() {

                return treeSel;
            }
        };

        deleteServer.run();

    }

    /**
     * Disconnects the selected server.
     * 
     * @param treeSel
     */
    private void disconnectServer(final TreeSelection treeSel) {

        if (navigator != null) {
            /*
             * Calling the 'DisconnectServerAction' for 2 reasons , 1. Code
             * reusability, 2. DisconnectServerAction has everything that we
             * need to disconnect the server(i.e. Monitor based job, exception
             * handling etc)
             */
            DisconnectServerAction connectServer =
                    new DisconnectServerAction(navigator.getCommonViewer()) {
                        /**
                         * @see org.eclipse.ui.actions.BaseSelectionListenerAction#getStructuredSelection()
                         * 
                         * @return
                         */
                        @Override
                        public IStructuredSelection getStructuredSelection() {

                            return treeSel;
                        }
                    };

            connectServer.run();

            /*
             * calling the commonviewer setselection on purpose because after
             * the server is disconnected we would want the disconnect action to
             * be disabled and the connect action to be enabled.
             */
            navigator.getCommonViewer().setSelection(currentSelectionInTree);
        }

    }

    /**
     * Connects the selected server.
     * 
     * @param treeSel
     */
    private void connectServer(final TreeSelection treeSel) {

        if (navigator != null) {
            /*
             * Calling the 'ConnectServerAction' for 2 reasons , 1. Code
             * reusability, 2. ConnectServerAction has everything that we need
             * to connect the server(i.e. Monitor based job, exception handling
             * etc)
             */
            ConnectServerAction connectServer =
                    new ConnectServerAction(navigator.getCommonViewer()) {
                        /**
                         * @see org.eclipse.ui.actions.BaseSelectionListenerAction#getStructuredSelection()
                         * 
                         * @return
                         */
                        @Override
                        public IStructuredSelection getStructuredSelection() {

                            return treeSel;
                        }
                    };

            connectServer.run();
            /*
             * calling the commonviewer setselection on purpose because after
             * the server is connected we would want the disconnect & refresh
             * actions to be enabled and the connect action to be disabled.
             */
            navigator.getCommonViewer().setSelection(currentSelectionInTree);
        }
    }

    @SuppressWarnings("restriction")
    @Override
    public void selectionChanged(IAction action, ISelection selection) {

        currentSelectionInTree = selection;

        if (action instanceof ViewPluginAction) {

            ViewPluginAction viewPluginAction = (ViewPluginAction) action;
            viewPluginAction.setEnabled(false);

            if (currentSelectionInTree instanceof TreeSelection) {
                TreeSelection treeSel = (TreeSelection) currentSelectionInTree;

                int noOfSelections = treeSel.size();

                Object firstElement = treeSel.getFirstElement();

                if (firstElement instanceof ServerImpl) {

                    Connection connection =
                            ((ServerImpl) firstElement).getConnection();

                    boolean isConnected = false;

                    if (connection != null) {
                        try {
                            isConnected = connection.isConnected();

                        } catch (ConnectionException e) {

                            e.printStackTrace();
                        }
                    }
                    /*
                     * If the server is connected, enable the Disconnect,New
                     * Server, Refresh,Delete Actions.
                     */
                    /*
                     * If the server is disconnected, enable the connect,New
                     * Server, Refresh,Delete Actions.
                     */
                    if ((CONNECT_SERVER_ACTION_ID.equals(viewPluginAction
                            .getId()) && noOfSelections == 1 && !isConnected)
                            || (DISCONNECT_SERVER_ACTION_ID
                                    .equals(viewPluginAction.getId())
                                    && noOfSelections == 1 && isConnected)
                            || (REFRESH_SERVER_ACTION_ID
                                    .equals(viewPluginAction.getId())
                                    && noOfSelections == 1 && isConnected)
                            || DELETE_SERVER_ACTION_ID.equals(viewPluginAction
                                    .getId())
                            || NEW_SERVER_ACTION_ID.equals(viewPluginAction
                                    .getId())) {

                        viewPluginAction.setEnabled(true);
                    }

                } else if (firstElement instanceof ServerContainerImpl) {

                    if (NEW_SERVER_ACTION_ID.equals(viewPluginAction.getId())) {
                        /*
                         * If Server Container is the selection then only enable
                         * the New Server Action.
                         */
                        viewPluginAction.setEnabled(true);
                    }
                }
            }
        }
    }

    @Override
    public void init(IViewPart view) {
        /*
         * initialise the common navigator
         */
        if (view instanceof CommonNavigator) {
            navigator = (CommonNavigator) view;
        }
    }
}
