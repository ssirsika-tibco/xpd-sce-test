/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.ChannelsFactory;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.Implementation;
import com.tibco.xpd.presentation.channeltypes.Presentation;
import com.tibco.xpd.presentation.channeltypes.Target;
import com.tibco.xpd.presentation.resources.ui.PresentationResourcesUIActivator;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Wizard for creating new channel.
 * <p>
 * <i>Created: 4 August 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class NewChannelWizard extends Wizard {

    /**
     * Content provider based on AdapterFactoryContentProvider modified to trim
     * off children.
     */
    private static class ChannelTypesContentProvider extends
            TransactionalAdapterFactoryContentProvider {
        /** */
        private static final Object[] emptyTable = new Object[0];

        /**
         * @param domain
         * @param adapterFactory
         */
        public ChannelTypesContentProvider(TransactionalEditingDomain domain,
                AdapterFactory adapterFactory) {
            super(domain, adapterFactory);
        }

        /** {@inheritDoc} */
        @Override
        public boolean hasChildren(Object object) {
            return false;
        }

        /** {@inheritDoc} */
        @Override
        public Object[] getChildren(Object object) {
            return emptyTable;
        }

        /** {@inheritDoc} */
        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Object[]) {
                return (Object[]) inputElement;
            }
            if (inputElement instanceof Collection) {
                return ((Collection) inputElement).toArray();
            }
            return new Object[0];
        }
    }

    /**
     * Label provider based on AdapterFactoryLabelProvider.
     */
    private static class ChannelTypesLabelProvider extends
            TransactionalAdapterFactoryLabelProvider {

        /**
         * @param domain
         * @param adapterFactory
         */
        public ChannelTypesLabelProvider(TransactionalEditingDomain domain,
                AdapterFactory adapterFactory) {
            super(domain, adapterFactory);
        }

        /** {@inheritDoc} */
        @Override
        public String getColumnText(final Object object, int columnIndex) {
            if (object instanceof ChannelType) {
                final ChannelType channelType = (ChannelType) object;
                switch (columnIndex) {
                case 0: // name
                    return super.getColumnText(object, columnIndex);
                case 1: // target
                    Target target = run(new RunnableWithResult.Impl<Target>() {
                        @Override
                        public void run() {
                            setResult(channelType.getTarget());
                        }
                    });
                    if (target == null) {
                        return ""; //$NON-NLS-1$
                    }
                    return getText(target);
                case 2: // presentation
                    Presentation presentation =
                            run(new RunnableWithResult.Impl<Presentation>() {
                                @Override
                                public void run() {
                                    setResult(channelType.getPresentation());
                                }
                            });
                    if (presentation == null) {
                        return ""; //$NON-NLS-1$
                    }
                    return getText(presentation);
                case 3: // implementation
                    Implementation implementation =
                            run(new RunnableWithResult.Impl<Implementation>() {
                                @Override
                                public void run() {
                                    setResult(channelType.getImplementation());
                                }
                            });
                    if (implementation == null) {
                        return ""; //$NON-NLS-1$
                    }
                    return getText(implementation);
                case 4: // runtimeVersion
                    String runtimeVersion =
                            run(new RunnableWithResult.Impl<String>() {
                                @Override
                                public void run() {
                                    setResult(channelType.getRuntimeVersion());
                                }
                            });
                    if (runtimeVersion == null) {
                        return ""; //$NON-NLS-1$
                    }
                    return getText(runtimeVersion);
                default:
                    return super.getColumnText(object, columnIndex);
                }
            }
            return ""; //$NON-NLS-1$
        }

        /** {@inheritDoc} */
        @Override
        public Image getColumnImage(Object object, int columnIndex) {
            if (columnIndex == 0) { // Only first column will have an image.
                return super.getColumnImage(object, columnIndex);
            }
            return null;
        }
    }

    private final static String DIALOG_SETTINGS_SECTION_NAME =
            "wizards.NewChannelWizard"; //$NON-NLS-1$

    private Channels model;

    private Channel selectedChannel = null;

    private final GeneralChannelPage generalChannelPage =
            new GeneralChannelPage("generalChannelPage"); //$NON-NLS-1$

    private final BaseXpdToolkit toolkit;

    /**
     * The constructor.
     */
    public NewChannelWizard() {
        setWindowTitle(Messages.NewChannelWizard_NewPresentationChannel_title);
        IDialogSettings pluginSettings =
                PresentationResourcesUIActivator.getDefault()
                        .getDialogSettings();
        IDialogSettings wizardSettings =
                pluginSettings.getSection(DIALOG_SETTINGS_SECTION_NAME);
        if (wizardSettings == null)
            wizardSettings =
                    pluginSettings.addNewSection(DIALOG_SETTINGS_SECTION_NAME);
        setDialogSettings(wizardSettings);
        toolkit = new BaseXpdToolkit();
        setNeedsProgressMonitor(false);
    }

    /** {@inheritDoc} */
    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * The general page for channel.
     */
    private final class GeneralChannelPage extends AbstractXpdWizardPage {
        private final static String CHANNEL_TYPE_SETTING = "defaultChannelType"; //$NON-NLS-1$

        private CheckboxTableViewer channelTypeViewer;

        private Text channelDisplayNameText;

        private final ValidationListener validationListener =
                new ValidationListener();

        private boolean showMessages;

        private class ValidationListener implements Listener {
            /** {@inheritDoc} */
            @Override
            public void handleEvent(Event event) {
                if (event.type == SWT.Modify || event.type == SWT.Selection) {
                    setPageComplete(validatePage());
                }
            }
        }

        /**
         * @param pageName
         */
        protected GeneralChannelPage(String pageName) {
            super(pageName);
            setTitle(Messages.NewChannelWizard_PresentationChannel_shortDesc);
            setDescription(Messages.NewChannelWizard_PresentationChannel_longdesc);

        }

        /** {@inheritDoc} */
        @Override
        public void createControl(Composite parent) {

            Composite composite = new Composite(parent, SWT.NONE);
            GridLayout gridLayout = new GridLayout(2, false);

            composite.setLayout(gridLayout);

            GridDataFactory textGDFactory =
                    GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                            .grab(true, false);

            // channel display name
            toolkit.createLabel(composite,
                    Messages.NewChannelWizard_Channel_label);
            channelDisplayNameText =
                    toolkit.createText(composite, "", "ChannelDisplayName"); //$NON-NLS-1$ //$NON-NLS-2$
            if (selectedChannel != null) {
                channelDisplayNameText
                        .setText(selectedChannel.getDisplayName());
            }
            textGDFactory.applyTo(channelDisplayNameText);

            if (CapabilityUtil.isDeveloperActivityEnabled()) {
                // channel type
                toolkit.createLabel(composite,
                        Messages.NewChannelWizard_ChannelTypes_label);
                Table channelTypeControl =
                        toolkit.createTable(composite,
                                SWT.CHECK | SWT.FULL_SELECTION | SWT.SINGLE
                                        | SWT.V_SCROLL | SWT.H_SCROLL
                                        | SWT.BORDER,
                                "ChannelType"); //$NON-NLS-1$

                TableColumn nameColumn =
                        new TableColumn(channelTypeControl, SWT.LEFT);
                nameColumn.setText(Messages.NewChannelWizard_NameColumn_label);
                nameColumn.setWidth(150);

                TableColumn targetColumn =
                        new TableColumn(channelTypeControl, SWT.LEFT);
                targetColumn
                        .setText(Messages.NewChannelWizard_TargetColumn_label);
                targetColumn.setWidth(110);

                TableColumn presentationColumn =
                        new TableColumn(channelTypeControl, SWT.LEFT);
                presentationColumn
                        .setText(Messages.NewChannelWizard_PresentationColumn_label);
                presentationColumn.setWidth(110);

                TableColumn implementationColumn =
                        new TableColumn(channelTypeControl, SWT.LEFT);
                implementationColumn
                        .setText(Messages.NewChannelWizard_ImplemanteationColumn_label);
                implementationColumn.setWidth(100);

                TableColumn runtimeVersionColumn =
                        new TableColumn(channelTypeControl, SWT.LEFT);
                runtimeVersionColumn
                        .setText(Messages.NewChannelWizard_ReuntimeVersion_label);
                runtimeVersionColumn.setWidth(100);

                channelTypeControl.setLinesVisible(true);
                channelTypeControl.setHeaderVisible(true);
                GridDataFactory.swtDefaults().span(2, 1)
                        .align(SWT.FILL, SWT.FILL).grab(true, true)
                        .applyTo(channelTypeControl);

                channelTypeViewer = new CheckboxTableViewer(channelTypeControl);
                channelTypeViewer
                        .setContentProvider(new ChannelTypesContentProvider(
                                XpdResourcesPlugin.getDefault()
                                        .getEditingDomain(), XpdResourcesPlugin
                                        .getDefault().getAdapterFactory()));
                channelTypeViewer
                        .setLabelProvider(new ChannelTypesLabelProvider(
                                XpdResourcesPlugin.getDefault()
                                        .getEditingDomain(), XpdResourcesPlugin
                                        .getDefault().getAdapterFactory()));
                // XPD-4427: Filter the ChannelTypes for available destinations.
                channelTypeViewer.setInput(PresentationManager.getInstance()
                        .getAceAvailableChannelTypes());
                if (selectedChannel != null) {
                    for (TypeAssociation typeAssociation : selectedChannel
                            .getTypeAssociations()) {
                        channelTypeViewer.setChecked(typeAssociation
                                .getChannelType(), true);
                    }
                }
                channelTypeControl.addListener(SWT.Selection,
                        validationListener);
            }

            // if (selectedChannel != null && selectedChannel.isDefault()) {
            // channelTypeViewer.getControl().setEnabled(false);
            // }
            // setDefaults();

            channelDisplayNameText.setFocus();

            showMessages = false;
            setPageComplete(validatePage());
            showMessages = true;
            channelDisplayNameText.addListener(SWT.Modify, validationListener);

            setControl(composite);
        }

        /**
         * Set defaults values for page controls.
         */
        private void setDefaults() {
            String channelTypeId =
                    getDialogSettings().get(CHANNEL_TYPE_SETTING);
            if (channelTypeId != null) {
                ChannelType channelType =
                        PresentationManager.getInstance().getChannelTypes()
                                .getChannelType(channelTypeId);
                if (channelType != null && channelTypeViewer != null) {
                    channelTypeViewer.setSelection(new StructuredSelection(
                            channelType), true);
                }
            }
        }

        /**
         * Updates persisted dialog settings for the page.
         */
        void updateDialogSettings() {
            // ISelection s = channelTypeViewer.getSelection();
            // if (s instanceof IStructuredSelection) {
            // IStructuredSelection ss = (IStructuredSelection) s;
            // if (!ss.isEmpty()
            // && ss.getFirstElement() instanceof ChannelType) {
            // String channelTypeId = ((ChannelType) ss.getFirstElement())
            // .getId();
            // getDialogSettings()
            // .put(CHANNEL_TYPE_SETTING, channelTypeId);
            // }
            // }
        }

        /**
         * Validates page.
         * 
         * @return true if validation is successful.
         */
        private boolean validatePage() {
            setMessage(null);
            setErrorMessage(null);
            if (getChannelDisplayName().trim().length() == 0) {
                setErrorMessage(Messages.NewChannelWizard_ChannelCannotBeEmpty_message);
                channelDisplayNameText.setFocus();
                return false;
            } else if (!getChannelDisplayName().equals(getChannelDisplayName()
                    .trim())) {
                setErrorMessage(Messages.NewChannelWizard_LeadingTrailingSpaces_message);
                channelDisplayNameText.setFocus();
                return false;
            } else if (selectedChannel != null) {
                if (selectedChannel.isDefault()) {
                    if (channelTypeViewer != null) {
                        if (channelTypeViewer.getCheckedElements().length == 0) {
                            setErrorMessage(Messages.NewChannelWizard_DefaultChannelMustBeAssociated_message);
                            return false;
                        } else if (channelTypeViewer.getCheckedElements().length > 0
                                && isTypeSelected(PresentationManager.EMAIL_GI_PUSH)) {
                            // XPD-3657 Workspace EMail channel should not be
                            // selectable in
                            // the default channel list in the Studio
                            setErrorMessage(String
                                    .format(Messages.NewChannelWizard_ChannelTypeSettingDefaultError_message,
                                            PresentationResourcesUIActivator
                                                    .getChannelTypes()
                                                    .getChannelType(PresentationManager.EMAIL_GI_PUSH)
                                                    .getDisplayName()));
                            return false;
                        }
                    }
                }
            }
            // XPD-3854: If this channel has Workspace Email or Workspace
            // General
            // Interface selected then set 'warningMessage' to true to
            // show a warning message that these are
            // deprecated and will be removed in the future
            if ((channelTypeViewer != null)
                    && (channelTypeViewer.getCheckedElements().length > 0)) {
                if (isTypeSelected(PresentationManager.EMAIL_GI_PUSH)) {
                    // XPD-4868 : Enable the workspace EMail Channel , with
                    // deprecation warning as before
                    setMessage(Messages.ChannelsPreferencePage_ChannelTypeSettingWarning_msg,
                            WARNING);
                    return true;
                } else if (isTypeSelected(PresentationManager.GI_GI_PULL)) {
                    // XPD-4789: Change level to Error, as GI channel support is
                    // removed
                    setMessage(Messages.ChannelsPreferencePage_ChannelTypeNotSupportedError,
                            ERROR);
                    return true;
                }
            }
            return true;
        }

        /**
         * @param channelTypeId
         * @param channel
         */
        private boolean isTypeSelected(String channelTypeId) {
            for (ChannelType channelType : getCheckedChannelTypes()) {
                if (channelType.getId() != null
                        && channelTypeId.equals(channelType.getId())) {
                    return true;
                }
            }
            return false;
        }

        // The message setting is controlled by internal class state.
        /** {@inheritDoc} */
        @Override
        public void setErrorMessage(String newMessage) {
            if (showMessages) {
                super.setErrorMessage(newMessage);
            } else {
                super.setErrorMessage(null);
            }
        }

        /**
         * Returns name of the channel.
         * 
         * @return name of the channel.
         */
        public String getChannelDisplayName() {
            return channelDisplayNameText.getText();
        }

        public List<ChannelType> getCheckedChannelTypes() {
            List<ChannelType> channelTypes = new ArrayList<ChannelType>();
            if (channelTypeViewer != null) {
                for (Object checked : channelTypeViewer.getCheckedElements()) {
                    if (checked instanceof ChannelType)
                        channelTypes.add((ChannelType) checked);
                }
            }
            return channelTypes;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addPages() {
        addPage(generalChannelPage);
    }

    /**
     * {@inheritDoc} Adds a new channel or modifies existing one. The properties
     * of type associations will be preserved.
     */
    @Override
    public boolean performFinish() {
        final String channelDisplayName =
                generalChannelPage.getChannelDisplayName();

        final List<ChannelType> checkedChannelTypes =
                generalChannelPage.getCheckedChannelTypes();
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        ed.getCommandStack().execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                ChannelsFactory f = ChannelsFactory.eINSTANCE;
                Channel channel = null;
                if (selectedChannel != null) { // Editing existing.
                    // Update DisplayName.
                    selectedChannel.setDisplayName(channelDisplayName);

                    /*
                     * Remove any existing associations that are not among the
                     * checked ones
                     */
                    EList<TypeAssociation> typedAssociations =
                            selectedChannel.getTypeAssociations();
                    List<TypeAssociation> removedTypeAssociations =
                            new ArrayList<TypeAssociation>();
                    List<String> existingChannelTypeIds =
                            new ArrayList<String>();
                    boolean found;
                    for (TypeAssociation typedAssociation : typedAssociations) {
                        found = false;
                        for (ChannelType checkedChannelType : checkedChannelTypes) {
                            if (checkedChannelType.getId()
                                    .equals(typedAssociation.getChannelType()
                                            .getId())) {
                                found = true;
                                existingChannelTypeIds.add(checkedChannelType
                                        .getId());
                                break;
                            }
                        }
                        if (!found) {
                            removedTypeAssociations.add(typedAssociation);
                        }
                    }

                    selectedChannel.getTypeAssociations()
                            .removeAll(removedTypeAssociations);

                    // Add association for newly selected channel types.
                    for (ChannelType checkedChannelType : checkedChannelTypes) {
                        if (!existingChannelTypeIds.contains(checkedChannelType
                                .getId())) {
                            // XPD-3657 Workspace EMail type should not be part
                            // of default channel
                            TypeAssociation typeAssociation =
                                    f.createTypeAssociation();
                            typeAssociation.setChannelType(checkedChannelType);
                            selectedChannel.getTypeAssociations()
                                    .add(typeAssociation);
                        }
                    }
                } else { // Creating new.
                    // Update DisplayName.
                    channel = f.createChannel();
                    channel.setDisplayName(channelDisplayName);
                    for (ChannelType channelType : checkedChannelTypes) {
                        TypeAssociation typeAssociation =
                                f.createTypeAssociation();
                        typeAssociation.setChannelType(channelType);
                        channel.getTypeAssociations().add(typeAssociation);
                    }
                    getModel().getChannels().add(channel);
                }
            }
        });
        generalChannelPage.updateDialogSettings();
        return true;
    }

    /**
     * @param model
     *            the model to set.
     */
    public void setModel(Channels model) {
        this.model = model;
    }

    /**
     * @return the model.
     */
    public Channels getModel() {
        return model;
    }

    /**
     * Setting the selected channel to edit. If selected channel is
     * <code>null</code> then wizard will be used to create a new channel.
     * 
     * @param selectedChannel
     *            channel to edit or <code>null</code> for a new channel.
     */
    public void setChannel(Channel selectedChannel) {
        this.selectedChannel = selectedChannel;
    }
}
