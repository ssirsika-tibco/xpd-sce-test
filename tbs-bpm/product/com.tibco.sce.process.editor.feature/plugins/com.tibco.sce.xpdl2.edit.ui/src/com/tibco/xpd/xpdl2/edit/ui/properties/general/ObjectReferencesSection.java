package com.tibco.xpd.xpdl2.edit.ui.properties.general;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;

/**
 * Class that can add controls for a tree-list of given process diagram object
 * references (activities/transitions).
 * <p>
 * The creator simply constructs this and then calls the setProcessFolders()
 * method to set the list contents.
 * <p>
 * The process folder class allows the caller to simpy add activities and
 * transitions as required.
 * <p>
 * This class will the provide a hierarchical tree view from these and also
 * double-click / button Go To facility.
 * 
 * @author aallway
 * 
 */
public class ObjectReferencesSection {

    /**
     * Content provider for refrences tree
     */
    private class DataRefContentProvider implements ITreeContentProvider {
        public Object[] getChildren(Object parentElement) {

            if (parentElement instanceof ObjectReferencesFolder) {
                ObjectReferencesFolder pf = (ObjectReferencesFolder) parentElement;

                return pf.getChildren().toArray();
            }

            return null;
        }

        public Object getParent(Object element) {
            if (element instanceof ObjectReferencesFolder) {
                return ((ObjectReferencesFolder) element).getParentFolder();
            } else if (element instanceof ObjectReferencesItem) {
                return ((ObjectReferencesItem) element).getParentFolder();
            }
            return null;
        }

        public boolean hasChildren(Object element) {
            if (element instanceof ObjectReferencesFolder) {
                if (((ObjectReferencesFolder) element).getChildren().size() > 0) {
                    return true;
                }
            }

            return false;
        }

        public Object[] getElements(Object inputElement) {
            return content.toArray();
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        public void dispose() {
        }

    }

    /**
     * Label provider for refrences tree
     */
    private class DataRefLabelProvider implements ILabelProvider, IFontProvider {

        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof ObjectReferencesFolder) {
                img = ((ObjectReferencesFolder) element).getImage();
            } else if (element instanceof ObjectReferencesItem) {
                img = ((ObjectReferencesItem) element).getImage();
            }

            return img;
        }

        public String getText(Object element) {
            String text = ""; //$NON-NLS-1$

            if (element instanceof ObjectReferencesFolder) {
                text = ((ObjectReferencesFolder) element).getName();
            } else if (element instanceof ObjectReferencesItem) {
                text = ((ObjectReferencesItem) element).getName();
            }

            return text;
        }
        

        /**
         * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
         *
         * @param element
         * @return
         */
        public Font getFont(Object element) {
            return JFaceResources.getFontRegistry().get(JFaceResources.DIALOG_FONT);        
        }

        public void addListener(ILabelProviderListener listener) {
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {
        }

        public void dispose() {
        }

    }

    private Button gotoButton = null;

    private TreeViewer referenceTree = null;

    private List content = Collections.EMPTY_LIST;

    public Composite createControls(Composite parent, XpdFormToolkit toolkit) {

        Composite comp = toolkit.createComposite(parent);

        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 2;
        comp.setLayout(gridLayout);

        Tree refTree = toolkit.createTree(comp, SWT.SINGLE);
        referenceTree = new TreeViewer(refTree);
        refTree.setLayoutData(new GridData(GridData.FILL_BOTH));

        referenceTree.setContentProvider(new DataRefContentProvider());
        referenceTree.setLabelProvider(new DataRefLabelProvider());
        referenceTree.setInput(this);

        referenceTree
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    public void selectionChanged(SelectionChangedEvent event) {
                        boolean enableGoto = false;

                        StructuredSelection sel = (StructuredSelection) event
                                .getSelection();
                        if (sel != null) {
                            Object obj = sel.getFirstElement();

                            if (obj instanceof ObjectReferencesItem
                                    && ((ObjectReferencesItem) obj).canGoto()) {
                                enableGoto = true;
                            }
                        }

                        gotoButton.setEnabled(enableGoto);

                    }
                });

        referenceTree.addOpenListener(new IOpenListener() {

            public void open(OpenEvent event) {
                StructuredSelection sel = (StructuredSelection) referenceTree
                        .getSelection();
                if (sel != null) {
                    Object element = sel.getFirstElement();

                    if (element instanceof ObjectReferencesItem
                            && ((ObjectReferencesItem) element).canGoto()) {
                        // Ask the object references item to jump to the object.
                        ((ObjectReferencesItem) element).gotoItem();

                    } else if (referenceTree.isExpandable(element)) {
                        if (referenceTree.getExpandedState(element)) {
                            referenceTree.collapseToLevel(element, 1);
                        } else {
                            referenceTree.expandToLevel(element, 1);
                        }
                    }
                }
            }
        });

        gotoButton = toolkit.createButton(comp,
                Messages.BaseFieldOrParamPropertySection_Goto_label, SWT.PUSH);
        gotoButton.setLayoutData(new GridData());

        gotoButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                StructuredSelection sel = (StructuredSelection) referenceTree
                        .getSelection();
                if (sel != null) {
                    Object element = sel.getFirstElement();

                    if (element instanceof ObjectReferencesItem
                            && ((ObjectReferencesItem) element).canGoto()) {
                        // Ask the object references item to jump to the object.
                        ((ObjectReferencesItem) element).gotoItem();
                    }
                }
            }
        });

        return comp;
    }

    /**
     * Set the contents of the references tree view.
     * 
     * The content must be opf type {@link ObjectReferencesFolder} or
     * {@link ObjectReferencesItem}
     * 
     * @param contentItems
     */
    public void setContent(List contentItems) {

        if (contentItems != null) {
            for (Object obj : contentItems) {
                if (!(obj instanceof ObjectReferencesFolder)
                        && !(obj instanceof ObjectReferencesItem)) {
                    throw new IllegalArgumentException(
                            "Content must be ObjectReferenceFolder or ObjectReferenceItem"); //$NON-NLS-1$
                }
            }

            content = contentItems;

        } else {
            content = Collections.EMPTY_LIST;
        }

        if (referenceTree != null) {
            referenceTree.getTree().setRedraw(false);
            referenceTree.refresh();

            Object firstObject = findFirstItem(content);

            if (firstObject != null) {
                referenceTree
                        .setSelection(new StructuredSelection(firstObject));
                referenceTree.reveal(firstObject);
            } else {
                gotoButton.setEnabled(false);
            }

            referenceTree.getTree().setRedraw(true);
        }
    }

    /**
     * Recursively find the first non-folder item.
     * 
     * @param items
     * @return
     */
    private Object findFirstItem(List<Object> items) {
        Object first = null;

        for (Iterator iter = items.iterator(); first == null && iter.hasNext();) {
            Object obj = (Object) iter.next();

            if (obj instanceof ObjectReferencesFolder) {
                first = findFirstItem(((ObjectReferencesFolder) obj)
                        .getChildren());
            } else {
                first = obj;
            }
        }
        return first;
    }

}
