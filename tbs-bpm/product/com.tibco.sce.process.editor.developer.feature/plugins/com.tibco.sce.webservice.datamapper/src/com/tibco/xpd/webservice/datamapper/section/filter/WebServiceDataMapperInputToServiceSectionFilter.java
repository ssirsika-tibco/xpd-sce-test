/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.implementer.resources.xpdl2.properties.IntermediateThrowMappingOneWayFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MappingEndMessageOneWayFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.SendTaskMappingOneWayFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ServiceTaskMappingToServiceFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
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
public class WebServiceDataMapperInputToServiceSectionFilter extends
        AbstractWebServiceDataMapperFilter {

    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */

    @Override
    public String getLocalId() {
        return "developer.activityDataMapperMapperInputToService";
    }

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#createFilter()
     * 
     * @return
     */
    @Override
    protected IFilter createFilter() {
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;

        ServiceTaskMappingToServiceFilter serviceTaskMappingToServiceFilterForDataMapper =
                new ServiceTaskMappingToServiceFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.ServiceTaskMappingToServiceFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter)
                     * 
                     * @param taskFilter
                     * @return
                     */
                    @Override
                    protected IFilter createGrammarFilter(
                            TaskViewerFilter taskFilter) {
                        return taskFilter
                                .hasScriptGrammar(DirectionType.IN_LITERAL,
                                        ScriptGrammarFactory.DATAMAPPER);
                    }
                };

        SendTaskMappingOneWayFilter sendTaskMappingOneWayFilterForDataMapper =
                new SendTaskMappingOneWayFilter() {
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
                                .hasScriptGrammar(DirectionType.IN_LITERAL,
                                        ScriptGrammarFactory.DATAMAPPER);
                    }
                };

        MappingEndMessageOneWayFilter mappingEndMessageOneWayFilterForDataMapper =
                new MappingEndMessageOneWayFilter() {
                    @Override
                    protected IFilter createGrammarFilter(
                            EventViewerFilter eventFilterBuilder) {
                        return eventFilterBuilder
                                .hasScriptGrammar(DirectionType.IN_LITERAL,
                                        ScriptGrammarFactory.DATAMAPPER);
                    };
                };

        IntermediateThrowMappingOneWayFilter intermediateThrowMappingOneWayFilterForDataMapper =
                new IntermediateThrowMappingOneWayFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IntermediateThrowMappingOneWayFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter)
                     * 
                     * @param eventViewerFilter
                     * @return
                     */
                    @Override
                    protected IFilter createGrammarFilter(
                            EventViewerFilter eventFilterBuilder) {
                        return eventFilterBuilder
                                .hasScriptGrammar(DirectionType.IN_LITERAL,
                                        ScriptGrammarFactory.DATAMAPPER);
                    }
                };
        return viewerFilterBuilder.getComposite(ViewerFilterBuilder.OR,
                serviceTaskMappingToServiceFilterForDataMapper,
                sendTaskMappingOneWayFilterForDataMapper,
                mappingEndMessageOneWayFilterForDataMapper,
                intermediateThrowMappingOneWayFilterForDataMapper);
    }
   
}
