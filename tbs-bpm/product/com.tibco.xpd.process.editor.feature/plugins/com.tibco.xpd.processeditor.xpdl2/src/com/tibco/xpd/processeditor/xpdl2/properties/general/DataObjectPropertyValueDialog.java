/**
 * DataObjectPropertyValueDialog.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */
package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class DataObjectPropertyValueDialog extends Dialog {

    private String propertyValue = null;
    
    private String title = ""; //$NON-NLS-1$
    
    private Button okButton = null;

    private Text propertyText;
    
    
    /**
     * Create properties editor for the given fragment. 
     */
    public DataObjectPropertyValueDialog(Shell parentShell, String dialogTitle, String description) {
        super(parentShell);
        
        this.title = dialogTitle;
        
        this.propertyValue = description;
        
        this.setShellStyle(this.getShellStyle() | SWT.RESIZE);
    }

    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
        // create OK and Cancel buttons by default
        okButton = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
        
        //do this here because setting the text will set enablement on the ok
        // button
        boolean okEnable = true;
        
        if (propertyText != null) {
            propertyText.setFocus();
            
            propertyText.selectAll();
                
        }
        
        okButton.setEnabled(okEnable);
        
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    @Override
    protected void okPressed() {
        propertyValue = propertyText.getText();
        super.okPressed();
    }
    
    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    protected Control createDialogArea(Composite parent) {
        // create composite
        Composite root = (Composite) super.createDialogArea(parent);
        
        Composite composite = new Composite(root, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        composite.setLayout(new GridLayout(1, false));
        
        propertyText = new Text(composite, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);

        GridData descGD = new GridData(GridData.FILL_BOTH);
        descGD.widthHint = 500;
        descGD.heightHint = 300;
        propertyText.setLayoutData(descGD);
        propertyText.setText(propertyValue);
        
        applyDialogFont(composite);
        return composite;
    }

    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);

        if (title != null) {
            shell.setText(title);
        }
    }
  
    
    /**
     * @return the description
     */
    public String getPropertyValue() {
        return propertyValue;
    }
}
