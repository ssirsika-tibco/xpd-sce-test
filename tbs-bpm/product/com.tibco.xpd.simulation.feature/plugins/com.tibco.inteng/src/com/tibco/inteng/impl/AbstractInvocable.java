/* 
 ** 
 **  MODULE:             $RCSfile: AbstractInvocable.java $ 
 **                      $Revision: 1.13 $ 
 **                      $Date: 2005/04/26 14:32:48Z $ 
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
 **    $Log: AbstractInvocable.java $ 
 **    Revision 1.13  2005/04/26 14:32:48Z  KamleshU 
 **    checking whether the value to replace is numeric or not. 
 **    Revision 1.12  2004/12/21 18:30:26Z  KamleshU 
 **    Revision 1.11  2004/11/18 11:10:18Z  KamleshU 
 **    changed code to ensure the default mode of parameters is INOUT and to avoid null pointer exception 
 **    Revision 1.10  2004/11/03 10:12:11Z  KamleshU 
 **    As the ProcessStateImpl has a ResourceLocator so the datatype constrcution should use it, so changes accordingly 
 **    Revision 1.9  2004/10/20 08:45:02Z  KamleshU 
 **    Changes made for making the restricted schema work 
 **    Revision 1.8  2004/10/05 14:45:19Z  KamleshU 
 **    changes made to avoid validation messages while skipping the form application 
 **    Revision 1.7  2004/08/12 07:29:03Z  WojciechZ 
 **    Revision 1.6  2004/08/11 09:54:36Z  WojciechZ 
 **    better error messages 
 **    Revision 1.5  2004/08/09 08:52:34Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.4  2004/07/27 16:46:01Z  WojciechZ 
 **    fix: npe when validation form in subFlow 
 **    Revision 1.3  2004/07/26 12:23:03Z  KamleshU 
 **    Changed the validate parameter that goes in the submit method 
 **    Revision 1.2  2004/07/26 10:33:13Z  KamleshU 
 **    Validate and Submit methods have been separated 
 **    Revision 1.1  2004/07/21 16:20:39Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  19-Jul-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.wfmc.x2002.xpdl10.FormalParameterDocument.FormalParameter;
import org.wfmc.x2002.xpdl10.FormalParameterDocument.FormalParameter.Mode;

import com.tibco.inteng.Invocable;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.xpdldata.SchemaTypedField;
import com.tibco.inteng.xpdldata.XpdlComplexData;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.XpdlDataFactory;
import com.tibco.inteng.xpdldata.XpdlSimpleData;

/**
 * This is an abstract class for the Invocable interface.
 * 
 * @author KamleshU
 */
public abstract class AbstractInvocable implements Invocable{
    /** log4j */
    private static Logger log = Logger.getLogger(AbstractInvocable.class);
    
    private static final String VARIABLE = "$";    
    /**
     * This method returns a list of formalParameter objects
     * 
     * @return a list of xml beans based FormalParamater objects
     */
    protected abstract List getParameters();
    /**
     * Validate list arguments   
     * 
     * @param state
     * @param args
     * @return
     */
    public boolean validate(ProcessState state, List args) {
        boolean toReturn = true;

        ProcessThread thread = state.getCurrentThread();
        // make sure we are using proper interaction state for current thread
        state = thread.getProcessState();
        com.tibco.inteng.Process process = state.getProcess();

        Activity act = ((ProcessImpl)process).getActivity(thread.getCurrentActivityId());

        String[] actualParams = act.getActualParameters();
        if (args.size() != actualParams.length) {
            XpdlException e = new XpdlException(
                    "Formal and Actual parameters for applicatoin '" + getId()
                            + "' have to be identical!");
            log.error(e);
            throw e;
        }
        if (getFormalParameters() == null) {
            toReturn = true;
        }
        return toReturn;
    }   
    /**
     * Returns ID of the interface
     * 
     * @return
     */
    public abstract String getId();
    /**
     * Submit list of parameters to workflow data.<br>
     * Validation according to mandatory fields are perfromed when <code>validate</code> is set.
     * No data type validation is performed (it should be done on seting valies for XpdlData).
     * 
     * Ususal usage pattern:
     * <pre>
     * ...
     * List params = app.getFormalPArameters(interactionState);
     * // modify params i.e.: 
     * ((XpdlData)params.get(0)).setValue("new value");
     * if (!app.submit(interactionState,params,true)) {
     *   // show error message etc.
     * }
     * ...
     * </pre>
     * 
     * @param state interaction state
     * @param args list of formal parameters     
     */
    public void submit(ProcessState state, List args) {       
        submit(state.getCurrentThread(), args);
        ProcessThreadImpl thread = (ProcessThreadImpl)state.getCurrentThread();
        thread.setSubmitted(true);
    }
    /**
     * @see #submit(ProcessStateImpl, List)
     * 
     * @param thread interaction thread state
     * @param args list of formal parameters      
     */
    protected void submit(ProcessThread thread, List args) {
        String activityId = thread.getCurrentActivityId();

        log.info("enter: submit(activityId='" + activityId
                + "', InteractionThreadStateImpl thread,List args)");
        ProcessStateImpl state = (ProcessStateImpl)thread.getProcessState();
        ProcessImpl processImpl = (ProcessImpl)state.getProcess(); 
        Activity act = processImpl.getActivity(
                thread.getCurrentActivityId());
        String[] actualParams = act.getActualParameters();

        List fParams = getParameters();

        if (actualParams.length != fParams.size()) {
            XpdlException e = new XpdlException(
                    "Formal and actual parametes have to match (" + act
                            + " to " + getId() + ")");
            log.error(e.getMessage(), e);
            throw e;
        }

        for (int i = 0; i < actualParams.length; i++) {
        	FormalParameter eachParam = ((FormalParameter)fParams.get(i));
            if (!eachParam.getMode().equals(Mode.IN) && args.get(i) != null) {
                if (log.isInfoEnabled()) {
                    log.info("Update (on submit) 'OUT' or 'INOUT' parameter: "
                            + actualParams[i]);
                }
                String path = actualParams[i].trim();
                if(path.indexOf(AbstractInvocable.VARIABLE)!=-1){
                    path= processPath(state, actualParams[i].trim());
                }
                XpdlData actualField = state.getPath(path);
                try {

                    actualField.setValue(args.get(i));

                } catch (XpdlDataFormatException e) {
                    XpdlException e1 = new XpdlException("Value ('"
                            + args.get(i)
                            + "') returned from Application (ID:" + getId()
                            + ") for actual parameter '" + actualParams[i]
                            + "' is not valid.", e);
                    log.error(e1.getMessage(), e);
                    throw e1;
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Skipped (on submit) 'IN' or removed parameter: "
                            + actualParams[i]);
                }
            }
        }
    }
    /**
     * Return list of initialized (from current state) formal parameters.
     * @see #submit(ProcessStateImpl, List)
     * 
     * @param state interaction state
     * @return list of FormFields objects
     */
    public List getPopulatedFormalParameters(ProcessThread thread) {
        String activityId = thread.getCurrentActivityId();
        return getFormalParameters(activityId, thread.getProcessState());
    }
    /**
     * Return a list of initialized formal parameters from the passed state.
     * Each item in the list is an instance of XpdlData.
     *  
     * @see #submit(ProcessStateImpl, List)
     * 
     * @param activityId
     * @param state interaction state
     * @return list of FormFields objects
     */
    protected List getFormalParameters(String activityId, ProcessState intState) {
        log.info("enter: getFormalParameters");

        // make sure, that we have proper state for current thread
        // see doc for <code>getCurrentThread</code>
        // state = state.getCurrentThread().getInteractionState();
        ProcessStateImpl state = (ProcessStateImpl)intState;
        ProcessImpl processImpl = (ProcessImpl)state.getProcess();
        Activity activity = processImpl.getActivity(activityId);
        if (activity == null) {
            throw new XpdlException("Invalid activity: " + activityId);
        }

        List fParams = getParameters();
        if (fParams == null) {
            return Collections.EMPTY_LIST;
        }        
        String[] actualParams = activity.getActualParameters();
        if (fParams.size() != actualParams.length) {
            XpdlException e = new XpdlException(
                    "Formal and Actual parameters for application have to be identical! (on activity: "
                            + activity.getId() + ", application: " + getId());
            log.error(e);
            throw e;
        }
        List result = new ArrayList();
        for (int i = 0; i < actualParams.length; i++) {
            String path = actualParams[i].trim();
            if(path.indexOf(AbstractInvocable.VARIABLE)!=-1){
                path= processPath(state, actualParams[i].trim());
            }
            XpdlData val = state.getPath(path);
            if (val == null) {
                XpdlException e = new XpdlException(
                        "There is no process parameter or data field with '"
                                + actualParams[i]
                                + "' id. (used as actual parameter on activity: '"
                                + activity.getId() + "')");
                log.error(e.getMessage(), e);
                throw e;
            }
            FormalParameter eachFP =  (FormalParameter)fParams.get(i);            
            String name = eachFP.getId();
            XpdlData data;
            Mode.Enum modeEnum = eachFP.getMode();
            boolean readonly = Mode.IN.equals(modeEnum);
            if (Mode.OUT.equals(modeEnum)) {
                if (log.isDebugEnabled()) {
                    log.debug("Initialize 'OUT' parameter: " + actualParams[i]);
                }
                data = XpdlDataFactory.getDataType(name, eachFP
                        .getDataType(), state, readonly);
            } else {
                if (val instanceof SchemaTypedField) {
                    if (log.isInfoEnabled()) {
                        log.info("Copy " + val.getName() + " to " + name);
                    }
                    data = XpdlDataFactory.getDataType(name, eachFP
                            .getDataType(), state, readonly);
                    try {
                        data.setValue(val);
                    } catch (XpdlDataFormatException e) {
                        throw new XpdlException(e.getMessage(), e);
                    }                    
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("Initialize: " + eachFP.getId());
                    }
                    data = XpdlDataFactory.getDataType(name, eachFP
                            .getDataType(), state, readonly);
                    if (log.isInfoEnabled()) {
                        log.info("Copy content from" + actualParams[i] + " to "
                                + eachFP.getId());
                    }
                    try {
                        data.setValue(val.getXml());
                    } catch (XpdlDataFormatException e) {
                        log.error(e.getMessage(), e);
                        throw new XpdlException(e);
                    }
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("data '" + data.getName() + "', readonly="
                        + data.isReadonly() + "; XML: " + data.getXml());
            }
            result.add(data);
        }
        log.info("exit: getFormalParameters");
        return result;
    }  
    
    /**
     * Transfer key in format: 
     * /claim/claimants/claimant/claimItems/claimItem[claimItemID=${vehicle/vehicleID}]/claimItemDetails
     * into
     * /claim/claimants/claimant/claimItems/claimItem[claimItemID=10]/claimItemDetails
     * The pattern used to get the data is '\$\{([^\{\}]+)\}'
     * Supports nested variable substitution.
     * 
     * @param state 
     * @param key key to translate 
     * @return xpatch
     */
    private String processPath(ProcessStateImpl state, String key) {
        //Pattern pattern = Pattern.compile("\\"+AbstractInvocable.VARIABLE+"\\{([\\w+/?]*)\\}");
        Pattern pattern = Pattern.compile("\\"+AbstractInvocable.VARIABLE+"\\{([^\\{\\}]+)\\}");
        Matcher matcher = pattern.matcher(key);
        String result = key;
        while (matcher.find()) {
            String newPath = matcher.group(1);            
            XpdlData data = state.getPath(newPath);
            String valueToReplace = "";
            if(data!=null && (data instanceof XpdlSimpleData || data instanceof XpdlComplexData)){
                valueToReplace = (String)data.getValue();
            }
            else {
                throw new XpdlException("The value from variable part "+newPath+" could not be set in the xpath "+key);
            }
            try{
                Integer.parseInt(valueToReplace);
                result = matcher.replaceFirst(valueToReplace);
            }catch(NumberFormatException nfe)
            {
                result = matcher.replaceFirst("'"+valueToReplace+"'");
            }            
            matcher = pattern.matcher(result);            
        }        
        return result;
    }
}