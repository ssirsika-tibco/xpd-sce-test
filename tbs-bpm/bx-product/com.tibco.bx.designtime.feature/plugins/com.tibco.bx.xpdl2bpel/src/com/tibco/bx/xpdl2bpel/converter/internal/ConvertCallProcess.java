package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.uml2.uml.Property;

import com.tibco.bx.bpelExtension.extensions.CallProcess;
import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.Mapping;
import com.tibco.bx.bpelExtension.extensions.Script;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.CDSUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ConvertCallProcess {

	private final ConverterContext context;
	private final Activity xpdlActivity;
	private final Collection<ActivityInterfaceData> mainProcessData;
	private final Collection<ProcessRelevantData> subProcessData;
	private final Set<String> localVariables = new HashSet<String>();
	
	public static final String JSCRIPT_LANGUAGE = "urn:tibco:wsbpel:2.0:sublang:javascript";

	public ConvertCallProcess(ConverterContext context, Activity xpdlActivity) {
		this.context = context;
		this.xpdlActivity = xpdlActivity;
		
		mainProcessData = ActivityInterfaceDataUtil.getActivityInterfaceData(xpdlActivity);
		
		EObject subProcessOrInterface = TaskObjectUtil.getSubProcessOrInterface(xpdlActivity);
		if (subProcessOrInterface instanceof ProcessInterface) {
			ProcessInterface procIf = (ProcessInterface) subProcessOrInterface;
			subProcessData = new ArrayList<ProcessRelevantData>();
			for (FormalParameter eachFP : procIf.getFormalParameters()) {
				subProcessData.add(eachFP);
			}
		} else {
			Process subProcess = (Process) subProcessOrInterface;
			subProcessData = ProcessInterfaceUtil.getAllDataDefinedInProcess(subProcess);
		}
	}
	
	public SubFlow getSubFlow() {
		return (SubFlow) xpdlActivity.getImplementation();
	}

	private CallProcess createCallProcess() {
		SubFlow subFlow = this.getSubFlow();
		CallProcess callProcess = ExtensionsFactory.eINSTANCE.createCallProcess();
		
		AsyncExecutionMode asyncMode = XPDLUtils.getAsyncExecutionMode(subFlow);
		if (asyncMode!=null) {
			callProcess.setAsynch(true);
            callProcess.setAttached(asyncMode.equals(AsyncExecutionMode.ATTACHED));
            if (!callProcess.isAttached()) {
            	XpdModelType modelType = XPDLUtils.getXpdModelType(xpdlActivity.getProcess());
            	if (XpdModelType.PAGE_FLOW.equals(modelType) ||
            			(XpdModelType.SERVICE_PROCESS.equals(modelType) && context.isPageFlowEngineTarget())) {
	        		//detached and pageflow or service process deployed to a pageflow engine
            		//set remote engine to indicate if we will be crossing engine boundaries
                	callProcess.setRemoteEngine(true);
            	}
            }
        } else {
        	callProcess.setAsynch(false);
        }

		Package targetPackage = null;
		if (subFlow.getPackageRefId()!=null) {
			//BX-660: use the full path of the xpdl file as the packageRef, 
			//and remove the ".qualifier" from the version spec.
			Process subProcess = SubProcUtil.getSubProcess(subFlow);
			if (subProcess!=null) {
				targetPackage = subProcess.getPackage();
			}
		} else if (callProcess.isRemoteEngine()) {
			//when crossing engine boundaries set package ref even when in the same package
			targetPackage = xpdlActivity.getProcess().getPackage();
		}
		
		if (targetPackage!=null) {
			String filePath = targetPackage.eResource().getURI().toPlatformString(true);
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
			callProcess.setPackageRef(file.getFullPath().toString());
			String versionRange = XPDLUtils.getPackageVersionRange(targetPackage);
			callProcess.setVersion(versionRange);
		}
		
		if (callProcess.getPackageRef() == null || callProcess.getPackageRef().isEmpty()) {
			// BX-3176 && BX-3317
			boolean allowUnqualifiedSubProcessIdentification = XPDLUtils.getAllowUnqualifiedSubProcessIdentification(subFlow);
			if (allowUnqualifiedSubProcessIdentification) {
				callProcess.setPackageRef("__iProcess__"); //$NON-NLS-1$ 
			}
		}
        callProcess.setSubProcessId(subFlow.getProcessId());
        callProcess.setStartActivityId(subFlow.getStartActivityId());
        // if runtime identifier field set, it is a dynamic call process
        // this field is an xpd extension:
        //    xpdExt:ProcessIdentifierField="<dataFieldName>"
        FeatureMap fm = subFlow.getOtherAttributes();
        if (fm!=null) {
            Iterator it = fm.iterator();
            while (it.hasNext()) {
                EStructuralFeatureImpl.BasicFeatureMapEntry item = (EStructuralFeatureImpl.BasicFeatureMapEntry)it.next();
                if ("processIdentifierField".equals(item.getEStructuralFeature().getName())) {
                    callProcess.setProcessIdentifierField((String)item.getValue());
                }
            }
        }
        
        if (xpdlActivity.getPriority() != null) {
            callProcess.setPriority(xpdlActivity.getPriority().getValue());
        } else {
        	SubProcessStartStrategy startStrategy = XPDLUtils.getSubProcessStartStrategy(subFlow);
        	if (SubProcessStartStrategy.START_IMMEDIATELY.equals(startStrategy)) {
                callProcess.setPriority("inline"); //$NON-NLS-1$ 
        	}
        }
        
        Boolean suspendResumeWithParent = XPDLUtils.getSuspendResumeWithParent(subFlow);
		if (suspendResumeWithParent != null) {
        	callProcess.setFollowParentLifecycle(suspendResumeWithParent);
        }
        
		return callProcess;
	}

    private org.eclipse.bpel.model.Activity createActivityForCallProcess(CallProcess callProcess) {
        ExtensionActivity activity = BPELUtils.createExtensionActivityFromEmfObject(callProcess, false);
        activity.setName(xpdlActivity.getName());
		return activity;
    }

    /**
     * Convert xpdl version of Call Sub-Process activity to it's bpel equivalent. 
     * <p>
     * Note that this method supports conversion of both JavaScript and DataMapper mappings.
     * <p>
     * JavaScript scope bpel model would look like:
     * <p>
     *      {Variables}
     *      <p>
     *          temporary scope-variables for mappings that are anything other than simpleField->simpleParam and visa versa...
     *      <p>
     *      {/Variables}
     *      <p>
     *      {sequence}
     *      <p>
     *          Will define the extension activities in the sequence in which they've to be executed at runtime.
     *      <p>    
     *          {preAssign}
     *      <p> 
     *              map values into temporary scope variables from mapped invoking process data.
     *      <p>
     *          {/preAssign}
     *      <p>
     *          {extensionActivity}
     *      <p>
     *              bpel extension activity for call sub-process
     *      <p>
     *          {/extensionActivity}
     *      <p>
     *          {postAssign}
     *      <p>
     *              map values from temporary scope variables of Sub-Process to calling process.
     *      <p>
     *          {/postAssign}
     *      <p>
     *      {/sequence}
     * <p>
     * DataMapper scope bpel model would look like:
     *      <p>
     *      {Variables}
     *      <p>   
     *          temporary scope-variables for all the sub-process parameters defined in the sub-process.
     *      <p>
     *      {Variables}
     *      <p>
     *      {sequence}
     *      <p>
     *          Will define the extension activities in the sequence in which they've to be executed at runtime.
     *      <p>    
     *          {SPInput}
     *          <p>
     *              contains the script representation of the sub-process input mappings wherein process data is mapped to temporary sub-process variables.
     *          <p>
     *          {SPInput}
     *          <p>
     *          {extensionActivity}
     *          <p>
     *              bpel extension activity for call sub-process
     *          <p>
     *          {/extensionActivity}
     *          <p>
     *          {SPOutput}
     *          <p>
     *              contains the script representation of the sub-process output mappings.
     *          <p>
     *          {/SPOutput}
     *          <p>
     *      {/sequence}
     * 
     * @return bpel equivalent of xpdl's Call Sub-Process activity
     * 
     * @throws ConversionException
     */
	public org.eclipse.bpel.model.Activity convertSubFlow() throws ConversionException {
		CallProcess callProcess = createCallProcess();

		org.eclipse.bpel.model.Scope scope = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createScope();
		org.eclipse.bpel.model.Sequence sequence = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createSequence();
		sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$
		
		/*
         * Boolean flags to track if we've configured input/output mappings via data mapper.
         */
        boolean isDataMapperExplicitlySetForInputMappings=ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory.getScriptGrammar(xpdlActivity, DirectionType.IN_LITERAL));
        boolean isDataMapperExplicitlySetForOutputMappings=ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory.getScriptGrammar(xpdlActivity, DirectionType.OUT_LITERAL));
        
		
		//setup parameter mappings
		EList inputs = callProcess.getSubProcessInput();
		EList outputs = callProcess.getSubProcessOutput();

		org.eclipse.bpel.model.Variables variables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
		List<FormalParameter> subProcParams = SubProcUtil.getSubProcessFormalParameters(xpdlActivity, null);
		
		
        /*
         * Sid ACE-1599 For ACE the sub-process params are wrapped in a
         * "parameters" data-field-descriptor object and therefore we don't need
         * to create _BX_xxx temporary variables or map them into the payload.
         * Run-time call-sub-process handling will explicitly use the data in
         * the "parameters" object
         * 
         * EXCEPT for Asynch-Sub-Process output mappings of __PROCESS_ID__ as
         * these still need to be mapped the old fashioned way with a temp var.
         */
		
        /*
         * Sid XPD-8224 - for Asynch sub-process calls with data-mapper grammar
         * on the outputs we need add the __PROCESS_ID__ parameter (so that it
         * gets included in the out mappings.
         */
        if (isDataMapperExplicitlySetForOutputMappings && XPDLUtils.getAsyncExecutionMode(getSubFlow()) != null) {
            FormalParameter process_id_param = StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER;
            
            outputs.addAll(getOutputListForSubProcParams(
                    Collections.singletonList(process_id_param)));
            
            addLocalVariable(variables, process_id_param, N2PEConstants.NAME_PREFIX + process_id_param.getName());
        }

        // if(isDataMapperExplicitlySetForInputMappings)
        // {
        // inputs.addAll(getInputListForSubProcParams(subProcParams));
        // }
        //
        // if (isDataMapperExplicitlySetForOutputMappings) {
        // outputs.addAll(getOutputListForSubProcParams(subProcParams));
        // }
        //
        // if (isDataMapperExplicitlySetForInputMappings ||
        // isDataMapperExplicitlySetForOutputMappings) {
        // for (FormalParameter eachSubProcParam : subProcParams) {
        //
        // addLocalVariable(variables, eachSubProcParam,
        // N2PEConstants.NAME_PREFIX + eachSubProcParam.getName());
        //
        // }
        // }
		
		/*
		 * JS Data Mappings.
		 */
		List<DataMapping> dataMappings = getSubFlow().getDataMappings();
		

		/*
		 * preCallAssign is created but may not be used (if peformJSDataMappingsConversion() does not find that input mappings are defined as JavaScript grammar.
		 */
        org.eclipse.bpel.model.Assign preCallAssign = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
        // preCallAssign.setName(context.makeUniqueActivityName("assign")); //$NON-NLS-1$
        preCallAssign.setName(context.generateActivityName("preAssign", xpdlActivity.getName(), xpdlActivity.getId()));
		
        /*
         * postCallAssign is created but may not be used (if peformJSDataMappingsConversion() does not find that output mappings are defined as JavaScript grammar.
         */
        org.eclipse.bpel.model.Assign postCallAssign = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
		// postCallAssign.setName(context.makeUniqueActivityName("assign")); //$NON-NLS-1$
        postCallAssign.setName(context.generateActivityName("postAssign", xpdlActivity.getName(), xpdlActivity.getId()));
        
        /*
         * Conversion of JS data mappings.
         */
        setupJavaScriptPrePostAssignActivities(isDataMapperExplicitlySetForInputMappings,
                isDataMapperExplicitlySetForOutputMappings,
                inputs,
                outputs,
                variables,
                subProcParams,
                dataMappings,
                preCallAssign,
                postCallAssign);
        
        /*
         * If data mapper is configured for input mappings, then add extension activity SPInput for that, otherwise add preAssign which is expected for JS input mappings.
         */
        if(isDataMapperExplicitlySetForInputMappings)
        {
            ExtensionActivity extActForDataMapperInputMappings=convertInputDataMappingsFromDataMapper();
            
            if(extActForDataMapperInputMappings!=null)
            {
                sequence.getActivities().add(extActForDataMapperInputMappings);
                XPDLUtils.findReferencedData(context, xpdlActivity, extActForDataMapperInputMappings.getName(), DataReferenceContext.CONTEXT_MAPPING_IN);
            }
        }else{
            if (!preCallAssign.getCopy().isEmpty()) {
                XPDLUtils.findReferencedData(context, xpdlActivity, preCallAssign.getName(), DataReferenceContext.CONTEXT_MAPPING_IN);
                sequence.getActivities().add(preCallAssign);
            }
        }

		org.eclipse.bpel.model.Activity extActivityForCallProcess = createActivityForCallProcess(callProcess);
		
        if (preCallAssign.getCopy().isEmpty() && postCallAssign.getCopy().isEmpty() &&!(isDataMapperExplicitlySetForInputMappings||isDataMapperExplicitlySetForOutputMappings)) {
        	//no temp variables; simply return the callProcess extension activity
          return extActivityForCallProcess;
        }

    	sequence.getActivities().add(extActivityForCallProcess);
    	
        /*
        * If data mapper is configured for output mappings, then add extension activity SPOutput for that, otherwise add postAssign which is expected for JS output mappings.
        */
       ExtensionActivity extActForDataMapperForOutputMappings=null;
       if(isDataMapperExplicitlySetForOutputMappings)
       {
           extActForDataMapperForOutputMappings=convertOutputDataMappingsFromDataMapper();
           
           if(extActForDataMapperForOutputMappings!=null)
           {
               XPDLUtils.findReferencedData(context, xpdlActivity, extActForDataMapperForOutputMappings.getName(), DataReferenceContext.CONTEXT_MAPPING_OUT);
               sequence.getActivities().add(extActForDataMapperForOutputMappings);
           }
       }else
       {
           if (!postCallAssign.getCopy().isEmpty()) {
             sequence.getActivities().add(postCallAssign);
           }
       }
       
       EList<DataField> localDataFields = xpdlActivity.getDataFields();
       if (localDataFields != null && !localDataFields.isEmpty()) {
            /* Sid ACE-2936 Add the dataField descriptor. This is here for completeness, we don't actually allow activity scope data fields on CallSubproc */
            BPELUtils.addActivityDataFieldDescriptorInfo(scope, xpdlActivity);
           
        	for (DataField dataField : localDataFields) {
				ProcessRelevantData prd = findProcessRelevantDataFromActivityInterfaceData(dataField.getName(), mainProcessData);
				if (prd != null) {
					addLocalVariable(variables, prd, dataField.getName());
				}
			}
        }
        
        scope.setVariables(variables);
        scope.setActivity(sequence);
        return scope;
	}

    /**
     * Convert JS Data Mappings to BPEL for setting up preAssign and postAssign activities.
     * <p>
     * Please note that this is an extraction from converCallProcess().
     * <p>
     * 
     * @param isDataMapperConfiguredForInputMappings
     * @param isDataMapperConfiguredForOutputMappings
     * @param inputs
     * @param outputs
     * @param variables
     * @param subProcParams
     * @param dataMappings
     * @param preCallAssign
     * @param postCallAssign
     * @throws ConversionException
     * 
     */
    private void setupJavaScriptPrePostAssignActivities(
            boolean isDataMapperConfiguredForInputMappings,
            boolean isDataMapperConfiguredForOutputMappings, EList<Mapping> inputs,
            EList<Mapping> outputs, org.eclipse.bpel.model.Variables variables,
            List<FormalParameter> subProcParams,
            List<DataMapping> dataMappings,
            org.eclipse.bpel.model.Assign preCallAssign,
            org.eclipse.bpel.model.Assign postCallAssign)
            throws ConversionException {
        for (DataMapping mapping : dataMappings) {
        	Copy copy = convertDataMappingToCopy(mapping, subProcParams);
            String subProcParamName = mapping.getFormal();
            Mapping map = ExtensionsFactory.eINSTANCE.createMapping();
			String field = copy == null ? mapping.getActual().getText() : N2PEConstants.NAME_PREFIX + subProcParamName;
            map.setField(field);
            map.setFormalParameter(subProcParamName);

            ProcessRelevantData subProcParam = findProcessRelevantDataFromProcessData(subProcParamName, subProcessData);
            
            int direction = mapping.getDirection().getValue();
            if (direction == DirectionType.IN || direction == DirectionType.INOUT) {
            	
            	/*
            	 * ABPM-1014: Saket: No need to process input mappings (there won't be any in this list anyways) if Data Mapper is configured for Input mappings.
            	 */
            	if(!isDataMapperConfiguredForInputMappings)
            	{
					inputs.add(map);
					if (copy != null) {
						preCallAssign.getCopy().add(copy);
						addLocalVariable(variables, subProcParam, field);
					}

            	}
            }
            //notice that the map between data field and formal parameter for INOUT is the same in both directions.
            if (direction == DirectionType.OUT || direction == DirectionType.INOUT) {
            	
            	/*
            	 * ABPM-1014: Saket: No need to process output mappings (there won't be any in this list anyways) if Data Mapper is configured for Output mappings.
            	 */
            	if(!isDataMapperConfiguredForOutputMappings)
            	{
					outputs.add(map);
					if (copy != null) {
						postCallAssign.getCopy().add(copy);
						addLocalVariable(variables, subProcParam, field);
					} else {
						// inline mapping; we mark this mapping to be mandatory
						// if the destination field (the main process parameter)
						// is required
						ProcessRelevantData mainProcParam = findProcessRelevantDataFromActivityInterfaceData(
								field, mainProcessData);
						if (mainProcParam instanceof FormalParameter) {
							map.setMandatory(((FormalParameter) mainProcParam)
									.isRequired());
						}
					}
            	}
            }
        }
    }

	/**
	 * Convert input mappings in the script data mapper to get a BPEL extension activity.
	 * <p>
	 * Basic structure of the extension activity would look like..
	 * <p>
	 * {bpws:extensionActivity}
	 * <p>
	 * {tibex:extActivity name="_BX_SPInput_CallSubProcess"}
	 * <p>
	 *     Contains the script representation of the sub-process input mappings wherein process data is mapped to temporary sub-process variables.
	 * <p>
	 * {/tibex:extActivity}
	 * <p>
	 * {/bpws:extensionActivity}
	 * <p>
	 *     
	 * @return BPEL extension activity with converted input mappings in the script data mapper
	 */
	private ExtensionActivity convertInputDataMappingsFromDataMapper() {
		
        org.eclipse.bpel.model.ExtensionActivity extensionAct = null;

        String script = new String();

        Object sdmIpObj =
                Xpdl2ModelUtil.getOtherElement(getSubFlow(),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InputMappings());

        if (sdmIpObj instanceof ScriptDataMapper) {

            ScriptDataMapper sdmInp = (ScriptDataMapper) sdmIpObj;

            String convertMappingsToJavascript =
                    new DataMapperJavascriptGenerator()
                            .convertMappingsToJavascript(sdmInp);

            if (convertMappingsToJavascript != null) {
                script = script.concat(convertMappingsToJavascript);
            }
        }

        if (script != null && !script.isEmpty()) {
            Script scriptForText = getScriptForText(script);

            extensionAct =
                    BPELUtils
                            .createExtensionActivityFromEmfObject(scriptForText,
                                    false);

            extensionAct
                    .setName(context
                            .generateActivityName("SPInput", xpdlActivity.getName(), xpdlActivity.getId())); //$NON-NLS-1$
        }

        return extensionAct;
	}

   /**
    * List of Call sub-process inputs which will have an input for every input parameter with field="_BX_Param" and formalparameter="Param".
    * 
    * @param subProcParams
    * 
    * @return List of Call sub-process inputs which will have an input for every input parameter with field="_BX_Param" and formalparameter="Param".
    */
    private List<Mapping> getInputListForSubProcParams(List<FormalParameter> subProcParams) {
        
        List<Mapping> inputs=new ArrayList<Mapping>();
        
        for (FormalParameter eachSubProcParam : subProcParams) {
            
		    if(ModeType.IN_LITERAL.equals(eachSubProcParam.getMode()) || ModeType.INOUT_LITERAL.equals(eachSubProcParam.getMode()))
		    {
		        Mapping map = ExtensionsFactory. eINSTANCE.createMapping();
		     
		        String subProcParamName = eachSubProcParam.getName();
		        String field = N2PEConstants.NAME_PREFIX + subProcParamName;
		        
		        map.setField(field);
		        map.setFormalParameter(subProcParamName);
		     
		        inputs.add(map);
		    }
        }
        
        return inputs;
    }
    
    /**
     * List of Call sub-process outputs which will have an output for every output parameter with field="_BX_Param" and formalparameter="Param".
     * 
     * @param subProcParams
     * 
     * @return List of Call sub-process outputs which will have an output for every output parameter with field="_BX_Param" and formalparameter="Param".
     */
     private List<Mapping> getOutputListForSubProcParams(List<FormalParameter> subProcParams) {
         
         List<Mapping> outputs=new ArrayList<Mapping>();
         
         for (FormalParameter eachSubProcParam : subProcParams) {
             
             if(ModeType.OUT_LITERAL.equals(eachSubProcParam.getMode()) || ModeType.INOUT_LITERAL.equals(eachSubProcParam.getMode()))
             {
                 Mapping map = ExtensionsFactory. eINSTANCE.createMapping();
              
                 String subProcParamName = eachSubProcParam.getName();
                 String field = N2PEConstants.NAME_PREFIX + subProcParamName;
                 
                 map.setField(field);
                 map.setFormalParameter(subProcParamName);
              
                 outputs.add(map);
             }
         }
         
         return outputs;
     }
	
	/**
	 * Convert output mappings in the script data mapper to get a BPEL extension activity.
	 * <p>
     * Basic structure of the extension activity would look like..
     * <p>
     * {bpws:extensionActivity}
     * <p>
     * {tibex:extActivity name="_BX_SPOutput_CallSubProcess"}
     * <p>
     *      Contains the script representation of the sub-process output mappings.
     * <p>
     * {/tibex:extActivity}
     * <p>
     * {/bpws:extensionActivity}
     * <p>
	 * 
	 * @return BPEL extension activity with converted output mappings in the script data mapper.
	 */
	 private ExtensionActivity convertOutputDataMappingsFromDataMapper() {
	        
	        org.eclipse.bpel.model.ExtensionActivity extensionAct=null;
	        
	        String script=new String();
	        
	            Object sdmOutpObj=Xpdl2ModelUtil.getOtherElement(getSubFlow(), XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings());
	            
	            if(sdmOutpObj instanceof ScriptDataMapper)
	            {
	            
	            ScriptDataMapper sdmOutp=(ScriptDataMapper)sdmOutpObj;
	            
	            String convertMappingsToJavascript = new DataMapperJavascriptGenerator().convertMappingsToJavascript(sdmOutp);
	            
	            if(convertMappingsToJavascript!=null)
	            {
	                script=script.concat(convertMappingsToJavascript);
	            }
	        }
	        
	        if(script!=null && !script.isEmpty())
	        {
	            Script scriptForText = getScriptForText(script);
	            
	            extensionAct=BPELUtils.createExtensionActivityFromEmfObject(scriptForText, false);
	            
	            extensionAct.setName(context.generateActivityName("SPOutput", xpdlActivity.getName(), xpdlActivity.getId())); //$NON-NLS-1$
	        }
	         
	         return extensionAct;
	    }

   /**
	* Construct a script for the specified script text.
	* 
	* @param text
	* 
	* @return a script for the specified script text.
	*/
	private Script getScriptForText(String text)
	{
	    Script script = ExtensionsFactory.eINSTANCE.createScript();
	    script.setExpressionLanguage(JSCRIPT_LANGUAGE);
	    script.setExpression(text);
	    return script;
	}
	
	private void addLocalVariable(org.eclipse.bpel.model.Variables variables,
			ProcessRelevantData param, String varName) throws ConversionException {
		if (param != null && !localVariables.contains(varName)) {
    		org.eclipse.bpel.model.Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
    		variable.setName(varName);
    		ConvertDataField.setDataTypeForVariable(xpdlActivity.getProcess(), variable, param.getDataType(), param.isIsArray());
    		variables.getChildren().add(variable);
    		
    		localVariables.add(varName);
		}
	}

	private Copy convertDataMappingToCopy(final DataMapping dataMapping, List<FormalParameter> subProcParams) {
		/* Example:
              <xpdl2:DataMappings>
                <xpdl2:DataMapping Direction="IN" Formal="p1">
                  <xpdl2:Actual ScriptGrammar="JavaScript">bomField.param1</xpdl2:Actual>
                </xpdl2:DataMapping>
                <xpdl2:DataMapping Direction="IN" Formal="p2">
                  <xpdl2:Actual ScriptGrammar="JavaScript">bomField.param2</xpdl2:Actual>
                </xpdl2:DataMapping>
                <xpdl2:DataMapping Direction="OUT" Formal="result">
                  <xpdl2:Actual ScriptGrammar="JavaScript">result</xpdl2:Actual>
                </xpdl2:DataMapping>
              </xpdl2:DataMappings>
		 */
		Expression actual = dataMapping.getActual();
		if (actual == null) {
			return null;
		}		
		
		boolean mappingOutFromSubProcess = DirectionType.OUT_LITERAL.equals(dataMapping.getDirection());
        String target = DataMappingUtil.getTarget(dataMapping);
        String script = DataMappingUtil.getScript(dataMapping);
        String grammar = DataMappingUtil.getGrammar(dataMapping);
		boolean isXPath = N2PEConstants.XPATH_1_LANGUAGE.equals(grammar);
    	boolean isScript = XPDLUtils.getScriptInformation(dataMapping) != null;
    	
    	if (isXPath && !isScript) {
    		return null;
    	}
    	
    	String mainExpr = mappingOutFromSubProcess ? target : script;
    	String subExpr = mappingOutFromSubProcess ? script : target;
    	
    	ProcessRelevantData mainPrd = findProcessRelevantDataFromActivityInterfaceData(mainExpr, mainProcessData);
    	ProcessRelevantData subPrd = findProcessRelevantDataFromProcessData(subExpr, subProcessData);
    	
    	if (subPrd == null) {
    		//shouldn't happen because this would mean that the subprocess parameter was not found
			return null;
    	}
    	
		if (mainPrd != null && !mainPrd.isIsArray() && !subPrd.isIsArray()) {
			//the fields in the mapping are both entire parameter/data field; 
			//we don't need to create a temp var
			return null;
		}
		
		org.eclipse.bpel.model.From from;
		org.eclipse.bpel.model.To to;
		String subParamName = N2PEConstants.NAME_PREFIX + subExpr; //$NON-NLS-1$ 
		if (mappingOutFromSubProcess) {
			boolean isToArray = isMainArray(mainExpr, mainPrd);
			boolean mappingFromSingleToArray = !subPrd.isIsArray() && isToArray;
			
			from = BPELUtils.createFromVariable(subParamName);
			BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
			to = CDSUtils.createToExpressionWithCDS(xpdlActivity, mainExpr, mappingFromSingleToArray);
		} else {
			boolean isFromArray = isMainArray(mainExpr, mainPrd);
			boolean mappingFromArrayToSingle = isFromArray && !subPrd.isIsArray();
    		Property property = CDSUtils.getProperty(xpdlActivity, mainExpr);
    		
            /*
             * Sid ACE-194 - we don't support XSD based BOMs in ACE
             */

			String expression;
			if (mappingFromArrayToSingle) {
				expression = mainExpr + ".get(Process.getActivityLoopIndex());"; //$NON-NLS-1$ 
			} else {
				expression = mainExpr;
			}
			from = BPELUtils.createFromScript(expression, grammar);
			to = BPELUtils.createToVariable(subParamName);
		}
			
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		copy.setTo(to);
		copy.setFrom(from);
    	for (FormalParameter param : subProcParams) {
			if (param.getName().equals(subExpr)) {
				if (!param.isRequired()) {
					copy.setIgnoreMissingFromData(true);
				}
				break;
			}
		}
		
		return copy;
	}

	private ProcessRelevantData findProcessRelevantDataFromActivityInterfaceData(String mainExpr, Collection<ActivityInterfaceData> allData) {
		for (ActivityInterfaceData aid : allData) {
			if (mainExpr.equals(aid.getName())) {
				return aid.getData();
			}
		}
		return null;
	}

	private ProcessRelevantData findProcessRelevantDataFromProcessData(String mainExpr, Collection<ProcessRelevantData> allData) {
		for (ProcessRelevantData prd : allData) {
			if (mainExpr.equals(prd.getName())) {
				return prd;
			}
		}
		return null;
	}

	private boolean isMainArray(String mainExpr, ProcessRelevantData mainPrd) {
		boolean isToArray;
		if (mainPrd != null) {
			isToArray = mainPrd.isIsArray();
		} else {
			Property property = CDSUtils.getProperty(xpdlActivity, mainExpr);
			isToArray = property != null && (property.getUpper() > 1 || property.getUpper() == -1);
		}
		return isToArray;
	}

}
