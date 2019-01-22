/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.uml2.uml.Classifier;

/**
 * Root of the rest payload concept path. It uses a specific prefix as a start
 * of the path (Usually:
 * com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix .PAYLOAD
 * literal).
 * 
 * @author jarciuch
 * @since 14 Apr 2015
 */
public class RestConceptPath extends ConceptPath {

    private final String pathPrefix;

    private boolean isArray;

    /**
     * @param pathPrefix
     * @param item
     * @param type
     */
    public RestConceptPath(String pathPrefix, Object item, Classifier type,
            boolean isArray) {
        super(item, type);
        this.pathPrefix = pathPrefix;
        this.isArray = isArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPath() {
        return pathPrefix;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        String prefix = "Payload."; //$NON-NLS-1$
        if (getType() != null) {
            return prefix + getType().getName();
        }
        return prefix;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#isArray()
     * 
     * @return
     */
    @Override
    public boolean isArray() {
        return isArray;
    }
}
