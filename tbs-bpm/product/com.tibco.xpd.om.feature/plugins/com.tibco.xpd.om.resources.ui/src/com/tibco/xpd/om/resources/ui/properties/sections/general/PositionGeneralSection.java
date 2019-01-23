/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * General properties section for items specific to a {@link Position} object.
 * 
 * @author njpatel
 * 
 */
public class PositionGeneralSection extends AbstractGeneralSection implements
        IFilter {

    private Text numberTxt;

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        if (obj instanceof Text) {
            EObject input = getInput();
            Text text = (Text) obj;
            Object data = text.getData(XpdFormToolkit.FEATURE_DATA);

            if (data != null && input instanceof Position) {
                int intValue = 0;
                String value = text.getText();
                if (value.length() > 0) {
                    try {
                        intValue = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        intValue = 0;
                    }
                }

                cmd = SetCommand.create(getEditingDomain(), input, data,
                        intValue);
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Position && numberTxt != null
                && !numberTxt.isDisposed()) {
            int number = ((Position) input).getIdealNumber();
            numberTxt.setText(String.valueOf(number));
        }
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.PositionGeneralSection_number_label);
        numberTxt = toolkit.createText(root, getInput(), OMPackage.eINSTANCE
                .getPosition_IdealNumber(), "position-number"); //$NON-NLS-1$
        setLayoutData(numberTxt);
        numberTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char ch = e.character;
                // Only allow numeric characters in this control
                if ((ch >= '0' && ch <= '9') || ch == SWT.DEL || ch == SWT.BS
                        || e.keyCode == SWT.ARROW_LEFT
                        || e.keyCode == SWT.ARROW_RIGHT
                        || e.keyCode == SWT.HOME || e.keyCode == SWT.END) {
                    e.doit = true;
                } else {
                    e.doit = false;
                }
            }
        });
        manageControlUpdateOnDeactivate(numberTxt);

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        return resollveInput(toTest) instanceof Position;
    }
}
