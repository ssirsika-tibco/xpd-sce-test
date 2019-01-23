/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;

/**
 * Select project and package wizard page. Extends
 * <code>CreationWizardProjectSelectionPage</code>
 * 
 * @see CreationWizardProjectSelectionPage
 * 
 * @author njpatel
 */
public class PackageSelectionPage extends
        AbstractSpecialFolderFileSelectionPage {

    public PackageSelectionPage() {
        super(Messages.PackageSelectionPage_TITLE,
                Messages.PackageSelectionPage_DESC);
    }
    
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
    protected String getFileNotExistMessage() {
        return Messages.PackageSelectionPage_2;
    }

    @Override
    protected String getFileNameEmptyMessage() {
        return Messages.PackageSelectionPage_3;
    }

    @Override
    protected String getFileTypeNameLabel() {
        return Messages.PackageSelectionPage_4;
    }

    @Override
    protected String getFileSelectionMessage() {
        return Messages.PackageSelectionPage_7;
    }
    
    @Override
    protected String getNotCorrectSpecialFolderMessage() {
        return Messages.CreationWizardProjectSelectionPage_8;
    }

    @Override
    protected String getBrowseSpecialFolderTitle() {
        return Messages.CreationWizardProjectSelectionPage_14;
    }
}
