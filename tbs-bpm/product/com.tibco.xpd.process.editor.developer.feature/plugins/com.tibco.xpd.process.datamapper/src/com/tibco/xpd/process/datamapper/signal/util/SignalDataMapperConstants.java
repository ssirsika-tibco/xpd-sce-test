/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.signal.util;

/**
 * Common constants definitions for Local/Global Signal Data Mapper.
 *
 * @author sajain
 * @since Jul 16, 2019
 */
public class SignalDataMapperConstants {

    /**
     * Global Signal Catch context.
     */
    public static final String GLOBAL_SIGNAL_CATCH = "GlobalSignalCatch"; //$NON-NLS-1$

    /**
     * Global Signal Throw context.
     */
    public static final String GLOBAL_SIGNAL_THROW = "GlobalSignalThrow"; //$NON-NLS-1$

    /**
     * Global Signal Catch Correlation Data Mapper Content Contributor ID.
     */
    public static final String GS_CATCH_CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID =
            "MapFromGlobalSignalCorrelation.DataMapperContent"; //$NON-NLS-1$

    /**
     * Global Signal Throw Correlation Data Mapper Content Contributor ID.
     */
    public static final String GS_THROW_CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID =
            "MapToGlobalSignalCorrelation.DataMapperContent"; //$NON-NLS-1$

    /**
     * Local Signal Catch context.
     */
    public static final String LOCAL_SIGNAL_CATCH = "LocalSignalCatch"; //$NON-NLS-1$

    /**
     * Map to Local Signal Source Mapping Contributor ID.
     */
    public static final String LOCAL_SIGNAL_CATCH_SOURCE_CONTRIBUTOR_ID = "MapFromLocalSignal.DataMapperContent"; //$NON-NLS-1$

    /**
     * Map to Local Signal Target Mapping Contributor ID.
     */
    public static final String LOCAL_SIGNAL_CATCH_TARGET_CONTRIBUTOR_ID =
            "MapFromLocalSignalTarget.DataMapperContent"; //$NON-NLS-1$

    /**
     * Map to Global Signal Target Mapping Contributor ID.
     */
    public static final String GLOBAL_SIGNAL_CATCH_TARGET_CONTRIBUTOR_ID =
            "MapFromGlobalSignalTarget.DataMapperContent"; //$NON-NLS-1$

}
