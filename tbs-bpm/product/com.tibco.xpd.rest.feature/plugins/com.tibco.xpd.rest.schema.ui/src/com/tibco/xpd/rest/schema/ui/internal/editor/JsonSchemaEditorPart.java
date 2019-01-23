package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.GlobalActionManager;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalAction;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.editorHandler.IDisplayEObject;
import com.tibco.xpd.resources.ui.util.MarkerFinder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.ui.misc.PartListener2Adapter;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.XpdPropertiesFormToolkit;

/**
 * The editor part containing the JSON Schema editor.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class JsonSchemaEditorPart extends EditorPart implements
        PropertyChangeListener, ITabbedPropertySheetPageContributor,
        IGotoMarker, IDisplayEObject {

    private Package pkg;

    private WorkingCopy wc;

    private Map<String, IAction> actions;

    private JsonSchemaEditor editor;

    private IPartListener2 partListener;

    private XpdFormToolkit toolkit;

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
                if (root instanceof Package) {
                    pkg = (Package) root;
                } else {
                    throw new PartInitException(
                            String.format(Messages.JsonSchemaEditorPart_contentInvalid,
                                    file.getName()));
                }
            } else {
                throw new PartInitException(
                        String.format(Messages.JsonSchemaEditorPart_fileUnsupported,
                                file.getFileExtension()));
            }
        } else {
            throw new PartInitException(
                    Messages.JsonSchemaEditorPart_invalidInputType);
        }
        partListener = new PartListener2Adapter() {
            @Override
            public void partDeactivated(IWorkbenchPartReference partRef) {
                IWorkbenchPart part = partRef.getPart(false);
                if (JsonSchemaEditorPart.this.equals(part)) {
                    editor.saveSettings();
                }
            }

            @Override
            public void partActivated(IWorkbenchPartReference partRef) {
                IWorkbenchPart part = partRef.getPart(false);
                if (JsonSchemaEditorPart.this.equals(part)) {
                    editor.loadSettings();
                }
            }
        };
        site.getPage().addPartListener(partListener);
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart
     *      #createPartControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     *            The aprent composite.
     */
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout());
        parent.setBackground(parent.getDisplay()
                .getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        toolkit =
                new XpdPropertiesFormToolkit(
                        new TabbedPropertySheetWidgetFactory());
        editor = new JsonSchemaEditor(this);
        editor.createControls(parent, toolkit);
        getSite().setSelectionProvider(editor);
        editor.setInput(pkg);
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     * 
     */
    @Override
    public void dispose() {
        getSite().getPage().removePartListener(partListener);
        if (wc != null) {
            wc.removeListener(this);
        }
        if (toolkit != null) {
            toolkit.dispose();
        }
        super.dispose();
    }

    /**
     * @see org.eclipse.ui.part.EditorPart
     *      #doSave(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     *            The progress monitor.
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        try {
            if (wc != null) {
                wc.save();
            }
        } catch (IOException e) {
            throw new WrappedException(e);
        } finally {
            firePropertyChange(PROP_DIRTY);
        }
    }

    /**
     * Not supported in this editor.
     * 
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     * 
     */
    @Override
    public void doSaveAs() {
    }

    /**
     * @see org.eclipse.ui.part.EditorPart#isDirty()
     * 
     * @return true if the model is dirty.
     */
    @Override
    public boolean isDirty() {
        return wc.isWorkingCopyDirty();
    }

    /**
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     * 
     * @return false, saveAs is not supported.
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     * 
     */
    @Override
    public void setFocus() {
        editor.setFocus();
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * 
     * @param evt
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
                    getSite().getPage().closeEditor(JsonSchemaEditorPart.this,
                            false);
                }
            }
        });
    }

    /**
     * Return action associated to the Editor with given ID, returns null when
     * not found.
     * 
     * @param id
     *            The action ID.
     * @return {@link IAction} associated with this id.
     */

    public IAction getAction(String id) {
        if (actions == null) {
            createActions();
        }
        return actions.get(id);
    }

    /**
     * Create set of actions for WorkListFacade Editor
     */
    protected void createActions() {
        if (getSite() != null) {
            actions = new HashMap<>();

            if (wc instanceof AbstractTransactionalWorkingCopy) {
                AbstractTransactionalWorkingCopy jswc =
                        (AbstractTransactionalWorkingCopy) wc;
                IUndoContext undoContext = jswc.getUndoContext();
                UndoActionHandler undo =
                        new UndoActionHandler(getSite(), undoContext);
                undo.setId(ActionFactory.UNDO.getId());
                actions.put(ActionFactory.UNDO.getId(), undo);

                RedoActionHandler redo =
                        new RedoActionHandler(getSite(), undoContext);
                redo.setId(ActionFactory.REDO.getId());
                actions.put(ActionFactory.REDO.getId(), redo);

                String[] actionIds =
                        new String[] { GlobalActionId.CUT, GlobalActionId.COPY,
                                GlobalActionId.PASTE };
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
     * @return The working copy.
     */
    public WorkingCopy getWorkingCopy() {
        return wc;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "com.tibco.xpd.rest.schema.ui.json.editor"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
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
     * @return The editor control instance.
     */
    public JsonSchemaEditor getJsonSchemaEditor() {
        return editor;
    }

    /**
     * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     */
    @Override
    public void gotoMarker(IMarker marker) {
        EObject[] objects = MarkerFinder.getObject(marker);
        if (objects != null && objects.length > 0) {
            editor.setSelection(new StructuredSelection(objects));
        }
    }

    /**
     * @see com.tibco.xpd.resources.ui.editorHandler.IDisplayEObject#gotoEObject(boolean,
     *      org.eclipse.emf.ecore.EObject[])
     * 
     * @param selectObjects
     * @param eObjects
     * @return
     */
    @Override
    public boolean gotoEObject(boolean selectObjects, EObject... eObjects) {
        if (selectObjects) {
            editor.setSelection(new StructuredSelection(eObjects));
        }
        return true;
    }
}
