/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.tibco.xpd.resources.wc.gmf.URIFragmentQueryProvider;

/**
 * Util to provide commonly used methods for managing <code>URI</code>s.
 * 
 * @author njpatel
 * 
 */
public final class URIUtil {

    private static final char QUERY_DELIM = '?';

    /**
     * Get the fragment query from the given <code>URI</code>. The fragment
     * query contains some meaningful information to identify the object that
     * this <code>URI</code> represents.
     * <p>
     * NOTE: The returned value is already decoded.
     * </p>
     * 
     * @param uri
     *            <code>URI</code>
     * @return fragment query, <code>null</code> if none set.
     */
    public static String getFragmentQuery(URI uri) {
        String query = null;

        if (uri != null && uri.hasFragment()) {
            String fragment = uri.fragment();

            if (fragment.indexOf(QUERY_DELIM) > 0) {
                query =
                        fragment.substring(fragment.indexOf(QUERY_DELIM) + 1,
                                fragment.lastIndexOf(QUERY_DELIM));

                // Decode the query
                query = decodeQuery(query);
            }
        }

        return query;
    }

    /**
     * Get the <code>URI</code> for the given <code>EObject</code>. If the
     * <code>Resource</code> of the <code>EObject</code> is an
     * {@link XMLResource} and provides a {@link URIFragmentQueryProvider
     * fragment query provider} then the <code>URI</code> will contain a
     * fragment with the query appended to it. Otherwise, this will return an
     * <code>URI</code> with the fragment only.
     * 
     * @param eo
     *            <code>EObject</code> for which to get the <code>URI</code>.
     * @return <code>URI</code> containing a fragment and query if applicable.
     */
    public static URI getURIWithFragmentQuery(EObject eo) {
        URI uri = null;

        if (eo != null && eo.eResource() != null) {
            Resource resource = eo.eResource();
            uri = resource.getURI();

            if (uri != null) {
                String fragment = resource.getURIFragment(eo);

                if (fragment != null) {
                    /*
                     * If this is a resource that has a fragment query provider
                     * then get query from it
                     */
                    if (resource instanceof XMLResource) {
                        Object provider =
                                ((XMLResource) resource)
                                        .getDefaultLoadOptions()
                                        .get(URIFragmentQueryProvider.OPTION_FRAGMENT_QUERY_PROVIDER);

                        if (provider instanceof URIFragmentQueryProvider) {
                            String query =
                                    ((URIFragmentQueryProvider) provider)
                                            .getURIFragmentQuery(eo);

                            if (query != null && query.length() > 0) {
                                fragment +=
                                        QUERY_DELIM + encodeQuery(query)
                                                + QUERY_DELIM;
                            }
                        }
                    }
                    // Append the fragment to the uri
                    uri = uri.appendFragment(fragment);
                }
            }
        }

        return uri;
    }

    /**
     * Encode the given query string. This will use
     * {@link URI#encodeSegment(String, boolean) URI.encodeSegment} to encode
     * the string and additionally will encode all ':' and '&' characters.
     * 
     * @see URI#encodeSegment(String, boolean)
     * 
     * @param query
     *            query string to encode
     * @return encoded string
     */
    public static String encodeQuery(String query) {
        if (query != null) {
            query = URI.encodeSegment(query, false);
            StringBuffer escaped = new StringBuffer();
            int len = query.length();

            // Additionally all ':' and '&' in the query need to be escaped
            String colonHexString = Integer.toHexString(':');
            String ampHexString = Integer.toHexString('&');
            for (int idx = 0; idx < len; idx++) {
                char ch = query.charAt(idx);

                if (ch == ':') {
                    escaped.append("%" + colonHexString); //$NON-NLS-1$
                } else if (ch == '&') {
                    escaped.append("%" + ampHexString); //$NON-NLS-1$
                } else {
                    escaped.append(ch);
                }
            }
            query = escaped.toString();
        }
        return query;
    }

    /**
     * Decode the given query string.
     * 
     * @see URI#decode(String)
     * 
     * @param query
     * @return decoded query
     */
    public static String decodeQuery(String query) {
        if (query != null) {
            query = URI.decode(query);
        }

        return query;
    }
}
