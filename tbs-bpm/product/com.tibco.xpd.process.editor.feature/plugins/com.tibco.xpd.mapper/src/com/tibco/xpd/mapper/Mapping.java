/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * @author nwilson
 */
public class Mapping {
    /** The source object. */
    private Object source;

    /** The target object. */
    private Object target;

    /** The mapping model object. */
    private Object mappingModel;

    /** **/
    private boolean editable = true;

    /** The icon that should be used to decorate the start of mapping line **/
    private Image startLineAnnotation = null;

    /** The icon that should be used to decorate the start of mapping line */
    private Image endLineAnnotation = null;

    /** Sid XPD-7399 Override color (if null, then defaults are used). */
    private Color color;

    /**
     * Sid XPD-7399 Allow mapping to be set as virtual. This means that it is
     * not really a mapping explicitly drawn by user, instead it is a mapping
     * that is implied by some other scenario specific meta data.
     */
    private boolean isVirtual = false;

    /**
     * @param source
     *            The source object.
     * @param target
     *            The target object.
     * 
     * @deprecated v3.4 Use {@link #Mapping(Object, Object, Object)} instead.
     */
    @Deprecated
    public Mapping(final Object source, final Object target) {
        this(source, target, null);
    }

    /**
     * @param source
     * @param target
     * @param mappingModel
     */
    public Mapping(final Object source, final Object target,
            final Object mappingModel) {
        this.source = source;
        this.target = target;
        this.mappingModel = mappingModel;
    }

    /**
     * @return The source object.
     */
    public Object getSource() {
        return source;
    }

    /**
     * @return The target object.
     */
    public Object getTarget() {
        return target;
    }

    /**
     * @return The model object for the mapping.
     */
    public Object getMappingModel() {
        return mappingModel;
    }

    /**
     * @param obj
     *            The object to test.
     * @return true if equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof Mapping) {
            Mapping other = (Mapping) obj;
            /*
             * In the absence of either source OR target which happens for
             * broken mappings, the actual mapping model will be used to
             * differentiate between two mappings
             */
            Object mySource = source != null ? source : mappingModel;
            Object myTarget = target != null ? target : mappingModel;

            Object otherSource =
                    other.source != null ? other.source : other.mappingModel;
            Object otherTarget =
                    other.target != null ? other.target : other.mappingModel;

            if (mySource == null) {
                if (myTarget == null) {
                    if (otherSource == null && otherTarget == null) {
                        equal = true;
                    }
                } else {
                    if (myTarget.equals(otherTarget) && otherSource == null) {
                        equal = true;
                    }
                }
            } else {
                if (myTarget == null) {
                    if (mySource.equals(otherSource) && otherTarget == null) {
                        equal = true;
                    }
                } else {
                    if (mySource.equals(otherSource)
                            && myTarget.equals(otherTarget)) {
                        equal = true;
                    }
                }
            }

            if (equal) {
                equal = false;
                if (startLineAnnotation == null) {
                    if (other.startLineAnnotation == null) {
                        equal = true;
                    }
                } else {
                    if (startLineAnnotation.equals(other.startLineAnnotation)) {
                        equal = true;
                    }
                }

            }

            if (equal) {
                equal = false;
                if (endLineAnnotation == null) {
                    if (other.endLineAnnotation == null) {
                        equal = true;
                    }
                } else {
                    if (endLineAnnotation.equals(other.endLineAnnotation)) {
                        equal = true;
                    }
                }

            }
        }
        return equal;
    }

    /**
     * @return The hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        /*
         * In the absence of either source OR target which happens for broken
         * mappings, the actual mapping model will be used to differentiate
         * between two mappings
         */
        Object mySource = source != null ? source : mappingModel;
        Object myTarget = target != null ? target : mappingModel;

        return (mySource == null ? super.hashCode() : mySource.hashCode())
                + (myTarget == null ? super.hashCode() : myTarget.hashCode());

    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable
     *            the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @param startLineAnnotation
     *            The icon that should be used to decorate the start of mapping
     *            line
     */
    public void setStartLineAnnotation(Image startLineAnnotation) {
        this.startLineAnnotation = startLineAnnotation;
    }

    /**
     * @param endLineAnnotation
     *            The icon that should be used to decorate the end of mapping
     *            line
     */
    public void setEndLineAnnotation(Image endLineAnnotation) {
        this.endLineAnnotation = endLineAnnotation;
    }

    /**
     * @return The icon that should be used to decorate the start of mapping
     *         line
     */
    public Image getStartLineAnnotation() {
        return startLineAnnotation;
    }

    /**
     * @return The icon that should be used to decorate the end of mapping line
     */
    public Image getEndLineAnnotation() {
        return endLineAnnotation;
    }

    /**
     * @return The override color (null means use {@link Mapper} defaults).
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color
     *            The override color (null means use {@link Mapper} defaults).
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Allows mapping to be set as virtual. This means that it is not really a
     * mapping explicitly drawn by user, instead it is a mapping that is implied
     * by some other scenario specific meta data.
     * <p>
     * Virtual mappings may be treated slightly differently by {@link Mapper}.
     * Forinstance, the user will not necessarily automatically be prevented
     * from dropping a new mapping on a virtually mapped target. That validation
     * will be left to the owner.
     * 
     * @param isVirtualMapping
     *            the isVirtualMapping to set
     */
    public void setVirtual(boolean isVirtualMapping) {
        this.isVirtual = isVirtualMapping;
    }

    /**
     * @return Whether the mapping is set as virtual. This means that it is not
     *         really a mapping explicitly drawn by user, instead it is a
     *         mapping that is implied by some other scenario specific meta
     *         data.
     */
    public boolean isVirtual() {
        return isVirtual;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {

        return String
                .format("Mapping: From '%s' to '%s' (Editable=%s, MappingModel=(%s))", //$NON-NLS-1$
                        source,
                        target,
                        editable,
                        mappingModel);
    }
}
