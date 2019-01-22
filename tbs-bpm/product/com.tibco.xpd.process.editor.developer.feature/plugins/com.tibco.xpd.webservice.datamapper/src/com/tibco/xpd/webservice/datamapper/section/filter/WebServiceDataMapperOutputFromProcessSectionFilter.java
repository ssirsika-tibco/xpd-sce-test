/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.implementer.resources.xpdl2.properties.IntermediateThrowMappingFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MappingEndMessageFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.SendTaskMappingFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * 
 * 
 * @author ssirsika
 * @since 27-Jan-2016
 */
public class WebServiceDataMapperOutputFromProcessSectionFilter extends
        AbstractWebServiceDataMapperFilter {

    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */

    @Override
    public String getLocalId() {
        return "developer.activityDataMapperOutputFromProcess";
    }

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#createFilter()
     * 
     * @return
     */
    @Override
    protected IFilter createFilter() {
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;
        MappingEndMessageFilter mappingEndMessageFilterForDataMapper =
                new MappingEndMessageFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.MappingEndMessageFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter)
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

        IntermediateThrowMappingFilter intermediateThrowMappingFilterForDataMapper =
                new IntermediateThrowMappingFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.MappingEndMessageFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter)
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

        SendTaskMappingFilter sendTaskMappingFilterForDataMapper =
                new SendTaskMappingFilter() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.MappingEndMessageFilter#createGrammarFilter(com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter)
                     * 
                     * @param eventViewerFilter
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

        return viewerFilterBuilder.getComposite(ViewerFilterBuilder.OR,
                mappingEndMessageFilterForDataMapper,
                intermediateThrowMappingFilterForDataMapper,
                sendTaskMappingFilterForDataMapper);
    }
}
