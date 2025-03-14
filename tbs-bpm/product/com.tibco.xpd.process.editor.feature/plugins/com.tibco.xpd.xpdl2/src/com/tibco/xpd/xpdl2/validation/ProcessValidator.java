/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.AccessLevelType;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.ProcessType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.StatusType;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.Process}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ProcessValidator {
    boolean validate();

    boolean validateProcessHeader(ProcessHeader value);

    boolean validateRedefinableHeader(RedefinableHeader value);

    boolean validatePartnerLinks(EList value);

    boolean validateObject(com.tibco.xpd.xpdl2.Object value);

    boolean validateExtensions(EObject value);

    boolean validateAccessLevel(AccessLevelType value);

    boolean validateDefaultStartActivitySetId(String value);

    boolean validateEnableInstanceCompensation(boolean value);

    boolean validateProcessType(ProcessType value);

    boolean validateStatus(StatusType value);

    boolean validateSuppressJoinFailure(boolean value);

    boolean validatePackage(com.tibco.xpd.xpdl2.Package value);

    boolean validateActivitySets(EList value);
}
