/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.Server;

/**
 * Wizard node for specific server module contributed wizard.
 * <p>
 * <i>Created: 30 Aug 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ModuleDeploymentWizardNode implements IWizardNode {

    /**
     * 
     */
    private static final Point DEFAULT_EXTENT = new Point(-1, -1);

    private final ISimpleDeployWizard wizard;

    private final String name;

    private final ImageDescriptor wizardImageDescriptor;

    private Image wizardImage;

    private final int priority;

    /**
     * @param wizardImage
     * 
     */
    public ModuleDeploymentWizardNode(ISimpleDeployWizard wizard, String name,
            ImageDescriptor wizardImage) {
        this(wizard, name, wizardImage, 0);
    }

    /**
     * @param wizardImage
     * 
     */
    public ModuleDeploymentWizardNode(ISimpleDeployWizard wizard, String name,
            ImageDescriptor wizardImage, int priority) {
        this.wizard = wizard;
        this.name = name;
        this.wizardImageDescriptor = wizardImage;
        this.priority = priority;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#dispose()
     */
    public void dispose() {
        if (wizardImage != null) {
            wizardImage.dispose();
        }
        wizard.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
     */
    public Point getExtent() {
        return DEFAULT_EXTENT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
     */
    public IWizard getWizard() {
        return wizard;
    }

    public ISimpleDeployWizard getDeployWizard() {
        return wizard;
    }

    public Server getServer() {
        return wizard.getServer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
     */
    public boolean isContentCreated() {
        return (wizard.getPageCount() > 0);
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        if (wizardImageDescriptor == null) {
            return PlatformUI.getWorkbench().getSharedImages()
                    .getImage(ISharedImages.IMG_OBJ_ELEMENT);
        } else {
            if (wizardImage == null) {
                wizardImage = wizardImageDescriptor.createImage();
            }
            return wizardImage;
        }
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result + ((wizard == null) ? 0 : wizard.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ModuleDeploymentWizardNode other =
                (ModuleDeploymentWizardNode) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (wizard == null) {
            if (other.wizard != null)
                return false;
        } else if (!wizard.equals(other.wizard))
            return false;
        return true;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

}
