/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.presentation.resources.ui.internal.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.presentation.channels.AttributeValue;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.resources.ui.PresentationResourcesUIActivator;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.presentation.resources.ui.internal.components.ChannelsEditorControl;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.resources.ui.components.PropertyAndPreferencePage;

/**
 * @author Jan Arciuchiewicz
 */
public class ChannelsPreferencePage extends PropertyAndPreferencePage {
    private static Logger LOG = PresentationResourcesUIActivator.getDefault()
            .getLogger();

    public static final String PROP_ID =
            "com.tibco.xpd.presentation.resources.ui.channelsPropertyPage"; //$NON-NLS-1$

    public static final String PREF_ID =
            "com.tibco.xpd.presentation.resources.ui.channelsPreferencePage"; //$NON-NLS-1$

    private ChannelsEditorControl editorControl;

    private boolean workspaceRebuildNeeded = false;

    private final PresentationManager pm;

    private ResourceSetListener resourceListener =
            new ResourceSetListenerImpl() {

                @Override
                public boolean isPostcommitOnly() {
                    return true;
                }

                @Override
                public void resourceSetChanged(ResourceSetChangeEvent e) {
                    for (Notification n : e.getNotifications()) {
                        Object notifier = n.getNotifier();
                        if (isProjectPreferencePage()) {
                            validateProjectSpecificChannels();
                        }
                        if (notifier instanceof EObject) {
                            Resource resource =
                                    ((EObject) notifier).eResource();
                            if (resource != null
                                    && PresentationManager.getInstance()
                                            .getWorkspaceChannelURI()
                                            .equals(resource.getURI())) {
                                Object feature = n.getFeature();
                                validateWorkspaceChannels();
                                if (ChannelsPackage.eINSTANCE
                                        .getAttributeValue_Value() == feature
                                        || ChannelsPackage.eINSTANCE
                                                .getAttributeValue_EnumValues() == feature) {
                                    workspaceRebuildNeeded = true;
                                }
                            }
                        }
                    }
                }

            };

    private void validateWorkspaceChannels() {
        Channels workspaceChannels =
                PresentationManager.getInstance().getWorkspaceChannels();
        validateChannels(workspaceChannels.getChannels());
    }

    /**
     * 
     */
    public ChannelsPreferencePage() {
        setTitle(Messages.ChannelsPreferencePage_presentationChannel_title);
        pm = PresentationManager.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control createPreferenceContent(Composite composite) {
        BaseXpdToolkit toolkit = new BaseXpdToolkit();
        editorControl =
                new ChannelsEditorControl(composite, toolkit,
                        PresentationManager.getInstance()
                                .getChannels(getProject()));
        XpdResourcesPlugin.getDefault().getEditingDomain()
                .addResourceSetListener(resourceListener);
        if (getProject() == null) {
            validateWorkspaceChannels();
        }
        return editorControl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (resourceListener != null) {
            XpdResourcesPlugin.getDefault().getEditingDomain()
                    .removeResourceSetListener(resourceListener);
            resourceListener = null;
        }
        super.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPreferencePageID() {
        return PREF_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPropertyPageID() {
        return PROP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasProjectSpecificOptions(IProject project) {
        return PresentationManager.getInstance()
                .hasProjectSpecificChannelsSettings(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performOk() {
        if (isProjectPreferencePage()) {
            if (!useProjectSettings()) {
                IFile projectChannelsFile =
                        pm.getProjectChannelsFile(getProject());
                if (projectChannelsFile.exists()) {
                    try {
                        projectChannelsFile.delete(true, null);
                        enableProjectSpecificSettings(useProjectSettings());
                    } catch (CoreException e) {
                        LOG.error(e);
                    }
                }
                return true;
            }
        }
        boolean saved = editorControl.save();
        if (workspaceRebuildNeeded && saved) {
            Job workspaceBuild =
                    new Job(
                            Messages.ChannelsPreferencePage_WorkspaceBuild_message) {
                        @Override
                        public IStatus run(IProgressMonitor monitor) {
                            try {
                                ResourcesPlugin
                                        .getWorkspace()
                                        .build(IncrementalProjectBuilder.FULL_BUILD,
                                                monitor);
                            } catch (CoreException e) {
                                return Status.CANCEL_STATUS;
                            }
                            return Status.OK_STATUS;
                        }
                    };
            workspaceBuild.schedule();
        }
        return saved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performCancel() {
        editorControl.discardChanges();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performDefaults() {
        if (!isProjectPreferencePage()) {
            pm.resetWorkspaceChannelsToDefault();
            editorControl.setChannels(pm.getWorkspaceChannels());
        }
        super.performDefaults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void enableProjectSpecificSettings(
            boolean useProjectSpecificSettings) {
        IProject project = getProject();
        Assert.isNotNull(project);
        if (useProjectSpecificSettings) {
            PresentationManager pm = PresentationManager.getInstance();
            Channels currentChannels = editorControl.getChannels();
            if (currentChannels != null
                    && !pm.isProjectChannels(currentChannels, project)) {
                Channels projectChannels =
                        pm.getProjectChannels(project, false);
                if (projectChannels == null) {
                    projectChannels = pm.createProjectDefaultChannels(project);
                }
                editorControl.setChannels(projectChannels);
            }
        }
        super.enableProjectSpecificSettings(useProjectSpecificSettings);
        if (useProjectSpecificSettings) {
            validateProjectSpecificChannels();
        }

    }

    /**
     * validate project specific channels
     */
    private void validateProjectSpecificChannels() {
        Channels projectChannels =
                PresentationManager.getInstance()
                        .getProjectChannels(getProject(), false);
        if (projectChannels != null)
            validateChannels(projectChannels.getChannels());
    }

    /**
     * Validates the list of channels selected
     * 
     * @param channels
     */
    private void validateChannels(EList<Channel> channels) {
        String message = null;
        int messageType = -1;
        ChannelType channelType = null;

        for (Channel channel : channels) {
            for (TypeAssociation ta : channel.getTypeAssociations()) {
                for (AttributeValue attrValue : ta.getAttributeValues()) {
                    if (AttributeType.RESOURCE == attrValue.getAttribute()
                            .getType()
                            && attrValue.getValue() != null
                            && !attrValue.getValue().isEmpty()) {
                        setMessage(Messages.ChannelsPreferencePage_ResourceDefinedOnWorkspaceLevel_message,
                                WARNING);
                        return;
                    }
                }
                // XPD-3854: If this channel has Workspace Email or Workspace
                // General
                // Interface selected then set 'warningMessage' to true to
                // show a warning message that these are
                // deprecated and will be removed in the future
                channelType = ta.getChannelType();
                // XPD-4427: change sequence of equality test, to avoid possible
                // NPE
                if (PresentationManager.GI_GI_PULL.equals(channelType
                                .getId())) {
                    message =
                            Messages.ChannelsPreferencePage_ChannelTypeNotSupportedError;
                    messageType = ERROR;
                }//XPD-4868 : Enable the workspace EMail Channel 
                else if (PresentationManager.EMAIL_GI_PUSH.equals(channelType
                        .getId())) {
                    message =
                            Messages.ChannelsPreferencePage_ChannelTypeSettingWarning_msg;
                    messageType = WARNING;
                }
            }
        }
//        XPD-4789: Change level to Error, as GI channel support is removed
        if (message != null) {
            setMessage(message,
                    messageType);
        } else {
            setMessage(null);
        }
    }

}
