/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.export.progress;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * This class provides progress monitor messaging for the Bom2Xsd.ext BOM ->
 * XSD/WSDL transformation.
 * 
 * @author aallway
 * @since 1 Apr 2011
 */
public class Bom2XsdMonitorMessages {
    private IProgressMonitor parentMonitor = null;

    private SubProgressMonitor transformMonitor = null;

    private IFile topLevelSourceInTransform = null;

    /**
     * @param parentMonitor
     *            the parentMonitor to set
     */
    public void setParentMonitor(IProgressMonitor parentMonitor) {
        this.parentMonitor = parentMonitor;
    }

    /**
     * @param topLevelSourceInTransform
     *            the topLevelSourceInTransform to set
     */
    public void setTopLevelSourceInTransform(IFile topLevelSourceInTransform) {
        this.topLevelSourceInTransform = topLevelSourceInTransform;
    }

    /**
     * Create the transform sub-task monitor.
     * 
     * @param numTransformTasks
     *            Number of operations to perform and 'tick off' during the
     *            transform.
     */
    public void createTransformMonitor(int numTransformTasks) {
        if (parentMonitor != null) {
            transformMonitor =
                    new SubProgressMonitor(parentMonitor, numTransformTasks);
            transformMonitor.beginTask("", numTransformTasks); //$NON-NLS-1$
        }
    }

    /**
     * Tick off transform sub-task one it has finished.
     * <p>
     * It is only necessary to call this ONCE for each of the main sub-tasks
     * (i.e. the sub-tasks counted in the numTransformTasks given to
     * {@link #createTransformMonitor(int)}.
     * <p>
     * It is not necessary to call this for every call to
     * {@link #subTask(String)} if that is used more than once per task.
     */
    public void subTaskDone() {
        if (transformMonitor != null) {
            transformMonitor.worked(1);
        }
    }

    /**
     * Tag the transform monitor as complete.
     */
    public void transformDone() {
        if (transformMonitor != null) {
            transformMonitor.done();
        }
    }

    /**
     * Display the given vanilla sub-task string
     * 
     * @param s
     */
    private void subTask(String s) {
        if (transformMonitor != null) {
            String bom2XsdProgressText =
                    getBom2XsdProgressText((topLevelSourceInTransform != null ? topLevelSourceInTransform
                            .getName() : "???") + //$NON-NLS-1$
                            " :: " + s); //$NON-NLS-1$
            sysout(bom2XsdProgressText);
            transformMonitor.subTask(bom2XsdProgressText);

        }
        return;
    }

    /**
     * Build a string for the BOM2XSD progress metre.
     * <p>
     * The string will always be led with "Building XSDs from Business Objects".
     * <p>
     * If the subMessage is non <code>null</code>(and optionally args to that)
     * then it is appended to the leader above.
     * <p>
     * The subMessage is used as a String.format() string in conjunction with
     * the 0 or more args.
     * 
     * @param subMessage
     *            String.format() formatted string or <code>null</code> if no
     *            subMessage is to be appended to leader.
     * @param args
     *            Zero or more arguments to teh subMessage string.
     * 
     * @return
     */
    public static String getBom2XsdProgressText(String subMessage,
            Object... args) {
        if (subMessage != null && subMessage.length() != 0) {
            /*
             * Escape %'s in the subMessage else we get excpetions from the
             * String.format.
             */
            subMessage = subMessage.replace("%", "%%");

            return Messages.Bom2XsdMonitorMessages_BuildXsdsFromBomsLeader_message
                    + " :: " + String.format(subMessage, args) + "..."; //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            return Messages.Bom2XsdMonitorMessages_BuildXsdsFromBomsLeader_message
                    + "..."; //$NON-NLS-1$
        }
    }

    /**
     * Output to system console (can be turned off/on)
     * 
     * @param msg
     */
    private void sysout(String msg) {
        //        System.out.println(this.getClass().getSimpleName() + ": " + msg); //$NON-NLS-1$
        return;
    }

    /**
     * ===================================================================
     * 
     * These are the methods called from Bom2Xsd.ext (so that appropriate
     * internationalisation can be done.
     * 
     * ===================================================================
     */

    public void subTaskCollectModelAndRefs() {
        subTask(Messages.Bom2XsdMonitorMessages_CollectModelAndRef_message);
    }

    public void subTaskCreateSkeletonSchemas() {
        subTask(Messages.Bom2XsdMonitorMessages_CreateSchemas_message);
    }

    public void subTaskCreateSkeletonDataTypes() {
        subTask(Messages.Bom2XsdMonitorMessages_CreateDataTypes_message);
    }

    public void subTaskProcessSimpleDataTypes() {
        subTask(Messages.Bom2XsdMonitorMessages_ProcessSimpleTypes_message);
    }

    public void subTaskProcessTypeHierarchy() {
        subTask(Messages.Bom2XsdMonitorMessages_ProcessInheritance_message);
    }

    public void subTaskProcessComplexDataTypes() {
        subTask(Messages.Bom2XsdMonitorMessages_ProcessDataTypes_message);
    }

    public void subTaskProcessTopLevelElements() {
        subTask(Messages.Bom2XsdMonitorMessages_ProcessTopLevelElements_message);
    }

    public void subTaskCleanAnonymousTypes() {
        subTask(Messages.Bom2XsdMonitorMessages_CleanAnonymousTypes_message);
    }

    public void subTaskProcessDataType(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_ProcessComplexType_message
                + name);
    }

    public void subTaskCreateSkeletonSchema(String namespace) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateSchema_message
                + namespace);
    }

    public void subTaskCreateDataType(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateDataType_message + name);
    }

    public void subTaskCreateGroup(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateGroup_message + name);
    }

    public void subTaskCreateAttributeGroup(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateAttributeGroup_message
                + name);
    }

    public void subTaskCreateSimpleContent(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateSimpleContent_message
                + name);
    }

    public void subTaskCreateSimpleType(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateSimpleType_message + name);
    }

    public void subTaskProcessTopLevelAttributesForPrimitive(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateAttrForPrim_message
                + name);
    }

    public void subTaskProcessTopLevelElementsForPrimitive(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateElemForPrim_message
                + name);
    }

    public void subTaskProcessTopLevelAttributesForEnum(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateAttrForEnum_message
                + name);
    }

    public void subTaskProcessTopLevelElementsForEnum(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateElemForEnum_message
                + name);
    }

    public void subTaskProcessTopLevelElementsForClass(String name) {
        subTask(Messages.Bom2XsdMonitorMessages_CreateElemForClass_message
                + name);
    }

}
