/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.ribbon.groups;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.RetargetAction;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.ribbon.AbstractRibbonGroup;
import com.tibco.xpd.rcp.ribbon.RibbonConsts;
import com.tibco.xpd.rcp.ribbon.action.RibbonButtonGroupAction;

/**
 * Edit group in the File tab. This allows the user to perform edits such as
 * copy/paste.
 * 
 * @author njpatel
 * 
 */
public class EditRibbonGroup extends AbstractRibbonGroup {

    private static final ImageRegistry IMAGE_REGISTRY = RCPActivator
            .getDefault().getImageRegistry();

    public EditRibbonGroup() {
    }

    @Override
    protected void createContent(RibbonGroup group) {
        ISharedImages sharedImages =
                getWindow().getWorkbench().getSharedImages();

        createAction(group,
                RCPActivator.getGlobalAction(RibbonConsts.ACTION_PASTE.getId()),
                RibbonConsts.ACTION_PASTE.getLabel(),
                IMAGE_REGISTRY.get(RCPImages.PASTE.getPath()),
                IMAGE_REGISTRY.get(RCPImages.PASTE.getDisabledPath()),
                false);

        RibbonButtonGroup btnGrp = createButtonGroup(group);

        createAction(btnGrp,
                RCPActivator.getGlobalAction(RibbonConsts.ACTION_CUT.getId()),
                RibbonConsts.ACTION_CUT.getLabel(),
                sharedImages.getImage(ISharedImages.IMG_TOOL_CUT),
                sharedImages.getImage(ISharedImages.IMG_TOOL_CUT_DISABLED),
                false);
        createAction(btnGrp,
                new CopyDelegateAction(RCPActivator
                        .getGlobalAction(RibbonConsts.ACTION_COPY.getId())),
                RibbonConsts.ACTION_COPY.getLabel(),
                sharedImages.getImage(ISharedImages.IMG_TOOL_COPY),
                sharedImages.getImage(ISharedImages.IMG_TOOL_COPY_DISABLED),
                false);
        createAction(btnGrp,
                RCPActivator.getGlobalAction(RibbonConsts.ACTION_DELETE.getId()),
                RibbonConsts.ACTION_DELETE.getLabel(),
                sharedImages.getImage(ISharedImages.IMG_TOOL_DELETE),
                sharedImages.getImage(ISharedImages.IMG_TOOL_DELETE_DISABLED),
                false);

        new UndoRedoAction(btnGrp,
                sharedImages.getImage(ISharedImages.IMG_TOOL_UNDO),
                sharedImages.getImage(ISharedImages.IMG_TOOL_UNDO_DISABLED),
                RibbonConsts.ACTION_UNDO);

        new UndoRedoAction(btnGrp,
                sharedImages.getImage(ISharedImages.IMG_TOOL_REDO),
                sharedImages.getImage(ISharedImages.IMG_TOOL_REDO_DISABLED),
                RibbonConsts.ACTION_REDO);
    }

    /**
     * Overrides the RibbonButtonGroupAction as the label of the undo/redo
     * action will be used as the tooltip text rather than the tooltip text from
     * the action as this doesn't seem to return an accurate value.
     * 
     * @author njpatel
     * 
     */
    private class UndoRedoAction extends RibbonButtonGroupAction {

        /**
         * Undo/Redo action.
         * 
         * @param buttonGroup
         *            button group to add the action to
         * @param activeImage
         *            active image of the action
         * @param disabledImage
         *            disabled action of the image
         * @param actionType
         *            UNDO or REDO
         */
        private UndoRedoAction(RibbonButtonGroup buttonGroup,
                Image activeImage, Image disabledImage, RibbonConsts actionType) {
            super(buttonGroup,
                    RCPActivator.getGlobalAction(actionType.getId()),
                    actionType.getLabel(), activeImage, disabledImage,
                    RibbonButton.STYLE_NO_DEPRESS, true);
        }

        @Override
        protected String getTooltipText() {
            IAction action = getAction();
            AbstractRibbonGroupItem btn = getButton();
            String tooltipText = null;

            if (action != null) {
                if (action instanceof RetargetAction) {
                    action = ((RetargetAction) action).getActionHandler();
                }

                if (action != null) {
                    tooltipText = action.getText();
                }

                if (tooltipText != null) {
                    tooltipText = Action.removeMnemonics(tooltipText);
                }

                // If the title and content is the same then just show title in
                // the tooltip
                if (btn != null && btn.getToolTip() != null) {
                    String title = btn.getToolTip().getTitle();
                    if (tooltipText != null && tooltipText.equals(title)) {
                        tooltipText = null;
                    }
                }
            }

            return tooltipText;
        }
    }

    /**
     * XPD-4160: Created a delegate action to control the enable-ment of the
     * Copy action. When the RCP is launched (or when all editors are closed)
     * the properties view becomes the active part and its retargetable Copy
     * action is set to enabled which shows the Copy action in the RCP as
     * enabled but there is nothing that can be copied.
     * 
     * @author njpatel
     */
    private class CopyDelegateAction implements IAction {
        private final IAction copyRetargetableAction;

        /**
         * 
         */
        public CopyDelegateAction(IAction copyRetargetableAction) {
            this.copyRetargetableAction = copyRetargetableAction;
        }

        /**
         * @param listener
         * @see org.eclipse.jface.action.IAction#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
         */
        @Override
        public void addPropertyChangeListener(IPropertyChangeListener listener) {
            copyRetargetableAction.addPropertyChangeListener(listener);
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getAccelerator()
         */
        @Override
        public int getAccelerator() {
            return copyRetargetableAction.getAccelerator();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getActionDefinitionId()
         */
        @Override
        public String getActionDefinitionId() {
            return copyRetargetableAction.getActionDefinitionId();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getDescription()
         */
        @Override
        public String getDescription() {
            return copyRetargetableAction.getDescription();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getDisabledImageDescriptor()
         */
        @Override
        public ImageDescriptor getDisabledImageDescriptor() {
            return copyRetargetableAction.getDisabledImageDescriptor();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getHelpListener()
         */
        @Override
        public HelpListener getHelpListener() {
            return copyRetargetableAction.getHelpListener();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getHoverImageDescriptor()
         */
        @Override
        public ImageDescriptor getHoverImageDescriptor() {
            return copyRetargetableAction.getHoverImageDescriptor();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getId()
         */
        @Override
        public String getId() {
            return copyRetargetableAction.getId();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getImageDescriptor()
         */
        @Override
        public ImageDescriptor getImageDescriptor() {
            return copyRetargetableAction.getImageDescriptor();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getMenuCreator()
         */
        @Override
        public IMenuCreator getMenuCreator() {
            return copyRetargetableAction.getMenuCreator();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getStyle()
         */
        @Override
        public int getStyle() {
            return copyRetargetableAction.getStyle();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getText()
         */
        @Override
        public String getText() {
            return copyRetargetableAction.getText();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#getToolTipText()
         */
        @Override
        public String getToolTipText() {
            return copyRetargetableAction.getToolTipText();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#isChecked()
         */
        @Override
        public boolean isChecked() {
            return copyRetargetableAction.isChecked();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#isEnabled()
         */
        @Override
        public boolean isEnabled() {
            /*
             * If no editor is active then return false (as otherwise if the
             * properties view is active then this will return true but there
             * won't be anything to copy.
             */
            IWorkbenchWindow window = getWindow();
            if (window != null) {
                IWorkbenchPage page = window.getActivePage();
                if (page != null) {
                    if (page.getActiveEditor() == null) {
                        return false;
                    }
                }
            }

            return copyRetargetableAction.isEnabled();
        }

        /**
         * @return
         * @see org.eclipse.jface.action.IAction#isHandled()
         */
        @Override
        public boolean isHandled() {
            return copyRetargetableAction.isHandled();
        }

        /**
         * @param listener
         * @see org.eclipse.jface.action.IAction#removePropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
         */
        @Override
        public void removePropertyChangeListener(
                IPropertyChangeListener listener) {
            copyRetargetableAction.removePropertyChangeListener(listener);
        }

        /**
         * 
         * @see org.eclipse.jface.action.IAction#run()
         */
        @Override
        public void run() {
            copyRetargetableAction.run();
        }

        /**
         * @param event
         * @see org.eclipse.jface.action.IAction#runWithEvent(org.eclipse.swt.widgets.Event)
         */
        @Override
        public void runWithEvent(Event event) {
            copyRetargetableAction.runWithEvent(event);
        }

        /**
         * @param id
         * @see org.eclipse.jface.action.IAction#setActionDefinitionId(java.lang.String)
         */
        @Override
        public void setActionDefinitionId(String id) {
            copyRetargetableAction.setActionDefinitionId(id);
        }

        /**
         * @param checked
         * @see org.eclipse.jface.action.IAction#setChecked(boolean)
         */
        @Override
        public void setChecked(boolean checked) {
            copyRetargetableAction.setChecked(checked);
        }

        /**
         * @param text
         * @see org.eclipse.jface.action.IAction#setDescription(java.lang.String)
         */
        @Override
        public void setDescription(String text) {
            copyRetargetableAction.setDescription(text);
        }

        /**
         * @param newImage
         * @see org.eclipse.jface.action.IAction#setDisabledImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         */
        @Override
        public void setDisabledImageDescriptor(ImageDescriptor newImage) {
            copyRetargetableAction.setDisabledImageDescriptor(newImage);
        }

        /**
         * @param enabled
         * @see org.eclipse.jface.action.IAction#setEnabled(boolean)
         */
        @Override
        public void setEnabled(boolean enabled) {
            copyRetargetableAction.setEnabled(enabled);
        }

        /**
         * @param listener
         * @see org.eclipse.jface.action.IAction#setHelpListener(org.eclipse.swt.events.HelpListener)
         */
        @Override
        public void setHelpListener(HelpListener listener) {
            copyRetargetableAction.setHelpListener(listener);
        }

        /**
         * @param newImage
         * @see org.eclipse.jface.action.IAction#setHoverImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         */
        @Override
        public void setHoverImageDescriptor(ImageDescriptor newImage) {
            copyRetargetableAction.setHoverImageDescriptor(newImage);
        }

        /**
         * @param id
         * @see org.eclipse.jface.action.IAction#setId(java.lang.String)
         */
        @Override
        public void setId(String id) {
            copyRetargetableAction.setId(id);
        }

        /**
         * @param newImage
         * @see org.eclipse.jface.action.IAction#setImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
         */
        @Override
        public void setImageDescriptor(ImageDescriptor newImage) {
            copyRetargetableAction.setImageDescriptor(newImage);
        }

        /**
         * @param creator
         * @see org.eclipse.jface.action.IAction#setMenuCreator(org.eclipse.jface.action.IMenuCreator)
         */
        @Override
        public void setMenuCreator(IMenuCreator creator) {
            copyRetargetableAction.setMenuCreator(creator);
        }

        /**
         * @param text
         * @see org.eclipse.jface.action.IAction#setText(java.lang.String)
         */
        @Override
        public void setText(String text) {
            copyRetargetableAction.setText(text);
        }

        /**
         * @param text
         * @see org.eclipse.jface.action.IAction#setToolTipText(java.lang.String)
         */
        @Override
        public void setToolTipText(String text) {
            copyRetargetableAction.setToolTipText(text);
        }

        /**
         * @param keycode
         * @see org.eclipse.jface.action.IAction#setAccelerator(int)
         */
        @Override
        public void setAccelerator(int keycode) {
            copyRetargetableAction.setAccelerator(keycode);
        }

    }
}
