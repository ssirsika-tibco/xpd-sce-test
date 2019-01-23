/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (2 Jul 2010)
 */
public class EventThrowMessageImplSection extends EventMessageImplSection {

    private AbstractXpdSection restServiceDetailsSection;

    protected Composite restServiceDetailsContainer;

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.EventMessageImplSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        if (restServiceDetailsSection != null) {
            restServiceDetailsSection.setInput(getPart(), getSelection());
        }
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.EventMessageImplSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param tk
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {
        Control control = super.doCreateControls(parent, tk);
        impCombo.setData(Messages.Rest_Service_Impl_Name,
                RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME);

        // Create the REST Service details section.
        restServiceDetailsSection = new RestServiceTaskSection();

        restServiceDetailsContainer =
                tk.createComposite(detailsPageBook.getContainer());
        restServiceDetailsContainer.setLayout(new FillLayout());
        GridData gd2 = new GridData(GridData.FILL_BOTH);
        gd2.horizontalSpan = 2;
        restServiceDetailsContainer.setLayoutData(gd2);

        restServiceDetailsSection.createControls(restServiceDetailsContainer,
                getPropertySheetPage());
        detailsPageBook
                .registerPage(RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME,
                        restServiceDetailsContainer);

        return control;
    }

    /**
     * @return
     */
    @Override
    protected String[] getImplementations() {
        return new String[] { Messages.Web_Service_Impl_Name,
                Messages.Rest_Service_Impl_Name, Messages.Unspecified_Impl_Name };
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.EventMessageImplSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection items) {
        super.setInput(items);
        restServiceDetailsSection.setInput(items);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.EventMessageImplSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
        restServiceDetailsSection.refresh();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.EventMessageImplSection#createWebServiceDetailsSection()
     * 
     * @return
     */
    @Override
    protected WebServiceDetailsSection createWebServiceDetailsSection() {
        return new TaskSendSection();
    }
}
