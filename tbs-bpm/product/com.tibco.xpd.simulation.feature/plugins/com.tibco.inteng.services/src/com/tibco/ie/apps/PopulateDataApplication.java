/* 
 ** 
 **  MODULE:             $RCSfile: PopulateDataApplication.java $ 
 **                      $Revision: 1.11 $ 
 **                      $Date: 2005/05/31 16:28:44Z $ 
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
 **    $Log: PopulateDataApplication.java $ 
 **    Revision 1.11  2005/05/31 16:28:44Z  wzurek 
 **    removed old doc 
 **    Revision 1.10  2005/04/01 13:02:07Z  KamleshU 
 **    Revision 1.9  2005/03/07 14:53:31Z  KamleshU 
 **    Revision 1.8  2005/03/01 20:03:38Z  KamleshU 
 **    Revision 1.7  2005/03/01 19:27:10Z  KamleshU 
 **    Changes made due to change in the structure of the extended attributes 
 **    Revision 1.6  2004/08/03 11:51:47Z  WojciechZ 
 **    Revision 1.5  2004/08/02 16:13:10Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.4  2004/07/21 15:52:15Z  WojciechZ 
 **    new feature: SubFlows 
 **    Revision 1.3  2004/06/22 14:19:22Z  WojciechZ 
 **    added 'forceCopy' method of coping 
 **    added support for many formal parameters 
 **    removed default copuAll behaviour 
 **    Revision 1.2  2004/06/10 16:29:37Z  WojciechZ 
 **    Revision 1.1  2004/06/10 09:04:54Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  10-Jun-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.ie.apps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.tibco.intEng.CopyType;
import com.tibco.intEng.InitializeType;
import com.tibco.intEng.XMLMappingApplicationType;
import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.xpdldata.XpdlComplexData;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * PopulateDataApplication XPDL Application<br>
 * 
 * @author WojciechZ
 */
public class PopulateDataApplication extends AbstractApplication {
	/**
	 * log4j logger for this class
	 */
	private final static Logger log = Logger.getLogger(PopulateDataApplication.class);

	/**
	 * @param mapppingType
	 * @param applicationId
	 * @return
	 */
	public List getCopyPaths(XMLMappingApplicationType mapppingType,
			String applicationId) {
		List result = new ArrayList();
		for (int i = 0; i < mapppingType.getCopyArray().length; i++) {
			CopyType copyType = mapppingType.getCopyArray(i);
			String fromElement = copyType.getFrom();
			String toElement = copyType.getTo();
			if (fromElement == null || fromElement.trim().length() < 1
					|| toElement == null || toElement.trim().length() < 1) {
				XpdlException e = new XpdlException(
						"From or To element do not have a value "
								+ "specified for the 'XMLMappingApplication' "
								+ "with applicationId= " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			result.add(new String[] { fromElement.trim(), toElement.trim() });
		}
		return result;
	}

	/**
	 * 
	 * @param mappingType
	 * @param applicationId
	 * @return
	 */
	public List getInits(XMLMappingApplicationType mapppingType,
			String applicationId) {
		List result = new ArrayList();
		for (int i = 0; i < mapppingType.getInitializeArray().length; i++) {
			InitializeType initType = mapppingType.getInitializeArray(i);
			String element = initType.getElement();
			String initValue = initType.getValue();
			if (element == null || element.trim().length() < 1
					|| initValue == null) {
				XpdlException e = new XpdlException(
						"Element or Value element under Initialize do not have a value "
								+ "specified for the 'XMLMappingApplication' "
								+ "with applicationId= " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			result.add(new String[] { element.trim(), initValue });
		}
		return result;
	}

	/**
	 * 
	 * @see com.tibco.inteng.apps.XpdlAutomaticApplication#invoke(String,
	 *      ExtendedAttribute[], XpdlData[])
	 * @param applicationId
	 * @param exts
	 * @param args
	 * @throws XpdlBusinessException
	 */
	public void invoke(String applicationId, ExtendedAttribute[] exts,
			XpdlData[] args) throws XpdlBusinessException {

		XMLMappingApplicationType mapppingType = getApplicationType(exts,
				applicationId);
		// set init parameters
		for (Iterator iter = getInits(mapppingType, applicationId).iterator(); iter
				.hasNext();) {
			String[] init = (String[]) iter.next();
			try {
				XpdlData out = getFormalParam(applicationId, args, init[0]);
				out.setValue(init[1]);
			} catch (XpdlDataFormatException e) {
				XpdlBusinessException e1 = new XpdlBusinessException(e
						.getMessage(), e);
				log.error(e1.getMessage(), e);
				throw e1;
			}
		}
		// copy from source
		for (Iterator iter = getCopyPaths(mapppingType, applicationId)
				.iterator(); iter.hasNext();) {
			String[] paths = (String[]) iter.next();
			try {
				XpdlData out = getFormalParam(applicationId, args, paths[1]);
				XpdlData in = getFormalParam(applicationId, args, paths[0]);
				if (in != null
						&& (XpdlData.XPDL_TEXT.equals(in.getType())
								|| XpdlData.XPDL_TEXT_AREA.equals(in.getType()) || XpdlData.XPDL_CHOOSE
								.equals(in.getType())) && in.getValue() != null
						&& ((String) in.getValue()).length() == 0) {
					log.debug("Have empty String input value: " + in);
					// if((in.getValue() instanceof String)
					// && ((String)in.getValue()).length() > 0) {
					// log.debug("Have String input value: " + in) ;
					// out.setValue(in);

				} else {
					log
							.debug("Have non-String (or non-empty String) input value: "
									+ in);
					// if(!(in.getValue() instanceof String)) {
					out.setValue(in);
					// }
					// }
				}
				// out = getFormalParam(applicationId, args, paths[1]);
				// out.setValue(getFormalParam(applicationId, args, paths[0]));
			} catch (Exception e) {
				XpdlBusinessException e1 = new XpdlBusinessException(
						"Unable to copy property '" + paths[0] + "' to '"
								+ paths[1]
								+ "' on populateApp (Application ID: "
								+ applicationId + "): " + e.getMessage(), e);
				log.error(e1.getMessage(), e);
				throw e1;
			}
		}
	}

	/**
	 * Returns XpdlData specified by given path
	 * 
	 * @param applicationId
	 * 
	 * @param params -
	 *            array of formal parameters
	 * @param path -
	 *            selection path
	 * @return XpdlData selected by path
	 */
	private XpdlData getFormalParam(String applicationId, XpdlData[] params,
			String path) {
		String property = null;
		String name;
		if (path.startsWith("/")) {
			int tmp = path.indexOf('/', 1);
			if (tmp < 0) {
				name = path.substring(1);
			} else {
				name = path.substring(1, tmp);
				if (tmp + 1 < path.length()) {
					property = path.substring(tmp + 1);
				}
			}
		} else {
			name = path;
		}
		XpdlData fp = null;
		for (int i = 0; i < params.length; i++) {
			if (params[i].getName().equals(name)) {
				fp = params[i];
				break;
			}
		}
		if (fp == null) {
			XpdlException e = new XpdlException(
					"Invalid FormalParameter path: '" + path
							+ "' on application with ID:'" + applicationId
							+ "'");
			log.error(e.getMessage(), e);
			throw e;
		}
		if (property == null) {
			return fp;
		}
		if (!(fp instanceof XpdlComplexData)) {
			XpdlException e = new XpdlException(
					"Invalid FormalParameter path: '"
							+ path
							+ "' on application with ID:'"
							+ applicationId
							+ "', you cannot specify subelement of simple XpdlData");
			log.error(e.getMessage(), e);
			throw e;
		}
		return ((XpdlComplexData) fp).get(property);
	}

	/**
	 * @param extendedAttributes
	 * @param applicationId
	 * @return XMLMappingApplicationType
	 */
	protected XMLMappingApplicationType getApplicationType(
			ExtendedAttribute[] extendedAttributes, String applicationId) {
		XmlCursor cur = null;
		try {
			ExtendedAttribute extAttribute = getExtendedAttribute(
					ServicesConsts.XML_MAPPING_APP_NAME, extendedAttributes);
			if (extAttribute == null) {
				XpdlException e = new XpdlException(
						"There should be an extended attribute with name 'XMLMappingApplication' "
								+ "for the XMLMappingApplication with id = "
								+ applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			String xmlString = extAttribute.getXmlString();
			XmlObject xmlObject = XmlObject.Factory.parse(xmlString);
			cur = xmlObject.newCursor();
			if (!cur.toFirstChild()) {
				XpdlException e = new XpdlException(
						"The XMLMappingApplication element is not present under the  "
								+ "the extended attribute with name 'XMLMappingApplication' "
								+ "for applicationId= " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			XMLMappingApplicationType type = (XMLMappingApplicationType) cur
					.getObject().changeType(XMLMappingApplicationType.type);
			return type;
		} catch (ClassCastException ex) {
			XpdlException e = new XpdlException(
					"The content of the extended attribute with name='XMLMappingApplication' "
							+ "does not adhere to the schemaType of the XMLMappingApplicationType "
							+ "for applicationId= " + applicationId);
			log.error(e.getMessage(), e);
			throw e;
		} catch (XmlException ex) {
			XpdlException e = new XpdlException(
					"The content of the extended attribute with name='XMLMappingApplication' "
							+ "could not be parsed into xml for applicationId= "
							+ applicationId);
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			if (cur != null) {
				cur.dispose();
			}
		}
	}
}