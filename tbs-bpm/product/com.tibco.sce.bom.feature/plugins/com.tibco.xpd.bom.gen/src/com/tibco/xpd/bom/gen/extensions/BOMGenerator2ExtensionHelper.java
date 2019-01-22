/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.gen.extensions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.gen.BOMGenActivator;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension.GenerationMode;
import com.tibco.xpd.bom.gen.internal.BOMGeneratorHelper;
import com.tibco.xpd.bom.gen.internal.Messages;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Helper for the <code>com.tibco.xpd.bom.gen.bomGenerator2</code> extension
 * point. This can be used to get all the extensions (or extension with a given
 * id) of this extension point. Use {@link #getInstance()} to create an instance
 * of of this class.
 * 
 * @author njpatel
 * 
 */
public final class BOMGenerator2ExtensionHelper {

    private static final String EXT_ID = "bomGenerator2"; //$NON-NLS-1$

    private static final Logger LOG = BOMGenActivator.getDefault().getLogger();

    private final Map<String, BOMGenerator2Extension> extensionMap;

    /**
     * bomGenerator extension point helper class.
     */
    protected BOMGenerator2ExtensionHelper() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(BOMGenActivator.PLUGIN_ID, EXT_ID);

        extensionMap = new HashMap<String, BOMGenerator2Extension>();

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            for (IExtension extension : extensions) {
                IConfigurationElement[] elements =
                        extension.getConfigurationElements();
                for (IConfigurationElement element : elements) {
                    BOMGenerator2Extension ext =
                            new BOMGenerator2Extension(element);
                    if (ext.getId() != null) {
                        extensionMap.put(ext.getId(), ext);
                    }
                }
            }
        }
    }

    /**
     * @param generatorIds
     */
    public BOMGenerator2ExtensionHelper(Set<String> generatorIds) {
        extensionMap = initialiseExtensionMap(generatorIds);
    }

    /**
     * Initialises extensionMap. Can be selective in adding
     * <code>BOMGenerator2Extension</code>s entries restricting to those
     * extensions which have generator IDs corresponding to those provided.
     * 
     * @param requestedGeneratorIds
     *            if <code>null</code> or empty all
     *            <code>BOMGenerator2Extension</code>s are added to the map
     * @return Map of all found <code>BOMGenerator2Extension</code>s keyed by
     *         their generator IDs. Empty map if extension point not found.
     */
    private Map<String, BOMGenerator2Extension> initialiseExtensionMap(
            Set<String> requestedGeneratorIds) {

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(BOMGenActivator.PLUGIN_ID, EXT_ID);

        if (point == null) {
            return Collections.<String, BOMGenerator2Extension> emptyMap();
        } else {
            Map<String, BOMGenerator2Extension> ret =
                    new HashMap<String, BOMGenerator2Extension>();

            boolean specificExtensionsRequested =
                    (requestedGeneratorIds != null)
                            && !requestedGeneratorIds.isEmpty();

            for (IExtension extension : point.getExtensions()) {
                for (IConfigurationElement element : extension
                        .getConfigurationElements()) {

                    BOMGenerator2Extension ext =
                            new BOMGenerator2Extension(element);
                    String generatorId = ext.getId();
                    if (generatorId != null) {
                        boolean isExtensionRequested =
                                specificExtensionsRequested ? requestedGeneratorIds
                                        .contains(generatorId) : true;
                        if (isExtensionRequested) {
                            ret.put(generatorId, ext);
                        }
                    }
                }
            }
            return ret;
        }

    }

    /**
     * Create an instance of this class.
     * 
     * @return
     */
    public static final BOMGenerator2ExtensionHelper getInstance() {
        return new BOMGenerator2ExtensionHelper();
    }

    /**
     * Get the {@link BOMGenerator2Extension extension} with the given id.
     * 
     * @param id
     *            extension id.
     * @return extension information
     */
    public BOMGenerator2Extension getExtension(String id) {
        return extensionMap.get(id);
    }

    /**
     * Get all extensions (sorted by label of the extension).
     * 
     * @return array of {@link BOMGenerator2Extension}s - empty array if no
     *         extensions found.
     */
    public BOMGenerator2Extension[] getExtensions() {
        Collection<BOMGenerator2Extension> values = extensionMap.values();
        Set<BOMGenerator2Extension> sortedValues =
                new TreeSet<BOMGenerator2Extension>(values);
        return sortedValues.toArray(new BOMGenerator2Extension[sortedValues
                .size()]);
    }

    /**
     * Get all extensions set to work in a provided mode.(sorted by label of the
     * extension).
     * 
     * @return array of {@link BOMGenerator2Extension}s with specified
     *         generation mode - empty array if no extensions found.
     */
    public BOMGenerator2Extension[] getExtensions(GenerationMode genMode) {

        Set<BOMGenerator2Extension> selectedExts =
                new TreeSet<BOMGenerator2Extension>();

        for (BOMGenerator2Extension ext : extensionMap.values()) {
            boolean isRequestedGenerationMode =
                    (ext != null) && ext.getGenerationMode().equals(genMode);
            if (isRequestedGenerationMode) {
                selectedExts.add(ext);
            }
        }

        return selectedExts.toArray(new BOMGenerator2Extension[selectedExts
                .size()]);
    }

    /**
     * Get all the projects that will potentially be generated by all the
     * extensions for the given BOM file. This will return the immediate
     * projects and does not provide the projects of any BOMs that may be
     * referenced by the given resource.
     * 
     * @param bomFile
     * @return Collection of {@link IProject projects}, empty list if no
     *         extensions found. Note that this will be <code>IProject</code>
     *         handles and project(s) may not exist.
     */
    public Collection<IProject> getGeneratedProjects(IFile bomFile) {
        Set<IProject> projects = new HashSet<IProject>();

        for (BOMGenerator2Extension extension : getExtensions()) {
            try {
                if (extension.getGenerator()
                        .supports(Collections.singleton(bomFile),
                                new NullProgressMonitor())) {
                    Collection<IProject> generatedProjects =
                            getGeneratedProjects(bomFile, extension);
                    if (generatedProjects != null) {
                        projects.addAll(generatedProjects);
                    }
                }
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return projects;
    }

    /**
     * Get the project that will be generated by the extension id given for the
     * given BOM file. This will return the immediate projects and does not
     * provide the projects of any BOMs that may be referenced by the given
     * resource.
     * 
     * @param bomFile
     * @param extensionId
     * @return Collection of {@link IProject projects}, empty list if the
     *         extension is not found. Note that this will be
     *         <code>IProject</code> handles and project(s) may not exist.
     */
    public Collection<IProject> getGeneratedProjects(IFile bomFile,
            String extensionId) {
        BOMGenerator2Extension extension = getExtension(extensionId);
        if (extension != null) {
            try {
                if (extension.getGenerator()
                        .supports(Collections.singleton(bomFile),
                                new NullProgressMonitor())) {
                    return getGeneratedProjects(bomFile, extension);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        } else {
            LOG.info(String
                    .format("There is no BOMGenerator2 extension with the id '%s'", //$NON-NLS-1$
                            extensionId));
            return Collections.emptyList();
        }
    }

    /**
     * Get the project that will be generated by the extension for the given BOM
     * file. This will return the immediate projects and does not provide the
     * projects of any BOMs that may be referenced by the given resource.
     * 
     * @param bomFile
     * @param extension
     * @return Collection of {@link IProject projects}, empty list if the
     *         extension is not found. Note that this will be
     *         <code>IProject</code> handles and project(s) may not exist.
     */
    public Collection<IProject> getGeneratedProjects(IFile bomFile,
            BOMGenerator2Extension extension) {

        Set<IProject> projects = new HashSet<IProject>();
        if (bomFile != null && extension != null) {

            Model model = BOMUtils.getModel(bomFile);
            if (model != null && BOMGeneratorHelper.hasContent(bomFile)) {

                String name = getGeneratedProjectName(model, extension);
                if (name != null) {

                    projects.add(ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(name));
                } else {

                    LOG.info(String
                            .format(Messages.BOMGenerator2ExtensionHelper_BDS_model_bundle_notfound_info_message,
                                    bomFile.getName()));
                }
            } else {

                LOG.info(String
                        .format(Messages.BOMGenerator2ExtensionHelper_BOM_model_empty_info_message,
                                bomFile.getName()));
            }
        }
        return projects;
    }

    /**
     * Get the {@link Model} from the BOM file given.
     * 
     * @param bomFile
     * @return <code>Model</code> or <code>null</code> if this is not a BOM file
     *         or does not contain a {@link Model} as the top-level element.
     * @deprecated Use {@link BOMUtils#getModel(IFile)} instead
     */
    @Deprecated
    public static Model getModel(IFile bomFile) {
        return BOMUtils.getModel(bomFile);
    }

    /**
     * Get the name of the project that should be generated for the given BOM
     * file and BOM Generator2 extension. This will prefix the name of the Model
     * with a '.' and suffix using the suffix string provided in the extension
     * ('.' delimited).
     * 
     * @param model
     * @param extension
     * @return name of the project to generate.
     */
    public static String getGeneratedProjectName(Model model,
            BOMGenerator2Extension extension) {
        String name = null;

        if (model != null && extension != null) {
            name = "." + model.getName(); //$NON-NLS-1$
            String suffix = extension.getSuffix();
            if (suffix != null && suffix.length() > 0) {
                name += "." + suffix; //$NON-NLS-1$
            }
        }

        return name;
    }

}
