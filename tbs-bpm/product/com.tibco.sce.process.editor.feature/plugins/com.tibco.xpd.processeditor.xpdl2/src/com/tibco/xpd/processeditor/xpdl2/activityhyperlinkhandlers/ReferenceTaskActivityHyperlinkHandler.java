/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.activityhyperlinkhandlers;

import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity hyperlink handler for reference tasks.
 * 
 * @author aallway
 * @since 22 Aug 2011
 */
public class ReferenceTaskActivityHyperlinkHandler implements
        IActivityHyperlinkHandler {

    public ReferenceTaskActivityHyperlinkHandler() {
    }

    /**
     * @see com.tibco.xpd.processwidget.IActivityHyperlinkHandler#isApplicableActivity(java.lang.Object)
     * 
     * @param activityModelObject
     * @return
     */
    @Override
    public boolean isApplicableActivity(Object activityModelObject) {
        if (activityModelObject instanceof Activity
                && TaskType.REFERENCE_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict((Activity) activityModelObject))) {
            return true;
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
            Activity refdAct =
                    ReferenceTaskUtil
                            .getReferencedTask((Activity) activityModelObject);
            return refdAct;
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
        if (contextObject instanceof Activity) {
            return String
                    .format(Messages.ReferenceTaskActivityHyperlinkHandler_GotoRefTask_tooltip,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName((Activity) contextObject));
        }
        return Messages.ReferenceTaskActivityHyperlinkHandler_RefTaskNotSet_tooltip;
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
        if (contextObject instanceof Activity) {
            RevealProcessDiagramEObject.revealEObject(PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage(),
                    (Activity) contextObject,
                    true);

        }

    }

}
