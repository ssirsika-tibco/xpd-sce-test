package com.tibco.bx.emulation.ui.common;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.tibco.bx.emulation.core.common.IEnumerationVariableElement;
import com.tibco.bx.emulation.core.common.IPrimitiveVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.bx.emulation.ui.properties.ListCellEditor;
import com.tibco.xpd.resources.ui.components.calendar.DateType;

public class CellEditorFactory {

	private static CellEditorFactory instance = new CellEditorFactory();
	
	public static CellEditorFactory getInstance(){
		return instance;
	}
	
	public CellEditor createCellEditor(Composite parent, IVariableElement element){
		CellEditor editor = null;
		if(element instanceof IVariableElementList){
			editor = new ListCellEditor(parent, (IVariableElementList)element);
		}else if(element instanceof IEnumerationVariableElement){
			editor = new EnumerationCellEditor(parent,((IEnumerationVariableElement)element).getEnumNames());
		}else if(element instanceof IPrimitiveVariableElement){
			IPrimitiveVariableElement.PrimitiveType type = ((IPrimitiveVariableElement)element).getPrimitiveType();
			if(type==IPrimitiveVariableElement.PrimitiveType.BOOLEAN) {
				editor = new BooleanCellEditor(parent);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.DECIMAL) {
				editor = new DoubleCellEditor(parent);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.INTEGER) {
				editor = new IntegerCellEditor(parent);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.TEXT) {
				editor = new TextCellEditor(parent);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.DATE) {
				editor = new DateTimeCellEditor(parent, DateType.DATE);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.DATETIME) {
				editor = new DateTimeCellEditor(parent, DateType.DATETIME);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.TIME) {
				editor = new DateTimeCellEditor(parent, DateType.TIME);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.DATETIMETZ) {
				editor = new DateTimeCellEditor(parent, DateType.DATETIMETZ);
			}else if(type==IPrimitiveVariableElement.PrimitiveType.DURATION){
				editor = new DurationCellEditor(parent);
			}
		}
		return editor;
	}
}
