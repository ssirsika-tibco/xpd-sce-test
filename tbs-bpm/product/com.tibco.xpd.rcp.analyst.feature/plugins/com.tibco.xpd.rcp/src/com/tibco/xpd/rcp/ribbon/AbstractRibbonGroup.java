/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.services.IDisposable;

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonTooltip;
import com.tibco.xpd.rcp.ribbon.action.AbstractRibbonAction;
import com.tibco.xpd.rcp.ribbon.action.RibbonButtonGroupAction;
import com.tibco.xpd.rcp.ribbon.action.RibbonCheckboxGroupAction;
import com.tibco.xpd.rcp.ribbon.action.RibbonGroupAction;

/**
 * A ribbon group abstract class.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractRibbonGroup implements IDisposable {

    private RibbonGroup group = null;

    private IWorkbenchWindow window;

    /**
     * Create the ribbon group in the given tab.
     * 
     * @param window
     * @param tab
     * @param label
     * @return
     */
    public final RibbonGroup create(IWorkbenchWindow window, RibbonTab tab,
            String label) {
        this.window = window;
        group = createGroup(label, tab, getRibbonTooltip());

        createContent(group);

        return group;
    }

    /**
     * Get the workbench window.
     * 
     * @return
     */
    protected final IWorkbenchWindow getWindow() {
        return window;
    }

    /**
     * Create the contents of the Ribbon group.
     * 
     * @param group
     */
    protected abstract void createContent(RibbonGroup group);

    /**
     * Get the Ribbon tooltip for this group. Default implementation returns
     * <code>null</code> subclasses may extend to provide a tooltip.
     * 
     * @return
     */
    protected RibbonTooltip getRibbonTooltip() {
        return null;
    }

    /**
     * Create the group.
     * 
     * @param label
     * @param tab
     * @param tooltip
     * @return
     */
    private RibbonGroup createGroup(String label, RibbonTab tab,
            RibbonTooltip tooltip) {
        RibbonGroup grp = null;
        if (label != null && tab != null) {
            grp =
                    tooltip != null ? new RibbonGroup(tab, label, tooltip)
                            : new RibbonGroup(tab, label);
        }
        return grp;
    }

    /**
     * Create a buttons' group in the Ribbon group.
     * 
     * @param group
     * @return
     */
    protected RibbonButtonGroup createButtonGroup(RibbonGroup group) {
        RibbonButtonGroup btnGrp = null;
        if (group != null) {
            btnGrp = new RibbonButtonGroup(group);
        }
        return btnGrp;
    }

    /**
     * Create an action in the Ribbon group.
     * 
     * @param group
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @return
     */
    protected AbstractRibbonAction createAction(RibbonGroup group,
            IAction action, String label, Image activeImage, Image disabledImage) {
        return createAction(group,
                action,
                label,
                activeImage,
                disabledImage,
                0,
                RibbonButton.STYLE_NO_DEPRESS,
                true);
    }

    /**
     * Create an action in the Ribbon group.
     * 
     * @param group
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @return
     */
    protected AbstractRibbonAction createAction(RibbonGroup group,
            IAction action, String label, Image activeImage,
            Image disabledImage, boolean showToolTip) {
        return createAction(group,
                action,
                label,
                activeImage,
                disabledImage,
                0,
                RibbonButton.STYLE_NO_DEPRESS,
                showToolTip);
    }

    /**
     * Create an action in the Ribbon group.
     * 
     * @param group
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @param imgAlignment
     * @param style
     * @return
     */
    protected AbstractRibbonAction createAction(RibbonGroup group,
            IAction action, String label, Image activeImage,
            Image disabledImage, int imgAlignment, int style,
            boolean showToolTip) {

        RibbonGroupAction theAction = null;
        if (group != null && action != null && label != null
                && activeImage != null) {
            theAction =
                    new RibbonGroupAction(group, action, label, activeImage,
                            disabledImage, imgAlignment, style, showToolTip);
        }

        return theAction;
    }

    /**
     * Create an action in the ribbon buttons' group.
     * 
     * @param buttonGroup
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @return
     */
    protected AbstractRibbonAction createAction(RibbonButtonGroup buttonGroup,
            IAction action, String label, Image activeImage, Image disabledImage) {
        return createAction(buttonGroup,
                action,
                label,
                activeImage,
                disabledImage,
                RibbonButton.STYLE_NO_DEPRESS,
                true);
    }

    /**
     * Create an action in the ribbon buttons' group.
     * 
     * @param buttonGroup
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @param showToolTip
     * @return
     */
    protected AbstractRibbonAction createAction(RibbonButtonGroup buttonGroup,
            IAction action, String label, Image activeImage,
            Image disabledImage, boolean showToolTip) {
        return createAction(buttonGroup,
                action,
                label,
                activeImage,
                disabledImage,
                RibbonButton.STYLE_NO_DEPRESS,
                showToolTip);
    }

    /**
     * Create an action in the ribbon buttons' group.
     * 
     * @param buttonGroup
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @param style
     * @param showToolTip
     * @return
     */
    protected AbstractRibbonAction createAction(RibbonButtonGroup buttonGroup,
            IAction action, String label, Image activeImage,
            Image disabledImage, int style, boolean showToolTip) {

        RibbonButtonGroupAction theAction = null;
        if (buttonGroup != null && action != null && label != null
                && activeImage != null) {
            theAction =
                    new RibbonButtonGroupAction(buttonGroup, action, label,
                            activeImage, disabledImage, style, showToolTip);
        }

        return theAction;
    }

    /**
     * Create an action in the ribbon buttons' group.
     * 
     * @param buttonGroup
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @param style
     * @param showToolTip
     * @return
     */
    protected AbstractRibbonAction createCheckboxAction(
            RibbonButtonGroup buttonGroup, IAction action, String label,
            int style, boolean showToolTip) {

        RibbonCheckboxGroupAction theAction = null;
        if (buttonGroup != null && action != null && label != null) {
            theAction =
                    new RibbonCheckboxGroupAction(buttonGroup, action, label,
                            style, showToolTip);
        }

        return theAction;
    }

    @Override
    public void dispose() {
        if (group != null) {
            group.dispose();
        }
    }
}
