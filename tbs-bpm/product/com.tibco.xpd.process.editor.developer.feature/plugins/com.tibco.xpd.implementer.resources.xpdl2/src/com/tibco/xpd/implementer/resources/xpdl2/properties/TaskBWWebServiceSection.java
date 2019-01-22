/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.script.TaskBWServiceMessageAdapter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property section for assigning a web serviceNameText to a serviceNameText
 * task.
 * 
 * @author aallway
 */
public class TaskBWWebServiceSection extends WebServiceDetailsSection {

    @Override
    protected boolean isBWImplementation() {
        return true;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        Activity act = getActivityInput();
        if (act != null) {
            activityMessage = new TaskBWServiceMessageAdapter();
        }
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
        remoteWsdl.setText(Messages.TaskWebServiceSection_RemoteLiveLinkLabel);
        localWsdl.setEnabled(true);
        WebServiceOperation wso =
                activityMessage.getWebServiceOperation(getActivityInput());
        IProject project = getProject();
        if (wso != null && project != null) {
            Service ws = wso.getService();
            boolean isRemote =
                    Xpdl2ModelUtil.getOtherAttributeAsBoolean(ws,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_IsRemote());
            remoteWsdl.setSelection(isRemote);
            localWsdl.setSelection(!isRemote);
            if (isRemote) {
                wsdlLocationText
                        .setText(Messages.TaskWebServiceSection_URLTakenFromRuntimeAliasLabel);
            } else {
                wsdlLocationText.setText(ws.getEndPoint()
                        .getExternalReference().getLocation());
            }
        }
    }
}
