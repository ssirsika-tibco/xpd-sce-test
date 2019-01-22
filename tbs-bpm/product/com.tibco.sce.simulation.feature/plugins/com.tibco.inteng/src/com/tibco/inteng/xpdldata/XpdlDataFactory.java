/* 
 ** MODULE: $RCSfile: XpdlDataFactory.java $ 
 ** $Revision: 1.14 $ 
 ** $Date:
 ** 2004/03/29 16:27:31Z $ 
 ** 
 ** DESCRIPTION : 
 ** 
 ** 
 ** ENVIRONMENT: Java - Platform independent 
 ** 
 ** COPYRIGHT: (c) 2003 Staffware plc, All Rights Reserved. 
 ** 
 **
 ** MODIFICATION HISTORY: 
 ** 
 ** $Log: XpdlDataFactory.java $ 
 ** Revision 1.14  2005/04/12 16:10:53Z  KamleshU 
 ** Changed the Error Messages which are thrown back to the client 
 ** Revision 1.13  2004/12/02 15:44:02Z  KamleshU 
 ** Deleted 2 methods, to take care of deletion of XpdlComplexDataImpl, XpdlArrayImpl and XpdlStringData classes as they were not used. 
 ** Revision 1.12  2004/11/03 10:11:58Z  KamleshU 
 ** As the ProcessStateImpl has a ResourceLocator so the datatype constrcution should use it, so changes accordingly 
 ** Revision 1.11  2004/10/20 08:44:59Z  KamleshU 
 ** Changes made for making the restricted schema work 
 ** Revision 1.10  2004/08/12 07:29:04Z  WojciechZ 
 ** Revision 1.9  2004/06/10 16:31:09Z  WojciechZ 
 ** fixed readonly flag 
 ** Revision 1.8  2004/06/10 10:54:39Z  WojciechZ 
 ** fix: readonly XpdlData 
 ** Revision 1.7  2004/04/29 14:15:54Z  WojciechZ 
 ** External references.  
 ** 1. posibility to load external xpdl file (i.e. from CMF-web project) 
 ** 2. external defined schema types (references to external XSD) 
 ** Revision 1.6  2004/04/16 08:53:40Z  WojciechZ 
 ** work up to 16/04/2004 
 ** Revision 1.5  2004/04/13 16:32:44Z  WojciechZ 
 ** work up to 13/04/2004 
 ** Revision 1.4  2004/04/08 16:02:23Z  WojciechZ 
 ** code review 
 ** move to apache xml beans 
 ** xpdl data use xml beans to hold the data 
 ** Revision 1.3  2004/04/01 11:00:42Z  WojciechZ 
 ** Work up to 01/04/2004 (working) 
 ** Revision 1.2 2004/03/29 16:27:31Z WojciechZ 
 ** work up to 29/03/2004 
 ** Revision 1.1 2004/03/26 15:08:20Z WojciechZ 
 ** Initial revision 
 */
package com.tibco.inteng.xpdldata;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.wfmc.x2002.xpdl10.BasicTypeDocument.BasicType;
import org.wfmc.x2002.xpdl10.DataTypeDocument.DataType;
import org.wfmc.x2002.xpdl10.ExternalReferenceDocument.ExternalReference;
import org.wfmc.x2002.xpdl10.SchemaTypeDocument.SchemaType;
import org.wfmc.x2002.xpdl10.TypeDeclarationDocument.TypeDeclaration;

import com.tibco.inteng.Package;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.impl.PackageImpl;
import com.tibco.inteng.utils.XmlUtils;

/**
 * Factory for all XpdlData objects
 * 
 * @author WojciechZ
 */
public final class XpdlDataFactory {
	/** Log4j */
	private static Logger log = Logger.getLogger(XpdlDataFactory.class);

	/** private constructor */
	private XpdlDataFactory() {
	}

	/**
	 * Create XpdlData based on type description in Xpdl <br>
	 * Can create complex types
	 * 
	 * @param name -
	 *            name of the field
	 * @param type -
	 *            XmlBean, description of datatype form xpdl
	 * @param interactionState -
	 *            process description (required when datatype = declared)
	 * @param readonly
	 *            readonly flag
	 * @return new created xpdl data
	 */
	public static XpdlData getDataType(String name, DataType type,
			ProcessState interactionState, boolean readonly) {
		XpdlData result;
		Package xpdlPackage = interactionState.getProcess().getPackage();
		if (type.isSetBasicType()) {
			result = getDataType(name, type.getBasicType(), readonly);
		} else if (type.isSetSchemaType()) {
			result = getDataType(name, type.getSchemaType(), readonly);
		} else if (type.isSetExternalReference()) {
			result = getDataType(name, xpdlPackage, type
					.getExternalReference(), readonly);
		} else if (type.isSetDeclaredType()) {
			TypeDeclaration declaration = ((PackageImpl)xpdlPackage).getDataType(type.getDeclaredType().getId());
			if (declaration == null) {
				throw new IllegalArgumentException("Data type: "
						+ type.getDeclaredType().getId()
						+ " is not a declared type");
			}
			if (declaration.isSetBasicType()) {
				result = getDataType(name, declaration.getBasicType(), readonly);
			} else if (declaration.isSetSchemaType()) {
				result = getDataType(name, declaration.getSchemaType(),
						readonly);
			} else if (declaration.isSetExternalReference()) {
				result = getDataType(name, xpdlPackage, declaration
						.getExternalReference(), readonly);
			} else {
				throw new IllegalArgumentException(
						"This data type is not supported.");
			}
		} else {
			throw new IllegalArgumentException(
					"This data type is not supported.");
		}
		return result;
	}
	
	/**
	 * Create XpdlData based on type description in Xpdl <br>
	 * Can create complex types
	 * 
	 * @param name -
	 *            name of the field
	 * @param type -
	 *            XmlBean, description of datatype form xpdl
	 * @param interactionState -
	 *            process description (required when datatype = declared)
	 * @param readonly
	 *            readonly flag
	 * @return new created xpdl data
	 */
	public static XpdlData getDataType(String name, DataType type,
			com.tibco.inteng.Package xpdlPackage, boolean readonly) {
		XpdlData result;
		if (type.isSetBasicType()) {
			result = getDataType(name, type.getBasicType(), readonly);
		} else if (type.isSetSchemaType()) {
			result = getDataType(name, type.getSchemaType(), readonly);
		} else if (type.isSetExternalReference()) {
			result = getDataType(name, xpdlPackage, type
					.getExternalReference(), readonly);
		} else if (type.isSetDeclaredType()) {
			TypeDeclaration declaration = ((PackageImpl)xpdlPackage).getDataType(type.getDeclaredType().getId());
			if (declaration == null) {
				throw new IllegalArgumentException("Data type: "
						+ type.getDeclaredType().getId()
						+ " is not a declared type");
			}
			if (declaration.isSetBasicType()) {
				result = getDataType(name, declaration.getBasicType(), readonly);
			} else if (declaration.isSetSchemaType()) {
				result = getDataType(name, declaration.getSchemaType(),
						readonly);
			} else if (declaration.isSetExternalReference()) {
				result = getDataType(name, xpdlPackage, declaration
						.getExternalReference(), readonly);
			} else {
				throw new IllegalArgumentException(
						"This data type is not supported.");
			}
		} else {
			throw new IllegalArgumentException(
					"This data type is not supported.");
		}
		return result;
	}


	/**
	 * Create data based on external reference link
	 * 
	 * @param name
	 *            name of the field
	 * @param interactionState
	 *            whose resourceLocator should be used
	 * @param ref
	 *            external reference xbean element
	 * @param readonly
	 * @return XpdlData (as SchemaTypedField)
	 */
	private static XpdlData getDataType(String name,
			Package xpdlPackage, ExternalReference ref,
			boolean readonly) {
		org.apache.xmlbeans.SchemaType schemaType = ((PackageImpl)xpdlPackage).getExternalType(ref.getLocation(),
						ref.getXref());
		XmlOptions opt = new XmlOptions();
		opt.setDocumentType(schemaType);
		XmlObject xmlObject = XmlObject.Factory.newInstance(opt);
		SchemaTypedField field = new SchemaTypedField(name, xmlObject);
		field.setReadonly(readonly);
		return field;
	}

	/**
	 * Create complex XpdlData based on SchemaType description
	 * 
	 * @param name
	 *            name of the field
	 * @param schema
	 *            XmlBean, description of schema type
	 * @param readonly
	 *            readonly flag
	 * @return XpdlData
	 */
	private static XpdlData getDataType(String name, SchemaType schema,
			boolean readonly) {
		if (log.isDebugEnabled()) {
			log.debug("create '" + name + "' as SchemaType");
		}
		String schemaTxt = XmlUtils.getInnerText(schema.xmlText());
		org.apache.xmlbeans.SchemaType type; // xsd schema
		SchemaTypeLoader loader;
		try {
			XmlObject schemaType = XmlObject.Factory.parse(schemaTxt);
			loader = XmlBeans.loadXsd(new XmlObject[] { schemaType });
		} catch (Exception e) {
			throw new XpdlException(e);
		}
		type = loader.findDocumentType(new QName(name));
		if (type == null) {
			XpdlException e = new XpdlException("There is no global element '"
					+ name + "' in SchemaType");
			log.error(e);
			throw e;
		}
		XmlObject xmlObject = loader.newInstance(type, null);
		SchemaTypedField field = new SchemaTypedField(name, xmlObject);
		field.setReadonly(readonly);
		return field;
	}

	/**
	 * Create XpdlData based on BasicType description in Xpdl
	 * 
	 * @param name
	 *            of the field
	 * @param basic
	 *            XmlBean, description of basic type
	 * @param readonly
	 * @return XpdlData
	 */
	private static XpdlData getDataType(String name, BasicType basic,
			boolean readonly) {
		log.debug("create '" + name + "' as BasicType: " + basic);
		XpdlSimpleData data = new XpdlSimpleData(name);
		data.setReadonly(readonly);
		return data;
	}

	/**
	 * Create and return copy of xpdl data element.
	 * 
	 * @param val
	 *            source xpdl data
	 * @param name
	 *            new name (null for no name changes)
	 * @param readonly
	 *            readonly falg
	 * @return XpdlData
	 */
	public static XpdlData copyOf(XpdlData val, String name, boolean readonly) {
		if (val.getName().indexOf(XpdlComplexData.DELIM) >= 0) {
			throw new XpdlException("Cannot copy a subelement.");
		}
		XpdlData copy = null;
		if (name == null) {
			name = val.getName();
		}
		if (val instanceof XpdlSimpleData) {
			copy = new XpdlSimpleData(name);
			try {
				copy.setValue(val.getValue());
			} catch (XpdlDataFormatException e) {
				// it is a copy, it shouldnt heppend
				log.error(e);
				throw new XpdlException(e);
			}
			((XpdlSimpleData) copy).setReadonly(readonly);
		} else if (val instanceof SchemaTypedField) {
			copy = new SchemaTypedField((SchemaTypedField) val, name);
			((SchemaTypedField) copy).setReadonly(readonly);
		} else {
			UnsupportedOperationException e = new UnsupportedOperationException(
					"Coping XpdlData '" + val.getClass().getName()
							+ "' is unsupported.");
			log.error(e);
			throw e;
		}
		return copy;
	}
}