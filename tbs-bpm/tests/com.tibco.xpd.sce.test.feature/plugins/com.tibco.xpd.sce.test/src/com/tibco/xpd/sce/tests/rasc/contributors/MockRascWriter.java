/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.rasc.contributors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.PropertyValue;
import com.tibco.bpm.dt.rasc.exception.RuntimeApplicationException;
import com.tibco.xpd.rasc.core.RascWriter;

/**
 * A mock implementation of RascWriter that can be passed to RascContributor
 * implementations and to allow the unit tests to retrieve the content generated
 * by them.
 *
 * @author pwatson
 * @since 4 Jun 2019
 */
public class MockRascWriter implements RascWriter {
    final ArrayList<WriterContent> artifacts = new ArrayList<>();

    final Map<String, PropertyValue[]> manifestAttrs = new HashMap<>();

    /**
     * @see com.tibco.xpd.rasc.core.RascWriter#addContent(java.lang.String,
     *      com.tibco.bpm.dt.rasc.MicroService[])
     */
    @Override
    public OutputStream addContent(String aName, String aArtifactName,
            String aInternalName, MicroService[] aMicroServices)
            throws RuntimeApplicationException, IOException {
        WriterContent result = new WriterContent(aName, aArtifactName,
                aInternalName, aMicroServices);
        artifacts.add(result);
        return result.getContent();
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascWriter#setManifestAttribute(java.lang.String,
     *      com.tibco.bpm.dt.rasc.PropertyValue[])
     */
    @Override
    public void setManifestAttribute(String aAttrName,
            PropertyValue[] aValues) {
        manifestAttrs.put(aAttrName, aValues);
    }

    /**
     * Allows the test to retrieve the captured artifacts, in the order they
     * were captured.
     *
     * @return the ordered collection of captured artifacts.
     */
    public List<WriterContent> getArtifacts() {
        return artifacts;
    }

    /**
     * Allows the test to retrieve any PropertyValues that have been assigned to
     * the named manifest attribute by the RascContributors.
     * 
     * @param aAttrName
     * @return
     */
    public PropertyValue[] getManifestAttribute(String aAttrName) {
        return manifestAttrs.get(aAttrName);
    }

    /**
     * Used to capture the properties and content of an artifact written to the
     * MockRascWriter.
     */
    public static class WriterContent {
        private String resourcePath;

        private MicroService[] services;

        private ByteArrayOutputStream content;

        private String internalName;

        private String artifactName;

        public WriterContent(String aResourcePath, String aArtifactName,
                String aInternalName, MicroService[] aServices) {
            resourcePath = aResourcePath;
            artifactName = aArtifactName;
            internalName = aInternalName;
            services = aServices;
            content = new ByteArrayOutputStream();
        }

        public String getArtifactName() {
            return artifactName;
        }

        public String getInternalName() {
            return internalName;
        }

        public String getFullPath() {
            return resourcePath;
        }

        public MicroService[] getServices() {
            return services;
        }

        public ByteArrayOutputStream getContent() {
            return content;
        }
    }
}