/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * AbstractSetScriptToUnspecifiedResolution - sets script grammar to unspecified
 * for user task scripts, audit scripts, re-usable sub-proc scripts
 * 
 * 
 * @author bharge
 * @since 3.3 (22 Jun 2010)
 */
public abstract class AbstractSetScriptToUnspecifiedResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            cmd = getUnsetScriptGrammarCommand(editingDomain, activity);
        }
        return cmd;
    }

    /**
     * @param editingDomain
     * @param activity
     * @return
     */
    protected abstract Command getUnsetScriptGrammarCommand(
            EditingDomain editingDomain, Activity activity);

    // set audit scripts - initiate, timeout, cancel, complete

    public static class SetInitiateScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteInitiatedScriptGrammarCommand(editingDomain,
                            activity);
        }
    }

    public static class SetDeadlineScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteDeadlineExpiredScriptGrammarCommand(editingDomain,
                            activity);
        }
    }

    public static class SetCancelScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteCancelledScriptGrammarCommand(editingDomain,
                            activity);
        }
    }

    public static class SetCompleteScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteCompletedScriptGrammarCommand(editingDomain,
                            activity);
        }
    }

    // set user task scripts - open, close, submit
    public static class SetOpenScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {
        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteOpenUserTaskScriptGrammarCommand(editingDomain,
                            activity);
        }
    }

    public static class SetCloseScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteCloseUserTaskScriptGrammarCommand(editingDomain,
                            activity);
        }

    }

    public static class SetSubmitScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteSubmitUserTaskScriptGrammarCommand(editingDomain,
                            activity);
        }

    }

    public static class SetScheduleScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteScheduleUserTaskScriptGrammarCommand(editingDomain,
                            activity);
        }

    }

    public static class SetRescheduleScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getDeleteRescheduleUserTaskScriptGrammarCommand(editingDomain,
                            activity);
        }

    }

    /**
     * Resolution to Set the Adhoc Task Precondition Script Grammar to
     * Unspecified.
     * 
     * 
     * @author kthombar
     * @since 19-Aug-2014
     */
    public static class SetAdhocPreconditionScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /**
         * Unset the Adhoc task precondition Script grammar. [i.e. make the
         * Script Grammar Unspecified.
         * 
         * @see com.tibco.bx.validation.resolutions.AbstractSetScriptToUnspecifiedResolution#getUnsetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      com.tibco.xpd.xpdl2.Activity)
         * 
         * @param editingDomain
         * @param activity
         * @return command to unset the Adhoc Precondition script grammar.
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getRemoveAdhocScriptGrammarCommand(editingDomain, activity);
        }
    }

    // set re-usable sub-proc scripts - complex exit expr, additional
    // instance expr

    public static class SetComplexExitScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getRemoveMIComplexExitScriptGrammarCommand(editingDomain,
                            activity);

        }

    }

    public static class SetAdditionalInstanceScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getRemoveMIAdditionalInstancesScriptGrammarCommand(editingDomain,
                            activity);
        }

    }

    public static class RemoveRescheduleTimerScriptResolution extends
            AbstractSetScriptToUnspecifiedResolution {

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.bx.validation.resolutions.
         * AbstractSetScriptToUnspecifiedResolution
         * #getUnsetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
         * com.tibco.xpd.xpdl2.Activity)
         */
        @Override
        protected Command getUnsetScriptGrammarCommand(
                EditingDomain editingDomain, Activity activity) {
            return ProcessScriptUtil
                    .getRemoveRescheduleTimerEventScriptGrammarCommand(editingDomain,
                            activity);
        }

    }

}
