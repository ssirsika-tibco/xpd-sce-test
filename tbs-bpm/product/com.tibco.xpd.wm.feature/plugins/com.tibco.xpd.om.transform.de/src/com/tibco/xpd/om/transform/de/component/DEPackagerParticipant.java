/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.component;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.tools.packager.IPackagerParticipant;
import com.tibco.amf.tools.packager.util.PackagerUtils;
import com.tibco.n2.directory.model.de.DocumentRoot;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.util.DeResourceImpl;
import com.tibco.xpd.om.transform.de.TransformDEActivator;

/**
 * @author kupadhya
 * 
 */
public class DEPackagerParticipant implements IPackagerParticipant {

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#done(java.lang.Object)
     * 
     * @param context
     */
    @Override
    public void done(Object context) {

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
    public File prepareArtifact(Object contextObject, IPath iPath) {
        if (!(contextObject instanceof Component)) {
            return null;
        }
        boolean isDEFile = isDEFile(iPath);
        if (!isDEFile) {
            return null;
        }
        Component bxComponent = (Component) contextObject;
        String timeStamp = PackagerUtils.getQualifier(bxComponent);
        IFile bpelFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(iPath);
        replaceDEVersionQualifier(bpelFile, timeStamp);
        return null;
    }

    static final String versionRangeRegex = "(version=\".*?)\\.qualifier(\")"; //$NON-NLS-1$

    /**
     * Replace the version qualifier with the actual timestamp in the DE model.
     * 
     * @param deFile
     * @param timeStamp
     */
    private void replaceDEVersionQualifier(IFile deFile, String timeStamp) {
        ResourceSetImpl rsImpl = new ResourceSetImpl();
        Resource deResource =
                rsImpl.getResource(URI.createPlatformResourceURI(deFile
                        .getFullPath().toString(), true), true);
        try {
            if (deResource instanceof DeResourceImpl) {
                DocumentRoot root = null;

                for (EObject eo : deResource.getContents()) {
                    if (eo instanceof DocumentRoot) {
                        root = (DocumentRoot) eo;
                        break;
                    }
                }

                if (root != null) {
                    ModelType directory = root.getModel();

                    if (directory != null) {
                        String version = directory.getVersion();

                        if (version != null) {
                            int idx = version.lastIndexOf(".qualifier"); //$NON-NLS-1$

                            if (idx > 0) {
                                String newVersion =
                                        version.substring(0, idx + 1)
                                                + timeStamp;

                                directory.setVersion(newVersion);

                                deResource.save(((DeResourceImpl) deResource)
                                        .getDefaultSaveOptions());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            TransformDEActivator
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Failed to update the version qualifier in the de model: " //$NON-NLS-1$
                                    + deFile.getFullPath().toString());
        } finally {
            if (deResource != null) {
                deResource.unload();
                rsImpl.getResources().clear();
            }
        }
    }

    private boolean isDEFile(IPath path) {
        String fileExtension = path.getFileExtension();
        if ("de".equals(fileExtension)) { //$NON-NLS-1$
            return true;
        }

        return false;
    }

    /**
     * @see com.tibco.amf.tools.packager.IPackagerParticipant#started(java.lang.Object)
     * 
     * @param context
     */
    @Override
    public void started(Object context) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
        String str =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><de:directory xmlns:de=\"http://tibco.com/n2/directory-model/1.0\" xmlns:demeta=\"http://tibco.com/n2/directory-metamodel/1.0\" name=\"UC2AOrganizationModel\" version=\"1.0.0.qualifier\">"; //$NON-NLS-1$
        String replaceStr = "201022010000"; //$NON-NLS-1$

        String replaceAll =
                str.replaceAll(versionRangeRegex, "$1." + replaceStr + "$2"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println(" replaced String is ---> " + replaceAll); //$NON-NLS-1$

    }

}
