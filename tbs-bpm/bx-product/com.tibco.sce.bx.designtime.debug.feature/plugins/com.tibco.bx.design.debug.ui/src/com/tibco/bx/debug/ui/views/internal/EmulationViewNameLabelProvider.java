package com.tibco.bx.debug.ui.views.internal;

import java.util.Map;

import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.emulation.core.invoke.EmulationRunStatus;
import com.tibco.bx.emulation.core.invoke.EmulationRunner;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;

public class EmulationViewNameLabelProvider extends StyledCellLabelProvider
        implements IStyledLabelProvider {

    Map<ProcessNode,EmulationRunner> willRunning;
    private Display display;
    private Color waitingForRunColor; 
    private Color failedColor;
    private Color successfulColor;
    private Color defaultColor;
    CheckboxTreeViewer emulationViewer;
    
    public EmulationViewNameLabelProvider(Display display ,  CheckboxTreeViewer emulationViewer) {
        super();
        this.display = display;
        this.emulationViewer = emulationViewer;
        
        this.waitingForRunColor =  this.display.getSystemColor(SWT.COLOR_GRAY );
        this.failedColor = this.display.getSystemColor(SWT.COLOR_RED);
        this.successfulColor =  this.display.getSystemColor(SWT.COLOR_GREEN);
        defaultColor = this.display.getSystemColor(SWT.COLOR_WHITE);
    }

    @Override
    public StyledString getStyledText(Object element) {
        StyledString styleString = new StyledString();
        if(element instanceof File) {
            styleString.append(((File)element).getFullPath().toString());
        }else if(element instanceof ProcessNode) {
            styleString.append(((ProcessNode)element).getName());
            styleString.append(" (").append(((ProcessNode)element).getAlias()).append(")"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return styleString;
    }

    @Override
    public Image getImage(Object element) {
    	if(element instanceof File){
    		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_EMULATION);
    	}
        return null;
    }

    private EmulationRunner getRunner(Object node) {
        if(willRunning == null) {
            return null;
        }
        return willRunning.get(node);
    }
    
    @Override
    public void update(ViewerCell cell) {
        Object node = cell.getElement();
        Color statusColor = defaultColor;
        Image statusIcon = null;
        if(node instanceof ProcessNode) {
        	String cellText = ((ProcessNode)node).getName();
        	if(StringUtils.isNotBlank(((ProcessNode)node).getAlias())){
        		cellText += " (" + ((ProcessNode)node).getAlias() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        	}
        	cell.setText(cellText);
        	
            EmulationRunner runner = getRunner( node);
            if(isRunning(runner) || 
               isWaitingForRun((ProcessNode)node , runner)) {
                statusColor = this.waitingForRunColor;
                statusIcon = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_NODE);
            }else if(isFailed(runner)) {
                statusColor = this.failedColor;
                statusIcon = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_FAULT);
            } else if(isSuccessed(runner)) {
               statusColor = this.successfulColor;
               statusIcon = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_COMPLETED);
            }else if(isTerminated(runner)){
            	 statusIcon = DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_TERMINATED);
            }else{
            	statusIcon = EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_PROCESSNODE);
            }
            cell.setImage(statusIcon);
            
        }else if(node instanceof File) {
            cell.setText(((File)node).getFullPath().toString());
            cell.setImage(EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_EMULATION));
        }
        super.update(cell);
    }

    private boolean isTerminated(EmulationRunner runner) {

    	 if(runner != null && runner.getStatus() == EmulationRunStatus.TERMINATED ) {
             return true;
         }
         return false;
	}

	private boolean isSuccessed(EmulationRunner runner) {
        if(runner != null && runner.getStatus() == EmulationRunStatus.COMPLETED ) {
            return true;
        }
         return false;
    }

    private boolean isFailed(EmulationRunner runner) {
        if(runner != null && runner.getStatus() == EmulationRunStatus.FAULT ) {
            return true;
        }
         return false;
    }

 /*   private boolean isWaitingForRun(EmulationRunner runner) {
       if(runner != null && runner.getStatus() == EmulationRunStatus.WAITING ) {
           return true;
       }
        return false;
    }*/
    
    private boolean isWaitingForRun(ProcessNode node , EmulationRunner runner) {
        
        if(runner != null) {
            return runner.getStatus() == EmulationRunStatus.WAITING;
        }
        
        Object[] checkedItem = emulationViewer.getCheckedElements();
        for(Object item : checkedItem) {
            if(node == item) {
                return true;
            }
        }
        return false;
    }

    private boolean isRunning(EmulationRunner runner) {
        if(runner != null && runner.getStatus() == EmulationRunStatus.RUNNING ) {
            return true;
        }
         return false;
    }

    public void setNodeWillRun(Map<ProcessNode, EmulationRunner> nodes) {
        willRunning = nodes;
    }
    

}
