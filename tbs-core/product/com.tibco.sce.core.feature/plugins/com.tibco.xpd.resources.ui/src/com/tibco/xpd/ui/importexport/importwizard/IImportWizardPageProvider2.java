/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.importwizard;

import java.util.List;

import org.eclipse.core.runtime.IStatus;

/**
 * This should be implemented by the import wizard that needs to validate the
 * resources selected for import. This is an extension of
 * <code>IImportWizardPageProvider</code>.
 * 
 * @see IImportWizardPageProvider
 * 
 * @author njpatel
 * 
 * @since 3.2
 */
public interface IImportWizardPageProvider2 extends IImportWizardPageProvider {

    /**
     * Validate the resources selected to be imported.
     * 
     * @param selectedResources
     * @return
     */
    IStatus validateResourceSelection(List<?> selectedResources);
}
