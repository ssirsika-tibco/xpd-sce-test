/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.internal.dependencies;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSchemaDirective;

import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xsd.XSDWorkingCopy;

/**
 * Provides dependencies between XSDs (imports and includes).
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class XSDDependencyProvider implements IWorkingCopyDependencyProvider {

    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return XSDWorkingCopy.class;
    }

    /**
     * @see com.tibco.xpd.wsdl.ui.internal.dependencies.WSDLTypeDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        EObject eo = wc.getRootElement();

        if (eo instanceof XSDSchema) {
            IFile file = getFile(wc);
            if (file != null) {
                return getDependencies(file.getWorkspace(),
                        URI.createPlatformResourceURI(file.getFullPath()
                                .toString(), true),
                        (XSDSchema) eo);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Get external references from the given schema.
     * 
     * @param hostURI
     * @param schema
     * @return
     */
    protected final Collection<IResource> getDependencies(IWorkspace workspace,
            URI hostURI, XSDSchema schema) {

        Set<IResource> dependencies = new HashSet<IResource>();

        if (schema != null) {
            for (XSDSchemaContent content : schema.getContents()) {
                if (content instanceof XSDSchemaDirective) {
                    String location =
                            ((XSDSchemaDirective) content).getSchemaLocation();

                    if (location != null) {
                        URI uri = URI.createURI(location);
                        if (uri.isRelative()) {
                            URI absoluteURI = uri.resolve(hostURI);

                            if (absoluteURI.isPlatformResource()) {
                                IFile file =
                                        workspace
                                                .getRoot()
                                                .getFile(new Path(
                                                        absoluteURI
                                                                .toPlatformString(true)));
                                dependencies.add(file);
                            }
                        }
                    }
                }
            }
        }
        return dependencies;
    }

    /**
     * Get the file being managed by the given working copy.
     * 
     * @param wc
     * @return
     */
    protected final IFile getFile(WorkingCopy wc) {
        List<IResource> resources = wc.getEclipseResources();
        if (resources != null && !resources.isEmpty()) {
            if (resources.get(0) instanceof IFile) {
                return (IFile) resources.get(0);
            }
        }
        return null;
    }
}
