/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.activityhyperlinkhandlers;

import javax.wsdl.Operation;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity hyperlink handler for web-service related activities.
 * 
 * @author aallway
 * @since 22 Aug 2011
 */
public class WebServiceActivityHyperlinkHandler implements
        IActivityHyperlinkHandler {

    public WebServiceActivityHyperlinkHandler() {
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#isApplicableActivity(java.lang.Object)
     * 
     * @param activityModelObject
     * @return
     */
    @Override
    public boolean isApplicableActivity(Object activityModelObject) {
        if (activityModelObject instanceof Activity) {
            Activity activity = (Activity) activityModelObject;

            /*
             * Any activity with the web-service operation element defined (even
             * if not filled) is a web-service op (except invoke business
             * service.
             */
            if (Xpdl2ModelUtil.getWebServiceOperation(activity) != null
                    && !TaskImplementationTypeDefinitions.INVOKE_BUSINESS_PROCESS
                            .equals(TaskObjectUtil
                                    .getTaskImplementationExtensionId(activity))) {
                return true;
            }

        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#getEnablementContextObject(java.lang.Object)
     * 
     * @param activityModelObject
     * @return
     */
    @Override
    public Object getEnablementContextObject(Object activityModelObject) {
        if (activityModelObject instanceof Activity) {
            Activity activity = (Activity) activityModelObject;

            Operation operation = Xpdl2WsdlUtil.getOperation(activity);
            return operation;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#getHyperlinkTooltip(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param activityModelObject
     * @param contextObject
     * @return
     */
    @Override
    public String getHyperlinkTooltip(Object activityModelObject,
            Object contextObject) {
        if (contextObject instanceof Operation) {
            Operation operation = (Operation) contextObject;

            String name = operation.getName();

            return String
                    .format(Messages.WebServiceActivityHyperlinkHandler_WebServiceOperationHyperlink_tooltip,
                            name);

        }
        return Messages.WebServiceActivityHyperlinkHandler_WebServiceOperationNotAvialbel_tooltip;
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#doHyperLink(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param activityModelObject
     * @param contextObject
     */
    @Override
    public void doHyperLink(Object activityModelObject, Object contextObject) {
        if (contextObject instanceof Operation) {

            /* Try opening the wsdl file editor instead. */
            if (activityModelObject instanceof Activity) {
                IFile file =
                        Xpdl2WsdlUtil
                                .getWsdlFile((Activity) activityModelObject);
                if (file != null && file.exists()) {
                    try {
                        IDE.openEditor(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getActivePage(),
                                file);
                    } catch (PartInitException e) {
                        Activator.getDefault().getLogger().error(e);
                    }
                }
            }
        }

        return;
    }
}
