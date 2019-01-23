/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public class ActivityAssignmentsSection extends AbstractFilteredTransactionalSection {

    public ActivityAssignmentsSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @param parent
     * @param toolkit
     * @return
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        return root;
    }

    /**
     * @param obj
     * @return
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        // TODO Auto-generated method stub

    }

}
