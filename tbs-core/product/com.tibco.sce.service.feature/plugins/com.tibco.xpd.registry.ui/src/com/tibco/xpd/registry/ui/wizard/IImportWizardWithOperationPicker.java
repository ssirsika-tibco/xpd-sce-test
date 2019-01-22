package com.tibco.xpd.registry.ui.wizard;

import org.eclipse.ui.IImportWizard;


/**
 * Interface for wsdl wizard nodes to implement in order to support
 * operation picker page as the last selection process.
 * Used when needing to select a webservice operation from a newly imported wsdl file. 
 * @author glewis
 *
 */
public interface IImportWizardWithOperationPicker extends IImportWizard {
    
    /**
     * Method to set the operation picker page so it appears in the 
     * wizard that implements this interface.
     * @param operationPickerPage
     */
    void setOperationPickerPage(OperationPickerPage operationPickerPage);
    
    /**
     * Method to return the operation picker page so classes that call
     * the wizard implementing this interface can get the results of the
     * operation picker page.
     * @return
     */
    OperationPickerPage getOperationPickerPage();
}
