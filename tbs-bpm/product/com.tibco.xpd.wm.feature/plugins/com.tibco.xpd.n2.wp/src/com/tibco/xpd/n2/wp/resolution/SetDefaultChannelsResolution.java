/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.wp.resolution;

import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.ChannelsFactory;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * @author bharge
 * 
 */
public class SetDefaultChannelsResolution implements IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    public void run(IMarker marker) throws ResolutionException {

        IResource resource = marker.getResource();

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        if (resource instanceof IProject) {

            IProject project = (IProject) resource;

            Channels channels =
                    PresentationManager.getInstance().getChannels(project);

            /*
             * removing a workspace gi channel type from a default channel.
             */
            removeWSGIChannelTypeFromDefaultChannel(ed, channels);

            /*
             * add a workspace gwt channel type for a default channel.
             */
            addWSGWTChannelTypeForDefaultChannel(ed, channels);

            try {
                PresentationManager.getInstance().saveChannels(channels);
                project.touch(new NullProgressMonitor());
            } catch (CoreException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * @param ed
     * @param channels
     */
    private void removeWSGIChannelTypeFromDefaultChannel(
            TransactionalEditingDomain ed, Channels channels) {
        /*
         * iterate thru all the channels; for a default channel find a type
         * association that has workspace gi channel type
         */
        for (Iterator channelIterator = channels.getChannels().iterator(); channelIterator
                .hasNext();) {
            final Channel channel = (Channel) channelIterator.next();
            if (channel.isDefault()) {
                /*
                 * get all associated channel types and look for workspace gi
                 * channel type
                 */
                for (Iterator typeAssocIterator =
                        channel.getTypeAssociations().iterator(); typeAssocIterator
                        .hasNext();) {
                    final TypeAssociation ta =
                            (TypeAssociation) typeAssocIterator.next();
                    /* get Workspace GI channel type */
                    ChannelType giChannelType =
                            PresentationManager
                                    .getInstance()
                                    .getChannelTypes()
                                    .getChannelType(PresentationManager.GI_GI_PULL);

                    /* remove workspace gi channel type if found */
                    if (ta.getChannelType() == giChannelType) {

                        ed.getCommandStack().execute(new RecordingCommand(ed) {

                            @Override
                            protected void doExecute() {
                                channel.getTypeAssociations().remove(ta);
                            }

                        });
                        break;
                    }
                }
            }
        }
    }

    /**
     * @param ed
     * @param channels
     */
    private void addWSGWTChannelTypeForDefaultChannel(
            TransactionalEditingDomain ed, Channels channels) {

        Channel defaultChannel = null;
        boolean gwtChannelFound = false;

        /*
         * iterate thru all the channels; for a default channel find a type
         * association that has workspace gwt channel type
         */
        for (final Channel channel : channels.getChannels()) {
            if (channel.isDefault()) {
                /*
                 * get all associated channel types and look for workspace gwt
                 * channel type
                 */
                for (TypeAssociation ta : channel.getTypeAssociations()) {
                    /* get workspace gwt channel type */
                    ChannelType gwtChannelType =
                            PresentationManager
                                    .getInstance()
                                    .getChannelTypes()
                                    .getChannelType(PresentationManager.GI_GWT_PULL);
                    defaultChannel = channel;
                    /*
                     * if workspace gwt channel is found, dont do anything, if
                     * not found we need to add
                     */
                    if (ta.getChannelType() == gwtChannelType) {
                        gwtChannelFound = true;
                    }
                }
            }
        }

        /* add workspace gwt channel if it is not found */
        if (!gwtChannelFound) {
            addGWTChannel(defaultChannel, ed);
        }
    }

    /**
     * @param defaultChannel
     * @param ed
     */
    private void addGWTChannel(final Channel defaultChannel,
            TransactionalEditingDomain ed) {
        ed.getCommandStack().execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                ChannelsFactory factory = ChannelsFactory.eINSTANCE;
                TypeAssociation typeAssoc = factory.createTypeAssociation();
                ChannelType gwtChannelType =
                        PresentationManager
                                .getInstance()
                                .getChannelTypes()
                                .getChannelType(PresentationManager.GI_GWT_PULL);
                typeAssoc.setChannelType(gwtChannelType);
                defaultChannel.getTypeAssociations().add(typeAssoc);
            }

        });

    }

}
