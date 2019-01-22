/*
 */

package com.tibco.xpd.ui.complexdatatype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Small data class for storing complex data type extension point info. This is
 * designed to compile the filters for all the given types.
 * <p>
 * This class should not be constructed by anything else except
 * {@link ComplexDataTypeExtPointHelper}.
 * 
 * @author aallway
 */
public final class ComplexDataTypesMergedInfo {

    /** The list of filters from extension point. */
    private List<ViewerFilter> viewerFilters = new ArrayList<ViewerFilter>();

    /** The reference resolver. */
    private List<ComplexDataTypeRefResolver> refResolvers =
            new ArrayList<ComplexDataTypeRefResolver>();

    /**
     * <b>This class should not be constructed by anything except
     * {@link ComplexDataTypeExtPointHelper}.<b>
     */
    ComplexDataTypesMergedInfo() {
    }

    /**
     * @return the viewerFilters
     */
    public List<ViewerFilter> getViewerFilters() {
        return viewerFilters;
    }

    /**
     * @return the refResolvers
     */
    public List<ComplexDataTypeRefResolver> getRefResolvers() {
        return refResolvers;
    }

    /**
     * Add a viewer filter to list.
     * 
     * @param filter
     */
    void addViewerFilter(ViewerFilter filter) {
        getViewerFilters().add(filter);
    }

    /**
     * Add a reference resolver.
     * 
     * @param resolver
     */
    void addReferenceResolver(ComplexDataTypeRefResolver resolver) {
        getRefResolvers().add(resolver);
    }

    /**
     * Get a reference for the given complex data type object.
     * <p>
     * The reference will be constructed by the <b>first</b> complex data type
     * extensoin point reference resolver that recognises the given object.
     * 
     * @param complexDataTypeObject
     *            Object representing the complex data type definition.
     * @param project
     *            that reference is relative to.
     * 
     * @return Reference or null if there are nop resolvers that handle given
     *         complex data type object.
     */
    public ComplexDataTypeReference getComplexDataTypeReference(
            Object complexDataTypeObject, IProject project) {
        ComplexDataTypeReference reference = null;

        for (Iterator<ComplexDataTypeRefResolver> iter =
                getRefResolvers().iterator(); iter.hasNext();) {
            ComplexDataTypeRefResolver refResolver = iter.next();

            if (refResolver.isValidComplexDataType(complexDataTypeObject)) {
                reference =
                        refResolver
                                .complexDataTypeToReference(complexDataTypeObject,
                                        project);
                if (reference != null) {
                    break;
                }
            }
        } // Next resolver

        return reference;

    }

    /**
     * Get the object represented by the given complex data type reference.
     * <p>
     * The object will be returned by the first complex data type reference
     * resolver that recognises the reference.
     * 
     * @param complexDataTypeReference
     *            Reference representing a complex data type object.
     * @param project
     *            that reference is relative to.
     * 
     * @return Complex data type object or null if there are no reference
     *         resolvers that handle given reference or the object does not
     *         exist.
     */
    public Object getComplexDataTypeFromReference(
            ComplexDataTypeReference complexDataTypeReference, IProject project) {

        Object resolvedObject = null;

        if (isValidReference(complexDataTypeReference)) {
            // Find a reference resolver that understands reference and can
            // convert to object.
            for (Iterator<ComplexDataTypeRefResolver> iter =
                    getRefResolvers().iterator(); iter.hasNext();) {
                ComplexDataTypeRefResolver refResolver = iter.next();

                resolvedObject =
                        refResolver
                                .referenceToComplexDataType(complexDataTypeReference,
                                        project);
                if (resolvedObject != null) {
                    break;
                }
            }
        }

        return resolvedObject;
    }

    /**
     * Check whether given object is valid for any of the complex data type
     * reference resolvers.
     * 
     * @param complexDataType
     *            Object to test
     * 
     * @return true if there is a complex data type extension point that handles
     *         this object type.
     */
    public boolean isValidComplexDataType(Object complexDataType) {
        boolean isValid = false;

        for (Iterator<ComplexDataTypeRefResolver> iter =
                getRefResolvers().iterator(); iter.hasNext();) {
            ComplexDataTypeRefResolver refResolver = iter.next();

            if (refResolver.isValidComplexDataType(complexDataType)) {
                isValid = true;
                break;
            }
        } // Next resolver

        return isValid;
    }

    /**
     * Get the complex data type descriptive name for given object reference.
     * 
     * @param complexDataTypeReference
     * @param project
     * @return
     */
    public String getComplexDataTypeDescriptiveName(
            ComplexDataTypeReference complexDataTypeReference, IProject project) {
        String name = ""; //$NON-NLS-1$

        if (isValidReference(complexDataTypeReference)) {
            // Find a reference resolver that understands reference and can
            // convert to object.
            for (Iterator<ComplexDataTypeRefResolver> iter =
                    getRefResolvers().iterator(); iter.hasNext();) {
                ComplexDataTypeRefResolver refResolver = iter.next();

                Object resolvedObject =
                        refResolver
                                .referenceToComplexDataType(complexDataTypeReference,
                                        project);
                if (resolvedObject != null) {
                    name = refResolver.getComplexDataTypeName(resolvedObject);
                }
            }
        }

        return name;
    }

    /**
     * Check if the given reference is worthy of passing to reference resolvers.
     * 
     * @param complexDataTypeReference
     * @return
     */
    private boolean isValidReference(
            ComplexDataTypeReference complexDataTypeReference) {
        if (complexDataTypeReference != null) {
            String nameSpace = complexDataTypeReference.getNameSpace();
            String location = complexDataTypeReference.getLocation();
            String xRef = complexDataTypeReference.getXRef();

            // At least one thing must be filled in!
            int len1 = (nameSpace == null) ? 0 : nameSpace.length();
            int len2 = (location == null) ? 0 : location.length();
            int len3 = (xRef == null) ? 0 : xRef.length();

            if ((len1 + len2 + len3) > 0) {
                return true;
            }
        }
        return false;
    }
}
