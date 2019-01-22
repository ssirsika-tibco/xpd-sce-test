package com.tibco.bds.designtime.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.uml2.uml.Model;
import org.openarchitectureware.expression.Variable;
import org.openarchitectureware.uml2.UML2MetaModel;
import org.openarchitectureware.xpand2.XpandExecutionContextImpl;
import org.openarchitectureware.xpand2.XpandFacade;
import org.openarchitectureware.xpand2.output.JavaBeautifier;
import org.openarchitectureware.xpand2.output.Outlet;
import org.openarchitectureware.xpand2.output.OutputImpl;

import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * A BDS generator that uses oAW Xpand to generate an output project from a BOM.
 * 
 * @author smorgan
 * 
 */
public abstract class AbstractXpandGenerator extends AbstractBDSGenerator {

    @Override
    public boolean supports(Collection<IFile> bomResources,
            IProgressMonitor monitor) throws CoreException {

        // Make sure the BOM has at lease one case class, as we only want to do
        // anything if there is at least one case class
        return super.supports(bomResources, monitor)
                && containsGlobalDataCaseClass(bomResources);
    }

    /**
     * @param bomResources
     *            Collection of BOM resources
     * @return <code>true</code> if a Global Data entity has been detected in
     *         one of the resources
     */
    private static boolean containsGlobalDataCaseClass(
            Collection<? extends IResource> bomResources) {

        for (IResource bom : bomResources) {
            if (BOMGlobalDataUtils.hasGlobalCaseClass(bom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void generate(Collection<IFile> bomResources, GeneratorData data,
            IProgressMonitor monitor) throws CoreException {

        // If globaldata.development is set, then set BDS plugins to be built
        if (BOMGlobalDataUtils.isGlobalDataDevelopmentEnabled()) {
            for (IFile bomFile : bomResources) {
                // Only want to generate the DA and SI bundle if there is at
                // lease one Case Class
                if (BOMGlobalDataUtils.hasGlobalCaseClass(bomFile)) {
                    addJavaPDEBuildersToGeneratedProject(bomFile);
                }
            }
        }

        for (IFile bomFile : bomResources) {
            // XPD-6987 - Ensure SI and DA projects are only built if necessary
            if (needsGenerating(bomFile)) {
                // Only want to generate the DA and SI bundle if there is at
                // lease
                // one Case Class
                if (!BOMGlobalDataUtils.hasGlobalCaseClass(bomFile)) {
                    continue;
                }
                long stamp = System.nanoTime();
                generateJavaSourceViaFacade(bomFile);
                generateOtherArtifactsViaFacade(bomFile);
                // TODO implement proper performance measures, like model bundle
                long elapsed = System.nanoTime() - stamp;
                System.out.println(String
                        .format("%s took %.3fs to generate from %s",
                                getClass().getSimpleName(),
                                elapsed / 1000000000.0d,
                                bomFile.getName()));
                // addJavaPDEBuildersToGeneratedProject(bomFile);
            }
        }
    }

    protected abstract String getJavaSourceTemplatePath();

    protected abstract String getOtherArtifactsTemplatePath();

    protected abstract String getBundleName();

    protected abstract String getRootPackageName(String modelName);

    /**
     * Override this method to return true if the manifest should declare an
     * 'Activator' class in the top-level package
     * 
     * @return
     */
    protected boolean isActivatorRequired() {
        return false;
    }

    private void generateJavaSourceViaFacade(IFile bomFile)
            throws CoreException {

        IProject outputProject = getProject(bomFile);
        // TODO Allow non-'src' source path?
        String outletPath =
                outputProject.getLocation().toPortableString() + "/src";

        // configure outlets
        OutputImpl output = new OutputImpl();
        Outlet outlet = new Outlet(outletPath);
        outlet.addPostprocessor(new JavaBeautifier());
        outlet.setOverwrite(true);
        output.addOutlet(outlet);

        Map<String, Variable> globalVarsMap = new HashMap<String, Variable>();
        globalVarsMap.put("projectVersion", new Variable("projectVersion",
                resolveGeneratedPluginVersion(bomFile)));
        XpandExecutionContextImpl execCtx =
                new XpandExecutionContextImpl(output, null, globalVarsMap,
                        null, null);
        UML2MetaModel uml2MetaModel = new UML2MetaModel();
        execCtx.registerMetaModel(uml2MetaModel);
        XpandFacade facade = XpandFacade.create(execCtx);
        EObject root =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile)
                        .getRootElement();
        facade.evaluate(getJavaSourceTemplatePath(), root);

        outputProject.refreshLocal(IResource.DEPTH_INFINITE, null);
    }

    private void generateOtherArtifactsViaFacade(IFile bomFile)
            throws CoreException {
        IProject outputProject = getProject(bomFile);
        // i.e. the root of the project
        String outletPath = outputProject.getLocation().toPortableString();

        // configure outlets
        OutputImpl output = new OutputImpl();
        Outlet outlet = new Outlet(outletPath);
        outlet.setOverwrite(true);
        output.addOutlet(outlet);

        // TODO refactor this and above method
        Map<String, Variable> globalVarsMap = new HashMap<String, Variable>();
        globalVarsMap.put("projectVersion", new Variable("projectVersion",
                resolveGeneratedPluginVersion(bomFile)));
        XpandExecutionContextImpl execCtx =
                new XpandExecutionContextImpl(output, null, globalVarsMap,
                        null, null);
        UML2MetaModel uml2MetaModel = new UML2MetaModel();
        execCtx.registerMetaModel(uml2MetaModel);
        XpandFacade facade = XpandFacade.create(execCtx);
        EObject root =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile)
                        .getRootElement();
        facade.evaluate(getOtherArtifactsTemplatePath(), root);

        outputProject.refreshLocal(IResource.DEPTH_INFINITE, null);
    }

    protected String getModelName(IFile bomFile) {
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        Model model = (Model) wc.getRootElement();
        String modelName = model.getName();
        return modelName;
    }

    @Override
    protected IProject createProject(IProject project, IProject[] dependencies,
            GeneratorData data) throws CoreException {
        IProject result = super.createProject(project, dependencies, data);

        // Add org.eclipse.pde.PluginNature nature
        IProjectDescription description = project.getDescription();
        List<String> natureIds = new ArrayList<String>();
        natureIds.add("org.eclipse.pde.PluginNature"); //$NON-NLS-1$
        natureIds.addAll(Arrays.asList(description.getNatureIds()));
        description.setNatureIds(natureIds.toArray(new String[0]));
        project.setDescription(description, new NullProgressMonitor());

        // Add <classpathentry kind="con"
        // path="org.eclipse.pde.core.requiredPlugins"/>
        IJavaProject javaProject = JavaCore.create(project);
        List<IClasspathEntry> classpathEntries =
                new ArrayList<IClasspathEntry>();
        classpathEntries.addAll(Arrays.asList(javaProject.getRawClasspath()));

        // TODO Verify that dependencies appear below this in explorer tree once
        // manifest is written
        IClasspathEntry rpEntry =
                JavaCore.newContainerEntry(new Path(
                        "org.eclipse.pde.core.requiredPlugins")); //$NON-NLS-1$
        classpathEntries.add(rpEntry);
        javaProject.setRawClasspath(classpathEntries
                .toArray(new IClasspathEntry[0]), new NullProgressMonitor());

        return result;
    }
}