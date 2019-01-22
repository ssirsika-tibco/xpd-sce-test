package com.tibco.xpd.n2.live.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IPerspectiveListener4;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    /**
     * The live dev plugin ID.
     */
    public static final String PLUGIN_ID = "com.tibco.xpd.n2.live"; //$NON-NLS-1$

    /**
     * Live dev perspective ID.
     */
    public static final String LIVE_DEV_PERSPECTIVE_ID =
            "com.tibco.xpd.n2.LiveDevelopment"; //$NON-NLS-1$

    /**
     * Openspace view browser refresh icon.
     */
    public static final String REFRESH_ICON = "icons/ReloadOpenspace.png"; //$NON-NLS-1$

    /**
     * Openspace view external browser launch icon.
     */
    public static final String LAUNCH_ICON =
            "icons/LaunchInExternalBrowser.png"; //$NON-NLS-1$

    /**
     * Live dev icon.
     */
    public static final String LIVE_DEV_ICON = "icons/LiveDevPerspective.png"; //$NON-NLS-1$

    /**
     * Copy URL to Clipboard icon.
     */
    public static final String COPY_URL_ICON = "icons/CopyOpenspceViewURL.png"; //$NON-NLS-1$

    /**
     * The plugin instance.
     */
    private static Activator plugin;

    /**
     * Cache of perspective listeners registered for each workbench window.
     */
    private Map<IWorkbenchWindow, IPerspectiveListener> perspectiveListeners;

    /**
     * Flags to indicate if we are in live dev mode for each window. This is
     * updated via a perspective listener.
     */
    private Map<IWorkbenchWindow, Boolean> liveDevFilterOn;

    /**
     * The workbench window listener.
     */
    private IWindowListener windowListener;

    /**
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *      #start(org.osgi.framework.BundleContext )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        perspectiveListeners = new HashMap<>();
        liveDevFilterOn = new HashMap<>();
        initPerspectiveListener();
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *      #initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     * 
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        reg.put(REFRESH_ICON,
                imageDescriptorFromPlugin(PLUGIN_ID, REFRESH_ICON));
        reg.put(LAUNCH_ICON, imageDescriptorFromPlugin(PLUGIN_ID, LAUNCH_ICON));
        reg.put(LIVE_DEV_ICON,
                imageDescriptorFromPlugin(PLUGIN_ID, LIVE_DEV_ICON));
        reg.put(COPY_URL_ICON,
                imageDescriptorFromPlugin(PLUGIN_ID, COPY_URL_ICON));
    }

    /**
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *      #stop(org.osgi.framework.BundleContext )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        if (windowListener != null) {
            IWorkbench workbench = PlatformUI.getWorkbench();
            if (workbench != null) {
                workbench.removeWindowListener(windowListener);
            }
        }
        plugin = null;
        super.stop(context);
    }

    /**
     * Sets up a perspective listener and ensures that the isLiveDevFilterOn
     * flag is initialised and updated on perspective changes. This is done in a
     * Display asyncExec to ensure that the workbench display is available prior
     * to the initialisation code being run.
     */
    private void initPerspectiveListener() {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                IWorkbench workbench = PlatformUI.getWorkbench();
                if (workbench != null) {
                    addWorkbenchListener(workbench);
                }
            }
        });
    }

    /**
     * Adds a listener for workbench windows so that perspective listeners can
     * be registered and unregistered.
     * 
     * @param workbench
     *            The workbench.
     */
    protected void addWorkbenchListener(IWorkbench workbench) {
        windowListener = new IWindowListener() {

            @Override
            public void windowOpened(final IWorkbenchWindow window) {
                createPerpectiveListener(window);
            }

            @Override
            public void windowDeactivated(IWorkbenchWindow window) {
            }

            @Override
            public void windowClosed(IWorkbenchWindow window) {
                IPerspectiveListener listener =
                        perspectiveListeners.get(window);
                if (listener != null) {
                    window.removePerspectiveListener(listener);
                    perspectiveListeners.remove(window);
                }
            }

            @Override
            public void windowActivated(IWorkbenchWindow window) {
            }
        };
        workbench.addWindowListener(windowListener);
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        if (window != null) {
            createPerpectiveListener(window);
        }
    }

    /**
     * Shows the live dev info dialog unless the "Do not notify me again"
     * checkbox has been previously selected.
     * 
     * @param shell
     *            The current shell.
     */
    private void showLiveDevDialog(Shell shell) {
        String key = "liveDevPerspectiveInfoDialog"; //$NON-NLS-1$
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        String value = store.getString(key);
        if (!MessageDialogWithToggle.ALWAYS.equals(value)) {
            String title = Messages.Activator_PerspectiveDialogTitle;
            String message = Messages.Activator_PerspectiveDialogMessage;
            String toggleMessage = Messages.Activator_PerspectiveDialogToggle;
            MessageDialogWithToggle.openInformation(shell,
                    title,
                    message,
                    toggleMessage,
                    false,
                    store,
                    key);
        }
    }

    /**
     * Creates and attaches a perspective listener to a window so that
     * "liveDevFilterOn" flags can be updated on perspective change.
     * 
     * @param window
     *            The workbench window.
     */
    private void createPerpectiveListener(final IWorkbenchWindow window) {
        IPerspectiveListener listener = new IPerspectiveListener4() {

            @Override
            public void perspectiveChanged(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective, String changeId) {
            }

            @Override
            public void perspectiveActivated(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
                boolean liveDevSelected =
                        LIVE_DEV_PERSPECTIVE_ID.equals(perspective.getId());
                if (liveDevSelected) {
                    showLiveDevDialog(window.getShell());
                }
                if (!Boolean.valueOf(liveDevSelected)
                        .equals(liveDevFilterOn.get(window))) {
                    liveDevFilterOn.put(window, liveDevSelected);
                    refreshViewer(page);
                }

                /*
                 * Activate openspace view so that the property section is
                 * displayed on switching to Live Dev Perspective
                 */
                if (LIVE_DEV_PERSPECTIVE_ID.equals(perspective.getId())) {
                    try {
                        window.getActivePage()
                                .showView(LiveDevPerspectiveFactory.LIVE_DEV_OPENSPACE_VIEW_ID);
                    } catch (PartInitException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void perspectiveOpened(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
            }

            @Override
            public void perspectiveClosed(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
            }

            @Override
            public void perspectiveDeactivated(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
            }

            @Override
            public void perspectiveSavedAs(IWorkbenchPage page,
                    IPerspectiveDescriptor oldPerspective,
                    IPerspectiveDescriptor newPerspective) {
            }

            @Override
            public void perspectiveChanged(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective,
                    IWorkbenchPartReference partRef, String changeId) {
            }

            /**
             * XPD-6814: Ensure that when the live dev perspective is
             * deactivated then focus is moved to another workbench part. If
             * this isn't done the live dev properties view is still shown after
             * the perspective switch.
             * 
             * @see org.eclipse.ui.IPerspectiveListener4#perspectivePreDeactivate(org.eclipse.ui.IWorkbenchPage,
             *      org.eclipse.ui.IPerspectiveDescriptor)
             * 
             * @param page
             * @param perspective
             */
            @Override
            public void perspectivePreDeactivate(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
                boolean liveDevSelected =
                        LIVE_DEV_PERSPECTIVE_ID.equals(perspective.getId());
                if (liveDevSelected) {
                    IEditorPart activeEditor = page.getActiveEditor();
                    if (activeEditor == null) {
                        IViewReference[] views = page.getViewReferences();
                        for (IViewReference ref : views) {
                            if (!"com.tibco.xpd.n2.live.openspaceView" //$NON-NLS-1$
                            .equals(ref.getId())) {
                                IViewPart view = ref.getView(false);
                                if (view != null) {
                                    view.setFocus();
                                }
                            }
                        }
                    } else {
                        activeEditor.setFocus();
                    }
                }
            }
        };
        window.addPerspectiveListener(listener);
        perspectiveListeners.put(window, listener);
        initPerpectiveState(window);
    }

    /**
     * Refreshed the project explorer tree. This is called after any perspective
     * change.
     * 
     * @param page
     *            The navigator workbench page.
     */
    private void refreshViewer(IWorkbenchPage page) {
        IViewPart view = page.findView(IPageLayout.ID_PROJECT_EXPLORER);
        if (view instanceof CommonNavigator) {
            CommonNavigator cn = (CommonNavigator) view;
            cn.getCommonViewer().refresh();
        }
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Checks if live dev mode is turned on for a given workbench window.
     * 
     * @param window
     *            The workbench window.
     * @return Flag to indicate if live dev mode is on for the given window.
     */
    public boolean isLiveDevFilterOn(IWorkbenchWindow window) {
        return Boolean.TRUE.equals(liveDevFilterOn.get(window));
    }

    /**
     * Initialises the "liveDevFilterOn" flag for the given window.
     * 
     * @param window
     *            The workbench window.
     */
    private void initPerpectiveState(final IWorkbenchWindow window) {
        IWorkbenchPage page = window.getActivePage();
        if (page != null) {
            IPerspectiveDescriptor perspective = page.getPerspective();
            if (perspective != null) {
                liveDevFilterOn.put(window,
                        LIVE_DEV_PERSPECTIVE_ID.equals(perspective.getId()));
            }
        }
    }
}
