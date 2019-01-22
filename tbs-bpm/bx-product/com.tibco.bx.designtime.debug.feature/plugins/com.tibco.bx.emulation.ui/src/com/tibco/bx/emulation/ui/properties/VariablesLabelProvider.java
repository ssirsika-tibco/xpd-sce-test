package com.tibco.bx.emulation.ui.properties;

import java.util.Date;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.debug.core.util.DateUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.BasicTypeType;

public class VariablesLabelProvider extends LabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof IVariableElement) {
			IVariableElement var = (IVariableElement) element;
			if (columnIndex == 0)
				return WorkingCopyUtil.getImage(var.getEMFCharacter());
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof IVariableElement) {
			IVariableElement var = (IVariableElement) element;
			switch (columnIndex) {
			case 0:
				return var.getName();
			case 1:
				//TODO
				return var.getType().toString();
			case 2:
				Object type = var.getType();
				if(type instanceof BasicTypeType){
					int typeValue = ((BasicTypeType)type).getValue();
					Object value = var.getValue();
					if(typeValue == BasicTypeType.TIME)
						return DateUtil.formatLocaleTime((Date)value);
					else if(typeValue == BasicTypeType.DATETIME)
						return DateUtil.formatLocaleDateTime((Date)value);
					else if(typeValue == BasicTypeType.DATE)
						return DateUtil.formatLocaleDate((Date)value);
				}
				return var.getValueString();
			default:
				break;
			}
		}
		return null;
	}

}
