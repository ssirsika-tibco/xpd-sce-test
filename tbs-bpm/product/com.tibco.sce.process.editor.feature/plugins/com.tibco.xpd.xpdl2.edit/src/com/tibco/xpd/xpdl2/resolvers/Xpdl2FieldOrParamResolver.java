/**
 * Xpdl2DataFieldResolver.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.xpdl2.resolvers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.edit.internal.Messages;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * <b>Xpdl2FieldOrParamResolver</b>
 * <p>
 * The data resolver provides the ability to check for data field / formal
 * parameter references within given model objects.
 * </p>
 * <p>
 * This allows complex operations on the model (such as refactoring) to discover
 * data field / formal parameter references so that they can perform appropriate
 * actions (such as moving/copying data fields that are used by objects).
 * </p>
 * <p>
 * Other plug-ins can extend this ability via extension points and in this way,
 * plug-in specific referencing of data fields are handled by the plug-ins
 * themselves.
 * </p>
 * 
 * 
 */
public class Xpdl2FieldOrParamResolver {

    private static final ActivitySet ActivitySet = null;

    private Set<ProcessRelevantData> checkData;

    private Map<String, ProcessRelevantData> checkDataNameMap;

    private Map<String, ProcessRelevantData> checkDataIdMap;

    /**
     * Construct a data field resolver that can resolve references to the given
     * data fields.
     * 
     * @param checkData
     *            Data fields to check references for.
     */
    public Xpdl2FieldOrParamResolver(Set<ProcessRelevantData> checkData) {
        init(checkData);

    }

    /**
     * @param checkDataFields2
     */
    private void init(Set<ProcessRelevantData> checkDataFields) {
        this.checkData = checkDataFields;

        // Create name and id map
        checkDataNameMap = new HashMap<String, ProcessRelevantData>();
        checkDataIdMap = new HashMap<String, ProcessRelevantData>();

        for (ProcessRelevantData data : this.checkData) {
            checkDataNameMap.put(data.getName(), data);
            checkDataIdMap.put(data.getId(), data);
        }

    }

    /**
     * Construct a data field resolver that can resolve references to data
     * fields and foirmal parameters that are in the the given object's context.
     * (i.e. if object is a process or activity then context fields are those in
     * the process and the process's parent package.
     * 
     * 
     * @param contextObject
     */
    public Xpdl2FieldOrParamResolver(EObject contextObject) {
        HashSet<ProcessRelevantData> dataFields =
                new HashSet<ProcessRelevantData>();

        //
        // Go up thru hierarchy, when we find process add it's data fields, when
        // we find package add package data fields.
        EObject parent = contextObject;

        while (parent != null && !(parent instanceof ActivitySet)
                && !(parent instanceof Process)
                && !(parent instanceof ProcessInterface)
                && !(parent instanceof Package)) {
            parent = parent.eContainer();
        }

        if (parent instanceof ActivitySet) {
            // Get all the data in this embedded sub-process and it's parent emb
            // sub-procs.
            List<ProcessRelevantData> embData =
                    LocalPackageProcessInterfaceUtil
                            .getEmbeddedSubProcessSetScopeData((ActivitySet) parent);
            dataFields.addAll(embData);

            // Then go on and add process /pakcage as usual.
            parent = ((ActivitySet) parent).getProcess();
        }

        if (parent instanceof Process) {

            dataFields.addAll(LocalPackageProcessInterfaceUtil
                    .getAllFormalParameters((Process) parent));

            dataFields.addAll(((Process) parent).getDataFields());

        } else if (parent instanceof ProcessInterface) {
            dataFields
                    .addAll(((ProcessInterface) parent).getFormalParameters());
        }

        // Now add the package data fields (unless its a process interface).
        if (!(parent instanceof ProcessInterface)) {
            while (parent != null && !(parent instanceof Package)) {
                parent = parent.eContainer();
            }

            if (parent instanceof Package) {
                dataFields.addAll(((Package) parent).getDataFields());
            }
        }

        init(dataFields);
    }

    /**
     * Return a list of Objects (activities and transitions) that reference the
     * given data field / param.
     * 
     * @param fieldOrParam
     * @return activities transitions and processdata that reference the given
     *         field.
     * 
     */
    public static List<EObject> getReferencingObjects(
            ProcessRelevantData fieldOrParam) {
        Set<ProcessRelevantData> data = new HashSet<ProcessRelevantData>();
        data.add(fieldOrParam);

        Xpdl2FieldOrParamResolver resolver =
                new Xpdl2FieldOrParamResolver(data);

        // Create a list of activities and transitions to check.
        ArrayList<EObject> checkObjs =
                getInScopePossibleDataReferencers(fieldOrParam);

        // Check for references o data field in each.
        List<EObject> referenceObjects = new ArrayList<EObject>();

        for (Iterator iter = checkObjs.iterator(); iter.hasNext();) {
            EObject obj = (EObject) iter.next();

            if (obj instanceof Activity) {
                Set<ProcessRelevantData> ref =
                        resolver.getDataReferences((Activity) obj);
                if (ref != null && ref.size() > 0) {
                    referenceObjects.add(obj);
                }
            } else if (obj instanceof Transition) {
                Set<ProcessRelevantData> ref =
                        resolver.getDataReferences((Transition) obj);
                if (ref != null && ref.size() > 0) {
                    referenceObjects.add(obj);
                }
            } else if (obj instanceof AssociatedParametersContainer) {
                // Check for references in process interface start/intermediate
                // methods.
                Set<ProcessRelevantData> ref =
                        resolver.getDataReferences((AssociatedParametersContainer) obj);
                if (ref != null && ref.size() > 0) {
                    referenceObjects.add(obj);
                }
            } else if (obj instanceof ProcessRelevantData) {
                Set<ProcessRelevantData> ref =
                        resolver.getDataReferences((ProcessRelevantData) obj);
                if (null != ref && ref.size() > 0) {
                    referenceObjects.add(obj);
                }
            }
        }

        /*
         * XPD-5427 - Handle miscellaneous data references for new extension.
         * 
         * For each data container object (process/activity/process interface)
         * that's in scope of data, get references from each contributed
         * IDataReferenceResolver.
         */
        Set<EObject> dataScopeObjects = getDataScopeObjects(fieldOrParam);

        for (EObject dataScopeObject : dataScopeObjects) {

            for (IDataReferenceResolver dataRefResolver : ResolverExtPointHelper.INSTANCE
                    .getDataReferenceResolvers()) {

                Map<EObject, ProcessDataReferenceAndContexts> dataReferences =
                        dataRefResolver.getDataReferences(dataScopeObject,
                                fieldOrParam);

                if (dataReferences != null) {
                    referenceObjects.addAll(dataReferences.keySet());
                }
            }
        }

        return referenceObjects;
    }

    /**
     * Return set of container objects that teh given data is in scope of.
     * <p>
     * Data Container resolved to...
     * 
     * <li>Processes in scope of data (so all process if it's pkg data or
     * implementing proceses if it's interface data THIS is in order to save
     * every implementation having to do this scope checking)</li>
     * 
     * <li>Activity ONLY if the data is an activity scope data field changes</li>
     * 
     * <li>Process Interface. Note that in the circumstance of process-interface
     * data then the scope object may be the implementing process (so not
     * necessarily the direct data container.</li>
     * 
     * @param data
     * @return
     */
    private static Set<EObject> getDataScopeObjects(
            ProcessRelevantData fieldOrParam) {
        Set<EObject> dataScopeObjects = new HashSet<EObject>();

        EObject container = fieldOrParam.eContainer();

        if (container instanceof Package) {
            /*
             * If data is owned by package then all processes are in scope of
             * the data.
             */
            dataScopeObjects.addAll(((Package) container).getProcesses());

        } else if (container instanceof ProcessInterface) {
            /*
             * If data is in process interface then interface itself and all
             * local processes that implement it are in scope of the data.
             */
            ProcessInterface ifc = (ProcessInterface) container;
            dataScopeObjects.add(ifc);

            Package pkg = Xpdl2ModelUtil.getPackage(ifc);

            if (pkg != null) {
                for (Process process : pkg.getProcesses()) {
                    ImplementedInterface implementedInterface =
                            (ImplementedInterface) Xpdl2ModelUtil
                                    .getOtherElement(process,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementedInterface());

                    if (implementedInterface != null) {
                        if (ifc.getId()
                                .equals(implementedInterface.getProcessInterfaceId())) {
                            dataScopeObjects.add(process);
                        }
                    }
                }
            }
        } else {
            /*
             * Otherwise (Process and Activity data) we'll just provide the
             * container as the scope object
             */
            dataScopeObjects.add(container);
        }

        return dataScopeObjects;
    }

    /**
     * Return a list of Objects (activities and transitions) that reference the
     * given data field / param AND the contexts in which it is referenced.
     * 
     * @param fieldOrParam
     * @return activities, transitions, interface-methods and and process-data
     *         that reference the given field, or indeed any process package
     *         content that can reference the given data.
     * 
     */
    public static Map<EObject, ProcessDataReferenceAndContexts> getReferencingObjectsAndContexts(
            ProcessRelevantData fieldOrParam) {
        Set<ProcessRelevantData> data = new HashSet<ProcessRelevantData>();
        data.add(fieldOrParam);

        Xpdl2FieldOrParamResolver resolver =
                new Xpdl2FieldOrParamResolver(data);

        // Create a list of activities and transitions to check.
        ArrayList<EObject> checkObjs =
                getInScopePossibleDataReferencers(fieldOrParam);

        // Check for references o data field in each.
        Map<EObject, ProcessDataReferenceAndContexts> referenceObjects =
                new HashMap<EObject, ProcessDataReferenceAndContexts>();

        for (Iterator iter = checkObjs.iterator(); iter.hasNext();) {
            EObject obj = (EObject) iter.next();

            if (obj instanceof Activity) {
                Collection<ProcessDataReferenceAndContexts> dataRefs =
                        resolver.getDataContextReferences((Activity) obj);

                if (dataRefs != null && dataRefs.size() > 0) {
                    /*
                     * We only passed one data item to the resolver so it should
                     * only pass one back!
                     */
                    referenceObjects.put(obj, dataRefs.iterator().next());
                }

            } else if (obj instanceof Transition) {
                Collection<ProcessDataReferenceAndContexts> dataRefs =
                        resolver.getDataContextReferences((Transition) obj);

                if (dataRefs != null && dataRefs.size() > 0) {
                    /*
                     * We only passed one data item to the resolver so it should
                     * only pass one back!
                     */
                    referenceObjects.put(obj, dataRefs.iterator().next());
                }

            } else if (obj instanceof AssociatedParametersContainer) {
                // Check for references in process interface start/intermediate
                // methods.
                Set<ProcessRelevantData> ref =
                        resolver.getDataReferences((AssociatedParametersContainer) obj);
                if (ref != null && ref.size() > 0) {
                    referenceObjects
                            .put(obj,
                                    new ProcessDataReferenceAndContexts(
                                            fieldOrParam,
                                            DataReferenceContext.CONTEXT_INTERFACE_METHOD));
                }

            } else if (obj instanceof ProcessRelevantData) {
                Set<ProcessRelevantData> ref =
                        resolver.getDataReferences((ProcessRelevantData) obj);
                if (null != ref && ref.size() > 0) {
                    /*
                     * USe unknown context cos ref from data to data is only in
                     * performer script and that's been removed anyhow.
                     */
                    referenceObjects.put(obj,
                            new ProcessDataReferenceAndContexts(fieldOrParam,
                                    DataReferenceContext.CONTEXT_UNKNOWN));
                }
            }
        }

        /*
         * XPD-5427 - Handle miscellaneous data references for new extension.
         * 
         * For each data container object (process/activity/process interface)
         * that's in scope of data, get references from each contributed
         * IDataReferenceResolver.
         */
        Set<EObject> dataScopeObjects = getDataScopeObjects(fieldOrParam);

        for (EObject dataScopeObject : dataScopeObjects) {

            for (IDataReferenceResolver dataRefResolver : ResolverExtPointHelper.INSTANCE
                    .getDataReferenceResolvers()) {

                Map<EObject, ProcessDataReferenceAndContexts> dataReferences =
                        dataRefResolver.getDataReferences(dataScopeObject,
                                fieldOrParam);

                if (dataReferences != null) {
                    /*
                     * Merge contexts with previous those of previous
                     * refernecing object or add new entry.
                     */
                    for (Entry<EObject, ProcessDataReferenceAndContexts> entry : dataReferences
                            .entrySet()) {
                        ProcessDataReferenceAndContexts currentDataRefs =
                                referenceObjects.get(entry.getKey());

                        if (currentDataRefs != null) {
                            currentDataRefs.addAllContexts(entry.getValue()
                                    .getContexts());
                        } else {
                            referenceObjects.put(entry.getKey(),
                                    entry.getValue());
                        }
                    }

                }
            }
        }

        return referenceObjects;
    }

    /**
     * @param fieldOrParam
     * @return A list of object that are able to reference the given
     *         field/param.
     */
    private static ArrayList<EObject> getInScopePossibleDataReferencers(
            ProcessRelevantData fieldOrParam) {
        ArrayList<EObject> checkObjs = new ArrayList<EObject>();

        EObject dataContainer = fieldOrParam.eContainer();
        if (dataContainer instanceof Package) {
            Package pkg = (Package) dataContainer;

            // Package field can be referenced by package level data fields
            // (because performer fields can have scripts).
            checkObjs.addAll(pkg.getDataFields());

            for (Iterator iter = pkg.getProcesses().iterator(); iter.hasNext();) {
                Process process = (Process) iter.next();

                addProcessPossibleReferencers(checkObjs, process);
            }

        } else if (dataContainer instanceof Process) {
            Process process = (Process) dataContainer;

            addProcessPossibleReferencers(checkObjs, process);

        } else if (dataContainer instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) dataContainer;

            //
            // Things that can reference process interface params are...

            // Methods...
            checkObjs.addAll(processInterface.getStartMethods());
            checkObjs.addAll(processInterface.getIntermediateMethods());

            // Other params (because performer fields can have scripts).
            checkObjs.addAll(processInterface.getFormalParameters());

            // Add the Error Methods for start and intermediate methods.
            for (StartMethod startMethod : processInterface.getStartMethods()) {
                checkObjs.addAll(startMethod.getErrorMethods());
            }
            for (IntermediateMethod intermediateMethod : processInterface
                    .getIntermediateMethods()) {
                checkObjs.addAll(intermediateMethod.getErrorMethods());
            }
            // Add the activities and transitions in all processes that
            // implement this interface as they can all reference an inherited
            // process interface.
            Package pkg = Xpdl2ModelUtil.getPackage(processInterface);

            for (Iterator iterator = pkg.getProcesses().iterator(); iterator
                    .hasNext();) {
                Process process = (Process) iterator.next();

                ProcessInterface implementedInterface =
                        LocalPackageProcessInterfaceUtil
                                .getImplementedProcessInterface(process);

                if (implementedInterface != null
                        && implementedInterface.getId()
                                .equals(processInterface.getId())) {
                    addProcessPossibleReferencers(checkObjs, process);
                }
            }

        } else if (dataContainer instanceof Activity) {
            Activity dataContainerAct = (Activity) dataContainer;

            //
            // Things that can reference activity scope data...
            addActivityPossibleReferencers(checkObjs, dataContainerAct);

        }

        return checkObjs;
    }

    /**
     * For getReferenceObjects() - get all the potential references of data
     * fields in given activity .
     * 
     * @param checkObjs
     * @param activity
     */
    private static void addActivityPossibleReferencers(
            ArrayList<EObject> checkObjs, Activity activity) {
        // The activity itself.
        checkObjs.add(activity);

        // Other fields (because performer fields can have scripts).
        checkObjs.addAll(activity.getDataFields());

        // If it's an embedded sub-process, then all of it's child tasks and
        // flows (and their children).
        if (activity.getBlockActivity() != null) {
            Collection<Activity> subActs =
                    Xpdl2ModelUtil.getAllActivitiesInEmbeddedSubProc(activity);
            for (Activity subAct : subActs) {
                checkObjs.add(subAct);
                checkObjs.addAll(subAct.getDataFields());
            }

            checkObjs.addAll(Xpdl2ModelUtil
                    .getAllTransitionsInEmbeddedSubProc(activity));
        }

        return;
    }

    /**
     * For getReferenceObjects() - get all the potential references of data
     * fields in given process.
     * 
     * @param checkObjs
     * @param process
     */
    private static void addProcessPossibleReferencers(
            ArrayList<EObject> checkObjs, Process process) {
        //
        // Process data fields / params can be referenced by...

        // All activities in process.
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        checkObjs.addAll(allActivitiesInProc);

        // All transitions in process.
        checkObjs.addAll(Xpdl2ModelUtil.getAllTransitionsInProc(process));

        // Other data fields/formal params (because performer fields can have
        // scripts).
        checkObjs.addAll(process.getDataFields());
        checkObjs.addAll(process.getFormalParameters());

        // Data Fields in embedded sub-processes.
        for (Activity act : allActivitiesInProc) {
            checkObjs.addAll(act.getDataFields());
        }
    }

    /**
     * Get a list of data fields that this class object instance will/has
     * checked references for.
     * 
     * @return
     */
    public Set<ProcessRelevantData> getDataBeingChecked() {
        return checkData;
    }

    /**
     * Get list of data fields that are referenced in the given Activity. The
     * returned data fields may be from package or activity's parent process.
     * 
     * @param activity
     * @param wantAllTaskImplReferences
     *            <code>true</code> If the caller wants
     * 
     *            <li>ALL "taskImplementation" data references</li>
     * 
     *            <li>REGARDLESS of whether they are implicitly or explicitly
     *            associated with the activity</li>
     * 
     *            <li>WHERE those references have an impact at runtime even when
     *            they are implicit.</li>
     * 
     *            <li>At present this basically means return taskImplementation
     *            context references for user-tasks (which will be either
     *            implciit or explicit associations). (for instance data that is
     *            implicitly associated with user task forms the user-task->form
     *            interface so it is significantat runtime). Sometimes this is
     *            necessary (when you want to know what data an activity
     *            actually will use at runtime) and sometimes it isn't (for
     *            instance a copy and past of an implicit assoc user task
     *            probably means the user hasn't finished defining it yet and
     *            therefore copy/paste should not carry the implicit ref'd data
     *            with the task)</li>
     * 
     *            <li>Implicit association for other activity types normally
     *            doesn't mean anything as it's usually just a way of
     *            restricting potential access to the fields (not necessarily
     *            defining that they WILL be used). This has the exception of
     *            generated process API activities HOWEVER, those should be ok
     *            because the implicitly associated parameters will be
     *            explicitly used in auto-generated data mappings so will still
     *            be returned as referenced data.</li>
     * 
     * @return Set of Referenced Data Fields or Empty Set if none
     */
    public Set<ProcessRelevantData> getDataReferences(Activity activity,
            boolean wantAllTaskImplReferences) {

        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();

        // resolve references in the activity types we know about.
        Collection<ProcessDataReferenceAndContexts> stdResult =
                getStandardActivityDataReferences(activity,
                        wantAllTaskImplReferences);

        if (stdResult != null) {
            for (ProcessDataReferenceAndContexts dataRef : stdResult) {
                result.add(dataRef.getReferencedData());
            }
        }

        /*
         * If the dataSetInUse is the same size as the set are looking for then
         * we have found references to everything we've been asked to look for
         * and so can quit now to save parsing more scripts.
         */
        if (result.size() != checkData.size()) {
            // Pass the activity to all our data field resolver extenders.
            Collection<IFieldResolverExtension> extResolvers =
                    ResolverExtPointHelper.INSTANCE.getExtensions();

            for (IFieldResolverExtension resolver : extResolvers) {
                Set<ProcessRelevantData> resolverResult =
                        resolver.getActivityDataReferences(activity, checkData);
                if (resolverResult != null && resolverResult.size() > 0) {
                    result.addAll(resolverResult);
                }

                /*
                 * If the dataSetInUse is the same size as the set are looking
                 * for then we have found references to everything we've been
                 * asked to look for and so can quit now to save parsing more
                 * scripts.
                 */
                if (result.size() == checkData.size()) {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Get list of data fields that are referenced in the given Activity. The
     * returned data fields may be from package or activity's parent process.
     * 
     * @param activity
     * @return Set of Referenced Data Fields or Empty Set if none
     */
    public Set<ProcessRelevantData> getDataReferences(Activity activity) {
        return getDataReferences(activity, false);
    }

    /**
     * Get list of formal parameters that are referenced in the given process
     * interface StartMethod/Intermediate.
     * 
     * @param transition
     * @return The subset of data that this resolver instance is checking for
     *         that is referenced in the given interface method.
     */
    public Set<ProcessRelevantData> getDataReferences(
            AssociatedParametersContainer interfaceMethod) {
        EList associatedParametersList =
                interfaceMethod.getAssociatedParameters();

        Set<ProcessRelevantData> result =
                getAssociatedParametersListRefs(associatedParametersList);

        return result;
    }

    /**
     * Get list of process relevant data that are referenced in the given
     * ProcessRelevantData. The returned ProcessRelevantData may be from package
     * or process of the ProcessRelevantData
     * 
     * @param processRelevantData
     * @return Set of Referenced Process Relevant Data or Empty Set if none
     */
    public Set<ProcessRelevantData> getDataReferences(
            ProcessRelevantData processRelevantData) {
        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            if (resolver instanceof IFieldResolverExtension2) {
                IFieldResolverExtension2 resolver2 =
                        (IFieldResolverExtension2) resolver;
                Set<ProcessRelevantData> resolverResult =
                        resolver2
                                .getProcessRelevantDataReferences(processRelevantData,
                                        checkData);
                if (resolverResult != null && resolverResult.size() > 0) {
                    result.addAll(resolverResult);
                }
            }

            /*
             * If the dataSetInUse is the same size as the set are looking for
             * then we have found references to everything we've been asked to
             * look for and so can quit now to save parsing more scripts.
             */
            if (result.size() == checkData.size()) {
                break;
            }
        }
        return result;
    }

    /**
     * Get list of data fields that are referenced in the given transition. The
     * returned data fields may be from package or activity's parent process.
     * 
     * @param transition
     * @return Set of Referenced Data Fields or Empty Set if none
     */
    public Set<ProcessRelevantData> getDataReferences(Transition transition) {
        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();

        // Pass the transition to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Set<ProcessRelevantData> resolverResult =
                    resolver.getTransitionDataReferences(transition, checkData);
            if (resolverResult != null && resolverResult.size() > 0) {
                result.addAll(resolverResult);
            }

            /*
             * If the dataSetInUse is the same size as the set are looking for
             * then we have found references to everything we've been asked to
             * look for and so can quit now to save parsing more scripts.
             */
            if (result.size() == checkData.size()) {
                break;
            }
        }

        // System.err
        // .println("Fix soon Simple field resolution should only be done when
        // not being done by java script");
        // Set<ProcessRelevantData> simpleResult =
        // simpleGetTransitionDataFieldRefs(transition);
        //
        // result.addAll(simpleResult);

        return result;
    }

    /**
     * Get list of data fields that are referenced in the given transition. The
     * returned data fields may be from package or activity's parent process.
     * 
     * @param messageFlow
     * @return Set of Referenced Data Fields or Empty Set if none
     */
    public Set<ProcessRelevantData> getDataReferences(MessageFlow messageFlow) {
        // Pass the message flow to all our data field resolver extenders.
        // for now, we'll do a quick and dirty implementation of our own.
        return simpleGetMessageFlowDataFieldRefs(messageFlow);
    }

    /**
     * Resolve references to data fields / formal params in the activity types
     * we know about.
     * <p>
     * For user tasks and independent sub-process call tasks we know that
     * parameter mapping is by field / param name so we can handle these
     * references here.
     * <p>
     * For catch error event, we can resolve references to local data that is
     * mapped to from thrown error output params.
     * 
     * @param activity
     * @param wantAllTaskImplReferences
     *            <code>true</code> If the caller wants
     * 
     *            <li>ALL "taskImplementation" data references</li>
     * 
     *            <li>REGARDLESS of whether they are implicitly or explicitly
     *            associated with the activity</li>
     * 
     *            <li>WHERE those references have an impact at runtime even when
     *            they are implicit.</li>
     * 
     *            <li>At present this basically means return taskImplementation
     *            context references for user-tasks (which will be either
     *            implciit or explicit associations). (for instance data that is
     *            implicitly associated with user task forms the user-task->form
     *            interface so it is significantat runtime). Sometimes this is
     *            necessary (when you want to know what data an activity
     *            actually will use at runtime) and sometimes it isn't (for
     *            instance a copy and past of an implicit assoc user task
     *            probably means the user hasn't finished defining it yet and
     *            therefore copy/paste should not carry the implicit ref'd data
     *            with the task)</li>
     * 
     *            <li>Implicit association for other activity types normally
     *            doesn't mean anything as it's usually just a way of
     *            restricting potential access to the fields (not necessarily
     *            defining that they WILL be used). This has the exception of
     *            generated process API activities HOWEVER, those should be ok
     *            because the implicitly associated parameters will be
     *            explicitly used in auto-generated data mappings so will still
     *            be returned as referenced data.</li>
     * 
     * @return set of process data that is referenced in the given activity
     */
    private Collection<ProcessDataReferenceAndContexts> getStandardActivityDataReferences(
            Activity activity, boolean wantAllTaskImplReferences) {
        Map<ProcessRelevantData, ProcessDataReferenceAndContexts> result =
                new HashMap<ProcessRelevantData, ProcessDataReferenceAndContexts>();

        Implementation impl = activity.getImplementation();

        // There are certain activity types that we do know how to handle.
        // SubFlow and User Task have standardised (currently) parameters which
        // are data field / param references.
        if (impl instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) impl;

            EList dataMappings = subFlow.getDataMappings();

            for (Iterator iter = dataMappings.iterator(); iter.hasNext();) {
                DataMapping dm = (DataMapping) iter.next();

                if (dm.getActual() != null) {
                    String chkRef = dm.getActual().getText();

                    ProcessRelevantData data =
                            getCheckedFieldOrParam_ByName(chkRef);
                    if (data != null) {
                        if (!DirectionType.OUT_LITERAL
                                .equals(dm.getDirection())) {
                            mergeDataReference(result,
                                    data,
                                    new DataReferenceContext.MappingInReferenceContext(
                                            Messages.Xpdl2FieldOrParamResolver_InputToSubProcess_label));
                        } else {
                            mergeDataReference(result,
                                    data,
                                    new DataReferenceContext.MappingOutReferenceContext(
                                            Messages.Xpdl2FieldOrParamResolver_OutputFromSubProcess_label));
                        }
                    }
                }
            }

            Object obj =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessIdentifierField());
            if (obj instanceof String) {
                String fieldName = (String) obj;
                ProcessRelevantData data =
                        getCheckedFieldOrParam_ByName(fieldName);
                if (data != null) {
                    mergeDataReference(result,
                            data,
                            DataReferenceContext.CONTEXT_SUBPROC_RUNTIME_IDENTIFIER);
                }
            }

        } else if (impl instanceof Task && ((Task) impl).getTaskUser() != null) {
            TaskUser taskUser = ((Task) impl).getTaskUser();

            /*
             * Sid - Used to return references from TaskUser.messageIn/Out data
             * mappings BUT we never use those. So removed that part and
             * replaced with new handling for new wantAllTaskImplReferences
             * stuff).
             */
            if (wantAllTaskImplReferences) {
                if (!LocalPackageProcessInterfaceUtil
                        .isImplicitAssociationDisabled(activity)) {
                    /*
                     * If there are explicit associations add those as the
                     * user-task's "taskImplementation" context references.
                     */
                    EList associatedParams =
                            LocalPackageProcessInterfaceUtil
                                    .getActivityAssociatedParameters(activity);

                    if (associatedParams != null && !associatedParams.isEmpty()) {
                        Set<ProcessRelevantData> refs =
                                getAssociatedParametersListRefs(associatedParams);

                        if (refs != null && refs.size() > 0) {
                            for (ProcessRelevantData data : refs) {
                                mergeDataReference(result,
                                        data,
                                        DataReferenceContext.CONTEXT_ACTIVITY_IMPLEMENTATION);
                            }
                        }
                    } else {
                        /*
                         * If there are no explicit associations (and implicit
                         * not disabled) then effectively every field we were
                         * passed (assuming all are in scope of activity) is
                         * reference implicitly
                         */
                        for (ProcessRelevantData data : this.checkData) {
                            mergeDataReference(result,
                                    data,
                                    DataReferenceContext.CONTEXT_ACTIVITY_IMPLEMENTATION);
                        }
                    }
                }
            }

        } else if (activity.getEvent() instanceof IntermediateEvent) {
            if (activity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                /*
                 * Sid XPD-3666:
                 * 
                 * Catch error mappings need to be handled differently depending
                 * on the type of error caught, this is handled via contribution
                 * to ext point (by BpmnCatchErrorEventMappingFieldResolver and
                 * WebServiceMappingJavaScriptFieldResolver) now
                 */

            } else if (activity.getEvent().getEventTriggerTypeNode() instanceof TriggerTimer) {
                TriggerTimer timer =
                        (TriggerTimer) activity.getEvent()
                                .getEventTriggerTypeNode();

                /*
                 * Check for runtime identified field in Calendar References
                 */
                CalendarReference calendarReference =
                        getCalendarReference(timer);
                if (calendarReference != null) {
                    String dataFieldId = calendarReference.getDataFieldId();
                    if (dataFieldId != null) {
                        ProcessRelevantData data =
                                getCheckedFieldOrParam_ById(dataFieldId);
                        if (data != null) {
                            mergeDataReference(result,
                                    data,
                                    DataReferenceContext.CONTEXT_CALENDAR_REFERENCE);
                        }
                    }
                }
            }
        }

        // Look for participant = performer data field.
        EList performers = activity.getPerformerList();
        for (Iterator iter = performers.iterator(); iter.hasNext();) {
            Performer performer = (Performer) iter.next();

            String performerId = performer.getValue();

            ProcessRelevantData data = getCheckedFieldOrParam_ById(performerId);
            if (data != null) {
                mergeDataReference(result,
                        data,
                        DataReferenceContext.CONTEXT_ACTIVITY_PERFORMER);
            }

        }

        // Look for references to parameter in activity (start/intermediate
        // event or receive task) associated parameters list.
        EList associatedParams =
                LocalPackageProcessInterfaceUtil
                        .getActivityAssociatedParameters(activity);
        if (associatedParams != null) {
            Set<ProcessRelevantData> refs =
                    getAssociatedParametersListRefs(associatedParams);
            if (refs != null && refs.size() > 0) {
                for (ProcessRelevantData data : refs) {
                    mergeDataReference(result,
                            data,
                            DataReferenceContext.CONTEXT_EXPLICIT_ASSOCIATION);
                }

            }
        }

        return result.values();
    }

    /**
     * Get the {@link CalendarReference} from the given container.
     * 
     * @param container
     * @return CalendarReference, or <code>null</code> if one is not set.
     */
    private CalendarReference getCalendarReference(
            OtherElementsContainer container) {
        Object element =
                Xpdl2ModelUtil.getOtherElement(container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CalendarReference());

        return (CalendarReference) (element instanceof CalendarReference ? element
                : null);
    }

    /**
     * Find and return a field or param (that creator originally asked us to
     * check for) that matches the given name.
     * 
     * @param chkRefName
     */
    private ProcessRelevantData getCheckedFieldOrParam_ByName(String chkRefName) {
        if (chkRefName != null) {
            return checkDataNameMap.get(chkRefName);
        }
        return null;
    }

    /**
     * Find and return a field or param (that creator originally asked us to
     * check for) that matches the given id.
     * 
     * @param chkRefName
     */
    private ProcessRelevantData getCheckedFieldOrParam_ById(String chkRefId) {
        if (chkRefId != null) {
            return checkDataIdMap.get(chkRefId);
        }
        return null;
    }

    /**
     * @param messageFlow
     * @return
     */
    private Set<ProcessRelevantData> simpleGetMessageFlowDataFieldRefs(
            MessageFlow messageFlow) {
        return Collections.emptySet();
    }

    /**
     * @param transition
     * @return Set of Referenced Data Fields or Empty Set if none
     */
    private Set<ProcessRelevantData> simpleGetTransitionDataFieldRefs(
            Transition transition) {
        Condition cond = transition.getCondition();
        if (cond != null) {
            Expression exp = cond.getExpression();

            if (exp != null) {
                String expStr = exp.getText();

                if (expStr != null) {
                    return getFieldRefs(expStr);
                }
            }
        }
        return Collections.emptySet();
    }

    /**
     * Get field references in given expression string.
     * 
     * @param text
     * @return
     */
    private Set<ProcessRelevantData> getFieldRefs(String text) {
        HashSet<ProcessRelevantData> refFields =
                new HashSet<ProcessRelevantData>();

        for (ProcessRelevantData dataField : checkData) {
            if (isFieldReferenced(dataField, text)) {
                refFields.add(dataField);
            }
        }
        return refFields;
    }

    /**
     * VERY simple check whether the given datafield is referenced in the given
     * string.
     * <p>
     * <b>
     * <li>Assumes that data fields contain ONLY the characters A-Z, a-z and
     * 0-9.</li>
     * <li>This method does NOT take quotes etc into account.</li>
     * </b>
     * </p>
     * 
     * @param fieldOrParam
     * @param text
     * @return
     */
    private boolean isFieldReferenced(ProcessRelevantData fieldOrParam,
            String text) {
        boolean isRefd = false;

        final String FIELD_NAME_CHARS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; //$NON-NLS-1$

        String fldName = fieldOrParam.getName();

        int pos = text.indexOf(fldName);

        if (pos >= 0) {
            // found field name, make sure it's a 'whole word'
            isRefd = true; // Now assume true unless find otherwise.

            if (pos != 0) {
                // Not first thing in string, check the previous character.
                String prev = text.substring(pos - 1, pos);

                if (FIELD_NAME_CHARS.contains(prev)) {
                    // char before is valid field name so not whole word.
                    isRefd = false;
                }
            }

            if (isRefd) {
                // char before isn't valid field name char, check char after.
                int fldNameLen = fldName.length();

                if ((pos + fldNameLen) < text.length()) {
                    int nextPos = pos + fldNameLen;
                    String next = text.substring(nextPos, nextPos + 1);

                    if (FIELD_NAME_CHARS.contains(next)) {
                        // next char is valid field name char so not whole word.
                        isRefd = false;
                    }
                }
            }

        }

        return isRefd;
    }

    /**
     * Check for references to data (that this resolver instance is looking for)
     * in the given list of formal parameters.
     * 
     * @param associatedParametersList
     * @return The subset of data that this resolver instance is checking for
     *         that is referenced in the given interface method.
     */
    private Set<ProcessRelevantData> getAssociatedParametersListRefs(
            EList associatedParametersList) {
        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();

        for (Iterator iterator = associatedParametersList.iterator(); iterator
                .hasNext();) {
            AssociatedParameter associatedParam =
                    (AssociatedParameter) iterator.next();

            // See if this associated param is a ref to one of the ones we are
            // looking for.
            ProcessRelevantData data =
                    getCheckedFieldOrParam_ByName(associatedParam
                            .getFormalParam());
            if (data != null) {
                // This data is Referenced in associated parameters
                result.add(data);
            }

        }
        return result;
    }

    /**
     * Get list of data fields that are referenced in the given Activity, each
     * with a set of context identification strings naming the contexts in which
     * the activity is referenced on the given activity. The returned data
     * fields may be from package or activity's parent process.
     * 
     * @param activity
     * 
     * @return Set of Referenced Data Fields or Empty Set if none
     */
    public Collection<ProcessDataReferenceAndContexts> getDataContextReferences(
            Activity activity) {
        HashMap<ProcessRelevantData, ProcessDataReferenceAndContexts> result =
                new HashMap<ProcessRelevantData, ProcessDataReferenceAndContexts>();

        /*
         * resolve references in the activity types we know about. (includign
         * user task implciit associations)
         */
        Collection<ProcessDataReferenceAndContexts> stdResult =
                getStandardActivityDataReferences(activity, true);

        if (stdResult != null && stdResult.size() > 0) {
            /* Merge the resolver's list into main list. */
            mergeDataReferences(result, stdResult);
        }

        // Pass the activity to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {

            if (resolver instanceof IFieldContextResolverExtension) {
                /*
                 * If resolver supports new reference and context method use
                 * that.
                 */
                Set<ProcessDataReferenceAndContexts> resolverResult =
                        ((IFieldContextResolverExtension) resolver)
                                .getDataReferences(activity, checkData);

                /* Merge the resolver's list into main list. */
                mergeDataReferences(result, resolverResult);

            } else {
                /* Else use the old interface. */
                Set<ProcessRelevantData> resolverResult =
                        resolver.getActivityDataReferences(activity, checkData);
                if (resolverResult != null) {
                    for (ProcessRelevantData data : resolverResult) {
                        /*
                         * If this data is not already in the result then add a
                         * new reference with no context. Otherwise, we'll leave
                         * the existing entry and it's contexts in place.
                         */
                        if (!result.containsKey(data)) {
                            result.put(data,
                                    new ProcessDataReferenceAndContexts(data));
                        }
                    }
                }
            }

        }

        return result.values();
    }

    /**
     * Get list of data fields that are referenced in the given Transition
     * (sequence flow), each with a set of context identification strings naming
     * the contexts in which the activity is referenced on the given activity.
     * 
     * @param activity
     * @return Set of Referenced Data Fields or Empty Set if none
     */
    public Collection<ProcessDataReferenceAndContexts> getDataContextReferences(
            Transition transition) {
        HashMap<ProcessRelevantData, ProcessDataReferenceAndContexts> result =
                new HashMap<ProcessRelevantData, ProcessDataReferenceAndContexts>();

        // Pass the transition to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {

            if (resolver instanceof IFieldContextResolverExtension) {
                /*
                 * If resolver supports new reference and context method use
                 * that.
                 */
                Set<ProcessDataReferenceAndContexts> resolverResult =
                        ((IFieldContextResolverExtension) resolver)
                                .getDataReferences(transition, checkData);

                /* Merge the resolver's list into main list. */
                mergeDataReferences(result, resolverResult);

            } else {
                /* Else use the old interface. */
                Set<ProcessRelevantData> resolverResult =
                        resolver.getTransitionDataReferences(transition,
                                checkData);
                if (resolverResult != null) {
                    for (ProcessRelevantData data : resolverResult) {
                        /*
                         * If this data is not already in the result then add a
                         * new reference with no context. Otherwise, we'll leave
                         * the existing entry and it's contexts in place.
                         */
                        if (!result.containsKey(data)) {
                            result.put(data,
                                    new ProcessDataReferenceAndContexts(data));
                        }
                    }
                }
            }

        }

        return result.values();
    }

    /**
     * Merge the source references into the target map of data to data
     * reference.
     * 
     * @param target
     * @param dataRef
     * @param context
     */
    public void mergeDataReference(
            Map<ProcessRelevantData, ProcessDataReferenceAndContexts> target,
            ProcessRelevantData data, DataReferenceContext context) {
        if (data != null) {
            /* Get existing or add new data reference to result. */
            ProcessDataReferenceAndContexts existingDataRef = target.get(data);

            if (existingDataRef == null) {
                /*
                 * No existing reference, add this new data reference.
                 */
                target.put(data, new ProcessDataReferenceAndContexts(data,
                        context));

            } else {
                /* Merge extra contexts into existing reference. */
                existingDataRef.addContext(context);
            }
        }
    }

    /**
     * Merge the source set of references into the target map of data to data
     * reference.
     * 
     * @param target
     * @param source
     */
    public void mergeDataReferences(
            Map<ProcessRelevantData, ProcessDataReferenceAndContexts> target,
            Collection<ProcessDataReferenceAndContexts> source) {
        if (source != null) {
            for (ProcessDataReferenceAndContexts dataRef : source) {
                /* Get existing or add new data reference to result. */
                ProcessDataReferenceAndContexts existingDataRef =
                        target.get(dataRef.getReferencedData());

                if (existingDataRef == null) {
                    /*
                     * No existing reference, add this new data reference.
                     */
                    target.put(dataRef.getReferencedData(), dataRef);

                } else {
                    /* Merge extra contexts into existing reference. */
                    if (dataRef.hasContexts()) {
                        existingDataRef.addAllContexts(dataRef.getContexts());
                    }
                }
            }
        }
    }

}
