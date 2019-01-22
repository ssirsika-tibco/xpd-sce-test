/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.ui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.quickfixtooltip.api.QuickFixPopupStandalone;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.validation.IValidationListener;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.ValidationEvent;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (14 Jan 2010)
 */
public class QuickFixPopupAction implements IViewActionDelegate {

    private static final String QUICKFIX_ACTION_ID =
            "org.eclipse.jdt.ui.edit.text.java.correction.assist.proposals";

    private Object selectedObject = null;

    private IAction proxyAction = null;

    /*
     * SID: THIS MAY LOOK ODD (That validaitonEndListener is NOT type
     * ValidationListener) but it is very important that we don't mention
     * ValidationListenerAT ALL until after the validation manager plugin is
     * loaded via the validation builder). Otherwise we cause the validaiton
     * plugins to be loaded on the Main thread and that can deadlock with the
     * validaiton builder.
     */
    private Object validationEndListener = null;

    private IViewPart view;

    public QuickFixPopupAction() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
     */
    @Override
    public void init(IViewPart view) {
        this.view = view;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate2#dispose()
     */
    public void dispose() {
        removeValidationListener();
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run(IAction action) {
        Collection<IMarker> markers = getProblemMarkers(selectedObject);

        if (!markers.isEmpty()) {

            Control cursorControl = Display.getCurrent().getCursorControl();

            Point location = getPopupLocation(cursorControl);

            QuickFixPopupStandalone quickFixPopupStandalone =
                    new QuickFixPopupStandalone(markers, location);

            quickFixPopupStandalone.openPopup();
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action
     * .IAction, org.eclipse.swt.widgets.Event)
     */
    public void runWithEvent(IAction action, Event event) {
        run(action);
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        /*
         * Save the action that is the REAL action that wraps our action - it is
         * THAT that we have to enable etc
         */
        proxyAction = action;

        proxyAction.setActionDefinitionId(QUICKFIX_ACTION_ID);

        view.getViewSite().getActionBars()
                .setGlobalActionHandler(QUICKFIX_ACTION_ID, proxyAction);

        internalSelectionChanged(selection);

        return;
    }

    /**
     * @param selection
     */
    protected void internalSelectionChanged(ISelection selection) {
        boolean enabled = false;
        selectedObject = null;

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection) selection;

            if (ssel.size() == 1) {
                selectedObject = ssel.getFirstElement();

                enabled = calulateEnabledState();
            }
        }

        if (proxyAction != null) {
            proxyAction.setEnabled(enabled);
        }

        /*
         * MUST load the validation listener AS LATAE as possible to prevent
         * deadlocks with validation buidler.
         */
        if (validationEndListener == null && selectedObject != null) {
            addValidationListener();
        }

        return;
    }

    /**
     * 
     */
    protected void addValidationListener() {
        /*
         * Add validation listener on first update. If you do this in init() you
         * can get lock-ups with class loaders!
         */
        if (validationEndListener == null) {
            /*
             * SID: THIS MAY LOOK ODD (That validaitonEndListener is NOT type
             * ValidationListener) but it is very important that we don't
             * mention ValidationListenerAT ALL until after the validation
             * manager plugin is loaded via the validation builder). Otherwise
             * we cause the validaiton plugins to be loaded on the Main thread
             * and that can deadlock with the validaiton builder.
             */
            validationEndListener = new ValidationListener(this);
            ValidationActivator
                    .getDefault()
                    .addValidationListener((IValidationListener) validationEndListener);
        }
    }

    /**
     * 
     */
    protected void removeValidationListener() {
        if (validationEndListener != null) {
            /*
             * SID: THIS MAY LOOK ODD (That validaitonEndListener is NOT type
             * ValidationListener) but it is very important that we don't
             * mention ValidationListenerAT ALL until after the validation
             * manager plugin is loaded via the validation builder). Otherwise
             * we cause the validaiton plugins to be loaded on the Main thread
             * and that can deadlock with the validaiton builder.
             */
            ValidationActivator
                    .getDefault()
                    .removeValidationListener((IValidationListener) validationEndListener);
            validationEndListener = null;
        }
    }

    /**
     * @return true if view problems and fixes action should be enabled for
     *         current selection.
     */
    private boolean calulateEnabledState() {
        String actionTooltip = "View Problems";

        boolean enabled = false;
        if (selectedObject != null) {
            Collection<IMarker> markers = getProblemMarkers(selectedObject);
            if (!markers.isEmpty()) {
                enabled = true;

                int errorCount = 0;
                int warnCount = 0;

                for (Iterator iterator = markers.iterator(); iterator.hasNext();) {
                    IMarker marker = (IMarker) iterator.next();
                    int severity = marker.getAttribute(IMarker.SEVERITY, -1);

                    /* Don't enable just for Info markers. */
                    if (severity == IMarker.SEVERITY_ERROR) {
                        errorCount++;
                    } else if (severity == IMarker.SEVERITY_WARNING) {
                        warnCount++;
                    }
                }

                if (errorCount > 0 || warnCount > 0) {
                    actionTooltip = "View...";
                    actionTooltip += "\n"; //$NON-NLS-1$
                    if (errorCount > 0) {
                        actionTooltip += "  "; //$NON-NLS-1$
                        actionTooltip +=
                                String.format("%1$d Error(s) on selected element (or children).",
                                        errorCount);
                    }

                    if (warnCount > 0) {
                        if (errorCount > 0) {
                            actionTooltip += "\n"; //$NON-NLS-1$
                        }
                        actionTooltip += "  "; //$NON-NLS-1$
                        actionTooltip +=
                                String.format("%1$d Warning(s) on selected element (or children).",
                                        warnCount);
                    }
                }
            }
        }

        if (proxyAction != null) {
            proxyAction.setToolTipText(actionTooltip);
        }

        return enabled;
    }

    /**
     * @param selection
     * 
     * @return markers for selection.
     */
    private Collection<IMarker> getProblemMarkers(Object selection) {
        List<IMarker> markers = new ArrayList<IMarker>();

        IResource resource = getIResource(selection);
        if (resource != null && resource.isAccessible()) {
            try {
                /*
                 * Get the markers for this resource.
                 */
                IMarker[] marks =
                        resource.findMarkers(IMarker.PROBLEM,
                                true,
                                IResource.DEPTH_ZERO);

                /*
                 * If the selection is an EObject within a resource then include
                 * the markers only for it.
                 */
                if (marks != null) {
                    EObject eo = getEObject(selection);
                    if (eo != null) {
                        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
                        if (wc != null) {
                            List<IMarker> descendentMarkers =
                                    new ArrayList<IMarker>();

                            for (IMarker marker : marks) {
                                EObject markerTargetEObject = null;
                                if (wc instanceof AbstractWorkingCopy) {
                                    markerTargetEObject =
                                            ((AbstractWorkingCopy) wc)
                                                    .getTarget(marker);
                                } else {
                                    String uri =
                                            marker.getAttribute(IMarker.LOCATION,
                                                    ""); //$NON-NLS-1$
                                    if (uri != null && !uri.trim().isEmpty()) {
                                        markerTargetEObject =
                                                wc.resolveEObject(uri);
                                    }
                                }

                                if (markerTargetEObject != null) {
                                    if (eo.equals(markerTargetEObject)) {
                                        markers.add(marker);
                                    } else if (EcoreUtil.isAncestor(eo,
                                            markerTargetEObject)) {
                                        /*
                                         * When marker is raised against one of
                                         * the selected object's descendents
                                         * then add to a separate list.
                                         */
                                        descendentMarkers.add(marker);
                                    }
                                }
                            } /* Next marker */

                            /*
                             * TODO Not sure what to do here - include
                             * decendent's markers or not?
                             * 
                             * Things with lots of children may get swamped, but
                             * at least it would be consistent with resoruce
                             * level.
                             * 
                             * Maybe we could look at setting our own sort order
                             * for quick fix tooltip to group the selected
                             * object to the head of list.
                             * 
                             * To remind me I'll include all fort now then we
                             * can look at it again later.
                             */
                            markers.addAll(descendentMarkers);
                        }

                    } else {
                        for (IMarker marker : marks) {
                            markers.add(marker);
                        }
                    }
                }

            } catch (CoreException e) {
                XpdResourcesUIActivator.getDefault().getLogger()
                        .error(e, "Error getting markers for resource"); //$NON-NLS-1$
            }
        }

        /*
         * For now remove everything except Error and Warning markers, because
         * we don't show 'Info' level decoration on project explorer selection
         * it can look weird that the quick fix action is still enabled.
         */
        for (Iterator iterator = markers.iterator(); iterator.hasNext();) {
            IMarker marker = (IMarker) iterator.next();
            int severity = marker.getAttribute(IMarker.SEVERITY, -1);

            /* Don't enable just for Info markers. */
            if (severity != IMarker.SEVERITY_ERROR
                    && severity != IMarker.SEVERITY_WARNING) {
                iterator.remove();
            }
        }

        return markers;
    }

    /**
     * @param selection
     * 
     * @return selection if it's a resource OR the closest resource ancestor for
     *         selection.
     */
    private IResource getIResource(Object selection) {
        IResource resource = null;

        if (selection != null) {
            if (selection instanceof IResource) {
                resource = (IResource) selection;
            }

            if (resource == null) {
                if (selection instanceof IAdaptable) {
                    IAdaptable adaptable = (IAdaptable) selection;

                    Object o = adaptable.getAdapter(IResource.class);
                    if (o instanceof IResource) {
                        resource = (IResource) o;
                    }
                }
            }

            if (resource == null) {
                EObject eo = getEObject(selection);
                if (eo != null) {
                    resource = WorkingCopyUtil.getFile(eo);
                }
            }
        }

        return resource;
    }

    private EObject getEObject(Object selection) {
        EObject eo = null;

        if (selection instanceof EObject) {
            eo = (EObject) selection;

        } else if (selection instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) selection;

            Object o = adaptable.getAdapter(EObject.class);
            if (o instanceof EObject) {
                eo = (EObject) o;
            }

        }

        return eo;
    }

    /**
     * @param focusControl
     * 
     * @return Get most appropriate location for the quick search popup - If the
     *         mouse cursor is not in the bounds of the control for the view
     *         we're doing a search in then use closest point on border
     */
    private Point getPopupLocation(Control focusControl) {
        Point cursorLoc = Display.getDefault().getCursorLocation();

        if (focusControl != null) {
            /*
             * Bit of a fudge here - FigureCanvas control on GEF editors does
             * not give accurate result for toDisplay() - so use it's parent
             * instead.
             */
            if (focusControl instanceof FigureCanvas) {
                focusControl = focusControl.getParent();
            }

            if (focusControl != null) {
                Rectangle bnds = focusControl.getBounds();

                Point loc = focusControl.toDisplay(0, 0);

                bnds.x = loc.x;
                bnds.y = loc.y;

                if (!bnds.contains(cursorLoc)) {

                    if (cursorLoc.x > (bnds.x + bnds.width)) {
                        cursorLoc.x = (bnds.x + bnds.width) - 32;
                    } else if (cursorLoc.x < bnds.x) {
                        cursorLoc.x = bnds.x;
                    }

                    if (cursorLoc.y > (bnds.y + bnds.height)) {
                        cursorLoc.y = (bnds.y + bnds.height) - 32;
                    } else if (cursorLoc.y < bnds.y) {
                        cursorLoc.y = bnds.y;
                    }
                }
            }
        }

        // By default return the current cursor location
        return cursorLoc;
    }

    /**
     * Update action state when validation cycle complete.
     * <p>
     * SID: NOTE***** THIS CLASS MUST REMAIN STATIC!!!! IF not it can cause
     * early load of ValidationManager bundle which then deadlocks with the
     * validaiton builder.
     * 
     * @author aallway
     * @since 3.3 (14 Jan 2010)
     */
    private static class ValidationListener implements IValidationListener {

        private final QuickFixPopupAction action;

        ValidationListener(QuickFixPopupAction action) {
            this.action = action;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.validation.IValidationListener#validationEvent(com.
         * tibco .xpd.validation.ValidationEvent)
         */
        @Override
        public void validationEvent(ValidationEvent event) {
            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {
                    if (action.proxyAction != null) {
                        action.proxyAction.setEnabled(action
                                .calulateEnabledState());
                    }
                }
            });
            return;
        }
    }

}
