/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.analyst.resources.xpdl2.indexing.ProcessParticipantResourceIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Collapsible section with the shared resource configuration for system
 * participant.
 * 
 * @author Jan Arciuchiewicz
 */
public class SharedResourcesSection
        extends AbstractFilteredTransactionalSection
        implements FixedValueFieldChangedListener {

    private List<Button> typeButtons;

    private ScrolledPageBook resourceTypeBook;

    private DecoratedField emailInstanceNameText;

    private Text jdbcInstanceNameText;

    private Button wsInboundButton;

    private Button wsOutboundButton;

    private Text jdbcProfileNameText;
    
    private DecoratedField endPointIdentifierText;
    
    private Text endPointIdentifierDescText;

    /**
     * @param eClass
     */
    public SharedResourcesSection(EClass eClass) {
        super(eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        GridLayoutFactory.swtDefaults().numColumns(3).applyTo(parent);
        Composite typesComposite = toolkit.createComposite(parent);
        GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
                .applyTo(typesComposite);
        GridLayoutFactory.swtDefaults().applyTo(typesComposite);
        // Create the types radio buttons
        typeButtons = new ArrayList<Button>();
        for (ProcessParticipantResourceIndexProvider.ResourceType resourceType : ProcessParticipantResourceIndexProvider.ResourceType
                .values()) {
            Button button = toolkit.createButton(typesComposite,
                    resourceType.toString(),
                    SWT.RADIO,
                    ""); //$NON-NLS-1$
            button.setData(resourceType);
            button.setData(XpdFormToolkit.FEATURE_DATA,
                    resourceType.getFeature());
            /* Set description only when it is specified */
            String description = resourceType.getDescription();
            if (description != null && !description.isEmpty()) {
                button.setToolTipText(description);
            }

            /*
             * ACE-1371: Saket: User should not be able to create participant of
             * JDBC type
             */
            if (resourceType == ProcessParticipantResourceIndexProvider.ResourceType.JDBC) {
                button.setVisible(false);
            }

            manageControl(button);
            typeButtons.add(button);
        }
        GridDataFactory.fillDefaults()
                .applyTo(toolkit.createSeparator(parent, SWT.VERTICAL));

        resourceTypeBook = toolkit.createPageBook(parent, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(resourceTypeBook);

        Point minSize = createTypeBookPages(resourceTypeBook, toolkit);
        setMinimumHeight(280);
        return parent;
    }

    private Point createTypeBookPages(ScrolledPageBook book,
            XpdFormToolkit toolkit) {
        Composite page = null;

        Point minSize = new Point(0, 0);

        for (ProcessParticipantResourceIndexProvider.ResourceType resourceType : ProcessParticipantResourceIndexProvider.ResourceType
                .values()) {
            page = toolkit.createComposite(book.getContainer());
            GridDataFactory.fillDefaults().grab(true, true).applyTo(page);
            // Create pages for each type declaration type
            switch (resourceType) {
            case EMAIL:
                createEmailPage(page, toolkit);
                break;
            case JDBC:
                createJdbcPage(page, toolkit);
                break;
            case REST_SERVICE:
                createRestServicePage(page, toolkit);
                break;
            default:
                Assert.isTrue(false, "Unknown resource type."); //$NON-NLS-1$
            }

            Point min = page.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            if (min.x > minSize.x) {
                minSize.x = min.x;
            }
            if (min.y > minSize.y) {
                minSize.y = min.y;
            }

            book.registerPage(resourceType, page);
        }

        return minSize;
    }

    /**
     * Creates Email Service page.
     * 
     * @param page
     * @param toolkit
     */
    private void createEmailPage(Composite page, XpdFormToolkit toolkit) {
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(page);
        toolkit.createLabel(page,
                Messages.SharedResourcesSection_SharedResourceLabel);

        FixedValueFieldProposalProvider emailInstanceNamesProposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        /*
                         * Get all the participants which match the content of
                         * the content assist field
                         */
                        Set<EmailParticpantSharedResourceProposal> allInstanceNameProposals = getAllInstanceNameProposals();
                        Object[] proposals = allInstanceNameProposals.toArray();
                        return proposals;
                    }

                    /**
                     * Get all email instance name proposals that are in the
                     * scope of the input object.
                     * 
                     * @return
                     */
                    private Set<EmailParticpantSharedResourceProposal> getAllInstanceNameProposals() {

                        Set<EmailParticpantSharedResourceProposal> allInstanceNameProposals =
                                new HashSet<EmailParticpantSharedResourceProposal>();

                        Collection<IndexerItem> allParticipantIndexerItems =
                                ProcessUIUtil.getAllParticipantIndexerItems();

                        for (IndexerItem eachParticipantIndexerItem : allParticipantIndexerItems) {
                            /*
                             * Check if the participant is of shared resource is
                             * of EMAIL share resource type.
                             */

                            if (eachParticipantIndexerItem != null) {
                                String resourceType = eachParticipantIndexerItem
                                        .get(ProcessParticipantResourceIndexProvider.ATTRIBUTE_RESOURCE_TYPE);
                                if (ProcessParticipantResourceIndexProvider.ResourceType.EMAIL.toString()
                                        .equals(resourceType)) {

                                    /*
                                     * Make sure that we haven't already added
                                     * the current email instance name to the
                                     * list of all resource names.
                                     */

                                    String emailInstanceName = eachParticipantIndexerItem
                                            .get(ProcessParticipantResourceIndexProvider.ATTRIBUTE_EMAIL_INSTANCE_NAME);
                                    if (null != emailInstanceName && !emailInstanceName.isEmpty()
                                            && !isAlreadyInTheInstanceNameProposals(emailInstanceName,
                                                    allInstanceNameProposals)) {

                                        EmailParticpantSharedResourceProposal newEmailServiceSharedResourceProposal =
                                                new EmailParticpantSharedResourceProposal(emailInstanceName);
                                        allInstanceNameProposals.add(newEmailServiceSharedResourceProposal);
                                    }
                                }
                            }
                        }

                        return allInstanceNameProposals;
                    }

                    /**
                     * Return <code>true</code> if a resource proposal with the
                     * specified instance name is already present in the list of
                     * resource proposals, <code>false</code> otherwise.
                     * 
                     * @param instanceName
                     * @param allResourceProposals
                     * 
                     * @return <code>true</code> if a resource proposal with the
                     *         specified instance name is already present in the
                     *         list of resource proposals, <code>false</code>
                     *         otherwise.
                     */
                    private boolean isAlreadyInTheInstanceNameProposals(String instanceName,
                            Set<EmailParticpantSharedResourceProposal> allResourceProposals) {
                        boolean isInstanceNameInTheResourceProposals = false;

                        if (null != instanceName) {
                            for (EmailParticpantSharedResourceProposal eachProposal : allResourceProposals) {
                                if (null != eachProposal && null != eachProposal.getEmailInstanceName()) {
                                    if (instanceName.equals(eachProposal.getEmailInstanceName())) {
                                        isInstanceNameInTheResourceProposals = true;
                                        break;
                                    }
                                }
                            }
                        }
                        return isInstanceNameInTheResourceProposals;
                    }
                };

        /*
         * Content assist helper for email instance names content assist.
         */
        FixedValueFieldAssistHelper emailInstanceNameContentAssistHelper =
                new FixedValueFieldAssistHelper(toolkit, page, emailInstanceNamesProposalProvider, false);
        emailInstanceNameContentAssistHelper.addValueChangedListener(this);
        emailInstanceNameText = emailInstanceNameContentAssistHelper.getDecoratedField();
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalIndent = -6;
        emailInstanceNameText.getLayoutControl().setLayoutData(gridData);
        emailInstanceNameText.getLayoutControl().setBackground(page.getBackground());
        emailInstanceNameText.getControl().setToolTipText(Messages.SharedResourcesSection_EmailInstanceNameTooltip);
        manageControlUpdateOnDeactivate((Text) emailInstanceNameText.getControl());
    }

    /**
     * Creates JDBC Service page.
     * 
     * @param page
     * @param toolkit
     */
    private void createJdbcPage(Composite page, XpdFormToolkit toolkit) {
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(page);
        toolkit.createLabel(page,
                Messages.SharedResourcesSection_JdbcInstanceName_label);
        jdbcInstanceNameText = toolkit.createText(page, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(jdbcInstanceNameText);
        manageControlUpdateOnDeactivate(jdbcInstanceNameText);

        Label profileLabel = toolkit.createLabel(page,
                Messages.SharedResourcesSection_JdbcProfileName_label);
        profileLabel.setToolTipText(
                Messages.SharedResourcesSection_JdbcProfileName_tooltip);
        jdbcProfileNameText = toolkit.createText(page, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(jdbcProfileNameText);
        manageControlUpdateOnDeactivate(jdbcProfileNameText);
    }

    /**
     * Creates the REST Service shared resource property page.
     * 
     * @param page
     * @param toolkit
     */
    private void createRestServicePage(Composite page, XpdFormToolkit toolkit) {
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(page);
        Label description = toolkit.createLabel(page,
                Messages.SharedResourcesSection_RestResourceDesc);
        GridDataFactory.swtDefaults().span(2, 1).indent(5, 0)
                .applyTo(description);
        
        /*
         * Endpoint resource name controls.
         */

        Label endPointIdentifierLabel = toolkit.createLabel(page, Messages.SharedResourcesSection_SharedResourceLabel);
        GridDataFactory.swtDefaults().applyTo(endPointIdentifierLabel);

        FixedValueFieldProposalProvider endpointNamesProposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        /*
                         * Get all the participants which match the content of
                         * the content assist field
                         */
                        Set<RestServiceSharedResourceProposal> allResourceNameProposals =
                                getAllResourceProposals();
                        Object[] proposals = allResourceNameProposals.toArray();
                        return proposals;
                    }

                    /**
                     * Get all resource name proposals that are in the scope of
                     * the input object.
                     * 
                     * @return
                     */
                    private Set<RestServiceSharedResourceProposal> getAllResourceProposals() {

                        Set<RestServiceSharedResourceProposal> allResourceProposals = new HashSet<RestServiceSharedResourceProposal>();

                        Collection<IndexerItem> allParticipantIndexerItems =
                                ProcessUIUtil.getAllParticipantIndexerItems();

                        for (IndexerItem eachParticipantIndexerItem : allParticipantIndexerItems) {
                            /*
                             * Check if the participant is of shared resource is
                             * of REST share resource type.
                             */

                            String resourceType = eachParticipantIndexerItem
                                    .get(ProcessParticipantResourceIndexProvider.ATTRIBUTE_RESOURCE_TYPE);
                            if (eachParticipantIndexerItem != null
                                    && ProcessParticipantResourceIndexProvider.ResourceType.REST_SERVICE
                                            .toString()
                                            .equals(resourceType)) {

                                /*
                                 * Make sure that we haven't already added the
                                 * current resource name to the list of all
                                 * resource names.
                                 */

                                String resourceName = eachParticipantIndexerItem
                                        .get(ProcessParticipantResourceIndexProvider.ATTRIBUTE_RESOURCE_NAME);
                                if (null != resourceName
                                        && !resourceName.isEmpty()
                                        && !isAlreadyInTheResourceProposals(
                                                resourceName,
                                                allResourceProposals)) {
                                    
                                    String resourceDescription = eachParticipantIndexerItem
                                            .get(ProcessParticipantResourceIndexProvider.ATTRIBUTE_RESOURCE_DESCRIPTION);
                                    
                                    RestServiceSharedResourceProposal newRestServiceSharedResourceProposal =
                                            new RestServiceSharedResourceProposal(
                                                    resourceName,
                                                    resourceDescription);
                                    allResourceProposals
                                            .add(newRestServiceSharedResourceProposal);
                                }

                            }
                        }

                        return allResourceProposals;
                    }

                    /**
                     * Return <code>true</code> if a resource proposal with the
                     * specified resource name is already present in the list of
                     * resource proposals, <code>false</code> otherwise.
                     * 
                     * @param resourceName
                     * @param allResourceProposals
                     * 
                     * @return <code>true</code> if a resource proposal with the
                     *         specified resource name is already present in the
                     *         list of resource proposals, <code>false</code>
                     *         otherwise.
                     */
                    private boolean isAlreadyInTheResourceProposals(
                            String resourceName,
                            Set<RestServiceSharedResourceProposal> allResourceProposals) {
                        boolean isResourceNameInTheResourceProposals = false;

                        if (null != resourceName) {
                            for (RestServiceSharedResourceProposal eachProposal : allResourceProposals) {
                                if (null != eachProposal && null != eachProposal
                                        .getResourceName()) {
                                    if (resourceName.equals(
                                            eachProposal.getResourceName())) {
                                        isResourceNameInTheResourceProposals =
                                                true;
                                        break;
                                    }
                                }
                            }
                        }
                        return isResourceNameInTheResourceProposals;
                    }
                };

        /*
         * Content assist helper for endpoint names content assist.
         */
        FixedValueFieldAssistHelper endpointNameContentAssistHelper =
                new FixedValueFieldAssistHelper(toolkit, page, endpointNamesProposalProvider,
                        false);
        endpointNameContentAssistHelper.addValueChangedListener(this);
        endPointIdentifierText = endpointNameContentAssistHelper.getDecoratedField();
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalIndent = -6;
        endPointIdentifierText.getLayoutControl().setLayoutData(gridData);
        endPointIdentifierText.getLayoutControl()
                .setBackground(page.getBackground());
        endPointIdentifierText.getControl().setToolTipText(
                Messages.SharedResourcesSection_SharedResourceTooltip);

        /*
         * Endpoint resource description controls.
         */

        Label endPointIdentifierDescLabel = toolkit.createLabel(page, Messages.SharedResourcesSection_SharedResourceDescLabel);
        GridDataFactory.swtDefaults().applyTo(endPointIdentifierDescLabel);
        endPointIdentifierDescText = toolkit.createText(page, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false).applyTo(endPointIdentifierDescText);

        manageControlUpdateOnDeactivate(
                (Text) endPointIdentifierText.getControl());
        manageControlUpdateOnDeactivate(endPointIdentifierDescText);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener#fixedValueFieldChanged(java.lang.Object)
     *
     * @param newValue
     */
    @Override
    public void fixedValueFieldChanged(Object newValue) {
        final Participant participant = (Participant) getInput();
        final ParticipantSharedResource sr =
                getSetParticipantSharedResource(participant, false);
        if (null != sr && null != sr.getRestService()) {
            final String text = newValue.toString();

            if (!nullSafe(text)
                    .equals(nullSafe(sr.getRestService().getResourceName()))) {
                Command cmd = new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        if (newValue instanceof RestServiceSharedResourceProposal) {
                            String resName =
                                    ((RestServiceSharedResourceProposal) newValue)
                                            .getResourceName();
                            String resDesc =
                                    ((RestServiceSharedResourceProposal) newValue)
                                            .getResourceDescription();
                            if (null != resName && !resName.isEmpty()) {
                                sr.getRestService().setResourceName(resName);
                            }
                            if (null != resDesc && !resDesc.isEmpty()) {
                                sr.getRestService().setDescription(resDesc);
                            }
                        } else if (newValue instanceof String) {
                            String newValueString = (String) newValue;
                            if (!newValueString.equals(
                                    sr.getRestService().getResourceName()))
                                sr.getRestService()
                                        .setResourceName(newValueString);
                        }
                    }
                };

                if (cmd != null) {
                    EditingDomain ed = getEditingDomain();
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

    /**
     * Class to facilitate the proposals for Rest service shared resource name
     * content assist.
     *
     * @author sajain
     * @since Jun 4, 2019
     */
    private static class RestServiceSharedResourceProposal {

        /**
         * Resource name.
         */
        private String resourceName;

        /**
         * Resource description.
         */
        private String resourceDescription;

        /**
         * Class to facilitate the proposals for Rest service shared resource
         * name content assist.
         * 
         * @param aResourceName
         * @param aResourceDescription
         */
        public RestServiceSharedResourceProposal(
                String aResourceName, String aResourceDescription) {
            this.setResourceName(aResourceName);
            this.setResourceDescription(aResourceDescription);
        }

        /**
         * @see java.lang.Object#toString()
         *
         * @return
         */
        @Override
        public String toString() {
            String toStr = super.toString();
            if (null != this.resourceName && !this.resourceName.isEmpty()) {
                toStr = this.resourceName;

                if (null != this.resourceDescription
                        && !this.resourceDescription.isEmpty()) {
                    toStr += " - " + this.resourceDescription; //$NON-NLS-1$
                }
            }

            return toStr;
        }

        /**
         * @return the resourceName
         */
        public String getResourceName() {
            return resourceName;
        }

        /**
         * @param resourceName
         *            the resourceName to set
         */
        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        /**
         * @return the resourceDescription
         */
        public String getResourceDescription() {
            return resourceDescription;
        }

        /**
         * @param resourceDescription
         *            the resourceDescription to set
         */
        public void setResourceDescription(String resourceDescription) {
            this.resourceDescription = resourceDescription;
        }
    }

    /**
     * Class to facilitate the proposals for Email particpant shared resource
     * instance name content assist.
     *
     * @author sajain
     * @since Jul 30, 2019
     */
    private static class EmailParticpantSharedResourceProposal {

        /**
         * Email participant instance name.
         */
        private String emailInstanceName;

        /**
         * Class to facilitate the proposals for Email particpant shared
         * resource instance name content assist.
         * 
         * @param aEmailInstanceName
         */
        public EmailParticpantSharedResourceProposal(String aEmailInstanceName) {
            this.setEmailInstanceName(aEmailInstanceName);
        }

        /**
         * @see java.lang.Object#toString()
         *
         * @return
         */
        @Override
        public String toString() {
            String toStr = super.toString();
            if (null != this.emailInstanceName && !this.emailInstanceName.isEmpty()) {
                toStr = this.emailInstanceName;
            }

            return toStr;
        }

        /**
         * @return the emailInstanceName
         */
        public String getEmailInstanceName() {
            return emailInstanceName;
        }

        /**
         * @param emailInstanceName
         *            the emailInstanceName to set
         */
        public void setEmailInstanceName(String emailInstanceName) {
            this.emailInstanceName = emailInstanceName;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        final Participant participant = (Participant) getInput();
        if (typeButtons.contains(obj)) {
            Button button = (Button) obj;
            if (button.getSelection()) { // is selected
                final ProcessParticipantResourceIndexProvider.ResourceType resourceType =
                        (ProcessParticipantResourceIndexProvider.ResourceType) button
                                .getData();
                RecordingCommand cmd = new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        ParticipantSharedResource sr =
                                getSetParticipantSharedResource(participant,
                                        true);
                        switch (resourceType) {
                        case EMAIL:
                            if (sr.getEmail() == null) {
                                EmailResource er = XpdExtensionFactory.eINSTANCE
                                        .createEmailResource();
                                sr.setSharedResource(er);
                            }
                            break;
                        case JDBC:
                            if (sr.getJdbc() == null) {
                                JdbcResource jr = XpdExtensionFactory.eINSTANCE
                                        .createJdbcResource();
                                jr.setInstanceName(""); //$NON-NLS-1$
                                sr.setSharedResource(jr);
                            }
                            break;
                        case REST_SERVICE:
                            if (sr.getRestService() == null) {
                                RestServiceResource rsr =
                                        XpdExtensionFactory.eINSTANCE
                                                .createRestServiceResource();
                                sr.setSharedResource(rsr);
                            }
                            break;
                        default:
                            sr.setSharedResource(null);
                        }
                    }
                };
                return cmd;
            }
        }
        if (obj == emailInstanceNameText.getControl()) {
            final ParticipantSharedResource sr =
                    getSetParticipantSharedResource(participant, false);
            if (sr != null && sr.getEmail() != null) {
                Text tc = (Text) obj;
                final String text = tc.getText();
                if (!text.equals(sr.getEmail().getInstanceName())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            sr.getEmail().setInstanceName(text.length() > 0 ? text : null);
                        }
                    };
                }
            }
        }
        if (obj == jdbcInstanceNameText) {
            final ParticipantSharedResource sr =
                    getSetParticipantSharedResource(participant, false);
            if (sr != null && sr.getJdbc() != null) {
                Text tc = (Text) obj;
                final String text = tc.getText();
                if (!text.equals(sr.getJdbc().getInstanceName())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            sr.getJdbc().setInstanceName(text);
                        }
                    };
                }
            }
        }
        if (obj == jdbcProfileNameText) {
            final ParticipantSharedResource sr =
                    getSetParticipantSharedResource(participant, false);
            if (sr != null && sr.getJdbc() != null) {
                Text tc = (Text) obj;
                final String text = tc.getText();
                if (!text.equals(sr.getJdbc().getJdbcProfileName())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            sr.getJdbc().setJdbcProfileName(text);
                        }
                    };
                }
            }
        }
        
        if (obj == endPointIdentifierText.getControl()) {
          final ParticipantSharedResource sr =
                  getSetParticipantSharedResource(participant, false);
            if (null != sr && null != sr.getRestService()) {
              Text tc = (Text) obj;
              final String text = tc.getText();

              if (!nullSafe(text).equals(nullSafe(
                        sr.getRestService().getResourceName()))) {
                  return new RecordingCommand(
                          (TransactionalEditingDomain) getEditingDomain()) {
                      @Override
                      protected void doExecute() {
                            sr.getRestService().setResourceName(
                                  text.length() > 0 ? text : null);
                      }
                  };
                }
            }
        }

        if (obj == endPointIdentifierDescText) {
            final ParticipantSharedResource sr =
                    getSetParticipantSharedResource(participant, false);
            if (null != sr && null != sr.getRestService()) {
                Text tc = (Text) obj;
                final String text = tc.getText();

                RestServiceResource rsr = sr.getRestService();

                if (!nullSafe(text).equals(nullSafe(rsr.getDescription()))) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            rsr.setDescription(text.length() > 0 ? text : null);
                        }
                    };
                }
            }
        }
        return null;
    }

    /**
     * @param participant
     * @return
     */
    protected ParticipantSharedResource getSetParticipantSharedResource(
            Participant participant, boolean create) {
        EReference sharedResFeature = XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ParticipantSharedResource();
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(participant, sharedResFeature);
        if (otherElement instanceof ParticipantSharedResource) {
            return (ParticipantSharedResource) otherElement;
        } else if (create) {
            ParticipantSharedResource psr = XpdExtensionFactory.eINSTANCE
                    .createParticipantSharedResource();
            Xpdl2ModelUtil.addOtherElement(participant, sharedResFeature, psr);
            return psr;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        Participant participant = (Participant) getInput();
        if (participant != null) {
            ParticipantTypeElem participantType =
                    participant.getParticipantType();
            if (participantType != null && ParticipantType.SYSTEM_LITERAL
                    .equals(participantType.getType())) {
                ParticipantSharedResource sharedResource =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());
                if (sharedResource != null) {
                    /*
                     * Select/deselect resource type radio buttons and show
                     * corresponding page in the book.
                     */

                    ProcessParticipantResourceIndexProvider.ResourceType resType =
                            getResourceType(sharedResource);

                    for (Button typeButton : typeButtons) {

                        typeButton.setSelection(
                                typeButton.getData().equals(resType));

                        if (typeButton.getData().equals(resType)) {
                            resourceTypeBook.showPage(typeButton.getData());
                        }
                    }
                    refreshResourceTypeBookPages(sharedResource);
                } else {
                    for (Button b : typeButtons) {
                        b.setSelection(false);
                    }
                    resourceTypeBook.showEmptyPage();
                }
            }
        }
    }

    /**
     * Returns the {@link ResourceType} for the given
     * {@link ParticipantSharedResource}, checks for the existence of the
     * respective element in the {@link ParticipantSharedResource}.
     * 
     * @param sharedResource
     * @return {@link ResourceType} for the given
     *         {@link ParticipantSharedResource}
     */
    private ProcessParticipantResourceIndexProvider.ResourceType getResourceType(
            ParticipantSharedResource sharedResource) {
        if (sharedResource.getEmail() != null) {
            return ProcessParticipantResourceIndexProvider.ResourceType.EMAIL;
        }
        if (sharedResource.getJdbc() != null) {
            return ProcessParticipantResourceIndexProvider.ResourceType.JDBC;
        }
        if (sharedResource.getRestService() != null) {
            return ProcessParticipantResourceIndexProvider.ResourceType.REST_SERVICE;
        }

        return null;
    }

    /**
     * @param sharedResource
     */
    private void refreshResourceTypeBookPages(
            ParticipantSharedResource sharedResource) {
        if (sharedResource.getEmail() != null) {
            EmailResource emailResource = sharedResource.getEmail();
            if (emailResource.getInstanceName() != null) {
                ((Text) emailInstanceNameText.getControl()).setText(nullSafe(emailResource.getInstanceName()));
            } else {
                ((Text) emailInstanceNameText.getControl()).setText("");//$NON-NLS-1$
            }

        } else if (sharedResource.getJdbc() != null) {
            jdbcInstanceNameText.setText(
                    nullSafe(sharedResource.getJdbc().getInstanceName()));
            jdbcProfileNameText.setText(
                    nullSafe(sharedResource.getJdbc().getJdbcProfileName()));
        }
        else if (sharedResource.getRestService() != null) {
            RestServiceResource restService = sharedResource.getRestService();
            if (restService.getResourceName() != null) {
                String resName =
                        restService.getResourceName();
                ((Text) endPointIdentifierText.getControl()).setText(resName);
            } else {
                ((Text) endPointIdentifierText.getControl()).setText("");//$NON-NLS-1$
            }
            if (restService.getDescription() != null) {
                String resDesc = restService.getDescription();
                endPointIdentifierDescText.setText(resDesc);
            } else {
                endPointIdentifierDescText.setText(""); //$NON-NLS-1$
            }

        }
    }

    private String nullSafe(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }
}
