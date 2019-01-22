/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Label provider used by picker controls to display the fully qualified name of
 * {@link NamedElement} objects.
 * 
 * @author njpatel
 * 
 */
/* public */class PickerControlLabelProvider extends LabelProvider {
    public TransactionalAdapterFactoryLabelProvider afLabelProvider;

    /**
     * Label provider for the picker controls.
     */
    public PickerControlLabelProvider() {
        afLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());
    }

    @Override
    public void dispose() {
        afLabelProvider.dispose();
        super.dispose();
    }

    @Override
    public String getText(Object element) {
        if (element instanceof NamedElement) {
            String label =
                    UML2ModelUtil.getQualifiedLabel((NamedElement) element);
            if (label != null) {
                return label;
            }
        }
        return afLabelProvider.getText(element);
    }

    @Override
    public Image getImage(Object element) {
        return afLabelProvider.getImage(element);
    }
}