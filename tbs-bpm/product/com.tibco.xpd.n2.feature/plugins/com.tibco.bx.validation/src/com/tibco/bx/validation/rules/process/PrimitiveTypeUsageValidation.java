/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;

/**
 * This Validation class ensures that the primitive types in a BOM are not used
 * to create data fields or parameters
 *
 */
public class PrimitiveTypeUsageValidation extends ProcessValidationRule {

    private static final String PRIMITIVE_DATA_FIELD = "cds.process.primitive.data.field"; //$NON-NLS-1$
    private static final String ENUM_DATA_FIELD = "cds.process.enum.data.field"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process, org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities,
            EList<Transition> transitions) {

        List<ProcessRelevantData> allProcessRelevantData = ProcessInterfaceUtil.getAllProcessRelevantData(process);

        // Check to see if there is any data to process before doing all the process global work
        if( ! allProcessRelevantData.isEmpty() ) {
            ComplexDataTypesMergedInfo compMergeInfo = ComplexDataTypeExtPointHelper.getAllComplexDataTypesMergedInfo();

            IProject project = WorkspaceSynchronizer.getFile(process.eResource()).getProject();

            // Check the list of parameters and data fields in the process
            // to see if there are any that reference a BOM Primitive Type
            for ( ProcessRelevantData processRelevantData : allProcessRelevantData ) {
                DataType dataType = processRelevantData.getDataType();
                if (dataType instanceof ExternalReference)
                {
                    // Check to see if the external reference is a BOM element
                    ComplexDataTypeReference complexDataTypeRef = xpdl2RefToComplexDataTypeRef((ExternalReference)dataType);
                    
                    if( complexDataTypeRef != null ) {

                        Object objectForDataType = compMergeInfo.getComplexDataTypeFromReference(complexDataTypeRef, project);
                        
                        // Check if this is an external reference to a primitive type
                        if( isPrimitiveType( (EObject)objectForDataType ) )
                        {
                            addIssue(PRIMITIVE_DATA_FIELD, processRelevantData);
                        }
                        // Check if this is an external reference to a enum type
                        if( isEnumType( (EObject)objectForDataType ) )
                        {
                            addIssue(ENUM_DATA_FIELD, processRelevantData);
                        }
                    }
                }
            }            
        }
    }

    /**
     * Checks if the external reference is a BOM element and returns it
     * 
     * @param extRef    The external reference
     * @return
     */
    private ComplexDataTypeReference xpdl2RefToComplexDataTypeRef( ExternalReference extRef )
    {
        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        // Must have at least some info defined.
        int length = (nameSpace == null ? 0 : nameSpace.length());
        length += (location == null ? 0 : location.length());
        length += (xRef == null ? 0 : xRef.length());
        if (length == 0) {
            return null;
        }
        return new ComplexDataTypeReference(location, xRef, nameSpace);
    }

    /**
     * Get the {@link PrimitiveType} type of this or related to this object.
     * 
     * @param target
     *            the element being validated
     * @return
     */
    private boolean isPrimitiveType(EObject target) {
        boolean isPrimitiveType = false;
        if (target instanceof PrimitiveType) {
            isPrimitiveType = true;
        } else if (target instanceof TypedElement) {
            Type type = ((TypedElement) target).getType();
            if (type instanceof PrimitiveType) {
                isPrimitiveType = true;
            }
        }
        return isPrimitiveType;
    }
    
    /**
     * Check to see if the type is an Enumeration
     * 
     * @param target     the element being validated
     * @return
     */
    private boolean isEnumType(EObject target) {
        boolean isEnumType = false;
        if ((target instanceof Enumeration) || (target instanceof EnumerationLiteral)) {
            isEnumType = true;
        } else if (target instanceof TypedElement) {
            Type type = ((TypedElement) target).getType();
            if ((type instanceof PrimitiveType) || (target instanceof EnumerationLiteral)) {
                isEnumType = true;
            }
        }
        return isEnumType;
    }
    
}
