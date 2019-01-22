/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.components;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.resources.ui.PresentationResourcesUIActivator;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * UI block for editing the project's or workspace presentation channels.
 * <p>
 * <i>Created: 27 Oct 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ChannelsEditorControl extends Composite {

    private static final Logger LOG =
            PresentationResourcesUIActivator.getDefault().getLogger();

    private boolean initialized = false;

    private final XpdToolkit toolkit;

    private TabFolder channelTabFolder;

    private TabFolder typeAssocTabFolder;

    private ChannelsControl channelsControl;

    public ChannelsEditorControl(Composite parent) {
        super(parent, SWT.NONE);
        toolkit = new BaseXpdToolkit();
    }

    public Channels getChannels() {
        if (channelsControl != null) {
            return channelsControl.getChannels();
        }
        return null;
    }

    public void setChannels(Channels channels) {
        if (channelsControl != null
                && channelsControl.getChannels() != channels) {
            channelsControl.setChannels(channels);
        }
    }

    public ChannelsEditorControl(Composite parent, XpdToolkit toolkit,
            Channels channels) {
        super(parent, SWT.NONE);
        this.toolkit = toolkit;
        createContents(parent, channels);
    }

    protected Control createContents(Composite parent, Channels channels) {

        GridLayoutFactory.fillDefaults().applyTo(this);

        final SashForm sashForm = new SashForm(this, SWT.VERTICAL);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL)
                .grab(true, true).applyTo(sashForm);

        channelsControl = new ChannelsControl(sashForm, toolkit, channels);

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            final PageBook pageBook = new PageBook(sashForm, SWT.NONE);
            // Add tab folder for Channel elements.
            channelTabFolder = new TabFolder(pageBook, SWT.NONE);
            channelTabFolder.setLayoutData(new GridData(GridData.FILL_BOTH
                    | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

            final ChannelInfoControl channelInfoControl =
                    new ChannelInfoControl(channelTabFolder, toolkit);
            final TabItem tabProperties =
                    new TabItem(channelTabFolder, SWT.NONE);
            tabProperties
                    .setText(Messages.ChannelsEditorControl_propertiesTab_label);
            tabProperties.setControl(channelInfoControl);

            // Add tab folder for TypeAssociation elements.
            typeAssocTabFolder = new TabFolder(pageBook, SWT.NONE);
            typeAssocTabFolder.setLayoutData(new GridData(GridData.FILL_BOTH
                    | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

            final TypeAssociationAttributesControl attributesControl =
                    new TypeAssociationAttributesControl(typeAssocTabFolder,
                            toolkit);
            final TabItem tabAttributes =
                    new TabItem(typeAssocTabFolder, SWT.NONE);
            tabAttributes
                    .setText(Messages.ChannelsEditorControl_attributesTab_label);
            tabAttributes.setControl(attributesControl);

            final TypeAssociationExtendedAttributesControl extendedAttributesControl =
                    new TypeAssociationExtendedAttributesControl(
                            typeAssocTabFolder, toolkit);
            final TabItem tabExtendedAttr =
                    new TabItem(typeAssocTabFolder, SWT.NONE);
            tabExtendedAttr
                    .setText(Messages.ChannelsEditorControl_extendedAttributesTab_label);
            tabExtendedAttr.setControl(extendedAttributesControl);

            final TypeAssociationInfoControl typeAssociationInfoControl =
                    new TypeAssociationInfoControl(typeAssocTabFolder, toolkit);
            final TabItem tabTypeAssocProperties =
                    new TabItem(typeAssocTabFolder, SWT.NONE);
            tabTypeAssocProperties
                    .setText(Messages.ChannelsEditorControl_propertiesTab_label);
            tabTypeAssocProperties.setControl(typeAssociationInfoControl);

            // Register controls as listeners of channel viewer.
            channelsControl.getViewer()
                    .addSelectionChangedListener(attributesControl);
            channelsControl.getViewer()
                    .addSelectionChangedListener(extendedAttributesControl);
            channelsControl.getViewer()
                    .addSelectionChangedListener(channelInfoControl);

            channelsControl.getViewer()
                    .addSelectionChangedListener(typeAssociationInfoControl);

            channelsControl
                    .getTreeViewer()
                    .addSelectionChangedListener(new ISelectionChangedListener() {
                        public void selectionChanged(SelectionChangedEvent event) {
                            if (event.getSelection() instanceof TreeSelection
                                    && ((TreeSelection) event.getSelection())
                                            .getFirstElement() != null) {
                                Object firstElement =
                                        ((TreeSelection) event.getSelection())
                                                .getFirstElement();
                                if (firstElement instanceof Channel) {
                                    pageBook.setVisible(true);
                                    pageBook.showPage(channelTabFolder);
                                    channelInfoControl.update();
                                    sashForm.layout();
                                } else if (firstElement instanceof TypeAssociation) {
                                    pageBook.setVisible(true);
                                    pageBook.showPage(typeAssocTabFolder);
                                    sashForm.layout();

                                } else {
                                    pageBook.setVisible(false);
                                    sashForm.layout();
                                }
                            }
                        }
                    });
            pageBook.setVisible(false);

        }

        return sashForm;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible == true && !initialized) {
            initializePageDefaults();
            initialized = true;
        }
        super.setVisible(visible);
    }

    /**
     * Initialize page.
     */
    protected void initializePageDefaults() {
        // do some initialization if needed.
    }

    public boolean save() {
        try {
            PresentationManager.saveChannels(getChannels());
            return true;
        } catch (CoreException e) {
            LOG.error(e);
            return false;
        }
    }

    public boolean discardChanges() {
        try {
            PresentationManager.reloadChannels(getChannels());
        } catch (CoreException e) {
            LOG.error(e);
            return false;
        }
        return true;
    }
}
