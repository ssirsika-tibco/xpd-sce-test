package com.tibco.bds.designtime.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.jet.IJETNature;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

import com.tibco.xpd.bom.gen.biz.GenerationException;

/**
 * Copies EMF codegen templates from this plugin's
 * resources/EMF-codegen-templates folder into the specified destination. These
 * can then be applied at generation-time to affect the generated code with
 * CDS-specific behaviour.
 * 
 */
public class TemplateManager {

    private GenModel genModel = null;

    private final static String BDSUtilName = "bdsutil";

    // Details the package name that is the default in the template
    protected final static String defaultPackageName =
            "com.example.businessobjectmodel." + BDSUtilName;

    // The name of the BDS Pre-Compiled templates
    private final static String bdsTemplatePackage = "bdsTemplates";

    // The name of the Package within the bundle that the
    // BDS Utility classes are located
    private String utilTemplatePackageName = null;

    // This is the version of the pre-compiled templates to use.
    // Inside the bdsTemplate.zip there is a .project file
    // In the comments section there will be a number - that needs to
    // match the number in this string
    private final static String bdsTemplateVersion = "30";

    private final static String validationSchemaTag = "BDS_VALIDATION_SCHEMAS";

    private final static String validationNamespaceTag =
            "BDS_VALIDATION_NAMESPACES";

    // The BDS Util java files to copy to the generated project
    private final static String bdsUtilFiles[] = {
            BDSUtilName + "/BDSActivator.java",
            BDSUtilName + "/BDSFeatureMap.java",
            BDSUtilName + "/BDSFeatureMapImpl.java",
            BDSUtilName + "/BDSCopyUtil.java", BDSUtilName + "/BDSLists.java",
            BDSUtilName + "/BDSNotifyingCalendar.java",
            BDSUtilName + "/BDSEObjectLists.java",
            BDSUtilName + "/BDSIntegerLists.java",
            BDSUtilName + "/BDSDoubleLists.java",
            BDSUtilName + "/BDSStringLists.java",
            BDSUtilName + "/BDSTypeConversions.java",
            BDSUtilName + "/BDSBigDecimalLists.java",
            BDSUtilName + "/BDSBigIntegerLists.java" };

    /**
     * Creates a template manager for a given genModel
     * 
     * @param genModelInput
     *            The Gen Model to manage the templates for
     */
    public TemplateManager(GenModel genModelInput) {
        genModel = genModelInput;

        // Need to get a package to store the utility classes in.
        EList<GenPackage> genPackages = genModel.getGenPackages();
        if (!genPackages.isEmpty()) {
            // Look for the longest package name, this will ensure that
            // it will not be possible for a name-clash between the internal
            // bdsutil package and a user defined sub-package called bdsutil
            // this is because it will always add bdsutil to the package name
            for (GenPackage genPackage : genPackages) {
                String localPackage = genPackage.getInterfacePackageName();
                if ((utilTemplatePackageName == null)
                        || (utilTemplatePackageName.length() < localPackage
                                .length())) {
                    utilTemplatePackageName = localPackage;
                }
            }
            utilTemplatePackageName += "." + BDSUtilName;
        }
    }

    /**
     * Enables the template support on the EMF BDS package, copying in the
     * required files
     * 
     * @param destinationFolder
     *            Folder to copy to
     * @throws IOException
     * @throws GenerationException
     * @throws CoreException
     */
    public void enableTemplates(IProject destinationFolder) throws IOException,
            GenerationException, CoreException {
        // Copy the raw EMF jet templates into the project
        copyEmfTemplates(destinationFolder);

        // Get the existing model directory and then use it to get the
        // projects root name
        String modelDirectory = genModel.getModelDirectory();
        if ((modelDirectory != null) && (modelDirectory.length() > 1)) {
            modelDirectory =
                    modelDirectory.substring(0, modelDirectory.indexOf("/", 1));
        }

        // Need to get a package to store the utility classes in, the primary
        // package will do
        String basePackage = getTemplatedPackage();
        if (basePackage != null) {
            // Set the FeatureMap Wrapper details in the Gen Model
            genModel.setFeatureMapWrapperClass(basePackage
                    + ".BDSFeatureMapImpl");
            genModel.setFeatureMapWrapperInterface(basePackage
                    + ".BDSFeatureMap");
            genModel.setFeatureMapWrapperInternalInterface(basePackage
                    + ".BDSFeatureMap");

            // Copy over the template code and change the namespace at the top
            // of the file. These will all become non-dynamic classes
            // in the BDS 'common bundle', if and when that exists.
            IFolder bdsUtilFolder =
                    destinationFolder.getFolder("src/"
                            + basePackage.replaceAll("\\.", "/"));

            Map<String, String> variables = new HashMap<String, String>();
            variables.put(defaultPackageName, getTemplatedPackage());

            for (String bdsUtilFile : bdsUtilFiles) {
                copyTemplate(bdsUtilFile, bdsUtilFolder, variables);
            }

            // Now enable the alternate validation code
            enableValidation(destinationFolder, bdsUtilFolder);
        }

        // Enable the use of templates on the Gen model
        // The following line will point to the Jet Templates, we are forced to
        // pre-compile
        // these templates because of the following issue:
        // http://www.eclipse.org/forums/index.php?t=rview&goto=651583
        // genModel.setTemplateDirectory(modelDirectory + "/templates");
        genModel.setTemplateDirectory("platform:/resource/."
                + bdsTemplatePackage);
        genModel.setUpdateClasspath(false);
        genModel.setDynamicTemplates(true);
    }

    /**
     * Gets the extra package that is need in order to support the templates
     * 
     * @return Name of the package required for template support
     */
    public String getTemplatedPackage() {
        return utilTemplatePackageName;
    }

    /**
     * Copies the given file to the specified location. It will also replace the
     * package name (if not null)
     * 
     * @param templateName
     *            Name of the file to copy
     * @param destinationFolder
     *            Location to copy the file to
     * @param variables
     *            Optional list of data strings to substitute (or null if none)
     * @throws IOException
     * @throws CoreException
     */
    private void copyTemplate(String templateName, IFolder destinationFolder,
            Map<String, String> variables) throws IOException, CoreException {
        final String path =
                String.format("/resources/EMF-codegen-templates/%s",
                        templateName);
        InputStream in =
                TemplateManager.class.getClassLoader()
                        .getResourceAsStream(path);

        String fileName =
                (templateName.contains("/") ? templateName
                        .substring(templateName.lastIndexOf("/") + 1)
                        : templateName);

        // If a package name has been supplied, then do a global replace of the
        // name
        if (variables != null) {
            // Read in the existing data to a string, preserving new lines
            String source = FileHelper.readContents(in, true);
            // now replace the key words
            for (Map.Entry<String, String> aVar : variables.entrySet()) {
                source = source.replaceAll(aVar.getKey(), aVar.getValue());
            }

            FileHelper.copyStringToFileDir(source, fileName, destinationFolder);
        } else {
            // Nothing to replace in the file, so just copy it
            FileHelper.copyInputStreamToDir(in, fileName, destinationFolder);
        }
    }

    /**
     * Updates the BDS project with the required data for template generation
     * 
     * @param bdsProject
     *            The BDS project to update
     * @throws GenerationException
     */
    static public void updateBDSProjectForTemplates(IProject bdsProject)
            throws GenerationException {
        // In order to support jet templates we need to add
        // the Jet Nature to the BDS project
        if (Activator.getDefault().isCopyEmfJetTemplatesEnabled() != false) {
            try {
                IProjectDescription description = bdsProject.getDescription();
                String[] natures = description.getNatureIds();
                String[] newNatures = new String[natures.length + 1];
                System.arraycopy(natures, 0, newNatures, 0, natures.length);
                newNatures[natures.length] = IJETNature.NATURE_ID;
                IStatus status =
                        ResourcesPlugin.getWorkspace()
                                .validateNatureSet(newNatures);

                // check the status and decide what to do
                if (status.getCode() == IStatus.OK) {
                    description.setNatureIds(newNatures);
                    bdsProject.setDescription(description, null);
                }
            } catch (CoreException e) {
                throw new GenerationException(e.getMessage(), e);
            }
        }

        // Need to add the template project into the BDS project classpath
        try {
            IJavaProject javaProject = JavaCore.create(bdsProject);
            IClasspathEntry[] rawClasspath = javaProject.getRawClasspath();
            IClasspathEntry[] newClasspath =
                    new IClasspathEntry[rawClasspath.length + 1];
            System.arraycopy(rawClasspath,
                    0,
                    newClasspath,
                    0,
                    rawClasspath.length);
            IProject project =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject("." + bdsTemplatePackage);
            newClasspath[rawClasspath.length] =
                    JavaCore.newProjectEntry(new Path(project.getFullPath()
                            .toPortableString()));
            javaProject.setRawClasspath(newClasspath, null);
        } catch (CoreException e) {
            throw new GenerationException(e.getMessage(), e);
        }
    }

    /**
     * Imports into studio the required project that contains the BDS
     * pre-compiled templates
     * 
     * @throws GenerationException
     */
    static public void importBDSTemplateProject() throws GenerationException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = root.getProject("." + bdsTemplatePackage);

        boolean templatesAlreadyInstalled = false;

        try {

            // Check to see if the templates project already exists
            if (project.exists()) {
                // Check to see if the project is open
                if (!project.isOpen()) {
                    project.open(null);
                }
                // Get the description of the project
                IProjectDescription description = project.getDescription();
                // The version number is stored in the comments, so get them
                if (description.getComment().startsWith(bdsTemplateVersion)) {
                    templatesAlreadyInstalled = true;
                } else {
                    // The version is an old one, so delete and recreate
                    project.delete(true, null);
                }
            }
        } catch (CoreException e) {
            throw new GenerationException(e.getMessage(), e);
        }

        // Check if the templates need to be installed
        if (templatesAlreadyInstalled != true) {
            String path =
                    String.format("/resources/EMF-codegen-templates/%s",
                            bdsTemplatePackage + ".zip");
            InputStream in =
                    TemplateManager.class.getClassLoader()
                            .getResourceAsStream(path);

            try {
                // Extract into a temporary directory
                File zipExportRoot =
                        File.createTempFile(bdsTemplatePackage, "zip");
                FileHelper.unzipFromResource(in, zipExportRoot);

                // Interested in the directory under the temporary directory
                File srcTarget =
                        new File(zipExportRoot, "." + bdsTemplatePackage);

                // Create the import operation that simulates the wizard
                ImportOperation op =
                        new ImportOperation(root.getProject("."
                                + bdsTemplatePackage).getFullPath(), srcTarget,
                                FileSystemStructureProvider.INSTANCE, null);
                // We don't want the full path nested, just from where we define
                op.setCreateContainerStructure(false);

                // Run the import operation
                op.run(null);
                // Remove the temporary directory
                zipExportRoot.delete();
            } catch (IOException e) {
                throw new GenerationException(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                throw new GenerationException(e.getMessage(), e);
            } catch (InterruptedException e) {
                throw new GenerationException(e.getMessage(), e);
            }
        }
    }

    /**
     * Deals with the changes required in order to support an alternate
     * validation method where SAX is used to validate
     * 
     * @param destinationFolder
     *            BDS project folder root
     * @param bdsUtilFolder
     *            Folder where all the bdsutil files are stored
     * @throws IOException
     * @throws GenerationException
     * @throws CoreException
     */
    private void enableValidation(IProject destinationFolder,
            IFolder bdsUtilFolder) throws IOException, GenerationException,
            CoreException {

        ValidationTemplate validationTemplate =
                new ValidationTemplate(destinationFolder);

        // Load the schemas from the folder
        validationTemplate.loadSchemas();

        // Save the custom validation schemas into the project
        validationTemplate.saveSchemas();

        // Set the variables to replace while copying the template file
        Map<String, String> variables = new HashMap<String, String>();
        variables.put(defaultPackageName, getTemplatedPackage());

        // Get the list of schemas to store into the template
        variables.put(validationSchemaTag,
                validationTemplate.getSchemaListString());

        // Get the list of namespaces to store into the template
        variables.put(validationNamespaceTag,
                validationTemplate.getNamespaceListString());

        // Copy over the additional file
        copyTemplate(BDSUtilName + "/BDSValidationUtils.java",
                bdsUtilFolder,
                variables);
    }

    /**
     * This method copied over all the custom templates into the given directory
     * These are only used for debugging purposes
     * 
     * @param destinationFolder
     *            Project to copy to
     * @throws IOException
     * @throws CoreException
     */
    private void copyEmfTemplates(IProject destinationFolder)
            throws IOException, CoreException {
        // Check if the templates should be copied
        if (Activator.getDefault().isCopyEmfJetTemplatesEnabled() != true) {
            return;
        }

        final String classTemplates[] =
                { "model/Class/insert.javajetinc",
                        "model/Class/setGenFeature.pre.insert.javajetinc",
                        "model/Class/getGenFeature.override.javajetinc" };

        IFolder templateClassFolder =
                destinationFolder.getFolder("templates/model/Class");

        for (String classTemplate : classTemplates) {
            copyTemplate(classTemplate, templateClassFolder, null);
        }

        final String modelTemplates[] =
                { "model/Class.javajet", "model/FactoryClass.javajet",
                        "model/EnumClass.javajet", "model/build.propertiesjet",
                        "model/plugin.propertiesjet",
                        "model/PackageClass.javajet", "model/manifest.mfjet",
                        "model/ValidatorClass.javajet" };

        IFolder templateModelFolder =
                destinationFolder.getFolder("templates/model");

        for (String modelTemplate : modelTemplates) {
            copyTemplate(modelTemplate, templateModelFolder, null);
        }

        // Copy the additional templates for validation
        copyTemplate("model/Package/insert.javajetinc",
                templateModelFolder.getFolder("Package"),
                null);
    }
}