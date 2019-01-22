/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ui.components.FaultsControl;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Properties Section for Method's faults.
 *
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class FaultsSection extends AbstractRsdSection {

    protected Composite groupControl;

    protected FaultsControl faultsControl;

    protected String tableLabel;

    public FaultsSection() {
        setMinimumHeight(110);
        setShouldUseExtraSpace(true);
        tableLabel = Messages.FaultsSection_faults_label;
    }

    /**
     * Creates a faults control which could be configured and tailored to the
     * context.
     * 
     * @param parent
     *            parent composite.
     * @param toolkit
     *            XPD toolkit.
     * @return A faults control which could be configured and tailored to the
     *         context.
     */
    protected FaultsControl createViewerControl(Composite parent,
            XpdFormToolkit toolkit) {
        return new FaultsControl(parent, toolkit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Method && isValidControl(faultsControl)
                && isValidControl(faultsControl.getViewer().getControl())) {
            Method method = (Method) input;
            faultsControl.getViewer().cancelEditing();
            if (method != faultsControl.getViewer().getInput()) {
                faultsControl.getViewer().setInput(method);
            } else {
                faultsControl.getViewer().refresh();
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayoutFactory.swtDefaults().applyTo(root);

        groupControl = toolkit.createComposite(root);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
                .grab(true, true).applyTo(groupControl);
        getGroupControlLayoutFactory().numColumns(2).applyTo(groupControl);

        Label label = createLabel(groupControl, toolkit, tableLabel);
        GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).applyTo(label);
        faultsControl = createViewerControl(groupControl, toolkit);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
                .grab(true, true).hint(30, 30).applyTo(faultsControl);
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // Table handles this
        return null;
    }

}
