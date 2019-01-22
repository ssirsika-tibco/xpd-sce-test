package com.tibco.bx.emulation.ui.properties;

import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class NameColumnLabelProvider extends CellLabelProvider{

	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		if (element instanceof IVariableElement) {
			IVariableElement var = (IVariableElement) element;
			cell.setImage(WorkingCopyUtil.getImage(var.getEMFCharacter()));
			String label = var.getName();
			IVariableElement parent = var.getParent();
			if(parent instanceof IVariableElementList){
				int index= ((IVariableElementList)parent).getIndex(var);
				label = "[" + index + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			cell.setText(label);
			if(!var.isValid()){
				cell.setForeground(JFaceColors.getErrorText(cell.getControl().getDisplay()));
			}else{
				cell.setForeground(JFaceColors.getBannerForeground(cell.getControl().getDisplay()));
			}
		}
	}
	
}
