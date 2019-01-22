/**
 * IPropertyContribution.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * IPropertyContribution
 * <p>
 * Interface representing either a property or property category contributed to
 * the <code>com.tibco.xpd.processeditor.xpdl2.advancedProperties</code>
 * extension point.
 * </p>
 */
interface IAdvancedPropertyContribution {
    String getId();

    String getName();

    PropertyDescriptor getPropertyDescriptor(EObject input);

    Object getValue(EObject input);

    boolean isApplicable(EObject input);

}