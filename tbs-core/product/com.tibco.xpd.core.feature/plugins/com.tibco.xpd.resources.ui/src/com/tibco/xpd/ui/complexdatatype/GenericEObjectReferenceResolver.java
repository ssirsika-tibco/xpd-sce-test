/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.complexdatatype;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Generic Complex Data Type Reference Resolver implementation for EObjects.
 * <p>
 * Handles most of the referencing/dereferencing aspects of an Eobject relative
 * to project. Leaving the sub-class just to validate that a given EObject is
 * correct type to represent the given complex data type and a method that
 * returns the name of the object.
 * 
 * @author aallway
 * 
 */
public abstract class GenericEObjectReferenceResolver implements
        ComplexDataTypeRefResolver {

    /**
     * 
     */
    private static final String URI_DELIMITER = "?";

    /**
     * Ecore util URI separator.
     */
    private static final String URI_SEPARATOR = "/"; //$NON-NLS-1$

    /**
     * Prefix of EcoreUtil returned URI to remove.
     */
    private static final String PLATFORM_RESOURCE = "platform:/resource"; //$NON-NLS-1$

    /**
     * Get descriptive name for complex data type object.
     * 
     * @param obj
     * @return
     */
    protected abstract String getNameForDataType(EObject obj);

    /**
     * Check whether the given EObject is of correct type for the given complex
     * data type object.
     * 
     * @param complexDataType
     * @return true if complexDataType is instance of object that represents
     *         your complex data type.
     */
    protected abstract boolean isCorrectEObjectType(Object complexDataType);

    /**
     * @param complexDataType
     *            Object to create reference for.
     * @param project
     *            Create reference relative to this project.
     * 
     * @see com.tibco.xpd.ui.complexdatatype.ComplexDataTypeRefResolver#complexDataTypeToReference(java.lang.Object,
     *      org.eclipse.core.resources.IProject)
     * 
     * @return Reference to EObject or null on error.
     */
    @Override
    public final ComplexDataTypeReference complexDataTypeToReference(
            Object complexDataType, IProject project) {
        if (complexDataType instanceof EObject) {

            EObject eObj = (EObject) complexDataType;

            if (isCorrectEObjectType(complexDataType)) {
                URI uri = EcoreUtil.getURI(eObj);
                if (uri != null) {
                    String nameSpace = eObj.eClass().getEPackage().getNsURI();
                    String location = uri.trimFragment().toString();

                    /**
                     * XPD-1515: The xref was set incorrectly when a resultset
                     * BOM was created from the DB task. In order to fix that I
                     * could have used
                     * <code>eObj.eResource.getURIFragment(eObj)</code> but
                     * there are some occurences where the eResource of the eObj
                     * is null which would then cause unexpected behavior.
                     * 
                     * 
                     */

                    String xRef = null;
                    String uriFragment = uri.fragment();
                    if (uriFragment != null
                            && uriFragment.contains(URI_DELIMITER)) {
                        xRef =
                                uriFragment.substring(0,
                                        uriFragment.indexOf(URI_DELIMITER));
                    } else {
                        xRef = uriFragment;
                    }
                    location = getSpecialFolderRelativeURI(location, project);
                    return new ComplexDataTypeReference(location, xRef,
                            nameSpace);
                }

            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.complexdatatype.ComplexDataTypeRefResolver#
     * isValidComplexDataType(java.lang.Object)
     */
    @Override
    public final boolean isValidComplexDataType(Object complexDataType) {
        if (complexDataType instanceof EObject) {

            if (isCorrectEObjectType(complexDataType)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.complexdatatype.ComplexDataTypeRefResolver#
     * referenceToComplexDataType
     * (com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference,
     * org.eclipse.core.resources.IProject)
     */
    @Override
    public final Object referenceToComplexDataType(
            ComplexDataTypeReference complexDataTypeReference, IProject project) {

        String location = complexDataTypeReference.getLocation();

        // If the location URI starts with the following string then
        // remove it from the string. This is to fix hang over definitions from
        // Studio 2.0 which used to save "platform:resource/project/" to file.

        location = getSpecialFolderRelativeURI(location, project);
        IResource res = getResource(location, project);
        if (res != null) {
            WorkingCopy wCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(res);

            if (wCopy != null && wCopy.getRootElement() != null) {

                Resource eResource = wCopy.getRootElement().eResource();
                if (eResource != null) {
                    EObject eExternalRef =
                            eResource.getEObject(complexDataTypeReference
                                    .getXRef());

                    if (isCorrectEObjectType(eExternalRef)) {
                        return eExternalRef;
                    }
                }
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.complexdatatype.ComplexDataTypeRefResolver#
     * getComplexDataTypeName(java.lang.Object)
     */
    @Override
    public final String getComplexDataTypeName(Object complexDataType) {
        if (complexDataType instanceof EObject) {

            EObject eObj = (EObject) complexDataType;

            if (isCorrectEObjectType(complexDataType)) {
                return getNameForDataType(eObj);
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Give an EcoreUtil URI for an eObject, make it special folder relative if
     * it is not already.
     * 
     * @param location
     *            EcoreUtil generated URI for EObject.
     * 
     * @return project relative fragment of URI.
     */
    protected String getSpecialFolderRelativeURI(String location,
            IProject project) {
        // If the location URI starts with the following string then
        // remove it from the string
        if (location.startsWith(PLATFORM_RESOURCE)) {
            location = location.substring(PLATFORM_RESOURCE.length() + 1);
            // And remove the next 2 fragments which will be the project and
            // special folder
            int idx = location.indexOf(URI_SEPARATOR);
            if (idx >= 0) {
                int lastIndex = location.indexOf(URI_SEPARATOR, idx + 1);
                location = location.substring(lastIndex + 1);
            }
        } else {
            // Check if first part is project name.
        }
        return location;
    }

    /**
     * 
     * @param relativePath
     *            it is the path relative to the special folder
     * @param project
     * @return
     */
    protected IResource getResource(String relativePath, IProject project) {
        // We store reference location as a URI but special folder util expects
        // normal path so convert back again.
        String decodedPath = URI.decode(relativePath);

        IFile resolveSpecialFolderRelativePath =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        getSpecialFolderKind(),
                        decodedPath);
        return resolveSpecialFolderRelativePath;
    }

    protected abstract String getSpecialFolderKind();

}
