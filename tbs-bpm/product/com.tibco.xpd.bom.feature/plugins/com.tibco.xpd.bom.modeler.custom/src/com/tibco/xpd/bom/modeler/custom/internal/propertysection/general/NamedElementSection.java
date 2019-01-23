/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDiagramNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM property section for a {@link NamedElement}. This will allow the setting
 * of the name of the object.
 * 
 * @author njpatel
 * 
 */
public class NamedElementSection extends AbstractGeneralSection implements
        TextFieldVerifier {

    private Text nameTxt;

    private Text labelTxt;

    private final StereotypeChangeListener listener;

    private boolean ignoreEvents = false;

    private Composite parentComposite;

    private ActivityManagerListener activityListener;

    /**
     * plug-in contribution to check for capability.
     */
    private final IPluginContribution pluginContribution =
            new IPluginContribution() {

                @Override
                public String getLocalId() {
                    return "developer-name"; //$NON-NLS-1$
                }

                @Override
                public String getPluginId() {
                    return Activator.PLUGIN_ID;
                }

            };

    private Composite labelControls;

    private Composite nameControls;

    /**
     * Property section for a {@link NamedElement}.
     */
    public NamedElementSection() {
        listener = new StereotypeChangeListener();
        EditingDomain ed = getEditingDomain();
        if (ed instanceof TransactionalEditingDomain) {
            ((TransactionalEditingDomain) ed).addResourceSetListener(listener);
        }
        IActivityManager activityManager =
                PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager();

        if (activityManager != null) {
            activityListener = new ActivityManagerListener();
            activityManager.addActivityManagerListener(activityListener);
        }
    }

    @Override
    public void dispose() {
        EditingDomain ed = getEditingDomain();
        if (ed instanceof TransactionalEditingDomain) {
            ((TransactionalEditingDomain) ed)
                    .removeResourceSetListener(listener);
        }
        if (activityListener != null) {
            IActivityManager activityManager =
                    PlatformUI.getWorkbench().getActivitySupport()
                            .getActivityManager();

            if (activityManager != null) {
                activityManager.removeActivityManagerListener(activityListener);
            }
        }
        super.dispose();
    }

    @Override
    protected boolean shouldDisplay(EObject eo) {
        return eo instanceof NamedElement || eo instanceof Diagram;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        parentComposite = parent;

        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        /*
         * Add label controls
         */
        labelControls = toolkit.createComposite(root);
        labelControls.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));

        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        labelControls.setLayout(layout);

        createLabel(labelControls,
                toolkit,
                Messages.NamedElementSection_label_label);
        labelTxt = toolkit.createText(labelControls, "", "namedElement-label"); //$NON-NLS-1$ //$NON-NLS-2$
        setLayoutData(labelTxt);
        manageControlUpdateOnDeactivate(labelTxt);

        /*
         * Add name controls
         */

        nameControls = toolkit.createComposite(root);
        nameControls.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        nameControls.setLayout(layout);

        createLabel(nameControls,
                toolkit,
                Messages.NamedElementSection_name_label);
        nameTxt = toolkit.createText(nameControls, "", "namedElement-name"); //$NON-NLS-1$ //$NON-NLS-2$

        setLayoutData(nameTxt);
        manageControlUpdateOnDeactivate(nameTxt);

        // Update the name control visibility
        updateVisibility();

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        final EObject input = getInput();

        // Check if the current thread is not in a read only transaction already
        // as then it won't be possible to start another read/write one.
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(input);
        if (ted instanceof InternalTransactionalEditingDomain) {
            InternalTransaction activeTransaction =
                    ((InternalTransactionalEditingDomain) ted)
                            .getActiveTransaction();
            Thread current = Thread.currentThread();

            if ((activeTransaction != null)
                    && (activeTransaction.getOwner() == current)
                    && activeTransaction.isReadOnly()) {
                return null;
            }

        }

        if (input instanceof NamedElement) {
            if (obj == nameTxt) {
                String value = nameTxt.getText();
                if (value != null && value.length() > 0) {
                    cmd =
                            SetCommand
                                    .create(getEditingDomain(),
                                            input,
                                            UMLPackage.eINSTANCE
                                                    .getNamedElement_Name(),
                                            value);
                } else {
                    // Reset the name to current name from model as a blank name
                    // cannot be applied
                    if (input != null && input instanceof Association
                            && !(input instanceof AssociationClass)) {
                        cmd =
                                SetCommand.create(getEditingDomain(),
                                        input,
                                        UMLPackage.eINSTANCE
                                                .getNamedElement_Name(),
                                        SetCommand.UNSET_VALUE);

                    } else {
                        refresh();
                    }
                }
            } else if (obj == labelTxt) {
                final String labelValue = labelTxt.getText();

                TransactionalEditingDomain ed =
                        (TransactionalEditingDomain) getEditingDomain();

                if (labelValue != null && labelValue.length() > 0) {
                    /*
                     * Only return command if the label has changed
                     */
                    if (!labelValue.equals(PrimitivesUtil
                            .getDisplayLabel((NamedElement) input))) {
                        cmd = new RecordingCommand(ed) {
                            @Override
                            protected void doExecute() {
                                PrimitivesUtil
                                        .setDisplayLabel((NamedElement) input,
                                                labelValue);
                            }
                        };
                    }
                } else {

                    if (input != null && input instanceof Association
                            && !(input instanceof AssociationClass)) {

                        cmd = new RecordingCommand(ed) {
                            @Override
                            protected void doExecute() {
                                PrimitivesUtil
                                        .unsetDisplayLabel((NamedElement) input);

                            }
                        };

                    } else {

                        // Reset the label to current name from model as a blank
                        // name cannot be applied
                        refresh();
                    }
                }
            }
        }
        return cmd;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        boolean ret = super.shouldRefresh(notifications);

        // There is still a possibility though that a stereotype feature has
        // changed. For example the display label feature. In this case the
        // notifier will be a dynamic EObject.
        if (!ret) {
            for (Notification n : notifications) {
                if (n.getNotifier() instanceof DynamicEObjectImpl) {
                    Object feature = n.getFeature();

                    if (feature instanceof EAttribute) {
                        EAttribute att = (EAttribute) feature;

                        if (att.getName()
                                .equals(PrimitivesUtil.DISPLAY_LABEL_PROPERTY)) {

                            EObject input = getInput();

                            if (input instanceof NamedElement) {
                                NamedElement elem = (NamedElement) input;

                                EList<EObject> stereotypeApplications =
                                        elem.getStereotypeApplications();

                                for (EObject stApp : stereotypeApplications) {
                                    if (stApp == n.getNotifier()) {
                                        ret = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof CanvasPackageEditPart) {
            // If this is a Diagram then use the diagram as input (support for
            // user diagrams)
            Object model = ((CanvasPackageEditPart) object).getModel();
            if (model instanceof Diagram
                    && BomUIUtil.isUserDiagram((Diagram) model)) {
                return (EObject) model;
            }
        } else if (object instanceof BadgeDiagramNameEditPart) {
            // For a badge in the user diagram
            Object model = ((BadgeDiagramNameEditPart) object).getModel();
            if (model instanceof View) {
                return ((View) model).getElement();
            }
        }
        return super.resollveInput(object);
    }

    @Override
    protected void doRefresh() {
        ignoreEvents = true;
        try {

            if (labelTxt != null && !labelTxt.isDisposed()) {
                EObject input = getInput();
                // Disable the controls if this is a user diagram
                enableControls(!(input instanceof Diagram && BomUIUtil
                        .isUserDiagram((Diagram) input)));
                if (input instanceof Diagram) {
                    input = ((Diagram) input).getElement();
                }

                if (input instanceof NamedElement) {

                    /*
                     * Check if eContainer is null, i.e. we have an orphaned
                     * EObject. Can happen for deletion of links such as
                     * Generalization and Association. A Model does not have an
                     * eContainer remember.
                     */
                    if (input instanceof Model || input.eContainer() != null) {
                        NamedElement namedElement = (NamedElement) input;

                        updateText(nameTxt, namedElement.getName());
                        updateText(labelTxt,
                                PrimitivesUtil.getDisplayLabel(namedElement));
                    }

                }
            }
        } finally {
            ignoreEvents = false;
        }
    }

    /**
     * Set the state of the input controls in this section.
     * 
     * @param enable
     */
    private void enableControls(boolean enable) {
        if (labelTxt.isEnabled() != enable) {
            labelTxt.setEnabled(enable);
            nameTxt.setEnabled(enable);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.TextFieldVerifier#verifyText(org.eclipse.
     * swt.widgets.Event)
     */
    @Override
    public void verifyText(Event event) {

        EObject input2 = getInput();

        // Association is a special case as it can have optional label and/or
        // name
        if (input2 instanceof Association
                && !(input2 instanceof AssociationClass)) {

            Association assoc = (Association) input2;
            if (PrimitivesUtil.getDisplayLabel(assoc) == null) {
                return;
            }
        }
        if (!ignoreEvents) {
            /*
             * Generate name if required
             */
            if (event.widget == labelTxt
                    && (labelTxt.getText().equals(nameTxt.getText()) || nameTxt
                            .getText()
                            .equals(getNameFromLabel(labelTxt.getText())))) {

                String text =
                        labelTxt.getText(0, event.start - 1)
                                + event.text
                                + labelTxt.getText(event.end,
                                        labelTxt.getCharCount() - 1);
                nameTxt.setText(getNameFromLabel(text));
            }
        }
    }

    /**
     * Get the (internal) name from the label to store as the name of this
     * element.
     * 
     * @param label
     *            user-defined label of this element
     * @return name
     */
    private String getNameFromLabel(String label) {
        String name = label;
        EObject input = getInput();
        if (label != null && input instanceof NamedElement) {
            name =
                    PrimitivesUtil.getBOMInternalName(label,
                            false,
                            (NamedElement) input);
        }

        return name;
    }

    /**
     * Update the visibility of the Name controls in accordance with the
     * selected capability.
     */
    private void updateVisibility() {
        if (nameControls != null && !nameControls.isDisposed()) {
            if (isNameVisible()) {
                nameControls.setVisible(true);
                nameControls.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                        true, false));
            } else {
                nameControls.setVisible(false);
                nameControls.setLayoutData(new GridData(0, 0));
            }

            // Force layout on the section composite to hide/show the name
            // controls
            if (parentComposite != null && parentComposite.getParent() != null) {
                parentComposite.getParent().layout();
            }
        }
    }

    /**
     * Check if the name controls should be visible. This is determined by the
     * set capability.
     * 
     * @return <code>true</code> if the name controls should be visible,
     *         <code>false</code> otherwise.
     */
    private boolean isNameVisible() {
        return !WorkbenchActivityHelper.filterItem(pluginContribution);
    }

    /**
     * Activity manager listener that will update the visibility of the Name
     * controls in accordance with the selected capabilities (activities).
     * 
     * @author njpatel
     * 
     */
    private class ActivityManagerListener implements IActivityManagerListener {

        @Override
        public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {
            updateVisibility();
        }
    }

    /**
     * A resourceSet listener that will detect when a stereotype change on an
     * element occurs. This will then force the update of the property view's
     * header so that any image/label change can be applied. (Mainly for
     * First-class profile support.)
     * 
     * @author njpatel
     * 
     */
    private class StereotypeChangeListener extends ResourceSetListenerImpl {
        @Override
        public boolean isPostcommitOnly() {
            return true;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {

            boolean stereoTypeChanged = false;
            List<Notification> notifications = event.getNotifications();

            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    if (isStereotypeChange(notification)) {
                        /*
                         * XPD-4544: we need not refresh the header for each
                         * notification i guess. if the stereotype has changed
                         * for the given notification, then break out of this
                         * loop and refresh the header once. otherwise
                         * refreshing the header for each notification where the
                         * notifications size is large (in this case some 30K
                         * odd notifications), causes flickering in the UI
                         */
                        stereoTypeChanged = true;
                        break;
                    }
                }
            }

            /* Ensure the refresh always happens in the UI thread */
            if (stereoTypeChanged) {
                IWorkbenchSite site = getSite();
                if (site != null) {
                    site.getShell().getDisplay().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            refreshHeader();
                        }
                    });
                }
            }
        }

        /**
         * Check if a Stereotype has changed in the model.
         * 
         * @param notification
         * @return
         */
        private boolean isStereotypeChange(Notification notification) {
            if (notification != null
                    && notification.getNotifier() instanceof Resource) {
                Object oldValue = notification.getOldValue();
                Object newValue = notification.getNewValue();

                switch (notification.getEventType()) {
                case Notification.ADD:
                    return newValue instanceof DynamicEObjectImpl;
                case Notification.REMOVE:
                    return oldValue instanceof DynamicEObjectImpl;
                }
            }
            return false;
        }
    }

}
