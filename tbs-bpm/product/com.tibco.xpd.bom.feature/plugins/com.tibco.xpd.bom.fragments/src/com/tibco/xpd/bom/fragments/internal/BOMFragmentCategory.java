/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.fragments.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.fragments.Activator;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.wc.gmf.XpdResourceFactory;

/**
 * Represents the BOM fragment category extension. This will provide information
 * of any sub-categories and fragment BOM models contained at the location of
 * this category.
 * 
 * @author njpatel
 * 
 */
public class BOMFragmentCategory {

    private static final String EXTENSION_ID = "bomFragment"; //$NON-NLS-1$
    private static final String ELEM_CATEGORY = "category"; //$NON-NLS-1$

    private final String id;
    private final String name;
    private final String description;
    private List<BOMFragment> fragments;
    private List<BOMFragmentCategory> subCategories;
    private File folder;
    private final TransactionalEditingDomain ed = XpdResourcesPlugin
            .getDefault().getEditingDomain();
    private String location;
    private final IConfigurationElement element;

    protected BOMFragmentCategory(IConfigurationElement element) {
        this.element = element;
        id = element.getAttribute("id"); //$NON-NLS-1$
        name = element.getAttribute("name"); //$NON-NLS-1$
        description = element.getAttribute("description"); //$NON-NLS-1$
        location = element.getAttribute("location"); //$NON-NLS-1$
    }

    /**
     * Get the id of the category extension.
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name of the category extension.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the category extension.
     * 
     * @return description or <code>null</code> if no description provided.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get all fragment models found at this category's location.
     * 
     * @return list of {@link BOMFragment}s - empty list if no BOM models are
     *         found under this category.
     */
    public List<BOMFragment> getFragments() {
        if (fragments == null) {
            fragments = new ArrayList<BOMFragment>();
            // Find the fragments at this category location
            URL url = FileLocator.find(Activator.getDefault().getBundle(),
                    new Path(location), null);

            if (url != null) {
                try {
                    url = FileLocator.toFileURL(url);
                    folder = new File(url.getFile());

                    if (folder.exists()) {
                        // Get all BOM files
                        File[] files = folder.listFiles(new FilenameFilter() {
                            public boolean accept(File dir, String name) {
                                return name.endsWith(".bom"); //$NON-NLS-1$
                            }
                        });

                        if (files != null) {
                            IPath path = new Path(location).makeRelative();

                            for (File file : files) {
                                try {
                                    // Create a BOM fragment for each file
                                    BOMFragment fragment = new BOMFragment(
                                            file, path.append(file.getName())
                                                    .toString());
                                    if (fragment.isValidModel()) {
                                        fragments.add(fragment);
                                    }
                                } catch (IOException e) {
                                    Activator
                                            .getDefault()
                                            .getLogger()
                                            .error(
                                                    e,
                                                    String
                                                            .format(
                                                                    "Trying to access file '%s'.",
                                                                    file
                                                                            .getName()));
                                }
                            }
                        }

                    } else {
                        throw new FileNotFoundException(String.format(
                                "Location '%s' does not exist.", location));
                    }
                } catch (IOException e) {
                    Activator.getDefault().getLogger().error(e,
                            String.format("Trying to access '%s'", location));
                }
            } else {
                throw new IllegalArgumentException(String.format(
                        "Location '%s' not found.", location));
            }
        }
        return fragments;
    }

    /**
     * Get any sub-categories of this category.
     * 
     * @return list of sub-categories, empty list if none specified.
     */
    public List<BOMFragmentCategory> getSubCategories() {
        if (subCategories == null) {
            subCategories = new ArrayList<BOMFragmentCategory>();
            // Add sub-categories if any
            IConfigurationElement[] children = element
                    .getChildren(ELEM_CATEGORY);
            if (children != null) {
                for (IConfigurationElement child : children) {
                    subCategories.add(new BOMFragmentCategory(child));
                }
            }
        }
        return subCategories;
    }

    /**
     * Dispose off resources. This will unload all BOM resources loaded to
     * created fragments.
     */
    public void dispose() {
        if (fragments != null) {
            for (BOMFragment frag : fragments) {
                frag.dispose();
            }
            fragments.clear();
            fragments = null;
        }

        if (subCategories != null) {
            for (BOMFragmentCategory cat : subCategories) {
                cat.dispose();
            }
            subCategories.clear();
            subCategories = null;
        }
    }

    /**
     * Get the top-level BOM categories defined in the plugin.xml
     * 
     * @return top level categories
     */
    public static List<BOMFragmentCategory> getCategories() {
        List<BOMFragmentCategory> categories = new ArrayList<BOMFragmentCategory>();

        IExtensionPoint ePoint = Platform.getExtensionRegistry()
                .getExtensionPoint(Activator.PLUGIN_ID, EXTENSION_ID);
        if (ePoint != null) {
            for (IConfigurationElement element : ePoint
                    .getConfigurationElements()) {
                categories.add(new BOMFragmentCategory(element));
            }
        }

        return categories;
    }

    /**
     * Get a BOM category extension with the given id.
     * 
     * @param id
     * @return <code>BOMFragmentCategory</code> if found with the given id,
     *         <code>null</code> otherwise.
     */
    public static BOMFragmentCategory getCategory(String id) {
        if (id != null) {
            List<BOMFragmentCategory> categories = getCategories();
            for (BOMFragmentCategory category : categories) {
                if (category.getId().equals(id)) {
                    return category;
                }
            }

            // Search sub-categories
            for (BOMFragmentCategory category : categories) {
                BOMFragmentCategory ret = getCategory(category, id);
                if (ret != null) {
                    return ret;
                }
            }
        }

        return null;
    }

    /**
     * Search the root category's sub-categories for a category extension with
     * the given id
     * 
     * @param root
     * @param id
     * @return
     */
    private static BOMFragmentCategory getCategory(BOMFragmentCategory root,
            String id) {
        if (root != null) {
            List<BOMFragmentCategory> subCats = root.getSubCategories();
            for (BOMFragmentCategory cat : subCats) {
                if (cat.getId().equals(id)) {
                    return cat;
                }
            }

            // Search sub-categories of the this category's sub-categories
            for (BOMFragmentCategory cat : subCats) {
                BOMFragmentCategory ret = getCategory(cat, id);
                if (ret != null) {
                    return ret;
                }
            }
        }

        return null;
    }

    /**
     * Represents a BOM fragment model at the location of the parent category.
     * 
     * @author njpatel
     * 
     */
    public class BOMFragment {

        private final String filePath;
        private Resource resource;
        private Model model;
        private Properties prop;

        protected BOMFragment(File fragmentFile, String relativeFilePath)
                throws IOException {
            this.filePath = relativeFilePath;

            URI uri = URI.createFileURI(fragmentFile.getPath());
            // resource = ed.getResourceSet().getResource(uri, true);
            resource = ed.getResourceSet().createResource(uri);
            resource.load(XpdResourceFactory.getDefaultLoadOptions());

            if (resource != null) {
                for (EObject eo : resource.getContents()) {
                    if (eo instanceof Model) {
                        model = (Model) eo;
                        break;
                    }
                }
            }

            if (isValidModel()) {
                // Load the properties file
                String path = filePath.replaceFirst("\\.bom$", ".properties");
                URL url = FileLocator.find(Activator.getDefault().getBundle(),
                        new Path(path), null);
                if (url != null) {
                    url = FileLocator.toFileURL(url);
                    File file = new File(url.getFile());

                    if (file.exists()) {
                        FileInputStream in = null;
                        try {
                            in = new FileInputStream(file);
                            prop = new Properties();
                            prop.load(in);
                        } finally {
                            if (in != null) {
                                in.close();
                            }
                        }
                    }
                }
            }
        }

        /**
         * Check if this BOM model is a valid fragment. This will return
         * <code>true</code> if the resource was loaded and it contained a
         * {@link Model}.
         * 
         * @return
         */
        public boolean isValidModel() {
            return resource != null && model != null;
        }

        /**
         * Get the value to be used as the key for this fragment - this will be
         * the file's plug-in relative path.
         * 
         * @return
         */
        public String getKey() {
            return filePath;
        }

        /**
         * Get the value to be used as the name of the fragment - this will be
         * the Model's name.
         * 
         * @return
         */
        public String getName() {
            return model != null ? model.getName() : null;
        }

        /**
         * Get the value to be used as the description of the fragment. The
         * properties file accompanying this fragment file will first be checked
         * for the description, if it does not contain any then the Model's
         * description will be used.
         * 
         * @return
         */
        public String getDescription() {
            String description = null;

            if (prop != null) {
                description = BOMFragmentLocalizer.getDescription(prop, model);
            }

            if (description == null && model != null) {
                EList<Comment> comments = model.getOwnedComments();
                if (comments != null && !comments.isEmpty()) {
                    description = comments.get(0).getBody();
                }
            }
            return description;
        }

        /**
         * Get the fragment data from the BOM model.
         * 
         * @return
         */
        public String getData() {
            if (resource != null) {
                List<EObject> contents = getContents(resource);

                if (!contents.isEmpty()) {
                    return BOMCopyPasteCommandFactory.getInstance()
                            .copyToString(contents);
                }
            }
            return null;
        }

        /**
         * Get contents of the given resource.
         * 
         * @param resource
         * @return
         */
        private List<EObject> getContents(Resource resource) {
            List<EObject> contents = new ArrayList<EObject>();

            EList<EObject> resContent = resource.getContents();

            for (EObject eo : resContent) {
                if (eo instanceof Model || eo instanceof Diagram) {
                    contents.addAll(eo.eContents());
                }
            }
            return contents;
        }

        /**
         * Get the image data of the BOM model.
         * 
         * @return
         */
        public ImageData getImageData() {
            ImageData imgData = null;
            Diagram diag = null;

            for (EObject eo : resource.getContents()) {
                if (eo instanceof Diagram) {
                    diag = (Diagram) eo;
                    break;
                }
            }

            if (diag != null) {
                FragmentImageGenerator generator = new FragmentImageGenerator(
                        diag);
                XpdResourcesPlugin.getStandardDisplay().syncExec(generator);
                imgData = generator.getImageData();
            }
            return imgData;
        }

        /**
         * Dispose off all resources
         */
        public void dispose() {
            if (prop != null) {
                prop = null;
            }
            if (resource != null) {
                if (resource.isLoaded()) {
                    resource.unload();
                }
                ed.getResourceSet().getResources().remove(resource);
            }
        }
    }
}
