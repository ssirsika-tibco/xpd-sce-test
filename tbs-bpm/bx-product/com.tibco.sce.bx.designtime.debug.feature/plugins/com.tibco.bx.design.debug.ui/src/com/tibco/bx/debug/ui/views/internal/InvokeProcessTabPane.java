package com.tibco.bx.debug.ui.views.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.bx.debug.core.runtime.IProcessInstanceController;

public class InvokeProcessTabPane extends ProcessTabPane  {

    private ComboxContributionItem contributionItem;
    
	public InvokeProcessTabPane(FormToolkit toolkit, Composite parent,
			int style, boolean isSoapType, String soapRequest) {
		super(toolkit, parent, style, isSoapType, soapRequest);
	}

  	
  	@Override
	protected List getActionsForToolbar() {
		List actionList = new ArrayList();
		
		contributionItem = new ComboxContributionItem("my Item",new ViewerFilter(){ //$NON-NLS-1$
				@Override
				public boolean select(Viewer viewer, Object parentElement,
						Object element) {
					return true;
				}
	        	
	        });
		 contributionItem.setToolBarManager(getToolBarManager());
		 actionList.add(contributionItem);
		 actionList.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		 actionList.addAll(super.getActionsForToolbar());
		return actionList;
	}

   public void setInput(Object input){
	   if(input instanceof Process){
		   contributionItem.setProcess((Process)input);
	   }else{
		   contributionItem.setInput(input);
	   }
	  
   }

   @Override
   public void setController(IProcessInstanceController controller) {
	   super.setController(controller);
	   contributionItem.addSelectionChangedListener((ProcessInstanceController)getController());
	   contributionItem.setInput(getController().getRunnedActivity());
   }
   
}
