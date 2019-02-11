/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
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

    enum ResourceType {
        EMAIL(Messages.SharedResourcesSection_EmailEnum_button,
                XpdExtensionPackage.eINSTANCE
                        .getParticipantSharedResource_Email(),
                ""), // //$NON-NLS-1$
        JDBC(Messages.SharedResourcesSection_JdbcEnum_button,
                XpdExtensionPackage.eINSTANCE
                        .getParticipantSharedResource_Jdbc(),
                ""), // //$NON-NLS-1$

        REST_SERVICE(Messages.SharedResourcesSection_RestServiceEnum_button,
                XpdExtensionPackage.eINSTANCE
                        .getParticipantSharedResource_RestService(),
                "REST Service invocation shared resource.");

        private final String label;

        private final EStructuralFeature feature;

        private final String description;

        private ResourceType(String label, EStructuralFeature feature,
                String description) {
            this.label = label;
            this.feature = feature;
            this.description = description;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        public EStructuralFeature getFeature() {
            return feature;
        }

        @Override
        public String toString() {
            return label;
        }

        public static ResourceType getByFeature(EStructuralFeature feature) {
            for (ResourceType r : ResourceType.values()) {
                if (r.feature == feature) {
                    return r;
                }
            }
            throw new IllegalArgumentException("Incorrect feature: " + feature); //$NON-NLS-1$
        }
    };

    private List<Button> typeButtons;

    private ScrolledPageBook resourceTypeBook;

    private Text emailInstanceNameText;

    private Text jdbcInstanceNameText;

    private Button wsInboundButton;

    private Button wsOutboundButton;

    private Text jdbcProfileNameText;

    private Text restInstanceNameText;

    private RestSecurityPolicySection restPolicy;

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
        for (ResourceType resourceType : ResourceType.values()) {
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

        for (ResourceType resourceType : ResourceType.values()) {
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
        Label invokeLabel = toolkit.createLabel(page,
                Messages.SharedResourcesSection_RestInvokeUsingLabel);
        GridDataFactory.swtDefaults().span(2, 1).applyTo(invokeLabel);
        Label clientLabel = toolkit.createLabel(page,
                Messages.SharedResourcesSection_RestClientInstanceLabel);
        GridDataFactory.swtDefaults().applyTo(clientLabel);
        restInstanceNameText = toolkit.createText(page, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(restInstanceNameText);

        restPolicy = new RestSecurityPolicySection(
                XpdExtensionPackage.eINSTANCE.getRestServiceResource());
        restPolicy.createControls(page, toolkit);

        manageControlUpdateOnDeactivate(restInstanceNameText);
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
                final ResourceType resourceType =
                        (ResourceType) button.getData();
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
        if (obj == restInstanceNameText) {
            final ParticipantSharedResource sr =
                    getSetParticipantSharedResource(participant, false);
            if (sr != null && sr.getRestService() != null) {
                Text tc = (Text) obj;
                final String text = tc.getText();

                /*
                 * Sid XPD-7543 Don't exec command if text is "" and no ext
                 * attrib set yet - can confuses the SharedResourceUtil check
                 * for same configs.
                 */
                if (!nullSafe(text).equals(nullSafe(
                        sr.getRestService().getHttpClientInstanceName()))) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            sr.getRestService().setHttpClientInstanceName(
                                    text.length() > 0 ? text : null);
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

                    ResourceType resType = getResourceType(sharedResource);

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
    private ResourceType getResourceType(
            ParticipantSharedResource sharedResource) {
        if (sharedResource.getEmail() != null) {
            return ResourceType.EMAIL;
        }
        if (sharedResource.getJdbc() != null) {
            return ResourceType.JDBC;
        }
        if (sharedResource.getRestService() != null) {
            return ResourceType.REST_SERVICE;
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
        } else if (sharedResource.getRestService() != null) {
            RestServiceResource restService = sharedResource.getRestService();
            restInstanceNameText
                    .setText(nullSafe(restService.getHttpClientInstanceName()));
            restPolicy.setInput(Collections.singleton(restService));
            restPolicy.refresh();
        }
    }

    private String nullSafe(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }
}
