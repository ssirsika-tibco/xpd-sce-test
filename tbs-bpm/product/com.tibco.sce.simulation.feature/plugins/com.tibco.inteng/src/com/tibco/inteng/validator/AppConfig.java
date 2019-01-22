/* 
** 
**  MODULE:             $RCSfile: AppConfig.java $ 
**                      $Revision: 1.2 $ 
**                      $Date: 2005/05/23 15:11:22Z $ 
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
**    $Log: AppConfig.java $ 
**    Revision 1.2  2005/05/23 15:11:22Z  pburton 
**    Added code to explicitly close streams. 
**    Revision 1.1  2004/06/15 11:57:31Z  TimSt 
**    Initial revision 
**    Revision 1.0  11-Jun-2004  TimSt 
**    Initial revision 
** 
*/
package com.tibco.inteng.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.wfmc.x2002.xpdl10.PackageDocument.Package;

/**
 * TODO Description of 'AppConfig' class
 * 
 * @author TimSt
 */
public class AppConfig {

    private Logger logger = Logger.getLogger(AppConfig.class) ; 
    /** 
     * The URL of an XPDL file to validate. 
     */
    private URL url ; 

    /** 
     * The filename of an XPDL file to validate. 
     */    
    private File file ;

    /** 
     * Holds a list of <code>Validator</code> instances to use when validating. 
     */
    private Vector validators = new Vector() ;

    /** 
     * XPDL package to validate
     */
    private Package pack; 
    
    /**
     * @return Returns the file.
     */
    public File getFile() {
        return file;
    }
    /**
     * @param file The file to set.
     */
    public void setFile(File file) {
        this.file = file;
    }
    /**
     * @return Returns the url.
     */
    public URL getUrl() {
        return url;
    }
    /**
     * @param url The url to set.
     */
    public void setUrl(URL url) {
        this.url = url;
    }
    public Iterator validatorsIterator() { 
        if (validators.size() <= 0) {
            initDefaultValidators() ; 
        }
        return validators.iterator() ; 
    }
    /**
     * @throws IOException
     */
    private void initDefaultValidators() {
        
        InputStream is = getClass().getResourceAsStream("/validators.properties");
        Properties props = new Properties() ;
        
        try {
            props.load(is) ;
        } catch (IOException e1) {
            System.err.println("Default configuration failed from resource "
                    + "named '/validators.properties'") ;
            return ; 
        }
        finally {
            try { if (is!=null) is.close(); }
            catch (Exception e) {
                logger.error("Error closing stream");
            }
        }
        
        for (Iterator it = props.values().iterator() ; it.hasNext() ;) { 
            String vClazz = (String) it.next() ;
            try {
                Class clazz = getClass().getClassLoader().loadClass(vClazz) ;
                validators.add(clazz.newInstance()) ;
                logger.warn("Added validator: " + vClazz) ; 
            } catch (ClassNotFoundException e) {
                logger.error("Unable to find: " + vClazz) ; 
            } catch (InstantiationException e) {
                logger.error("Found but cannot instantiate: " + vClazz) ;
            } catch (IllegalAccessException e) {
                logger.error("Found but not authorised to instantiate: " 
                        + vClazz) ;
            }  
        }
    }
    
    /**
     * @return Input Stream holding XPDL definition.  
     */
    public InputStream getInputStream() throws IOException {
        InputStream source ;
        if (getUrl() != null ) {
            logger.warn("Validating " + getUrl()) ; 
            source = getUrl().openStream() ; 
        } else { 
            logger.warn("Validating " + getFile()) ; 
            source = new FileInputStream(getFile());  
        }
        return source;
    }
    /**
     * Add a new validator to the application. 
     * @param validator
     */
    public void addValidator(Validator validator) {
        validators.add(validator) ; 
    }
}
