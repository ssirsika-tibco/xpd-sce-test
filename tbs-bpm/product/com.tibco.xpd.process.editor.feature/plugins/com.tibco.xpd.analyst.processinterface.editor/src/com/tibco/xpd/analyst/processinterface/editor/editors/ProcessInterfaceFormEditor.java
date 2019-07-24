/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.analyst.processinterface.editor.ProcessInterfaceEditorConstants;
import com.tibco.xpd.analyst.processinterface.editor.ProcessInterfaceEditorPlugin;
import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInput;
import com.tibco.xpd.analyst.processinterface.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.MarkerLocator;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.IRefreshableTitle;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Editor for {@link ProcessInterface}, contains controls to add and remove
 * {@link StartEvent}, {@link IntermediateEvent}, and {@link FormalParameter}
 * along with modification controls for name and description. Works out of the
 * same model and uses a specialized {@link ProcessInterfaceEditorInput}
 * 
 * @author rsomayaj
 * 
 * 
 */
public class ProcessInterfaceFormEditor extends FormEditor implements
        ITabbedPropertySheetPageContributor, INotifyChangedListener,
        DisposeListener, PropertyChangeListener, IGotoMarker, ISaveablePart2, IRefreshableTitle {

    public static final String INTERFACEEDITOR_CONTRIBUTOR_ID =
            ProcessInterfaceEditorPlugin.PLUGIN_ID + ".propertyContributor"; //$NON-NLS-1$

    private ISelectionProvider selectionProvider =
            new ProcessInterfaceSelectionProvider(this);

    private ProcessInterfaceFormPage formPage;

    /**
     * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
     * 
     */
    @Override
    protected void addPages() {
        try {
            /*
             * XPD-1140: Show correct title to same as editor title (display
             * name (tokenname) ( + [Read-Only] as appropriate)
             */
            formPage =
                    new ProcessInterfaceFormPage(this,
                            "ProcessInterface.Editor", //$NON-NLS-1$
                            getPartName());
            addPage(formPage);
        } catch (PartInitException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doSaveAs() {
        // Do nothing
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        super.init(site, input);
        site.setSelectionProvider(selectionProvider);
        if (!(input instanceof ProcessInterfaceEditorInput)) {
            closeEditor();
            throw new PartInitException(
                    Messages.ProcessInterfaceFormEditor_ErrIncorrectEditorinput_shortdesc);
        }

        ProcessInterfaceEditorInput processInterfaceInput =
                (ProcessInterfaceEditorInput) input;

        /*
         * XPD-1140: Show correct title to same as editor title (display name
         * (tokenname) ( + [Read-Only] as appropriate)
         */
        WorkingCopy workingCopy = processInterfaceInput.getWorkingCopy();

        updateTitle();
        setTitleToolTip(input.getToolTipText());

        ItemProviderAdapter ip =
                (ItemProviderAdapter) workingCopy.getAdapterFactory()
                        .adapt(processInterfaceInput.getProcessInterface(),
                                processInterfaceInput.getProcessInterface()
                                        .eClass().getEPackage());
        ip.addListener(this);
        workingCopy.addListener(this);

        if (processInterfaceInput.getProcessInterface() != null
                && Xpdl2ModelUtil
                        .isServiceProcessInterface(processInterfaceInput
                                .getProcessInterface())) {
            setTitleImage(ProcessInterfaceEditorPlugin
                    .getDefault()
                    .getImageRegistry()
                    .get(ProcessInterfaceEditorConstants.IMG_SERVICE_PROCESS_INTERFACE));
        }
    }

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
                    WorkingCopy workingCopy =
                            ((ProcessInterfaceEditorInput) getEditorInput()).getWorkingCopy();

                    String title = getEditorInput().getName();
                    if (workingCopy != null && workingCopy.isReadOnly()) {
                        title +=
                                " "     + Messages.ProcessInterfaceFormEditor_ReadOnly_label; //$NON-NLS-1$
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
        WorkingCopy workingCopy =
                ((ProcessInterfaceEditorInput) getEditorInput())
                        .getWorkingCopy();

        String title = getEditorInput().getName();
        if (workingCopy != null && workingCopy.isReadOnly()) {
            title += " " + Messages.ProcessInterfaceFormEditor_ReadOnly_label; //$NON-NLS-1$
        }
        setPartName(title);
        formPage.getForm().setText(title);
    }

    @Override
    public void widgetDisposed(DisposeEvent e) {
        ProcessInterfaceEditorInput processInterfaceInput =
                (ProcessInterfaceEditorInput) getEditorInput();
        ItemProviderAdapter ip =
                (ItemProviderAdapter) processInterfaceInput
                        .getWorkingCopy()
                        .getAdapterFactory()
                        .adapt(processInterfaceInput.getProcessInterface(),
                                processInterfaceInput.getProcessInterface()
                                        .eClass().getEPackage());
        ip.removeListener(this);

        processInterfaceInput.getWorkingCopy().removeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        // If Working Copy changed or removed then close editor
        // else if Working Copy dirtied then fire dirty property change
        if (propName.equals(WorkingCopy.PROP_RELOADED)
                || propName.equals(WorkingCopy.PROP_REMOVED)) {
            closeEditor();
        } else if (propName.equals(WorkingCopy.PROP_DIRTY)) {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
    }

    @Override
    public String getContributorId() {
        //
        // Eclipse tabbed property sheet strategy does not cope very well with
        // using tabs contributed by plug-ins that are depended by a plug-=in
        // that we depend on (or is loaded before us anyway).
        // So now all editors should make their own contributions.
        return INTERFACEEDITOR_CONTRIBUTOR_ID;
    }

    private void closeEditor() {
        Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            @Override
            public void run() {
                getSite().getPage()
                        .closeEditor(ProcessInterfaceFormEditor.this, false);
            }
        });
    }

    @Override
    public boolean isDirty() {
        ProcessInterfaceEditorInput input =
                (ProcessInterfaceEditorInput) getEditorInput();
        return input.getWorkingCopy().isWorkingCopyDirty();
        // return false;
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

    /**
     * Navigates to and selects the diagram object specified by the marker.
     */
    @Override
    public void gotoMarker(IMarker marker) {
        EObject[] objects = MarkerLocator.getObject(marker);
        gotoEObject(true, objects);
    }

    // Ravi - redefine gotoEObject
    public boolean gotoEObject(boolean selectObject, EObject[] objects) {
        return true;
    }

    /**
     * Delegate save to WorkingCopy
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        final String toggleMsg =
                Messages.ProcessInterfaceFormEditor_PrefDontAsk_shortdesc;
        ProcessInterfaceEditorInput input =
                (ProcessInterfaceEditorInput) getEditorInput();

        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        boolean prefAskSave =
                prefStore
                        .getBoolean(ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE);
        if (!prefAskSave) {
            MessageDialogWithToggle saveAllProcsDialog =
                    MessageDialogWithToggle
                            .openOkCancelConfirm(Display.getCurrent()
                                    .getActiveShell(),
                                    Messages.ProcessInterfaceFormEditor_SavePackage_label,
                                    Messages.ProcessInterfaceFormEditor_PrefSaveAllElements_longdesc,
                                    toggleMsg,
                                    prefAskSave,
                                    prefStore,
                                    ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE);

            prefAskSave = saveAllProcsDialog.getToggleState();
            prefStore
                    .setValue(ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE,
                            prefAskSave);

            prefStore.setValue(ProcessEditorConstants.PREF_SAVE_EDITOR,
                    saveAllProcsDialog.getReturnCode());

            if (IDialogConstants.OK_ID == saveAllProcsDialog.getReturnCode()) {
                savePackage(input);
            }
        } else {
            if (IDialogConstants.OK_ID == prefStore
                    .getInt(ProcessEditorConstants.PREF_SAVE_EDITOR)) {
                savePackage(input);
            }
        }
    }

    private void savePackage(ProcessInterfaceEditorInput input)
            throws WrappedException {
        try {
            input.getWorkingCopy().save();
        } catch (IOException e) {
            throw new WrappedException(e);
        } finally {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
     */
    @Override
    public void dispose() {
        /*
         * XPD-1140: Must remove listeners when we are disposed otherwise we'll
         * be kept for ever by being in listener list (and our notifyChanged()
         * will keep being called.
         */
        ProcessInterfaceEditorInput processInterfaceInput =
                (ProcessInterfaceEditorInput) getEditorInput();
        WorkingCopy workingCopy = processInterfaceInput.getWorkingCopy();
        if (workingCopy != null) {
            ItemProviderAdapter ip =
                    (ItemProviderAdapter) workingCopy.getAdapterFactory()
                            .adapt(processInterfaceInput.getProcessInterface(),
                                    processInterfaceInput.getProcessInterface()
                                            .eClass().getEPackage());
            ip.removeListener(this);
            workingCopy.removeListener(this);
        }

        super.dispose();
    }

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
     * SID XPD-7285: the default isSaveOnCloseNeeded() will return true if
     * anything in XPDL file has changed, AND because the interface editor is
     * only editoing part of the file and the user can save from project
     * explorer then we don't have to force save.
     * 
     * @see org.eclipse.ui.part.EditorPart#isSaveOnCloseNeeded()
     * 
     * @return
     */
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
                if (ref.getId()
                        .equals("org.eclipse.ui.navigator.ProjectExplorer")) { //$NON-NLS-1$
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
     * @see com.tibco.xpd.resources.ui.IRefreshableTitle#refreshTitle()
     *
     */
    @Override
    public void refreshTitle() {
        updateTitle();
        firePropertyChange(PROP_TITLE);
    }

    /**
     * Internal method to update the editor title.
     */
    private void updateTitle() {
        ProcessInterfaceEditorInput processInterfaceInput = (ProcessInterfaceEditorInput) getEditorInput();

        /*
         * XPD-1140: Show correct title to same as editor title (display name
         * (tokenname) ( + [Read-Only] as appropriate)
         */
        WorkingCopy workingCopy = processInterfaceInput.getWorkingCopy();

        String title = processInterfaceInput.getName();
        if (workingCopy != null && workingCopy.isReadOnly()) {
            title += " " + Messages.ProcessInterfaceFormEditor_ReadOnly_label; //$NON-NLS-1$
        }
        setPartName(title);
    }

}
