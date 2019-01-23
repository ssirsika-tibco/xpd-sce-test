/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManagerOverrides;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.tibco.xpd.processeditor.xpdl2.actions.DropDownToolbarButton;
import com.tibco.xpd.processeditor.xpdl2.actions.PrintAction;
import com.tibco.xpd.processeditor.xpdl2.actions.ReferenceHighlightersAction;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.actions.EnableDisableEditPolicyAction;
import com.tibco.xpd.processwidget.policies.ClickOrDragLinkEventPolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragReplyActivityPolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragSignalEventPolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragTaskReferencePolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragThrowFaultEventPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.policies.connectgadget.ClickOrDragCreateConnectionPolicy;
import com.tibco.xpd.processwidget.policies.cycletypegadget.AbstractCycleObjectTypeGadgetPolicy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;

/**
 * Mostly the guts of the old process diagram editor action contribution, but
 * with some things abstracted.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractProcessDiagramEditorContributor
        extends EditorActionBarContributor {

    private List viewerActions;

    private QuickSearchPopupAction quickSearchAction;

    private static ReferenceHighlightersAction refHighlightersAction =
            new ReferenceHighlightersAction();

    private ProcessEditorPolicyEnablementProvider policyEnablementProvider =
            new ProcessEditorPolicyEnablementProvider();

    private MenuManager enableDisableGadgetsMenu;

    @Override
    public void init(IActionBars bars) {
        createActions();

        super.init(bars);

        bars.setGlobalActionHandler(ActionFactory.PRINT.getId(),
                new PrintAction());

        bars.setGlobalActionHandler(ActionFactory.FIND.getId(),
                quickSearchAction);

    }

    /**
     * @return View menu group id (to match the editorActions extension point)
     */
    protected abstract String getViewMenuGroupId();

    /**
     * @return View menu id (to match the editorActions extension point)
     */
    protected abstract String getViewMenuId();

    @Override
    public void contributeToToolBar(IToolBarManager toolBarManager) {

        for (Iterator iter = viewerActions.iterator(); iter.hasNext();) {
            IAction act = (IAction) iter.next();
            toolBarManager.add(act);

        }
        AlignmentRetargetAction act;
        int[] poss =
                new int[] { PositionConstants.LEFT, PositionConstants.CENTER,
                        PositionConstants.RIGHT, PositionConstants.TOP,
                        PositionConstants.MIDDLE, PositionConstants.BOTTOM };
        for (int i = 0; i < poss.length; i++) {
            act = new AlignmentRetargetAction(poss[i]);
            getPage().addPartListener(act);
            toolBarManager.add(act);
        }

        // SID ZOOM - add zoom controls to toolbar...
        toolBarManager.add(new Separator());

        ZoomInRetargetAction zoomIn = new ZoomInRetargetAction();
        getPage().addPartListener(zoomIn);
        toolBarManager.add(zoomIn);
        ZoomOutRetargetAction zoomOut = new ZoomOutRetargetAction();
        getPage().addPartListener(zoomOut);
        toolBarManager.add(zoomOut);

        /*
         * String[] zoomStrings = new String[] { ZoomManager.FIT_ALL,
         * ZoomManager.FIT_HEIGHT, ZoomManager.FIT_WIDTH };
         */
        ZoomComboContributionItem zoomCombo =
                new ZoomComboContributionItem(getPage());
        toolBarManager.add(zoomCombo);

        //
        // Add the quick find tool bar action.
        toolBarManager.add(new Separator());
        toolBarManager.add(quickSearchAction);

        toolBarManager.add(new DropDownToolbarButton(
                Messages.ProcessDiagramEditorContributor_ShowClickDragGadgets_menu,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor("icons/emptyGadgetIcon.png")) { //$NON-NLS-1$
            @Override
            protected MenuManager createMenuManager() {
                return createEnableDisableGadgetMenu();
            }
        });

        toolBarManager.add(new EnableDisableEditPolicyAction(
                policyEnablementProvider,
                Messages.ProcessDiagramEditorContributor_AnimateOutgoingConnections_menu,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor("icons/pigInASnakeIcon.png"), //$NON-NLS-1$
                ConnectionsAnimatorEditPolicy.EDIT_POLICY_ID));

        toolBarManager.add(refHighlightersAction);

        /*
         * Set a toolbar manager override that provides the ability to override
         * the default disablement of actions when editor deactivated and so on.
         */
        if (toolBarManager instanceof ContributionManager) {
            ((ContributionManager) toolBarManager)
                    .setOverrides(new ProcessToolManagerOverrides(
                            toolBarManager.getOverrides()));
        }

        return;
    }

    private void createActions() {
        viewerActions = new ArrayList();
        quickSearchAction = getQuickSearchPopupAction();
        refHighlightersAction = new ReferenceHighlightersAction();
    }

    protected abstract QuickSearchPopupAction getQuickSearchPopupAction();

    @Override
    public void setActiveEditor(IEditorPart targetEditor) {
        ProcessWidget widget = targetEditor.getAdapter(ProcessWidget.class);

        widget.setEditPolicyEnablementProvider(policyEnablementProvider);

        String ids[] = new String[] { GEFActionConstants.ALIGN_LEFT,
                GEFActionConstants.ALIGN_CENTER, GEFActionConstants.ALIGN_RIGHT,
                GEFActionConstants.ALIGN_TOP, GEFActionConstants.ALIGN_MIDDLE,
                GEFActionConstants.ALIGN_BOTTOM,
                // GEFActionConstants.TOGGLE_RULER_VISIBILITY,
                GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY,
                GEFActionConstants.TOGGLE_GRID_VISIBILITY,
                GEFActionConstants.ZOOM_IN, GEFActionConstants.ZOOM_OUT,
                ActionFactory.UNDO.getId(), ActionFactory.REDO.getId(),
                ActionFactory.CUT.getId(), ActionFactory.COPY.getId(),
                ActionFactory.PASTE.getId(), ActionFactory.DELETE.getId(),
                ActionFactory.SELECT_ALL.getId(), ActionFactory.RENAME.getId(),
                IDEActionFactory.BOOKMARK.getId() };

        for (int i = 0; i < ids.length; i++) {
            IAction act = widget.getAction(ids[i]);
            if (act != null) {
                getActionBars().setGlobalActionHandler(ids[i], act);
            } else {
                System.out.println(ids[i]);
            }
        }

        getActionBars().updateActionBars();
    }

    @Override
    public void contributeToMenu(IMenuManager menuManager) {
        super.contributeToMenu(menuManager);

        /*
         * Sid XPD-8302 - Studio for Analyst does not have a main menu. So we
         * can't add to it (and Eclipse 4.7+ now throws exceptions and hence
         * will fail to create the processEditorPart.
         */
        if (XpdResourcesPlugin.isRCP()) {
            // Don't add to main menu in Studio for Analyst as there isn't one
            // to add to
            return;
        }

        AlignmentRetargetAction act;
        IContributionItem viewMenu = menuManager.find(getViewMenuId());
        IMenuManager mm = (IMenuManager) viewMenu;
        if (mm == null) {
            mm = new MenuManager(
                    Messages.ProcessDiagramEditorContributor_DiagramMenu_menu,
                    getViewMenuId());
            mm.add(new GroupMarker("align")); //$NON-NLS-1$
        }
        MenuManager alignMenu = new MenuManager(
                Messages.ProcessDiagramEditorContributor_AlignmentInDiagramMenu_menu);
        mm.appendToGroup("align", alignMenu); //$NON-NLS-1$
        mm.add(new GroupMarker(getViewMenuGroupId()));
        menuManager.insertAfter("project", mm); //$NON-NLS-1$

        int[] poss =
                new int[] { PositionConstants.LEFT, PositionConstants.CENTER,
                        PositionConstants.RIGHT, PositionConstants.TOP,
                        PositionConstants.MIDDLE, PositionConstants.BOTTOM };
        for (int i = 0; i < poss.length; i++) {
            act = new AlignmentRetargetAction(poss[i]);
            getPage().addPartListener(act);
            alignMenu.add(act);
        }

        ImageRegistry ir =
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry();
        RetargetAction ra;
        ra = new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY,
                Messages.ProcessDiagramEditorContributor_GridInDiagramMenu_menu,
                IAction.AS_CHECK_BOX);
        getPage().addPartListener(ra);
        ra.setImageDescriptor(
                ir.getDescriptor(ProcessEditorConstants.IMG_SNAP_TO_GRID));
        mm.add(ra);

        // TODO rulers
        // ra = new RetargetAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY,
        // "Rulers", IAction.AS_CHECK_BOX);
        // getPage().addPartListener(ra);
        // mm.add(ra);

        ra = new RetargetAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY,
                Messages.ProcessDiagramEditorContributor_AlignGuide_menu,
                IAction.AS_CHECK_BOX);
        ra.setImageDescriptor(
                ir.getDescriptor(ProcessEditorConstants.IMG_SNAP_TO_GEOMETRY));
        getPage().addPartListener(ra);
        mm.add(ra);

        //
        // Enable / disable connections animation.
        EnableDisableEditPolicyAction action =
                new EnableDisableEditPolicyAction(policyEnablementProvider,
                        Messages.ProcessDiagramEditorContributor_AnimateOutgoingConnections_menu,
                        Xpdl2ProcessEditorPlugin.getImageDescriptor(
                                "icons/pigInASnakeIcon.png"), //$NON-NLS-1$
                        ConnectionsAnimatorEditPolicy.EDIT_POLICY_ID);

        mm.add(action);

        mm.add(refHighlightersAction);

        //
        // Enable / disable gadgets sub-menu.
        addEditPolicyEnablementActions(mm);

        // Zoom
        mm.add(new Separator());
        ZoomInRetargetAction zIn = new ZoomInRetargetAction();
        getPage().addPartListener(zIn);
        mm.add(zIn);

        ZoomOutRetargetAction zOut = new ZoomOutRetargetAction();
        getPage().addPartListener(zOut);
        mm.add(zOut);

        /*
         * Set a toolbar manager override that provides the ability to override
         * the default disablement of actions when editor deactivated and so on.
         */
        if (mm instanceof ContributionManager) {
            ((ContributionManager) mm).setOverrides(
                    new ProcessToolManagerOverrides(mm.getOverrides()));
        }
    }

    /**
     * @param mm
     */
    private void addEditPolicyEnablementActions(IMenuManager mm) {
        //
        // Click drag gadgets enablement.
        //
        mm.add(new GroupMarker("clickDragGadgets")); //$NON-NLS-1$

        enableDisableGadgetsMenu = createEnableDisableGadgetMenu();
        mm.appendToGroup("clickDragGadgets", enableDisableGadgetsMenu); //$NON-NLS-1$

        return;
    }

    /**
     * @param mm
     */
    private MenuManager createEnableDisableGadgetMenu() {
        MenuManager gadgetMenu = new MenuManager(
                Messages.ProcessDiagramEditorContributor_ShowClickDragGadgets_menu,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor("icons/emptyGadgetIcon.png"), //$NON-NLS-1$
                null);

        EnableDisableEditPolicyAction action =
                createEnableCycleTypeGadgetAction();
        if (action != null) {
            gadgetMenu.add(action);
        }

        action = createEnableConnectionGadgetAction();
        if (action != null) {
            gadgetMenu.add(action);
        }

        action = createEnableLinkEventGadgetAction();
        if (action != null) {
            gadgetMenu.add(action);
        }

        action = createEnableSignalEventGadgetAction();
        if (action != null) {
            gadgetMenu.add(action);
        }

        action = createEnableReplyActivityGadgetAction();
        if (action != null) {
            gadgetMenu.add(action);
        }

        action = createEnableThrowErrorActivityGadgetAction();
        if (action != null) {
            gadgetMenu.add(action);
        }

        action = createEnableTaskRefGadgetAction();
        if (action != null) {
            gadgetMenu.add(action);
        }
        return gadgetMenu;
    }

    /**
     * @return The task reference gadget enablement action or null if not
     *         required.
     */
    protected EnableDisableEditPolicyAction createEnableTaskRefGadgetAction() {
        EnableDisableEditPolicyAction action;
        action = new EnableDisableEditPolicyAction(policyEnablementProvider,
                Messages.ProcessDiagramEditorContributor_RefTaskGadget_menu,
                Xpdl2ProcessEditorPlugin.getImageDescriptor(
                        "icons/referenceTaskGadgetIcon.png"), //$NON-NLS-1$
                ClickOrDragTaskReferencePolicy.EDIT_POLICY_ID);
        return action;
    }

    /**
     * @return The reply activity gadget enablement action or null if not
     *         required.
     */
    protected EnableDisableEditPolicyAction createEnableReplyActivityGadgetAction() {
        EnableDisableEditPolicyAction action;
        action = new EnableDisableEditPolicyAction(policyEnablementProvider,
                Messages.ProcessDiagramEditorContributor_ReplyGadget_menu,
                Xpdl2ProcessEditorPlugin.getImageDescriptor(
                        "icons/replyActivityGadgetIcon.png"), //$NON-NLS-1$
                ClickOrDragReplyActivityPolicy.EDIT_POLICY_ID);
        return action;
    }

    /**
     * @return The reply activity gadget enablement action or null if not
     *         required.
     */
    protected EnableDisableEditPolicyAction createEnableThrowErrorActivityGadgetAction() {
        EnableDisableEditPolicyAction action;
        action = new EnableDisableEditPolicyAction(policyEnablementProvider,
                Messages.AbstractProcessDiagramEditorContributor_ThrowFaultMEssageErrorGadget_menu,
                Xpdl2ProcessEditorPlugin.getImageDescriptor(
                        "icons/throwFaultErrorGadgetIcon.png"), //$NON-NLS-1$
                ClickOrDragThrowFaultEventPolicy.EDIT_POLICY_ID);
        return action;
    }

    /**
     * @return The task reference signal event action or null if not required.
     */
    protected EnableDisableEditPolicyAction createEnableSignalEventGadgetAction() {
        EnableDisableEditPolicyAction action;
        action = new EnableDisableEditPolicyAction(policyEnablementProvider,
                Messages.ProcessDiagramEditorContributor_SignalEventGadget_menu,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor("icons/signalEventGadgetIcon.png"), //$NON-NLS-1$
                ClickOrDragSignalEventPolicy.EDIT_POLICY_ID);
        return action;
    }

    /**
     * @return The link event gadget enablement action or null if not required.
     */
    protected EnableDisableEditPolicyAction createEnableLinkEventGadgetAction() {
        EnableDisableEditPolicyAction action;
        action = new EnableDisableEditPolicyAction(policyEnablementProvider,
                Messages.ProcessDiagramEditorContributor_LinkEventGadget_menu,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor("icons/linkEventGadgetIcon.png"), //$NON-NLS-1$
                ClickOrDragLinkEventPolicy.EDIT_POLICY_ID);
        return action;
    }

    /**
     * @return The cycle object type gadget enablement action or null if not
     *         required.
     */
    protected EnableDisableEditPolicyAction createEnableCycleTypeGadgetAction() {
        EnableDisableEditPolicyAction action =
                new EnableDisableEditPolicyAction(policyEnablementProvider,
                        Messages.AbstractProcessDiagramEditorContributor_CycleTypeGadget_menu,
                        Xpdl2ProcessEditorPlugin.getImageDescriptor(
                                "icons/cycleTypeGadgetIcon.png"), //$NON-NLS-1$
                        AbstractCycleObjectTypeGadgetPolicy.EDIT_POLICY_ID);
        return action;
    }

    /**
     * @return The create connection gadget enablement action or null if not
     *         required.
     */
    protected EnableDisableEditPolicyAction createEnableConnectionGadgetAction() {
        EnableDisableEditPolicyAction action =
                new EnableDisableEditPolicyAction(policyEnablementProvider,
                        Messages.ProcessDiagramEditorContributor_CreateConnectionGadget_menu,
                        Xpdl2ProcessEditorPlugin.getImageDescriptor(
                                "icons/connectionGadgetIcon.png"), //$NON-NLS-1$
                        ClickOrDragCreateConnectionPolicy.EDIT_POLICY_ID);
        return action;
    }

    /**
     * Edit policy enablement provider for menu's to disable various process
     * widget bits.
     * 
     * @author aallway
     * @since 3.2
     */
    private class ProcessEditorPolicyEnablementProvider
            implements EditPolicyEnablementProvider {

        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();

        @Override
        public boolean isPolicyEnabled(String policyId) {
            String prefName = getEnabledPreferenceName(policyId);

            if (prefStore.contains(prefName)) {
                return !prefStore.getBoolean(prefName);
            }
            return true;
        }

        @Override
        public void setPolicyEnabled(String policyId, boolean enabled) {
            prefStore.setValue(getEnabledPreferenceName(policyId), !enabled);

            IEditorPart editor = getPage().getActiveEditor();
            if (editor instanceof AbstractProcessDiagramEditor) {
                AbstractProcessDiagramEditor processEditor =
                        (AbstractProcessDiagramEditor) editor;

                ProcessWidget processWidget = processEditor.getProcessWidget();
                if (processWidget != null) {
                    // Force refresh of preocess widget and edit policies by
                    // unsetting and resetting selection.
                    ISelection sel = processWidget.getSelection();
                    processWidget.setSelection(new StructuredSelection());
                    processWidget.setSelection(sel);
                }
            }
        }

        private String getEnabledPreferenceName(String policyId) {
            // Store the state as DISABLED - this is because when the we set the
            // state to false the item is removed from preference store and we
            // want "not in store" and "default" to be true!
            return policyId + ".isDisabled"; //$NON-NLS-1$
        }

    }

    /**
     * This class allows us to override the default behaviour for process editor
     * tool bar items in that they are forced to disabled when the editor is not
     * active.
     * <p>
     * Certain actions may be desirable to make available only if the editor
     * exists.
     * 
     * 
     * @author aallway
     * @since 4 Feb 2011
     */
    private static class ProcessToolManagerOverrides
            implements IContributionManagerOverrides {
        IContributionManagerOverrides delegate;

        /**
         * @param delegate
         */
        public ProcessToolManagerOverrides(
                IContributionManagerOverrides delegate) {
            super();
            this.delegate = delegate;
        }

        /**
         * @param item
         * @return
         * @see org.eclipse.jface.action.IContributionManagerOverrides#getAccelerator(org.eclipse.jface.action.IContributionItem)
         */
        @Override
        public Integer getAccelerator(IContributionItem item) {
            return delegate.getAccelerator(item);
        }

        /**
         * @param item
         * @return
         * @see org.eclipse.jface.action.IContributionManagerOverrides#getAcceleratorText(org.eclipse.jface.action.IContributionItem)
         */
        @Override
        public String getAcceleratorText(IContributionItem item) {
            return delegate.getAcceleratorText(item);
        }

        /**
         * @param item
         * @return
         * @see org.eclipse.jface.action.IContributionManagerOverrides#getEnabled(org.eclipse.jface.action.IContributionItem)
         */
        @Override
        public Boolean getEnabled(IContributionItem item) {
            /*
             * If we return null then the action itself will be asked to
             * determine it's own enablement (toolbar manager will ask it
             * whether it is enabled.
             */
            if (ReferenceHighlightersAction.ACTION_ID.equals(item.getId())) {
                return null;
            }
            return delegate.getEnabled(item);
        }

        /**
         * @param item
         * @return
         * @see org.eclipse.jface.action.IContributionManagerOverrides#getText(org.eclipse.jface.action.IContributionItem)
         */
        @Override
        public String getText(IContributionItem item) {
            /*
             * Default Toolbar button text is a bit long winded (and click to
             * enable etc) doesn't apply to Diagram menu item).
             */
            if (item instanceof ActionContributionItem) {
                ActionContributionItem actionItem =
                        (ActionContributionItem) item;

                if (actionItem.getParent() instanceof IMenuManager) {

                    if (ReferenceHighlightersAction.ACTION_ID
                            .equals(item.getId())) {
                        return Messages.AbstractProcessDiagramEditorContributor_ReferenceHighlighting_menu;
                    }
                }
            }
            return delegate.getText(item);
        }

        /**
         * @see org.eclipse.jface.action.IContributionManagerOverrides#getVisible(org.eclipse.jface.action.IContributionItem)
         * 
         * @param item
         * @return
         */
        @Override
        public Boolean getVisible(IContributionItem item) {
            return delegate.getVisible(item);
        }

    }

}
