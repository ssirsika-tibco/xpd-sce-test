package com.tibco.bds.designtime.generator;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenAnnotation;
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenResourceKind;
import org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.bds.designtime.generator.internal.Messages;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.resources.utils.NameMapper;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;

/**
 * Wraps an EMF GenModel. Capable of creating that GenModel and configuring it
 * for BDS model bundle generation, saving/loading it as a file and invoking
 * code generation.
 * 
 * @author smorgan
 * 
 */
public class BDSGenModel {

    // The actual EMF GenModel
    private GenModel genModel;

    public BDSGenModel(Context ctx, List<EPackage> packages,
            ForeignPackageMap foreignPackageMap, IPath javaSourcePath)
            throws GenerationException {

        genModel = GenModelFactory.eINSTANCE.createGenModel();
        genModel.getUsedGenPackages()
                .addAll(foreignPackageMap.getUsedGenPackages());
        genModel.setModelDirectory(javaSourcePath.toString());
        genModel.setModelPluginID(ctx.getBundleName());
        genModel.setBundleManifest(true);
        genModel.setComplianceLevel(GenJDKLevel.JDK60_LITERAL);
        genModel.setRuntimeVersion(GenRuntimeVersion.EMF24);

        // Initialise the genmodel with the list of EPackages that correspond to
        // our ecores
        genModel.initialize(packages);

        if (genModel.getGenPackages().size() == 0) {
            throw new GenerationException(
                    Messages.getString("OawTeneoGenerator_noClasses")); //$NON-NLS-1$
        }

        for (GenPackage genPackage : genModel.getGenPackages()) {
            EPackage ePackage = genPackage.getEcorePackage();
            IProject project = null;
            /*
             * XPD-8098: Some of the BDS design time junit tests will not have
             * bom file in their context. Put a null check to avoid getting NPE
             */
            if (null != ctx.getBOMFile()) {

                project = ctx.getBOMFile().getProject();
            }
            String javaPackageName =
                    XSDUtil.getJavaPackageNameFromNamespaceURI(project,
                            ePackage.getNsURI());
            // Set each genpackage's prefix to correspond to the last fragment
            // of the Java package name
            genPackage
                    .setPrefix(deriveGenPackagePrefixFromJavaPackageName(javaPackageName));

            // The EPackage's name is the last fragment of the Java package
            // name, so setting the remainder as the GenPackage's base package
            // will give the desired result when combined at generation time.
            genPackage.setBasePackage(NameMapper
                    .removeLastFragmentFromJavaPackageName(javaPackageName));

            // Suppress creation of a customized resource/resource factory
            // for this package. We don't need this but, more importantly,
            // we need to suppress it, as it can sometimes expose a
            // platform bug (JIRA BX-1400).
            genPackage.setResource(GenResourceKind.NONE_LITERAL);
        }

        genModel.setModelName(ctx.getBundleName());
    }

    public void saveGenModel(URI uri) throws GenerationException {
        try {
            ResourceSet resourceSet = new ResourceSetImpl();
            resourceSet
                    .getResourceFactoryRegistry()
                    .getExtensionToFactoryMap()
                    .put(Resource.Factory.Registry.DEFAULT_EXTENSION,
                            new XMIResourceFactoryImpl());
            resourceSet.getURIConverter().getURIMap()
                    .putAll(EcorePlugin.computePlatformURIMap());

            XMLResource genModelResource =
                    (XMLResource) resourceSet.createResource(uri);
            // TODO use constant?
            genModelResource.setEncoding("UTF-8");
            genModelResource.getContents().add(genModel);
            resourceSet.getResources().add(genModelResource);
            genModelResource.save(Collections.EMPTY_MAP);

            // Reload the GenModel from the file we've just written.
            // This is to ensure we'll encounter any issues that may have arisen
            // when writing the file, or that will occur when reading it back.
            // This also means that later manual regeneration from the .genmodel
            // file should be consistent with the result we'll get here. This
            // precaution was added when we encountered an issue whereby foreign
            // references to EPackages with dots in their name would succeed
            // when generating from the in-memory GenModel, but not after saving
            // and reloading it (the underlying issue has been fixed). More
            // recently, it was discovered that the code that applies patterns
            // to date/time attributes is ineffective unless the GenModel is
            // reloaded. In summary: It's a good idea to do this.
            XMLResource genModelReloadResource =
                    (XMLResource) resourceSet.createResource(uri);
            genModelReloadResource.load(Collections.EMPTY_MAP);
            genModel = (GenModel) genModelReloadResource.getContents().get(0);
        } catch (IOException e) {
            throw new GenerationException(
                    Messages.getString("OawTeneoGenerator_genmodelSaveFail"), e); //$NON-NLS-1$
        }
    }

    public void invokeGenerator(TemplateManager templateManager)
            throws GenerationException {

        genModel.setCanGenerate(true);
        // Create the generator and set the model-level input object.
        Generator generator = new Generator();
        generator.setInput(genModel);

        // Generate EMF code from .genModel.
        Diagnostic diagnostic =
                generator
                        .generate(genModel,
                                GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
                                BasicMonitor
                                        .toMonitor(new org.eclipse.core.runtime.NullProgressMonitor()));

        if (DiagnosticHelper.containsSeverity(diagnostic, Diagnostic.ERROR)) {
            throw new GenerationException(String.format(Messages
                    .getString("OawTeneoGenerator_genFailed"), DiagnosticHelper
                    .toStringBySeverity(diagnostic, Diagnostic.ERROR)));
        }
    }

    private String deriveGenPackagePrefixFromJavaPackageName(String packageName) {

        // The genpackage name is the last (dot-separated) segment of the Java
        // package name with the first character uppercased.
        StringBuilder working =
                new StringBuilder(
                        NameMapper
                                .getLastFragmentFromJavaPackageName(packageName));

        // Set the initial character to uppercase
        working.setCharAt(0, Character.toUpperCase(working.charAt(0)));
        return working.toString();
    }

    public GenModel getGenModel() {
        return genModel;
    }

    /**
     * Add the annotations to the genModel Note: This is adding to the Gen Model
     * - not the eCore These are read by the Manifest generation template
     * 
     * <genAnnotations source="BDS-Manifest"> <details key="version"
     * value="3.2"/> <details key="binFolder" value="bin/"/> <details
     * key="bdsutilPackage" value="com.example.businessobjectmodel.bdsutil"/>
     * </genAnnotations>
     * 
     * @param project
     * @param version
     * @param bdsutilPackage
     * @throws GenerationException
     */
    public void addAnnotations(IProject project, String version,
            String bdsutilPackage, boolean isGlobalData)
            throws GenerationException {
        // Add 'bin' to bundle classpath.
        // Harmless for us, but works around a bug (see JIRA BX-1400).
        String binFolderName = null;
        try {
            binFolderName = getProjectBinFolderPath(project);
        } catch (JavaModelException e) {
            throw new GenerationException(
                    Messages.getString("OawTeneoGenerator_unableToModifyManifest"), e); //$NON-NLS-1$
        }

        String bdsManifestInfo = "BDS-Manifest";

        GenAnnotation genAnnotation =
                genModel.getGenAnnotation(bdsManifestInfo);

        // Check to see if there is already a genModel annotation
        if (genAnnotation == null) {
            genAnnotation = genModel.createGenAnnotation();
            genAnnotation.setSource(bdsManifestInfo);
            genModel.getGenAnnotations().add(genAnnotation);
        }

        if (version != null) {
            genAnnotation.getDetails().put("version", version);
        }
        if (binFolderName != null) {
            genAnnotation.getDetails().put("binFolder", binFolderName + "/");
        }
        if (version != null) {
            genAnnotation.getDetails().put("bdsutilPackage", bdsutilPackage);
        }
        if (isGlobalData) {
            genAnnotation.getDetails().put("Globaldata", "true");
        }
    }

    /**
     * Obtains the output folder path for the given project (typically 'bin').
     * The path is OS-neutral and relative to the project root. Note: The
     * project is expected to be a BDS model project and null may be returned if
     * it isn't.
     * 
     * @param project
     * @return
     * @throws JavaModelException
     */
    private String getProjectBinFolderPath(IProject project)
            throws JavaModelException {
        String result = null;
        IJavaProject javaProject = JavaCore.create(project);
        if (javaProject != null) {
            IPath outputLocationPath = javaProject.getOutputLocation();
            IPath projLoc = project.getFullPath();
            int segs = outputLocationPath.matchingFirstSegments(projLoc);
            IPath diffPart = outputLocationPath.removeFirstSegments(segs);
            result = diffPart.toPortableString();
        }
        return result;
    }
}
