package com.tibco.xpd.simulation.ui.views;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * A composite control that presents a navigable real-time view of an EMF object tree. The
 * control is split into a Tree on the left and a Table on the right separated by a draggable
 * Sash. The control takes an EObject to use as the root element of the tree and the tree is
 * populated with all non-leaf nodes of the object structure. On selecting an object in the tree
 * the table is updated to show the data contained by the selected object.
 * @author NWilson
 */
public class EObjectTreeViewer extends Composite implements SelectionListener {
    private Tree tree;
    private Sash sash;
    private ResultsContentViewer tableContentManager;
    private Object currentSelection;
    private TreeViewer viewer;
    
    public EObjectTreeViewer(Composite parent, AdapterFactory factory) {
        super(parent, SWT.NONE);

        GridLayout layout = new GridLayout(); 
        layout.numColumns = 3;
        layout.marginWidth = 0;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        setLayout(layout);
        
        tree  = new Tree(this, SWT.SINGLE);
        GridData treeGridData = new GridData();
        treeGridData.widthHint = 100;
        treeGridData.grabExcessVerticalSpace = true;
        treeGridData.verticalAlignment = SWT.FILL;
        treeGridData.horizontalAlignment = SWT.FILL;
        tree.setLayoutData(treeGridData);
        tree.addSelectionListener(this);
        
        sash = new Sash(this, SWT.VERTICAL);
        GridData sashGridData = new GridData();
        sashGridData.grabExcessVerticalSpace = true;
        sashGridData.verticalAlignment = SWT.FILL;
        sash.setLayoutData(sashGridData);
        sash.addSelectionListener(this);
        
        tableContentManager = new ResultsContentViewer(this, factory);
        GridData contentGridData = new GridData();
        contentGridData.grabExcessVerticalSpace = true;
        contentGridData.grabExcessHorizontalSpace = true;
        contentGridData.verticalAlignment = SWT.FILL;
        contentGridData.horizontalAlignment = SWT.FILL;
        tableContentManager.setLayoutData(contentGridData);
                
        viewer = new TreeViewer(tree);
        viewer.setContentProvider(new AdapterFactoryContentProvider(factory));
        viewer.setLabelProvider(new AdapterFactoryLabelProvider(factory));
    }

    public void setInput(EObject input) {
        if (input != null) {
            viewer.setInput(input);
            tableContentManager.changeContent(null);
            TreeItem item = tree.getItem(0);
            if (item != null) {
                tree.setSelection(new TreeItem[] {item});
                updateTableContents();
            }
        }
    }

    public void widgetSelected(SelectionEvent e) {
        if (e.getSource() == tree) {
            updateTableContents();
        } else if (e.getSource() == sash) {
            if (e.detail != SWT.DRAG) {
                GridData data = (GridData) tree.getLayoutData();
                data.widthHint = e.x - 16;
                tree.getParent().layout();
            }
        }
    }
    
    private void updateTableContents() {
        TreeItem[] items = tree.getSelection();
        if (items.length == 1) {
            TreeItem item = items[0];
            Object modelObject = item.getData();
            if (modelObject != currentSelection) {
                if (modelObject instanceof EObject) {
                    EObject eObject = (EObject) modelObject;
                    currentSelection = modelObject;
                    tableContentManager.changeContent(eObject);
                } else if (modelObject instanceof IStructuredItemContentProvider) {
                    IStructuredItemContentProvider provider = (IStructuredItemContentProvider) modelObject;
                    currentSelection = modelObject;
                    tableContentManager.changeContent(provider);
                }/* else if (modelObject instanceof EObjectContainmentEList) {
                    EObjectContainmentEList eList = (EObjectContainmentEList) modelObject;
                    currentSelection = modelObject;
                    tableContentManager.changeContent(eList);
                }*/ else {
                    tableContentManager.reset();
                }
            }
        } else {
            tableContentManager.reset();
        }
    }

    public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void addTreeFilter(ViewerFilter filter) {
        viewer.addFilter(filter);
    }
}
