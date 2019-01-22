/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import java.util.ArrayList;
import java.util.List;

public class DestinationSetting {
    private String name;

    private String globalDestinationId;

    private ArrayList<DestinationComponentSetting> components;

    public DestinationSetting(String name) {
        this.name = name;
        components = new ArrayList<DestinationComponentSetting>();
    }

    public String getName() {
        return name;
    }

    /**
     * Setting the name should only be done by DestinationPreferences so
     * that it can be checked for uniquness first.
     * @param name The new name.
     */
    protected void setName(String name) {
        this.name = name;
    }

    public void setGlobalDestinationId(String globalDestinationId) {
        this.globalDestinationId = globalDestinationId;
    }

    public String getGlobalDestinationId() {
        return globalDestinationId;
    }

    public List<DestinationComponentSetting> getComponents() {
        return components;
    }

    public void addComponent(DestinationComponentSetting component) {
        components.add(component);
    }

    public DestinationComponentSetting getComponent(String componentId) {
        DestinationComponentSetting component = null;
        for (DestinationComponentSetting current : components) {
            if (componentId.equals(current.getComponentId())) {
                component = current;
                break;
            }
        }
        return component;
    }
}