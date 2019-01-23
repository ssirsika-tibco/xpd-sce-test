/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.wc.gmf;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.tibco.xpd.resources.util.URIUtil;

/**
 * Interface to be implemented by the {@link URI} fragment query provider. This
 * will provide the additional information that will be tagged with the
 * {@link URI} fragment to give some meaningful data of the given {@link URI}
 * reference. This can be used, for instance, when resolving broken links.
 * <p>
 * The query will be added with the fragment in the following manner:<br/><br/>
 * &lt;uri&gt;#&lt;fragment&gt;?&lt;query&gt;?
 * </p>
 * <p>
 * This query provider will be added as a default load option for a resource
 * using key {@link #OPTION_FRAGMENT_QUERY_PROVIDER}.
 * </p>
 * 
 * @author njpatel
 * 
 */
public interface URIFragmentQueryProvider {

    /**
     * {@link XMLResource} load option to set the query provider.
     */
    public static final String OPTION_FRAGMENT_QUERY_PROVIDER = "FRAGMENT_QUERY_PROVIDER"; //$NON-NLS-1$

    /**
     * Get the URI fragment query to append to the fragment. This will typically
     * include additional information that will aid in identifying this object
     * if it's reference link is broken.
     * <p>
     * <b>NOTE:</b> The returned query string should not be encoded - it will be
     * {@link URIUtil#encodeQuery(String) encoded} before it is appended to a
     * <code>URI</code>.
     * </p>
     * 
     * @param eo
     *            <code>EObject</code> being referenced.
     * @return query string. This can be <code>null</code>.
     */
    public String getURIFragmentQuery(EObject eo);
}
