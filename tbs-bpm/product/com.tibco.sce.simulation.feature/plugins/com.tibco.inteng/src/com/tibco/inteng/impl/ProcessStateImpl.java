package com.tibco.inteng.impl;
/* 
 ** 
 **  MODULE:             $RCSfile: ProcessStateImpl.java $ 
 **                      $Revision: 1.30 $ 
 **                      $Date: 2005/05/03 05:47:19Z $ 
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
 **    $Log: ProcessStateImpl.java $ 
 **    Revision 1.30  2005/05/03 05:47:19Z  tstephen 
 **    corrected spelling in msg and made process id available 
 **    Revision 1.29  2005/03/21 09:09:09Z  tstephen 
 **    added getUserThreadsMap and commented on the likely future deprecation of getUserThreads and getUserThreadsNames 
 **    Revision 1.28  2005/03/18 13:54:23Z  tstephen 
 **    added logging and corrected some spellings  
 **    Revision 1.27  2005/02/02 19:34:44Z  KamleshU 
 **    Implemented SecurityPlugIn 
 **    Revision 1.26  2005/01/26 16:32:15Z  KamleshU 
 **    Revision 1.25  2004/11/03 10:15:53Z  KamleshU 
 **    changed code getting an xml representhing the state 
 **    Revision 1.24  2004/08/12 14:13:54Z  WojciechZ 
 **    propagate session data to subflows 
 **    Revision 1.23  2004/08/09 08:52:34Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.22  2004/08/02 16:13:09Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.21  2004/07/23 16:20:58Z  WojciechZ 
 **    fixes in subflows 
 **    Revision 1.20  2004/07/22 16:41:54Z  WojciechZ 
 **    fixes: starting subflow with one thread with autoactivities 
 **    Revision 1.19  2004/07/22 10:09:02Z  WojciechZ 
 **    added: new method to get full name of current thread 
 **    Revision 1.18  2004/07/21 15:52:17Z  WojciechZ 
 **    new feature: SubFlows 
 **    Revision 1.17  2004/07/19 12:51:51Z  WojciechZ 
 **    remove dependency of InteractionImpl class to praticular WorkflowProcess in xpdl file. Now InteractionImpl class represent the whole xpdl package, and ProcessStateImpl carry name of it WorkflowProcess (first step to SubFlows) 
 **    Revision 1.16  2004/07/15 12:31:07Z  WojciechZ 
 **    fix: multhread interaction - fixed 'processThreadUnitlWait' method 
 **    Revision 1.15  2004/07/14 10:43:15Z  WojciechZ 
 **    list of user threads shows only threads on manual applications 
 **    Revision 1.14  2004/07/08 16:14:07Z  WojciechZ 
 **    changes to comments, code cleaning 
 **    Revision 1.13  2004/07/02 11:03:19Z  WojciechZ 
 **    support for 'interaction state path' 
 **    Revision 1.12  2004/07/01 15:39:38Z  WojciechZ 
 **    tabbular interface, but still in progress 
 **    Revision 1.11  2004/06/28 14:13:52Z  WojciechZ 
 **    multiple interaction threads 
 **    Revision 1.10  2004/06/22 14:20:02Z  WojciechZ 
 **    some logging clean up 
 **    Revision 1.9  2004/06/21 08:08:14Z  WojciechZ 
 **    added method to select datafield by path (getPath()) 
 **    Revision 1.8  2004/06/09 13:25:02Z  WojciechZ 
 **    Handler for BusinessException 
 **    Revision 1.7  2004/06/07 10:01:18Z  WojciechZ 
 **    add session data for interaction state 
 **    Revision 1.6  2004/04/14 15:33:08Z  WojciechZ 
 **    InteractionImpl end state NPE fixed 
 **    Revision 1.5  2004/04/13 16:32:52Z  WojciechZ 
 **    work up to 13/04/2004 
 **    Revision 1.4  2004/04/08 16:02:16Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 **    Revision 1.3  2004/03/29 16:27:27Z  WojciechZ 
 **    work up to 29/03/2004 
 **    Revision 1.2  2004/03/29 08:48:55Z  WojciechZ 
 **    Work up to 29-03-2004 
 **    Revision 1.0  15-Mar-2004 WojciechZ 
 **    Initial revision 
 ** 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.InteractionRepository;
import com.tibco.inteng.InteractionRepositoryImpl;
import com.tibco.inteng.Package;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.interactionstate.ProcessStateDocument;
import com.tibco.inteng.interactionstate.ProcessStateType;
import com.tibco.inteng.xpdldata.XpdlComplexData;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * Current interaction state. All individual interaction data are stored in
 * obiects in this class.
 * 
 * @author WojciechZ
 */
public class ProcessStateImpl implements ProcessState {

    /** log4j */
    private final Logger log = Logger.getLogger(this.getClass());

    /** xpdlPackage (package) definition */
    private final Package xpdlPackage;

    /**
     * Indicator whether the state should be saved for as it cannot be completed
     * due to security reasons
     */
    private boolean stateToBeSaved;

    /**
     * XPDL process for this state, Although one interaction can have threads in
     * many processes in many packages when it use subflows, this is top level
     * package for this state
     */
    private final com.tibco.inteng.Process process;

    /** data fields: formal parameters and wrd */
    private Map fields = new HashMap();

    /**
     * session data
     * 
     * @see #getSessionData(Object)
     */
    private Map sessionData = new HashMap();

    /**
     * list of active xpdlPackage threads, empty list == all threads has
     * finished
     */
    private Map interactionThreads = new HashMap();

    private String currentThreadName;

    /**
     * Creation. Should be called only from factory method
     * InteractionImpl.getStartState()
     * 
     * @param xpdlPackage
     *            description of the xpdlPackage
     * @param process
     *            xpdl process in xpdl package
     */
    ProcessStateImpl(Package xpdlPackage, com.tibco.inteng.Process process) {
        this.xpdlPackage = xpdlPackage;
        this.process = process;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getInteraction()
     */
    public Package getXpdlPackage() {
        return xpdlPackage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#isStateToBeSaved()
     */
    public boolean isStateToBeSaved() {
        return stateToBeSaved;
    }

    /**
     * setter for security state
     * 
     * @param stateToBeSaved
     */
    void setStateToBeSaved(boolean stateToBeSaved) {
        this.stateToBeSaved = stateToBeSaved;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getPath(java.lang.String)
     */
    public XpdlData getPath(String path) {
        log.info("enter getPath: " + path);
        XpdlData result;
        if (path.startsWith("/")) {
            int i = path.indexOf('/', 1);
            if (i < 0) {
                // only name of the field: '/field'
                result = getField(path.substring(1));
            } else {
                // complex query: '/field/subfield ...'
                String fieldName = path.substring(1, i);
                String subField;
                if (i + 1 < path.length()) {
                    // '/field/sub...'
                    subField = path.substring(i + 1);
                } else {
                    // '/field/'
                    subField = "";
                }
                if (log.isDebugEnabled()) {
                    log.debug("field: " + fieldName);
                }
                XpdlData field = getField(fieldName);
                if (field instanceof XpdlComplexData) {
                    if (log.isDebugEnabled()) {
                        log.debug("sub field: " + subField);
                    }
                    result = ((XpdlComplexData) field).get(subField);
                } else {
                    if (log.isDebugEnabled()) {
                        log.warn("sub field: null!");
                    }
                    result = null;
                }
            }
        } else {
            // simple name of the field: 'field'
            if (log.isDebugEnabled()) {
                log.debug("simple field: " + path);
            }
            result = getField(path);
        }
        if (log.isDebugEnabled()) {
            log.debug("getPath(" + path + "): " + result.getXml());
        }
        log.info("exit getPath: " + path);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#isFinished()
     */
    public boolean isFinished() {
        return getCurrentActivityId() == null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getFields()
     */
    public Map getFields() {
        return fields;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getXml()
     */
    public String getStateXml() {
        ProcessStateDocument document = ProcessStateDocument.Factory
                .newInstance();
        document.setProcessState(getState());
        String toReturn = document.toString();
        return toReturn;
    }

    /**
     * Save current interaction state to xbean document. <br>
     * <i>Note: </i> session data is not stored in xml returned from this method
     * 
     * @return xbean representation of current state
     */
    private ProcessStateType getState() {
        ProcessStateType stateXml = ProcessStateType.Factory.newInstance();
        InteractionRepositoryImpl repoImpl = (InteractionRepositoryImpl) xpdlPackage
                .getInteractionRepository();
        String packageId = xpdlPackage.getPackageId();
        String packageLocation = repoImpl.getPackageLocation(packageId);
        String versionId = ((PackageImpl) xpdlPackage).getPackageVersionId();
        stateXml.setPackageId(xpdlPackage.getPackageId());
        if (!packageId.equalsIgnoreCase(packageLocation)) {
            stateXml.setLocation(packageLocation);
        }
        stateXml.setWorkflowProcessId(process.getId());
        // stateXml.setCurrentThreadName(getCurrentThread().getName());
        stateXml.setCurrentThreadName(currentThreadName);
        stateXml.setVersion(versionId);
        // storing datafields and formal parameters for the xpdlPackage state
        if (fields != null && fields.size() > 0) {
            ProcessStateType.Wrds wrds = stateXml.addNewWrds();
            Set entrySet = this.fields.entrySet();
            for (Iterator iter = entrySet.iterator(); iter.hasNext();) {
                Map.Entry element = (Map.Entry) iter.next();
                ProcessStateType.Wrds.Wrd eachField = wrds.addNewWrd();
                eachField.setId((String) element.getKey());
                eachField.setValue(((XpdlData) element.getValue()).getXml());
            }
        }
        // storing all the threads for this xpdlPackage state
        List threadsNames = getStateThreadsName();
        if (threadsNames != null && threadsNames.size() > 0) {
            ProcessStateType.Threads threads = stateXml.addNewThreads();
            for (Iterator iter = threadsNames.iterator(); iter.hasNext();) {
                ProcessStateType.Threads.Thread eachThread = threads
                        .addNewThread();
                String thName = (String) iter.next();
                ProcessThread th = getThreadByName(thName);
                eachThread.setName(thName);
                eachThread.setCurrentActivity(th.getCurrentActivityId());
                // setting the error activity id and error messages
                if (th.getErrorActivityId() != null
                        && th.getErrorActivityId().length() > 0
                        && th.getErrorMessageKey() != null
                        && th.getErrorMessageKey().length() > 0) {
                    eachThread.addNewError();
                    eachThread.getError()
                            .setActivityId(th.getErrorActivityId());
                    eachThread.getError().setMessage(th.getErrorMessageKey());
                }
                // setting the flag values
                eachThread.addNewStatusFlags();
                eachThread.getStatusFlags().setIsManual(th.isManual());
                eachThread.getStatusFlags().setIsSubmitted(th.isSubmitted());
                eachThread.getStatusFlags().setIsWaiting(th.isWaiting());
                ProcessState subFlow = th.getSubFlowProcessState();
                // setting the xpdlPackage state information for the current
                // thread.
                if (subFlow != null) {
                    ProcessStateType stateType = ((ProcessStateImpl) subFlow)
                            .getState();
                    eachThread.setState(stateType);
                }
            }
        }
        return stateXml;
    }

    /**
     * Return current threds's activity Id
     * 
     * @return current activity
     */
    private String getCurrentActivityId() {
        ProcessThread th = getCurrentThread();
        return th == null ? null : th.getCurrentActivityId();
    }

    /**
     * Create nef thread, and set it current activityId, and add it to the list
     * of active thread
     * 
     * @param threadName -
     *            name of new thread
     * @return created thread
     */
    public ProcessThread createNewThread(String threadName) {
        if (log.isInfoEnabled()) {
            log.info("Start thread: '" + threadName + "'");
        }
        ProcessThreadImpl thread = new ProcessThreadImpl(this);
        List stateThreadsName = getStateThreadsName();
        boolean b = stateThreadsName.contains(threadName);
        if (!b) {
            thread.setName(threadName);
            interactionThreads.put(threadName, thread);
        } else {
            int index = 0;
            while (true) {
                boolean idPresent = stateThreadsName.contains(threadName
                        + index);
                if (!idPresent) {
                    break;
                } else {
                    ++index;
                }
            }
            thread.setName(threadName + index);
            interactionThreads.put(threadName + index, thread);
        }
        return thread;
    }

    /**
     * This method will work on the passed threadName which could be either in
     * format xx::yy::zz or xx. If the passed threadName format is xx::yy::zz
     * then it will return the thread with name zz. If the passed threadName
     * format is xx then it will return the thread with name xx.
     * 
     * @param threadName
     * @return thread with given name, or null
     */
    public ProcessThread getThreadByName(String threadName) {
        log.debug("enter: getThread: " + threadName);

        String subFlowName = getSubFlowThreadName(threadName);
        ProcessThread result;
        if (subFlowName == null) {
            result = (ProcessThread) interactionThreads.get(threadName);
        } else {
            String thn = threadName
                    .substring(0, threadName.length() - subFlowName.length()
                            - IntEngConsts.THREAD_DELIM.length());
            result = (ProcessThread) interactionThreads.get(thn);
            ProcessState subFlow = result.getSubFlowProcessState();
            if (subFlow == null) {
                XpdlException e = new XpdlException(
                        "Try to get SubThread from thread without SubFlow: '"
                                + threadName + "'");
                log.error(e.getMessage(), e);
                throw e;
            }
            result = ((ProcessStateImpl) subFlow).getThreadByName(subFlowName);
        }
        log.info("exit: getThread, returning: " + result);
        return result;
    }

    /**
     * List of 'top-level' thread names
     * 
     * @return List of thread names
     */
    public List getStateThreadsName() {
        log.info("enter/exit: getThreadsNames");
        return new ArrayList(interactionThreads.keySet());
    }

    /**
     * List of thread names that can be presented to the user:
     * 
     * @return list of names if there is more then 1 thread
     */
    public List getAllUserThreadsName() {
        // TODO: TS this should use getUserThreadsMap else could produce diff
        // result from
        // getUserThreads and getUserThreadsNames!!

        log.debug("enter: getUserThreadsNames");
        List result;
        if (interactionThreads.size() > 1) {
            result = new ArrayList();
            for (Iterator iter = interactionThreads.keySet().iterator(); iter
                    .hasNext();) {
                String thName = (String) iter.next();
                ProcessThread th = (ProcessThread) interactionThreads
                        .get(thName);
                // if the thread has been made to wait due to security
                // then do not put in the list
                if (th.isSecurityWait()) {
                    continue;
                }
                ProcessStateImpl subFlow = (ProcessStateImpl) th
                        .getSubFlowProcessState();
                if (subFlow != null) {
                    List subFlowThreads = subFlow.getAllUserThreadsName();
                    if (subFlowThreads.isEmpty()) {
                        String subThName = subFlow.getCurrentThreadName();
                        if (!subFlow.getCurrentThread().isSecurityWait()) {
                            result.add(thName + IntEngConsts.THREAD_DELIM
                                    + subThName);
                        }
                    } else {
                        for (Iterator iterator = subFlowThreads.iterator(); iterator
                                .hasNext();) {
                            String subThName = (String) iterator.next();
                            result.add(thName + IntEngConsts.THREAD_DELIM
                                    + subThName);
                        }
                    }
                } else if (th.isManual()) {
                    result.add(thName);
                }
            }
            Collections.sort(result);
        } else {
            result = Collections.EMPTY_LIST;
        }
        if (log.isDebugEnabled()) {
            log.debug("exit: getUserThreadsNames: " + result);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getUserThreads()
     */
    private List getUserThreads() {
        // TODO: TS this should use getUserThreadsMap else could produce diff
        // result from
        // getUserThreads and getUserThreadsNames!!
        log.debug("enter: getUserThreadsNames");
        List result;
        if (interactionThreads.size() > 1) {
            result = new ArrayList();
            for (Iterator iter = interactionThreads.values().iterator(); iter
                    .hasNext();) {
                ProcessThread th = (ProcessThread) iter.next();
                ProcessStateImpl subFlow = (ProcessStateImpl) th
                        .getSubFlowProcessState();
                if (subFlow != null) {
                    result.addAll(subFlow.getUserThreads());
                } else if (th.isManual()) {
                    result.add(th);
                }
            }
        } else {
            result = Collections.EMPTY_LIST;
        }
        return result;
    }

    /**
     * List of threads that can be presented to the user.
     * 
     * @return <code>Map</code> of <code>InteractionThreadStateImpl</code>
     *         objects keyed by thread name.
     */
    private Map getUserThreadsMap() {
        log.info("enter: getUserThreadsMap");
        Map result;
        if (interactionThreads.size() > 1) {
            result = new TreeMap(); // use treemap to sort alphabetically
            for (Iterator iter = interactionThreads.keySet().iterator(); iter
                    .hasNext();) {
                String thName = (String) iter.next();
                ProcessThread th = (ProcessThread) interactionThreads
                        .get(thName);
                log.debug("Thread stored with name: " + thName);
                log.debug("Thread name: " + th.getName());
                // if the thread has been made to wait due to security
                // then do not put in the list
                if (th.isSecurityWait()) {
                    log.debug("ignoring thread not authorised to exec: "
                            + th.getName());
                    continue;
                }
                ProcessStateImpl subFlow = (ProcessStateImpl) th
                        .getSubFlowProcessState();
                if (subFlow != null) {
                    log.debug(th.getName() + " is manual");
                    result.putAll(subFlow.getUserThreadsMap());
                } else if (th.isManual()) {
                    result.put(th.getName(), th);
                }
            }
        } else {
            result = Collections.EMPTY_MAP;
        }
        log.info("exit: getUserThreadsMap, no. of threads: " + result.size());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getCurrentThread()
     */
    public ProcessThread getCurrentThread() {
        log.debug("enter: getCurrentThread " + currentThreadName);
        ProcessThread th = getThreadByName(currentThreadName);
        if (th == null) {
            return null;
        }
        ProcessState subFlowKey = th.getSubFlowProcessState();
        if (subFlowKey != null) {
            th = subFlowKey.getCurrentThread();
        }
        return th;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#switchToThread(java.lang.String)
     */
    public void switchToThread(String newCurrentThreadName) {
        log
                .debug("+----------------------------------------------------------------");
        log.debug("|");
        log.debug("|");
        log.debug("| SWITCH TO THREAD: " + newCurrentThreadName);
        log.debug("|");
        log.debug("|");
        log
                .debug("+----------------------------------------------------------------");

        String sub = getSubFlowThreadName(newCurrentThreadName);
        if (sub == null) {
            currentThreadName = newCurrentThreadName;
        } else {
            currentThreadName = newCurrentThreadName.substring(0,
                    newCurrentThreadName.length() - sub.length()
                            - IntEngConsts.THREAD_DELIM.length());
            ProcessThread th = getThreadByName(currentThreadName);
            th.getSubFlowProcessState().switchToThread(sub);
        }

        if (getThreadByName(currentThreadName) == null) {
            XpdlException e = new XpdlException(
                    "Try to switch to not existing thread: "
                            + newCurrentThreadName);
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @return name of the current thread
     */
    private String getCurrentThreadName() {
        log.debug("enter: getCurrentThreadName: " + currentThreadName);
        return currentThreadName;
    }

    /**
     * Returns full name of current thread: if current thread is on subFlow it
     * return subFlow currentThreadFullName prefixed with ths thread name, ie:
     * 'thread::subFlowThread'
     * 
     * @return Full name of current thread
     */
    public String getCurrentThreadFullName() {
        StringBuffer sb = new StringBuffer(currentThreadName);
        ProcessThread th = getThreadByName(currentThreadName);
        ProcessStateImpl subFlowKey = (ProcessStateImpl) th
                .getSubFlowProcessState();
        if (subFlowKey != null) {
            sb.append(IntEngConsts.THREAD_DELIM);
            sb.append(subFlowKey.getCurrentThreadFullName());
        }
        return sb.toString();
    }

    /**
     * withdraw (remove) thread with given name
     * 
     * @param thName -
     *            name of the thread to remove
     */
    public void withdrawThread(String thName) {
        log.debug("Withdraw thread: " + thName);
        /**
         * This change was done by kam on 3rd of Aug 2005 to look into bug
         * 22614. If the current thread is being withdrawn then we should change
         * the threadName to startThread1
         */
        String startThreadName = "startThread1";
        if (thName.equalsIgnoreCase(currentThreadName)) {
            currentThreadName = startThreadName;
        }
        interactionThreads.remove(thName);
        /* there could be mismatch of thread names between the waiting threads on
         * a activity and threads known to the process state so not a good idea.
         * if (interactionThreads.keySet().size() == 1) {
            String lastThreadName = (String) interactionThreads.keySet().iterator()
                    .next();
            if (!lastThreadName.equals(startThreadName)) {
                ProcessThreadImpl lastProcessThread = (ProcessThreadImpl) interactionThreads
                        .get(lastThreadName);                
                lastProcessThread.setName(startThreadName);
                interactionThreads.remove(lastThreadName);
                interactionThreads.put(lastProcessThread.getName(),lastProcessThread);
            }
        }*/

    }

    /**
     * Scan all threads, and return one which is waiting on given activity Id
     * 
     * @param activityId
     * @return thread waiting on given activity or null
     */
    ProcessThread getWaitingThreadOnActivity(String activityId) {
        for (Iterator iter = interactionThreads.values().iterator(); iter
                .hasNext();) {
            ProcessThread thread = (ProcessThread) iter.next();
            if (activityId.equals(thread.getCurrentActivityId())) {
                return thread;
            }
        }
        return null;
    }

    /**
     * @param oldName
     * @param newName
     */
    public void renameThread(String oldName, String newName) {
        ProcessThreadImpl th = (ProcessThreadImpl) interactionThreads
                .remove(oldName);
        if (currentThreadName != null && currentThreadName.equals(oldName)) {
            currentThreadName = newName;
        }
        th.setName(newName);
        interactionThreads.put(newName, th);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getProcess()
     */
    public com.tibco.inteng.Process getProcess() {
        return process;
    }

    public InteractionRepository getInteractionRepository() {
        return getXpdlPackage().getInteractionRepository();
    }

    private String getSubFlowThreadName(String threadName) {
        int nr = threadName.indexOf(IntEngConsts.THREAD_DELIM);
        if (nr < 0) {
            return null;
        }
        if (nr + IntEngConsts.THREAD_DELIM.length() >= threadName.length()
                || nr == 0) {
            XpdlException e = new XpdlException("Invalid thread name: '"
                    + threadName + "'");
            log.error(e.getMessage(), e);
            throw e;
        }
        return threadName.substring(nr + IntEngConsts.THREAD_DELIM.length());
    }

    /**
     * check if thread is waiting for other threads before join condition
     * 
     * @param thName -
     *            mane of thread to check
     * @return true, if: top level thread is waiting or sub flow thred is
     *         waiting
     * 
     */
    private boolean isThreadWaiting(String thName) {
        String subThName = getSubFlowThreadName(thName);
        if (subThName != null) {
            thName = thName.substring(0, thName.length() - subThName.length()
                    - IntEngConsts.THREAD_DELIM.length());
        }
        ProcessThread th = getThreadByName(thName);
        if (th.isWaiting()) {
            return true;
        }
        if (th.getSubFlowProcessState() != null) {
            ProcessStateImpl stateImpl = (ProcessStateImpl) th
                    .getSubFlowProcessState();
            return stateImpl.isThreadWaiting(subThName);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#getSessionData()
     */
    public Map getSessionData() {
        return sessionData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.inteng.ProcessState#setSessionData(java.util.Map)
     */
    public void setSessionData(Map sessionData) {
        this.sessionData = sessionData;
    }

    /**
     * Get alue of interaction field
     * 
     * @param key
     *            field Id
     * @return field
     */
    private XpdlData getField(String key) {
        log.info("enter/: getField(" + key + ")");
        key = key.trim();
        if (!fields.containsKey(key)) {
            XpdlException e = new XpdlException("Process doesn't contain '"
                    + key + "' field");
            log.warn(e);
            throw e;
        }
        XpdlData field = (XpdlData) fields.get(key);
        return field;
    }

}