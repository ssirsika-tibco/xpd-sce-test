/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.editor;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.EditorPart;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.editor.util.WorkListFacadeEditorUtil;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * The WorkListFacade form editor designed to edit the Display Labels of the
 * Physical Work item Attribute.This Editor uses Eclipse Forms support.It embeds
 * WorkListFacadeEditorSection which holds the table in which the Physical Work
 * item Attributes are presented to the user.
 * 
 * @author aprasad
 */
public class WorkListFacadeEditor extends EditorPart implements
        PropertyChangeListener, ISaveablesSource {

    /**
     * Editor Section for Physical Work Item Attributes .
     */
    private WorkListFacadeEditorSection section;

    private ActionRegistry actions;

    private WorkingCopy workingCopy;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite,
     * org.eclipse.ui.IEditorInput)
     */
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        // ONLY allow FileEditorInput
        final WorkListFacadeEditorInput editorInput =
                adaptInput((IFileEditorInput) input);

        if (input instanceof IFileEditorInput && editorInput != null) {
            // Adapt IFileEditorInput for WorkListFacadeEditor
            setInput(editorInput);

            if (WorkListFacadeEditorUtil.isWorkListFacadeFile(editorInput
                    .getFile())) {
                // Show WorkListFacade file name in Editor title.
                setPartName(editorInput.getfileName());
                editorInput.getWorkingCopy().addListener(this);

                /*
                 * XPD-7165: Keep track of Working Copy to remove listener on
                 * dispose
                 */

                workingCopy = editorInput.getWorkingCopy();

                site.getPage()
                        .addPartListener(new WorkListFacadeEditorListener(
                                editorInput));
            }
        } else {
            throw new PartInitException(
                    Messages.WorkListFacadeEditor_Invalid_Input);
        }

    }

    @Override
    public void dispose() {

        /* XPD-7165: Remove listener from Working Copy */
        if (workingCopy != null) {
            workingCopy.removeListener(this);
        }
    }

    /**
     * Adapts EditorInput for WorkListFacadeEditor, wraps {@link IFile} from
     * {@link IFileEditorInput} into the {@link WorkListFacadeEditorInput}.
     * 
     * @param input
     * @return WorkListFacadeEditorInput, {@link IFile} wrapped into
     *         {@link WorkListFacadeEditorInput}.
     */
    private WorkListFacadeEditorInput adaptInput(IFileEditorInput input) {
        try {
            return new WorkListFacadeEditorInput(input.getFile());
        } catch (InvalidFileException e) {
            WorkListFacadeResourcePlugin.getDefault()
                    .logError(Messages.WorkListFacadeEditor_Invalid_File, e);
        }
        return null;
    }

    @Override
    public void createPartControl(Composite parent) {

        parent.setLayout(new FillLayout());

        // Add Section , which contains the controls for editing facade details
        section = new WorkListFacadeEditorSection(this);
        XpdFormToolkit secToolkit = new XpdWizardToolkit(parent);

        setInputData();
        section.createControls(parent, secToolkit);

    }

    @Override
    public void setFocus() {
        if (section != null) {
            section.setFocus();
        }
    }

    /**
     * Gets the input data from the {@link WorkListFacadeEditorInput} and sets
     * it to the {@link WorkListFacadeEditorSection}.
     */
    private void setInputData() {
        WorkListFacade facadeRoot =
                ((WorkListFacadeEditorInput) getEditorInput()).getModelRoot();

        ArrayList<EObject> input = new ArrayList<EObject>();
        input.add(facadeRoot);
        section.setInput(input);
        section.refresh();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorPart#isDirty()
     */
    @Override
    public boolean isDirty() {
        WorkListFacadeWorkingCopy workingCopy =
                ((WorkListFacadeEditorInput) getEditorInput()).getWorkingCopy();

        if (workingCopy != null) {
            return workingCopy.isWorkingCopyDirty();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor
     * )
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        try {
            WorkingCopy workingCopy =
                    ((WorkListFacadeEditorInput) getEditorInput())
                            .getWorkingCopy();
            if (workingCopy != null) {
                workingCopy.save();
            }
        } catch (IOException e) {
            throw new WrappedException(e);
        } finally {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#doSaveAs()
     */
    @Override
    public void doSaveAs() {
        // Save As Not Allowed
    }

    /**
     * Retrieves the descriptor for this editor
     * 
     * @return the editor descriptor
     */
    final protected IEditorDescriptor getEditorDescriptor() {
        IEditorRegistry editorRegistry =
                PlatformUI.getWorkbench().getEditorRegistry();
        IEditorDescriptor editorDesc =
                editorRegistry.findEditor(getSite().getId());
        return editorDesc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
     * PropertyChangeEvent)
     */
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
                    getSite().getPage().closeEditor(WorkListFacadeEditor.this,
                            false);
                }
            }
        });
    }

    /**
     * WorkListFacadeEditor Listener, which handles the editor close event. When
     * the working copy is dirty and the Editor is closed without saving , the
     * listener calls the method revertModelChanges on the
     * WorkListFacadeWorkingCopy, which reloads the working copy and fires the
     * WCdirtyflag change event.Firing the event is required to refresh the
     * Project Explorer , and get rid of the dirty flag.
     * 
     * @author aprasad
     * 
     */
    class WorkListFacadeEditorListener implements IPartListener2 {

        WorkListFacadeEditorInput editorInput;

        public WorkListFacadeEditorListener(
                WorkListFacadeEditorInput editorInput) {
            this.editorInput = editorInput;
        }

        @Override
        public void partVisible(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partOpened(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partInputChanged(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partHidden(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partDeactivated(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partClosed(IWorkbenchPartReference partRef) {
            // Revert the changes of WorkingCopy when editor is closed without
            // saving.

            if (editorInput.getWorkingCopy().isWorkingCopyDirty()) {
                editorInput.getWorkingCopy().revertModelChanges();
            }

        }

        @Override
        public void partBroughtToTop(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partActivated(IWorkbenchPartReference partRef) {
        }
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
        if (getSite() != null) {
            actions = new ActionRegistry();

            IUndoContext undoContext =
                    ((WorkListFacadeEditorInput) getEditorInput())
                            .getWorkingCopy().getUndoContext();
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

        if (workingCopy != null) {

            if (workingCopy.getSaveable() != null) {

                return new Saveable[] { workingCopy.getSaveable() };
            }
        }
        return new Saveable[0];
    }
}