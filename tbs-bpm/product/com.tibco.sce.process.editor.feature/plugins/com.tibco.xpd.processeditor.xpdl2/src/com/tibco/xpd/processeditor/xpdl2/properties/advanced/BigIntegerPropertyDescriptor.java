/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import java.math.BigInteger;

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
 * This class is copied from {@link IntegerPropertyDescriptor}
 * 
 * @author bharge
 * @since 13 Jun 2012
 */
public class BigIntegerPropertyDescriptor extends TextPropertyDescriptor {

    /**
     * @param id
     * @param displayName
     */
    public BigIntegerPropertyDescriptor(Object id, String displayName) {
        super(id, displayName);
    }

    /**
     * @see org.eclipse.ui.views.properties.TextPropertyDescriptor#createPropertyEditor(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    public CellEditor createPropertyEditor(Composite parent) {

        /*
         * After creating text control add our own verify listener for numeric
         * digits.
         */
        TextCellEditor cell = new TextCellEditor(parent) {

            @Override
            protected Control createControl(Composite parent) {
                Control ctrl = super.createControl(parent);

                if (ctrl instanceof Text) {
                    Text text = (Text) ctrl;

                    text.addVerifyListener(new VerifyListener() {

                        @Override
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
                /*
                 * Convert from owner's BigInteger value to text cell editor's
                 * expected string.
                 */
                if (value instanceof BigInteger) {
                    super.doSetValue(String.valueOf(value));
                } else {
                    super.doSetValue(""); //$NON-NLS-1$
                }

            }

            @Override
            protected Object doGetValue() {
                /*
                 * Convert from text editor String to expected BigInteger output
                 * value.
                 */
                Object o = super.doGetValue();
                if (o instanceof String && ((String) o).length() > 0) {
                    try {
                        return new BigInteger((String) o);
                    } catch (Exception e) {
                    }
                }
                return null;
            }
        };

        return cell;
    }
}
