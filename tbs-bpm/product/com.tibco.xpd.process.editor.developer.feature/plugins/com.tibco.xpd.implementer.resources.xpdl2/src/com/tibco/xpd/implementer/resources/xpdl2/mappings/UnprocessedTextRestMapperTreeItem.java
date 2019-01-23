/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Data Mapper tree item to represent the special "Unprocessed Text" payload.
 * This is available as a single payload item if the payload type in the RSD has
 * been set to "Unprocessed Text".
 * 
 * @author nwilson
 * @since 20 Jul 2015
 */
public class UnprocessedTextRestMapperTreeItem extends RestMapperTreeItem {

    /**
     * Name of tree item used in path. This is the value stored in the data
     * mapping, not a display value.
     */
    public static String UNPROCESSED_TEXT_NAME = "UnprocessedText"; //$NON-NLS-1$

    /**
     * The full path of the JSON String tree item.
     */
    public static String JSON_PATH = RestMappingPrefix.PAYLOAD.getPrefix()
            + "." + UNPROCESSED_TEXT_NAME; //$NON-NLS-1$

    /**
     * The parent PAYLOAD_CONTAINER item.
     */
    private RestMapperTreeItem parent;

    /**
     * The activity.
     */
    private Activity activity;

    private PrimitiveType type;

    /**
     * Creates a new JSON String tree item.
     */
    public UnprocessedTextRestMapperTreeItem(RestMapperTreeItem parent,
            Activity activity) {
        this.parent = parent;
        this.activity = activity;
        ResourceSet rs = activity.eResource().getResourceSet();
        type =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getParent()
     * 
     * @return The parent PAYLOAD_CONTAINER item.
     */
    @Override
    public RestMapperTreeItem getParent() {
        return parent;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getChildren()
     * 
     * @return an empty list.
     */
    @Override
    public List<?> getChildren() {
        return Collections.emptyList();
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#hasChildren()
     * 
     * @return false.
     */
    @Override
    public boolean hasChildren() {
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getText()
     * 
     * @return The JSON String display name.
     */
    @Override
    public String getText() {
        return UNPROCESSED_TEXT_NAME;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getActivity()
     * 
     * @return The activity.
     */
    @Override
    public Activity getActivity() {
        return activity;
    }

    /**
     * @return The full path of the JSON String tree item.
     */
    public String getPath() {
        return JSON_PATH;
    }

    public PrimitiveType getType() {
        return type;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return the path hashcode.
     */
    @Override
    public int hashCode() {
        return getPath().hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     *            The object to test for equality.
     * @return true if it is a JsonStringRestMapperTreeItem.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnprocessedTextRestMapperTreeItem;
    }

}
