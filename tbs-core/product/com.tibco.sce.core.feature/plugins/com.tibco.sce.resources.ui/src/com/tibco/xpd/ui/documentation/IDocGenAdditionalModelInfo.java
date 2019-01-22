/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.ui.documentation;

/**
 * 
 * This interface adds additional info to the Doc Generation framework.
 * 
 * @author kthombar
 * @since 18-Dec-2013
 * @deprecated Use {@link IDocGenAdditionalModelInfo2} instead because it
 *             additionally provides methods to add the Priority, ModelTitle and
 *             Model Icon path info to the Doc Generation framework.
 */
@Deprecated
public interface IDocGenAdditionalModelInfo extends IDocGenModelInfo {

    /**
     * Returns the path of the icons used in the report
     * 
     * @return String
     */
    public String getIconPath();

    /**
     * Sets the path of the icons used in the report
     * 
     * @param extraStuff
     */
    public void setIconPath(String iconPath);

}
