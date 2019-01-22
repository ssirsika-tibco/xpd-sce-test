/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.js.validation.tools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.Package;

/**
 * This Cache will hold the enumerations in scope for the given Process
 * Package.Keeps track of all the enumerations which are in scope for the given
 * Process package. Includes enumerations from the direct as well as indirect
 * referenced BOMs in the process package scope. This cache is used to determine
 * the existence of multiple enumerations with same name in the process package
 * scope, which leads to ambiguity in scripts. The cache is structured for each
 * Process package, as an enumeration can exist in ambiguous situation in one
 * Process package scope, and in unambiguous situation for another process
 * package scope.
 * 
 * @author aprasad
 * @since 19 Jun 2013
 */
public class PackageScopeEnumCache implements IPreProcessor {

    /**
     * Process Package
     */
    private com.tibco.xpd.xpdl2.Package pkg;

    /**
     * Map keeps track of the set of Enumerations with given name. <unqualified
     * enumeration name, set of enumerations with this name>
     */
    private Map<String, Set<Enumeration>> enumCache = null;

    /**
     * Initilized
     * 
     * @param pkg
     */
    public PackageScopeEnumCache(Package pkg) {
        super();
        this.pkg = pkg;
    }

    /**
     * This method initialises the cache for the given process package.
     */
    private void initialiseCache() {
        enumCache = getAllEnumsInProcessPackageScope(pkg);
    }

    /**
     * This method returns the cache for the process package. The returned map
     * contains the set of enumerations with the given name
     * 
     * @return the enumCache , <unqualified enum name, set of enumerations with
     *         this name>
     */
    public Map<String, Set<Enumeration>> getEnumCache() {
        if (enumCache == null) {
            initialiseCache();
        }
        return enumCache;
    }

    /**
     * Searches for enumeration, in the provided BOM resource.
     * 
     * @param enumName
     * @param bomFile
     * @param visitedResources
     * @param enumsMap
     *            , <unqualified enum name, set of enumerations with this name>
     * @return
     */
    private void getEnumsInResource(IFile bomFile,
            Set<IResource> visitedResources,
            Map<String, Set<Enumeration>> enumsMap) {

        if (!visitedResources.contains(bomFile)) {
            visitedResources.add(bomFile);

            if (bomFile instanceof IFile && bomFile.exists()) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);

                if (wc instanceof BOMWorkingCopy
                        && wc.getRootElement() instanceof Model) {
                    Model model = (Model) wc.getRootElement();
                    appendEnumsFromModel(enumsMap, model);
                }
            }
        }
    }

    /**
     * Checks in model provided for packages containing enumerations.
     * 
     * @param enumsMap
     * @param model
     */
    private void appendEnumsFromModel(Map<String, Set<Enumeration>> enumsMap,
            Model model) {

        Set<org.eclipse.uml2.uml.Package> relevantPkgs =
                new HashSet<org.eclipse.uml2.uml.Package>();
        // recursively search for packages containing
        addPackagesContainingUMLtypes(relevantPkgs, model);

        // use retrieved packages to construct enumsMap
        for (org.eclipse.uml2.uml.Package pkg : relevantPkgs) {

            for (PackageableElement element : pkg.getPackagedElements()) {

                if (element instanceof Enumeration) {
                    Set<Enumeration> enumsSet = enumsMap.get(element.getName());
                    if (enumsSet == null) {
                        enumsSet = new HashSet<Enumeration>();
                        enumsMap.put(element.getName(), enumsSet);
                    }
                    enumsSet.add((Enumeration) element);
                }

            }
        }

    }

    /**
     * Recursive method for searching packages containing states UML class types
     * 
     * @param relevantPkgs
     * @param classes
     * @param model
     */
    private void addPackagesContainingUMLtypes(
            Set<org.eclipse.uml2.uml.Package> relevantPkgs,
            org.eclipse.uml2.uml.Package pkg) {

        for (PackageableElement child : pkg.getPackagedElements()) {
            if (child instanceof Enumeration) {
                relevantPkgs.add(pkg);
            } else if (child instanceof org.eclipse.uml2.uml.Package) {
                addPackagesContainingUMLtypes(relevantPkgs,
                        (org.eclipse.uml2.uml.Package) child);
            }
        }
    }

    /**
     * This utility method gets all enumerations with given name available in
     * the xpdl package scope.
     * 
     * @param enumName
     * @return
     */
    // XPD-4936: Using set to collect unique Enumerations
    private Map<String, Set<Enumeration>> getAllEnumsInProcessPackageScope(
            com.tibco.xpd.xpdl2.Package processPackage) {

        HashMap<String, Set<Enumeration>> enumsMap =
                new HashMap<String, Set<Enumeration>>();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(processPackage);
        IResource processIResource = wc.getEclipseResources().get(0);
        Set<IResource> visitedResources = new HashSet<IResource>();
        /* Get all the Direct/Indirect referenced packages BOM */
        Set<IResource> dependencies =
                ProcessUIUtil.queryReferencingBomResources(processIResource
                        .getProject(), processIResource.getFullPath()
                        .toPortableString(), false);
        /*
         * this block fetches GENERATED boms referenced by wsdl.
         */
        for (IResource wsdlResource : ProcessUIUtil
                .queryReferencedWSDLResources(processIResource, false)) {
            Set<IFile> bomFiles =
                    WsdlToBomBuilder.getBOMFiles((IFile) wsdlResource,
                            false,
                            true);
            dependencies.addAll(bomFiles);

        }
        // scan all BOM resources for enumerations.
        for (IResource iResource : dependencies) {
            // method to get enum types
            if (iResource instanceof IFile) {
                if (iResource != null && iResource.isAccessible()) {
                    getEnumsInResource((IFile) iResource,
                            visitedResources,
                            enumsMap);
                }
            }
        }
        return enumsMap;
    }

}
