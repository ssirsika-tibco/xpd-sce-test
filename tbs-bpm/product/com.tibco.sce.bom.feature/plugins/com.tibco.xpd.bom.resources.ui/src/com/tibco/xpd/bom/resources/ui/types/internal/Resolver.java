/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.types.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.bom.resources.ui.types.BOMTypesFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.types.ITypeResolver;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * This class resolves the descriptive typed item to its instance.
 * 
 * @author rassisi
 * 
 */
@Deprecated
public class Resolver implements ITypeResolver {

    private String providerId;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeResolver#getTypeName(java.lang.
     * Object)
     */
    public String getTypeName(Object type) {
        if (type instanceof TypedItem) {
            return ((TypedItem) type).getName();
        }
        return ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeResolver#isValidDataType(java.lang
     * .Object)
     */
    public boolean isValidDataType(Object dataType) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeResolver#toObject(com.tibco.xpd
     * .resources.ui.types.TypedItem, org.eclipse.core.resources.IProject)
     */
    public Object toObject(TypedItem item, IResource[] queryResources,
            Object[] contentToExclude) {
        EditingDomain ed = getEditingDomain();
        Assert.isNotNull(ed, "Editing domain"); //$NON-NLS-1$
        if (item != null) {
            URI uri = URI.createURI(item.getUriString());
            if (uri != null) {
                Object result = ed.getResourceSet().getEObject(uri, true);
                return result;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeResolver#toType(java.lang.Object)
     */
    public TypeInfo toType(Object element) {
        return BOMTypesFactory.getType(element);
    }

    /**
     * @return
     */
    protected EditingDomain getEditingDomain() {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    /**
     * @return
     */
    public String getProviderId() {
        return providerId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeResolver#setProviderId(java.lang
     * .String)
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
