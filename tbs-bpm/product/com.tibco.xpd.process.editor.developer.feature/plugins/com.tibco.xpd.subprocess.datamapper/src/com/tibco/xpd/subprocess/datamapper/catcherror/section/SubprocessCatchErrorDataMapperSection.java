/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper.catcherror.section;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.catcherror.datamapper.CatchErrorDataMapperConstants;
import com.tibco.xpd.catcherror.datamapper.section.AbstractCatchErrorDataMapperSection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.subprocess.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Data Mapper section for Catch SubProcess Error.
 * 
 * @author ssirsika
 * @since Feb 26, 2016
 */
public class SubprocessCatchErrorDataMapperSection extends
        AbstractCatchErrorDataMapperSection implements IFilter {

    /**
     * @param direction
     */
    public SubprocessCatchErrorDataMapperSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return CatchErrorDataMapperConstants.CATCH_SUBPROCESS_ERROR;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.CEDataMapperSection_OutputMappingTitle_message;
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
            Activity attachedToTask =
                    BpmnCatchableErrorUtil.getAttachedToTask((Activity) eo);
            if (attachedToTask != null
                    && SubProcUtil.isSubProcessImplementation(attachedToTask)) {
                Activity activity = (Activity) eo;
                if (ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory
                        .getGrammarToUse(activity, DirectionType.OUT_LITERAL))) {
                    ErrorCatchType catchType =
                            BpmnCatchableErrorUtil.getCatchTypeStrict(activity);
                    return ErrorCatchType.CATCH_SPECIFIC.equals(catchType);
                }
            }
        }
        return false;
    }

}
