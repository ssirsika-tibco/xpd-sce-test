/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsSoapBinding;

/**
 * Abstract section providing UI component common to SOAP inbound (provider)
 * bindings.
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class WsSoapBindingSection extends WsBindingSection {

    public enum SoapVersion {
        SOAP_11("1.1"), //$NON-NLS-1$

        SOAP_12("1.2"); //$NON-NLS-1$

        private String versionName;

        private SoapVersion(String label) {
            this.versionName = label;
        }

        public String getName() {
            return versionName;
        }

        public static SoapVersion findByName(String name) {
            for (SoapVersion sv : values()) {
                if (sv.versionName.equals(name)) {
                    return sv;
                }
            }
            return null;
        }

        public static SoapVersion getDefault() {
            return SOAP_11;
        }

        @Override
        public String toString() {
            return versionName;
        }
    };

    enum BindingStyle {
        RPC_LITERAL(Messages.WsSoapBindingSection_RpcLiteralEnum_name,
                SoapBindingStyle.RPC_LITERAL),

        DOCUMENT_LITERAL(
                Messages.WsSoapBindingSection_DocumentLiteralEnum_name,
                SoapBindingStyle.DOCUMENT_LITERAL);

        private String label;

        private SoapBindingStyle soapStyle;

        private static String[] labels;

        static {
            List<String> list = new ArrayList<String>();
            for (BindingStyle bs : BindingStyle.values()) {
                list.add(bs.label);
            }
            labels = list.toArray(new String[list.size()]);
        }

        private BindingStyle(String label, SoapBindingStyle soapStyle) {
            this.label = label;
            this.soapStyle = soapStyle;
        }

        public static BindingStyle getBySoapBindingStyle(
                SoapBindingStyle soapSyle) {
            for (BindingStyle bs : BindingStyle.values()) {
                if (bs.soapStyle == soapSyle) {
                    return bs;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return label;
        }

        public SoapBindingStyle getSoapBindingStyle() {
            return soapStyle;
        }

    }

    private ComboViewer bindingStyleViewer;

    private ComboViewer versionComboViewer;

    public WsSoapBindingSection(EClass eClass) {
        super(eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(parent);
        createNameControl(parent, toolkit);
        createSoapControls(parent, toolkit);
        return parent;
    }

    protected void createSoapControls(Composite parent, XpdFormToolkit toolkit) {
        indentedGridData.applyTo(toolkit.createLabel(parent,
                Messages.WsSoapBindingSection_BindingStyle_label));
        CCombo bindingStyle = toolkit.createCCombo(parent, SWT.READ_ONLY);
        bindingStyleViewer = new ComboViewer(bindingStyle);
        bindingStyleViewer.setContentProvider(new ArrayContentProvider());
        bindingStyleViewer.setLabelProvider(new LabelProvider());
        bindingStyleViewer.setInput(BindingStyle.values());
        GridDataFactory.fillDefaults().applyTo(bindingStyle);
        manageControl(bindingStyle);

        indentedGridData.applyTo(toolkit.createLabel(parent,
                Messages.WsSoapBindingSection_SoapVersion_label));
        CCombo versionCombo = toolkit.createCCombo(parent);
        versionComboViewer = new ComboViewer(versionCombo);
        versionComboViewer.setContentProvider(new ArrayContentProvider());
        versionComboViewer.setLabelProvider(new LabelProvider());
        versionComboViewer.setInput(SoapVersion.values());
        GridDataFactory.fillDefaults().applyTo(versionCombo);
        manageControl(versionCombo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = super.doGetCommand(obj);
        if (cmd != null)
            return cmd;

        final WsSoapBinding binding = (WsSoapBinding) getInput();
        if (obj == bindingStyleViewer.getCCombo()) {
            if (binding != null) {
                IStructuredSelection sel =
                        (IStructuredSelection) bindingStyleViewer
                                .getSelection();
                final Object first =
                        !sel.isEmpty() ? sel.getFirstElement() : null;

                if (first instanceof BindingStyle) {
                    final SoapBindingStyle soapBindingStyle =
                            ((BindingStyle) first).getSoapBindingStyle();
                    if (soapBindingStyle != null
                            && !soapBindingStyle.equals(binding
                                    .getBindingStyle())) {
                        return new RecordingCommand(
                                (TransactionalEditingDomain) getEditingDomain()) {
                            @Override
                            protected void doExecute() {
                                binding.setBindingStyle(soapBindingStyle);
                            }
                        };
                    }
                }
            }
        }
        if (obj == versionComboViewer.getCCombo()) {
            if (binding != null) {
                IStructuredSelection sel =
                        (IStructuredSelection) versionComboViewer
                                .getSelection();
                final Object first =
                        !sel.isEmpty() ? sel.getFirstElement() : null;
                if (first instanceof SoapVersion) {
                    final String version = ((SoapVersion) first).getName();
                    if (!version.equals(binding.getSoapVersion())) {
                        return new RecordingCommand(
                                (TransactionalEditingDomain) getEditingDomain()) {
                            @Override
                            protected void doExecute() {
                                binding.setSoapVersion(version);
                            }
                        };
                    }
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
        super.doRefresh();
        refreshSoapControls();
    }

    protected void refreshSoapControls() {
        WsSoapBinding binding = (WsSoapBinding) getInput();
        if (binding != null) {
            BindingStyle bindingStyle =
                    BindingStyle.getBySoapBindingStyle(binding
                            .getBindingStyle());
            bindingStyleViewer
                    .setSelection(bindingStyle != null ? new StructuredSelection(
                            bindingStyle) : StructuredSelection.EMPTY);

            SoapVersion soapVersion =
                    SoapVersion.findByName(binding.getSoapVersion());
            versionComboViewer
                    .setSelection((soapVersion != null ? new StructuredSelection(
                            soapVersion) : StructuredSelection.EMPTY));
        }
    }
}
