/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.firstclassprofiles;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Profile;

/**
 * This represents a first-class profile extension.
 * 
 * @author njpatel
 * 
 */
public interface IFirstClassProfileExtension {

    /**
     * Get id of this extension.
     * 
     * @return
     */
    String getId();

    /**
     * Get the human-readable label of this extension.
     * 
     * @return
     */
    String getLabel();

    /**
     * Get the specified image descriptor of the icon (if any).
     * 
     * @return image descriptor if specified, <code>null</code> otherwise.
     */
    ImageDescriptor getIcon();

    /**
     * Get the uri of the profile location.
     * 
     * @see #getProfile()
     * 
     * @return
     */
    URI getProfileUri();

    /**
     * Get the first-class {@link Profile} of this extension.
     * 
     * @see #getProfileUri()
     * 
     * @return {@link Profile} if found, <code>null</code> otherwise
     */
    Profile getProfile();

    /**
     * Get the specified image descriptor of the diagram image (if any). This
     * image is intended to be used in the diagram badge.
     * 
     * @return image descriptor if specified, <code>null</code> otherwise.
     */
    ImageDescriptor getDiagramImage();

    /**
     * Check if the {@link Operation}s container and functionality should be
     * supported in this first-class profile extension model.
     * 
     * @return <code>true</code> to show the <code>Operation</code>s,
     *         <code>false</code> otherwise.
     */
    boolean showOperations();

    /**
     * Check if the regular BOM elements should be displayed in the palette
     * along with elements defined in this extension.
     * 
     * @return <code>true</code> if BOM elements should be shown in the palette
     *         and <code>false</code> to hide the elements.
     */
    boolean showBomPaletteElements();
}
