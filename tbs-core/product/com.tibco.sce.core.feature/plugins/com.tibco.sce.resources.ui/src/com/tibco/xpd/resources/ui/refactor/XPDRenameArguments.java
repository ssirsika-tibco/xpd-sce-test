/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.refactor;

import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

/**
 * XPD's flavour of RenameArguments to aid the modification of Display name as
 * well when we are renaming an XPD element.
 * 
 * @author sajain
 * @since Sep 21, 2015
 */
public class XPDRenameArguments extends RenameArguments {

    /**
     * Old display name of the element being refactored.
     */
    private String oldDisplayName;

    /**
     * New display name of the element being refactored.
     */
    private String newDisplayName;

    /**
     * @param newName
     * @param updateReferences
     */
    public XPDRenameArguments(String newName, boolean updateReferences) {

        super(newName, updateReferences);

    }

    /**
     * 
     * @param newElementName
     * @param updateReferences
     * @param oldElementDisplayName
     * @param newElementDisplayName
     */
    public XPDRenameArguments(String newElementName, boolean updateReferences,
            String oldElementDisplayName, String newElementDisplayName) {

        super(newElementName, updateReferences);
        this.newDisplayName = newElementDisplayName;
        this.oldDisplayName = oldElementDisplayName;

    }

    /**
     * @return the newDisplayName
     */
    public String getNewDisplayName() {
        return newDisplayName;
    }

    /**
     * @param newDisplayName
     *            the newDisplayName to set
     */
    public void setNewDisplayName(String newDisplayName) {
        this.newDisplayName = newDisplayName;
    }

    /**
     * @return the oldDisplayName
     */
    public String getOldDisplayName() {
        return oldDisplayName;
    }

    /**
     * @param oldDisplayName
     *            the oldDisplayName to set
     */
    public void setOldDisplayName(String oldDisplayName) {
        this.oldDisplayName = oldDisplayName;
    }

}
