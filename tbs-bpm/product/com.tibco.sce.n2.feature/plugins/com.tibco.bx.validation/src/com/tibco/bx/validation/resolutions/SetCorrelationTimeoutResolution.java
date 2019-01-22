/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.math.BigInteger;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.RenameDialog;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to set the correlation timeout days, hours, minutes and seconds if
 * they are not in the range 1 to 2,147,483,647 (Maximum Integer value)
 * 
 * @author bharge
 * @since 13 Jun 2012
 */
public class SetCorrelationTimeoutResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand cmd = new CompoundCommand();

        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            EStructuralFeature feature =
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_CorrelationTimeout();
            ConstantPeriod constantPeriod =
                    (ConstantPeriod) Xpdl2ModelUtil.getOtherElement(activity,
                            feature);

            if (null != constantPeriod) {

                /**
                 * reading the additional info added in the rule class
                 */
                Properties props = ValidationUtil.getAdditionalInfo(marker);
                if (null != props) {
                    String days =
                            props.getProperty(Messages.CorrelationTimeoutRule_timeoutDays);
                    if (null != days) {
                        return setCorrelationTimeoutDays(editingDomain,
                                cmd,
                                constantPeriod,
                                days);
                    }
                    String hours =
                            props.getProperty(Messages.CorrelationTimeoutRule_timeoutHours);
                    if (null != hours) {
                        return setCorrelationTimeoutHours(editingDomain,
                                cmd,
                                constantPeriod,
                                hours);
                    }
                    String minutes =
                            props.getProperty(Messages.CorrelationTimeoutRule_timeoutMinutes);
                    if (null != minutes) {
                        return setCorrelationTimeoutMinutes(editingDomain,
                                cmd,
                                constantPeriod,
                                minutes);
                    }
                    String seconds =
                            props.getProperty(Messages.CorrelationTimeoutRule_timeoutSeconds);
                    if (null != seconds) {
                        return setCorrelationTimeoutSeconds(editingDomain,
                                cmd,
                                constantPeriod,
                                seconds);
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param constantPeriod
     * @param seconds
     */
    private CompoundCommand setCorrelationTimeoutSeconds(
            EditingDomain editingDomain, CompoundCommand cmd,
            ConstantPeriod constantPeriod, String seconds) {

        Object newSecondsValue = getNewValue(seconds);

        if (newSecondsValue instanceof BigInteger) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Seconds(),
                    newSecondsValue));
            return cmd;
        } else if (null == newSecondsValue) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Seconds(),
                    null));
            return cmd;
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param constantPeriod
     * @param minutes
     */
    private CompoundCommand setCorrelationTimeoutMinutes(
            EditingDomain editingDomain, CompoundCommand cmd,
            ConstantPeriod constantPeriod, String minutes) {

        Object newMinutesValue = getNewValue(minutes);

        if (newMinutesValue instanceof BigInteger) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Minutes(),
                    newMinutesValue));
            return cmd;
        } else if (null == newMinutesValue) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Minutes(),
                    null));
            return cmd;
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param constantPeriod
     * @param hours
     */
    private CompoundCommand setCorrelationTimeoutHours(
            EditingDomain editingDomain, CompoundCommand cmd,
            ConstantPeriod constantPeriod, String hours) {

        Object newHoursValue = getNewValue(hours);

        if (newHoursValue instanceof BigInteger) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Hours(),
                    newHoursValue));
            return cmd;
        } else if (null == newHoursValue) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Hours(),
                    null));
            return cmd;
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param constantPeriod
     * @param days
     * @return
     */
    private CompoundCommand setCorrelationTimeoutDays(
            EditingDomain editingDomain, CompoundCommand cmd,
            ConstantPeriod constantPeriod, String days) {

        Object newDaysValue = getNewValue(days);

        if (newDaysValue instanceof BigInteger) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Days(),
                    newDaysValue));
            return cmd;
        } else if (null == newDaysValue) {
            cmd.append(SetCommand.create(editingDomain,
                    constantPeriod,
                    XpdExtensionPackage.eINSTANCE.getConstantPeriod_Days(),
                    null));
            return cmd;
        }
        return null;
    }

    /**
     * @param oldValue
     * @return
     */
    private Object getNewValue(String oldValue) {

        RenameDialog dlg =
                new RenameDialog(
                        Display.getDefault().getActiveShell(),
                        Messages.SetCorrelationTimeoutResolution_SetCorrelationTimeout_title,
                        oldValue);
        if (dlg.open() == RenameDialog.OK) {
            String value = dlg.getName();

            if (null != value && value.trim().length() > 0) {
                BigInteger newValue = new BigInteger(value);
                return newValue;
            }
        }

        return null;
    }

}
