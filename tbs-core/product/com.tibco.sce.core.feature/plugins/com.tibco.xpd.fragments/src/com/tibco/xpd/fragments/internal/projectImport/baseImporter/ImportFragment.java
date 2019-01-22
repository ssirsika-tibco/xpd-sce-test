/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.projectImport.baseImporter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsContributor;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.FragmentsContributor.UpdateResult;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentProperties;

/**
 * The fragment in the external project being imported.
 * 
 * @author njpatel
 * 
 */
public class ImportFragment extends ImportFragmentElement implements IFragment {

    private final Object imgSource;
    private String data;
    private IFragmentCategory parent;

    /**
     * The fragment in the external project being imported.
     * 
     * @param provider
     *            fragment provider of this fragment
     * @param fragmentSource
     *            source fragment in the external project
     * @param imgSource
     *            image source of the fragment
     * @param structureProvider
     *            import structure provider
     * @param properties
     *            properties file of this fragment
     * @param defaultName
     *            default name if no name for this fragment found in the
     *            properties
     */
    public ImportFragment(FragmentsProvider provider, Object fragmentSource,
            Object imgSource, IImportStructureProvider structureProvider,
            FragmentProperties properties, String defaultName) {
        super(provider, fragmentSource, structureProvider, properties,
                defaultName);
        this.imgSource = imgSource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.IFragment#createImage(org.eclipse.swt.graphics
     * .Device)
     */
    public Image createImage(Device device) {
        return null;
    }

    /**
     * Get the image source of this fragment.
     * 
     * @return Image source from the external project
     */
    public Object getImgSource() {
        return imgSource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getData()
     */
    public String getData() {
        if (data == null) {
            InputStream contents = getStructureProvider().getContents(
                    getImportSource());
            data = getContent(contents);
            try {
                contents.close();
            } catch (IOException e) {
                // Do nothing
            }
        }
        return data;
    }

    /**
     * Set the data of this fragment
     * 
     * @param data
     *            fragment data
     */
    private void setData(String data) {
        this.data = data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getImageData()
     */
    public ImageData getImageData() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getLocalizedData()
     */
    public String getLocalizedData() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getLocalizedImageData()
     */
    public ImageData getLocalizedImageData() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IContainedFragmentElement#getParent()
     */
    public IFragmentCategory getParent() {
        return parent;
    }

    /**
     * Set the parent of this fragment.
     * 
     * @param parent
     *            category
     */
    public void setParent(IFragmentCategory parent) {
        this.parent = parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#isSystem()
     */
    public boolean isSystem() {

        IFragmentCategory parent = getParent();

        if (parent != null) {
            return parent.isSystem();
        }

        return false;
    }

    @Override
    public void doImport(FragmentCategoryImpl targetCategory,
            String fragmentVersion, boolean needsUpdate) throws CoreException {
        if (targetCategory != null
                && targetCategory.getResource() instanceof IFolder
                && targetCategory.getResource().isAccessible()) {
            IFolder targetFolder = (IFolder) targetCategory.getResource();
            String fragFileName = getResourceName();

            if (fragFileName != null) {
                final IFile file = targetFolder.getFile(fragFileName);
                boolean importImage = true;

                // Only import if the fragment file doesn't already exist in the
                // workspace
                if (!file.exists()) {
                    // If needs updating then update
                    if (needsUpdate && getProvider() != null) {
                        FragmentsContributor contributor = getProvider()
                                .getProviderClass();

                        UpdateResult result = contributor.updateContent(this,
                                fragmentVersion);

                        if (result != null) {
                            String data = result.getData();

                            if (data != null) {
                                setData(data);
                            }
                            importImage = !result.doClearImage();
                        }
                    }

                    // Import the fragments and image
                    FragmentsManager.getInstance().runWorkspaceOperation(
                            new ImportFragmentRunnable(file,
                                    importImage ? imgSource : null),
                            targetFolder, null);

                    FragmentImpl fragment = targetCategory
                            .createChildFragment(file);
                    fragment.setDetails(getKey(), getName(), getDescription());
                }
            }
        }
    }

    /**
     * Get the contents from the input stream.
     * 
     * @param in
     *            input stream
     * @return content from the input stream, <code>null</code> if none found.
     */
    private String getContent(InputStream in) {
        String data = null;
        if (in != null) {
            // Load the data
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();

            try {
                byte[] buffer = new byte[512];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    oStream.write(buffer, 0, bytesRead);
                }

                data = oStream.toString();

            } catch (IOException e) {
                FragmentsActivator.getDefault().getLogger().error(e);
            } finally {
                try {
                    oStream.close();
                } catch (IOException e) {
                    // Do nothing, closing streams
                }
            }
        }
        return data;
    }

    /**
     * Workspace runnable to import the given fragment file and image.
     * 
     * @author njpatel
     * 
     */
    private class ImportFragmentRunnable implements IWorkspaceRunnable {
        private final IFile fragmentFile;
        private final Object imgFile;

        /**
         * Import the given fragment file and image.
         * 
         * @param file
         *            fragment file
         * @param imgFile
         *            fragment's image file
         */
        public ImportFragmentRunnable(IFile file, Object imgFile) {
            this.fragmentFile = file;
            this.imgFile = imgFile;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core
         * .runtime.IProgressMonitor)
         */
        public void run(IProgressMonitor monitor) throws CoreException {
            if (fragmentFile != null) {
                String data = getData();

                if (data != null) {
                    InputStream in = new ByteArrayInputStream(data.getBytes());
                    fragmentFile.create(in, false, monitor);

                    if (imgFile != null) {
                        IImportStructureProvider structureProvider = getStructureProvider();
                        in = structureProvider.getContents(imgFile);

                        if (in != null) {
                            try {
                                IFile file = fragmentFile.getParent().getFile(
                                        new Path(structureProvider
                                                .getLabel(imgFile)));
                                file.create(in, false, monitor);
                            } finally {
                                try {
                                    in.close();
                                } catch (IOException e) {
                                    // Do nothing
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
