/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.ThrowWsdlFaultMessageErrorEventFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * 
 * 
 * @author ssirsika
 * @since 03-Feb-2016
 */
public class WebServiceDataMapperOutputFaultFromProcessSectionFilter extends
        AbstractWebServiceDataMapperFilter {

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#createFilter()
     * 
     * @return
     */
    @Override
    protected IFilter createFilter() {
        ThrowWsdlFaultMessageErrorEventFilter throwWsdlFaultMessageErrorEventFilterForDataMapper =
                new ThrowWsdlFaultMessageErrorEventFilter() {
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
        return throwWsdlFaultMessageErrorEventFilterForDataMapper;
    }

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "developer.activityDataMapperOutputFaultFromProcess"; //$NON-NLS-1$
    }

}
