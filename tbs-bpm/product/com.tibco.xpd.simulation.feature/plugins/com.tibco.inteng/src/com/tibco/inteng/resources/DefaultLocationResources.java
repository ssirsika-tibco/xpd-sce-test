/* 
 ** 
 **  MODULE:             $RCSfile: DefaultLocationResources.java $ 
 **                      $Revision: 1.2 $ 
 **                      $Date: 2005/04/29 15:51:34Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: DefaultLocationResources.java $ 
 **    Revision 1.2  2005/04/29 15:51:34Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.1  2004/08/02 16:19:23Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  28-Jul-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Resource Locator that check location, if location does not contain
 * valid URL, the location is prefixed with default location prefix
 * 
 * @author WojciechZ
 */
public class DefaultLocationResources extends ResourceLocatorAdapter {

    private final String defaultLocation;

    /**
     * Configure default location for this Resource Adapter
     * 
     * @param defaultLocation
     * @param parent
     */
    public DefaultLocationResources(String defaultLocation,
            ResourceLocator parent) {
        super(parent);
        this.defaultLocation = defaultLocation;
        // assert
        if (getParent() == null) {
            ResourceRTException e = new ResourceRTException("DefaultLocationResources requires 'parent' to not to be null");
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    /**
     * If URL is not valid, it is prefixed with default location
     * prefix
     * 
     * @param location
     * @return
     */
    private String modifyLocation(String location) {
        try {
            new URL(location);
        } catch (MalformedURLException e) {
            location = defaultLocation + location;
        }
        return location;
    }
    /**
     * @throws IOException
     * @see com.tibco.inteng.resources.ResourceLocator#getStream(java.lang.String)
     */
    public InputStream getStream(String location) throws IOException {
        
        return getParent().getStream(modifyLocation(location));
    }
    /**
     * @throws IOException
     * @see com.tibco.inteng.resources.ResourceLocator#getString(java.lang.String)
     */
    public String getString(String location) throws IOException {
        
        return getParent().getString(modifyLocation(location));
    }
}