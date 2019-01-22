/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.adhoc;

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
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Organization Privilege configuration section for Adhoc tasks [User task /
 * re-usable sub process having AdhocConfigurationType set]
 * 
 * 
 * @author kthombar
 * @since 22-Aug-2014
 */
public class AdhocTaskPrivilegeConfigurationSection extends
        AbstractTransactionalSection {

    private AdhocTaskPrivilegeConfigurationTable privilegesTable;

    private Composite privilegesComposite;

    private Label privilegeTableLbl;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        privilegesTable.getViewer().refresh();

        EObject input = getInput();

        if (input instanceof Activity) {

            Activity activity = (Activity) input;

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                AdHocExecutionTypeType adHocExecutionType =
                        adhocConfigType.getAdHocExecutionType();

                /*
                 * Change the Organization Privileges label based on the type of
                 * Adhoc operation selected.
                 */
                if (AdHocExecutionTypeType.AUTOMATIC.equals(adHocExecutionType)) {
                    privilegeTableLbl
                            .setText(Messages.AdhocTaskPrivilegeConfigurationSection_OrganizationPrivilegesTableAutomaticInvocation_desc);
                } else {
                    privilegeTableLbl
                            .setText(Messages.AdhocTaskPrivilegeConfigurationSection_OrganizationPrivilegesTableManualInvocation_desc);
                }
            }
        }
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
        privilegesTable.getViewer().setInput(getInput());
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

        privilegeTableLbl =
                toolkit.createLabel(privilegesComposite,
                        Messages.AdhocTaskPrivilegeConfigurationSection_OrganizationPrivilegesTableAutomaticInvocation_desc,
                        SWT.READ_ONLY);
        privilegeTableLbl
                .setToolTipText(Messages.AdhocTaskPrivilegeConfigurationSection_OrganizationPrivilegesTable_tooltip);

        privilegeTableLbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        privilegesTable =
                new AdhocTaskPrivilegeConfigurationTable(
                        privilegesComposite,
                        toolkit,
                        getEditingDomain(),
                        Messages.AdhocTaskPrivilegeConfigurationSection_OrganizationPrivilegesTableColumn_label);

        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.minimumHeight = 125;

        privilegesTable.setLayoutData(gridData);
        return privilegesComposite;
    }

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
