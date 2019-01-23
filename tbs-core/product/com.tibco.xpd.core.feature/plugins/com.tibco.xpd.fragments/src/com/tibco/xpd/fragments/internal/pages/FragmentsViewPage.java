/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.dnd.TreeDropTargetEffect;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.Page;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.dnd.FragmentDropTargetAdapter;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.dnd.FragmentDragSourceAdapter;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;
import com.tibco.xpd.fragments.internal.operations.CopyOperation;
import com.tibco.xpd.fragments.internal.operations.FragmentElementOperation;
import com.tibco.xpd.fragments.internal.operations.MoveOperation;
import com.tibco.xpd.fragments.internal.operations.RenameOperation;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;
import com.tibco.xpd.fragments.internal.view.FragmentActiveEditorViewerFilter;

/**
 * Fragments view page that will be displayed in the fragments tree/preview when
 * a provider(s) is found for the current active editor.
 * 
 * @author njpatel
 * 
 */
public class FragmentsViewPage extends Page {

    private final FragmentsViewPart viewPart;

    private final FragmentsBlock block;

    private final FragmentsManager manager = FragmentsManager.getInstance();

    private MenuManager menuManager;

    private Composite root;

    private FragmentsProvider[] providers;

    /**
     * Fragments thumbnail page.
     * 
     * @param viewPart
     */
    public FragmentsViewPage(FragmentsViewPart viewPart) {
        this.viewPart = viewPart;

        block = new FragmentsBlock();

    }

    /**
     * Set the current providers contributing to this view page.
     * 
     * @param providers
     *            fragment providers.
     */
    public void setProviders(FragmentsProvider[] providers) {
        if (block != null) {
            if (this.providers != null) {
                // Clear the current providers
                block.unsetDropTarget(providers);
            }
            this.providers = providers;
            if (providers != null) {
                block.setDropTarget(providers);
            }
        }
    }

    /**
     * Get the tree viewer from the fragments view.
     * 
     * @return <code>TreeViewer</code> or <code>null</code> if tree hasn't been
     *         created or has been disposed.
     */
    public TreeViewer getTreeViewer() {
        TreeViewer viewer = null;

        if (block != null && block.treeViewer != null
                && !block.treeViewer.getTree().isDisposed()) {
            viewer = block.treeViewer;
        }

        return viewer;
    }

    @Override
    public void createControl(Composite parent) {
        root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        ManagedForm fragmentsForm = new ManagedForm(root);
        fragmentsForm.getForm().setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true));

        block.createContent(fragmentsForm);

        menuManager = createMenuManager();
        Menu menu = menuManager.createContextMenu(getTreeViewer().getControl());
        getTreeViewer().getControl().setMenu(menu);

        viewPart.getSite().registerContextMenu(menuManager, getTreeViewer());

        viewPart.addSelectionProvider(getTreeViewer());
    }

    /**
     * Refresh the given fragment element in the view.
     * 
     * @param element
     *            fragment element to refresh or <code>null</code> to refresh
     *            entire view.
     */
    public void refresh(IFragmentElement element) {
        if (block != null) {
            block.refresh(element);
        }
    }

    @Override
    public Control getControl() {
        return root;
    }

    @Override
    public void setFocus() {
    }

    @Override
    public void dispose() {
        if (menuManager != null) {
            menuManager.dispose();
        }

        // Unregister the listener
        viewPart.removeSelectionProvider(getTreeViewer());
        if (getSite() != null) {
            getSite().setSelectionProvider(null);
        }
        block.dispose();

        super.dispose();
    }

    /**
     * Create the context menu manager.
     * 
     * @return
     */
    public static MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager();
        menuManager.add(new Separator(IWorkbenchActionConstants.M_FILE));
        menuManager.add(new Separator(IWorkbenchActionConstants.M_EDIT));
        menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        return menuManager;
    }

    /**
     * Master details block for the fragments view.
     * 
     * @author njpatel
     * 
     */
    private class FragmentsBlock extends MasterDetailsBlock {

        private TreeViewer treeViewer;
        private List<IDetailsPage> pages = new ArrayList<IDetailsPage>();
        // Object being edited currently; null if no editing taking place
        private Object objectBeingEdited;
        private DragSource dragSource;
        private DropTarget dropTarget;

        @Override
        public void createContent(IManagedForm managedForm) {
            super.createContent(managedForm);
            sashForm.setWeights(new int[] { 1, 2 });
        }

        /**
         * Refresh the given element in the view.
         * 
         * @param element
         *            , or <code>null</code> to refresh the entire view.
         */
        public void refresh(IFragmentElement element) {
            if (treeViewer != null && !treeViewer.getTree().isDisposed()) {
                if (element != null) {
                    treeViewer.refresh(element);
                } else {
                    // Refresh the entire tree and expand it
                    treeViewer.refresh();
                    treeViewer.expandToLevel(2);
                }
            }

            // Refresh all pages
            for (IDetailsPage page : pages) {
                page.refresh();
            }
        }

        /**
         * Dispose off all resources.
         */
        public void dispose() {
            if (treeViewer != null) {
                treeViewer.getControl().dispose();
                treeViewer = null;
            }

            for (IDetailsPage page : pages) {
                page.dispose();
            }
            pages.clear();

            if (dragSource != null) {
                dragSource.dispose();
                dragSource = null;
            }

            if (dropTarget != null) {
                dropTarget.dispose();
                dropTarget = null;
            }
        }

        @Override
        protected void createMasterPart(final IManagedForm managedForm,
                Composite parent) {
            FormToolkit toolkit = managedForm.getToolkit();
            Section section = toolkit.createSection(parent, Section.NO_TITLE);
            // section.setText("Fragments");
            section.marginWidth = 10;
            section.marginHeight = 5;
            // toolkit.createCompositeSeparator(section);

            treeViewer = new TreeViewer(section, SWT.H_SCROLL | SWT.V_SCROLL
                    | SWT.BORDER);
            // treeViewer = new TreeViewer(section);
            treeViewer.getTree().setLayoutData(
                    new GridData(SWT.FILL, SWT.FILL, true, true));

            treeViewer.setLabelProvider(new DecoratingLabelProvider(manager
                    .getFragmentsLabelProvider(), manager
                    .getFragmentsLabelDecorator()));

            treeViewer
                    .setContentProvider(manager.getFragmentsContentProvider());

            treeViewer.setComparator(manager.getFragmentsViewerComparator());

            treeViewer
                    .setFilters(new ViewerFilter[] { new FragmentActiveEditorViewerFilter() });

            setEditingSupport(treeViewer);

            toolkit.adapt(treeViewer.getControl(), true, true);

            try {
                treeViewer.setInput(manager.getFragmentsContainer());
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            section.setClient(treeViewer.getControl());
            final SectionPart spart = new SectionPart(section);
            managedForm.addPart(spart);

            treeViewer
                    .addSelectionChangedListener(new ISelectionChangedListener() {

                        public void selectionChanged(SelectionChangedEvent event) {
                            managedForm.fireSelectionChanged(spart, event
                                    .getSelection());
                        }

                    });

            setDnDSupport(treeViewer);

        }

        /**
         * Update the drop target with the <code>FragmentProvider</code>s.
         * 
         * @param providers
         *            current fragment providers displayed in the page.
         */
        protected void setDropTarget(FragmentsProvider[] providers) {
            if (dropTarget != null) {
                Set<Transfer> transfers = new HashSet<Transfer>();
                // Add default transfer type
                transfers.add(FragmentLocalSelectionTransfer.getTransfer());

                if (providers != null) {
                    // If the provider has a drop target registered then add to
                    // the drop target
                    for (FragmentsProvider provider : providers) {
                        try {
                            FragmentDropTargetAdapter[] adapters = provider
                                    .getDropAdapters();
                            for (FragmentDropTargetAdapter adapter : adapters) {
                                Transfer[] types = adapter.getTransferTypes();

                                if (types != null) {
                                    transfers.addAll(Arrays.asList(types));
                                }
                                dropTarget.addDropListener(adapter);
                            }
                        } catch (CoreException e) {
                            FragmentsActivator
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            "Setting drop target adapters in FragmentsViewPage");
                        }
                    }
                }
                dropTarget.setTransfer(transfers.toArray(new Transfer[transfers
                        .size()]));
            }
        }

        /**
         * Unregister the drop target adapters registered in the providers, if
         * any.
         * 
         * @param providers
         *            fragment providers (drop target adapters) to unregister.
         */
        protected void unsetDropTarget(FragmentsProvider[] providers) {
            if (dropTarget != null) {
                dropTarget
                        .setTransfer(new Transfer[] { FragmentLocalSelectionTransfer
                                .getTransfer() });
                if (providers != null) {
                    // Remove all registered drop target adapters in this
                    // providers from the drop target
                    for (FragmentsProvider provider : providers) {
                        try {
                            FragmentDropTargetAdapter[] adapters = provider
                                    .getDropAdapters();
                            for (FragmentDropTargetAdapter adapter : adapters) {
                                dropTarget.removeDropListener(adapter);
                            }
                        } catch (CoreException e) {
                            FragmentsActivator
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            "Unsetting drop target adapters in FragmentsViewPage");
                        }
                    }
                }
            }
        }

        /**
         * Set up the drag-drop support on the tree viewer.
         * 
         * @param viewer
         *            tree viewer.
         */
        private void setDnDSupport(final TreeViewer viewer) {
            int operations = DND.DROP_COPY | DND.DROP_MOVE;
            dragSource = new DragSource(viewer.getControl(), operations);

            dragSource
                    .setTransfer(new Transfer[] { FragmentLocalSelectionTransfer
                            .getTransfer() });
            dragSource.addDragListener(new FragmentDragSourceAdapter(viewer
                    .getTree(), viewer));

            dropTarget = new DropTarget(viewer.getControl(), operations);
            dropTarget
                    .setTransfer(new Transfer[] { FragmentLocalSelectionTransfer
                            .getTransfer() });
            dropTarget
                    .addDropListener(new FragmentLocalSelectionDropTargetAdapter(
                            viewer));
        }

        /**
         * Set editing support for the tree viewer.
         * 
         * @param viewer
         *            tree viewer.
         */
        private void setEditingSupport(final TreeViewer viewer) {
            viewer.setCellEditors(new CellEditor[] { new TextCellEditor(viewer
                    .getTree()) });
            viewer.setColumnProperties(new String[] { "name" }); //$NON-NLS-1$
            viewer.setCellModifier(new ICellModifier() {

                public boolean canModify(Object element, String property) {
                    return true;
                }

                public Object getValue(Object element, String property) {
                    String value = ""; //$NON-NLS-1$
                    if (element instanceof IFragmentElement) {
                        value = ((IFragmentElement) element).getName();
                    }
                    return value;
                }

                public void modify(Object element, String property, Object value) {
                    if (element instanceof Item) {
                        element = ((Item) element).getData();
                    }

                    if (element instanceof FragmentElementImpl
                            && value instanceof String) {
                        String strVal = ((String) value).trim();

                        // Rename if there is a name and it has changed
                        if (strVal.length() > 0
                                && !(strVal
                                        .equals(((FragmentElementImpl) element)
                                                .getName()))) {
                            RenameOperation op = new RenameOperation(
                                    (FragmentElementImpl) element, strVal);
                            try {
                                FragmentsUtil.execute(op, null);
                            } catch (ExecutionException e) {
                                FragmentsActivator.getDefault().getLogger()
                                        .error(e);
                                ErrorDialog
                                        .openError(
                                                getSite().getShell(),
                                                Messages.FragmentsViewPage_rename_errorDialog_title,
                                                Messages.FragmentsViewPage_rename_errorDialog_message,
                                                new Status(
                                                        IStatus.ERROR,
                                                        FragmentsActivator.PLUGIN_ID,
                                                        e.getLocalizedMessage(),
                                                        e));
                            }
                        }
                    }
                }

            });
            TreeViewerEditor.create(viewer,
                    new ColumnViewerEditorActivationStrategy(viewer) {

                        @Override
                        protected boolean isEditorActivationEvent(
                                ColumnViewerEditorActivationEvent event) {
                            return event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
                        }

                    }, ColumnViewerEditor.DEFAULT);

            viewer.getTree().addListener(SWT.MouseDoubleClick, new Listener() {

                public void handleEvent(Event event) {
                    handleEdit(viewer);
                }

            });
            viewer.getTree().addKeyListener(new KeyAdapter() {

                public void keyReleased(KeyEvent e) {
                    if (e.keyCode == SWT.F2) {
                        handleEdit(viewer);
                        e.doit = false;
                    }
                }

            });

            viewer.getTree().addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    // If an object is being edited then cancel the editing as
                    // user selecting something else
                    if (objectBeingEdited != null) {
                        objectBeingEdited = null;
                        viewer.cancelEditing();
                    }
                }
            });
        }

        /**
         * Handle the edit of the selected item in the tree.
         * 
         * @param viewer
         */
        private void handleEdit(TreeViewer viewer) {
            TreeItem[] selection = viewer.getTree().getSelection();

            if (selection.length == 1) {
                Object data = selection[0].getData();
                if (data instanceof IFragmentElement
                        && !((IFragmentElement) data).isSystem()) {
                    objectBeingEdited = data;
                    viewer.editElement(data, 0);
                }
            }
        }

        @Override
        protected void createToolBarActions(IManagedForm managedForm) {

        }

        @Override
        protected void registerPages(DetailsPart detailsPart) {
            detailsPart.setPageProvider(new IDetailsPageProvider() {

                public IDetailsPage getPage(Object key) {
                    IDetailsPage page = null;

                    if (key == IFragmentCategory.class
                            || key == IFragment.class) {
                        page = new FragmentElementPage(viewPart);
                        pages.add(page);
                    }

                    return page;
                }

                public Object getPageKey(Object object) {
                    Object key = null;

                    if (object instanceof IFragmentCategory) {
                        key = IFragmentCategory.class;
                    } else if (object instanceof IFragment) {
                        key = IFragment.class;
                    }

                    return key;
                }

            });
        }
    }

    /**
     * Fragment tree viewer drop target listener.
     * 
     * @author njpatel
     * 
     */
    private class FragmentLocalSelectionDropTargetAdapter extends
            TreeDropTargetEffect {

        private final FragmentLocalSelectionTransfer fragmentTransfer;
        private int currentDetail = DND.DROP_MOVE;

        public FragmentLocalSelectionDropTargetAdapter(TreeViewer treeViewer) {
            super(treeViewer.getTree());

            fragmentTransfer = FragmentLocalSelectionTransfer.getTransfer();
        }

        @Override
        public void dragEnter(DropTargetEvent event) {
            super.dragEnter(event);
            currentDetail = DND.DROP_MOVE;

            for (TransferData type : event.dataTypes) {
                if (fragmentTransfer.isSupportedType(type)) {
                    event.currentDataType = type;
                    break;
                }
            }
        }

        @Override
        public void dragOperationChanged(DropTargetEvent event) {
            currentDetail = event.detail;
        }

        @Override
        public void dragOver(DropTargetEvent event) {
            super.dragOver(event);

            if (fragmentTransfer.isSupportedType(event.currentDataType)) {
                IStructuredSelection selection = (IStructuredSelection) fragmentTransfer
                        .getSelection();

                if (selection != null && !selection.isEmpty()) {
                    Object element = selection.getFirstElement();

                    event.detail = currentDetail;

                    if (element instanceof IFragmentElement) {
                        IFragmentElement dragItem = (IFragmentElement) element;
                        Widget item = event.item;

                        if (item != null) {
                            Object data = item.getData();

                            // Cannot drag a system object, or an object onto
                            // itself
                            if (dragItem.equals(data) || dragItem.isSystem()) {
                                event.detail = DND.DROP_NONE;
                            } else {
                                if (data instanceof IFragmentCategory) {
                                    IFragmentCategory target = (IFragmentCategory) data;
                                    // Only allowed to DND within
                                    // fragment/category from the same provider
                                    // and also user category
                                    if (target.isSystem()
                                            || ((FragmentElementImpl) target)
                                                    .getProvider() != ((FragmentElementImpl) dragItem)
                                                    .getProvider()) {
                                        event.detail = DND.DROP_NONE;
                                    } else {
                                        if (event.detail == DND.DROP_MOVE) {
                                            // Cannot move a category into one
                                            // of it's children or
                                            // grand-children
                                            if (dragItem instanceof IFragmentCategory
                                                    && isAncestor(
                                                            (FragmentElementImpl) dragItem,
                                                            (FragmentElementImpl) target)) {

                                                if ((event.operations & DND.DROP_COPY) != 0) {
                                                    event.detail = DND.DROP_COPY;
                                                } else {
                                                    event.detail = DND.DROP_NONE;
                                                }
                                            } else {
                                                IFragmentCategory parent = null;

                                                if (dragItem instanceof IContainedFragmentElement) {
                                                    parent = ((IContainedFragmentElement) dragItem)
                                                            .getParent();
                                                }
                                                /*
                                                 * If the drag item is a system
                                                 * element or the drag item's
                                                 * parent is the target then
                                                 * this should be copy
                                                 */
                                                if (target.equals(parent)
                                                        || dragItem.isSystem()) {
                                                    if ((event.operations & DND.DROP_COPY) != 0) {
                                                        event.detail = DND.DROP_COPY;
                                                    } else {
                                                        event.detail = DND.DROP_NONE;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    // Can't drop into a fragment
                                    event.detail = DND.DROP_NONE;
                                }
                            }
                        } else {
                            // No target
                            event.detail = DND.DROP_NONE;
                        }
                    }
                } else {
                    event.detail = DND.DROP_NONE;
                }
            }
        }

        /**
         * Check if the given ancestor element is an ancestor of element.
         * 
         * @param ancestor
         * @param element
         * @return
         */
        private boolean isAncestor(FragmentElementImpl ancestor,
                FragmentElementImpl element) {
            boolean isAncestor = false;

            if (ancestor != null && element != null) {
                IResource ancestorResource = ancestor.getResource();
                IResource elementResource = element.getResource();

                isAncestor = ancestorResource.getFullPath().isPrefixOf(
                        elementResource.getFullPath());
            }

            return isAncestor;
        }

        @Override
        public void drop(DropTargetEvent event) {
            if (fragmentTransfer.isSupportedType(event.currentDataType)) {
                Widget item = event.item;

                if (item != null
                        && item.getData() instanceof FragmentCategoryImpl) {
                    FragmentCategoryImpl target = (FragmentCategoryImpl) item
                            .getData();
                    FragmentElementOperation operation = null;

                    // Move the selection
                    if (event.data instanceof IStructuredSelection) {
                        IStructuredSelection selection = (IStructuredSelection) event.data;

                        Object element = selection.getFirstElement();

                        if (element instanceof FragmentElementImpl) {
                            // Can only DND into object from same provider
                            if (((FragmentElementImpl) element).getProvider() == target
                                    .getProvider()) {
                                if (event.detail == DND.DROP_MOVE) {
                                    // Not allowed to move object into it's
                                    // child
                                    if (!isAncestor(
                                            (FragmentElementImpl) element,
                                            target)) {
                                        // Move fragment element
                                        operation = new MoveOperation(target,
                                                (FragmentElementImpl) element);
                                    }
                                } else if (event.detail == DND.DROP_COPY) {

                                    // Copy fragment element
                                    operation = new CopyOperation(
                                            target,
                                            (FragmentElementImpl) element,
                                            FragmentsUtil
                                                    .getCopyOfName(
                                                            target,
                                                            (FragmentElementImpl) element));
                                }
                            }
                        }
                    }

                    if (operation != null) {
                        try {
                            FragmentsUtil.execute(operation, null);
                        } catch (ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
