package com.tibco.bx.emulation.ui.tooltip;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.BasicTypeType;

public class VariableToolTipLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof IVariableElement) {
			IVariableElement var = (IVariableElement) element;
			Object type = var.getType();
			if(type instanceof EObject)
				return WorkingCopyUtil.getImage((EObject)type);
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IVariableElement) {
			Object type = ((IVariableElement)element).getType();
			String typeName = null;
			if(type instanceof BasicTypeType){
				typeName = ((BasicTypeType)type).getName();
			}else if(type instanceof Type){
				typeName = ((Type)type).getName();
			}
			return typeName+ ((element instanceof IVariableElementList)?"{}":""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return null;
	}
}
