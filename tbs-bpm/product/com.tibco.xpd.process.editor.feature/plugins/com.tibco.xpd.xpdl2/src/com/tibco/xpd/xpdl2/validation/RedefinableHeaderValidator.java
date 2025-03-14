/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.validation;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.xpdl2.Codepage;
import com.tibco.xpd.xpdl2.CountryKey;
import com.tibco.xpd.xpdl2.PublicationStatusType;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdl2.RedefinableHeader}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface RedefinableHeaderValidator {
    boolean validate();

    boolean validateAuthor(String value);

    boolean validateVersion(String value);

    boolean validateCodepage(Codepage value);

    boolean validateCountrykey(CountryKey value);

    boolean validateResponsibles(EList value);

    boolean validatePublicationStatus(PublicationStatusType value);
}
