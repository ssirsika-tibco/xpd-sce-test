/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.internal.provider;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.n2.cds.reader.CdsExtendedJScriptProcessDefinitionReader;
import com.tibco.xpd.n2.pe.PEActivator;
import com.tibco.xpd.n2.pe.internal.reader.PEJScriptProcessDefinitionReader;
import com.tibco.xpd.n2.pe.util.PEN2Utils;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.UMLScriptClassWrapperRelevantData;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Data provider for PE JScript process and Case signal attributes scripts.
 * <p>
 * This wraps and delegates to the two Process Engine related UML JScript class
 * definition readers.
 * <p>
 * The reason for this is that we need to provide CaseSignalAttribtues related
 * JS Classes <b>conditionally</b> only in scripts in case signal catch activity
 * or activities downstream of that.
 * <p>
 * The JsDefinitionReaders that provide these JS classes have no contextual
 * information (the scripting scenario that is in play) when they are asked for
 * their contributed classes and hence cannot conditionalise the availability of
 * the JS Classes they provide.
 * <p>
 * However IScriptRelevantDataProviders do have access to the context and input
 * object in play when they are asked for the supported JS Classes and hence can
 * conditionalise whether to return CaseSignalAttribtues class based on input
 * activity.
 * 
 * @author sajain
 * @since Mar 24, 2015
 */
public class PEJScriptProcessAndCSARelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    /**
     * PE JScript Process Definition Reader reader class instance.
     */
    PEJScriptProcessDefinitionReader delegatePEJSSPDefinitionReader =
            new PEJScriptProcessDefinitionReader();

    /**
     * Case signal attributes reader class instance.
     */
    CaseSignalAttributesReader delegateCSADefinitionReader =
            new CaseSignalAttributesReader();

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
     * 
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {

        Activity activity = getContextActivity();

        Collection<JsClass> classes = new ArrayList<JsClass>();

        /*
         * Always load the classes supported by
         * PEJScriptProcessDefinitionReader.
         */
        classes.addAll(delegatePEJSSPDefinitionReader.getSupportedClasses());

        /*
         * If we have an activity in the flow of a case data signal catch event
         * handler/event sub-process, then add the classes supported by
         * CaseSignalAttributesReader as well.
         */
        if (activity != null) {

            if (isCaseDataEvent(activity)
                    || isCaseDataEventHandlerOrESPFlowActivity(activity)) {

                classes.addAll(delegateCSADefinitionReader
                        .getSupportedClasses());
            }
        }

        List<IScriptRelevantData> scriptRelevantDataList =
                new ArrayList<IScriptRelevantData>();

        for (JsClass eachClass : classes) {
            scriptRelevantDataList.add(new UMLScriptClassWrapperRelevantData(
                    eachClass));
        }

        return scriptRelevantDataList;
    }

    /**
     * Return <code>true</code> if the passed activity is a Case Data event.
     * 
     * @param activity
     *            Activity to be checked.
     * 
     * @return <code>true</code> if the passed activity is a Case Data event.
     */
    private boolean isCaseDataEvent(Activity activity) {

        Event event = activity.getEvent();

        if (event != null) {

            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                Object otherAttribute =
                        Xpdl2ModelUtil.getOtherAttribute(signal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalType());

                if (otherAttribute instanceof SignalType) {

                    SignalType sigType = (SignalType) otherAttribute;

                    return SignalType.CASE_DATA.equals(sigType) ? true : false;
                }
            }
        }

        return false;
    }

    /**
     * Return <code>true</code> if the specified activity falls ahead in the
     * flow of a case data catch signal event handler/event sub-process,
     * <code>false</code> otherwise.
     * 
     * @param activity
     * @return <code>true</code> if the specified activity falls ahead in the
     *         flow of a case data catch signal event handler/event sub-process,
     *         <code>false</code> otherwise.
     */
    private boolean isCaseDataEventHandlerOrESPFlowActivity(Activity activity) {

        List<Activity> caseDataEvents = new ArrayList<Activity>();

        FlowContainer container = activity.getFlowContainer();

        ProcessFlowAnalyser flowAnalyser =
                new ProcessFlowAnalyser(true, container);

        /*
         * Collect the list of case data event handlers/event subprocess start
         * event.
         */
        for (Activity eachActivity : container.getActivities()) {

            /*
             * Check if the activity is a case data signal catch event
             * handler/event subprocess start event.
             */
            if (isCaseDataEvent(eachActivity)
                    && (EventObjectUtil
                            .isEventSubProcessStartEvent(eachActivity) || Xpdl2ModelUtil
                            .isEventHandlerActivity(eachActivity))) {

                caseDataEvents.add(eachActivity);
            }
        }

        /*
         * XPD-7680: Saket: If the list of case signal events is empty, then
         * check if the selected activity is in an activity set and if that's
         * the case, then call this method recursively for that 'container'
         * activity (because we want the CaseSignalAttributes API available for
         * the activities inside an embedded sub-process downstream to a Case
         * Signal Catch Event). Please note that the other way to do this would
         * be to pass the parent PROCESS as the flow container while creating
         * the flow analyzer instance. But since flow analyzer operations are
         * very heavy, so we should be as specific as we can with the flow
         * container which we pass to flow analyzer constructor.
         */
        if (!caseDataEvents.isEmpty()) {

            for (Activity eachCaseDataEvent : caseDataEvents) {

                Set<Activity> downstreamOfCaseDataEvents =
                        flowAnalyser.getDownstreamActivities(eachCaseDataEvent
                                .getId(), false);

                if (downstreamOfCaseDataEvents.contains(activity)) {
                    return true;
                }
            }

        } else {

            if (container instanceof ActivitySet) {

                ActivitySet activitySet = (ActivitySet) container;

                Activity embSubProcActivityForActSet =
                        Xpdl2ModelUtil.getEmbSubProcActivityForActSet(activity
                                .getProcess(), activitySet.getId());

                if (isCaseDataEventHandlerOrESPFlowActivity(embSubProcActivityForActSet)) {

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return the context activity for checking whether case signal attribs
     *         reader classes should be included (for seqwuence flows the
     *         context activity is the soruce activity).
     */
    private Activity getContextActivity() {
        if (getEObject() instanceof Activity) {
            return (Activity) getEObject();

        } else if (Xpdl2ModelUtil.getAncestor(getEObject(), Activity.class) instanceof Activity) {
            return (Activity) Xpdl2ModelUtil.getAncestor(getEObject(),
                    Activity.class);

        } else if (getEObject() instanceof Transition) {
            Transition tr = (Transition) getEObject();

            if (tr.getFlowContainer() != null) {
                return tr.getFlowContainer().getActivity(tr.getFrom());
            }
        }

        return null;

    }

    /**
     * Case signal attributes reader to deal with CaseSignalAttributes.uml.
     * 
     * @author sajain
     * @since Mar 24, 2015
     */
    public class CaseSignalAttributesReader extends
            CdsExtendedJScriptProcessDefinitionReader {

        @Override
        protected URI getURI() {

            URL entry =
                    PEActivator.getDefault().getBundle()
                            .getEntry(PEN2Utils.CASE_SIGNAL_MODEL_FILE_NAME);

            return URI.createURI(entry.toExternalForm());
        }

    }

}
