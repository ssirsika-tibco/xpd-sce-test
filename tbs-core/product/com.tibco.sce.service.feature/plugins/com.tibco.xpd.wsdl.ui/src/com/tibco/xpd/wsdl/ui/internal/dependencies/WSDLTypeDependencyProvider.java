/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.internal.dependencies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;

/**
 * Provides dependency as set by the WSDL types (import/include of XSDs).
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class WSDLTypeDependencyProvider extends XSDDependencyProvider {

    /**
     * @see com.tibco.xpd.wsdl.ui.internal.dependencies.WSDLTypeDependencyProvider#getWorkingCopyClass()
     * 
     * @return
     */
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
            Types types = ((Definition) eo).getETypes();
            if (types != null) {
                for (Object next : types.getEExtensibilityElements()) {
                    if (next instanceof XSDSchemaExtensibilityElement) {
                        XSDSchema schema =
                                ((XSDSchemaExtensibilityElement) next)
                                        .getSchema();
                        dependencies.addAll(getDependencies(wsdlFile
                                .getWorkspace(),
                                URI.createPlatformResourceURI(wsdlFile
                                        .getFullPath().toString(), true),
                                schema));

                    }
                }
            }
        }

        return dependencies;
    }

}
