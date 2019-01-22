/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (16 Jun 2010)
 */
public class JavaScriptMapperTreeItem extends MapperTreeItem {

    private final Object contextObject;

    private ConceptPath conceptPath;

    /**
     * @param contextObject
     * @param mappingObject
     */
    public JavaScriptMapperTreeItem(Object contextObject,
            ConceptPath conceptPath) {
        super(conceptPath);
        this.contextObject = contextObject;
        this.conceptPath = conceptPath;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getParent()
     * 
     * @return
     */
    @Override
    public MapperTreeItem getParent() {
        if (conceptPath.getParent() != null) {
            return new JavaScriptMapperTreeItem(contextObject, conceptPath
                    .getParent());
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getNormalizedLabel()
     * 
     * @return
     */
    @Override
    protected String getNormalizedLabel() {
        return conceptPath.getBaseName();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem#getType()
     * 
     * @return
     */
    @Override
    public Object getType() {
        return conceptPath.getType();
    }
}
