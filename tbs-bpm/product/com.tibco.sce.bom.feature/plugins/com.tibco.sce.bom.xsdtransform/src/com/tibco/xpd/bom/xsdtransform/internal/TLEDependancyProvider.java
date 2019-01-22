/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelAttributeWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Calls an extra check to ensure that all top level element and attribute
 * dependencies are brought back.
 * 
 * @author glewis
 * 
 */
public class TLEDependancyProvider implements IWorkingCopyDependencyProvider {

    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Set<IResource> bomResources = new HashSet<IResource>();

        // only need to check if we have a working copy that is Model as root
        // element
        if (wc != null && wc.getRootElement() instanceof Model) {
            EList<?> listAttrs = null;
            EList<?> listElems = null;

            Model model = (Model) wc.getRootElement();
            IFile currentFile = WorkingCopyUtil.getFile(model);

            if (currentFile.getProject() != null) {
                // get all top level attributes
                Stereotype topLevelAttributesStereotype =
                        getAppliedStereotype(model,
                                model,
                                XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTES);
                if (topLevelAttributesStereotype != null) {
                    Object attributes =
                            model
                                    .getValue(topLevelAttributesStereotype,
                                            XsdStereotypeUtils.XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES);
                    if (attributes instanceof EList) {
                        listAttrs = (EList<?>) attributes;
                    }
                }

                // get all top level elements
                Stereotype topLevelAElementsStereotype =
                        getAppliedStereotype(model,
                                model,
                                XsdStereotypeUtils.TOP_LEVEL_ELEMENTS);
                if (topLevelAElementsStereotype != null) {
                    Object elements =
                            model
                                    .getValue(topLevelAElementsStereotype,
                                            XsdStereotypeUtils.XSD_TOP_LEVEL_ELEMENT_ELEMENTS);
                    if (elements instanceof EList) {
                        listElems = (EList<?>) elements;
                    }
                }

                // go through and see if any external BOMS are referenced with
                // these
                // top level attributes
                if (listAttrs != null) {
                    Iterator<?> iterator = listAttrs.iterator();
                    while (iterator.hasNext()) {
                        Object topLevelAttr = iterator.next();
                        TopLevelAttributeWrapper topLevelAttributeWrapper =
                                new TopLevelAttributeWrapper(
                                        (EObject) topLevelAttr);
                        if (topLevelAttributeWrapper.getType() != null) {
                            IFile file =
                                    WorkingCopyUtil
                                            .getFile(topLevelAttributeWrapper
                                                    .getType());
                            if (!currentFile.equals(file)) {
                                bomResources.add(file);
                            }
                        }

                    }
                }

                // go through and see if any external BOMS are referenced with
                // these
                // top level elements
                if (listElems != null) {
                    Iterator<?> iterator = listElems.iterator();
                    while (iterator.hasNext()) {
                        Object topLevelElem = iterator.next();
                        TopLevelElementWrapper topLevelElementWrapper =
                                new TopLevelElementWrapper(
                                        (EObject) topLevelElem);
                        if (topLevelElementWrapper.getType() != null) {
                            IFile file =
                                    WorkingCopyUtil
                                            .getFile(topLevelElementWrapper
                                                    .getType());
                            if (!currentFile.equals(file)) {
                                bomResources.add(file);
                            }
                        }

                    }
                }

                /*
                 * Get the BOM model (if any) that is referenced from any
                 * properties via the XsdBasedProperty -> xRef stereotype value
                 */
                Map<String, IResource> xsdNamespaceToBomFileMap =
                        TransformHelper.getXsdNamespaceToBomFileMapForXsdXrefs(currentFile
                                .getProject(), currentFile);

                for (PackageableElement pe : model.getPackagedElements()) {
                    if (pe instanceof org.eclipse.uml2.uml.Class) {
                        org.eclipse.uml2.uml.Class clazz =
                                (org.eclipse.uml2.uml.Class) pe;

                        EList<Property> allAttributes =
                                clazz.getAllAttributes();
                        for (Property property : allAttributes) {
                            IResource refdBomFile =
                                    TransformHelper
                                            .getXsdBasedXrefBomFile(property,
                                                    xsdNamespaceToBomFileMap);
                            if (refdBomFile != null
                                    && !refdBomFile.equals(currentFile)) {
                                bomResources.add(refdBomFile);
                            }
                        }
                    }
                }
            }
        }

        return bomResources;
    }

    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return BOMWorkingCopy.class;
    }

    /**
     * @param model
     * @param element
     * @param stereotypeName
     * @return
     */
    private Stereotype getAppliedStereotype(Model model, Element element,
            String stereotypeName) {
        Stereotype stereotype = null;
        Iterator<Profile> profilesIter = model.getAppliedProfiles().iterator();
        while (profilesIter.hasNext()) {
            Profile profile = profilesIter.next();
            if (profile.getName()
                    .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                Object obj = profile.getPackagedElement(stereotypeName);
                if (obj instanceof Stereotype) {
                    stereotype = (Stereotype) obj;
                    if (stereotype.getName().equals(stereotypeName)) {
                        Stereotype alreadyAppliedStereotype =
                                element.getAppliedStereotype(stereotype
                                        .getQualifiedName());
                        return alreadyAppliedStereotype;
                    }
                }
            }
        }
        return null;
    }

}
