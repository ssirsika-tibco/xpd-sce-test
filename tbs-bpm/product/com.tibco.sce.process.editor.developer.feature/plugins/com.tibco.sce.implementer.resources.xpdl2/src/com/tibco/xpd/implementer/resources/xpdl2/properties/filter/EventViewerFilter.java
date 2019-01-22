package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.TriggerType;

public interface EventViewerFilter {

    IFilter isImplementedBy(ImplementationType i);

    IFilter hasType(EventType... taskType);

    IFilter hasTriggerType(TriggerType... triggerType);

    IFilter hasTriggerResultMessage();

    /**
     * Return a {@link IFilter} which will return true if any of the passed
     * grammarType is set in the {@link Activity}
     * 
     * @param grammarType
     *            {@link String} grammarType
     * @param direction
     *            {@link DirectionType}
     * @return {@link IFilter}
     */
    IFilter hasScriptGrammar(DirectionType direction, String... grammarType);
}
