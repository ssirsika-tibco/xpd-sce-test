/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityFilterPicker.ActivityPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.IActivityPickerProxyItem;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract select an activity from picker resolution. The subclass can filter
 * out activities that are not applicable and provide the command to set the
 * reference to activity given the resoltuionTarget (the object that problem
 * marker was attached to).
 * <p>
 * The available activities are provided from the process derived from the
 * resolution target object so this MUST be a child of the process in order to
 * function correctly).
 * 
 * @author aallway
 * @since 3.3
 */
public abstract class AbstractSelectActivityReferenceResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @param resolutionTarget
     * @param activity
     * 
     * @return true if <code>activity</code> should be incldued in th elist of
     *         selectable activities for teh given <code>resolutionTarget</code>
     */
    protected abstract boolean isValidActivityForReference(
            EObject resolutionTarget, Activity activity);

    /**
     * @param editingDomain
     * @param resolutionTarget
     * @param activity
     * 
     * @return The command to set the reference in resolutionTarget to the given
     *         ctivitity picked by the user.
     */
    protected abstract Command getSetActivityReferenceCommand(
            EditingDomain editingDomain, EObject resolutionTarget,
            Activity activity);

    /**
     * @return title for picker.
     */
    protected abstract String getTitle(EObject resolutionTarget);

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            final EObject target, IMarker marker) throws ResolutionException {

        Process process = Xpdl2ModelUtil.getProcess(target);
        if (process != null) {

            Activity activity = (Activity) target;
            /*
             * XPD-3704 ABPM-470 - changes to exclude start events with option
             * 'Reply Immediate With Process Id' set.
             */
            List<Activity> incomingRequests =
                    ReplyActivityUtil.getAllIncomingRequestActivities(activity
                            .getProcess());
            List<EObject> toExclude = new ArrayList<EObject>();
            for (Activity actv : incomingRequests) {
                if (ReplyActivityUtil.isStartMessageWithReplyImmediate(actv)) {
                    toExclude.add(actv);
                }
            }
            EObject[] excludeList = new EObject[toExclude.size()];

            ActivityFilterPicker picker =
                    new ActivityFilterPicker(Display.getDefault()
                            .getActiveShell(), toExclude.toArray(excludeList),
                            ActivityPickerType.ACTIVITY, false);

            String title = getTitle(target);
            if (title == null) {
                title =
                        Messages.AbstractSelectActivityReferenceResolution_SelectActivity_title;
            }
            picker.setTitle(title);

            picker.setScope(process);

            picker.addFilter(new IFilter() {
                public boolean select(Object toTest) {
                    if (toTest instanceof IActivityPickerProxyItem) {
                        Object obj =
                                ((IActivityPickerProxyItem) toTest).getItem();
                        if (obj instanceof Activity) {
                            if (isValidActivityForReference(target,
                                    (Activity) obj)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });

            if (picker.open() == picker.OK) {
                Object[] result = picker.getResult();

                if (result.length == 1 && result[0] instanceof Activity) {
                    Activity pickedAct = (Activity) result[0];

                    return getSetActivityReferenceCommand(editingDomain,
                            target,
                            pickedAct);
                }
            }
        }
        return null;
    }

}
