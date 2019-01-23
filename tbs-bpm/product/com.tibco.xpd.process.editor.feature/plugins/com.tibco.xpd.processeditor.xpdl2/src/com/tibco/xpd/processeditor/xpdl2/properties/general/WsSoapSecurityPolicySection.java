/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Arrays;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PolicySetReference;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;

/**
 * Section to configure of SOAP security policy.
 * 
 * @author Jan Arciuchiewicz
 */
public class WsSoapSecurityPolicySection extends
        AbstractFilteredTransactionalSection {

    private static final String NONE_PAGE_KEY = "NONE"; //$NON-NLS-1$

    private static final String TEMPLATE_PAGE_KEY = "TEMPLATE"; //$NON-NLS-1$

    private static final String CUSTOM_PAGE_KEY = "CUSTOM"; //$NON-NLS-1$

    enum PolicyType {
        NONE(Messages.WsSoapSecurityPolicySection_NoneEnum_name, null,
                NONE_PAGE_KEY), //

        USERNAME_TOKEN(Messages.WsSoapSecurityPolicySection_UsernameToken_name,
                SecurityPolicy.USERNAME_TOKEN, TEMPLATE_PAGE_KEY), //

        X509_TOKEN(Messages.WsSoapSecurityPolicySection_X509Token_name,
                SecurityPolicy.X509_TOKEN, TEMPLATE_PAGE_KEY), //

        SAML_TOKEN(Messages.WsSoapSecurityPolicySection_SamlToken_name,
                SecurityPolicy.SAML_TOKEN, TEMPLATE_PAGE_KEY), //

        CUSTOM(Messages.WsSoapSecurityPolicySection_CustomPolicy_name,
                SecurityPolicy.CUSTOM, CUSTOM_PAGE_KEY);

        private String label;

        private SecurityPolicy securityPolicy;

        private final Object pageKey;

        private PolicyType(String label, SecurityPolicy securityPolicy,
                Object pageKey) {
            this.label = label;
            this.securityPolicy = securityPolicy;
            this.pageKey = pageKey;
        }

        public static PolicyType getSecurityPolicy(SecurityPolicy securityPolicy) {
            if (securityPolicy == null) {
                return PolicyType.NONE;
            }
            for (PolicyType pt : PolicyType.values()) {
                if (pt.securityPolicy == securityPolicy) {
                    return pt;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return label;
        }

        public SecurityPolicy getSecurityPolicy() {
            return securityPolicy;
        }

        public Object getPageKey() {
            return pageKey;
        }
    }

    protected static final GridDataFactory indentedGridData = GridDataFactory
            .swtDefaults().indent(5, 0);

    private Text govAppNameText;

    private ComboViewer policyTypeViewer;

    private PolicySetPickerControl customPolicyControl;

    private CLabel govAppLabel;

    private CLabel customPolicyLabel;

    private ScrolledPageBook policyTypeBook;

    /**
     * @param eClass
     */
    public WsSoapSecurityPolicySection(EClass eClass) {
        super(eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite parentContainer = toolkit.createComposite(parent);

        GridLayoutFactory.fillDefaults().numColumns(2).margins(1, 0)
                .applyTo(parentContainer);
        // Did not check for type of Layout on parent, as it is already
        // implemented taken into consideration that parent has GridLayout set
        GridDataFactory.fillDefaults()
                .span(((GridLayout) parent.getLayout()).numColumns, 0)
                .grab(true, true).applyTo(parentContainer);

        createSeurityPolicyControls(parentContainer, toolkit);
        return parentContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#getMinimumHeight()
     * 
     * @return
     */
    @Override
    public int getMinimumHeight() {
        return 200;
    }

    protected void createSeurityPolicyControls(Composite parent,
            XpdFormToolkit toolkit) {

        Composite container = getContainer(parent, toolkit);

        indentedGridData.applyTo(toolkit.createLabel(container,
                Messages.WsSoapSecurityPolicySection_PolicyType_label));

        CCombo policyType =
                toolkit.createCCombo(container, getPolicyComboStyle());

        policyTypeViewer = new ComboViewer(policyType);
        policyTypeViewer.setContentProvider(new ArrayContentProvider());
        policyTypeViewer.setLabelProvider(new LabelProvider());
        policyTypeViewer.setInput(PolicyType.values());
        GridDataFactory.fillDefaults().grab(true, false).applyTo(policyType);
        manageControl(policyType);

        Composite bookWrapper = toolkit.createComposite(container);

        bookWrapper.setLayout(new GridLayout(1, false));

        policyTypeBook = toolkit.createPageBook(bookWrapper, SWT.NONE);

        GridDataFactory.fillDefaults().indent(0, 5).grab(true, true)
                .applyTo(policyTypeBook);

        // Create layout and register pages.
        int maxPageHeight = 0;

        Composite nonePage =
                toolkit.createComposite(policyTypeBook.getContainer());

        policyTypeBook.registerPage(NONE_PAGE_KEY, nonePage);

        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(nonePage);

        maxPageHeight =
                Math.max(nonePage.computeSize(SWT.DEFAULT, SWT.DEFAULT).y,
                        maxPageHeight);

        Composite templatePage =
                toolkit.createComposite(policyTypeBook.getContainer());

        policyTypeBook.registerPage(TEMPLATE_PAGE_KEY, templatePage);

        GridLayoutFactory.fillDefaults().numColumns(2).margins(1, 3)
                .applyTo(templatePage);

        govAppLabel =
                toolkit.createCLabel(templatePage,
                        Messages.WsSoapSecurityPolicySection_GovAppName_label);

        govAppLabel
                .setToolTipText(Messages.WsSoapSecurityPolicySection_GovAppName_tooltip);

        indentedGridData.applyTo(govAppLabel);
        govAppNameText = toolkit.createText(templatePage, ""); //$NON-NLS-1$

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(govAppNameText);

        manageControlUpdateOnDeactivate(govAppNameText);

        maxPageHeight =
                Math.max(templatePage.computeSize(SWT.DEFAULT, SWT.DEFAULT).y,
                        maxPageHeight);

        Composite customPage =
                toolkit.createComposite(policyTypeBook.getContainer());

        policyTypeBook.registerPage(CUSTOM_PAGE_KEY, customPage);
        // GridDataFactory.fillDefaults().grab(true, false).applyTo(customPage);
        GridLayoutFactory.fillDefaults().numColumns(2).margins(1, 3)
                .applyTo(customPage);

        customPolicyLabel =
                toolkit.createCLabel(customPage,
                        Messages.WsSoapSecurityPolicySection_CustomPolicySet_label);

        customPolicyLabel
                .setToolTipText(Messages.WsSoapSecurityPolicySection_CustomPolicySet_tooltip);

        indentedGridData.applyTo(customPolicyLabel);

        customPolicyControl =
                new PolicySetPickerControl(this, customPage, toolkit,
                        getEditingDomain());

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(customPolicyControl);

        maxPageHeight =
                Math.max(customPage.computeSize(SWT.DEFAULT, SWT.DEFAULT).y,
                        maxPageHeight);

        GridDataFactory.fillDefaults().minSize(SWT.DEFAULT, maxPageHeight)
                .hint(SWT.DEFAULT, maxPageHeight).grab(true, true).span(2, 1)
                .applyTo(bookWrapper);
    }

    /**
     * Returns the style for the Policy Combo drop down .
     * 
     * @return SWT.READ_ONLY.
     */
    protected int getPolicyComboStyle() {
        return SWT.READ_ONLY;
    }

    /**
     * Returns Container {@link Composite} for the controls of this Section.
     * 
     * @param parent
     * @param toolkit
     * @return {@link Group} Container.
     */
    protected Composite getContainer(Composite parent, XpdFormToolkit toolkit) {

        GridDataFactory
                .swtDefaults()
                .span(2, 1)
                .applyTo(toolkit.createLabel(parent,
                        Messages.WsSoapSecurityPolicySection_SecurityConfig_label));

        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        final WsBinding binding = (WsBinding) getInput();
        if (binding != null) {
            if (obj == govAppNameText) {
                final WsSoapSecurity oldSoapSecurity = getSoapSecurity();
                final PolicyType oldPolicyType =
                        oldSoapSecurity == null ? PolicyType.NONE
                                : (oldSoapSecurity.getSecurityPolicy()
                                        .isEmpty() ? PolicyType.NONE
                                        : PolicyType
                                                .getSecurityPolicy(oldSoapSecurity
                                                        .getSecurityPolicy()
                                                        .get(0).getType()));
                if (oldSoapSecurity != null && oldPolicyType != PolicyType.NONE
                        && !oldSoapSecurity.getSecurityPolicy().isEmpty()) {
                    String oldGovernanceApplicationName =
                            oldSoapSecurity.getSecurityPolicy().get(0)
                                    .getGovernanceApplicationName();
                    Text tc = (Text) obj;
                    final String newText = tc.getText();
                    if (!newText.equals(oldGovernanceApplicationName)) {
                        return new RecordingCommand(
                                (TransactionalEditingDomain) getEditingDomain()) {
                            @Override
                            protected void doExecute() {
                                oldSoapSecurity.getSecurityPolicy().get(0)
                                        .setGovernanceApplicationName(newText);
                            }
                        };
                    }
                }
            }
            if (obj == policyTypeViewer.getCCombo()) {
                IStructuredSelection s =
                        (IStructuredSelection) policyTypeViewer.getSelection();
                if (s.isEmpty()) {
                    return null;
                }
                final PolicyType newPolicyType =
                        !s.isEmpty() ? (PolicyType) s.getFirstElement()
                                : PolicyType.NONE;
                final WsSoapSecurity oldSoapSecurity = getSoapSecurity();
                final PolicyType oldPolicyType =
                        oldSoapSecurity == null ? PolicyType.NONE
                                : (oldSoapSecurity.getSecurityPolicy()
                                        .isEmpty() ? PolicyType.NONE
                                        : PolicyType
                                                .getSecurityPolicy(oldSoapSecurity
                                                        .getSecurityPolicy()
                                                        .get(0).getType()));
                if (!newPolicyType.equals(oldPolicyType)) {
                    // If the new policy is of type NONE then in model the
                    // WsSoapSecurity element will be empty, otherwise it will
                    // contain exactly one WsSecurtyPolicy element.
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            WsSoapSecurity newSoapSecurity =
                                    oldSoapSecurity == null ? XpdExtensionFactory.eINSTANCE
                                            .createWsSoapSecurity()
                                            : oldSoapSecurity;
                            if (newPolicyType != PolicyType.NONE) {
                                WsSecurityPolicy securityPolicy;
                                if (!newSoapSecurity.getSecurityPolicy()
                                        .isEmpty()) {
                                    securityPolicy =
                                            newSoapSecurity.getSecurityPolicy()
                                                    .get(0);
                                } else {
                                    securityPolicy =
                                            XpdExtensionFactory.eINSTANCE
                                                    .createWsSecurityPolicy();
                                    newSoapSecurity.getSecurityPolicy()
                                            .add(securityPolicy);
                                }
                                securityPolicy.setType(newPolicyType
                                        .getSecurityPolicy());
                            } else {
                                newSoapSecurity.getSecurityPolicy().clear();
                            }
                            if (oldSoapSecurity == null) {
                                setSoapSecurity(newSoapSecurity);
                            }
                        }
                    };
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        final WsSoapSecurity oldSoapSecurity = getSoapSecurity();
        final PolicyType policyType =
                oldSoapSecurity == null ? PolicyType.NONE : (oldSoapSecurity
                        .getSecurityPolicy().isEmpty() ? PolicyType.NONE
                        : PolicyType.getSecurityPolicy(oldSoapSecurity
                                .getSecurityPolicy().get(0).getType()));
        policyTypeViewer
                .setSelection(new StructuredSelection(policyType), true);

        policyTypeBook.showPage(policyType.getPageKey());

        if (TEMPLATE_PAGE_KEY.equals(policyType.getPageKey())) {
            String governanceApplicationName =
                    oldSoapSecurity.getSecurityPolicy().get(0)
                            .getGovernanceApplicationName();
            govAppNameText.setText(nullSafe(governanceApplicationName));
        }

        if (oldSoapSecurity != null && policyType != PolicyType.NONE
                && !oldSoapSecurity.getSecurityPolicy().isEmpty()) {

            EList<WsSecurityPolicy> securityPolicy =
                    oldSoapSecurity.getSecurityPolicy();

            for (WsSecurityPolicy wsSecurityPolicy : securityPolicy) {

                SecurityPolicy type = wsSecurityPolicy.getType();

                if (Arrays.asList(SecurityPolicy.USERNAME_TOKEN,
                        SecurityPolicy.X509_TOKEN,
                        SecurityPolicy.SAML_TOKEN).contains(type)) {

                    String applicationName =
                            wsSecurityPolicy.getGovernanceApplicationName();

                    boolean errorIcon = false;

                    if (null == applicationName
                            || applicationName.trim().length() == 0) {

                        govAppLabel
                                .setToolTipText(Messages.PolicySetReference_Ws_NoGovApp_tooltip);

                        errorIcon = true;

                    } else {

                        govAppLabel
                                .setToolTipText(Messages.WsSoapSecurityPolicySection_GovAppName_tooltip);

                    }

                    if (errorIcon) {

                        if (null == govAppLabel.getImage()) {

                            govAppLabel.setImage(Xpdl2UiPlugin.getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2UiPlugin.IMG_ERROR));

                            govAppLabel.getParent().layout(true);

                        }

                    } else {

                        if (govAppLabel.getImage() != null) {

                            govAppLabel.setImage(null);

                            govAppLabel.getParent().layout(true);
                        }

                    }
                }
            }
        }

        if (policyType == PolicyType.CUSTOM) {

            PolicySetReference psr =
                    PolicySetReference.getPolicySetReference(getSoapSecurity());

            customPolicyControl.setValue(psr);

            /*
             * XPD-7484: Saket: Need to ensure that we show an error icon on the
             * UI in case of an unresolved reference.
             */

            boolean errorIcon = false;

            if (psr.getName() == null || psr.getName().length() == 0) {

                customPolicyLabel
                        .setToolTipText(Messages.PolicySetReference_NoValueSet_tooltip);

                errorIcon = true;

            } else if (!psr.policySetExists()) {

                customPolicyLabel
                        .setToolTipText(String
                                .format(Messages.PolicySetReference_UnresolvedReference_tooltip,
                                        psr.toString()));

                errorIcon = true;

            } else {

                customPolicyLabel
                        .setToolTipText(Messages.RestSecurityPolicySection_CustomPolicySet_tooltip);
            }

            if (errorIcon) {

                if (null == customPolicyLabel.getImage()) {

                    customPolicyLabel.setImage(Xpdl2UiPlugin.getDefault()
                            .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));

                    customPolicyLabel.getParent().layout(true);

                }

            } else {
                if (customPolicyLabel.getImage() != null) {

                    customPolicyLabel.setImage(null);

                    customPolicyLabel.getParent().layout(true);
                }

            }
        }

    }

    protected String nullSafe(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }

    private WsSoapSecurity getSoapSecurity() {
        EObject input = getInput();
        if (input instanceof WsSoapBinding) {
            return ((WsSoapBinding) input).getSoapSecurity();
        }
        return null;
    }

    private void setSoapSecurity(WsSoapSecurity soapSecurity) {
        EObject input = getInput();
        if (input instanceof WsSoapBinding) {
            ((WsSoapBinding) input).setSoapSecurity(soapSecurity);

        }
    }
}
