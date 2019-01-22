/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.SetReferencedProjectResolution;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * <p>
 * This rule lists all the problems with required access privileges referenced
 * from business/case service Privileges Configuration Table
 * </p>
 * <p>
 * At the time of writing the problems identified to be reported are
 * <li>To complain if the referenced Organisation Model is not available in the
 * workspace</li>
 * <li>If the referenced privilege is removed from the referenced organisation
 * model, but is present in the xpdl model</li>
 * </p>
 * 
 * 
 * @author bharge
 * @since 14 Aug 2014
 */
public class RequiredAccessPrivilegesRule extends ProcessValidationRule {

    private final String REFERENCED_PRIVILEGE_NOT_FOUND_IN_ORG_MODEL =
            "wm.InvalidPrivilegeReference"; //$NON-NLS-1$

    private final String ISSUE_PRIVILEGE_IN_NON_REFERENCED_PROJ =
            "wm.PrivilegeInNonReferencedProject"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        if (null != process) {

            RequiredAccessPrivileges requiredAccessPrivileges =
                    (RequiredAccessPrivileges) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_RequiredAccessPrivileges());
            if (null != requiredAccessPrivileges) {

                /*
                 * XPD-6970: removing the call to getOMProject because this
                 * method returns project only for the first privilege. So it
                 * will return incorrect OM project.
                 */

                IProject sourceProcessProject =
                        WorkingCopyUtil.getProjectFor(process);

                EList<ExternalReference> privilegeReference =
                        requiredAccessPrivileges.getPrivilegeReference();

                for (ExternalReference externalReference : privilegeReference) {

                    String xref = externalReference.getXref();
                    EObject eObject = OMUtil.getEObjectByID(xref);

                    if (null == eObject) {
                        /*
                         * referenced privilege doesn't exist
                         */
                        addIssue(REFERENCED_PRIVILEGE_NOT_FOUND_IN_ORG_MODEL,
                                process);
                    } else {

                        IProject omProject =
                                WorkingCopyUtil.getProjectFor(eObject);

                        if (omProject == null) {
                            /*
                             * om project doesn't exist or is deleted from the
                             * workspace
                             */

                            addIssue(REFERENCED_PRIVILEGE_NOT_FOUND_IN_ORG_MODEL,
                                    process);
                        } else {
                            /*
                             * check if the OM project is referenced.
                             */
                            if (!isInSameProjectSet(sourceProcessProject,
                                    omProject)) {
                                /*
                                 * XPD-6970: If the OM project is not referenced
                                 * then raise validation with quick fix to add
                                 * project reference.
                                 */

                                Map<String, String> additional =
                                        new HashMap<String, String>();
                                additional
                                        .put(SetReferencedProjectResolution.PROJECTNAME_ADDITIONAL_INFO,
                                                omProject.getName());

                                addIssue(ISSUE_PRIVILEGE_IN_NON_REFERENCED_PROJ,
                                        process,
                                        Collections
                                                .singletonList(((NamedElement) eObject)
                                                        .getName()),
                                        additional);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param referencerProject
     * @param referenceeProject
     * @return true if the two projects are in the same cross-referencing set of
     *         projects or false if not.
     */
    private boolean isInSameProjectSet(IProject referencerProject,
            IProject referenceeProject) {

        if (referencerProject != null && referenceeProject != null) {
            try {
                return ProjectUtil.isProjectReferenced(referencerProject,
                        referenceeProject);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
