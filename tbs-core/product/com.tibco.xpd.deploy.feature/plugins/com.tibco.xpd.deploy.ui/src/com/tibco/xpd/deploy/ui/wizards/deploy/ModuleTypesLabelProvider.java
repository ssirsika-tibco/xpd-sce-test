/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * LabelProvider for module types and associated deploy wizards.
 * <p>
 * <i>Created: 30 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ModuleTypesLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    @Override
    public Image getImage(Object element) {
        if (element instanceof ModuleDeploymentWizardNode) {
            ModuleDeploymentWizardNode wizardNode = (ModuleDeploymentWizardNode) element;
            return wizardNode.getImage();
        }
        return super.getImage(element);
    }

    @Override
    public String getText(Object element) {
        if (element instanceof ModuleDeploymentWizardNode) {
            ModuleDeploymentWizardNode wizardNode = (ModuleDeploymentWizardNode) element;
            return wizardNode.getName();
        }
        return super.getText(element);
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
     *      int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return getImage(element);
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
     *      int)
     */
    public String getColumnText(Object element, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return getText(element);
        }
        return ""; //$NON-NLS-1$
    }

}
