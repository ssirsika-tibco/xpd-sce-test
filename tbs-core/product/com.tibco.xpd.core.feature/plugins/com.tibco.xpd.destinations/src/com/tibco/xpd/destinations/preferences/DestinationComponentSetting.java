/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

public class DestinationComponentSetting {

    private String componentId;

    private String validationDestinationId;

    public DestinationComponentSetting(String componentId) {
        this.componentId = componentId;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setValidationDestinationId(String validationDestinationId) {
        this.validationDestinationId = validationDestinationId;
    }

    public String getValidationDestinationId() {
        return validationDestinationId;
    }
}