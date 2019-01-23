/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.resources.ui.properties.sections.general.NamedElementSection;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AttributeSection;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section for the Qualifier tab for {@link QualifiedOrgElement} objects.
 * 
 * @author njpatel
 * 
 */
public class QualifierSection extends AbstractTransactionalSection implements
        IFilter {

    public static final String QUALIFIER_TAB_ID =
            "com.tibco.xpd.om.propertyTabs.qualifier"; //$NON-NLS-1$

    private final NamedElementSection nameSection;

    private final AttributeSection attrSection;

    private Attribute input;

    public QualifierSection() {
        /*
         * This section will be made up of the named element and the attribute
         * section as this, in effect, is an attribute.
         */
        nameSection = new NamedElementSection();
        attrSection = new AttributeSection();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(new GridLayout());

        Control content = nameSection.createControls(root, toolkit);
        setLayoutData(nameSection, content);

        content = attrSection.createControls(root, toolkit);
        setLayoutData(attrSection, content);

        return root;
    }

    /**
     * Set the layout data of the given control. This will check whether the
     * section is set to use extra space and set the layout of the control
     * accordingly.
     * 
     * @param section
     * @param content
     */
    private void setLayoutData(AbstractXpdSection section, Control content) {
        if (section != null && content != null) {
            if (section.shouldUseExtraSpace()) {
                content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                        true));
            } else {
                content.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
                        false));
            }
        }
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = nameSection.getCommand(obj);

        if (cmd == null) {
            cmd = attrSection.getCommand(obj);
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        nameSection.refresh();
        attrSection.refresh();
    }

    @Override
    public void setInput(Collection<?> items) {
        if (items != null && !items.isEmpty()) {
            Object next = items.iterator().next();

            if (next instanceof QualifiedOrgElement) {
                input = ((QualifiedOrgElement) next).getQualifierAttribute();
            }
        }

        // Update the input to be an Attribute
        items = input != null ? Collections.singleton(input) : null;
        nameSection.setInput(items);
        attrSection.setInput(items);

        super.setInput(items);
    }

    @Override
    public EObject getInput() {
        return input;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof QualifiedOrgElement) {
            return ((QualifiedOrgElement) toTest).getQualifierAttribute() != null;
        }
        return false;
    }
}
