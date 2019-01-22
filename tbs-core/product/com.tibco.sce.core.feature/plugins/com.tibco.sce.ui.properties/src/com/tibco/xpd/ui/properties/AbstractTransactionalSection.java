/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.ResourceUndoContext;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.GlobalActionManager;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.GlobalRedoAction;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.GlobalUndoAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ElapsedTimerHelper;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Abstract class to be used by property sections of any <code>EObject</code>
 * that uses the transactional editing domain (the shared single transactional
 * editing domain is used - to change this override
 * {@link #getEditingDomainID()}).
 * <p>
 * This property section is intended to be used by single-selection property
 * tabs.
 * </p>
 * <p>
 * It is recommended that
 * {@link #createLabel(Composite, XpdFormToolkit, String) createLabel} is used
 * to create labels in this property page. This will ensure all label are
 * aligned with the same standard width.
 * </p>
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractTransactionalSection extends AbstractXpdSection {

    /**
     * Transactional editing domain.
     */
    private TransactionalEditingDomain editingDomain;

    /**
     * Resource set listener registered with the editing domain.
     */
    private ResourceSetListener resourceListener;

    /**
     * Input for this property section.
     */
    private EObject input;

    private CommandStackListener cmdExecutedForWizardRefreshSectionListener =
            null;

    @Override
    protected EObject doGetInputContainer() {
        return input != null ? input.eContainer() : null;
    }

    @Override
    protected EditingDomain getEditingDomain(EObject eo) {
        return TransactionUtil.getEditingDomain(eo);
    }

    @Override
    public final EditingDomain getEditingDomain() {

        if (editingDomain == null) {
            // Get the editing domain
            String id = getEditingDomainID();

            if (id != null) {
                editingDomain =
                        TransactionalEditingDomain.Registry.INSTANCE
                                .getEditingDomain(id);

                if (editingDomain != null) {
                    // Register the resource set listener with this editing
                    // domain
                    resourceListener = createResourceSetListener();
                    editingDomain.addResourceSetListener(resourceListener);
                } else {
                    // Editing domain is null
                    throw new IllegalArgumentException(
                            String.format("Cannot find or create transactional editing domain with id %s", //$NON-NLS-1$
                                    id));
                }
            } else {
                throw new NullPointerException(
                        "Transactional editing domain id is null."); //$NON-NLS-1$
            }
        }

        return editingDomain;
    }

    /**
     * Create a resource set listener to registered with the transactional
     * editing domain when created. The default implementation uses a
     * post-commit listener that will refresh the section when the input of the
     * section, or any if its content, changes.
     * <p>
     * Subclasses may override this method.
     * </p>
     * 
     * @return <code>ResourceSetListener</code>.
     */
    protected ResourceSetListener createResourceSetListener() {
        return new SectionResourceSetListener();
    }

    /**
     * Get the id of the transactional editing domain. The default
     * implementation will return the id of the single shared editing domain.
     * Subclasses that wish to use a different editing domain can override this
     * method.
     * 
     * @return id of the editing domain.
     */
    protected String getEditingDomainID() {
        return XpdConsts.EDITING_DOMAIN_ID;
    }

    @Override
    protected boolean gotInput() {
        return input != null;
    }

    /**
     * Sets the input for this section. This section is intended for a <b>single
     * selection</b> property tab. Subclasses for multiple selection tabs should
     * override this method.
     */
    @Override
    public void setInput(Collection<?> items) {
        if (items != null && !items.isEmpty()) {
            input = resollveInput(items.iterator().next());
        } else {
            input = null;
        }

        /*
         * Create and add a command stack listener so that we can tell sections
         * to refresh EVEN when they are in a wizard.
         * 
         * (first time only)
         */
        if (cmdExecutedForWizardRefreshSectionListener == null) {
            createCmdStackChangedForWizardRefreshListener();
        }

        updateUndoContext();

        return;
    }

    /**
     * Set the undo context in the undo action handler
     */
    private void updateUndoContext() {
        /* Update the undo context. */
        TabbedPropertySheetPage propertySheetPage = getPropertySheetPage();

        if (propertySheetPage != null) {
            IWorkbenchPart part = getPart();

            IActionBars actionBars =
                    propertySheetPage.getSite().getActionBars();

            if (input != null && part != null && actionBars != null) {
                // Registed undo action
                IAction action = getUndoAction(part);
                if (action != null) {
                    actionBars.setGlobalActionHandler(ActionFactory.UNDO
                            .getId(), action);
                }
                // Register redo action
                action = getRedoAction(part);
                if (action != null) {
                    actionBars.setGlobalActionHandler(ActionFactory.REDO
                            .getId(), action);
                }

                actionBars.updateActionBars();
            }
        }
        return;
    }

    /**
     * Resolve the given object (input of this section) to it's semantic model
     * element. The default implementation will simply cast the object to an
     * <code>EObject</code> if it's an <code>EObject</code> type. Subclasses may
     * override.
     * 
     * @param object
     * @return
     */
    protected EObject resollveInput(Object object) {
        return object instanceof EObject ? (EObject) object : null;
    }

    /**
     * Get the input of this property section.
     * <p>
     * The default input implementation only deals with a single input object.
     * If multiple inputs need to be supported then
     * {@link #setInput(Collection) setInput} and this method should be
     * overridden.
     * </p>
     * 
     * @see #setInput(Collection)
     * 
     * @return the input for this section.
     */
    public EObject getInput() {
        return input;
    }

    @Override
    public final void refresh() {

        if (isIgnoreEvents()) {
            // See super method for notes
            return;
        }

        /* XPD-1128: DOn't refresh if object has been divorced from model! */
        if (input != null
                && (input.eResource() != null || getSectionContainerType() == ContainerType.WIZARD)) {

            if (isCreated()) {
                try {
                    setIgnoreEvents(true);

                    // Run refresh in a read-only transaction
                    TransactionalEditingDomain ed =
                            (TransactionalEditingDomain) getEditingDomain();

                    if (ed != null) {
                        final String className =
                                this.getClass().getSimpleName();

                        ed.runExclusive(new Runnable() {

                            public void run() {
                                if (input != null) {
                                    ElapsedTimerHelper.timedMsg(className
                                            + ": refresh()"); //$NON-NLS-1$
                                    doRefresh();

                                    /*
                                     * XPD-1128: When editing a read only
                                     * resource then disable editing parts of
                                     * controls.
                                     */
                                    if (input != null) {
                                        WorkingCopy wc =
                                                WorkingCopyUtil
                                                        .getWorkingCopyFor(input);
                                        if (wc != null && wc.isReadOnly()) {
                                            /*
                                             * Only disable if read only, don't
                                             * enable if it isn't read only,
                                             * just leave as whatever the
                                             * individual section says.
                                             */
                                            disableControlsForReadOnlyInput(getControlsContainer());
                                        }
                                    }

                                    ElapsedTimerHelper.timedMsg(className
                                            + ": <==refresh()"); //$NON-NLS-1$ 
                                }
                            }

                        });
                    }
                } catch (InterruptedException e) {
                    PropertiesPlugin.getDefault().getLogger().error(e);
                } finally {
                    setIgnoreEvents(false);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#getCommand(java.lang.Object
     * )
     */
    @Override
    public final Command getCommand(Object obj) {
        /*
         * XPD-1140: When requested to make a change then ignore it if the
         * working copy is read only.
         */
        if (input != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(input);
            if (wc != null && wc.isReadOnly()) {
                /*
                 * Don't run the command but DO perform a refresh (therefore the
                 * user-entered change to control should be wound back).
                 */
                refresh();
                return null;
            }
        }

        return super.getCommand(obj);
    }

    @Override
    public void dispose() {
        // Unregister the resource set listener
        if (resourceListener != null && editingDomain != null) {
            editingDomain.removeResourceSetListener(resourceListener);
        }

        /* Remove the command staack listener too */
        if (cmdExecutedForWizardRefreshSectionListener != null) {
            if (editingDomain != null) {
                if (editingDomain.getCommandStack() != null) {
                    editingDomain
                            .getCommandStack()
                            .removeCommandStackListener(cmdExecutedForWizardRefreshSectionListener);
                    cmdExecutedForWizardRefreshSectionListener = null;
                }

            }
        }

        super.dispose();
    }

    @Override
    public void aboutToBeShown() {
        updateUndoContext();

        super.aboutToBeShown();
    }

    /**
     * Get the undo action to add to the global action handler.
     * 
     * @param part
     *            <code>IWorkbenchPart</code>
     * @return Undo action registered with the given workbench part. If one is
     *         not found then <code>null</code> will be returned.
     */
    protected IAction getUndoAction(IWorkbenchPart part) {
        IAction action =
                GlobalActionManager.getInstance().getGlobalActionHandler(part,
                        ActionFactory.UNDO.getId());

        if (action != null) {
            // Set the undo context
            if (action instanceof GlobalUndoAction) {
                IUndoContext context = getUndoContext();

                if (context != null) {
                    ((GlobalUndoAction) action).setUndoContext(context);
                }
            }
        }
        return action;
    }

    /**
     * Get the redo action to add to the global action handler.
     * 
     * @param part
     *            <code>IWorkbenchPart</code>
     * @return Redo action registered with the given workbench part. If one is
     *         not found then <code>null</code> will be returned.
     */
    protected IAction getRedoAction(IWorkbenchPart part) {
        IAction action =
                GlobalActionManager.getInstance().getGlobalActionHandler(part,
                        ActionFactory.REDO.getId());

        if (action != null) {
            // Set the redo context
            if (action instanceof GlobalRedoAction) {
                IUndoContext context = getUndoContext();

                if (context != null) {
                    ((GlobalRedoAction) action).setUndoContext(context);
                }
            }
        }
        return action;
    }

    /**
     * Get the undo context for the Undo/Redo global actions. The default
     * implementation will attempt to get the workbench part's undo context. If
     * one is not found then a <code>ResourceUndoContext</code> based on the
     * resource of the input to this section will be returned. Subclasses may
     * override if a different undo context is required.
     * 
     * @return undo context.
     */
    protected IUndoContext getUndoContext() {
        IUndoContext context = null;
        IWorkbenchPart part = getPart();

        if (part != null) {
            context = (IUndoContext) part.getAdapter(IUndoContext.class);

            if (context == null) {
                context =
                        (IUndoContext) Platform.getAdapterManager()
                                .getAdapter(part, IUndoContext.class);
            }
        }

        // If context is not found from the part then create ResourceUndoContext
        if (context == null) {
            EObject input = getInput();
            if (input != null) {
                Resource resource = input.eResource();

                if (resource != null) {
                    context =
                            new ResourceUndoContext(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    resource);
                }
            }
        }

        return context;
    }

    /**
     * This is called by the resource set listener to determine whether the
     * notifications received affect this property section and thus requires a
     * refresh.
     * <p>
     * The default implementation checks for each notification in the list:
     * <ul>
     * <li>the notification changed the state of the model.</li>
     * <li>the notifier object is the input, or a direct or indirect content of
     * the input, of this section.</li>
     * </ul>
     * </p>
     * <p>
     * Subclasses may override this method.
     * </p>
     * 
     * @param notifications
     *            list of notification received by the resource set listener
     * @return <code>true</code> if the notifications affect this property
     *         section and therefore requires a refresh, <code>false</code>
     *         otherwise.
     */
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean refresh = false;

        if (input != null && notifications != null && !notifications.isEmpty()) {
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    Object notifier = notification.getNotifier();

                    if (notifier instanceof EObject) {
                        if (notifier == input
                                || EcoreUtil.isAncestor(input,
                                        (EObject) notifier)) {
                            refresh = true;
                            break;
                        }
                    }
                }
            }
        }

        return refresh;
    }

    /**
     * Resource set listener which will listen to post-commit changes.
     * 
     * @author njpatel
     * 
     */
    private class SectionResourceSetListener extends ResourceSetListenerImpl {
        @Override
        public boolean isPostcommitOnly() {
            // Only interested in post-commit changes
            return true;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {

            /*
             * If the input of this section has been removed then reset the
             * selection of the properties view - this will ensure that any tabs
             * showing for this input are removed.
             */
            if (isInputRemoved(event.getNotifications())) {
                final TabbedPropertySheetPage page = getPropertySheetPage();
                if (page != null) {
                    page.getSite().getShell().getDisplay()
                            .asyncExec(new Runnable() {

                                public void run() {
                                    if (page != null && !isPageDisposed(page)) {
                                        page.selectionChanged(getPart(),
                                                StructuredSelection.EMPTY);
                                    }
                                }

                                private boolean isPageDisposed(
                                        TabbedPropertySheetPage page) {
                                    Control c = page.getControl();
                                    return (c != null) ? c.isDisposed() : true;
                                }
                            });
                    // Section will be removed so no point doing anything else
                    return;
                }
            }

            // Check if the section should be refreshed
            if (shouldRefresh(event.getNotifications())) {
                // Ensure the refresh always happens in the UI thread
                IWorkbenchSite site = getSite();
                if (site != null) {
                    /*
                     * XPD-40 BEGIN - Don't asynchExec() unless we REALLY have
                     * to (because not running from UI thread.
                     * 
                     * Otherwise things that turn off refreshes by doing
                     * setIgnoreEvents(true then false) around execution of a
                     * command get gazumped and the refresh will run a bit later
                     * when it shouldn't do.
                     * 
                     * This, for instance, caused problems with timed command
                     * execution in text controls. BaseTestFieldHandler would
                     * turn off refreshes whilst updating the field BUT by the
                     * asynchExec() does the refresh() the ignore events is off
                     * again and the refresh goes ahead - hence screwing up the
                     * value in the text control.
                     */
                    if (Thread.currentThread().equals(site.getShell()
                            .getDisplay().getThread())) {
                        // Already running in UI thread.
                        refresh();
                    } else {
                        // Not running in UI thread.
                        site.getShell().getDisplay().asyncExec(new Runnable() {
                            public void run() {
                                refresh();
                            }
                        });
                    }
                    /* XPD-40 END */

                }
            }
        }

    }

    /**
     * Check if the input of this section has been removed.
     * <p>
     * If the input has been removed then return <code>true</code> - note that
     * this will empty the selection on the WHOLE TabbedPropertySheetPage (thus
     * wiping out all property tabs.
     * <p>
     * Therefore if you have a section whose input is NOT the main selection,
     * then you may wish to override this method to prevent all property tabs
     * getting wiped when your individual selection element has been removed.
     * 
     * @param notifications
     * @return <code>true</code> if the input to the section has been removed
     *         and hence all TabbedPropertySheetPage should have its selection
     *         unset.
     */
    protected boolean isInputRemoved(List<Notification> notifications) {
        EObject input = getInput();

        if (input != null) {
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    if (notification.getEventType() == Notification.REMOVE
                            && input.equals(notification.getOldValue())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Create a label in the given parent with the standard label width defined
     * for property sections (uses word wrap).
     * 
     * @param parent
     *            parent composite
     * @param toolkit
     *            form toolkit
     * @param text
     *            label text
     * @return <code>Label</code>
     */
    protected Label createLabel(Composite parent, XpdFormToolkit toolkit,
            String text) {
        Label lbl = toolkit.createLabel(parent, text, SWT.WRAP);
        GridData gData = new GridData();
        gData.widthHint = STANDARD_LABEL_WIDTH;
        lbl.setLayoutData(gData);

        return lbl;
    }

    protected void createCmdStackChangedForWizardRefreshListener() {
        if (cmdExecutedForWizardRefreshSectionListener == null) {
            EditingDomain editingDomain = getEditingDomain();
            if (editingDomain != null
                    && editingDomain.getCommandStack() != null) {
                cmdExecutedForWizardRefreshSectionListener =
                        new CommandStackListener() {
                            public void commandStackChanged(EventObject event) {
                                if (getInput() != null) {
                                    IWorkbench workbench =
                                            PlatformUI.getWorkbench();
                                    if (getSectionContainerType() == ContainerType.WIZARD
                                            && workbench != null) {
                                        workbench.getDisplay()
                                                .asyncExec(new Runnable() {
                                                    public void run() {
                                                        refresh();
                                                    }
                                                });

                                    }
                                }
                            }
                        };

                editingDomain
                        .getCommandStack()
                        .addCommandStackListener(cmdExecutedForWizardRefreshSectionListener);
            }
        }
        return;
    }

    protected Label createSectionHeading(Composite parent,
            XpdFormToolkit toolkit, String heading) {
        Label label = toolkit.createLabel(parent, heading);
        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;
        label.setLayoutData(data);
        label.setForeground(ColorConstants.darkGray);

        Font sectionHeaderFont =
                JFaceResources.getResources()
                        .createFont(FontDescriptor.createFrom("Arial", //$NON-NLS-1$
                                10,
                                SWT.BOLD));
        label.setFont(sectionHeaderFont);
        return label;
    }
}
