/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.wst.wsdl.Part;

/**
 * @author rsomayaj
 * 
 */
public class WsdlPartConceptPath extends ConceptPath {

    private final Part wsdlPart;

    private final PartMode partMode;

    public enum PartMode {
        INPUT, OUTPUT;
    }

    /**
     * @param item
     * @param type
     */
    public WsdlPartConceptPath(Part wsdlPart, PartMode partMode, Object item,
            Classifier type) {
        super(item, type);
        this.wsdlPart = wsdlPart;
        this.partMode = partMode;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#getChildren()
     * 
     * @return
     */
    @Override
    public List<ConceptPath> getChildren() {
        return super.getChildren();
    }

    /**
     * @return
     */
    public Part getPart() {
        return wsdlPart;
    }
}
