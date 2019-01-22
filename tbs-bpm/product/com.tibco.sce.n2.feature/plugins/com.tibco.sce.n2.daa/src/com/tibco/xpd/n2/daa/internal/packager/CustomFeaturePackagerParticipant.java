/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.daa.internal.packager;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;

import com.tibco.amf.tools.packager.IPackagerParticipant2;
import com.tibco.amf.tools.packager.IPackagerService;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Packager participant which contributes (by adding a pre-built custom
 * feature(s)) to a process of packaging DAA for a BPM project.
 * <p/>
 * Because the participant needs to be bound into component type, binding or
 * interface {@link #reset()} method must be called before each DAA build (
 * {@link IPackagerService#generateDaa(com.tibco.amf.model.daaspec.DistributedApplicationArchiveDescriptor, org.eclipse.core.runtime.IProgressMonitor)}
 * ) to prevent adding custom feature multiple times.
 * <p/>
 * Currently this participant uses process component implementation type
 * 'implementation.bx' pageflow component implementation type
 * 'implementation.pageflow' and bds component implementation type for global
 * data 'implementation.bds'.
 * 
 * @author jarciuch
 * @since 17 Aug 2012
 */
public class CustomFeaturePackagerParticipant implements IPackagerParticipant2 {

    private static final Logger LOG = Activator.getDefault().getLogger();

    private static boolean getPrebuiltFeaturesInvoked = false;

    private static boolean getPrebuiltPluginsInvoked = false;

    /**
     * Reset should be called before each DAA packaging cycle. After each
     * {@link #reset()} call {@link #doGetPrebuiltFeatures(Object)} and
     * {@link #doGetPrebuiltPlugins(Object)} will be called at most once each.
     */
    public static synchronized void reset() {
        getPrebuiltFeaturesInvoked = false;
        getPrebuiltPluginsInvoked = false;
    }

    private static synchronized void prebuiltFeaturesInvoked() {
        getPrebuiltFeaturesInvoked = true;
    }

    private static synchronized void prebuiltPluginsInvoked() {
        getPrebuiltPluginsInvoked = true;
    }

    private static synchronized boolean isPrebuiltFeaturesInvoked() {
        return getPrebuiltFeaturesInvoked;
    }

    private static synchronized boolean isPrebuiltPluginsInvoked() {
        return getPrebuiltPluginsInvoked;
    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant2#getPrebuiltFeatures(java.lang.Object)
     * 
     * @param context
     * @return
     */
    @Override
    public Set<File> getPrebuiltFeatures(Object context) {
        if (!isPrebuiltFeaturesInvoked()) {
            prebuiltFeaturesInvoked();
            return doGetPrebuiltFeatures(context);
        }
        return null;
    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant2#getPrebuiltPlugins(java.lang.Object)
     * 
     * @param context
     * @return
     */
    @Override
    public Set<File> getPrebuiltPlugins(Object context) {
        if (!isPrebuiltPluginsInvoked()) {
            prebuiltPluginsInvoked();
            return doGetPrebuiltPlugins(context);
        }
        return null;
    }

    /**
     * Returns set of pre-built features folders (folders containing
     * feature.xml).
     * 
     * This method will run only once after each reset.
     * 
     * @see com.tibco.amf.tools.packager.IPackagerParticipant2#getPrebuiltFeatures(java.lang.Object)
     * 
     * @param context
     *            one of 'implementation.bx' SCA components.
     * @return
     */
    protected Set<File> doGetPrebuiltFeatures(Object context) {
        if (context instanceof EObject) {
            IProject project = WorkingCopyUtil.getProjectFor((EObject) context);
            if (project != null) {

                IFolder bpmStagingFolder =
                        N2PENamingUtils.getModuleOutputFolder(project, false);
                // For example: /MyProject/.bpm/customfeatures/features
                IFolder featuresFolder =
                        bpmStagingFolder.getFolder(new Path(
                                N2PENamingUtils.CUSTOMFEATURES_FOLDER_NAME)
                                .append(N2PENamingUtils.FEATURES_FOLDER));
                if (featuresFolder.isAccessible()) {
                    Set<File> featureFolderSet = new LinkedHashSet<File>();
                    try {
                        for (IResource r : featuresFolder.members()) {
                            if (r instanceof IFolder) {
                                featureFolderSet.add(new File(r.getLocation()
                                        .toPortableString()));
                            }
                        }
                        return featureFolderSet.isEmpty() ? null
                                : featureFolderSet;
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns set of plug-ins files.
     * 
     * This method will run only once after each reset.
     * 
     * @see com.tibco.amf.tools.packager.IPackagerParticipant2#getPrebuiltPlugins(java.lang.Object)
     * 
     * @param context
     *            one of 'implementation.bx' SCA components.
     * @return
     */
    protected Set<File> doGetPrebuiltPlugins(Object context) {
        if (context instanceof EObject) {
            IProject project = WorkingCopyUtil.getProjectFor((EObject) context);
            if (project != null) {
                IFolder bpmStagingFolder =
                        N2PENamingUtils.getModuleOutputFolder(project, false);
                // For example: /MyProject/.bpm/customfeatures/plugins
                IFolder pluginsFolder =
                        bpmStagingFolder.getFolder(new Path(
                                N2PENamingUtils.CUSTOMFEATURES_FOLDER_NAME)
                                .append(N2PENamingUtils.PLUGINS_FOLDER));
                if (pluginsFolder.isAccessible()) {
                    Set<File> pluginsFileSet = new LinkedHashSet<File>();
                    try {
                        for (IResource r : pluginsFolder.members()) {
                            pluginsFileSet.add(new File(r.getLocation()
                                    .toPortableString()));
                        }
                        return pluginsFileSet.isEmpty() ? null : pluginsFileSet;
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#started(java.lang.Object)
     * 
     * @param context
     */
    @Override
    public void started(Object context) {
        // NOT USED.
    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#prepareArtifact(java.lang.Object,
     *      org.eclipse.core.runtime.IPath)
     * 
     * @param context
     * @param resourcePath
     * @return
     */
    @Override
    public File prepareArtifact(Object context, IPath resourcePath) {
        // NOT USED.
        return null;
    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#done(java.lang.Object)
     * 
     * @param context
     */
    @Override
    public void done(Object context) {
        // NOT USED.
    }
}
