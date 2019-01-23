/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.wsdltobom.indexer;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexResourceMatcher;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;

/**
 * 
 * @author rsomayaj
 * 
 */
public class Wsdl2BomIndexResourceMatcher implements IndexResourceMatcher {

    public Wsdl2BomIndexResourceMatcher() {
    }

    public boolean accept(WorkingCopy wc) {
        if (wc != null && !wc.isInvalidFile()) {
            if (wc instanceof WsdlWorkingCopy) {
                return true;
            } else if (wc instanceof BOMWorkingCopy) {
                /*
                 * If this BOM is not in the generated BOM folder then ignore it
                 */
                List<IResource> resources = wc.getEclipseResources();

                if (resources != null && !resources.isEmpty()) {
                    IResource resource = resources.get(0);

                    SpecialFolder sf =
                            SpecialFolderUtil.getRootSpecialFolder(resource);

                    if (sf != null && sf.getFolder() != null) {
                        IFolder genFolder =
                                WsdlToBomBuilder.getGeneratedBOMFolder(sf
                                        .getFolder().getProject(), false);

                        return sf.getFolder().equals(genFolder);

                    }
                }
            }
        }
        return false;
    }

}