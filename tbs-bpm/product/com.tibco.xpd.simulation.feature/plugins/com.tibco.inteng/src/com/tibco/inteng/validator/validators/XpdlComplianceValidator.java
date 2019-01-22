/* 
** 
**  MODULE:             $RCSfile: XpdlComplianceValidator.java $ 
**                      $Revision: 1.2 $ 
**                      $Date: 2004/06/17 08:41:22Z $ 
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
**    $Log: XpdlComplianceValidator.java $ 
**    Revision 1.2  2004/06/17 08:41:22Z  TimSt 
**    implement feature to permit test to return messages  
**    Revision 1.1  2004/06/15 11:57:34Z  TimSt 
**    Initial revision 
**    Revision 1.0  14-Jun-2004  TimSt 
**    Initial revision 
** 
*/
package com.tibco.inteng.validator.validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import org.wfmc.x2002.xpdl10.PackageDocument;
import org.wfmc.x2002.xpdl10.PackageDocument.Package;

import com.tibco.inteng.validator.App;
import com.tibco.inteng.validator.Validator;

/**
 * Validates Compliance with XPDL schema. 
 * 
 * @author TimSt
 */
public class XpdlComplianceValidator implements Validator {

    /** 
     * Logger for this class. 
     */
    private static Logger logger = Logger.getLogger(App.class);
    
    /** 
     * XBeans representation of the XPDL Package.  
     */
    private Package pack; 
    
    /**
     * @see com.tibco.inteng.validator.Validator#validate(InputStream)
     * @return <code>List</code> of <code>ValidatorMsg</code>s.  
     */
    public List validate(InputStream xpdlIS) throws IOException {
        List msgs = new Vector() ; 
        try {
            PackageDocument doc = PackageDocument.Factory.parse(xpdlIS);
            pack = doc.getPackage();
        } catch (XmlException e) {
            logger.error("XmlException", e);
            
            // TODO convert to Msg instance 
            throw new IOException("Cannot load file. (" + e.getMessage() + ", "
                    + e.getClass().getName() + ")");
        }
        return msgs = validate() ; 
    }

    /**
     * An empty implementation to permit simple subclassing by validators 
     * that wish to validate against an XBeans implementation of the 
     * XPDL process.
     *   
     * @return <code>List</code> of <code>ValidatorMsg</code>s, never null.  
     */
    public List validate() throws IOException {
        return new Vector() ; 
    }
    
    /**
     * @return Returns the XPDL package as an XBean.
     */
    protected Package getPackage() {
        return pack;
    }
}
