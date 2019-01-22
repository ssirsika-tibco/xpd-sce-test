/**
 * BpmnTaskPropertiesUtils.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.contributions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BpmnTaskPropertiesUtils
 * 
 */
public class BpmnTaskPropertiesUtils {

    /**
     * Return the xpdl2:Process or xpdl2:Activity/xpdExt:DurationCalculation
     * element.
     * 
     * @param container
     * @return DurationCalculation or <code>null</code> if not currently
     *         defined.
     */
    public static DurationCalculation getDurationCalculation(
            OtherElementsContainer container) {
        DurationCalculation duration =
                (DurationCalculation) Xpdl2ModelUtil.getOtherElement(container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DurationCalculation());

        return duration;

    }

    /**
     * Return the xpdl2:Process or xpdl2:Activity/xpdExt:DurationCalculation
     * element, if it does not already exist it is created and an EMF command to
     * add it to the given activity is appended to the given compound command.
     * 
     * @param container
     * @param editingDomain
     * @param cmd
     * 
     * @return The activity's xpdExt:DurationCalculation element.
     */
    public static DurationCalculation getOrAddDurationCalculation(
            OtherElementsContainer container, EditingDomain editingDomain,
            CompoundCommand cmd) {
        DurationCalculation duration = getDurationCalculation(container);
        if (duration == null) {
            duration =
                    XpdExtensionFactory.eINSTANCE.createDurationCalculation();

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    container,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DurationCalculation(),
                    duration));
        }

        return duration;
    }

    /**
     * Get command to remove the xpdl2:Process or
     * xpdl2:Activity/xpdExt:DurationCalculation element if it exists.
     * 
     * @param editingDomain
     * @param container
     * 
     * @return command or null if element does not exist.
     */
    public static Command getRemoveDurationCalculationCommand(
            EditingDomain editingDomain, OtherElementsContainer container) {

        DurationCalculation duration = getDurationCalculation(container);
        if (duration != null) {
            return Xpdl2ModelUtil.getRemoveOtherElementCommand(editingDomain,
                    container,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DurationCalculation(),
                    duration);
        }
        return null;
    }

}
