package com.tibco.xpd.om.resources.ui.types.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.types.ITypeResolver;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * Resolver Provider for the PickerDialog.
 * 
 * @author rassisi
 * 
 */
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

        try {

            if (item.equals(OMTypesFactory.TYPE_ATTRIBUTE_TYPE)) {
                return item.getQualifiedName();
            }
            if (item.getData() instanceof OrgElementType) {
                return item.getData();
            }

            EditingDomain ed = getEditingDomain();
            Assert.isNotNull(ed, "Editing domain"); //$NON-NLS-1$

            if (item.getUriString() == null
                    || item.getUriString().length() == 0) {

                // List<TypedItem> standardItems = Filter
                // .getStandardTypesTypedItems();
                // for (TypedItem standardItem : standardItems) {
                // if (item.getTypeId().equals(standardItem.getTypeId())) {
                // Object result = Filter.getStandardType(standardItem);
                // return result;
                // }
                // }
                return null;

            } else {
                URI uri = URI.createURI(item.getUriString());
                if (uri != null) {
                    Object result = ed.getResourceSet().getEObject(uri, true);
                    if (contentToExclude != null) {
                        for (Object object : contentToExclude) {
                            if (result == object) {
                                return null;
                            }
                        }
                    }
                    return result;
                }
            }
        } catch (Exception ex) {
            OMResourcesUIActivator.getDefault().getLogger().error(ex);
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
        return OMTypesFactory.getType(element);
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
