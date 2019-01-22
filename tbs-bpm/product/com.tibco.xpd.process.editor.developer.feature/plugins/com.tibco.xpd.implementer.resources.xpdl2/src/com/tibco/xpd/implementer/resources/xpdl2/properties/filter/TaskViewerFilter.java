package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ImplementationType;

public interface TaskViewerFilter {

    /**
     * Return a {@link IFilter} which will return true {@link Activity}
     * implements passed {@link ImplementationType}
     * 
     * @param i
     *            {@link ImplementationType}
     * @return
     */
    IFilter isImplementedBy(ImplementationType i);

    /**
     * Return a {@link IFilter} which will return true if any of the passed type
     * is set in the {@link Activity}
     * 
     * @param taskType
     * @return
     */
    IFilter hasType(TaskType... taskType);

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
