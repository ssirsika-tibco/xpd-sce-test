/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import java.io.IOException;
import java.io.OutputStream;

import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.PropertyValue;
import com.tibco.bpm.dt.rasc.exception.RuntimeApplicationException;

/**
 * A facade on the RASC DeploymentWriter to restrict the RascContributors'
 * access to the RASC manifest. Allow contributors access only to those methods
 * of the DeploymentWriter that they require in order to add content to the
 * RASC.
 *
 * @author pwatson
 * @since 26 Feb 2019
 */
public interface RascWriter {
    /**
     * Adds the named content to the RASC and identifies the micro-services to
     * which the content is to be delivered. It then provides the caller with an
     * OutputStream to which the artifact content can be written.
     * <p>
     * The name may include a path relative to the root of the RASC, but must be
     * unique within this RASC's artifacts. That is, no other content of the
     * same path and name can exist.
     * <p>
     * The returned OutputStream will be closed when another call is made to the
     * addContent() method. The caller does not need to call close() on the
     * returned OutputStream but, if it does, it should not attempt to write any
     * additional content to that stream.
     * 
     * @param aName
     *            the full path name to which the artifact content is to be
     *            written within the RASC.
     * @param aArtifactName
     *            The human readable name for the artifact
     * @param aInternalName
     *            The internal name for the artifact
     * @param aMicroServices
     *            the collection of micro-services to which the content is
     *            destined.
     * 
     * @return the output stream to which the content can be written.
     * @throws RuntimeApplicationException
     *             if no name or MicroServices are provided, or an artifact of
     *             the same name already exists.
     * @throws IOException
     *             if an IO error occurs whilst generating the OutputStream.
     */
    public OutputStream addContent(String aName, String aArtifactName,
            String aInternalName, MicroService[] aMicroServices)
            throws RuntimeApplicationException, IOException;

    /**
     * Sets the named manifest attribute to the given collection of values. A
     * manifest attribute can hold many values, each comma-separated.
     * Furthermore, each value can have any number of additional name/value
     * pairs, each semi-colon-separated.
     * 
     * @param aAttrName
     *            the name of the manifest attribute to be set.
     * @param aValues
     *            the value(s) to be set against the named attribute.
     */
    public void setManifestAttribute(String aAttrName, PropertyValue[] aValues);
}
