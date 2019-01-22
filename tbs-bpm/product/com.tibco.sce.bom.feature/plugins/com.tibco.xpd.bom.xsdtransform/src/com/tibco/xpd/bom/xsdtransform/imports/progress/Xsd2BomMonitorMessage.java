/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.imports.progress;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * This class provides progress monitor messaging for the Xsd2Bom.ext WSDL/XSD
 * -> BOM transformation.
 * 
 * @author aallway
 * @since 4 Apr 2011
 */
public class Xsd2BomMonitorMessage {

    private IProgressMonitor parentMonitor = null;

    private SubProgressMonitor transformMonitor = null;

    private String topLevelSourceInTransform = null;

    private long startTime;

    public Xsd2BomMonitorMessage() {
        startTime = System.currentTimeMillis();
    }

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
    public void setTopLevelSourceInTransform(String topLevelSourceInTransform) {
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
                    getXsd2BomProgressText((topLevelSourceInTransform != null ? topLevelSourceInTransform
                            : "???") + //$NON-NLS-1$
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
    public static String getXsd2BomProgressText(String subMessage,
            Object... args) {
        if (subMessage != null && subMessage.length() != 0) {
            /*
             * Escape %'s in the subMessage else we get excpetions from the
             * String.format.
             */
            subMessage = subMessage.replace("%", "%%");

            return Messages.Xsd2BomMonitorMessage_BuildingBomsFromWsdlsXsds_message
                    + " :: " + String.format(subMessage, args) + "..."; //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            return Messages.Xsd2BomMonitorMessage_BuildingBomsFromWsdlsXsds_message
                    + "..."; //$NON-NLS-1$
        }
    }

    /**
     * Output to system console (can be turned off/on)
     * 
     * @param msg
     */
    public void sysout(String msg) {
        // System.out.println(((System.currentTimeMillis() - startTime) /
        // 1000.0f)
        // + ": " + Xsd2BomMonitorMessage.class.getSimpleName()
        //                + ": " + msg); //$NON-NLS-1$
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

    public void subTaskNormalisingAndFixingNames() {
        subTask(Messages.Xsd2BomMonitorMessage_NormalisingDataNames_message);
    }

    public void subTaskCleanUpAndFixTargetNamespaces() {
        subTask(Messages.Xsd2BomMonitorMessage_CleaningUpAndFIxingNamespaces_message);
    }

    public void subTaskCreateBOMForSchema(String schemaName) {
        subTask(Messages.Xsd2BomMonitorMessage_CreatingBOMForSchema_message
                + schemaName);
    }

}
