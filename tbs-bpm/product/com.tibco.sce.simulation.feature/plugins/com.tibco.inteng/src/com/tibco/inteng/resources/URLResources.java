/* 
 ** 
 **  MODULE:             $RCSfile: URLResources.java $ 
 **                      $Revision: 1.3 $ 
 **                      $Date: 2005/04/29 15:51:39Z $ 
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
 **    $Log: URLResources.java $ 
 **    Revision 1.3  2005/04/29 15:51:39Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.2  2004/11/08 16:54:35Z  Timst 
 **    added additional logging 
 **    Revision 1.1  2004/08/02 16:19:26Z  WojciechZ 
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
 * Basic resource locator, locate resource using URL.openString() method
 * location should be a valid URL
 * 
 * @author WojciechZ
 */
public class URLResources extends ResourceLocatorAdapter {
    /**
     * Create ResourceAdapter with no parent
     */
    public URLResources() {
        super(null);
    }
    /**
     * Open stream from URL
     * 
     * @param location - should be valid URL
     * @throws IOException
     * @see com.tibco.inteng.resources.ResourceLocator#getStream(java.lang.String)
     */
    public InputStream getStream(String location) throws IOException {
        
        try {
            URL url = new URL(location);
            log.warn("Seeking data from URL: " + url) ;
            return url.openStream();
        } 
        catch (MalformedURLException e) {
            throw new IOException(e.getMessage());
        }
        
    }

    /**
     * Open stream from URL, fetch content to String and return string
     * 
     * @param location - should be valid URL
     * @throws IOException
     * @see com.tibco.inteng.resources.ResourceLocator#getString(java.lang.String)
     */
    public String getString(String location) throws IOException {
        
        String str;
        InputStream stream = getStream(location);
        
        try {
            str = getStringFromStream(stream);
        }
        finally {
            stream.close();
        }
        
        return str;
    }
}