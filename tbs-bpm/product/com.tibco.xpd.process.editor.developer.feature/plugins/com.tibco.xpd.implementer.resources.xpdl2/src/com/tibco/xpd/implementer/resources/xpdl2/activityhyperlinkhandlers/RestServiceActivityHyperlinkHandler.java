/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.activityhyperlinkhandlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorPart;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.resources.ui.editorHandler.IDisplayEObject;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.editor.RsdEditorOpener;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Activity hyperlink handler for REST services invocation tasks.
 * 
 * @author aallway
 * @since 25 Jun 2015
 */
public class RestServiceActivityHyperlinkHandler implements
        IActivityHyperlinkHandler {

    private RestServiceTaskAdapter restServiceActivityAdapter =
            new RestServiceTaskAdapter();

    /**
     * 
     */
    public RestServiceActivityHyperlinkHandler() {
        // TODO Auto-generated constructor stub
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
            return restServiceActivityAdapter
                    .isRestServiceImplementation((Activity) activityModelObject);
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
            return restServiceActivityAdapter
                    .getRSOMethod((Activity) activityModelObject);
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
        if (contextObject instanceof Method) {
            Method method = (Method) contextObject;
            Resource resource = (Resource) method.eContainer();
            Service service = (Service) resource.eContainer();

            return String
                    .format(Messages.RestServiceActivityHyperlinkHandler_RESTServiceHyperLink_tooltip,
                            service.getName(),
                            resource.getName(),
                            method.getName());
        }
        return Messages.RestServiceActivityHyperlinkHandler_RESTServiceNotSelectedHYperlink_tooltip;
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
        if (contextObject instanceof Method) {
            Method method = (Method) contextObject;

            IFile rsdFile = WorkingCopyUtil.getFile(method);

            if (rsdFile != null && rsdFile.isAccessible()) {
                RsdEditorOpener opener = new RsdEditorOpener();
                IEditorPart editor = opener.openEditor(rsdFile);
                if (editor instanceof IDisplayEObject) {
                    IDisplayEObject go = (IDisplayEObject) editor;
                    go.gotoEObject(true, method);
                }
            }
        }

    }

}
