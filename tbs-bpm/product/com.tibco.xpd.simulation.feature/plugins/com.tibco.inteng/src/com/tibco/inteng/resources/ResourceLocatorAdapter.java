/* 
 ** 
 **  MODULE:             $RCSfile: ResourceLocatorAdapter.java $ 
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
 **    $Log: ResourceLocatorAdapter.java $ 
 **    Revision 1.2  2005/04/29 15:51:34Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.1  2004/08/02 16:19:25Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  28-Jul-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * Base implementation of Resource Locator, 
 * contains two convinient methods to get Sting form InputStream 
 * and vice versa
 * 
 * @author WojciechZ
 */
public abstract class ResourceLocatorAdapter implements ResourceLocator {

    /** lo4j */
    protected Logger log = Logger.getLogger(this.getClass());
    /** size of buffer when fetching InputStream to String */
    private static final int BUFFER_SIZE = 2048;
    /** parent resource locator */
    private final ResourceLocator parent;

    /**
     * The constructor
     * 
     * @param parent - parent of the resource locator, depends on 
     * current implementation if it may be null
     */
    public ResourceLocatorAdapter(ResourceLocator parent) {
        this.parent = parent;
    }

    /**
     * @return parent ot this ResourceLocator, in some cases may be null
     */
    public ResourceLocator getParent() {
        return parent;
    }

    /**
     * Read String from provided InputString
     * helper method
     * 
     * @param input - input stream to read
     * @return contetnt of given input string in string
     * @throws IOException - on IO problem
     */
    protected String getStringFromStream(InputStream input) throws IOException {
        
        StringBuffer sb = new StringBuffer();
        char[] buffer = new char[BUFFER_SIZE];
        int len;
        
        InputStreamReader reader = new InputStreamReader(input);
        
        try {
            while ((len = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, len);
            }
        }
        finally {
            reader.close();
        }
        
        return sb.toString();
        
    }
    /**
     * Returns input stram from provided Stirng
     * helper method
     * 
     * @param input - string to wrap into input stream
     * @return InputStream
     */
    protected InputStream getStreamFromString(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

}