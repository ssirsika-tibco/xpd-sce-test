package com.tibco.bx.debug.ui.views.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.bx.emulation.model.ProcessNode;

public class EmulationPaneContentProvider implements ITreeContentProvider {
    private Map<IFile, List<ProcessNode>> root = null;
    

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if(oldInput != newInput &&
                newInput instanceof Map<?,?>) {
            root = (Map<IFile, List<ProcessNode>>) newInput;
        }
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if(parentElement instanceof Map) {
            root = (Map<IFile, List<ProcessNode>>) parentElement;
            return root.keySet().toArray();
        }else if(parentElement instanceof IFile) {
            List<ProcessNode> processNodes =  root.get(parentElement);
            return processNodes.toArray();
        }
        return null;
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if(element instanceof Map) {
            return ((Map)element).size() > 0;
        }else if(element instanceof IFile) {
            List<ProcessNode> processNodes =  root.get(element);
           return processNodes != null &&
                    processNodes.size() >0;
        }
        return false;
    }
    
    @Override
    public void dispose() {
        root = null;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        if(inputElement instanceof ProcessNode) {
            return new Object[] {inputElement};
        }else {
            return getChildren(inputElement);
        }
    }

}
