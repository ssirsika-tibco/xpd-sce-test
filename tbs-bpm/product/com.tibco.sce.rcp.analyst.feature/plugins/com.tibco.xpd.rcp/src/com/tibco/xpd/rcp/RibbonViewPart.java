/*
 * Copyright (c) TIBCO Software Inc 2004, 2018. All rights reserved.
 */

package com.tibco.xpd.rcp;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.ViewPart;

import com.tibco.xpd.rcp.ribbon.Ribbon;

/**
 * Sid XPD-8302 This is the new ViewPart that contributes the Analyst Ribbon
 * control to the view.
 * 
 * This is now necessary as Eclipse v4 e4 framework will not allow us to create
 * our on top level SWT content.
 *
 * @author aallway
 * @since 23 Aug 2018
 */
public class RibbonViewPart extends ViewPart {

    public static final String RIBBON_CONTROL_KEY =
            "com.tibco.xpd.ribbon.control.key"; //$NON-NLS-1$

    public static final String RIBBON_CONTROL_ID =
            "com.tibco.xpd.ribbon.control.id"; //$NON-NLS-1$

    public RibbonViewPart() {
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     *
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {

        Composite root = new Composite(parent, SWT.NONE);

        root.setData(RIBBON_CONTROL_KEY, RIBBON_CONTROL_ID);

        FillLayout layout = new FillLayout();
        root.setLayout(layout);

        root.setBackground(new Color(null, 255, 255, 255));

        IWorkbenchWindow activeWorkbenchWindow =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        Ribbon ribbon = new Ribbon(activeWorkbenchWindow);

        ribbon.createRibbon(root);

        /* Hook into global action handlers (cut copy past etc. */
        IActionBars actionBars = getViewSite().getActionBars();

        /*
         * Sid XPD-8302 - now that the Ribbon is a separate view part it means
         * that when the user uses global actions in the ribbon it is the Ribbon
         * control that gets the request to perform the action NOT the selected
         * editor.
         * 
         * This is a problem and the way we work around this is this
         * DelegateGlobalAction action.
         *
         * When the ribbon is created one of these delegates is created for each
         * of the global actions in the ribbon. When these are triggered they
         * trigger the equivalent action in the last active editor
         */
        Map<String, IAction> globalactions = RCPActivator.getGlobalactions();

        for (Entry<String, IAction> globalAction : globalactions.entrySet()) {
            actionBars.setGlobalActionHandler(globalAction.getKey(),
                    new DelegateGlobalAction(globalAction.getKey(),
                            globalAction.getValue()));

        }

    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     *
     */
    @Override
    public void setFocus() {
    }

    /**
     * Sid XPD-8302 - now that the Ribbon is a separate view part it means that
     * when the user uses global actions in the ribbon it is the Ribbon control
     * that gets the request to perform the action NOT the selected editor.
     * 
     * This is a problem and the way we work around this is this
     * DelegateGlobalAction action.
     *
     * When the ribbon is created one of these delegates is created for each of
     * the global actions in the ribbon.
     * 
     * When the action is triggered then it gets the currently 'active' editor,
     * looks thru it's action-bars for IT'S implementation of the given global
     * action and then sets focus back on that editor and executes the action.
     * 
     * Not very nice I know, but once again Eclipse 4.7 framework has made life
     * hard for the RCP use case.
     *
     * @author aallway
     * @since 6 Sep 2018
     */
    private static class DelegateGlobalAction extends Action {

        String actionId;

        IAction delegateAction;

        public DelegateGlobalAction(String id, IAction iAction) {
            this.actionId = id;
            delegateAction = iAction;

            /*
             * Track enablement of the action. When the activate editor sets the
             * enablement of the action, then this will be called and we will
             * set OUR action's enablement according to the current Editor's
             * enablement of the action.
             */
            delegateAction
                    .addPropertyChangeListener(new IPropertyChangeListener() {

                        @Override
                        public void propertyChange(PropertyChangeEvent event) {

                            if (IAction.ENABLED.equals(event.getProperty())) {
                                /*
                                 * Set our enablement from the last enablement
                                 * from the action by an (the active) editor
                                 */
                                if (event
                                        .getSource() instanceof RetargetAction) {

                                    RetargetAction eventAction =
                                            (RetargetAction) event.getSource();

                                    if (eventAction
                                            .getActivePart() instanceof IEditorPart
                                            && event.getNewValue() instanceof Boolean) {
                                        DelegateGlobalAction.this.setEnabled(
                                                (Boolean) event.getNewValue());
                                    }
                                }
                            }
                        }
                    });
        }

        /**
         * @see org.eclipse.jface.action.Action#run()
         *
         */
        @Override
        public void run() {
            IEditorPart activeEditor = getActiveEditor();
            if (activeEditor != null) {

                IAction editorAction = getEditorAction();

                activeEditor.setFocus();

                if (editorAction != null) {
                    editorAction.run();
                }
            }
        }

        /**
         * 
         * @return the active editor's equivalent action to the action we
         *         represent
         */
        private IAction getEditorAction() {
            IEditorPart activeEditor = getActiveEditor();

            if (activeEditor != null) {
                IEditorSite editorSite = activeEditor.getEditorSite();

                if (editorSite != null) {
                    IActionBars actionBars = editorSite.getActionBars();

                    if (actionBars != null) {
                        IAction globalActionHandler =
                                actionBars.getGlobalActionHandler(actionId);

                        return globalActionHandler;
                    }
                }
            }

            return null;
        }

        /**
         * Get the last active editor.
         *
         * @return last active editor.
         */
        private IEditorPart getActiveEditor() {
            if (PlatformUI.getWorkbench() != null
                    && PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow() != null
                    && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage() != null) {

                IEditorPart activeEditor =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage().getActiveEditor();

                return activeEditor;
            }

            return null;
        }

    }
}
