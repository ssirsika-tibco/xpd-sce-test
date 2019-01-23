/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import org.eclipse.swt.graphics.Image;

/**
 * Action added to indicate that a separate can be added here - this is used in
 * the Model menu.
 * 
 * @author njpatel
 * 
 */
public class SeparatorAction extends ModelAction {

    public SeparatorAction() {
        super(null, null, (Image) null);
    }

    @Override
    public void run() {
    }

}
