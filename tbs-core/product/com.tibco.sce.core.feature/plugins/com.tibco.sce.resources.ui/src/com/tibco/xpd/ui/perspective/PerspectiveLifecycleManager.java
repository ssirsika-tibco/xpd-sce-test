/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.ui.perspective;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IPerspectiveListener4;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;

/**
 * Manages the perspective lifecycle extensions, adding and removing workbench
 * listener and delegating perspective events.
 * 
 * This class should not be used directly or extended by clients.
 * 
 * @author jarciuch
 * @since 1 Nov 2014
 */
public class PerspectiveLifecycleManager {

    private IWindowListener windowListener;

    /**
     * Map of workbench window to perspective listener delegate.
     */
    private Map<IWorkbenchWindow, IPerspectiveListener> perspectiveDelegatingListeners =
            new HashMap<>();

    /**
     * Map of perspectiveId to a list of all relevant perspective listeners (as
     * retrieved from extensions). Also all allPerspectivesListeners are
     * included in every list.
     */
    private Map<String, List<IPerspectiveLifecycleListener>> specificPerspectiveListeners;

    /**
     * Perspective listeners for all perspectives.
     */
    private LinkedList<IPerspectiveLifecycleListener> allPerspectivesListeners;

    /**
     * Private constructor to prevent instantiation.
     */
    private PerspectiveLifecycleManager() {
        initialize();
    }

    /**
     * Holds a singleton instance of this class to not have lazy initialization
     * without synchronization.
     */
    private static class InstanceHolder {
        private static final PerspectiveLifecycleManager INSTANCE =
                new PerspectiveLifecycleManager();

    }

    /**
     * Returns and instance of this manager.
     * 
     * @return an singleton instance of this manager.
     */
    public static PerspectiveLifecycleManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Removes workbench window listener,
     */
    public void dispose() {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                IWorkbench workbench = PlatformUI.getWorkbench();
                if (workbench != null && windowListener != null) {
                    workbench.removeWindowListener(windowListener);
                    windowListener = null;
                }
            }
        });
    }

    /**
     * Initializes manager and register workbench window listener and indirectly
     * perspective listeners.
     */
    private void initialize() {
        createDelegatesFromExtensions();
        // Attach listeners.
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                IWorkbench workbench = PlatformUI.getWorkbench();
                if (workbench != null) {
                    addWorkbenchWindowListener(workbench);
                }
            }
        });
    }

    /**
     * Adds a workbench listener for workbench windows so that perspective
     * listeners can be registered and unregistered for opened and closed
     * windows.
     * 
     * @param workbench
     *            the workbench.
     */
    private void addWorkbenchWindowListener(IWorkbench workbench) {
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
                        perspectiveDelegatingListeners.get(window);
                if (listener != null) {
                    window.removePerspectiveListener(listener);
                    perspectiveDelegatingListeners.remove(window);
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
     * Creates and attaches a perspective listener delegate to a workbench
     * window so that filtered events on a specific perspective are delegated to
     * appropriate listeners (defined in extension).
     * 
     * @param window
     *            the workbench window.
     */
    private void createPerpectiveListener(final IWorkbenchWindow window) {
        IPerspectiveListener delegate = new IPerspectiveListener4() {

            @Override
            public void perspectiveChanged(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective, String changeId) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectiveChanged(page, perspective, changeId);
                    }
                }

            }

            @Override
            public void perspectiveActivated(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectiveActivated(page, perspective);
                    }
                }
            }

            @Override
            public void perspectiveChanged(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective,
                    IWorkbenchPartReference partRef, String changeId) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectiveChanged(page,
                                perspective,
                                partRef,
                                changeId);
                    }
                }
            }

            @Override
            public void perspectiveSavedAs(IWorkbenchPage page,
                    IPerspectiveDescriptor oldPerspective,
                    IPerspectiveDescriptor newPerspective) {
                List<IPerspectiveLifecycleListener> ds =
                        specificPerspectiveListeners
                                .get(oldPerspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectiveSavedAs(page,
                                oldPerspective,
                                newPerspective);
                    }
                }
            }

            @Override
            public void perspectiveOpened(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectiveOpened(page, perspective);
                    }
                }

            }

            @Override
            public void perspectiveDeactivated(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectiveDeactivated(page, perspective);
                    }
                }
            }

            @Override
            public void perspectiveClosed(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectiveClosed(page, perspective);
                    }
                }
            }

            @Override
            public void perspectivePreDeactivate(IWorkbenchPage page,
                    IPerspectiveDescriptor perspective) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener d : ds) {
                        d.perspectivePreDeactivate(page, perspective);
                    }
                }
            }
        };
        window.addPerspectiveListener(delegate);
        perspectiveDelegatingListeners.put(window, delegate);
        triggerInitializePerspectiveEvent(window);
    }

    /**
     * Triggers "initialized" event (calls
     * {@link IPerspectiveLifecycleListener#perspectiveInitialized(IWorkbenchWindow, IWorkbenchPage, IPerspectiveDescriptor)}
     * ) on the relevant perspective listeners.
     * 
     * @param window
     *            the context workbench window.
     */
    private void triggerInitializePerspectiveEvent(final IWorkbenchWindow window) {
        IWorkbenchPage page = window.getActivePage();
        if (page != null) {
            IPerspectiveDescriptor perspective = page.getPerspective();
            if (perspective != null) {
                List<IPerspectiveLifecycleListener> ds =
                        getListeners(perspective.getId());
                if (ds != null) {
                    for (IPerspectiveLifecycleListener ls : ds) {
                        ls.perspectiveInitialized(window, page, perspective);
                    }
                }
            }
        }
    }

    private List<IPerspectiveLifecycleListener> getListeners(
            String perspectiveId) {
        List<IPerspectiveLifecycleListener> specificListeners =
                specificPerspectiveListeners.get(perspectiveId);
        if (specificListeners != null) {
            return specificListeners;
        } else {
            return allPerspectivesListeners;
        }

    }

    /**
     * Creates a map of perspectiveId to a list of perspective listeners from
     * the extension (com.tibco.xpd.resources.ui.perspectiveLifecycleListener).
     */
    private void createDelegatesFromExtensions() {
        LinkedHashMap<String, List<IPerspectiveLifecycleListener>> specificPerspectiveListeners =
                new LinkedHashMap<>();
        LinkedList<IPerspectiveLifecycleListener> allPerspectivesListeners =
                new LinkedList<IPerspectiveLifecycleListener>();

        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                extensionRegistry
                        .getConfigurationElementsFor(XpdResourcesUIActivator.ID,
                                "perspectiveLifecycleListener");
        for (IConfigurationElement element : configurationElements) {
            if ("perspectiveListener".equals(element.getName())) {
                try {
                    IPerspectiveLifecycleListener listener =
                            (IPerspectiveLifecycleListener) element
                                    .createExecutableExtension("class");

                    IConfigurationElement[] includes =
                            element.getChildren("include");
                    if (includes.length > 0) {
                        for (IConfigurationElement include : includes) {
                            String id = include.getAttribute("perspectiveId");
                            Assert.isNotNull(id);
                            List<IPerspectiveLifecycleListener> ds =
                                    specificPerspectiveListeners.get(id);
                            if (ds == null) {
                                ds = new LinkedList<>();
                                specificPerspectiveListeners.put(id, ds);
                            }
                            if (!ds.contains(listener)) {
                                ds.add(listener);
                            }
                        }
                    } else {
                        allPerspectivesListeners.add(listener);
                    }
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        for (List<IPerspectiveLifecycleListener> specificListeners : specificPerspectiveListeners
                .values()) {
            specificListeners.addAll(allPerspectivesListeners);
        }
        this.specificPerspectiveListeners = specificPerspectiveListeners;
        this.allPerspectivesListeners = allPerspectivesListeners;
    }
}
