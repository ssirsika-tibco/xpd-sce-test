/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PolicySetReference;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;

/**
 * 
 */
/* package */class PolicySetPickerControl extends
        AbstractPickerControl<PolicySetReference> {

    private final AbstractFilteredTransactionalSection section;

    public PolicySetPickerControl(AbstractFilteredTransactionalSection section,
            Composite parent, XpdFormToolkit toolkit,
            EditingDomain editingDomain) {
        /*
         * Note that we do not create the clear button on purpose here because
         * if the policy type is custom policy then there should be a policy set
         * specified.
         */
        super(parent, SWT.FILL, toolkit, editingDomain, /* Show clear button. */
        false);
        this.section = section;
        setBrowseTooltip(Messages.PolicySetPickerControl_SelectPolicySet_tooltip);
        setLabelProvider(new LabelProvider());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.components.AbstractPickerControl#
     * hyperlinkActivated(java.lang.Object)
     */
    @Override
    protected void hyperlinkActivated(PolicySetReference value) {
        if (value != null) {
            IFile file = value.getFile();
            if (file != null && file.isAccessible()) {
                IWorkbenchPage activePage =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();

                try {
                    /*
                     * XPD-7672: We should not use superclass
                     * openInEditor(IFile) method as it depeneds on any
                     * contributed Editors(example BOM, OM, JSON etc) as policy
                     * sets does not have any contributed editor (is xml based
                     * editor) we use IDE to open it.
                     */
                    IDE.openEditor(activePage, file, true);

                } catch (PartInitException e) {
                    ErrorDialog
                            .openError(getShell(),
                                    Messages.PolicySetPickerControl_FailedToOpenPoliSetErrorDialog_title,
                                    String.format(Messages.PolicySetPickerControl_FailedToOpenPoliSetErrorDialog_desc,
                                            file.getProjectRelativePath()
                                                    .toString()),
                                    e.getStatus());
                    Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
                } catch (Exception e) {
                    Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#createHyperlink(com.tibco.xpd.ui.properties.XpdFormToolkit,
     *      org.eclipse.swt.widgets.Composite)
     * 
     * @param toolkit
     * @param root
     * @return
     */
    @Override
    protected ImageHyperlink createHyperlink(XpdFormToolkit toolkit,
            Composite root) {
        ImageHyperlink link = super.createHyperlink(toolkit, root);
        // GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
        // .grab(true, false).minSize(90, SWT.DEFAULT)
        // .hint(200, SWT.DEFAULT).applyTo(link);
        return link;
    }

    @Override
    protected PolicySetReference doBrowse(Control control) {
        EObject input = section.getInput();
        if (input != null) {
            return PolicySetPickerUtil.browsePolicySetReference(control
                    .getShell());
        }
        return null;
    }

    @Override
    protected Command getClearValueCommand(EditingDomain ed,
            final PolicySetReference value) {
        Command cmd = null;
        final ExtendedAttributesContainer eac =
                getExtendedAttributesContainer();
        if (eac != null) {
            cmd =
                    new RecordingCommand(
                            (TransactionalEditingDomain) ed,
                            Messages.PolicySetPickerControl_ClearSecurityPolicy_action) {
                        @Override
                        protected void doExecute() {
                            PolicySetReference
                                    .clearCustomPolicySetAttributes(eac);
                        }
                    };
        }
        return cmd;

    }

    @Override
    protected Command getSetValueCommand(EditingDomain ed,
            final PolicySetReference value) {
        Command cmd = null;
        final ExtendedAttributesContainer eac =
                getExtendedAttributesContainer();
        if (eac != null) {
            cmd =
                    new RecordingCommand(
                            (TransactionalEditingDomain) ed,
                            Messages.PolicySetPickerControl_SetSecurityPolicy_action) {
                        @Override
                        protected void doExecute() {
                            value.setCustomPolicySet(eac);

                        }
                    };
        }
        return cmd;
    }

    @Override
    public void setValue(PolicySetReference value) {
        super.setValue(value);
        /*
         * If this value is from a resource in the workspace then enable the
         * hyperlink, otherwise don't as user won't be able to 'go to' the
         * resource
         */
        setHyperlinkActive(value != null && value.getFile() != null
                && value.getFile().isAccessible());
    }

    /**
     */
    public void setIsInvalidValue() {
        super.setValue(null);
        setHyperlinkActive(false);
    }

    @Override
    protected String getClearText() {
        // if (value != null) {
        // if (value == INVALID_VALUE) {
        // return "- invalid value set -";
        // } else if (value instanceof EObject && ((EObject) value).eIsProxy())
        // {
        // return "- Unresolved Reference -";
        // }
        // }
        return super.getClearText();
    }

    private ExtendedAttributesContainer getExtendedAttributesContainer() {
        EObject sectionInput = section.getInput();
        if (sectionInput instanceof RestServiceResource) {
            EList<RestServiceResourceSecurity> policy =
                    ((RestServiceResource) sectionInput).getSecurityPolicy();
            if (!policy.isEmpty()) {
                sectionInput = policy.get(0);
            }
        }
        if (sectionInput instanceof WsSoapBinding) {
            WsSoapSecurity soapSecurity =
                    ((WsSoapBinding) sectionInput).getSoapSecurity();
            if (soapSecurity != null) {
                EList<WsSecurityPolicy> policy =
                        soapSecurity.getSecurityPolicy();
                if (!policy.isEmpty()) {
                    sectionInput = policy.get(0);
                }
            }
        }
        if (sectionInput instanceof ExtendedAttributesContainer) {
            return ((ExtendedAttributesContainer) sectionInput);
        }
        return null;
    }

}
