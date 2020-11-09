package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.Variable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.xsd.XSDSimpleTypeDefinition;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.Tracing;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.Expression;
import com.tibco.bx.xpdl2bpel.extensions.ExtensionVariable;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder2;
import com.tibco.bx.xpdl2bpel.extensions.IActivityMappingDataModelProvider;
import com.tibco.bx.xpdl2bpel.extensions.IDataModel;
import com.tibco.bx.xpdl2bpel.extensions.IMappingDataModel;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.CDSUtils;
import com.tibco.bx.xpdl2bpel.util.DataTypeUtil;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

public class ConverterExtensions {

	private static final Map<String, IConfigurationElement> builderExtensions = new HashMap<String, IConfigurationElement>();
	
	static {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint xpt = registry.getExtensionPoint(ConverterActivator.PLUGIN_ID, 
        		IActivityConfigurationModelBuilder.EXT_ACTIVITY_CONFIG_MODEL_BUILDER);
        IExtension[] extensions = xpt.getExtensions();
        for (IExtension extension : extensions) {
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			for (IConfigurationElement element : configurationElements) {
				if (!"builder".equals(element.getName())) { //$NON-NLS-1$
					continue;
				}
				String targetClass = element.getAttribute("targetModel"); //$NON-NLS-1$
				builderExtensions.put(targetClass, element);
			}
		}
	}
	
	public static boolean hasModelBuilderExtension(EObject model) throws CoreException {
		String className = model.eClass().getInstanceClass().getName();
		return builderExtensions.containsKey(className);
	}

	public static IActivityConfigurationModelBuilder getModelBuilder(EObject model) throws CoreException {
		String className = model.eClass().getInstanceClass().getName();
		IConfigurationElement configurationElement = builderExtensions.get(className);
		if (configurationElement != null) {
			return (IActivityConfigurationModelBuilder) configurationElement.createExecutableExtension("class"); //$NON-NLS-1$
		}
		return null;
	}

	public static org.eclipse.bpel.model.Activity convertTaskServiceToExtensionActivity(
			ConverterContext context, final com.tibco.xpd.xpdl2.Activity xpdlActivity, EObject xpdlConfig) throws ConversionException {
        EObject configModel = null;
        IActivityConfigurationModelBuilder modelBuilder = null;
        try {
            modelBuilder = ConverterExtensions.getModelBuilder(xpdlConfig);
        } catch (CoreException e) {
            throw new ConversionException(Messages.getString("ConvertUtil.cannotLoadBuilderExtension") + xpdlConfig, e); //$NON-NLS-1$
        }
        if (modelBuilder != null) {
            configModel = modelBuilder.transformConfigModel(xpdlActivity, context.getParticipantMap());
            if (Tracing.ENABLED) Tracing.trace("Config model is " + configModel); //$NON-NLS-1$
        }
        
        if (configModel == null) {
        	return null;
        }
        
	    ExtensionActivity extensionActivity = BPELUtils.createExtensionActivityFromEmfObject(configModel, false);
	    extensionActivity.setName(xpdlActivity.getName());
	    
	    addSharedResourceInfo(extensionActivity, xpdlActivity);
	    
	    //see if we need to add pre- and/or post- mappings around the extension activity
    	org.eclipse.bpel.model.Variables variables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
        org.eclipse.bpel.model.Assign preCallAssign = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
        preCallAssign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$
		org.eclipse.bpel.model.Assign postCallAssign = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
        postCallAssign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$

		if (modelBuilder instanceof IActivityConfigurationModelBuilder2) {
			IActivityConfigurationModelBuilder2 modelBuilder2 = (IActivityConfigurationModelBuilder2) modelBuilder;

	        for (Variable var : modelBuilder2.getVariables()) {
	        	variables.getChildren().add(var);
			}
	        for (Copy copy : modelBuilder2.getPreActivityMappings()) {
	        	preCallAssign.getCopy().add(copy);
			}
	        for (Copy copy : modelBuilder2.getPostActivityMappings()) {
	        	postCallAssign.getCopy().add(copy);
			}
		} else if (modelBuilder instanceof IActivityMappingDataModelProvider) {
			IActivityMappingDataModelProvider provider = (IActivityMappingDataModelProvider) modelBuilder;
			ExtensionVariable[] extensionVariables = provider.getExtensionVariables();
			if (extensionVariables != null) {
				for (ExtensionVariable extVar : extensionVariables) {
					org.eclipse.bpel.model.Variable var = createVariable(extVar);
		        	variables.getChildren().add(var);
				}
			}

			List<IMappingDataModel> inputMappings = provider.getInputToServiceMappingData();
			for (IMappingDataModel mappingDataModel : inputMappings) {
				org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(xpdlActivity, mappingDataModel, true);
	        	preCallAssign.getCopy().add(copy);
			}
			List<IMappingDataModel> outputMappings = provider.getOutputFromServiceMappingData();
			for (IMappingDataModel mappingDataModel : outputMappings) {
				org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(xpdlActivity, mappingDataModel, false);
	        	postCallAssign.getCopy().add(copy);
			}
		}
		
		XPDLUtils.findReferencedData(context, xpdlActivity, xpdlActivity.getName(), DataReferenceContext.CONTEXT_ACTIVITY_IMPLEMENTATION);
		if (!preCallAssign.getCopy().isEmpty()) {
			XPDLUtils.findReferencedData(context, xpdlActivity, preCallAssign.getName(), DataReferenceContext.CONTEXT_MAPPING_IN);
		}

		XPDLUtils.configureRetry(extensionActivity, xpdlActivity);

    	if (variables.getChildren().isEmpty() && preCallAssign.getCopy().isEmpty() && postCallAssign.getCopy().isEmpty()) {
        	//No temp variables; no mappings. Simply return the extension activity
	    	XPDLUtils.configureHaltOnError(extensionActivity, xpdlActivity);
            return extensionActivity;
        }
        
        org.eclipse.bpel.model.Scope scope = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createScope();
        org.eclipse.bpel.model.Sequence sequence = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createSequence();
        sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$
        if (!preCallAssign.getCopy().isEmpty()) {
        	sequence.getActivities().add(preCallAssign);
        }
    	sequence.getActivities().add(extensionActivity);
        if (!postCallAssign.getCopy().isEmpty()) {
        	sequence.getActivities().add(postCallAssign);
        }
        scope.setVariables(variables);
        scope.setActivity(sequence);
    	XPDLUtils.configureHaltOnError(scope, xpdlActivity);
        return scope;
	}

    /**
     * @param extensionActivity 
     * @param configModel 
     * @param xpdlActivity
     */
    private static void addSharedResourceInfo(
            ExtensionActivity extensionActivity, final com.tibco.xpd.xpdl2.Activity xpdlActivity) {
        String implementationTypeId =
                TaskObjectUtil.getTaskImplementationExtensionId(xpdlActivity);
        if (TaskImplementationTypeDefinitions.EMAIL_SERVICE
                .equals(implementationTypeId)) {
            // Set shared resource configuration attributes.
            EmailResource emailSharedResource = XPDLUtils.getEmailResource(xpdlActivity);
            if (emailSharedResource != null) {
                BPELUtils.addExtensionAttribute(extensionActivity,
                        XPDLUtils.ATTR_SHARED_RESOURCE_TYPE,
                        XPDLUtils.SharedResourceType.EMAIL.getName());
                String sharedResourceName = emailSharedResource.getInstanceName();
                if (sharedResourceName != null) {
                    BPELUtils.addExtensionAttribute(extensionActivity,
                            XPDLUtils.ATTR_SHARED_RESOURCE_NAME,
                            sharedResourceName);
                }
            }   
        }
    }

	static private org.eclipse.bpel.model.Variable createVariable(ExtensionVariable extVar) {
		org.eclipse.bpel.model.Variable var = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		var.setName(extVar.getName());
		if (extVar.getType() instanceof PrimitiveType) {
			XSDSimpleTypeDefinition xsdType = DataTypeUtil.getXsdForUmlType(extVar.getType());
			var.setType(xsdType);
		} else if (extVar.getType() instanceof NamedElement) {
            String className = BOMWorkingCopy.getQualifiedClassName((NamedElement) extVar.getType());
			BPELUtils.addExtensionAttribute(var, "class", className); //$NON-NLS-1
			
            /*
             * Sid ACE-4840 include tibx:classVersion in field definitions (required by runtime when initialising
             * script engine.
             */
            String bomClassVersion = XPDLUtils.getBomClassMajorVersion((NamedElement) extVar.getType());
            BPELUtils.addExtensionAttribute(var, "classVersion", bomClassVersion);
		}
        if (extVar.isArray()) {
        	BPELUtils.addExtensionAttribute(var, "array", "yes"); //$NON-NLS-1 //$NON-NLS-2
        }
		Map<QName, String> extAttrs = extVar.getExtensionAttributes();
		if (extAttrs != null) {
			for (Entry<QName, String> entry : extAttrs.entrySet()) {
				BPELUtils.addExtensionAttribute(var, entry.getKey(), entry.getValue());
			}
		}
		return var;
	}

	static private org.eclipse.bpel.model.Copy convertDataMappingToCopy(
			Activity xpdlActivity, IMappingDataModel mappingDataModel, boolean inputToExtensionAct) {
		IDataModel sourceDataModel = mappingDataModel.getSourceDataModel();
		Expression sourceExpression = sourceDataModel.getExpression();
		IDataModel targetDataModel = mappingDataModel.getTargetDataModel();
		Expression targetExpression = targetDataModel.getExpression();
		
		org.eclipse.bpel.model.From from;
		org.eclipse.bpel.model.To to;
		
		if (inputToExtensionAct) {
			boolean mappingFromArrayToSingle = sourceDataModel.isArray() && !targetDataModel.isArray();

			String expression = mappingFromArrayToSingle ? 
					sourceExpression.getText() + ".get(Process.getActivityLoopIndex());" : 
					sourceExpression.getText(); //$NON-NLS-1$ 
			from = BPELUtils.createFromScript(expression, sourceExpression.getScriptGrammar());
			to = BPELUtils.createToVariable(targetExpression.getText());
		} else {
			boolean mappingFromSingleToArray = !sourceDataModel.isArray() && targetDataModel.isArray();
			
			from = BPELUtils.createFromScript(sourceExpression.getText(), sourceExpression.getScriptGrammar());
			BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
			to = createToExpressionWithCDS(xpdlActivity, targetDataModel, mappingFromSingleToArray);
		}

		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		copy.setTo(to);
		copy.setFrom(from);
		copy.setIgnoreMissingFromData(mappingDataModel.isOptional());
		
		return copy;
	}

	static private org.eclipse.bpel.model.To createToExpressionWithCDS(Activity xpdlActivity, IDataModel targetDataModel, boolean mappingFromSingleToArray) {
		String expression = targetDataModel.getExpression().getText();
		StringBuffer script = new StringBuffer();
    	
		String createStmts = CDSUtils.getCDSClassCreateStatementsForExpression(xpdlActivity, expression);
		if (createStmts != null) {
			script.append(createStmts);
		}
		
    	if (mappingFromSingleToArray) {
    		script.append("var loopIdx = Process.getActivityLoopIndex();\n"); //$NON-NLS-1$
    		script.append("if (loopIdx < ").append(expression).append(".size()) {\n");
        	script.append(expression).append(".set(loopIdx, fromReturn);\n"); //$NON-NLS-1$
    		script.append("} else {\n");
    		script.append("for (i = ").append(expression).append(".size(); i < loopIdx; i++) {\n"); //$NON-NLS-1$ //$NON-NLS-2$
    		script.append(expression).append(".add(i, null);\n"); //$NON-NLS-1$
    		script.append("}\n"); //$NON-NLS-1$
            script.append(expression).append(".add(loopIdx, fromReturn);\n"); //$NON-NLS-1$
            script.append("}\n"); //$NON-NLS-1$
    	} else {
        	script.append(expression);    	
    		
    		boolean mapToEnumField = targetDataModel.getType() instanceof Enumeration; 
        	if (mapToEnumField) {
        		String enumTypeName = targetDataModel.getType().getName(); 
        		script.append(" = ").append(enumTypeName).append(".get(fromReturn);"); //$NON-NLS-1$ //$NON-NLS-2$
        	} else {
        		script.append(" = fromReturn;"); //$NON-NLS-1$
        	}
    	}
		
		org.eclipse.bpel.model.Expression expr = BPELFactory.eINSTANCE.createExpression();
    	expr.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
    	expr.setBody(script.toString());
    	
    	org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
		to.setExpression(expr);
    	return to;
	}
}
