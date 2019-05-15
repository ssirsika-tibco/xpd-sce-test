/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;

import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.ChannelsFactory;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.ChannelTypes;
import com.tibco.xpd.presentation.resources.ui.PresentationResourcesUIActivator;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Manages presentation artifacts.
 * <p>
 * <i>Created: 22 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class PresentationManager {

    /** Pull implementation ID. */
    public static final String IMPLEMANTATION_PULL = "Pull"; //$NON-NLS-1$

    /** Push implementation ID. */
    public static final String IMPLEMANTATION_PUSH = "Push"; //$NON-NLS-1$

    /** Workspace GI channel. */
    @Deprecated // Sid ACE-1132 no longer supported in ACE
    public static final String GI_GI_PULL = "GIGIPull"; //$NON-NLS-1$

    /** Workspace GWT channel. */
    @Deprecated // Sid ACE-1132 no longer supported in ACE
    public static final String GI_GWT_PULL = "GIGWTPull"; //$NON-NLS-1$

    /** Mobile GWT channel. */
    public static final String MOBILE_GWT_PULL = "MobileGWTPull"; //$NON-NLS-1$

    /** Email GI (push) channel. */
    @Deprecated // Sid ACE-1132 no longer supported in ACE
    public static final String EMAIL_GI_PUSH = "EmailGIPush"; //$NON-NLS-1$

    /** Openspace GWT channel. */
    public static final String OPENSPACE_GWT_PULL = "openspaceGWTPull"; //$NON-NLS-1$

    /** Openspace Email channel */
    public static final String OPENSPACE_EMAIL_PUSH = "openspaceEmailPush"; //$NON-NLS-1$

    /** Workspace Email GWT channel */
    // XPD-4003 : INvalid channel , will be removed at later stages.
    @Deprecated
    public static final String WORKSPACE_EMAIL_PUSH = "workspaceEmailPush"; //$NON-NLS-1$

    /** Default channel types. */
    /*
     * Sid ACE-1132 WOrkspace GWT and Workspace Email aren't supported in ACE so
     * remove them.
     */

    private static final String[] DEFAULT_CHANNEL_TYPES = new String[] {
            OPENSPACE_GWT_PULL, OPENSPACE_EMAIL_PUSH };

    private static final String DEFAULT_PREFERENCES_DIRNAME = ".settings"; //$NON-NLS-1$

    /** Channel types URI. */
    public static final String CHANNEL_TYPES_URI = "xpdextension:channelTypes"; //$NON-NLS-1$

    /** Extension for files with channels content. */
    public static final String CHANNELS_FILE_EXTENSION = "channels"; //$NON-NLS-1$

    /** Presentation special folder kind. */
    public static final String PRESENTATION_SPECIAL_FOLDER_KIND =
            "presentation"; //$NON-NLS-1$

    /** Singleton instance. */
    private static final PresentationManager INSTANCE =
            new PresentationManager();

    /** Project relative path to channels resource. */
    private static final String PROJECT_CHANNELS_FILE =
            "ProjectChannels.channels"; //$NON-NLS-1$

    private static Logger LOG =
            PresentationResourcesUIActivator.getDefault().getLogger();

    private Channels workspaceChannels;

    /**
     * Private constructor to prevent instantiation.
     */
    private PresentationManager() {
    }

    public static PresentationManager getInstance() {
        return INSTANCE;
    }

    /**
     * Returns all available channel types.
     * 
     * @return the channel types.
     */
    public ChannelTypes getChannelTypes() {
        Resource resource = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet()
                .getResource(URI.createURI(CHANNEL_TYPES_URI), true);
        return (ChannelTypes) resource.getContents().get(0);
    }

    /**
     * Sid ACE-1132 filter out unsupported channel types (Workspace general
     * interface, Workspace Google Web Toolkit, Workspace Email)
     * 
     * In theory we *should* have been able to simply remove the contributions
     * from com.tibco.xpd.presentation.resources.ui extensions. *However* this
     * caused BOM forms-GWT builder issues (throws up errors when building
     * projects with user tasks).
     * 
     * So instead, we simply filter them out of the UI for now.
     * 
     * @return The list of channel types with unsupported types removed.
     */
    public List<ChannelType> getAceAvailableChannelTypes() {
        EList<ChannelType> channelTypes =
                PresentationManager.getInstance().getChannelTypes()
                        .getChannelTypes(new BasicEList<ChannelDestination>(
                                PresentationManager.getChannelDestinations()));

        List<ChannelType> filtered = new ArrayList<>();

        for (ChannelType channelType : channelTypes) {
            if (isAceSupportedChannelType(channelType.getId())) {
                filtered.add(channelType);
            }
        }
        return filtered;
    }

    /**
     * @param channelTypeId
     * @return <code>true</code> if channel type is supported in ACE.
     */
    public boolean isAceSupportedChannelType(String channelTypeId) {
        if (PresentationManager.GI_GI_PULL.equals(channelTypeId)
                || PresentationManager.GI_GWT_PULL.equals(channelTypeId)
                || PresentationManager.EMAIL_GI_PUSH.equals(channelTypeId)) {
            return false;
        }
        return true;
    }

    /**
     * Returns channel type id given the components
     * 
     * @param target
     * @param presentation
     * @param implementation
     * @return
     */
    public String findChannelTypeIdByComponents(String target,
            String presentation, String implementation) {
        ChannelTypes channelTypes = getChannelTypes();
        List<ChannelType> types = channelTypes.getChannelTypes();
        for (ChannelType channelType : types) {
            if (channelType.getImplementation().getId().equals(implementation)
                    && channelType.getPresentation().getId()
                            .equals(presentation)
                    && channelType.getTarget().getId().equals(target)) {
                return channelType.getId();
            }
        }
        throw new IllegalArgumentException(
                "Channel type not found for the components provided"); //$NON-NLS-1$
    }

    /**
     * Returns channels defined for the project. The workspace channel will be
     * returned if there is no project specific setting for channels.
     * 
     * @param project
     *            the context project.
     */
    public Channels getChannels(IProject project) {
        if (project != null) {
            Channels projectChannels = getProjectChannels(project, false);
            if (projectChannels != null) {
                return projectChannels;
            }
        }
        // Fall back - get workspace channels.
        return getWorkspaceChannels();
    }

    public boolean isProjectChannels(Channels channels, IProject project) {
        Resource resource = channels.eResource();
        if (resource != null) {
            IFile channelsFile = getProjectChannelsFile(project);
            URI uri = URI.createPlatformResourceURI(
                    channelsFile.getFullPath().toPortableString(),
                    true);
            return uri.equals(resource.getURI());
        }
        return false;
    }

    public boolean isWorkspaceChannels(Channels channels) {
        Resource resource = channels.eResource();
        if (resource != null) {
            URI uri =
                    PresentationManager.getInstance().getWorkspaceChannelURI();
            return uri.equals(resource.getURI());
        }
        return false;
    }

    /**
     * Returns channels defined for the project. The <code>null</code> value
     * will be returned if no channels were defined (unless create parameter is
     * set to true) or channel definition is incorrect.
     * 
     * @param project
     *            the context project.
     * @param create
     *            if set to <code>true</code> and the channels definition for
     *            the project doesn't exist then default channels will be
     *            created.
     */
    public Channels getProjectChannels(IProject project, boolean create) {
        IFile channelsFile = getProjectChannelsFile(project);
        if (channelsFile != null && channelsFile.exists()) {
            try {
                URI uri = URI.createPlatformResourceURI(
                        channelsFile.getFullPath().toPortableString(),
                        true);
                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();
                final Resource r = ed.getResourceSet().getResource(uri, true);
                try {
                    Channels projectChannels = (Channels) ed.runExclusive(
                            new RunnableWithResult.Impl<Channels>() {
                                @Override
                                public void run() {
                                    setResult(
                                            (Channels) r.getContents().get(0));
                                }
                            });
                    return projectChannels;
                } catch (InterruptedException e) {
                    LOG.error(e);
                    throw e;
                }
            } catch (Exception e) {
                LOG.error(e);
                if (create) {
                    return createProjectDefaultChannels(project);
                }
            }
        } else if (create) {
            return createProjectDefaultChannels(project);
        }
        return null;
    }

    public boolean hasProjectSpecificChannelsSettings(IProject project) {
        IFile channelsFile = getProjectChannelsFile(project);
        if (channelsFile != null) {
            return channelsFile.isAccessible();
        }
        return false;
    }

    /**
     * Creates channels for the project adds to resource but doesnt't save.
     * Initial channels are copied from workspace.
     * 
     * @throws CoreExcetion
     *             if there is a problem with saving default channels.
     * @return the reference to the created default channels.
     */
    public Channels createProjectDefaultChannels(IProject project) {
        IFile channelsFile = getProjectChannelsFile(project);
        if (channelsFile != null) {
            final Channels projectChannels =
                    EcoreUtil.copy(getWorkspaceChannels());
            URI uri = URI.createPlatformResourceURI(
                    channelsFile.getFullPath().toPortableString(),
                    true);
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            final Resource r = ed.getResourceSet().createResource(uri);
            ed.getCommandStack().execute(new RecordingCommand(ed) {
                @Override
                protected void doExecute() {
                    r.getContents().clear();
                    r.getContents().add(projectChannels);
                }
            });
            return projectChannels;
        }
        return null;
    }

    private void saveProjectChannels(IFile channelsFile,
            final Channels projectChannels) throws CoreException {
        URI uri = URI.createPlatformResourceURI(
                channelsFile.getFullPath().toPortableString(),
                true);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        final Resource r = ed.getResourceSet().getResource(uri, false);
        ed.getCommandStack().execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                r.getContents().clear();
                r.getContents().add(projectChannels);
            }
        });

        RunnableWithResult.Impl<Object> op =
                new RunnableWithResult.Impl<Object>() {
                    @Override
                    public void run() {
                        try {
                            r.save(null);
                            setStatus(Status.OK_STATUS);
                        } catch (IOException e) {
                            setStatus(PresentationResourcesUIActivator
                                    .createStatusForException(e, null));
                        }
                    }
                };
        try {
            ed.runExclusive(op);
            if (op.getStatus() != null
                    && op.getStatus().getSeverity() == IStatus.ERROR) {
                throw new CoreException(op.getStatus());
            }
        } catch (InterruptedException e) {
            LOG.error(e);
        }
        channelsFile.refreshLocal(IResource.DEPTH_ZERO, null);
    }

    /**
     * Returns reference to the IFile in the project with the channels
     * definition. There is only one such file per project.
     * 
     * @param project
     *            the context project.
     * @return the IFile with the channels definition.
     */
    public IFile getProjectChannelsFile(IProject project) {
        IFolder settingsFolder = project.getFolder(DEFAULT_PREFERENCES_DIRNAME);
        /*
         * JA: XPD-2090 It doesn't look like the creation of the folder is
         * necessary and it's causing problems if called from working copy
         * creation listener as workspace is locked.
         */
        // ProjectUtil.createFolder(settingsFolder,
        // false,
        // new NullProgressMonitor());
        return settingsFolder.getFile(PROJECT_CHANNELS_FILE);
    }

    private Channels createWorkspaceDefaultChannels() {
        ChannelsFactory f = ChannelsFactory.eINSTANCE;
        final Channels channels = f.createChannels();
        Channel defaultChannel = f.createChannel();
        defaultChannel.setDefault(true);
        defaultChannel.setDisplayName(
                Messages.PresentationManager_DefaultChannelName);
        ChannelTypes channelTypes = getChannelTypes();
        List<ChannelType> associatedTypes = new ArrayList<ChannelType>();
        for (String channelTypeString : DEFAULT_CHANNEL_TYPES) {
            ChannelType ct = channelTypes.getChannelType(channelTypeString);
            if (ct != null) {
                associatedTypes.add(ct);
            } else {
                LOG.error(String.format(
                        "Undefined channel type set as default (%1$s)", //$NON-NLS-1$
                        ct));
            }
        }
        for (ChannelType channelType : associatedTypes) {
            if (channelType != null) {
                TypeAssociation typeAssoc = f.createTypeAssociation();
                typeAssoc.setChannelType(channelType);
                defaultChannel.getTypeAssociations().add(typeAssoc);
            }
        }
        channels.getChannels().add(defaultChannel);
        return channels;
    }

    public static void saveChannels(Channels channels) throws CoreException {
        IFile file = WorkspaceSynchronizer.getFile(channels.eResource());
        if (file != null) {
            getInstance().saveProjectChannels(file, channels);
        } else {
            synchronized (INSTANCE) {
                if (channels == getInstance().getWorkspaceChannels()) {
                    getInstance().saveWorkspaceChannels();
                }
            }
        }
    }

    public static void reloadChannels(Channels channels) throws CoreException {
        final Resource r = channels.eResource();
        if (r != null && WorkspaceSynchronizer.getFile(r) != null) {
            if (r.isLoaded()) {
                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();
                ed.getCommandStack().execute(new RecordingCommand(ed) {
                    @Override
                    protected void doExecute() {
                        r.unload();
                    }
                });
            }
        } else {
            synchronized (INSTANCE) {
                if (channels == getInstance().getWorkspaceChannels()) {
                    getInstance().reloadWorkspaceChannels();
                }
            }
        }
    }

    public Channels getWorkspaceChannels() {
        if (workspaceChannels == null) {
            try {
                final Resource r = XpdResourcesPlugin.getDefault()
                        .getEditingDomain().getResourceSet()
                        .getResource(getWorkspaceChannelURI(), true);
                if (!r.getContents().isEmpty()) {
                    TransactionalEditingDomain ed =
                            XpdResourcesPlugin.getDefault().getEditingDomain();
                    ed.runExclusive(new Runnable() {
                        @Override
                        public void run() {
                            if (workspaceChannels == null) {
                                workspaceChannels =
                                        (Channels) r.getContents().get(0);
                            }
                        }
                    });
                } else {
                    resetWorkspaceChannelsToDefault();
                    saveWorkspaceChannels();
                }
            } catch (Exception e) {
                resetWorkspaceChannelsToDefault();
                saveWorkspaceChannels();
            }
        }
        return workspaceChannels;
    }

    public synchronized void resetWorkspaceChannelsToDefault() {
        // Problem with loading: Create and save default channels.
        final Resource r = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet().getResource(getWorkspaceChannelURI(), false);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        ed.getCommandStack().execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                workspaceChannels = createWorkspaceDefaultChannels();
                r.getContents().clear();
                r.getContents().add(workspaceChannels);
            }

        });
    }

    public synchronized void reloadWorkspaceChannels() {
        workspaceChannels = null;
        final Resource r = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet().getResource(getWorkspaceChannelURI(), false);
        if (r.isLoaded()) {
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            ed.getCommandStack().execute(new RecordingCommand(ed) {
                @Override
                protected void doExecute() {
                    r.unload();
                }
            });
        }
    }

    public synchronized void saveWorkspaceChannels() {
        if (workspaceChannels != null) {
            final Resource r = XpdResourcesPlugin.getDefault()
                    .getEditingDomain().getResourceSet()
                    .getResource(getWorkspaceChannelURI(), false);
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            RunnableWithResult.Impl<Boolean> saveOp =
                    new RunnableWithResult.Impl<Boolean>() {
                        @Override
                        public void run() {
                            try {
                                r.save(null);
                                setStatus(Status.OK_STATUS);
                                setResult(Boolean.TRUE);
                            } catch (IOException e) {
                                setStatus(PresentationResourcesUIActivator
                                        .createStatusForException(e,
                                                "Unexpected error while saving workspace channels.")); //$NON-NLS-1$

                                setResult(Boolean.FALSE);
                            }
                        }
                    };
            try {
                if (!((Boolean) ed.runExclusive(saveOp)).booleanValue()) {
                    PresentationResourcesUIActivator.getDefault().getLog()
                            .log(saveOp.getStatus());
                }
            } catch (InterruptedException e) {
                LOG.error(e);
            }
        }
    }

    public static URI getWorkspaceChannelURI() {
        return URI.createFileURI(PresentationResourcesUIActivator.getDefault()
                .getStateLocation().append("WorkspaceChannels.channels") //$NON-NLS-1$
                .toPortableString());
    }

    /**
     * Gets all available channel destinations. Channel destination is
     * contributed from extension. and channel types can be bound to it.
     * 
     * @return a collection of all available channel destinations.
     */
    public static Collection<ChannelDestination> getChannelDestinations() {
        // fetch available Destinations
        List<ChannelDestination> channelDestinations = PresentationManager
                .getInstance().getChannelTypes().getChannelDestinations();

        return channelDestinations;
    }

    /**
     * Gets all available channel destinations. Channel destination is
     * contributed from extension. and channel types can be bound to it.
     * 
     * @return a collection of all available channel destinations.
     */
    public static Collection<ChannelDestination> getChannelDestinationsByIds(
            String... destinationIds) {

        List<ChannelDestination> channelDestinations = PresentationManager
                .getInstance().getChannelTypes().getChannelDestinations();
        BasicEList<ChannelDestination> filteredtList =
                new BasicEList<ChannelDestination>();
        for (ChannelDestination channelDestination : channelDestinations) {
            for (String destinationId : destinationIds) {
                if (destinationId.equals(channelDestination.getId())) {
                    filteredtList.add(channelDestination);
                }
            }
        }
        return filteredtList;
    }
}
