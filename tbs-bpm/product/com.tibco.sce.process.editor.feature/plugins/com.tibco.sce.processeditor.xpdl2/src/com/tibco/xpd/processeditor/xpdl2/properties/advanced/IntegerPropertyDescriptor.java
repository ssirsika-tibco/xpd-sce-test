/**
 * IntegerPropertyDescriptor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tibco.xpd.ui.properties.DigitTextVerifyListener;

/**
 * IntegerPropertyDescriptor
 * 
 */
public class IntegerPropertyDescriptor extends TextPropertyDescriptor {

    public IntegerPropertyDescriptor(Object id, String displayName) {
        super(id, displayName);

    }

    @Override
    public CellEditor createPropertyEditor(Composite parent) {
        //
        // Afdter creating text control add our own verify listener for numeric
        // digits.
        TextCellEditor cell = new TextCellEditor(parent) {

            @Override
            protected Control createControl(Composite parent) {
                Control ctrl = super.createControl(parent);

                if (ctrl instanceof Text) {
                    Text text = (Text) ctrl;

                    text.addVerifyListener(new DigitTextVerifyListener());
                }

                return ctrl;
            }

            @Override
            protected void doSetValue(Object value) {
                //
                // Convert from owner's Integer value to text cell editor's
                // expected string.
                if (value instanceof Integer) {
                    super.doSetValue(String.valueOf(((Integer) value)
                            .intValue()));
                } else {
                    super.doSetValue(""); //$NON-NLS-1$
                }

            }

            @Override
            protected Object doGetValue() {
                //
                // Convert from text text editor String to expected Integer
                // output value.
                Object o = super.doGetValue();
                if (o instanceof String) {
                    try {
                        int value = Integer.parseInt((String) o);
                        return new Integer(value);
                    } catch (Exception e) {
                    }
                }
                return null;
            }
        };

        return cell;
    }
}
