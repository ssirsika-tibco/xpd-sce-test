/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.QualifierSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for the {@link QualifiedOrgElement} object.
 * 
 * @author njpatel
 * 
 */
public class QualifiedOrgElementSection extends AbstractGeneralSection {

    private Button hasQualifierBtn;

    private Hyperlink showQualifierTabLink;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        // Create dummy label for layout
        createLabel(root, toolkit, ""); //$NON-NLS-1$

        Composite qualifierComposite = toolkit.createComposite(root);
        qualifierComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        qualifierComposite.setLayout(layout);

        hasQualifierBtn =
                toolkit.createButton(qualifierComposite,
                        Messages.QualifiedOrgElementSection_HasQualifier_label,
                        SWT.CHECK,
                        "qualifiedOrgElement-qualifier"); //$NON-NLS-1$

        // Use listener rather than manage control as we need to call refresh
        // tabs soon after command execution
        hasQualifierBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Command cmd = doGetCommand(hasQualifierBtn);
                if (cmd != null) {
                    getEditingDomain().getCommandStack().execute(cmd);
                    // Refresh to show/hide Qualifier tab
                    refreshTabs();
                }
            }
        });

        showQualifierTabLink =
                toolkit.createHyperlink(qualifierComposite,
                        Messages.QualifiedOrgElementSection_ShowQualifier_label,
                        SWT.NONE,
                        "qualification-jump-hyperlink"); //$NON-NLS-1$
        showQualifierTabLink.addHyperlinkListener(new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                showPropertyTab(QualifierSection.QUALIFIER_TAB_ID);
            }
        });
        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        EObject input = getInput();

        if (input instanceof QualifiedOrgElement) {
            if (obj == hasQualifierBtn) {
                Object value = SetCommand.UNSET_VALUE;
                if (hasQualifierBtn.getSelection()) {
                    Attribute attr = OMFactory.eINSTANCE.createAttribute();
                    attr.setName(Messages.QualifiedOrgElementSection_qualifier_name);
                    attr.setDisplayName(attr.getName());
                    attr.setType(AttributeType.TEXT);
                    value = attr;
                }
                // Set the attribute value
                return SetCommand.create(getEditingDomain(),
                        input,
                        OMPackage.eINSTANCE
                                .getQualifiedOrgElement_QualifierAttribute(),
                        value);
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof QualifiedOrgElement) {
            Attribute attribute =
                    ((QualifiedOrgElement) input).getQualifierAttribute();
            hasQualifierBtn.setSelection(attribute != null);
            showQualifierTabLink.setVisible(attribute != null);
        }
    }

}
