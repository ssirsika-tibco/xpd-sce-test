/*
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.bds.designtime.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jdt.core.JavaCore;

import com.tibco.bds.designtime.generator.emfmod.XSDToEcoreTransformer;
import com.tibco.bds.designtime.generator.internal.Messages;
import com.tibco.bds.designtime.generator.internal.PerfMetrics;
import com.tibco.bds.designtime.generator.internal.PerfMetrics.Operations;
import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.NameMapper;
import com.tibco.xpd.bom.xsdtransform.api.Bom2XsdCachedTransformer;
import com.tibco.xpd.bom.xsdtransform.api.Bom2XsdSourceValidationType;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;

/**
 * BOMGenerator2 implementation that generates BDS model bundles
 * 
 */
public class ModelGenerator extends AbstractBDSGenerator {

    private static final String MODEL = "model"; //$NON-NLS-1$

    public static final AbstractBDSGenerator instance = new ModelGenerator();

    private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

    // Class that handles the collection of metrics
    private PerfMetrics perfMetrics = Activator.getDefault().getPerfMetrics();

    /**
     * The bom2xsd transformer. This is held for the length of a single build
     * cycle. This allows it to hold caches of schemas previously transformed to
     * xsd within the same build cycle.
     */
    private Bom2XsdCachedTransformer bom2xsdTransformer = null;

    private Context constructContext(IFile bomFile) {
        Context ctx = new Context();
        ctx.setBOMFile(bomFile);
        ctx.setOutputProject(getProject(bomFile));
        ctx.setBundleName(resolveGeneratedPluginId(bomFile));
        ctx.setBundleVersion(resolveGeneratedPluginVersion(bomFile));

        List<IProject> foreignProjects = new ArrayList<IProject>();
        List<IFile> allDependencies =
                getDependencyProvider().getAllDependencies(bomFile);
        for (IFile dependency : allDependencies) {
            foreignProjects.add(getProject(dependency));
        }
        ctx.setForeignProjects(foreignProjects);
        return ctx;
    }

    @Override
    public void generate(Collection<IFile> bomResources, GeneratorData data,
            IProgressMonitor monitor) throws CoreException {

        // If globaldata.development is set, then set BDS plugins to be built
        if (BOMGlobalDataUtils.isGlobalDataDevelopmentEnabled()) {
            for (IFile bomFile : bomResources) {
                addJavaPDEBuildersToGeneratedProject(bomFile);
            }
        }

        perfMetrics.startOffset();
        monitor.beginTask("", bomResources.size());

        try {
            for (IFile bomResource : bomResources) {
                if (needsGenerating(bomResource)) {

                    // Gather contextual information required for the
                    // generation.
                    Context ctx = constructContext(bomResource);

                    try {
                        generateFromBOM(ctx, new SubProgressMonitor(monitor, 1));
                    } catch (GenerationException e) {
                        Activator
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format(Messages
                                                .getString("OawTeneoGenerator_createProjFail"),
                                                e.getMessage()));
                        IStatus status =
                                new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                        e.getMessage(), e);
                        throw new CoreException(status);
                    }

                } else {
                    monitor.worked(1);
                }
            }
        } finally {
            monitor.done();
            perfMetrics.setTiming(Operations.Generate);
        }
    }

    /**
     * @param monitor
     * @param bomFilePath
     * @param overwrite
     * @param retainProject
     * @param jarFilePath
     * @param bundleVersion
     * @throws GenerationException
     */
    private void generateFromBOM(Context ctx, IProgressMonitor monitor)
            throws GenerationException {

        try {
            monitor.beginTask("", 2);

            monitor.subTask(String
                    .format("Generating internal BDS project for: %s :: creating intermediate XSDs...",
                            ctx.getBOMFile().getName()));

            perfMetrics.startOffset();
            List<URI> xsdURIs =
                    new BOMToXSDTransformer(ctx)
                            .transformToXSDs(bom2xsdTransformer,
                                    new SubProgressMonitor(monitor, 1));
            perfMetrics.setTiming(Operations.Bom2Xsd);

            // Now use the XSD to create the BDS project
            monitor.subTask(String
                    .format("Generating BDS project for: %s  :: generating project...",
                            ctx.getBOMFile().getName()));

            perfMetrics.startOffset();
            generateModelProjectFromXSDs(ctx, xsdURIs);
            perfMetrics.setTiming(Operations.GenerateBDS);

            // Force a refresh at the end of the generation, this ensures that
            // everything is displayed - there is a case that the ecore does not
            // get displayed and this fixes it
            ctx.getOutputProject().refreshLocal(IResource.DEPTH_INFINITE, null);

            monitor.worked(1);

        } catch (GenerationException e) {
            Activator.getDefault().getLogger().error(e, e.toString());
            throw e;
        } catch (Exception e1) {
            Activator.getDefault().getLogger().error(e1, e1.toString());
            throw new GenerationException(
                    Messages.getString("OawTeneoGenerator_cdsgenerationFailed"),
                    e1);

        } finally {
            monitor.done();
        }
    }

    /**
     * Generates a BDS model bundle in the output project specified in the
     * supplied context, derived from the supplied XSDs
     */
    public void generateModelProjectFromXSDs(Context ctx, List<URI> xsdURIs)
            throws GenerationException, IOException, CoreException {

        // Create EPackages for package(s) represented by XSD(s)
        // and return a list of those packages. If the model references
        // foreign models, this will include EPackages for those foreign
        // models. Later, we'll determine which are foreign and deal with
        // them accordingly (i.e. associate with an appropriate Resource
        // referencing the existing eCore, rather than saving in our own
        // eCore file).
        perfMetrics.startOffset(); // ECoreReadXSDs
        List<EPackage> ePackages =
                new XSDToEcoreTransformer().transform(xsdURIs);
        perfMetrics.setTiming(Operations.ECoreReadXSDs);

        // The Java package name is derived from the EPackage's namespace
        // URI. The EPackage name is set to the final fragment of the Java
        // package name and the remainder is (later) set as the
        // corresponding GenPackage's 'base package' property.
        for (EPackage pkg : ePackages) {

            IProject project = null;
            /*
             * XPD-8098: Some of the BDS design time junit tests will not have
             * bom file in their context. Put a null check to avoid getting NPE
             */
            if (null != ctx.getBOMFile()) {

                project = ctx.getBOMFile().getProject();
            }
            String javaPkg =
                    XSDUtil.getJavaPackageNameFromNamespaceURI(project,
                            pkg.getNsURI());
            String newName =
                    NameMapper.getLastFragmentFromJavaPackageName(javaPkg);
            pkg.setName(newName);
        }

        removeDefaultsFromMultipleValueAttributes(ePackages);

        perfMetrics.startOffset(); // ECoreForeignRefs

        // Maps from namespace to GenPackage, Ecore URI and IProject
        ForeignPackageMap foreignPackageMap = new ForeignPackageMap();
        if (ctx.getForeignProjects() != null) {
            foreignPackageMap.populateFromProjects(ctx.getForeignProjects());
        }

        // Modify (in-memory only) the foreign package map
        // contents to reference the foreign EPackages we've
        // just generated.
        foreignPackageMap.replacePackages(ePackages);

        List<EPackage> localPackages =
                foreignPackageMap.filterToUnknownPackages(ePackages);

        // Add in an annotation to detail the order of values in a sequence
        // this is needed in order to support the mixed construct
        BDSBOMModel.applyAnnotationsForSequenceOrder(localPackages);

        perfMetrics.setTiming(Operations.ECoreForeignRefs);

        boolean isGlobalData = false;

        // Perform additional processing necessary for Global Data support
        perfMetrics.startOffset(); // GlobalData
        // Annotate Ecore based on BDS global data meta-data in BOM (assuming we
        // have a BOM - which we don't in some unit testing scenarios)
        if (ctx.getBOMFile() != null) {
            BDSBOMModel bdsModel = BDSBOMModel.fromResource(ctx.getBOMFile());

            if (bdsModel.isGlobalBOM()) {
                isGlobalData = true;
                // Add the BOM Name annotation
                BDSBOMModel.applyBomFileAnnotations(ctx, localPackages);

                bdsModel.applyAnnotationsToPackages(localPackages,
                        ctx.getBundleVersion());
                // Add id/version attributes to classes
                IdentityAttributeInserter
                        .addIdAndVersionAndCaseIdToClasses(localPackages);
                // Add opposite EReferences for uni-directional containments
                // to enable fast delete functionality.
                ContainmentOppositeInserter
                        .addContainmentOpposites(localPackages);
            }
        }
        perfMetrics.setTiming(Operations.GlobalData);

        // Save .ecore file
        perfMetrics.startOffset(); // ECoreSave
        // Derive the ecore file name from the output bundle name
        IFolder modelFolder = ctx.getOutputProject().getFolder(MODEL);
        IFile ecoreFile =
                modelFolder.getFile(String.format("%s.ecore",
                        ctx.getBundleName()));

        XMLResource ecoreFileResource =
                saveEPackagesToEcoreFile(localPackages, ecoreFile);
        try {

            perfMetrics.setTiming(Operations.ECoreSave);

            // Copy the BOM model file to the /model folder within the new
            // project
            // Check to see if a BOM file has been supplied first, it
            // technically
            // is not required for conversion so doesn't matter if it is not
            // there
            if (ctx.getBOMFile() != null) {
                try {
                    FileHelper.copyFileToDir(ctx.getBOMFile().getLocation()
                            .toFile(), ctx.getOutputProject().getFolder(MODEL)); //$NON-NLS-1$
                } catch (IOException e) {
                    throw new GenerationException(
                            Messages.getString("OawTeneoGenerator_bomCopyFail"), e); //$NON-NLS-1$
                }
            }

            perfMetrics.startOffset(); // GenModelCreation

            // Create a GenModel, prepared for generation
            BDSGenModel bdsGenModel =
                    new BDSGenModel(ctx, localPackages, foreignPackageMap,
                            getDefaultSrcLocation(JavaCore.create(ctx
                                    .getOutputProject())));

            perfMetrics.startOffset(); // Templates

            // Copy over the custom templates before saving or generating
            TemplateManager templateManager =
                    new TemplateManager(bdsGenModel.getGenModel());
            try {
                templateManager.enableTemplates(ctx.getOutputProject());
            } catch (IOException e) {
                throw new GenerationException(
                        Messages.getString("OawTeneoGenerator_genmodelTemplateSetupFail"), e); //$NON-NLS-1$
            } catch (CoreException e) {
                throw new GenerationException(
                        Messages.getString("OawTeneoGenerator_genmodelTemplateSetupFail"), e); //$NON-NLS-1$
            }

            perfMetrics.setTiming(Operations.Templates);
            IPath genModelPath =
                    ctx.getOutputProject()
                            .getLocation()
                            .append("/" + MODEL + "/" + ctx.getBundleName() + ".genmodel"); //$NON-NLS-1$ //$NON-NLS-2$

            URI genModelURI = URI.createFileURI(genModelPath.toString());

            // Add the annotations to the genmodel
            bdsGenModel.addAnnotations(ctx.getOutputProject(),
                    ctx.getBundleVersion(),
                    templateManager.getTemplatedPackage(),
                    isGlobalData);

            // Save the genmodel to a file
            perfMetrics.startOffset(); // GenModelSave
            bdsGenModel.saveGenModel(genModelURI);
            perfMetrics.setTiming(Operations.GenModelSave);

            // Invoke generation on the genmodel
            perfMetrics.startOffset(); // GenModelGenerate
            bdsGenModel.invokeGenerator(templateManager);
            perfMetrics.setTiming(Operations.GenModelGenerate);

            perfMetrics.startOffset(); // Metafile
            MetaFileHelper helper = new MetaFileHelper();
            helper.postProcessProjectMetaFiles(ctx.getOutputProject());

            perfMetrics.setTiming(Operations.Metafile);
            perfMetrics.setTiming(Operations.GenModelCreation);

        } finally {
            /*
             * Sid XPD-2898 Make sure we unload the resource BUT not until after
             * we've finished with it.
             */
            if (ecoreFileResource != null && ecoreFileResource.isLoaded()) {
                ecoreFileResource.unload();
            }
        }
    }

    // EMF does not support defaults for multi-valued features, so
    // this removes them. At the time of writing, it's feasible we'll
    // be given an XSD containing such a scenario; It may be a good idea
    // to validate against this earlier in the process.
    // See:
    // http://www.eclipse.org/newsportal/article.php?id=43643&group=eclipse.tools
    // .emf#43643
    private void removeDefaultsFromMultipleValueAttributes(
            List<EPackage> packages) {
        for (EPackage pkg : packages) {
            TreeIterator<EObject> ti = pkg.eAllContents();
            while (ti.hasNext()) {

                EObject eObj = ti.next();
                if (eObj instanceof EAttribute) {
                    EAttribute eAttr = (EAttribute) eObj;
                    if (eAttr.getUpperBound() != 1) {
                        Object defaultValue = eAttr.getDefaultValue();
                        if (defaultValue != null) {
                            eAttr.setDefaultValue(null);
                        }
                        String literal = eAttr.getDefaultValueLiteral();
                        if (literal != null) {
                            eAttr.setDefaultValueLiteral(null);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param packages
     * @param ecoreFile
     * 
     * @return The resource created for the ecoreFile
     * 
     * @throws GenerationException
     */
    private XMLResource saveEPackagesToEcoreFile(Collection<EPackage> packages,
            IFile ecoreFile) throws GenerationException {

        ResourceSet resourceSet = new ResourceSetImpl();

        resourceSet
                .getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION,
                        new XMIResourceFactoryImpl());

        URI fileURI = URI.createFileURI(ecoreFile.getLocation().toOSString());

        XMLResource resource =
                (XMLResource) resourceSet.createResource(fileURI);

        resource.setEncoding(UTF_8);
        resource.getContents().addAll(packages);

        try {
            resource.save(null);

        } catch (IOException e) {
            if (resource.isLoaded()) {
                resource.unload();
            }
            throw new GenerationException(
                    Messages.getString("OawTeneoGenerator_XsdEcoreTransFail"), e); //$NON-NLS-1$
        }

        /*
         * Sid XPD-2898 Make sure we unload the resource BUT not until after
         * we've finished with it.
         * 
         * So return it to caller rather than unloading it here.
         */
        return resource;
    }

    /**
     * Resolves the generated plugin id for a given Bom File
     * 
     * @param bomResource
     * 
     * @return the generated plugin id, null if the bom resource is invalid
     */
    private String resolveGeneratedPluginId(IResource bomResource) {

        if (bomResource != null && bomResource instanceof IFile) {
            // TODO Speak to Nilay about this. i.e. Is there a more
            // appropriate way to determine the bundle name, other than
            // by getting the output project name and removing the leading dot?
            // Kam considered this a reasonable approach, but I think it's worth
            // considering pushing this responsibility up into BOMGenerator2.
            IProject project = getProject((IFile) bomResource);
            String projectName = project.getName();
            return projectName.replaceAll("^\\.", "");
        }
        // TODO Wrong - I had a corrupted BOM file and this returned null,
        // causing confusion behaviour.
        return null;
    }

    /**
     * @see com.tibco.xpd.bom.gen.api.BOMGenerator2#prepareForBuildCycle(org.eclipse.core.resources.IProject,
     *      com.tibco.xpd.bom.gen.api.GeneratorData.BuildType)
     * 
     * @param project
     * @param buildType
     */
    @Override
    public void prepareForBuildCycle(IProject project, BuildType buildType) {
        perfMetrics.startMetrics();
        super.prepareForBuildCycle(project, buildType);

        /*
         * Sid XPD-1605: We can use BOM2XSD far more efficiently if we create
         * and use the same BOM2XSDTransformer instance for the whole build
         * cycle.
         * 
         * This will cache the schemas that it has already generated during this
         * cycle. So that when it transforms and generates the xsd's for a BOM
         * and it's dependencies (which each called to transform will do), when
         * it is called again for the next BOM file that has the same
         * dependencies, those dependencies will be pulled from the cache.
         */
        bom2xsdTransformer =
                new Bom2XsdCachedTransformer(
                        false,
                        Bom2XsdSourceValidationType.USE_MARKERS_FROM_PREVIOUS_BUILD);

        /*
         * Sid XPD-2569: There is no need to
         * setWantAllReferencedSchemasEveryTime=true anymore. This was only to
         * get past the fact that bds's call to transform wanted to take
         * advantage of re-using already parsed schemas by keep using the same
         * transformer BUT still wanted to get all xsd's serialised to output
         * folder anyway.
         * 
         * Now the transformer copies the complete set of xsds for BOM from
         * bom2xsd (regardless of whether a transform of out-of-date xsds was
         * performed beforehand there is no longer any need to have treansform
         * always return and serialise all xsds.
         */
    }

    /**
     * @see com.tibco.xpd.bom.gen.api.BOMGenerator2#createProject(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IProject[],
     *      com.tibco.xpd.bom.gen.api.GeneratorData)
     * 
     * @param cdsProject
     *            The Project to be created
     * @param dependencies
     *            The dependent projects
     * @param data
     * @return
     * @throws CoreException
     */
    @Override
    protected IProject createProject(IProject cdsProject,
            IProject[] dependencies, GeneratorData data) throws CoreException {
        // Start time for whole project create method
        perfMetrics.startOffset();

        perfMetrics.startOffset();
        IProject proj = super.createProject(cdsProject, dependencies, data);
        perfMetrics.setTiming(Operations.StudioCreateProject);

        try {
            // Before creating the BDS project, create the dependent project
            TemplateManager.importBDSTemplateProject();

            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

            String projectBasePath = cdsProject.getLocation().lastSegment();
            IResource javaResource = null;

            IFolder sourceContainer = cdsProject.getFolder(MODEL);
            if (!sourceContainer.exists()) {
                sourceContainer.create(false, true, new SubProgressMonitor(
                        new NullProgressMonitor(), 1));
            }
            IPath sourcePath =
                    getDefaultSrcLocation(JavaCore.create(cdsProject));
            javaResource = root.findMember(sourcePath);

            Path projectLocationPath =
                    new Path(new File(root.getLocation().toFile().getPath(),
                            projectBasePath).getAbsolutePath());

            List<IProject> referencedProjects = new ArrayList<IProject>();
            org.eclipse.emf.codegen.ecore.Generator
                    .createEMFProject(javaResource.getFullPath(),
                            projectLocationPath,
                            referencedProjects,
                            BasicMonitor
                                    .toMonitor(new org.eclipse.core.runtime.NullProgressMonitor()),
                            org.eclipse.emf.codegen.ecore.Generator.EMF_PLUGIN_PROJECT_STYLE);

            // Set up the project for template support
            TemplateManager.updateBDSProjectForTemplates(cdsProject);

        } catch (GenerationException e) {
            // Change to a core exception
            throw new CoreException(new Status(IStatus.ERROR,
                    Activator.PLUGIN_ID, e.getMessage(), e));
        }

        perfMetrics.setTiming(Operations.CreateProject);

        return proj;
    }

    /**
     * @see com.tibco.xpd.bom.gen.api.BOMGenerator2#buildCycleComplete(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void buildCycleComplete(IProject project) {
        /*
         * Sid XPD-1605: Make sure we ditch the caching transformer at the end
         * of the build cycle - next time around we'll start afresh.
         */
        bom2xsdTransformer = null;
        super.buildCycleComplete(project);
        perfMetrics.completeMetrics();
    }
}
