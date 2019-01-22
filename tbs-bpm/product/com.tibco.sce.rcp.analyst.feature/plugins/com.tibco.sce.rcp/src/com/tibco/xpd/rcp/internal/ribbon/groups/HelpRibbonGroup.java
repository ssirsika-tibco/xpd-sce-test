/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.ribbon.groups;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.actions.AboutAction;
import com.tibco.xpd.rcp.internal.actions.HelpAction;
import com.tibco.xpd.rcp.internal.actions.ShowPreferencesAction;
import com.tibco.xpd.rcp.ribbon.AbstractRibbonGroup;

/**
 * Help ribbon group.
 * 
 * @author njpatel
 * 
 */
public class HelpRibbonGroup extends AbstractRibbonGroup {

    private static final ImageRegistry IMAGE_REGISTRY = RCPActivator
            .getDefault().getImageRegistry();

    public HelpRibbonGroup() {
    }

    @Override
    protected void createContent(final RibbonGroup group) {
        createAction(group,
                new HelpAction(getWindow()),
                Messages.HelpRibbonGroup_help_button,
                IMAGE_REGISTRY.get(RCPImages.HELP.getPath()),
                /* disabledImage */null,
                SWT.NONE,
                RibbonButton.STYLE_ARROW_DOWN,
                /* showTooltip */false);

        RibbonButtonGroup helpGroup = createButtonGroup(group);
        createAction(helpGroup,
                new AboutAction(getWindow()),
                Messages.HelpRibbonGroup_about_button,
                IMAGE_REGISTRY.get(RCPImages.INFO.getPath()),
                null,
                false);

        createAction(helpGroup,
                new ShowPreferencesAction(getWindow()),
                Messages.HelpRibbonGroup_preferences_button,
                IMAGE_REGISTRY.get(RCPImages.PREFERENCES.getPath()),
                null,
                false);

    }

}
