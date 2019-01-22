package com.tibco.xpd.bom.gen.biz;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.ui.jarpackager.JarPackageData;
import org.eclipse.jdt.ui.jarpackager.JarWriter3;

import com.tibco.xpd.bom.gen.ui.Activator;

/**
 * The primary business logic class for generating Business Object Services.
 * 
 * @author tstephen
 */
public class BomGenerator {
    // TODO This class should have a unit test.

    // private static final String PARAM_PROFILE_PERSISTENCE_FILE =
    // "profile.persistence.file";
    // private static final String PARAM_OUTLET_RES_ONCE_DIR =
    // "outlet.res.once.dir";
    // private static final String PARAM_OUTLET_SRC_ONCE_DIR =
    // "outlet.src.once.dir";
    // private static final String PARAM_HIBERNATE_PROPS_FILE =
    // "hibernate.properties.file";
    private static final String JAVA_BUILDER =
            "org.eclipse.jdt.core.javabuilder";

    private static final String JAVA_NATURE = "org.eclipse.jdt.core.javanature";

    // private static final String PARAM_MODEL = "model.file";
    // private static final String PARAM_SRC_GEN_DIR = "outlet.src.dir";
    // private static final String PARAM_RES_GEN_DIR = "outlet.res.dir";

    private final BomGeneratorConfig config;

    /**
     * Default constructor.
     */
    public BomGenerator() {
        this.config = new BomGeneratorConfig();
    }

    /**
     * Constructor supplying the configuration params (e.g. source and binary
     * directories to use).
     * 
     * @param config
     */
    public BomGenerator(BomGeneratorConfig config) {
        this.config = config;
    }

    private void throwCoreException(String message) throws CoreException {
        IStatus status =
                new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                        message, null);
        throw new CoreException(status);
    }

    public void createJar(String jarFileName, IProject project)
            throws CoreException {
        // java.io.File jarFileDir = new
        // java.io.File(jarFileName).getParentFile();
        // if (!jarFileDir.exists()) {
        // try {
        // // make parent directories (required by jarwriter3)
        // jarFileDir.mkdirs();
        // } catch (Exception e) {
        // throwCoreException(String.format(
        // "Dir '%s' to write jar file to does not exist and cannot be
        // created.",
        // jarFileName));
        // }
        // }

        try {
            JarPackageData jarDescription = new JarPackageData();
            IPath location = new Path(jarFileName);
            jarDescription.setJarLocation(location);
            jarDescription.setSaveManifest(true);
            // description.setManifestMainClass(mainType);

            // find the compiled classes dir
            IJavaProject javaProject = JavaCore.create(project);
            IPath outputPath = javaProject.getOutputLocation();
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            IContainer outputDir = (IContainer) root.findMember(outputPath);
            if (outputDir == null || !outputDir.exists()) {
                throwCoreException("TODO Create output dir for jar");
                // project.find
            }

            // jarDescription.setElements(filestoExport);
            jarDescription.setElements(outputDir.members());

            JarWriter3 writer = new JarWriter3(jarDescription, null);
            // IResource[]

            // IFile[] filestoExport = (IFile[]) outputDir.members();
            for (IResource resource : outputDir.members()) {

                // for (IFile file : filestoExport) {
                writer
                        .write(project.getFile(resource
                                .getProjectRelativePath()), resource
                                .getProjectRelativePath());
            }
            // IFile file = project.getFile(new Path("bin/Person.class"));
            // filestoExport[0] = file;

            writer.close();

        } catch (Throwable t) {
            t.printStackTrace();
            // throw new CoreException();
        }
    }

    public void setupJavaNature(IResource srcDirResource, IProject project,
            IProgressMonitor monitor) throws CoreException {
        IProjectDescription description = project.getDescription();

        // ensure have Java nature on the project
        String[] natures = description.getNatureIds();
        if (!Arrays.asList(natures).contains(JAVA_NATURE)) {
            String[] newNatures = new String[natures.length + 1];
            System.arraycopy(natures, 0, newNatures, 0, natures.length);
            newNatures[natures.length] = JAVA_NATURE;
            description.setNatureIds(newNatures);
            project.setDescription(description, monitor);

            // ensure have Java builder on the project
            ICommand[] commands = description.getBuildSpec();
            boolean found = false;

            for (int i = 0; i < commands.length; ++i) {
                if (commands[i].getBuilderName().equals(JAVA_BUILDER)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                // add builder to project
                ICommand command = description.newCommand();
                command.setBuilderName(JAVA_BUILDER);
                ICommand[] newCommands = new ICommand[commands.length + 1];

                // Add it before other builders.
                System.arraycopy(commands, 0, newCommands, 1, commands.length);
                newCommands[0] = command;
                description.setBuildSpec(newCommands);
                project.setDescription(description, monitor);
            }
        }

        IJavaProject javaProject = JavaCore.create(project);
        IClasspathEntry[] newClasspath = new IClasspathEntry[2];
        IClasspathEntry srcEntry =
                JavaCore.newSourceEntry(srcDirResource.getFullPath());
        newClasspath[0] = srcEntry;
        IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
        IRuntimeClasspathEntry systemLibsEntry =
                JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath,
                        IRuntimeClasspathEntry.STANDARD_CLASSES);
        newClasspath[1] = systemLibsEntry.getClasspathEntry();
        // TODO omitting this relies on the default, is that ok?
        // IClasspathEntry binEntry = JavaCore.newProjectEntry(new Path(
        // project.getName() + "/bin"));
        // newClasspath[2] = binEntry;

        javaProject.setRawClasspath(newClasspath, monitor);

        // Configure project to produce Java 1.5 output. We might not ultimately
        // want to do this, but the code is here for reference, so we know how it's done.
        javaProject.setOption(JavaCore.COMPILER_COMPLIANCE,
                JavaCore.VERSION_1_5);
        javaProject.setOption(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
        javaProject.setOption(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
                JavaCore.VERSION_1_5);
        javaProject.setOption(JavaCore.COMPILER_PB_ASSERT_IDENTIFIER,
                JavaCore.ERROR);
        javaProject.setOption(JavaCore.COMPILER_PB_ENUM_IDENTIFIER,
                JavaCore.ERROR);
    }

    public void compileSources(IResource srcDirResource,
            IProgressMonitor monitor) throws CoreException {
        IProject myProject = srcDirResource.getProject();
        myProject.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
    }

    // public void generateSourceFile(IResource bomResource,
    // IResource srcDirResource, IResource resDirResource,
    // IProgressMonitor monitor) throws CoreException {
    // Map<String, String> params = new HashMap<String, String>();
    // params.put(PARAM_MODEL, bomResource.getLocation().toOSString());
    // params
    // .put(PARAM_SRC_GEN_DIR, srcDirResource.getLocation()
    // .toOSString());
    // params
    // .put(PARAM_RES_GEN_DIR, resDirResource.getLocation()
    // .toOSString());
    // params
    // .put(PARAM_HIBERNATE_PROPS_FILE,
    // "'org/fornax/cartridges/uml2/hibernate/generationParams.properties'");
    // // params.put("outlet.src.once.dir", "./src/generated/java/override");
    // params.put(PARAM_OUTLET_SRC_ONCE_DIR, "."
    // + srcDirResource.getFullPath().toString());
    // // params.put("outlet.res.once.dir", "./src/main/resources");
    // params.put(PARAM_OUTLET_RES_ONCE_DIR, "."
    // + resDirResource.getFullPath().toString());
    // params.put(PARAM_PROFILE_PERSISTENCE_FILE, bomResource.getParent()
    // .getRawLocation()
    // + "/Persistence.profile.uml");
    // Map<String, Object> externalSlots = new HashMap<String, Object>();
    // try {
    // // OawSampleGenerator runner = new OawSampleGenerator();
    // OawBosGenerator runner = new OawBosGenerator();
    // // TODO wrap oaw ProgressMonitor for display through JFace monitor
    // // (org.eclipse.jface.operation.AccumulatingProgressMonitor)
    // ProgressMonitor oawMonitor = null;
    // boolean success = runner.run(oawMonitor, params, externalSlots);
    //
    // // TODO Although it is bad practise to do file operations on the
    // // Java
    // // file rather than the IFile, have not found a way to pass OAW
    // // IFile
    // srcDirResource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
    // } catch (Throwable t) {
    // t.printStackTrace();
    // // TODO place in error log and provide better feedback to user
    // throwCoreException(t.getLocalizedMessage());
    // }
    // }
}
