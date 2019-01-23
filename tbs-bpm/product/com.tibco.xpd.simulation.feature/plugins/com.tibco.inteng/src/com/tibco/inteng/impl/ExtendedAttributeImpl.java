/* 
 ** 
 **  MODULE:             $RCSfile: ExtendedAttributeImpl.java $ 
 **                      $Revision: 1.2 $ 
 **                      $Date: 2005/03/01 19:26:57Z $ 
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
 **    $Log: ExtendedAttributeImpl.java $ 
 **    Revision 1.2  2005/03/01 19:26:57Z  KamleshU 
 **    Exposed the root object of the extended attribute 
 **    Revision 1.1  2004/08/02 16:21:14Z  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.impl;

import org.apache.xmlbeans.XmlCursor;
import org.wfmc.x2002.xpdl10.ExtendedAttributeDocument;

import com.tibco.inteng.ExtendedAttribute;

/**
 * TODO Description of 'ExtendedAttributeImpl' class
 * 
 * @author WojciechZ
 */
public class ExtendedAttributeImpl implements ExtendedAttribute {

    private final ExtendedAttributeDocument.ExtendedAttribute extAttribDef;
    /**
     * @param extAttribDef
     */
    public ExtendedAttributeImpl(
            ExtendedAttributeDocument.ExtendedAttribute extAttribDef) {
        this.extAttribDef = extAttribDef;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.impl.ExtendedAttribute#getContent()
	 */
    public String getContent() {
        XmlCursor cur = extAttribDef.newCursor();
        String result = cur.getTextValue();
        cur.dispose();
        return result;   	
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.impl.ExtendedAttribute#getName()
	 */
    public String getName() {
        return extAttribDef.getName();
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.impl.ExtendedAttribute#getValue()
	 */
    public String getValue() {
        return extAttribDef.getValue();
    }
    /**
     * @return Returns the xmlbeans definition of the ExtendedAttributeImpl
     */
    public String getXmlString() {
        return extAttribDef.toString();
    }
}