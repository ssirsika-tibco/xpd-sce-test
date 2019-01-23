/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.components;

import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.resources.ui.PresentationResourcesUIActivator;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.presentation.resources.ui.internal.wizards.NewChannelWizard;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerEditAction;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Control for managing presentation channels.
 * <p>
 * <i>Created: 27 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ChannelsControl extends BaseTreeControl {

    // private Channels channels;

    // protected Channel selectedChannel = null;

    private SetAsDefaultChannelAction setAsDefaultAction;

    /**
     * Creates control.
     * 
     * @param parent
     *            the parent composite.
     * @param toolkit
     *            the toolkit used to create controls.
     * @param channels
     *            the context channels container.
     */
    public ChannelsControl(Composite parent, XpdToolkit toolkit,
            Channels channels) {
        super(parent, toolkit, channels);
        this.setChannels(channels);
    }

    /**
     * @param channels
     *            the channels to set
     */
    public void setChannels(Channels channels) {
        getViewer().setInput(channels);
    }

    /**
     * @return the channels
     */
    public Channels getChannels() {
        return (Channels) getViewer().getInput();
    }

    @Override
    protected void createContents(Composite parent, XpdToolkit toolkit,
            Object viewerInput) {
        super.createContents(parent, toolkit, viewerInput);
        getViewer().addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                if (event.getSelection() instanceof StructuredSelection) {
                    IStructuredSelection s =
                            (IStructuredSelection) event.getSelection();
                    Object fe = s.getFirstElement();
                    Channel selectedChannel = null;
                    if (fe instanceof Channel) {
                        selectedChannel = (Channel) fe;
                    } else if (fe instanceof TypeAssociation) {
                        selectedChannel = ((TypeAssociation) fe).getChannel();
                    }
                    if (selectedChannel != null) {
                        editChannel(selectedChannel);
                    }
                }

            }

        });
    }

    protected void editChannel(Channel selectedChannel) {
        NewChannelWizard channelWizard = new NewChannelWizard();
        channelWizard.setModel(getChannels());
        channelWizard.setChannel(selectedChannel);
        channelWizard
                .setWindowTitle(Messages.ChannelsControl_editPresentationChannel_title);
        WizardDialog dialog =
                new WizardDialog(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell(), channelWizard);
        dialog.open();
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new ViewerAddAction(viewer) {
            /** {@inheritDoc} */
            @Override
            public void run() {
                NewChannelWizard channelWizard = new NewChannelWizard();
                channelWizard.setModel(getChannels());
                channelWizard
                        .setWindowTitle(Messages.ChannelsControl_newPresentationChannel_title);
                WizardDialog dialog =
                        new WizardDialog(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                channelWizard);
                dialog.open();
                // channelsViewer.refresh();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerEditAction createEditAction(ColumnViewer viewer) {
        return new ViewerEditAction(viewer) {
            /** {@inheritDoc} */
            @Override
            public void run() {
                Object fe = selection.getFirstElement();
                Channel selectedChannel = null;
                if (fe instanceof Channel) {
                    selectedChannel = (Channel) fe;
                } else if (fe instanceof TypeAssociation) {
                    selectedChannel = ((TypeAssociation) fe).getChannel();
                }
                if (selectedChannel != null) {
                    editChannel(selectedChannel);
                }
            }

            /** {@inheritDoc} */
            @Override
            protected boolean canEdit(IStructuredSelection selection) {
                return selection.getFirstElement() instanceof Channel
                        || selection.getFirstElement() instanceof TypeAssociation;
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        if (movableFeatures == null) {
            movableFeatures = super.getMovableFeatures();
            movableFeatures.add(ChannelsPackage.Literals.CHANNELS__CHANNELS);
            movableFeatures
                    .add(ChannelsPackage.Literals.CHANNEL__TYPE_ASSOCIATIONS);
        }
        return movableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    protected Set<EStructuralFeature> getDeletableFeatures() {
        if (deletableFeatures == null) {
            deletableFeatures = super.getMovableFeatures();
            deletableFeatures.add(ChannelsPackage.Literals.CHANNELS__CHANNELS);
            deletableFeatures
                    .add(ChannelsPackage.Literals.CHANNEL__TYPE_ASSOCIATIONS);
        }
        return deletableFeatures;
    }

    @Override
    protected IContentProvider getViewerContentProvider() {
        IContentProvider provider =
                new TransactionalAdapterFactoryContentProvider(
                        XpdResourcesPlugin.getDefault().getEditingDomain(),
                        XpdResourcesPlugin.getDefault().getAdapterFactory()) {
                    /**
                     * @see org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider#getChildren(java.lang.Object)
                     * 
                     * @param object
                     * @return
                     */
                    @Override
                    public Object[] getChildren(Object object) {
                        if (object instanceof Channel) {
                            Channel channel = (Channel) object;
                            return channel
                                    .getTypeAssociations(new BasicEList<ChannelDestination>(
                                            PresentationManager
                                                    .getChannelDestinations()))
                                    .toArray();
                        }
                        return super.getChildren(object);
                    }
                };
        if (!CapabilityUtil.isDeveloperActivityEnabled()) {
            provider =
                    new TransactionalAdapterFactoryContentProvider(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            XpdResourcesPlugin.getDefault().getAdapterFactory()) {
                        @Override
                        public boolean hasChildren(Object object) {
                            return false;
                        }
                    };
        }
        return provider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ILabelProvider getViewerLabelProvider() {
        if (viewerLabelProvider == null) {
            viewerLabelProvider =
                    new TransactionalAdapterFactoryLabelProvider(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            XpdResourcesPlugin.getDefault().getAdapterFactory()) {
                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Image getImage(final Object object) {
                            Image image =
                                    run(new RunnableWithResult.Impl<Image>() {
                                        @Override
                                        public void run() {
                                            if (object instanceof TypeAssociation
                                                    && ((TypeAssociation) object)
                                                            .getChannelType() != null
                                                    && ((TypeAssociation) object)
                                                            .getChannelType()
                                                            .eIsProxy()) {
                                                setResult(PlatformUI
                                                        .getWorkbench()
                                                        .getSharedImages()
                                                        .getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
                                            } else {
                                                setResult(null);
                                            }
                                        }
                                    });
                            return (image != null) ? image : super
                                    .getImage(object);
                        }
                    };

        }
        return viewerLabelProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new ViewerDeleteEMFAction(viewer, getDeletableFeatures()) {
            /**
             * {@inheritDoc}
             */
            @Override
            protected boolean canDelete(IStructuredSelection selection) {
                // Check if selection contains default channels or its
                // components.
                if (selection != null && !selection.isEmpty()) {
                    for (Object o : selection.toList()) {
                        if (o instanceof Channel) {
                            final Channel channel = (Channel) o;
                            if (channel.isDefault()) {
                                return false;
                            }
                        } else if (o instanceof TypeAssociation) {
                            final Channel channel =
                                    ((TypeAssociation) o).getChannel();
                            if (channel.isDefault()) {
                                return false;
                            }
                        }
                    }
                }
                return super.canDelete(selection);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createActions(ColumnViewer viewer) {
        super.createActions(viewer);
        setAsDefaultAction = new SetAsDefaultChannelAction(getViewer());
        viewer.addSelectionChangedListener(setAsDefaultAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillViewerButtonsBar(IContributionManager manager,
            ColumnViewer viever) {
        super.fillViewerButtonsBar(manager, viever);
        manager.appendToGroup(ADDITIONS_MARKER, setAsDefaultAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillViewerContextMenu(IMenuManager manager,
            ColumnViewer columnViewer) {
        super.fillViewerContextMenu(manager, columnViewer);
        manager.add(setAsDefaultAction);
    }

    public static class SetAsDefaultChannelAction extends ViewerAction {

        private static final String SET_AS_DEFAULT_ICON =
                "/icons/obj16/check.gif"; //$NON-NLS-1$

        public SetAsDefaultChannelAction(StructuredViewer viewer) {
            super(
                    viewer,
                    Messages.ChannelsControl_setAsDefault_label,
                    PresentationResourcesUIActivator
                            .imageDescriptorFromPlugin(PresentationResourcesUIActivator.PLUGIN_ID,
                                    SET_AS_DEFAULT_ICON));
            setToolTipText(Messages.ChannelsControl_setAsDefault_tooltip);
            selectionChanged((IStructuredSelection) viewer.getSelection());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void selectionChanged(IStructuredSelection selection) {
            if (selection.size() == 1
                    && selection.getFirstElement() instanceof Channel) {
                Channel channel = (Channel) selection.getFirstElement();
                if (!channel.isDefault()) {
                    setEnabled(true);
                    return;
                }
            }
            setEnabled(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            IStructuredSelection selection =
                    (IStructuredSelection) getViewer().getSelection();
            if (selection.size() == 1
                    && selection.getFirstElement() instanceof Channel) {
                Channel channel = (Channel) selection.getFirstElement();
                if (!channel.isDefault()) {
                    if (canSetAsDefault(channel)) {
                        setAsDefault(channel);
                    }
                }
            }
        }

        /**
         * @param channel
         * @return
         */
        private boolean canSetAsDefault(Channel channel) {
            if (channel.getTypeAssociations().size() > 0) {
                // XPD-3657 Workspace EMail type should not be selectable in
                // the default channel list in the Studio
                if (isTypeAssociated(PresentationManager.EMAIL_GI_PUSH, channel)) {
                    Shell shell =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getShell();
                    MessageDialog
                            .openError(shell,
                                    Messages.ChannelsControl_settingDefaultError_title,
                                    String.format(Messages.ChannelsControl_ChannelTypeSettingDefaultError_message,
                                            PresentationResourcesUIActivator
                                                    .getChannelTypes()
                                                    .getChannelType(PresentationManager.EMAIL_GI_PUSH)
                                                    .getDisplayName()));
                    return false;
                }
                return true;
            }
            Shell shell =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getShell();
            MessageDialog.openError(shell,
                    Messages.ChannelsControl_settingDefaultError_title,
                    Messages.ChannelsControl_settingDefaultError_message);
            return false;
        }

        /**
         * @param channelTypeID
         * @param channel
         */
        private boolean isTypeAssociated(String channelTypeID, Channel channel) {
            for (TypeAssociation typeAssociation : channel
                    .getTypeAssociations()) {
                if (typeAssociation.getChannelType() != null
                        && channelTypeID.equals(typeAssociation
                                .getChannelType().getId())) {
                    return true;
                }
            }
            return false;
        }

        /**
         * @param channel
         */
        private void setAsDefault(final Channel channel) {
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            ed.getCommandStack().execute(new RecordingCommand(ed) {
                @Override
                protected void doExecute() {
                    Channels channels = (Channels) channel.eContainer();
                    if (channels != null) {
                        for (Channel channel : channels.getChannels()) {
                            if (channel.isDefault()) {
                                channel.setDefault(false);
                            }
                        }
                    }
                    channel.setDefault(true);
                }
            });

        }

    }
}
