package com.tibco.bx.extension.database.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.uml2.uml.Property;

import com.tibco.bx.extensions.database.databaseExtensionsModel.BaseType;
import com.tibco.bx.extensions.database.databaseExtensionsModel.BomType;
import com.tibco.bx.extensions.database.databaseExtensionsModel.Database;
import com.tibco.bx.extensions.database.databaseExtensionsModel.DatabaseExtensionsModelFactory;
import com.tibco.bx.extensions.database.databaseExtensionsModel.LabelType;
import com.tibco.bx.extensions.database.databaseExtensionsModel.OperationType;
import com.tibco.bx.extensions.database.databaseExtensionsModel.ParameterType;
import com.tibco.bx.extensions.database.databaseExtensionsModel.VariableType;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.bx.xpdl2bpel.util.CDSUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * This class transforms the xpdl configuration model to the bpel configuration model
 * 
 * @author richardy
 */
public class ConvertDatabase implements IActivityConfigurationModelBuilder {
	Map xpdMsgMap = new HashMap();
	
	public EObject transformConfigModel(Activity xpdlActivity, Map<String, Participant> participantMap) throws ConversionException {
		
        Implementation impl = xpdlActivity.getImplementation();
        EObject databaseObj = null;
        TaskService taskService = null;
		if (impl instanceof Task) {
			Task t = (Task) impl;
			if (t.getTaskService() != null) {				
				taskService = t.getTaskService();
				ImplementationType implementationType = taskService.getImplementation();
				if (implementationType != null) {
					int implementationTypeValue = implementationType.getValue();
					switch (implementationTypeValue) {	     
					case ImplementationType.OTHER:
						FeatureMap featureMap = taskService.getOtherElements();
						FeatureMap.ValueListIterator it =featureMap.valueListIterator();
						if (it.hasNext()) {
							// databaseObj is DatabaseTypeImpl
							databaseObj = (EObject)it.next();
							break;
						}
						return null;
     
					case ImplementationType.WEB_SERVICE:
					case ImplementationType.UNSPECIFIED:
						return null;
					}// endswitch	
				}
			}
		}
		

        // for the given activity find all the process data which you are
        // interested & then go to indexer to get the required values
        Map<String, ProcessRelevantData> cdsMap =
                CDSUtils.getActivityOutDataList(xpdlActivity);
			
		
		DatabaseType xpdDatabase = (DatabaseType) databaseObj;
		Database bpelDatabase = DatabaseExtensionsModelFactory.eINSTANCE.createDatabase();		


		// transform database
		BaseType baseType = null;
		
        /*
         * Sid ACE-7122 Moved Shared resource instance name and type to main 
         * extension activity attributes for consistency with Email/REST tasks.
         */
        /* JdbcResource jdbcResource = XPDLUtils.getDatabaseResource(xpdlActivity);
        
        if (jdbcResource != null) {
            bpelDatabase.setConnectionResource(jdbcResource.getInstanceName());
        }
        */
				
		// transform operation	

		com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType xpdOperationType = xpdDatabase.getOperation();
		OperationType bpelOperation = DatabaseExtensionsModelFactory.eINSTANCE.createOperationType();
		
		baseType = setBaseType(xpdOperationType.getSql());
		baseType.setValue(xpdOperationType.getSql());	
		bpelOperation.setSql(baseType);
		SqlType xpdSqlType = xpdOperationType.getType();
		bpelOperation.setType(xpdSqlType.getLiteral());
		
		bpelDatabase.setOperation(bpelOperation);

		
		// transform parameters
		com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType xpdParametersType = xpdOperationType.getParameters();
		if (xpdParametersType != null) {
			/*
			EList xpdParameters = xpdParametersType.getParameter();
			Iterator xpdParametersItr = xpdParameters.listIterator();
			*/
			
			Message xpdMsgIn = taskService.getMessageIn();
			Iterator xpdMsgItr = xpdMsgIn.getDataMappings().listIterator();
			while (xpdMsgItr.hasNext()) {
				DataMapping mapping = (DataMapping)xpdMsgItr.next();
				setBpelParameter(mapping, baseType, xpdlActivity, cdsMap, bpelOperation);
			}
	
			Message xpdMsgOut = taskService.getMessageOut();
			xpdMsgItr = xpdMsgOut.getDataMappings().listIterator();
			while (xpdMsgItr.hasNext()) {
				DataMapping mapping = (DataMapping)xpdMsgItr.next();
				setBpelParameter(mapping, baseType, xpdlActivity, cdsMap, bpelOperation);
			}	
			
			
		}
	  
        return bpelDatabase;
	}
	
	
	private void setBpelParameter(DataMapping xpdMapping, 
									BaseType baseType, 
									Activity xpdlActivity,
									Map<String, ProcessRelevantData> cdsMap,
									OperationType bpelOperation
	) {
		ParameterType bpelParameterType = 
			DatabaseExtensionsModelFactory.eINSTANCE.createParameterType();	

		baseType = setBaseType(xpdMapping.getFormal());			
		baseType.setValue(xpdMapping.getFormal());
		
		bpelParameterType.setName(baseType);

		bpelParameterType.setType(xpdMapping.getDirection().getName());
		bpelParameterType.setValueField(xpdMapping.getActual().getText());
		if (xpdMapping.getDirection() == DirectionType.OUT_LITERAL) {
			
			// iterate over activity data fields before process data fields
			Iterator itr = xpdlActivity.getDataFields().iterator();	
			if (!addBomInformation(itr, xpdMapping, cdsMap, bpelParameterType)) {
				itr = xpdlActivity.getProcess().getDataFields().iterator();
				if (!addBomInformation(itr, xpdMapping, cdsMap, bpelParameterType)) {
					itr = xpdlActivity.getProcess().getFormalParameters().iterator();	
					addBomInformation(itr, xpdMapping, cdsMap, bpelParameterType);
				}
				
			}
		}
		
		// EMF generated bpelOperation.getParameters() will create a new EList if no EList exists
		bpelOperation.getParameters().add(bpelParameterType);	
	}
	
	private boolean addBomInformation(Iterator itr, DataMapping xpdMapping, Map<String, ProcessRelevantData> cdsMap, ParameterType bpelParameterType) {
		boolean addBomInfo = false;
		
		while (itr.hasNext()) {
			ProcessRelevantData fp = (ProcessRelevantData)itr.next();
			if (fp.getName().equals(xpdMapping.getActual().getText())) {
				DataType dataType = fp.getDataType();
				if (dataType instanceof BasicType) {
					BasicTypeType  basicTypeType = ((BasicType)dataType).getType();
				} else if (dataType instanceof ExternalReference) {
				    if (cdsMap.containsKey(fp.getName())) {
				    	ProcessRelevantData processRelevantData = cdsMap.get(fp.getName());
		            	String cdsClassName = CDSUtils.getCDSClassName(processRelevantData);
		            	String cdsFactoryClassName = CDSUtils.getCDSFactoryClassName(processRelevantData);
		            	//cdsPackageName = CDSUtils.getCDSPackageName(processRelevantData);
		            	String cdsFactoryMethodName = CDSUtils.getCDSFactoryMethodName(processRelevantData);
		            	
		            	/*
		            	 * Sid ACE-7432: Throw exception in the very rare case that for some reason we fail to get the reference BPM class. 
		            	 * This shouldn't be possible because if the BPM class had _really_ been removed then there would have been
		            	 * a validation error in the XPDL process that this BPMe is being derived from.
		            	 * But here the issue is probably that here we are using the BPM index and validation rules may use BOM model direct.
		            	 * 1-customer on 1-occasion had an issue here because index was not up to date and caused runtime issues
		            	 * because these properties were not set.
		            	 * 
		            	 * So in this ultra-rare circumstance we'll just hrow exception to highlight the issue in Studio rather 
		            	 * than having some obscure issue later in runtime. 
		            	 */
		            	if (cdsClassName == null || cdsClassName.isEmpty()) {
		            		throw new RuntimeException(String.format("BOM class Name, referenced by field '%1$s', could not be found (not indexed) - full workspace clean rebuild required", processRelevantData.getName())); //$NON-NLS-1$
		            	}
		            	if (cdsFactoryClassName == null || cdsFactoryClassName.isEmpty()) {
		            		throw new RuntimeException(String.format("BOM class Factory Name, referenced by field '%1$s', could not be found (not indexed) - full workspace clean rebuild required", processRelevantData.getName())); //$NON-NLS-1$
		            	}
		            	if (cdsFactoryMethodName == null || cdsFactoryMethodName.isEmpty()) {
		            		throw new RuntimeException(String.format("BOM class Factory Method Name, referenced by field '%1$s', could not be found (not indexed) - full workspace clean rebuild required", processRelevantData.getName())); //$NON-NLS-1$
		            	}
		            	
			    		BomType bomType = DatabaseExtensionsModelFactory.eINSTANCE.createBomType();
			    		bomType.setFactoryClass(cdsFactoryClassName);
			    		bomType.setInstanceClass(cdsClassName);		
			    		// temporarily use instanceName field to store CDS Factory Method Name until model is change
			    		bomType.setInstanceName(cdsFactoryMethodName);

			    		Map<String, Property> labelMap = CDSUtils.getBOMClassAttributesName(processRelevantData);
			    		
			    		if (labelMap != null) {
			    			LabelType labelType = null;
			    			for (Map.Entry<String, Property> entry: labelMap.entrySet()) {
					    		labelType = DatabaseExtensionsModelFactory.eINSTANCE.createLabelType();
					    		labelType.setLabel(entry.getKey());
					    		Property p = entry.getValue();
					    		labelType.setName(p.getName());
					    		labelType.setDatatype(p.getType().getName());
					    		bomType.getLabelMap().add(labelType);
							}
			    		}
			    		
			    		bpelParameterType.setBom(bomType);
			    		addBomInfo = true;

				    }
				}
			}
		}
		
		return addBomInfo;
	}
	
	private String getBomPackage(String bomRef) {
		int end = bomRef.lastIndexOf('.');
		
		return bomRef.substring(0,end);
	}
	
	private String getInstanceName(String bomRef) {
		String tokens[] = bomRef.split("\\.");

		int numTokens = tokens.length;

		String name = tokens[tokens.length-2];
		char[] charArray = name.toCharArray();
		charArray[0] = Character.toUpperCase(name.charAt(0));
		String upperName = new String(charArray);
		
		return upperName;
	}
	
	private String getFactoryClass(String bomPackage, String name) {
		
		String factoryClass = bomPackage + "." + name + "Factory";
		
		return factoryClass;
	}
	

	private BaseType setBaseType(String value) {	
		return setBaseType(value, true);
	}
	
	private BaseType setBaseType(String value, boolean checkVariable) {	
		BaseType baseType = DatabaseExtensionsModelFactory.eINSTANCE.createBaseType();
		if (checkVariable)
			checkVariables(baseType, value);
		return baseType;
	}	
	
    static private final String VARIABLE_MARKER = "%";
    static private final char   VARIABLE_MARKER_CHAR = '%';
    static private final int 	VARIABLE_MARKER_LEN = VARIABLE_MARKER.length();
	private void checkVariables(BaseType baseType, String body) {	
		
		if (body == null)
			return;

		int index = body.indexOf(VARIABLE_MARKER);
		if (body.indexOf(VARIABLE_MARKER) < 0) {
			// no variable to insert
			return;
		}

		// get list of variables
		int bIndex = -1;
		char letter;
		for (index = 0; index < body.length(); index++) {
			letter = body.charAt(index);
			if (letter == VARIABLE_MARKER_CHAR) {
				if (bIndex >= 0) {
					VariableType varType = DatabaseExtensionsModelFactory.eINSTANCE.createVariableType();
					varType.setName(body.substring(bIndex,index+1));
					varType.setIndex(new Integer(bIndex));
					// tricky way to add a list of variables; there is no setVariables() method; 
					// getVariables() will create a variables list if not exist
					baseType.getVariables().add(varType);
					bIndex = -1;
					continue;
				}
				if (bIndex < 0) {
					bIndex = index;
					continue;
				}

			}
		}

	}
}
