/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Filter for catch global signal mapper.
 *
 * @author sajain
 * @since Jul 4, 2019
 */
public class CatchGlobalSignalMapperFilter implements IFilter {

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
            if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity))) {
                /*
                 * XPD-7075: Show mapper only for Global Signal events.
                 */
                if (GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                    if (ScriptGrammarFactory.JAVASCRIPT
                            .equals(ScriptGrammarFactory
                                    .getGrammarToUse(activity,
                                            DirectionType.OUT_LITERAL))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
