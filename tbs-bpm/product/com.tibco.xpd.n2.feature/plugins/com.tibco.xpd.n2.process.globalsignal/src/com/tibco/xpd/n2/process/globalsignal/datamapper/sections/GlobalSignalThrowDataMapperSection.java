/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.sections;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalDataMapperConstants;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Data Mapper section for Global Signal Throw.
 * 
 * @author sajain
 * @since Apr 27, 2016
 */
public class GlobalSignalThrowDataMapperSection extends
        AbstractGlobalSignalDataMapperSection {

    /**
     * @param direction
     */
    public GlobalSignalThrowDataMapperSection() {
        super(MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return GlobalSignalDataMapperConstants.GLOBAL_SIGNAL_THROW;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.GlobalSignalThrowDataMapperSection_Title;
    }

    /**
     * Filter for Global Signal Throw Data Mapper Section.
     * 
     * @author sajain
     * @since Apr 27, 2016
     */
    public static class Filter implements IFilter {

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {

            EObject eo = null;

            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {

                    if (GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                        if (ScriptGrammarFactory.DATAMAPPER
                                .equals(ScriptGrammarFactory
                                        .getGrammarToUse(activity,
                                                DirectionType.IN_LITERAL))) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
}
