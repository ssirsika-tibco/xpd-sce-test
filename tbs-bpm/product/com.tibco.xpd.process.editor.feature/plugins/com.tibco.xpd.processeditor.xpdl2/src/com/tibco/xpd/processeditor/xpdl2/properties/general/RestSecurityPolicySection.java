/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PolicySetReference;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;

/**
 * Section to configure REST security policy.
 * 
 * @author Nick Wilson
 */
public class RestSecurityPolicySection extends
        AbstractFilteredTransactionalSection {

    private static final String NONE_PAGE_KEY = "NONE"; //$NON-NLS-1$

    private static final String TEMPLATE_PAGE_KEY = "TEMPLATE"; //$NON-NLS-1$

    private static final String CUSTOM_PAGE_KEY = "CUSTOM"; //$NON-NLS-1$

    enum PolicyType {
        NONE(Messages.WsSoapSecurityPolicySection_NoneEnum_name, null,
                NONE_PAGE_KEY), //

        BASIC(Messages.RestSecurityPolicySection_Basic_name,
                SecurityPolicy.BASIC, TEMPLATE_PAGE_KEY), //

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

    private Text identityProvider;

    private ComboViewer policyTypeViewer;

    private PolicySetPickerControl customPolicyControl;

    private CLabel govAppLabel;

    private CLabel customPolicyLabel;

    private ScrolledPageBook policyTypeBook;

    /**
     * @param eClass
     */
    public RestSecurityPolicySection(EClass eClass) {
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
        GridDataFactory
                .swtDefaults()
                .span(2, 1)
                .applyTo(toolkit.createLabel(parent,
                        Messages.WsSoapSecurityPolicySection_SecurityConfig_label));
        indentedGridData.applyTo(toolkit.createLabel(parent,
                Messages.WsSoapSecurityPolicySection_PolicyType_label));
        CCombo policyType = toolkit.createCCombo(parent, SWT.READ_ONLY);
        policyTypeViewer = new ComboViewer(policyType);
        policyTypeViewer.setContentProvider(new ArrayContentProvider());
        policyTypeViewer.setLabelProvider(new LabelProvider());
        policyTypeViewer.setInput(PolicyType.values());
        GridDataFactory.fillDefaults().grab(true, false).applyTo(policyType);
        manageControl(policyType);

        Composite bookWrapper = toolkit.createComposite(parent);

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
        identityProvider = toolkit.createText(templatePage, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(identityProvider);
        manageControlUpdateOnDeactivate(identityProvider);
        maxPageHeight =
                Math.max(templatePage.computeSize(SWT.DEFAULT, SWT.DEFAULT).y,
                        maxPageHeight);

        Composite customPage =
                toolkit.createComposite(policyTypeBook.getContainer());
        policyTypeBook.registerPage(CUSTOM_PAGE_KEY, customPage);
        GridLayoutFactory.fillDefaults().numColumns(2).margins(1, 3)
                .applyTo(customPage);
        customPolicyLabel =
                toolkit.createCLabel(customPage,
                        Messages.WsSoapSecurityPolicySection_CustomPolicySet_label);
        customPolicyLabel
                .setToolTipText(Messages.RestSecurityPolicySection_CustomPolicySet_tooltip);

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

        /**
         * Sid XPD-7882: Recommend use of Application Scope HTTP CLient shared
         * resource.
         */
        Composite filler = toolkit.createComposite(customPage);
        filler.setLayout(new GridLayout());
        GridData gd = new GridData();
        gd.widthHint = 1;
        gd.heightHint = 1;
        filler.setLayoutData(gd);

        Composite labCont = toolkit.createComposite(customPage);
        GridLayout gl = new GridLayout(2, false);
        gl.marginWidth = gl.marginHeight = 0;
        labCont.setLayout(gl);

        gd = new GridData(GridData.FILL_BOTH);
        gd.widthHint = 1;
        gd.heightHint = 1;
        labCont.setLayoutData(gd);

        Label warningLab = toolkit.createLabel(labCont, ""); //$NON-NLS-1$
        warningLab.setImage(Xpdl2ProcessEditorPlugin.getDefault()
                .getImageRegistry().get(ProcessEditorConstants.ICON_WARNING));
        warningLab.setLayoutData(new GridData());

        Label shareResHintLabel =
                toolkit.createLabel(labCont,
                        Messages.RestSecurityPolicySection_CustomHttpClientResourceWarning_message);
        gd = new GridData();
        shareResHintLabel.setLayoutData(gd);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        final RestServiceResource resource = (RestServiceResource) getInput();
        if (resource != null) {
            final RestServiceResourceSecurity security = getSecurity(resource);
            final ExtendedAttributeManager eam = new ExtendedAttributeManager();
            if (obj == identityProvider) {
                String oldIdentityProvider =
                        eam.getAttribute(security,
                                RestServiceUtil.IDENTITY_PROVIDER_ATTRIBUTE);
                Text tc = (Text) obj;

                final String newText = tc.getText();

                /*
                 * Sid XPD-7543 Don't exec command if text is "" and no ext
                 * attrib set yet - can confuses the SharedResourceUtil check
                 * for same configs.
                 */
                if (!nullSafe(newText).equals(nullSafe(oldIdentityProvider))) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            RestServiceResourceSecurity newSecurity =
                                    getOrCreateSecurity(resource);
                            eam.setAttribute(newSecurity,
                                    RestServiceUtil.IDENTITY_PROVIDER_ATTRIBUTE,
                                    newText.length() > 0 ? newText : null);
                        }

                    };
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
                final SecurityPolicy oldSecurity =
                        security == null ? null : security.getPolicyType();
                final PolicyType oldPolicyType =
                        oldSecurity == null ? PolicyType.NONE : PolicyType
                                .getSecurityPolicy(oldSecurity);
                if (!newPolicyType.equals(oldPolicyType)) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            resource.getSecurityPolicy().clear();
                            if (!PolicyType.NONE.equals(newPolicyType)) {
                                SecurityPolicy newSecurityPolicy =
                                        newPolicyType.getSecurityPolicy();
                                RestServiceResourceSecurity newSecurity =
                                        getOrCreateSecurity(resource);
                                newSecurity.setPolicyType(newSecurityPolicy);
                            }
                        }
                    };
                }
            }
        }
        return null;
    }

    /**
     * @param resource
     * @param security
     * @return
     */
    private RestServiceResourceSecurity getOrCreateSecurity(
            final RestServiceResource resource) {
        RestServiceResourceSecurity newSecurity = getSecurity(resource);
        if (newSecurity == null) {
            newSecurity =
                    XpdExtensionFactory.eINSTANCE
                            .createRestServiceResourceSecurity();
            resource.getSecurityPolicy().clear();
            resource.getSecurityPolicy().add(newSecurity);
        }
        return newSecurity;
    }

    /**
     * @param resource
     * @return
     */
    private RestServiceResourceSecurity getSecurity(RestServiceResource resource) {
        RestServiceResourceSecurity security = null;
        if (resource != null) {
            EList<RestServiceResourceSecurity> policies =
                    resource.getSecurityPolicy();
            if (!policies.isEmpty()) {
                security = policies.get(0);
            }
        }
        return security;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        final RestServiceResource resource = (RestServiceResource) getInput();
        if (resource != null) {
            final RestServiceResourceSecurity security = getSecurity(resource);
            final SecurityPolicy securitypolicy =
                    security == null ? null : security.getPolicyType();
            final PolicyType policyType =
                    securitypolicy == null ? PolicyType.NONE : PolicyType
                            .getSecurityPolicy(securitypolicy);
            policyTypeViewer.setSelection(new StructuredSelection(policyType),
                    true);
            policyTypeBook.showPage(policyType.getPageKey());
            if (TEMPLATE_PAGE_KEY.equals(policyType.getPageKey())) {
                ExtendedAttributeManager eam = new ExtendedAttributeManager();
                identityProvider.setText(nullSafe(eam.getAttribute(security,
                        RestServiceUtil.IDENTITY_PROVIDER_ATTRIBUTE)));
            }

            if (policyType == PolicyType.BASIC) {

                final ExtendedAttributeManager eam =
                        new ExtendedAttributeManager();

                String idProvider =
                        eam.getAttribute(security,
                                RestServiceUtil.IDENTITY_PROVIDER_ATTRIBUTE);

                boolean errorIcon = false;

                if (null == idProvider || idProvider.trim().length() == 0) {

                    govAppLabel
                            .setToolTipText(Messages.PolicySetReference_Rs_NoGovApp_tooltip);

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
            } else if (policyType == PolicyType.CUSTOM) {

                PolicySetReference psr =
                        PolicySetReference.getPolicySetReference(security);

                customPolicyControl.setValue(psr);

                /*
                 * XPD-7484: Saket: Need to ensure that we show an error icon on
                 * the UI in case of an unresolved reference.
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
                                .getImageRegistry()
                                .get(Xpdl2UiPlugin.IMG_ERROR));

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
    }

    protected String nullSafe(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }
}
