package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.invoke.util.ProcessTemplateManager;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.util.ProcessUtil;

public class ComboxContributionItem extends ControlContribution implements ISelectionChangedListener,ISelectionProvider{
    
    private ComboViewer startActivityView = null;
    private ListenerList startActivityListeners;
    private ViewerFilter vieweFilter = null;
    private IToolBarManager toolbar = null; 
    
    private static int COMBO_WIDTH = 160;

    public ComboxContributionItem(String id , ViewerFilter eventFilter) {
        super(id);
        startActivityListeners = new ListenerList();
        this.vieweFilter = eventFilter;
    }

    public void setProcess(Process process){
    	startActivityView.setInput(process);
    	selectFirst(false);
    }
    
    private void selectFirst(boolean sendMsg){
    	 if(startActivityView.getCombo().getItemCount() > 0){
         	startActivityView.getCombo().select(0);
         	SelectionChangedEvent activitySelectedEvent = new SelectionChangedEvent(startActivityView,startActivityView.getSelection());
         	if(sendMsg){
         		fireSelectionChange(activitySelectedEvent);
         	}
         }
    }
    
    public void setInput(Object input){
    	startActivityView.setInput(input);
    	selectFirst(true);
    }
    
    @Override
    protected Control createControl(Composite parent) {
        startActivityView = new ComboViewer(parent,SWT.NONE | SWT.READ_ONLY);
        startActivityView.setLabelProvider(new StartActivityLabelProvider());
        startActivityView.setContentProvider(new StartActivityContentProvider());
        startActivityView.addSelectionChangedListener(this);
        if(vieweFilter != null){
        	startActivityView.addFilter(vieweFilter);
        }
        return startActivityView.getControl();
    }

    public Object getInput(){
    	return startActivityView.getInput();
    }
    
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
	   //init start event and select the default one
       if(event.getSource() == startActivityView){
    	   fireSelectionChange(event);
           return;
       }
       
       if( event.getSelection().isEmpty() ||
               !(event.getSelection() instanceof StructuredSelection)){
    	   startActivityView.setInput(null);
    	   fireSelectionChange(event);
            return ;
       } 
       StructuredSelection selectItem = (StructuredSelection) event.getSelection(); 
       ProcessTemplate template = (ProcessTemplate) selectItem.getFirstElement();
       IBxDebugTarget target = TargetManager.getDefault().getCurrentTarget();
       if(target != null){
    	   EObject process = ProcessUtil.getProcess(template.getProcessId(), target.getModelType());
           startActivityView.setInput(process);
           ProcessTemplateManager.instance.put(process, template);
           selectFirst(true); 
       }
      
	}

	
	private void fireSelectionChange(SelectionChangedEvent event){
		for(Object listener : startActivityListeners.getListeners()){
			((ISelectionChangedListener)listener).selectionChanged(event);
		}
		if(this.toolbar != null){
			for(IContributionItem item :toolbar.getItems()){
				item.update();
			}
		}
		
	}
	
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
	    startActivityListeners.add(listener);	
	}

	@Override
	public ISelection getSelection() {
		return startActivityView.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
    	startActivityListeners.remove(listener);	
	}

	@Override
	public void setSelection(ISelection selection) {
	}

	@Override
	protected int computeWidth(Control control) {
		return COMBO_WIDTH;
	}

	public void setToolBarManager(IToolBarManager toolBarManager) {
		this.toolbar = toolBarManager;
	}
	
	
}
