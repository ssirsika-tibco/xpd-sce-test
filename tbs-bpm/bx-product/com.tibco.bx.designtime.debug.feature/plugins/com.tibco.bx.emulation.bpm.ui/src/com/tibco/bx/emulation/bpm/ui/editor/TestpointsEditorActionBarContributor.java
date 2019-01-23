package com.tibco.bx.emulation.bpm.ui.editor;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

public class TestpointsEditorActionBarContributor extends EditorActionBarContributor{

	@Override
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);
	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
	}

	@Override
	public IActionBars getActionBars() {
		return super.getActionBars();
	}

	@Override
	public void init(IActionBars bars) {
		super.init(bars);
		final TransactionalEditingDomain transactionalEditingDomain = XpdResourcesPlugin
		.getDefault().getEditingDomain();
		bars.setGlobalActionHandler(ActionFactory.DELETE.getId(), 
			      new DeleteAction(transactionalEditingDomain,true){

					@Override
					public IStructuredSelection getStructuredSelection() {
						IStructuredSelection selection = super.getStructuredSelection();
						if (((IStructuredSelection)selection).size() >= 1) {
							Object element = ((IStructuredSelection)selection).getFirstElement();
							if(element instanceof SequenceFlowEditPart){
								AbstractGraphicalEditPart editPart =(SequenceFlowEditPart) element;
								Object model = editPart.getModel();
								Assertion assertion = null;
								ProcessNode processNode = EmulationBPMUIUtil.getProcessNode();
								
								if(processNode == null){
									Process process = ((Transition)model).getProcess();
									processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
								}
								
								assertion = EmulationUtil.getAssertionById(processNode, ((Transition)model).getId());
								return new StructuredSelection(assertion);
							}  	
						}
						return new StructuredSelection();
					}

					@Override
					public Command createCommand(Collection<?> selection) {
						return super.createCommand(selection);
					}
					
			
		});
	}

	@Override
	public void setActiveEditor(IEditorPart targetEditor) {
		super.setActiveEditor(targetEditor);
	}

	
}
