/* 
 ** 
 **  MODULE:             $RCSfile: ProcessImpl.java $ 
 **                      $Revision: 1.7 $ 
 **                      $Date: 2005/02/02 19:36:10Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: ProcessImpl.java $ 
 **    Revision 1.7  2005/02/02 19:36:10Z  KamleshU 
 **    Changes made to copy the securityPlugIn from parent ProcessStateImpl to child ProcessStateImpl 
 **    Revision 1.6  2004/11/09 14:33:06Z  KamleshU 
 **    Revision 1.5  2004/11/03 10:12:47Z  KamleshU 
 **    As the ProcessStateImpl has a ResourceLocator so the datatype constrcution should use it, so changes accordingly 
 **    Revision 1.4  2004/08/10 13:00:31Z  WojciechZ 
 **    better error message 
 **    Revision 1.3  2004/08/09 08:52:35Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.2  2004/07/26 10:34:15Z  KamleshU 
 **    Specific Implementation for validate method as validate and submit functionality has been separated in AbstractInvocable 
 **    Revision 1.1  2004/07/21 16:20:38Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  20-Jul-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.wfmc.x2002.xpdl10.ConditionDocument;
import org.wfmc.x2002.xpdl10.PackageDocument;
import org.wfmc.x2002.xpdl10.WorkflowProcessDocument;
import org.wfmc.x2002.xpdl10.ActivityDocument.Activity;
import org.wfmc.x2002.xpdl10.ApplicationDocument.Application;
import org.wfmc.x2002.xpdl10.DataFieldDocument.DataField;
import org.wfmc.x2002.xpdl10.ExtendedAttributeDocument.ExtendedAttribute;
import org.wfmc.x2002.xpdl10.ExtendedAttributesDocument.ExtendedAttributes;
import org.wfmc.x2002.xpdl10.FormalParameterDocument.FormalParameter;
import org.wfmc.x2002.xpdl10.FormalParametersDocument.FormalParameters;
import org.wfmc.x2002.xpdl10.TransitionDocument.Transition;
import org.wfmc.x2002.xpdl10.WorkflowProcessDocument.WorkflowProcess;

import com.tibco.inteng.Package;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.XpdlDataFactory;

/**
 * TODO Description of 'ProcessImpl' class
 * 
 * @author WojciechZ
 */
public class ProcessImpl extends AbstractInvocable implements
        com.tibco.inteng.Process {

    /** log4j */
    private static Logger log = Logger.getLogger(ProcessImpl.class);

    private final WorkflowProcess process;

    private Collection startActivities;

    private final Package xpdlPackage;

    private Map applicationsCache = new HashMap();

    private Map activitiesCache = new HashMap();

    /**
     * 
     * @param xpdlPackage
     * @param process
     */
    ProcessImpl(Package xpdlPackage,
            WorkflowProcessDocument.WorkflowProcess process) {
        this.xpdlPackage = (Package) xpdlPackage;
        this.process = process;

        if (process.isSetApplications()) {
            Application[] appArr = process.getApplications()
                    .getApplicationArray();
            for (int i = 0; i < appArr.length; i++) {
                applicationsCache.put(appArr[i].getId(),
                        new com.tibco.inteng.impl.Application(appArr[i],
                                xpdlPackage));
            }
        }
        if (process.isSetActivities()) {
            Activity[] actArr = process.getActivities().getActivityArray();
            for (int i = 0; i < actArr.length; i++) {
                activitiesCache.put(actArr[i].getId(),
                        new com.tibco.inteng.impl.Activity(actArr[i], this));
            }
        }
        if (process.isSetTransitions()) {
            Transition[] ta = process.getTransitions().getTransitionArray();
            com.tibco.inteng.impl.Activity act;
            for (int i = 0; i < ta.length; i++) {
                act = (com.tibco.inteng.impl.Activity) activitiesCache
                        .get(ta[i].getFrom());
                if (act == null) {
                    XpdlException e = new XpdlException(
                            "Invalid transition: not existing 'From' activity, Transition ID: "
                                    + ta[i].getId());
                    log.error(e.getMessage());
                    throw e;
                }
                if (ta[i].isSetCondition()
                        && ta[i].getCondition().isSetType()
                        && ta[i]
                                .getCondition()
                                .getType()
                                .equals(
                                        ConditionDocument.Condition.Type.DEFAULTEXCEPTION)) {
                    act.addExceptionTransition(ta[i]);
                } else {
                    act.addOutgoingTransition(ta[i]);
                    act = (com.tibco.inteng.impl.Activity) activitiesCache
                            .get(ta[i].getTo());
                    if (act == null) {
                        XpdlException e = new XpdlException(
                                "Invalid transition: not existing 'To' activity, Transition ID: "
                                        + ta[i].getId());
                        log.error(e.getMessage());
                        throw e;
                    }
                    act.addIncomingTransition(ta[i]);
                }
            }
        }
    }

    /**
     * @see com.tibco.inteng.impl.AbstractInvocable#getFormalParameters()
     */
    public List getFormalParameters() {
        List parameterList = getParameters();
        ArrayList toReturn = new ArrayList(parameterList.size());
        for (int i = 0; i < parameterList.size(); i++) {
            FormalParameter fp = (FormalParameter) parameterList.get(i);
            XpdlData field = XpdlDataFactory.getDataType(fp.getId(), fp
                    .getDataType(), this.getPackage(), false);
            toReturn.add(field);
        }
        return toReturn;
    }

    /**
     * 
     */
    protected List getParameters() {
        if (!process.isSetFormalParameters()) {
            return Collections.EMPTY_LIST;
        }
        FormalParameters formalParameters = process.getFormalParameters();
        FormalParameter[] formalParameterArray = formalParameters
                .getFormalParameterArray();
        int arrayLength = formalParameterArray.length;
        ArrayList toReturn = new ArrayList(arrayLength);
        for (int i = 0; i < arrayLength; i++) {
            toReturn.add(formalParameterArray[i]);
        }
        return toReturn;
    }

    /**
     * @see com.tibco.inteng.impl.AbstractInvocable#getExtendedAttributes()
     */
    public Map getExtendedAttributes() {
        if (!process.isSetExtendedAttributes()) {
            return Collections.EMPTY_MAP;
        }
        ExtendedAttributes extendedAttributes = process.getExtendedAttributes();
        ExtendedAttribute[] extendedAttributeArray = extendedAttributes
                .getExtendedAttributeArray();
        Map toReturn = new HashMap();
        for (int i = 0; i < extendedAttributeArray.length; i++) {
            String attribName = extendedAttributeArray[i].getName();
            toReturn.put(attribName,
                    new com.tibco.inteng.impl.ExtendedAttributeImpl(
                            extendedAttributeArray[i]));
        }
        return toReturn;
    }

    /**
     * @see com.tibco.inteng.impl.AbstractInvocable#validate(com.tibco.inteng.ProcessStateImpl,
     *      java.util.List)
     */
    public boolean validate(ProcessState state, List args) {
        return super.validate(state, args);
    }

    /**
     * @see com.tibco.inteng.impl.AbstractInvocable#checkActivity(org.wfmc.x2002.xpdl10.ActivityDocument.Activity)
     * @param activity
     */
    protected void checkActivity(Activity activity) {
        if (!activity.isSetImplementation()
                || !activity.getImplementation().isSetSubFlow()) {
            XpdlException e = new XpdlException(
                    "Invalid activity for SubFlow (Activity ID: "
                            + activity.getId() + ")");
            if (log.isDebugEnabled()) {
                log.debug(activity);
            }
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @see com.tibco.inteng.impl.AbstractInvocable#getId()
     */
    public String getId() {
        return process.getId();
    }

    /**
     * This method sets the value of XpdlData in the processState from the list
     * of XpdlData passed as formalParameters.
     * 
     * @param formalParameters,
     *            list of populated Xpdldata
     * @param processState
     */
    private void setProcessFormalParameters(List formalParameters,
            ProcessState processState) {

        for (Iterator iter = formalParameters.iterator(); iter.hasNext();) {
            XpdlData data = (XpdlData) iter.next();
            try {
                XpdlData xdata = (XpdlData) processState.getFields().get(
                        data.getName());
                xdata.setValue(data);
            } catch (XpdlDataFormatException e) {
                XpdlException e1 = new XpdlException(
                        "Invalid type when seting subflow formal parameter: "
                                + data.getName() + ", subflow ID: " + getId());
                log.error(e1.getMessage(), e);
                throw e1;
            }
        }
    }

    /**
     * @param processState
     * @return
     */
    List getFinishedProcessFPs(ProcessState processState) {
        if (!process.isSetFormalParameters()) {
            return Collections.EMPTY_LIST;
        }
        List result = new ArrayList();
        FormalParameters fp = process.getFormalParameters();
        FormalParameter[] fpa = fp.getFormalParameterArray();
        for (int i = 0; i < fpa.length; i++) {
            String fpId = fpa[i].getId();
            XpdlData field = (XpdlData) processState.getFields().get(fpId);
            result.add(XpdlDataFactory.copyOf(field, fpId, false));
        }
        return result;
    }

    /**
     * find & return list of start activities for process. (synchronized because
     * of caching result)
     * 
     * @return unmodifiable collection of starting activities
     */
    synchronized Collection getStartActivities() {

        if (startActivities == null) {
            Map startActivitiesMap = new HashMap();
            if (process.isSetActivities()) {
                Activity[] acts = process.getActivities().getActivityArray();
                for (int i = 0; i < acts.length; i++) {
                    startActivitiesMap.put(acts[i].getId(), acts[i]);
                }
            }
            Transition[] trans;
            if (process.isSetTransitions()) {
                trans = process.getTransitions().getTransitionArray();
            } else {
                trans = new Transition[0];
            }
            for (int i = 0; i < trans.length; i++) {
                startActivitiesMap.remove(trans[i].getTo());
            }
            if (startActivitiesMap.size() == 0) {
                XpdlException e = new XpdlException(
                        "Cannot find start state(s), there is no activity without incoming connections");
                log.error(e);
                throw e;
            }
            startActivities = Collections
                    .unmodifiableCollection(startActivitiesMap.values());
        }
        return startActivities;
    }

    /**
     * Create and init all Workflow Relevant Data for this process:
     * <li>create and init FormalParameters
     * <li>create and init DataFields
     * 
     * @param state -
     *            where to init
     */
    void initWRD(ProcessState state) {
        // initialize formal parameters
        if (process.isSetFormalParameters()) {
            FormalParameter[] params = process.getFormalParameters()
                    .getFormalParameterArray();
            for (int i = 0; i < params.length; i++) {
                XpdlData field = XpdlDataFactory.getDataType(params[i].getId(),
                        params[i].getDataType(), state, false);
                state.getFields().put(params[i].getId(), field);
            }
        }
        // initialize data fields
        Map stateFields = state.getFields();
        if (process.isSetDataFields()) {
            DataField[] dataFields = process.getDataFields()
                    .getDataFieldArray();
            for (int i = 0; i < dataFields.length; i++) {
                XpdlData field = XpdlDataFactory.getDataType(dataFields[i]
                        .getId(), dataFields[i].getDataType(), state, false);
                stateFields.put(dataFields[i].getId(), field);
                if (dataFields[i].isSetInitialValue()) {
                    try {
                        field.setValue(dataFields[i].getInitialValue());
                    } catch (XpdlDataFormatException e) {
                        log.error("Incorrect initialization value", e);
                        throw new XpdlException(
                                "Incorrect initialization value");
                    }
                }
            }
        }
    }

    /**
     * @return
     */
    public Package getPackage() {
        return xpdlPackage;
    }

    /**
     * @param id
     * @return
     */
    com.tibco.inteng.impl.Application getApplication(String id) {
        if (applicationsCache.containsKey(id)) {
            return (com.tibco.inteng.impl.Application) applicationsCache
                    .get(id);
        }
        return ((PackageImpl) xpdlPackage).getApplication(id);
    }

    /**
     * @param activityId
     * @return Activity
     */
    com.tibco.inteng.impl.Activity getActivity(String activityId) {
        return (com.tibco.inteng.impl.Activity) activitiesCache.get(activityId);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return xpdlPackage + ";process[" + getId() + "]";
    }

    public ProcessState newProcessState() {
        ProcessState state = ((PackageImpl) xpdlPackage)
                .newProcessState(getId());
        return state;
    }

    /**
     * @param fp,
     *            list of populated XpdlData which will be set to the
     * 
     * @param b
     * @return
     */
    public ProcessState newProcessState(List fp) {
        ProcessState state = newProcessState();
        setProcessFormalParameters(fp, state);
        return state;
    }

    /**
     * @return
     */
    public WorkflowProcess getProcessDef() {
        return process;
    }

    /**
     * Fix references (in activities) when all external packages, processes, and
     * applications are accesible
     */
    void fixReferences() {
        for (Iterator iter = activitiesCache.values().iterator(); iter
                .hasNext();) {
            com.tibco.inteng.impl.Activity act = (com.tibco.inteng.impl.Activity) iter
                    .next();
            act.fixReferences();
        }
    }

    /**
     * TODO implementation of this method
     * 
     * @param performerId
     * @param process
     * @return
     */
    Participant isPerformerPartOfParticipants(String performerId,
            ProcessImpl process) {
        log.debug("enter: isPerformerPartOfParticipants: processId "
                + process.getId());
        Participant toReturn = null;
        // checking whether the performer is part of the participant list of the
        // process
        if (process.getProcessDef().isSetParticipants()) {
            for (int i = 0; i < process.getProcessDef().getParticipants()
                    .getParticipantArray().length; i++) {
                String participantId = process.getProcessDef()
                        .getParticipants().getParticipantArray(i).getId();
                if (performerId.equals(participantId)) {
                    String participantName = process.getProcessDef()
                            .getParticipants().getParticipantArray(i).getName();
                    String participantType = process.getProcessDef()
                            .getParticipants().getParticipantArray(i)
                            .getParticipantType().getType().toString();
                    toReturn = new Participant(participantId, participantName,
                            participantType);
                    return toReturn;
                }
            }
        }
        // checking whether the performer is part of the participant list of the
        // package
        PackageDocument.Package pack = ((PackageImpl) xpdlPackage).pack;
        if (pack.isSetParticipants()) {
            for (int i = 0; i < pack.getParticipants().getParticipantArray().length; i++) {
                String participantId = pack.getParticipants()
                        .getParticipantArray(i).getId();
                if (performerId.equals(participantId)) {
                    String participantName = pack.getParticipants()
                            .getParticipantArray(i).getName();
                    String participantType = pack.getParticipants()
                            .getParticipantArray(i).getParticipantType()
                            .getType().toString();
                    toReturn = new Participant(participantId, participantName,
                            participantType);
                    return toReturn;
                }
            }
        }
        log.debug("exit: isPerformerPartOfParticipants:");
        return toReturn;
    }

    public boolean isManualActivity(String activityId) {
        boolean toReturn = false;
        com.tibco.inteng.impl.Activity activity = getActivity(activityId);
        if (activity != null) {
            toReturn = activity.isManual();
        }
        return toReturn;
    }

}