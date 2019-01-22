/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.gmf.extensions.palette;

import org.eclipse.gmf.runtime.diagram.ui.services.palette.SelectionToolEx;

import com.tibco.xpd.resources.ui.util.ShowViewUtil;

/**
 * This is an extension of the GMF
 * {@link org.eclipse.gmf.runtime.diagram.ui.services.palette.SelectionToolEx}
 * for XPD selection tool customisation.
 * 
 * @author rsawant
 * @since 29-Nov-2012
 */
public class SelectionToolExEx extends SelectionToolEx {

    /**
     * @see org.eclipse.gef.tools.AbstractTool#handleDoubleClick(int)
     * 
     * @param button
     * @return
     */
    @Override
    protected boolean handleDoubleClick(int button) {

        ShowViewUtil.showOrDetachPropertiesView(null, null);
        return true;
    }
}
