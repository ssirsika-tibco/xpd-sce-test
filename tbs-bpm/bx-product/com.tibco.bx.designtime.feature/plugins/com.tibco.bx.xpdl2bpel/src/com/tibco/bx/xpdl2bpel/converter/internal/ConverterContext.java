package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import javax.wsdl.Import;
import javax.xml.namespace.QName;

import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.internal.impl.ImportImpl;
import org.eclipse.xsd.XSDSchema;

import com.tibco.bx.bpelExtension.BxExtensionRegistry;
import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.Task;
import com.tibco.bx.bpelExtension.extensions.impl.CallProcessImpl;
import com.tibco.bx.bpelExtension.extensions.impl.GlobalSignalImpl;
import com.tibco.bx.bpelExtension.extensions.impl.IntermediateEventImpl;
import com.tibco.bx.bpelExtension.extensions.impl.ScriptImpl;
import com.tibco.bx.bpelExtension.extensions.impl.SignalEventImpl;
import com.tibco.bx.bpelExtension.extensions.impl.SignalImpl;
import com.tibco.bx.bpelExtension.extensions.impl.StartEventImpl;
import com.tibco.bx.bpelExtension.extensions.impl.TimerEventImpl;
import com.tibco.bx.bpelExtension.extensions.misc.CallProcessSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.GlobalSignalSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.IntermediateEventSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.ScriptSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.SignalEventSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.SignalSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.StartEventSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.TimerEventSerializer;
import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.analyzer.Analyzer;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.WebServiceOperation;

public class ConverterContext {

	private static final String SOURCE_ACTIVITY_DEFAULT_NAME = "Source Activity"; //$NON-NLS-1$
	private static final String TARGET_ACTIVITY_DEFAULT_NAME = "Target Activity"; //$NON-NLS-1$
    public static final String LINK_CONNECTOR_WORD = " to "; //$NON-NLS-1$

    private Analyzer analyzer = null;
    
    private boolean isPageFlowEngineTarget = false;
    private WorkingCopy workingCopy;
    private EObject xpdlModelRoot;
	private Package topPackage;
	private String outputFilePath;
    private MultiStatus multiStatus = new MultiStatus(ConverterActivator.PLUGIN_ID, IStatus.OK, "XPDL-to-BPEL conversion error", null);
    /** An int that is added to name Strings to make them unique. */
    private int uniqueGenerationValue = 1;

    /** A Map from the XPDL Activity ID to the XPDL Activity. */
	private Map<String, Activity> xpdlActivityMap = new HashMap<String, Activity>();

    /** A Map of names (currently link names) that are "taken" (already used). */
	private Map<String, String> m_takenLinkNames = new HashMap<String, String>();

    /** A Map from the XPDL id to the XPDL Participant. */
	private Map<String, Participant>  m_participantMap = new HashMap<String, Participant> ();

	private Map<String, org.eclipse.bpel.model.PartnerLink> m_partnerLinks = new HashMap<String, org.eclipse.bpel.model.PartnerLink>();
	private Map<EObject, org.eclipse.bpel.model.Variables> m_variableMap = new HashMap<EObject, org.eclipse.bpel.model.Variables>();
	private Map<EObject, Map<String, org.eclipse.bpel.model.messageproperties.Property>> m_propertyMap = new HashMap<EObject, Map<String, org.eclipse.bpel.model.messageproperties.Property>>();
	private Map<String, org.eclipse.bpel.model.partnerlinktype.PartnerLinkType> m_partnerLinkTypes = 
		new HashMap<String, org.eclipse.bpel.model.partnerlinktype.PartnerLinkType>();

    // A map of XPDL activity ids to XPDL ids of that activity's boundary events
    private Map<String, List<String>> boundaryEvents = new HashMap<String, List<String>>();
    // The set of XPDL activity ids for looping activities
    private Set<String> loopActivities = new HashSet<String>();

    private Set<String> takenActivityNames = new HashSet<String>();
    private Set<String> takenVariableNames = new HashSet<String>();
    private Stack<UniqueIdElement> currentElements = new Stack<UniqueIdElement>();
    
    private Map<QualifiedName, WebServiceOperationInfo> serviceOperationMap = new HashMap<QualifiedName, WebServiceOperationInfo>();
    private Map<QualifiedName, WebServiceOperationInfo> pageflowServiceOperationMap = new HashMap<QualifiedName, WebServiceOperationInfo>();

    List<Transition> obsoleteTransitions = new ArrayList<Transition>();

    private Map<String, org.eclipse.bpel.model.CorrelationSet> correlations = new HashMap<String, org.eclipse.bpel.model.CorrelationSet>();
    // Map of Map
    private Map<URI, Map<String, org.eclipse.bpel.model.messageproperties.Property>> properties = new LinkedHashMap<URI, Map<String, org.eclipse.bpel.model.messageproperties.Property>>();
    // Map of List
    private Map<URI, Map<QualifiedName,org.eclipse.bpel.model.messageproperties.PropertyAlias>> propertyAliases = new LinkedHashMap<URI, Map<QualifiedName,org.eclipse.bpel.model.messageproperties.PropertyAlias>>();
    private boolean useExplicitCompensation = true;   // Default should be true to match BPMN compensation

    private Map<URI,Definition> wsdlMap = new HashMap<URI, Definition>();
    
    private Map<String, Definition> processToFaultDefinitionMap = new HashMap<String, Definition>();
    
    private Map<String, String> prefixToNsMap = new HashMap<String, String>();
    private Map<String, String> nsToPrefixMap = new HashMap<String, String>();
    
    private Map<String, Definition> srcWSDLMap = new HashMap<String, Definition>(); // targetNamespace to the source WSDL
    
	private Map<String, Task> taskDescriptors = new HashMap<String, Task>();
    
	private Map<String, ExtensibleElement> xpdlToBpelMap = new HashMap<String, ExtensibleElement>();
    
    /**
     * Sid XPD-8199 - The next number to use for a new _BX_fanInSetup activity
     */
    private int nextFlowFanInNumber = 1;

    /**
     * Sid XPD-8199 - The next number to use for a new _BX_fanOutSetup activity
     */
    private int nextFlowFanOutNumber = 1;

    /**
     * Initiate the converter context from an XPDL file
     * @param xpdlFile the XPDL file to convert
     */
    public ConverterContext(IFile xpdlFile) {
    	workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
        registerSerializers();
		setXpdlModelRoot(workingCopy != null ? workingCopy.getRootElement() : null);
	}
    
    /**
     * Initiate the converter context from an XPDL instance
     * @param root XPDL model root
     */
    public ConverterContext(EObject root) {
    	workingCopy = WorkingCopyUtil.getWorkingCopyFor(root);
        registerSerializers();
        setXpdlModelRoot(root);
	}

    private void registerSerializers() {
    	BxExtensionRegistry bxExtensionRegistry = ConverterActivator.getBxExtensionRegistry();
        if (bxExtensionRegistry.getSerializer(StartEventImpl.class)==null) {
        	bxExtensionRegistry.registerSerializer(SignalEventImpl.class, new SignalEventSerializer());
        	bxExtensionRegistry.registerSerializer(TimerEventImpl.class, new TimerEventSerializer());
        	bxExtensionRegistry.registerSerializer(StartEventImpl.class, new StartEventSerializer());
        	bxExtensionRegistry.registerSerializer(SignalImpl.class, new SignalSerializer());
        	bxExtensionRegistry.registerSerializer(CallProcessImpl.class, new CallProcessSerializer());
        	bxExtensionRegistry.registerSerializer(ScriptImpl.class, new ScriptSerializer());
        	bxExtensionRegistry.registerSerializer(IntermediateEventImpl.class, new IntermediateEventSerializer());
        	bxExtensionRegistry.registerSerializer(GlobalSignalImpl.class, new GlobalSignalSerializer());
        }
    }

    private void setXpdlModelRoot(EObject xpdlModelRoot) {
		this.xpdlModelRoot = xpdlModelRoot;
		if (xpdlModelRoot instanceof Package) {
            topPackage = (Package) xpdlModelRoot;
			initPackageInfo(topPackage);
        }
	}

	public WorkingCopy getWorkingCopy() {
		return workingCopy;
	}
	
	public IProject getProject() {
		return workingCopy != null ? workingCopy.getEclipseResources().get(0).getProject() : null;
	}

	public EObject getXpdlModelRoot() {
		return xpdlModelRoot;
	}
	
	public Package getXpdlPackage() {
		return topPackage;
	}
	
	public void setOutputFilePath(String newPath) {
		this.outputFilePath = newPath;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void logError(final String message, final Throwable exception) {
        UniqueIdElement currentElement = getCurrentElement();
		String xpdlId = currentElement != null ? currentElement.getId() : ""; //$NON-NLS-1$
		logError(xpdlId, message, exception);
    }

	public void logError(final String xpdlId, final String message, final Throwable exception) {
		if (exception instanceof CoreException) {
			CoreException ce = (CoreException) exception;
			IStatus status = ce.getStatus();
			if (status.isMultiStatus()) {
				for (IStatus child : status.getChildren()) {
					multiStatus.add(new ConversionStatus(xpdlId, child));
				}
			} else {
				multiStatus.add(new ConversionStatus(xpdlId, status));
			}
		} else {
			multiStatus.add(new ConversionStatus(xpdlId, IStatus.ERROR, message, exception));
		}
    }

	public IStatus getConversionStatus() {
		return multiStatus;
	}
	
    /** Traverse the XPDL model and build any information that will be needed
     * for conversion.
     * @param topPackage The root Package to create info for. */
    private void initPackageInfo(final Package  topPackage) {
        initParticipantInfo((List<Participant>) topPackage.getParticipants());
		initProcessInfo((List<Process>) topPackage.getProcesses());
    }

    /**
	 * Traverse the XPDL participant List and add participant info so
	 * participants can be queried.
	 * @param participants A List of Participants to build info for.
	 */
    private void initParticipantInfo (final List<Participant> participants) {
        if (participants != null) {
			for (Participant participant : participants) {
				m_participantMap.put(participant.getId(), participant);
			}
		}
    }

    /**
	 * Traverse the XPDL model and build any information that will be needed for conversion.
	 * @param processes A List of XPDL processes to create info for.
	 */
    private void initProcessInfo(final List<Process> processes) {
        if (processes != null) {
			for (Process process : processes) {
				initParticipantInfo(process.getParticipants());
                List<ActivitySet> activitySets = (List<ActivitySet>) process.getActivitySets();
				if (activitySets != null) {
					for (ActivitySet activitySet : activitySets) {
						initActivityInfo((List<Activity>) activitySet.getActivities());
					}
				}
				initActivityInfo((List<Activity>) process.getActivities());
			}
		}
    }

    /** 
     * Traverse the XPDL model and build any information that will be needed for conversion.
     * @param xpdlActivities A List of XPDL processes to create info for. 
     */
    private void initActivityInfo(final List<Activity> xpdlActivities) {
		if (xpdlActivities != null) {
			for (Activity xpdlActivity : xpdlActivities) {
				addXpdlActivity(xpdlActivity);
			}
		}
	}

    /** Get the XPDL activity with the ID requested within the map of
     * activities read in so far.
     * @param activityId The ID of the activity to find.
     * @return The Activity with the requested ID. */
    public Activity getXpdlActivityWithID(final String activityId) {
        return xpdlActivityMap.get(activityId);
    }
    
    public void addXpdlActivity(Activity xpdlActivity) {
    	xpdlActivityMap.put(xpdlActivity.getId(), xpdlActivity);
    }
    
    /** 
     * Get the XPDL Participant for the XPDL Activity parameter.
     * @param performer The performer to get the Participant for.
     * @return The XPDL Participant matching the Performer in the XPDL
     * Activity passed in. 
     */
    public Participant getParticipantFromActivityPerformer (final Performer performer) {
        return performer != null ? m_participantMap.get(performer.getValue()) : null;
    }

    public Map<String, Participant> getParticipantMap() {
        return m_participantMap;
    }

    public boolean isUseExplicitCompensation() {
        return useExplicitCompensation;
    }

    public void setUseExplicitCompensation(boolean useExplicitCompensation) {
        this.useExplicitCompensation = useExplicitCompensation;
    }

    /**
     * Create a unique BPEL link name based on the current XPDL transition and linked activities. 
     */
    public String getUniqueLinkName (
    		Transition transition, 
    		org.eclipse.bpel.model.Activity sourceActivity, 
    		org.eclipse.bpel.model.Activity targetActivity) {
    	String transitionName = transition!=null ? transition.getName() : null;
        if (!isNullString(transitionName)) {
        	return getUniqueName(transitionName, m_takenLinkNames);
        }
        
    	String sourceActivityName = sourceActivity.getName();
        String targetActivityName = targetActivity.getName();
        if (isNullString(sourceActivityName)) {
        	sourceActivityName = BPELUtils.getXpdlId(sourceActivity);
        }
        if (isNullString(sourceActivityName)) {
        	sourceActivityName = SOURCE_ACTIVITY_DEFAULT_NAME;
        }
        if (sourceActivityName.length()>28) {
        	if (!isNullString(BPELUtils.getXpdlId(sourceActivity))) {
        		sourceActivityName = BPELUtils.getXpdlId(sourceActivity);
        	} else {
        		sourceActivityName = sourceActivityName.substring(0,28);
        	}
        }
        
        if (isNullString(targetActivityName)) {
        	targetActivityName = BPELUtils.getXpdlId(targetActivity);
        }
        if (isNullString(targetActivityName)) {
        	targetActivityName = TARGET_ACTIVITY_DEFAULT_NAME;
        }
        if (targetActivityName.length()>28) {
        	if (!isNullString(BPELUtils.getXpdlId(targetActivity))) {
        		targetActivityName = BPELUtils.getXpdlId(targetActivity);
        	} else {
        		targetActivityName = targetActivityName.substring(0,28);
        	}
        }
        
        String linkName = sourceActivityName + LINK_CONNECTOR_WORD + targetActivityName;
        return getUniqueName(linkName, m_takenLinkNames);
    }

	public void addPartnerLinkType(org.eclipse.bpel.model.partnerlinktype.PartnerLinkType partnerLinkType) {
		m_partnerLinkTypes.put(partnerLinkType.getName(), partnerLinkType);
	}

    public org.eclipse.bpel.model.partnerlinktype.PartnerLinkType getPartnerLinkType(String portTypeName) {
        return m_partnerLinkTypes.get(portTypeName);
    }
    
    public org.eclipse.bpel.model.PartnerLinks getPartnerLinks() {
    	org.eclipse.bpel.model.PartnerLinks ret = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createPartnerLinks();
    	ret.getChildren().addAll(m_partnerLinks.values());
    	return ret;
    }

	public void addPartnerLink(org.eclipse.bpel.model.PartnerLink partnerLink) {
		if (!m_partnerLinks.containsKey(partnerLink.getName())) {
			m_partnerLinks.put(partnerLink.getName(), partnerLink);
		}
	}

    public org.eclipse.bpel.model.Variables getLocalVariables(EObject parent) {
    	//use EcoreUtil.Copier to clone the variables so that the original variables don't get re-assigned to a new parent
    	org.eclipse.bpel.model.Variables ret = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
    	org.eclipse.bpel.model.Variables variables = m_variableMap.get(parent);
    	if (variables != null) {
			Copier copier = new Copier();
			Collection<org.eclipse.bpel.model.Variable> vars = copier.copyAll(variables.getChildren());
			copier.copyReferences();
			
    		ret.getChildren().addAll(vars);
    	}
    	return ret;
	}

    public org.eclipse.bpel.model.Variables getVariables(EObject parent) {
    	//use EcoreUtil.Copier to clone the variables so that the original variables don't get re-assigned to a new parent
    	org.eclipse.bpel.model.Variables ret = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
    	org.eclipse.bpel.model.Variables variables = m_variableMap.remove(parent);
    	if (variables != null) {
			Copier copier = new Copier();
			Collection<org.eclipse.bpel.model.Variable> vars = copier.copyAll(variables.getChildren());
			copier.copyReferences();
			
    		ret.getChildren().addAll(vars);
    	}

    	if (parent instanceof Process) {
	    	Package topPackage = ((Process)parent).getPackage();
    		ret.getChildren().addAll(getLocalVariables(topPackage).getChildren());
		}
    	return ret;
    }

	public void setVariables(EObject parent, org.eclipse.bpel.model.Variables variables) {
    	org.eclipse.bpel.model.Variables currVariables = m_variableMap.get(parent);
    	if (currVariables == null) {
    		currVariables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
    		m_variableMap.put(parent, currVariables);
    	}
    	currVariables.getChildren().addAll(variables.getChildren());
		m_variableMap.put(parent, currVariables);
	}
	
	/**
	 * Adds the variable to the list of variables of the current process.
	 * @param
	 */
	public void addVariable(org.eclipse.bpel.model.Variable variable) {
		UniqueIdElement currElement = getCurrentElement();
		if (currElement != null) {
	    	org.eclipse.bpel.model.Variables variables = m_variableMap.get(currElement);
	    	if (variables == null) {
	    		variables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
	    		m_variableMap.put(currElement, variables);
	    	} else {
	    		//BX-2287: guard against duplicate variables
	    		for (org.eclipse.bpel.model.Variable var : variables.getChildren()) {
	    			if (variable.getName().equals(var.getName())) {
	    				return;
	    			}
				}
	    	}
    		variables.getChildren().add(variable);
		}
	}

	public void setProperties(EObject parent, Map<String, org.eclipse.bpel.model.messageproperties.Property> properties) {
		Map<String, org.eclipse.bpel.model.messageproperties.Property> currProperties = m_propertyMap.get(parent);
    	if (currProperties == null) {
    		//currProperties = properties;
    		m_propertyMap.put(parent, properties);
    	} else {
	    	currProperties.putAll(properties);
    	}
	}

	public Map<String, org.eclipse.bpel.model.messageproperties.Property> getProperties(EObject parent) {
		return m_propertyMap.get(parent);
	}
	
	public org.eclipse.bpel.model.messageproperties.Property addProperty(URI wsdlLocation, org.eclipse.bpel.model.messageproperties.Property property) {
		String key = property.getName();

		Map<String, Property> propertiesMap = getPropertiesMap(wsdlLocation);
		Property prop = propertiesMap.get(key);
		if (prop != null) {
			return prop;
		}
		
		boolean inOtherWSDL = false;
		Collection<Map<String, Property>> allMaps = properties.values();
		for (Map<String, Property> map : allMaps) {
			if (map == propertiesMap) continue;
			Property otherProp = map.get(key);
			if (otherProp != null && otherProp.getName().equals(property.getName())) {
				inOtherWSDL = true;
			}
		}
		prop = inOtherWSDL ? (Property) EcoreUtil.copy(property) : property;
		propertiesMap.put(key, prop);			
		
		return prop;
	}

	public org.eclipse.bpel.model.CorrelationSet getCorrelationSet(String name) {
		org.eclipse.bpel.model.CorrelationSet correlationSet = correlations.get(name);
		if (correlationSet == null) {
			correlationSet = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createCorrelationSet();
            correlationSet.setName(name);	// need to be unique within the correlationSets
            correlations.put(name, correlationSet);
		}
		return correlationSet;
	}
    /**
     * Adds the event as a boundary event to the parent
     * @param parentId
     * @param eventId
     * @return number of boundary events for this parent
     */
    public int addBoundaryEvent(String parentId, String eventId) {
        List<String> list = boundaryEvents.get(parentId);
        if (list==null) {
            // none yet for this parent
            list = new ArrayList<String>();
            boundaryEvents.put(parentId, list);
        }
        list.add(eventId);
        return list.size();
    }

    public Map<String, List<String>> getBoundaryEvents() {
        return boundaryEvents;
    }


    public Set<String> getLoopActivities() {
        return loopActivities;
    }

    public void addLoopActivity(String activityId) {
        this.loopActivities.add(activityId);
    }

    public void setActivityName(NamedElement xpdlElement, org.eclipse.bpel.model.Activity bpelActivity, String proposedName) {
        String name = bpelActivity.getName();
        if (xpdlElement!=null) {
            name = xpdlElement.getName();
        }
        if (isNullString(name)) {
            // if no xpdl name, try bpel name
            name = bpelActivity.getName();
            if (isNullString(name)) {
                name = genUniqueActivityName(proposedName);
            } else {
                name = makeUniqueActivityName(name);
            }
        } else {
            // if xpdl name provided, do not attempt to make in unique but do make it a legal bpel name
            name = BPELUtils.getLegalBPELActivityName(name, null);
        }
        bpelActivity.setName(name);
    }

    public String generateActivityName(String part1, String part2, String alternatePart2) {
    	String result = N2PEConstants.NAME_PREFIX + part1;
    	if (part2 != null && part2.length() > 0) {
    		result+= '_' + part2;
    		if (result.length() >64) {
    			result = N2PEConstants.NAME_PREFIX + part1 + '_' + alternatePart2;
    		}
    	} else if (alternatePart2!=null) {
    		result+= '_' + alternatePart2;
    	}
    	return result;
    }
    
    public String genUniqueActivityName(String activityName) {
    	return makeUniqueActivityName(N2PEConstants.NAME_PREFIX+activityName);
    }

    /**
     * Sid XPD-8210 Generate a unique activity name based on _BX_<name>_<UUID>
     * as opposed to the {@link #genUniqueActivityName(String)} which suffixes
     * with a number
     * 
     * NOTE that if the system property "bx_flownames_number_suffixed" is set to
     * "TRUE" then this method will revert to the existing
     * {@link #genUniqueActivityName(String)} method and generate names with
     * numeric suffixes. This may be useful for JUNIT tests that perform gold
     * file comparisons
     * 
     * 
     * @param activityName
     * @return Unique activity name in the form _BX_<name>_<UUID>
     */
    public String genUniqueActivityNameWithUUID(String activityName) {

        String numberSuffix =
                System.getProperty("bx_flownames_number_suffixed"); //$NON-NLS-1$
        
        if (numberSuffix != null
                && numberSuffix.equalsIgnoreCase("true")) { //$NON-NLS-1$
            return genUniqueActivityName(activityName);
        }

        return N2PEConstants.NAME_PREFIX + activityName + EcoreUtil.generateUUID();
    }    
    
    public String makeUniqueActivityName(String activityName) {
    	if (isNullString(activityName)) {
    		return activityName;
    	}
    	
    	String result = activityName;
        int nextNum = 2;
        while (takenActivityNames.contains((result))) {
            result = activityName + "_"+nextNum++; //$NON-NLS-1$
        }
        takenActivityNames.add(result);
        return result;
    }

    public String makeUniqueVariableName(String varName) {
        String result = varName;
        int nextNum = 2;
        while (takenVariableNames.contains((result))) {
            result = varName + "_"+nextNum++; //$NON-NLS-1$
        }
        takenVariableNames.add(result);
        return result;
    }

    public void beginElement(UniqueIdElement newElement) {
    	this.currentElements.push(newElement);
    	
    	if (newElement instanceof Process) {
            uniqueGenerationValue = 1;
            m_takenLinkNames.clear();
            takenActivityNames.clear();
            takenVariableNames.clear();
        	m_partnerLinks.clear();
        }
    }

    public void endElement() {
        UniqueIdElement currentElement = getCurrentElement();
		org.eclipse.bpel.model.Variables variables = m_variableMap.get(currentElement);
        this.currentElements.pop();
        if (variables!=null && (currentElement instanceof Activity) && !XPDLUtils.isLoopActivity((Activity) currentElement)) {
            Copier copier = new Copier();
			Collection<org.eclipse.bpel.model.Variable> vars = copier.copyAll(variables.getChildren());
			copier.copyReferences();
            for (org.eclipse.bpel.model.Variable var : vars) {
                addVariable(var);
            }
        }
    }

    public UniqueIdElement getCurrentElement() {
    	return currentElements.isEmpty() ? null : currentElements.peek();
    }
    
    /** 
     * Create a unique name based on the initial name parameter.  The name is
     * forced to be unique by adding an incrementing number to the end of the
     * name until a unique name is found.
     * @param initialName The starting name to build on to create the unique name.
     * @param takenNames A Map where the keys are names that are already taken (and can't be used).
     * @return A new unique name (unique relative to the other names in the Map passed in). */
    private String getUniqueName(final String initialName, final Map<String, String> takenNames) {
        String uniqueName = initialName;
        while (takenNames.get(uniqueName) != null) {
            uniqueName = initialName + '_' + uniqueGenerationValue++;
        }
        takenNames.put(uniqueName, uniqueName);
        return uniqueName;
    }
    
    private Map<QualifiedName, WebServiceOperationInfo> getServiceOperationMap() {
    	if (isPageFlowEngineTarget) {
    		return pageflowServiceOperationMap;
    	} else {
    		return serviceOperationMap;
    	}
    }
    
    public WebServiceOperationInfo getWebServiceOperationInfo(WebServiceOperation wso) throws ConversionException {
        IProject project = this.getProject();
        URI wsdlURI = XPDLUtils.getWSDLURI(project, wso);
        PortTypeOperation pto = XPDLUtils.getPortTypeOperation(wso);
        String participantName = XPDLUtils.getParticipantName(wso);
        String wsoName = String.format("%s/%s/%s/%s",  //$NON-NLS-1$
        		new Object[] {pto.getPortTypeName(), pto.getOperationName(), wso.getService().getServiceName(), participantName});
        QualifiedName qName = new QualifiedName(wsdlURI.toString(), wsoName);
		WebServiceOperationInfo wsoInfo = getServiceOperationMap().get(qName);
		if (wsoInfo == null) {
			wsoInfo = new WebServiceOperationInfo(this, project, wso);
			getServiceOperationMap().put(qName, wsoInfo);
			//register all namespaces found in the WSDL
			Map<String, String> namespaceMap = wsoInfo.getWsdlDefinition().getNamespaces();
			for (String prefix : namespaceMap.keySet()) {
				String namespace = namespaceMap.get(prefix);
				registerNamespace(prefix, namespace);
			}
			//register all namespaces found in the imported schemas
			Collection<XSDSchema> allSchemas = WSDLUtils.getSchemaImports(wsoInfo.getWsdlDefinition(), false);
			for (XSDSchema schema : allSchemas) {
				String targetNamespace = schema.getTargetNamespace();
				if (targetNamespace == null) continue;
				namespaceMap = schema.getQNamePrefixToNamespaceMap();
				for (String prefix : namespaceMap.keySet()) {
					String namespace = namespaceMap.get(prefix);
					registerNamespace(prefix, namespace);
				}
			}
		}
		// This is for modifying wsdl with properties and property aliases
		wsdlMap.put(wsoInfo.getWSDLLocation(), wsoInfo.getWsdlDefinition());	// just need one copy
    	return wsoInfo;
    }

    public Collection<URI> getWsdlLocations() {
    	return wsdlMap.keySet();
    }
    
    public Definition getWsdlDefinition(URI location) {
    	return wsdlMap.get(location);
    }
    
    public Definition getWsdlDefinition(IProject project, WebServiceOperation wso) throws ConversionException {
        URI wsdlURI = XPDLUtils.getWSDLURI(project, wso);
        if (wsdlURI == null) {
        	throw new ConversionException(Messages.getString("WebServiceOperationInfo.noWSDLPath")); //$NON-NLS-1$
        }
		String wsdlFileName = wsdlURI.lastSegment();
		URI targetFolder = URI.createPlatformResourceURI(outputFilePath, false);
		URI targetFileURI = targetFolder.appendSegment(wsdlFileName);

		Definition wsdlDefinition = wsdlMap.get(targetFileURI);
		if (wsdlDefinition == null) {
			wsdlDefinition = WSDLUtils.loadWsdlDefinition(wsdlURI);
			try {
				registerSourceWSDL(wsdlDefinition);
				if (WSDLUtils.fileExists(targetFileURI)) {
					wsdlDefinition = WSDLUtils.loadWsdlDefinition(targetFileURI);
				} else {
					wsdlDefinition = WSDLUtils.clone(wsdlURI, targetFolder);
				}
				wsdlMap.put(targetFileURI, wsdlDefinition);
			} catch (Exception e) {
				throw new ConversionException(Messages.getString("WebServiceOperationInfo.cannotSaveWSDL") + e.getMessage()); //$NON-NLS-1$
			}
		}

    	return wsdlDefinition;
    }

	private void registerSourceWSDL(Definition wsdlDefinition) {
		srcWSDLMap.put(wsdlDefinition.getTargetNamespace(), wsdlDefinition);
		
		Collection<List<Import>> imports = wsdlDefinition.getImports().values();
		for (List<Import> list : imports) {
			for (Import imp : list) {
    			ImportImpl wsdlImport = (ImportImpl) imp;
    			wsdlImport.importDefinitionOrSchema();
    			Definition wsdl = wsdlImport.getEDefinition(); 
    			if (wsdl != null) {
					srcWSDLMap.put(wsdl.getTargetNamespace(), wsdl);
					registerSourceWSDL(wsdl);
    			}
			}
		}
	}
    
    public List<Transition> getObsoleteTransitions() {
    	return obsoleteTransitions;
    }
    
    private static boolean isNullString(String s) {
    	return s == null || s.length() == 0;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }
    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }
    
    public Map<String, org.eclipse.bpel.model.CorrelationSet> getCorrelations() {
    	return correlations;
    }
        
    public void addPropertyAlias(URI wsdlLocation, org.eclipse.bpel.model.messageproperties.PropertyAlias alias) {
    	Map<QualifiedName,org.eclipse.bpel.model.messageproperties.PropertyAlias> list = getPropertyAliases(wsdlLocation);
    	// uniqueness is one of the combinations of attributes plus property name 
    	// messageType + part
    	// type
    	// element
    	Message messageType = ((Message)alias.getMessageType());
    	if (messageType != null) {
    		String qualifier = "m:"+ messageType.getQName()+"/p:"+alias.getPart();
    		if (alias.getQuery() != null) {
    			qualifier += "/q:" + alias.getQuery().getValue();
    		}
			QualifiedName key = new QualifiedName(qualifier, alias.getPropertyName().toString());
    		if (!list.containsKey(key)) {    		// 
        		list.put(key, alias);	// TODO - will bpel take care of duplicate alias?
        	} else {
        		//error
        	}
    	}
    	/*
    	Element element = alias.getElement();
    	if (element != null) {
    		key = "e:" + element.getNamespaceURI() + element.getLocalName();
    	}
    	Object object = alias.getType();
    	*/
    	
    }
    
    private Map<String, org.eclipse.bpel.model.messageproperties.Property> getPropertiesMap(URI wsdlLocation) {
    	Map<String, org.eclipse.bpel.model.messageproperties.Property> map = properties.get(wsdlLocation);
    	if (map == null) {
    		map = new LinkedHashMap<String, org.eclipse.bpel.model.messageproperties.Property>();
    		properties.put(wsdlLocation, map);
    	}
    	return map;
    }

    public Collection<org.eclipse.bpel.model.messageproperties.Property> getProperties(URI wsdlLocation) {
    	return getPropertiesMap(wsdlLocation).values();
    }

    public Map<QualifiedName, org.eclipse.bpel.model.messageproperties.PropertyAlias> getPropertyAliases(URI wsdlLocation) {
		Map<QualifiedName, org.eclipse.bpel.model.messageproperties.PropertyAlias> map = propertyAliases.get(wsdlLocation);
    	if (map == null) {
			map = new TreeMap<QualifiedName,org.eclipse.bpel.model.messageproperties.PropertyAlias>(new Comparator<QualifiedName>() {
				@Override
				public int compare(QualifiedName qn1, QualifiedName qn2) {
					return qn1.toString().compareTo(qn2.toString());
				}
			});
			propertyAliases.put(wsdlLocation, map);
		}
    	return map;
    }
    
    public Definition getFaultDefinition(NamedElement processOrInterface) {
    	Definition definition = processToFaultDefinitionMap.get(processOrInterface.getId());
    	if (definition == null) {
    		definition = WSDLFactory.eINSTANCE.createDefinition();
    		String name = processOrInterface.getName().replace(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$;
    		String namespace = N2PEConstants.TARGET_WSDL_NS_PREFIX + processOrInterface.getId() + "/fault"; //$NON-NLS-1$
    		definition.setTargetNamespace(namespace);
    		definition.addNamespace("fault", namespace); //$NON-NLS-1$
    		definition.addNamespace("xsd", N2PEConstants.XSD_NAMESPACE_URI); //$NON-NLS-1$
    		definition.setQName(new QName(namespace, name));

    		//create a port type
    		PortType portType = WSDLFactory.eINSTANCE.createPortType();
    		portType.setQName(definition.getQName());
    		definition.addPortType(portType);
    		portType.setEnclosingDefinition(definition);

    		processToFaultDefinitionMap.put(processOrInterface.getId(), definition);
    	}
    	
    	return definition;
    }
    
    public String[] getProcessIdsWithFaultDefinition() {
    	return processToFaultDefinitionMap.keySet().toArray(new String[0]);
    }
    
    private String registerNamespace(String prefix, String namespace) {
    	if (prefix == null || prefix.length() == 0 || namespace == null || namespace.length() == 0) {
    		return prefix;
    	}
    	
        String existingNS = prefixToNsMap.get(prefix);
        if (existingNS != null) {
        	//the prefix has been registered. Let's check if the namespace is the same
            if (existingNS.equals(namespace)) {
            	//the prefix and namespace match; no further action is required
            	return prefix;
            }

            //Prefix has been registered for another namespace.
            //First check if there's a known prefix for this namespace
            String existingPrefix = nsToPrefixMap.get(namespace);
            if (existingPrefix != null) {
            	return existingPrefix;
            }
            
            //we'll make a new prefix that is unique
            prefix = WSDLUtils.getUniqueNamespacePrefix(prefix, prefixToNsMap.keySet());
        }
        
        prefixToNsMap.put(prefix, namespace);
        
        if (!nsToPrefixMap.containsKey(namespace)) {
            nsToPrefixMap.put(namespace, prefix);
        }

        return prefix;
    }
    
    public Map<String, String> getPrefixToNamespaceMap() {
    	return Collections.unmodifiableMap(prefixToNsMap);
    }
    
    public Map<String, String> getNamespaceToPrefixMap() {
    	return Collections.unmodifiableMap(nsToPrefixMap);
    }
    
    public Definition getSourceWsdl(String targetNamespace) {
    	return srcWSDLMap.get(targetNamespace);
    }
    
    public Task getTaskDescriptor(String taskName) {
    	Task task = taskDescriptors.get(taskName);
    	if (task == null) {
			task = ExtensionsFactory.eINSTANCE.createTask();
			task.setName(taskName);
	    	taskDescriptors.put(taskName, task);
    	}
		return task;
    }
    
    public Task[] getTaskDescriptors() {
    	return taskDescriptors.values().toArray(new Task[0]);
    }
    
    public ExtensibleElement getBpelElementByXpdlId(String xpdlId) {
    	return xpdlToBpelMap.get(xpdlId);
    }

    public void syncXpdlId(org.eclipse.bpel.model.ExtensibleElement bpelElement, com.tibco.xpd.xpdl2.UniqueIdElement uniqueIdElement) {
    	String id = uniqueIdElement.getId();
		BPELUtils.setXpdlId(bpelElement, id);
    	xpdlToBpelMap.put(id, bpelElement);
	}

	public boolean isPageFlowEngineTarget() {
		return isPageFlowEngineTarget;
	}

	public void setPageFlowEngineTarget(boolean isPageFlowEngineTarget) {
		this.isPageFlowEngineTarget = isPageFlowEngineTarget;
	}

    /**
     * Sid XPD-8199
     * 
     * For direct links into BPEL child Flows we need a _BX_fanInSetup'n'
     * activity to set up a fan variable that allows the flow's
     * _BX_Starting_Point activity to jump to the appropriate activity in that
     * flow.
     * 
     * @return The next number to use for a new _BX_fanInSetup activity
     */
    public int getNextFlowFanInNumber() {
        int next = nextFlowFanInNumber;
        nextFlowFanInNumber++;

        return next;
    }

    /**
     * Sid XPD-8199
     * 
     * For direct links out of BPEL child Flows we need a _BX_fanOutSetup'n'
     * activity to set up a fan variable that allows the receiving flow's
     * activity to jump to the appropriate activity flow.
     * 
     * @return The next number to use for a new _BX_fanInSetup activity
     */
    public int getNextFlowFanOutNumber() {
        int next = nextFlowFanOutNumber;
        nextFlowFanOutNumber++;

        return next;
    }
    
    /**
     * Returns the default correlation timeout for the incoming request activity (in seconds).
     *  
     * @return a string representing the default timeout (integer) for incoming request activity (in seconds)
     */
    public String getDefaultIncomingRequestTimeout() {
        return "3600"; //$NON-NLS-1$
    }
}
