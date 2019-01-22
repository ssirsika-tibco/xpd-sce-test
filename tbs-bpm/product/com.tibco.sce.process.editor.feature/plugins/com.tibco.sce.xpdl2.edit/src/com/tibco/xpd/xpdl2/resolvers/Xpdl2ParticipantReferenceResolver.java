package com.tibco.xpd.xpdl2.resolvers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Finds references to participants. (can only be referenced in activity
 * performer currently).
 * <p>
 * If the p[articipant is package level then the activities from multiple
 * processes may be returned.
 * 
 * @author aallway
 * 
 */
public class Xpdl2ParticipantReferenceResolver {

    /**
     * Finds references to participants. (can only be referenced in activity
     * performer currently).
     * <p>
     * If the p[articipant is package level then the activities from multiple
     * processes may be returned.
     */
    public static Set<EObject> getReferencingObjects(Participant participant) {
        Set<EObject> refs = new HashSet<EObject>();

        if (participant != null) {
            EObject container = participant.eContainer();

            if (container instanceof Package) {
                for (Iterator iter =
                        ((Package) container).getProcesses().iterator(); iter
                        .hasNext();) {
                    Process process = (Process) iter.next();

                    addParticReferences(process, refs, participant);
                }
            } else if (container instanceof Process) {
                addParticReferences((Process) container, refs, participant);

            }
        }

        return refs;
    }

    private static void addParticReferences(Process process, Set<EObject> refs,
            Participant participant) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();

            EList performers = activity.getPerformerList();
            if (performers != null && performers.size() > 0) {
                for (Iterator perfIter = performers.iterator(); perfIter
                        .hasNext();) {
                    Performer perf = (Performer) perfIter.next();

                    if (participant.getId().equals(perf.getValue())) {
                        refs.add(activity);
                        break;
                    }
                } // next performer
            }
        } // next activity

        return;
    }

    public static Set<Participant> getParticipantDataReferences(
            ProcessRelevantData processRelevantData) {
        Set<ProcessRelevantData> checkData = new HashSet<ProcessRelevantData>();
        Set<Participant> checkParticipantData = new HashSet<Participant>();

        Set<Participant> result = new HashSet<Participant>();
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            if (resolver instanceof IFieldResolverExtension2) {
                IFieldResolverExtension2 resolver2 =
                        (IFieldResolverExtension2) resolver;
                
                EObject parent = processRelevantData.eContainer();
                if (parent instanceof Process) {
                    checkParticipantData.addAll(((Process) parent).getParticipants());
                } else if (parent instanceof Package) {
                    checkParticipantData.addAll(((Package) parent).getParticipants());
                }                 
                
                Set<Participant> resolverResult =
                        resolver2
                                .getParticipantDataReferences(processRelevantData,
                                        checkParticipantData);
                if (resolverResult != null && resolverResult.size() > 0) {
                    result.addAll(resolverResult);
                }
            }
        }
        return result;
    }
}
