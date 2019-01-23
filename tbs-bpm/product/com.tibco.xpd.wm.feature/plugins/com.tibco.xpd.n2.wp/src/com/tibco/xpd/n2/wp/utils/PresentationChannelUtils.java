/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.wp.utils;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
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

/**
 * Provides utility method for Presentation Channels.
 * 
 * @author aprasad
 * @since 12 Nov 2012
 */
public class PresentationChannelUtils {

    /**
     * Removes given channel Type from the Channel, if exists. If channelType is
     * null , then searches for a channel type with null id. XPD-4003: to remove
     * the invalid channel whose extension is now removed, hence channel type id
     * might be null.
     * 
     * @param ed
     * @param channel
     * @param channelType
     * @return
     */
    public static boolean removeChannelTypeFromChannel(
            TransactionalEditingDomain ed, final Channel channel,
            ChannelType channelType) {
        boolean found = false;
        /*
         * get all associated channel types and look for given channel type
         */
        for (Iterator typeAssocIterator =
                channel.getTypeAssociations().iterator(); typeAssocIterator
                .hasNext();) {
            final TypeAssociation ta =
                    (TypeAssociation) typeAssocIterator.next();

            /* remove channel type if found */
            if ((ta.getChannelType() == channelType)
                    || (channelType == null && ta.getChannelType().getId() == null)) {
                // XPD-4003: to remove the invalid channel whose extension is
                // now removed, hence channel type id might be null.
                ed.getCommandStack().execute(new RecordingCommand(ed) {
                    @Override
                    protected void doExecute() {
                        channel.getTypeAssociations().remove(ta);
                    }

                });
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Add given Channel Type to the channel.
     * 
     * @param ed
     * @param channel
     * @param channelType
     */
    public static void addChannelTypeToChannel(TransactionalEditingDomain ed,
            final Channel channel, final ChannelType channelType) {
        ed.getCommandStack().execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                ChannelsFactory factory = ChannelsFactory.eINSTANCE;
                TypeAssociation typeAssoc = factory.createTypeAssociation();
                typeAssoc.setChannelType(channelType);
                channel.getTypeAssociations().add(typeAssoc);
            }

        });
    }

    /**
     * Replaces one channel type associations (with a type id oldChannelName) to
     * another (with a type id newChannelName) in ALL channels.
     * 
     * @param ed
     *            a context EditingDomain.
     * @param project
     *            context project.
     * @param oldChannelName
     *            the id of the channel type to be replaced.
     * @param newChannelName
     *            the id of the channel type to replace oldChannelName type.
     */
    public static void replaceChannelType(TransactionalEditingDomain ed,
            IProject project, String oldChannelName, String newChannelName) {
        replaceChannelType(ed, project, oldChannelName, newChannelName,
        /* defaulChannelOnly = */false);
    }

    /**
     * Replaces one channel type associations (with a type id oldChannelName) to
     * another (with a type id newChannelName) in ALL channels.
     * 
     * @param ed
     *            a context EditingDomain.
     * @param project
     *            context project.
     * @param oldChannelName
     *            the id of the channel type to be replaced.
     * @param newChannelName
     *            the id of the channel type to replace oldChannelName type.
     * @param defaultChannelOnly
     *            if 'true' then the type will be replaced only in default
     *            channel (otherwise is will replace the type in all channels).
     */
    public static void replaceChannelType(TransactionalEditingDomain ed,
            IProject project, String oldChannelName, String newChannelName,
            boolean defaultChannelOnly) {
        /* get 'Workspace GI' channel type */
        ChannelType oldChannelType =
                PresentationManager.getInstance().getChannelTypes()
                        .getChannelType(oldChannelName);
        /* get 'Workspace GWT' channel type */
        ChannelType newChannelType =
                PresentationManager.getInstance().getChannelTypes()
                        .getChannelType(newChannelName);
        Channels channels =
                PresentationManager.getInstance().getChannels(project);
        /*
         * iterate through all the channels
         */
        for (Iterator channelIterator = channels.getChannels().iterator(); channelIterator
                .hasNext();) {
            Channel channel = (Channel) channelIterator.next();
            if (defaultChannelOnly && !channel.isDefault()) {
                continue;
            }
            // if channel contains old channel type, remove it and add
            // new channel type.
            if (PresentationChannelUtils.removeChannelTypeFromChannel(ed,
                    channel,
                    oldChannelType)) {
                // add channelType if it does not already exists
                boolean channelAlreadyExists = false;
                for (TypeAssociation typeAsso : channel.getTypeAssociations()) {
                    if (newChannelType.getId().equals(typeAsso.getChannelType()
                            .getId())) {
                        channelAlreadyExists = true;
                        break;
                    }
                }
                if (!channelAlreadyExists) {
                    PresentationChannelUtils.addChannelTypeToChannel(ed,
                            channel,
                            newChannelType);
                }
            }

        }
        try {
            PresentationManager.getInstance().saveChannels(channels);
            project.touch(new NullProgressMonitor());
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove Invalid channel Type for Project.If channelType is null , then
     * searches for a channel type with null id. XPD-4003: to remove the invalid
     * channel whose extension is now removed, hence channel type id might be
     * null.
     * 
     * @param ed
     * @param project
     * @param channels
     * @param oldChannelName
     * @param newChannelName
     */
    public static void removeInvalidChannelType(TransactionalEditingDomain ed,
            IProject project, ChannelType channelType) {

        Channels channels =
                PresentationManager.getInstance().getChannels(project);
        /*
         * iterate through all the channels
         */
        for (Iterator channelIterator = channels.getChannels().iterator(); channelIterator
                .hasNext();) {
            Channel channel = (Channel) channelIterator.next();
            // if channel contains channel type, remove it
            removeChannelTypeFromChannel(ed, channel, channelType);
        }
        try {
            PresentationManager.getInstance().saveChannels(channels);
            project.touch(new NullProgressMonitor());
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
