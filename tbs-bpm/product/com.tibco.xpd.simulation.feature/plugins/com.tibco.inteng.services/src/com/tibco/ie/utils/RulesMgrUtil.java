/* 
 ** 
 **  MODULE:             $RCSfile: RulesMgrUtil.java $ 
 **                      $Revision: 1.1 $ 
 **                      $Date: 2005/03/01 15:46:55Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2003 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **  Initial Revision 
 ** 
 */
package com.tibco.ie.utils;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;

import com.tibco.inteng.exceptions.XpdlBusinessException;
/**
 * This is the base class for Rules Manager Util classes
 * 
 * @author KamleshU 
 * 
 */
public class RulesMgrUtil {
    /**
     * This constant is used as a key to store Info Messages in the result HashMap
     * from RulesEngine, if any 
     */
    public static final String INFO_MESSAGES = "messagesINFO";
    /**
     * This constant is used as a key to store warning Messages in the result HashMap
     * from RulesEngine, if any 
     */
    public static final String WARNING_MESSAGES = "messagesWARNING";
    /**
     * This constant is used as a key to store violation Messages in the result HashMap
     * from RulesEngine, if any 
     */
    public static final String VIOLATION_MESSAGES = "messagesVIOLATION";
    /**
     * This constant is used as a key to store messages as the String in the result HashMap
     * from RulesEngine, if any 
     */
    public static final String MESSAGES = "messages";

    private static Logger logger = Logger.getLogger(RulesMgrUtil.class);    
    private List infoMessages;
    private List warningMessages;
    private List violationMessages;
    
    public static TransformerFactory tfactory = TransformerFactory
            .newInstance();

    public static final int STRIP_ATTR_AND_NS = 1001;
    public static final String XSL_STRIP_ATTR_AND_NS =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +    
        "   <xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
        "   <xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\"/>\n" +
        "   <xsl:template match=\"*\">\n" +
        "       <xsl:element name=\"{local-name()}\">\n" +
        "           <xsl:apply-templates/>\n" +
        "       </xsl:element>\n" +
        "   </xsl:template>\n" +
        "   </xsl:stylesheet>\n";

    public static final int RETIEVE_DATA_FROM_RESPONSE = 1002;
    public static final String XSL_RETIEVE_DATA_FROM_RESPONSE =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +    
        "   <xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
        "   <xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\"/>\n" +       
        "   <xsl:template match=\"/\">\n" + 
        "      <xsl:for-each select=\"/CorticonResponse/WorkDocuments/Context\">\n" + 
        "         <xsl:copy-of select=\"*\"/>\n" + 
        "      </xsl:for-each>\n" + 
        "   </xsl:template>\n" +
        "   </xsl:stylesheet>\n";

    /**
     * Get the messages XML from the CorticonResponse xml
     */
    public static final int GET_MESSAGES = 1003;
    public static final String XSL_GET_MESSAGES = 
        "<?xml version=\"1.0\"?>" + 
        "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" xmlns:cc=\"urn:Corticon\">" + 
        "<xsl:output method=\"xml\"/>" + 
        "    <xsl:template match=\"/\">" + 
        "        <xsl:for-each select=\"/cc:CorticonResponse\">" + 
        "            <xsl:copy-of select=\"cc:Messages\"/>" + 
        "        </xsl:for-each>" +     
        "    </xsl:template>" + 
        "</xsl:stylesheet>";

    public static HashMap xfrmerCache = new HashMap();

    /**
     * Gets a transformer, from the cache if possible
     * 
     * @param iXfrmerRef
     * @return
     * @throws XpdlBusinessException
     */
    public static Transformer getTransformer(int iXfrmerRef)
            throws XpdlBusinessException {
        Integer ref = new Integer(iXfrmerRef);
        Transformer xfrmer = (Transformer) xfrmerCache.get(ref);
        if (xfrmer != null) {
            return xfrmer;
        }
        switch (iXfrmerRef) {
            case STRIP_ATTR_AND_NS :
                xfrmer = RulesMgrUtil.createTransformer(XSL_STRIP_ATTR_AND_NS);
                break;
            case RETIEVE_DATA_FROM_RESPONSE :
                xfrmer = RulesMgrUtil
                        .createTransformer(XSL_RETIEVE_DATA_FROM_RESPONSE);
                break;
            case GET_MESSAGES :
                xfrmer = RulesMgrUtil.createTransformer(XSL_GET_MESSAGES);
                break;
            default :
                throw new XpdlBusinessException(
                        "Unknown transformer ref:" + iXfrmerRef);
        }
        xfrmerCache.put(ref, xfrmer); //cache the transformer
        return xfrmer;

    }

    /**
     * Populates the messages lists from the rules response
     * @param response
     */
    public void populateMessages(HashMap response) {
        infoMessages = (List) response.get(RulesMgrUtil.INFO_MESSAGES);
        warningMessages = (List) response.get(RulesMgrUtil.WARNING_MESSAGES);
        violationMessages = (List) response.get(RulesMgrUtil.VIOLATION_MESSAGES);
    }

    /**
     * Gets the info messages from the last invocation
     * @return non-null vector of type MessageType (JAXB class) objects
     */
    public List getInfoMessages() {
        return infoMessages == null ? new Vector() : infoMessages;
    }

    /**
     * Gets the violation messages from the last invocation
     * @return non-null vector of type MessageType (JAXB class) objects
     */
    public List getViolationMessages() {
        return violationMessages == null ? new Vector() : violationMessages;
    }

    /**
     * Gets the warning messages from the last invocation
     * @return non-null vector of type MessageType (JAXB class) objects
     */
    public List getWarningMessages() {
        return warningMessages == null ? new Vector() : warningMessages;
    }

    /**
     * Constructs a Transformer for the XSL passed in
     * 
     * @param xsl
     * @return Transformer
     * @throws XpdlBusinessException
     */
    public static Transformer createTransformer(String xsl)
            throws XpdlBusinessException {

        // Create a transformer for the stylesheet.            
        Transformer transformer;
        try {
            transformer = tfactory.newTransformer(new StreamSource(
                    new StringReader(xsl)));
            return transformer;
        } catch (TransformerConfigurationException e) {
            logger.error(e.getMessage(), e);
            XpdlBusinessException e2 = new XpdlBusinessException(
                    e.getMessage());
            e2.initCause(e);
            throw e2;
        }
    }

    /**
     * Runs a transform operation
     * 
     * @param transformer
     * @param xml
     * @return
     * @throws XpdlBusinessException
     */
    public static String transform(Transformer transformer, String xml)
            throws XpdlBusinessException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Transform the source XML...            
        try {
            transformer.transform(new StreamSource(new StringReader(xml)),
                    new StreamResult(out));
        } catch (TransformerException e) {
            logger.error(e.getMessage(), e);
            XpdlBusinessException e2 = new XpdlBusinessException(
                    e.getMessage());
            e2.initCause(e);
            throw e2;
        }
        String sXMLOut = out.toString();
        return sXMLOut;
    }

}