/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventType;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ImplementationType;

/**
 * @author rsomayaj
 * 
 */
public class StartEventMappingFilter extends MappingReceiveServiceFilter {

    /**
     * 
     */
    public StartEventMappingFilter() {
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;
        EventViewerFilter eventViewerFilter =
                viewerFilterBuilder.getEventFilter();
        IFilter wsEventFilter =
                eventViewerFilter
                        .isImplementedBy(ImplementationType.WEB_SERVICE_LITERAL);
        IFilter eventTypeFilter = eventViewerFilter.hasType(EventType.START);
        IFilter triggerResultMessageFilter =
                eventViewerFilter.hasTriggerResultMessage();
        IFilter grammerCheckFilter = createGrammarFilter(eventViewerFilter);
        IFilter notImplemented = new IFilter() {

            @Override
            public boolean select(Object toTest) {
                boolean result = false;
                EObject eo = null;
                if (toTest instanceof EObject) {
                    eo = (EObject) toTest;
                } else if (toTest instanceof IAdaptable) {
                    eo =
                            (EObject) ((IAdaptable) toTest)
                                    .getAdapter(EObject.class);
                }

                if (eo instanceof Activity) {

                    Activity act = (Activity) eo;

                    if (!ProcessInterfaceUtil.isEventImplemented(act)) {
                        result = true;
                    }
                }
                return result;
            }

        };
        filter =
                viewerFilterBuilder.getComposite(wsEventFilter,
                        eventTypeFilter,
                        triggerResultMessageFilter,
                        grammerCheckFilter,
                        notImplemented);
    }

    /**
     * Return {@link IFilter} to check applicable script grammar types.
     * 
     * @param taskFilter
     * @return {@link IFilter}
     */
    protected IFilter createGrammarFilter(EventViewerFilter eventFilterBuilder) {
        IFilter grammerCheckFilter =
                eventFilterBuilder.hasScriptGrammar(DirectionType.OUT_LITERAL,
                        ScriptGrammarFactory.JAVASCRIPT,
                        ScriptGrammarFactory.XPATH);
        return grammerCheckFilter;
    }

}
