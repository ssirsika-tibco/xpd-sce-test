/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Data mapper section for "Output from Service" mappings of Web-Service
 * activities.
 * 
 * @author sajain
 * @since Jan 20, 2016
 */
public class WebServiceDataMapperOutputFromServiceSection extends
        AbstractWebServiceDataMapperSection implements IFilter {

    /**
     * @param direction
     */
    public WebServiceDataMapperOutputFromServiceSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.OUTPUT_FROM_SERVICE;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.WSDataMapperOutSection_OutputMappingTitle_message;
    }

    @Override
    public boolean select(Object toTest) {
        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        if (eo instanceof Activity) {

            Implementation impl = ((Activity) eo).getImplementation();

            /*
             * Web Service Task.
             */
            if (impl instanceof Task) {

                Task task = (Task) impl;

                TaskService taskService = task.getTaskService();

                if (null != taskService) {

                    if (null != taskService.getMessageOut()) {

                        if (ScriptGrammarFactory.DATAMAPPER
                                .equals(ScriptGrammarFactory
                                        .getGrammarToUse((Activity) eo,
                                                DirectionType.OUT_LITERAL))) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

}
