/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processinterface.properties;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class ErrorMethodPropertySection extends
        AbstractFilteredTransactionalSection {

    private Text txtErrorCode;

    /**
     * @param class1
     */
    public ErrorMethodPropertySection() {
        super(XpdExtensionPackage.eINSTANCE.getErrorMethod());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite errorPropComposite = toolkit.createComposite(parent);
        errorPropComposite.setLayout(new GridLayout(2, false));
        createLabel(errorPropComposite,
                toolkit,
                Messages.ErrorMethodPropertySection_ErrorCodeLabel_label);
        txtErrorCode = toolkit.createText(errorPropComposite, "", null); //$NON-NLS-1$
        txtErrorCode.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(txtErrorCode);

        return errorPropComposite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        if (getInput() instanceof ErrorMethod) {
            ErrorMethod errorMethod = (ErrorMethod) getInput();
            if (obj == txtErrorCode) {
                return SetCommand.create(getEditingDomain(),
                        errorMethod,
                        XpdExtensionPackage.eINSTANCE
                                .getErrorMethod_ErrorCode(),
                        txtErrorCode.getText());
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (getInput() instanceof ErrorMethod) {
            ErrorMethod errorMethod = (ErrorMethod) getInput();
            if (!txtErrorCode.isDisposed()) {
                updateText(txtErrorCode, errorMethod.getErrorCode());
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        EObject element = getInput();
        if (element instanceof ErrorMethod && element.eContainer() == null) {
            ErrorMethod errorMethod = (ErrorMethod) element;
            String baseName = errorMethod.getErrorCode();
            String finalName = baseName;

            EObject container = getInputContainer();
            if (container instanceof InterfaceMethod) {
                int idx = 1;
                while (Xpdl2ModelUtil.getDuplicateError(container,
                        errorMethod,
                        finalName) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                errorMethod.setErrorCode(NameUtil.getInternalName(finalName,
                        false));
            }
        }
    }
}
