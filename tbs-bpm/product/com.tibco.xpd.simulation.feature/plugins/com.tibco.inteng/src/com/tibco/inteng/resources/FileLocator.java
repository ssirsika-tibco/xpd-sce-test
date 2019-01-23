/* 
 ** 
 **  MODULE:             $RCSfile: FileLocator.java $ 
 **                      $Revision: 1.7 $ 
 **                      $Date: 2005/05/22 13:56:25Z $ 
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
 **    $Log: FileLocator.java $ 
 **    Revision 1.7  2005/05/22 13:56:25Z  tstephen 
 **    changes related to refactoring of default bundle in inteng-web (and test for it)  
 **    Revision 1.6  2005/04/29 15:51:34Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.5  2005/03/18 13:54:24Z  tstephen 
 **    added logging and corrected some spellings  
 **    Revision 1.4  2004/11/03 19:24:28Z  KamleshU 
 **    Revision 1.3  2004/11/03 10:05:12Z  KamleshU 
 **    Added a check for xsd files in FileLocator 
 **    Revision 1.2  2004/08/09 08:52:35Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.1  2004/08/02 16:19:24Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  28-Jul-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * FileLocator, checks if location is a existing file, 
 * if so, return stream or string form this file.
 * When location is not an existing file, it delegate
 * location to parent locator
 * 
 * @author WojciechZ
 */
public class FileLocator extends ResourceLocatorAdapter {

    String locationPrefix;
    /**
     * @param parent     
     */
    public FileLocator(ResourceLocator parent) {
        super(parent);
    }

    /**
     * @throws IOException
     * @see com.tibco.inteng.resources.ResourceLocator#getStream(java.lang.String)
     */
    public InputStream getStream(String location) throws IOException {
        
        log.info("enter: getStream: " + location) ; 
        File fl = new File(fixLocation(location));
        log.debug("Seeking file: " + fl.getPath()) ; 
        
        if (fl.exists()) {
            log.info("exit: getStream, found directly");
            return new FileInputStream(fl);
        }
        
        if (locationPrefix != null && locationPrefix.length() > 0) {
            fl = new File(fixLocation(locationPrefix + location));
            log.debug("Seeking file: " + fl.getPath()) ; 
            if (fl.exists()) {
                log.info("exit: getStream, found in default location");
                return new FileInputStream(fl);
            }
        }  
        
        if (getParent() == null) {
            return null ; 
        } else { 
            log.info("exit: getStream, delegate to parent resource loader"); 
            return getParent().getStream(location); 
        }
    }

    /**
     * If file extension is not xpdl or xsd, append .xpdl.  
     * @param location
     * @return modified resource location. 
     */
    private String fixLocation(String location) {
        // TODO: TS 22 May 2005 This seems like an opportunity for a bug
//        if (!location.endsWith(".xpdl") && !location.endsWith(".xsd")) {
//            location += ".xpdl";
//        }
        return location;
    }

    /**
     * @see com.tibco.inteng.resources.ResourceLocator#getString(java.lang.String)
     */
    public String getString(String location) throws IOException {
        
        File fl = new File(fixLocation(location));
        String str;
        InputStream stream = getStream(location);
        
        try {
            if (fl.exists()) {
                str = getStringFromStream(stream);
            }
            else {
                str = getParent().getString(location);
            }
        }
        finally {
            stream.close();
        }
        
        return str;
    }

    /**
     * @param prefix
     */
    public void setLocationPrefix(String prefix) {
        String fileSeparator = System.getProperty("file.separator") ; 
        if (!prefix.endsWith(fileSeparator)) { 
            prefix += fileSeparator ; 
        }
        this.locationPrefix = prefix;
    }
}