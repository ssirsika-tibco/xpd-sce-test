/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.globalSignalDefinition.workingcopy.GsdWorkingCopy;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Form editor to maneuver with global signals and their respective payload
 * data.
 * 
 * @author sajain
 * @since Jan 28, 2015
 */
public class GsdFormEditor extends FormEditor implements
        ITabbedPropertySheetPageContributor, INotifyChangedListener,
        DisposeListener, PropertyChangeListener, IGotoMarker, ISaveablePart2,
        ISaveablesSource {

    private ActionRegistry actions;

    private GsdWorkingCopy wc;

    /**
     * GSD contributor ID.
     */
    public static final String GSDEDITOR_CONTRIBUTOR_ID =
            GsdResourcePlugin.PLUGIN_ID + ".propertyContributor"; //$NON-NLS-1$

    /**
     * Instance of Global Signal Definition Selection provider.
     */
    private ISelectionProvider selectionProvider = new GsdSelectionProvider(
            this);

    /**
     * Form page.
     */
    private GsdFormPage formPage;

    private ItemProviderAdapter itemProviderAdapter;

    /**
     * @see org.eclipse.ui.ISaveablePart2#promptToSaveOnClose()
     * 
     * @return
     */
    @Override
    public int promptToSaveOnClose() {

        return DEFAULT;
    }

    /**
     * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     */
    @Override
    public void gotoMarker(IMarker marker) {

        /*
         * XPD-7228: Saket: Need to use a marker locator specific to GSD here.
         */
        EObject[] objects = GsdMarkerLocator.getObject(marker);
        gotoEObject(true, objects);
    }

    public boolean gotoEObject(boolean selectObject, EObject[] objects) {

        return true;
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * 
     * @param arg0
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propName = evt.getPropertyName();

        /*
         * If Working Copy changed or removed then close editor else if Working
         * Copy dirtied then fire dirty property change
         */

        if (propName.equals(WorkingCopy.PROP_RELOADED)
                || propName.equals(WorkingCopy.PROP_REMOVED)) {

            closeEditor();
        } else if (propName.equals(WorkingCopy.PROP_DIRTY)) {

            firePropertyChange(IEditorPart.PROP_DIRTY);
        }

    }

    /**
     * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
     * 
     * @param e
     */
    @Override
    public void widgetDisposed(DisposeEvent e) {
        removeModelListeners();
    }

    /**
     * Remove the model listeners.
     */
    private void removeModelListeners() {
        /*
         * Sid (We already have working copy as class field ) - so don't get it
         * again (remove wc=getWorkingCOpy()
         * 
         * This is especially important because we may be being called during
         * closeEditor() because of a file rename in which case we won't get the
         * working copy for the OLD file name and hence we won't remove any of
         * the listeners. Which would in turn mean that after a close and
         * rename, our refresh() would still get called if other model changes
         * are made.
         */

        /*
         * Check if root element is correct and then only proceed.
         * 
         * Have to used preserved working copy and itemProviderAdapter because
         * if we are closing because of a file rename then wc.getRootElement()
         * will return null.
         */
        if (wc != null && itemProviderAdapter != null) {
            /*
             * Remove listeners.
             */
            itemProviderAdapter.removeListener(this);

            wc.removeListener(this);
        }
    }

    /**
     * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
     * 
     * @param notification
     */
    @Override
    public void notifyChanged(Notification notification) {

        /*
         * If we are called outside of the display thread then we must perform
         * the refresh on the display thread later - else we will cause invalid
         * thread access exception and popup an error box
         */
        if (Thread.currentThread() != Display.getDefault().getThread()) {
            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {

                    IFile file =
                            ((IFileEditorInput) getEditorInput()).getFile();

                    WorkingCopy workingCopy =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(file);

                    String title = getEditorInput().getName();

                    if (workingCopy != null && workingCopy.isReadOnly()) {
                        title += " " + Messages.GSDFormEditor_ReadOnly_label; //$NON-NLS-1$
                    }

                    setPartName(title);
                    formPage.getForm().setText(title);

                }
            });

        } else {
            /* We're on the display thread so we can do it now. */
            refresh();
        }

        return;

    }

    /**
     * Refresh on notify change.
     */
    private void refresh() {

        IFile file = ((IFileEditorInput) getEditorInput()).getFile();

        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(file);

        String title = getEditorInput().getName();

        if (workingCopy != null && workingCopy.isReadOnly()) {
            title += " " + Messages.GSDFormEditor_ReadOnly_label; //$NON-NLS-1$
        }

        setPartName(title);
        formPage.getForm().setText(title);
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        /*
         * Eclipse tabbed property sheet strategy does not cope very well with
         * using tabs contributed by plug-ins that are depended by a plug-=in
         * that we depend on (or is loaded before us anyway). So now all editors
         * should make their own contributions.
         */

        return GSDEDITOR_CONTRIBUTOR_ID;
    }

    /**
     * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
     * 
     */
    @Override
    protected void addPages() {

        try {

            formPage = new GsdFormPage(this, "GlobalSignalDefinition.Editor", //$NON-NLS-1$
                    getPartName());

            addPage(formPage);

        } catch (PartInitException e) {

            e.printStackTrace();
        }
    }

    /**
     * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     */
    @Override
    public void doSave(IProgressMonitor monitor) {

        FileEditorInput input = (FileEditorInput) getEditorInput();

        IPreferenceStore prefStore =
                GsdResourcePlugin.getDefault().getPreferenceStore();

        if (IDialogConstants.OK_ID == prefStore
                .getInt(ProcessEditorConstants.PREF_SAVE_EDITOR)) {

            saveGSD(input);
        }

    }

    /**
     * Save the global signal definition.
     * 
     * @param input
     * @throws WrappedException
     */
    private void saveGSD(FileEditorInput input) throws WrappedException {

        try {

            IFile file = ((IFileEditorInput) input).getFile();

            WorkingCopy workingCopyObj =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(file);

            workingCopyObj.save();

        } catch (IOException e) {

            throw new WrappedException(e);

        } finally {

            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
    }

    /**
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     * 
     */
    @Override
    public void doSaveAs() {

        // Do nothing

    }

    /**
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     * 
     * @return
     */
    @Override
    public boolean isSaveAsAllowed() {

        return false;
    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {

        super.init(site, input);

        site.setSelectionProvider(selectionProvider);

        /* Set up working copy field during init (not createActions()) */

        if (input instanceof IFileEditorInput) {

            IFile file = ((IFileEditorInput) input).getFile();

            WorkingCopy workingCopyObj =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(file);

            if (workingCopyObj instanceof GsdWorkingCopy) {

                wc = (GsdWorkingCopy) workingCopyObj;
            }

        }

        String title = getEditorInput().getName();

        if (wc != null) {

            /* Force load else isReadOnly returns false even when it is! */

            wc.getRootElement();
            if (wc.isReadOnly()) {

                title = title + " " + Messages.GSDFormEditor_ReadOnly_label; //$NON-NLS-1$ 
            }
        }

        setPartName(title);

        /*
         * Mark GSD file and GSD editor as dirty when anything is changed.
         */
        if (wc != null && wc.getRootElement() != null) {

            AdapterFactory adapterFactory = wc.getAdapterFactory();

            if (adapterFactory != null) {
                /*
                 * Sid - preserve th item provider adapter so that we can remove
                 * our listener on dispose even if file is renamed.
                 */
                itemProviderAdapter =
                        (ItemProviderAdapter) adapterFactory.adapt(wc
                                .getRootElement(), wc.getRootElement().eClass()
                                .getEPackage());

                itemProviderAdapter.addListener(this);
            }

            wc.addListener(this);
        }
    }

    /**
     * Close the GSD editor.
     */
    private void closeEditor() {

        Display d = PlatformUI.getWorkbench().getDisplay();

        d.syncExec(new Runnable() {

            @Override
            public void run() {
                getSite().getPage().closeEditor(GsdFormEditor.this, false);

            }
        });
    }

    @Override
    public boolean isDirty() {

        /*
         * As we have a class field for working copy then we should just use
         * that.
         */

        if (wc != null) {

            return wc.isWorkingCopyDirty();
        }

        return false;
    }

    @Override
    public Object getAdapter(Class adapter) {

        if (adapter == IPropertySheetPage.class) {

            TabbedPropertySheetPage p = new TabbedPropertySheetPage(this);

            return p;
        }
        return super.getAdapter(adapter);
    }

    @Override
    public void setFocus() {
        formPage.setFocus();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
     */
    @Override
    public void dispose() {
        /*
         * Must remove listeners when we are disposed otherwise we'll be kept
         * for ever by being in listener list (and our notifyChanged() will keep
         * being called.
         */
        removeModelListeners();
    }

    /**
     * Return action associated to the Editor with given ID, returns null when
     * not found.
     * 
     * @param id
     * @return {@link IAction} associated with this id.
     */

    public IAction getAction(String id) {
        if (actions == null) {
            createActions();
        }
        return actions.getAction(id);
    }

    /**
     * Create set of actions for WorkListFacade Editor
     */
    protected void createActions() {
        if (getSite() != null && wc != null) {
            actions = new ActionRegistry();

            /*
             * Get gsd file.
             */
            IFile file = ((IFileEditorInput) getEditorInput()).getFile();

            /*
             * Sid (We already have working copy as class field ) - so don't get
             * it again (remove wc=getWorkingCOpy()
             */

            IUndoContext undoContext = wc.getUndoContext();
            UndoActionHandler undo =
                    new UndoActionHandler(getSite(), undoContext);
            undo.setId(ActionFactory.UNDO.getId());
            actions.registerAction(undo);

            RedoActionHandler redo =
                    new RedoActionHandler(getSite(), undoContext);
            redo.setId(ActionFactory.REDO.getId());
            actions.registerAction(redo);

        }

        //
        // PLEASE NOTE: Just registering an action here is NOT enough to get a
        // standard action handler (for standard keystrokes etc) to work).
        // You must also go to the AbstractProcessDiagramEditorContributor and
        // ensure that the action is set as a global action handler.
        //
    }

    /**
     * @see org.eclipse.ui.ISaveablesSource#getSaveables()
     * 
     * @return
     */
    @Override
    public Saveable[] getSaveables() {
        return getActiveSaveables();
    }

    /**
     * @see org.eclipse.ui.ISaveablesSource#getActiveSaveables()
     * 
     * @return
     */
    @Override
    public Saveable[] getActiveSaveables() {
        if (wc != null) {
            if (wc.getSaveable() != null) {
                return new Saveable[] { wc.getSaveable() };
            }
        }
        return new Saveable[0];
    }
}
