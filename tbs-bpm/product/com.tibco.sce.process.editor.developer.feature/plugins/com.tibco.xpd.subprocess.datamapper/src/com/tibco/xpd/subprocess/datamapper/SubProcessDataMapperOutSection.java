/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.subprocess.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Sub-Process Mapping OUT section for Data Mapper grammar.
 * 
 * @author sajain
 * @since Oct 29, 2015
 */
public class SubProcessDataMapperOutSection extends
        AbstractSubProcessDataMapperSection implements IFilter {

    /**
     * @param direction
     */
    public SubProcessDataMapperOutSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return SubProcessDataMapperConstants.SUBPROCESS_TO_PROCESS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.CSPDataMapperOutSection_OutputMappingTitle_message;
    }

    @Override
    public boolean select(Object toTest) {
        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        if (eo instanceof Activity
                && SubProcUtil.isSubProcessImplementation((Activity) eo)) {

            if (ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory
                    .getGrammarToUse((Activity) eo, DirectionType.OUT_LITERAL))) {
                return true;
            }

        }
        return false;
    }

}
