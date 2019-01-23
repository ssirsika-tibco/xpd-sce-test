/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.GlobalActionManager;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.components.actions.CollapseAllAction;
import com.tibco.xpd.resources.ui.components.actions.ExpandAllAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.editorHandler.IDisplayEObject;
import com.tibco.xpd.resources.ui.util.MarkerFinder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.rsd.ModelElement;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.components.RsdMainControl;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

/**
 * The editor part containing REST Service Descriptor editor.
 * 
 * @author jarciuch
 * @since 20 Feb 2015
 */
public class RsdEditorPart extends EditorPart implements
        PropertyChangeListener, ITabbedPropertySheetPageContributor,
        IMenuListener, IEditingDomainProvider, IGotoMarker, IDisplayEObject,
        IPersistableEditor {

    /**
     * Id of this editor (as defined in the extension).
     */
    public static final String EDITOR_ID =
            "com.tibco.xpd.rsd.ui.rsd.editor".toString(); //$NON-NLS-1$

    /**
     * Used as a key for storing expanded elements in the memento.
     */
    private static final String EXPANDED_TAG = "EXPANDED_TAG"; //$NON-NLS-1$

    /**
     * Used as a key for storing selected elements in the memento.
     */
    private static final String SELECTED_TAG = "SELECTED_TAG"; //$NON-NLS-1$

    /**
     * Delimiter used to separate list of element's ids stored in the saved
     * editor state.
     */
    private static final char DELIMITER = ',';

    private Service service;

    private WorkingCopy wc;

    private Map<String, IAction> actions;

    private RsdMainControl mainControl;

    private XpdFormToolkit toolkit;

    /**
     * Saved state of this editor.
     */
    private IMemento memento;

    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        setInput(input);
        setPartName(input.getName());
        if (input instanceof FileEditorInput) {
            FileEditorInput fei = (FileEditorInput) input;
            IFile file = fei.getFile();
            wc = WorkingCopyUtil.getWorkingCopy(file);
            if (wc != null) {
                wc.addListener(this);
                EObject root = wc.getRootElement();
                if (root instanceof Service) {
                    service = (Service) root;
                } else {
                    throw new PartInitException(
                            String.format("File content is not recognized.", //$NON-NLS-1$
                                    file.getName()));
                }
            } else {
                throw new PartInitException(
                        String.format("File type %s not supported.", //$NON-NLS-1$
                                file.getFileExtension()));
            }
        } else {
            throw new PartInitException(
                    String.format("Invalid input type used to open in REST Service Descriptor editor.")); //$NON-NLS-1$
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveState(IMemento memento) {
        if (mainControl != null && mainControl.getTreeViewer() != null) {
            TreeViewer viewer = mainControl.getTreeViewer();

            /* Save expansion state. */
            Object[] expandedElements = viewer.getVisibleExpandedElements();
            String expandedElemsStr =
                    getStringFromCollection(Arrays.asList(expandedElements),
                            DELIMITER);
            if (expandedElemsStr != null) {
                memento.putString(EXPANDED_TAG, expandedElemsStr);
            }

            /* Save selection state. */
            List<?> selectedElements =
                    ((IStructuredSelection) viewer.getSelection()).toList();
            String selectedElemsStr =
                    getStringFromCollection(selectedElements, DELIMITER);
            if (selectedElemsStr != null) {
                memento.putString(SELECTED_TAG, selectedElemsStr);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreState(IMemento memento) {
        /*
         * Saved into instance variable as the saved state is used at the end
         * #createParControl(...)
         * 
         * @see restoreEditorState(...)
         */
        this.memento = memento;
    }

    /**
     * Restores the expand and selection state of the viewer (after closing and
     * opening wokrbench).
     * 
     * @param memento
     * @param viewer
     */
    private void restoreMainViewerState(IMemento memento, TreeViewer viewer,
            WorkingCopy wc) {
        if (memento != null) {
            {
                String expanded = memento.getString(EXPANDED_TAG);
                if (expanded != null && !expanded.trim().isEmpty()) {
                    List<EObject> expandedEos =
                            getCollectionFromString(expanded, DELIMITER, wc);
                    if (!expandedEos.isEmpty()) {
                        viewer.setExpandedElements(expandedEos
                                .toArray(new Object[expandedEos.size()]));
                    }
                }
            }
            String selected = memento.getString(SELECTED_TAG);
            if (selected != null && !selected.trim().isEmpty()) {
                List<EObject> selectedEos =
                        getCollectionFromString(selected, DELIMITER, wc);
                if (!selectedEos.isEmpty()) {
                    viewer.setSelection(new StructuredSelection(selectedEos));
                }
            }

            memento = null; // memento is no longer needed and can be gc-ed.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout());
        parent.setBackground(parent.getDisplay()
                .getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        toolkit = new XpdWizardToolkit(parent);
        Form form = toolkit.createForm(parent);
        form.setText(Messages.RsdEditorPart_RsdMain_label);
        form.setImage(RsdImage.getImage(RsdImage.RSD_FILE));
        toolkit.getFormToolkit().decorateFormHeading(form);
        GridLayoutFactory.swtDefaults().applyTo(form.getBody());

        Section section =
                toolkit.createSection(form.getBody(), Section.DESCRIPTION
                        | ExpandableComposite.TITLE_BAR);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
        GridLayoutFactory.swtDefaults().applyTo(section);

        section.setText(Messages.RsdEditorPart_RsdResourcesSection_header);
        section.setDescription(Messages.RsdEditorPart_RsdResourceSection_desc);
        mainControl = new RsdMainControl(section, toolkit);
        section.setClient(mainControl);
        TreeViewer editorViewer = mainControl.getTreeViewer();
        getSite().setSelectionProvider(editorViewer);

        // Set the initial input.
        editorViewer.setInput(service.eResource());
        editorViewer.setSelection(new StructuredSelection(service), true);

        createContextMenuFor(editorViewer);
        createSectionToolbar(section, editorViewer);
        editorViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        setStatusLineManager(event.getSelection());
                    }
                });

        if (this.memento != null) {
            restoreMainViewerState(this.memento, editorViewer, wc);
        } else {
            /* Expand all by default (if not restoring state from memento.) */
            editorViewer.expandAll();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (wc != null) {
            wc.removeListener(this);
        }
        if (toolkit != null) {
            toolkit.dispose();
            toolkit = null;
        }
        super.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        try {
            if (wc != null) {
                /* wc.save() method runs save in the workspace operation. */
                wc.save();
            }
        } catch (IOException e) {
            throw new WrappedException(e);
        } finally {
            firePropertyChange(PROP_DIRTY);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSaveAs() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDirty() {
        return wc != null ? wc.isWorkingCopyDirty() : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFocus() {
        mainControl.setFocus();
    }

    /**
     * Listen (and propagates) working copy life cycle events.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        // If Working Copy changed or removed then close editor
        // else if Working Copy dirtied then fire dirty property change
        if (propName.equals(WorkingCopy.PROP_RELOADED)
                || propName.equals(WorkingCopy.PROP_REMOVED)) {
            closeEditor();
        } else if (propName.equals(WorkingCopy.PROP_DIRTY)) {
            firePropertyChange(PROP_DIRTY);
        }
    }

    /**
     * Closes the Editor.
     */
    private void closeEditor() {
        Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            @Override
            public void run() {
                // Try to close editor only if it is open , This check is added
                // to avoid cyclic call to closeEditor() , which is called
                // for WorkingCopy Reload, and When a dirty Editor is closed
                // without save the Working Copy is reloaded [simply to revert
                // the changes in Working Copy], in this scenario it should not
                // try to close the editor, as it will be already be closed by
                // then.
                if (getSite().getPage().findEditor(getEditorInput()) != null) {
                    getSite().getPage().closeEditor(RsdEditorPart.this, false);
                }
            }
        });
    }

    /**
     * Return action associated to the Editor with given ID.
     * 
     * @param id
     *            The action ID.
     * @return {@link IAction} associated with this id or <code>null</code> when
     *         not found.
     */

    public IAction getAction(String id) {
        if (actions == null) {
            createActions();
        }
        return actions.get(id);
    }

    /**
     * Creates maps of of actions (actionId: String -> IAction) for this editor.
     */
    protected void createActions() {
        if (getSite() != null) {
            actions = new HashMap<>();

            if (wc instanceof AbstractTransactionalWorkingCopy) {
                AbstractTransactionalWorkingCopy twc =
                        (AbstractTransactionalWorkingCopy) wc;
                IUndoContext undoContext = twc.getUndoContext();
                UndoActionHandler undo =
                        new UndoActionHandler(getSite(), undoContext);
                undo.setId(ActionFactory.UNDO.getId());
                actions.put(ActionFactory.UNDO.getId(), undo);

                RedoActionHandler redo =
                        new RedoActionHandler(getSite(), undoContext);
                redo.setId(ActionFactory.REDO.getId());
                actions.put(ActionFactory.REDO.getId(), redo);

                ViewerDeleteAction delete = mainControl.getDeleteAction();
                delete.setId(ActionFactory.DELETE.getId());
                actions.put(ActionFactory.DELETE.getId(), delete);

                // Handled by GMF action handlers.
                String[] actionIds =
                        new String[] { ActionFactory.CUT.getId(),
                                ActionFactory.COPY.getId(),
                                ActionFactory.PASTE.getId() };
                GlobalAction[] globalActions =
                        GlobalActionManager.getInstance()
                                .createGlobalActions(this, actionIds);
                for (GlobalAction globalAction : globalActions) {
                    actions.put(globalAction.getActionId(), globalAction);
                }
            }
        }
    }

    /**
     * This creates a context menu for the viewer and adds a listener as well
     * registering the menu for extension.
     */
    protected void createContextMenuFor(StructuredViewer viewer) {
        MenuManager contextMenu = new MenuManager("#PopUp"); //$NON-NLS-1$
        contextMenu.add(new Separator("additions")); //$NON-NLS-1$
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(this);
        Menu menu = contextMenu.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);

        /* Don't register this menu for extensions. */
        // getSite().registerContextMenu(contextMenu,
        // new UnwrappingSelectionProvider(viewer));

        /* Add drag and drop support for the viewer. */
        int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
        Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
        viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(
                viewer));
        viewer.addDropSupport(dndOperations,
                transfers,
                new EditingDomainViewerDropAdapter(getEditingDomain(), viewer));
    }

    /**
     * This implements {@link org.eclipse.jface.action.IMenuListener} to help
     * fill the context menus with contributions from the Edit menu.
     */
    @Override
    public void menuAboutToShow(IMenuManager menuManager) {
        menuManager.add(new Separator("add-actions")); //$NON-NLS-1$
        menuManager.add(mainControl.getAddResourceAction());
        menuManager.add(mainControl.getAddMethodAction());
        menuManager.add(new Separator("edit-actions-undo")); //$NON-NLS-1$
        menuManager.add(actions.get(ActionFactory.CUT.getId()));
        menuManager.add(actions.get(ActionFactory.COPY.getId()));
        menuManager.add(actions.get(ActionFactory.PASTE.getId()));
        menuManager.add(new Separator("edit-actions")); //$NON-NLS-1$
        menuManager.add(mainControl.getDeleteAction());
        menuManager.add(new Separator("misc-actions")); //$NON-NLS-1$
        menuManager.add(mainControl.getShowPropertiesViewAction());
    }

    /**
     * @return The working copy.
     */
    public WorkingCopy getWorkingCopy() {
        return wc;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
     */
    @Override
    public String getContributorId() {
        return "com.tibco.xpd.rsd.editor.propertyContributor"; //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     * 
     * This editor adapts to the TabbedPropertySheetPage (to support tabbed
     * properties).
     */
    @Override
    public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
        if (adapter == IPropertySheetPage.class) {
            TabbedPropertySheetPage p = new TabbedPropertySheetPage(this);
            return p;
        }
        return super.getAdapter(adapter);
    }

    /**
     * @return main control reference used by RSD editor.
     */
    public RsdMainControl getRsdMainControl() {
        return mainControl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EditingDomain getEditingDomain() {
        return wc.getEditingDomain();
    }

    /**
     * Adds expand/collapse all buttons to the section header.
     * 
     * @param section
     *            The section to add controls to.
     * @param viewer
     *            The viewer to expand/collapse.
     */
    private void createSectionToolbar(Section section, TreeViewer viewer) {
        ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
        ToolBar toolbar = toolBarManager.createControl(section);
        toolBarManager.add(new ExpandAllAction(viewer));
        toolBarManager.add(new CollapseAllAction(viewer));
        toolBarManager.update(true);
        section.setTextClient(toolbar);
    }

    /**
     * Returns action bar contributor for this editor.
     */
    private EditorActionBarContributor getActionBarContributor() {
        return (EditorActionBarContributor) getEditorSite()
                .getActionBarContributor();
    }

    /**
     * Gets action bars for this editor.
     */
    private IActionBars getActionBars() {
        return getActionBarContributor().getActionBars();
    }

    /**
     * Sets the status line.
     */
    public void setStatusLineManager(ISelection selection) {
        IStatusLineManager statusLineManager =
                getActionBars().getStatusLineManager();

        if (statusLineManager != null) {
            if (selection instanceof IStructuredSelection) {
                Collection<?> collection =
                        ((IStructuredSelection) selection).toList();
                switch (collection.size()) {
                case 0: {
                    statusLineManager
                            .setMessage(Messages.RsdEditorPart_StatusNothingSelected_message);
                    break;
                }
                case 1: {
                    ILabelProvider labeProvider =
                            (ILabelProvider) mainControl.getViewer()
                                    .getLabelProvider();
                    Object element = collection.iterator().next();
                    String text = labeProvider.getText(element);
                    Image image = labeProvider.getImage(element);
                    statusLineManager
                            .setMessage(image,
                                    String.format(Messages.RsdEditorPart_StatusOneObjectSelected_message,
                                            text));
                    break;
                }
                default: {
                    int numberOfObjects = collection.size();
                    statusLineManager
                            .setMessage(Messages.RsdEditorPart_StatusManyObjectsSelected_message
                                    + numberOfObjects);
                    break;
                }
                }
            } else {
                statusLineManager.setMessage(""); //$NON-NLS-1$
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gotoMarker(IMarker marker) {
        EObject[] objects = MarkerFinder.getObject(marker);
        if (objects != null && objects.length > 0) {
            getRsdMainControl().getViewer()
                    .setSelection(new StructuredSelection(objects), true);
        }
    }

    /**
     * @see com.tibco.xpd.xpdl2.edit.util.IGotoEObject#gotoEObject(boolean,
     *      org.eclipse.emf.ecore.EObject[])
     * 
     * @param selectObjects
     * @param eObjects
     * @return
     */
    @Override
    public boolean gotoEObject(boolean selectObjects, EObject... eObjects) {
        if (selectObjects && mainControl != null) {
            ISelection selection = new StructuredSelection(eObjects);
            mainControl.getTreeViewer().setSelection(selection);
        }
        return true;
    }

    /**
     * Returns string representation for the list of elements (string containing
     * IDs separated by the delimiter) or <code>null</code> if list doesn't
     * contain any {@link ModelElement}s.
     * 
     * @param elements
     *            the collection of elements.
     * @param delim
     *            delimiter to separate elements in the list.
     * @return string representation for the list of elements (string containing
     *         IDs separated by the delimiter) or <code>null</code> if list
     *         doesn't contain any {@link ModelElement}s.
     * 
     * @see {@link ModelElement#getId()}
     */
    private String getStringFromCollection(Iterable<?> elements, char delim) {
        boolean empty = true;
        StringBuilder sb = new StringBuilder();
        for (Object o : elements) {
            if (o instanceof ModelElement) {
                if (empty) {
                    empty = false;
                } else {
                    sb.append(DELIMITER);
                }
                sb.append(((ModelElement) o).getId());
            }
        }
        if (!empty) {
            return sb.toString();
        }
        return null;
    }

    /**
     * Gets a list of resolved EObject from the string of delimited ids.
     */
    private List<EObject> getCollectionFromString(String s, char delim,
            WorkingCopy wc) {
        if (s == null || s.trim().isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<EObject> elems = new ArrayList<>();
        for (String id : s.split(Character.toString(delim))) {
            if (!id.trim().isEmpty()) {
                EObject eo = resolve(wc, id);
                if (eo != null) {
                    elems.add(eo);
                }
            }
        }
        return elems;
    }

    /**
     * Resolves element id against the working copy.
     * 
     * @param wc
     *            working copy.
     * @param id
     *            identifier of the element.
     * @return resolved EObject or <code>null</code>
     */
    private EObject resolve(WorkingCopy wc, String id) {
        IResource r = wc.getEclipseResources().get(0);
        if (r instanceof IFile) {
            URI uri =
                    URI.createPlatformResourceURI(r.getFullPath()
                            .toPortableString(),
                            true).appendFragment(id);
            return wc.getEditingDomain().getResourceSet().getEObject(uri, true);
        }
        return null;
    }
}
