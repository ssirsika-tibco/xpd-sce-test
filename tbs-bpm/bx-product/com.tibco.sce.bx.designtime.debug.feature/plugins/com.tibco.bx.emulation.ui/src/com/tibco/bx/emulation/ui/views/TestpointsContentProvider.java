package com.tibco.bx.emulation.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.ProcessNode;

public class TestpointsContentProvider implements ITreeContentProvider{

	boolean showNode = true;
	public Object[] getChildren(Object element) {
		if(element instanceof EmulationData && showNode){
			return ((EmulationData)element).getProcessNodes().toArray();
		}else if(element instanceof ProcessNode && showNode){
			return getAllElements(((ProcessNode)element));
		}else if(element instanceof EmulationData && !showNode){
			return getAllElements((EmulationData)element);
		}else if(element instanceof ProcessNode && !showNode){
			return getAllElements(((ProcessNode)element));
		}
		return new Object[0];
	}
	/*
	 * get all Testpoints and Assertions, Input and Output from an EmulationData
	 */
	private Object[] getAllElements(EmulationData emulationData){
		List<EmulationElement> tList = new ArrayList<EmulationElement>();
		EList<ProcessNode> pList = emulationData.getProcessNodes();
		for (ProcessNode processNode : pList) {
			Input input = processNode.getInput();
			if(input != null){
				tList.add(input);
			}
			//TODO does not display outputs
			/*Output output = processNode.getOutput();
			if(output != null){
				tList.add(output);
			}*/
			tList.addAll(processNode.getIntermediateInputs());
			tList.addAll(processNode.getTestpoints());
			tList.addAll(processNode.getAssertions());
			tList.addAll(processNode.getMultiInputNodes());
		}
		return tList.toArray();
	}
	
	/*
	 * get all Testpoints and Assertions, Input and Output from a ProcessNode
	 */
	private Object[] getAllElements(ProcessNode processNode){
		List<EmulationElement> tList = new ArrayList<EmulationElement>();
		Input input = processNode.getInput();
		if(input != null){
			tList.add(input);
		}
		//TODO does not display outputs
		/*Output output = processNode.getOutput();
		if(output != null){
			tList.add(output);
		}*/
		tList.addAll(processNode.getIntermediateInputs());
		tList.addAll(processNode.getTestpoints());
		tList.addAll(processNode.getAssertions());
		tList.addAll(processNode.getMultiInputNodes());
		return tList.toArray();
	}
	
	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public void setShowNode(boolean showNode) {
		this.showNode = showNode;
	}
	
}
