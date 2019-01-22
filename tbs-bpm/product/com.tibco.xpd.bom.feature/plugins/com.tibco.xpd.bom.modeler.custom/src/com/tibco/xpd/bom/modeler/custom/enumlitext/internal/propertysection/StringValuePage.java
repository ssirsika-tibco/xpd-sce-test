/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.propertysection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Enumeration Literal section page to set a string value.
 * 
 * @author njpatel
 */
/* public */class StringValuePage extends ValuePage {

    private Text singleValueTxt;

    public StringValuePage(XpdFormToolkit toolkit, EditingDomain ed) {
        super(toolkit, ed);
    }

    @Override
    protected Composite doCreateControl(Composite parent) {
        Composite root = getToolkit().createComposite(parent);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        singleValueTxt = getToolkit().createText(root, ""); //$NON-NLS-1$
        singleValueTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));

        manageControl(singleValueTxt);

        return root;
    }

    @Override
    public void setFocus() {
        if (singleValueTxt != null && !singleValueTxt.isDisposed()) {
            singleValueTxt.setFocus();
        }
    }

    @Override
    protected void doRefresh(EnumerationLiteral input) {
        DomainValue value = EnumLitValueUtil.getDomainValue(input);
        if (singleValueTxt != null && !singleValueTxt.isDisposed()) {
            if (value instanceof SingleValue) {
                singleValueTxt.setText(value.getValue());
            } else {
                singleValueTxt.setText(""); //$NON-NLS-1$
            }
        }
    }

    @Override
    protected Command doGetCommand(Widget widget, EnumerationLiteral input) {
        Command cmd = null;

        // If value has changed then update the model
        boolean valueChanged = true;
        DomainValue value = EnumLitValueUtil.getDomainValue(input);
        if (value instanceof SingleValue) {
            String currValue = value.getValue();
            if (singleValueTxt.getText().equals(currValue)) {
                valueChanged = false;
            }
        }

        if (valueChanged) {
            cmd =
                    EnumLitValueUtil
                            .getSetSingleValueCommand((TransactionalEditingDomain) getEditingDomain(),
                                    input,
                                    singleValueTxt.getText());
        }

        return cmd;
    }

}
