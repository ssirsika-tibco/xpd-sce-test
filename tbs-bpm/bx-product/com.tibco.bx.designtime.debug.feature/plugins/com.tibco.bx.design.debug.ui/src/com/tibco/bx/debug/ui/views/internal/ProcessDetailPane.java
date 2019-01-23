package com.tibco.bx.debug.ui.views.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.invoke.launcher.ProcessLauncherManager;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.OpenEditorAction;
import com.tibco.bx.debug.ui.util.DebugContextUtil;

public class ProcessDetailPane extends Composite implements IDebugContextListener{

	private TableViewer processViewer;
	private BxDebugTarget target;
	private Map<ProcessTemplate,OpenEditorAction> openEditorActionMap ;
	    
	public ProcessDetailPane(Composite parent, int style , FormToolkit toolkit) {
		super(parent, style);
		openEditorActionMap = new HashMap<ProcessTemplate,OpenEditorAction>();
		Section processTreeG = toolkit.createSection(this, Section.TITLE_BAR);
		processTreeG.setText(Messages.getString("ProcessDetailPane.processSection.text")); //$NON-NLS-1$
		processTreeG.setLayoutData(new GridData(GridData.FILL_BOTH));
		processTreeG.setLayout(new FillLayout());
		Composite client = toolkit.createComposite(processTreeG, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 1;
		client.setLayout(gridLayout);
		processTreeG.setClient(client);
		createProcessListGroup(toolkit, processTreeG, client);
		createSectionToolbar(toolkit, processTreeG);
	}
	
	private void createSectionToolbar(FormToolkit toolkit, Section section) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});
		
		toolBarManager.add(new RefreshAction());
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}
	
	private void createProcessListGroup(FormToolkit toolkit, Section processTreeG, Composite client) {
		
		Table table = toolkit.createTable(client, SWT.SINGLE | SWT.BORDER);
		GridData tableGd = new GridData(GridData.FILL_BOTH);
		tableGd.horizontalSpan = ((GridLayout)client.getLayout()).numColumns;
		table.setLayoutData(tableGd);
		processViewer = new TableViewer(table);
		processViewer.setLabelProvider(new ProcessListViewerLabelProvider(target== null? "" : target.getModelType())); //$NON-NLS-1$
		processViewer.setContentProvider(new ArrayContentProvider());
		processViewer.setSorter(new ViewerSorter());
		processViewer.addFilter(new ProcessTemplateFilter(target== null? "" : target.getModelType())); //$NON-NLS-1$
		updateProcessViewer(false);
		
		processViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object firstElement = ((IStructuredSelection) event
						.getSelection()).getFirstElement();
				ProcessTemplate template = (ProcessTemplate) firstElement;
				OpenEditorAction openEditor = openEditorActionMap.get(template);
				if (openEditor == null) {
					openEditor = new OpenEditorAction(template, target.getModelType());
					openEditorActionMap.put(template, openEditor);
				}
				openEditor.run();
			}
		});
	}

	public void updateProcessViewer(boolean refresh) {
		processViewer.setSelection(null);
		((ProcessListViewerLabelProvider)processViewer.getLabelProvider()).setModelType(target== null? "" : target.getModelType()); //$NON-NLS-1$
		ViewerFilter[] filters = processViewer.getFilters();
		for (int i = 0; i < filters.length; i++) {
			if(filters[i] instanceof ProcessTemplateFilter){
				((ProcessTemplateFilter)filters[i]).setModelType(target== null? "" : target.getModelType()); //$NON-NLS-1$
			}
		}
		processViewer.setInput(getProcessViewerInput(refresh));
		processViewer.refresh();
	}
	
	public ISelectionProvider getSelectionProvider(){
		return processViewer;
	}
	
	public void setTarget(BxDebugTarget target) {
		if (target == this.target) {
			return;
		}
		this.target = target;
		updateProcessViewer(false);
	}
	
	private List<ProcessTemplate> getProcessViewerInput(boolean refresh) {
		if(target == null){
			setTarget( TargetManager.getDefault().getCurrentTarget());
		}
		if (target != null) {
			IBxProcessListing processListing = (IBxProcessListing) target.getAdapter(IBxProcessListing.class);
			if (processListing != null) {
				try {
					return Arrays.asList(processListing.getProcessTemplates());
				} catch (CoreException e) {
					DebugUIActivator.log(e);
				}
			}
		}
		return Collections.emptyList();
	}
	
	@Override
	public void debugContextChanged(DebugContextEvent event) {
		if (DebugContextUtil.hasTarget(event)) {
			BxDebugTarget debugTarget = DebugContextUtil.getTargetFromDebugContextEvent(event);
			if (debugTarget == null || debugTarget.isTerminated() || debugTarget.isDisconnected()) {
				if (target == null || target.isTerminated() || target.isDisconnected()) {
					setTarget(null);
					ProcessLauncherManager.instance.removeLaunchers();
				}
			}
			if (debugTarget != target) {
				setTarget((BxDebugTarget) debugTarget);
			}
		}
	}
	
    public void addDebugTargetChangedListener(IDebugTargetChangedListener listener) {
    	TargetManager.getDefault().addDebugTargetChangedListener(listener);
    }

    public void refresh(){
    	if(processViewer != null){
    		processViewer.refresh();
    	}
    }

    public void removeDebugTargetChangedListener(
            IDebugTargetChangedListener listener) {
    	TargetManager.getDefault().removeDebugTargetChangedListener(listener);
    }
    
	class RefreshAction extends Action{
		public RefreshAction() {
			super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$
			setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_REFRESH));
			setToolTipText(Messages.getString("ProcessDetailPane.RefreshAction.tooltip")); //$NON-NLS-1$
		}
		
		@Override
		public void run() {
			updateProcessViewer(true);
		}
	}
	
	public BxDebugTarget getTarget(){
		return target;
	}
	
}
