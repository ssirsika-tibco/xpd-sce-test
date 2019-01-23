/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import com.tibco.xpd.processeditor.xpdl2.wizards.TreeWithImagePreviewComposite.ICheckTreeWithDescriptionInputProvider;
import com.tibco.xpd.xpdl2.Package;

/**
 * Abstract Template(Content) provider This provides the template items.
 * 
 * @author mtorres
 * 
 */
public abstract class AbstractProcessTemplateProvider implements
        ICheckTreeWithDescriptionInputProvider {

    public abstract Object getInitialSelection();
    
    public abstract void setXpdl2Package(Package xpdl2Package);
    
    public abstract Package getXpdl2Package();    
}
