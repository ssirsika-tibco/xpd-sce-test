/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * XSD Validation shows an error if an external reference has been made in the
 * Project containing the BOM file.
 * 
 * @author glewis
 */
public class ExternalReferenceNotAllowedRule extends AbstractXsdRule {

    @Override
    public Class<?> getTargetClass() {
        return Model.class;
    }

    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        Model model = (Model) o;
        String uri = model.eResource().getURIFragment(model);
        IFile bomFile = WorkspaceSynchronizer.getFile(model.eResource());
        Set<IProject> refProjects =
                ProjectUtil
                        .getReferencedProjectsHierarchy(bomFile.getProject(),
                                new HashSet<IProject>());
        if (refProjects != null && refProjects.size() > 0) {
            WorkingCopy tempWC =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
            if (tempWC instanceof BOMWorkingCopy) {
                BOMWorkingCopy bomWorkingCopy = (BOMWorkingCopy) tempWC;
                ArrayList<IResource> allDependenciesForModel =
                        new ArrayList<IResource>();
                processDependencies(bomWorkingCopy.getDependency(),
                        allDependenciesForModel);

                for (IResource resource : allDependenciesForModel) {
                    if (!resource.getProject().equals(bomFile.getProject())) {
                        scope
                                .createIssue(XsdIssueIds.EXTERNAL_REF_NOT_ALLOWED,
                                        BOMValidationUtil
                                                .getLocation((NamedElement) o),
                                        uri,
                                        Collections.singleton(bomFile
                                                .getProject().getName()));
                        return;
                    }
                }
            }
        }
    }

    private static void processDependencies(List<IResource> dependencies,
            List<IResource> allDependenciesForModel) {
        if (dependencies != null && dependencies.size() > 0) {
            Iterator<IResource> iterator = dependencies.iterator();
            while (iterator.hasNext()) {
                IResource resource = iterator.next();
                if (resource instanceof IFile) {
                    IFile file = (IFile) resource;
                    WorkingCopy tempWC =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(file);
                    if (tempWC instanceof BOMWorkingCopy) {
                        BOMWorkingCopy bomWorkingCopy = (BOMWorkingCopy) tempWC;
                        allDependenciesForModel.add(resource);
                        List<IResource> tmpDependencies =
                                bomWorkingCopy.getDependency();
                        processDependencies(tmpDependencies,
                                allDependenciesForModel);
                    }
                }
            }
        }
    }

}
