package com.tibco.xpd.processwidget.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

public interface GatewayAdapter extends FlowNodeAdapter {
    /**
     * Type of the gateway type (@see GatewayType)
     * 
     * @return the type of the event
     */
    GatewayType getGatewayType();

    /**
     * Create command to set event type
     * 
     * @param eventType
     * @return
     */
    Command getSetGatewayTypeCommand(EditingDomain editingDomain, GatewayType gatewayType);

    
    /**
     * 
     * @return Whether 'X' Marker should be visible
     */
    boolean isXORDataMarkerVisible();
}
