/**
 * RefactorAsIndiSubprocWizardInfo.java
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
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdl2.Process;

/**
 * RefactorAsIndiSubprocWizardInfo
 * 
 * Information passed between Refactor as independent subproc command and its
 * wizard.
 */
public class RefactorAsIndiSubprocWizardInfo extends
        RefactorAsSubProcWizardInfo {

    /**
     * 
     */
    public RefactorAsIndiSubprocWizardInfo(
            DiagramModelImageProvider imageProvider, String defaultProcessName) {
        super();
        this.imageProvider = imageProvider;
        this.subprocName = defaultProcessName;
    }

    /**
     * List of participants used in the selected activities, mapped to a boolean
     * stating whether they should be MOVED to new sub-process or duplicated in
     * new sub-process.
     */
    public HashSet<RefactorReferencedParticipantInfo> referencedParticipants =
            new HashSet<RefactorReferencedParticipantInfo>();

    /**
     * List of data fields used in the selected activities and the transitions
     * between them mapped to a boolean stating whether they should be MOVED to
     * new sub-process or duplicated in new sub-process.
     */
    public HashSet<RefactorReferencedDataFieldInfo> referencedDataFields =
            new HashSet<RefactorReferencedDataFieldInfo>();

    /**
     * Cached information about Sep of duties and retain familiar task groups
     */
    public List<TaskGroupReferenceInfo> taskGroups =
            new ArrayList<TaskGroupReferenceInfo>();

    /**
     * Information about resource pattern task groups referenced in selection
     * being refactored.
     * 
     * @author aallway
     * @since 3.4.2 (7 Sep 2010)
     */
    public static class TaskGroupReferenceInfo {
        /**
         * 
         * Either {@link SeparationOfDutiesActivities} or
         * {@link RetainFamiliarActivities}
         */
        public EObject taskGroup;

        public Process parentProcess;

        /**
         * Is the task group referenced in activities being refactored
         */
        public boolean isReferenced = false;

        /**
         * User has confirmed in wizard that it is ok to split the group.
         */
        public boolean isOkToRemove = false;

        /**
         * Id's of activities in task group that are _not_ selected for
         * refactor.
         */
        public Set<String> unselectedActivitiesInGroup = new HashSet<String>();

        /**
         * Id's of activities in task group that _are_ selected for refactor.
         */
        public Set<String> selectedActivitiesInGroup = new HashSet<String>();

        public TaskGroupReferenceInfo(EObject taskGroup, Process parentProcess) {
            super();
            this.taskGroup = taskGroup;
            this.parentProcess = parentProcess;
        }

    }

}
