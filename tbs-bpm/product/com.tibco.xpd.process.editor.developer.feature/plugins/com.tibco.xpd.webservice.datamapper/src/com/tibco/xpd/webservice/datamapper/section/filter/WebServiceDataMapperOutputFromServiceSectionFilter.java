/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ServiceTaskMappingFromServiceFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * 
 * 
 * @author ssirsika
 * @since 22-Jan-2016
 */
public class WebServiceDataMapperOutputFromServiceSectionFilter extends
        AbstractWebServiceDataMapperFilter {


    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "developer.activityDataMapperOutputFromService";
    }

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#createFilter()
     * 
     * @return
     */
    @Override
    protected IFilter createFilter() {
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;

        ServiceTaskMappingFromServiceFilter serviceTaskMappingFromServiceFilterForDataMapper =
                new ServiceTaskMappingFromServiceFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.SendTaskMappingOneWayFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter)
                     * 
                     * @param taskFilterBuilder
                     * @return
                     */
                    @Override
                    protected IFilter createGrammarFilter(
                            TaskViewerFilter taskFilterBuilder) {
                        return taskFilterBuilder
                                .hasScriptGrammar(DirectionType.OUT_LITERAL,
                                        ScriptGrammarFactory.DATAMAPPER);
                    }
                };

        return viewerFilterBuilder
                .getComposite(serviceTaskMappingFromServiceFilterForDataMapper);
    }

}
