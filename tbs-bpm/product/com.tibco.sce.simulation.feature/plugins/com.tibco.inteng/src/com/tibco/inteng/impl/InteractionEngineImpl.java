package com.tibco.inteng.impl;

/* 
 ** 
 **  MODULE:             $RCSfile: InteractionImpl.java $ 
 **                      $Revision: 1.79 $ 
 **                      $Date: 2005/05/31 08:44:46Z $ 
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
 **    $Log: InteractionImpl.java $ 
 **    Revision 1.79  2005/05/31 08:44:46Z  tstephen 
 **    review comments and some tweaks to javadoc 
 **    Revision 1.78  2005/05/25 08:45:26Z  KamleshU 
 **    Code change to restore state when the subflow is there in other xpdls file. 
 **    Revision 1.77  2005/05/22 21:30:36Z  tstephen 
 **    fixed potential null pointer when have no external packages 
 **    Revision 1.76  2005/05/22 13:55:54Z  tstephen 
 **    changes related to refactoring of default bundle in inteng-web (and test for it)  
 **    Revision 1.74  2005/05/12 09:38:33Z  wzurek 
 **    defect #21724 
 **    Revision 1.73  2005/04/29 15:51:32Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.72  2005/04/13 15:18:17Z  KamleshU 
 **    Reverted back the change to make the tabs appear 
 **    Revision 1.71  2005/04/13 11:23:13Z  KamleshU 
 **    Changes made to read the process data values while restoring the state 
 **    Revision 1.70  2005/04/13 08:42:58Z  KamleshU 
 **    Changes in the way the Simple data is being set the value from the state xml 
 **    Revision 1.69  2005/04/12 16:10:24Z  KamleshU 
 **    Changed the error messages which are thrown back to the client 
 **    Revision 1.68  2005/04/12 14:40:04Z  KamleshU 
 **    Added change to invoke the Application class method to evaluate the script 
 **    Revision 1.67  2005/04/12 14:37:28Z  KamleshU 
 **    Made change to the way the values to the state variables are set when we restore the state 
 **    Revision 1.66  2005/04/04 09:44:59Z  KamleshU 
 **    Added a function to read the package level datafields and store that as process datafields 
 **    Revision 1.65  2005/03/18 13:54:11Z  tstephen 
 **    added logging and corrected some spellings 
 **    Revision 1.64  2005/02/04 17:27:40Z  KamleshU 
 **    Changed the paramater to the method which registers the objects on Context 
 **    Revision 1.63  2005/02/02 19:34:11Z  KamleshU 
 **    Implemented SecurityPlugIn 
 **    Revision 1.62  2005/01/28 12:54:05Z  KamleshU 
 **    Revision 1.61  2005/01/27 13:36:05Z  KamleshU 
 **    Revision 1.60  2005/01/26 16:31:40Z  KamleshU 
 **    Revision 1.58  2004/12/21 17:36:30Z  KamleshU 
 **    Made changes to move from BSF to Rhino Scripting 
 **    Revision 1.57  2004/11/11 18:56:50Z  KamleshU 
 **    To restore the ProcessStateImpl we do not need to pass the InputStream any more 
 **    Revision 1.56  2004/11/09 14:49:31Z  KamleshU 
 **    Changed the signature of methods to restore the InteractionImpl 
 **    Revision 1.55  2004/11/03 19:24:00Z  KamleshU 
 **    Revision 1.54  2004/11/03 10:19:08Z  KamleshU 
 **    Added code for restoring the Interactionstate from xml file 
 **    Revision 1.53  2004/08/24 12:48:17Z  TimSt 
 **    removed spelling mistake 'bussiness' - defect 21063 
 **    Revision 1.52  2004/08/13 14:06:58Z  WojciechZ 
 **    better messages, 
 **    references to types declared in external packages 
 **    Revision 1.50  2004/08/12 14:13:54Z  WojciechZ 
 **    propagate session data to subflows 
 **    Revision 1.49  2004/08/12 07:29:03Z  WojciechZ 
 **    Revision 1.48  2004/08/11 09:54:36Z  WojciechZ 
 **    better error messages 
 **    Revision 1.47  2004/08/09 12:28:48Z  WojciechZ 
 **    Revision 1.46  2004/08/09 08:52:33Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.45  2004/08/02 16:13:08Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.44  2004/07/27 16:13:52Z  WojciechZ 
 **    Revision 1.43  2004/07/26 12:42:24Z  WojciechZ 
 **    Changed signature of submit method (removed validation) 
 **    Revision 1.42  2004/07/26 10:31:48Z  KamleshU 
 **    As the parameters for submit method has been changed in AbstractInvocable, so corresponding changes done 
 **    Revision 1.41  2004/07/23 16:20:58Z  WojciechZ 
 **    fixes in subflows 
 **    Revision 1.40  2004/07/22 16:41:53Z  WojciechZ 
 **    fixes: starting subflow with one thread with autoactivities 
 **    Revision 1.39  2004/07/22 10:35:11Z  WojciechZ 
 **    better behaviour when number of threads changes between automatic applications 
 **    Revision 1.38  2004/07/22 09:20:55Z  WojciechZ 
 **    fix: process level ExtendedAttributes cache 
 **    Revision 1.37  2004/07/21 15:52:17Z  WojciechZ 
 **    new feature: SubFlows 
 **    Revision 1.36  2004/07/19 12:51:50Z  WojciechZ 
 **    remove dependency of InteractionImpl class to praticular WorkflowProcess in xpdl file. Now InteractionImpl class represent the whole xpdl package, and ProcessStateImpl carry name of it WorkflowProcess (first step to SubFlows) 
 **    Revision 1.35  2004/07/15 12:31:06Z  WojciechZ 
 **    fix: multhread interaction - fixed 'processThreadUnitlWait' method 
 **    Revision 1.34  2004/07/14 13:51:22Z  WojciechZ 
 **    added: helper method to load string from url 
 **    Revision 1.33  2004/07/14 10:43:15Z  WojciechZ 
 **    list of user threads shows only threads on manual applications 
 **    Revision 1.32  2004/07/08 16:14:07Z  WojciechZ 
 **    changes to comments, code cleaning 
 **    Revision 1.31  2004/07/02 13:45:49Z  WojciechZ 
 **    Revision 1.30  2004/07/02 11:03:18Z  WojciechZ 
 **    support for 'interaction state path' 
 **    Revision 1.29  2004/07/01 15:39:38Z  WojciechZ 
 **    tabbular interface, but still in progress 
 **    Revision 1.28  2004/06/28 14:13:52Z  WojciechZ 
 **    multiple interaction threads 
 **    Revision 1.27  2004/06/22 14:22:01Z  WojciechZ 
 **    some logging clean up 
 **    more carefull with result from script on condition 
 **    Revision 1.26  2004/06/17 08:42:49Z  WojciechZ 
 **    fix: can process interaction without transition 
 **    Revision 1.25  2004/06/16 08:02:43Z  TimSt 
 **    enabled config of URL to be a file 
 **    Revision 1.24  2004/06/15 13:56:53Z  WojciechZ 
 **    bettetr messages 
 **    Revision 1.23  2004/06/10 16:29:13Z  WojciechZ 
 **    better messages 
 **    Revision 1.22  2004/06/09 15:30:53Z  WojciechZ 
 **    When BussinessException.getMessager() returns null, it create message 'null' insted clearing it 
 **    Revision 1.21  2004/06/09 13:24:41Z  WojciechZ 
 **    handler for BusinessException 
 **    Revision 1.20  2004/06/07 16:25:00Z  WojciechZ 
 **    business exception from auto application handler 
 **    Revision 1.19  2004/06/07 10:00:44Z  WojciechZ 
 **    better error messages 
 **    Revision 1.18  2004/05/20 09:28:45Z  WojciechZ 
 **    support for changing default location prefix 
 **    decouple interface with xpdl-xbean 
 **    Revision 1.17  2004/05/14 11:14:56Z  WojciechZ 
 **    move services.xml to CMF-web project (to default location) 
 **    Revision 1.16  2004/05/11 14:30:57Z  WojciechZ 
 **    Revision 1.15  2004/05/10 16:24:51Z  WojciechZ 
 **    script application + test 
 **    Revision 1.14  2004/05/07 13:46:22Z  TimSt 
 **    undo port change (my fault)  
 **    Revision 1.13  2004/05/07 13:29:40Z  WojciechZ 
 **    move to from 80 to 8080  
 **    Revision 1.12  2004/05/06 07:45:08Z  WojciechZ 
 **    Initial value for process' data field 
 **    Revision 1.11  2004/05/05 08:51:32Z  WojciechZ 
 **    Access to extended attributes of process and package 
 **    common method to get URL from a string (for external references) 
 **    Revision 1.10  2004/04/29 14:15:53Z  WojciechZ 
 **    External references.  
 **    1. posibility to load external xpdl file (i.e. from CMF-web project) 
 **    2. external defined schema types (references to external XSD) 
 **    Revision 1.9  2004/04/16 08:53:33Z  WojciechZ 
 **    work up to 16/04/2004 
 **    Revision 1.8  2004/04/13 16:32:52Z  WojciechZ 
 **    work up to 13/04/2004 
 **    Revision 1.7  2004/04/08 16:02:15Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 **    Revision 1.6  2004/04/02 14:19:41Z  WojciechZ 
 **    work up to 02/04/2004 
 **    Revision 1.5  2004/04/01 11:00:33Z  WojciechZ 
 **    Work up to 01/04/2004 (working) 
 **    Revision 1.4  2004/03/30 16:46:29Z  WojciechZ 
 **    work up to 30/03/2004 
 **    Revision 1.3  2004/03/29 16:27:26Z  WojciechZ 
 **    work up to 29/03/2004 
 **    Revision 1.2  2004/03/29 08:48:54Z  WojciechZ 
 **    Work up to 29-03-2004 
 **    Revision 1.0  15-Mar-2004 WojciechZ 
 **    Initial revision 
 ** 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.wfmc.x2002.xpdl10.ConditionDocument.Condition;
import org.wfmc.x2002.xpdl10.ConditionDocument.Condition.Type;
import org.wfmc.x2002.xpdl10.ExtendedAttributeDocument.ExtendedAttribute;
import org.wfmc.x2002.xpdl10.ImplementationDocument.Implementation;
import org.wfmc.x2002.xpdl10.ToolDocument.Tool;
import org.wfmc.x2002.xpdl10.TransitionDocument.Transition;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.InteractionEngine;
import com.tibco.inteng.Invocable;
import com.tibco.inteng.Package;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.security.SecurityPlugIn;
import com.tibco.inteng.utils.IntEngUtils;
import com.tibco.inteng.utils.XmlUtils;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * Main entry point to the InteractionImpl Engine library.
 * 
 * @author WojciechZ
 * @author KamleshU
 */
public class InteractionEngineImpl implements InteractionEngine {
    /**
     * Logger for this class.
     */
    private static Logger log = Logger.getLogger(InteractionEngineImpl.class);

    private Map configPrameters = Collections.EMPTY_MAP;

    private InteractionEngineImpl() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.inteng.InteractionEngine#processInteraction(com.tibco.inteng
     * .ProcessState)
     */
    @Override
    public void executeProcess(ProcessState intState) {
        if (log.isInfoEnabled()) {
            log.info("enter: processInteraction: "
                    + intState.getProcess().getId());
        }
        ProcessStateImpl state = (ProcessStateImpl) intState;
        String crThName = state.getCurrentThreadFullName();
        if (log.isDebugEnabled()) {
            log.debug("Process Interaction, current thread: " + crThName);
        }

        List threadsNames = state.getStateThreadsName();
        for (Iterator iter = threadsNames.iterator(); iter.hasNext();) {
            String thName = (String) iter.next();
            ProcessThreadImpl th =
                    (ProcessThreadImpl) state.getThreadByName(thName);

            ProcessState subFlow = th.getSubFlowProcessState();
            if (subFlow != null) {
                executeProcess(subFlow);
                if (subFlow.isFinished()) {
                    ProcessImpl processImpl =
                            (ProcessImpl) subFlow.getProcess();
                    List fp = processImpl.getFinishedProcessFPs(subFlow);
                    processImpl.submit(th, fp);
                    th.setSubFlowProcessState(null);
                    th.setSubmitted(true);
                }
            }
            processThreadUntilWait(th);

            /*
             * After processing as far as we can, check to see whether the
             * active flow-threads have changed. If they have then reset the
             * loop and re-process all of the threads.
             */
            /**
             * Sid XPD-6414 Simulation Cases do not finish when a parallel
             * split/join is followed directly by a parallel split/join with
             * same number of activiites.
             * 
             * The following condition only use to check if the *number* of
             * active threads had change before and after a wait (aka parallel
             * join). Therefore if you had the same number of tasks either side
             * of a parallel join followed by a parallel split then this loop
             * would carry on processing the previous set of active threads and
             * some/all of those will already have completed and therefore their
             * threads for their thread name would be null and this would result
             * in NPE's in the code above.
             * 
             * Therefore check the actual content of the sets NOT just the size
             * of list.
             */
            List latestThreadNames = state.getStateThreadsName();

            if (threadsNames.size() != latestThreadNames.size()
                    || !threadsNames.containsAll(latestThreadNames)) {
                threadsNames = latestThreadNames;
                iter = threadsNames.iterator();

            }
        }

        if (!state.isFinished()) {
            List utn = state.getAllUserThreadsName();
            if (utn.isEmpty()) {
                ; // no user thread == the only one thread
                /*
                 * As the state has not finished, we should set current thread
                 * to the first one. Not sure how it will behave, need to check.
                 */
                List stateThreadsName = state.getStateThreadsName();
                if (stateThreadsName.size() > 0) {
                    String firstThread = (String) stateThreadsName.get(0);
                    state.switchToThread(firstThread);
                }
            } else {
                if (!utn.contains(crThName)
                        || !state.getThreadByName(crThName).isManual()) {
                    // switch to different thread
                    for (Iterator iter = utn.iterator(); iter.hasNext();) {
                        String name = (String) iter.next();
                        if (!state.getThreadByName(name).isWaiting()) {
                            state.switchToThread(name);
                            break;
                        }
                    }
                } else {
                    /*
                     * As the state has not finished, we should switch back to
                     * the thread which the user had specified as the current
                     * thread.
                     */
                    state.switchToThread(crThName);
                }
            }
            // Logic for deciding whether state should be stored or not. The
            // saving of the state
            // will be made from the client side.
            state.setStateToBeSaved(true);
            for (Iterator iter = state.getStateThreadsName().iterator(); iter
                    .hasNext();) {
                String thName = (String) iter.next();
                ProcessThread thread = state.getThreadByName(thName);
                /*
                 * If the thread is on a manual activity and not a subflow and
                 * and is not waiting for security.
                 */
                ProcessImpl processImpl =
                        (ProcessImpl) thread.getProcessState().getProcess();
                Activity activity =
                        processImpl.getActivity(thread.getCurrentActivityId());
                if (activity == null) {
                    continue;
                }
                /**
                 * activity.isManual() check added, as there could be threads
                 * having manual start mode but automatic finish mode, so should
                 * be given to client for processing.
                 * 
                 */
                if ((activity.isManualFinishActivity() || activity.isManual())
                        && !thread.isSecurityWait()
                        && !(thread.getSubFlowProcessState() != null && thread
                                .getSubFlowProcessState().isStateToBeSaved())) {
                    state.setStateToBeSaved(false);
                    break;
                }
            }
        }
        if (state.isStateToBeSaved()) {
            log.info("State should be saved");
        }
        log.info("exit: processInteraction");

    }

    /**
     * Check if thread can be processed to next activity. Thread can be
     * processed when it is not waiting for user action (subbmiting manual
     * application) or subflow
     * 
     * This method do not check if this thread should wait for another thread
     * 
     * Thread cannot be processed if one of following is true: <li>current
     * activity is manual and 'subbmited' flag in the thread is 'false' <li>
     * current activity is automatic, but implementation is a 'SubFlow' and
     * 'subbmited' flag in the thread is 'false'
     * 
     * @param thread
     *            - thread to process
     * @return
     */
    private boolean canThreadBeProcessed(ProcessThread thread) {
        log.info("enter: canProcessThread: " + thread.getName());
        if (thread == null) {
            RuntimeException e =
                    new IllegalArgumentException("Cannot process 'null' thread");
            log.error(e.getMessage(), e);
            throw e;
        }

        // get activity definition
        ProcessState processState = thread.getProcessState();
        ProcessImpl process = (ProcessImpl) processState.getProcess();
        String actId = thread.getCurrentActivityId();
        if (actId == null) {
            return false;
        }
        if (!canActivityBeExecuted(thread)) {
            ProcessThreadImpl threadImpl = (ProcessThreadImpl) thread;
            threadImpl.setWaiting(true);
            threadImpl.setSecurityWait(true);
            return false;
        }
        if (thread.isSecurityWait()) {
            return false;
        }
        Activity activity = process.getActivity(actId);
        boolean result;

        if (activity.isRoute()) {
            result = isRouteActivity(activity);
        } else if (activity.getActivity().isSetImplementation()) {
            result = canImplementationActivityBeExecuted(activity, thread);
        } else {
            // unsupported activity
            XpdlException e =
                    new XpdlException(
                            "Unsupported type of activity (Activity ID:'"
                                    + actId
                                    + "'), only Route Activities and Activities with "
                                    + "Implementation are supported.");
            log.error(e.getMessage(), e);
            throw e;
        }

        log.info("exit: canProcessThread: " + thread.getName() + "? " + result);
        return result;
    }

    /**
     * Process given thread to next activity.
     * 
     * 
     * 
     * @param thread
     * @return true, when thread has to be switched
     */
    private boolean processThread(ProcessThread thread) {
        log.info("enter: processThread");
        boolean result = false;

        if (log.isInfoEnabled()) {
            log.info("enter: processThread - " + thread.getName());
        }
        List validTrans = new LinkedList();

        ProcessStateImpl state = (ProcessStateImpl) thread.getProcessState();
        ProcessImpl process = (ProcessImpl) state.getProcess();
        String activityId = thread.getCurrentActivityId();
        if (activityId == null) {
            throw new IllegalStateException(
                    "Thread has already finished (current activity=null)");
        }
        Activity activity = process.getActivity(activityId);

        if (thread.isError(activityId)) {
            List et = activity.getExceptionTransitions();
            if (et.isEmpty()) {
                XpdlException e =
                        new XpdlException(
                                "In the event of Business Exception, No transition is defined "
                                        + "from activity with id: '"
                                        + activityId + "' to proceed, "
                                        + "(Exception message key: "
                                        + thread.getErrorMessageKey() + ")");
                log.error(e.getMessage(), e);
                throw e;
            }
            log.warn("follows DEFAULTEXCEPTION transition");
            result = followTransition(thread, (Transition) et.get(0));
            return result;
        }

        List trans = activity.getOutgoingTransitions();
        if (trans.isEmpty()) {
            // end of interaction!
            log.info("End of interaction, no valid transitions from '"
                    + activityId + "'");
            ((ProcessThreadImpl) thread).setCurrentActivity(null);
            ((ProcessThreadImpl) thread).setCurrentActivityId(null);
        } else {

            for (Iterator iter = trans.iterator(); iter.hasNext();) {
                Transition tra = (Transition) iter.next();
                if (isTransitionValid(tra, state)) {
                    validTrans.add(tra);
                }
            }
            if (validTrans.isEmpty()) {
                // no valid transitions - error
                XpdlException e =
                        new XpdlException(
                                "No valid transition defined from activity ID: "
                                        + activity.getId()
                                        + ". So, cannot proceed further");
                log.error(e.getMessage(), e);
                throw e;
            } else if (validTrans.size() == 1) {
                // the only one transition
                log.debug("one valid transition");
                Transition tran = (Transition) validTrans.get(0);
                result = followTransition(thread, tran);
            } else {
                // more valid transitions
                log.debug("multiple valid transitions: " + validTrans);
                if (activity.isSplitActivity()) {
                    // TODO TS consider renaming thread from startTHread1 to
                    // transition name here
                    followTransition(thread, (Transition) validTrans.get(0));
                    if (canThreadBeProcessed(thread)) {
                        processThreadUntilWait(thread);
                    }
                    // TODO: TS is this cause of lost subflow threads? unlikely
                    // but may look into
                    // Also why starting at 1? think this may be intentional to
                    // skip startThread1
                    // for (Iterator it = validTrans.iterator() ; it.hasNext()
                    // ;) {
                    for (int i = 1; i < validTrans.size(); i++) {
                        // Transition tran = (Transition) it.next();
                        Transition tran = (Transition) validTrans.get(i);
                        String targetActId = tran.getTo();

                        ProcessThreadImpl newThState =
                                (ProcessThreadImpl) state.createNewThread(tran
                                        .getId());
                        log.debug("+-------------------------------------------------");
                        log.debug("|");
                        log.debug("|");
                        log.info("| Starting thread: " + tran.getId());

                        renameThreadWithTransitionValues(newThState, tran);
                        log.debug("|");
                        log.debug("|");
                        log.debug("+-------------------------------------------------");

                        moveThreadToNextActivity(newThState, targetActId);

                        if (canThreadBeProcessed(newThState)) {
                            processThreadUntilWait(newThState);
                        }
                    }

                } else {
                    Transition best = chooseTransitionToFollow(validTrans);
                    result = followTransition(thread, best);
                }
            }
        }

        if (log.isInfoEnabled()) {
            log.info("exit: processThread - " + thread.getName());
        }
        return result;
    }

    /**
     * @param thread
     */
    private void processThreadUntilWait(ProcessThread thread) {
        log.debug("enter: processThreadUntilWait: thread:'" + thread.getName()
                + "' on " + thread.getProcessState().getProcess());
        while (canThreadBeProcessed(thread)) {
            String actId = thread.getCurrentActivityId();
            ProcessState state = thread.getProcessState();
            state.switchToThread(((ProcessThreadImpl) thread).getFullName());
            ProcessImpl processImpl = (ProcessImpl) state.getProcess();
            Activity act = processImpl.getActivity(actId);
            if (!isRouteActivity(act) && !thread.isSubmitted()) {
                executeApp(thread);
                if (canThreadBeProcessed(thread)) {
                    if (processThread(thread)) {
                        break;
                    }
                }
            } else if (processThread(thread)) {
                break;
            }
        }
        log.debug("exit: processThreadUntilWait");
    }

    /**
     * Choose one transition from set of valid transitions.<br>
     * Order: <li>transition without condition <li>transition with valid
     * condition <li>transition with 'otherwise' <li>other transitions <li>
     * transitions with type 'DEFAULTEXCEPTION' or 'EXCEPTION' are ignored
     * 
     * @param validTrans
     *            list of valid transitions
     * @return best transition
     */
    private Transition chooseTransitionToFollow(final List validTrans) {
        if (log.isInfoEnabled()) {
            log.info("entry: chooseTransition, choose from " + validTrans);
        }
        Transition best = null;
        // checking for any transition which has condition='Exception'
        for (Iterator iter = validTrans.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            if (tran.getCondition() == null) {
                continue;
            }
            Type.Enum tranEnum = tran.getCondition().getType();
            if (Type.EXCEPTION.equals(tranEnum)) {
                best = tran;
                break;
            }
        }
        if (best != null) {
            return best;
        }
        // checking for any transition which is uncontrolled
        for (Iterator iter = validTrans.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            if (tran.getCondition() == null) {
                best = tran;
                break;
            }
        }
        if (best != null) {
            return best;
        }
        // checking for any transition whose condition is valid
        for (Iterator iter = validTrans.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            if (tran.getCondition() == null) {
                continue;
            }
            Type.Enum tranEnum = tran.getCondition().getType();
            if (Type.CONDITION.equals(tranEnum)) {
                best = tran;
                break;
            }
        }
        if (best != null) {
            return best;
        }
        // checking for any transition whis is Otherwise
        for (Iterator iter = validTrans.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            if (tran.getCondition() == null) {
                continue;
            }
            Type.Enum tranEnum = tran.getCondition().getType();
            if (Type.OTHERWISE.equals(tranEnum)) {
                best = tran;
                break;
            }
        }
        if (best != null) {
            return best;
        }
        /*
         * These lines of code are trying to log the conditions which the engine
         * do not understand.
         */
        for (Iterator iter = validTrans.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            if (tran.getCondition() == null) {
                continue;
            }
            Type.Enum tranEnum = tran.getCondition().getType();
            if (Type.CONDITION.equals(tranEnum)) {
            } else if (Type.OTHERWISE.equals(tranEnum)) {
            } else if (Type.EXCEPTION.equals(tranEnum)) {
            } else if (Type.DEFAULTEXCEPTION.equals(tranEnum)) {
                log.warn(tran.getCondition().getType() + " is not supported");
            } else {
                log.warn(tran.getCondition().getType() + " is not supported");
            }
        }
        log.info("exit: chooseTransition");
        return best;
    }

    /**
     * Follow the transition to the next workflow state. helper.
     * 
     * @param thread
     *            - current state (will be modified)
     * @param trans
     *            - transition to follow
     * @return true, when thread had to be switched to other
     */
    private boolean followTransition(ProcessThread thread,
            final Transition trans) {
        if (log.isInfoEnabled()) {
            log.debug("+-------------------------------------------------");
            log.debug("|");
            log.debug("|");
            log.info("| FOLLOWING: thread: '" + thread.getName() + "': "
                    + trans.getFrom() + "-->" + trans.getTo());
        }
        ProcessImpl process =
                (ProcessImpl) thread.getProcessState().getProcess();
        boolean switched = false;
        String targetId = trans.getTo();
        Activity activity = process.getActivity(targetId);
        if (activity.isJoinActivity()) {
            // if a target activity is a join activity, we need to check if
            // we need to wait for other threads, and is so, we switch
            // current thread to one with is required to finish
            boolean shouldSwitchThread =
                    checkJoinConditions(thread.getName(),
                            thread.getProcessState(),
                            targetId);
            if (shouldSwitchThread) {
                ((ProcessThreadImpl) thread).setWaiting(true);
                switched = true;
            }
        } else {
            // follow to next activity
            moveThreadToNextActivity(thread, targetId);
        }
        renameThreadWithTransitionValues(thread, trans);
        log.debug("|");
        log.debug("|");
        log.debug("+-------------------------------------------------");
        return switched;
    }

    /**
     * @param thread
     * @param trans
     */
    private void renameThreadWithTransitionValues(ProcessThread thread,
            final Transition trans) {
        if (trans.isSetExtendedAttributes()) {
            ExtendedAttribute[] exts =
                    trans.getExtendedAttributes().getExtendedAttributeArray();
            for (int i = 0; i < exts.length; i++) {
                if ("setThreadName".equals(exts[i].getName())) {
                    ProcessState state = thread.getProcessState();
                    state.renameThread(thread.getName(), exts[i].getValue());
                    if (log.isInfoEnabled()) {
                        log.debug("| Change thread name to: '"
                                + thread.getName() + "'");
                    }
                } else if ("interactionPath".equals(exts[i].getName())) {
                    String cmd = exts[i].getValue();
                    if (log.isInfoEnabled()) {
                        log.debug("| Interaction Path command: '" + cmd + "'");
                    }
                } else if ("threadPath".equals(exts[i].getName())) {
                    String cmd = exts[i].getValue();
                    if (log.isInfoEnabled()) {
                        log.debug("| Interaction Path command: '" + cmd + "'");
                    }
                }
            }
        }
    }

    /**
     * check if thread has to wait for any other thread before go to given
     * activity with Join condition. If it has to wait, method returns 'false',
     * If all other threads are waiting, it withdraw those threads and follow
     * current thread to this ativity
     * 
     * @param threadName
     *            - current thread
     * @param state
     *            - interaction state
     * @param targetId
     *            - activity where we want go
     * @return false, if thread has to wait for different thread to go to this
     *         activity (thread became waiting thread)
     */
    private boolean checkJoinConditions(String threadName, ProcessState state,
            String targetId) {

        ProcessImpl process = (ProcessImpl) state.getProcess();
        Activity activity = process.getActivity(targetId);

        if (targetId == null) {
            return false;
        }
        boolean result = true;
        int has = 0;
        int required = 0;
        List threadsWaiting = new ArrayList();

        List incom = activity.getIncomingTransitions();
        for (Iterator iter = incom.iterator(); iter.hasNext();) {
            Transition tra = (Transition) iter.next();
            required++;
            if (isTransitionValid(tra, state)) {
                String srcActId = tra.getFrom();
                ProcessStateImpl stateImpl = (ProcessStateImpl) state;
                ProcessThread thrd =
                        stateImpl.getWaitingThreadOnActivity(srcActId);
                if (thrd != null) {
                    Activity srcAct = process.getActivity(srcActId);
                    if (thrd.isSubmitted() || isAutoFinishManual(srcAct)) {
                        has++;
                        threadsWaiting.add(thrd);
                    }
                }
            }
        }

        if (required == has) {
            ProcessThreadImpl masterThread = null;
            result = true;
            for (Iterator iter = threadsWaiting.iterator(); iter.hasNext();) {
                ProcessThreadImpl th = (ProcessThreadImpl) iter.next();
                if (!th.getName().equals(threadName)) {
                    ((ProcessStateImpl) state).withdrawThread(th.getName());
                } else {
                    masterThread = th;
                }
            }
            moveThreadToNextActivity(masterThread, targetId);
            result = false;
        }
        return result;
    }

    /**
     * Move thread to given activity, set all helper flags on thread
     * 
     * @param thread
     * @param targetId
     */
    private void moveThreadToNextActivity(ProcessThread thread, String targetId) {
        log.info("enter: moveThread, " + thread.getName() + " to " + targetId);
        ProcessImpl process =
                (ProcessImpl) thread.getProcessState().getProcess();
        Activity activity = process.getActivity(targetId);
        ProcessThreadImpl threadImpl = (ProcessThreadImpl) thread;
        threadImpl.setCurrentActivity(activity);
        threadImpl.setCurrentActivityId(targetId);
        threadImpl.setSubmitted(false);
        /*
         * boolean manual = activity.isManual(); if (!manual) { manual =
         * isActivityManual(activity, threadImpl); }
         */
        boolean manual = isActivityManual(activity, threadImpl);
        threadImpl.setManual(manual);
        /*
         * boolean autoFinishActivity = activity.isAutoFinishActivity();
         * if(autoFinishActivity){ autoFinishActivity =
         * isAutoFinishManual(activity); }
         */
        boolean autoFinishActivity = isAutoFinishManual(activity);
        threadImpl.setWaiting(autoFinishActivity);
        log.info("exit: moveThread");
    }

    /**
     * Check if contition on transition is valid.
     * 
     * @param transition
     *            - transition to check
     * @param state
     *            - interaction state
     * @return true if transition is valid @ throws XpdlException when condition
     *         is invalid
     */
    public boolean isTransitionValid(final Transition transition,
            final ProcessState state) {
        log.info("enter: isTransitionValid, trans: " + transition);
        boolean result;
        Condition cond = transition.getCondition();
        if (cond == null || cond.getType() == null) {
            // condition without condition
            result = true;
        } else if (cond.getType().equals(Condition.Type.OTHERWISE)) {
            // otherwise
            result = true;
        } else if (cond.getType().equals(Condition.Type.CONDITION)
                || cond.getType().equals(Condition.Type.EXCEPTION)) {
            String script;
            // condition should be in Xpresion element, but JaWE remove it from
            // XPDL
            if (cond.sizeOfXpressionArray() > 0) {
                script =
                        XmlUtils.getInnerText(cond.getXpressionArray(0)
                                .xmlText());
            } else {
                log.debug("condition without xpresion element");
                script = XmlUtils.getInnerText(cond.xmlText());
            }
            script = XmlUtils.decodeXmlText(script);
            script = XmlUtils.stripNonAscText(script);

            result = Application.scriptValidation(script, state, null);
        } else {
            result = false;
        }
        log.info("exit: isTransitionValid, returning" + result);
        return result;
    }

    void executeApp(ProcessThread thread) {
        executeApp(thread.getCurrentActivityId(), thread);
    }

    private void executeApp(String activityId, ProcessThread intThread) {
        log.info("enter: executeApp, activity: " + activityId);
        ProcessThreadImpl thread = (ProcessThreadImpl) intThread;
        ProcessState state = thread.getProcessState();
        ProcessImpl process = (ProcessImpl) state.getProcess();
        Activity activity = process.getActivity(activityId);
        Package xpdlPackage = process.getPackage();

        /*
         * This has been commented out on if (state.getXpdlPackage() != this) {
         * throw new XpdlException("Invalid use of XPDL Packages' references");
         * }
         */

        if (activity.isManual()) {
            IllegalStateException e =
                    new IllegalStateException(
                            "Application cannot be executed within the engine from a manual activity (Activity: '"
                                    + activityId + "')");
            log.error(e.getMessage(), e);
            throw e;
        }
        if (!activity.isRoute()) {
            Invocable impl = activity.getImplementation();

            if (impl instanceof com.tibco.inteng.Process) {
                ProcessImpl xpdlProcess = (ProcessImpl) impl;
                List fp = xpdlProcess.getPopulatedFormalParameters(thread);
                ProcessState subFlow = xpdlProcess.newProcessState(fp);
                executeProcess(subFlow);
                subFlow.setSessionData(thread.getProcessState()
                        .getSessionData());
                executeProcess(subFlow);
                /*
                 * Added this if statement for the scenario where in the subflow
                 * does not have manual applications but only have automatic
                 * applications, so it might have finished by now.- Kamlesh
                 * 09/11/2004.
                 */
                if (subFlow.isFinished()) {
                    ProcessImpl xpdlProc = (ProcessImpl) subFlow.getProcess();
                    List fp1 = xpdlProc.getFinishedProcessFPs(subFlow);
                    xpdlProcess.submit(thread, fp1);
                    thread.setSubFlowProcessState(null);
                    thread.setSubmitted(true);
                } else {
                    thread.setSubFlowProcessState(subFlow);
                    /*
                     * addded by Kamlesh on 05/04/2005, if the sub flow does not
                     * finish but the activity is autofinish, the we should set
                     * the thread to be submitted
                     */
                    /*
                     * This change is commented as this stops tabs to work as
                     * the subflow gets submitted if
                     * (activity.isAutoFinishActivity()) {
                     * thread.setSubFlowKey(null); thread.setSubbited(true); }
                     */
                }
            } else if (impl instanceof Application) {
                Application app = (Application) impl;
                /*
                 * XpdlAutomaticApplication autoApp = ((ProcessStateImpl)state)
                 * .getAutomaticApplication(app); if (autoApp == null) { autoApp
                 * =
                 * xpdlPackage.getInteractionRepository().getAutomaticApplication
                 * (app); }
                 */
                List fp = app.getPopulatedFormalParameters(thread);
                AutomaticApplication autoApp =
                        xpdlPackage.getInteractionRepository()
                                .getAutomaticApplication(app);
                if (log.isDebugEnabled()) {
                    log.debug("Automatic app '" + app.getId()
                            + "' with params: " + fp);
                }

                try {
                    int i = 0;
                    XpdlData[] argsArray = new XpdlData[fp.size()];
                    for (Iterator iter = fp.iterator(); iter.hasNext();) {
                        XpdlData d = (XpdlData) iter.next();
                        argsArray[i++] = d;
                    }
                    autoApp.invoke(app.getId(),
                            getExtAttribs(app.getExtendedAttributes()),
                            argsArray);

                    // invocation succedeed - clear error message
                    thread.setErrorMessageKey(null);

                } catch (XpdlBusinessException e) {
                    log.warn("Business Exception occurred in Application "
                            + app.getId() + " : " + e.getMessage());
                    String msg;
                    if (e.getMessage() == null) {
                        msg = "null";
                    } else {
                        msg = e.getMessage();
                    }
                    ProcessThreadImpl threadStateImpl =
                            (ProcessThreadImpl) state.getCurrentThread();
                    threadStateImpl.setErrorMessageKey(msg);
                }
                app.submit(thread, fp);
            }
        }
        log.info("exit: executeApp, activity: " + activityId);
    }

    /**
     * @param exts
     * @return
     */
    private com.tibco.inteng.ExtendedAttribute[] getExtAttribs(
            Map extendedAttributes) {
        com.tibco.inteng.ExtendedAttribute[] result;
        if (extendedAttributes != null) {
            result =
                    new com.tibco.inteng.ExtendedAttribute[extendedAttributes
                            .size()];
            int index = 0;
            for (Iterator iter = extendedAttributes.values().iterator(); iter
                    .hasNext();) {
                com.tibco.inteng.ExtendedAttribute element =
                        (com.tibco.inteng.ExtendedAttribute) iter.next();
                result[index++] = element;
            }
        } else {
            result = new com.tibco.inteng.ExtendedAttribute[0];
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.InteractionEngine#getCurrentTool(com.tibco.inteng.
     * ProcessState)
     */
    @Override
    public Invocable getCurrentApplication(ProcessState state) {
        ProcessThread currentThread = state.getCurrentThread();
        ProcessImpl process =
                (ProcessImpl) currentThread.getProcessState().getProcess();
        Activity activity =
                process.getActivity(currentThread.getCurrentActivityId());
        Invocable impl = activity.getImplementation();
        if (impl instanceof ProcessImpl) {
            XpdlException e =
                    new XpdlException("Activity: " + activity
                            + " calls a subflow, not a tool!");
            log.error(e.getMessage(), e);
            Object value =
                    configPrameters.get(IntEngConsts.SUB_PROCESS_AS_MANUAL);
            boolean b = IntEngUtils.returnBooleanValue(value);
            if (b) {
                return null;
            } else {
                throw e;
            }
        }
        return impl;
    }

    /**
     * This method decides whether the activity can be executed or not
     * 
     * @param thread
     * @return
     */
    private boolean canActivityBeExecuted(ProcessThread thread) {
        log.debug("enter: canActivityBeExecuted: thread:'" + thread.getName()
                + "' on " + thread.getProcessState().getProcess()
                + " current activity Id is " + thread.getCurrentActivityId());
        ProcessState state = thread.getProcessState();
        ProcessImpl process = (ProcessImpl) state.getProcess();
        String currentActivityId = thread.getCurrentActivityId();
        Activity currentActivity = process.getActivity(currentActivityId);
        // if the activity has a performer specified
        if (currentActivity.getActivity().isSetPerformer()) {
            String performerId = currentActivity.getActivity().getPerformer();
            // checking whether the performer is part of the participant
            Participant participant =
                    process.isPerformerPartOfParticipants(performerId, process);
            if (participant != null) {
                // getting the securtiy plugin from InteractionImpl Repository
                SecurityPlugIn plugIn =
                        process.getPackage().getInteractionRepository()
                                .getSecurityPlugIn();
                if (plugIn != null) {
                    if (plugIn.isUserAllowed(participant.participantId,
                            participant.participantName,
                            participant.participantType)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    XpdlException e =
                            new XpdlException(
                                    "No Security PlugIn installed to authenticate the role of the current user");
                    log.error(e);
                    throw e;
                }
            } else {
                XpdlException e =
                        new XpdlException(
                                "Performer "
                                        + performerId
                                        + " for activityId= "
                                        + currentActivityId
                                        + " is not part of the participants specified for processId= "
                                        + process.getId() + " and package");
                log.error(e);
                throw e;
            }
        }
        log.debug("exit: canActivityBeExecuted: activity can be executed, returning true");
        return true;
    }

    public static InteractionEngine getInstance() {
        InteractionEngine engine = new InteractionEngineImpl();
        return engine;
    }

    @Override
    public void setConfigParameters(Map configPrameters) {
        if (configPrameters == null) {
            configPrameters = Collections.EMPTY_MAP;
        }
        this.configPrameters = configPrameters;
    }

    @Override
    public Map getConfigParameters() {
        return configPrameters;
    }

    private boolean isRouteActivity(Activity activity) {
        boolean toReturn = false;
        Object value = configPrameters.get(IntEngConsts.ROUTE_AS_MANUAL);
        if (value != null) {
            toReturn = getBooleanValue(value);
        } else {
            toReturn = activity.isRoute();
        }
        return toReturn;
    }

    private boolean canSubFlowBeProcessed(ProcessThread thread) {
        boolean toReturn = false;
        if (thread.getSubFlowProcessState() == null) {
            log.debug("canProcessThread: (subFlow==null) == true");
            toReturn = true;
        } else {
            // can process if subflow is submitted
            toReturn = thread.isSubmitted();
            log.debug("canProcessThread: (subFlow!=null) == " + toReturn);
        }
        return toReturn;
    }

    /**
     * This method will let the engine know whether the activity can be executed
     * 
     * @param activity
     * @param thread
     * @return
     */
    private boolean canImplementationActivityBeExecuted(Activity activity,
            ProcessThread thread) {
        boolean toReturn = false;
        Implementation implementation =
                activity.getActivity().getImplementation();
        Object value = null;
        boolean isSetNo = implementation.isSetNo();
        if (isSetNo) {
            value = configPrameters.get(IntEngConsts.NOOP_IMPL_AS_MANUAL);
            if (value != null) {
                toReturn = getBooleanValue(value);
            } else {
                toReturn = activity.isAutomatic();
            }
        }
        boolean isSubFlow = implementation.isSetSubFlow();
        if (isSubFlow) {
            value = configPrameters.get(IntEngConsts.SUB_PROCESS_AS_MANUAL);
            toReturn = IntEngUtils.returnBooleanValue(value);
            if (toReturn) {
                toReturn = thread.isSubmitted();
            } else {
                toReturn = canSubFlowBeProcessed(thread);
            }
        }
        Tool[] toolArray = implementation.getToolArray();
        boolean isTool = toolArray.length > 0;
        if (isTool) {
            boolean autoActivity = activity.isAutomatic();
            if (autoActivity) {
                value = configPrameters.get(IntEngConsts.AUTO_ACT_AS_MANUAL);
                toReturn = IntEngUtils.returnBooleanValue(value);
                if (toReturn) {
                    toReturn = thread.isSubmitted();
                } else {
                    toReturn = activity.isAutomatic();
                }
            } else {
                // manual activity,which in all cases will depend on thread.
                toReturn = thread.isSubmitted();
            }
        }
        return toReturn;
    }

    private boolean getBooleanValue(Object value) {
        boolean toReturn = false;
        boolean manAct = IntEngUtils.returnBooleanValue(value);
        if (manAct) {
            /**
             * As the activity needs to be treated as manual so we should return
             * false
             */
            toReturn = false;
        } else {
            toReturn = true;
        }
        return toReturn;
    }

    private boolean isActivityManual(Activity activity, ProcessThread thread) {
        boolean toReturn = false;
        Object value = null;
        if (activity.isRoute()) {
            value = configPrameters.get(IntEngConsts.ROUTE_AS_MANUAL);
            toReturn = IntEngUtils.returnBooleanValue(value);
            if (toReturn) {
                return true;
            } else {
                return false;
            }
        }
        Implementation implementation =
                activity.getActivity().getImplementation();
        boolean isSetNo = implementation.isSetNo();
        if (isSetNo) {
            value = configPrameters.get(IntEngConsts.NOOP_IMPL_AS_MANUAL);
            toReturn = IntEngUtils.returnBooleanValue(value);
            if (toReturn) {
                return true;
            } else {
                toReturn = activity.isAutomatic();
                return toReturn;
            }
        }
        boolean isSubFlow = implementation.isSetSubFlow();
        if (isSubFlow) {
            value = configPrameters.get(IntEngConsts.SUB_PROCESS_AS_MANUAL);
            toReturn = IntEngUtils.returnBooleanValue(value);
            if (toReturn) {
                return true;
            } else {
                toReturn = canSubFlowBeProcessed(thread);
                return toReturn;
            }
        }
        Tool[] toolArray = implementation.getToolArray();
        boolean isTool = toolArray.length > 0;
        if (isTool) {
            boolean autoActivity = activity.isAutomatic();
            if (autoActivity) {
                value = configPrameters.get(IntEngConsts.AUTO_ACT_AS_MANUAL);
                toReturn = IntEngUtils.returnBooleanValue(value);
                if (toReturn) {
                    toReturn = true;
                } else {
                    toReturn = activity.isAutomatic();
                    return toReturn;
                }
            } else {
                // manual activity
                toReturn = true;
                return toReturn;
            }
        }
        return toReturn;
    }

    private boolean isAutoFinishManual(Activity activity) {
        boolean toReturn = false;
        Object value = null;
        if (activity.isRoute()) {
            value = configPrameters.get(IntEngConsts.ROUTE_AS_MANUAL);
            toReturn = IntEngUtils.returnBooleanValue(value);
            if (toReturn) {
                return false;
            } else {
                return true;
            }
        }
        Implementation implementation =
                activity.getActivity().getImplementation();
        boolean isSetNo = implementation.isSetNo();
        if (isSetNo) {
            value = configPrameters.get(IntEngConsts.NOOP_IMPL_AS_MANUAL);
            toReturn = IntEngUtils.returnBooleanValue(value);
            if (toReturn) {
                return false;
            } else {
                toReturn = activity.isAutoFinishActivity();
                return toReturn;
            }
        }
        boolean isSubFlow = implementation.isSetSubFlow();
        if (isSubFlow) {
            value = configPrameters.get(IntEngConsts.SUB_PROCESS_AS_MANUAL);
            toReturn = IntEngUtils.returnBooleanValue(value);
            if (toReturn) {
                return false;
            } else {
                toReturn = activity.isAutoFinishActivity();
                return toReturn;
            }
        }
        Tool[] toolArray = implementation.getToolArray();
        boolean isTool = toolArray.length > 0;
        if (isTool) {
            boolean autoActivity = activity.isAutomatic();
            if (autoActivity) {
                value = configPrameters.get(IntEngConsts.AUTO_ACT_AS_MANUAL);
                toReturn = IntEngUtils.returnBooleanValue(value);
                if (toReturn) {
                    toReturn = false;
                } else {
                    toReturn = activity.isAutoFinishActivity();
                    return toReturn;
                }
            } else {
                // manual activity
                toReturn = false;
                return toReturn;
            }
        }
        return toReturn;
    }

}
