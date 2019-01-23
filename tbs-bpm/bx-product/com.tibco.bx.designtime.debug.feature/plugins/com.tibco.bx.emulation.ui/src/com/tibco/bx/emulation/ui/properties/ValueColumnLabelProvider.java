package com.tibco.bx.emulation.ui.properties;

import java.util.Date;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import com.tibco.bx.debug.core.util.DateUtil;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.xpd.xpdl2.BasicTypeType;

public class ValueColumnLabelProvider extends CellLabelProvider{

	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		if(element instanceof IVariableElementList){
				cell.setText(""); //$NON-NLS-1$
		}else if (element instanceof IVariableElement) {
			IVariableElement var = (IVariableElement) element;
			Object type = var.getType();
			Object value = var.getValue();
			if(value != null && type instanceof BasicTypeType){
				int typeValue = ((BasicTypeType)type).getValue();
				if(typeValue == BasicTypeType.TIME){
					cell.setText(DateUtil.formatLocaleTime((Date)value));
				}
				else if(typeValue == BasicTypeType.DATETIME)
					cell.setText(DateUtil.formatLocaleDateTime((Date)value));
				else if(typeValue == BasicTypeType.DATE)
					cell.setText(DateUtil.formatLocaleDate((Date)value));
				else
					cell.setText(var.getValueString());
			}else if(value != null){
				cell.setText(var.getValueString());
			}else if(value == null){
				cell.setText(""); //$NON-NLS-1$
			}
		}
	}
}
