/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.presentation.resources.ui.internal.components;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.XpdToolkit;

/**
 * @author Jan Arciuchiewicz
 */
public class ChannelInfoControl extends Composite implements
        ISelectionChangedListener {

    private Label labelContent;

    private Label nameContent;

    private Adapter adapter;

    private Text defaultContent;

    /**
     * @param parent
     * @param style
     */
    public ChannelInfoControl(Composite parent, XpdToolkit toolkit) {
        super(parent, SWT.NONE);
        createContents(parent, toolkit);
    }

    protected void createContents(Composite parent, XpdToolkit toolkit) {
        GridLayout gridLayout = new GridLayout(2, false);
        this.setLayout(gridLayout);

        // Set the background colour of this control the same as the parent's
        setBackground(this.getBackground());

        toolkit.createLabel(this, Messages.ChannelInfoControl_label_label);
        labelContent = toolkit.createLabel(this, ""); //$NON-NLS-1$
        toolkit.createLabel(this, Messages.ChannelInfoControl_name_label);
        nameContent = toolkit.createLabel(this, ""); //$NON-NLS-1$
        toolkit.createLabel(this, Messages.ChannelInfoControl_default_label);
        defaultContent = toolkit.createText(this, "", //$NON-NLS-1$
                SWT.WRAP | SWT.READ_ONLY,
                "defaultContent"); //$NON-NLS-1$
        // GridDataFactory.swtDefaults().span(2, 1).applyTo(defaultContent);
    }

    /**
     * {@inheritDoc}
     */
    public void selectionChanged(SelectionChangedEvent event) {
        ISelection selection = event.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            if (ss.getFirstElement() instanceof Channel) {
                final Channel channel = (Channel) ss.getFirstElement();
                if (adapter == null || channel != adapter.getTarget()) {
                    if (adapter != null) {
                        adapter.getTarget().eAdapters().remove(adapter);
                    }
                    adapter = new Adapter() {

                        public Notifier getTarget() {
                            return channel;
                        }

                        public boolean isAdapterForType(Object type) {
                            return false;
                        }

                        public void notifyChanged(Notification notification) {
                            updateLabels(channel);

                        }

                        public void setTarget(Notifier newTarget) {
                        }

                    };
                    adapter.getTarget().eAdapters().add(adapter);
                }
                updateLabels(channel);
                return;
            }
        }
        if (adapter != null) {
            adapter.getTarget().eAdapters().remove(adapter);
            adapter = null;
        }
        updateLabels(null);
    }

    private void updateLabels(Channel channel) {
        if (!this.isDisposed()) {
            if (channel != null) {
                labelContent.setText(channel.getDisplayName());
                nameContent.setText(channel.getName());
                defaultContent
                        .setText(channel.isDefault() ? Messages.ChannelInfoControl_true_label
                                : Messages.ChannelInfoControl_false_label);
            } else {
                labelContent.setText(""); //$NON-NLS-1$
                nameContent.setText(""); //$NON-NLS-1$
                defaultContent.setText(""); //$NON-NLS-1$
            }
            this.layout();
        }
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
