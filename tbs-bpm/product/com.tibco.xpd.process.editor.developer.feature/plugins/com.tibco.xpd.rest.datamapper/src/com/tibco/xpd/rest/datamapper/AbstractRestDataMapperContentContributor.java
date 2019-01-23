/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperLabelProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskItemProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;

/**
 * Data Mapper Content contributor for REST Service items.
 * 
 * @author nwilson
 * @since 1 May 2015
 */
public abstract class AbstractRestDataMapperContentContributor extends
        AbstractDataMapperContentContributor {
    private MappingDirection direction;

    public AbstractRestDataMapperContentContributor(MappingDirection direction) {
        this.direction = direction;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#createInfoProvider()
     * 
     * @return
     */
    @Override
    public AbstractDataMapperInfoProvider createInfoProvider() {
        ITreeContentProvider contentProvider =
                new RestServiceTaskItemProvider(direction);
        ILabelProvider labelProvider =
                new ScriptableLabelProvider(new RestMapperLabelProvider());
        return new RestDataMapperInfoProvider(direction, contentProvider,
                labelProvider, false);
    }

}
