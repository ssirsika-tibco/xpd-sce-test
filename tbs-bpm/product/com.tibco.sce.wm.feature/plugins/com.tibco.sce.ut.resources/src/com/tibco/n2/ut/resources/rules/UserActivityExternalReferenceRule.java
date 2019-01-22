/*
* ENVIRONMENT:    Java Generic
*
* COPYRIGHT:      (C) 2008 TIBCO Software Inc
*/
package com.tibco.n2.ut.resources.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class UserActivityExternalReferenceRule extends ProcessValidationRule
{
	// TODO: !!!!!!!!!!!!!! ATTENTION !!!!!!!!!!!!!!!!!!!
	// This Validation Rule is currently not enabled in the plugin.xml file
	// It was early work for the CDS feature and will be enabled when it is required
	// This was copied out of the original validation code in UserActivityConfigurationModelBuilder
	
    private static final String ONLY_COMPOSITION = "n2.ut.onlyComposition"; //$NON-NLS-1$
    	// Parameter '%s' refers to BOM class '%s', which has invalid associations.
    	// Only outward composition associations are allowed. Invalid association(s) found: %s
    
    private ComplexDataTypesMergedInfo _complexTypesInfo;

	@Override
	protected void validateFlowContainer( Process           process,
										  EList<Activity>   activities,
										  EList<Transition> transitions )
	{
        for (Activity activity : activities)
        {
            Object apsObj = Xpdl2ModelUtil.getOtherElement(activity,
            		XpdExtensionPackage.eINSTANCE.getDocumentRoot_AssociatedParameters());

            if (apsObj instanceof AssociatedParameters) {
            	// For each parameter...
            	for (Object apObj : ((AssociatedParameters) apsObj).getAssociatedParameter())
            	{
            		if (apObj instanceof AssociatedParameter) {
            			// Validate that external references adhere to our rules for
            			// use as a task parameter.
            			validateExternalReference(activity, ((AssociatedParameter)apObj).getFormalParam());
            		}
            	}
            }
        }
	}

    private void validateExternalReference( Activity activity, String name )
    {
        DataField field = getDataFieldByName(activity.getProcess(), name);
        if (field != null)
        {
            DataType dataType = field.getDataType();
            if (dataType instanceof ExternalReference)
            {
                ExternalReference extRef = (ExternalReference) dataType;
                if (_complexTypesInfo == null) {
                    // We only call this once, as it's relatively expensive.
                    _complexTypesInfo = ComplexDataTypeExtPointHelper.getAllComplexDataTypesMergedInfo();
                }

                ComplexDataTypeReference complexDataTypeRef = xpdl2RefToComplexDataTypeRef(extRef);
                if (complexDataTypeRef != null) {
                    // TODO Need to get project reference properly
                    // This will only work is the hard coded project name matches
                    // the process project.
                    // Ideally, we need to be passed the process project. If
                    // that isn't available, we would
                    // have to try each project in turn until we find one that
                    // can resolve our reference
                    // (and then maintain a reference to it to avoid hunting
                    // twice)
                    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                    IProject project = root.getProject("BPM1");

                    Object objectForDataType = _complexTypesInfo.getComplexDataTypeFromReference(
                    		complexDataTypeRef, project);
                    // If the external reference is to a UML class in a BOM, we
                    // need to apply our validation.
                    if (objectForDataType instanceof org.eclipse.uml2.uml.Class) {
                        List<String> invalidAssociationNames = new ArrayList<String>();
                        org.eclipse.uml2.uml.Class umlClass = (org.eclipse.uml2.uml.Class) objectForDataType;

                        // Our validation concerns the association(s) linked to
                        // a class
                        for (Association assoc : umlClass.getAssociations()) {
                            // An association is only considered valid if it's a
                            // composition
                            // relationship with our class on the originating
                            // end.
                            boolean isValid = false;
                            EList<Property> memberEnds = assoc.getMemberEnds();
                            Property end0 = memberEnds.get(0);
                            Property end1 = memberEnds.get(1);
                            if (end0.getAggregation() == AggregationKind.COMPOSITE_LITERAL
                                    && end1.getAggregation() == AggregationKind.NONE_LITERAL) {
                                // The composition association goes from 1 to 0.
                                // So, valid if our class is on the 1 end.
                                isValid = end1.getType().equals(umlClass);
                            } else if (end0.getAggregation() == AggregationKind.NONE_LITERAL
                                    && end1.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
                                // The composition association goes from 0 to 1.
                                // So, valid if our class is on the 0 end.
                                isValid = end0.getType().equals(umlClass);
                            }

                            if (!isValid) {
                                invalidAssociationNames.add(assoc.getName());
                                System.out.println("Association "
                                                + assoc.getName()
                                                + " is not a composition originating from this class");
                            }
                        }

                        if (!invalidAssociationNames.isEmpty())
                        {
                    		addIssue(ONLY_COMPOSITION, activity);
                        }
                    }
                }
            }
        }
    }

    private DataField getDataFieldByName(Process process, String name) {
        // TODO Would need to look at package-level data fields too (or are they
        // inherited?)
        DataField field = null;
        for (Object fieldObj : process.getDataFields())
		{
        	if (fieldObj instanceof DataField) {
        		DataField aField = (DataField) fieldObj;
        		if (aField.getName().equals(name)) {
        			field = aField;
        		}
        	}
		}

        // If we failed to find the field, it probably means it's defined up at
        // the package level
        if (field == null) {
        	for (Object fieldObj : process.getPackage().getDataFields())
			{
        		if (fieldObj instanceof DataField) {
        			DataField aField = (DataField) fieldObj;
        			if (aField.getName().equals(name)) {
        				field = aField;
        			}
        		}
			}
        }

        return field;
    }

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

	
}
