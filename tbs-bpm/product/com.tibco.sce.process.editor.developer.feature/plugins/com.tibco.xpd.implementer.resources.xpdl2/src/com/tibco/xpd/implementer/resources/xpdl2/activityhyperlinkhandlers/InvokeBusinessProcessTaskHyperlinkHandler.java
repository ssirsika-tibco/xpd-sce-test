/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.activityhyperlinkhandlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity hyperlink handler for invoke business process send task.
 * 
 * @author aallway
 * @since 23 Aug 2011
 */
public class InvokeBusinessProcessTaskHyperlinkHandler implements
        IActivityHyperlinkHandler {

    public InvokeBusinessProcessTaskHyperlinkHandler() {
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
            if (TaskImplementationTypeDefinitions.INVOKE_BUSINESS_PROCESS
                    .equals(TaskObjectUtil
                            .getTaskImplementationExtensionId((Activity) activityModelObject))) {
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

            Activity referencedActivity =
                    TaskObjectUtil
                            .getInvokeBusinessProcessReferencedActivity((Activity) activityModelObject);

            if (referencedActivity != null) {
                return referencedActivity;
            }
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
                    .format(Messages.InvokeBusinessProcessTaskHyperlinkHandler_OpenBusinessProecssHyperlink_tooltip,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName((Activity) contextObject));
        }

        return Messages.InvokeBusinessProcessTaskHyperlinkHandler_RefBusinessProcessNotSet_tooltip;
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
            try {

                Activity referencedActivity = (Activity) contextObject;

                IConfigurationElement facConfig =
                        XpdResourcesUIActivator
                                .getEditorFactoryConfigFor(referencedActivity
                                        .getProcess());
                String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                IWorkbenchPage page =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();
                EditorInputFactory f =
                        (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$
                IEditorInput input = f.getEditorInputFor(referencedActivity);

                IEditorPart part = IDE.openEditor(page, input, editorID);

                if (part instanceof IGotoEObject) {
                    ((IGotoEObject) part).gotoEObject(true, referencedActivity);
                }

            } catch (CoreException e) {
            }
        }
    }

}
