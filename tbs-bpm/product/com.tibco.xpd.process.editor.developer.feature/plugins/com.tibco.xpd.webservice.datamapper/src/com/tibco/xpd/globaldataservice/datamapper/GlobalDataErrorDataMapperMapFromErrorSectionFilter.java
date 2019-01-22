/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.globaldataservice.datamapper;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Filter for 'Map From Error' section which handles Global Data.
 * 
 * @author ssirsika
 * @since 08-Mar-2016
 */
public class GlobalDataErrorDataMapperMapFromErrorSectionFilter implements
        IFilter {

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

        // Check it's a catch Error intermediate event.
        if (eo instanceof Activity) {
            Activity activity = (Activity) eo;

            if (activity.getEvent() instanceof IntermediateEvent) {
                IntermediateEvent event =
                        (IntermediateEvent) activity.getEvent();

                if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {

                    ErrorCatchType catchType =
                            BpmnCatchableErrorUtil.getCatchTypeStrict(activity);

                    /*
                     * Check that this is a specific error for a Global Data
                     * task.
                     */
                    if (catchType == ErrorCatchType.CATCH_SPECIFIC) {
                        if (ScriptGrammarFactory.DATAMAPPER
                                .equals(ScriptGrammarFactory
                                        .getGrammarToUse(activity,
                                                DirectionType.OUT_LITERAL))) {
                            Object thrower =
                                    BpmnCatchableErrorUtil
                                            .getErrorThrower(activity);
                            if (thrower instanceof Activity) {
                                Implementation implementation =
                                        ((Activity) thrower)
                                                .getImplementation();

                                if (implementation instanceof Task) {
                                    TaskService taskService =
                                            ((Task) implementation)
                                                    .getTaskService();
                                    if (taskService != null) {
                                        GlobalDataOperation globalDataOp =
                                                (GlobalDataOperation) Xpdl2ModelUtil
                                                        .getOtherElement(taskService,
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getDocumentRoot_GlobalDataOperation());

                                        return globalDataOp != null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

}
