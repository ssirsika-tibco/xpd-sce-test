/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.extensions;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Process;

/**
 * Class for contributors to the
 * <code>com.tibco.xpd.processeditor.xpdl2.processDiagramReferenceHighlighter</code>
 * extension point.
 * <p>
 * When the eclipse workspace selection changes (the selection that is governed
 * by the currently active view / editor) the contributor is given the
 * opportunity to return a list of model objects for the main process diagram
 * elements (xpdl2:activity / xpdl2:transition and so on) that reference a given
 * object.
 * <p>
 * 
 * 
 * @author aallway
 * @since 20 Jan 2011
 */
public abstract class AbstractReferenceHighlighterContribution {

    /**
     * @return The class of referenced objects that this contribution deals
     *         with.
     */
    public abstract Class<? extends Object> getInterestingReferencedObjectClass();

    /**
     * @param interestingReferencedObject
     *            The selected object to search for reference to. Guaranteed to
     *            be of type you return from
     *            {@link #getInterestingReferencedObjectClass()}
     * @return The label for the object
     */
    public abstract String getInterestingObjectLabel(
            Object interestingReferencedObject);

    /**
     * Get the diagram model objects (the model objects such as Activity,
     * Transition etc) for the main process diagram parts that reference the
     * given referencedObject.
     * 
     * @param interestingReferencedObject
     *            The selected object to search for reference to. Guaranteed to
     *            be of type you return from
     *            {@link #getInterestingReferencedObjectClass()}
     * @param diagramProcess
     *            The process in which to search for references to
     *            referencedObject
     * 
     * @return Set of diagram part model objects.
     */
    public abstract Collection<? extends EObject> getReferencingDiagramModelObjects(
            Object interestingReferencedObject, Process diagramProcess);

    /**
     * @param interestingReferencedObject
     * @param process
     * @return <code>true</code> if the given object (which is guaranteed
     *         correct type according to
     *         {@link #getInterestingReferencedObjectClass()}) is in scope of
     *         the given process.
     */
    public abstract boolean isInScope(Object interestingReferencedObject,
            Process process);

}
