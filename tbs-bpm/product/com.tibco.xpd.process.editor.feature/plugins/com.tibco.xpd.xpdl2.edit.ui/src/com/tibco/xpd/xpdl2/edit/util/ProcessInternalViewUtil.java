package com.tibco.xpd.xpdl2.edit.util;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;

/**
 * 
 * @author KamleshU
 * 
 */
public class ProcessInternalViewUtil {

    private static IWorkbenchActivitySupport activitySupport =
            PlatformUI.getWorkbench().getActivitySupport();

    private static IActivityManager activityManager =
            activitySupport.getActivityManager();

    public static boolean isProcessInternalViewEnabled() {
        boolean toReturn = false;
        Set definedActivityIds = activityManager.getDefinedActivityIds();
        for (Iterator iter = definedActivityIds.iterator(); iter.hasNext();) {
            String activityId = (String) iter.next();
            if (XpdConsts.PROCESS_INTERNAL_CAPABILITY.equals(activityId)) {
                toReturn = activityManager.getActivity(activityId).isEnabled();
                break;
            }
        }
        return toReturn;
    }

    public static Activity getActivity(String id) {
        Activity activity = null;
        Set definedActivityIds = activityManager.getDefinedActivityIds();
        for (Iterator iter = definedActivityIds.iterator(); iter.hasNext();) {
            String activityId = (String) iter.next();
            if (id != null && activityId != null && id.equals(activityId)) {
                IActivity iActivity = activityManager.getActivity(activityId);
                break;
            }
        }
        return activity;
    }

    public static String getParticipantTypeLabel(Enumerator enumerator) {
        if (enumerator != null) {
            if (enumerator.equals(ParticipantType.ROLE_LITERAL)) {
                return Messages.ParticipantType_Role_Label;
            } else if (enumerator
                    .equals(ParticipantType.ORGANIZATIONAL_UNIT_LITERAL)) {
                return Messages.ParticipantType_Organizational_Unit_Label;
            } else if (enumerator.equals(ParticipantType.HUMAN_LITERAL)) {
                return Messages.ParticipantType_Human_Label;
            } else if (enumerator.equals(ParticipantType.SYSTEM_LITERAL)) {
                return Messages.ParticipantType_System_Label;
            } else if (enumerator.equals(ParticipantType.RESOURCE_SET_LITERAL)) {
                return Messages.ProcessInternalViewUtil_OrgModel_Label;
            }
        }
        return null;
    }

}
