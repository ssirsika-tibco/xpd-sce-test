/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.internal;

import static com.tibco.xpd.wsdl.Activator.TIBEX;
import static com.tibco.xpd.wsdl.Activator.TIBEX_URI;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.wsdl.Definition;
import org.w3c.dom.Element;

import com.tibco.xpd.bom.wsdltransform.Activator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to enable/disable WSDL BDS support. This class should be used with
 * the parameter "enable" set, i.e.
 * <code>com.tibco.xpd.bom.wsdltransform.internal.WsdlBDSSupportResolution:enable=true</code>
 * (if this resolution should enable BDS support).
 * 
 * @author njpatel
 */
public class WsdlBDSSupportResolution implements IResolution,
        IExecutableExtension {

    private static final String PARAM_ENABLE = "enable"; //$NON-NLS-1$

    private boolean doEnable = false;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core
     * .resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        if (marker != null && marker.exists()) {
            Display display = XpdResourcesPlugin.getStandardDisplay();
            IResource resource = marker.getResource();
            if (resource != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);
                EObject root = wc != null ? wc.getRootElement() : null;

                if (root instanceof Definition) {
                    /*
                     * If editor is open then we need to close it (save if
                     * necessary before closing)
                     */
                    IEditorPart editor = getEditor(resource);

                    boolean doUpdate = true;
                    if (editor != null) {
                        // Editor is open so need to save (if dirty) and close
                        doUpdate = closeEditor(display, editor, resource);
                    }

                    if (doUpdate) {
                        try {
                            // Apply the BDS support change
                            enableBDSSupport(wc, doEnable);
                        } catch (Exception e) {
                            String message =
                                    doEnable ? Messages.WsdlBDSSupportResolution_problemEnablingBDSSupport_error_message
                                            : Messages.WsdlBDSSupportResolution_problemDisablingBDSSupport_error_message;

                            Activator.getDefault().getLogger()
                                    .error(e, message);

                            ErrorDialog
                                    .openError(display.getActiveShell(),
                                            Messages.WsdlBDSSupportResolution_ErrorApplyingChange_dialog_title,
                                            message,
                                            new Status(IStatus.ERROR,
                                                    Activator.PLUGIN_ID,
                                                    e.getLocalizedMessage(), e));
                        }
                    }
                }
            }
        }
    }

    /**
     * @param display
     * @param editor
     * @param resource
     * @return
     */
    private boolean closeEditor(final Display display,
            final IEditorPart editor, IResource resource) {

        final Boolean[] editorClosed = new Boolean[] { false };
        final Boolean[] failedToCloseEditor = new Boolean[] { false };
        // If editor is dirty then need to save it first
        String message =
                editor.isDirty() ? String
                        .format(Messages.WsdlBDSSupportResolution_fileNeedsSaveAndEditorClosed_message,
                                resource.getName())
                        : String
                                .format(Messages.WsdlBDSSupportResolution_editorNeedsClosing_message,
                                        resource.getName());

        if (MessageDialog
                .openQuestion(display.getActiveShell(),
                        editor.isDirty() ? Messages.WsdlBDSSupportResolution_saveAndCloseEditor_dialog_title
                                : Messages.WsdlBDSSupportResolution_closeEditor_dialog_title,
                        message)) {

            IRunnableWithProgress op = new IRunnableWithProgress() {
                public void run(final IProgressMonitor monitor)
                        throws InvocationTargetException, InterruptedException {

                    display.syncExec(new Runnable() {
                        public void run() {
                            if (editor.isDirty()) {
                                monitor
                                        .beginTask(Messages.WsdlBDSSupportResolution_savingAndClosingEditor_progress_shortdesc,
                                                2);
                                editor
                                        .doSave(new SubProgressMonitor(monitor,
                                                1));
                            } else {
                                monitor
                                        .beginTask(Messages.WsdlBDSSupportResolution_closingEditor_progress_shortdesc,
                                                1);
                            }

                            IWorkbenchWindow window =
                                    editor.getEditorSite().getWorkbenchWindow();
                            if (window != null
                                    && window.getActivePage() != null) {
                                editorClosed[0] =
                                        window.getActivePage()
                                                .closeEditor(editor, false);
                                /*
                                 * Indicate that the close of the editor failed
                                 * so we can alert user
                                 */
                                failedToCloseEditor[0] = !editorClosed[0];
                            }
                            monitor.worked(1);
                        }
                    });
                }
            };
            try {
                new ProgressMonitorDialog(display.getActiveShell()).run(true,
                        true,
                        op);
            } catch (InvocationTargetException e) {
                Activator.getDefault().getLogger().error(e);
                editorClosed[0] = false;
            } catch (InterruptedException e) {
                Activator.getDefault().getLogger().error(e);
                editorClosed[0] = false;
            }
        }

        // If the editor was not closed then tell the user to close it
        if (failedToCloseEditor[0]) {
            MessageDialog
                    .openInformation(display.getActiveShell(),
                            Messages.WsdlBDSSupportResolution_problemClosingEditor_error_title,
                            Messages.WsdlBDSSupportResolution_problemClosingEditor_error_message);
        }

        return editorClosed[0];
    }

    /**
     * Set the enablement of the BDS support on the given model.
     * 
     * @param wc
     *            wsdl working copy.
     * @param enable
     *            <code>true</code> if BDS support should be enabled
     * @throws IOException
     * @throws InterruptedException
     * @throws RollbackException
     */
    private void enableBDSSupport(WorkingCopy wc, boolean enable)
            throws IOException, InterruptedException, RollbackException {
        if (wc != null && wc.getRootElement() instanceof Definition) {
            Definition definition = (Definition) wc.getRootElement();

            if (definition.getElement() != null) {
                final Element element = definition.getElement();
                InternalTransactionalEditingDomain ed =
                        (InternalTransactionalEditingDomain) XpdResourcesPlugin
                                .getDefault().getEditingDomain();

                Transaction transaction = ed.getActiveTransaction();

                if (transaction == null || transaction.isReadOnly()) {
                    /*
                     * If read-only transaction then need to start unprotected
                     * transaction. In actual fact there should never be an
                     * active transaction already.
                     */
                    Map<String, Object> opts = new HashMap<String, Object>();
                    if (transaction != null) {
                        opts.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
                    }
                    transaction = ed.startTransaction(false, opts);
                } else {
                    /*
                     * There is already an active read-write transaction so no
                     * need to start a new transaction
                     */
                    transaction = null;
                }
                try {
                    if (!enable) {
                        /*
                         * Disable BDS support by adding attribute
                         */
                    if (definition.getNamespace(TIBEX) == null) {
                            // Need to add the extension namespace
                        definition.addNamespace(TIBEX, TIBEX_URI);
                        }

                        element.setAttribute(Activator.TIBEX_BDSSUPPORT_ATTR,
                                Boolean.FALSE.toString());
                    } else {
                        /*
                         * Enable BDS support by removing attribute
                         */
                    if (element.hasAttribute(Activator.TIBEX_BDSSUPPORT_ATTR)) {
                            element
                                    .removeAttribute(Activator.TIBEX_BDSSUPPORT_ATTR);
                        }
                    }
                } finally {
                    if (transaction != null) {
                        transaction.commit();
                    }
                }
                wc.save();
            }
        }
    }

    /**
     * Get the editor, if any, of the given resource.
     * 
     * @param resource
     * @return editor if open, otherwise <code>null</code>.
     */
    private IEditorPart getEditor(IResource resource) {
        IWorkbenchWindow workbenchWindow =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (workbenchWindow != null) {
            IWorkbenchPage page = workbenchWindow.getActivePage();
            if (page != null) {
                IEditorReference[] editorReferences =
                        page.getEditorReferences();

                for (IEditorReference ref : editorReferences) {
                    IEditorInput input;
                    try {
                        input = ref.getEditorInput();
                        if (input instanceof IFileEditorInput
                                && resource.equals(((IFileEditorInput) input)
                                        .getFile())) {
                            return ref.getEditor(false);
                        }
                    } catch (PartInitException e) {
                        /*
                         * Ignore, this will be thrown when the editor cannot be
                         * restored, but if this is the case then we can assume
                         * the editor is not open in the first place.
                         */
                    }
                }
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        if (data instanceof String) {
            String[] split = ((String) data).split("-"); //$NON-NLS-1$
            if (split.length > 0) {
                split = split[0].split("="); //$NON-NLS-1$
                if (split.length > 1) {
                    if (PARAM_ENABLE.equalsIgnoreCase(split[0])) {
                        doEnable = Boolean.parseBoolean(split[1]);
                    }
                }
            }
        }
    }

}
