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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
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
import com.tibco.xpd.processeditor.xpdl2.properties.util.CommandContentAssistTextHandler;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
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
        extends AbstractFilteredTransactionalSection {

    private List<Button> typeButtons;

    private ScrolledPageBook resourceTypeBook;

    private Text emailInstanceNameText;

    private Text jdbcInstanceNameText;

    private Button wsInboundButton;

    private Button wsOutboundButton;

    private Text jdbcProfileNameText;
    
    private Text endPointIdentifierText;
    
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
                Messages.SharedResourcesSection_EmailInstanceName_label);
        emailInstanceNameText = toolkit.createText(page, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(emailInstanceNameText);
        manageControlUpdateOnDeactivate(emailInstanceNameText);

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
     * TODO Content proposal for Enpoint identifier.
     * 
     * @author sajain
     */
    private static abstract class EndpointIdentifierProposalProvider
            implements IContentProposalProvider {

        @Override
        public IContentProposal[] getProposals(String contents, int position) {

            List<ContentProposal> proposals = new ArrayList<ContentProposal>();

            /*
             * Get all the participants which match the content of the content
             * assist field
             */
            Set<IndexerItem> allParticipantIndexerItems = getAllParticipantIndexerItems();

            for (IndexerItem eachParticipantIndexerItem : allParticipantIndexerItems) {

                String displayName =
                        eachParticipantIndexerItem.getName();

                if (doesProposalMatch(eachParticipantIndexerItem.getName(),
                        contents,
                        position)) {

                    proposals.add(
                            new ContentProposal(eachParticipantIndexerItem.getName(),
                                    displayName));
                }
            }

            return proposals.toArray(new IContentProposal[proposals.size()]);
        }

        /**
         * Get the input of the section.
         * 
         * @return
         */
        protected abstract Participant getInput();

        /**
         * Get all Case class reference participant indexer items that are in
         * scope of the input object.
         * 
         * @return
         */
        private Set<IndexerItem> getAllParticipantIndexerItems() {

            Set<IndexerItem> contentAssistItems = new HashSet<IndexerItem>();

            Collection<IndexerItem> allParticipantIndexerItems =
                    ProcessUIUtil.getAllParticipantIndexerItems();

            for (IndexerItem eachParticipantIndexerItem : allParticipantIndexerItems) {

                // TODO Filter goes here
                if (eachParticipantIndexerItem != null
                        && ProcessParticipantResourceIndexProvider.ResourceType.REST_SERVICE
                                .toString()
                                .equals(eachParticipantIndexerItem.get(
                                        ProcessParticipantResourceIndexProvider.ATTRIBUTE_SHARED_RESOURCE_TYPE))) {
                    contentAssistItems.add(eachParticipantIndexerItem);
                }
            }

            return contentAssistItems;
        }

        /**
         * Check if the value matches the content assist proposal.
         * 
         * @param value
         * @param contentAssistContents
         * @param contentAssistPosition
         * @return
         */
        public boolean doesProposalMatch(String value,
                String contentAssistContents, int contentAssistPosition) {

            if (value != null && !value.isEmpty()
                    && contentAssistContents != null
                    && !contentAssistContents.isEmpty()) {
                String toMatch = null;

                if (!contentAssistContents.isEmpty()) {
                    toMatch = contentAssistPosition > 0
                            ? contentAssistContents.substring(0,
                                    contentAssistPosition)
                            : contentAssistContents;
                }

                return toMatch == null || value.startsWith(toMatch);
            }

            return true;
        }

    }

    /**
     * Provides the same functionality for ContentAssistText fields as the
     * manageControl methods in AbstractXpdSection do for SWT Controls.
     * 
     * @param control
     *            The content assist control to manage.
     */
    protected void manageControl(final ContentAssistText control) {

        new CommandContentAssistTextHandler(control, this);
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
        
        Label endPointIdentifierLabel = toolkit.createLabel(page, Messages.SharedResourcesSection_EndpointIdentifierLabel);
        GridDataFactory.swtDefaults().applyTo(endPointIdentifierLabel);

        /*
         * Content assist text control to enter case reference data.
         */
        ContentAssistText contentAssistText = new ContentAssistText(page,
                toolkit, new EndpointIdentifierProposalProvider() {

                    @Override
                    protected Participant getInput() {

                        EObject input = SharedResourcesSection.this.getInput();

                        return (Participant) (input instanceof Participant
                                ? input
                                : null);
                    }
                });

        manageControl(contentAssistText);

        /*
         * Get the text control from the content assist text control.
         */
        endPointIdentifierText = contentAssistText.getText();

        endPointIdentifierText.setToolTipText(
                Messages.SharedResourcesSection_EndpointIdentifierTooltip);

        GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
        gd1.horizontalIndent = -6;
        contentAssistText.setLayoutData(gd1);

        Label endPointIdentifierDescLabel = toolkit.createLabel(page, Messages.SharedResourcesSection_EndpointIdentifierDescLabel);
        GridDataFactory.swtDefaults().applyTo(endPointIdentifierDescLabel);
        endPointIdentifierDescText = toolkit.createText(page, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false).applyTo(endPointIdentifierDescText);

        manageControlUpdateOnDeactivate(endPointIdentifierText);
        manageControlUpdateOnDeactivate(endPointIdentifierDescText);
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
                                er.setInstanceName(""); //$NON-NLS-1$
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
        if (obj == emailInstanceNameText) {
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
                            sr.getEmail().setInstanceName(text);
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
        
        if (obj == endPointIdentifierText) {
          final ParticipantSharedResource sr =
                  getSetParticipantSharedResource(participant, false);
            if (null != sr && null != sr.getRestService()) {
              Text tc = (Text) obj;
              final String text = tc.getText();

                Collection<IndexerItem> s =
                        ProcessUIUtil.getAllParticipantIndexerItems();

              /*
               * Don't exec command if text is "" and no ext
               * attrib set yet - can confuse the SharedResourceUtil check
               * for same configs.
               */
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

                /*
                 * Don't exec command if text is "" and no ext attrib set yet -
                 * can confuse the SharedResourceUtil check for same configs.
                 */
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
            emailInstanceNameText.setText(
                    nullSafe(sharedResource.getEmail().getInstanceName()));
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
                endPointIdentifierText.setText(resName);
            }
            if (restService.getDescription() != null) {
                String resDesc = restService.getDescription();
                endPointIdentifierDescText.setText(resDesc);
            }

        }
    }

    private String nullSafe(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }
}
