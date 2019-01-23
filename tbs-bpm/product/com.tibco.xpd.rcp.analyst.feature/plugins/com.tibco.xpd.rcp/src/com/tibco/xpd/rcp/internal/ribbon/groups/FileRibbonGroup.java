/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.ribbon.groups;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.actions.DuplicateAction;
import com.tibco.xpd.rcp.internal.actions.ImportAction;
import com.tibco.xpd.rcp.internal.actions.OverviewAction;
import com.tibco.xpd.rcp.internal.actions.PublishAction;
import com.tibco.xpd.rcp.ribbon.AbstractRibbonGroup;
import com.tibco.xpd.rcp.ribbon.RibbonConsts;

/**
 * Diagram group in the File tab. This allows user to run actions such as
 * rename, duplicate, etc...
 * 
 * @author njpatel
 * 
 */
public class FileRibbonGroup extends AbstractRibbonGroup {

    private final List<Image> imagesToDispose;

    public FileRibbonGroup() {
        imagesToDispose = new ArrayList<Image>();
    }

    @Override
    public void dispose() {
        for (Image img : imagesToDispose) {
            img.dispose();
        }
        imagesToDispose.clear();

        super.dispose();
    }

    @Override
    public void createContent(RibbonGroup group) {
        RibbonButtonGroup btnGrp = createButtonGroup(group);

        // Show overview
        createAction(btnGrp, new OverviewAction(getWindow()));

        // Rename
        createAction(btnGrp,
                RCPActivator.getGlobalAction(RibbonConsts.ACTION_RENAME.getId()),
                RibbonConsts.ACTION_RENAME.getLabel(),
                RCPActivator.getDefault().getImageRegistry()
                        .get(RCPImages.RENAME.getPath()),
                RCPActivator.getDefault().getImageRegistry()
                        .get(RCPImages.RENAME.getDisabledPath()),
                false);
        // Duplicate
        createAction(btnGrp, new DuplicateAction(getWindow()));
        // Import
        createAction(btnGrp,
                new ImportAction(getWindow()),
                RibbonButton.STYLE_ARROW_DOWN);
        // Publish
        createAction(btnGrp, new PublishAction(getWindow()));
        // Print
        createAction(btnGrp,
                RCPActivator.getGlobalAction(RibbonConsts.ACTION_PRINT.getId()),
                RibbonConsts.ACTION_PRINT.getLabel(),
                getWindow().getWorkbench().getSharedImages()
                        .getImage(ISharedImages.IMG_ETOOL_PRINT_EDIT),
                getWindow().getWorkbench().getSharedImages()
                        .getImage(ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED),
                false);
    }

    /**
     * Create a ribbon button for the action given.
     * 
     * @param group
     * @param action
     */
    private void createAction(RibbonButtonGroup group, IAction action) {
        createAction(group, action, 0);
    }

    /**
     * Create a ribbon button with the given style for the action.
     * 
     * @param group
     * @param action
     * @param style
     */
    private void createAction(RibbonButtonGroup group, IAction action, int style) {
        Image img = null;
        if (action.getImageDescriptor() != null) {
            img = action.getImageDescriptor().createImage();
            imagesToDispose.add(img);
        }
        Image disabledImg = null;
        if (action.getDisabledImageDescriptor() != null) {
            disabledImg = action.getDisabledImageDescriptor().createImage();
            imagesToDispose.add(disabledImg);
        }

        if (style != 0) {
            createAction(group,
                    action,
                    action.getText(),
                    img,
                    disabledImg,
                    style,
                    true);
        } else {
            createAction(group, action, action.getText(), img, disabledImg);
        }
    }
}
