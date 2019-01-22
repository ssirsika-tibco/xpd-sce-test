package com.tibco.xpd.process.datamapper.common;

import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Data Mapper content contributor for Process Data. It provides content and
 * javascript generation info providers.
 * 
 * 
 * @author Ali
 * @since 8 Apr 2015
 */
public class ProcessDataMapperContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * The data mapper contributor id for process data content provider.
     */
    public static final String PROCESS_DATA_CONTRIBUTOR_ID =
            "ProcessData.DataMapperContent"; //$NON-NLS-1$

    public ProcessDataMapperContentContributor() {
    }

    @Override
    public String getContributorId() {
        return PROCESS_DATA_CONTRIBUTOR_ID;
    }

    /**
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new ProcessDataMapperConceptPathProvider();
    }

}
