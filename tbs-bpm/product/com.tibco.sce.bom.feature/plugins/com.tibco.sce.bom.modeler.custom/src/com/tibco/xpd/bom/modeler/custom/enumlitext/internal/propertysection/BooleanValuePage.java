/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.propertysection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Enumeration Literal section page to set a boolean value.
 * 
 * @author njpatel
 */
/* public */class BooleanValuePage extends ValuePage {

    private Button trueBtn;

    private Button falseBtn;

    public BooleanValuePage(XpdFormToolkit toolkit, EditingDomain ed) {
        super(toolkit, ed);
    }

    @Override
    protected Composite doCreateControl(Composite parent) {
        Composite root = getToolkit().createComposite(parent);
        RowLayout layout = new RowLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);
        trueBtn =
                getToolkit().createButton(root,
                        Messages.BooleanValuePage_true_button,
                        SWT.RADIO);
        falseBtn =
                getToolkit().createButton(root,
                        Messages.BooleanValuePage_false_button,
                        SWT.RADIO);

        manageControl(trueBtn);
        manageControl(falseBtn);

        return root;
    }

    @Override
    protected Command doGetCommand(Widget widget, EnumerationLiteral input) {
        Command cmd = null;

        if (trueBtn != null && !trueBtn.isDisposed()) {
            cmd =
                    EnumLitValueUtil
                            .getSetSingleValueCommand((TransactionalEditingDomain) getEditingDomain(),
                                    input,
                                    Boolean.toString(trueBtn.getSelection()));
        }

        return cmd;
    }

    @Override
    public void setFocus() {
        if (trueBtn != null && !trueBtn.isDisposed()) {
            trueBtn.setFocus();
        }
    }

    @Override
    protected void doRefresh(EnumerationLiteral input) {
        if (input != null && trueBtn != null && !trueBtn.isDisposed()) {
            DomainValue value = EnumLitValueUtil.getDomainValue(input);
            if (value instanceof SingleValue) {
                String booleanValue = value.getValue();
                if (booleanValue != null) {
                    boolean bool = Boolean.parseBoolean(booleanValue);
                    trueBtn.setSelection(bool);
                    falseBtn.setSelection(!bool);
                }
            }
        }
    }

}
