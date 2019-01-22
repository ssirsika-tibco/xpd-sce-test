/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

import java.util.Objects;

/**
 * This class will be implemented to contribute content that can be provided on
 * the Data Mapper viwer for users to map to and from.
 * 
 * @author Ali
 * @since 4 Feb 2015
 */
public abstract class AbstractDataMapperContentContributor {

    private AbstractDataMapperInfoProvider dataMapperInfoProvider = null;

    /**
     * Create the info provider for the data mapper content contributor.
     * <p>
     * <b>This is called only once, on demand, for each contributor.</b>
     * 
     * @return DataMapperInfoProvider - info provider for the process data
     *         mapper
     * 
     */
    public abstract AbstractDataMapperInfoProvider createInfoProvider();

    /**
     * @return DataMapperInfoProvider - info provider for the process data
     *         mapper
     */
    public final AbstractDataMapperInfoProvider getInfoProvider() {
        if (dataMapperInfoProvider == null) {
            dataMapperInfoProvider = createInfoProvider();
        }

        return dataMapperInfoProvider;
    }

    /**
     * 
     * @return ID for this contributor
     */
    public abstract String getContributorId();

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractDataMapperContentContributor) {
            return Objects.equals(((AbstractDataMapperContentContributor) obj)
                    .getContributorId(), this.getContributorId());
        }
        return super.equals(obj);
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        if (getContributorId() != null) {
            return getContributorId().hashCode();
        }
        return super.hashCode();
    }
}
