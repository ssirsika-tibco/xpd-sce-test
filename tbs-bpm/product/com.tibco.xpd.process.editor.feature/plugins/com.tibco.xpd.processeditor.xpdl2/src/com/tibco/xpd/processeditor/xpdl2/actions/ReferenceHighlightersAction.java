/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution;
import com.tibco.xpd.processeditor.xpdl2.highlighting.ReferenceHighlighterEditPartListener;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Process;

/**
 * Toolbar button that toggles enablement of reference highlighting in process
 * editor
 * 
 * @author aallway
 * @since 3 Feb 2011
 */
public class ReferenceHighlightersAction extends DropDownToolbarButton {

    public static final String ACTION_ID = "ReferenceHighlightersAction"; //$NON-NLS-1$

    private ReferenceHighlighterEnableAction enableHighlighingAction;

    private AnimateHighlightEnableAction enableAnimateHighlighingAction;

    private Collection<StaticHighlighterAction> staticHighlighterActions = null;

    /**
     * @param label
     * @param image
     */
    public ReferenceHighlightersAction() {
        super(
                Messages.EnableExtraReferenceHighlightersAction_EnableHighlightOrActivateAdditional_menu,
                getStateImageDescriptor());
        setId(ACTION_ID);

        /*
         * Check for null in case createMenuManager was called during
         * construction.
         */
        if (enableHighlighingAction == null) {
            enableHighlighingAction = new ReferenceHighlighterEnableAction();
        }

        if (enableAnimateHighlighingAction == null) {
            enableAnimateHighlighingAction = new AnimateHighlightEnableAction();
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.actions.DropDownToolbarButton#createMenuManager()
     * 
     * @return
     */
    @Override
    protected MenuManager createMenuManager() {
        MenuManager menu =
                new MenuManager(
                        Messages.EnableExtraReferenceHighlightersAction_EnableHighlightOrActivateAdditional_menu,
                        getStateImageDescriptor(), null);

        staticHighlighterActions = new ArrayList<StaticHighlighterAction>();

        for (AbstractStaticHighlighterContribution staticHighlighter : ReferenceHighlighterEditPartListener
                .getStaticHighlighters()) {
            StaticHighlighterAction action =
                    new StaticHighlighterAction(staticHighlighter);
            staticHighlighterActions.add(action);
            menu.add(action);
        }

        /*
         * Create actions in case createMenuManager is called during
         * construction.
         */
        if (enableHighlighingAction == null) {
            enableHighlighingAction = new ReferenceHighlighterEnableAction();
        }

        if (enableAnimateHighlighingAction == null) {
            enableAnimateHighlighingAction = new AnimateHighlightEnableAction();
        }

        menu.add(new Separator());
        menu.add(enableHighlighingAction);

        menu.add(enableAnimateHighlighingAction);

        setFromHighlightingEnabledState(ReferenceHighlighterEditPartListener
                .isHighlightReferencingEnabled());

        /*
         * We didn't used to show selected-state on currently active static
         * highlighter items.
         */
        menu.addMenuListener(new HighlighterMenuOpenListener());

        return menu;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        toggleReferenceHighlighting();
        enableHighlighingAction.setChecked(ReferenceHighlighterEditPartListener
                .isHighlightReferencingEnabled());
        return;
    }

    /**
     * Toggle the enablement state of the reference highlighting.
     */
    private void toggleReferenceHighlighting() {
        boolean newState =
                !ReferenceHighlighterEditPartListener
                        .isHighlightReferencingEnabled();

        ReferenceHighlighterEditPartListener
                .setHighlightReferencingEnabled(newState);

        setFromHighlightingEnabledState(newState);
        return;
    }

    /**
     * @param newState
     */
    private void setFromHighlightingEnabledState(boolean newState) {
        setImageDescriptor(getStateImageDescriptor());

        if (staticHighlighterActions != null) {
            for (StaticHighlighterAction action : staticHighlighterActions) {
                action.setEnabled(newState);
            }
        }
    }

    /**
     * @return The image descriptor appropriate to toggle state.
     */
    private static ImageDescriptor getStateImageDescriptor() {
        if (ReferenceHighlighterEditPartListener
                .isHighlightReferencingEnabled()) {
            return Xpdl2ProcessEditorPlugin
                    .getImageDescriptor("icons/enableRefHighlightOn.png"); //$NON-NLS-1$
        } else {
            return Xpdl2ProcessEditorPlugin
                    .getImageDescriptor("icons/enableRefHighlightOff.png"); //$NON-NLS-1$
        }
    }

    /**
     * Set check state and makes items visible/invisible as required.
     * 
     * @author aallway
     * @since 16 Jul 2012
     */
    private class HighlighterMenuOpenListener implements IMenuListener {
        @Override
        public void menuAboutToShow(IMenuManager manager) {

            boolean menuChanged = false;

            Process activeEditorProcess = getActiveEditorProcess();

            for (StaticHighlighterAction action : staticHighlighterActions) {

                IContributionItem menuItemForAction = null;

                IContributionItem[] menuItems = manager.getItems();
                for (IContributionItem menuItem : menuItems) {
                    if (menuItem instanceof ActionContributionItem) {
                        if (action.equals(((ActionContributionItem) menuItem)
                                .getAction())) {
                            menuItemForAction = menuItem;
                            break;
                        }
                    }
                }

                /* First check if item should be visible. */
                if (menuItemForAction != null) {
                    boolean isVisible = true;

                    if (activeEditorProcess != null) {
                        if (action.staticHighlighter
                                .shouldShow(activeEditorProcess)) {
                            isVisible = true;
                        } else {
                            isVisible = false;
                        }
                    }

                    if (isVisible != menuItemForAction.isVisible()) {
                        menuItemForAction.setVisible(isVisible);
                        menuChanged = true;
                    }
                }

                /*
                 * Set the check state according to whether the highlighter is
                 * currently active or not.
                 */
                boolean isActive =
                        ReferenceHighlighterEditPartListener
                                .isStaticHighlighterActive(action.staticHighlighter);
                action.setChecked(isActive);

            }

            /* Update the menu if we changed visibility. */
            if (menuChanged) {
                manager.update(true);
            }

        }

        /**
         * @return Set of processes currently being edited.
         */
        private Process getActiveEditorProcess() {
            Set<Process> editorProcesses = new HashSet<Process>();

            if (PlatformUI.getWorkbench() != null
                    && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
                IWorkbenchPage activePage =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();
                if (activePage != null) {
                    IEditorPart activeEditor = activePage.getActiveEditor();

                    if (activeEditor != null) {
                        Object process = activeEditor.getAdapter(Process.class);

                        if (process instanceof Process) {
                            return (Process) process;
                        }
                    }
                }
            }

            return null;
        }

    }

    /**
     * Action to Enable reference highlighting in process diagram editor
     * 
     * @author aallway
     * @since 25 Jan 2011
     */
    public class ReferenceHighlighterEnableAction extends Action {

        public ReferenceHighlighterEnableAction() {
            super(
                    Messages.EnableDisableReferenceHighlighterAction_EnableRefHighlight_button,
                    null);

            setChecked(ReferenceHighlighterEditPartListener
                    .isHighlightReferencingEnabled());
        }

        /**
         * @see org.eclipse.jface.action.Action#run()
         * 
         */
        @Override
        public void run() {
            toggleReferenceHighlighting();
        }
    }

    /**
     * Action to Enable/Disable animate highlighting in process diagram editor
     * 
     * @author aallway
     * @since 25 Jan 2011
     */
    public class AnimateHighlightEnableAction extends Action {

        public AnimateHighlightEnableAction() {
            super(
                    Messages.ReferenceHighlightersAction_EnableHighlightAnimation_menu,
                    null);

            setChecked(ReferenceHighlighterEditPartListener
                    .isAnimateHighlightEnabled());
        }

        /**
         * @see org.eclipse.jface.action.Action#run()
         * 
         */
        @Override
        public void run() {
            ReferenceHighlighterEditPartListener
                    .setAnimateHighlightEnabled(isChecked());
        }
    }

    /**
     * Static highlighter menu item action.
     * 
     * 
     * @author aallway
     * @since 3 Feb 2011
     */
    public class StaticHighlighterAction extends Action {
        private AbstractStaticHighlighterContribution staticHighlighter;

        public StaticHighlighterAction(
                AbstractStaticHighlighterContribution staticHighlighter) {
            super(staticHighlighter.getMenuText(), staticHighlighter
                    .getMenuImageDescriptor());
            this.staticHighlighter = staticHighlighter;
            // this.setChecked(ReferenceHighlighterEditPartListener
            // .isStaticHighlighterActive(staticHighlighter));

        }

        /**
         * @see org.eclipse.jface.action.Action#run()
         * 
         */
        @Override
        public void run() {
            ReferenceHighlighterEditPartListener
                    .activateStaticHighlighter(staticHighlighter, true);
        }

    }

}
