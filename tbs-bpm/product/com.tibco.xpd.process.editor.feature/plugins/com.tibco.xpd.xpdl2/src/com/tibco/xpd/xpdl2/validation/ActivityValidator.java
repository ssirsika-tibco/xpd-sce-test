/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FinishModeType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IORules;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Limit;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.OutputSet;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.SimulationInformation;
import com.tibco.xpd.xpdl2.StartModeType;
import com.tibco.xpd.xpdl2.StatusType;
import com.tibco.xpd.xpdl2.Transaction;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.Activity}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ActivityValidator {
    boolean validate();

    boolean validateLimit(Limit value);

    boolean validateRoute(Route value);

    boolean validateImplementation(Implementation value);

    boolean validateBlockActivity(BlockActivity value);

    boolean validateEvent(Event value);

    boolean validateTransaction(Transaction value);

    boolean validatePerformer(Performer value);

    boolean validatePerformers(Performers value);

    boolean validatePriority(Priority value);

    boolean validateDeadline(EList value);

    boolean validateSimulationInformation(SimulationInformation value);

    boolean validateIcon(Icon value);

    boolean validateDocumentation(Documentation value);

    boolean validateTransitionRestrictions(EList value);

    boolean validateDataFields(EList value);

    boolean validateInputSets(EList value);

    boolean validateOutputSets(OutputSet value);

    boolean validateIoRules(IORules value);

    boolean validateLoop(Loop value);

    boolean validateAssignments(EList value);

    boolean validateObject(com.tibco.xpd.xpdl2.Object value);

    boolean validateExtensions(EObject value);

    boolean validateFinishMode(FinishModeType value);

    boolean validateIsATransaction(boolean value);

    boolean validateStartActivity(boolean value);

    boolean validateStartMode(StartModeType value);

    boolean validateStartQuantity(BigInteger value);

    boolean validateStatus(StatusType value);

    boolean validateFlowContainer(FlowContainer value);

    boolean validateProcess(com.tibco.xpd.xpdl2.Process value);

    boolean validateIsForCompensation(boolean value);

    boolean validateCompletionQuantity(BigInteger value);
}
