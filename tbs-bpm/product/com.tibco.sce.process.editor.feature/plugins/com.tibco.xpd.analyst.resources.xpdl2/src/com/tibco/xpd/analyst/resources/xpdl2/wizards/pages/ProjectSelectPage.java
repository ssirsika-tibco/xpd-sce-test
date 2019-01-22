/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;

/**
 * Project selection wizard page. Extends
 * <code>CreationWizardProjectSelectionPage</code>
 * 
 * @see CreationWizardProjectSelectionPage,
 *      {@link AbstractXpdlProjectSelectPage}
 * 
 * @author njpatel / aallway (moved guts of this class to
 *         {@link AbstractXpdlProjectSelectPage}
 */
public class ProjectSelectPage extends AbstractXpdlProjectSelectPage {

    public ProjectSelectPage() {
        super(Messages.ProjectSelectPage_Window_title,
                Messages.ProjectSelectPage_Window_shortdesc);
    }

    @Override
    protected String getBaseFileName() {
        return Xpdl2ResourcesConsts.DEFAULT_PACKAGE_NAME;
    }

    @Override
    protected String getFileExtension() {
        return Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT;
    }

    @Override
    protected String getSpecialFolderKind() {
        return Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND;
    }

    @Override
    protected String getFileFolderLabelText() {
        return Messages.ProjectSelectPage_PackagesFolder_label;
    }

    @Override
    protected String getPageInternalDescription() {
        return Messages.CreationWizardProjectSelectionPage_1;
    }

    @Override
    protected String getNotCorrectSpecialFolderMessage() {
        return Messages.ProjectSelectPage_NotAProcessPackagesFolder_longdesc;
    }

    @Override
    protected String getBrowseSpecialFolderTitle() {
        return Messages.CreationWizardProjectSelectionPage_14;
    }

    // MR 39946 - begin
    /**
     * if ProcessPackage special folder is not selected, disable the next button
     * */
    @Override
    public boolean canFlipToNextPage() {
        if (getErrorMessage() != null) {
            return false;
        }
        return true;
    }
    // MR 39946 - end
}