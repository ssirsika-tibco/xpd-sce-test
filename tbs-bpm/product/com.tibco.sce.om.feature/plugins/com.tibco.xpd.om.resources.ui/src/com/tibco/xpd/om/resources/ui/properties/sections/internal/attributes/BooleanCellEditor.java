/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Boolean cell editor that shows a drop down control with the values "",
 * "false" and "true".
 * 
 * @author njpatel
 * 
 */
/* public */class BooleanCellEditor extends CellEditor {

    private CCombo cmb;
    private int currentSelection;

    public BooleanCellEditor(Composite parent) {
        super(parent);
    }

    @Override
    protected Control createControl(Composite parent) {
        cmb = new CCombo(parent, SWT.NONE);
        cmb.add(""); //$NON-NLS-1$
        cmb.add(Boolean.FALSE.toString());
        cmb.add(Boolean.TRUE.toString());

        cmb.addKeyListener(new KeyAdapter() {
            // hook key pressed - see PR 14201
            public void keyPressed(KeyEvent e) {
                keyReleaseOccured(e);
            }
        });

        cmb.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int newSelection = cmb.getSelectionIndex();

                if (newSelection != currentSelection) {
                    currentSelection = newSelection;
                    markDirty();
                }
            }
        });

        cmb.addTraverseListener(new TraverseListener() {
            public void keyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_ESCAPE
                        || e.detail == SWT.TRAVERSE_RETURN) {
                    e.doit = false;
                }
            }
        });
        currentSelection = 0;
        cmb.select(currentSelection);

        return cmb;
    }

    @Override
    protected Object doGetValue() {
        switch (cmb.getSelectionIndex()) {
        case 1:
            return Boolean.FALSE.toString();
        case 2:
            return Boolean.TRUE.toString();
        }
        return ""; //$NON-NLS-1$
    }

    @Override
    protected void doSetFocus() {
        if (cmb != null && !cmb.isDisposed()) {
            cmb.setFocus();
        }
    }

    @Override
    protected void doSetValue(Object value) {
        String strVal = value.toString();

        if (strVal.length() > 0) {
            boolean bool = Boolean.parseBoolean(strVal);
            cmb.select(bool ? 2 : 1);
        } else {
            cmb.select(0);
        }
    }

}
