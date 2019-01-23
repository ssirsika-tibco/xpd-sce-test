/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import java.math.BigDecimal;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * BigDecimalPropertyDescriptor
 * 
 * 
 * @author bharge
 * @since 3.3 (6 Mar 2010)
 */
public class BigDecimalPropertyDescriptor extends TextPropertyDescriptor {

    /**
     * @param id
     * @param displayName
     */
    public BigDecimalPropertyDescriptor(Object id, String displayName) {
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

                    text.addVerifyListener(new VerifyListener() {

                        public void verifyText(VerifyEvent e) {
                            if (Character.isDigit(e.character)
                                    || e.keyCode == SWT.DEL
                                    || e.keyCode == SWT.BS || e.character == 0) {
                                e.doit = true;
                            } else {
                                e.doit = false;
                            }

                        }
                    });
                }

                return ctrl;
            }

            @Override
            protected void doSetValue(Object value) {
                // 
                // Convert from owner's Integer/BigDecimal value to text cell
                // editor's
                // expected string.
                if (value instanceof BigDecimal) {
                    super.doSetValue(String.valueOf(((BigDecimal) value)
                            .intValue()));
                } else if (value instanceof Integer) {
                    super.doSetValue(String.valueOf(new BigDecimal(
                            ((Integer) value).intValue())));
                } else if (value instanceof String) {

                    super.doSetValue(""); //$NON-NLS-1$?
                    // super.doSetValue(value);
                }

            }

            @Override
            protected Object doGetValue() {
                //
                // Convert from text text editor String to expected BigDecimal
                // output value.
                Object o = super.doGetValue();
                if (o instanceof String) {
                    try {
                        return new BigDecimal((String) o);

                    } catch (Exception e) {
                    }
                }
                return null;
            }
        };

        return cell;
    }

}
