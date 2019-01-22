/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.presentation.resources.ui.internal.components;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.Implementation;
import com.tibco.xpd.presentation.channeltypes.Presentation;
import com.tibco.xpd.presentation.channeltypes.Target;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.XpdToolkit;

/**
 * @author Jan Arciuchiewicz
 */
public class TypeAssociationInfoControl extends Composite implements
        ISelectionChangedListener {

    private Text idContent;

    private Adapter adapter;

    private Text nameContent;

    private Text targetContent;

    private Text presetnationContent;

    private Text implementationContent;

    /**
     * @param parent
     * @param style
     */
    public TypeAssociationInfoControl(Composite parent, XpdToolkit toolkit) {
        super(parent, SWT.NONE);
        createContents(parent, toolkit);
    }

    protected void createContents(Composite parent, XpdToolkit toolkit) {
        GridLayout gridLayout = new GridLayout(2, false);
        this.setLayout(gridLayout);

        // Set the background colour of this control the same as the parent's
        setBackground(this.getBackground());

        Label id =
                toolkit.createLabel(this,
                        Messages.TypeAssociationInfoControl_id_label);
        idContent = toolkit.createText(this, "", //$NON-NLS-1$
                SWT.WRAP | SWT.READ_ONLY,
                "idContent"); //$NON-NLS-1$

        Label name = toolkit.createLabel(this, Messages.TypeAssociationInfoControl_ChannelName_label);
        nameContent = toolkit.createText(this, "", //$NON-NLS-1$
                SWT.WRAP | SWT.READ_ONLY,
                "nameContent"); //$NON-NLS-1$

        Label target = toolkit.createLabel(this, Messages.TypeAssociationInfoControl_ChannnelTarget_label);
        targetContent = toolkit.createText(this, "", //$NON-NLS-1$
                SWT.WRAP | SWT.READ_ONLY,
                "targetContent"); //$NON-NLS-1$

        Label presentation = toolkit.createLabel(this, Messages.TypeAssociationInfoControl_ChannelPresentation_label);
        presetnationContent = toolkit.createText(this, "", //$NON-NLS-1$
                SWT.WRAP | SWT.READ_ONLY,
                "presentationContent"); //$NON-NLS-1$

        Label implementation = toolkit.createLabel(this, Messages.TypeAssociationInfoControl_ChanelImplementation_label);
        implementationContent = toolkit.createText(this, "", //$NON-NLS-1$
                SWT.WRAP | SWT.READ_ONLY,
                "implementationContent"); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    public void selectionChanged(SelectionChangedEvent event) {
        ISelection selection = event.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            if (ss.getFirstElement() instanceof TypeAssociation) {
                final TypeAssociation typeAssociation =
                        (TypeAssociation) ss.getFirstElement();
                if (adapter == null || typeAssociation != adapter.getTarget()) {
                    if (adapter != null) {
                        adapter.getTarget().eAdapters().remove(adapter);
                    }
                    adapter = new Adapter() {

                        public Notifier getTarget() {
                            return typeAssociation;
                        }

                        public boolean isAdapterForType(Object type) {
                            return false;
                        }

                        public void notifyChanged(Notification notification) {
                            updateLabels(typeAssociation);

                        }

                        public void setTarget(Notifier newTarget) {
                        }

                    };
                    adapter.getTarget().eAdapters().add(adapter);
                }
                updateLabels(typeAssociation);
                return;
            }
        }
        if (adapter != null) {
            adapter.getTarget().eAdapters().remove(adapter);
            adapter = null;
        }
        updateLabels(null);
    }

    private void updateLabels(TypeAssociation typeAssociation) {
        if (!this.isDisposed()) {
            if (typeAssociation != null) {
                idContent.setText(typeAssociation.getDerivedId());
                nameContent.setText(getTypeName(typeAssociation));
                targetContent.setText(getTargetName(typeAssociation));
                presetnationContent
                        .setText(getPresentationName(typeAssociation));
                implementationContent
                        .setText(getImplemantationName(typeAssociation));
            } else {
                idContent.setText(""); //$NON-NLS-1$
                nameContent.setText(""); //$NON-NLS-1$
                targetContent.setText(""); //$NON-NLS-1$
                presetnationContent.setText(""); //$NON-NLS-1$
                implementationContent.setText(""); //$NON-NLS-1$
            }
            this.layout();
        }
    }

    /**
     * @param typeAssociation
     * @return
     */
    private String getImplemantationName(TypeAssociation typeAssociation) {
        ChannelType channelType = typeAssociation.getChannelType();
        if (channelType == null) {
            return ""; //$NON-NLS-1$
        }
        Implementation implementation = channelType.getImplementation();
        if (implementation == null) {
            return ""; //$NON-NLS-1$
        }
        String label = getTextFromLabelProvider(implementation);
        return label != null ? label : nullSafe(implementation.getName());
    }

    /**
     * @param typeAssociation
     * @return
     */
    private String getPresentationName(TypeAssociation typeAssociation) {
        ChannelType channelType = typeAssociation.getChannelType();
        if (channelType == null) {
            return ""; //$NON-NLS-1$
        }
        Presentation presentation = channelType.getPresentation();
        if (presentation == null) {
            return ""; //$NON-NLS-1$
        }
        String label = getTextFromLabelProvider(presentation);
        return label != null ? label : nullSafe(presentation.getName());
    }

    /**
     * @param typeAssociation
     * @return
     */
    private String getTargetName(TypeAssociation typeAssociation) {
        ChannelType channelType = typeAssociation.getChannelType();
        if (channelType == null) {
            return ""; //$NON-NLS-1$
        }
        Target target = channelType.getTarget();
        if (target == null) {
            return ""; //$NON-NLS-1$
        }
        String label = getTextFromLabelProvider(target);
        return label != null ? label : nullSafe(target.getName());
    }

    private String getTypeName(TypeAssociation typeAssociation) {
        ChannelType channelType = typeAssociation.getChannelType();
        if (channelType == null) {
            return ""; //$NON-NLS-1$
        }
        String label = getTextFromLabelProvider(channelType);
        return label != null ? label : nullSafe(channelType.getName());
    }

    /**
     * Converts null to empty string.
     */
    private String nullSafe(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }

    private String getTextFromLabelProvider(EObject eo) {
        AdapterFactory af = XpdResourcesPlugin.getDefault().getAdapterFactory();
        IItemLabelProvider labelProvider =
                (IItemLabelProvider) af.adapt(eo, IItemLabelProvider.class);
        if (labelProvider != null) {
            return labelProvider.getText(eo);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (adapter != null) {
            adapter.getTarget().eAdapters().remove(adapter);
        }
        super.dispose();
    }

}
