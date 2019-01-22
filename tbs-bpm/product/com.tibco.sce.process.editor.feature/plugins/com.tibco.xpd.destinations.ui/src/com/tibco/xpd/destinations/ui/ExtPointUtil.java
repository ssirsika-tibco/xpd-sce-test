/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.destinations.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

/**
 * @author aallway
 * 
 */
public class ExtPointUtil {

    /**
     * Get named extension point config element from extension point.
     * 
     * @param extPoint
     * @param name
     * 
     * @return Element with given name or null if not found
     */
    public static IConfigurationElement getConfigElement(IExtension extPoint,
            String name, boolean bRequired) {
        IConfigurationElement retElement = null;

        IConfigurationElement[] elements = extPoint.getConfigurationElements();

        if (elements != null) {
            for (IConfigurationElement elem : elements) {
                if (name.equals(elem.getName())) {
                    retElement = elem;
                }
            }
        }

        if (retElement == null && bRequired) {
            String contributerId = extPoint.getContributor().getName();
            System.err.println(contributerId + ": " //$NON-NLS-1$
                    + extPoint.getExtensionPointUniqueIdentifier()
                    + ": Incorrectly defined extension - missing " //$NON-NLS-1$
                    + name + " element(s)"); //$NON-NLS-1$
        }

        return retElement;
    }

    /**
     * Get named extension point config sub elements from extension point
     * 
     * @param extPoint
     * @param name
     * @param bRequired
     *            If true then output sys err if element not present.
     * 
     * @return Element with given name or null if not found
     */
    public static Collection<IConfigurationElement> getConfigElements(
            IExtension extPoint, String name, boolean bRequired) {

        List<IConfigurationElement> retElements =
                new ArrayList<IConfigurationElement>();

        IConfigurationElement[] elements = extPoint.getConfigurationElements();

        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                IConfigurationElement el = elements[i];

                if (el.getName().equals(name)) {
                    retElements.add(el);
                }
            }
        }

        if (retElements.size() == 0 && bRequired) {
            String contributerId = extPoint.getContributor().getName();
            System.err.println(contributerId + ": " //$NON-NLS-1$
                    + extPoint.getExtensionPointUniqueIdentifier()
                    + ": Incorrectly defined extension - missing" //$NON-NLS-1$
                    + name + " required element(s)"); //$NON-NLS-1$
        }

        return retElements;
    }

    /**
     * Get named extension point config sub elements from extension point
     * configuration element.
     * 
     * @param extPoint
     * @param name
     * @param bRequired
     *            If true then output sys err if element not present.
     * 
     * @return Element with given name or null if not found
     */
    public static Collection<IConfigurationElement> getConfigElements(
            IConfigurationElement element, String name, boolean bRequired) {

        List<IConfigurationElement> retElements =
                new ArrayList<IConfigurationElement>();

        IConfigurationElement[] elements = element.getChildren(name);

        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                IConfigurationElement el = elements[i];

                if (el.getName().equals(name)) {
                    retElements.add(el);
                }
            }
        }

        if (retElements.size() == 0 && bRequired) {
            String contributerId =
                    element.getDeclaringExtension().getContributor().getName();
            System.err.println(contributerId
                    + ": " //$NON-NLS-1$
                    + element.getDeclaringExtension()
                            .getExtensionPointUniqueIdentifier()
                    + ": Incorrectly defined extension - missing" //$NON-NLS-1$
                    + name + " required element(s)"); //$NON-NLS-1$
        }

        return retElements;
    }

    /**
     * Get named extension point config attribute from extension point
     * configuration element.
     * 
     * @param extPoint
     * @param name
     * @param bRequired
     *            If true the output sys err if element not present.
     * 
     * @return Element or null if not found and required.
     */
    public static String getConfigAttribute(IConfigurationElement element,
            String name, boolean bRequired) {

        String retAttribute = element.getAttribute(name);

        if ((retAttribute == null || retAttribute.length() == 0)) {
            if (bRequired) {
                String contributerId =
                        element.getDeclaringExtension().getContributor()
                                .getName();
                System.err.println(contributerId
                        + ": " //$NON-NLS-1$
                        + element.getDeclaringExtension()
                                .getExtensionPointUniqueIdentifier()
                        + ": Incorrectly defined extension - missing " //$NON-NLS-1$
                        + name + " attribute"); //$NON-NLS-1$
                retAttribute = null;
            } else {
                retAttribute = ""; //$NON-NLS-1$
            }
        }

        return retAttribute;
    }

}
