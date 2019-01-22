/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.activityhyperlinkhandlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity icon hyperlink handler for sub-process tasks.
 * 
 * @author aallway
 * @since 22 Aug 2011
 */
public class SubProcessTaskHyperlinkHandler implements
        IActivityHyperlinkHandler {

    public SubProcessTaskHyperlinkHandler() {
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
                && TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
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
            EObject subproc =
                    TaskObjectUtil
                            .getSubProcessOrInterface((Activity) activityModelObject);
            if (subproc != null) {
                return subproc;
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
        if (contextObject instanceof Process) {
            return String
                    .format(Messages.SubProcessTaskHyperlinkHandler_OpenSubProcessHyperlink_tooltip,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName((Process) contextObject));
        } else if (contextObject instanceof ProcessInterface) {
            return String
                    .format(Messages.SubProcessTaskHyperlinkHandler_OpenProcessInterfaceHyperlink_tooltip,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName((ProcessInterface) contextObject));
        }

        return Messages.SubProcessTaskHyperlinkHandler_RefSubProcessNotAvailable_tooltip;
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
        if (contextObject instanceof Process
                || contextObject instanceof ProcessInterface) {
            try {
                IConfigurationElement facConfig =
                        XpdResourcesUIActivator
                                .getEditorFactoryConfigFor(contextObject);
                String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                IWorkbenchPage page =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();
                EditorInputFactory f =
                        (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$
                IEditorInput input = f.getEditorInputFor(contextObject);
                page.openEditor(input, editorID);

            } catch (CoreException e) {
            }
        }
    }

}
