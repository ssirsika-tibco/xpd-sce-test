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
 * Sub-Process Mapping IN section for Data Mapper grammar.
 * 
 * @author sajain
 * @since Oct 20, 2015
 */
public class SubProcessDataMapperInSection extends
        AbstractSubProcessDataMapperSection implements IFilter {

    /**
     * @param direction
     */
    public SubProcessDataMapperInSection() {
        super(MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return SubProcessDataMapperConstants.PROCESS_TO_SUBPROCESS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.CSPDataMapperInSection_InputMappingTitle_message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        if (eo instanceof Activity
                && SubProcUtil.isSubProcessImplementation((Activity) eo)) {

            if (ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory
                    .getGrammarToUse((Activity) eo, DirectionType.IN_LITERAL))) {

                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#isCreatingTargetData()
     *
     * @return
     */
    @Override
    protected boolean isCreatingTargetData() {
        /* Sub-process input data is brand new and created from scratch */
        return true;
    }
}
