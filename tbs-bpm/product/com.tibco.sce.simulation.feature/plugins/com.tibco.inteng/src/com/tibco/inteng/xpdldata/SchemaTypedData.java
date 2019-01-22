/* 
 ** 
 **  MODULE:             $RCSfile: SchemaTypedData.java $ 
 **                      $Revision: 1.5 $ 
 **                      $Date: 2004/10/20 08:44:52Z $ 
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
 **    $Log: SchemaTypedData.java $ 
 **    Revision 1.5  2004/10/20 08:44:52Z  KamleshU 
 **    Changes made for making the restricted schema work 
 **    Revision 1.4  2004/09/16 15:35:05Z  WojciechZ 
 **    fixed memory leak with XML Cursor 
 **    Revision 1.3  2004/08/11 09:55:47Z  WojciechZ 
 **    Revision 1.2  2004/06/21 08:30:15Z  WojciechZ 
 **    changed root element type to SchemaTypedField 
 **    Revision 1.1  2004/04/15 07:56:49Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  07-Apr-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.xpdldata;

import java.math.BigInteger;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

/**
 * Part of schema typed xpdl data
 * 
 * @author WojciechZ
 */
public class SchemaTypedData extends SchemaTypedBase {

    /**
     * @param root xpdl data, schema tuyped element with root of the xml document
     * @param data xml bean 
     */
    SchemaTypedData(SchemaTypedField root, XmlObject data) {
        super(root, data);
    }
    SchemaTypedData(SchemaTypedField root, XmlObject[] data) {
        super(root, data);
    }

    /**
     * @see com.tibco.inteng.xpdldata.SchemaTypedBase#getName()
     */
    public String getName() {
        StringBuffer sb = new StringBuffer();
        XmlObject obj = getData();
        XmlCursor cursor = obj.newCursor();
        QName subName = cursor.getName();
        int position;
        // count position of the tag
        position = 0;
        while (cursor.toPrevSibling()) {
            if (subName.equals(cursor.getName())) {
                position++;
            }
        }
        while (cursor.toParent()) {
            // check it this is an array property
            SchemaProperty prop = cursor.getObject().schemaType()
                    .getElementProperty(subName);
            if (prop != null
                    && (prop.getMaxOccurs() == null || prop.getMaxOccurs()
                            .compareTo(BigInteger.ONE) > 0)) {
                // array property
                sb.insert(0, subName.getLocalPart() + "{" + position + "}/");
            } else {
                // regular property
                sb.insert(0, subName.getLocalPart() + "/");
            }
            subName = cursor.getName();
            // count position of the tag
            position = 0;
            while (cursor.toPrevSibling()) {
                if (subName.equals(cursor.getName())) {
                    position++;
                }
            }
        }
        cursor.dispose();
        sb.deleteCharAt(sb.length() - 1);
        int ind = sb.indexOf("/");
        if(ind!=-1)
        {
            sb.replace(0,ind,getRoot().getName());
        } else {
            // should never happen
            throw new IllegalStateException();
        }
        return sb.toString();
    }
}