/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import java.util.HashMap;
import java.util.Map;

/**
 * Request object used to provide setter methods to the Javascript context.
 * 
 * @author nwilson
 * @since 26 May 2015
 */
public class RestRequest {
    private Map<String, String> headers = new HashMap<>();

    private String data;

    private String url;

    private String method;

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

}