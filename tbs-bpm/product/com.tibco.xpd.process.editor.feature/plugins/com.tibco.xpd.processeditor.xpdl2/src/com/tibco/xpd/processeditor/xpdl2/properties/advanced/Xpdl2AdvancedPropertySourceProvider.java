/**
 * Xpdl2PropertySourceProvider.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.AdvancedPropertiesExtPointHelper.ContributionsForDestinationGroup;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * Xpdl2PropertySourceProvider
 * <p>
 * Properties Advanced Tab Property source provider for Xpdl2 process model.
 * <p>
 * There can be only one property source provider for a given selected element
 * so this provider is a generic one for NamedElement elements in Xpdl2
 * (nominally, NamedElement derived classes are those top-level objects that are
 * selectable for process package / process.
 * <p>
 * Then the com.tibco.xpd.processeditor.xpdl2.advancedProperties extension point
 * can be used to contribute properties to the advanced tab for selected xpdl2
 * objects.
 */
public class Xpdl2AdvancedPropertySourceProvider implements IAdapterFactory,
        IPropertySourceProvider {

    public static final String BPMN_DESTINATION =
            ProcessEditorConstants.BPMN_DESTINATION;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     * java.lang.Class)
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adapterType == IPropertySourceProvider.class) {
            if (getNamedElementForObject(adaptableObject) != null) {
                return this;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    public Class[] getAdapterList() {
        return new Class[] { IPropertySourceProvider.class };
    }

    public IPropertySource getPropertySource(Object object) {
        // System.out.println("GET PROPERTY SOURCE"); //$NON-NLS-1$
        NamedElement namedElement = getNamedElementForObject(object);

        if (namedElement != null) {
            List<IAdvancedPropertyContribution> contributions =
                    new ArrayList<IAdvancedPropertyContribution>();

            //
            // Get the ext point contributions for this particular object.
            // First filtering by destination environments.
            //
            Set<String> enabledDestinations = new HashSet<String>();

            enabledDestinations.addAll(DestinationUtil
                    .getEnabledGlobalDestinationTypes(namedElement));

            // Always include "bpmn1" as a base destination environment for all
            // processes.
            enabledDestinations.add(BPMN_DESTINATION);

            List<ContributionsForDestinationGroup> allContributions =
                    AdvancedPropertiesExtPointHelper.getDefault()
                            .getDestinationCategories();

            for (ContributionsForDestinationGroup contrForDestGroup : allContributions) {
                boolean groupEnabled = false;

                for (String dest : contrForDestGroup.destinations) {
                    if (enabledDestinations.contains(dest)) {
                        groupEnabled = true;
                        break;
                    }
                }

                if (groupEnabled) {
                    for (IAdvancedPropertyContribution contr : contrForDestGroup.propertiesAndCategories) {
                        if (contr.isApplicable(namedElement)) {
                            contributions.add(contr);
                            contr
                                    .getPropertyDescriptor(namedElement)
                                    .setCategory(contrForDestGroup.destinationCategoryName);
                        }
                    }

                }
            }

            if (contributions != null && contributions.size() > 0) {
                Xpdl2AdvancedPropertySource propSource =
                        new Xpdl2AdvancedPropertySource(namedElement,
                                contributions);

                return propSource;
            }
        }

        return null;
    }

    private NamedElement getNamedElementForObject(Object adaptableObject) {
        NamedElement namedElement = null;

        if (adaptableObject instanceof NamedElement) {
            namedElement = (NamedElement) adaptableObject;
        } else {
            if (adaptableObject instanceof IAdaptable) {
                EObject eo =
                        (EObject) ((IAdaptable) adaptableObject)
                                .getAdapter(EObject.class);

                if (eo instanceof NamedElement) {
                    namedElement = (NamedElement) eo;
                }
            }
        }

        return namedElement;
    }

}
