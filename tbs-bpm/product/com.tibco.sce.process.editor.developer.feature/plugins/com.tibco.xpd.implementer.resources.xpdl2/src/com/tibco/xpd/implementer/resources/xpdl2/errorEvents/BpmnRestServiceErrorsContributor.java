package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Task;

/**
 * Contributor class to provide the list of REST Faults that can be thrown by an
 * Activity. This list is read from the REST Service Descriptor associated with
 * the Activity.
 * 
 * @author nwilson
 * @since 27 Feb 2015
 */
public class BpmnRestServiceErrorsContributor implements
        IBpmnCatchableErrorsContributor {

    /**
     * Checks to see if the given Activity has a REST Service Descriptor
     * associated with it.
     * 
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#isApplicableErrorThrower(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param errorTask
     *            The Activity throwing the error.
     * @return true if it has a REST Service Descriptor associated with it.
     */
    @Override
    public boolean isApplicableErrorThrower(Activity errorTask) {
        final RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        OtherElementsContainer rsoParent = rsta.getRSOContainer(errorTask);
        RestServiceOperation rso = rsta.getRSO(rsoParent);
        return rso != null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getCatchableErrorTreeItems(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param errorTask
     *            The Activity throwing the error.
     * @return A collection of Fault codes that can be thrown by the Activity.
     */
    @Override
    public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorTask) {
        Collection<IBpmnCatchableErrorTreeItem> items = new ArrayList<>();
        BpmnCatchableErrorFolder httpErrorFolder =
                new BpmnCatchableErrorFolder(getActivityName(errorTask),
                        getActivityImage(errorTask));
        items.add(httpErrorFolder);
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        List<Fault> faults = rsta.getRSOFaults(errorTask);
        if (faults != null) {
            for (Fault fault : faults) {
                IBpmnCatchableErrorTreeItem item =
                        new BpmnCatchableError(errorTask,
                                ErrorThrowerType.PROCESS_ACTIVITY,
                                fault.getStatusCode(), fault.getName(), this);
                httpErrorFolder.addChild(item);
            }
        }
        return items;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorImage(java.lang.Object,
     *      java.lang.String)
     * 
     * @param errorThrower
     *            The Activity throwing the error.
     * @param errorId
     *            The error code.
     * @return The associated image.
     */
    @Override
    public Image getErrorImage(Object errorThrower, String errorId) {
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2ResourcesConsts.IMG_ERROR_EVENT_ICON12);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerId(java.lang.Object)
     * 
     * @param errorThrower
     *            The Activity throwing the error.
     * @return The Activity ID.
     */
    @Override
    public String getErrorThrowerId(Object errorThrower) {
        // Safe to assume that errorThrower is the one we returned which is
        // ALWAYS an activity.
        return ((Activity) errorThrower).getId();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor#getErrorThrowerContainerId(java.lang.Object)
     * 
     * @param errorThrower
     *            The Activity throwing the error.
     * @return The containing process ID.
     */
    @Override
    public String getErrorThrowerContainerId(Object errorThrower) {
        // Safe to assume that errorThrower is the one we returned which is
        // ALWAYS an activity.
        return ((Activity) errorThrower).getProcess().getId();
    }

    /**
     * @param errorThrowerTaskOrEvent
     *            The Activity throwing the error.
     * @return The image for that activity.
     */
    private Image getActivityImage(Activity errorThrowerTaskOrEvent) {
        if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
            return DiagramDropObjectUtils.getImageForTaskType(TaskObjectUtil
                    .getTaskType(errorThrowerTaskOrEvent));

        } else if (errorThrowerTaskOrEvent.getEvent() != null) {
            return DiagramDropObjectUtils.getImageForEventType(EventObjectUtil
                    .getFlowType(errorThrowerTaskOrEvent), EventObjectUtil
                    .getEventTriggerType(errorThrowerTaskOrEvent));
        }
        return null;
    }

    /**
     * @param errorThrowerTaskOrEvent
     *            The Activity throwing the error.
     * @return The display name for that Activity.
     */
    private String getActivityName(Activity errorThrowerTaskOrEvent) {
        String name = ProcessDataUtil.getActivityName(errorThrowerTaskOrEvent);
        if (name == null || name.length() == 0) {
            if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
                name =
                        "<"     + TaskObjectUtil.getTaskType(errorThrowerTaskOrEvent) //$NON-NLS-1$
                                        .toString() + ">"; //$NON-NLS-1$

            } else if (errorThrowerTaskOrEvent.getEvent() != null) {
                name =
                        "<"     + EventObjectUtil.getFlowType(errorThrowerTaskOrEvent) //$NON-NLS-1$
                                        .toString() + ">"; //$NON-NLS-1$
            } else {
                name = "?"; //$NON-NLS-1$
            }
        }

        return name;
    }
}
