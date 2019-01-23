/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.Collection;

/**
 * @author nwilson
 */
public interface IErrorProvider {

    /**
     * @param mapping
     *            The mapping to check.
     * @return problem severity
     */
    ErrorSeverity getSeverityForMappingSource(Mapping mapping);

    /**
     * @param mapping
     *            The mapping to check.
     * @return problem severity
     */
    ErrorSeverity getSeverityForMappingTarget(Mapping mapping);

    /**
     * @param mapping
     *            The mapping to check.
     * 
     * @return The error messages associated with the mapping source.
     */
    MappingError getErrorsForMappingSource(Mapping mapping);

    /**
     * @param mapping
     *            The mapping to check.
     * 
     * @return The error messages associated with the mapping target.
     */
    MappingError getErrorsForMappingTarget(Mapping mapping);

    /**
     * @param sourceObject
     *            An object that appears in the source tree (not necessarily
     *            used in an existing mapping).
     * 
     * @return problem severity
     */
    ErrorSeverity getSeverityForSourceObject(Object sourceObject);

    /**
     * @param sourceTreeContentObject
     *            An object that appears in the source tree (not necessarily
     *            used in an existing mapping).
     * 
     * @return The error message associated with the source tree.
     */
    Collection<String> getErrorsForSourceObject(Object sourceTreeContentObject);

    /**
     * @param targetObject
     *            An object that appears in the source target (not necessarily
     *            used in an existing mapping).
     * 
     * @return problem severity
     */
    ErrorSeverity getSeverityForTargetObject(Object targetObject);

    /**
     * @param targetTreeContentObject
     *            An object that appears in the target tree (not necessarily
     *            used in an existing mapping).
     * 
     * @return The error messages associated with the target tree.
     */
    Collection<String> getErrorsForTargetObject(Object targetTreeContentObject);

    /**
     * When dealing with problem markers on the source or target tree items it
     * is necessary to recurs down thru the tree items so that problem markers
     * can be added to parent items that have descendant with problems.
     * <p>
     * It is <b>vital</b> that when there is a LOOP in the schema (element of
     * type X has child of type Y has child of type X etc) that we protect
     * against infinite recursion.
     * <p>
     * This method is be used for protecting against that and it should return
     * an object that can be .equal()'d with other element types in the tree.
     * 
     * @param sourceTreeContentObject
     * 
     * @return Data Type of tree element to be used for protection against
     *         looping nested type recursion
     */
    Object getSourceObjectTypeForRecursionComparison(
            Object sourceTreeContentObject);

    /**
     * When dealing with problem markers on the source or target tree items it
     * is necessary to recurs down thru the tree items so that problem markers
     * can be added to parent items that have descendant with problems.
     * <p>
     * It is <b>vital</b> that when there is a LOOP in the schema (element of
     * type X has child of type Y has child of type X etc) that we protect
     * against infinite recursion.
     * <p>
     * This method is be used for protecting against that and it should return
     * an object that can be .equal()'d with other element types in the tree.
     * 
     * @param sourceTreeContentObject
     * 
     * @return Data Type of tree element to be used for protection against
     *         looping nested type recursion
     */
    Object getTargetObjectTypeForRecursionComparison(
            Object targetTreeContentObject);

}
