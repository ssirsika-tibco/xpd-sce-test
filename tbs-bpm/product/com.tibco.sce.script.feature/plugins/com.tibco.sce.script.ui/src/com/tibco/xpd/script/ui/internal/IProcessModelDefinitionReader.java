/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;

/**
 * @author mtorres
 */
public interface IProcessModelDefinitionReader extends IProcessJsDefinitionReader {
    
    /**
     * Sets the model url
     * 
     */
    void setUri(URI uri);
    
    /**
     * Sets the image
     * 
     */
    void setImage(Image image);
    
}
