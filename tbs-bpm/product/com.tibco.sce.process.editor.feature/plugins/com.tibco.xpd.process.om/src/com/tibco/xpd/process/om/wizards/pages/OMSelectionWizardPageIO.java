/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.wizards.pages;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExplorerTreeViewer;

/**
 * Wizard page for selecting an organisation model (multi select enabled). Used by the 
 * participant import wizard to import all om elements to participants in the selected 
 * xpdl files.
 * 
 * @author glewis
 * 
 */
public class OMSelectionWizardPageIO extends AbstractXpdWizardPage implements SelectionListener {

    // Tree view control
    private ExplorerTreeViewer fileViewer;

    // This will be set to the initial selection and subsequently to the current
    // selection in the tree
    private IStructuredSelection selection;  
    
    private Text txtTypes;    
    
    private Button btnPickTypes;   
    
    private final Object input;

    private final ViewerSorter sorter;

    private final ViewerFilter[] filters;
    
    private IFolder selectedFolder = null;
    
    private Object[] filteredTypes = null;
    
    private static final String REFLECTION_OM_RESOURCES_UI_PLUGIN_ID = "com.tibco.xpd.om.resources.ui"; //$NON-NLS-1$
    
    private static final String REFLECTION_OM_RESOURCES_UI_PACKAGEFOLDERPICKER_CLASS = "com.tibco.xpd.om.resources.ui.pickers.PackageFolderPicker"; //$NON-NLS-1$
    
    private static final String REFLECTION_OM_RESOURCES_UI_OMTYPESPICKER_CLASS = "com.tibco.xpd.om.resources.ui.pickers.OMTypesPicker"; //$NON-NLS-1$
        
    private static final String REFLECTION_OM_SET_ALLOW_MULTIPLE_METHOD = "setAllowMultiple"; //$NON-NLS-1$
    
    private static final String REFLECTION_OM_SET_INPUT_METHOD = "setInput"; //$NON-NLS-1$
    
    private static final String REFLECTION_OM_OPEN_METHOD = "open"; //$NON-NLS-1$
    
    private static final String REFLECTION_OM_GET_RESULT_METHOD = "getResult"; //$NON-NLS-1$
    
    private static final String REFLECTION_OM_GET_DEFAULT_OM_TYPES_METHOD = "getDefaultOMTypes"; //$NON-NLS-1$    
    
    private Bundle omResourcesBundle = null;
    
    private Class omPackagePickerCls = null;
    
    private Class omTypesPickerCls = null;
    
    private Method setAllowMultipleMeth = null;
    
    private Method setInputMeth = null;
    
    private Method openMeth = null;
    
    private Method getResultMeth = null;
    
    private Method getDefaultOMTypesMeth = null;

    /**
     * Constructor.
     * 
     * @param selection
     *            The initial selection to be made in the tree viewer.
     * @param input
     *            The input of the tree viewer.
     * @param sorter
     *            The <code>ViewerSorter</code> to apply to the tree viewer.
     * 
     * @param filters
     *            An array of <code>ViewerFilter</code>s to apply to filter
     *            the content of the tree viewer.
     */
    public OMSelectionWizardPageIO(Object input,
            ViewerSorter sorter, ViewerFilter[] filters) {
        super(Messages.OMSelectionWizardPageIO_ImportParticipants_title);        
        this.input = input;
        this.sorter = sorter;
        this.filters = filters;
        
        try{
            omResourcesBundle = Platform.getBundle(REFLECTION_OM_RESOURCES_UI_PLUGIN_ID);
            if (omResourcesBundle != null){   
                omPackagePickerCls = omResourcesBundle.loadClass(REFLECTION_OM_RESOURCES_UI_PACKAGEFOLDERPICKER_CLASS);
                omTypesPickerCls = omResourcesBundle.loadClass(REFLECTION_OM_RESOURCES_UI_OMTYPESPICKER_CLASS);
                
                Class setAllowMultParam = boolean.class;                
                Class setInputParam = Object.class;           
                      
                setAllowMultipleMeth = omPackagePickerCls.getMethod(REFLECTION_OM_SET_ALLOW_MULTIPLE_METHOD, setAllowMultParam);
                setInputMeth  = omPackagePickerCls.getMethod(REFLECTION_OM_SET_INPUT_METHOD, setInputParam);
                openMeth  = omPackagePickerCls.getMethod(REFLECTION_OM_OPEN_METHOD, null);
                getResultMeth  = omPackagePickerCls.getMethod(REFLECTION_OM_GET_RESULT_METHOD, null);
                getDefaultOMTypesMeth  = omTypesPickerCls.getMethod(REFLECTION_OM_GET_DEFAULT_OM_TYPES_METHOD, null);                
            }
        } catch (Exception e) {            
        }   
    }

    @Override
    public void dispose() {

        fileViewer.dispose();
        fileViewer = null;

        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);

        GridLayout gridLayout = new GridLayout();
        container.setLayout(gridLayout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Create the package viewer control
        createTreeControl(container);
        
        createOMTypesControl(container);

        setControl(container);

        // Update page completion
        updatePageCompletion();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        Object source = e.getSource();

        if (source instanceof Button) {
            Button btn = (Button) source;

            // Only execute if the radio button is selected or a button is
            // pushed
            boolean execute = true;

            if ((btn.getStyle() & SWT.RADIO) == SWT.RADIO) {
                execute = btn.getSelection();
            }

            if (execute) {               
                if(source == btnPickTypes){
                    try{
                        handleContainerPickTypesButtonPressed();
                    }catch(Exception e1){    
                        int h=0;
                    }
                }

                // Update page completion
                updatePageCompletion();
            }
        }
    }

    /**
     * @return
     */
    public Object[] getFilteredOMTypes(){
       return filteredTypes;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Returns a list of files selected in the File Viewer
     * 
     * @return IStructuredSelection List of files selected, empty list if no
     *         files selected
     */
    public IStructuredSelection getSelectedFiles() {
        return selection;
    }    

    /**
     * Gets the export destination folder
     */
    public final IFolder getDestinationFolder() {

        return selectedFolder;
    }

    /**
     * Check page completion
     */
    protected void updatePageCompletion() {
        boolean complete = true;

        setPageComplete(false);

        // Check that we have selection in the tree
        selection = fileViewer.getSelectedFiles();

        if (selection == null || selection.isEmpty()) {
            // No selection
            setMessage(Messages.OMSelectionWizardPageIO_selectFileToImport_shortdesc);
            complete = false;
        }       

        // If complete then clear error
        if (complete) {
            setErrorMessage(null);
        }

        setPageComplete(complete);
    }

    /**
     * Create the file viewer control
     * 
     * @param container
     */
    private void createTreeControl(Composite container) {
        GridData gridData;

        // Expand/Collapse all toolbar
        final ToolBar tBar = new ToolBar(container, SWT.HORIZONTAL | SWT.FLAT);
        tBar.setLayout(new RowLayout(SWT.HORIZONTAL));
        gridData = new GridData(SWT.END, 0, false, false);
        tBar.setLayoutData(gridData);

        final ToolItem tExpandAll = new ToolItem(tBar, SWT.PUSH);
        tExpandAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry()
                .get(XpdResourcesUIConstants.EXPORT_EXPAND_ALL));
        tExpandAll.setToolTipText(Messages.OMSelectionWizardPageIO_expandAll_label);
        tExpandAll.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                fileViewer.expandAll();
            }
        });

        final ToolItem tCollapseAll = new ToolItem(tBar, SWT.PUSH);
        tCollapseAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry()
                .get(XpdResourcesUIConstants.EXPORT_COLLAPSE_ALL));
        tCollapseAll
                .setToolTipText(Messages.OMSelectionWizardPageIO_collapseAll_label);
        tCollapseAll.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                fileViewer.collapseAll();
            }
        });

        // Package tree viewer
        fileViewer = new ExplorerTreeViewer(container, input, sorter, filters);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 2;
        gridData.widthHint = 100;
        gridData.heightHint = 150;
        fileViewer.getControl().setLayoutData(gridData);
        
        fileViewer
                .addPostSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        // Update page completion
                        updatePageCompletion();
                    }
                });

        //
        // Do not expand all (not expanding will just cause the parent nodes
        // of the selected elements to be expanded - which is what we want
        // really anyway).
        // It is a bad idea to expand all because this will always cause a get
        // children on things (like xpdl files etc) and that will always cause
        // the file to be loaded into memory. So expandAll causes every files
        // that matches the filter to be loaded into memory.
        //
        // fileViewer.expandAll();
        //

        // Container for the Select/unselect all buttons
        final Composite cmpTreeSelect = new Composite(container, SWT.NULL);
        RowLayout selectLayout = new RowLayout();
        selectLayout.type = SWT.HORIZONTAL;
        cmpTreeSelect.setLayout(selectLayout);

        // Select All button
        final Button btnSelectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnSelectAll.setText(Messages.OMSelectionWizardPageIO_selectAll_label);
        btnSelectAll.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                fileViewer.setAllChecked(true);
                updatePageCompletion();
            }
        });

        // Unselect All button
        final Button btnUnselectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnUnselectAll.setText(Messages.OMSelectionWizardPageIO_unselectAll_label);
        btnUnselectAll.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                fileViewer.setAllChecked(false);
                updatePageCompletion();
            }
        });
    }
    
    /**
     * Create the filtered om types picker control
     * 
     * @param container
     */
    private void createOMTypesControl(Composite container) {
        GridData gridData;   
        
        // om types group
        final Group grpTypes = new Group(container, SWT.NULL);
        grpTypes
                .setText(Messages.OMSelectionWizardPageIO_filteredTypesGroup_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        grpTypes.setLayoutData(gridData);
        grpTypes.setLayout(new GridLayout(2, false));        

        // Path input text control
        txtTypes = new Text(grpTypes, SWT.BORDER);
        gridData = new GridData(GridData.FILL_BOTH);
        txtTypes.setLayoutData(gridData);      

        // Disable the text control as default
        txtTypes.setEnabled(false);
        txtTypes.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                // Update page completion
                updatePageCompletion();
            }
        });
        
        try {
            Object[] result = (Object[])getDefaultOMTypesMeth.invoke(omTypesPickerCls);
            setTypes(result);
        } catch (IllegalArgumentException e1) {            
        } catch (IllegalAccessException e1) {
        } catch (InvocationTargetException e1) {            
        }
        

        // Types browse button
        btnPickTypes = new Button(grpTypes, SWT.NONE);
        btnPickTypes.setText(Messages.OMSelectionWizardPageIO_browse_label);                
        btnPickTypes.addSelectionListener(this);
    }
   
    /**
     * Sets the types and displays them comma delimited using the name of om classes  
     * @param result
     */
    private void setTypes(Object[] result){
        filteredTypes = null;            
        txtTypes.setText(""); //$NON-NLS-1$
        String txtPathStr = ""; //$NON-NLS-1$
        if (result != null && result.length > 0){
            for (int i=0; i< result.length; i++){
                String fullClsName = (String) result[i];
                int index = fullClsName.lastIndexOf('.') + 1;
                txtPathStr += fullClsName.substring(index, fullClsName.length());
                txtPathStr += ", ";  //$NON-NLS-1$       
            }
            int lastIndex = txtPathStr.lastIndexOf(',');
            txtPathStr = txtPathStr.substring(0,lastIndex);
            filteredTypes = result;
            txtTypes.setText(txtPathStr);
        }
    }
    
    /**
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InstantiationException 
     * Shows the om types picker for user to select which types they want importing as participants into the xpdl files.
     */
    protected void handleContainerPickTypesButtonPressed() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = omTypesPickerCls.getConstructor(Shell.class);
        Object packageFolderPickerObj = constructor.newInstance(getShell());
        if (packageFolderPickerObj != null){            
            Object retVal = openMeth.invoke(packageFolderPickerObj);            
            if (((Integer)retVal).intValue() == Dialog.OK) {
                Object[] result = (Object[])getResultMeth.invoke(packageFolderPickerObj);
                setTypes(result);           
            }
        }
    }
}
