/* 
 ** 
 **  MODULE:             $RCSfile: RulesApplication.java $ 
 **                      $Revision: 1.9 $ 
 **                      $Date: 2005/04/29 15:51:33Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: RulesApplication.java $ 
 **    Revision 1.9  2005/04/29 15:51:33Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.8  2005/04/01 13:01:49Z  KamleshU 
 **    Revision 1.7  2005/03/24 16:11:06Z  gbingham 
 **    Allowing process to continue when rules reponse fails validation - TEMP SOLUTION  
 **    Revision 1.6  2005/03/18 17:16:22Z  KamleshU 
 **    Added code to write the validation messages to the server log file 
 **    Revision 1.5  2005/03/01 20:03:48Z  KamleshU 
 **    Revision 1.4  2004/12/15 11:21:43Z  KamleshU 
 **    Revision 1.3  2004/12/02 11:42:40Z  KamleshU 
 **    Revision 1.2  2004/12/02 10:16:17Z  KamleshU 
 **    Changed the hierarchy of the Rules Application 
 **    Revision 1.1  2004/11/29 10:00:56Z  KamleshU 
 **    Initial revision 
 **    Revision 1.0  20-Nov-2004  KamleshU 
 **    Initial revision 
 ** 
 */
package com.tibco.ie.apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.tibco.ie.utils.RulesMgrUtil;
import com.tibco.inteng.ExtendedAttribute;
import com.tibco.intEng.FPXsltType;
import com.tibco.intEng.RuleType;
import com.tibco.intEng.RulesApplicationType;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.resources.ResourceLocator;
import com.tibco.inteng.xpdldata.SchemaTypedBase;
import com.tibco.inteng.xpdldata.SchemaTypedField;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * The RulesApplication is an automatic application which will be invoking jsr94 
 * rules implementation to execute a rule, this application expects that there can be 
 * 'n' number of input parameters for which there can be a xslt file specified to 
 * transform the data and then all the inout parameters are concatted to make the 
 * request data XML for the rules implementation.
 * 
 * The application expects that the last formal parameter of the application
 * will have an OUT mode where in the response from the jsr94 rules implementation,
 * will be copied to. 
 * 
 * If the application wants to capture the messages sent out from jsr94 rules 
 * implementation then it should have a formal parameter with 'Messages' or 'messages'
 * id with OUT mode. The messages coming from the jsr94 rules implementation are not 
 * segregated as INFO, WARNING or VIOLATION for the time being. 
 *  
 *  
 * @author KamleshU
 */
public abstract class RulesApplication extends AbstractApplication {

    private final static Logger logger = Logger.getLogger(RulesApplication.class);
    private final ResourceLocator locator;
    /**
     * Create Rules application based on given application description
     * 
     * @param locator
     */
    public RulesApplication(ResourceLocator locator) {
        this.locator = locator;
    }
    /* (non-Javadoc)
     * @see com.tibco.inteng.apps.XpdlAutomaticApplication#invoke(java.lang.String, 
     * com.tibco.inteng.apps.ExtendedAttribute[], com.tibco.inteng.xpdldata.XpdlData[])
     */
    /**
     * This method is invoked by the IntEng
     * 
     * @param applicationId, the id of the application invoked
     * @param extendedAttributes, the extended attributes that are defined for the
     * application
     * @param args, the formal parameters for the application 
     * @throws XpdlBusinessException
     */
    public void invoke(String applicationId,
            ExtendedAttribute[] extendedAttributes, XpdlData[] args)
            throws XpdlBusinessException {
        if (logger.isDebugEnabled()) {
            logger.debug("ENTER::invoke applicationid is " + applicationId);
        }
        RulesApplicationType rulesAppType = getRulesApplicationType(
                extendedAttributes, applicationId);
        RuleType ruleType = rulesAppType.getRule();
        ArrayList inArgs = new ArrayList();
        int messageElementIndex = -1;
        int outElementIndex = -1;
        boolean outArgsFound = false;
        for (int index = 0; index < args.length; index++) {
            if (args[index] instanceof SchemaTypedBase) {
                if (args[index].getName().equals("Messages")
                        || args[index].getName().equals("messages")) {
                    if (args[index].isReadonly()) {
                        throw new XpdlException(
                                "The messages/Messages parameter on index "
                                        + (index + 1) + " cannot have IN mode ");
                    }
                    messageElementIndex = index;
                    continue;
                }
            }
            if (!args[index].isReadonly()) {
                if (outArgsFound) {
                    throw new XpdlException("Only one OUT parameter"
                            + " should be specified for the rulesApplication");
                }
                outArgsFound = true;
                outElementIndex = index;
            } else {
                // adding the IN XpdlData in the ArrayList
                inArgs.add(args[index]);
            }
        }

        if (outElementIndex == -1 || inArgs.size() < 1) {
            throw new XpdlException(
                    "There should be atleast one 'IN' and one 'OUT' parameter for Rules application");
        }
        if (logger.isInfoEnabled()) {
            logger.info("Flags for " + applicationId
                    + ": has input parameters & one output parameter");
        }
        String ruleInputXML = "";
        // iterating over the input parameters and generating the input XML
        for (Iterator iter = inArgs.iterator(); iter.hasNext();) {
            XpdlData element = (XpdlData) iter.next();
            String location = isXsltSpecified(element, rulesAppType);
            String resultantXML;
            if (location != null) {
                resultantXML = applyXslt(element, location, applicationId);
            } else {
                resultantXML = element.getXml().xmlText();
            }
            if (resultantXML != null && resultantXML.length() > 0) {
                ruleInputXML += resultantXML;
            }
        }

        try {
            // adding the ruleInputXML            
            List requests = new ArrayList();
            HashMap map = new HashMap();
            map.put("data", ruleInputXML);
            requests.add(map);
            // invoking rule
            String ruleSetId = ruleType.getRuleSetId();
            String resourcePath = ruleType.getResourcePath();
            String fileName = ruleType.getFileName();
            if (ruleSetId == null || ruleSetId.trim().length() < 1
                    || resourcePath == null || resourcePath.trim().length() < 1
                    || fileName == null || fileName.trim().length() < 1) {
                XpdlException e = new XpdlException(
                        "Values for ruleSetId, ResourcePath, FileName have not "
                                + "been set for application = " + applicationId);
                logger.error(e.getMessage(), e);
                throw e;
            }
            ruleSetId = ruleSetId.trim();
            resourcePath = resourcePath.trim();
            fileName = fileName.trim();
            HashMap retVal = invokeRule(requests, ruleSetId, resourcePath,
                    fileName);
            String responseXML = (String) retVal.get("data");
            // applying the xslt specificed on the response (if any).
            String location = isXsltSpecified(args[outElementIndex],
                    rulesAppType);
            if (location != null) {
                responseXML = applyXslt(args[outElementIndex], location,
                        applicationId);
            }
            // converting the String back into XMLObject and setting the value back to the xpdl data.
            XmlObject xResult = XmlObject.Factory.parse(responseXML);
            if (xResult != null) {
                XmlCursor cur = xResult.newCursor();
                cur.toFirstChild();
                xResult = cur.getObject();
                cur.dispose();
                args[outElementIndex].setValue(xResult);
                if (!args[outElementIndex].validate()) {
                    logger
                            .error("The response from the rules manager does not match with the expected format");
                    //try{
                    //    XmlUtils.validateXML(args[args.length - 1].getXml());
                    //}catch(XpdlException xpdlex){
                    //    xpdlex.printStackTrace();
                    //    logger.error("Validation Message is "+xpdlex.getMessage());
                    //}
                    //throw new XpdlBusinessException(
                    //        "The response from the rules manager does not match with the expected format");
                }
                if (logger.isInfoEnabled()) {
                    logger.info("Result from invocation: "
                            + args[outElementIndex].getXml());
                }
            }
            // if messages element is present then, 
            if (messageElementIndex != -1) {
                setMessages(args[messageElementIndex], retVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XpdlBusinessException(
                    "Exception occurred while invoking rules " + e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("EXIT::invoke applicationid is " + applicationId);
        }
    }

    protected abstract HashMap invokeRule(List requests, String ruleSetId,
            String resourcePath, String fileName) throws XpdlBusinessException;

    /**
     * @param extendedAttributes
     * @param applicationId
     * @return RulesApplicationType
     */
    protected abstract RulesApplicationType getRulesApplicationType(
            ExtendedAttribute[] extendedAttributes, String applicationId);

    /**
     * This method will generate the XML from XpdlData
     * 
     * @param data
     * @param rulesApplicationType     
     * @return     
     */
    protected String isXsltSpecified(XpdlData data,
            RulesApplicationType rulesApplicationType) {
        if (logger.isDebugEnabled()) {
            logger.debug("ENTER::isXsltSpecified XpdlData " + data != null
                    ? data.getName()
                    : "");
        }
        String location = null;
        if (data instanceof SchemaTypedBase) {
            ((SchemaTypedBase) data).stripEmptyOptionalFields();
        }
        // finding if there is any xslt file mentioned for the formal paramter
        if (data instanceof SchemaTypedField) {
            SchemaTypedField field = (SchemaTypedField) data;
            FPXsltType[] fpArr = rulesApplicationType.getFPXsltArray();
            for (int i = 0; i < fpArr.length; i++) {
                String fpName = fpArr[i].getFPName();
                if (fpName == null) {
                    continue;
                }
                fpName = fpName.trim();
                if (fpName.equalsIgnoreCase(field.getName())) {
                    if (fpArr[i].getXsltLocation() != null) {
                        location = fpArr[i].getXsltLocation().trim();
                        break;
                    }
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("EXIT::isXsltSpecified, The value of location is "
                    + location);
        }
        return location;
    }

    /**
     * @param src
     * @param xslLocation
     * @param applicationId
     * @return     
     */
    protected String applyXslt(XpdlData src, String xslLocation,
            String applicationId) {
        
        if (logger.isDebugEnabled()) {
            logger.debug("ENTER::applyXslt XpdlData " + src != null ? src
                    .getName() : "");
        }
        
        String strResult = null;
        InputStream stream = null;
        StringReader input = null;
        StringWriter output = null;
        
        try {
            // getting the input xml on which xslt will be applied        
            input = new StringReader(src.getXml().xmlText());
            
            // opening stream
            stream = locator.getStream(xslLocation);
            
            output = new StringWriter();
            
            Source xmlSource = new StreamSource(input);
            
            // getting the xslt which will be applied
            Source xsltSource = new StreamSource(stream);
            
            // the result
            Result result = new StreamResult(output);
            TransformerFactory transFact = TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer(xsltSource);
            trans.transform(xmlSource, result);
            
            strResult = output.getBuffer().toString();
            
        } catch (TransformerException te) {
            XpdlException e1 = new XpdlException(
                    "Error during transformation on XsltApplication (ID: "
                            + applicationId + "): " + te.getMessage(), te);
            logger.error(e1.getMessage(), te);
            throw e1;
        }
        catch (IOException e) {
            XpdlException e1 = new XpdlException(
                    "IO error during transformation on XsltApplication (ID: "
                            + applicationId + "): " + e.getMessage(), e);
            logger.error(e1.getMessage(), e);
            throw e1;
        }
        finally {
            try { if (stream !=null) stream.close(); }
            catch (Exception e) {
                logger.error("Unable to close stream");
            }
            
            try { if (input != null) input.close(); } 
            catch (Exception e) {
                logger.error("Unable to close inputreader");
            }
            
            try { if (output != null) output.close(); }
            catch (Exception e) {
                logger.error("Unable to close outputreader");
            }
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug("EXIT::applyXslt");
        }
        
        return strResult;
    }
    /**
     * 
     * @param messages
     * @param map
     * @throws XpdlDataFormatException
     * @throws XmlException
     */
    protected void setMessages(XpdlData messages, HashMap map)
            throws XpdlDataFormatException, XmlException {

        if (logger.isDebugEnabled()) {
            logger.debug("ENTER::setMessages XpdlData " + messages != null
                    ? messages.getName()
                    : "");
        }
        if (map.get(RulesMgrUtil.INFO_MESSAGES) != null) {

        } else if (map.get(RulesMgrUtil.WARNING_MESSAGES) != null) {

        } else if (map.get(RulesMgrUtil.WARNING_MESSAGES) != null) {

        } else if (map.get(RulesMgrUtil.MESSAGES) != null) {
            XmlObject xResult = XmlObject.Factory.parse((String) map
                    .get(RulesMgrUtil.MESSAGES));
            if (xResult != null) {
                XmlCursor cur = xResult.newCursor();
                cur.toFirstChild();
                xResult = cur.getObject();
                cur.dispose();
                messages.setValue(xResult);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("EXIT::setMessages");
        }
    }
}