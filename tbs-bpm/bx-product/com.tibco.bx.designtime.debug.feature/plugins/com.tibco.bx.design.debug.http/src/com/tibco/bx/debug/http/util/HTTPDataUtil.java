package com.tibco.bx.debug.http.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.core.util.URLUtils;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.n2.pfe.services.pageflowType.DataModel;
import com.tibco.n2.pfe.services.pageflowType.DataPayload;
import com.tibco.n2.pfe.services.pageflowType.FieldType;
import com.tibco.n2.pfe.services.pageflowType.FieldTypeType;
import com.tibco.n2.pfe.services.pageflowType.PFETemplate;
import com.tibco.n2.pfe.services.pageflowType.PayloadModeType;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

public class HTTPDataUtil {

	private static final Map<BasicTypeType, FieldTypeType> dataTypeMap = new HashMap<BasicTypeType, FieldTypeType>();
	static {
		dataTypeMap.put(BasicTypeType.STRING_LITERAL, FieldTypeType.STRING_TYPE);
		dataTypeMap.put(BasicTypeType.FLOAT_LITERAL, FieldTypeType.DECIMAL_TYPE);
		dataTypeMap.put(BasicTypeType.INTEGER_LITERAL, FieldTypeType.INTEGER_TYPE);
		dataTypeMap.put(BasicTypeType.BOOLEAN_LITERAL, FieldTypeType.BOOLEAN_TYPE);
		dataTypeMap.put(BasicTypeType.DATE_LITERAL, FieldTypeType.DATE_TYPE);
		dataTypeMap.put(BasicTypeType.TIME_LITERAL, FieldTypeType.TIME_TYPE);
		dataTypeMap.put(BasicTypeType.DATETIME_LITERAL, FieldTypeType.DATETIME_TYPE);
		dataTypeMap.put(BasicTypeType.PERFORMER_LITERAL, FieldTypeType.PERFORMER_TYPE);
	}

	public static PFETemplate createPFETemplate(ProcessTemplate processTemplate){
		return new PFETemplate(processTemplate.getModuleName(), processTemplate.getProcessName(), processTemplate.getModuleVersion());
	}
	
	public static DataPayload createDataPayload(DataModel dataModel){
		return new DataPayload(null, dataModel, PayloadModeType.XML);
	}
	
	public static DataModel createDataModel(ProcessTemplate processTemplate, Map<String, String> parametersMap,
			ILauncherEventHandler handler, IConnection connection) {
		String pId = processTemplate.getProcessId();
		List<EObject> allParams = ProcessUtil.getAllParms(ProcessUtil.getProcess(pId, connection.getModelType()));
		return new DataModel(createInputs(allParams, parametersMap), createOutputs(allParams, parametersMap),
				createInouts(allParams, parametersMap));
	}

	private static FieldType[] createInputs(List<EObject> allParams, Map<String, String> parametersMap) {
		List<FieldType> inputs = new ArrayList<FieldType>();
		for (EObject obj : allParams) {
			if (obj instanceof FormalParameter) {
				FormalParameter param = (FormalParameter) obj;
				if (ModeType.IN == param.getMode().getValue()) {
					FieldType ft = createFieldType(param, parametersMap);
					inputs.add(ft);
				}
			}
		}

		return (FieldType[]) inputs.toArray(new FieldType[inputs.size()]);
	}

	private static FieldType[] createOutputs(List<EObject> allParams, Map<String, String> parametersMap) {
		List<FieldType> outputs = new ArrayList<FieldType>();
		for (EObject obj : allParams) {
			if (obj instanceof FormalParameter) {
				FormalParameter param = (FormalParameter) obj;
				if (ModeType.OUT == param.getMode().getValue()) {
					FieldType ft = createFieldType(param, parametersMap);
					outputs.add(ft);
				}
			}
		}

		return (FieldType[]) outputs.toArray(new FieldType[outputs.size()]);
	}

	private static FieldType[] createInouts(List<EObject> allParams, Map<String, String> parametersMap) {
		List<FieldType> inouts = new ArrayList<FieldType>();
		for (EObject obj : allParams) {
			if (obj instanceof FormalParameter) {
				FormalParameter param = (FormalParameter) obj;
				if (ModeType.INOUT == param.getMode().getValue()) {
					FieldType ft = createFieldType(param, parametersMap);
					inouts.add(ft);
				}
			} else if (obj instanceof DataField) {
				DataField param = (DataField) obj;
				FieldType ft = createFieldType(param, parametersMap);
				inouts.add(ft);
			}
		}

		return (FieldType[]) inouts.toArray(new FieldType[inouts.size()]);
	}

	private static FieldType createFieldType(ProcessRelevantData param, Map<String, String> parametersMap) {
		FieldType ft = new FieldType();
		ft.setName(param.getName());
		String value = parametersMap.get(param.getName()) != null ? parametersMap.get(param.getName()) : "";
		ft.setArray(param.isIsArray());
		if (param.getDataType() instanceof BasicType) {
			BasicType bt = (BasicType) param.getDataType();
			ft.setType(dataTypeMap.get(bt.getType()));
			ft.setSimpleSpec(convert2ArrayValue(value, param.isIsArray()));
		} else if (param.getDataType() instanceof ExternalReference) {
			ft.setType(FieldTypeType.COMPLEX_TYPE);
			ft.setComplexSpec(convert2ArrayElement(value, param.isIsArray()));
		}
		return ft;
	}
	
	private static Element[] convert2ArrayElement(String value, boolean isArray){
		if(isArray){
			value = value.substring(1, value.length() - 1);
		}
		String[] arrayValue = value.split(",");
		Element[] elements = new Element[arrayValue.length];
		for (int i = 0; i < arrayValue.length; i++) {
			arrayValue[i] = URLUtils.decode(arrayValue[i].trim());
			ByteArrayInputStream bais = new ByteArrayInputStream(arrayValue[i].getBytes());
			try {
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(bais));
				Element element = doc.getDocumentElement();
				elements[i] = element;
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} finally {
				if (bais != null)
					try {
						bais.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		return elements;
	}

	private static String[] convert2ArrayValue(String value, boolean isArray) {
		if (isArray) {
			value = value.substring(1, value.length() - 1);
		}
		String[] arrayValue = value.split(","); //$NON-NLS-1$
		for (int i = 0; i < arrayValue.length; i++) {
			arrayValue[i] = arrayValue[i].trim();
		}
		return arrayValue;
	}

}
