/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.util;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.GenericEObjectReferenceResolver;

/**
 * Complex data type reference resolver for complex data types provided by
 * Business Object Modeler.
 * 
 * @author aallway
 * @since 3.2
 */
public class BomComplexDataTypeRefResolver extends
        GenericEObjectReferenceResolver {

    /**
     * @see com.tibco.xpd.resources.ui.complexdatatype.GenericEObjectReferenceResolver#getNameForDataType(org.eclipse.emf.ecore.EObject)
     * 
     * @param obj
     * @return
     */
    @Override
    protected String getNameForDataType(EObject obj) {
        if (isCorrectEObjectType(obj)) {
            return WorkingCopyUtil.getText(obj);
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.complexdatatype.GenericEObjectReferenceResolver#isCorrectEObjectType(java.lang.Object)
     * 
     * @param complexDataType
     * @return
     */
    @Override
    protected boolean isCorrectEObjectType(Object complexDataType) {
        if (complexDataType instanceof org.eclipse.uml2.uml.Class) {
            return true;
        } else if (complexDataType instanceof PrimitiveType) {
            return true;
        } else if (complexDataType instanceof Enumeration) {
            return true;
        }

        return false;
    }

    /**
         * 
         */
    @Override
    protected String getSpecialFolderKind() {
        return BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;
    }

    @Override
    protected IResource getResource(String relativePath, IProject project) {
        IResource resource = super.getResource(relativePath, project);
        if (resource == null) {
            // Look in the references
            try {
                Set<IProject> referencedProjects =
                        ProjectUtil2.getReferencedProjectsHierarchy(project,
                                true);
                if (referencedProjects != null) {
                    for (IProject referencedProject : referencedProjects) {
                        resource =
                                super.getResource(relativePath,
                                        referencedProject);
                        if (resource != null) {
                            break;
                        }
                    }
                }
            } catch (CyclicDependencyException e) {
                // Do nothing
            }
        }
        return resource;
    }

}
