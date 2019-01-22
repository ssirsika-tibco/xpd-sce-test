/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section.filter;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ImplementationType;

/**
 * Filter for 'Map From Error' section which handles service task.
 * 
 * @author ssirsika
 * @since 08-Mar-2016
 */
public class WebServiceDataMapperMapFromErrorSectionFilter extends
        AbstractWebServiceDataMapperFilter {

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#createFilter()
     * 
     * @return
     */
    @Override
    protected IFilter createFilter() {
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;
        TaskViewerFilter taskFilter = viewerFilterBuilder.getTaskFilter();
        final IFilter implFilter =
                taskFilter
                        .isImplementedBy(ImplementationType.WEB_SERVICE_LITERAL);
        // use implFilter for checking implementation type.
        return new IFilter() {

            @Override
            public boolean select(Object toTest) {
                EObject eo = null;
                if (toTest instanceof EObject) {
                    eo = (EObject) toTest;
                } else if (toTest instanceof IAdaptable) {
                    eo =
                            (EObject) ((IAdaptable) toTest)
                                    .getAdapter(EObject.class);
                }

                if (eo instanceof Activity) {
                    Activity attachedToTask =
                            BpmnCatchableErrorUtil
                                    .getAttachedToTask((Activity) eo);
                    // check if attached task has WebService Implementation
                    if (attachedToTask != null
                            && implFilter.select(attachedToTask)) {
                        Activity activity = (Activity) eo;
                        if (ScriptGrammarFactory.DATAMAPPER
                                .equals(ScriptGrammarFactory
                                        .getGrammarToUse(activity,
                                                DirectionType.OUT_LITERAL))) {
                            ErrorCatchType catchType =
                                    BpmnCatchableErrorUtil
                                            .getCatchTypeStrict(activity);
                            return ErrorCatchType.CATCH_SPECIFIC
                                    .equals(catchType);
                        }
                    }
                }
                return false;
            }
        };
    }

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.filter.AbstractWebServiceDataMapperFilter#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "developer.activityDataMapperMapperMapFromError"; //$NON-NLS-1$
    }

}
