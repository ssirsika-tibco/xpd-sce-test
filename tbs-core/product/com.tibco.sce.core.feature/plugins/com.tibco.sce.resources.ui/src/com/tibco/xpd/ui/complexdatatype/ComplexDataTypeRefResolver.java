/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.complexdatatype;

import org.eclipse.core.resources.IProject;

/**
 * Interface to support the com.tibco.xpd.complexDataType extension point.
 * 
 * @author aallway
 * 
 */
public interface ComplexDataTypeRefResolver {

    /**
     * Return true if the given object type (for instance, selected by user from
     * picker) is valid for this complex data type.
     * 
     * @param complexDataType
     *            Complex data type to validate.
     * 
     * @return true if the object is a complex data type handled by this
     *         extension point.
     */
    boolean isValidComplexDataType(Object complexDataType);

    /**
     * Return the Complex data type reference information <b>relative to the
     * given project</b> for the complex data type represented by the given
     * object.
     * <p>
     * This ComplexDataTypeReference returned should be consistent and valid for
     * use with the
     * {@link #referenceToComplexDataType(ComplexDataTypeReference, IProject)}
     * method.
     * </p>
     * <p>
     * <b>Note:</b> This method will only be called if
     * {@link ComplexDataTypeRefResolver#isValidComplexDataType(Object)} returns
     * true for given object.
     * </p>
     * 
     * @param complexDataType
     *            Complex data type to resolve to reference.
     * @param project
     *            Create reference relative to this project.
     * 
     * @return {@link ComplexDataTypeReference} describing the reference to
     *         given object.
     */
    ComplexDataTypeReference complexDataTypeToReference(Object complexDataType,
            IProject project);

    /**
     * Return that represents the complex data type object rerferenced by the
     * given ComplexDataTypeReference object <b>relative to the given
     * project</b>.
     * <p>
     * The {@link ComplexDataTypeReference} will have its path and ref
     * attributes set to the same values as that returned by
     * {@link #complexDataTypeToReference(Object, IProject)}
     * </p>
     * 
     * @param complexDataTypeReference
     *            Reference to resolve to complex data type.
     * @param project
     *            Create reference relative to this project.
     * 
     * @return The object referenced by the given
     *         {@link ComplexDataTypeReference}
     */
    Object referenceToComplexDataType(
            ComplexDataTypeReference complexDataTypeReference, IProject project);

    /**
     * Get the descriptive name for this particular instance of complex data
     * type.
     * 
     * @param complexDataType
     * @return
     */
    String getComplexDataTypeName(Object complexDataType);

}
