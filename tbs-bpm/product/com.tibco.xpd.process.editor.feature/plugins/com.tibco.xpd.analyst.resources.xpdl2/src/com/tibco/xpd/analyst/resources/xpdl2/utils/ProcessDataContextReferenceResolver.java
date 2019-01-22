/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class provides information about the process data that is referenced
 * from given activities and transitions (sequence flows) <b>and</b>, in the
 * context(s) in which the data is referenced within the activity.
 * <p>
 * By 'context' we mean 'the type of thing that references the data' such as a
 * particular type of script, or an explicit association and so on. <b>Not all
 * data reference contexts are necessarily known by base-studio (field reference
 * resolution is a contributable extension point framework), therefore not all
 * context-id's are known. However, the common ones that are known can be found
 * as constants in {@link DataReferenceContext}.</b>
 * <p>
 * This class maintains a load-on-demand cache, this makes things more efficient
 * if you wish to make several calls per-process-per-activity for different
 * contexts. It does mean though that if the process model is changed the
 * results may be inaccurate.
 * <p>
 * Note that this class only deals in <b>explicit references to data</b> (i.e.
 * not implicit associations); if you want to access the explicitly/implicitly
 * associated data for an activity the {@link ActivityInterfaceDataUtil} class
 * can provide the necessary information.
 * 
 * @author aallway
 * @since 19 Jun 2012
 */
public class ProcessDataContextReferenceResolver {

    private Map<EObject, Collection<ProcessDataReferenceAndContexts>> dataReferenceCache =
            new HashMap<EObject, Collection<ProcessDataReferenceAndContexts>>();

    /**
     * Get the set of data referenced in the given activity in the given context
     * (i.e. script type and so on).
     * <p>
     * e.g {@link #getDataReferences(Activity, DataReferenceContext))} passing
     * {@link DataReferenceContext#CONTEXT_INITIATE_SCRIPT} will return the set
     * of data used in the initiate script of an activity
     * 
     * @param activity
     * @param context
     *            The context in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given context
     */
    public Set<ProcessRelevantData> getDataReferences(Activity activity,
            DataReferenceContext context) {
        return getDataReferences(activity,
                new DataReferenceContext[] { context });
    }

    /**
     * Get the set of data referenced in the given activity in the given context
     * (i.e. script type and so on).
     * <p>
     * e.g Calling this method passing
     * 
     * [{@link DataReferenceContext#CONTEXT_INITIATE_SCRIPT},
     * {@link DataReferenceContext#CONTEXT_COMPLETE_SCRIPT}]
     * 
     * will return the set of data used in the initiate-script OR the complete
     * script of an activity.
     * 
     * @param activity
     * @param contexts
     *            The contexts in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given contexts
     */
    public Set<ProcessRelevantData> getDataReferences(Activity activity,
            DataReferenceContext[] contexts) {
        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        /*
         * Get (and cache for later reference) the set of all data references
         * and the contexts they're referenced in.
         */
        Collection<ProcessDataReferenceAndContexts> dataReferences =
                getDataReferences(activity);

        /* Then search for any that have the given context. */
        for (ProcessDataReferenceAndContexts dataRef : dataReferences) {
            /*
             * The data is referenced, check if it's in any of the ways caller
             * is interested in.
             */
            for (DataReferenceContext context : contexts) {
                if (dataRef.getContexts().contains(context)) {
                    referencedData.add(dataRef.getReferencedData());
                    break;
                }
            }
        }

        return referencedData;
    }

    /**
     * Get the set of data referenced in the given activity in the given context
     * (i.e. script type and so on).
     * <p>
     * e.g Calling this method passing
     * DataReferenceContext.CONTEXT_INITIATE_SCRIPT.getContext() will return the
     * set of data used in the initiate script of an activity
     * 
     * @param activity
     * @param contextId
     *            The context in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given contexts
     */
    public Set<ProcessRelevantData> getDataReferences(Activity activity,
            String contextId) {
        return getDataReferences(activity,
                new DataReferenceContext[] { new DataReferenceContext(contextId) });
    }

    /**
     * Get the set of data referenced in the given activity in ANY of the given
     * contexts (i.e. script type and so on).
     * <p>
     * e.g Calling this method passing
     * 
     * [DataReferenceContext.CONTEXT_INITIATE_SCRIPT.getContext(),
     * DataReferenceContext.CONTEXT_COMPLETE_SCRIPT.getContext()]
     * 
     * will return the set of data used in the initiate-script OR the complete
     * script of an activity.
     * 
     * @param activity
     * @param contextIds
     *            The contexts in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given contexts
     */
    public Set<ProcessRelevantData> getDataReferences(Activity activity,
            String[] contextIds) {
        DataReferenceContext[] contexts =
                new DataReferenceContext[contextIds.length];

        for (int i = 0; i < contextIds.length; i++) {
            contexts[i] = new DataReferenceContext(contextIds[i]);
        }

        return getDataReferences(activity, contexts);
    }

    /**
     * Get the complete set of data referenced by the given activity regardless
     * of the context in which it is referenced.
     * 
     * @param activity
     * 
     * @return The complete set of data referenced by the given activity
     *         regardless of the context in which it is referenced (context info
     *         is provided in the individual
     *         {@link ProcessDataReferenceAndContexts} objects.
     */
    public Collection<ProcessDataReferenceAndContexts> getDataReferences(
            Activity activity) {
        /* Check for already cached set. */
        Collection<ProcessDataReferenceAndContexts> dataReferences =
                dataReferenceCache.get(activity);

        if (dataReferences != null) {
            return dataReferences;
        }

        /*
         * Not cached yet, use the Xpdl2FieldOrParamResolver to get the info we
         * need.
         */
        List<ProcessRelevantData> allInScopeData =
                ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(activity);

        Xpdl2FieldOrParamResolver dataResolver =
                new Xpdl2FieldOrParamResolver(new HashSet<ProcessRelevantData>(
                        allInScopeData));

        dataReferences = dataResolver.getDataContextReferences(activity);

        /* Cache the result. */
        dataReferenceCache.put(activity, dataReferences);

        return dataReferences;
    }

    /**
     * Get the set of data referenced in the given transition in the given
     * context (i.e. script type and so on).
     * <p>
     * e.g Calling thismethod passing
     * {@link DataReferenceContext#CONTEXT_SEQUENCEFLOW_SCRIPT} will return the
     * set of data used in the condition script the transition
     * 
     * @param transition
     * @param context
     *            The context in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given context
     */
    public Set<ProcessRelevantData> getDataReferences(Transition transition,
            DataReferenceContext context) {
        return getDataReferences(transition,
                new DataReferenceContext[] { context });
    }

    /**
     * Get the set of data referenced in the given transition in ANY of the
     * given contexts (i.e. script type and so on).
     * <p>
     * e.g Calling this method passing [
     * {@link DataReferenceContext#CONTEXT_SEQUENCEFLOW_SCRIPT}] will return the
     * set of data used in the condition script the transition
     * 
     * @param transition
     * @param contexts
     *            The contexts in which the data is referenced (i.e. type of
     *            'things' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given contexts
     */
    public Set<ProcessRelevantData> getDataReferences(Transition transition,
            DataReferenceContext[] contexts) {
        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        /*
         * Get (and cache for later reference) the set of all data references
         * and the contexts they're referenced in.
         */
        Collection<ProcessDataReferenceAndContexts> dataReferences =
                getDataReferences(transition);

        /* Then search for any that have the given context. */
        for (ProcessDataReferenceAndContexts dataRef : dataReferences) {
            /*
             * The data is referenced, check if it's in any of the ways caller
             * is interested in.
             */
            for (DataReferenceContext context : contexts) {
                if (dataRef.getContexts().contains(context)) {
                    referencedData.add(dataRef.getReferencedData());
                    break;
                }
            }
        }

        return referencedData;
    }

    /**
     * Get the set of data referenced in the given transition in the given
     * context (i.e. script type and so on).
     * <p>
     * e.g Calling this method passing
     * DataReferenceContext.CONTEXT_SEQUENCEFLOW_SCRIPT.getContext() will return
     * the set of data used in the condition script the transition
     * 
     * @param transition
     * @param contextId
     *            The context in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given context
     */
    public Set<ProcessRelevantData> getDataReferences(Transition transition,
            String contextId) {
        return getDataReferences(transition,
                new DataReferenceContext[] { new DataReferenceContext(contextId) });
    }

    /**
     * Get the set of data referenced in the given transition in ANY of the
     * given contexts (i.e. script type and so on).
     * <p>
     * e.g Calling this method passing
     * [DataReferenceContext.CONTEXT_SEQUENCEFLOW_SCRIPT.getContext()] will
     * return the set of data used in the condition script the transition
     * 
     * @param transition
     * @param contextIds
     *            The contexts in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     * 
     * @return the set of data referenced in the given contexts
     */
    public Set<ProcessRelevantData> getDataReferences(Transition transition,
            String[] contextIds) {
        DataReferenceContext[] contexts =
                new DataReferenceContext[contextIds.length];

        for (int i = 0; i < contextIds.length; i++) {
            contexts[i] = new DataReferenceContext(contextIds[i]);
        }

        return getDataReferences(transition, contexts);

    }

    /**
     * Get the complete set of data referenced by the given transition
     * regardless of the context in which it is referenced.
     * 
     * @param transition
     * 
     * @return The complete set of data referenced by the given transition
     *         regardless of the context in which it is referenced (context info
     *         is provided in the individual
     *         {@link ProcessDataReferenceAndContexts} objects.
     */
    public Collection<ProcessDataReferenceAndContexts> getDataReferences(
            Transition transition) {
        /* Check for already cached set. */
        Collection<ProcessDataReferenceAndContexts> dataReferences =
                dataReferenceCache.get(transition);

        if (dataReferences != null) {
            return dataReferences;
        }

        /*
         * Not cached yet, use the Xpdl2FieldOrParamResolver to get the info we
         * need.
         */

        /*
         * Get data in scope of transition's containing embedded sub-process
         * activity set / process
         */
        Collection<ProcessRelevantData> allInScopeData = null;

        FlowContainer flowContainer = transition.getFlowContainer();
        if (flowContainer instanceof ActivitySet) {
            Process process = transition.getProcess();
            if (process != null) {
                Activity activity =
                        Xpdl2ModelUtil.getEmbSubProcActivityForActSet(process,
                                ((ActivitySet) flowContainer).getId());
                if (activity != null) {
                    allInScopeData =
                            ProcessInterfaceUtil
                                    .getAllAvailableRelevantDataForActivity(activity);
                }
            }
        } else if (flowContainer instanceof Process) {
            allInScopeData =
                    ProcessInterfaceUtil
                            .getAllProcessRelevantData((Process) flowContainer);
        }

        if (allInScopeData == null) {
            return Collections.emptySet();
        }

        Xpdl2FieldOrParamResolver dataResolver =
                new Xpdl2FieldOrParamResolver(new HashSet<ProcessRelevantData>(
                        allInScopeData));

        dataReferences = dataResolver.getDataContextReferences(transition);

        /* Cache the result. */
        dataReferenceCache.put(transition, dataReferences);

        return dataReferences;
    }
}
