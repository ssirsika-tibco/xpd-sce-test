/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Used by the Bpm2CeProcessScriptMigration to provide utilities for introspecting the BOM models.
 *
 * @author pwatson
 * @since 2 Aug 2019
 */
class BomInspector {
    /**
     * Searches the given project for eligible BOMs and return a collection {@link Model}. If no BOMs can be found the
     * result will be an empty collection.
     * 
     * @param aProject
     *            the project to be searched.
     * @return the collection of {@link Model}s for the project's BOMs.
     * @throws CoreException
     */
    public static Collection<Model> getBomModels(IProject aProject) throws CoreException {
        if (aProject == null) {
            return Collections.emptyList();
        }

        // look for all nested BOM model resources
        Collection<IResource> bomResources = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(aProject,
                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                BOMResourcesPlugin.BOM_FILE_EXTENSION,
                false);
        if (bomResources == null) {
            return Collections.emptyList();
        }

        Collection<Model> result = new ArrayList<>();
        for (IResource resource : bomResources) {
            Model model = BomInspector.getBomModel(resource);
            if (model != null) {
                // add the model to the result
                result.add(model);
            }
        }

        return result;
    }

    /**
     * Gets the BOM root model for the provided .bom file reference.
     * 
     * @param aBomFile
     *            the IResource that identifies the file containing the BOM model.
     * @return {@link Model} for the provided BOM file (or <code>null</code> if the model can't be loaded).
     */
    public static Model getBomModel(IResource aBomFile) {
        WorkingCopy bomWorkingCopy = WorkingCopyUtil.getWorkingCopy(aBomFile);
        // Obtain BOM Model
        if (bomWorkingCopy instanceof BOMWorkingCopy
                && ((BOMWorkingCopy) bomWorkingCopy).getRootElement() instanceof Model
                && ((BOMWorkingCopy) bomWorkingCopy).getRootElement().eResource() != null) {
            return (Model) ((BOMWorkingCopy) bomWorkingCopy).getRootElement();
        }

        return null;
    }

    /**
     * Returns a collection those elements of the given BOM package (of which Model is a sub-class) that are
     * assignment-compatible with the given class. If no matching elements are found, the return value will be an empty
     * collection.
     * 
     * @param aBomPackage
     *            the BOM package to be searched.
     * @param aRequiredClass
     *            the class to which the returned elements must be assignment-compatible.
     * @return the identified elements. This will never be <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    public static <T extends PackageableElement> Collection<T> getFields(org.eclipse.uml2.uml.Package aBomPackage,
            final Class<T> aRequiredClass) {
        EList<PackageableElement> source = aBomPackage.getPackagedElements();
        if ((source == null) || (source.isEmpty())) {
            return Collections.emptyList();
        }

        Collection<T> result = new ArrayList<>();
        for (PackageableElement element : source) {
            if (aRequiredClass.isInstance(element)) {
                result.add((T) element);
            }

            // traverse nested packages
            if (element instanceof org.eclipse.uml2.uml.Package) {
                result.addAll(getFields((org.eclipse.uml2.uml.Package) element, aRequiredClass));
            }
        }

        return result;
    }
}
