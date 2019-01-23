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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.simulation.empiricaldata.Messages;
import com.tibco.xpd.simulation.empiricaldata.Period;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

public class PeriodsDataEditorPage extends AbstractXpdWizardPage implements
        ISelectionChangedListener {

    private final ModelImporter modelImporter;

    public PeriodsDataEditorPage(ModelImporter modelImporter, String pageName) {
        super(pageName);
        // TODO Auto-generated constructor stub
        this.modelImporter = modelImporter;
    }

    private Composite composite;

    private TreeViewer periodsViewer;

    private TableViewer parametersViewer;
    
    private Combo parameterCombo;

    public void createControl(Composite parent) {

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        Group paramGroup = new Group(composite, SWT.NULL);
        paramGroup.setText(Messages.PeriodsDataEditorPage_Parameter);
        GridData paramComboGridData = new GridData(GridData.FILL_HORIZONTAL);
        paramGroup.setLayoutData(paramComboGridData);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        paramGroup.setLayout(paramGroupLayout);
        
        Label chooseParameterLabel = new Label(paramGroup, SWT.NONE);
        chooseParameterLabel.setText(Messages.PeriodsDataEditorPage_Choose);
        
        parameterCombo = new Combo(paramGroup, SWT.BORDER);
        parameterCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Group periodsGroup = new Group(composite, SWT.NULL);
        periodsGroup.setText(Messages.PeriodsDataEditorPage_Periods);
        GridData periodsGridData = new GridData(GridData.FILL_BOTH);
        periodsGroup.setLayoutData(periodsGridData);
        GridLayout periodsGroupLayout = new GridLayout();
        periodsGroupLayout.numColumns = 2;
        periodsGroup.setLayout(periodsGroupLayout);

        // labels
        Label availablePeriodsLabel = new Label(periodsGroup, SWT.NONE);
        availablePeriodsLabel.setText(Messages.PeriodsDataEditorPage_Intervals);
        
        Label selectedPeriodsLabel = new Label(periodsGroup, SWT.NONE);
        selectedPeriodsLabel.setText(Messages.PeriodsDataEditorPage_ParametersDensity);

        periodsViewer = new TreeViewer(periodsGroup, SWT.BORDER);
        periodsViewer
                .setContentProvider(new AdapterFactoryContentProvider(modelImporter.getAdapterFactory()));
        periodsViewer
                .setLabelProvider(new AdapterFactoryLabelProvider(modelImporter.getAdapterFactory()));
        periodsViewer.addSelectionChangedListener(this);
        GridData periodsViewerGridData = new GridData(GridData.FILL_BOTH);
        periodsViewer.getTree()
                .setLayoutData(periodsViewerGridData);
 
        parametersViewer = new TableViewer(periodsGroup, SWT.BORDER);
        
        parametersViewer
                .setContentProvider(new AdapterFactoryContentProvider(modelImporter.getAdapterFactory()));
        parametersViewer
                .setLabelProvider(new CasesDensityTableLabelProvider());
        Table pTable = parametersViewer.getTable();
        TableLayout layout = new TableLayout();
        pTable.setLayout(layout);
        pTable.setHeaderVisible(true);
        pTable.setLinesVisible(true);
        
        TableColumn objectColumn = new TableColumn(pTable, SWT.NONE);
        layout.addColumnData(new ColumnWeightData(3, 100, true));
        objectColumn.setText(Messages.PeriodsDataEditorPage_Name);
        objectColumn.setResizable(true);

        TableColumn selfColumn = new TableColumn(pTable, SWT.NONE);
        layout.addColumnData(new ColumnWeightData(2, 100, true));
        selfColumn.setText(Messages.PeriodsDataEditorPage_Weight);
        selfColumn.setResizable(true);
        
        parametersViewer.addSelectionChangedListener(this);
        GridData selectedPeriodsGridData = new GridData(GridData.FILL_BOTH);
        parametersViewer.getTable().setLayoutData(selectedPeriodsGridData);
       
        
        updatePageComplite();
        setControl(composite);
    }

    private class CasesDensityTableLabelProvider extends LabelProvider implements ITableLabelProvider {

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
        public String getColumnText(Object element, int columnIndex) {
            Period period = null;
            if (element instanceof Period) {
                period = (Period) element;
            } else {
                return null;
            }
            switch (columnIndex) {
            case 0:
                return period.getName();
            case 1:
                return Double.toString(period.getWeightingFactor());
            default:
                break;
            }
            return null;
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
        if (event.getSource() == periodsViewer) {
            ISelection sel = event.getSelection();
            if (sel instanceof IStructuredSelection) {
                IStructuredSelection ss = ((IStructuredSelection) sel);
                Object selectedObj = ss.getFirstElement();
                parametersViewer.setInput(selectedObj);
                parametersViewer.refresh();
            }
        }
        if (event.getSource() == parametersViewer) {
           
        }
    }

    public void setVisible(boolean visible) {
        if (visible && modelImporter != null) {
            //TODO change to real parameters.
            setParameterCombo(Collections.EMPTY_LIST);
            modelImporter.createPeriods();
            periodsViewer.setInput(modelImporter.getRootObject());
            periodsViewer.refresh();
            //parametersViewer.setInput(modelImporter.getSelectedPeriods());
            parametersViewer.refresh();
            
        }
        super.setVisible(visible);
    }
    
    private void setParameterCombo(List parameters) {
        parameterCombo.removeAll();
        parameterCombo.add(Messages.PeriodsDataEditorPage_CasesDensity);
        for (Iterator iter = parameters.iterator(); iter.hasNext();) {
            String parameterName = (String) iter.next();
            parameterCombo.add(parameterName);
        }
        parameterCombo.select(0);
    }
}
