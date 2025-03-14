/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import com.tibco.xpd.xpdl2.Duration;
import com.tibco.xpd.xpdl2.WaitingTime;
import com.tibco.xpd.xpdl2.WorkingTime;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.TimeEstimation}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TimeEstimationValidator {
    boolean validate();

    boolean validateWaitingTime(WaitingTime value);

    boolean validateWorkingTime(WorkingTime value);

    boolean validateDuration(Duration value);
}
