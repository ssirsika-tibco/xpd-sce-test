/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.destinations;

import java.util.Collection;
import java.util.Collections;

/**
 * Destination environment storage class.
 * 
 * @author nwilson
 */
public class Destination {
    /** The destination environment ID. */
    private String id;

    /** The destination environment name. */
    private String name;

    /** The destination environment version. */
    private String version;

    /** Whether the environment is selectable as a destination. */
    private boolean selectable;

    /** A list of validation provider IDs. */
    private Collection<String> validationProviders;

    /**
     * @param id
     *            The destination environment ID.
     * @param name
     *            The destination environment name.
     * @param version
     *            The destination environment version.
     * @param selectable
     *            Whether the environment is selectable as a destination.
     * @param validationProviders
     *            A list of validation provider IDs.
     */
    public Destination(String id, String name, String version,
            boolean selectable, Collection<String> validationProviders) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.selectable = selectable;

        this.validationProviders = Collections
                .unmodifiableCollection(validationProviders);
    }

    /**
     * Get the destination id as set in the extension.
     * 
     * @return The destination environment ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the destination name as set in the extension.
     * 
     * @return The destination environment name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the destination version as set in the extension.
     * 
     * @return The destination environment version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return Whether the environment is selectable as a destination.
     */
    public boolean isSelectable() {
        return selectable;
    }

    /**
     * Get all providers bound to this destination.
     * 
     * @return A list of validation provider IDs.
     */
    public Collection<String> getValidationProviders() {
        return validationProviders;
    }
}
