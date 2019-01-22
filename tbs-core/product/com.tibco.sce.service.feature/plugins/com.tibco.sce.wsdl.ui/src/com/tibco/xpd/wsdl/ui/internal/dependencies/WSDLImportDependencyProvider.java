/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.internal.dependencies;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;

import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;

/**
 * Provides dependency as set by the import element in WSDLs (import of WSDLs or
 * XSDs).
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class WSDLImportDependencyProvider implements
        IWorkingCopyDependencyProvider {

    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return WsdlWorkingCopy.class;
    }

    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Set<IResource> dependencies = new HashSet<IResource>();

        EObject eo = wc.getRootElement();
        IFile wsdlFile = getFile(wc);
        if (eo instanceof Definition && wsdlFile != null) {

            Map<?, ?> imports = ((Definition) eo).getImports();

            if (imports != null) {
                for (Object value : imports.values()) {
                    if (value instanceof Collection<?>) {
                        for (Object impValue : (Collection<?>) value) {
                            if (impValue instanceof Import) {
                                String location =
                                        ((Import) impValue).getLocationURI();

                                if (location != null) {
                                    URI uri = URI.createURI(location, /* ignoreEscaped */
                                            true);
                                    if (uri.isRelative()) {
                                        IPath containerPath =
                                                wsdlFile.getParent()
                                                        .getFullPath();
                                        IPath importPath =
                                                containerPath.append(location);

                                        if (importPath != null) {
                                            IResource resource =
                                                    wsdlFile.getWorkspace()
                                                            .getRoot()
                                                            .findMember(importPath);

                                            if (resource instanceof IFile) {
                                                dependencies.add(resource);
                                            }
                                        }
                                    }
                                }
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
    private IFile getFile(WorkingCopy wc) {
        List<IResource> resources = wc.getEclipseResources();
        if (resources != null && !resources.isEmpty()) {
            if (resources.get(0) instanceof IFile) {
                return (IFile) resources.get(0);
            }
        }
        return null;
    }
}
