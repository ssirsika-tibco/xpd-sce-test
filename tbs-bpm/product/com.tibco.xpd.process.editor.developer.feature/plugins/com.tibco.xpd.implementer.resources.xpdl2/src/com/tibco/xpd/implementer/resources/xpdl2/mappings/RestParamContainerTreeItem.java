/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Container tree item used for grouping REST tree items.
 *
 * @author jarciuch
 * @since 26 Mar 2015
 */
public abstract class RestParamContainerTreeItem extends RestMapperTreeItem {

    private final Activity activity;

    private final String label;

    private final ParameterStyle paramStyle;

    private final MappingDirection direction;

    /**
     * 
     */
    public RestParamContainerTreeItem(Activity activity, String label,
            ParameterStyle paramStyle, MappingDirection direction) {
        this.activity = activity;
        this.label = label;
        this.paramStyle = paramStyle;
        this.direction = direction;
    }

    protected abstract Collection<Parameter> getParams(Activity activity);

    /**
     * {@inheritDoc}
     */
    @Override
    public RestMapperTreeItem getParent() {
        return null; // OR Activity
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RestMapperTreeItem> getChildren() {
        if (activity != null) {
            List<RestMapperTreeItem> children =
                    new ArrayList<RestMapperTreeItem>();
            for (Parameter param : getParams(activity)) {
                if (paramStyle == param.getStyle()) {
                    children.add(new RestParamTreeItem(this, param, activity,
                            direction));
                }
            }
            return children;
        }
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasChildren() {
        if (activity != null) {
            for (Parameter param : getParams(activity)) {
                if (paramStyle == param.getStyle()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
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
                prime * result + ((activity == null) ? 0 : activity.hashCode());
        result =
                prime * result
                        + ((direction == null) ? 0 : direction.hashCode());
        result =
                prime * result
                        + ((paramStyle == null) ? 0 : paramStyle.hashCode());
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
        RestParamContainerTreeItem other = (RestParamContainerTreeItem) obj;
        if (activity == null) {
            if (other.activity != null)
                return false;
        } else if (!activity.equals(other.activity))
            return false;
        if (direction != other.direction)
            return false;
        if (paramStyle != other.paramStyle)
            return false;
        return true;
    }

}
