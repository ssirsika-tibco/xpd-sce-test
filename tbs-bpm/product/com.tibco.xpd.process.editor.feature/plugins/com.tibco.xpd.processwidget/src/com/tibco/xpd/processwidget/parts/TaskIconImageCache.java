/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.parts;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * TaskIconImageCache
 * 
 * 
 * @author aallway
 * @since 3.3 (2 Oct 2009)
 */
public class TaskIconImageCache {

    private static Map<String, Image> imageCache = new HashMap<String, Image>();

    private static IResourceChangeListener resourceChangeListener = null;

    public static Image getImage(String projectRelativeIconPath,
            Object taskModel) {
        if (resourceChangeListener == null) {
            resourceChangeListener = new ImageFileChangeListener();

            ResourcesPlugin.getWorkspace()
                    .addResourceChangeListener(resourceChangeListener,
                            IResourceChangeEvent.POST_CHANGE);
        }

        Image img = null;

        if (projectRelativeIconPath != null
                && projectRelativeIconPath.length() > 0) {
            if (imageCache.containsKey(projectRelativeIconPath)) {
                img = imageCache.get(projectRelativeIconPath);
            } else {
                if (taskModel instanceof EObject) {
                    IProject project =
                            WorkingCopyUtil.getProjectFor((EObject) taskModel);

                    if (project != null) {
                        IFile file =
                                project.getFile(new Path(
                                        projectRelativeIconPath));
                        if (file.exists()) {
                            try {
                                ImageData imgData =
                                        new ImageData(file.getContents());
                                if (imgData != null) {
                                    img = new Image(null, imgData);
                                }
                            } catch (CoreException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }

                imageCache.put(projectRelativeIconPath, img);
            }
        }
        return img;
    }

    /**
     * ImageFileChangeListener
     * <p>
     * If anything at all happens to an image file we have cached then disp[ose
     * of it and throw the cache away (so it gets reloaded on next call).
     * 
     * @author aallway
     * @since 3.3 (5 Oct 2009)
     */
    private static class ImageFileChangeListener implements
            IResourceChangeListener {

        public void resourceChanged(IResourceChangeEvent event) {
            final IResourceDelta mainDelta = event.getDelta();
            try {
                mainDelta.accept(new IResourceDeltaVisitor() {

                    public boolean visit(IResourceDelta delta)
                            throws CoreException {
                        IResource resource = delta.getResource();
                        if (resource != null) {
                            IPath path = resource.getProjectRelativePath();

                            String pathStr = path.toString();

                            if (imageCache.containsKey(pathStr)) {
                                imageCache.remove(pathStr);
                            }
                        }
                        return true;
                    }
                }, false);

            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            IResource resource = event.getResource();
            if (resource != null) {
            }

            return;
        }

    }

}
