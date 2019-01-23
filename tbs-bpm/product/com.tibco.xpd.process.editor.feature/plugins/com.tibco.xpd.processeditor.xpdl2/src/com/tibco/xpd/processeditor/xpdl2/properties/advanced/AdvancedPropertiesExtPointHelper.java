/**
 * AdvancedPropertiesExtPointHelper.java
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty;

/**
 * AdvancedPropertiesExtPointHelper
 * 
 */
public class AdvancedPropertiesExtPointHelper {

    private static final String ADVANCEDPROPERTIES_EXTPOINT_ID =
            "advancedProperties"; //$NON-NLS-1$

    private static final String DESTINATIONENVIRONMENTS_EL =
            "DestinationEnvironments"; //$NON-NLS-1$

    private static final String DESTINATIONENVCATEGORYLABEL_ATTR =
            "DestinationEnvCategoryLabel"; //$NON-NLS-1$

    private static final String DESTINATIONENVIRONMENT_EL =
            "DestinationEnvironment"; //$NON-NLS-1$

    private static final String DESTINATIONENVIRONMENTID_ATTR =
            "DestinationEnvironmentId"; //$NON-NLS-1$

    private static final String ADVANCEDPROPERTIES_EL = "AdvancedProperties"; //$NON-NLS-1$

    private static final String ADVANCEDPROPERTYCATEGORY_EL =
            "AdvancedPropertyCategory"; //$NON-NLS-1$

    private static final String DISPLAYNAME_ATTR = "DisplayName"; //$NON-NLS-1$

    private static final String ADVANCEDPROPERTY_EL = "AdvancedProperty"; //$NON-NLS-1$

    private static final String CONTRIBUTEDADVANCEDPROPERTYCLASS_ATTR =
            "ContributedAdvancedPropertyClass"; //$NON-NLS-1$

    private static final AdvancedPropertiesExtPointHelper INSTANCE =
            new AdvancedPropertiesExtPointHelper();

    /**
     * DestEnvAdvProps
     * <p>
     * Simple data class for holding the properties contributed for a given set
     * of destination environments.
     * </p>
     */
    public class ContributionsForDestinationGroup {
        String destinationCategoryName = ""; //$NON-NLS-1$

        /**
         * Set of destinations that this group of properties and categories
         * applies to.
         */
        Set<String> destinations = new HashSet<String>();

        /**
         * Hierarchical list of properties and categories contributed to this
         * destination group.
         */
        List<IAdvancedPropertyContribution> propertiesAndCategories =
                new ArrayList<IAdvancedPropertyContribution>();

        /**
         * Flat list of properties contributed to this destination group.
         */
        List<AdvancedPropertyContribution> allDestinationGroupPropertyContributions =
                new ArrayList<AdvancedPropertyContribution>();
    }

    /**
     * Hierarchical list of properties and categories grouped by destination
     * group.
     */
    private List<ContributionsForDestinationGroup> destinationCategories;

    /** Flat list of all property contributions. */
    private List<AdvancedPropertyContribution> allPropertyContributions;

    /**
     * Get the default static instance of this class.
     * <p>
     * The Advanced Properties tab works better if the same PropertyDescriptor
     * instances are re-used (it merges them in with the tree view and hence
     * preserves expansion state of sub-categories whenever possible).
     * </p>
     * 
     * @return
     */
    public static AdvancedPropertiesExtPointHelper getDefault() {
        return INSTANCE;
    }

    public AdvancedPropertiesExtPointHelper() {
        loadContributions();
    }

    /**
     * Return the list of contributions grouped by destination group.
     * 
     * @return the list of contributions grouped by destination group.
     */
    public List<ContributionsForDestinationGroup> getDestinationCategories() {
        return destinationCategories;
    }

    /**
     * Return a flat list of all the actual advanced property contributions.
     * 
     * @return a flat list of all the actual advanced property contributions.
     */
    public List<AdvancedPropertyContribution> getPropertyContributions() {
        return allPropertyContributions;
    }

    private void loadContributions() {
        destinationCategories =
                new ArrayList<ContributionsForDestinationGroup>();
        allPropertyContributions =
                new ArrayList<AdvancedPropertyContribution>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                ADVANCEDPROPERTIES_EXTPOINT_ID);

        if (point != null) {
            //
            // Go thru each contribution to this extension point.
            //
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    //
                    // Get the destinations element
                    //
                    IConfigurationElement destinationsEl =
                            getConfigElement(ext,
                                    DESTINATIONENVIRONMENTS_EL,
                                    true);
                    if (destinationsEl == null) {
                        continue;
                    }

                    // Category name for destinations.
                    ContributionsForDestinationGroup destProps =
                            new ContributionsForDestinationGroup();

                    destProps.destinationCategoryName =
                            getConfigAttribute(destinationsEl,
                                    DESTINATIONENVCATEGORYLABEL_ATTR,
                                    true);
                    if (destProps.destinationCategoryName == null) {
                        continue;
                    }

                    // The destinations themselves
                    IConfigurationElement[] elements =
                            destinationsEl
                                    .getChildren(DESTINATIONENVIRONMENT_EL);
                    if (elements != null) {
                        for (int i = 0; i < elements.length; i++) {
                            IConfigurationElement destEl = elements[i];

                            String destId =
                                    getConfigAttribute(destEl,
                                            DESTINATIONENVIRONMENTID_ATTR,
                                            true);
                            if (destId != null) {
                                destProps.destinations.add(destId);
                            }
                        }
                    }

                    if (destProps.destinations.size() <= 0) {
                        String contributerId = ext.getContributor().getName();
                        Xpdl2ProcessEditorPlugin
                                .getDefault()
                                .getLogger()
                                .error(contributerId
                                        + ": " //$NON-NLS-1$
                                        + ADVANCEDPROPERTIES_EXTPOINT_ID
                                        + ": Incorrectly defined extension - no destinations specified"); //$NON-NLS-1$
                        continue;
                    }

                    //
                    // Then load the properties/property categories.
                    //
                    IConfigurationElement advProps =
                            getConfigElement(ext, ADVANCEDPROPERTIES_EL, true);
                    if (advProps == null) {
                        continue;
                    }

                    List<IAdvancedPropertyContribution> propsAndCats =
                            loadPropertiesAndCategories(ext,
                                    destProps,
                                    advProps,
                                    destProps.destinationCategoryName);
                    if (propsAndCats == null || propsAndCats.size() < 1) {
                        String contributerId = ext.getContributor().getName();
                        Xpdl2ProcessEditorPlugin
                                .getDefault()
                                .getLogger()
                                .error(contributerId
                                        + ": " //$NON-NLS-1$
                                        + ADVANCEDPROPERTIES_EXTPOINT_ID
                                        + ": Incorrectly defined extension - no properties specified for destination group - " //$NON-NLS-1$ 
                                        + destProps.destinationCategoryName);
                        continue;
                    }

                    destProps.propertiesAndCategories = propsAndCats;

                    //
                    // Finally save the contribution in our cached list
                    //
                    destinationCategories.add(destProps);

                }
            }
        }

        return;
    }

    /**
     * Recursively load all properties and category contributions
     * 
     * @param ext
     * @param destProps
     * @param advProps
     * @param idPrefix
     * @return
     */
    private List<IAdvancedPropertyContribution> loadPropertiesAndCategories(
            IExtension ext, ContributionsForDestinationGroup destProps,
            IConfigurationElement advProps, String idPrefix) {
        List<IAdvancedPropertyContribution> propsAndCats =
                new ArrayList<IAdvancedPropertyContribution>();

        //
        // Load the individual properties first (sub-categories later).
        //
        IConfigurationElement[] elements =
                advProps.getChildren(ADVANCEDPROPERTY_EL);
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                IConfigurationElement propEl = elements[i];

                String displayName =
                        getConfigAttribute(propEl, DISPLAYNAME_ATTR, true);
                if (displayName == null) {
                    continue;
                }

                Object propClass = null;
                try {
                    propClass =
                            propEl
                                    .createExecutableExtension(CONTRIBUTEDADVANCEDPROPERTYCLASS_ATTR);

                } catch (CoreException e) {
                }

                if (!(propClass instanceof AbstractContributedAdvancedProperty)) {
                    String contributerId = ext.getContributor().getName();
                    Xpdl2ProcessEditorPlugin
                            .getDefault()
                            .getLogger()
                            .error(contributerId
                                    + ": " //$NON-NLS-1$
                                    + ADVANCEDPROPERTIES_EXTPOINT_ID
                                    + ": Incorrectly defined extension - class for property '" + displayName //$NON-NLS-1$ 
                                    + "' must implement " + AbstractContributedAdvancedProperty.class.getCanonicalName()); //$NON-NLS-1$

                    continue;
                }

                //
                // Create our own wrapper for the contribution.
                // NOTE any contribution is potentially a sequence so we will
                // create an entry for index=0 now and remove/add others from
                // list when we return the properties when we know what the
                // selected input object is later on.
                String propertyId =
                        idPrefix
                                + "." + displayName + "(" + propClass.hashCode() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

                AdvancedPropertyContribution propContr =
                        new AdvancedPropertyContribution(
                                propertyId,
                                displayName,
                                (AbstractContributedAdvancedProperty) propClass,
                                0);

                propsAndCats.add(propContr);
                allPropertyContributions.add(propContr);
                destProps.allDestinationGroupPropertyContributions
                        .add(propContr);
            }
        }

        //
        // Then load the sub-categories.
        //
        elements = advProps.getChildren(ADVANCEDPROPERTYCATEGORY_EL);
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                IConfigurationElement catEl = elements[i];

                String displayName =
                        getConfigAttribute(catEl, DISPLAYNAME_ATTR, true);
                if (displayName == null) {
                    continue;
                }

                IConfigurationElement subAdvProps =
                        getConfigElement(catEl, ADVANCEDPROPERTIES_EL, true);
                if (subAdvProps == null) {
                    continue;
                }

                //
                // Create our own wrapper for the category contribution
                //
                String categoryId =
                        idPrefix + "." + displayName + "(" + i + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

                AdvancedPropertyCategoryContribution catContr =
                        new AdvancedPropertyCategoryContribution(categoryId,
                                displayName);

                List<IAdvancedPropertyContribution> subPropsAndCats =
                        loadPropertiesAndCategories(ext,
                                destProps,
                                subAdvProps,
                                categoryId);
                for (IAdvancedPropertyContribution contr : subPropsAndCats) {
                    catContr.addChild(contr);
                }

                propsAndCats.add(catContr);
            }
        }

        return propsAndCats;
    }

    /**
     * Get named extension point config element from extension point.
     * 
     * @param extPoint
     * @param name
     * 
     * @return Element with given name or null if not found
     */
    private static IConfigurationElement getConfigElement(IExtension extPoint,
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
            Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                    .error(contributerId
                            + ": " //$NON-NLS-1$
                            + ADVANCEDPROPERTIES_EXTPOINT_ID
                            + ": Incorrectly defined extension - missing " //$NON-NLS-1$
                            + name + " element(s)"); //$NON-NLS-1$
        }

        return retElement;
    }

    /**
     * Get named extension point config sub elements from extension point
     * configuration element.
     * 
     * @param extPoint
     * @param name
     * @param bRequired
     *            If true the output sys err if element not present.
     * 
     * @return Element with given name or null if not found
     */
    private static IConfigurationElement getConfigElement(
            IConfigurationElement element, String name, boolean bRequired) {

        IConfigurationElement retElement = null;

        IConfigurationElement[] elements = element.getChildren(name);

        if (elements == null || elements.length != 1) {
            if (bRequired) {
                String contributerId =
                        element.getDeclaringExtension().getContributor()
                                .getName();
                Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getLogger()
                        .error(contributerId
                                + ": " //$NON-NLS-1$
                                + ADVANCEDPROPERTIES_EXTPOINT_ID
                                + ": Incorrectly defined extension - missing (or too many)" //$NON-NLS-1$
                                + name + " required element(s)"); //$NON-NLS-1$
            }
        } else {
            retElement = elements[0];
        }

        return retElement;
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
    private static String getConfigAttribute(IConfigurationElement element,
            String name, boolean bRequired) {

        String retAttribute = element.getAttribute(name);

        if ((retAttribute == null || retAttribute.length() == 0)) {
            if (bRequired) {
                String contributerId =
                        element.getDeclaringExtension().getContributor()
                                .getName();
                Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                        .error(contributerId
                                + ": " //$NON-NLS-1$
                                + ADVANCEDPROPERTIES_EXTPOINT_ID
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
