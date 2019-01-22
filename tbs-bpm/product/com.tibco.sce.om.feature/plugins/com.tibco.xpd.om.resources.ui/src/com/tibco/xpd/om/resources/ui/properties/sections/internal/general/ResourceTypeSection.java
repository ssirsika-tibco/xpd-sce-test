/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.themes.ColorUtil;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property general section for a {@link ResourceType} object. This will display
 * a message if this resource is of human resource type.
 * 
 * @author njpatel
 * 
 */
public class ResourceTypeSection extends AbstractGeneralSection {

    private Font arielFont;
    private Color grayColor;
    private Label messageLbl;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        // Create empty label to offset the content into the correct column
        createLabel(root, toolkit, ""); //$NON-NLS-1$

        messageLbl = toolkit.createLabel(root,
                Messages.ResourceTypeSection_humanResourceType_shortdesc);

        arielFont = new Font(root.getDisplay(), "Arial", 8, //$NON-NLS-1$
                SWT.BOLD);
        messageLbl.setFont(arielFont);
        Color foreground = messageLbl.getForeground();
        if (foreground != null) {
            grayColor = new Color(messageLbl.getDisplay(), ColorUtil.blend(
                    foreground.getRGB(), ColorConstants.white.getRGB(), 60));
            messageLbl.setForeground(grayColor);
        }

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // No commands to set in this section
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof ResourceType && messageLbl != null
                && !messageLbl.isDisposed()) {
            GridData data;
            if (((ResourceType) input).isHumanResourceType()) {
                // Show the label
                data = new GridData(SWT.FILL, SWT.CENTER, true, false);
                data.widthHint = 160;
                messageLbl.setVisible(true);
            } else {
                // hide the label
                data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
                data.widthHint = 0;
                data.heightHint = 0;
                messageLbl.setVisible(false);
            }
            messageLbl.setLayoutData(data);
            messageLbl.getParent().layout();
        }
    }

    @Override
    public void dispose() {
        if (arielFont != null) {
            arielFont.dispose();
            arielFont = null;
        }
        if (grayColor != null) {
            grayColor.dispose();
            grayColor = null;
        }
        super.dispose();
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        /*
         * Should refresh this section if the human resource type has changed in
         * the OrgModel.
         */
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (notification.getNotifier() instanceof OrgModel
                        && OMPackage.Literals.ORG_MODEL__HUMAN_RESOURCE_TYPE
                                .equals(notification.getFeature())) {
                    return true;
                }
            }
        }

        return super.shouldRefresh(notifications);
    }
}
