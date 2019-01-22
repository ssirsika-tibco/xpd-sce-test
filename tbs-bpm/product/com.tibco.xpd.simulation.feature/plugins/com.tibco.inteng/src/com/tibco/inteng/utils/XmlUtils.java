/* 
 ** 
 **  MODULE:             $RCSfile: XmlUtils.java $ 
 **                      $Revision: 1.6 $ 
 **                      $Date: 2005/06/06 08:12:44Z $ 
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
 **    $Log: XmlUtils.java $ 
 **    Revision 1.6  2005/06/06 08:12:44Z  jarciuch 
 **    CDATA section was used for queues values to escape special characters. 
 **    Revision 1.5  2005/03/01 19:29:04Z  KamleshU 
 **    Added helper method to validate the xml 
 **    Revision 1.4  2004/07/20 15:46:38Z  WojciechZ 
 **    added &gt; and &lt; entities to string decoder 
 **    Revision 1.3  2004/06/22 14:18:21Z  WojciechZ 
 **    added decode xml entities method 
 **    Revision 1.2  2004/04/08 16:02:16Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 ** 
 */
package com.tibco.inteng.utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import com.tibco.inteng.exceptions.XpdlException;

/**
 * Set of hepers form xml
 * 
 * @author WojciechZ
 */
public final class XmlUtils {
    
    private static int MIN_ASCII_CHAR_NUM = 33;
    
    private static int MAX_ASCII_CHAR_NUM = 127;
    
    /** log4j */
    private static Logger log = Logger.getLogger(XmlUtils.class);
    /**
     * private contructor
     */
    private XmlUtils() {
        // empty
    }
    /**
     * remove top level '&lt;xml-fragment&gt;'
     * 
     * @param xmlFragment text to process
     * @return text without xml-fragment
     */
    public static String getInnerText(String xmlFragment) {
        if (log.isInfoEnabled()) {
            log.info("enter/: getInnerText");
        }
        String result;
        if (xmlFragment.startsWith("<xml-fragment")) {
            int s = xmlFragment.indexOf('>');
            xmlFragment = xmlFragment.substring(s + 1);
            s = xmlFragment.lastIndexOf('<');
            if (s < 0) {
                // empty string, for: <xml-fragment/>
                result = "";
            } else {
                result = xmlFragment.substring(0, s);
            }
        } else {
            result = xmlFragment;
        }
        return result;
    }
    /**
     * Decode xml '&amp;' entity
     * 
     * @param text text to decode
     * @return text with decoded entities
     */
    public static String decodeXmlText(String text) {
        text = text.replaceAll("&amp;", "&");
        text = text.replaceAll("&lt;", "<");
        text = text.replaceAll("&gt;", ">");
        return text;
    }

    /**
     * 
     * @param xmlobject
     */
    public static void validateXML(XmlObject xmlobject) {

        if (log.isDebugEnabled()) {
            log.debug("enter:validateXML");
        }
        // Create an XmlOptions instance and set the error listener.
        XmlOptions validateOptions = new XmlOptions();
        ArrayList errorList = new ArrayList();
        validateOptions.setErrorListener(errorList);

        // Validate the XML.
        boolean isValid = xmlobject.validate(validateOptions);

        // If the XML isn't valid, loop through the listener's contents,
        // building validation messages.
        if (!isValid) {
            String validationMessage = "";
            for (int i = 0; i < errorList.size(); i++) {
                XmlError error = (XmlError) errorList.get(i);

                System.out.println("\n");
                validationMessage += ("Message: " + error.getMessage() + "\n")
                        + "Location of invalid XML: "
                        + error.getCursorLocation().xmlText() + "\n";
            }
            log.error("validateXML() : " + validationMessage);
            throw new XpdlException("XML is not valid : " + validationMessage);
        }
        if (log.isDebugEnabled()) {
            log.debug("exit:validateXML");
        }
    }

    /**
     * Encloses string in xml CDATA section.
     * 
     * @param s string to be enclosed
     * @return enclosed in CDATA section string or empty CDATA section if s is
     * null.
     */
    public static String encloseInCDATASection(String s) {
        if (s == null) {
            return "<![CDATA[]]>";
        }
        return "<![CDATA[" + s + "]]>";
    }
    
    /**
     * Some standard java methods don't support non-ascii charactors
     * and so this function is used to strip them out of a String so
     * we can use it somewhere if so desired.
     * @param text
     * @return
     */
    public static String stripNonAscText(String text){
        String nonAsciiText = text;
        for (int i=0;i<nonAsciiText.length();i++){
            char tempChar = nonAsciiText.charAt(i);
            if (tempChar < MIN_ASCII_CHAR_NUM || tempChar > MAX_ASCII_CHAR_NUM){
                nonAsciiText = nonAsciiText.replace(tempChar, ' ');
            }
        }
        return nonAsciiText;
    }
    

}