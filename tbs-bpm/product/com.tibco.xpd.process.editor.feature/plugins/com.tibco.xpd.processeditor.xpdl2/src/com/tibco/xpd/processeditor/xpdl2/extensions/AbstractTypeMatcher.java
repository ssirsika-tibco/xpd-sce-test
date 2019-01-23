/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.extensions;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem;

/**
 *Abstract class for matching name and types for source and target on the
 * mapper.
 * 
 * This type check is also used validating source and target types in the
 * mapping section
 * 
 * @author rsomayaj
 * @since 3.3 (10 Jun 2010)
 */
public abstract class AbstractTypeMatcher {

    /**
     * Each time a contribution is asked to match the types, it is provided with
     * the source and target objects of the mapping tree.
     * 
     * Since the TypeMatcher is aware of the types on either sides(as the
     * section it refers to provides the content to the tree), it would
     * understand both sides and can thus resolve the objects into comparable
     * types.
     * 
     * This matching could be used by both the types matching validator and the
     * auto-map functionality provided on the mappings section.
     * 
     * @param sourceObject
     * @param targetObject
     * @return
     */
    public abstract boolean typesMatch(Object sourceObject, Object targetObject);

    /**
     * Each contribution is asked whether it is applicable given the context
     * object and the mapping direction. Some type matchers work for the
     * "Input to Service" sections and some of them do for the
     * "Input to Process".
     * 
     * @param contextObject
     * @return
     */
    public abstract boolean isApplicable(Object contextObject,
            MappingDirection mappingDirection);

    /**
     * @param contextObject
     *            object reference to the process. Generally expected to be
     *            <code>Activity</code>
     * @param mappingObject
     *            the object which needs to be broken down into the mapper tree
     *            item.
     * 
     * @return a MapperTreeItem object which has a data structure broken down
     *         into its hierarchical structure.
     */
    public abstract MapperTreeItem getNormalizedPath(Object contextObject,
            Object mappingObject);
}
