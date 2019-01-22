/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.daa.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.amf.sca.model.extensionpoints.PolicySet;
import com.tibco.amf.sca.model.externalpolicy.ExternalPolicySetContainerDocument;
import com.tibco.xpd.processeditor.xpdl2.util.PolicySetReference;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Utility class supporting operations on Policy Sets.
 * 
 * @author jarciuch
 * @since 16 Jan 2012
 */
public class PolicySetUtil {

    public static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Resolves {@link PolicySet} from the given policy set reference.
     * 
     * @param ref
     *            policy set reference to resolve.
     * @return {@link PolicySet} resolved from the given policy set reference,
     *         or 'null' if it can't be resolved.
     */
    public static PolicySet resolvePolicySetReference(PolicySetReference ref) {

        EditingDomain ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        if (ref != null && ref.getUri() != null) {

            URI uri = URI.createURI(ref.getUri());

            if (uri != null) {

                EObject result = ed.getResourceSet().getEObject(uri, true);

                if (result == null) {

                    Resource resource =
                            ed.getResourceSet().getResource(uri, true);

                    resource.unload();

                    resource = ed.getResourceSet().getResource(uri, true);

                    result = resource.getEObject(uri.fragment());

                }

                if (result instanceof PolicySet) {

                    return (PolicySet) result;
                }
            }
        }
        return null;
    }

    /**
     * Gets the policy set template with the given name from the given resource
     * uri location
     * 
     * @param fileName
     * @return String - contents of the template file from the given template
     *         resource location
     */
    public static String getTemplate(String fileName, String resourceUri) {

        String template = null;
        try {

            InputStream policyInputStream =
                    new ResourceSetImpl().getURIConverter()
                            .createInputStream(URI.createURI(resourceUri
                                    + fileName));
            template = convertStreamToString(policyInputStream, "UTF-8"); //$NON-NLS-1$
        } catch (IOException e) {

            e.printStackTrace();
        }
        return template;
    }

    /**
     * Converts bytes from an {@link InputStream} to a string using the
     * specified encoding. It closes the stream after reading bytes.
     * 
     * @param is
     *            the input stream.
     * @param encoding
     *            the encoding used to decode bytes from the stream into
     *            characters.
     * @return the string containing bytes from the stream.
     * @throws IOException
     */
    private static String convertStreamToString(InputStream is, String encoding)
            throws IOException {

        if (is == null) {

            return null;
        }
        StringBuilder sb = new StringBuilder();
        String line;
        try {

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(is, encoding));
            while ((line = reader.readLine()) != null) {

                sb.append(line).append("\n"); //$NON-NLS-1$
            }
        } finally {

            is.close();
        }
        return sb.toString();
    }

    /**
     * Get or create policy set for the given policy file name by replacing the
     * given substitution variables in the given template. Gets the working copy
     * for the policy file and gets the policy set from the model
     * 
     * @param policyFolder
     * @param policyFileName
     * @param template
     * @param substitutions
     * @return {@link PolicySet}
     */
    public static PolicySet getCreatePolicySetFromTemplate(
            IFolder policyFolder, String policyFileName, String template,
            Map<String, String> substitutions) {

        try {

            if (!policyFolder.exists()) {

                ProjectUtil.createFolder(policyFolder, true, null);
            }
            IFile policyFile = policyFolder.getFile(policyFileName);
            if (!policyFile.exists()) {

                for (Entry<String, String> entry : substitutions.entrySet()) {

                    template =
                            template.replace(entry.getKey(), entry.getValue());
                }
                ByteArrayInputStream is =
                        new ByteArrayInputStream(template.getBytes("UTF-8")); //$NON-NLS-1$
                policyFile.create(is, IResource.FORCE, null);
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(policyFile);
            // wc.reLoad();
            EObject root = wc.getRootElement();
            if (root instanceof ExternalPolicySetContainerDocument) {

                ExternalPolicySetContainerDocument policyContainerDocument =
                        (ExternalPolicySetContainerDocument) root;
                return policyContainerDocument.getPolicySetContainer()
                        .getEmbeddedPolicySets().get(0);
            }

        } catch (CoreException e) {

            LOG.error(e);
        } catch (IOException e) {

            LOG.error(e);
        }
        return null;
    }

}
