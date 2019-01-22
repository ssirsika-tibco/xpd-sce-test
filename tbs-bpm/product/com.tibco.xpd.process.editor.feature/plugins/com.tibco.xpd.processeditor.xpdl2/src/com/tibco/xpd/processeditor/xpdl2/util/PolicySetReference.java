/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Utility class acting as a reference to a PolicySet inside an external file
 * (but doesn't depend on policy set EObject). This reference contains label and
 * URI (compatible with EMF URI) to the PolicySet EObject.
 * 
 * @author jarciuch
 * @since 12 Jan 2012
 */
public class PolicySetReference {

    private static final String CUSTOM_POLICY_NAME_KEY = "CustomPolicyName"; //$NON-NLS-1$

    private static final String CUSTOM_POLICY_URI_KEY = "CustomPolicyURI"; //$NON-NLS-1$

    private String name;

    private String uri;

    private IFile file;

    public PolicySetReference() {
    }

    public PolicySetReference(String name, String uri) {
        this.name = name;
        this.uri = uri;
        this.file = getIFileFormURI(uri);
    }

    public static PolicySetReference getPolicySetReference(PickerItem item) {
        return new PolicySetReference(item.getName(), item.getURI());
    }

    public static PolicySetReference getPolicySetReference(
            WsSoapSecurity soapSecurity) {
        if (soapSecurity.getSecurityPolicy().size() > 0
                && soapSecurity.getSecurityPolicy().get(0) != null) {
            WsSecurityPolicy securityPolicy =
                    soapSecurity.getSecurityPolicy().get(0);
            return getPolicySetReference(securityPolicy);
        }
        return null;
    }

    public static PolicySetReference getPolicySetReference(
            ExtendedAttributesContainer container) {
        if (container != null) {
            EList<ExtendedAttribute> eas = container.getExtendedAttributes();
            String name = getAttributeValueByName(eas, CUSTOM_POLICY_NAME_KEY);
            String uri = getAttributeValueByName(eas, CUSTOM_POLICY_URI_KEY);
            return new PolicySetReference(name, uri);
        }
        return null;
    }

    // public void setCustomPolicySet(WsSoapSecurity wsSoapSecurity) {
    // if (wsSoapSecurity == null) {
    // wsSoapSecurity =
    // XpdExtensionFactory.eINSTANCE.createWsSoapSecurity();
    // }
    // WsSecurityPolicy securityPolicy;
    // if (wsSoapSecurity.getSecurityPolicy().size() > 0) {
    // securityPolicy = wsSoapSecurity.getSecurityPolicy().get(0);
    // } else {
    // securityPolicy =
    // XpdExtensionFactory.eINSTANCE.createWsSecurityPolicy();
    // wsSoapSecurity.getSecurityPolicy().add(0, securityPolicy);
    // }
    // if (securityPolicy.getType() != SecurityPolicy.CUSTOM) {
    // securityPolicy.setType(SecurityPolicy.CUSTOM);
    // }
    // if (securityPolicy.getGovernanceApplicationName() != null) {
    // securityPolicy.setGovernanceApplicationName(null);
    // }
    // setCustomPolicySet(securityPolicy);
    //
    // }

    /**
     * @param securityPolicy
     */
    public void setCustomPolicySet(ExtendedAttributesContainer container) {
        boolean foundName = false, foundUri = false;
        EList<ExtendedAttribute> eas = container.getExtendedAttributes();
        for (Iterator<ExtendedAttribute> iterator = eas.iterator(); iterator
                .hasNext();) {
            ExtendedAttribute ea = iterator.next();
            if (CUSTOM_POLICY_NAME_KEY.equals(ea.getName())) {
                if (foundName) {
                    iterator.remove();
                } else {
                    if (this.name != null) {
                        ea.setValue(this.name);
                    } else {
                        iterator.remove();
                    }
                    foundName = true;
                }
            }
            if (CUSTOM_POLICY_URI_KEY.equals(ea.getName())) {
                if (foundUri) {
                    iterator.remove();
                } else {
                    if (this.uri != null) {
                        ea.setValue(this.uri);
                    } else {
                        iterator.remove();
                    }
                    foundUri = true;
                }
            }
        }
        if (!foundName) {
            ExtendedAttribute ea =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            ea.setName(CUSTOM_POLICY_NAME_KEY);
            ea.setValue(this.name);
            eas.add(ea);
        }
        if (!foundUri) {
            ExtendedAttribute ea =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            ea.setName(CUSTOM_POLICY_URI_KEY);
            ea.setValue(this.uri);
            eas.add(ea);
        }
    }

    public static void clearCustomPolicySetAttributes(
            ExtendedAttributesContainer container) {
        new PolicySetReference(null, null).setCustomPolicySet(container);
    }

    private static String getAttributeValueByName(EList<ExtendedAttribute> eas,
            String name) {
        ExtendedAttribute ea = getAttributeByName(eas, name);
        return ea != null ? ea.getValue() : null;
    }

    private static ExtendedAttribute getAttributeByName(
            EList<ExtendedAttribute> eas, String name) {
        for (ExtendedAttribute ea : eas) {
            if (name.equals(ea.getName())) {
                return ea;
            }
        }
        return null;
    }

    private static IFile getIFileFormURI(String uriString) {
        if (uriString == null || uriString.trim().isEmpty()) {
            return null;
        }
        URI uri = URI.createURI(uriString);
        if (uri != null) {
            uri = uri.trimFragment();
            String platformString = uri.toPlatformString(true);
            if (platformString != null) {
                IResource resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .findMember(platformString);
                if (resource instanceof IFile) {
                    return (IFile) resource;
                }
            }
        }
        return null;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PolicySetReference other = (PolicySetReference) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        String s =
                name != null ? name
                        : Messages.PolicySetReference_NoValueSet_label;

        /* Show file whether or not is actually exists. */
        if (uri != null && uri.length() > 0) {
            URI actualUri = URI.createURI(uri, false);

            if (actualUri != null && actualUri.isPlatformResource()) {
                IPath path = new Path(actualUri.toPlatformString(true));
                IFile result =
                        ResourcesPlugin.getWorkspace().getRoot().getFile(path);
                if (result != null) {
                    s +=
                            " ("    + URI.decode(result.getFullPath().toString()) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        }
        return s;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @return the file
     */
    public IFile getFile() {
        return file;
    }

    /**
     * Ascertain whether the policy set actually exists.
     * 
     * @return <code>true</code> if the policy set exists.
     */
    public boolean policySetExists() {
        EditingDomain ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        if (uri != null) {

            URI actualUri = URI.createURI(uri);

            if (actualUri != null) {
                Resource resource =
                        ed.getResourceSet().getResource(actualUri, true);

                if (resource != null) {

                    IFile file = WorkspaceSynchronizer.getFile(resource);

                    if (file != null && file.isAccessible()) {
                        EObject result =
                                ed.getResourceSet().getEObject(actualUri, true);

                        if (result == null) {
                            /*
                             * Old Policyset resources have a nasty habit of
                             * lying around in the resource set, so if we can't
                             * find it then force a reload just in case.
                             */
                            resource.unload();

                            resource =
                                    ed.getResourceSet().getResource(actualUri,
                                            true);

                            result = resource.getEObject(actualUri.fragment());
                        }

                        return result != null;
                    }
                }
            }
        }

        return false;
    }
}