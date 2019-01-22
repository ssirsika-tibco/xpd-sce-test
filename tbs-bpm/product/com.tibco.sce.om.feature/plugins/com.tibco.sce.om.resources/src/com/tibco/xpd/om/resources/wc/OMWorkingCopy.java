/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.wc;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.provider.OMItemProviderAdapterFactory;
import com.tibco.xpd.om.core.om.provider.OrganisationModelEditPlugin;
import com.tibco.xpd.om.core.om.provider.OrganisationModelEditPlugin.Implementation;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * Working copy for Organisation Model.
 * <p>
 * <i>Created: 21 Nov 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class OMWorkingCopy extends AbstractGMFWorkingCopy {

    /**
     * 
     */
    private static final Implementation EDIT_PLUGIN =
            OrganisationModelEditPlugin.getPlugin();

    public static final String JAVA_PACKAGE_SEPARATOR = "."; //$NON-NLS-1$

    public static final String UML_PACKAGE_SEPARATOR = "::"; //$NON-NLS-1$

    /**
     * OM Working copy.
     * 
     * @param resources
     *            list of <code>IResource</code>s to be maintained by this
     *            working copy.
     */
    public OMWorkingCopy(List<IResource> resources) {
        super(resources);
        registerResourceProvider(getWorkingCopyEPackage(), EDIT_PLUGIN);
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#createAdapterFactory()
     * 
     * @return
     */
    @Override
    protected AdapterFactory createAdapterFactory() {
        AdapterFactory af = super.createAdapterFactory();
        /*
         * XPD-5300: Need to register the adapter factory now, as otherwise will
         * not be able to get the label provider for OM models until one of the
         * OM models is first loaded. This way the OM adapter factory is
         * available when this adapter factory is used before an Org Model
         * resource is loaded (e.g. by WorkingCopyUtil.getText).
         */
        if (af instanceof ComposedAdapterFactory) {
            ((ComposedAdapterFactory) af)
                    .addAdapterFactory(new OMItemProviderAdapterFactory());
        }

        return af;
    }

    @Override
    protected EObject getModelFromResource(Resource res) {
        // Get the BaseOrgModel from the resource
        if (res != null && res.getContents() != null) {
            for (EObject content : res.getContents()) {
                if (content instanceof BaseOrgModel) {
                    return content;
                }
            }
        }
        return null;
    }

    @Override
    public EPackage getWorkingCopyEPackage() {
        return OMPackage.eINSTANCE;
    }

    /**
     * Produce fully qualified name of the concept. It differ from uml qualified
     * name as it doesn't use model name as prefix, and use '.' as section
     * separator.
     * 
     * @param cl
     * @return
     */
    public static String getQualifiedName(NamedElement cl) {
        return getQualifiedName(cl.getQualifiedName());
    }

    public static String getQualifiedName(String rawName) {
        if (rawName == null)
            return ""; //$NON-NLS-1$
        // removed, because it looks like we don't need this any more
        // rawName = rawName.substring(rawName.indexOf(UML_PACKAGE_SEPARATOR) +
        // 2);

        rawName =
                rawName.replace(UML_PACKAGE_SEPARATOR, JAVA_PACKAGE_SEPARATOR);
        return rawName;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#getMetaText(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    public String getMetaText(EObject eo) {
        /*
         * XPD-5300: Need to change label for dynamic organizations
         */
        if (eo instanceof Organization && ((Organization) eo).isDynamic()) {
            return EDIT_PLUGIN.getString("_UI_DynamicOrganisation_type"); //$NON-NLS-1$
        }
        return super.getMetaText(eo);
    }
}
