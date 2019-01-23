/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core.wsdlconversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.core.createtestwizards.CreateBaseTestPage;
import com.tibco.xpd.n2.test.core.N2CreateBaseTestWizard;
import com.tibco.xpd.n2.test.core.N2TestCorePlugin;
import com.tibco.xpd.n2.test.core.generators.WsdlConversionTestJavaClassGenerator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Create a WSDL/XSD COnversion test from the selected WSDL / XSD.
 * <p>
 * The generated test class tests that inclusion of the selected WSDL / XSD in a
 * BPM project generates the appropriate BOM files with the correct content (and
 * that they have no problem markers).
 * <p>
 * Then checks that the correct XSDs are derived by the Bom2XsdBuilder (in the
 * .bom2xsd" folder) with the correct content and that they have no problem
 * markers.
 * <p>
 * Then checks that the correct BDS projects are created and contains the
 * correct XSDs dervied for them to build the BDS ecore - and that the BDS
 * project has no problem markers.
 * 
 * @author aallway
 * @since 19 Apr 2011
 */
public class N2CreateWsdlConversionTestWizard extends N2CreateBaseTestWizard {

    private String baseWsdlOrXsdPath;

    private IResource baseWsdlOrXsd;

    private Set<IFile> generatedBomFiles;

    private List<String> bomFolderRelativeBoms;

    private Set<IFile> bom2XsdFiles;

    private List<String> bom2XsdFolderRelativeXsds;

    private Set<Xsd4BdsInfo> xsd4BdsInfos;

    /**
     * @param selectedStudioResources
     */
    public N2CreateWsdlConversionTestWizard(
            Collection<IResource> selectedStudioResources) {
        super(selectedStudioResources);

        return;
    }

    /**
     * @see com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard#init(java.util.Collection)
     * 
     * @param selectedStudioResources
     */
    @Override
    protected void init(Collection<IResource> selectedStudioResources) {
        /*
         * Action should enable this wizard with one SDL / XSD resource
         * selected.
         */
        if (selectedStudioResources.size() == 1) {
            baseWsdlOrXsd = selectedStudioResources.iterator().next();
            if ("wsdl".equalsIgnoreCase(baseWsdlOrXsd.getFileExtension()) //$NON-NLS-1$
                    || "xsd".equalsIgnoreCase(baseWsdlOrXsd.getFileExtension())) { //$NON-NLS-1$

                baseWsdlOrXsdPath =
                        baseWsdlOrXsd.getProjectRelativePath().toString();
                super.init(getSchemaSet(baseWsdlOrXsd));
                return;
            }
        }
        throw new RuntimeException("A single WSDL or XSD must be selected."); //$NON-NLS-1$
    }

    /**
     * @param baseWsdlOrXsd2
     * @return the given resource and all its dependencies (nested)
     */
    private Collection<IResource> getSchemaSet(IResource baseWsdlOrXsd) {

        Set<XSDSchema> schemaDependencies = new HashSet<XSDSchema>();

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(baseWsdlOrXsd);

        if ("wsdl".equalsIgnoreCase(baseWsdlOrXsd.getFileExtension())) { //$NON-NLS-1$
            getFileAndDependencySetWsdl((Definition) WorkingCopyUtil
                    .getWorkingCopy(baseWsdlOrXsd).getRootElement(),
                    schemaDependencies);
        } else {
            getFileAndDependencySetXsd((XSDSchema) WorkingCopyUtil
                    .getWorkingCopy(baseWsdlOrXsd).getRootElement(),
                    schemaDependencies);
        }

        // TODO - look up the (deeply nested) schema imports.
        Set<IResource> fileSet = new HashSet<IResource>();
        fileSet.add(baseWsdlOrXsd);
        for (XSDSchema schema : schemaDependencies) {
            IFile file = WorkingCopyUtil.getFile(schema);
            fileSet.add(file);
        }
        return fileSet;
    }

    private void getFileAndDependencySetWsdl(Definition definition,
            Set<XSDSchema> schemaDependencies) {

        List extensibilityElements =
                definition.getTypes().getExtensibilityElements();

        for (Object ee : extensibilityElements) {
            if (ee instanceof XSDSchemaExtensibilityElement) {
                XSDSchema schema =
                        ((XSDSchemaExtensibilityElement) ee).getSchema();
                if (schema != null && !schemaDependencies.contains(schema)) {
                    getFileAndDependencySetXsd(schema, schemaDependencies);
                }
            }
        }

        EList<?> schemaImports = definition.getEImports();
        if (schemaImports != null) {
            for (Object obj : schemaImports) {
                Import tmpImport = (Import) obj;
                XSDSchema schema = tmpImport.getSchema();
                if (schema != null && !schemaDependencies.contains(schema)) {
                    getFileAndDependencySetXsd(schema, schemaDependencies);
                }
            }
        }

        return;
    }

    /**
     * @param schema
     * @param schemaDependencies
     */
    private void getFileAndDependencySetXsd(XSDSchema schema,
            Set<XSDSchema> schemaDependencies) {

        if (!schemaDependencies.contains(schema)) {
            schemaDependencies.add(schema);

            for (Iterator iter2 = schema.eAllContents(); iter2.hasNext();) {
                EObject se = (EObject) iter2.next();

                if (se instanceof XSDImport) {
                    XSDSchema resolvedSchema =
                            ((XSDImport) se).getResolvedSchema();

                    getFileAndDependencySetXsd(resolvedSchema,
                            schemaDependencies);

                } else if (se instanceof XSDInclude) {
                    XSDSchema resolvedSchema =
                            ((XSDInclude) se).getResolvedSchema();

                    getFileAndDependencySetXsd(resolvedSchema,
                            schemaDependencies);

                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard#saveTestResources(java.util.Collection,
     *      org.eclipse.core.resources.IFolder, java.util.jar.Manifest, boolean)
     * 
     * @param studioResources
     * @param testResourceFolder
     * @param testPluginManifest
     * @param includeDotResources
     * @return
     */
    @Override
    protected Collection<String> saveTestResources(
            Collection<IResource> studioResources, IFolder testResourceFolder,
            Manifest testPluginManifest, boolean includeDotResources) {
        Collection<String> saveTestResources =
                super.saveTestResources(studioResources,
                        testResourceFolder,
                        testPluginManifest,
                        includeDotResources);

        saveTestResources.addAll(saveGoldGeneratedBoms(testResourceFolder));
        saveTestResources.addAll(saveGoldBom2Xsds(testResourceFolder));
        saveTestResources.addAll(saveGoldXsds4Bds(testResourceFolder));

        return saveTestResources;
    }

    /**
     * Save the boms generated for source wsdl to the test resources folder.
     * 
     * @param baseTestResourceFolder
     * 
     * @return Set of TestResourceInfo path strings for the copied resources.
     */
    private Collection<? extends String> saveGoldGeneratedBoms(
            IFolder baseTestResourceFolder) {

        try {
            bomFolderRelativeBoms = new ArrayList<String>();

            generatedBomFiles =
                    WsdlToBomBuilder
                            .getBomsGeneratedFromWsdl((IFile) baseWsdlOrXsd);

            return saveGoldFilesToTestFolder(baseTestResourceFolder,
                    baseWsdlOrXsd.getProject()
                            .getFolder(WsdlToBomBuilder.GENERATED_BOM_FOLDER),
                    generatedBomFiles,
                    AbstractN2WsdlConversionTest.GOLD_GENERATED_BOM_FOLDER,
                    bomFolderRelativeBoms);

        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save the XSDs derived from the boms generated for the source WSDL / xsd.
     * 
     * @param baseTestResourceFolder
     * 
     * @return Set of TestResourceInfo path strings for the copied resources.
     */
    private Collection<? extends String> saveGoldBom2Xsds(
            IFolder baseTestResourceFolder) {

        bom2XsdFiles = new HashSet<IFile>();

        bom2XsdFolderRelativeXsds = new ArrayList<String>();

        for (IFile genBom : generatedBomFiles) {
            Set<IFile> currentXsdsGeneratedFromBom =
                    Bom2XsdUtil.getCurrentXsdsGeneratedFromBom(baseWsdlOrXsd
                            .getProject(), genBom);

            bom2XsdFiles.addAll(currentXsdsGeneratedFromBom);
        }

        return saveGoldFilesToTestFolder(baseTestResourceFolder,
                baseWsdlOrXsd.getProject()
                        .getFolder(Bom2XsdUtil.DEFAULT_BOM_2_XSD_FOLDER),
                bom2XsdFiles,
                AbstractN2WsdlConversionTest.GOLD_GENERATED_BOM2XSDS_FOLDER,
                bom2XsdFolderRelativeXsds);

    }

    /**
     * Save the XSDs generated for BDS project for the boms generated for the
     * soruce wsdl / xsd.
     * 
     * @param baseTestResourceFolder
     * 
     * @return Set of TestResourceInfo path strings for the copied resources.
     */
    private Collection<? extends String> saveGoldXsds4Bds(
            IFolder baseTestResourceFolder) {

        List<String> testResourceInfoPaths = new ArrayList<String>();

        xsd4BdsInfos = new HashSet<Xsd4BdsInfo>();

        for (IFile genBom : generatedBomFiles) {

            String bdsProjectName = getBdsProjectName(genBom);

            /* Get the xsd's generated into the bds project. */
            IProject bdsProject =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(bdsProjectName);

            if (bdsProject != null) {
                IFolder modelFolder = bdsProject.getFolder("model"); //$NON-NLS-1$

                List<IFile> xsds = getXsdsBelowFolder(modelFolder);

                if (xsds.size() > 0) {
                    /*
                     * Save the files to test gold resources.
                     */
                    IPath goldFolderPath =
                            new Path(
                                    AbstractN2WsdlConversionTest.GOLD_GENERATED_XSDS4BDS_FOLDER);
                    if (bdsProjectName.startsWith(".")) { //$NON-NLS-1$
                        goldFolderPath =
                                goldFolderPath.append(bdsProjectName
                                        .substring(1));
                    } else {
                        goldFolderPath = goldFolderPath.append(bdsProjectName);
                    }

                    List<String> modelFolderRelativePaths =
                            new ArrayList<String>();

                    testResourceInfoPaths
                            .addAll(saveGoldFilesToTestFolder(baseTestResourceFolder,
                                    modelFolder,
                                    xsds,
                                    goldFolderPath.toString(),
                                    modelFolderRelativePaths));

                    xsd4BdsInfos.add(new Xsd4BdsInfo(genBom.getName(),
                            bdsProjectName, modelFolderRelativePaths
                                    .toArray(new String[0])));

                }
            }
        }

        return testResourceInfoPaths;
    }

    /**
     * @param modelFolder
     * @return all the xsd's in the bds project
     */
    private List<IFile> getXsdsBelowFolder(IFolder modelFolder) {
        final List<IFile> xsds = new ArrayList<IFile>();

        if (modelFolder != null) {
            try {

                modelFolder.accept(new IResourceVisitor() {

                    @Override
                    public boolean visit(IResource resource)
                            throws CoreException {
                        if (resource instanceof IFile) {
                            if ("xsd".equalsIgnoreCase(resource //$NON-NLS-1$
                                    .getFileExtension())) {
                                xsds.add((IFile) resource);
                            }
                        }
                        return true;
                    }
                });

            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

        return xsds;
    }

    private String getBdsProjectName(IFile bomFile) {
        Model model = null;

        if (bomFile != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
            if (wc != null && wc.getRootElement() instanceof Model) {
                model = (Model) wc.getRootElement();

                return "." + model.getName() + ".bds"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return null;
    }

    /**
     * Save the given set of files to the given fold file folder name in the
     * target test resource/projectname folder.
     * 
     * @param baseTestResourceFolder
     * @param files
     * @param goldFileFolderName
     * @param baseSourceFolderRelativeFiles
     * 
     * @return Set of TestResourceInfo path strings for the copied resources.
     */
    private Collection<? extends String> saveGoldFilesToTestFolder(
            IFolder baseTestResourceFolder, IFolder baseSourceFolder,
            Collection<IFile> files, String goldFileFolderName,
            List<String> baseSourceFolderRelativeFiles) {

        IPath goldFolderPath = new Path(baseWsdlOrXsd.getProject().getName());
        goldFolderPath = goldFolderPath.append(goldFileFolderName);

        IFolder goldFolder = baseTestResourceFolder.getFolder(goldFolderPath);

        if (!goldFolder.exists()) {
            createFolderPath(goldFolder);
        }

        Collection<String> testResourceInfoPaths = new ArrayList<String>();

        IPath baseSourcePath = baseSourceFolder.getFullPath();

        for (IFile file : files) {

            IPath pathRelToBase =
                    file.getFullPath()
                            .removeFirstSegments(baseSourcePath.segmentCount());
            IFolder targetFolder = null;

            if (pathRelToBase.segmentCount() > 1) {
                targetFolder =
                        goldFolder.getFolder(pathRelToBase
                                .removeLastSegments(1));
                if (!targetFolder.exists()) {
                    createFolderPath(targetFolder);
                }
            } else {
                targetFolder = goldFolder;
            }

            saveToTestFolder(file, targetFolder);

            testResourceInfoPaths.add(goldFolderPath.append(pathRelToBase)
                    .toString());

            baseSourceFolderRelativeFiles.add(pathRelToBase.toString());
        }

        return testResourceInfoPaths;

    }

    /**
     * Generate the base junit test class according to the given parameters.
     * 
     * @param testName
     * @param testClassName
     * @param testPackageId
     * @param testPluginId
     * @param baseTestPluginResourcePath
     * @param testResourceInfoPaths
     */
    @Override
    protected String generateTestClassContent(String testName,
            String testClassName, String testPackageId, String testPluginId,
            IPath baseTestPluginResourcePath,
            Collection<String> testResourceInfoPaths) {

        //
        // Create a class to pass all data to the BaseTestJavaClassGenerator.
        String[] resourceInfoPathArray =
                testResourceInfoPaths.toArray(new String[testResourceInfoPaths
                        .size()]);

        Class testSuperClass = getTestSuperClass();

        N2WsdlConversionTestGeneratorData generatorData =
                new N2WsdlConversionTestGeneratorData(testName, testClassName,
                        testSuperClass.getSimpleName(), testSuperClass
                                .getPackage().getName(),
                        CreateBaseTestPage.toJavaName(testClassName),
                        testPluginId, testPackageId,
                        baseTestPluginResourcePath.toString(),
                        resourceInfoPathArray, baseWsdlOrXsd.getProject()
                                .getName(), baseWsdlOrXsdPath,
                        bomFolderRelativeBoms, bom2XsdFolderRelativeXsds,
                        xsd4BdsInfos);

        //
        // Generate the base test class.
        WsdlConversionTestJavaClassGenerator wsdlConversionTestJavaClassGenerator =
                new WsdlConversionTestJavaClassGenerator();

        String baseTestClass =
                wsdlConversionTestJavaClassGenerator.generate(generatorData);

        return baseTestClass;
    }

    @Override
    protected Class getTestSuperClass() {
        return AbstractN2WsdlConversionTest.class;
    }

    /**
     * @see com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard#getTestSuperClassPluginId()
     * 
     * @return
     */
    @Override
    protected String getTestSuperClassPluginId() {
        return N2TestCorePlugin.PLUGIN_ID;
    }

}
