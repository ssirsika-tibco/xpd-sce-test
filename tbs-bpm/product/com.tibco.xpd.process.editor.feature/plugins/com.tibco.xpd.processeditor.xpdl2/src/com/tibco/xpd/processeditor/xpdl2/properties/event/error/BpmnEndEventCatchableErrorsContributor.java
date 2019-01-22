/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.error;

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
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Catchable errors provider for Throw Error End Events (via the
 * 
 * <code>com.tibco.xpd.analyst.resources.xpdl2.errorEvents.bpmnCatchableErrorProviders/ErrorBrowser</code>
 * extension point.
 * 
 * @author aallway
 * @since 3.2
 */
public class BpmnEndEventCatchableErrorsContributor implements
        IBpmnCatchableErrorsContributor {

    @Override
    public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorThrower) {
        BpmnCatchableError error = null;

        List<IBpmnCatchableErrorTreeItem> items =
                new ArrayList<IBpmnCatchableErrorTreeItem>();

        // Always put the error inside a folder representing the End Error
        // Event.
        String name = Xpdl2ModelUtil.getDisplayName(errorThrower);
        if (name == null || name.length() == 0) {
            name =
                    "<" //$NON-NLS-1$
                            + Messages.BpmnEndEventCatchableErrorsProvider_ErrorCodePickerErrorEndEvent_label
                            + ">"; //$NON-NLS-1$
        }

        Image image = null;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            image =
                    Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2ResourcesConsts.IMG_ERROR_END_EVENT_ICON);
        }
        BpmnCatchableErrorFolder folder =
                new BpmnCatchableErrorFolder(name, image);

        // All the casts etc without checking are ok - we won't be called
        // unless isApplicableErrorThrower has already been called to check.
        ResultError resError =
                ((EndEvent) errorThrower.getEvent()).getResultError();
        if (resError != null) {
            // For BPMN end error events the errorCode IS the errorId.
            String errCode = resError.getErrorCode();
            if (errCode != null && errCode.length() > 0) {
                error =
                        new BpmnCatchableError(errorThrower,
                                ErrorThrowerType.PROCESS_ACTIVITY, errCode,
                                this);
            }
        }

        if (error != null) {
            folder.addChild(error);
        }
        items.add(folder);

        return items;
    }

    @Override
    public boolean isApplicableErrorThrower(Activity errorThrower) {
        if (ThrowErrorEventUtil.isThrowProcessErrorEvent(errorThrower)) {
            return true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorEventInfoProvider#getErrorImage(com.tibco.xpd.xpdl2.Activity,
     *      java.lang.String)
     * 
     * @param errorThrower
     * @param errorId
     * @return
     */
    @Override
    public Image getErrorImage(Object errorThrower, String errorId) {
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2ResourcesConsts.IMG_ERROR_EVENT_ICON12);
        }
        return null;
    }

    @Override
    public String getErrorThrowerContainerId(Object errorThrower) {
        if (errorThrower instanceof Activity) {
            return ((Activity) errorThrower).getProcess().getId();
        } else {
            throw new RuntimeException(
                    "Expected errorThrower to be instanceof Activity - got: " //$NON-NLS-1$ 
                            + errorThrower.getClass().getName());
        }
    }

    @Override
    public String getErrorThrowerId(Object errorThrower) {
        if (errorThrower instanceof Activity) {
            return ((Activity) errorThrower).getId();
        } else {
            throw new RuntimeException(
                    "Expected errorThrower to be instanceof Activity - got: " //$NON-NLS-1$ 
                            + errorThrower.getClass().getName());
        }
    }

}
