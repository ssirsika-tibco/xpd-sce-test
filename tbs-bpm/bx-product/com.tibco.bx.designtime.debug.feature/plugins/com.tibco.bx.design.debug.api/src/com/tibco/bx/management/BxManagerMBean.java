/*******************************************************************************
 * Copyright 2006 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */
package com.tibco.bx.management;

import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;


/**
 * Created by IntelliJ IDEA.
 * User: goldberg
 * Date: Sep 13, 2007
 * Time: 11:32:06 AM
 */

/**
 * Runtime process engine management MBean
 */
public interface BxManagerMBean {

    // field names
    static String MODULE_NAME = "moduleName";
    static String PROCESS_NAME = "processName";
    static String PROCESS_DESCRIPTION = "processDescription";
    static String PROCESS_ID = "processId";
    static String VERSION = "version";
    static String PROCESS_STATE = "processState";
    static String START_TIME = "startTime";
    static String COMPLETION_TIME = "completionTime";
    static String NAMESPACE = "namespace";
    static String PORT_TYPE = "portType";
    static String OPERATION = "operation";
    static String ORIGINATOR = "originator";
    static String PRIORITY = "priority";
    static String ACTIVITY_ID = "activityId";
    static String ACTIVITY_NAME = "activityName";
    static String ACTIVITY_STATE = "activityState";
    static String DEADLINE = "deadline";
    static String PARENT_PROCESS_ID = "parentProcessId";
    static String WAITING_WORK_COUNT = "waitingWorkCount";

    static String INDEX = "INDEX";
    static String NAME = "NAME";
    static String TYPE = "TYPE";
    static String VALUE = "VALUE";

    // add for annotations
	static String VOID_TYPE = "void";
	static String INT_TYPE = "int";
	static String STRING_TYPE = "java.lang.String";
	static String TABULARDATA_TYPE = "javax.management.openmbean.TabularData";
	static String COMPOSITEDATA_TYPE = "javax.management.openmbean.CompositeData";
	static String COMPOSITEDATA_ARRAY_TYPE = "[Ljavax.management.openmbean.CompositeData;";
	static String OBJECT_TYPE = "java.lang.Object";
	
	static String ELE_NAME = "processName";
	static String ELE_TYPE = "attributeType";
	static String ELE_DESCRIPTION = "description";
	static String ELE_MODULE_NAME = "moduleName";
	static String ELE_PROCESS_NAME = "processName";
	static String ELE_PROCESS_DESCRIPTION = "description";
	static String ELE_VERSION = "version";
	static String ELE_STATE = "state";
	static String ELE_PROCESS_ID = "processID";
	static String ELE_PROCESS_STATE = "processState";
	static String ELE_START_TIME = "startTime";
	static String ELE_COMPLETION_TIME = "completionTime";
	static String ELE_ORIGINATOR = "originator";
	static String ELE_VALUE = "value";
	static String ELE_NAMESPACE = "namespace";
	static String ELE_OPERATION = "operation";
	static String ELE_PORTTYPE = "portType";
	static String ELE_PRIORITY = "priority";
	static String ELE_ACTIVITY_ID = "activityId";
	static String ELE_ACTIVITY_NAME = "activityName";
	static String ELE_ACTIVITY_STATE = "activityState";
	static String ELE_DEADLINE_TIME = "deadlineExpirationTime";
	static String ELE_COMPLETION_COUNT = "completionCount";
	static String ELE_ITEM_COUNT = "itemCount";
	static String ELE_SUMMARY = "summary";
	static String ELE_QUERY_ID = "queryID";
	static String ELE_QUERY = "query";
	static String ELE_QUERY_STRING = "queryString";
	static String ELE_COUNT = "count";
	static String ELE_PARAMETER_VALUE = "parameterValue";
	static String ELE_UNDEPLOY_RESPONSE = "undeployResponse";
	static String ELE_CREATION_DATE = "creationDate";
    static String ELE_PARENT_PROCESS_ID = "parentProcessID";
    static String ELE_WAITING_WORK_COUNT = "waitingWorkCount";
	static String ELE_PAGESIZE = "pageSize";
	static String ELE_MAP = "attributeMap";
	static String ELE_PMAP = "parameterMap";
	static String ELE_SELECT = "select";
	static String ELE_WHERE = "where";
	static String ELE_ORDERBY = "orderBy";
	static String ELE_PAGING_ID = "pagingID";
	static String ELE_EXPIRATION_TIME = "expirationTime";
	static String ELE_PARAMETER_NAME = "parameterName";
	static String ELE_OPERATION_NAME = "operationName";
	static String ELE_SUCCESS = "success";
	
	


    String getVersion();

    String getGroup();

    /**
    *
    * Retrieves information about deployed process templates.
    * TabularData contains a CompositeData object for each process template.
    * The CompositeData contains the following items:
    * <table cellspacing="12">
    * <tr>
    * <td>NAME</td>               <td>TYPE</td>      <td>DESCRIPTION</td>
    * </tr> <tr>
    * <td>moduleName</td>         <td>String</td>    <td>module name</td>
    * </tr> <tr>
    * <td>processName</td>        <td>String</td>    <td>process template name</td>
    * </tr> <tr>
    * <td>processDescription</td> <td>String</td>    <td>process template description</td>
    * </tr> <tr>
    * <td>version</td>            <td>String</td>   <td>process template version</td>
    * </tr>
    * </table>
    * @param moduleName Optional, if specified restricts return to only process templates with matching module name
    * @param processName Optional, if specified restricts return to only process templates with matching process name
    * @return results in tabular data format
    */
   @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType = TABULARDATA_TYPE, operationName = "Deprecated", 
   		itemNames = {MODULE_NAME, PROCESS_NAME, PROCESS_DESCRIPTION, VERSION },
   		elementNames = {ELE_MODULE_NAME,ELE_NAME,ELE_PROCESS_DESCRIPTION,ELE_VERSION},
   		parentElement="processDefinition",grandElement="processDefinitions")
   	@Deprecated
   TabularData listProcessTemplates(String moduleName, String processName) throws Throwable;

    /**
    *
    * Retrieves information about deployed process templates.
    * TabularData contains a CompositeData object for each process template.
    * The CompositeData contains the following items:
    * <table cellspacing="12">
    * <tr>
    * <td>NAME</td>               <td>TYPE</td>      <td>DESCRIPTION</td>
    * </tr> <tr>
    * <td>moduleName</td>         <td>String</td>    <td>module name</td>
    * </tr> <tr>
    * <td>processName</td>        <td>String</td>    <td>process template name</td>
    * </tr> <tr>
    * <td>processDescription</td> <td>String</td>    <td>process template description</td>
    * </tr> <tr>
    * <td>version</td>            <td>String</td>   <td>process template version</td>
    * </tr>
    * </table>
    * @param moduleName Optional, if specified restricts return to only process templates with matching module name
    * @param processName Optional, if specified restricts return to only process templates with matching process name
    * @param version Optional, if specified restricts return to only process templates with matching version
    * @return results in tabular data format
    */
   @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType = TABULARDATA_TYPE, operationName = "listProcessTemplates", 
   		itemNames = {MODULE_NAME, PROCESS_NAME, PROCESS_DESCRIPTION, VERSION },
   		elementNames = {ELE_MODULE_NAME,ELE_NAME,ELE_PROCESS_DESCRIPTION,ELE_VERSION},
   		parentElement="processDefinition",grandElement="processDefinitions")
   TabularData listProcessTemplates(String moduleName, String processName, String version) throws Throwable;
   
   /**
    * Retrieves information about process instances.
    * TabularData contains a CompositeData object for each process instance.
    * The CompositeData contains the following items:
    * <table cellspacing="12">
    * <tr>
    * <td>NAME</td>           <td>TYPE</td>      <td>DESCRIPTION</td>
    * </tr> <tr>
    * <td>moduleName</td>     <td>String</td>   <td>module name</td>
    * </tr> <tr>
    * <td>processId</td>      <td>String</td>   <td>process instance identifier</td>
    * </tr> <tr>
    * <td>processState</td>   <td>String</td>   <td>process instance state</td>
    * </tr> <tr>
    * <td>processName</td>    <td>String</td>   <td>process template name</td>
    * </tr> <tr>
    * <td>version</td>        <td>String</td>   <td>process template version</td>
    * </tr> <tr>
    * <td>startTime</td>      <td>Long</td>     <td>process start time</td>
    * </tr> <tr>
    * <td>completionTime</td> <td>Long</td>     <td>process completion time</td>
    * </tr> <tr>
    * <td>originator</td>     <td>String</td>   <td>user triggering process instance</td>
    * </tr> <tr>
    * <td>parentProcessId</td> <td>String</td>  <td>process instance id of parent process</td>
    * </tr> <tr>
    * <td>waitingWorkCount</td> <td>Integer</td> <td>count of specifally marked waiting tasks for this process instance</td>
    * </tr>
    * </table>
    *
    * @param moduleName Optional, if specified restricts return to only process instances with matching module name
    * @param processName Optional, if specified restricts return to only process instances with matching process name
    * @return results in tabular data format
    */
   @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME}, returnType=TABULARDATA_TYPE,operationName="Deprecated",
   		itemNames={MODULE_NAME, PROCESS_ID, PROCESS_STATE, PROCESS_NAME, VERSION, START_TIME, COMPLETION_TIME, ORIGINATOR, PARENT_PROCESS_ID, WAITING_WORK_COUNT},
   		elementNames={ELE_MODULE_NAME,ELE_PROCESS_ID,ELE_PROCESS_STATE,ELE_PROCESS_NAME,ELE_VERSION,ELE_START_TIME,ELE_COMPLETION_TIME,ELE_ORIGINATOR,ELE_PARENT_PROCESS_ID, ELE_WAITING_WORK_COUNT}
   		,parentElement="processInstance",grandElement="processInstances")
   @Deprecated
   TabularData listProcessInstances(String moduleName, String processName) throws Throwable;
    
    
    /**
     * Retrieves information about process instances.
     * TabularData contains a CompositeData object for each process instance.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>           <td>TYPE</td>      <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>moduleName</td>     <td>String</td>   <td>module name</td>
     * </tr> <tr>
     * <td>processId</td>      <td>String</td>   <td>process instance identifier</td>
     * </tr> <tr>
     * <td>processState</td>   <td>String</td>   <td>process instance state</td>
     * </tr> <tr>
     * <td>processName</td>    <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>version</td>        <td>String</td>   <td>process template version</td>
     * </tr> <tr>
     * <td>startTime</td>      <td>Long</td>     <td>process start time</td>
     * </tr> <tr>
     * <td>completionTime</td> <td>Long</td>     <td>process completion time</td>
     * </tr> <tr>
     * <td>originator</td>     <td>String</td>   <td>user triggering process instance</td>
     * </tr> <tr>
     * <td>parentProcessId</td> <td>String</td>  <td>process instance id of parent process</td>
     * </tr> <tr>
     * <td>waitingWorkCount</td> <td>Integer</td> <td>count of specifally marked waiting tasks for this process instance</td>
     * </tr>
     * </table>
     *
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @param version Optional, if specified restricts return to only process instances with matching version
     * @return results in tabular data format
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=TABULARDATA_TYPE,operationName="listProcessInstances",
    		itemNames={MODULE_NAME, PROCESS_ID, PROCESS_STATE, PROCESS_NAME, VERSION, START_TIME, COMPLETION_TIME, ORIGINATOR, PARENT_PROCESS_ID, WAITING_WORK_COUNT},
    		elementNames={ELE_MODULE_NAME,ELE_PROCESS_ID,ELE_PROCESS_STATE,ELE_PROCESS_NAME,ELE_VERSION,ELE_START_TIME,ELE_COMPLETION_TIME,ELE_ORIGINATOR,ELE_PARENT_PROCESS_ID, ELE_WAITING_WORK_COUNT}
    		,parentElement="processInstance",grandElement="processInstances")
    TabularData listProcessInstances(String moduleName, String processName, String version) throws Throwable;


   /**
     * Retrieves information about starter operations, operations that can start a process instance.
     * TabularData contains a CompositeData object for each starter operation.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>             <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>moduleName</td>       <td>String</td>   <td>module name</td>
     * </tr> <tr>
     * <td>processName</td>      <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>namespace</td>        <td>String</td>   <td>namespace</td>
     * </tr> <tr>
     * <td>portType</td>         <td>String</td>   <td>port type name</td>
     * </tr> <tr>
     * <td>operationName</td>    <td>String</td>   <td>name of operation</td>
     * </tr>
     * </table>
     *
     * @param moduleName Optional, if specified restricts return to only starter operations with matching module name
     * @param processName Optional, if specified restricts return to only starter operations with matching process name
    * @return results in tabular data format
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME }, returnType=TABULARDATA_TYPE,operationName="Deprecated",
    		itemNames={MODULE_NAME, PROCESS_NAME, NAMESPACE, PORT_TYPE, OPERATION},
    		elementNames={ELE_MODULE_NAME,ELE_PROCESS_NAME,ELE_NAMESPACE,ELE_PORTTYPE,ELE_OPERATION},
    		parentElement="starterOperation",grandElement="starterOperations")
    @Deprecated
    TabularData listStarterOperations(String moduleName, String processName) throws Throwable;
    
    /**
     * Retrieves information about starter operations, operations that can start a process instance.
     * TabularData contains a CompositeData object for each starter operation.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>             <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>moduleName</td>       <td>String</td>   <td>module name</td>
     * </tr> <tr>
     * <td>processName</td>      <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>namespace</td>        <td>String</td>   <td>namespace</td>
     * </tr> <tr>
     * <td>portType</td>         <td>String</td>   <td>port type name</td>
     * </tr> <tr>
     * <td>operationName</td>    <td>String</td>   <td>name of operation</td>
     * </tr>
     * </table>
     *
     * @param moduleName Optional, if specified restricts return to only starter operations with matching module name
     * @param processName Optional, if specified restricts return to only starter operations with matching process name
     * @param version Optional, if specified restricts return to only starter operations with matching version
    * @return results in tabular data format
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=TABULARDATA_TYPE,operationName="listStarterOperations",
    		itemNames={MODULE_NAME, PROCESS_NAME, NAMESPACE, PORT_TYPE, OPERATION},
    		elementNames={ELE_MODULE_NAME,ELE_PROCESS_NAME,ELE_NAMESPACE,ELE_PORTTYPE,ELE_OPERATION},
    		parentElement="starterOperation",grandElement="starterOperations")
    TabularData listStarterOperations(String moduleName, String processName, String version) throws Throwable;

    /**
     * Get current information of a process instance. Includes current state information for each task and link.
     * @param processId Id of process instance.
     * @return process summary information
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID }, returnType=STRING_TYPE,operationName="getProcessInstanceSummary",
    		itemNames={},elementNames={ELE_SUMMARY})
    String getProcessInstanceSummary(String processId) throws Throwable;;

    /**
     * Suspends execution of the specified process instance
     * @param processId process instance identifier of process to suspend
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID }, returnType=VOID_TYPE,operationName="suspendProcessInstance",itemNames={},elementNames={ELE_SUCCESS})
    void suspendProcessInstance(String processId) throws Throwable;

    /**
     * Resumes execution of the specified process instance
     * @param processId process instance identifier of process to resume
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID }, returnType=VOID_TYPE,operationName="resumeProcessInstance",itemNames={},elementNames={ELE_SUCCESS})
    void resumeProcessInstance(String processId) throws Throwable;

  /**
     * Cancels execution of the specified process instance
     * @param processId process instance identifier of process to cancel
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID }, returnType=VOID_TYPE,operationName="cancelProcessInstance",itemNames={},elementNames={ELE_SUCCESS})
    void cancelProcessInstance(String processId) throws Throwable;
    
    /**
     * Suspends execution of the process instances that belong to the specified template.
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @return number of process instances suspended 
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=INT_TYPE,operationName="Deprecated",itemNames={},elementNames={ELE_ITEM_COUNT})
    @Deprecated
    int suspendProcessInstances(String moduleName, String processName) throws Throwable;
    
    /**
     * Suspends execution of the process instances that belong to the specified template.
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @param version Optional, if specified restricts return to only process instances with matching version
     * @return number of process instances suspended 
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=INT_TYPE,operationName="suspendProcessInstances",itemNames={},elementNames={ELE_ITEM_COUNT})
    int suspendProcessInstances(String moduleName, String processName, String version) throws Throwable;

    /**
     * Resumes execution of the process instances that belong to the specified template.
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @return number of process instances resumed 
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=INT_TYPE,operationName="Deprecated",itemNames={},elementNames={ELE_ITEM_COUNT})
    @Deprecated
    int resumeProcessInstances(String moduleName, String processName) throws Throwable;
    
    /**
     * Resumes execution of the process instances that belong to the specified template.
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @param version Optional, if specified restricts return to only process instances with matching version
     * @return number of process instances resumed 
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=INT_TYPE,operationName="resumeProcessInstances",itemNames={},elementNames={ELE_ITEM_COUNT})
    int resumeProcessInstances(String moduleName, String processName, String version) throws Throwable;

  /**
     * Cancels execution of the process instances that belong to the specified template.
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @return number of process instances cancelled 
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType = INT_TYPE, operationName = "Deprecated", itemNames = {}, 
    		elementNames = { ELE_ITEM_COUNT })
    @Deprecated
    int cancelProcessInstances(String moduleName, String processName) throws Throwable;
    
    /**
     * Cancels execution of the process instances that belong to the specified template.
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @param version Optional, if specified restricts return to only process instances with matching version
     * @return number of process instances cancelled 
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType = INT_TYPE, operationName = "cancelProcessInstances", itemNames = {}, 
    		elementNames = { ELE_ITEM_COUNT })
    int cancelProcessInstances(String moduleName, String processName, String version) throws Throwable;

    /**
     * Purges process instances that belong to the specified template.
     * Only process instances that are finished, in the COMPLETED, CANCELLED or FAILED state, will be purged.
     * @param moduleName Optional, if specified restricts purge to only process instances with matching module name
     * @param processName Optional, if specified restricts purge to only process instances with matching process name
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME }, returnType=INT_TYPE,operationName="Deprecated",
    		itemNames={},elementNames = { ELE_ITEM_COUNT })
    @Deprecated
    void purgeProcessInstances(String moduleName, String processName) throws Throwable;
    
    /**
     * Purges process instances that belong to the specified template.
     * Only process instances that are finished, in the COMPLETED, CANCELLED or FAILED state, will be purged.
     * @param moduleName Optional, if specified restricts purge to only process instances with matching module name
     * @param processName Optional, if specified restricts purge to only process instances with matching process name
     * @param version Optional, if specified restricts purge to only process instances with matching version
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=INT_TYPE,operationName="purgeProcessInstances",
    		itemNames={},elementNames = { ELE_ITEM_COUNT })
    int purgeProcessInstances(String moduleName, String processName, String version) throws Throwable;
    

    /**
     * Undeploys the module.
     * Will delete the module and process templates and completed process instances.
     * This operation will not suceed if there are uncompleted process instances.
     * <b>WARNING - USE WITH CAUTION.</b>
     * This is not a substitue for the platform provided undeploy mechanism.
     * This can be used if the platform provided mechanism failed or if there is no platform provided mechanism.
     * @param moduleName name of module to undeploy
     * @param version module version to undeploy
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=VOID_TYPE,operationName="undeploy",itemNames={},elementNames={})
    void undeploy(String moduleName, String version) throws Throwable;

    /**
     * Get process template attributes.
     * TabularData contains a CompositeData object for each process template.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>           <td>TYPE</td>    <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>attributeName</td>  <td>String</td>  <td>atrribute name</td>
     * </tr> <tr>
     * <td>attributeType</td>  <td>String</td>  <td>attribute type</td>
     * </tr>
     * </table>
     *
     * @param type  0=all attributes (default), 1=filterable, 2=sortable, 3=displayable
     * @return results in tabular data format
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_TYPE }, returnType=TABULARDATA_TYPE,
    		operationName="listProcessTemplateAttributes",itemNames={NAME, TYPE},
    		elementNames={ELE_NAME,ELE_TYPE},
    		parentElement="templateAttribute",grandElement="templateAttributes")
    TabularData listProcessTemplateAttributes(int type);
    
    /**
     * Get process instance attributes.
     * TabularData contains a CompositeData object for each process instance.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>            <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>moduleName</td>      <td>String</td>   <td>module name</td>
     * </tr> <tr>
     * <td>processName</td>     <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>attributeName</td>   <td>String</td>   <td>atrribute name</td>
     * </tr> <tr>
     * <td>attributeType</td>   <td>String</td>   <td>attribute type</td>
     * </tr>
     * </table>
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @param type  0=all attributes (default), 1=filterable, 2=sortable, 3=displayable
     * @return results in tabular data format
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, ELE_TYPE}, returnType=TABULARDATA_TYPE,
    		operationName="Deprecated",itemNames={MODULE_NAME, PROCESS_NAME, NAME, TYPE},
    		elementNames={ELE_MODULE_NAME,ELE_PROCESS_NAME,ELE_NAME,ELE_TYPE},
    		parentElement="instanceAttributes",grandElement="instanceAttributes")
    @Deprecated
    TabularData listProcessInstanceAttributes(String moduleName, String processName, int type) throws Throwable;
 
    /**
     * Get process instance attributes.
     * TabularData contains a CompositeData object for each process instance.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>            <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>moduleName</td>      <td>String</td>   <td>module name</td>
     * </tr> <tr>
     * <td>processName</td>     <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>attributeName</td>   <td>String</td>   <td>atrribute name</td>
     * </tr> <tr>
     * <td>attributeType</td>   <td>String</td>   <td>attribute type</td>
     * </tr>
     * </table>
     * @param moduleName Optional, if specified restricts return to only process instances with matching module name
     * @param processName Optional, if specified restricts return to only process instances with matching process name
     * @param version Optional, if specified restricts return to only process instances with matching version
     * @param type  0=all attributes (default), 1=filterable, 2=sortable, 3=displayable
     * @return results in tabular data format
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION, ELE_TYPE}, returnType=TABULARDATA_TYPE,
    		operationName="listProcessInstanceAttributes",itemNames={MODULE_NAME, PROCESS_NAME, NAME, TYPE},
    		elementNames={ELE_MODULE_NAME,ELE_PROCESS_NAME,ELE_NAME,ELE_TYPE},
    		parentElement="instanceAttributes",grandElement="instanceAttributes")
    TabularData listProcessInstanceAttributes(String moduleName, String processName, String version, int type) throws Throwable;

    /**
     * Queries information about deployed process templates.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>                <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>moduleName</td>          <td>String</td>   <td>module name</td>
     * </tr> <tr>
     * <td>processName</td>         <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>processDescription</td>  <td>String</td>   <td>process template description</td>
     * </tr> <tr>
     * <td>version</td>             <td>String</td>  <td>process template version</td>
     * </tr>
     * </table>
     *
     * @param query	Optional, if not specified, returns all process templates 
     * @param pageSize	page size 
     * @return CompositeData[]
     */
    /**
     * this operation will be processed individually
     * so the itemNames and elementNames are not necessary
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_QUERY,ELE_PAGESIZE}, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryProcessTemplates",
    		itemNames={},elementNames={},
			parentElement="processTemplate",grandElement="processTemplates")
    CompositeData[] queryProcessTemplates(String query, int pageSize) throws Throwable;
    
    /**
     * Queries information about deployed process templates.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>                <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>moduleName</td>          <td>String</td>   <td>module name</td>
     * </tr> <tr>
     * <td>processName</td>         <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>processDescription</td>  <td>String</td>   <td>process template description</td>
     * </tr> <tr>
     * <td>version</td>             <td>String</td>  <td>process template version</td>
     * </tr>
     * </table>
     *
     * @param select	selection part of the query
     * @param where		filter part of the query
     * @param orderBy	order by part of the query
     * @param pageSize	page size 
     * @return CompositeData[]
     */
    /**
     * this operation will be processed individually
     * so the itemNames and elementNames are not necessary
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_SELECT,ELE_WHERE,ELE_ORDERBY,ELE_PAGESIZE}, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryProcessTemplatesAlt",
    		itemNames={},elementNames={},
			parentElement="processTemplate",grandElement="processTemplates")
    CompositeData[] queryProcessTemplates(String select, String where, String orderBy, int pageSize) throws Throwable;
    
    /**
     * Queries information about deployed process instances.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>        <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>processId</td>   <td>String</td>   <td>process instance identifier</td>
     * </tr> <tr>
     * <td>name</td>        <td>String</td>   <td>process instance attribute name</td>
     * </tr> <tr>
     * <td>value</td>       <td>String</td>   <td>process instance attribute value</td>
     * </tr>
     * </table>
     *
     * @param query	Optional, if not specified, returns all process instances 
     * @param pageSize	page size 
     * @param attributeTypeMap	map of attribute name/attribute type pairing 
     * @return CompositeData[]
     */
    /**
     * this operation will be processed individually in process management
     * the fixed will be processed first, then the custom attributes will 
     * be processed.
     * So the itemNames and elementNames are not necessary.
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_QUERY,ELE_PAGESIZE, ELE_MAP }, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryProcessInstances",
    		itemNames={},	elementNames={},
    		parentElement="processInstance",grandElement="processInstances")
    CompositeData[] queryProcessInstances(String query, int pageSize, Map<String, String> attributeTypeMap) throws Throwable;
 
    /**
     * Queries information about deployed process instances.
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>             <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>processId</td>   <td>String</td>   <td>process instance identifier</td>
     * </tr> <tr>
     * <td>name</td>        <td>String</td>   <td>process instance attribute name</td>
     * </tr> <tr>
     * <td>value</td>       <td>String</td>   <td>process instance attribute value</td>
     * </tr>
     * </table>
     *
     * @param select	selection part of the query
     * @param where	filter part of the query
     * @param orderBy	order by part of the query
     * @param pageSize	page size
     * @param attributeTypeMap	map of attribute name/attribute type pairing 
     * @return CompositeData[]
     */
    /**
     * this operation will be processed individually in process management
     * the fixed will be processed first, then the custom attributes will 
     * be processed.
     * So the itemNames and elementNames are not necessary.
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_SELECT,ELE_WHERE,ELE_ORDERBY,ELE_PAGESIZE, ELE_MAP }, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryProcessInstancesAlt",
    		itemNames={},elementNames={},
    		parentElement="processInstance",grandElement="processInstances")
    CompositeData[] queryProcessInstances(String select, String where, String orderBy, int pageSize, Map<String, String> attributeTypeMap) throws Throwable;
  
    /**
     * Return the next page/set of data of either <b>queryProcessInstances</b> or <b>queryProcessTemplates</b>.
     * The CompositeData matches the format of the first page.
     *
     * @param id	this refers to the query ID returned from queryProcessInstances or queryProcessTemplates when using pagination
     * @return CompositeData[]
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PAGING_ID }, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryNextPage",
    		itemNames={},elementNames={},
    		parentElement="processInstance",grandElement="processInstances")
    /**
     * this operation will be processed individually in process management
     * the fixed will be processed first, then the custom attributes will 
     * be processed.
     * So the itemNames and elementNames are not necessary.
     */
    CompositeData[] queryNextPage(int id) throws Throwable ;
 
    /**
     * Return the previous page/set of data of either <b>queryProcessInstances</b> or <b>queryProcessTemplates</b>.
     * The CompositeData matches the format of the first page.
     *
     * @param id	this refers to the query ID returned from queryProcessInstances or queryProcessTemplates when using pagination
     * @return CompositeData[]
     */
    /**
     * this operation will be processed individually in process management
     * the fixed will be processed first, then the custom attributes will 
     * be processed.
     * So the itemNames and elementNames are not necessary.
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PAGING_ID}, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryPreviousPage",
    		itemNames={},elementNames={},
    		parentElement="processInstance",grandElement="processInstances")
    CompositeData[] queryPreviousPage(int id) throws Throwable;
    
    /**
     * Return the first page/set of data of either <b>queryProcessInstances</b> or <b>queryProcessTemplates</b>.
     * The CompositeData matches the format of the first page.
     *
     * @param id	this refers to the query ID returned from queryProcessInstances or queryProcessTemplates when using pagination
     * @return CompositeData[]
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PAGING_ID}, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryFirstPage",
    		itemNames={},elementNames={},
    		parentElement="processInstance",grandElement="processInstances")
    CompositeData[] queryFirstPage(int id) throws Throwable;
    
    /**
     * Return the last page/set of data of either <b>queryProcessInstances</b> or <b>queryProcessTemplates</b>.
     * The CompositeData matches the format of the first page.
     *
     * @param id	this refers to the query ID returned from queryProcessInstances or queryProcessTemplates when using pagination
     * @return CompositeData[]
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PAGING_ID}, returnType=COMPOSITEDATA_ARRAY_TYPE,operationName="queryLastPage",
    		itemNames={},elementNames={},
    		parentElement="processInstance",grandElement="processInstances")
    CompositeData[] queryLastPage(int id) throws Throwable;
    
    

    /**
     * Release resources associated with the given query ID. Call this method 
     * when a query is no longer in use.
	 * The engine will also automatically release resources for any query that has been in the system for over half an hour.
	 * This method only applies to pagination.
     *
     * @param id	this refers to the query ID returned from queryProcessInstances or queryProcessTemplates when using pagination
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PAGING_ID }, returnType=VOID_TYPE,operationName="queryDone",itemNames={},elementNames={ELE_SUCCESS})
    void queryDone(int id) throws Throwable; 

    /**
     * Returnns the number of process template records that meet the query.
     *
     * @param query		the query statement
     * @return 			the number of records that meet the query
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_QUERY_STRING}, returnType=INT_TYPE,operationName="queryProcessTemplateCount",
    		itemNames={},elementNames={ELE_ITEM_COUNT})
    int queryProcessTemplateCount(String query) throws Throwable;
    
    /**
     * Returnns the number of process instance records that meet the query.
     *
     * @param query		the query statement
     * @return 			the number of records that meet the query
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_QUERY_STRING }, returnType=INT_TYPE,operationName="queryProcessInstanceCount",
    		itemNames={},elementNames={ELE_ITEM_COUNT})
    int queryProcessInstanceCount(String query) throws Throwable ;

    /**
     * Retrieves status information about the specified process instance
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>               <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>processId</td>          <td>String</td>   <td>process instance id</td>
     * </tr> <tr>
     * <td>processName</td>        <td>String</td>   <td>process template name</td>
     * </tr> <tr>
     * <td>processDescription</td> <td>String</td>   <td>process template description</td>
     * </tr> <tr>
     * <td>processState</td>       <td>String</td>   <td>process instance state</td>
     * </tr> <tr>
     * <td>priority</td>           <td>Short</td>    <td>priority</td>   //todo make this a string
     * </tr> <tr>
     * <td>startTime</td>          <td>String</td>   <td>instance start time</td>
     * </tr> <tr>
     * <td>originator</td>         <td>String</td>   <td>process instance originator</td>
     * </tr>
     * </table>
     *
     * @param processId
     * @return
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID}, returnType=COMPOSITEDATA_TYPE,operationName="getProcessInstanceStatus",
    		itemNames={PROCESS_ID, PROCESS_NAME, PROCESS_DESCRIPTION, PROCESS_STATE, PRIORITY, START_TIME, ORIGINATOR},
    		elementNames={ELE_PROCESS_ID,ELE_PROCESS_NAME,ELE_PROCESS_DESCRIPTION,ELE_PROCESS_STATE,ELE_PRIORITY,ELE_START_TIME,ELE_ORIGINATOR},
    		parentElement="processInstanceStatus")
    CompositeData getProcessInstanceStatus(String processId) throws Throwable;

   /**
     * Retrieves status information about the specified activity instance
     * The CompositeData contains the following items:
     * <table cellspacing="12">
     * <tr>
     * <td>NAME</td>               <td>TYPE</td>     <td>DESCRIPTION</td>
     * </tr> <tr>
     * <td>activityId</td>         <td>String</td>   <td>activity instance id</td>
     * </tr> <tr>
     * <td>activityName</td>       <td>String</td>   <td>activity name</td>
     * </tr> <tr>
     * <td>activityState</td>      <td>String</td>   <td>activity instance state</td>
     * </tr> <tr>
     * <td>startTime</td>          <td>String</td>   <td>instance start time</td>
     * </tr> <tr>
     * <td>completionTime</td>     <td>String</td>   <td>instance completion time</td>
     * </tr> <tr>
     * <td>deadline</td>           <td>String</td>   <td>deadline expiration time</td>
     * </tr>
     * </table>
     * todo 	Completer *, Participant *, Work item id *
     * @param processId
     * @param activityName
     * @return
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID, ACTIVITY_NAME }, returnType=COMPOSITEDATA_TYPE,operationName="getActivityInstanceStatus",
    		itemNames={ACTIVITY_ID, ACTIVITY_NAME, ACTIVITY_STATE, START_TIME, COMPLETION_TIME, DEADLINE},
    		elementNames={ELE_ACTIVITY_ID,ELE_ACTIVITY_NAME,ELE_ACTIVITY_STATE,ELE_START_TIME,ELE_COMPLETION_TIME,ELE_DEADLINE_TIME},
    		parentElement="activityInstanceStatus")
    CompositeData getActivityInstanceStatus(String processId, String activityName) throws Throwable;

    /**
     * Set process instance priority
     * @param processId Id of process instance to set
     * @param priority new priority = 100(LOW) | 200(NORMAL) | 300(HIGH) | 400 (EXCEPTIONAL)
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID, PRIORITY }, returnType=VOID_TYPE,operationName="setPriority",itemNames={},elementNames={ELE_SUCCESS})
    void setPriority(String processId, short priority) throws Throwable;

    /**
     * Gets value of a parameter (or data field or any other variable/attribute) defined at the process level
     * @param processId Id of process instance
     * @param parameterName Name of parameter to get
     * @return
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_PROCESS_ID, ELE_PARAMETER_NAME }, returnType=OBJECT_TYPE,operationName="getParameterValue",itemNames={},elementNames={ELE_PARAMETER_VALUE})
    Object getParameterValue(String processId, String parameterName) throws Throwable;

    /**
     * Changes the value of the deadline expiration time.
     * The expiration time may be specified as an offset to the current time in the format of an xs duration.
     *       "PnYnMnDTnHnMnS"
     * or as a data time value of the form
     *    "YYYY-MM-DDThh:mm:ss" with or without UTC format 'Z' or offset '+/- hh.mm"
     * @param activityId  Id of activity to set
     * @param expirationTime  New expiration time
     */
    @BxManagerMBeanAnnotation(inputNames = {ACTIVITY_ID,ELE_EXPIRATION_TIME }, returnType=VOID_TYPE,operationName="setDeadlineExpiration",itemNames={},elementNames={ELE_SUCCESS})
    void setDeadlineExpiration(String activityId, String expirationTime) throws Throwable;
    
    /**
     * get the information of mbean operation. Only works for the non-message starters currently.
     * @param moduleName: the module name
     * @param namespace: namespace
     * @param portType: porttype
     * @param operationName: operation name
     * @return
     * @throws Throwable
     */
    /**
     * this method will be processed individually in process management service
     * the response structure will be like this:
     * 		<operationInfo>
     * 			<operationName>operationName</operationName>
     * 			<parameters>
     * 				<parameter/>
     * 			</parameters>
     * 		</operationInfo>
     */
    /* *************** HAD TO REMOVE AS IT CONFLICTS WITH NEW SIGNATURE BELOW **************
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION, ELE_OPERATION}, returnType = TABULARDATA_TYPE, operationName = "Deprecated", itemNames = {
			OPERATION, NAME, TYPE }, elementNames = { ELE_OPERATION, ELE_NAME,
			ELE_TYPE }, parentElement = "parameters", grandElement = "operationInfo")
	@Deprecated
    TabularData getStarterOperationInfo(String moduleName,String namespace, String portType,
			String operationName) throws Throwable;
	*/

    /**
     * get the information of mbean operation. Only works for the non-message starters currently.
     * @param moduleName: the module name
     * @param processName: the process name
     * @param version: version string
     * @param operationName: operation name
     * @return
     * @throws Throwable
     */
    /**
     * this method will be processed individually in process management service
     * the response structure will be like this:
     * 		<operationInfo>
     * 			<operationName>operationName</operationName>
     * 			<parameters>
     * 				<parameter/>
     * 			</parameters>
     * 		</operationInfo>
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME, ELE_NAME, VERSION, ELE_OPERATION}, returnType = TABULARDATA_TYPE, operationName = "getStarterOperationInfo", itemNames = {
			OPERATION, NAME, TYPE }, elementNames = { ELE_OPERATION, ELE_NAME,
			ELE_TYPE }, parentElement = "parameters", grandElement = "operationInfo")
    TabularData getStarterOperationInfo(String moduleName, String processName, String version, String operationName) throws Throwable;
    
    /**
     * create a process instance
     * @param moduleName: module name
     * @param operationName: an operation of this module
     * @param parametersMap: the parameters of this operation
     * @param version: version string
     * @return
     * @throws Throwable
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION }, returnType=STRING_TYPE,operationName="Deprecated",itemNames={},elementNames={ELE_PROCESS_ID})
    @Deprecated
    String createProcessInstance(String moduleName,String operationName,Map<String, String> parametersMap) throws Throwable;
    
    /**
     * create a process instance
     * @param moduleName: module name
     * @param operationName: an operation of this module
     * @param parametersMap: the parameters of this operation
     * @param version: version string
     * @return
     * @throws Throwable
     */
    @BxManagerMBeanAnnotation(inputNames = {ELE_MODULE_NAME,ELE_NAME, VERSION, ELE_OPERATION_NAME, ELE_PMAP}, returnType=STRING_TYPE,operationName="createProcessInstance",itemNames={},elementNames={ELE_PROCESS_ID})
    String createProcessInstance(String moduleName,String processName, String version, String operationName,Map<String, String> parametersMap) throws Throwable;
    
}
