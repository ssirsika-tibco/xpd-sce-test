/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.FragmentsManager;

/**
 * Fragment. Implementation of {@link IFragment}.
 * 
 * @author njpatel
 * 
 */
public class FragmentImpl extends FragmentElementImpl implements IFragment {

    private FragmentCategoryImpl parent;

    private String data;

    private String localizedData;

    private ImageData localizedImgData;

    private ImageData imgData;

    private IFile imageFile;

    // NOTE: The following 2 values should be changed together.
    public static int IMG_TYPE = SWT.IMAGE_JPEG;

    public static String IMG_FILE_EXT = "jpg"; //$NON-NLS-1$

    /**
     * Fragment.
     * 
     * @param file
     *            fragment file
     */
    public FragmentImpl(IFile file) {
        super(file);
    }

    @Override
    public Collection<IResource> getAllResources() {
        final Set<IResource> resources = new HashSet<IResource>();
        if (getResource() != null) {
            resources.add(getResource());

            // Get all fragment and image files associated with this resource
            String filename =
                    getResource().getFullPath().removeFileExtension()
                            .lastSegment();

            final Pattern imgFilePattern = Pattern.compile(filename + ".*\\." //$NON-NLS-1$
                    + IMG_FILE_EXT);
            final Pattern frgFilePattern = Pattern.compile(filename + ".+\\." //$NON-NLS-1$
                    + FragmentConsts.FRAGMENT_FILE_EXT);

            try {
                getResource().getParent().accept(new IResourceProxyVisitor() {

                    @Override
                    public boolean visit(IResourceProxy proxy)
                            throws CoreException {
                        if (proxy.getType() == IResource.FOLDER
                                && proxy.getName().equals(getResource()
                                        .getParent().getName())) {
                            return true;
                        }

                        if (proxy.getType() == IResource.FILE) {
                            String name = proxy.getName();

                            IResource res = null;
                            // Check if this resource is the fragment image
                            Matcher matcher = imgFilePattern.matcher(name);
                            if (matcher.matches()) {
                                res = proxy.requestResource();
                            }
                            // If not image resource then check if it's the
                            // fragment resource
                            if (res == null) {
                                matcher = frgFilePattern.matcher(name);

                                if (matcher.matches()) {
                                    res = proxy.requestResource();
                                }
                            }

                            if (res != null) {
                                resources.add(res);
                            }
                        }
                        return false;
                    }

                },
                        IContainer.INCLUDE_PHANTOMS);
            } catch (CoreException e) {
                // Do nothing
            }
        }

        return resources;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getData()
     */
    @Override
    public String getData() {
        if (data == null && getResource() instanceof IFile) {
            localizedData = null;
            data = getContent((IFile) getResource());
        }
        return data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getLocalizedData()
     */
    @Override
    public String getLocalizedData() {
        if (localizedData == null && getResource() instanceof IFile) {
            IFile fragmentFile = null;
            if (isSystem()) {
                fragmentFile =
                        getLocalizedFile(FragmentConsts.FRAGMENT_FILE_EXT,
                                new LocaleDataFileCreator());

                if (fragmentFile != null && fragmentFile.exists()) {
                    localizedData = getContent(fragmentFile);
                }
            } else {
                localizedData = getData();
            }
            data = null;
        }
        return localizedData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragmentElement#isSystem()
     */
    @Override
    public boolean isSystem() {
        boolean system = false;

        // If the parent category is a system category then this fragment is a
        // system fragment
        if (getParent() != null) {
            system = getParent().isSystem();
        }

        return system;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#createImage(org.eclipse.swt
     * .graphics.Device)
     */
    @Override
    public Image createImage(Device device) {
        IFile file = getImageFile();
        Image img = null;
        if (file != null) {
            // Check if the file is in sync with the file system - if not then
            // synch
            if (!file.isSynchronized(IResource.DEPTH_ZERO)) {
                try {
                    file.refreshLocal(IResource.DEPTH_ZERO, null);
                } catch (CoreException e) {
                    FragmentsActivator
                            .getDefault()
                            .getLogger()
                            .warn(e,
                                    "Create fragment image - resync with file system"); //$NON-NLS-1$
                }
            }
            if (file.exists()) {
                try {
                    img = new Image(device, file.getLocation().toOSString());
                } catch (SWTException e) {
                    FragmentsActivator.getDefault().getLogger()
                            .warn(e, "Create fragment image"); //$NON-NLS-1$
                    img = null;
                }
            }
        }

        if (img == null) {
            InputStream stream = null;
            try {
                stream =
                        FileLocator.openStream(FragmentsActivator.getDefault()
                                .getBundle(), new Path(
                                FragmentConsts.IMG_NO_FRAGMENT), false);
                if (stream != null) {
                    img = new Image(device, stream);
                }
            } catch (IOException e) {
                // Do nothing
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        // Do nothing
                    }
                }
            }

        }
        return img;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getImageData()
     */
    @Override
    public ImageData getImageData() {
        if (imgData == null) {
            localizedImgData = null;
            IFile file = getAssociatedFile(IMG_FILE_EXT, null);

            if (file != null && file.exists()) {
                imgData = new ImageData(file.getLocation().toOSString());
            } else {
                imgData = getDefaultImageData();
            }
        }
        return imgData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IFragment#getLocalizedImageData()
     */
    @Override
    public ImageData getLocalizedImageData() {
        if (localizedImgData == null) {

            if (isSystem()) {
                IFile file = getImageFile();

                if (file != null) {
                    localizedImgData =
                            new ImageData(file.getLocation().toOSString());
                } else {
                    localizedImgData = getDefaultImageData();
                }
            } else {
                localizedImgData = getImageData();
            }

            imgData = null;
        }
        return localizedImgData;
    }

    /**
     * Get the default image data when the fragment has no image.
     * 
     * @return <code>ImageData</code>.
     */
    private ImageData getDefaultImageData() {
        ImageData data = null;
        ImageDescriptor imageDesc =
                FragmentsActivator
                        .imageDescriptorFromPlugin(FragmentsActivator.PLUGIN_ID,
                                FragmentConsts.IMG_NO_FRAGMENT);

        if (imageDesc != null) {
            data = imageDesc.getImageData();
        }
        return data;
    }

    /**
     * Get the contents of the file.
     * 
     * @param file
     *            file to get content of.
     * @return content of file, <code>null</code> if none found.
     */
    private String getContent(IFile file) {
        String data = null;
        if (file != null && file.exists()) {
            // Load the data
            InputStream contents = null;
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();

            try {
                contents = file.getContents();
                byte[] buffer = new byte[512];
                int bytesRead;

                while ((bytesRead = contents.read(buffer)) != -1) {
                    oStream.write(buffer, 0, bytesRead);
                }

                data = oStream.toString();

            } catch (CoreException e) {
                FragmentsActivator.getDefault().getLogger().error(e);
            } catch (IOException e) {
                FragmentsActivator.getDefault().getLogger().error(e);
            } finally {
                try {
                    if (contents != null) {
                        contents.close();
                    }
                    oStream.close();
                } catch (IOException e) {
                    // Do nothing, closing streams
                }
            }
        }
        return data;
    }

    /**
     * Get the localized file with the given file extension. If this file does
     * not exist then the provider will be asked for the localized version of
     * the data. The creator will be used to access this data(T) from the
     * provider and then save the data if provided.
     * 
     * @param <T>
     *            data being accessed and saved
     * @param fileExt
     *            file extension
     * @param creator
     *            file creator
     * @return localized file if found, <code>null</code> otherwise.
     */
    private <T> IFile getLocalizedFile(String fileExt,
            ILocaleFileCreator<T> creator) {
        String nl = Platform.getNL();
        IFile file = null;

        do {
            file = getAssociatedFile(fileExt, nl);

            if (file == null || nl == null) {
                break;
            }

            if (!file.exists()) {
                // Ask the provider for the localized file
                if (getProvider() != null) {
                    try {
                        T data = creator.getData(getProvider(), nl);

                        if (data != null) {
                            creator.save(file, data);
                        }
                    } catch (CoreException e) {
                        FragmentsActivator.getDefault().getLogger().error(e);
                    }
                }

                if (nl.indexOf('_') > 0) {
                    nl = nl.substring(0, nl.lastIndexOf('_'));
                } else {
                    nl = null;
                }
            }
        } while (!file.exists());

        return file != null && file.exists() ? file : null;
    }

    /**
     * Get the localized image file. If the localized file is not found locally
     * then ask the provider for it (if provider provides the image data then
     * this will be saved locally for future use).
     * 
     * @return image file or <code>null</code> if no image file is found.
     */
    private IFile getImageFile() {
        if (imageFile == null) {
            if (isSystem()) {
                imageFile =
                        getLocalizedFile(IMG_FILE_EXT,
                                new LocaleImageFileCreator());
            } else {
                // User fragment so get the default image file
                IFile file = getAssociatedFile(IMG_FILE_EXT, null);

                if (file != null && file.exists()) {
                    imageFile = file;
                }
            }
        }
        return imageFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.internal.impl.FragmentElementImpl#getProvider()
     */
    @Override
    public FragmentsProvider getProvider() {
        Assert.isTrue(getParent() instanceof FragmentElementImpl,
                "Fragment's parent is not set"); //$NON-NLS-1$
        return ((FragmentElementImpl) getParent()).getProvider();
    }

    /**
     * Get this fragment's associated file with the given file extension and
     * locale string.
     * 
     * @param fileExt
     *            extension
     * @param NL
     *            locale string
     * @return <code>IFile</code>. This file will be a resource handle and may
     *         not exist on the file system.
     */
    private IFile getAssociatedFile(String fileExt, String NL) {
        IFile file = null;
        IFile fragmentFile = (IFile) getResource();

        if (fragmentFile != null && fragmentFile.exists()) {
            IPath path = new Path(fragmentFile.getName()).removeFileExtension();

            if (NL != null) {
                String fileName = path.toString();
                fileName += "__" + NL; //$NON-NLS-1$
                path = new Path(fileName);
            }

            if (fileExt != null)
                path = path.addFileExtension(fileExt);

            file = fragmentFile.getParent().getFile(path);
        }

        return file;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.fragments.IContainedFragmentElement#getParent()
     */
    @Override
    public IFragmentCategory getParent() {
        return parent;
    }

    @Override
    protected FragmentProperties getProperties() {
        Assert.isTrue(getParent() != null, "Fragment's parent is not set."); //$NON-NLS-1$
        return ((FragmentCategoryImpl) getParent()).getProperties();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.IContainedFragmentElement#setParent(com.tibco
     * .xpd.fragments.IFragmentCategory)
     */
    public void setParent(IFragmentCategory parent) {
        this.parent = (FragmentCategoryImpl) parent;
    }

    /**
     * Create a fragment file.
     * 
     * @param parent
     *            parent folder
     * @param id
     *            name of the resource
     * @param data
     *            fragment data
     * @return fragment file, <code>null</code> if one could not be created
     * @throws CoreException
     */
    public static IFile createFragmentFile(IFolder parent, String id,
            String data) throws CoreException {
        IFile file = null;

        if (parent != null && id != null && data != null) {
            file =
                    parent.getFile(new Path(id)
                            .addFileExtension(FragmentConsts.FRAGMENT_FILE_EXT)
                            .toString());

            ByteArrayInputStream source =
                    new ByteArrayInputStream(data.getBytes());
            createFile(file, source);
        }

        return file;
    }

    /**
     * Create an image file.
     * 
     * @param parent
     *            parent folder
     * @param id
     *            name of the resource
     * @param imgData
     *            image data
     * @return image file, <code>null</code> if one could not be created
     * @throws CoreException
     */
    public static IFile createImageFile(IFolder parent, String id,
            ImageData imgData) throws CoreException {
        IFile file = null;

        if (parent != null && id != null && imgData != null) {
            file =
                    parent.getFile(new Path(id).addFileExtension(IMG_FILE_EXT)
                            .toString());
            createImageFile(file, imgData);
        }

        return file;
    }

    /**
     * Create the given image file with the content of the imgData.
     * 
     * @param file
     *            file to create
     * @param imgData
     *            image data
     * @throws CoreException
     */
    private static void createImageFile(IFile file, ImageData imgData)
            throws CoreException {
        if (file != null && imgData != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ByteArrayInputStream source = null;
            try {
                ImageLoader loader = new ImageLoader();
                loader.data = new ImageData[] { imgData };
                loader.save(outStream, IMG_TYPE);

                source = new ByteArrayInputStream(outStream.toByteArray());
                createFile(file, source);

            } finally {
                try {
                    outStream.close();
                    if (source != null) {
                        source.close();
                    }
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }

    /**
     * Create a file with the given name and content.
     * 
     * @param file
     *            file to create
     * @param source
     *            file content
     * @throws CoreException
     */
    private static void createFile(final IFile file, final InputStream source)
            throws CoreException {

        if (file != null && source != null) {
            final IWorkspace workspace = ResourcesPlugin.getWorkspace();

            FragmentsManager.getInstance()
                    .runWorkspaceOperation(new IWorkspaceRunnable() {

                        @Override
                        public void run(IProgressMonitor monitor)
                                throws CoreException {
                            if (file.exists()) {
                                // Update content
                                file.setContents(source, true, true, null);
                            } else {
                                // Create file
                                file.create(source, true, null);
                            }
                        }

                    },
                            file.exists() ? file : workspace.getRuleFactory()
                                    .createRule(file),
                            null);

        }
    }

    /**
     * Locale file creator.
     * 
     * @author njpatel
     * 
     * @param <T>
     *            data being saved to file.
     */
    private interface ILocaleFileCreator<T> {
        /**
         * Get the data from the provider.
         * 
         * @param provider
         *            fragment provider
         * @param NL
         *            locale string
         * @return localized data
         * @throws CoreException
         */
        T getData(FragmentsProvider provider, String NL) throws CoreException;

        /**
         * Save the localized data to the file.
         * 
         * @param file
         *            file to create
         * @param data
         *            localized data
         * @throws CoreException
         */
        void save(IFile file, T data) throws CoreException;
    }

    /**
     * Localized fragment data file creator.
     * 
     * @author njpatel
     * 
     */
    private class LocaleDataFileCreator implements ILocaleFileCreator<String> {

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.fragments.internal.impl.FragmentImpl.ILocaleFileCreator
         * #getData(com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.
         * FragmentsProvider, java.lang.String)
         */
        @Override
        public String getData(FragmentsProvider provider, String NL)
                throws CoreException {
            String data = null;

            if (provider != null && NL != null) {
                data =
                        provider.getProviderClass()
                                .getLocalizedData(FragmentImpl.this, NL);
            }
            return data;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.fragments.internal.impl.FragmentImpl.ILocaleFileCreator
         * #save(org.eclipse.core.resources.IFile, java.lang.Object)
         */
        @Override
        public void save(IFile file, String data) throws CoreException {
            if (file != null && data != null) {
                createFile(file, new ByteArrayInputStream(data.getBytes()));
            }
        }
    }

    /**
     * Localized image file creator.
     * 
     * @author njpatel
     * 
     */
    private class LocaleImageFileCreator implements
            ILocaleFileCreator<ImageData> {

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.fragments.internal.impl.FragmentImpl.ILocaleFileCreator
         * #getData(com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.
         * FragmentsProvider, java.lang.String)
         */
        @Override
        public ImageData getData(FragmentsProvider provider, String nl)
                throws CoreException {
            ImageData data = null;
            if (provider != null) {
                data =
                        provider.getProviderClass()
                                .getLocalizedImageData(FragmentImpl.this, nl);
            }
            return data;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.fragments.internal.impl.FragmentImpl.ILocaleFileCreator
         * #save(org.eclipse.core.resources.IFile, java.lang.Object)
         */
        @Override
        public void save(IFile file, ImageData data) throws CoreException {
            createImageFile(file, data);
        }
    }

}
