/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.fragments.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.fragments.Activator;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * The BOM fragment localizer. This will provide the localized fragment and
 * image data for a given locale.
 * 
 * @author njpatel
 * 
 */
public class BOMFragmentLocalizer {
    /** Label key prefix in the model's properties file */
    private static final String LABEL_KEY_PREFIX = "_label_"; //$NON-NLS-1$
    private static final String DESC_KEY_PREFIX = "_shortdesc_"; //$NON-NLS-1$

    private boolean canLocalize = false;
    private File fragmentFile;
    private final TransactionalEditingDomain ed;
    private Properties fragmentProperties;
    private final IFragment fragment;

    /**
     * BOM fragments localizer.
     * 
     * @param fragment
     *            fragment to localize
     * @param NL
     *            locale string
     */
    public BOMFragmentLocalizer(IFragment fragment, String NL) {
        this.fragment = fragment;
        ed = XpdResourcesPlugin.getDefault().getEditingDomain();

        String relativePath = fragment.getKey();

        if (relativePath != null) {
            URL url = FileLocator.find(Activator.getDefault().getBundle(),
                    new Path(relativePath), null);
            if (url != null) {
                try {
                    url = FileLocator.toFileURL(url);
                    fragmentFile = new File(url.getFile());

                    if (fragmentFile.exists()) {
                        fragmentProperties = getProperties(relativePath, NL);
                        canLocalize = fragmentProperties != null;
                    }
                } catch (IOException e) {
                    Activator.getDefault().getLogger().error(
                            e,
                            String.format("Trying to localize '%s'.", fragment
                                    .getName()));
                }
            }
        }
    }

    /**
     * Check if the fragment can be localized to the given locale.
     * 
     * @return <code>true</code> if can be localized.
     */
    public boolean canLocalize() {
        return canLocalize;
    }

    public String getLocalizedName() {
        String name = null;
        if (canLocalize) {
            Resource res = loadResource();
            if (res != null) {
                try {
                    Model model = getModel(res);
                    if (model != null) {
                        name = getLabel(fragmentProperties, model);
                    }

                } finally {
                    unloadResource(res);
                }
            }
        }
        return name;
    }

    public String getLocalizedDescription() {
        String desc = null;
        if (canLocalize) {
            Resource res = loadResource();
            if (res != null) {
                try {
                    Model model = getModel(res);
                    if (model != null) {
                        desc = getDescription(fragmentProperties, model);
                    }
                } finally {
                    unloadResource(res);
                }
            }
        }
        return desc;
    }

    /**
     * Get the localized data of the fragment.
     * 
     * @return fragment data or <code>null</code> if it cannot be localized.
     */
    public String getLocalizedData() {
        String data = null;
        if (canLocalize) {
            Resource resource = loadResource();
            if (resource != null) {
                try {
                    List<EObject> contents = new ArrayList<EObject>();

                    for (EObject eo : resource.getContents()) {
                        if (eo instanceof Model) {
                            contents.addAll(getLocalizedContent((Model) eo));
                        } else if (eo instanceof Diagram) {
                            contents.addAll(eo.eContents());
                        }
                    }

                    if (!contents.isEmpty()) {
                        data = BOMCopyPasteCommandFactory.getInstance()
                                .copyToString(contents);
                    }

                } catch (InterruptedException e) {
                    // Do nothing
                } catch (RollbackException e) {
                    IStatus status = e.getStatus();
                    if (status != null) {
                        String message = String.format(
                                "Trying to localize fragment '%s'", fragment
                                        .getName());
                        switch (status.getSeverity()) {
                        case IStatus.ERROR:
                            Activator.getDefault().getLogger()
                                    .error(e, message);
                            break;
                        case IStatus.WARNING:
                            Activator.getDefault().getLogger().warn(e, message);
                            break;
                        }
                    } else {
                        Activator.getDefault().getLogger().error(e);
                    }
                } finally {
                    unloadResource(resource);
                }
            }
        }

        return data;
    }

    /**
     * Get the localized image data of the fragment.
     * 
     * @return {@link ImageData} or <code>null</code> if cannot be localized.
     */
    public ImageData getLocalizedImage() {
        ImageData imgData = null;

        if (canLocalize) {
            Resource resource = loadResource();
            if (resource != null) {
                try {
                    // Localize the model
                    getLocalizedContent(getModel(resource));

                    // Generate the image of the diagram
                    Diagram diag = getDiagram(resource);
                    if (diag != null) {
                        FragmentImageGenerator generator = new FragmentImageGenerator(
                                diag);
                        XpdResourcesPlugin.getStandardDisplay().syncExec(
                                generator);
                        imgData = generator.getImageData();
                    }
                } catch (InterruptedException e) {
                    // Do nothing
                } catch (RollbackException e) {
                    IStatus status = e.getStatus();
                    if (status != null) {
                        String message = String.format(
                                "Trying to localize fragment image '%s'",
                                fragment.getName());
                        switch (status.getSeverity()) {
                        case IStatus.ERROR:
                            Activator.getDefault().getLogger()
                                    .error(e, message);
                            break;
                        case IStatus.WARNING:
                            Activator.getDefault().getLogger().warn(e, message);
                            break;
                        }
                    } else {
                        Activator.getDefault().getLogger().error(e);
                    }
                } finally {
                    unloadResource(resource);
                }
            }
        }

        return imgData;
    }

    /**
     * Get the {@link Diagram} object from the given resource.
     * 
     * @param res
     * @return <code>Diagram</code> or <code>null</code> if not found.
     */
    private Diagram getDiagram(Resource res) {
        if (res != null) {
            for (EObject eo : res.getContents()) {
                if (eo instanceof Diagram) {
                    return (Diagram) eo;
                }
            }
        }
        return null;
    }

    /**
     * Get the {@link Model} object from the resource.
     * 
     * @param res
     * @return <code>Model</code> or <code>null</code> if not found.
     */
    private Model getModel(Resource res) {
        if (res != null) {
            for (EObject eo : res.getContents()) {
                if (eo instanceof Model) {
                    return (Model) eo;
                }
            }
        }
        return null;
    }

    /**
     * Get the localized content of the {@link Model}. (This runs in a
     * write-transaction.)
     * 
     * @param model
     *            model to localize
     * @return
     * @throws InterruptedException
     * @throws RollbackException
     */
    private List<EObject> getLocalizedContent(Model model)
            throws InterruptedException, RollbackException {
        InternalTransaction tx = ((InternalTransactionalEditingDomain) ed)
                .startTransaction(false, null);
        if (tx != null) {
            try {
                localize(model);
            } finally {
                tx.commit();
            }
        }

        return model.eContents();
    }

    /**
     * Localize the given <code>EObject</code>. This is a recursive method in
     * which all contents of this object will be localized before it is (has to
     * be a {@link NamedElement} object to be localized).
     * 
     * @param eObject
     */
    private void localize(EObject eObject) {
        if (eObject != null) {
            EList<EObject> contents = eObject.eContents();

            if (contents != null && !contents.isEmpty()) {
                for (EObject eo : contents) {
                    localize(eo);

                    if (eo instanceof NamedElement) {
                        NamedElement elem = (NamedElement) eo;
                        String label = getLabel(fragmentProperties, elem);

                        if (label != null) {
                            elem.setName(label);
                        }
                    }
                }
            }
        }
    }

    /**
     * Load the resource of this fragment.
     * 
     * @return
     */
    private Resource loadResource() {
        Resource res = null;
        if (fragmentFile != null) {
            URI uri = URI.createFileURI(fragmentFile.getPath());
            res = ed.getResourceSet().getResource(uri, true);
        }
        return res;
    }

    /**
     * Unload the given resource.
     * 
     * @param res
     */
    private void unloadResource(Resource res) {
        if (res != null) {
            if (res.isLoaded()) {
                res.unload();
            }
            ed.getResourceSet().getResources().remove(res);

        }
    }

    /**
     * Get the properties file associated with the file with the given path and
     * for the given locale. (This will work across plug-in fragments.)
     * 
     * @param pluginRelativePath
     *            relative path of the file whose properties is required
     * @param NL
     *            locale string, <code>null</code> if default properties
     *            required.
     * @return {@link Properties} if found, <code>null</code> otherwise.
     * @throws IOException
     */
    public static Properties getProperties(String pluginRelativePath, String NL)
            throws IOException {
        Properties prop = null;
        if (pluginRelativePath != null) {
            if (NL != null) {
                pluginRelativePath = pluginRelativePath.replaceAll(
                        "\\.[\\w[\\.]]*$", "_" + NL + ".properties");
            } else {
                pluginRelativePath = pluginRelativePath.replaceAll(
                        "\\.[\\w[\\.]]*$", ".properties");
            }
            URL url = FileLocator.find(Activator.getDefault().getBundle(),
                    new Path(pluginRelativePath), null);

            if (url != null) {
                url = FileLocator.toFileURL(url);
                File propertiesFile = new File(url.getFile());

                if (propertiesFile.exists()) {
                    prop = new Properties();
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(propertiesFile);
                        prop.load(in);
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                    }
                }
            }
        }

        return prop;
    }

    /**
     * Get the label of this {@link NamedElement}. This will look up the
     * localized properties file provided.
     * 
     * @param properties
     *            properties file
     * @param namedElement
     *            <code>NamedElement</code>
     * @return label if found, <code>null</code> otherwise.
     */
    public static String getLabel(Properties properties,
            NamedElement namedElement) {

        if (properties != null && namedElement != null) {
            String qualifiedName = namedElement.getQualifiedName();

            if (qualifiedName != null && qualifiedName.length() > 0) {
                String key = qualifiedName.replace(':', '_');
                key = LABEL_KEY_PREFIX + UML2Util.getValidJavaIdentifier(key);

                return properties.getProperty(key);
            }
        }

        return null;
    }

    /**
     * Get the description of the given {@link NamedElement}. This will look up
     * the localized properties provided.
     * 
     * @param properties
     *            properties file
     * @param namedElement
     *            <code>NamedElement</code>
     * @return description if found, <code>null</code> otherwise.
     */
    public static String getDescription(Properties properties,
            NamedElement namedElement) {

        if (properties != null && namedElement != null) {
            String qualifiedName = namedElement.getQualifiedName();

            if (qualifiedName != null && qualifiedName.length() > 0) {
                String key = qualifiedName.replace(':', '_');
                key = DESC_KEY_PREFIX + UML2Util.getValidJavaIdentifier(key);

                return properties.getProperty(key);
            }
        }
        return null;
    }
}
