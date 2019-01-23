/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * SetEventTriggerTypeResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (5 Nov 2009)
 */
public abstract class SetEventTriggerTypeResolution extends
        AbstractWorkingCopyResolution {
    private EventTriggerType triggerType;

    private SetEventTriggerTypeResolution(EventTriggerType triggerType) {
        this.triggerType = triggerType;
    }

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            return EventObjectUtil.getSetEventTriggerTypeCommand(editingDomain,
                    (Activity) target,
                    triggerType);
        }
        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        return String.format(propertiesLabel, triggerType.toString());
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return String.format(propertiesDescription, triggerType.toString());
    }

    /**
     * SetEventTriggerTypeNoneResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeNoneResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeNoneResolution() {
            super(EventTriggerType.EVENT_NONE_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeTerminateResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeTerminateResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeTerminateResolution() {
            super(EventTriggerType.EVENT_TERMINATE_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeThrowMsgResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeThrowMsgResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeThrowMsgResolution() {
            super(EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeCatchMsgResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeCatchMsgResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeCatchMsgResolution() {
            super(EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeThrowSignalResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeThrowSignalResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeThrowSignalResolution() {
            super(EventTriggerType.EVENT_SIGNAL_THROW_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeCatchSignalResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeCatchSignalResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeCatchSignalResolution() {
            super(EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeThrowCompensationResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeThrowCompensationResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeThrowCompensationResolution() {
            super(EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeCatchCompensationResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeCatchCompensationResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeCatchCompensationResolution() {
            super(EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeTimerResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeTimerResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeTimerResolution() {
            super(EventTriggerType.EVENT_TIMER_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeErrorResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeErrorResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeErrorResolution() {
            super(EventTriggerType.EVENT_ERROR_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeCancelResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeCancelResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeCancelResolution() {
            super(EventTriggerType.EVENT_CANCEL_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeConditionalResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeConditionalResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeConditionalResolution() {
            super(EventTriggerType.EVENT_CONDITIONAL_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeCatchMultipleResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeCatchMultipleResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeCatchMultipleResolution() {
            super(EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeThrowMultipleResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeThrowMultipleResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeThrowMultipleResolution() {
            super(EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeThrowLinkResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeThrowLinkResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeThrowLinkResolution() {
            super(EventTriggerType.EVENT_LINK_THROW_LITERAL);
        }
    }

    /**
     * SetEventTriggerTypeCatchLinkResolution
     * 
     * @author aallway
     * @since 3.3 (5 Nov 2009)
     */
    public static class SetEventTriggerTypeCatchLinkResolution extends
            SetEventTriggerTypeResolution {

        public SetEventTriggerTypeCatchLinkResolution() {
            super(EventTriggerType.EVENT_LINK_CATCH_LITERAL);
        }
    }
}
