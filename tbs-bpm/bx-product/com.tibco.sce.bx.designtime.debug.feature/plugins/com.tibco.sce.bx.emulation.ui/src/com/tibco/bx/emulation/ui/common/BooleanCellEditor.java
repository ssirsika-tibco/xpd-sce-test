package com.tibco.bx.emulation.ui.common;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;

public class BooleanCellEditor extends ComboBoxCellEditor {

	private static final String[] LABELS = { "true", "false" }; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String[] VALUES = { "true", "false" }; //$NON-NLS-1$ //$NON-NLS-2$
	public BooleanCellEditor(Composite parent){
		super(parent, LABELS);
	}
	protected Object doGetValue() {
		Object val = super.doGetValue();

		if (!(val instanceof Integer))
			return "";//$NON-NLS-1$

		Integer iVal = (Integer) val;

		if ((iVal.intValue() < 0) || (iVal.intValue() >= VALUES.length))
			return "";//$NON-NLS-1$

		return VALUES[iVal.intValue()];
	}

	protected void doSetValue(Object value) {
		if (value == null) {
			super.doSetValue(Integer.valueOf(-1));
		} else if (VALUES[0].equalsIgnoreCase(value.toString())) {
			super.doSetValue(Integer.valueOf(0));
		} else if (VALUES[1].equalsIgnoreCase(value.toString())) {
			super.doSetValue(Integer.valueOf(1));
		} else {
			super.doSetValue(Integer.valueOf(-1));
		}
	}
}
