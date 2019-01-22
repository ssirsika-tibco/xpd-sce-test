/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.DeliveryMode;
import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * The configuration UI component of web service resource type (acting as a
 * service consumer).
 * 
 * @author Jan Arciuchiewicz
 */
public class WsOutboundSection extends AbstractFilteredTransactionalSection {

    enum OutBindingType {
        VIRTUAL(Messages.WsOutboundSection_VirtualizationEnum_name,
                XpdExtensionPackage.eINSTANCE.getWsVirtualBinding()), //
        SOAP_OVER_HTTP(Messages.WsOutboundSection_SoapOverHttpEnum_name,
                XpdExtensionPackage.eINSTANCE.getWsSoapHttpOutboundBinding()), //
        SOAP_OVER_JMS(Messages.WsOutboundSection_SoapOverJmsEnum_name,
                XpdExtensionPackage.eINSTANCE.getWsSoapJmsOutboundBinding());

        private String label;

        private EClass eClass;

        private OutBindingType(String label, EClass eClass) {
            this.label = label;
            this.eClass = eClass;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return getLabel();
        }

        public static OutBindingType getByEClass(EClass eClass) {
            for (OutBindingType bt : OutBindingType.values()) {
                if (bt.eClass == eClass) {
                    return bt;
                }
            }
            return null;
        }

        public static OutBindingType getOutBindingType(WsOutbound wsOutbound) {
            if (wsOutbound != null) {
                if (wsOutbound.getVirtualBinding() != null) {
                    return VIRTUAL;
                } else if (wsOutbound.getSoapHttpBinding() != null) {
                    return SOAP_OVER_HTTP;
                } else if (null != wsOutbound.getSoapJmsBinding()) {
                    return SOAP_OVER_JMS;
                }
            }
            return null;
        }

        public static WsBinding getWsBinding(WsOutbound wsOutbound,
                OutBindingType bindingType) {
            switch (bindingType) {
            case VIRTUAL:
                return wsOutbound.getVirtualBinding();
            case SOAP_OVER_HTTP:
                return wsOutbound.getSoapHttpBinding();
            case SOAP_OVER_JMS:
                return wsOutbound.getSoapJmsBinding();
            }
            return null;
        }
    };

    private WsBindingSection virtualSection;

    private WsBindingSection soapHttpSection;

    private WsBindingSection soapJmsSection;

    private WsSoapSecurityPolicySection securityPolicySection;

    private WsSoapSecurityPolicySection jmsSecurityPolicySection;

    private WsSoapJmsMessageConfigurationSection jmsMsgConfigSection;

    private ScrolledPageBook bindingDetailsPageBook;

    private List<Button> bindingButtons;

    public WsOutboundSection() {
        super(XpdExtensionPackage.eINSTANCE.getWsOutbound());
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        GridLayoutFactory.fillDefaults().applyTo(parent);
        final Composite rootComposite = toolkit.createComposite(parent);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(rootComposite);
        GridLayoutFactory.fillDefaults().numColumns(1).applyTo(rootComposite);

        // LHS
        Composite bindingButtonsComposite =
                toolkit.createComposite(rootComposite);

        GridDataFactory.swtDefaults().indent(5, 0)
                .align(SWT.BEGINNING, SWT.BEGINNING)
                .applyTo(bindingButtonsComposite);

        GridLayoutFactory.fillDefaults().numColumns(4)
                .applyTo(bindingButtonsComposite);

        toolkit.createLabel(bindingButtonsComposite,
                Messages.WsSoapHttpOutboundBindingSection_BindingDetails_label);

        bindingButtons = new ArrayList<Button>();

        for (OutBindingType bindingType : OutBindingType.values()) {

            Button button =
                    toolkit.createButton(bindingButtonsComposite,
                            bindingType.getLabel(),
                            SWT.RADIO);

            button.setData(bindingType);
            bindingButtons.add(button);
            manageControl(button);

        }

        // RHS
        Composite rhsComposite = toolkit.createComposite(rootComposite);

        GridDataFactory.fillDefaults().indent(5, 0).grab(true, true)
                .applyTo(rhsComposite);

        GridLayoutFactory.fillDefaults().applyTo(rhsComposite);

        bindingDetailsPageBook = toolkit.createPageBook(rhsComposite, SWT.NONE);

        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(bindingDetailsPageBook);

        Point minGeneralSize =
                createBindingDetailsBookPages(bindingDetailsPageBook, toolkit);
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        if (bindingButtons.contains(obj)) {
            final OutBindingType newBindingType =
                    (OutBindingType) ((Button) obj).getData();
            final WsOutbound wsOutbound = getWsOutbound();
            OutBindingType oldBindingType =
                    OutBindingType.getOutBindingType(wsOutbound);
            if (wsOutbound != null && newBindingType != null
                    && !newBindingType.equals(oldBindingType)) {
                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        XpdExtensionFactory factory =
                                XpdExtensionFactory.eINSTANCE;
                        switch (newBindingType) {
                        case VIRTUAL:
                            WsVirtualBinding virtualBinding =
                                    factory.createWsVirtualBindingDefault();
                            wsOutbound.setBinding(virtualBinding);
                            break;
                        case SOAP_OVER_HTTP:
                            WsSoapHttpOutboundBinding soapHttpBinding =
                                    WsOutboundSection
                                            .createDefaultSoapHttpOutBinding();
                            wsOutbound.setBinding(soapHttpBinding);
                            break;
                        case SOAP_OVER_JMS:
                            WsSoapJmsOutboundBinding soapJmsBinding =
                                    WsOutboundSection
                                            .createDefaultSoapJmsOutBinding();
                            wsOutbound.setBinding(soapJmsBinding);
                            break;
                        default:
                            wsOutbound.setBinding(null);
                            break;
                        }
                    }

                };
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        WsOutbound wsOutbound = getWsOutbound();
        OutBindingType bindingType =
                OutBindingType.getOutBindingType(wsOutbound);
        if (wsOutbound != null && bindingType != null) {
            selectBindingTypeButton(bindingType);
            WsBinding wsBinding =
                    OutBindingType.getWsBinding(wsOutbound, bindingType);
            switch (bindingType) {
            case VIRTUAL:
                virtualSection.setInput(Collections.singleton(wsBinding));
                virtualSection.refresh();
                break;
            case SOAP_OVER_HTTP:
                soapHttpSection.setInput(Collections.singleton(wsBinding));
                soapHttpSection.refresh();
                securityPolicySection
                        .setInput(Collections.singleton(wsBinding));
                securityPolicySection.refresh();
                break;
            case SOAP_OVER_JMS:

                soapJmsSection.setInput(Collections.singleton(wsBinding));
                soapJmsSection.refresh();

                jmsSecurityPolicySection.setInput(Collections
                        .singleton(wsBinding));
                jmsSecurityPolicySection.refresh();

                jmsMsgConfigSection.setInput(Collections.singleton(wsBinding));
                jmsMsgConfigSection.refresh();
                break;
            default:
                bindingDetailsPageBook.showEmptyPage();
                Assert.isTrue(false, "Unknown binding type."); //$NON-NLS-1$
                break;
            }
            bindingDetailsPageBook.showPage(bindingType);
        } else {
            selectBindingTypeButton(null);
        }
    }

    /**
     * Selects/Deselects appropriate radio buttons according to bindingType.
     * 
     * @param bindingType
     *            binding type enum to select of 'null' to deselect all.
     */
    private void selectBindingTypeButton(OutBindingType bindingType) {
        for (Button button : bindingButtons) {
            if (bindingType != null && bindingType.equals(button.getData())) {
                button.setSelection(true);
            } else {
                button.setSelection(false);
            }
        }
    }

    private Point createBindingDetailsBookPages(ScrolledPageBook book,
            XpdToolkit toolkit) {
        Composite page = null;

        Point minSize = new Point(0, 0);

        for (OutBindingType bindingType : OutBindingType.values()) {
            page = toolkit.createComposite(book.getContainer());
            GridDataFactory.fillDefaults().grab(true, true).applyTo(page);
            // Create pages for each type
            switch (bindingType) {
            case VIRTUAL:
                createGeneralVirtualPage(page, toolkit);
                break;
            case SOAP_OVER_HTTP:
                createGeneralSoapHttpPage(page, toolkit);
                break;
            case SOAP_OVER_JMS:
                createGeneralSoapJmsPage(page, toolkit);
                break;
            default:
                Assert.isTrue(false, "Unknown binding type."); //$NON-NLS-1$
            }

            Point min = page.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            if (min.x > minSize.x) {
                minSize.x = min.x;
            }
            if (min.y > minSize.y) {
                minSize.y = min.y;
            }

            book.registerPage(bindingType, page);
        }

        return minSize;
    }

    private void createGeneralVirtualPage(Composite page, XpdToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2)
                .extendedMargins(0, 1, 0, 0).applyTo(page);
        virtualSection =
                new WsVirtualBindingSection(
                        XpdExtensionPackage.eINSTANCE.getWsBinding());
        virtualSection.createControls(page, getPropertySheetPage());
    }

    private void createGeneralSoapHttpPage(Composite page, XpdToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2)
                .extendedMargins(0, 1, 0, 0).applyTo(page);
        soapHttpSection =
                new WsSoapHttpOutboundBindingSection(
                        XpdExtensionPackage.eINSTANCE.getWsBinding());
        soapHttpSection.createControls(page, getPropertySheetPage());
        securityPolicySection =
                new WsSoapSecurityPolicySection(
                        XpdExtensionPackage.eINSTANCE.getWsSoapSecurity());
        securityPolicySection.createControls(page, getPropertySheetPage());
    }

    private void createGeneralSoapJmsPage(Composite page, XpdToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2)
                .extendedMargins(0, 1, 0, 0).applyTo(page);
        soapJmsSection =
                new WsSoapJmsOutboundBindingSection(
                        XpdExtensionPackage.eINSTANCE.getWsBinding());

        soapJmsSection.createControls(page, getPropertySheetPage());

        Point soapJMSSectionSize =
                soapJmsSection.getControlsContainer().computeSize(SWT.DEFAULT,
                        SWT.DEFAULT);

        Composite subSection = toolkit.createComposite(page);

        GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
                .applyTo(subSection);

        GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true)
                .applyTo(subSection);

        jmsSecurityPolicySection =
                new WsSoapSecurityPolicyGroupSection(
                        XpdExtensionPackage.eINSTANCE.getWsSoapSecurity());

        jmsSecurityPolicySection.createControls(subSection,
                getPropertySheetPage());

        int maxHeightForSubSection =
                jmsSecurityPolicySection.getControlsContainer()
                        .computeSize(SWT.DEFAULT, SWT.DEFAULT).y;

        jmsMsgConfigSection =
                new WsSoapJmsMessageConfigurationSection(
                        XpdExtensionPackage.eINSTANCE
                                .getWsSoapJmsOutboundBinding());

        jmsMsgConfigSection.createControls(subSection, getPropertySheetPage());

        maxHeightForSubSection =
                Math.max(jmsMsgConfigSection.getControlsContainer()
                        .computeSize(SWT.DEFAULT, SWT.DEFAULT).y,
                        maxHeightForSubSection);

        GridDataFactory
                .fillDefaults()
                .minSize(SWT.DEFAULT,
                        soapJMSSectionSize.y + maxHeightForSubSection)
                .hint(SWT.DEFAULT,
                        soapJMSSectionSize.y + maxHeightForSubSection)
                .grab(true, true).span(2, 1).applyTo(page);
    }

    /**
     * Returns {@link WsOutbound} input object.
     * 
     * @return
     */
    private WsOutbound getWsOutbound() {
        return (WsOutbound) getInput();
    }

    /**
     * Returns SoapHttpOutBinding filled with default values.
     */
    private static WsSoapHttpOutboundBinding createDefaultSoapHttpOutBinding() {
        XpdExtensionFactory f = XpdExtensionFactory.eINSTANCE;
        WsSoapHttpOutboundBinding soapHttpBinding =
                f.createWsSoapHttpOutboundBinding();
        soapHttpBinding.setName("SoapOverHttp"); //$NON-NLS-1$
        soapHttpBinding.setHttpClientInstanceName(""); //$NON-NLS-1$
        return soapHttpBinding;
    }

    /**
     * Returns SoapJmsOutBinding filled with default values.
     */
    private static WsSoapJmsOutboundBinding createDefaultSoapJmsOutBinding() {
        XpdExtensionFactory f = XpdExtensionFactory.eINSTANCE;
        WsSoapJmsOutboundBinding soapJmsBinding =
                f.createWsSoapJmsOutboundBinding();
        soapJmsBinding.setName("SoapOverJms"); //$NON-NLS-1$
        soapJmsBinding.setInboundDestination(""); //$NON-NLS-1$
        soapJmsBinding.setOutboundConnectionFactory(""); //$NON-NLS-1$
        soapJmsBinding.setOutboundDestination(""); //$NON-NLS-1$
        /* Set Default Soap JMS OutBound Message Configuration */
        soapJmsBinding.setPriority(4);
        soapJmsBinding.setInvocationTimeout(6);
        soapJmsBinding.setMessageExpiration(3);
        soapJmsBinding.setDeliveryMode(DeliveryMode.PERSISTENT);
        return soapJmsBinding;
    }
}
