/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public final class SubProcUtil {

    /** Script context identifier for sub-processes. */
    public static final String SCRIPT_CONTEXT = "SubProcess"; //$NON-NLS-1$

    /**
     * Private constructor.
     */
    private SubProcUtil() {
    }

    /**
     * Return <code>true</code> if it's an asynchronous call, <code>false</code>
     * otherwise.
     * 
     * @param objectToValidate
     *            Object to validate.
     * @return <code>true</code> if it's an asynchronous call,
     *         <code>false</code> otherwise.
     */
    public static boolean isAsyncCall(Object objectToValidate) {

        /*
         * Check if input element is an activity.
         */
        if (objectToValidate instanceof Activity) {

            Activity act = (Activity) objectToValidate;

            /*
             * Filter out for Call sub-process activities.
             */
            if (act.getImplementation() instanceof SubFlow) {

                /*
                 * Get sub-flow.
                 */
                SubFlow subFlow = (SubFlow) act.getImplementation();

                /*
                 * Get execution mode object.
                 */
                Object execModeObject =
                        Xpdl2ModelUtil.getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode());

                /*
                 * We need to show PROCESS_ID parameter only for asynchronous
                 * sub-process calls.
                 */
                if (execModeObject != null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Return <code>true</code> if the specified activity is a Call Sub-Process
     * activity and has got InputMappings/OutputMappings extension element in it
     * which would mean that it has Data Mapper grammar configured. Return
     * <code>false</code> otherwise.
     * 
     * @param activity
     *            Activity to be checked if it is a Call Sub-Process and
     *            supports Data Mapper.
     * 
     * @return <code>true</code> if the specified activity is a Call Sub-Process
     *         activity and has got InputMappings/OutputMappings extension
     *         element in it which would mean that it has Data Mapper grammar
     *         configured. Return <code>false</code> otherwise.
     */
    public static boolean isDataMapperMappingsUsed(Activity activity,
            DirectionType direction) {
        SubFlow sf = getSubFlow(activity);
        if (sf != null) {
            if (DirectionType.IN_LITERAL.equals(direction)
                    && null != Xpdl2ModelUtil.getOtherElement(sf,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InputMappings())) {
                return true;

            } else if (DirectionType.OUT_LITERAL.equals(direction)
                    && null != Xpdl2ModelUtil.getOtherElement(sf,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OutputMappings())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the SubFlow extension element from the specified activity OR return
     * <code>null</code> if that doesn't exist.
     * 
     * @param activity
     * 
     * @return The SubFlow extension element from the specified activity OR
     *         return <code>null</code> if that doesn't exist.
     */
    public static SubFlow getSubFlow(Activity activity) {
        if (activity != null && activity.getImplementation() instanceof SubFlow) {
            SubFlow subFlow = ((SubFlow) activity.getImplementation());
            return subFlow;
        }
        return null;
    }

    /**
     * Locates a package object by name. This works by scanning all of the XPDL
     * files in a project and checking the package name against the required
     * one. It first scans the packages that have already been loaded. If the
     * package is not found it will then go through the other packages, loading
     * and checking them.
     * 
     * @param src
     *            The resource containing the package.
     * @param name
     *            The package name.
     * @return The package EObject.
     */
    public static Package getPackageByName(IResource src, String name) {
        Package pckg = null;
        if (src != null && name != null) {
            IProject project = src.getProject();
            if (project != null) {
                XpdProjectResourceFactory resourceFactory =
                        XpdResourcesPlugin.getDefault()
                                .getXpdProjectResourceFactory(project);
                if (resourceFactory != null) {
                    WorkingCopy wc = resourceFactory.getWorkingCopy(src);
                    if (wc instanceof Xpdl2WorkingCopyImpl) {
                        Xpdl2WorkingCopyImpl xpdlwc = (Xpdl2WorkingCopyImpl) wc;
                        String location =
                                xpdlwc.getExternalPackageLocation(name);
                        if (location != null) {
                            List<IResource> resourceList =
                                    ProcessUIUtil
                                            .getResourcesForLocation(project,
                                                    location,
                                                    Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                            if (resourceList != null) {
                                for (IResource rsrc : resourceList) {
                                    IProject rscProject = rsrc.getProject();
                                    if (rscProject != null) {
                                        XpdProjectResourceFactory fact =
                                                XpdResourcesPlugin
                                                        .getDefault()
                                                        .getXpdProjectResourceFactory(rscProject);
                                        if (fact != null) {
                                            IResource resolveResourceReference =
                                                    fact.resolveResourceReference(rsrc,
                                                            location,
                                                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

                                            if (resolveResourceReference != null) {
                                                WorkingCopy externalWC =
                                                        fact.getWorkingCopy(resolveResourceReference);

                                                if (externalWC != null) {
                                                    Package externalPkg =
                                                            (Package) externalWC
                                                                    .getRootElement();
                                                    if (externalPkg != null) {
                                                        pckg = externalPkg;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return pckg;
    }

    /**
     * Checks to see if an activity has a Sub flow implementation type in it.
     * 
     * @param activity
     *            The activity to check.
     * 
     * @return true if the implementation type is "SubProcess",
     *         <code>false</code>.
     */
    public static boolean isSubProcessImplementation(Activity activity) {

        boolean isCsp = false;
        SubFlow sf = null;
        Implementation impl = activity.getImplementation();

        if (impl instanceof SubFlow) {
            sf = (SubFlow) impl;
        }

        if (sf != null) {
            isCsp = true;
        }

        return isCsp;
    }

    /**
     * @param item
     *            The item to get the name for.
     * @return The name.
     */
    public static String getParameterName(Object item) {
        String name = null;
        if (item instanceof NamedElement) {
            name = ((NamedElement) item).getName();
        } else if (item instanceof ConceptPath) {
            name = ((ConceptPath) item).getPath();
        }
        return name;
    }

    /**
     * Return the process object for the sub-process called from the given
     * subFlow (indi sub-proc task) - regardless of what package it is in.
     * 
     * @param subFlow
     * @return
     */
    public static Process getSubProcess(SubFlow subFlow) {
        Process subProc = null;

        Activity act = subFlow.getActivity();

        String packageId = subFlow.getPackageRefId();
        String processId = subFlow.getProcessId();
        Package pckage = null;

        if (packageId == null) {
            pckage = act.getProcess().getPackage();
        } else {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(act);
            if (wc != null) {
                IResource resource = wc.getEclipseResources().get(0);
                pckage = SubProcUtil.getPackageByName(resource, packageId);
            }
        }

        if (pckage != null) {
            subProc = pckage.getProcess(processId);
        }
        return subProc;
    }

    /**
     * Get process parameters - that is, the sub-process parameters that are
     * implicitly or explicitly assocated with the start event (if there is one)
     * 
     * @param activity
     * @param direction
     *            in / out or <code>null</code> for both.
     * 
     * @return
     */
    public static List<FormalParameter> getSubProcessFormalParameters(
            Activity activity, MappingDirection direction) {

        List<FormalParameter> formalParamList =
                new ArrayList<FormalParameter>();
        if (activity != null) {
            EObject processOrInterface =
                    TaskObjectUtil.getSubProcessOrInterface(activity);

            if (processOrInterface != null) {
                Map<ConceptPath, Boolean> parametersAndMandatory =
                        getSubProcessParametersAndMandatory(processOrInterface,
                                direction);

                for (ConceptPath conceptPath : parametersAndMandatory.keySet()) {
                    if (conceptPath.getItem() instanceof FormalParameter) {
                        formalParamList.add((FormalParameter) conceptPath
                                .getItem());
                    }
                }
            }
        }
        return formalParamList;
    }

    /**
     * Get process parameters - that is, the sub-process parameters that are
     * implicitly or explicitly assocated with the start event (if there is one)
     * 
     * @param process
     * @param direction
     *            in / out or <code>null</code> for both.
     * @return
     */
    public static List<FormalParameter> getProcessParameters(Process process,
            MappingDirection direction) {
        List<FormalParameter> formalParamList =
                new ArrayList<FormalParameter>();
        //
        // Independent Sub-Process task referecing an actual
        // Sub-Process
        Activity start = getSubProcessStartEvent(process);

        if (start != null) {
            List<FormalParameter> formals =
                    ProcessInterfaceUtil.getAssociatedFormalParameters(start);
            for (Object next : formals) {
                FormalParameter formal = (FormalParameter) next;
                if (isValidDirection(formal.getMode(), direction)) {
                    formalParamList.add(formal);
                    // addParameter(parameters, formal);
                }
            }
        } else {
            List<?> formals =
                    ProcessInterfaceUtil.getAllFormalParameters(process);
            for (Object next : formals) {
                FormalParameter formal = (FormalParameter) next;
                if (isValidDirection(formal.getMode(), direction)) {
                    formalParamList.add(formal);
                    // addParameter(parameters, formal);
                }
            }
        }
        return formalParamList;
    }

    /**
     * @param process
     * @return
     */
    public static Activity getSubProcessStartEvent(Process process) {
        Activity start = null;
        for (Object next : process.getActivities()) {
            Activity subActivity = (Activity) next;
            Event event = subActivity.getEvent();
            if (event instanceof StartEvent) {
                StartEvent current = (StartEvent) event;
                TriggerType type = current.getTrigger();
                if (TriggerType.NONE_LITERAL.equals(type)) {
                    start = subActivity;
                    break;
                }
            }
        }
        return start;
    }

    public static List<ConceptPath> getSubProcessParameters(Activity activity,
            MappingDirection direction) {
        List<ConceptPath> parameters = new ArrayList<ConceptPath>();

        EObject processOrInterface =
                TaskObjectUtil.getSubProcessOrInterface(activity);
        if (processOrInterface != null) {
            Map<ConceptPath, Boolean> parametersAndMandatory =
                    getSubProcessParametersAndMandatory(processOrInterface,
                            direction);

            parameters.addAll(parametersAndMandatory.keySet());
        }
        return parameters;
    }

    /**
     * @param data
     * @return concept path for given parameter.
     */
    private static ConceptPath createConceptPath(ProcessRelevantData data) {
        Class cls = ConceptUtil.getConceptClass(data);
        ConceptPath conceptPath = new ConceptPath(data, cls);
        return conceptPath;
    }

    private static boolean isValidDirection(ModeType mode,
            MappingDirection direction) {
        boolean valid = false;
        if (direction == null) {
            valid = true;
        } else if (ModeType.INOUT_LITERAL.equals(mode)) {
            valid = true;
        } else {
            if (ModeType.IN_LITERAL.equals(mode)) {
                if (MappingDirection.IN.equals(direction)) {
                    valid = true;
                }
            } else {
                if (MappingDirection.OUT.equals(direction)) {
                    valid = true;
                }
            }
        }
        return valid;
    }

    /**
     * @param process
     *            CAN BE NULL!
     * @param direction
     * @param name
     * @return
     */
    public static ConceptPath resolveParameter(
            FormalParametersContainer procOrIfc, MappingDirection direction,
            String name) {
        ConceptPath concept = null;

        StringTokenizer tokenizer = new StringTokenizer(name, "/"); //$NON-NLS-1$
        if (tokenizer.hasMoreTokens()) {
            String field = tokenizer.nextToken();
            ProcessRelevantData data =
                    resolveProcessParameter(procOrIfc, direction, field);
            if (data != null) {
                Class clss = ConceptUtil.getConceptClass(data);
                ConceptPath current = new ConceptPath(data, clss);
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    current = resolveConcept(current, token);
                }
                concept = current;
            }
        }

        return concept;
    }

    /**
     * @param activity
     *            The activity.
     * @param direction
     * @param name
     *            The parameter name to resolve.
     * @return The parameter.
     */
    private static ProcessRelevantData resolveProcessParameter(
            FormalParametersContainer procOrIfc, MappingDirection direction,
            String name) {
        ProcessRelevantData field = null;
        if (name.startsWith("process.")) { //$NON-NLS-1$
            name = name.substring(8);
        }

        if (procOrIfc != null) {
            List<?> params = null;
            if (procOrIfc instanceof Process) {
                params =
                        ProcessInterfaceUtil
                                .getAllFormalParameters((Process) procOrIfc);

            } else {
                params = procOrIfc.getFormalParameters();
            }

            field = resolveField(params, direction, name);

            if (field == null && procOrIfc instanceof Process) {
                Process process = (Process) procOrIfc;
                field = resolveField(process.getDataFields(), direction, name);
                if (field == null) {
                    Package pckg = process.getPackage();
                    field = resolveField(pckg.getDataFields(), direction, name);
                }
            }
        }

        if (field == null) {
            //
            // If we didn't find the given field anywhere in normal data then
            // check our 'special fields' (in this case the errorcode field that
            // can be caught by catch error events.
            //
            if (StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER.getName()
                    .equals(name)) {
                return StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER;
            } else if (StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER
                    .getName().equals(name)) {
                return StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER;
            } else if (StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                    .getName().equals(name)) {
                return StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER;
            }
        }
        return field;
    }

    /**
     * @param fields
     *            A list of DataField objects.
     * @param direction
     * @param name
     *            The ame of the field to resolve.
     * @return The resolved DataField or null if it can't be found.
     */
    private static ProcessRelevantData resolveField(List<?> fields,
            MappingDirection direction, String name) {
        ProcessRelevantData field = null;
        for (Object next : fields) {
            if (next instanceof ProcessRelevantData) {
                ProcessRelevantData current = (ProcessRelevantData) next;
                if (name.equals(current.getName())) {
                    if (current instanceof FormalParameter) {
                        FormalParameter formal = (FormalParameter) current;
                        ModeType mode = formal.getMode();
                        ModeType targetMode =
                                MappingDirection.IN.equals(direction) ? ModeType.IN_LITERAL
                                        : ModeType.OUT_LITERAL;
                        if (ModeType.INOUT_LITERAL.equals(mode)
                                || mode.equals(targetMode)) {
                            field = current;
                            break;
                        }
                    } else {
                        field = current;
                        break;
                    }
                }
            }
        }
        return field;
    }

    /**
     * @param current
     * @param token
     * @return
     */
    private static ConceptPath resolveConcept(ConceptPath current, String token) {
        ConceptPath concept = null;
        for (ConceptPath child : current.getChildren()) {
            if (token.equals(child.getName())) {
                concept = child;
                break;
            }
        }
        return concept;
    }

    /**
     * Get a list of the input parameters including an idea of whether they are
     * mandatory.
     * 
     * @param processOrInterface
     * @return Map of formal parameter to mandatory flag.
     */
    public static Map<ConceptPath, Boolean> getSubProcessParametersAndMandatory(
            Object processOrInterface, MappingDirection direction) {
        Map<ConceptPath, Boolean> paramAndMandatory =
                new HashMap<ConceptPath, Boolean>();

        if (processOrInterface instanceof Process) {
            /*
             * For sub-process call use the start type none event assocaited
             * parameters or all if no start event at the mo.
             */
            Process subProcess = (Process) processOrInterface;

            Activity startEvent =
                    SubProcUtil.getSubProcessStartEvent(subProcess);

            /*
             * This method used to get things horribly wrong for implementing
             * events in terms of what sub-process start parameters there were
             * for various circumstances. So using new classes to get it right
             * this time.
             */
            Collection<ActivityInterfaceData> startEventInterfaceData;

            if (startEvent != null) {
                startEventInterfaceData =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(startEvent);
            } else {
                /* If we can't find start event then just get for all params. */
                startEventInterfaceData = new HashSet<ActivityInterfaceData>();

                for (FormalParameter parameter : ProcessInterfaceUtil
                        .getAllFormalParameters(subProcess)) {
                    startEventInterfaceData.add(new ActivityInterfaceData(
                            parameter));
                }
            }

            for (ActivityInterfaceData ifcData : startEventInterfaceData) {
                if (isValidDirection(ifcData.getMode(), direction)) {
                    paramAndMandatory.put(createConceptPath(ifcData.getData()),
                            new Boolean(ifcData.isMandatory()));
                }
            }

        } else if (processOrInterface instanceof ProcessInterface) {
            /*
             * For process-interface call use the start type none event
             * associated parameters or all if no start event at the mo.
             */
            ProcessInterface processInterface =
                    (ProcessInterface) processOrInterface;

            StartMethod startMethod = null;
            for (StartMethod sm : processInterface.getStartMethods()) {
                if (TriggerType.NONE_LITERAL.equals(sm.getTrigger())) {
                    startMethod = sm;
                    break;
                }
            }

            /*
             * This method used to get things horribly wrong for implementing
             * events in terms of what sub-process start parameters there were
             * for various circumstances. So using new classes to get it right
             * this time.
             */
            Collection<ActivityInterfaceData> startEventInterfaceData;

            if (startMethod != null) {
                startEventInterfaceData =
                        ActivityInterfaceDataUtil
                                .getInterfaceEventInterfaceData(startMethod);

            } else {
                /* If we can't find start event then just get for all params. */
                startEventInterfaceData = new HashSet<ActivityInterfaceData>();

                for (FormalParameter parameter : processInterface
                        .getFormalParameters()) {
                    startEventInterfaceData.add(new ActivityInterfaceData(
                            parameter));
                }
            }

            for (ActivityInterfaceData ifcData : startEventInterfaceData) {
                if (isValidDirection(ifcData.getMode(), direction)) {
                    paramAndMandatory.put(createConceptPath(ifcData.getData()),
                            new Boolean(ifcData.isMandatory()));
                }
            }

        }

        return paramAndMandatory;
    }

    /**
     * Returns true, if the given activity is a Reusable/Embedded Sub Process.
     * 
     * @param activity
     * @return
     */
    public static boolean isSubProcessActivity(Activity activity) {

        /*
         * ABPM-911: Saket: An event subprocess is also a subprocess activity.
         */
        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))
                || TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {
            return true;
        }
        return false;
    }

}
