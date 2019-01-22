/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * SetTaskTypeResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (6 Nov 2009)
 */
public abstract class SetTaskTypeResolution extends
        AbstractWorkingCopyResolution {

    private TaskType taskType;

    /**
     * @param taskType
     */
    SetTaskTypeResolution(TaskType taskType) {
        super();
        this.taskType = taskType;
    }

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            return TaskObjectUtil.getSetTaskTypeCommandEx(editingDomain,
                    activity,
                    taskType,
                    activity.getProcess(),
                    true,
                    true,
                    true);
        }

        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        return String.format(propertiesLabel, taskType.toString());
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return String.format(propertiesDescription, taskType.toString());
    }

    /**
     * SetTaskTypeNoneResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeNoneResolution extends SetTaskTypeResolution {
        public SetTaskTypeNoneResolution() {
            super(TaskType.NONE_LITERAL);
        }
    }

    /**
     * SetTaskTypeServiceResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeServiceResolution extends
            SetTaskTypeResolution {
        public SetTaskTypeServiceResolution() {
            super(TaskType.SERVICE_LITERAL);
        }
    }

    /**
     * SetTaskTypeUserResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeUserResolution extends SetTaskTypeResolution {
        public SetTaskTypeUserResolution() {
            super(TaskType.USER_LITERAL);
        }
    }

    /**
     * SetTaskTypeManualResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeManualResolution extends
            SetTaskTypeResolution {
        public SetTaskTypeManualResolution() {
            super(TaskType.MANUAL_LITERAL);
        }
    }

    /**
     * SetTaskTypeReceiveResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeReceiveResolution extends
            SetTaskTypeResolution {
        public SetTaskTypeReceiveResolution() {
            super(TaskType.RECEIVE_LITERAL);
        }
    }

    /**
     * SetTaskTypeReferenceResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeReferenceResolution extends
            SetTaskTypeResolution {
        public SetTaskTypeReferenceResolution() {
            super(TaskType.REFERENCE_LITERAL);
        }
    }

    /**
     * SetTaskTypeScriptResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeScriptResolution extends
            SetTaskTypeResolution {
        public SetTaskTypeScriptResolution() {
            super(TaskType.SCRIPT_LITERAL);
        }
    }

    /**
     * SetTaskTypeSendResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeSendResolution extends SetTaskTypeResolution {
        public SetTaskTypeSendResolution() {
            super(TaskType.SEND_LITERAL);
        }
    }

    /**
     * SetTaskTypeSubProcessResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeSubProcessResolution extends
            SetTaskTypeResolution {
        public SetTaskTypeSubProcessResolution() {
            super(TaskType.SUBPROCESS_LITERAL);
        }
    }

    /**
     * SetTaskTypeEmbeddedSubProcessResolution
     * 
     * @author aallway
     * @since 3.3 (6 Nov 2009)
     */
    public static class SetTaskTypeEmbeddedSubProcessResolution extends
            SetTaskTypeResolution {
        public SetTaskTypeEmbeddedSubProcessResolution() {
            super(TaskType.EMBEDDED_SUBPROCESS_LITERAL);
        }
    }
}
