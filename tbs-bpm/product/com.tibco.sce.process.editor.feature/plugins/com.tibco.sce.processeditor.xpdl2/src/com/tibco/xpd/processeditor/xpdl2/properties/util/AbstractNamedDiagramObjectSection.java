/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.util;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * Base class for any process diagram object with a name (i.e. that is a named
 * element.
 * 
 * @author aallway
 * 
 */
public abstract class AbstractNamedDiagramObjectSection extends
        AbstractFilteredTransactionalSection {

    protected String instrumentationPrefixName = ""; //$NON-NLS-1$

    public AbstractNamedDiagramObjectSection(EClass selectionFilterClass) {
        super(selectionFilterClass);
    }

    /**
     * Return the object type descriptor (such as 'Task', Gateway etc)
     * 
     * @return
     */
    protected abstract String objectTypeGetDescriptor();

    /**
     * Allow sub-class to create the object type specific controls.
     * <p>
     * <b>Note: The provided parent has a grid layout with 2 columns so you must
     * add appropriate children with appropriate grid data)</b>
     * 
     * 
     * @param parent
     *            Container of the activity type specific controls.
     */
    protected abstract void objectTypeCreateControls(Composite parent,
            XpdFormToolkit toolkit);

    /**
     * Allow sub-class to return commands for its object type specific controls.
     * 
     * @param obj
     *            Managed control object that command was requested for
     * 
     * @return Command to execute or null if there is none.
     */
    protected abstract Command objectTypeGetCommand(Object obj);

    /**
     * Allow sub-class to refresh its controls from model changes.
     */
    protected abstract void objectTypeRefresh();

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
        root.setLayout(new GridLayout(2, false));

        objectTypeCreateControls(root, toolkit);

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
        EditingDomain editingDomain = getEditingDomain();
        NamedElement namedElement = getNamedElement();

        if (editingDomain != null && namedElement != null) {

            // Handle changes to name.
            //
            // Get object type controls specific command.
            return objectTypeGetCommand(obj);
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        NamedElement namedElement = getNamedElement();

        if (namedElement != null) {
            objectTypeRefresh();
        }

        return;
    }

    /**
     * Return the input as a NamedElement xpdl2 object.
     * 
     * @return NamedElement or null on error
     */
    public NamedElement getNamedElement() {
        NamedElement namedElement = null;

        if (getInput() instanceof NamedElement) {
            namedElement = (NamedElement) getInput();
        }
        return namedElement;
    }

    public void setInstrumentationPrefixName(String instrumentationPrefixName) {
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

}
