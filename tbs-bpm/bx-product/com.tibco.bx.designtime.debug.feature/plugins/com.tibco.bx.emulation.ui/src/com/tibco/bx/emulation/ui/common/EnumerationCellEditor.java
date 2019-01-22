package com.tibco.bx.emulation.ui.common;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;

public class EnumerationCellEditor extends ComboBoxCellEditor {

	private String[] names;
	public EnumerationCellEditor(Composite parent, String[] labels){
		super();
		this.names = labels;
		setItems(this.names);
		create(parent);
	}

	protected Object doGetValue() {
		Object val = super.doGetValue();
		if (!(val instanceof Integer))
			return null;
		Integer iVal = (Integer) val;

		if ((iVal.intValue() < 0) || (iVal.intValue() >= names.length))
			return null;

		return names[iVal.intValue()];
	}

	protected void doSetValue(Object value) {
		if (value == null) {
			super.doSetValue(Integer.valueOf(-1));
			return;
		}
		for (int i = 0; i < names.length; i++) {
			if(value.equals(names[i])){
				super.doSetValue(Integer.valueOf(i));
				return;
			}
		}
		super.doSetValue(Integer.valueOf(-1));
	}
}
