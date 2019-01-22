package com.tibco.inteng;

import java.util.Map;

public interface ExtendedAttributeProvider {
	/**
     * Returns a Map, containing key as the Name and the value as an instance 
     * of ExtendedAttribute element.
     * 
     * @return Map
     */
    public abstract Map getExtendedAttributes();
}
