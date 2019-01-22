/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.complexdatatype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;

/**
 * Helper for the com.tibco.xpd.resources.ui.complexDataType extension point.
 * <p>
 * See {@link ComplexDataTypeExtPointHelper#getComplexDataTypesMergedInfo(Set)}
 * for more information.
 * 
 * @author aallway
 * 
 */
public final class ComplexDataTypeExtPointHelper {

    /** Complex data type extension point Id. */
    private static final String COMPLEX_DATATYPE_EXTPOINT_ID = "complexDataType"; //$NON-NLS-1$ 

    /** main element. */
    private static final String COMPLEX_DATATYPE_EL = "complexDataType"; //$NON-NLS-1$ 

    /** Id attribute for complex data type. */
    private static final String ID_ATTR = "id"; //$NON-NLS-1$

    /** reference resolver class attribute. */
    private static final String REFERENCE_RESOLVER_ATTR = "referenceResolver"; //$NON-NLS-1$

    /** element describing filters for type. */
    private static final String BROWSEFILTERS_EL = "browseFilters"; //$NON-NLS-1$ 

    /** element describing inclusion filter for eObject sub-class. */
    private static final String OBJECT_INC_FILTER_EL = "eObjectInclusionFilter"; //$NON-NLS-1$ 

    /** element describing exclusion filter for eObject sub-class. */
    private static final String OBJECT_EXC_FILTER_EL = "eObjectExclusionFilter"; //$NON-NLS-1$ 

    /** element descrining file type inclusion filter. */
    private static final String FILE_INC_FILTER_EL = "fileTypeInclusionFilter"; //$NON-NLS-1$ 

    /** file name extension attribute for file type inclusion filter. */
    private static final String FILENAME_EXTENSION_ATTR = "fileNameExtension"; //$NON-NLS-1$ 

    /** element describing special folder inclusion filter. */
    private static final String SPECIALFOLDER_INC_FILTER_EL = "specialFolderInclusionFilter"; //$NON-NLS-1$ 

    /** special folder id attribute for special folder filter. */
    private static final String SPECIALFOLDER_KIND_ATTR = "kind"; //$NON-NLS-1$ 

    /** element describing custyom filter. */
    private static final String CUSTOM_FILTER_EL = "customFilter"; //$NON-NLS-1$ 

    /** attribute for declaring class type (various elements). */
    private static final String CLASS_ATTR = "class"; //$NON-NLS-1$ 

    private static Object allComplexDataTypesCreationLock = new Boolean(true); 
    
    private static ComplexDataTypesMergedInfo staticAllComplexDataTypesMergeInfo = null; 
    
    /**
     * disable default construction.
     */
    private ComplexDataTypeExtPointHelper() {
    }

    /**
     * Small data class for Info for an individual complex data type extension.
     */
    private static class ComplexDataTypeExtPointInfo {
        String id = null;

        Set<String> specialFolderFilters = new HashSet<String>();

        Set<String> fileNameExtFilters = new HashSet<String>();

        Set<String> eObjectInclusionFilters = new HashSet<String>();

        Set<String> eObjectExclusionFilters = new HashSet<String>();

        List<ViewerFilter> customFilters = new ArrayList<ViewerFilter>();

        ComplexDataTypeRefResolver refResolver = null;

    }

    /**
     * Get the complex data type descriptive name.
     * <p>
     * Unlike the similar method in {@link ComplexDataTypesMergedInfo} this
     * method will return the name from the first of <b>any</b> contributed
     * complex data type resolver (rather than only the supported types.
     * <p>
     * This is so that if a type should become unsupported, the feature
     * accessing the complex data type info can still get a sensible name for
     * the reference.
     * 
     * @param complexDataTypeReference
     * @param project
     * @return Complex data type descriptive name or <b>null</b> if type
     *         reference not handled by any data type contributer.
     */
    public static String getComplexDataTypeDescriptiveName(
            ComplexDataTypeReference complexDataTypeReference, IProject project) {
        String name = null;

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(XpdResourcesUIActivator.ID,
                        COMPLEX_DATATYPE_EXTPOINT_ID);

        if (point != null) {
            //
            // Go thru each extender of this extension point.
            //
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    // 
                    // Load the top level complexDataType element.
                    //
                    IConfigurationElement complexDataTypeElem = getConfigElement(
                            ext, COMPLEX_DATATYPE_EL, true);

                    // If we have a valid extension and extension type is
                    // active.
                    if (complexDataTypeElem != null) {
                        ComplexDataTypeRefResolver refResolver = loadRefResolver(complexDataTypeElem);
                        if (refResolver != null) {
                            //
                            // Check if this object can resolve type.
                            Object complexTypeObject = refResolver
                                    .referenceToComplexDataType(
                                            complexDataTypeReference, project);

                            if (complexTypeObject != null) {
                                // 
                                // This reference resolver understands reference
                                // and can find object, so it should know how to
                                // name it.
                                name = refResolver
                                        .getComplexDataTypeName(complexTypeObject);

                                if (name != null) {
                                    break;
                                }
                            }
                        }
                    }
                } // Next extension contributer.
            }
        }

        return name;
    }

    /**
     * Load and the combination of all complex data type extensions info. All
     * filters defined for required types are merged so that the returned filter
     * list should show all iotems defined by required types.
     * <p>
     * Complex types are declared via the
     * com.tibco.xpd.resources.ui.complexDataType extension point.
     * <p>
     * Each extension declares...
     * <li>An id for the type.</li>
     * <li>A reference resolver (that can consistently create a string-based
     * reference to a given complex data type object and back again)</li>
     * <li>A list of filters designed to show parts of project explorer tree
     * necessary to drill-down to the given complex data type definition.</li>
     * 
     * @param requiredTypes
     *            List of required types (by
     *            com.tibco.xpd.resources.ui.complexDataType/Id attribute).
     * 
     * @return Object containing list of filters and reference Resolvers for
     *         required types.
     */
    @SuppressWarnings("unchecked")
    public static ComplexDataTypesMergedInfo getComplexDataTypesMergedInfo(
            Set<String> requiredTypes) {
        if (requiredTypes == null) {
            requiredTypes = Collections.EMPTY_SET;
        }
        return internalGetMergedInfo(requiredTypes);
    }

    /**
     * As per getComplexDataTypesMergedInfo() but merges all available complex
     * data types' infos.
     * 
     * @return Object containing list of filters and reference Resolvers for all
     *         types.
     */
    public static ComplexDataTypesMergedInfo getAllComplexDataTypesMergedInfo() {
        synchronized (allComplexDataTypesCreationLock) {
            if (staticAllComplexDataTypesMergeInfo == null) {
                staticAllComplexDataTypesMergeInfo = internalGetMergedInfo(null);
            }
            return staticAllComplexDataTypesMergeInfo;
        }
    }

    /**
     * 
     * @param requiredTypes
     *            null = All types.
     * @return
     */
    private static ComplexDataTypesMergedInfo internalGetMergedInfo(
            Set<String> requiredTypes) {

        ComplexDataTypesMergedInfo complexDataTypes = new ComplexDataTypesMergedInfo();

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(XpdResourcesUIActivator.ID,
                        COMPLEX_DATATYPE_EXTPOINT_ID);

        if (point != null) {
            //
            // Go thru each extender of this extension point.
            //
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                // In order for filter to work correctly for multiple complex
                // data types we have to merge all special folder types and so
                // on. (Else for instance, separate special folder filters will
                // say 'no' to any special folder that isn't in its list of
                // types and TreeViewer says object is filtered out if any
                // filter returns 'no')
                Set<String> specialFolderFilters = new HashSet<String>();
                Set<String> fileNameExtFilters = new HashSet<String>();
                Set<String> eObjectInclusionFilters = new HashSet<String>();
                Set<String> eObjectExclusionFilters = new HashSet<String>();
                List<ViewerFilter> customFilters = new ArrayList<ViewerFilter>();

                for (IExtension ext : extensions) {
                    // Load this ext point into memory if supported by caller.
                    ComplexDataTypeExtPointInfo complexType = loadSingleExtension(
                            ext, requiredTypes);

                    if (complexType != null) {
                        // This is a required type, add info to combination
                        // lists.
                        complexDataTypes
                                .addReferenceResolver(complexType.refResolver);

                        specialFolderFilters
                                .addAll(complexType.specialFolderFilters);
                        fileNameExtFilters
                                .addAll(complexType.fileNameExtFilters);
                        eObjectInclusionFilters
                                .addAll(complexType.eObjectInclusionFilters);
                        eObjectExclusionFilters
                                .addAll(complexType.eObjectExclusionFilters);
                        customFilters.addAll(complexType.customFilters);
                    }

                } // Next extension point.

                //
                // Having loaded all the individual extension points, create
                // filter for each type of filter.
                //
                if (specialFolderFilters.size() > 0) {
                    ViewerFilter filter = new SpecialFoldersOnlyFilter(
                            specialFolderFilters);
                    complexDataTypes.addViewerFilter(filter);
                }

                if (fileNameExtFilters.size() > 0) {
                    ViewerFilter filter = new FileExtensionInclusionFilter(
                            fileNameExtFilters);
                    complexDataTypes.addViewerFilter(filter);
                }

                if (eObjectInclusionFilters.size() > 0) {
                    ViewerFilter filter = new EObjIncFilter(
                            eObjectInclusionFilters);
                    complexDataTypes.addViewerFilter(filter);
                }

                if (eObjectExclusionFilters.size() > 0) {
                    ViewerFilter filter = new EObjExcFilter(
                            eObjectExclusionFilters);
                    complexDataTypes.addViewerFilter(filter);
                }

                if (customFilters.size() > 0) {
                    for (Iterator<?> iter = customFilters.iterator(); iter
                            .hasNext();) {
                        ViewerFilter filter = (ViewerFilter) iter.next();
                        complexDataTypes.addViewerFilter(filter);
                    }

                }

            }
        }

        return complexDataTypes;
    }

    /**
     * Load extension info for one single complex data type.
     * 
     * @param ext
     * @param requiredTypes
     * 
     * @return Complex data type ext point info or null if unrequired type (or
     *         error)
     */
    private static ComplexDataTypeExtPointInfo loadSingleExtension(
            IExtension ext, Set<String> requiredTypes) {

        ComplexDataTypeExtPointInfo complexDataTypeExtPoint = new ComplexDataTypeExtPointInfo();

        // 
        // Load the top level complexDataType element.
        //
        IConfigurationElement complexDataTypeElem = getConfigElement(ext,
                COMPLEX_DATATYPE_EL, true);

        // If we have a valid extension and extension type is active.
        if (complexDataTypeElem != null
                && isExtensionEnabled(complexDataTypeElem, requiredTypes)) {

            complexDataTypeExtPoint.id = getConfigAttribute(
                    complexDataTypeElem, ID_ATTR, true);

            if (complexDataTypeExtPoint.id != null) {

                // 
                // Load the complex data type reference resolver.
                //
                complexDataTypeExtPoint.refResolver = loadRefResolver(complexDataTypeElem);

                if (complexDataTypeExtPoint.refResolver != null) {

                    //
                    // Load the browser filters.
                    //
                    IConfigurationElement browseFiltersElem = getConfigElement(
                            complexDataTypeElem, BROWSEFILTERS_EL, true);

                    if (browseFiltersElem != null) {
                        if (loadViewerFilters(browseFiltersElem,
                                complexDataTypeExtPoint)) {
                            // 
                            // All is well.
                            //
                            return complexDataTypeExtPoint;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Return whether the given complex data type extension should be active.
     * <p>
     * It will be active if either...
     * <li>The extension has an id that matches an entry in the requiredTypes
     * set.</li>
     * </p>
     * 
     * @param complexDataTypeElem
     * @param requiredTypes
     * 
     * @return true if extension should be active.
     */
    private static boolean isExtensionEnabled(
            IConfigurationElement complexDataTypeElem, Set<String> requiredTypes) {
        boolean enabled = false;

        if (requiredTypes == null) {
            // special case, if caller wants all types infos merged.
            enabled = true;
        } else if (requiredTypes.size() > 0) {
            // If specific types are required then include only if contained in
            // list.
            String id = getConfigAttribute(complexDataTypeElem, ID_ATTR, true);
            if (id != null && id.length() > 0) {
                enabled = requiredTypes.contains(id);
            }
        }

        return enabled;
    }

    /**
     * Load filter definitions from extension point info ext point info
     * 
     * @param browseFiltersElem
     * @param complexDataTypeExtPoint
     * @return
     */
    private static boolean loadViewerFilters(
            IConfigurationElement browseFiltersElem,
            ComplexDataTypeExtPointInfo complexDataTypeExtPoint) {

        IConfigurationElement[] elements = browseFiltersElem.getChildren();

        if (elements != null && elements.length > 0) {

            for (int i = 0; i < elements.length; i++) {
                IConfigurationElement elem = elements[i];

                String elemName = elem.getName();

                if (SPECIALFOLDER_INC_FILTER_EL.equals(elemName)) {
                    // Create a special folder viewer filter.
                    String kind = getConfigAttribute(elem,
                            SPECIALFOLDER_KIND_ATTR, true);

                    if (kind != null && kind.length() > 0) {
                        complexDataTypeExtPoint.specialFolderFilters.add(kind);
                    } else {
                        return false;
                    }

                } else if (FILE_INC_FILTER_EL.equals(elemName)) {
                    // Create a file viewer filter.
                    String fileNameExt = getConfigAttribute(elem,
                            FILENAME_EXTENSION_ATTR, true);

                    if (fileNameExt != null && fileNameExt.length() > 0) {
                        if (".".equals(fileNameExt.substring(0, 1))) { //$NON-NLS-1$
                            fileNameExt = fileNameExt.substring(1);
                        }

                        complexDataTypeExtPoint.fileNameExtFilters
                                .add(fileNameExt);
                    } else {
                        return false;
                    }

                } else if (OBJECT_INC_FILTER_EL.equals(elemName)) {
                    // Create a EObject inclusion viewer filter.
                    String eObjectClassName = getConfigAttribute(elem,
                            CLASS_ATTR, true);

                    if (eObjectClassName != null
                            && eObjectClassName.length() > 0) {
                        complexDataTypeExtPoint.eObjectInclusionFilters
                                .add(eObjectClassName);
                    } else {
                        return false;
                    }

                } else if (OBJECT_EXC_FILTER_EL.equals(elemName)) {
                    // Create a EObject exclusion viewer filter.
                    String eObjectClassName = getConfigAttribute(elem,
                            CLASS_ATTR, true);

                    if (eObjectClassName != null
                            && eObjectClassName.length() > 0) {
                        complexDataTypeExtPoint.eObjectExclusionFilters
                                .add(eObjectClassName);
                    } else {
                        return false;
                    }

                } else if (CUSTOM_FILTER_EL.equals(elemName)) {
                    // Create a custom viewer filter.
                    ViewerFilter viewerFilter = null;
                    try {
                        Object clazz = elem
                                .createExecutableExtension(CLASS_ATTR);

                        if (clazz instanceof ViewerFilter) {
                            viewerFilter = (ViewerFilter) clazz;
                        }

                    } catch (CoreException e) {
                        XpdResourcesUIActivator.getDefault().getLogger().error(
                                e);
                    }

                    if (viewerFilter == null) {
                        XpdResourcesUIActivator
                                .getDefault()
                                .getLogger()
                                .error(
                                        COMPLEX_DATATYPE_EXTPOINT_ID
                                                + ": Incorrectly defined extension - custom filter class create failed."); //$NON-NLS-1$
                        return false;
                    }

                    complexDataTypeExtPoint.customFilters.add(viewerFilter);

                } else {
                    XpdResourcesUIActivator
                            .getDefault()
                            .getLogger()
                            .error(
                                    COMPLEX_DATATYPE_EXTPOINT_ID
                                            + ": Incorrectly defined extension - unknow browse filter type."); //$NON-NLS-1$
                    return false;
                }

            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Load the complex data type reference resolver defined in ext point.
     * 
     * @param complexDataTypeElem
     * @return
     */
    private static ComplexDataTypeRefResolver loadRefResolver(
            IConfigurationElement complexDataTypeElem) {

        ComplexDataTypeRefResolver refResolver = null;

        try {
            Object clazz = complexDataTypeElem
                    .createExecutableExtension(REFERENCE_RESOLVER_ATTR);

            if (clazz instanceof ComplexDataTypeRefResolver) {
                refResolver = (ComplexDataTypeRefResolver) clazz;
            }

        } catch (CoreException e) {
        }

        if (refResolver == null) {
            XpdResourcesUIActivator
                    .getDefault()
                    .getLogger()
                    .error(
                            COMPLEX_DATATYPE_EXTPOINT_ID
                                    + ": Incorrectly defined extension - failed loading resolver class"); //$NON-NLS-1$
        }

        return refResolver;
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
            XpdResourcesUIActivator.getDefault().getLogger().error(
                    contributerId
                            + ": " //$NON-NLS-1$
                            + COMPLEX_DATATYPE_EXTPOINT_ID
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
                String contributerId = element.getDeclaringExtension()
                        .getContributor().getName();
                XpdResourcesUIActivator
                        .getDefault()
                        .getLogger()
                        .error(
                                contributerId
                                        + ": " //$NON-NLS-1$
                                        + COMPLEX_DATATYPE_EXTPOINT_ID
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
                String contributerId = element.getDeclaringExtension()
                        .getContributor().getName();
                XpdResourcesUIActivator.getDefault().getLogger().error(
                        contributerId
                                + ": " //$NON-NLS-1$
                                + COMPLEX_DATATYPE_EXTPOINT_ID
                                + ": Incorrectly defined extension - missing " //$NON-NLS-1$
                                + name + " attribute"); //$NON-NLS-1$
                retAttribute = null;
            } else {
                retAttribute = ""; //$NON-NLS-1$
            }
        }

        return retAttribute;
    }

    /**
     * Filter class that includes any object whose fully qualified name is
     * contained in the given set of names.
     * 
     * @author aallway
     * 
     */
    private static class EObjIncFilter extends ViewerFilter {

        private Set<String> classNames;

        /**
         * 
         */
        public EObjIncFilter(Set<String> classNames) {
            this.classNames = classNames;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            boolean match = true; // If not an eobject then we don't deal with
            // it.

            if (element instanceof EObject
                    && !(element instanceof SpecialFolder)) {
                EObject eObj = (EObject) element;

                String className = eObj.eClass().getInstanceClassName();

                match = classNames.contains(className);
            }

            return match;
        }

    }

    /**
     * Filter class that excludes any object whose fully qualified name is
     * contained in the given set of names.
     * 
     * @author aallway
     */
    private static class EObjExcFilter extends ViewerFilter {

        private Set<String> classNames;

        /**
         * 
         */
        public EObjExcFilter(Set<String> classNames) {
            this.classNames = classNames;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            boolean match = true; // If not an eobject then we don't deal with
            // it.

            if (element instanceof EObject
                    && !(element instanceof SpecialFolder)) {
                EObject eObj = (EObject) element;

                String className = eObj.eClass().getInstanceClassName();

                match = !(classNames.contains(className));
            }

            return match;
        }

    }

}
