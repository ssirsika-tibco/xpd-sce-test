package com.tibco.bx.emulation.ui.providers;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;

public class EmulationPasteAction extends PasteAction{

	public EmulationPasteAction(TransactionalEditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		if ((selection != null) && (!selection.isEmpty())) {
			
		}
		return super.createCommand(selection);
	}

	@Override
	public boolean updateSelection(IStructuredSelection selection) {
		return super.updateSelection(selection) && checkClipboard();
	}

	/*
	 * only support copy Testpoints and Assertions into ProcessNode 
	 */
	private boolean checkClipboard() {
		Object container = getStructuredSelection().getFirstElement();
		//container must be a ProcessNode
		//each pasting objects must be TestPoint and never exists in the container. 
		if(container instanceof ProcessNode){
			Collection<Object> original = domain.getClipboard();
			for(Object object : original){
				if(!(object instanceof Testpoint) && !(object instanceof Assertion) &&
						!(object instanceof Input) && !(object instanceof Output))
					return false;
				else if(object instanceof Input && ((ProcessNode)container).getInput() != null)
					return false;
				else if(object instanceof Output && ((ProcessNode)container).getOutput() != null)
					return false;
				else if(isExisted((ProcessNode)container,((NamedElement)object).getId()))
					return false;
			}
			return true;
		}
		return true;
	}

	private boolean isExisted(ProcessNode container, String id){
		return EmulationUtil.getTestpointById((ProcessNode)container, id) != null
		|| EmulationUtil.getAssertionById((ProcessNode)container, id) != null;
	}
	
}
