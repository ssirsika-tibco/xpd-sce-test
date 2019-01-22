package com.tibco.bx.emulation.ui.properties;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.TypeDeclaration;

public class TypeColumnLabelProvider extends CellLabelProvider{

	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		if (element instanceof IVariableElement) {
			IVariableElement var = (IVariableElement) element;
			Object type = var.getType();
			String typeName = null;
			if(type instanceof BasicTypeType){
				typeName = ((BasicTypeType)type).getName();
			}else if(type instanceof Type){
				typeName = ((Type)type).getName();
			}else if(type instanceof TypeDeclaration){
				typeName = ((TypeDeclaration)type).getName();
			}
			cell.setText(typeName+ ((var instanceof IVariableElementList)?"{}":"")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
}