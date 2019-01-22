/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.importwizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Interfaced used by the Import Wizard to get the file extension filter for the
 * source selection, the dialog to display when browse is selected for the
 * destination container and validation of the destination container.
 * 
 * @see IImportWizardPageProvider2
 * 
 * @author njpatel
 * 
 */
public interface IImportWizardPageProvider {
    /**
     * Get the dialog to display when the destination container browse button is
     * clicked.
     * 
     * @return
     */
    public SelectionDialog getDestinationContainerSelectionDialog();

    /**
     * File extension filter to filter the source file selection view.
     * 
     * @return Array of file extensions to filter on. If no filtering is
     *         required then return <code>null</code>.
     */
    public String[] getFileExtensionFilter();

    /**
     * Validate the <i>container</i> selected as the destination of the import.
     * 
     * @param container
     * @return OK status if the destination container is valid for import, ERROR
     *         otherwise.
     */
    public IStatus validateDestinationContainer(IContainer container);
}
