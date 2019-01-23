/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Class for validating REST service descriptor COntext and Resource paths. (Sid
 * XPD-7755)
 * 
 * @author aallway
 * @since 17 Aug 2015
 */
public class RestServicePathValidator {
    private static final String URL_PROTOCOL_PATTERN = "[a-z,A-z,0-9]*://.*"; //$NON-NLS-1$

    /**
     * Validator for context path in RSD service definition.
     * 
     * @param service
     */
    public RestPathIssue validate(Service service, String contextPath) {
        if (contextPath != null) {
            if (contextPath.length() > 0 && !isValidPathSegment(contextPath)) {
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_INVALID_URL_CHARACTERS,
                        Messages.RestServicePathValidator_invalidContextPath_message);
            }
            /*
             * Sid XPD-7755. Check against things that look like user has pasted
             * URL absolute path into path protocol spec (http: etc) by checking
             * for "!<alphanum>://<any>"
             */
            else if (contextPath.matches(URL_PROTOCOL_PATTERN)) {
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_URL_PROTOCOL,
                        Messages.RestServicePathValidator_absoluteContextPath_message);
            }
            /*
             * Sid XPD-7755. Check against that look like the user has pasted
             * URL absolute path containing query parameters.
             */
            else if (contextPath.contains("?")) { //$NON-NLS-1$
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_URL_QUERY,
                        Messages.RestServicePathValidator_contextPathContainsQuery_message);
            }
            /*
             * Sid XPD-7755. Check against use of URL encoded sequences in path,
             * user should not add these as the URL will be encoded at runtime.
             */
            else if (containsEncoded(contextPath)) {
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_URL_ENCODING,
                        Messages.RestServicePathValidator_contextPathContainsEncoded_message);
            }
        }

        return new RestPathIssue(RestPathIssueType.OK, "OK"); //$NON-NLS-1$
    }

    /**
     * Validator for context path in RSD service definition.
     * 
     * @param resource
     */
    public RestPathIssue validate(Resource resource, String resourcePath) {
        if (resourcePath != null) {
            Set<String> paramNames = getParamNamesFromPath(resourcePath);

            if (paramNames == null) {
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_UNMATCHED_PARAMS,
                        Messages.RestServicePathValidator_mismatchedTemplateBraces_message);

            }
            /*
             * Sid XPD-7755. Check against things that look like user has pasted
             * URL absolute path into path protocol spec (http: etc) by checking
             * for "!<alphanum>://<any>"
             */
            else if (resourcePath.matches(URL_PROTOCOL_PATTERN)) {
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_URL_PROTOCOL,
                        Messages.RestServicePathValidator_absoluteResourcePath_message);
            }
            /*
             * Sid XPD-7755. Check against things that look like the user has
             * pasted URL absolute path containing query parameters.
             */
            else if (resourcePath.contains("?")) { //$NON-NLS-1$
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_URL_QUERY,
                        Messages.RestServicePathValidator_resourcePathContainsQuery_message);
            }
            /*
             * Sid XPD-7755. Check against use of URL encoded sequences in path,
             * user should not add these as the URL will be encoded at runtime.
             */
            else if (containsEncoded(resourcePath)) {
                return new RestPathIssue(
                        RestPathIssueType.CONTAINS_URL_ENCODING,
                        Messages.RestServicePathValidator_resourcePathContainsEncoded_message);
            }

            RestPathIssue ret =
                    validateTemplateParameters((Service) resource.eContainer(),
                            resource,
                            paramNames != null ? paramNames
                                    : new HashSet<String>());

            if (RestPathIssueType.OK.equals(ret)) {
                if (!isValidPathSegment(resourcePath)) {
                    ret =
                            new RestPathIssue(
                                    RestPathIssueType.CONTAINS_INVALID_URL_CHARACTERS,
                                    Messages.RestServicePathValidator_invalidResourcePath_message);

                }
            }

            return ret;

        }

        return new RestPathIssue(RestPathIssueType.OK, "OK"); //$NON-NLS-1$

    }

    /**
     * Validate that the specified path parameters are all valid
     * 
     * @param eContainer
     * @param resource
     * @param set
     */
    private RestPathIssue validateTemplateParameters(Service eContainer,
            Resource resource, Set<String> paramNames) {
        Set<String> available = new HashSet<>();
        for (Parameter parameter : resource.getParameters()) {
            String name = parameter.getName();
            available.add(name);
        }

        StringBuilder invalidParams = new StringBuilder(""); //$NON-NLS-1$
        boolean invalid = false;

        for (String templateName : paramNames) {
            if (!available.contains(templateName)) {
                invalid = true;

                if (invalidParams.length() > 0) {
                    invalidParams.append(", "); //$NON-NLS-1$
                }
                invalidParams.append("'" + templateName + "'"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }

        if (invalid) {
            return new RestPathIssue(
                    RestPathIssueType.INVALID_PARAMETER_REFERENCE,
                    String.format(Messages.RestServicePathValidator_invalidPathParameter_message,
                            invalidParams.toString()));
        }

        return new RestPathIssue(RestPathIssueType.OK, "OK"); //$NON-NLS-1$

    }

    /**
     * @param template
     * 
     * @return The parameter names used in the path or <code>null</code> if
     *         there are mismatched { } sequences.
     */
    public Set<String> getParamNamesFromPath(String template) {
        Set<String> paramNames = new HashSet<>();
        if (template != null) {
            StringBuilder remaining = new StringBuilder();
            int location = 0;
            boolean mismatch = false;
            while (location != -1) {
                int open = template.indexOf('{', location);
                int nextOpen = template.indexOf('{', open + 1);
                int close = template.indexOf('}', location);
                if (open == -1 && close == -1) {
                    location = -1;
                } else if (open == -1 || close == -1 || open > close
                        || (nextOpen != -1 && nextOpen < close)) {
                    mismatch = true;
                    location = -1;
                } else {
                    paramNames.add(template.substring(open + 1, close));
                    remaining.append(template.substring(location, open));
                    location = close + 1;
                }
            }

            if (mismatch) {
                return null;
            }
        }
        return paramNames;
    }

    /**
     * @param path
     * @return <code>true</code> if the given string contains encoded
     *         characters.
     */
    private boolean containsEncoded(String path) {

        /*
         * This should be relatively easy to ascertain. If we decode the strign
         * and check whether the outcome is different from the original then the
         * original has encoded characters.
         */
        if (path != null) {
            String decoded = URI.decode(path);

            if (!path.equals(decoded)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param cp
     *            The context path to check.
     * @return true if it is valid.
     */
    private boolean isValidPathSegment(String cp) {
        boolean valid = true;
        try {
            new URL("http://a/" + cp); //$NON-NLS-1$
        } catch (MalformedURLException e) {
            valid = false;
        }
        return valid;
    }

    /**
     * Enum describing the result of the validation of an RSD file context /
     * resource path.
     * 
     * @author aallway
     * @since 17 Aug 2015
     */
    public enum RestPathIssueType {
        OK,
        /** The context/resource path contains invalid URL path characters */
        CONTAINS_INVALID_URL_CHARACTERS,
        /** The resource path contains unmatched brackets. */
        CONTAINS_UNMATCHED_PARAMS,
        /**
         * Invalid parameter reference '%1$s' in resource path
         */
        INVALID_PARAMETER_REFERENCE,
        /**
         * Unexpected URL protocol spec (e.g "http://" etc:) in context/resource
         * path. Note that the base URL (e.g. http://xyz) will be provided by
         * the late-bound shared resource URL.
         */
        CONTAINS_URL_PROTOCOL,
        /**
         * Unexpected ? character in context/resource path. Note that the URL
         * query parameters (e.g. ...?myval=Yes) will be automatically added for
         * the query parameters in the method definition.
         */
        CONTAINS_URL_QUERY,
        /**
         * URL encoding (%%20 etc) should not be used in the context/resource
         * path. The context path will be automatically encoded at runtime.
         */
        CONTAINS_URL_ENCODING
    }

    /**
     * Small data class for the return of {@link RestPathIssueType} and an
     * accompanying message
     */
    public class RestPathIssue {
        private RestPathIssueType type;

        private String message;

        /**
         * @param type
         * @param message
         */
        public RestPathIssue(RestPathIssueType type, String message) {
            super();
            this.type = type;
            this.message = message;
        }

        /**
         * @return the type
         */
        public RestPathIssueType getType() {
            return type;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }
    }

}
