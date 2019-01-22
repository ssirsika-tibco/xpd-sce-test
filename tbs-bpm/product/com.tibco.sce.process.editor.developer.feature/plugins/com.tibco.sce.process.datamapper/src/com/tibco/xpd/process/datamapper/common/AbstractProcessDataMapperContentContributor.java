/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.common;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * This class will be implemented to contribute Process Data Content that can be
 * provided on the Data Mapper viewer for users to map to and from.
 * 
 * @author sajain
 * @since Nov 2, 2015
 */
public abstract class AbstractProcessDataMapperContentContributor extends
        AbstractDataMapperContentContributor {

    public AbstractProcessDataMapperContentContributor() {
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#createInfoProvider()
     * 
     * @return
     */
    @Override
    public final AbstractDataMapperInfoProvider createInfoProvider() {

        ITreeContentProvider contentProvider = createItemProvider();

        ILabelProvider labelProvider = createLabelProvider();

        AbstractDataMapperInfoProvider infoProvider =
                createProcessDataInfoProvider(contentProvider, labelProvider);
        return infoProvider;
    }

    /**
     * Subclasses can override this method to provide custom
     * {@link ILabelProvider}
     * 
     * @return {@link ILabelProvider} which can be used to build
     *         {@link AbstractDataMapperInfoProvider}
     */
    protected ILabelProvider createLabelProvider() {
        return new ParameterLabelProvider();
    }

    /**
     * Create the content provider for this content contributor (must be one
     * that deals in {@link ConceptPath} for data fields and parameters.
     * 
     * @return The content provider
     */
    abstract protected ITreeContentProvider createItemProvider();

    /**
     * Create the info provider for this content contributor,(must be one that
     * deals with {@link ConceptPath} for data fields and parameters.
     * 
     * @param labelProvider
     * @param contentProvider
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#createInfoProvider()
     * 
     * @return
     */
    protected ProcessDataMapperInfoProvider createProcessDataInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        return new ProcessDataMapperInfoProvider(contentProvider, labelProvider);

    }
}
