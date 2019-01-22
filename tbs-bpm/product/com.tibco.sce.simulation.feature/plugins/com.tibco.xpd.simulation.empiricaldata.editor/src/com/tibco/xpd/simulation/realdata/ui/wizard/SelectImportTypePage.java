/* 
 ** 
 **  MODULE:             $RCSfile: EmpiricalDataWizardImportData2Page.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-18 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.realdata.ui.wizard;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.simulation.EmpircalDataEditorPlugin;
import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class SelectImportTypePage extends AbstractXpdWizardPage implements ISelectionChangedListener {

    private Composite composite = null;

    private Label importersLabel = null;

    private TableViewer importersViever = null;

    private RealDataWizard wizard;
    

    protected SelectImportTypePage(RealDataWizard wizard, String pageName) {
        super(pageName);
        this.wizard = wizard;
    }

    public void createControl(Composite parent) {
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 12;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);
        importersLabel = new Label(composite, SWT.NONE);
        importersLabel.setText(Messages.SelectImportTypePage_Historical);
        importersViever  = new TableViewer(composite, SWT.BORDER);
        importersViever.getTable().setLayoutData(gridData);
        importersViever.setContentProvider(new ArrayContentProvider());
        importersViever.setLabelProvider(new LabelProvider() {
            public Image getImage(Object element) {
                return super.getImage(element);
            }

            public String getText(Object element) {
                return EmpircalDataEditorPlugin.INSTANCE.getString("_UI_ImportTypePage_" + (element == null ? "" : element.toString()) + "_importer"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            }
        });
        importersViever.addSelectionChangedListener(this);
        importersViever.setInput(wizard.getImportTypes());
        updatePageComplite();
        setControl(composite);
    }

    private void updatePageComplite() {
        //errors
        ISelection selection = importersViever.getSelection();
        if (selection == null || selection.isEmpty()) {
            setMessage(null);
            setErrorMessage(Messages.SelectImportTypePage_Select);
            return;            
        }
        
        wizard.getNextPage(this);
        wizard.getPreviousPage(this);
        setPageComplete(true);
        
        //warnings
        setMessage(null);
        setErrorMessage(null);
    }

    public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSource() == importersViever) {           
            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
            wizard.setCurrentImportType((String) selection.getFirstElement());
            updatePageComplite();
        }        
    }
} 
