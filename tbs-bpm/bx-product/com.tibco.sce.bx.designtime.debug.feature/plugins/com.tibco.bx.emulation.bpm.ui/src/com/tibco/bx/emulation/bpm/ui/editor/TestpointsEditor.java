package com.tibco.bx.emulation.bpm.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.bpm.ui.editor.ProcessNodeEditorInput;
import com.tibco.xpd.processeditor.xpdl2.MarkerLocator;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.Xpdl2ProcessWidgetAdapterFactory;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.viewer.NavigationListener;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;

public class TestpointsEditor extends EditorPart implements
		ITabbedPropertySheetPageContributor, NavigationListener,
		DisposeListener, PropertyChangeListener,
		IGotoMarker, INavigationLocationProvider, IGotoEObject {
	 /** part ID */
    public final static String ID = "com.tibco.bx.emulation.bpm.ui.TestpointsEditor"; //$NON-NLS-1$
    
    public final static String CONTRIBUTOR_ID = "com.tibco.bx.emulation.ui.properties"; //$NON-NLS-1$
    
    protected ComposedAdapterFactory adapterFactory;
    
    private ProcessWidgetImpl widget;
    
	public void doSave(IProgressMonitor monitor) {
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		WorkspaceModifyOperation operation =
			new WorkspaceModifyOperation() {
				protected void execute(IProgressMonitor monitor)
						throws CoreException, InvocationTargetException,
						InterruptedException {
					ProcessNodeEditorInput processIntput = (ProcessNodeEditorInput) getEditorInput();
					try {
						processIntput.getWorkingCopy().save();
					} catch (IOException e) {
						EmulationUIActivator.log(e);
					}
				}
			
		};
		
		try {
			// This runs the options, and shows progress.
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			// Refresh the necessary state.
			//((BasicCommandStack)editingDomain.getCommandStack()).saveIsDone();
		}
		catch (Exception exception) {
			EmulationUIActivator.log(exception);
		}
	}

	public void doSaveAs() {
		
	}

	 /**
     * @see org.eclipse.ui.IEditorPart#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     */
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        if (!(input instanceof ProcessNodeEditorInput)) {
            closeEditor();
            throw new PartInitException(Messages.getString("TestpointsEditor.PartInitException.message")); //$NON-NLS-1$
        }
        
        ProcessNodeEditorInput processIntput = (ProcessNodeEditorInput) input;
        setPartName(processIntput.getName());
        setTitleToolTip(processIntput.getToolTipText());
        setInput(processIntput);
        processIntput.getWorkingCopy().addListener(this);
    }

    private void closeEditor() {
        Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            public void run() {
                getSite().getPage().closeEditor(TestpointsEditor.this,
                        false);
            }
        });
    }
    
    /**
     * Create process widget here
     */
    public void createPartControl(final Composite parent) {
        if (this.getEditorInput() == null) {
            return;
        }
        try {
            getEditorSite().getWorkbenchWindow().run(false, false,
                    new IRunnableWithProgress() {
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
    
    protected void doCreatePartControl(final Composite parent,
            IProgressMonitor monitor) {
    	ProcessNodeEditorInput processNodeIntput = (ProcessNodeEditorInput) getEditorInput();
		Process process = processNodeIntput.getProcess();
		Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.getString("TestpointsEditor.group.label") + WorkingCopyUtil.getText(process)); //$NON-NLS-1$
		group.setLayout(new FillLayout());
		// TODO the process may be page flow process
		widget = new ProcessWidgetImpl(true,
				ProcessWidgetImpl.ProcessWidgetType.BPMN_PROCESS);

		adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		BasicCommandStack commandStack = new BasicCommandStack();

		EditingDomain xpdlEditingDomain = new AdapterFactoryEditingDomain(
				adapterFactory, commandStack,
				new HashMap<Resource, Boolean>());
		widget.setEditingDomain(xpdlEditingDomain);
		widget.addNavigationListener(this);
		// factory for XPDL specific adapters
		widget.setAdapterFactory(new Xpdl2ProcessWidgetAdapterFactory());
		widget.setPreferences(Xpdl2ProcessEditorPlugin.getDefault()
				.getPluginPreferences());
		widget.setSite(getSite());
		
		widget.setInput(process);
		widget.createControl(group);
		
		widget.getControl().addDisposeListener(this);
		getEditorSite().setSelectionProvider(widget);
         
    }
    
    @Override
	public boolean isDirty() {
		return ((ProcessNodeEditorInput) getEditorInput()).getWorkingCopy().isWorkingCopyDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		return false;
	}

	@Override
	public void setFocus() {
		widget.getControl().setFocus();
	}

	/**
     * Delegate getAdapter to the Widget
     */
    public Object getAdapter(Class adapter) {
        Object ad = widget.getAdapter(adapter);
        if (ad != null) {
            return ad;
        } else if (adapter == IPropertySheetPage.class) {
            TabbedPropertySheetPage p = new TabbedPropertySheetPage(this);
            return p;
        } else if (adapter == IGotoMarker.class) {
            return this;
        } 
        return super.getAdapter(adapter);
    }
    
    
	public String getContributorId() {
		return CONTRIBUTOR_ID;
	}

	public boolean revealObject(Object arg0) {
		return false;
	}

	public void notifyChanged(Notification notification) {
		setPartName(getEditorInput().getName());
		firePropertyChange(PROP_DIRTY);
	}

	public void widgetDisposed(DisposeEvent disposeEvent) {
		ProcessNodeEditorInput processIntput = (ProcessNodeEditorInput) getEditorInput();
        processIntput.getWorkingCopy().removeListener(this);
	}

	public void propertyChange(PropertyChangeEvent event) {
		String propName = event.getPropertyName();
		if (propName.equals(WorkingCopy.PROP_RELOADED)){
        	firePropertyChange(PROP_DIRTY);
        }else if (propName.equals(WorkingCopy.PROP_REMOVED)){
        	closeEditor();
        }else if (propName.equals(WorkingCopy.PROP_DIRTY)) {
        	firePropertyChange(PROP_DIRTY);
        }else if (propName.equals(WorkingCopy.CHANGED)) {
        	ProcessNodeEditorInput processIntput = (ProcessNodeEditorInput) getEditorInput();
        	if(processIntput.getProcessNode().eContainer() == null){//ProcessNode removed
        		closeEditor();
        	}else{
        		firePropertyChange(PROP_DIRTY);
        	}
        }

	}

	/**
     * Navigates to and selects the diagram object specified by the marker.
     */
    public void gotoMarker(IMarker marker) {
        EObject[] objects = MarkerLocator.getObject(marker);
        gotoEObject(true, objects);
    }

	public INavigationLocation createEmptyNavigationLocation() {
		return null;
	}

	public INavigationLocation createNavigationLocation() {
		return null;
	}

	 public boolean gotoEObject(boolean selectObject, EObject... eObjects) {

	        List<Object> selection = new ArrayList<Object>();
	        for (EObject eo : eObjects) {
	            Object editPart = getFindEditPartFor(eo);
	            if (editPart != null) {
	                selection.add(editPart);
	            }
	        }
	        if (!selection.isEmpty()) {
	            if (selectObject) {
	                widget.setSelection(new StructuredSelection(selection));
	            }
	            widget.navigateTo(selection.get(0));
	            return true;
	        }
	        return false;
	    }

	 private Object getFindEditPartFor(EObject eObject) {
	        Map registry = widget.getGraphicalViewer().getEditPartRegistry();
	        Object editPart = registry.get(eObject);
	        ProcessNodeEditorInput processIntput = (ProcessNodeEditorInput) getEditorInput();
	        Process process = processIntput.getProcess();
	        while (eObject != null && editPart == null) {
	        	if(eObject instanceof Testpoint){
	        		eObject = process.getActivity(((Testpoint)eObject).getId());
	        	}else if(eObject instanceof Assertion){
	        		eObject = process.getTransition(((Assertion)eObject).getId());
	        	}else if(eObject instanceof Input){
	        		eObject = process.getActivity(((Input)eObject).getId());
	        	}else if(eObject instanceof Output){
	        		eObject = process.getActivity(((Output)eObject).getId());
	        	}else if(eObject instanceof IntermediateInput){
	        		eObject = process.getActivity(((IntermediateInput)eObject).getId());
	        	}else{
	        		eObject = process;
	        	}
	            editPart = registry.get(eObject);
	        }
	        return editPart;
	    }

	@Override
	public void dispose() {
		super.dispose();
	}

	
}