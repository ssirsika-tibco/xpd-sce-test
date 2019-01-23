/*******************************************************************************
 * Copyright 2010 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */
package com.tibco.bx.debug.api;

import javax.management.NotificationEmitter;
import javax.management.openmbean.TabularData;

public interface DebuggerMBean extends NotificationEmitter {

	/**
	 * for Debugger
	 */
	
    void initialize();

    void close();
    
    boolean addBreakpoint(String processId, String location, BreakWhen when, String condExpression, ConditionLanguage language);

    boolean removeBreakpoint(String processId, String location, BreakWhen when);

    boolean removeAllBreakpoints();

    void setBreakpointEnable(String processId, String location, BreakWhen when, boolean isEnable);
    
    public void runToActivity(String instanceId, String location, BreakWhen when);
   
    void terminate(String instanceId);

    void terminateAll();

    void stepOver(String instanceId);

    boolean stepInto(String instanceId);
    
	boolean canStepInto(String instanceId);
	
	boolean canStepOut(String instanceId);

    void stepOut(String instanceId);
  
    void suspend(String instanceId);

    void suspendAll();

    void resume(String instanceId);

    void resumeAll();

    TabularData getProcessTemplates();
   
    TabularData getVariables(String instanceId);

    TabularData getActivities(String instanceId);
    
    boolean setVariable(String instanceId, String variableName, Object variableValue);
    
    boolean setNodeDefinitions(String processId, TabularData tabularData);
    
    
    /**
     *  for Tester
     */
    boolean addTestpoint(String processId, String location, String emulationExpression, ConditionLanguage expressionLanguage);

    boolean removeTestpoint(String processId, String location);

    boolean removeAllTestpoints();

    boolean addAssertion(String processId, String location, boolean value);

    boolean removeAssertion(String processId, String location);

    boolean removeAllAssertions();

    
    // field names
    final static String PROCESS_NAME = "name";
    final static String PROCESS_ID = "processId";
    final static String PROCESS_INSTANCE_ID = "processInstanceId";
    final static String PROCESS_STATE = "state";
    final static String MODULE_NAME = "moduleName";
    final static String MODULE_VERSION = "moduleVersion";
    final static String STARTER = "starter";

    final static String ACTIVITY_NAME = "name";
    final static String ACTIVITY_ID = "activityId";
    final static String ACTIVITY_TYPE = "activityType";
    final static String ACTIVITY_TOKEN = "activityInstanceToken";
    final static String ACTIVITY_STATE = "state";
    final static String PARENT_INSTANCE_ID = "parentInstanceId";
    final static String SUB_PROCESS = "subProcess";
    final static String SKIPPED = "skipped";   
    
    final static String PARENT_ACTIVITY_ID = "parentActivityId";
    final static String CAN_STEP_INTO = "canStepInto";
    final static String CAN_STEP_OUT = "canStepOut";
    final static String SOURCE_IDS = "sourceIds";
    final static String TARGET_IDS = "targetIds";
    
    final static String LINK_NAME = "name";
    final static String LINK_ID = "linkId";
    final static String LINK_TOKEN = "linkInstanceToken";
    final static String LINK_STATE = "state";
    final static String LINK_FROM_ID = "fromId";
    final static String LINK_FROM_TASK_NAME = "fromTask";
    final static String LINK_TO_ID = "toId";
    final static String LINK_TO_TASK_NAME = "toTask";    

    final static String VARIABLE_NAME = "name";
    final static String VARIABLE_ID = "variableId";
    final static String VARIABLE_TYPE = "type";
    final static String VARIABLE_NAMESPACE = "namespace";
    final static String VARIABLE_HASCHILDREN = "hasChildren";
    final static String VARIABLE_VALUE = "value";
    final static String VARIABLE_LIST = "variableList";

    final static String BREAK_WHEN = "breakWhen";
    final static String BREAKPOINT_EXPR = "breakpointExpression";

    final static String STATUS = "status";
    final static String ACTION_CMD = "actionCommand";
    final static String TRACK_ID = "track";
    final static String INSTANCE_ID = "instanceId";
    
    final static int ACTIVITY_BEGIN = 1;
    final static int ACTIVITY_END = 2;
}