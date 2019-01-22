/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.activityhyperlinkhandlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.IActivityHyperlinkHandler;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * User task icon hyperlink handler.
 * 
 * @author aallway
 * @since 22 Aug 2011
 */
public class UserTaskHyperlinkHandler implements IActivityHyperlinkHandler {

    public UserTaskHyperlinkHandler() {
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
                && TaskType.USER_LITERAL.equals(TaskObjectUtil
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
            Activity activity = (Activity) activityModelObject;

            FormImplementation formImplementation =
                    TaskObjectUtil.getUserTaskFormImplementation(activity);
            if (formImplementation != null) {
                if (FormImplementationType.FORM.equals(formImplementation
                        .getFormType())) {
                    return TaskObjectUtil
                            .getUserTaskFormFile((Activity) activityModelObject);

                } else if (FormImplementationType.PAGEFLOW
                        .equals(formImplementation.getFormType())) {
                    return TaskObjectUtil.getUserTaskPageflowProcess(activity);
                }
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
            /* Pageflow proces reference. */
            return String
                    .format(Messages.UserTaskHyperlinkHandler_OpenPageflow_tooltip,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(((Process) contextObject)));
        } else if (contextObject instanceof IFile) {
            /* FOrm file reference. */
            return String
                    .format(Messages.UserTaskHyperlinkHandler_OpemFormFile_tooltip,
                            ((IFile) contextObject).getName());
        }
        return Messages.UserTaskHyperlinkHandler_NoFormSelected_tooltip;
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
        if (contextObject instanceof Process) {
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
                Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
            }

        } else if (contextObject instanceof IFile) {
            IFile formFile = (IFile) contextObject;

            try {
                IDE.openEditor(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage(), formFile);

            } catch (PartInitException e) {
                Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
            }
        }
    }

}
