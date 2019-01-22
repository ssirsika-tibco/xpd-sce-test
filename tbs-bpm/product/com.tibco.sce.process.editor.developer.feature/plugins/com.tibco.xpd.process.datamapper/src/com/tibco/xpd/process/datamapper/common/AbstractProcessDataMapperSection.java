/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.common;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.datamapper.api.AbstractDataMapperSection;
import com.tibco.xpd.mapper.MappingDirection;

/**
 * The class provides the common functionality among Script Task and Activity
 * Script related Data Mapper sections for different script contexts (e.g.,
 * setting input etc.) and sub-classes should provide implementation of
 * getSetDataMapperGrammarCommand() based on their context.
 * 
 * @author Ali
 * @since 12 Jan 2015
 */
public abstract class AbstractProcessDataMapperSection extends
        AbstractDataMapperSection {

    /**
     * @param direction
     */
    public AbstractProcessDataMapperSection() {
        super(MappingDirection.IN);
    }

    /**
     * @param editingDomain
     * @param scriptGrammar
     * @param eObject
     * @return
     */
    public abstract Command getSetDataMapperGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar, EObject eObject);

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        /* Sid XPD-7575 - no need to waste space stating the obvious. */
        return null;
    }

}
