/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.parser.validator;

import java.util.HashMap;
import java.util.Map;

public class ErrorType {

    private String type;

    private Map<String, String> resolutionInformation;

    public ErrorType(String type, Map<String, String> resolutionInformation) {
        this.type = type;
        this.resolutionInformation = resolutionInformation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getResolutionInformation() {
        if (resolutionInformation == null) {
            resolutionInformation = new HashMap<String, String>();
        }
        return resolutionInformation;
    }

    public void setResolutionInformation(
            Map<String, String> resolutionInformation) {
        this.resolutionInformation = resolutionInformation;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        } else if (obj instanceof ErrorType) {

            ErrorType errorType = (ErrorType) obj;
            if (this.getType() == errorType.getType()) {
                return true;
            }
            return false;
        } else {
            return super.equals(obj);
        }
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        String toReturn = type;
        return toReturn;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        String str = toString();
        return str.hashCode();
    }
}
