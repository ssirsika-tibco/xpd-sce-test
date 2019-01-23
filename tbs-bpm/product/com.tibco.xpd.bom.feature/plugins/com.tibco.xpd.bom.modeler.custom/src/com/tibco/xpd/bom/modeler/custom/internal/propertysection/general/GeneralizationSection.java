/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM properties section for the Generalization.
 * 
 * @author njpatel
 * 
 */
public class GeneralizationSection extends AbstractGeneralSection {

    private CLabel specificNameLabel;

    private CLabel generalNameLabel;

    private final TransactionalAdapterFactoryLabelProvider lp;

    public GeneralizationSection() {
        lp =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());
    }

    @Override
    protected boolean shouldDisplay(EObject eo) {
        return eo instanceof Generalization;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout(2, false);
        layout.verticalSpacing = 15;
        layout.marginTop = 5;
        root.setLayout(layout);

        createLabel(root,
                toolkit,
                Messages.GeneralizationSection_specific_label);

        specificNameLabel = toolkit.createCLabel(root, "", SWT.WRAP); //$NON-NLS-1$
        specificNameLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
                true, false));

        createLabel(root, toolkit, Messages.GeneralizationSection_general_label);

        generalNameLabel = toolkit.createCLabel(root, "", SWT.WRAP); //$NON-NLS-1$
        generalNameLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
                true, false));

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // This is a read-only property sheet
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Generalization) {
            Generalization gen = (Generalization) input;
            // Set the General
            Classifier cl = gen.getGeneral();
            if (cl != null) {
                Image img = lp.getImage(cl);
                if (img != null) {
                    generalNameLabel.setImage(img);
                }
                generalNameLabel.setText(cl.getQualifiedName());
            }
            // Set the Specific
            cl = gen.getSpecific();
            if (cl != null) {
                Image img = lp.getImage(cl);
                if (img != null) {
                    specificNameLabel.setImage(img);
                }
                specificNameLabel.setText(cl.getQualifiedName());
            }
        }
    }

    @Override
    public void dispose() {
        lp.dispose();
        super.dispose();
    }
}
