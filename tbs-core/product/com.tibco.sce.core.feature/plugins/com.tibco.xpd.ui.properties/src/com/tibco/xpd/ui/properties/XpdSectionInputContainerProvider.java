/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.ecore.EObject;

/**
 * <p>
 * <i>This is for wizards that will contain property sections.</i>
 * </p>
 * <p>
 * Wizards that intend to provide an input container for the property sections
 * it will contain should implement this interface.
 * </p>
 * 
 * @author njpatel
 * 
 */
public interface XpdSectionInputContainerProvider {
    /**
     * Get the input container (Package or Process)
     * 
     * @return EObject
     */
    public EObject getInputContainer();
    
    /**
     * This method allows AbstractXPDSections to set the can finish status of creation wizard.
     * 
     * @param canFinish
     */
    public void setCanFinish(boolean canFinish, String cantFinishMessage);
}
