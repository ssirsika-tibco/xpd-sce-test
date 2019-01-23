package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.SummaryInfoUtils;
import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Selection dialog that will prompt the user with a list of attributes in a
 * tree structure from a given Case CLass
 * 
 */
public class SummaryAttributeSelectionDialog extends SelectionStatusDialog {

    private TreeViewer treeViewer = null;

    private Class selectedClass;

    public SummaryAttributeSelectionDialog(Shell parentShell, Class rootClass) {
        super(parentShell);

        this.selectedClass = rootClass;

        setTitle(Messages.SummaryTabSection_section_title);
        setStatusLineAboveButtons(true);
        return;
    }

    @Override
    protected void computeResult() {
        setResult(((IStructuredSelection) treeViewer.getSelection()).toList());
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        // Create the controls.
        Composite composite = (Composite) super.createDialogArea(parent);

        // Create the error viewer.
        treeViewer = new TreeViewer(composite, SWT.BORDER | SWT.SINGLE);

        treeViewer.setLabelProvider(new AttributePickerLabelProvider());
        treeViewer.setContentProvider(new AttributeContentProvider());

        // Setup the treeviewer content
        treeViewer.setInput(this);

        // Make sure the tree is completely expanded
        treeViewer.expandAll();

        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = convertWidthInCharsToPixels(60);
        data.heightHint = convertHeightInCharsToPixels(18);

        Tree treeWidget = treeViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());

        // Grey out all the items that are already in the summary
        List<String> summaryArray =
                SummaryInfoUtils.getSummaryArray(selectedClass);
        for (TreeItem classItem : treeWidget.getItems()) {
            for (TreeItem treeItem : classItem.getItems()) {
                Object treeItemData = treeItem.getData();
                if (treeItemData instanceof Property) {
                    if (summaryArray.contains(((Property) treeItemData)
                            .getName())) {
                        // Make the items that already exist in the summary
                        // grayed out
                        treeItem.setForeground(ColorConstants.lightGray);
                    }
                }
            }
        }

        // Add a listener that will detect when the selection changes and
        // update the OK button on the dialog
        treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                updateOKEnabled();
            }

        });

        // Add support for the user double clicking on an entry to add it
        treeViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (updateOKEnabled()) {
                    okPressed();
                }
            }
        });
        return composite;
    }

    @Override
    protected Control createButtonBar(Composite parent) {
        Control ctrl = super.createButtonBar(parent);
        // Set the initial state of the OK button
        updateOKEnabled();
        return ctrl;
    }

    /**
     * Update the enablement state of the OK button depending on the user's
     * selection.
     * 
     * @return True is enabled, otherwise false
     */
    private boolean updateOKEnabled() {
        boolean isOk = false;

        Status status =
                new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        Messages.SummaryTabSection_dialog_error);

        // Update if the OK button is enabled or not
        ISelection selection = treeViewer.getSelection();
        if (!selection.isEmpty()) {
            if (selection instanceof StructuredSelection) {
                Object firstElement =
                        ((StructuredSelection) selection).getFirstElement();
                if (firstElement instanceof Property) {
                    boolean isEnabled = true;
                    // Make sure the attribute has not been disabled
                    for (TreeItem treeItem : treeViewer.getTree()
                            .getSelection()) {
                        if (treeItem.getForeground()
                                .equals(ColorConstants.lightGray)) {
                            isEnabled = false;
                            break;
                        }
                    }
                    if (isEnabled == false) {
                        status =
                                new Status(
                                        IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        Messages.SummaryTabSection_dialog_exists);
                    } else {
                        status =
                                new Status(IStatus.INFO, Activator.PLUGIN_ID,
                                        Messages.SummaryTabSection_dialog_info);
                    }
                    isOk = isEnabled;
                }
            }
        }

        updateStatus(status);

        return isOk;
    }

    /**
     * Label Provider for the dialog, will display the labels for the Named
     * Elements
     */
    private class AttributePickerLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            // Get the label text for the named element
            String txt = null;
            if (element instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) element;
                txt = PrimitivesUtil.getDisplayLabel(namedElement);
            }
            return txt != null ? txt : super.getText(element);
        }

        @Override
        public Image getImage(Object element) {
            // Get the icon used for the named element
            Image img = null;
            if (element instanceof NamedElement
                    && !((EObject) element).eIsProxy()) {
                img = WorkingCopyUtil.getImage((EObject) element);
            }
            return img != null ? img : super.getImage(element);
        }
    }

    /**
     * Class that provides the data to be displayed in the tree
     * 
     */
    private class AttributeContentProvider implements ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Class) {
                List<Property> filtered = new ArrayList<Property>();

                // Get all the attributes of this class, and it's super classes
                for (Property item : ((Class) parentElement).getAttributes()) {
                    filtered.add(item);
                }

                return filtered.toArray();
            }

            return new Object[0];
        }

        public Object getParent(Object element) {
            if (element instanceof Property) {
                return ((Property) element).getOwner();
            }
            return null;
        }

        public boolean hasChildren(Object element) {
            if (element instanceof Class) {
                return true;
            }

            return false;
        }

        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof SummaryAttributeSelectionDialog) {
                // The elements are just the one that was passed into when
                // the dialog was created, and any classes it extends
                List<Class> filtered = new ArrayList<Class>();
                // Start by adding the root class
                filtered.add(selectedClass);

                EList<Class> superClasses = selectedClass.getSuperClasses();
                while (!superClasses.isEmpty()) {
                    // Only support single inheritance, so will only actually be
                    // one entry here
                    for (Class superClass : superClasses) {
                        filtered.add(superClass);
                        // Now check to see if this class has a superclass
                        superClasses = superClass.getSuperClasses();
                    }
                }

                return filtered.toArray();
            }
            return new Object[0];
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }
}
