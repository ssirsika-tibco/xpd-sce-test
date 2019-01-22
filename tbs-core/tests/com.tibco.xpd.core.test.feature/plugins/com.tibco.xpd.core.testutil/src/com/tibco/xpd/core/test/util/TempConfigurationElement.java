/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

/**
 * @author rsomayaj
 * 
 */
public class TempConfigurationElement implements IConfigurationElement {

    private String wizardId;

    /**
     * Default constructor sets the wizard ID to the BPM Developer Project.
     */
    public TempConfigurationElement() {
        this("com.tibco.xpd.newProject.BPMSOADeveloper"); //$NON-NLS-1$
    }

    /**
     * @param wizardId
     *            The ID of the wizard to return.
     */
    public TempConfigurationElement(String wizardId) {
        this.wizardId = wizardId;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#createExecutableExtension(java.lang.String)
     * 
     * @param propertyName
     * @return
     * @throws CoreException
     */
    @Override
    public Object createExecutableExtension(String propertyName)
            throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getAttribute(java.lang.String)
     * 
     * @param name
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public String getAttribute(String name)
            throws InvalidRegistryObjectException {
        if ("id".equals(name)) { //$NON-NLS-1$
            return wizardId;
        } else if ("finalPerspective".equals(name)) { //$NON-NLS-1$
            return "com.tibco.modeling.perspective"; //$NON-NLS-1$
        }
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getAttributeAsIs(java.lang.String)
     * 
     * @param name
     * @return
     * @throws InvalidRegistryObjectException
     * @deprecated
     */
    @Deprecated
    @Override
    public String getAttributeAsIs(String name)
            throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getAttributeNames()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public String[] getAttributeNames() throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getChildren()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public IConfigurationElement[] getChildren()
            throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getChildren(java.lang.String)
     * 
     * @param name
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public IConfigurationElement[] getChildren(String name)
            throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getContributor()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public IContributor getContributor() throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getDeclaringExtension()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public IExtension getDeclaringExtension()
            throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getName()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public String getName() throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getNamespace()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     * @deprecated
     */
    @Deprecated
    @Override
    public String getNamespace() throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getNamespaceIdentifier()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public String getNamespaceIdentifier()
            throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getParent()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public Object getParent() throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getValue()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public String getValue() throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getValueAsIs()
     * 
     * @return
     * @throws InvalidRegistryObjectException
     * @deprecated
     */
    @Deprecated
    @Override
    public String getValueAsIs() throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#isValid()
     * 
     * @return
     */
    @Override
    public boolean isValid() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getAttribute(java.lang.String,
     *      java.lang.String)
     * 
     * @param attrName
     * @param locale
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public String getAttribute(String attrName, String locale)
            throws InvalidRegistryObjectException {
        // Ignore locale.
        return getAttribute(attrName);
    }

    /**
     * @see org.eclipse.core.runtime.IConfigurationElement#getValue(java.lang.String)
     * 
     * @param locale
     * @return
     * @throws InvalidRegistryObjectException
     */
    @Override
    public String getValue(String locale) throws InvalidRegistryObjectException {
        // TODO Auto-generated method stub
        return null;
    }

}
