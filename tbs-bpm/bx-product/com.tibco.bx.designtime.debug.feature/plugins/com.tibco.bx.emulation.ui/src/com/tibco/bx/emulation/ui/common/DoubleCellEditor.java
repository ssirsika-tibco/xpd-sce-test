package com.tibco.bx.emulation.ui.common;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * This cell editor ensures that only double format values are supported
 */
public class DoubleCellEditor extends TextCellEditor {
	public DoubleCellEditor(Composite composite) {
		super(composite);
		setValidator(new ICellEditorValidator() {
			public String isValid(Object object) {
				if (object instanceof Double) {
					return null;
				} else {
					String string = (String) object;
					try {
						Double.parseDouble(string);
						return null;
					} catch (NumberFormatException exception) {
						return exception.getMessage();
					}
				}
			}
		});
	}

	@Override
	public Object doGetValue() {
		return new Double(Double.parseDouble((String) super.doGetValue()));
	}

	@Override
	public void doSetValue(Object value) {
		super.doSetValue(value.toString());
	}
}
