/* 
 ** 
 **  MODULE:             $RCSfile: XsltApplication.java $ 
 **                      $Revision: 1.7 $ 
 **                      $Date: 2005/05/12 09:38:33Z $ 
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
 **    $Log: XsltApplication.java $ 
 **    Revision 1.7  2005/05/12 09:38:33Z  wzurek 
 **    defect #21724 
 **    Revision 1.6  2005/04/29 15:51:33Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.5  2005/03/01 19:28:09Z  KamleshU 
 **    Changes made due to change in the structure of the extended attributes 
 **    Revision 1.4  2004/08/02 16:13:12Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.3  2004/07/21 15:52:16Z  WojciechZ 
 **    new feature: SubFlows 
 **    Revision 1.2  2004/06/17 08:43:22Z  WojciechZ 
 **    better docs 
 **    Revision 1.1  2004/06/16 15:10:22Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  16-Jun-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.ie.apps;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.resources.ResourceLocator;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * Simple Application that transform first argument into  seccond one using 
 * xls from location provided by extended attribute<br>
 * Dependences:
 * <li>has to have two arguments, one in, and one out</li>
 * <li>'xls' extended attribute, value should contains URL to xsl file</li>  
 * 
 * @author WojciechZ
 */
public class XsltApplication extends AbstractApplication
        implements
            AutomaticApplication {

    /** log4j */
    private final static Logger log = Logger.getLogger(XsltApplication.class);
    private final ResourceLocator locator;
    /**
     * Create XSLT application based on given application description
     * 
     * @param locator
     */
    public XsltApplication(ResourceLocator locator) {
        this.locator = locator;
    }
    /**
     * @see com.tibco.inteng.apps.XpdlAutomaticApplication#invoke(String, ExtendedAttribute[], XpdlData[])
     */
    public void invoke(String applicationId, ExtendedAttribute[] exts,
            XpdlData[] args) throws XpdlBusinessException {
        if (log.isDebugEnabled()) {
            log.debug("enter xsltApp - invoke");
        }

        if (args.length != 2 || args[1].isReadonly() || !args[0].isReadonly()) {
            XpdlException e = new XpdlException(
                    "'xsltApp' application requires exactly two parameters, one "
                            + "IN and one OUT (Application ID: "
                            + applicationId + ")");
            log.error(e);
            throw e;
        }

        XpdlData src = args[0];
        XpdlData out = args[1];

        String xslLocation = getExtendedAttributeValue(
        		ServicesConsts.XSLT_APP_NAME, exts);
        
        if (xslLocation == null) {
            XpdlException e = new XpdlException(
                    "'xsl' extended attribute is required by 'XSLTApplication' application (Application ID: "
                            + applicationId + ")");
            log.error(e);
            throw e;
        }
        
        StringReader input = null;
        InputStream stream = null;
        StringWriter output = null;
        
        try {
            // getting the input xml on which xslt will be applied  
            input = new StringReader(src.getXml().xmlText());
            
            // opening stream
            stream = locator.getStream(xslLocation.trim());
            
            output = new StringWriter();
            
            Source xmlSource = new StreamSource(input);
            Source xsltSource = new StreamSource(stream);
            Result result = new StreamResult(output);

            TransformerFactory transFact = TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer(xsltSource);
            trans.transform(xmlSource, result);

            out.setValue(output.getBuffer().toString());
            if (log.isDebugEnabled()) {
                log.debug("exit xsltApp - invoke");
            }
            
        } catch (Exception e) {
            XpdlException e1 = new XpdlException(
                    "Error during transformation on XsltApplication (ID: "
                            + applicationId + "): " + e.getMessage(), e);
            log.error(e1.getMessage(), e);
            throw e1;
        }
        finally {
            try { if (stream !=null) stream.close(); }
            catch (Exception e) {
                log.error("Unable to close stream");
            }
            
            try { if (input != null) input.close(); } 
            catch (Exception e) {
                log.error("Unable to close inputreader");
            }
            
            try { if (output != null) output.close(); }
            catch (Exception e) {
                log.error("Unable to close outputreader");
            }
        }
    }
}