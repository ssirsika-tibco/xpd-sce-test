/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;

/**
 * Class to represent a registry search.
 * 
 * @author nwilson
 */
public class Search implements Serializable {
    /** The serial UID. */
    private static final long serialVersionUID = 420777901004597352L;
    /** Identifier for a business search. */
    public static final int FIND_BUSINESS = 1;
    /** Identifier for a service search. */
    public static final int FIND_SERVICE = 2;

    /** The name of the search. */
    private String name;
    /** The type of search. */
    private int type;
    /** A list of name search criteria. */
    private final ArrayList<String> names;

    /**
     * @param name
     *            The search name.
     * @param type
     *            The search type.
     */
    public Search(String name, int type) {
        Assert.isLegal(type == FIND_BUSINESS || type == FIND_SERVICE,
                "Unknown search type."); //$NON-NLS-1$
        this.name = name;
        this.type = type;
        names = new ArrayList<String>();
    }

    /**
     * @return The search name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the new name for search.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The search type.
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     *            new type.
     */
    public void setType(int type) {
        Assert.isLegal(type == FIND_BUSINESS || type == FIND_SERVICE,
                "Unknown search type."); //$NON-NLS-1$
        this.type = type;
    }

    /**
     * @param name
     *            The name criteria string to add.
     */
    public void addNameCriteria(String name) {
        names.add(name);
    }

    /**
     * @param name
     *            The name criteria string to remove.
     */
    public void removeNameCriteria(String name) {
        names.remove(name);
    }

    /**
     * @return A list of name criteria strings.
     */
    public List<String> getNameCriteria() {
        return Collections.unmodifiableList(names);
    }

    /**
     * @return name.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }

}
