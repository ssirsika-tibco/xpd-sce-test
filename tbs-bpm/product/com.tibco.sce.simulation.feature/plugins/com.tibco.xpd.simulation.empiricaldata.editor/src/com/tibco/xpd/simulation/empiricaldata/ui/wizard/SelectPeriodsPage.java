/* 
 ** 
 **  MODULE:             $RCSfile: ChooseParametersPage.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-23 $ 
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
package com.tibco.xpd.simulation.empiricaldata.ui.wizard;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.simulation.empiricaldata.util.Periods;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class SelectPeriodsPage extends AbstractXpdWizardPage implements
        ISelectionChangedListener {

    private final ModelImporter modelImporter;

    public SelectPeriodsPage(ModelImporter modelImporter, String pageName) {
        super(pageName);
        // TODO Auto-generated constructor stub
        this.modelImporter = modelImporter;
    }

    private Composite composite;

    private ListViewer availablePeriodsViewer;

    private ListViewer selectedPeriodsViewer;

    private TableViewer paramsViewer;

    private Combo timeParameterCombo;

    private Button addPeriodButton;

    private Button deletePeriodButton;

    public void createControl(Composite parent) {

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        Group paramGroup = new Group(composite, SWT.NULL);
        paramGroup.setText(Messages.SelectPeriodsPage_Periods);
        GridData gridData2 = new GridData(GridData.FILL_BOTH);
        paramGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        paramGroup.setLayout(paramGroupLayout);

        // labels
        Label availablePeriodsLabel = new Label(paramGroup, SWT.NONE);
        availablePeriodsLabel.setText(Messages.SelectPeriodsPage_Available);
        Label buttonsLabel = new Label(paramGroup, SWT.NONE);
        buttonsLabel.setText(""); //$NON-NLS-1$
        Label selectedPeriodsLabel = new Label(paramGroup, SWT.NONE);
        selectedPeriodsLabel.setText(Messages.SelectPeriodsPage_Selected);

        availablePeriodsViewer = new ListViewer(paramGroup, SWT.BORDER);
        availablePeriodsViewer
                .setContentProvider(new ArrayContentProvider());
        availablePeriodsViewer
                .setLabelProvider(new LabelProvider());
        availablePeriodsViewer.addSelectionChangedListener(this);
        GridData availablePeriodsGridData = new GridData(GridData.FILL_BOTH);
        availablePeriodsViewer.getList()
                .setLayoutData(availablePeriodsGridData);

        GridLayout listButtonsGridLayout = new GridLayout();
        listButtonsGridLayout.numColumns = 1;
        Composite listButtonsComposite = new Composite(paramGroup, SWT.NONE);
        listButtonsComposite.setLayout(listButtonsGridLayout);
        listButtonsComposite.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL, SWT.BEGINNING, false, false));

        addPeriodButton = new Button(listButtonsComposite, SWT.PUSH);
        addPeriodButton.setText(Messages.SelectPeriodsPage_Add);
        addPeriodButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                onAddButonClicked(); 
            }
        });
        GridData addButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
        addPeriodButton.setLayoutData(addButtonGridData);

        deletePeriodButton = new Button(listButtonsComposite, SWT.PUSH);
        deletePeriodButton.setText(Messages.SelectPeriodsPage_Remove);
        deletePeriodButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                onDeleteButonClicked(); 
            }
        });
        GridData deleteButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
        deletePeriodButton.setLayoutData(deleteButtonGridData);

        selectedPeriodsViewer = new ListViewer(paramGroup, SWT.BORDER);
        selectedPeriodsViewer
                .setContentProvider(new ArrayContentProvider());
        selectedPeriodsViewer
                .setLabelProvider(new LabelProvider());
        selectedPeriodsViewer.addSelectionChangedListener(this);
        GridData selectedPeriodsGridData = new GridData(GridData.FILL_BOTH);
        selectedPeriodsViewer.getList().setLayoutData(selectedPeriodsGridData);
       
        
        updatePageComplite();
        setControl(composite);
    }

    protected void onDeleteButonClicked() {
        IStructuredSelection selection = (IStructuredSelection) selectedPeriodsViewer.getSelection();
        if (!selection.isEmpty()) {
            Object selectedPeriods = selection.getFirstElement();
            if (selectedPeriods instanceof Periods.TimePeriod) {
                modelImporter.removeFromSelectedPeriods((Periods.TimePeriod) selectedPeriods);
                selectedPeriodsViewer.refresh();
                availablePeriodsViewer.refresh();
            }
        }      
    }

    protected void onAddButonClicked() {
        IStructuredSelection selection = (IStructuredSelection) availablePeriodsViewer.getSelection();
        if (!selection.isEmpty()) {
            Object selectedPeriods = selection.getFirstElement();
            if (selectedPeriods instanceof Periods.TimePeriod) {
                modelImporter.addToSelectedPeriods((Periods.TimePeriod) selectedPeriods);
                selectedPeriodsViewer.refresh();
                availablePeriodsViewer.refresh();
            }
        }        
    }

    private void updatePageComplite() {
        // errors
        /*
         * if (dataTableViewer.getSelection() == null) { setMessage(null);
         * setErrorMessage("Select one of the types!"); return; }
         */

        setPageComplete(true);

        // warnings
        setMessage(null);
        setErrorMessage(null);
    }

    public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSource() == availablePeriodsViewer) {
            updatateButtons();
        }
        if (event.getSource() == selectedPeriodsViewer) {
            updatateButtons();
        }
    }

    private void updatateButtons() {
        if (availablePeriodsViewer.getSelection().isEmpty()) {
            addPeriodButton.setEnabled(false);
        } else {
            addPeriodButton.setEnabled(true);
        }
        if (selectedPeriodsViewer.getSelection().isEmpty()) {
            deletePeriodButton.setEnabled(false);
        } else {
            deletePeriodButton.setEnabled(true);
        }
    }

    public void setVisible(boolean visible) {
        if (visible && modelImporter != null) {
            availablePeriodsViewer.setInput(modelImporter.getAvailablePeriods());
            availablePeriodsViewer.refresh();
            selectedPeriodsViewer.setInput(modelImporter.getSelectedPeriods());
            selectedPeriodsViewer.refresh();
            updatateButtons();
        }
        super.setVisible(visible);
    }
    
}
