/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.adhoc;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Adhoc Task Access Section.
 * 
 * @author kthombar
 * @since 30-Jul-2014
 */
public class AdhocConfigurationAccessSection extends
        AbstractFilteredTransactionalSection implements SashSection {
    /**
     * @param eClass
     */
    public AdhocConfigurationAccessSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());

    }

    private AdhocTaskPrivilegeConfigurationSection orgPrivilegeSection;

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashSection#shouldSashSectionRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    public boolean shouldSashSectionRefresh(List<Notification> notifications) {

        return shouldRefresh(notifications);
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
        if (orgPrivilegeSection != null) {
            orgPrivilegeSection.setInput(part, selection);
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
        if (orgPrivilegeSection != null) {
            orgPrivilegeSection.setInput(items);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (orgPrivilegeSection != null) {
            orgPrivilegeSection.refresh();
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
        Composite composite = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginBottom = 5;
        composite.setLayout(gridLayout);

        Composite privSecComposite = toolkit.createComposite(composite);

        privSecComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        privSecComposite.setLayout(new FillLayout());

        orgPrivilegeSection = new AdhocTaskPrivilegeConfigurationSection();
        orgPrivilegeSection.createControls(privSecComposite, toolkit);
        return composite;
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

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return <code>true</code> always, let
     *         {@link AdhocConfigurationSection#select(Object)} do the
     *         filtering.
     */
    @Override
    public boolean select(Object toTest) {
        return true;
    }
}
