/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.core.test;

import org.junit.Test;

import com.tibco.xpd.analyst.resources.xpdl2.indexing.ProcessParticipantResourceIndexProvider;

import junit.framework.TestCase;

/**
 * JUnit test suite for <code>ProcessParticipantResourceIndexProvider</code>.
 *
 * @author sajain
 * @since May 30, 2019
 */
public class ProcessParticipantResourceIndexProviderTest extends TestCase {

    /**
     * @see junit.framework.TestCase#setUp()
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test the available generic indexer attributes.
     */
    @Test
    public void testGenericIndexerAttributes() {
        assertEquals(
                ProcessParticipantResourceIndexProvider.PROCESS_PARTICIPANT_INDEX_TYPE,
                "PROCESS_PARTICIPANT"); //$NON-NLS-1$
        assertEquals(
                ProcessParticipantResourceIndexProvider.PROCESS_PARTICIPANT_INDEXER_ID,
                "com.tibco.xpd.analyst.resources.xpdl2.indexing.participantIndexer"); //$NON-NLS-1$
    }

    /**
     * Test the available resource types.
     */
    @Test
    public void testResourceTypes() {
        assertNotNull(
                ProcessParticipantResourceIndexProvider.ResourceType.EMAIL);
        assertNotNull(
                ProcessParticipantResourceIndexProvider.ResourceType.JDBC);
        assertNotNull(
                ProcessParticipantResourceIndexProvider.ResourceType.REST_SERVICE);
    }
    
    /**
     * Test the available REST service participant shared resource attributes.
     */
    @Test
    public void testRestSharedResourceAttributes() {
        assertEquals(
                ProcessParticipantResourceIndexProvider.ATTRIBUTE_ENDPOINT_NAME,
                "endPointName"); //$NON-NLS-1$
        assertEquals(
                ProcessParticipantResourceIndexProvider.ATTRIBUTE_SHARED_RESOURCE_TYPE,
                "sharedResourceType"); //$NON-NLS-1$
    }

    /**
     * Test the available resource types descriptions.
     */
    @Test
    public void testResourceTypesDescriptions() {
        /*
         * Check descriptions.
         */
        assertEquals(
                ProcessParticipantResourceIndexProvider.ResourceType.EMAIL
                        .getDescription(),
                ""); //$NON-NLS-1$
        assertEquals(
                ProcessParticipantResourceIndexProvider.ResourceType.JDBC
                        .getDescription(),
                ""); //$NON-NLS-1$
        assertEquals(
                ProcessParticipantResourceIndexProvider.ResourceType.REST_SERVICE
                        .getDescription(),
                "REST Service invocation shared resource."); //$NON-NLS-1$
    }

    /**
     * Test the available resource types descriptions.
     */
    @Test
    public void testResourceTypesFeatures() {

        /*
         * Check features.
         */
        assertEquals(
                ProcessParticipantResourceIndexProvider.ResourceType.EMAIL
                        .getFeature().getName(),
                "email"); //$NON-NLS-1$

        assertEquals(
                ProcessParticipantResourceIndexProvider.ResourceType.JDBC
                        .getFeature().getName(),
                "jdbc"); //$NON-NLS-1$

        assertEquals(
                ProcessParticipantResourceIndexProvider.ResourceType.REST_SERVICE
                        .getFeature().getName(),
                "restService"); //$NON-NLS-1$
    }
}
