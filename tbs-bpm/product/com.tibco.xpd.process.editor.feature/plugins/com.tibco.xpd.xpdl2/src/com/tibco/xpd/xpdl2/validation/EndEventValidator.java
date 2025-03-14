/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultMultiple;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.EndEvent}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EndEventValidator {
    boolean validate();

    boolean validateTriggerResultMessage(TriggerResultMessage value);

    boolean validateResultError(ResultError value);

    boolean validateTriggerResultCompensation(TriggerResultCompensation value);

    boolean validateResultCompensation(TriggerResultCompensation value);

    boolean validateTriggerResultLink(TriggerResultLink value);

    boolean validateResultMultiple(ResultMultiple value);

    boolean validateImplementation(ImplementationType value);

    boolean validateResult(ResultType value);
}
