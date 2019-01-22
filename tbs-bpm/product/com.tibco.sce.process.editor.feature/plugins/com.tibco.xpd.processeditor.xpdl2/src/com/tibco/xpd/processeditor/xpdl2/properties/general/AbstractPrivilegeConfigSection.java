/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.OtherElementsContainer;

/**
 * Abstract Privilege configuration section that appears in case or business
 * service property section. This abstract class demands the implementation
 * classes to provide their own implementation of table label and tool tip text
 * 
 * @author bharge
 * @since 4 Sep 2014
 */
public abstract class AbstractPrivilegeConfigSection extends
        AbstractTransactionalSection {

    private CaseOrBizServPrivilegesConfigurationTable privilegesTable;

    private Composite privilegesComposite;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        privilegesTable.getViewer().refresh();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {

        super.setInput(part, selection);
        if (getInput() instanceof OtherElementsContainer) {

            if (null != privilegesTable) {

                privilegesTable.getViewer().setInput(getInput());
            }
        }
    }

    @Override
    protected EObject resollveInput(Object object) {

        if (object instanceof EditPart) {

            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
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

        GridLayoutFactory.swtDefaults().applyTo(parent);

        privilegesComposite = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout();
        layout.marginBottom = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        privilegesComposite.setLayout(layout);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(privilegesComposite);

        Label privilegeTableLbl =
                toolkit.createLabel(privilegesComposite,
                        getTableLabel(),
                        SWT.READ_ONLY);
        privilegeTableLbl.setToolTipText(getTableToolTipText());

        privilegeTableLbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        privilegesTable =
                new CaseOrBizServPrivilegesConfigurationTable(
                        privilegesComposite, toolkit, getEditingDomain(),
                        Messages.RequiredPrivilegesColumn_label);

        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.minimumHeight = 125;

        privilegesTable.setLayoutData(gridData);
        return privilegesComposite;
    }

    public abstract String getTableLabel();

    public abstract String getTableToolTipText();

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        return null;
    }

}
