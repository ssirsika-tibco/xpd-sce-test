/**
 * RefactorAsSubProcWizardInfo.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Process;

/**
 * RefactorAsSubProcWizardInfo
 * 
 * Refactor information and data class. The refactor command performs the
 * validation and creates a RefactorAsSubProcWizardInfo to pass on various
 * information to the wizard.
 * 
 */
public class RefactorAsSubProcWizardInfo {

    /** Refactor Validation found multiple potential entry points. */
    public static final int SUBPROC_MULTIPLE_ENTRYPATHS = 0x0001;

    /** Refactor Validation found multiple potential exit points. */
    public static final int SUBPROC_MULTIPLE_EXITPATHS = 0x0002;

    /**
     * From Command -> Wizard - Select 'create start event' by default
     * 
     * From Wizard -> Command 'Create Start Event'
     */
    public static final int SUBPROC_CREATE_STARTEVENT = 0x0004;

    /**
     * From Command -> Wizard - Select 'create end events' by default.
     * 
     * From Wizard -> Command 'Create End Events'
     */
    public static final int SUBPROC_CREATE_ENDEVENT = 0x0008;

    /**
     * Selection already has a start event.
     */
    public static final int SUBPROC_EXISTING_STARTEVENT = 0x0010;

    /**
     * Selection already has an end event.
     */
    public static final int SUBPROC_EXISTING_ENDEVENT = 0x0020;

    /**
     * Make new embedded sub-process a transaction.
     */
    public static final int SUBPROC_IS_TRANSACTION = 0x0040;

    /**
     * INDI Sub-Proc can't have implementing events
     */
    public static final int SUBPROC_INDI_HAS_IMPLEMENTING_EVENTS = 0x0080;

    /**
     * INDI Sub-Proc - user tasks in selection break a separatin of duties
     * group.
     */
    public static final int SUBPROC_INDI_BREAKS_SEP_OF_DUTY_GROUP = 0x0100;

    /**
     * INDI Sub-Proc - user tasks in selection break a separatin of duties
     * group.
     */
    public static final int SUBPROC_INDI_BREAKS_RETAIN_FAMILIAR_GROUP = 0x0200;

    /**
     * Flag to say Refactor does not need Insert Start Event option
     */
    public static final int SUBPROC_HIDE_INSERT_STARTEVENT_OPTION = 0x0300;

    /**
     * Flag to say Refactor does not need Insert End Event option
     */
    public static final int SUBPROC_HIDE_INSERT_ENDEVENT_OPTION = 0x0400;

    /**
     * Flag to say Refactor does not SubProcess Is Transaction option
     */
    public static final int SUBPROC_HIDE_SUBPROC_IS_TRANSACTION_OPTION = 0x0500;

    /**
     * Refactor Validation Info passed from/back to refactor command.
     */
    public int validationInfo = 0;

    /**
     * Return for user selected new independent sub-process name
     * */
    public String subprocName = TaskType.EMBEDDED_SUBPROCESS_LITERAL.toString();

    /**
     * List of selected objects
     */
    public List<EObject> selectedObjects = new ArrayList<EObject>();

    /**
     * Source process for refactored objects.
     */
    public Process sourceProcess;

    /**
     * List of offending objects and transitions for multiple possible entry
     * paths.
     */
    public HashSet<EObject> entryPathActsAndTrans = new HashSet<EObject>();

    /**
     * List of offending objects and transitions for multiple possible exit.
     */
    public HashSet<EObject> exitPathActsAndTrans = new HashSet<EObject>();

    /**
     * Diagram image provider (allows get of image of selected objects in
     * diagram).
     */
    public DiagramModelImageProvider imageProvider = null;
}
