package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.PartnerActivity;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.util.XSDConstants;

import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.StartEvent;

public class ConvertCorrelations {
	
	public static void convert(ConverterContext context, com.tibco.xpd.xpdl2.Activity xpdlActivity, 
			PartnerActivity activity, WebServiceOperationInfo wsoInfo, com.tibco.xpd.xpdl2.Message message) {
        if (activity instanceof Receive || activity instanceof Reply) { // only Receive and Reply activities are supported
            /*
             * Sid ACE-194 - we don't support Correlation of web service incoming in ACE
             */
            throw new RuntimeException("Unexpected inclusion of incoming message correlation activity in source process.");
//          Correlations correlations = convertCorrelationMappings(context, xpdlActivity, message, wsoInfo, false);
//            ((PartnerActivity)activity).setCorrelations(correlations);
        }
    }
	
	public static void convert(ConverterContext context, com.tibco.xpd.xpdl2.Activity xpdlActivity, 
			OnEvent onEvent, WebServiceOperationInfo wsoInfo, com.tibco.xpd.xpdl2.Message message) {
        /*
         * Sid ACE-194 - we don't support Correlation of web service incoming in ACE
         */
        throw new RuntimeException("Unexpected inclusion of incoming message correlation activity in source process.");
//		Correlations correlations = convertCorrelationMappings(context, xpdlActivity, message, wsoInfo, true);
//		onEvent.setCorrelations(correlations);
	}
	
	public static void convert(ConverterContext context, com.tibco.xpd.xpdl2.Activity xpdlActivity, 
			OnMessage onMessage, WebServiceOperationInfo wsoInfo, com.tibco.xpd.xpdl2.Message message) {
	    /*
	     * Sid ACE-194 - we don't support Correlation of web service incoming in ACE
	     */
	    throw new RuntimeException("Unexpected inclusion of incoming message correlation activity in source process.");
//		Correlations correlations = convertCorrelationMappings(context, xpdlActivity, message, wsoInfo, false);
//		onMessage.setCorrelations(correlations);
	}
	
	public static boolean addPropertiesToWSDL(ConverterContext context, URI wsdlLocation) throws ConversionException {		
		boolean propertyAdded = false;
		
		Definition wsdl = context.getWsdlDefinition(wsdlLocation);
		Map<QName, Property> existingProperties = new HashMap<QName, Property>();
		List<?> extensibilityElements = wsdl.getExtensibilityElements();
		for (Object el : extensibilityElements) {
			if (el instanceof Property) {
				existingProperties.put(((Property) el).getQName(), (Property) el);
			}
		}
		
		Collection<Property> properties = context.getProperties(wsdlLocation);
		for (Property property: properties) {	// Need to add properties before adding property alias
			QName qname = new QName(wsdl.getTargetNamespace(), property.getName());
			if (property.eContainer() == null && !existingProperties.containsKey(qname)) {
				if (!wsdl.getNamespaces().containsValue(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001)) {
					wsdl.addNamespace("xsd", XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001); //$NON-NLS-1$
				}
				wsdl.addExtensibilityElement(property); 
				existingProperties.put(property.getQName(), property);
				propertyAdded = true;
			}
			
			//Even if we don't add the property to the WSDL, it might have been referenced in the BPEL correlation set, 
			//and we still need to set the enclosing definition, or the serialized property in the correlation set won't
			//have the proper namespace prefix.
			if (property.getEnclosingDefinition() == null) {
				property.setEnclosingDefinition(wsdl);
			}
		}
		
		Map<QualifiedName, PropertyAlias> aliases = context.getPropertyAliases(wsdlLocation);
		for (QualifiedName key : aliases.keySet()) {
			PropertyAlias alias = aliases.get(key);
			if (alias.eContainer() == null) {
				Property prop = (Property) alias.getPropertyName();
				QName qname = new QName(wsdl.getTargetNamespace(), prop.getName());
				alias.setPropertyName(existingProperties.get(qname));
				wsdl.addExtensibilityElement(alias); 
				propertyAdded = true;
			}
		}
		
		return propertyAdded;
	}
	
	public static CorrelationSets createCorrelationSets(ConverterContext context) {
		CorrelationSets correlationSets = BPELFactory.eINSTANCE.createCorrelationSets();
        BPELUtils.addExtensionAttribute(correlationSets, "useVariables", "yes");
        Map<String, CorrelationSet> correlations = context.getCorrelations();
		for (CorrelationSet set: correlations.values()) {
			correlationSets.getChildren().add(set);
		}		
		return correlationSets;
	}

    /**
     * Sid ACE-6365 Convert the correlation data associations to the appropriate BPEL correlation constructs for
     * incoming request activities
     * 
     * This is a list of bpws:correlations AND any referenced correlation data field is added to the main process
     * correlationSets list
     * 
     * @return
     */
	public static Correlations convertIncomingRequestCorrelations(ConverterContext context, Activity xpdlActivity) {

        /*
         * Get the implicitly / explicitly associated correlation data
         */
        List<DataField> correlationDataForActivity =
                ProcessInterfaceUtil.getAssociatedCorrelationDataForActivity(xpdlActivity);

        if (correlationDataForActivity.size() > 0) {
            Correlations correlations = BPELFactory.eINSTANCE.createCorrelations();

            for (DataField dataField : correlationDataForActivity) {
                /*
                 * Add the correlationSets entry (if not already there.
                 * 
                 * (getCorrelationSet() creates one 1st time reqeusted)
                 */
                CorrelationSet correlationSet = context.getCorrelationSet(dataField.getName());

                /*
                 * Add a correlation to this activity's correlations
                 */
                Correlation correlation = BPELFactory.eINSTANCE.createCorrelation();
                correlation.setInitiate("no"); //$NON-NLS-1$
                correlation.setSet(correlationSet);
                correlations.getChildren().add(correlation);
            }

            return correlations;
        }

        return null;
	}
	
	
    /*
     * Sid ACE-194 - we don't support Correlation of web service incoming in ACE
     */

//	private static Correlations convertCorrelationMappings(
//	        ConverterContext context, Activity xpdlActivity, Message message,
//	        WebServiceOperationInfo wsoInfo, boolean onEvent) {
//	    List<DataMapping> dataMappings = new ArrayList<DataMapping>();
//	    Map<String, CorrelationMode> correlationDataFields =
//	            XPDLUtils.getCorrelationDataFields(xpdlActivity);
//	    // XPD-8014 : Added bellow if part to handle Correlations in case of DataMapper grammar type
//	    if (isDataMapperGrammarSetForOutDirection(xpdlActivity)) {
//	        ScriptDataMapper scriptDataMapper =
//	                XPDLUtils.getScriptDataMapper(message);
//	        if (scriptDataMapper == null) {
//	            return null;
//	        } else {
//	            for (DataMapping mappings : scriptDataMapper.getDataMappings()) {
//	                if (WebServiceDataMapperUtil
//	                        .isMappedToCorrelationData(mappings)) {
//	                    dataMappings.add(mappings);
//	                }
//	            }
//	        }
//	    } else {
//	        CorrelationDataMappings correlationDataMappings =
//	                XPDLUtils.getCorrelationDataMappings(message);
//	        if (correlationDataMappings == null) {
//	            return null;
//	        } else {
//	            dataMappings.addAll(correlationDataMappings.getDataMappings());
//	        }
//	    }
//	    Correlations correlations = BPELFactory.eINSTANCE.createCorrelations();
//	    for (DataMapping dataMapping : dataMappings) {
//	        {
//	            convertCorrelationMapping(context,
//	                    xpdlActivity,
//	                    dataMapping,
//	                    wsoInfo,
//	                    correlations,
//	                    correlationDataFields,
//	                    onEvent);
//	        }
//	    }
//	    return correlations;
//	}

    /*
     * Sid ACE-194 - we don't support Correlation of web service incoming in ACE
     */
//
//	private static void convertCorrelationMapping(ConverterContext context, Activity xpdlActivity, DataMapping dataMapping
//			, WebServiceOperationInfo wsoInfo, Correlations correlations, Map<String, CorrelationMode> correlationDataFields, boolean onEvent) {
//		String target = DataMappingUtil.getTarget(dataMapping);
//    	String script = DataMappingUtil.getScript(dataMapping);
//        String grammar = DataMappingUtil.getGrammar(dataMapping);
//    	
//        ScriptMappingCompositorFactory factory = ScriptMappingCompositorFactory.getCompositorFactory(grammar, dataMapping.getDirection());
//        if (factory == null) {
//        	return;
//        }
//        String initiate = null;
//        ScriptMappingCompositor compositor = factory.getCompositor(xpdlActivity, target, script);
//        if (compositor instanceof SingleMappingCompositor) {        
//            SingleMappingCompositor single = (SingleMappingCompositor) compositor;
//            String expression = single.getScript();
//            if (expression != null) {                	
//            	org.eclipse.bpel.model.messageproperties.Query query = org.eclipse.bpel.model.messageproperties.MessagepropertiesFactory.eINSTANCE.createQuery();
//            	String queryStr = null;
//            	String partName = null;
//
//
//            	boolean isXPath = ScriptGrammarFactory.XPATH.equals(grammar);
//        		if (isXPath) {
//        			expression = WsdlUtil.wsdlPathToXPath(expression);
//        		}
//            	int pos = expression.indexOf(isXPath ? "/" : "."); //$NON-NLS-1$  //$NON-NLS-2$
//            	if (pos > 0) {
//            		queryStr = expression.substring(pos+1);
//                	partName = expression.substring(0, pos);
//                	if (!isXPath) {
//                		Map<String, String> namespaceToPrefixMap = new HashMap<String, String>();
//                		Map<String, String> namespaces = wsoInfo.getWsdlDefinition().getNamespaces();
//                		for (String prefix : namespaces.keySet()) {
//                			namespaceToPrefixMap.put(namespaces.get(prefix), prefix);
//						}
//						queryStr = JavaScriptWsdlPathToXPathConverter.javaScriptWsdlPathToXPath(xpdlActivity, expression, true, namespaceToPrefixMap);
//                	}
//            	} else {
//					queryStr = "."; 	// take current path
//            		partName = expression;
//            	}
//
//            	query.setValue(queryStr);
//
//            	// NOTE: setPropertyName() expects property and not the name of the property
//            	Property property = null;
//            	Map<String, Property> properties = context.getProperties(xpdlActivity.getProcess());
//            	if (properties != null) {
//            		property = properties.get(target);
//            	}
//            	if (property == null) {
//            		String msg = String.format(Messages.getString("ConvertCorrelations.noPropertyFound"), new Object[]{target});
//                    context.logError(msg, null);
//                    return;         		
//            	}
//            	
//            	CorrelationSet correlationSet = context.getCorrelationSet(target);
//            	
//            	org.eclipse.wst.wsdl.Message msg = (org.eclipse.wst.wsdl.Message)wsoInfo.getInput().getMessage();            	
//                String key = msg.getQName().toString() + "/" + target;
//                if (properties.get(key) == null) {  
//                	// only add one property for a given message type to a correlation set
//                	Property clonedProperty = context.addProperty(wsoInfo.getWSDLLocation(), property);
//                	// create property alias
//                	org.eclipse.bpel.model.messageproperties.PropertyAlias propertyAlias = org.eclipse.bpel.model.messageproperties.MessagepropertiesFactory.eINSTANCE.createPropertyAlias();
//                	propertyAlias.setPropertyName(clonedProperty);
//                	propertyAlias.setQuery(query);
//                	// Always do messageType/part for property alias
//                	propertyAlias.setPart(partName);
//                	propertyAlias.setMessageType(msg);
//                	context.addPropertyAlias(wsoInfo.getWSDLLocation(), propertyAlias);
//                	//CorrelationSet correlationSet = BPELFactory.eINSTANCE.createCorrelationSet();
//                    //correlationSet.setName(target);	// need to be unique within the correlationSets
//                    
//                	correlationSet.getProperties().add(clonedProperty);
//                	properties.put(key, clonedProperty);
//                }
//                                
//            	
//            	Correlation correlation = BPELFactory.eINSTANCE.createCorrelation();
//            	initiate = getInitiate(xpdlActivity.getEvent(), correlationDataFields, target, onEvent);
//            	correlation.setInitiate(initiate);
//            	correlation.setSet(correlationSet);
//            	//Correlations correlations = BPELFactory.eINSTANCE.createCorrelations();            		
//            	correlations.getChildren().add(correlation);
//            	                	
//	            //((PartnerActivity)activity).setCorrelations(correlations);
//	                             	
//            }
//        }        
//	}

	private static String getInitiate(Event event, Map<String, CorrelationMode> correlationDataFields, String name, boolean onEvent) {
		String initiate = null;
		CorrelationMode mode = correlationDataFields.get(name);
		if (mode != null) {
			if (mode == CorrelationMode.CORRELATE) {
				initiate = "no";
			} else if (mode == CorrelationMode.INITIALIZE){
				initiate = "yes"; 
			} else {
				initiate = "join";
			}
		} else if (onEvent) {
			initiate = "no";	// default to no for onEvent
		} else if (event instanceof StartEvent) {
			initiate = "yes"; // default to yes for Start Event
		} else {
			initiate = "no";	// default to no for non Start Event
		}
			
		return initiate;
	}
	
	/**
	 * Check if passed 'xpdlActivity' has DataMapper grammar type set in Out
	 * direction
	 * 
	 * @param xpdlActivity
	 * @return return true if DataMapper grammar set in OUT direction otherwise
	 *         false
	 */
	private static boolean isDataMapperGrammarSetForOutDirection(
	        Activity xpdlActivity) {
	    return ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory
	            .getScriptGrammar(xpdlActivity, DirectionType.OUT_LITERAL));
	}
}
