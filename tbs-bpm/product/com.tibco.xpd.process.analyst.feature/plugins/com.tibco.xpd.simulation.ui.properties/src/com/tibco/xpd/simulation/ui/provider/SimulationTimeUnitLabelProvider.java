/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.ui.properties.ActivitySimulationExistSection;
import com.tibco.xpd.simulation.ui.properties.PropertiesMessage;
import com.tibco.xpd.simulation.ui.properties.StartEventSimulationExistSection;

/**
 * Simulation Time Unit data label provider.
 * Used for globalisation - we need to keep model enum values the same but return different (locale dependant) strings
 * @see StartEventSimulationExistSection
 * @see ActivitySimulationExistSection
 * @author glewis
 *
 */
public class SimulationTimeUnitLabelProvider extends LabelProvider{

    @Override
    public String getText(Object element) {        
        TimeDisplayUnitType timeDisplayUnitType = (TimeDisplayUnitType)element;
        switch (timeDisplayUnitType.getValue()) {
            case TimeDisplayUnitType.YEAR:
                return PropertiesMessage.getString("Year"); //$NON-NLS-1$
            case TimeDisplayUnitType.MONTH:
                return PropertiesMessage.getString("Month"); //$NON-NLS-1$
            case TimeDisplayUnitType.DAY:
                return PropertiesMessage.getString("Day"); //$NON-NLS-1$
            case TimeDisplayUnitType.HOUR:
                return PropertiesMessage.getString("Hour"); //$NON-NLS-1$
            case TimeDisplayUnitType.MINUTE:
                return PropertiesMessage.getString("Minute"); //$NON-NLS-1$
            case TimeDisplayUnitType.SECOND:
                return PropertiesMessage.getString("Second"); //$NON-NLS-1$            
            default:
                return timeDisplayUnitType.getName();
        }
    }

    @Override
    public Image getImage(Object element) {       
        return super.getImage(element);
    }
}
