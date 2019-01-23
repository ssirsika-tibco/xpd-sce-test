package com.tibco.bx.debug.ui.views.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.util.DebugUIUtil;
import com.tibco.bx.emulation.core.invoke.EmulationRunner;
import com.tibco.bx.emulation.core.invoke.IEmulationRunnerListener;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.ProcessNode;

public class EmulationPane extends Composite  implements IEmulationRunnerListener , IDebugTargetChangedListener{

	private FormToolkit toolkit;
    private CheckboxTreeViewer emulationViewer;
    private EmulationViewNameLabelProvider emulationViewLabelProvider;
	
    private BxDebugTarget bxTarget;
    private Map<IFile,List<ProcessNode>> inputCache;
    private Map<ProcessNode,EmulationRunner> emulationRunner;
    private ProcessTemplate[] templates;
	
	
	public EmulationPane(FormToolkit toolkit ,Composite parent, int style) {
		super(parent, style);
		this.toolkit = toolkit;
		
		emulationRunner = new HashMap<ProcessNode, EmulationRunner>();
		  
		setLayout(new GridLayout(1,false));
		{
	    Section processTreeG = toolkit.createSection(this, Section.TITLE_BAR);
        processTreeG.setText(Messages.getString("EmulationPane.processSection.label")); //$NON-NLS-1$
        processTreeG.setLayoutData(new GridData(GridData.FILL_BOTH));
        processTreeG.setLayout(new GridLayout(1, false));
	    Composite client = toolkit.createComposite(processTreeG, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 1;
        client.setLayout(gridLayout);
        processTreeG.setClient(client);
        createEmulationComposite(client);
        createSectionToolbar(toolkit,processTreeG);
		toolkit.adapt(this);
		}
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
        
        toolBarManager.add(new SelectAllAction());
        toolBarManager.add(new DeselectAllAction());
        toolBarManager.update(true);
        section.setTextClient(toolbar);
	}

	public ISelectionProvider getSelectionProvider() {
	    return emulationViewer;
	}
	
	private Composite createEmulationComposite(Composite parent) {

		Tree emulationTree = toolkit.createTree(parent, SWT.CHECK | SWT.V_SCROLL | SWT.H_SCROLL);
		emulationViewer = new CheckboxTreeViewer(emulationTree);
		emulationTree.setLinesVisible(false);
		emulationTree.setLayoutData(new GridData(GridData.FILL_BOTH));

		emulationViewer.setContentProvider(new EmulationPaneContentProvider());
		emulationViewer.setInput(getInputCache(true));
		emulationViewer.setSorter(new ViewerSorter());

//		TreeViewerColumn nameColumn = new TreeViewerColumn(emulationViewer, SWT.NONE);
//		nameColumn.getColumn().setWidth(350);
//		EmulationViewLabelProvider viewLabelProvider = new EmulationViewLabelProvider();
		emulationViewLabelProvider = new EmulationViewNameLabelProvider(parent.getDisplay(), emulationViewer);
//		emulationViewer.setLabelProvider(viewLabelProvider);
		emulationViewer.setLabelProvider(emulationViewLabelProvider);
		emulationViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object item = event.getElement();
				if (event.getChecked()) {
					emulationViewer.setChecked(item, false);
					emulationViewer.setExpandedState(item, true);
					Object[] willCheckItem = getAvaiableNode(item);
					for (Object checkeditem : willCheckItem) {
						emulationViewer.setChecked(checkeditem, true);
						createEmulationRuner(checkeditem);
					}
				} else {
					if (item instanceof ProcessNode) {
						if (emulationViewLabelProvider.willRunning != null && emulationViewLabelProvider.willRunning.containsKey(item)) {
							emulationViewLabelProvider.willRunning.remove(item);
						}
					} else {
						List<ProcessNode> children = getInputCache(false).get(item);
						for (ProcessNode child : children) {
							if (emulationViewLabelProvider.willRunning != null && emulationViewLabelProvider.willRunning.containsKey(child)) {
								emulationViewLabelProvider.willRunning.remove(child);
							}
						}
					}
					emulationViewer.setSubtreeChecked(event.getElement(), event.getChecked());
				}
				updateCheckbox();
				emulationViewer.refresh(true);
			}
		});

		emulationViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {

				if (event.getSelection() instanceof TreeSelection) {

					try {
						TreeSelection selection = (TreeSelection) event.getSelection();
						TreePath[] paths = selection.getPaths();
						if (paths.length > 0 && paths[0].getFirstSegment() instanceof IFile) {

							DebugUIUtil.openEmulationFile((IFile) paths[0].getFirstSegment(), PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getActivePage());
						}
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}

		});
		return null;
	}

	
    private void createEmulationRuner(Object checkedItem) {
        if(checkedItem instanceof IFile ||
                bxTarget == null ||
                emulationRunner == null) {
            return ;
        }
        
        EmulationRunner runner =  emulationRunner.get(checkedItem);
        if(runner == null) {
            runner = new EmulationRunner(bxTarget);
            emulationRunner.put((ProcessNode) checkedItem, runner);
        }
    }
    
	private Object[] getAvaiableNode(Object item) {
		List<Object> availableNodes = new ArrayList<Object>();
		if (item instanceof ProcessNode) {
			if (isAvailableProcess((ProcessNode) item)) {
				availableNodes.add((ProcessNode) item);
			}
		} else {
			List<ProcessNode> children = getInputCache(false).get(item);
			int unavailable = 0;
			for (ProcessNode child : children) {
				if (isAvailableProcess(child)) {
					availableNodes.add(child);
				} else {
					unavailable++;
				}
			}
			if (unavailable == 0) {
				availableNodes.add(item);
			}
		}
		return availableNodes.toArray();
	}
	
	private boolean isAvailableProcess(ProcessNode processNode) {
	    if(bxTarget == null) {
	        return false;
	    }
	    if(templates != null){
	    	for(int i = 0 ; i < templates.length ; i ++) {
	    		if(StringUtils.equal(processNode.getId(), templates[i].getProcessId())) {
	    			return true;
	    		}
	    	}
	    }
	    return false;
	}
	
    public void setDebugTarget(BxDebugTarget newTarget) {
        
        if(newTarget == bxTarget) {
            return;
        }
        
        try {
            emulationRunner.clear();
            bxTarget = newTarget;
            if(newTarget != null) {
                IBxProcessListing processListing = (IBxProcessListing) newTarget.getAdapter(IBxProcessListing.class);
                if(processListing != null){
                	templates = processListing.getProcessTemplates();
                }
                checkAllAvailabeProcess();
            }else {
               reset();
               templates = null;
               return;
            }
            
        } catch (CoreException e) {
            DebugUIActivator.log(e);
        }
    }
    
   
	private void checkAllAvailabeProcess() {
    	if(emulationViewer.getTree().isDisposed()){
    		return;
    	}
        TreeItem []  items = emulationViewer.getTree().getItems();
        for (TreeItem treeItem : items) {
            emulationViewer.setExpandedState(treeItem.getData(), true);
            emulationViewer.setChecked(treeItem.getData(), false);
            emulationViewer.setSubtreeChecked(treeItem.getData(), false);
            Object[] willCheckItem = getAvaiableNode(treeItem.getData());
            for(Object checkeditem : willCheckItem) {
                emulationViewer.setChecked(checkeditem, true);
                createEmulationRuner(checkeditem);
            }
        }
        emulationViewer.refresh(true);
    }

    private Map<IFile,List<ProcessNode>> getInputCache(boolean refresh) {
        if(inputCache == null || refresh){
            inputCache = EmulationUtil.getAllInputFromWorkspace();
        }
        return inputCache;
    }
    
    public Map<ProcessNode,EmulationRunner> getSelectedRunner(){
        Map<ProcessNode,EmulationRunner> checkedRunner = new HashMap<ProcessNode, EmulationRunner>();
        ProcessNode[] checkedItem = getCheckedItem();
        for(ProcessNode item : checkedItem) {
                EmulationRunner runner = emulationRunner.get(item);
                if(runner != null) {
                    runner.reset();
                    checkedRunner.put((ProcessNode) item, runner);
                }
        }
        
        return checkedRunner;
    }
    
    public ProcessNode[] getCheckedItem() {
        ArrayList<ProcessNode> processNode = new ArrayList<ProcessNode>();
        Object[] checkedItem = emulationViewer.getCheckedElements();
        for(Object item : checkedItem) {
            if(item instanceof ProcessNode) {
                processNode.add((ProcessNode) item);
            }
        }
        ProcessNode[] nodes = new ProcessNode[processNode.size()];
        processNode.toArray(nodes);
        return nodes;
    }

    public void setNodeWillRun( Map<ProcessNode,EmulationRunner> nodes) {
        emulationViewLabelProvider.setNodeWillRun(nodes);
    }

    @Override
    public void updateExecutionStatus(int eventType, String message, Object data) {
        if((eventType & (BxDebugEvent.COMPLETED | BxDebugEvent.FAULT | BxDebugEvent.TERMINATED )) > 0) {
           UIJob uiJob = new UIJob("") { //$NON-NLS-1$
            @Override
            public IStatus runInUIThread(IProgressMonitor monitor) {
            	if(emulationViewer!= null &&
            	!emulationViewer.getControl().isDisposed()){
            		emulationViewer.refresh(true);
            	}
            	return Status.OK_STATUS;
            }
         
           };
           uiJob.schedule();
        }
    }

    @SuppressWarnings("deprecation")
    public void reset() {
    	if(!isDisposed()){
    		emulationViewLabelProvider.setNodeWillRun(null);
	        emulationViewer.setAllChecked(false);
	        emulationViewer.setInput(getInputCache(true));
	        emulationRunner.clear();
	        emulationViewer.refresh();
    	}
    }
	
    class SelectAllAction extends Action{
        public SelectAllAction() {
            super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$
            setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_SELECTED));
            setToolTipText(Messages.getString("EmulationPane.selectAllAction.tooltip")); //$NON-NLS-1$
        }
        
        @Override
        public void run() {
        	if(bxTarget != null){
        		setNodeWillRun(EmulationPane.this.getSelectedRunner());
        		checkAllAvailabeProcess();
        		emulationViewer.refresh(true);
        	}
        }
    }
    
    class DeselectAllAction extends Action{
        public DeselectAllAction() {
            super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$
            setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_DESELECTED));
            setToolTipText(Messages.getString("EmulationPane.deselectAllAction.tooltip")); //$NON-NLS-1$
        }
        
        @Override
        public void run() {
        	if(bxTarget != null){
        		if(emulationViewLabelProvider.willRunning != null){
        			emulationViewLabelProvider.willRunning.clear();
        		}
        		emulationViewer.setAllChecked(false);
        		emulationViewer.refresh(true);
        	}
        }
    }

	@Override
	public void selectionChanged(BxDebugTarget event) {
		setDebugTarget(event);
	}
	
	private void updateCheckbox() {
		TreeItem[] treeItems = emulationViewer.getTree().getItems();
		for (TreeItem item : treeItems) {
			TreeItem[] tItems = item.getItems();
			if(tItems.length == 0){
				continue;
			}
			int checked = 0;
			for (TreeItem tItem : tItems) {
				if (tItem.getChecked()) {
					checked++;
				}
			}
			item.setChecked(checked == tItems.length);
		}
	}
	
	public void updateEmulationViewer(){
		emulationViewer.refresh();
	}
	
	public void setCheckboxTreeEnabled(boolean isEnable){
		emulationViewer.getTree().setEnabled(isEnable);
	}

}
