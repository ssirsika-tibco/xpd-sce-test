/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Zoom action
 * 
 * @author njpatel
 * 
 */
public class ZoomAction extends Action {

    private PartListener listener;

    private ZoomManager zoomManager;

    private IAction action;

    private final ZoomType type;

    public static final String ZOOM_LEVEL = "zoomlevel"; //$NON-NLS-1$

    private boolean oldEnabled;

    private ActionZoomListener zoomListener;

    public enum ZoomType {
        IN, OUT, FIT_ALL, FIT_WIDTH, FIT_HEIGHT;
    }

    public ZoomAction(IWorkbenchWindow window, ZoomType type) {
        this.type = type;
        setEnabled(false);
        listener = new PartListener();

        window.getActivePage().addPartListener(listener);

    }

    public void update() {
        if (action != null && oldEnabled != action.isEnabled()) {
            firePropertyChange(IAction.ENABLED, oldEnabled, action.isEnabled());
            oldEnabled = action.isEnabled();
        }
    }

    @Override
    public void run() {
        if (action != null) {
            double oldZoom = zoomManager.getZoom();
            action.run();
            firePropertyChange(ZOOM_LEVEL, oldZoom, zoomManager.getZoom());
            oldEnabled = isEnabled();
        }
    }

    @Override
    public boolean isEnabled() {
        if (action != null) {
            return action.isEnabled();
        }
        return super.isEnabled();
    }

    private class PartListener implements IPartListener {

        @Override
        public void partActivated(IWorkbenchPart part) {

            /*
             * If a view is selected then check if there is an active editor and
             * use that as the context for this action.
             */
            if (part instanceof IViewPart) {
                part = getActiveEditor(part.getSite());
            }

            if (part != null) {
                zoomManager = part.getAdapter(ZoomManager.class);
                if (zoomManager != null) {
                    switch (type) {
                    case IN:
                        action = new ZoomInAction(zoomManager);
                        zoomListener = new ActionZoomListener();
                        zoomManager.addZoomListener(zoomListener);
                        break;
                    case OUT:
                        action = new ZoomOutAction(zoomManager);
                        zoomListener = new ActionZoomListener();
                        zoomManager.addZoomListener(zoomListener);
                        break;
                    case FIT_ALL:
                    case FIT_WIDTH:
                    case FIT_HEIGHT:
                        action = new ZoomToFitAction(zoomManager, type);
                        break;

                    }

                    setEnabled(action != null);
                }
            } else {
                setEnabled(false);
            }
        }

        /**
         * Get the active editor.
         * 
         * @param site
         * @return
         */
        private IEditorPart getActiveEditor(IWorkbenchPartSite site) {
            if (site != null && site.getPage() != null) {
                return site.getPage().getActiveEditor();
            }
            return null;
        }

        @Override
        public void partBroughtToTop(IWorkbenchPart part) {
            // Nothing to do
        }

        @Override
        public void partClosed(IWorkbenchPart part) {
            // Nothing to do
        }

        @Override
        public void partDeactivated(IWorkbenchPart part) {
            if (zoomListener != null) {
                zoomManager.removeZoomListener(zoomListener);
                zoomListener = null;
            }
            zoomManager = null;
            action = null;
            setEnabled(false);
        }

        @Override
        public void partOpened(IWorkbenchPart part) {
            // Nothing to do
        }
    }

    private class ActionZoomListener implements ZoomListener {

        @Override
        public void zoomChanged(double zoom) {
            update();
        }

    }

    private static class ZoomToFitAction extends Action {
        private final ZoomManager manager;

        private final ZoomType type;

        public ZoomToFitAction(ZoomManager manager, ZoomType type) {
            this.manager = manager;
            this.type = type;
        }

        @Override
        public void run() {
            String zoom = null;

            switch (type) {
            case FIT_ALL:
                zoom = ZoomManager.FIT_ALL;
                break;
            case FIT_WIDTH:
                zoom = ZoomManager.FIT_WIDTH;
                break;
            case FIT_HEIGHT:
                zoom = ZoomManager.FIT_HEIGHT;
                break;
            }

            if (zoom != null) {
                manager.setZoomAsText(zoom);
            }
        }

    }

}
