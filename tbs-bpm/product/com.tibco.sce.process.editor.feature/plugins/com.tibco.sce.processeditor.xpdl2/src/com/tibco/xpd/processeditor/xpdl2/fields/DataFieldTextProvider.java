/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.fields;

/**
 * This should be implemented by contributed Data Fields to allow them to
 * display custom text in the mapping tree.
 * 
 * @author nwilson
 */
public interface DataFieldTextProvider {

    String getName();
    
    String getId();
}
