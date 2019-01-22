/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.infoProviders;

import java.util.Objects;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;

/**
 * Wrapper class to wrap contributed content for the Data Mapper. It stores
 * contributed object, its parent (if any) and the contributor.
 * 
 * @author Ali
 * @since 20 Feb 2015
 */
public class WrappedContributedContent {

    private AbstractDataMapperContentContributor contributor;

    private WrappedContributedContent parent;

    private Object contributedObject;

    private IScriptDataMapperProvider scriptDataMapperProvider; // May be null!

    private Object dataMapperInputObject; // May be null!

    /**
     * Contruct wrapped contributed content
     * 
     * @param contributor
     *            COntributed content provider (that contributed the
     *            contributedObject)
     * @param contributedObject
     * @param parent
     * @param scriptDataMapperProvider
     *            The xpdExt:ScriptDataMapper element provider (needed for some
     *            label provision requirements).
     * @param dataMapperInputObject
     *            The input object passed to scriptDataMapperProvider for access
     *            to the xpdExt:ScriptDataMapper element in context
     */
    public WrappedContributedContent(
            AbstractDataMapperContentContributor contributor,
            Object contributedObject, WrappedContributedContent parent,
            IScriptDataMapperProvider scriptDataMapperProvider,
            Object dataMapperInputObject) {
        super();
        this.contributedObject = contributedObject;
        this.contributor = contributor;
        this.parent = parent;

        this.scriptDataMapperProvider = scriptDataMapperProvider;
        this.dataMapperInputObject = dataMapperInputObject;
    }

    /**
     * Contruct wrapped contributed content
     * 
     * @param contributor
     *            COntributed content provider (that contributed the
     *            contributedObject)
     * @param contributedObject
     * @param parent
     * 
     */
    public WrappedContributedContent(
            AbstractDataMapperContentContributor contributor,
            Object contributedObject, WrappedContributedContent parent) {
        super();
        this.contributedObject = contributedObject;
        this.contributor = contributor;
        this.parent = parent;

        this.scriptDataMapperProvider = null;
        this.dataMapperInputObject = null;
    }

    /**
     * @return the contributedObject
     */
    public Object getContributedObject() {
        return contributedObject;
    }

    /**
     * @return the contributor
     */
    public AbstractDataMapperContentContributor getContributor() {
        return contributor;
    }

    /**
     * @return the parent
     */
    public WrappedContributedContent getParent() {
        return parent;
    }

    /**
     * @return the scriptDataMapper model element.
     */
    public ScriptDataMapper getScriptDataMapperModel() {
        if (scriptDataMapperProvider != null) {
            return scriptDataMapperProvider
                    .getScriptDataMapper(dataMapperInputObject);
        }
        return null;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof WrappedContributedContent) {
            WrappedContributedContent other = (WrappedContributedContent) obj;

            if (Objects.equals(other.contributedObject, contributedObject)
                    && Objects.equals(other.contributor, contributor)) {
                return true;
            }

        }
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime
                        * result
                        + ((contributedObject == null) ? 0 : contributedObject
                                .hashCode());
        result =
                prime
                        * result
                        + ((contributor == null && contributor
                                .getContributorId() != null) ? 0 : contributor
                                .getContributorId().hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return "DataMapper WrappedContributedObject: " + (contributedObject != null ? contributedObject.toString() : "null"); //$NON-NLS-1$//$NON-NLS-2$
    }
}
