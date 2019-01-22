/* 
 ** 
 **  MODULE:             $RCSfile: UrlServerApplication.java $ 
 **                      $Revision: 1.4 $ 
 **                      $Date: 2005/05/22 13:55:45Z $ 
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
 **    $Log: UrlServerApplication.java $ 
 **    Revision 1.4  2005/05/22 13:55:45Z  tstephen 
 **    changes related to refactoring of default bundle in inteng-web (and test for it)  
 **    Revision 1.3  2005/03/01 19:28:01Z  KamleshU 
 **    Changes made due to change in the structure of the extended attributes 
 **    Revision 1.2  2004/12/06 12:53:17Z  TimST 
 **    javadoc only  
 **    Revision 1.1  2004/11/10 09:24:36Z  Timst 
 **    Initial revision 
 */
package com.tibco.ie.apps;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.DelegateException;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.resources.URLResources;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * Serve an URL back to the process.  
 * 
 * <p>Required extended attributes:
 * <ul><li><code>implementation</code> - must be <code>'urlServer'</code>
 * <li><code>url</code> - URL to retrieve. 
 * </ul></p>
 * 
 * @author Tim 
 */
public class UrlServerApplication extends AbstractApplication
        implements
            AutomaticApplication {

    /** log4j */
    private final static Logger log = Logger.getLogger(UrlServerApplication.class);
    /**
     * Default constructor. 
     */
    public UrlServerApplication() {
    }

    /**
     * @see com.tibco.inteng.apps.XpdlAutomaticApplication#invoke(String, ExtendedAttribute[], XpdlData[])
     */
    public void invoke(String applicationId, ExtendedAttribute[] exts,
            XpdlData[] args) throws XpdlBusinessException {
        log.info("enter: invoke");

        String url = getExtendedAttributeValue(ServicesConsts.URL_APP_NAME, exts);
        try {
            if (url == null) {
                XpdlException e = new XpdlException(
                        "The value attribute should have the url specified in the URLServerApplication "
                                + "for applicationId= " + applicationId);
                log.error(e.getMessage(), e);
                throw e;
            }
            log.info("Seeking data from URL: " + url);
            String stuff = new URLResources().getString(
                    url.trim());
            log.info("Url yielded: " + stuff);

            args[args.length - 1].setValue(stuff);
            if (log.isInfoEnabled()) {
                log.info("Result from invocation: "
                        + args[args.length - 1].getXml());
            }

        } catch (IOException e) {
            String msg = "IO Exception in " + "application (ID: "
                    + applicationId + "): " + e.getMessage();
            log.warn(msg);
            DelegateException e1 = new DelegateException(msg, e);
            log.error(msg, e);
            throw e1;
        } catch (XpdlDataFormatException e) {
            String msg = "Data format exception in " + "application (ID: "
                    + applicationId + "): " + e.getMessage();
            log.warn(msg);
            DelegateException e1 = new DelegateException(msg, e);
            log.error(msg, e);
            throw e1;
        }

        log.info("exit invoke");
    }
}