package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.emulation.ui.EmulationUIActivator;

public class EmulationViewLabelProvider extends BaseLabelProvider implements ILabelProvider{

	@Override
	public Image getImage(Object element) {
		if(element instanceof IFile){
    		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_EMULATION);
    	}else{
    		return EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_PROCESSNODE);
    	}
	}

	@Override
	public String getText(Object element) {
		if(element instanceof IFile){
    		return ((IFile) element).getFullPath().toString();
    	}
		return null;
	}
	

}
