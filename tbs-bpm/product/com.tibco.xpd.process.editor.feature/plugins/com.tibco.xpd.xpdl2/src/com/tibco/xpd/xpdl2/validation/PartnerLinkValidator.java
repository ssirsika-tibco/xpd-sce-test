/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import com.tibco.xpd.xpdl2.MyRole;
import com.tibco.xpd.xpdl2.PartnerRole;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.PartnerLink}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface PartnerLinkValidator {
    boolean validate();

    boolean validateMyRole(MyRole value);

    boolean validatePartnerRole(PartnerRole value);

    boolean validatePartnerLinkTypeId(String value);

    boolean validateName(String value);
}
