/* 
 ** 
 **  MODULE:             $RCSfile: ManualApplicationsQueueImpl.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-15 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.tibco.xpd.simulation.Messages;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;

public class TaskActivityThreadQueue {

    /**
     * Every task activity has separate queue. Keys are ActivityId (String)
     * value is Queue.
     */
    private Map queues = new HashMap();

    /** Linked list of Activities ids (String). */
    private LinkedList addedActivities = new LinkedList();

    /**
     * Map with participants. Keys are activityId (String) values are
     * participantId (String). This is a very simple implementation of security
     * based on map and String participants.
     */
    private Map activityParticipants = new HashMap();

    /**
     * The constructor.
     * 
     * @param model
     *            parent simulation model.
     * @param showInRaport
     * @param showInTrace
     * @param activityParticipants
     *            the keys are activities (type Acitivity) and values are
     *            performer (type String).
     */
    public TaskActivityThreadQueue(Model model, boolean showInRaport,
            boolean showInTrace, Map activityParticipants) {

        for (Iterator iter = activityParticipants.keySet().iterator(); iter
                .hasNext();) {
            Activity activity = (Activity) iter.next();
            String activityId = activity.getId();
            queues.put(activityId, new Queue(model, Messages.TaskActivityThreadQueue_Queue
                    + SimulationXpdlUtils.getActivityDisplayName(activity),
                    showInRaport, showInTrace));

            // defense copy of map.
            this.activityParticipants.put(activityId, activityParticipants
                    .get(activity));
        }
    }

    public boolean isQueueForParticipantEmpty(String participantId) {
        for (Iterator iter = addedActivities.listIterator(); iter.hasNext();) {
            String actId = (String) iter.next();
            if (participantId.equals(activityParticipants.get(actId))) {
                if (!((Queue) queues.get(actId)).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Entity firstForParticipant(String participantId) {
        for (Iterator iter = addedActivities.listIterator(); iter.hasNext();) {
            String actId = (String) iter.next();
            if (participantId.equals(activityParticipants.get(actId))) {
                Queue queue = (Queue) queues.get(actId);
                if (!queue.isEmpty()) {
                    return queue.first();
                }
            }
        }
        return null;
    }

    public void remove(String activityId, Entity entity) {
        addedActivities.remove(activityId); // removes first from list
        ((Queue) queues.get(activityId)).remove(entity);
    }

    public void insert(String activityId, Entity entity) {
        addedActivities.addLast(activityId);
        ((Queue) queues.get(activityId)).insert(entity);
    }

    public Queue getActivityQueue(String activityId) {
        return (Queue) queues.get(activityId);
    }

    /**
     * Places an entity back in a queue. The insertion will not count towards the
     * number of observed cases for that queue.
     * @param activityId The activty id.
     * @param entity The entity to insert into the queue.
     */
    public void reinsert(String activityId, Entity entity) {
        addedActivities.addLast(activityId);
        Queue queue = (Queue) queues.get(activityId);
        queue.insert(entity);
        queue.incrementObservations(-1);
    }
    
}
