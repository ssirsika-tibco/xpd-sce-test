/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.wp.component;

import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.ComponentProperty;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.extensionpoints.Extension;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.amf.sca.model.implementationtype.webapp.StaticResource;
import com.tibco.amf.sca.model.implementationtype.webapp.WebAppFactory;
import com.tibco.amf.sca.model.implementationtype.webapp.WebAppUpdate;
import com.tibco.n2.model.wp.BRMDescriptor;
import com.tibco.n2.model.wp.WPDescriptor;
import com.tibco.n2.model.wp.WPFactory;
import com.tibco.n2.model.wp.WPSpecification;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.n2.wp.WPActivator;
import com.tibco.xpd.n2.wp.WPGenerator;
import com.tibco.xpd.n2.wp.utils.WPUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * @author mtorres
 * 
 */
public class WPCompositeContributor extends CompositeContributor {

    private static final String WP_DESCRIPTOR_VERSION = "1.2.0"; //$NON-NLS-1$

    private static final String BRM_DESCRIPTOR_VERSION = "1.0.0"; //$NON-NLS-1$

    private static final Logger LOG = WPActivator.getDefault().getLogger();

    private static Pattern qualifierPattern = Pattern.compile("\\.qualifier"); //$NON-NLS-1$

    @Override
    public IStatus contributeToComposite(final IProject project,
            final IContainer stagingArea, final Composite composite,
            final URI compositeLocation, final String timeStamp,
            final boolean replaceWithTS, final IChangeRecorder changeRecorder,
            IProgressMonitor monitor) {

        try {

            monitor.beginTask("Adding Presentations", 3);

            IStatus status = Status.OK_STATUS;
            if (stagingArea instanceof IFolder) {
                long beforeTimeMillis = System.currentTimeMillis();
                final ComponentTypeActivator cts =
                        ComponentTypeActivator.getDefault();
                final CompositeModelBuilder mb = cts.getModelBuilder();
                TransactionalEditingDomain editingDomain =
                        TransactionUtil.getEditingDomain(composite);
                IFolder stagingFolder = (IFolder) stagingArea;

                status =
                        WPGenerator.getInstance().generateN2Modules(project,
                                stagingFolder,
                                timeStamp);

                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                monitor.worked(1);

                // prepare model
                IResource sadResource =
                        WPUtil.getArchiveDescriptorResource(stagingArea);
                IResource brmResource = BRMUtils.getWTResource(stagingArea);
                IFile wpModule = null;
                if (sadResource != null && sadResource.exists()) {
                    wpModule =
                            stagingFolder.getFile(N2PENamingUtils.WP_FILENAME);
                    WPFactory wpf = WPFactory.eINSTANCE;
                    WPSpecification specification = wpf.createWPSpecification();
                    WPDescriptor pwDescriptor = wpf.createWPDescriptor();
                    String sadLocation = sadResource.getFullPath().toString();
                    pwDescriptor.setLocation(sadLocation);
                    pwDescriptor.setVersion(WP_DESCRIPTOR_VERSION);//$NON-NLS-1$
                    specification.setWpDescriptor(pwDescriptor);
                    if (brmResource != null && brmResource.exists()) {
                        BRMDescriptor brmDescriptor = wpf.createBRMDescriptor();
                        String brmLocation =
                                brmResource.getFullPath().toString();
                        brmDescriptor.setLocation(brmLocation);
                        brmDescriptor.setVersion(BRM_DESCRIPTOR_VERSION);//$NON-NLS-1$
                        specification.setBrmDescriptor(brmDescriptor);
                    }
                    URI wpURI =
                            URI.createPlatformResourceURI(wpModule
                                    .getFullPath().toPortableString(), true);
                    Resource wpResource =
                            new ResourceSetImpl().createResource(wpURI);
                    wpResource.getContents().add(specification);
                    try {
                        wpResource.save(null);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        wpModule.refreshLocal(IResource.DEPTH_ZERO, null);
                        wpModule.setDerived(true);
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    LOG.info("Either ServiceArchive or wt.xml does not exist under .compositeResource folder"); //$NON-NLS-1$
                }

                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                monitor.worked(1);

                // create WP Component
                if (wpModule != null && wpModule.exists()) {

                    final String wpLoc =
                            wpModule.getFullPath().toPortableString();
                    final Implementation impl =
                            cts.getComponentTypeService()
                                    .createImplementationFromURL(wpLoc,
                                            compositeLocation);
                    UnprotectedWriteOperation setContentsOp =
                            new UnprotectedWriteOperation(editingDomain) {
                                @Override
                                protected Object doExecute() {
                                    Component comp =
                                            mb.createComponent(composite, impl);

                                    comp.setName("WorkPresentation");//$NON-NLS-1$
                                    comp.setVersion(CompositeUtil
                                            .getVersionNumber(project));
                                    // Add wp web App extension
                                    IFolder formChannelParentFolder =
                                            project.getWorkspace()
                                                    .getRoot()
                                                    .getFolder(stagingArea
                                                            .getFullPath()
                                                            .append(WPUtil.WP_RESOURCES_FOLDERNAME));
                                    addWpWebAppExtension(comp,
                                            formChannelParentFolder,
                                            project);
                                    for (ComponentService cs : comp
                                            .getServices()) {
                                        mb.promoteService(cs);
                                    }
                                    for (ComponentReference cs : comp
                                            .getReferences()) {
                                        mb.promoteReference(cs);
                                    }
                                    for (ComponentProperty cs : comp
                                            .getProperties()) {
                                        mb.promoteProperty(cs);
                                    }
                                    return comp;
                                }
                            };
                    setContentsOp.execute();

                }

                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                monitor.worked(1);

                long afterTimeMillis = System.currentTimeMillis();
                long timeTaken = afterTimeMillis - beforeTimeMillis;
                System.err
                        .println("*********The time taken to create a WP Component " //$NON-NLS-1$
                                + timeTaken + " milliseconds"); //$NON-NLS-1$
            }
            return status;

        } finally {
            monitor.done();
        }

    }

    /**
     * @return
     */
    private String getCompositeBaseName(IProject project) {
        return NameUtil.getInternalName(project.getName(), true);
    }

    private void addWpWebAppExtension(Component comp,
            IFolder formChannelParentFolder, IProject project) {
        if (comp != null && formChannelParentFolder != null
                && formChannelParentFolder.exists()) {
            Extension webAppExtension =
                    SCAExtensionsFactory.INSTANCE.createExtension();
            webAppExtension.setName(NameUtil.getInternalName(project.getName()
                    + "WebApp", true));
            webAppExtension
                    .setRequiredVersion(N2PENamingUtils.WEB_APP_REQUIRED_VERSION);
            webAppExtension
                    .setExtensionPoint(N2PENamingUtils.WEB_APP_EXTENSIONPOINT_ID);
            try {
                IResource[] members = formChannelParentFolder.members();
                if (members != null && members.length > 0) {
                    WebAppUpdate webAppUpdate =
                            WebAppFactory.eINSTANCE.createWebAppUpdate();
                    final EList<StaticResource> staticResources =
                            webAppUpdate.getStaticResources();
                    for (final IResource channel : members) {
                        if (channel instanceof IFolder) {
                            try {
                                channel.accept(new IResourceVisitor() {
                                    public boolean visit(IResource resource)
                                            throws CoreException {
                                        if (resource instanceof IFile) {
                                            // JA: All resources will be added.
                                            // (Excluding empty folders.)
                                            StaticResource staticResource =
                                                    WebAppFactory.eINSTANCE
                                                            .createStaticResource();
                                            staticResource
                                                    .setLocation(getStaticResourceLocation(resource));
                                            staticResource
                                                    .setPath(getStaticResourcePath(channel,
                                                            resource));
                                            staticResources.add(staticResource);
                                            return false;
                                        }
                                        return true;
                                    }
                                });
                            } catch (CoreException e) {
                                LOG.error(e);
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    webAppExtension.setExtensionConfiguration(webAppUpdate);
                }
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            comp.getExtensions().add(webAppExtension);
        }
    }

    private String getStaticResourceLocation(IResource resource) {
        String resourceLocation = resource.getFullPath().toPortableString();
        if (resourceLocation.startsWith("/")) { //$NON-NLS-1$
            resourceLocation = resourceLocation.substring(1);
        }
        return resourceLocation;
    }

    private String getStaticResourcePath(IResource channel, IResource resource) {
        String resourceLocationStr = resource.getFullPath().toPortableString();
        URI resourceURI =
                URI.createPlatformResourceURI(resourceLocationStr, false);
        URI baseURI =
                URI.createPlatformResourceURI(channel.getFullPath()
                        .toPortableString(), false);
        String resourcePath = "/" //$NON-NLS-1$
                + resourceURI.deresolve(baseURI, true, true, true).toString();
        return qualifierPattern.matcher(resourcePath)
                .replaceFirst(".\\$\\$COMPONENT_QUALIFIER\\$\\$"); //$NON-NLS-1$
    }

}
