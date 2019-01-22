/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.internal.part.NullEditorInput;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.processeditor.xpdl2.highlighting.ReferenceHighlighterEditPartListener;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessDialogUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.Xpdl2ProcessWidgetAdapterFactory;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.processwidget.viewer.NavigationListener;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;

/**
 * Process diagram editor that contains ProcessWidgetImpl
 * 
 * @author wzurek
 */
public abstract class AbstractProcessDiagramEditor extends EditorPart implements
        ITabbedPropertySheetPageContributor, NavigationListener,
        INotifyChangedListener, DisposeListener, PropertyChangeListener,
        IGotoMarker, INavigationLocationProvider, IGotoEObject, ISaveablePart2 {

    /**
     * Project explorer view ID.
     */
    private static final String PROJECT_EXPLORER =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /**
     * Process editor property contributor ID.
     */
    public static final String PROCESSEDITOR_PROPERTY_CONTRIBUTOR_ID =
            "com.tibco.xpd.processeditor.xpdl2.propertyContributor"; //$NON-NLS-1$

    /**
     * Process widget instance.
     */
    private ProcessWidgetImpl widget;

    /**
     * Flag to indicate whether we want to ignore preference changes or not.
     */
    protected boolean ignorePrefChanges = false;

    /**
     * Reference highlighter edit part listener instance.
     */
    private ReferenceHighlighterEditPartListener highlightReferringEditPartsListener;

    /**
     * Preference store property change listener instance.
     */
    private IPropertyChangeListener preferenceStorePropertyChangeListener;

    /**
     * Graphical viewer property change listener instance.
     */
    private PropertyChangeListener graphicalViewerPropertyChangeListener;

    /*
     * @see org.eclipse.ui.IEditorPart#init(org.eclipse.ui.IEditorSite,
     * org.eclipse.ui.IEditorInput)
     */
    @SuppressWarnings("restriction")
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {

        setSite(site);

        ProcessEditorInput processInput = null;

        /*
         * XPD-4315: If a workspace is closed with a process editor open and
         * that workspace is subsequently opened in a later version of Studio
         * then the ProcessEditorPersist class that creates the editor input for
         * a persisted editor will create a NullEditorInput if it fails to load
         * the working copy of that model. This will typically happen when that
         * model needs migrating. It is better to check for this eventuality
         * here and just closing the editor after logging the error then calling
         * the adapt method (below) as this will throw an
         * IllegalArgumentException.
         */
        if (!(input instanceof NullEditorInput)) {
            processInput = adaptToProcessEditorInput(site, input);
        }

        if (!(processInput instanceof ProcessEditorInput)) {
            closeEditor();
            throw new PartInitException(
                    Messages.ProcessDiagramEditor_ProcessNoLongerExists_message);
        }

        setInput(processInput);

        /*
         * XPD-1140: Show correct title to same as editor title (display name
         * (tokenname) ( + [Read-Only] as appropriate)
         */
        WorkingCopy workingCopy = processInput.getWorkingCopy();

        String title = processInput.getName();
        if (workingCopy != null && workingCopy.isReadOnly()) {
            title += " " + Messages.PackageEditor_ReadOnly_label; //$NON-NLS-1$ 
        }
        setPartName(title);

        setTitleToolTip(processInput.getToolTipText());

        ItemProviderAdapter ip =
                (ItemProviderAdapter) workingCopy.getAdapterFactory()
                        .adapt(processInput.getProcess(),
                                processInput.getProcess().eClass()
                                        .getEPackage());
        ip.addListener(this);

        workingCopy.addListener(this);

    }

    /**
     * Delegate getAdapter to the Widget
     */
    @Override
    public Object getAdapter(Class adapter) {
        Object ad = widget.getAdapter(adapter);
        if (ad != null) {
            return ad;
        }

        else if (adapter == IPropertySheetPage.class) {
            TabbedPropertySheetPage p = new TabbedPropertySheetPage(this);
            return p;
        }

        else if (adapter == IGotoMarker.class) {
            return this;
        }

        else if (adapter == Process.class) {
            IEditorInput input = getEditorInput();
            if (input instanceof ProcessEditorInput) {
                return ((ProcessEditorInput) input).getProcess();
            }

        }
        return super.getAdapter(adapter);
    }

    /**
     * Delegate save to WorkingCopy
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        // Ask user whether they want to save all processes in this package
        boolean canSave =
                ProcessDialogUtil.canSavePackage(getEditorSite().getShell());

        if (canSave) {
            savePackage((ProcessEditorInput) getEditorInput());
        }
    }

    /**
     * Save package.
     * 
     * @param input
     * 
     * @throws WrappedException
     */
    private void savePackage(ProcessEditorInput input) throws WrappedException {
        try {
            input.getWorkingCopy().save();
        } catch (IOException e) {
            throw new WrappedException(e);
        } finally {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
    }

    @Override
    public void doSaveAs() {
        // save as is illegal for process
    }

    /**
     * Always false, navigator is responsible for managing files to save.
     */
    @Override
    public boolean isDirty() {
        ProcessEditorInput input = (ProcessEditorInput) getEditorInput();
        return input.getWorkingCopy().isWorkingCopyDirty();
        // return false;
    }

    /**
     * save as is illegal for process
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * Create process widget here
     */
    @Override
    public void createPartControl(final Composite parent) {
        if (this.getEditorInput() == null) {
            return;
        }
        /*
         * Changed this to use the workbench progress monitor in the status bar
         * so that it doesn't popup the progress dialog. Now the open process
         * editors are persisted when the Studio is shut down. On subsequent
         * startup the progress dialog was being displayed while the Studio
         * splash screen was showing which didn't look nice.
         */
        // IProgressService progressService = PlatformUI.getWorkbench()
        // .getProgressService();
        // try {
        // progressService.runInUI(PlatformUI.getWorkbench()
        // .getProgressService(), new IRunnableWithProgress() {
        // public void run(IProgressMonitor monitor) {
        // doCreatePartControl(parent, monitor);
        // }
        // }, null);
        // } catch (InvocationTargetException e) {
        // e.printStackTrace();
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        try {
            getEditorSite().getWorkbenchWindow().run(false,
                    false,
                    new IRunnableWithProgress() {

                        @Override
                        public void run(IProgressMonitor monitor)
                                throws InvocationTargetException,
                                InterruptedException {
                            doCreatePartControl(parent, monitor);
                        }

                    });
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create part control.
     * 
     * @param parent
     * @param monitor
     */
    protected void doCreatePartControl(final Composite parent,
            IProgressMonitor monitor) {

        monitor.beginTask(Messages.ProcessDiagramEditor_ProgressMonitor_message,
                5);

        ProcessEditorInput input = (ProcessEditorInput) getEditorInput();

        monitor.worked(1);// ----------------------------------------------

        widget = new ProcessWidgetImpl(true, getProcessWidgetType());

        /** XPD-1140: Allow tag process editor figure as readonly. */
        WorkingCopy workingCopy =
                ((ProcessEditorInput) getEditorInput()).getWorkingCopy();
        widget.setReadOnly(workingCopy.isReadOnly());

        monitor.worked(1);// ----------------------------------------------

        widget.setEditingDomain(input.getWorkingCopy().getEditingDomain());

        widget.addNavigationListener(this);

        // factory for XPDL specyfic adapters
        widget.setAdapterFactory(new Xpdl2ProcessWidgetAdapterFactory());
        widget.setPreferences(Xpdl2ProcessEditorPlugin.getDefault()
                .getPluginPreferences());
        widget.setSite(getSite());

        /*
         * XPD-2516: Tool palette may be configurable and hence may need the
         * context of the input process.
         */
        widget.setInput(input.getProcess());

        monitor.worked(1);// ----------------------------------------------

        widget.createControl(parent);

        monitor.worked(1);// ----------------------------------------------

        widget.getControl().addDisposeListener(this);

        getEditorSite().setSelectionProvider(widget);

        Boolean val;
        IPreferenceStore store =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        val = new Boolean(store.getBoolean(SnapToGrid.PROPERTY_GRID_ENABLED));
        widget.getGraphicalViewer()
                .setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, val);
        val = new Boolean(store.getBoolean(SnapToGrid.PROPERTY_GRID_VISIBLE));
        widget.getGraphicalViewer()
                .setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, val);

        // Default snap to geometry (alignment guide) to ON.
        if (!store.contains(SnapToGeometry.PROPERTY_SNAP_ENABLED)) {
            val = new Boolean(true);
        } else {
            val =
                    new Boolean(
                            store.getBoolean(SnapToGeometry.PROPERTY_SNAP_ENABLED));
        }
        widget.getGraphicalViewer()
                .setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED, val);

        preferenceStorePropertyChangeListener = new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (ignorePrefChanges) {
                    // return;
                }
                try {
                    ignorePrefChanges = true;
                    if (SnapToGrid.PROPERTY_GRID_ENABLED.equals(event
                            .getProperty())) {
                        Boolean val = new Boolean((String) event.getNewValue());
                        widget.getGraphicalViewer()
                                .setProperty(SnapToGrid.PROPERTY_GRID_ENABLED,
                                        val);
                        widget.getGraphicalViewer()
                                .setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
                                        val);
                    } else if (SnapToGeometry.PROPERTY_SNAP_ENABLED
                            .equals(event.getProperty())) {
                        Boolean val = new Boolean((String) event.getNewValue());
                        widget.getGraphicalViewer()
                                .setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
                                        val);
                    }
                } finally {
                    ignorePrefChanges = false;
                }
            }
        };
        Xpdl2ProcessEditorPlugin
                .getDefault()
                .getPreferenceStore()
                .addPropertyChangeListener(preferenceStorePropertyChangeListener);

        graphicalViewerPropertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (ignorePrefChanges) {
                    // return;
                }
                try {
                    ignorePrefChanges = true;
                    IPreferenceStore store =
                            Xpdl2ProcessEditorPlugin.getDefault()
                                    .getPreferenceStore();
                    if (SnapToGrid.PROPERTY_GRID_ENABLED.equals(evt
                            .getPropertyName())) {
                        Boolean val = (Boolean) evt.getNewValue();
                        store.setValue(SnapToGrid.PROPERTY_GRID_ENABLED,
                                String.valueOf(val));
                        store.setValue(SnapToGrid.PROPERTY_GRID_VISIBLE,
                                String.valueOf(val));
                    } else if (SnapToGeometry.PROPERTY_SNAP_ENABLED.equals(evt
                            .getPropertyName())) {
                        Boolean val = (Boolean) evt.getNewValue();
                        store.setValue(SnapToGeometry.PROPERTY_SNAP_ENABLED,
                                String.valueOf(val));
                    }
                } finally {
                    ignorePrefChanges = false;
                }
            }
        };
        widget.getGraphicalViewer()
                .addPropertyChangeListener(graphicalViewerPropertyChangeListener);

        /*
         * XPD-1431: Add our highlight selected object listener to seleciton
         * service.
         */
        ISelectionService selService =
                (ISelectionService) this.getSite()
                        .getService(ISelectionService.class);
        if (selService != null) {
            highlightReferringEditPartsListener =
                    new ReferenceHighlighterEditPartListener(this);
            selService
                    .addPostSelectionListener(highlightReferringEditPartsListener);
        }

        monitor.worked(1);// ----------------------------------------------
        monitor.done();
    }

    /**
     * Set focus to the widget
     */
    @Override
    public void setFocus() {
        widget.getControl().setFocus();
    }

    /**
     * ID of tabbed properties view contributor
     */
    @Override
    public String getContributorId() {
        //
        // Eclipse tabbed property sheet strategy does not cope very well with
        // using tabs contributed by plug-ins that are depended by a plug-=in
        // that we depend on (or is loaded before us anyway).
        // So now all editors should make their own contributions.
        return PROCESSEDITOR_PROPERTY_CONTRIBUTOR_ID;
    }

    @Override
    public boolean revealObject(Object object) {
        if (object instanceof Process) {
            ProcessEditorInput input = (ProcessEditorInput) getEditorInput();

            if (object == input.getProcess()) {
                getSite().getPage().activate(this);
                return true;
            }

            ProcessEditorInputFactory fact = new ProcessEditorInputFactory();
            IEditorInput ei = fact.getEditorInputFor(object);
            try {
                getSite().getPage().openEditor(ei, getEditorId());
                return true;
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void notifyChanged(Notification notification) {
        /*
         * XPD-1140: Show correct title to same as editor title (display name
         * (tokenname) ( + [Read-Only] as appropriate)
         */
        WorkingCopy workingCopy =
                ((ProcessEditorInput) getEditorInput()).getWorkingCopy();

        String title = getEditorInput().getName();
        if (workingCopy != null && workingCopy.isReadOnly()) {
            title += " " + Messages.PackageEditor_ReadOnly_label; //$NON-NLS-1$ 
        }
        setPartName(title);

    }

    @Override
    public void widgetDisposed(DisposeEvent e) {
        ProcessEditorInput processIntput =
                (ProcessEditorInput) getEditorInput();
        ItemProviderAdapter ip =
                (ItemProviderAdapter) processIntput
                        .getWorkingCopy()
                        .getAdapterFactory()
                        .adapt(processIntput.getProcess(),
                                processIntput.getProcess().eClass()
                                        .getEPackage());
        ip.removeListener(this);
    }

    // The solution here is to set isSaveOnCloseNeeded to true when the project
    // explorer is not dirty and the editor is. Jus after Studio starts up, and
    // before the link-with-editor is turned on, this happens.
    @Override
    public boolean isSaveOnCloseNeeded() {
        if (isDirty()
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage() != null) {
            IViewReference[] refs =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getViewReferences();
            CommonNavigator navigator = null;
            for (IViewReference ref : refs) {
                if (ref.getId().equals(PROJECT_EXPLORER)) {
                    if (ref.getPart(false) instanceof CommonNavigator) {
                        navigator = (CommonNavigator) ref.getPart(false);
                        if (!(navigator.isDirty())) {
                            return true;
                        }
                    }
                }
            }
            if (navigator == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Close the process diagram editor.
     */
    private void closeEditor() {
        Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            @Override
            public void run() {
                getSite().getPage()
                        .closeEditor(AbstractProcessDiagramEditor.this, false);
            }
        });
    }

    /**
     * Navigates to and selects the diagram object specified by the marker.
     */
    @Override
    public void gotoMarker(IMarker marker) {
        EObject[] objects = MarkerLocator.getObject(marker);
        gotoEObject(true, objects);
    }

    @Override
    public INavigationLocation createEmptyNavigationLocation() {
        return new ProcessDiagramNavigationLocation(this);
    }

    @Override
    public INavigationLocation createNavigationLocation() {
        return new ProcessDiagramNavigationLocation(this);
    }

    public ProcessWidget getProcessWidget() {
        return widget;
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        // If Working Copy changed or removed then close editor
        // else if Working Copy dirtied then fire dirty property change
        if (propName.equals(WorkingCopy.PROP_RELOADED)
                || propName.equals(WorkingCopy.PROP_REMOVED)) {
            closeEditor();
        } else if (propName.equals(WorkingCopy.PROP_DIRTY)) {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        } else if (propName.equals(WorkingCopy.CHANGED)) {
            /*
             * XPD-2516 Listen for commands executed on the working copy to tell
             * process widget of possible changes that may effect its
             * configuration etc
             */
            if (widget != null) {
                widget.notifyInputContentModified();
            }

            /*
             * XPD-1431: Update our highlight if model changes.
             */
            IWorkbenchPage page =
                    this.getSite().getWorkbenchWindow().getActivePage();
            if (page != null) {
                IWorkbenchPart activePart = page.getActivePart();
                if (activePart != null
                        && highlightReferringEditPartsListener != null) {
                    highlightReferringEditPartsListener.redoHighlight();
                }
            }
        }
    }

    @Override
    public boolean gotoEObject(boolean selectObject, EObject... s) {

        List<Object> newSelection = new ArrayList<Object>();
        for (EObject eo : s) {
            Object editPart = getFindEditPartFor(eo);
            if (editPart != null) {
                newSelection.add(editPart);
            }
        }

        if (!newSelection.isEmpty()) {
            if (selectObject) {
                // ONLY set selection if it is different. Otherwise it can
                // cause teh Link with editor helper to repeat itself.
                boolean selectionChanged = true;

                ISelection widgetSel = widget.getSelection();
                if (widgetSel instanceof IStructuredSelection) {
                    IStructuredSelection curSelection =
                            (IStructuredSelection) widgetSel;

                    if (curSelection.size() == newSelection.size()) {
                        boolean allSame = true;
                        for (Iterator iterator = curSelection.iterator(); iterator
                                .hasNext();) {
                            Object csel = iterator.next();

                            if (!newSelection.contains(csel)) {
                                allSame = false;
                            }
                        }
                        if (allSame) {
                            selectionChanged = false;
                        }
                    }
                }

                if (selectionChanged) {
                    widget.setSelection(new StructuredSelection(newSelection));
                }
            }

            widget.navigateTo(newSelection.get(0));
            return true;
        }
        return false;
    }

    /**
     * Reveal and optionally select, highlight and smoothscroll to the object.
     * 
     * @param objects
     * @param doSelect
     * @param doReveal
     * @param doExtraHighlight
     * @param smoothScroll
     * @return Reveal are of the element(s) or null if cannot ascertain this
     *         (multiple element select or not expected graphical viewer). This
     *         is in display co-ords.
     */
    public Rectangle navigateToObjects(List<EObject> objects, boolean doSelect,
            boolean doReveal, boolean doExtraHighlight, boolean smoothScroll) {
        Rectangle revealRect = null;

        List<EditPart> selection = new ArrayList<EditPart>();
        for (EObject eo : objects) {
            Object editPart = getFindEditPartFor(eo);
            if (editPart instanceof EditPart) {
                selection.add((EditPart) editPart);
            }
        }
        if (!selection.isEmpty()) {
            if (doSelect) {
                widget.setSelection(new StructuredSelection(selection));
            }

            if (doReveal) {
                GraphicalViewer graphViewer = widget.getGraphicalViewer();
                if (graphViewer instanceof BpmnScrollingGraphicalViewer) {
                    revealRect =
                            ((BpmnScrollingGraphicalViewer) graphViewer)
                                    .reveal(selection.get(0),
                                            doExtraHighlight,
                                            smoothScroll);
                } else {
                    graphViewer.reveal(selection.get(0));
                }

            }
        }

        return revealRect;
    }

    /**
     * Get the Edit part for the specified EObject.
     * 
     * @param eo
     *            EObject whose edit part is to be found out.
     * 
     * @return The Edit part for the specified EObject.
     */
    @SuppressWarnings("unchecked")
    private Object getFindEditPartFor(EObject eo) {
        Map registry = widget.getGraphicalViewer().getEditPartRegistry();
        Object editPart = registry.get(eo);
        while (eo != null && editPart == null) {
            eo = eo.eContainer();
            if (eo != null) {
                editPart = registry.get(eo);
            }
        }
        return editPart;
    }

    /**
     * @return The editor id.
     */
    protected abstract String getEditorId();

    /**
     * So that process diagram editor can be opened from different sources (not
     * necessarily directly a Process EObject) this method is called during
     * editor init() to allow the nominal editor input to be converted into a
     * ProcssEditorInput.
     * 
     * @param editorInput
     * @return
     */
    protected abstract ProcessEditorInput adaptToProcessEditorInput(
            IEditorSite editorSite, IEditorInput editorInput);

    /**
     * @return The type of process widget required.
     */
    protected abstract ProcessWidgetType getProcessWidgetType();

    @Override
    public int promptToSaveOnClose() {
        /*
         * Don't prompt the user to save on editor close when running in RCP
         * application
         */
        if (XpdResourcesPlugin.isRCP()) {
            return NO;
        }

        return DEFAULT;
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (getEditorInput() != null) {
            WorkingCopy workingCopy =
                    ((ProcessEditorInput) getEditorInput()).getWorkingCopy();
            if (workingCopy != null) {
                workingCopy.removeListener(this);
            }
        }

        if (preferenceStorePropertyChangeListener != null) {
            Xpdl2ProcessEditorPlugin
                    .getDefault()
                    .getPreferenceStore()
                    .removePropertyChangeListener(preferenceStorePropertyChangeListener);
        }

        if (graphicalViewerPropertyChangeListener != null && widget != null
                && widget.getGraphicalViewer() != null) {
            widget.getGraphicalViewer()
                    .removePropertyChangeListener(graphicalViewerPropertyChangeListener);
        }

        if (highlightReferringEditPartsListener != null) {
            ISelectionService selService =
                    (ISelectionService) this.getSite()
                            .getService(ISelectionService.class);
            if (selService != null) {
                selService
                        .removePostSelectionListener(highlightReferringEditPartsListener);
            }

            highlightReferringEditPartsListener.dispose();
            highlightReferringEditPartsListener = null;
        }
        super.dispose();
        return;
    }
}
