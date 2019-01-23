/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapper tree item representing parameter used in REST service call.
 *
 * @author jarciuch
 * @since 26 Mar 2015
 */
public class RestParamTreeItem extends RestMapperTreeItem {

    private final RestMapperTreeItem parent;

    private final Parameter param;

    private final Activity activity;

    private final String path;

    private final MappingDirection direction;

    /**
     * Creates mapper tree item for a REST service parameter.
     * 
     * See also:
     * {@link RestMapperTreeItemFactory#resolveParameter(Activity, MappingDirection, String)}
     * 
     * @param parent
     *            parent tree item.
     * @param param
     *            the RSD parameter.
     * @param activity
     *            context activity.
     * @param direction
     *            mapping direction.
     */
    public RestParamTreeItem(RestMapperTreeItem parent, Parameter param,
            Activity activity, MappingDirection direction) {
        assert param != null;
        assert activity != null;
        this.parent = parent;
        this.param = param;
        this.activity = activity;
        this.path = getParamPath();
        this.direction = direction;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestMapperTreeItem getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RestMapperTreeItem> getChildren() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return param.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasChildren() {
        return false;
    }

    /**
     * Returns the wrapped parameter.
     * 
     * @return parameter reference.
     */
    public Parameter getParam() {
        return param;
    }

    public String getPath() {
        return path;
    }

    /**
     * @return the context activity.
     */
    @Override
    public Activity getActivity() {
        return activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result
                        + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RestParamTreeItem other = (RestParamTreeItem) obj;
        if (direction != other.direction)
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }

    private String getParamPath() {
        switch (param.getStyle()) {
        case PATH:
            return RestMappingPrefix.PATH_PARAM.addPrefix(param.getName());
        case QUERY:
            return RestMappingPrefix.QUERY_PARAM.addPrefix(param.getName());
        case HEADER:
            return RestMappingPrefix.HEADER_PARAM.addPrefix(param.getName());
        default:
            return param.getName();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return path;
    }
}
