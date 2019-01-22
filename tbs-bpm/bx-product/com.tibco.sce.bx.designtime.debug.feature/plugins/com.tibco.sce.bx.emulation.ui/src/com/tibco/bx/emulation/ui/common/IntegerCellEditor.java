package com.tibco.bx.emulation.ui.common;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

 /**
 * This cell editor ensures that only Integer format values are supported
 */
 public class IntegerCellEditor extends TextCellEditor {
	public IntegerCellEditor(Composite composite) {
		super(composite);
		setValidator(new ICellEditorValidator() {
			public String isValid(Object object) {
				if (object instanceof Integer)
					return object.toString();
				else {
					String string = (String) object;
					try {
						Integer.parseInt(string);
						return null;
					} catch (NumberFormatException exception) {
						return exception.getMessage();
					}
				}
			}
		});
	}

	@Override
    public Object doGetValue()
    {
      return new Integer(Integer.parseInt((String)super.doGetValue()));
    }

    @Override
    public void doSetValue(Object value)
    {
      super.doSetValue(value.toString());
    }
	
	
}
