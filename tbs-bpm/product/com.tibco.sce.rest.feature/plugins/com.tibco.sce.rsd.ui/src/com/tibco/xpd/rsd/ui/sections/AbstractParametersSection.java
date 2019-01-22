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

import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.ui.components.ParamsControl;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Abstract section for for parameters of a {@link ParameterContainer}.
 * Subclasses should create a customised parameters control by providing
 * {@link #createParamsControl(Composite, XpdFormToolkit)}.
 * <p>
 * You can also override the default table label by setting {@link #tableLabel}
 * in the constructor.
 * </p>
 *
 * @author jarciuch
 * @since 2 Feb 2015
 */
public abstract class AbstractParametersSection extends AbstractRsdSection {

    protected Composite groupControl;

    protected ParamsControl paramsControl;

    /**
     * The table label if it should be shown. This attribute should be set in
     * the constructor of an implementing class (used in
     * {@link #doCreateControls(Composite, XpdFormToolkit)})
     */
    private String tableLabel = null;

    public AbstractParametersSection() {
        setMinimumHeight(110);
        setShouldUseExtraSpace(true);
    }

    /**
     * Creates a parameter control which will be configured and tailored to the
     * context.
     * 
     * @param parent
     *            parent composite.
     * @param toolkit
     *            XPD toolkit.
     * @return A parameter control which will be configured and tailored to the
     *         context.
     */
    protected abstract ParamsControl createParamsControl(Composite parent,
            XpdFormToolkit toolkit);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof ParameterContainer
                && isValidControl(paramsControl)
                && isValidControl(paramsControl.getViewer().getControl())) {
            ParameterContainer paramsContainer = (ParameterContainer) input;
            paramsControl.getViewer().cancelEditing();
            if (paramsContainer != paramsControl.getViewer().getInput()) {
                paramsControl.getViewer().setInput(paramsContainer);
            } else {
                paramsControl.getViewer().refresh();
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

        if (getTableLabel() != null) {
            getGroupControlLayoutFactory().numColumns(2).applyTo(groupControl);
            Label label = createLabel(groupControl, toolkit, tableLabel);
            GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP)
                    .applyTo(label);
        } else {
            getGroupControlLayoutFactory().numColumns(1).applyTo(groupControl);
        }
        paramsControl = createParamsControl(groupControl, toolkit);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
                .grab(true, true).hint(30, 30).applyTo(paramsControl);
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

    /**
     * Sets the table label.
     * 
     * @param tableLabel
     *            the table Label.
     */
    public void setTableLabel(String tableLabel) {
        this.tableLabel = tableLabel;
    }

    /**
     * Retrieves the table label.
     * 
     * @return the table Label or <code>null</code> if no table label should be
     *         set.
     */
    public String getTableLabel() {
        return tableLabel;
    }

}
