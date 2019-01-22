package com.tibco.bx.debug.ui.views.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.bx.debug.core.util.URLUtils;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.emulation.bpm.core.common.ComplexVariableElement;
import com.tibco.bx.emulation.bpm.core.common.ProcessVariableElementList;
import com.tibco.bx.emulation.bpm.core.common.SimpleVariableElement;
import com.tibco.bx.emulation.bpm.core.util.CDSManager;
import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

public class ProcessDataUtil {

	private static final String UTF8 = "UTF-8"; //$NON-NLS-1$

	private static final String XMLPAYLOAD_NODE = "XmlPayload"; //$NON-NLS-1$
	private static final String OUTPUTS_NODE = "outputs"; //$NON-NLS-1$
	private static final String INOUTS_NODE = "inouts"; //$NON-NLS-1$
	private static final String SIMPLESPECT_NODE = "simpleSpec"; //$NON-NLS-1$
	private static final String COMPLEXSPEC_NODE = "complexSpec"; //$NON-NLS-1$
	private static final String VALUE_NODE = "value"; //$NON-NLS-1$

	private static final String NAME_ATTR = "name"; //$NON-NLS-1$
	private static final String TYPE_ATTR = "type"; //$NON-NLS-1$
	private static final String TEXT_VALUE = "#text"; //$NON-NLS-1$

	private static final String StringType = "String"; //$NON-NLS-1$
	private static final String DecimalType = "Decimal Number"; //$NON-NLS-1$
	private static final String IntegerType = "Integer Number"; //$NON-NLS-1$
	private static final String BooleanType = "Boolean"; //$NON-NLS-1$
	private static final String DateType = "Date"; //$NON-NLS-1$
	private static final String TimeType = "Time"; //$NON-NLS-1$
	private static final String DateTimeType = "Date Time"; //$NON-NLS-1$
	private static final String PerformerType = "Performer"; //$NON-NLS-1$
	private static final String ComplexType = "Complex"; //$NON-NLS-1$

	private static final String PREFIX_GET = "get"; //$NON-NLS-1$

	private static final List<String> simpleType = new ArrayList<String>();
	static {
		simpleType.add(StringType);
		simpleType.add(DecimalType);
		simpleType.add(IntegerType);
		simpleType.add(BooleanType);
		simpleType.add(DateType);
		simpleType.add(TimeType);
		simpleType.add(DateTimeType);
		simpleType.add(PerformerType);
	}

	public static IActivityElement handleInOutElement(IActivityElement element) {
		IInOutElement inoutElement = (IInOutElement) element;
		resetInOutElement(inoutElement);

		String resp = inoutElement.getInOutDataType().getSoapMessage();
		Document doc = documentParseBuilder(resp);
		if(doc != null){
			Node resultNode = doc.getElementsByTagName(XMLPAYLOAD_NODE).item(0);
			if (resultNode == null) {
				handleParameterInvokeResponse(inoutElement);
			} else {
				handlePageflowInvokeResponse(inoutElement, resultNode);
			}
		}
		return inoutElement;
	}

	private static void handleParameterInvokeResponse(IActivityElement element) {
		element.clearVariableElements();
	}

	private static String getComplexStringValue(Element element) {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "No"); //$NON-NLS-1$
		try {
			transformer.transform(new DOMSource(element), new StreamResult(buffer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private static void handlePageflowInvokeResponse(IActivityElement inoutElement, Node resultNode) {
		int size = resultNode.getChildNodes().getLength();
		if (size > 0) {
			for (int i = 0; i < resultNode.getChildNodes().getLength(); i++) {
				Node node = resultNode.getChildNodes().item(i);
				if (node.getNodeName().equals(INOUTS_NODE) || node.getNodeName().equals(OUTPUTS_NODE)) {
					handleDocumentNodeData(node, inoutElement);
				} 
			}
		} else {
			handleNonResponse(inoutElement);
		}
	}

	private static void handleNonResponse(IActivityElement inoutElement) {
		for (int i = 0; i < inoutElement.getVariableElements().length; i++) {
			IVariableElement var = inoutElement.getVariableElements()[i];
			if (var.getEMFCharacter() instanceof DataField) {
				inoutElement.removeVariableElement(var);
			}
		}
	}

	private static void handleDocumentNodeData(Node node, IActivityElement inoutElement) {
		String attrName = node.getAttributes().getNamedItem(NAME_ATTR).getNodeValue();
		String attrType = node.getAttributes().getNamedItem(TYPE_ATTR).getNodeValue();
		for (int j = 0; j < node.getChildNodes().getLength(); j++) {
			Node tNode = node.getChildNodes().item(j);
			if (tNode.getNodeName().equals(SIMPLESPECT_NODE)) {
				parseSimpleSpec(tNode, inoutElement, attrName, attrType);
			} else if (tNode.getNodeName().equals(COMPLEXSPEC_NODE)) {
				parseComplexSpec(tNode, inoutElement, attrName, attrType);
			}
		}
	}

	private static void parseSimpleSpec(Node node, IActivityElement inoutElement, String attrName, String attrType) {
		String attrValue = ""; //$NON-NLS-1$
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node mNode = node.getChildNodes().item(i);
			if (mNode.getNodeName().equals(VALUE_NODE)) {
				if (node.getChildNodes().item(i).getLastChild() != null) {
					attrValue = node.getChildNodes().item(i).getLastChild().getNodeValue();
				}
				for (IVariableElement var : Arrays.asList(inoutElement.getVariableElements())) {
					if (var.getName().equals(attrName)) {
						if (var instanceof SimpleVariableElement) {
							var.setValueString(attrValue);
						} else if (var instanceof ProcessVariableElementList) {
							handleProcessVariableElementList((ProcessVariableElementList) var, attrValue, attrType);
						}
						break;
					}
				}
			}
		}
	}

	private static void parseComplexSpec(Node node, IActivityElement inoutElement, String attrName, String attrType) {
		String attrValue = ""; //$NON-NLS-1$
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node mNode = node.getChildNodes().item(i);
			if (mNode.getNodeName().equals(VALUE_NODE)) {
				for (int m = 0; m < mNode.getChildNodes().getLength(); m++) {
					if (!mNode.getChildNodes().item(m).getNodeName().startsWith(TEXT_VALUE)) {
						attrValue = getComplexStringValue((Element) mNode.getChildNodes().item(1)).trim();
						break;
					}
				}

				for (IVariableElement var : Arrays.asList(inoutElement.getVariableElements())) {
					if (var.getName().equals(attrName)) {
						if (var instanceof ComplexVariableElement) {
							Map<String, IVariableElement> varMap = createVariableMap(var);
							try {
								EObject eObj = CDSManager.getDefault().deserialize(attrValue);
								handleComplexReferences(eObj, varMap);
							} catch (IOException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} else if (var instanceof ProcessVariableElementList) {
							handleProcessVariableElementList((ProcessVariableElementList) var, attrValue, attrType);
						}
						break;
					}
				}
			}
		}
	}

	private static Map<String, IVariableElement> createVariableMap(IVariableElement varElement) {
		Map<String, IVariableElement> varMap = new HashMap<String, IVariableElement>();
		for (IVariableElement var : varElement.getVariableElements()) {
			varMap.put(var.getName(), var);
		}
		return varMap;
	}

	private static void handleComplexReferences(EObject eObj, Map<String, IVariableElement> varMap) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		EList<EAttribute> eList = eObj.eClass().getEAttributes();
		Method[] methods = eObj.getClass().getDeclaredMethods();
		for (int i = 0; i < eList.size(); i++) {
			EAttribute eAttr = eList.get(i);
			String mName = PREFIX_GET + eAttr.getName().substring(0, 1).toUpperCase() + eAttr.getName().substring(1);
			for (Method method : methods) {
				if (method.getName().equals(mName)) {
					Object value = method.invoke(eObj);
					IVariableElement vElement = varMap.get(eAttr.getName());
					vElement.setValueString(String.valueOf(value));
					break;
				}
			}
		}

		EList<EReference> rList = eObj.eClass().getEReferences();
		for (int j = 0; j < rList.size(); j++) {
			EReference eRef = rList.get(j);
			String mName = PREFIX_GET + eRef.getName().substring(0, 1).toUpperCase() + eRef.getName().substring(1);
			for (Method method : methods) {
				if (method.getName().equals(mName)) {
					Object value = method.invoke(eObj);
					IVariableElement classVar = varMap.get(eRef.getName());
					Map<String, IVariableElement> refVarMap = createVariableMap(classVar);
					handleComplexReferences((EObject) value, refVarMap);
					break;
				}
			}
		}
	}

	private static void handleProcessVariableElementList(ProcessVariableElementList pvel, String attrValue, String attrType) {
		List<IVariableElement> itemList = getVariableValueList(pvel);
		if (simpleType.contains(attrType)) {
			SimpleVariableElement sve = new SimpleVariableElement(pvel, (ProcessRelevantData) pvel.getEMFCharacter(), URLUtils.decode(attrValue));
			itemList.add(sve);
		} else if (ComplexType.equals(attrType)) {
			ComplexVariableElement cve = new ComplexVariableElement(pvel, (ProcessRelevantData) pvel.getEMFCharacter(), attrValue);
			itemList.add(cve);
		}
		pvel.setItemList(itemList);
	}

	private static void resetInOutElement(IActivityElement inoutElement) {
		for (IVariableElement var : Arrays.asList(inoutElement.getVariableElements())) {
			if (var instanceof ProcessVariableElementList) {
				((ProcessVariableElementList) var).setItemList(new ArrayList<IVariableElement>());
			}
		}
	}

	private static Document documentParseBuilder(String soapResponse) {
		Document doc = null;
		if (soapResponse == null || "".equals(soapResponse)) { //$NON-NLS-1$
			return doc;
		}
		try {
			DocumentBuilderFactory parseFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parseBuilder = parseFactory.newDocumentBuilder();
			InputStream inputStream = new java.io.ByteArrayInputStream(soapResponse.getBytes(UTF8));
			InputSource input = new InputSource();
			input.setByteStream(inputStream);
			input.setEncoding(UTF8);
			doc = parseBuilder.parse(input);
		} catch (ParserConfigurationException pce) {
			DebugUIActivator.log(pce);
		} catch (IOException ioe) {
			DebugUIActivator.log(ioe);
		} catch (SAXException se) {
			DebugUIActivator.log(se);
		} catch (NullPointerException ne) {
			DebugUIActivator.log(ne);
		}
		return doc;
	}

	private static List<IVariableElement> getVariableValueList(ProcessVariableElementList pvel) {
		List<IVariableElement> valueList = new ArrayList<IVariableElement>();
		for (int i = 0; i < pvel.getVariableElements().length; i++) {
			valueList.add(pvel.getVariableElements()[i]);
		}
		return valueList;
	}

}
