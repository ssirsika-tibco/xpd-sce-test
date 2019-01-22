/**
 * Copyright TIBCO inc (c) 2007
 */
package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * On mouse-over, shows feedback lines to /from reference tasks
 * 
 * @author aallway
 * 
 */
public class TaskRefHighlightPolicy extends HighlightReferencesPolicy {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.policies.HighlightReferencesPolicy#getConnectionAnchor(org.eclipse.gef.GraphicalEditPart)
     */
    @Override
    protected ConnectionAnchor getConnectionAnchor(GraphicalEditPart editPart) {
        return new ChopboxAnchor(editPart.getFigure());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.policies.HighlightReferencesPolicy#getReferenceToObjects()
     */
    @Override
    protected Collection<GraphicalEditPart> getReferenceToObjects() {
        // Only references to other objects are from the source reference task
        // to the task it references.
        TaskEditPart hostTaskEditPart = (TaskEditPart) getHost();

        TaskAdapter taskAdp = hostTaskEditPart.getActivityAdapter();

        Collection<GraphicalEditPart> refTasks = new ArrayList<GraphicalEditPart>(); 

        if (TaskType.REFERENCE_LITERAL.equals(taskAdp.getTaskType())) {

            String refdTaskId = taskAdp.getReferencedTask();
            if (refdTaskId != null && refdTaskId.length() > 0) {
                // Get a list of all error events in same process.
                Collection<BaseGraphicalEditPart> tasks = EditPartUtil
                        .getAllActivitiesInProcess(hostTaskEditPart
                                .getParentProcess(),
                                EditPartUtil.ACTIVITY_FILTER_TASKS);

                for (BaseGraphicalEditPart ep : tasks) {
                    if (ep instanceof TaskEditPart) {
                        TaskEditPart taskEP = (TaskEditPart) ep;
                        
                        if (refdTaskId.equals(taskEP.getActivityAdapter().getId())) {
                            refTasks.add(taskEP);
                        }
                    }
                }
            }
        }

        return refTasks;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.policies.HighlightReferencesPolicy#getReferencedFromObjects()
     */
    @Override
    protected Collection<GraphicalEditPart> getReferencedFromObjects() {
        // Any task can be referenced by any other reference task.
        TaskEditPart hostTaskEditPart = (TaskEditPart) getHost();

        TaskAdapter taskAdp = hostTaskEditPart.getActivityAdapter();

        Collection<GraphicalEditPart> refTasks = new ArrayList<GraphicalEditPart>(); 

        String taskId = taskAdp.getId();

        // Get a list of all error events in same process.
        Collection<BaseGraphicalEditPart> tasks = EditPartUtil
                .getAllActivitiesInProcess(hostTaskEditPart
                        .getParentProcess(),
                        EditPartUtil.ACTIVITY_FILTER_TASKS);

        for (BaseGraphicalEditPart ep : tasks) {
            if (ep instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) ep;
                
                if (TaskType.REFERENCE_LITERAL.equals(taskEP.getActivityAdapter().getTaskType())) {
                    String refdTaskId = taskEP.getActivityAdapter().getReferencedTask();
                    
                    if (taskId.equals(refdTaskId)) {
                        refTasks.add(taskEP);
                    }
                }
            }
        }

        return refTasks;
    }

}

