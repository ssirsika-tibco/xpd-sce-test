/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.validation;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link com.tibco.xpd.xpdExtension.ProcessInterfaces}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ProcessInterfacesValidator {
    boolean validate();

    boolean validateProcessInterface(EList value);
}
