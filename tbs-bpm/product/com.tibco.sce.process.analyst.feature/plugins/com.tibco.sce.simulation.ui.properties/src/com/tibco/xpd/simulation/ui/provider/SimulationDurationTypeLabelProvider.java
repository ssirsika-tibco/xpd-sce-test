/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.simulation.RealDistributionCategory;
import com.tibco.xpd.simulation.ui.properties.ActivitySimulationExistSection;
import com.tibco.xpd.simulation.ui.properties.PropertiesMessage;
import com.tibco.xpd.simulation.ui.properties.StartEventSimulationExistSection;

/**
 * Simulation Duration Type data label provider.
 * Used for globalisation - we need to keep model enum values the same but return different (locale dependant) strings
 * @see StartEventSimulationExistSection
 * @see ActivitySimulationExistSection
 * @author glewis
 *
 */
public class SimulationDurationTypeLabelProvider extends LabelProvider{

    @Override
    public String getText(Object element) {        
        RealDistributionCategory distCategory = (RealDistributionCategory)element;
        switch (distCategory.getValue()) {
            case RealDistributionCategory.CONSTANT:
                return PropertiesMessage.getString("ActivitySimulationExistSection.Constant_value"); //$NON-NLS-1$
            case RealDistributionCategory.UNIFORM:
                return PropertiesMessage.getString("ActivitySimulationExistSection.Uniform_value"); //$NON-NLS-1$
            case RealDistributionCategory.NORMAL:
                return PropertiesMessage.getString("ActivitySimulationExistSection.Normal_value"); //$NON-NLS-1$
            case RealDistributionCategory.EXPONENTIAL:
                return PropertiesMessage.getString("ActivitySimulationExistSection.Exponential_value"); //$NON-NLS-1$
            case RealDistributionCategory.EMPIRICAL:
                return PropertiesMessage.getString("ActivitySimulationExistSection.Empirical_value"); //$NON-NLS-1$
            case RealDistributionCategory.PARAMETER_BASED:
                return PropertiesMessage.getString("ActivitySimulationExistSection.ParameterBased_value"); //$NON-NLS-1$
            default:
                return distCategory.getName();
        }
    }

    @Override
    public Image getImage(Object element) {       
        return super.getImage(element);
    }
}
