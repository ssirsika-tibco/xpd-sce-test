/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.GridData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;

import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.operations.FragmentContext;
import com.tibco.xpd.fragments.internal.pages.FragmentsViewPage;
import com.tibco.xpd.fragments.internal.pages.MessageViewPage;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Fragments view part.
 * 
 * @author njpatel
 * 
 */
public class FragmentsViewPart extends ViewPart {

    private PageBook book;
    // Page displayed in the view when no fragment provider is found for the
    // active editor, or fragments are still being initialized
    private MessageViewPage messagePage;
    // Page displayed in the view when fragment provider(s) is found for the
    // active editor
    private FragmentsViewPage fragmentsPage;

    private final FragmentViewSelectionProviderDelegate selectionProviderDelegate;

    private final FragmentPartListener listener;
    // Current active editor
    private IEditorPart activeEditor;
    private final FragmentsManager manager;

    // Undo/Redo action handlers
    private UndoActionHandler undoActionHandler;
    private RedoActionHandler redoActionHandler;

    private boolean initComplete = false;

    /**
     * Fragments view part.
     */
    public FragmentsViewPart() {
        selectionProviderDelegate = new FragmentViewSelectionProviderDelegate();
        listener = new FragmentPartListener();
        manager = FragmentsManager.getInstance();
        // Register this view part with the manager so other's can access it
        manager.registerFragmentViewPart(this);
    }

    @Override
    public void createPartControl(Composite parent) {
        book = new PageBook(parent, SWT.NONE);
        book.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        messagePage = new MessageViewPage();
        messagePage
                .setMessage(Messages.FragmentsViewPart_initFragments_shortdesc);
        messagePage.createControl(book);
        addSelectionProvider(messagePage);

        fragmentsPage = new FragmentsViewPage(this);
        fragmentsPage.createControl(book);

        getSite().setSelectionProvider(selectionProviderDelegate);

        // Register the listener
        getSite().getPage().addPartListener(listener);

        // Display the default page
        book.showPage(messagePage.getControl());

        // Listen to manager initialization and update pages once complete
        manager
                .addInitializationListener(new FragmentsManager.FragmentsInitializationListener() {

                    public void initializationComplete() {
                        initComplete = true;

                        if (getSite() != null && getSite().getShell() != null) {
                            getSite().getShell().getDisplay().syncExec(
                                    new Runnable() {
                                        public void run() {
                                            updatePage(activeEditor);

                                            if (messagePage != null) {
                                                // Reset the message page
                                                // message
                                                messagePage.setMessage(null);
                                            }
                                        }
                                    });
                        }
                    }

                });
        // Update the view with the correct fragment contribution
        listener.partBroughtToTop(this);
    }

    /**
     * Get the fragments <code>TreeViewer</code>.
     * 
     * @return tree viewer, or <code>null</code> if the viewer is not created or
     *         disposed.
     */
    public TreeViewer getTreeViewer() {
        TreeViewer viewer = null;

        if (fragmentsPage != null) {
            viewer = fragmentsPage.getTreeViewer();
        }

        return viewer;
    }

    /**
     * Refresh the give fragment element in the view.
     * 
     * @param element
     *            fragment element to refresh or <code>null</code> to refresh
     *            entire view.
     */
    public void refresh(final IFragmentElement element) {
        if (fragmentsPage != null) {
            if (getSite() != null && getSite().getShell() != null) {
                getSite().getShell().getDisplay().syncExec(new Runnable() {
                    public void run() {
                        fragmentsPage.refresh(element);
                    }
                });
            }
        }
    }

    @Override
    public void setFocus() {
        // Nothing to do
    }

    @Override
    public void dispose() {
        messagePage.dispose();
        fragmentsPage.dispose();
        book.dispose();

        getSite().getPage().removePartListener(listener);

        if (undoActionHandler != null) {
            undoActionHandler.dispose();
            undoActionHandler = null;
        }

        if (redoActionHandler != null) {
            redoActionHandler.dispose();
            redoActionHandler = null;
        }

        super.dispose();
    }

    /**
     * Add a selection provider to this view. This will passed to the selection
     * provider delegate that manages all selection providers in this view.
     * 
     * @param provider
     *            selection provider to add
     */
    public void addSelectionProvider(ISelectionProvider provider) {
        if (provider != null) {
            selectionProviderDelegate.addSelectionProvider(provider);
        }
    }

    /**
     * Remove selection provider from this view.
     * 
     * @see #addSelectionProvider(ISelectionProvider)
     * @param provider
     *            selection provider to remove
     */
    public void removeSelectionProvider(ISelectionProvider provider) {
        if (provider != null) {
            selectionProviderDelegate.removeSelectionProvider(provider);
        }
    }

    /**
     * Update the page in the view.
     * 
     * @param editorPart
     *            current active editor. If present then the provider binding
     *            will be checked and if found will display the fragments page,
     *            otherwise it will show the message page(also the case when
     *            this value is <code>null</code>).
     */
    private void updatePage(IEditorPart editorPart) {
        // If fragments are still initializing then ignore
        if (initComplete) {
            if (book != null && !book.isDisposed()) {
                Page pageToDisplay = messagePage;
                // Refresh the message page
                messagePage.refresh();
                // Check if the active editor has fragment providers, if not
                // then show message page, otherwise show fragment page
                if (editorPart != null) {
                    FragmentsProvider[] providers = manager
                            .getExtensionHelper().getBoundProviders(editorPart);

                    if (providers != null && providers.length > 0) {
                        pageToDisplay = fragmentsPage;
                        fragmentsPage.setProviders(providers);
                        refresh(null);

                        // Register the undo/redo action for the providers
                        List<String> providerIds = new ArrayList<String>();
                        for (FragmentsProvider provider : providers) {
                            providerIds.add(provider.getId());
                        }
                        FragmentContext undoContext = new FragmentContext(
                                providerIds.toArray(new String[providerIds
                                        .size()]));

                        if (undoActionHandler == null) {
                            // Set up the undo/redo action handlers
                            if (getViewSite() != null) {
                                undoActionHandler = new UndoActionHandler(
                                        getSite(), undoContext);
                                undoActionHandler.setPruneHistory(true);
                                redoActionHandler = new RedoActionHandler(
                                        getSite(), undoContext);
                                redoActionHandler.setPruneHistory(true);

                                getViewSite().getActionBars()
                                        .setGlobalActionHandler(
                                                ActionFactory.UNDO.getId(),
                                                undoActionHandler);
                                getViewSite().getActionBars()
                                        .setGlobalActionHandler(
                                                ActionFactory.REDO.getId(),
                                                redoActionHandler);
                            }
                        } else {
                            // Update the context
                            undoActionHandler.setContext(undoContext);
                            redoActionHandler.setContext(undoContext);
                        }
                    }
                }
                book.showPage(pageToDisplay.getControl());
            }
        }
    }

    /**
     * Selection provider delegate that will consolidate selection changes from
     * the tree viewer and the fragment thumbnails view.
     * 
     * @author njpatel
     * 
     */
    private class FragmentViewSelectionProviderDelegate implements
            ISelectionProvider, ISelectionChangedListener {

        private final Set<ISelectionChangedListener> selectionListeners;
        private ISelection selection;

        /**
         * Selection provider delegate.
         */
        public FragmentViewSelectionProviderDelegate() {
            selectionListeners = new HashSet<ISelectionChangedListener>();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
         * (org.eclipse.jface.viewers.ISelectionChangedListener)
         */
        public void addSelectionChangedListener(
                ISelectionChangedListener listener) {
            if (listener != null) {
                selectionListeners.add(listener);
            }
        }

        /**
         * Add selection provider to this delegate.
         * 
         * @param provider
         */
        public void addSelectionProvider(ISelectionProvider provider) {
            if (provider != null) {
                provider.addSelectionChangedListener(this);
            }
        }

        /**
         * Remove selection provider from this delegate.
         * 
         * @param provider
         */
        public void removeSelectionProvider(ISelectionProvider provider) {
            if (provider != null) {
                provider.removeSelectionChangedListener(this);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
         */
        public ISelection getSelection() {
            return selection;
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.jface.viewers.ISelectionProvider#
         * removeSelectionChangedListener
         * (org.eclipse.jface.viewers.ISelectionChangedListener)
         */
        public void removeSelectionChangedListener(
                ISelectionChangedListener listener) {
            if (listener != null) {
                selectionListeners.remove(listener);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse
         * .jface.viewers.ISelection)
         */
        public void setSelection(ISelection selection) {
            this.selection = selection;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged
         * (org.eclipse.jface.viewers.SelectionChangedEvent)
         */
        public void selectionChanged(SelectionChangedEvent event) {
            setSelection(event.getSelection());

            // Update all listeners with the selection change
            for (ISelectionChangedListener listener : selectionListeners) {
                listener.selectionChanged(event);
            }
        }

    }

    /**
     * Part listener that will update the fragments view when an editor has
     * changed.
     * 
     * @author njpatel
     * 
     */
    private class FragmentPartListener implements IPartListener {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart
         * )
         */
        public void partActivated(IWorkbenchPart part) {
        }

        /**
         * Update the view if the editor has changed
         * 
         * @param editorPart
         */
        private void updateView(IEditorPart editorPart) {
            // Only update if the editor type has changed
            if ((activeEditor == null && editorPart != null)
                    || (activeEditor != null && !activeEditor
                            .equals(editorPart))) {
                activeEditor = editorPart;
                updatePage(activeEditor);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.
         * IWorkbenchPart)
         */
        public void partBroughtToTop(IWorkbenchPart part) {
            if (part instanceof IEditorPart || part == FragmentsViewPart.this) {
                IEditorPart editorPart = null;

                if (part instanceof IEditorPart) {
                    editorPart = ((IEditorPart) part);
                } else {
                    editorPart = FragmentsUtil.getActiveEditor();
                }

                updateView(editorPart);

                if (part == FragmentsViewPart.this) {
                    /*
                     * If the fragments view part is selected then re-select the
                     * selection. This will force the context menu and actions
                     * to re-evaluate. This is required because a user could
                     * copy an item from the editor and then right-click the
                     * already selected custom category in the fragments view -
                     * by right-clicking this will not cause the actions to
                     * re-evaluate as the selection hadn't changed.
                     */
                    TreeViewer viewer = getTreeViewer();
                    if (viewer != null && !viewer.getControl().isDisposed()) {
                        viewer.setSelection(viewer.getSelection());
                    }
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart
         * )
         */
        public void partClosed(IWorkbenchPart part) {
            // Check if there is an active editor, if not then set current
            // editor id to null
            updateView(FragmentsUtil.getActiveEditor());
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.
         * IWorkbenchPart)
         */
        public void partDeactivated(IWorkbenchPart part) {
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart
         * )
         */
        public void partOpened(IWorkbenchPart part) {
        }
    }

}
