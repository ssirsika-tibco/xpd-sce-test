/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rsd.NamedElement;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Named Element section. Contains name text control.
 *
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class NamedElementSection extends AbstractRsdSection {

    private Composite nameControls;

    private Text nameTxt;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     *
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        NamedElement elem = (NamedElement) input;
        if (input instanceof NamedElement && nameTxt != null
                && !nameTxt.isDisposed()) {
            updateText(nameTxt, elem.getName());
        }

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
        Composite root = toolkit.createComposite(parent);
        GridLayoutFactory.swtDefaults().applyTo(root);

        nameControls = toolkit.createComposite(root);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                .grab(true, false).applyTo(nameControls);
        getGroupControlLayoutFactory().numColumns(2).applyTo(nameControls);

        createLabel(nameControls, toolkit, Messages.NamedElementSection_Name_label);
        nameTxt = toolkit.createText(nameControls, "", "namedElement-name"); //$NON-NLS-1$ //$NON-NLS-2$

        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT).applyTo(nameTxt);

        manageControlUpdateOnDeactivate(nameTxt);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     *
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        EObject input = getInput();
        Command cmd = null;
        if (input instanceof NamedElement && obj instanceof Text) {
            String value = ((Text) obj).getText();
            if (value.length() > 0) {
                if (obj == nameTxt) {
                    if (!value.equals(((NamedElement) input).getName())) {
                        // Update the name
                        cmd =
                                SetCommand.create(getEditingDomain(),
                                        input,
                                        RsdPackage.eINSTANCE
                                                .getNamedElement_Name(),
                                        value);
                    }
                }
            } else {
                // Value not changed as it was blanked so restore previous value
                doRefresh();
            }
        }
        return cmd;
    }
}
