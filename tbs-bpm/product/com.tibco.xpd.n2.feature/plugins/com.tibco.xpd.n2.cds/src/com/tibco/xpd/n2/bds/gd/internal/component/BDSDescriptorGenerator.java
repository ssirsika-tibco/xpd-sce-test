/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.n2.bds.gd.internal.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.bds.model.BOMPackageType;
import com.tibco.bds.model.CaseModelContentsType;
import com.tibco.bds.model.CaseModelType;
import com.tibco.bds.model.DocumentRoot;
import com.tibco.bds.model.ModelFactory;
import com.tibco.bds.model.NamespaceInfoType;
import com.tibco.bds.model.util.ModelResourceFactoryImpl;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Generates GlobalData descriptor for a project.
 * <p>
 * <i>Created: 10 Jun 2011</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
/* package */class BDSDescriptorGenerator {

    public static final String CASE_MODEL_FILE_NAME = "CaseModel.xml"; //$NON-NLS-1$

    private final Logger LOG = CDSActivator.getDefault().getLogger();

    private static BDSDescriptorGenerator INSTANCE =
            new BDSDescriptorGenerator();

    /** XML file extension */
    private final String XML = "xml"; //$NON-NLS-1$

    /** BDS bundle name suffix */
    private final String BDS_MODEL_SUFFIX = ".bds"; //$NON-NLS-1$

    /**
     * Obtains instance of this generator.
     * 
     * @return ScriptDescriptorGenerator instance.
     */
    public static BDSDescriptorGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor. Use {@link #getInstance()} factory method instead.
     */
    private BDSDescriptorGenerator() {
    }

    /**
     * Generate generate Global Data descriptor into the output folder.
     * 
     * @param project
     *            the context project.
     * @param outFolder
     *            the output folder.
     */
    public IStatus generateBSDDescriptor(final IProject project,
            IFolder outFolder) {
        Assert.isNotNull(project);
        Assert.isNotNull(outFolder);

        Map<String, Object> extensionToFactoryMap =
                Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        Object previousXMLFactory = extensionToFactoryMap.get(XML);
        extensionToFactoryMap.put(XML, new ModelResourceFactoryImpl());
        try {
            DocumentRoot caseModelDocRoot = generateCaseModel(project);

            // Save descriptor.
            if (caseModelDocRoot != null
                    && caseModelDocRoot.getCaseModel() != null
                    && caseModelDocRoot.getCaseModel().getCaseModelContents()
                            .size() > 0) {
                // Use a new resource set.
                ResourceSet rs = new ResourceSetImpl();
                final String filePath =
                        outFolder.getFullPath().append(CASE_MODEL_FILE_NAME)
                                .toPortableString();

                Resource r =
                        rs.createResource(URI
                                .createPlatformResourceURI(filePath, true));
                r.getContents().add(caseModelDocRoot);
                IFile file = WorkspaceSynchronizer.getFile(r);
                if (file == null) {
                    // out of workspace resource.
                    r.save(N2Utils.getDefaultXMLSaveOptions());
                } else {
                    r.save(N2Utils.getDefaultXMLSaveOptions()); // overwrite
                    file.refreshLocal(IResource.DEPTH_ZERO, null);
                    file.setDerived(true);
                }
            }
        } catch (Exception e) {
            LOG.error(e);
            return new Status(IStatus.ERROR, CDSActivator.PLUGIN_ID,
                    "BDS case model generation error.", e);
        } finally {
            extensionToFactoryMap.put(XML, previousXMLFactory);
        }
        return Status.OK_STATUS;
    }

    /**
     * Generate root element of case model descriptor.
     * 
     * @param project
     *            the context project.
     * @return the generated root of case model document.
     */
    private DocumentRoot generateCaseModel(IProject project) {

        List<IResource> bomResources = CDSUtils.getBomResources(project);
        final ModelFactory f = ModelFactory.eINSTANCE;
        final CaseModelType caseModel = f.createCaseModelType();
        /* Iterate through model with umlVisitor. */
        for (IResource bomResource : bomResources) {

            Model bomModel = BOMUtils.getModel((IFile) bomResource);
            IProject bomProject = bomResource.getProject();

            /*
             * XPD-7460: Generated BOM does not have global data profile. So
             * rather we check if the bom resource is in a business data project
             * instead of bom with global data profile
             */
            if (bomModel != null && BOMUtils.isBusinessDataProject(bomProject)) {

                CaseModelContentsType caseModelContents =
                        f.createCaseModelContentsType();
                String bomName = WorkingCopyUtil.getFile(bomModel).getName();
                caseModelContents.setBomName(bomName);
                /*
                 * ABPM-900: Set model bundle name. This model bundle name is
                 * the generated bds project name. This is required by BDS team
                 * for identifying model bundles related to an application while
                 * un-deploying.
                 * 
                 * Please note that this computation of model bundle name might
                 * need re-visiting if the naming convention for bds projects
                 * that get generated for each bom resource changes. Currently
                 * this is hard coded to the suffix that is contributed to the
                 * com.tibco.xpd.bom.gen.bomGenerator2 extension point in
                 * com.tibco.bds.designtime.generator plugin.xml in BDS
                 * Design-time feature (as it is highly unlikely that it would
                 * change)
                 */
                StringBuffer modelBundleName =
                        new StringBuffer(bomModel.getName());
                modelBundleName.append(BDS_MODEL_SUFFIX);
                caseModelContents
                        .setModelBundleName(modelBundleName.toString());

                ArrayList<Package> bomPackages = new ArrayList<Package>();
                bomPackages.add(bomModel);
                collectSubPackages(bomModel, bomPackages);

                for (Package bomPackage : bomPackages) {

                    /* ABPM-900: Add bom package entry always */
                    BOMPackageType bomPackageType = f.createBOMPackageType();
                    NamespaceInfoType namespaceInfoType =
                            f.createNamespaceInfoType();
                    namespaceInfoType.setNsURI(BOMUtils
                            .getNamespace(bomPackage));
                    namespaceInfoType.setFqName(BOMWorkingCopy
                            .getQualifiedPackageName(bomPackage));
                    bomPackageType.setNamespaceInfo(namespaceInfoType);

                    /*
                     * ABPM-900: Add case class entries only when there are case
                     * classes in a bom
                     */
                    List<Class> caseClasses =
                            getCaseClassesPackages(bomPackage);
                    if (caseClasses != null) {

                        for (Class caseClass : caseClasses) {

                            String qualifiedClassName =
                                    BOMWorkingCopy
                                            .getQualifiedClassName(caseClass);
                            bomPackageType.getCaseType()
                                    .add(qualifiedClassName);
                        }
                    }
                    caseModelContents.getBomPackage().add(bomPackageType);
                }
                if (!caseModelContents.getBomName().isEmpty()) {

                    caseModel.getCaseModelContents().add(caseModelContents);
                }
            }
        }
        DocumentRoot documentRoot = f.createDocumentRoot();
        documentRoot.setCaseModel(caseModel);
        return documentRoot;
    }

    /**
     * Collects all sub-packages of the provided package.
     * 
     * @param pkg
     *            the UML package.
     * @param packagesList
     *            the modifiable list to collect sub-packages.
     */
    private void collectSubPackages(Package pkg, List<Package> packagesList) {
        List<PackageableElement> pkgElements = pkg.getPackagedElements();
        for (PackageableElement element : pkgElements) {
            if (element instanceof Package) {
                packagesList.add((Package) element);
                collectSubPackages((Package) element, packagesList);
            }
        }
    }

    /**
     * Returns list of case classes in UML package (only one level deep).
     * 
     * @param pkg
     *            the UML package.
     * @return List of case classes in the UML package (only one level deep).
     *         Empty list if the package contains Global Class(es) but no case
     *         classes. <code>null</code> otherwise.
     */
    private List<Class> getCaseClassesPackages(Package pkg) {

        List<Class> caseClasses = null;

        for (PackageableElement element : pkg.getPackagedElements()) {
            if (element instanceof Class) {
                Class clazz = (Class) element;
                GlobalDataProfileManager manager =
                        GlobalDataProfileManager.getInstance();
                if (manager.isGlobal(clazz) && (caseClasses == null)) {
                    caseClasses = Collections.<Class> emptyList();
                } else if (manager.isCase(clazz)) {
                    if ((caseClasses == null) || (caseClasses.isEmpty())) {
                        caseClasses = new ArrayList<Class>();
                    }
                    caseClasses.add((Class) element);
                }
            }
        }

        return caseClasses;
    }
}
