/* 
 ** 
 **  MODULE:             $RCSfile: Activity.java $ 
 **                      $Revision: 1.4 $ 
 **                      $Date: 2005/02/17 10:09:48Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2003 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: Activity.java $ 
 **    Revision 1.4  2005/02/17 10:09:48Z  KamleshU 
 **    Made changes to ensure that if the activity does not have start and end mode set then they are considered to be automatic activities 
 **    Revision 1.3  2005/01/28 12:54:14Z  KamleshU 
 **    Revision 1.2  2004/08/10 15:15:47Z  WojciechZ 
 **    added 'toString()' 
 **    Revision 1.1  2004/08/09 09:02:33Z  WojciechZ 
 **    Initial revision 
 */
package com.tibco.inteng.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.wfmc.x2002.xpdl10.ActivityDocument;
import org.wfmc.x2002.xpdl10.ImplementationDocument.Implementation;
import org.wfmc.x2002.xpdl10.JoinDocument.Join;
import org.wfmc.x2002.xpdl10.SplitDocument.Split;
import org.wfmc.x2002.xpdl10.SubFlowDocument.SubFlow;
import org.wfmc.x2002.xpdl10.ToolDocument.Tool;
import org.wfmc.x2002.xpdl10.TransitionRestrictionDocument.TransitionRestriction;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.InteractionRepository;
import com.tibco.inteng.Invocable;
import com.tibco.inteng.Package;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.utils.IntEngUtils;

/**
 * Wrapper around activity XMLBean to avoid exposing XMLBean interface directly.
 * 
 * @author WojciechZ
 */
public class Activity {

    private static Logger log = Logger.getLogger(Activity.class);

    private List incomingTransitions = new ArrayList();

    private List outgoingTransitions = new ArrayList();

    private List exceptionTransitions = new ArrayList();

    private final ActivityDocument.Activity activity;

    private final com.tibco.inteng.Process process;

    private Invocable implementation;

    private boolean route;

    private String id;

    private String[] actualParams;

    private Invocable noOpImpl;

    /**
     * Create activity cache. Creation of Activity should be done in two stages
     * first, create all entities in Intraction Repository, then call
     * <code>fixReferences()</code> to set proper references
     * 
     * @see #fixReferences()
     * @param activity
     * @param process
     */
    public Activity(ActivityDocument.Activity activity, ProcessImpl process) {
        this.activity = activity;
        this.process = process;
        route = activity.isSetRoute();
        id = activity.getId();

    }

    void addIncomingTransition(Object trans) {
        incomingTransitions.add(trans);
    }

    void addOutgoingTransition(Object trans) {
        outgoingTransitions.add(trans);
    }

    void addExceptionTransition(Object trans) {
        exceptionTransitions.add(trans);
    }

    /**
     * @return Returns the incomingTransitions.
     */
    public List getIncomingTransitions() {
        return incomingTransitions;
    }

    /**
     * @return Returns the outgoingTransitions.
     */
    public List getOutgoingTransitions() {
        return outgoingTransitions;
    }

    /**
     * @return Returns the exceptionTransitions.
     */
    public List getExceptionTransitions() {
        return exceptionTransitions;
    }

    /**
     * @return Returns the activity.
     */
    ActivityDocument.Activity getActivity() {
        return activity;
    }

    /**
     * Return Implementation for this activity
     * 
     * @return XpdlInteraface object, or null if there is no implementation
     */
    public Invocable getImplementation() {
        if (isRoute()) {
            return null;
        }
        return implementation;
    }

    /**
     * @return true, when this activity is route activity
     */
    public boolean isRoute() {
        return route;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return
     */
    public boolean isSplitActivity() {
        log.debug("enter: isSplitActivity");
        boolean result = false;
        if (activity.isSetTransitionRestrictions()) {
            TransitionRestriction[] tr = activity.getTransitionRestrictions()
                    .getTransitionRestrictionArray();
            for (int i = 0; i < tr.length; i++) {
                if (tr[i].isSetSplit()
                        && Split.Type.AND.equals(tr[i].getSplit().getType())) {
                    result = true;
                    break;
                }
            }

        }
        log.debug("exit: isSplitActivity - " + result);
        return result;
    }

    /**
     * check is activity is a join activity
     * 
     * Activity is a join activity when it has at least one 'Join' transition
     * restriction with type 'AND'
     * 
     * @return true, if activity is a join activity
     */
    public boolean isJoinActivity() {
        boolean result = false;
        if (activity.isSetTransitionRestrictions()) {
            TransitionRestriction[] tr = activity.getTransitionRestrictions()
                    .getTransitionRestrictionArray();
            for (int i = 0; i < tr.length; i++) {
                if (tr[i].isSetJoin()
                        && Join.Type.AND.equals(tr[i].getJoin().getType())) {
                    result = true;
                }
            }

        }
        return result;
    }

    /**
     * @return
     */
    public boolean isAutoFinishActivity() {
        if (!activity.isSetFinishMode()) {
            return true;
        }
        return activity.getFinishMode().isSetAutomatic();
    }

    /**
     * @return
     */
    public boolean isManualFinishActivity() {
        if (!activity.isSetFinishMode()) {
            return false;
        }
        return activity.getFinishMode().isSetManual();
    }

    /**
     * @return
     */
    public boolean isManual() {
        if (!activity.isSetStartMode()) {
            return false;
        }
        return activity.getStartMode().isSetManual();

    }

    /**
     * @return
     */
    public boolean isAutomatic() {
        if (!activity.isSetStartMode()) {
            return true;
        }
        return activity.getStartMode().isSetAutomatic();

    }

    /**
     * @return
     */
    public String[] getActualParameters() {
        return actualParams;
    }

    /**
     * Fix references to applicaions and subflows.
     * 
     * Because of two-stage creation process of Activity, this method has to be
     * called when all external packages, processes and applciations are
     * accessible and before use anu of other methods
     */
    void fixReferences() {
        if (!isRoute()) {
            if (activity.isSetImplementation()) {
                Package xpdlPackage = process.getPackage();
                Implementation impl = activity.getImplementation();
                if (impl.isSetSubFlow()) {
                    SubFlow subFlow = impl.getSubFlow();
                    String subFlowId = subFlow.getId();
                    if (subFlow.isSetActualParameters()) {
                        actualParams = subFlow.getActualParameters()
                                .getActualParameterArray();
                    } else {
                        actualParams = new String[0];
                    }
                    implementation = xpdlPackage.getProcess(subFlowId);
                    if (implementation == null) {
                        if (ignoreSubFlowValidation()) {
                            return;
                        }
                        XpdlException e = new XpdlException("Unknown subflow '"
                                + subFlowId + "' on " + this);
                        log.error(e.getMessage(), e);
                        throw e;
                    }
                } else if (impl.sizeOfToolArray() == 1) {
                    Tool tool = impl.getToolArray(0);
                    if (tool.isSetActualParameters()) {
                        actualParams = tool.getActualParameters()
                                .getActualParameterArray();
                    } else {
                        actualParams = new String[0];
                    }
                    implementation = ((ProcessImpl) process)
                            .getApplication(tool.getId());
                    if (implementation == null) {
                        if (ignoreApplicationValidation()) {
                            return;
                        }
                        XpdlException e = new XpdlException(
                                "Unknown application (tool) '" + tool.getId()
                                        + "' on " + this);
                        log.error(e.getMessage(), e);
                        throw e;
                    }
                } else if (impl.isSetNo()) {
                    implementation = getNoOpImplementation();
                } else {
                    XpdlException e = new XpdlException(
                            "Invalid implementation entity in activity ID: '"
                                    + getId() + "'");
                    log.error(e.getMessage());
                    throw e;
                }
            } else {
                XpdlException e = new XpdlException(
                        "No implementation on activity ID:'" + getId() + "'");
                log.error(e.getMessage());
                throw e;
            }
        }
    }

    private Invocable getNoOpImplementation() {
        if (noOpImpl == null) {
            noOpImpl = new NoOpApplication();
        }
        return noOpImpl;
    }

    /**
     * @return Performer id required for this activity.
     */
    public String getPerformer() {
        return activity.getPerformer();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return process + ";activity[" + getId() + "]";
    }

    private InteractionRepository getInteractionRepository() {
        return process.getPackage().getInteractionRepository();
    }

    private boolean ignoreApplicationValidation() {
        boolean toReturn = false;
        Map configParameters = getInteractionRepository().getConfigParameters();
        Object value = configParameters.get(IntEngConsts.IGNORE_APP_REFERENCE);
        if (value != null) {
            toReturn = IntEngUtils.returnBooleanValue(value);
        }
        return toReturn;
    }

    private boolean ignoreSubFlowValidation() {
        boolean toReturn = false;
        Map configParameters = getInteractionRepository().getConfigParameters();
        Object value = configParameters
                .get(IntEngConsts.IGNORE_SUBFLOW_REFERENCE);
        if (value != null) {
            toReturn = IntEngUtils.returnBooleanValue(value);
        }
        return toReturn;
    }
}