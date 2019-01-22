/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.utils.DependencyAnalyzer;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.rules.BOMValidationRule;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * @author glewis
 * 
 */
public class CyclicDependencyRule extends BOMValidationRule {
    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Model.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        Model model = (Model) o;
        Resource resource = model.eResource();
        boolean hasIssue = false;
        if (resource != null) {
            // get all generalizations, properties and operations for current
            // bom resource so we can use to check what other bom
            // references this bom resource has
            IFile source = WorkspaceSynchronizer.getFile(resource);
            if (source != null) {
                ArrayList<IFile> arrFiles = new ArrayList<IFile>();
                arrFiles.add(source);
                try {
                    DependencyAnalyzer dependencyAnalyzer =
                            new DependencyAnalyzer(arrFiles);
                    dependencyAnalyzer.getResult();
                } catch (IllegalArgumentException e) {
                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(source);
                    EObject tmpModel = wc.getRootElement();
                    createIssue(scope,
                            ((Model) tmpModel).getName(),
                            resource.getURIFragment(tmpModel));
                    hasIssue = true;
                }
            }
        }
        // if no issues yet then we check cyclic dependencies for the inner
        // packages also
        if (!hasIssue) {
            Set<Package> allNestedPackages = new HashSet<Package>();
            getAllNestedPackages(model, allNestedPackages);
            for (Package tmpPkg : allNestedPackages) {
                for (PackageableElement packageableElement : tmpPkg
                        .getPackagedElements()) {
                    if (packageableElement instanceof Classifier) {
                        Classifier classifier = (Classifier) packageableElement;
                        for (Classifier tmpClassifier : classifier
                                .getGenerals()) {
                            // if classifiers type is not in same package we
                            // check that package to see if it is referencing
                            // our current package also
                            Package targetPkg = tmpClassifier.getPackage();
                            if (checkCyclicDependenciesInPackages(scope,
                                    resource,
                                    tmpPkg,
                                    targetPkg)) {
                                return;
                            }
                        }
                        if (classifier instanceof org.eclipse.uml2.uml.Class) {
                            org.eclipse.uml2.uml.Class tmpClass =
                                    (org.eclipse.uml2.uml.Class) classifier;
                            EList<Property> ownedAttributes =
                                    tmpClass.getOwnedAttributes();
                            if (checkCyclicDependenciesForProperties(scope,
                                    resource,
                                    tmpPkg,
                                    ownedAttributes)) {
                                return;
                            }
                            for (Operation operation : tmpClass.getOperations()) {
                                Type type = operation.getType();
                                if (checkOperationAndParamTypes(scope,
                                        resource,
                                        tmpPkg,
                                        type)) {
                                    return;
                                }
                                for (Parameter parameter : operation
                                        .getOwnedParameters()) {
                                    type = parameter.getType();
                                    if (checkOperationAndParamTypes(scope,
                                            resource,
                                            tmpPkg,
                                            type)) {
                                        return;
                                    }
                                }
                            }
                        }
                    } else if (packageableElement instanceof Association) {
                        Association association =
                                (Association) packageableElement;
                        // Check if this association type is an:
                        // Aggregation, Association, Composition
                        // Only case about cyclic dependencies in Compositions
                        if (UML2ModelUtil.getAggregationType(association) == AggregationKind.COMPOSITE_LITERAL) {
                            if (checkCyclicDependenciesForProperties(scope,
                                    resource,
                                    tmpPkg,
                                    association.getMemberEnds())) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param scope
     * @param resource
     * @param tmpPkg
     * @param type
     * @return
     */
    private boolean checkOperationAndParamTypes(IValidationScope scope,
            Resource resource, Package tmpPkg, Type type) {
        if (type != null) {
            Package targetPkg = type.getPackage();
            if (checkCyclicDependenciesInPackages(scope,
                    resource,
                    tmpPkg,
                    targetPkg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param scope
     * @param resource
     * @param tmpPkg
     * @param targetPkg
     * @return
     */
    private boolean checkCyclicDependenciesInPackages(IValidationScope scope,
            Resource resource, Package tmpPkg, Package targetPkg) {
        if (targetPkg != null && !targetPkg.equals(tmpPkg)) {
            if (isReferencingPackage(targetPkg, tmpPkg)) {
                createIssue(scope,
                        tmpPkg.getName(),
                        resource.getURIFragment(tmpPkg));
                return true;
            }
        }
        return false;
    }

    /**
     * @param scope
     * @param resource
     * @param tmpPkg
     * @param properties
     * @return
     */
    private boolean checkCyclicDependenciesForProperties(
            IValidationScope scope, Resource resource, Package tmpPkg,
            EList<Property> properties) {
        for (Property property : properties) {
            if (property.getType() != null) {
                // if property type is not in same package we check that package
                // to see if it is referencing our current package also
                Package targetPkg = property.getType().getPackage();
                if (checkCyclicDependenciesInPackages(scope,
                        resource,
                        tmpPkg,
                        targetPkg)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void createIssue(IValidationScope scope, String name,
            String fragment) {
        scope.createIssue(GenericIssueIds.CYCLIC_DEPENDENCY, name, fragment);
    }

    /**
     * Goes deep down the UML to find all packages in model
     * 
     * @param tmpPackage
     * @param allNestedPackages
     */
    protected void getAllNestedPackages(Package tmpPackage,
            Set<Package> allNestedPackages) {
        if (tmpPackage.getNestedPackages().size() > 0) {
            allNestedPackages.addAll(tmpPackage.getNestedPackages());
            Iterator<Package> iter = tmpPackage.getNestedPackages().iterator();
            while (iter.hasNext()) {
                Package nestedPkg = iter.next();
                getAllNestedPackages(nestedPkg, allNestedPackages);
            }
        }
    }

    /**
     * @param targetPkg
     * @param sourcePkg
     * @return
     */
    private boolean isReferencingPackage(Package targetPkg, Package sourcePkg) {
        for (PackageableElement packageableElement : targetPkg
                .getPackagedElements()) {
            if (packageableElement instanceof Classifier) {
                Classifier classifier = (Classifier) packageableElement;
                for (Classifier tmpClassifier : classifier.getGenerals()) {
                    if (tmpClassifier.getPackage() != null
                            && tmpClassifier.getPackage().equals(sourcePkg)) {
                        return true;
                    }
                }
                if (classifier instanceof org.eclipse.uml2.uml.Class) {
                    org.eclipse.uml2.uml.Class tmpClass =
                            (org.eclipse.uml2.uml.Class) classifier;
                    EList<Property> ownedAttributes =
                            tmpClass.getOwnedAttributes();
                    for (Property property : ownedAttributes) {
                        // Check that this is not an Association or Aggregation
                        Association association = property.getAssociation();
                        if ((association == null)
                                || (UML2ModelUtil
                                        .getAggregationType(association) == AggregationKind.COMPOSITE_LITERAL)) {
                            Type type = property.getType();
                            if (type != null && null != type.getPackage()
                                    && type.getPackage().equals(sourcePkg)) {
                                return true;
                            }
                        }
                    }
                    for (Operation operation : tmpClass.getOperations()) {
                        Type type = operation.getType();
                        if (null != type && type.getPackage().equals(sourcePkg)) {
                            return true;
                        }
                        for (Parameter parameter : operation
                                .getOwnedParameters()) {
                            type = parameter.getType();
                            if (type.getPackage().equals(sourcePkg)) {
                                return true;
                            }
                        }
                    }
                }
            } else if (packageableElement instanceof Association) {
                Association association = (Association) packageableElement;
                // Check if this association type is an:
                // Aggregation, Association, Composition
                // Only case about cyclic dependencies in Compositions
                if (UML2ModelUtil.getAggregationType(association) == AggregationKind.COMPOSITE_LITERAL) {
                    for (Property property : association.getMemberEnds()) {
                        if (property.getType() != null
                                && property.getType().getPackage()
                                        .equals(sourcePkg)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
