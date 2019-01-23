/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author aallway
 * 
 */
public class MessageFlowPropertySection extends
        AbstractNamedDiagramObjectSection {

    public MessageFlowPropertySection() {
        super(Xpdl2Package.eINSTANCE.getMessageFlow());
        instrumentationPrefixName = "MessageFlow"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     */
    @Override
    protected void objectTypeCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        // Message flow only has Name at the moment - nothing to do here.
        return;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command objectTypeGetCommand(Object obj) {
        // Message flow only has Name at the moment - nothing to do here.
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeGetDescriptor()
     * 
     * @return
     */
    @Override
    protected String objectTypeGetDescriptor() {
        return Messages.MessageFlowPropertySection_MessageFlow_label;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeRefresh()
     * 
     */
    @Override
    protected void objectTypeRefresh() {
        // Message flow only has Name at the moment - nothing to do here.
        return;
    }

}
