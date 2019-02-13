package com.tibco.bx.debug.bpm.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.bx.debug.bpm.core.util.BPMProcessUtil;
import com.tibco.bx.debug.core.runtime.IProcessModelHandler;
import com.tibco.bx.debug.core.ws.finder.WSProperties;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

public class BxProcessModelHandler implements IProcessModelHandler{

	@Override
	public EObject getActivity(EObject process, String activityId) {
		//TODO
		return ((Process)process).getActivity(activityId);
	}

	@Override
	public EObject getLink(EObject process, String linkId) {
		int pos = linkId.indexOf(" to ");
		if (pos < 0) {
			return ((Process) process).getTransition(linkId);
		} else {
			String[] fromTo = linkId.split(" to ");
			String from = fromTo[0].trim();
			String to = fromTo[1].trim();
			List<Transition> transitions = ((Process) process).getTransitions();
			for (Transition transition : transitions) {
				if (transition.getFrom().equals(from) && transition.getTo().equals(to)) {
					return transition;
				}
			}
		}

		return null;
	}
	
	@Override
	public List<EObject> getAllVariables(EObject process) {
		List<ProcessRelevantData> pList = getParms((Process)process);
		List<DataField> fList = ((Process)process).getPackage().getDataFields();
		pList.addAll(fList);
		List<EObject> ret = new ArrayList<EObject>();
		for (ProcessRelevantData object : pList) {
			ret.add((EObject)object);
		}
		return ret;
	}

	@Override
	public EObject[] getCanStartActivities(EObject currentProcess) {
		return getCanStartActivities((Process)currentProcess);
	}

	/*
	 * get display name of all kinds of activities, processes and links
	 */
	@Override
	public String getDisplayName(EObject element) {
		String text =  "";//process must have it's name
		if(element instanceof Process){
			return getDisplayName((Process)element);
		}else if(element instanceof Activity){
			text = getDisplayName((Activity)element);
			if(text == null || "".equals(text)){  //$NON-NLS-1$
	        	if(element instanceof Activity){
	        		if(((Activity)element).getEvent() != null){
	             		text = " Start Event";//$NON-NLS-1$
	             	}else if(((Activity)element).getImplementation() != null){
	             		text = " Receive Task";//$NON-NLS-1$
	             	}else{
	             		//TODO
	             		text = "";
	             	}
	        	}
			}else if(element instanceof Transition){
				Process process = ((Transition)element).getProcess();
				EObject from = process.eResource().getEObject(((Transition)element).getFrom());
				EObject to = process.eResource().getEObject(((Transition)element).getTo());
				text = getDisplayName((OtherAttributesContainer)from) + " - " + getDisplayName((OtherAttributesContainer)to);//$NON-NLS-1$
			}
		}
		return text;
	}

	@Override
	public String getElementId(EObject element) {
		String id = null;
		if(element instanceof UniqueIdElement){
			return id = ((UniqueIdElement)element).getId();
		}
		return id;
	}

	@Override
	public String getElementName(EObject element) {
		String name = null;
		if(element instanceof NamedElement){
			return name = ((NamedElement)element).getName();
		}
		return name;
	}

	@Override
	public EObject getLink(String linkId, EObject process) {
		return ((Process)process).eResource().getEObject(linkId);
	}

	@Override
	public EObject getProcess(String processId) {
		return Xpdl2WorkingCopyImpl.locateProcess(processId);
	}

	@Override
	public Class getProcessClass() {
		return Xpdl2Package.eINSTANCE.getProcess().getInstanceClass();
	}

	@Override
	public EObject[] getProcesses(IFile file) {
		WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
		EObject[] ret = new EObject[0];
        if (wc != null && !wc.isInvalidFile()){
        	EObject pack = wc.getRootElement();
        	EList<Process> list =((com.tibco.xpd.xpdl2.Package)pack).getProcesses();
        	ret = new EObject[list.size()];
        	for (int i = 0; i < ret.length; i++) {
        		ret[i] = list.get(i);
			}
        }
		return ret;
	}

	@Override
	public String getVariableTypeName(EObject variable) {
		String typeName = null;
		DataType type = ((ProcessRelevantData)variable).getDataType();
		if(type instanceof BasicType){
			typeName =  ((BasicType)type).getType().getName();
		}else if(type instanceof ExternalReference){
			String bomClassName = XPDLUtils.getBomClassName((ExternalReference)type);
			int lastDot = bomClassName.lastIndexOf("."); //$NON-NLS-1$
			typeName=  bomClassName.substring( lastDot +1 )+ " - " + bomClassName.substring(0, lastDot); //$NON-NLS-1$
		}else if(type instanceof DeclaredType){
			String id = ((DeclaredType)type).getTypeDeclarationId();
			EObject object = WorkingCopyUtil.getWorkingCopyFor(type).resolveEObject(id);
			if(object != null && object instanceof TypeDeclaration){
				typeName = ((TypeDeclaration)object).getName();
			}
			typeName = object.toString();
		}else{
			typeName = type.toString();
		}
		return typeName;
	}

	@Override
	public String getVariableName(EObject variable) {
		return ((ProcessRelevantData)variable).getName();
	}
	
	@Override
	public boolean isArrayVariable(EObject variable) {
		return ((ProcessRelevantData)variable).isIsArray();
	}

	@Override
	public boolean isIntermediateEvent(EObject activity) {
		return (((Activity)activity).getEvent() instanceof IntermediateEvent) || isReceiveActivity((Activity)activity);
	}

	@Override
	public boolean isWebServiceImplementationActivity(EObject startActivity) {
		Activity activity = (Activity)startActivity;
		if (activity != null && activity.getEvent() != null ) {
			Event event = activity.getEvent();
			EObject impType =  event.getEventTriggerTypeNode();
			if(impType instanceof TriggerResultMessage){
				return true;
			}else if(impType == null){
				return false;
			}
			return true;
			            
		}else if (activity != null && activity.getImplementation() instanceof Task ) {
			TaskReceive taskReceive = ((Task)activity.getImplementation()).getTaskReceive();
			ImplementationType type = (ImplementationType) taskReceive.eGet(
					Xpdl2Package.eINSTANCE.getTaskReceive_Implementation());
			return type == ImplementationType.WEB_SERVICE_LITERAL;
			            
		}

		return false;
	}

	@Override
	public WSProperties getWSProperties(EObject startActivity){
		WebServiceOperation wsOperation = BPMProcessUtil.getWebServiceOperationInXpdl((Activity)startActivity);
		WSProperties properties = null;
		if(wsOperation != null){
			properties = new WSProperties();
			Service service = wsOperation.getService();
			PortTypeOperation portTypeOperation = XPDLUtils.getPortTypeOperation(wsOperation);
			properties.setServiceName(service.getServiceName());
			properties.setPortName(service.getPortName());
			properties.setPortTypeName(portTypeOperation.getPortTypeName());
			properties.setOperationName(portTypeOperation.getOperationName());
			FeatureMap attributes = service.getOtherAttributes();
			properties.setIsRemoteService(((Boolean) attributes.get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsRemote(), true)).booleanValue());
		}
		return properties;
	}
	
	@Override
	public String getWebServiceUri(EObject startActivity){
		return  XPDLUtils.resolveWebServiceUri((Activity)startActivity);
	}
	
	/*
	 * data fields and parameters in process and data fields in package
	 */
	private  List<ProcessRelevantData> getAllParms(Process process ){
		List<ProcessRelevantData> pList = getParms(process);
		List<DataField> fList = process.getPackage().getDataFields();
		pList.addAll(fList);
		return pList;
	}
	
	private  List<ProcessRelevantData> getParms(Process process ){
		List<ProcessRelevantData> pList = new ArrayList<ProcessRelevantData>();
		List<FormalParameter> fList = process.getFormalParameters();
		ProcessInterface processInterface = getImplementedInterface(process);
		if(processInterface != null){
			pList.addAll(processInterface.getFormalParameters());
		}
		List<DataField> dList = process.getDataFields();
		pList.addAll(fList);
		pList.addAll(dList);
		return pList;
	}
	
	private  List<FormalParameter> getInputParms(Process process ){
		List<FormalParameter> pList = new ArrayList<FormalParameter>();
		for(FormalParameter parameter : process.getFormalParameters()){
			if(isInputParameter(parameter))
				pList.add(parameter);
		}
		ProcessInterface processInterface = getImplementedInterface(process);
		if(processInterface != null){
			for(FormalParameter parameter : processInterface.getFormalParameters()){
				if(isInputParameter(parameter))
					pList.add(parameter);
			}
		}
		return pList;
	}
	
	private  List<FormalParameter> getOutputParms(Process process ){
		List<FormalParameter> pList = new ArrayList<FormalParameter>();
		for(FormalParameter parameter : process.getFormalParameters()){
			if(isOutputParameter(parameter))
				pList.add(parameter);
		}
		ProcessInterface processInterface = getImplementedInterface(process);
		if(processInterface != null){
			for(FormalParameter parameter : processInterface.getFormalParameters()){
				if(isOutputParameter(parameter))
					pList.add(parameter);
			}
		}
		return pList;
	}
	
	private  ProcessInterface getImplementedInterface(Process process){
		BasicFeatureMap map = ((BasicFeatureMap)process.getOtherElements());
		for(int i = 0;i< map.size(); i++){
			Object object = map.get(i).getValue();
			if(object instanceof ImplementedInterface){
				String processInterfaceId = ((ImplementedInterface)object).getProcessInterfaceId();
				return Xpdl2WorkingCopyImpl.locateProcessInterface(processInterfaceId); 
			}
		}
		return null;
	}
	
	private  boolean isInputParameter(FormalParameter parameter){
		return parameter.getMode() == ModeType.IN_LITERAL || parameter.getMode() == ModeType.INOUT_LITERAL;
	}
	
	private  boolean isOutputParameter(FormalParameter parameter){
		return parameter.getMode() == ModeType.OUT_LITERAL || parameter.getMode() == ModeType.INOUT_LITERAL;
	}
	
	private  EObject[] getCanStartActivities(Process process){
		process.findStartActivities();
		EList<Activity> activities = process.getActivities();
		List<EObject> startEvents = new ArrayList<EObject>();
		for(Activity activity : activities){
			Event event = activity.getEvent();
			if(event instanceof StartEvent){
				startEvents.add(activity);
			}else if(event instanceof IntermediateEvent){
				IntermediateEvent intermediateEvent = (IntermediateEvent) event;
				TriggerType triggerType = intermediateEvent.getTrigger();
				if( triggerType.getValue() == TriggerType.MESSAGE ){
					String target = intermediateEvent.getTarget();
					if( target == null){
						CatchThrow catchThrow = intermediateEvent.getTriggerResultMessage().getCatchThrow();
						if(catchThrow == CatchThrow.CATCH){
							startEvents.add(activity);
						}
					}
				}
			}else if(event == null){
				
				if(isReceiveActivity(activity)){
					startEvents.add(activity);
				}
			}
		}
		
		return  startEvents.toArray(new EObject[0]);
	}
	
	private  boolean isReceiveActivity(Activity activity) {
		Implementation implement = activity.getImplementation();
		if(implement != null && implement instanceof Task){
			Task task = (Task) implement;
			TaskReceive receive =  task.getTaskReceive();
			if(receive != null && receive.getImplementation() == ImplementationType.WEB_SERVICE_LITERAL){
				return true;
			}
		}
		return false;
	}
	
	private  String getLabel(String xpdlId, Process process){
		if(process.getId().equals(xpdlId)){
			return getDisplayName(process);
		}else{
			EObject object = process.eResource().getEObject(xpdlId);
			if(object instanceof Activity){
				return getDisplayName((OtherAttributesContainer)object);
			}else if(object instanceof Transition){
				EObject from = process.eResource().getEObject(((Transition)object).getFrom());
				EObject to = process.eResource().getEObject(((Transition)object).getTo());
				return getDisplayName((OtherAttributesContainer)from) + " - " + getDisplayName((OtherAttributesContainer)to);//$NON-NLS-1$
			}
			return "";//$NON-NLS-1$
		}
	}
	
	private  String getDisplayName(OtherAttributesContainer attributesContainer){
		FeatureMap featureMap = attributesContainer.getOtherAttributes();
		int size = featureMap.size();
		for(int i =0; i< size; i++){
			EStructuralFeature feature = featureMap.get(i).getEStructuralFeature();
			if(feature.getName().equals("displayName")){//$NON-NLS-1$
				return (String)featureMap.getValue(i);
			}
		}
		return "";//$NON-NLS-1$
	}
	
	public static Operation getOperationFromWorkSpace(Activity startActivity){
	   /*
	    * Sid ACE-180: We should never be asked for WebServices any more in ACE
	    */
// 	   WsdlServiceKey serviceKey = ProcessUIUtil.getSpecificWsdlServiceKey(startActivity);
// 	   IProject project = WorkingCopyUtil.getProjectFor(startActivity);
// 	   return (Operation) WsdlIndexerUtil.getOperation(project, serviceKey, true, true);
	    return null;
    }

	@Override
	public boolean isEndActivity(EObject activity) {
		boolean isEnd = false;
		if (activity != null && activity instanceof Activity) {
			List<Transition> tList = ((Activity) activity).getOutgoingTransitions();
			if (tList.isEmpty()) {
				isEnd = true;
			}
		}
		return isEnd;
	}

}
