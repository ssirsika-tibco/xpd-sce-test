/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.implementer.resources.xpdl2.properties.IntermediateCatchMappingFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MappingReceiveServiceFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.StartEventMappingFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Filter to let Receive tasks with Data Mapper configured go through.
 * 
 * @author sajain
 * @since Jan 22, 2016
 */
public class WebServiceDataMapperInputToProcessSectionFilter extends
        AbstractWebServiceDataMapperFilter {

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.WebServiceDataMapperOutputFromServiceSectionFilter#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "developer.activityDataMapperForInputToProcess";
    }

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#createFilter()
     * 
     * @return
     */
    @Override
    protected IFilter createFilter() {

        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;

        MappingReceiveServiceFilter mappingReceiveServiceFilterForDataMapper =
                new MappingReceiveServiceFilter() {
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

        StartEventMappingFilter startEventMappingFilterForDataMapper =
                new StartEventMappingFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.SendTaskMappingOneWayFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter)
                     * 
                     * @param eventFilterBuilder
                     * @return
                     */
                    @Override
                    protected IFilter createGrammarFilter(
                            EventViewerFilter eventFilterBuilder) {
                        return eventFilterBuilder
                                .hasScriptGrammar(DirectionType.OUT_LITERAL,
                                        ScriptGrammarFactory.DATAMAPPER);
                    }
                };

        IntermediateCatchMappingFilter intermediateCatchMappingFilterForDataMapper =
                new IntermediateCatchMappingFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.IntermediateCatchMappingFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter)
                     * 
                     * @param eventFilterBuilder
                     * @return
                     */
                    @Override
                    protected IFilter createGrammarFilter(
                            EventViewerFilter eventFilterBuilder) {
                        return eventFilterBuilder
                                .hasScriptGrammar(DirectionType.OUT_LITERAL,
                                        ScriptGrammarFactory.DATAMAPPER);
                    }
                };

        return viewerFilterBuilder.getComposite(ViewerFilterBuilder.OR,
                mappingReceiveServiceFilterForDataMapper,
                startEventMappingFilterForDataMapper,
                intermediateCatchMappingFilterForDataMapper);
    }
}
