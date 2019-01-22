/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * New Diagram wizard page to select elements to add to the diagram.
 * 
 * @author njpatel
 * 
 */
public class SelectBOMElementsWizardPage extends AbstractXpdWizardPage {

    private CheckboxTableViewer viewer;

    private Text nameTxt;

    private Model model;

    private Collection<Diagram> diagrams;

    protected SelectBOMElementsWizardPage() {
        super("selectElements"); //$NON-NLS-1$

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout(2, false));

        Label lbl = new Label(root, SWT.NONE);
        lbl.setText(Messages.SelectBOMElementsWizardPage_diagramName_label);

        nameTxt = new Text(root, SWT.BORDER);
        nameTxt.setText(getNextDiagramName());
        nameTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        nameTxt.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setPageComplete(doValidate());
            }
        });

        lbl = new Label(root, SWT.NONE);
        lbl
                .setText(Messages.SelectBOMElementsWizardPage_selectElementsList_label);
        GridData data = new GridData();
        data.horizontalSpan = 2;
        lbl.setLayoutData(data);

        viewer = CheckboxTableViewer.newCheckList(root, SWT.BORDER);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 2;
        data.heightHint = 250;
        viewer.getControl().setLayoutData(data);
        viewer.setContentProvider(new ElementContentProvider());
        viewer.setLabelProvider(new ElementLabelProvider());
        viewer.setInput(model);

        Composite btnsControl = new Composite(root, SWT.NONE);
        data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
        data.horizontalSpan = 2;
        btnsControl.setLayoutData(data);
        btnsControl.setLayout(new RowLayout());

        Button selectAll = new Button(btnsControl, SWT.NONE);
        selectAll
                .setText(Messages.SelectBOMElementsWizardPage_selectAll_button);
        selectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                viewer.setAllChecked(true);
            }
        });

        Button deselectAll = new Button(btnsControl, SWT.NONE);
        deselectAll
                .setText(Messages.SelectBOMElementsWizardPage_deselectAll_button);
        deselectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                viewer.setAllChecked(false);
            }
        });

        setControl(root);
        setPageComplete(doValidate());
    }

    /**
     * Find a unique diagram name.
     * 
     * @return
     */
    private String getNextDiagramName() {
        String name = Messages.SelectBOMElementsWizardPage_diagramdefault_name;

        if (diagrams != null) {
            Set<String> names = new HashSet<String>();
            for (Diagram diag : diagrams) {
                names.add(diag.getName());
            }

            // Find a unique name
            int idx = 1;
            while (names.contains(name)) {
                name =
                        String
                                .format(Messages.SelectBOMElementsWizardPage_diagramName_pattern_label,
                                        ++idx);
            }
        }

        return name;
    }

    /**
     * Get the items selected in this page.
     * 
     * @return
     */
    public Collection<EObject> getSelection() {
        List<EObject> sel = new ArrayList<EObject>();

        Object[] elements = viewer.getCheckedElements();
        if (elements != null) {
            for (Object object : elements) {
                if (object instanceof EObject) {
                    sel.add((EObject) object);
                }
            }
        }

        return sel;
    }

    /**
     * Get the name of the diagram as set in this wizard page.
     * 
     * @return
     */
    public String getDiagramName() {
        return nameTxt.getText();
    }

    /**
     * Set the input for this page.
     * 
     * @param wc
     */
    protected void setInput(Model model) {
        this.model = model;
        if (viewer != null && !viewer.getControl().isDisposed()) {
            viewer.setInput(model);
        }

        // Load the diagrams already existing in the model resource
        diagrams = null;
        if (model != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(model);
            if (wc instanceof BOMWorkingCopy) {
                diagrams = ((BOMWorkingCopy) wc).getDiagrams();
            }
        }

        setPageComplete(doValidate());
    }

    /**
     * Validate the input in this page.
     * 
     * @return
     */
    private boolean doValidate() {

        // No BOM selected
        if (model == null) {
            setErrorMessage(Messages.SelectBOMElementsWizardPage_noBOMSelected_error_message);
            return false;
        }

        if (nameTxt != null && !nameTxt.isDisposed()) {
            String name = nameTxt.getText();
            // No diagram name
            if (name.length() == 0) {
                setErrorMessage(Messages.SelectBOMElementsWizardPage_emptyDiagramName_error_message);
                return false;
            }

            // Duplicate diagram name
            if (isDiagramNameInUse(name)) {
                setErrorMessage(String
                        .format(Messages.SelectBOMElementsWizardPage_duplicateDiagramName_message,
                                name));
                return false;
            }

        }

        setErrorMessage(null);
        return true;
    }

    /**
     * Check if the given diagram name is already in use.
     * 
     * @param name
     * @return
     */
    private boolean isDiagramNameInUse(String name) {
        if (diagrams != null && name != null) {
            for (Diagram diagram : diagrams) {
                if (name.equals(diagram.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private class ElementLabelProvider extends LabelProvider {

        private final TransactionalAdapterFactoryLabelProvider factoryLabelProvider;

        public ElementLabelProvider() {
            factoryLabelProvider =
                    new TransactionalAdapterFactoryLabelProvider(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            XpdResourcesPlugin.getDefault().getAdapterFactory());
        }

        @Override
        public String getText(Object element) {
            String text = null;
            if (element instanceof NamedElement) {
                text = getQualifiedName((NamedElement) element);
            }

            return text != null ? text : factoryLabelProvider.getText(element);
        }

        /**
         * Get the (model-relative) qualified name of this {@link NamedElement}.
         * 
         * @param element
         * @return
         */
        private String getQualifiedName(NamedElement element) {
            StringBuffer qName = new StringBuffer();

            if (element != null) {
                /*
                 * Using factoryLabelProvider to get the text will return the
                 * name and label (eg, Class 1 (Class1)) so not using it here
                 */
                qName.append(element.getName());
                do {
                    element =
                            (NamedElement) (element.eContainer() instanceof NamedElement ? element
                                    .eContainer()
                                    : null);
                    if (element instanceof Model) {
                        // Qualified name is Model relative
                        element = null;
                    }
                    if (element != null) {
                        qName.insert(0, BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR);
                        qName.insert(0, element.getName());
                    }
                } while (element != null);
            }
            return qName.toString();
        }

        @Override
        public Image getImage(Object element) {
            return factoryLabelProvider.getImage(element);
        }

        @Override
        public void dispose() {
            factoryLabelProvider.dispose();
            super.dispose();
        }
    }

    private class ElementContentProvider implements IStructuredContentProvider {

        private final TransactionalAdapterFactoryContentProvider factoryContentProvider;

        public ElementContentProvider() {
            factoryContentProvider =
                    new TransactionalAdapterFactoryContentProvider(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            XpdResourcesPlugin.getDefault().getAdapterFactory());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
         * java.lang.Object)
         */
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement).toArray();
        }

        private List<Object> getChildren(Object input) {
            Object[] elements = factoryContentProvider.getElements(input);
            List<Object> elems = new ArrayList<Object>();
            // Filter out any packages and association classes
            for (Object elem : elements) {

                if (elem instanceof AssociationClass) {
                    continue;
                }

                if (elem instanceof Package) {
                    // Get all children of package
                    elems.addAll(getChildren(elem));
                } else {
                    elems.add(elem);

                }
            }
            return elems;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {
            factoryContentProvider.dispose();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
         * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            factoryContentProvider.inputChanged(viewer, oldInput, newInput);
        }
    }
}
