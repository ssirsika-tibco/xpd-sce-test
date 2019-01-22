/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.errorEvents;

import java.util.Collection;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Interface for classes that wish to contribute information about the catchable
 * errors from activities that they are designed to catch. This is done via the
 * 
 * <code>com.tibco.xpd.analyst.resources.xpdl2.errorEvents.bpmnCatchableErrorProviders/ErrorBrowser</code>
 * extension point.
 * <p>
 * For instance, each service task implementation <i>may</i> return various
 * errors, but as implementation types are contributed via extensions then so
 * must the catchable error discovery be.
 * 
 * <p>
 * The errorId passed to the methods will be that returned by the
 * 
 * @author aallway
 * @since 3.2
 */
public interface IBpmnCatchableErrorsContributor {

    /**
     * @param errorThrower
     *            Activity that <b>may</b> throw errors handled by this
     *            contribution
     * 
     * @return Return true if the class supports browsing of the catchable
     *         errors thrown by the given activity.
     */
    boolean isApplicableErrorThrower(Activity errorTask);

    /**
     * This method is only called if isApplicableErrorThrower() returns true.
     * <p>
     * <b>NOTE: This method <i>must</i> return either instances of
     * {@link BpmnCatchableError} or {@link BpmnCatchableErrorFolder}</b>
     * 
     * @param errorTask
     *            Activity that <b>does</b> throw errors handled by this
     *            contribution
     * @return the list of the catchable errors (or folders of errors) that can
     *         be returned by the given activity.
     */
    Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorTask);

    /**
     * Return the image for the given error from the given thrower.
     * <p>
     * The thrower object will ALWAYS by the object in a BPmnCatchableError
     * object that was previously returned by THIS provider's
     * getCatchableTreeItems() method.
     * 
     * @param errorThrower
     *            Object taken from
     * @param errorId
     *            The id of the the specific error
     * 
     * @return The icon to use for browse picker tree.
     */
    Image getErrorImage(Object errorThrower, String errorId);

    /**
     * Given an errorThrower (originally set in a BpmnCatchError object returned
     * by getCatchableErrorTreeItems()) return it's label.
     * 
     * @param errorThrower
     * @return id
     */
    String getErrorThrowerId(Object errorThrower);

    /**
     * Given an errorThrower (originally set in a BpmnCatchError object) return
     * it's container's id.
     * 
     * @param errorThrower
     * @return containers id
     */
    String getErrorThrowerContainerId(Object errorThrower);
  
}
